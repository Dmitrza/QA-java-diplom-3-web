package praktikum.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {

    private final WebDriver webDriver;

    private final String url = "https://stellarburgers.nomoreparties.site/forgot-password";

    private final By loginLink = By.xpath(".//a[@href = '/login']");

    public ForgotPasswordPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public ForgotPasswordPage open() {
        webDriver.get(url);
        return this;
    }

    public ForgotPasswordPage clickLoginLink() {
        webDriver.findElement(loginLink).click();
        return this;
    }

}
