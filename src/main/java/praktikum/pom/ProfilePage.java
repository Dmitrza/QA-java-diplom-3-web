package praktikum.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {

    private final WebDriver webDriver;

    private final String url = "https://stellarburgers.nomoreparties.site/account/profile";

    private final By exitButton = By.xpath(".//button[text() = 'Выход']");
    private final By constructorButton = By.xpath(".//p[text() = 'Конструктор']");
    private final By logoButton = By.xpath(".//div/a[@href = '/']");

    public ProfilePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public ProfilePage open() {
        webDriver.get(url);
        return this;
    }

    public ProfilePage clickExitButton() {
        webDriver.findElement(exitButton).click();
        return this;
    }

    public ProfilePage clickConstructorButton() {
        webDriver.findElement(constructorButton).click();
        return this;
    }

    public ProfilePage clickLogoButton() {
        webDriver.findElement(logoButton).click();
        return this;
    }

    public boolean checkExitButton() {
        return webDriver.findElement(exitButton).isDisplayed();
    }

}
