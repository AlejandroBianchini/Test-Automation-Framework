package testclasses;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;
import utilities.Config;
import utilities.DriverHelper;
import utilities.Report;

public class BaseTest {
    public Report report = new Report();
    Config configurations = null;
//    public WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void initializeTestReports(XmlTest xml) {
        report.addSystemInfo(xml);
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"driverType", "browser", "runHeadless", "env", "mobileEmulation", "deviceName", "custom_capabilities", "platformName",
            "platformVersion", "serverUrl", "deviceOrientation", "deviceGroup", "browserName", "app", "appPackage", "appActivity",
            "autoAcceptAlerts", "autoGrantPermissions", "appWaitActivity", "desiredCapabilities", "noReset", "fullReset"})
    public void initializeBrowser(@Optional("") String driverType, @Optional("") String browser, @Optional("") String runHeadless, @Optional("") String env,
                                  @Optional("") String mobileEmulation, @Optional("") String deviceName, @Optional("") String custom_capabilities,
                                  @Optional("") String platformName, @Optional("") String platformVersion, @Optional("") String serverUrl, @Optional("") String deviceOrientation,
                                  @Optional("") String deviceGroup, @Optional("") String browserName, @Optional("") String app, @Optional("") String appPackage,
                                  @Optional("") String appActivity, @Optional("") String autoAcceptAlerts, @Optional("") String autoGrantPermissions,
                                  @Optional("") String appWaitActivity, @Optional("") String desiredCapabilities, @Optional("") String noReset, @Optional("") String fullReset
    ) {

        configurations = new Config();
        configurations.loadProperties(driverType, browser, runHeadless, env, mobileEmulation, deviceName, custom_capabilities,
                platformName, platformVersion, serverUrl, deviceOrientation,deviceGroup, browserName, app, appPackage, appActivity,
                autoAcceptAlerts, autoGrantPermissions, appWaitActivity, desiredCapabilities, noReset, fullReset);
//        driver = DriverHelper.openBrowser(configurations);
    }

    public WebDriver getDriver(){
        return DriverHelper.openBrowser(configurations);
    }

    @AfterMethod(alwaysRun = true)
    public void AfterMethod(ITestResult result) {

//        driver.close();
    }

    @AfterTest(alwaysRun = true)
    public void AfterTest() {

    report.generateReport();
    }
}
