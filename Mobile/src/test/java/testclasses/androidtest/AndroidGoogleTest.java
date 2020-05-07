package testclasses.androidtest;

import io.appium.java_client.MobileDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BaseTest;
import pages.mobilePages.androidpages.AndroidGooglePage;

import java.io.IOException;

public class AndroidGoogleTest extends BaseTest {

    private AndroidGooglePage androidGooglePage;
    private MobileDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(ITestResult result) throws IOException {
        driver = getMobileDriver();
        androidGooglePage = new AndroidGooglePage(driver);
        report.startTest(result);
    }

    @Test()
    public void GoogleSearchTest(){
        androidGooglePage.Goto();
        androidGooglePage.searchSomething("selanium");

        Assert.assertTrue(true);
    }

    @Test()
    public void ReportTest(){
        androidGooglePage.Goto();
        androidGooglePage.searchSomething("selanium");

        Assert.assertTrue(false);
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result){
        report.addTestReport(result);
        driver.quit();
    }
}
