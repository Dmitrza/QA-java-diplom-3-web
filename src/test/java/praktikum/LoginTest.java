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
import praktikum.pom.ForgotPasswordPage;
import praktikum.pom.LoginPage;
import praktikum.pom.MainPage;
import praktikum.pom.RegistrationPage;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static praktikum.driver.WebDriverCreator.createWebDriver;

public class LoginTest {

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
        Response response = sendCreateUser(user);
        token = response.body().as(UserCreatedResponse.class).
                getAccessToken().
                replace("Bearer ", "");
    }

    @Test
    @DisplayName("Login via \"Log into account\" button on main page")
    public void loginFromMainPageLoginButton() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open();
        mainPage.clickLogIntoAccountButton();

        login(user.getEmail(), user.getPassword());
        assertTrue(mainPage.checkPlaceOrderButton());
    }

    @Test
    @DisplayName("Login via button \"Personal account\"")
    public void loginFromMainPagePersonalAccountButton() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open();
        mainPage.clickPersonalAccountButton();

        login(user.getEmail(), user.getPassword());
        assertTrue(mainPage.checkPlaceOrderButton());
    }

    @Test
    @DisplayName("Login via button in registration form")
    public void loginFromRegistrationForm() {
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        registrationPage.open();
        registrationPage.clickLoginLink();

        login(user.getEmail(), user.getPassword());

        MainPage mainPage = new MainPage(webDriver);
        assertTrue(mainPage.checkPlaceOrderButton());
    }

    @Test
    @DisplayName("Login via button from password recover form")
    public void loginFromForgotPasswordForm() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(webDriver);
        forgotPasswordPage.open();
        forgotPasswordPage.clickLoginLink();

        login(user.getEmail(), user.getPassword());

        MainPage mainPage = new MainPage(webDriver);
        assertTrue(mainPage.checkPlaceOrderButton());
    }

    @Step("Fill email and password field and click login")
    public void login(String email, String password) {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.
                sendToEmailInput(email).
                sendToPasswordInput(password).
                clickLoginButton();
    }

    @Step("Send POST request to /api/auth/register")
    public Response sendCreateUser(User user) {
        return given().header("Content-type", "application/json").
                and().body(user).post("/api/auth/register");
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
