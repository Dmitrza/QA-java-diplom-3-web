package praktikum.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private final WebDriver webDriver;

    private final String url = "https://stellarburgers.nomoreparties.site";

    private final By personalAccountButton = By.xpath(".//a[@href = '/account']");
    private final By logIntoAccountButton = By.xpath(".//button[text() = 'Войти в аккаунт']");
    //private final By logIntoaccountButton = By.xpath(".//button[text() = 'Войти в аккаунт']");
    private final By placeOrderButton = By.xpath(".//button[text() = 'Оформить заказ']");
    private final By bunsTab = By.xpath(".//span[text() = 'Булки']/parent::div");
    private final By chosenBunsTab = By.xpath(".//div[contains (@class, 'tab_tab_type_current__2BEPc')]/span[text() = 'Булки']");
    private final By saucesTab = By.xpath(".//span[text() = 'Соусы']/parent::div");
    private final By chosenSaucesTab = By.xpath(".//div[contains (@class, 'tab_tab_type_current__2BEPc')]/span[text() = 'Соусы']");
    private final By fillingsTab = By.xpath(".//span[text() = 'Начинки']/parent::div");
    private final By chosenFillingsTab = By.xpath(".//div[contains (@class, 'tab_tab_type_current__2BEPc')]/span[text() = 'Начинки']");
    private final By bunsHeader = By.xpath(".//h2[text() = 'Булки']");
    private final By saucesHeader = By.xpath(".//h2[text() = 'Соусы']");
    private final By fillingsHeader = By.xpath(".//h2[text() = 'Начинки']");

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public MainPage open() {
        webDriver.get(url);
        return this;
    }

    public MainPage clickPersonalAccountButton() {
        webDriver.findElement(personalAccountButton).click();
        return this;
    }

    public MainPage clickLogIntoAccountButton() {
        webDriver.findElement(logIntoAccountButton).click();
        return this;
    }

    public MainPage clickPlaceOrderButton() {
        webDriver.findElement(placeOrderButton).click();
        return this;
    }

    public MainPage clickBunsTab() {
        webDriver.findElement(bunsTab).click();
        return this;
    }

    public MainPage clickSaucesTab() {
        webDriver.findElement(saucesTab).click();
        return this;
    }

    public MainPage clickFillingsTab() {
        webDriver.findElement(fillingsTab).click();
        return this;
    }

    /*public boolean checkLogIntoAccountButton() {
        return webDriver.findElement(logIntoAccountButton).isDisplayed();
    }*/

    public boolean checkPlaceOrderButton() {
        return webDriver.findElement(placeOrderButton).isDisplayed();
    }

    public boolean checkBunsHeader() {
        return webDriver.findElement(bunsHeader).isDisplayed();
    }

    public boolean checkSaucesHeader() {
        return webDriver.findElement(saucesHeader).isDisplayed();
    }

    public boolean checkFillingsHeader() {
        return webDriver.findElement(fillingsHeader).isDisplayed();
    }

    public boolean checkBunsTabIsChosen() {
        return webDriver.findElement(chosenBunsTab).isDisplayed();
    }

    public boolean checkSaucesTabIsChosen() {
        return webDriver.findElement(chosenSaucesTab).isDisplayed();
    }

    public boolean checkFillingsTabIsChosen() {
        return webDriver.findElement(chosenFillingsTab).isDisplayed();
    }

}