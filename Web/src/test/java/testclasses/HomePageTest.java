package testclasses;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.mobilePages.androidpages.AndroidGooglePage;
import pages.webPages.HomePage;
import pages.BaseTest;

import java.io.IOException;

public class HomePageTest extends BaseTest {

    private HomePage homePage;
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(ITestResult result) throws IOException {
        driver = getDriver();
        homePage = new HomePage(driver);
        report.startTest(result);
    }

    @Test(description = "probando test en diferentes category")
    public void pruebaOtraCategoryDeReporte() {

        homePage.Buscar();

        Assert.assertTrue(true);
    }

    @Test(description = "oootro test")
    public void testWebAndMobile() throws IOException {
        homePage.Buscar();
        driver.quit();

        MobileDriver _driver = getMobileDriver();
        AndroidGooglePage androidGooglePage = new AndroidGooglePage(_driver);

        androidGooglePage.Goto();
        androidGooglePage.searchSomething("Hola mundo");

        Assert.assertTrue(true);
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result){
        report.addTestReport(result);
        homePage.quit();
    }
}
