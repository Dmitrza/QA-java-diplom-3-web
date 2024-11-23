package praktikum.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver webDriver;

    private final String url = "https://stellarburgers.nomoreparties.site/login";

    private final By emailField = By.xpath(".//input[@name = 'name']");
    private final By passwordField = By.xpath(".//input[@name = 'Пароль']");
    private final By loginButton = By.xpath(".//button[text() = 'Войти']");
    private final By registrationLink = By.xpath(".//a[@href = '/register']");
    private final By recoverPasswordButton = By.xpath(".//a[@href = '/forgot-password']");

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public LoginPage open() {
        webDriver.get(url);
        return this;
    }

    public LoginPage sendToEmailInput(String text) {
        webDriver.findElement(emailField).sendKeys(text);
        return this;
    }

    public LoginPage sendToPasswordInput(String text) {
        webDriver.findElement(passwordField).sendKeys(text);
        return this;
    }

    public LoginPage clickLoginButton() {
        webDriver.findElement(loginButton).click();
        return this;
    }

    public LoginPage clickRegisterLink() {
        webDriver.findElement(registrationLink).click();
        return this;
    }

    public LoginPage clickRecoverPasswordButton() {
        webDriver.findElement(recoverPasswordButton).click();
        return this;
    }

    public boolean checkLoginButton() {
        return webDriver.findElement(loginButton).isDisplayed();
    }

}
