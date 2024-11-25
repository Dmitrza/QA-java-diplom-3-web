package praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.pom.MainPage;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertTrue;
import static praktikum.driver.WebDriverCreator.createWebDriver;

public class ConstructorTest {

    private WebDriver webDriver;

    @Before
    public void setUp() {
        webDriver = createWebDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(5, ChronoUnit.SECONDS));
    }

    @Test
    @DisplayName("Check transition to Buns section")
    public void transitionToBunsSection() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickFillingsTab()
                .clickBunsTab();
        assertTrue(mainPage.checkBunsTabIsChosen());
    }

    @Test
    @DisplayName("Check transition to Sauces section")
    public void transitionToSaucesSection() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickSaucesTab();
        assertTrue(mainPage.checkSaucesTabIsChosen());
    }

    @Test
    @DisplayName("Check transition to Fillings section")
    public void transitionToFillingsSection() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickFillingsTab();
        assertTrue(mainPage.checkFillingsTabIsChosen());
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }

}
