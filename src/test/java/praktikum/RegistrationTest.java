package praktikum;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.api.User;
import praktikum.api.UserCreatedResponse;
import praktikum.pom.LoginPage;
import praktikum.pom.RegistrationPage;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static praktikum.driver.WebDriverCreator.createWebDriver;

public class RegistrationTest {

    Faker faker = new Faker();
    private WebDriver webDriver;
    User user;
    String token;

    @Before
    public void setUp() {
        webDriver = createWebDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(5, ChronoUnit.SECONDS));
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        user = new User(faker.internet().emailAddress(),
                faker.internet().password(6,10),
                faker.name().firstName());
    }

    @Test
    @DisplayName("Check registration with valid fields")
    public void successfulRegistration() {
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        registrationPage.open();
        register(registrationPage, user.getName(), user.getEmail(), user.getPassword());
        token = sendLoginUser(user).body().as(UserCreatedResponse.class).
                getAccessToken().
                replace("Bearer ", "");

        LoginPage loginPage = new LoginPage(webDriver);
        assertTrue(loginPage.checkLoginButton());
    }

    @Test
    @DisplayName("Check registration with invalid password")
    public void errorWhenRegisterWithInvalidPassword() {
        user.setPassword("art");
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        registrationPage.open();
        register(registrationPage, user.getName(), user.getEmail(), user.getPassword());
        assertEquals("Некорректный пароль", registrationPage.getWrongPasswordErrorMessageText());
    }

    @Step("Fill name, email and password fields and click register button")
    public void register(RegistrationPage registrationPage, String name, String email, String password){
        registrationPage.
                sendToNameInput(name).
                sendToEmailInput(email).
                sendToPasswordInput(password).
                clickRegisterButton();
    }

    @Step("Send POST request to /api/auth/login")
    public Response sendLoginUser(User user) {
        user.setName(null);
        return given().header("Content-type", "application/json").
                and().body(user).post("/api/auth/login");
    }

    @Step("Send DELETE request to /api/auth/user")
    public void sendDeleteUser(String token) {
        given().header("Content-type", "application/json").
                auth().oauth2(token).delete("/api/auth/user");
    }


    @After
    public void tearDown() {
        webDriver.quit();
        if (token != null) {
            sendDeleteUser(token);
        }
    }

}
