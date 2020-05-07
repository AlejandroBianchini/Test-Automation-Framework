package testclasses.androidtest;

import io.appium.java_client.MobileDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BaseTest;
import pages.mobilePages.androidpages.AndroidCalculatorPage;

import java.io.IOException;

public class AndroidCalculatorTest extends BaseTest {

    private AndroidCalculatorPage androidCalculatorPage;
    private MobileDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(ITestResult result) throws IOException {
        driver = getMobileDriver();
        androidCalculatorPage = new AndroidCalculatorPage(driver);
        report.startTest(result);
    }

    @Test()
    public void myFirstTest(){
        String result = androidCalculatorPage.Sumar();

        Assert.assertEquals(result, "2");
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result){
        report.addTestReport(result);
        driver.quit();
    }
}
