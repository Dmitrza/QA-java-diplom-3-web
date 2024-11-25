package praktikum.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {

    private final WebDriver webDriver;

    private final String url = "https://stellarburgers.nomoreparties.site/register";

    private final By nameAndEmailFields = By.xpath(".//input");
    private final By passwordField = By.xpath(".//input[@name = 'Пароль']");
    private final By registerButton = By.xpath(".//button[text() = 'Зарегистрироваться']");
    private final By loginLink = By.xpath(".//a[@href = '/login']");
    public final By wrongPasswordErrorMessage = By.xpath(".//p[text() = 'Некорректный пароль']");

    public RegistrationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public RegistrationPage open() {
        webDriver.get(url);
        return this;
    }

    public RegistrationPage sendToNameInput(String text) {
        webDriver.findElements(nameAndEmailFields).get(0).sendKeys(text);
        return this;
    }

    public RegistrationPage sendToEmailInput(String text) {
        webDriver.findElements(nameAndEmailFields).get(1).sendKeys(text);
        return this;
    }

    public RegistrationPage sendToPasswordInput(String text) {
        webDriver.findElement(passwordField).sendKeys(text);
        return this;
    }

    public RegistrationPage clickRegisterButton() {
        webDriver.findElement(registerButton).click();
        return this;
    }

    public String getWrongPasswordErrorMessageText() {
        return webDriver.findElement(wrongPasswordErrorMessage).getText();
    }

    public RegistrationPage clickLoginLink() {
        webDriver.findElement(loginLink).click();
        return this;
    }

}
