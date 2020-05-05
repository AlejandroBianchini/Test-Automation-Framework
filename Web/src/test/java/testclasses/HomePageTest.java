package testclasses;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

import java.io.IOException;

public class HomePageTest extends BaseTest{

    private HomePage HomePage;
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(ITestResult result) throws IOException {
        driver = getDriver();
        HomePage = new HomePage(driver);
        report.startTest(result);
    }

    @Test(description = "probando test en diferentes category")
    public void pruebaOtraCategoryDeReporte() throws InvalidFormatException, IOException {
        HomePage.Buscar();

        Assert.assertTrue(true);
    }

    @Test(description = "oootro test")
    public void otroTest() throws IOException, InvalidFormatException {
        Assert.fail();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result){
        report.addTestReport(result);
        driver.quit();
    }
}
