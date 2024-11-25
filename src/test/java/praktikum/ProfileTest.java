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
import praktikum.pom.MainPage;
import praktikum.pom.ProfilePage;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static praktikum.driver.WebDriverCreator.createWebDriver;

public class ProfileTest {

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
    @DisplayName("Check transition to profile page")
    public void enterToProfile() {
        login(user.getEmail(), user.getPassword());

        MainPage mainPage = new MainPage(webDriver);
        clickPersonalAccountButton(mainPage);

        ProfilePage profilePage = new ProfilePage(webDriver);

        assertTrue(profilePage.checkExitButton());
    }

    @Test
    @DisplayName("Check trasition to main page from profile page by clicking on Constructor button")
    public void transitionToMainPageFromProfilePageByConstructorButton() {
        login(user.getEmail(), user.getPassword());

        MainPage mainPage = new MainPage(webDriver);
        clickPersonalAccountButton(mainPage);

        clickConstructorButtonOnProfilePage();

        assertTrue(mainPage.checkPlaceOrderButton());
    }

    @Test
    @DisplayName("Check trasition to main page from profile page by clicking on logo")
    public void transitionToMainPageFromProfilePageByLogo() {
        login(user.getEmail(), user.getPassword());

        MainPage mainPage = new MainPage(webDriver);
        clickPersonalAccountButton(mainPage);

        clickLogoButtonOnProfilePage();

        assertTrue(mainPage.checkPlaceOrderButton());
    }

    @Test
    @DisplayName("Check exit from the profile")
    public void exitFromProfile() {
        login(user.getEmail(), user.getPassword());

        MainPage mainPage = new MainPage(webDriver);
        clickPersonalAccountButton(mainPage);

        clickExitButtonOnProfilePage();

        LoginPage loginPage = new LoginPage(webDriver);
        assertTrue(loginPage.checkLoginButton());
    }

    @Step("Open Login page, then fill email and password field and click login")
    public void login(String email, String password) {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.open();
        loginPage.
                sendToEmailInput(email).
                sendToPasswordInput(password).
                clickLoginButton();
    }

    @Step("Click on Personal account button")
    public void clickPersonalAccountButton(MainPage mainPage) {
        mainPage.clickPersonalAccountButton();
    }

    @Step("Click on Constructor button on Profile Page")
    public void clickConstructorButtonOnProfilePage() {
        ProfilePage profilePage = new ProfilePage(webDriver);
        profilePage.clickConstructorButton();
    }

    @Step("Click on Logo button on Profile Page")
    public void clickLogoButtonOnProfilePage() {
        ProfilePage profilePage = new ProfilePage(webDriver);
        profilePage.clickLogoButton();
    }

    @Step("Click on Exit button on Profile Page")
    public void clickExitButtonOnProfilePage() {
        ProfilePage profilePage = new ProfilePage(webDriver);
        profilePage.clickExitButton();
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
