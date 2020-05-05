package utilities;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DriverHelper {

    private static final String IE_DRIVER_LOCATION = "../Core/src/main/resources/drivers/IEDriverServer.exe";
    private static final String FIREFOX_DRIVER_LOCATION = "../Core/src/main/resources/drivers/geckodriver.exe";
    private static final String CHROME_DRIVER_LOCATION = "../Core/src/main/resources/drivers/chromedriver.exe";
    private static final String CHROME_CAPS_LOCATION = "capabilities/";
    private static final String IE_CAPS_LOCATION = "capabilities/IEOptions.properties";

    private static DesiredCapabilities capabilities;

    public static WebDriver openBrowser(Config config) {
        Map<String, String> capabilitiesMap = null;
        boolean runHeadless = Boolean.valueOf(config.getConfig(Config.Param.RUNHEADLESS.getKey()));

        String custom_capabilities = config.getConfig(Config.Param.CUSTOM_CAPABILITIES.getKey());
        if (!custom_capabilities.isEmpty()) {
            capabilitiesMap = loadCapabilities(custom_capabilities);
        }

        System.setProperty("webdriver.ie.driver", IE_DRIVER_LOCATION);
        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_LOCATION);
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LOCATION);

        WebDriver driver;

        switch (config.getConfig(Config.Param.BROWSER.getKey()).toLowerCase())
        {
            case "chrome":
                ChromeOptions chromeOptions = getChromeOptions(capabilitiesMap, runHeadless, config);
                driver = new ChromeDriver(chromeOptions);
                setBrowserWindowSize(driver, config);
                return driver;
            case "firefox":
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                return driver;
            case "iexplorer":
            case "ie":
                Map<String, String> defaultCapabilitiesMap = loadCapabilities(IE_CAPS_LOCATION);
                InternetExplorerOptions option = getIEOptions(defaultCapabilitiesMap);
                driver = new InternetExplorerDriver(option);
                driver.manage().window().maximize();
                return driver;
            default:
                return null;
        }

    }

    public static MobileDriver openDevice(Config config) throws MalformedURLException {
        String appiumServerUrl = config.getConfig(Config.Param.SERVERURL.getKey()); // Set the server URL to be used in subsequent driver actions in the test script
        String currentDirectory =System.getProperty("user.dir");
//                "../Core/src/main/resources/mobiledrivers";

//        currentDirectory = currentDirectory + "\\src\\test\\resources\\drivers";
        currentDirectory = currentDirectory + "\\..\\Core\\src\\main\\resources\\mobiledrivers";
        initializeAppiumDriverCapabilities(config);

        switch (config.getConfig(Config.Param.PLATFORMNAME.getKey()).toLowerCase()) {
            case "android":
            case "droid":
                capabilities.setCapability("chromedriverExecutableDir", currentDirectory);
                return new AndroidDriver(new URL(appiumServerUrl), capabilities);
            case "ios":
                return new IOSDriver(new URL(appiumServerUrl), capabilities);
            default:
                return new AndroidDriver(new URL(appiumServerUrl), capabilities);
        }
    }

    public static void initializeAppiumDriverCapabilities(Config config) {
//        mobile = true;
        capabilities = new DesiredCapabilities();
        // The generated session will be visible to you only.
        capabilities.setCapability("sessionName", "Automation test session");
        capabilities.setCapability("deviceOrientation", config.getConfig(Config.Param.DEVICEORIENTATION.getKey()));
//        capabilities.setCapability("captureScreenshots", Boolean.valueOf(config.getConfig(Config.Param.CAPTURESCREENSHOTS.getKey()).toLowerCase()));
        capabilities.setCapability("deviceGroup", config.getConfig(Config.Param.DEVICEGROUP.getKey()));
        capabilities.setCapability("autoAcceptAlerts", Boolean.valueOf(config.getConfig(Config.Param.AUTOACCEPTALERTS.getKey()).toLowerCase()));
        capabilities.setCapability("noReset", config.getConfig(Config.Param.NORESET.getKey()));
        capabilities.setCapability("fullReset", config.getConfig(Config.Param.FULLRESET.getKey()));
        // For deviceName, platformVersion Kobiton supports wildcard
        // character *, with 3 formats: *text, text* and *text*
        // If there is no *, Kobiton will match the exact text provided
        capabilities.setCapability("deviceName", config.getConfig(Config.Param.DEVICENAME.getKey()));
        capabilities.setCapability("platformVersion", config.getConfig(Config.Param.PLATFORMVERSION.getKey()));
        capabilities.setCapability("autoGrantPermissions", Boolean.valueOf(config.getConfig(Config.Param.AUTOGRANTPERMISSIONS.getKey()).toLowerCase()));
        capabilities.setCapability("platformName", config.getConfig(Config.Param.PLATFORMNAME.getKey()));
        capabilities.setCapability("appWaitActivity", config.getConfig(Config.Param.APPWAITACTIVITY.getKey()));
        // Open either a specified app or a web browser in the mobile device
        if (config.getConfig(Config.Param.APP.getKey()).isEmpty()) {
            capabilities.setCapability("browserName", config.getConfig(Config.Param.BROWSERNAME.getKey()));
        } else {
            // The maximum size of application is 500MB
            // By default, HTTP requests from testing library are expired
            // in 2 minutes while the app copying and installation may
            // take up-to 30 minutes. Therefore, you need to extend the HTTP
            // request timeout duration in your testing library so that
            // it doesn't interrupt while the device is being initialized.
            capabilities.setCapability("app", config.getConfig(Config.Param.APP.getKey()));
        }
    }


    private static Map<String, String> loadCapabilities(String fileName) {
        Properties props = new Properties();
        URL baseResource = ClassLoader.getSystemResource(fileName);
        try {
            if (baseResource != null) {
                props.load(baseResource.openStream());
            } else {
                throw new RuntimeException("Unable to find custom capabilities file '" + fileName + "'!");
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load custom capabilities from '" + baseResource.getPath() + "'!", e);
        }

        Map<String, String> capabilitiesMap = new HashMap(props);

        for (Map.Entry<String, String> entry : capabilitiesMap.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
        }

        return capabilitiesMap;
    }

    private static ChromeOptions getChromeOptions(Map<String, String> optionsMap, boolean runHeadless, Config config) {

        boolean mobileEmulation = Boolean.valueOf(config.getConfig(Config.Param.MOBILEEMULATION.getKey()));
        String deviceName = config.getConfig(Config.Param.DEVICENAME.getKey());

        ChromeOptions chromeOptions = new ChromeOptions();

        if (mobileEmulation) {
            Map<String, String> mobileEmulationMap = new HashMap<>();
            mobileEmulationMap.put("deviceName", deviceName);
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulationMap);
        }

        if (optionsMap != null) {
            for (Map.Entry<String, String> option : optionsMap.entrySet()) {
                String value = option.getValue();
                String key = option.getKey();
                switch (key.toLowerCase()) {
                    case "locale":
                        chromeOptions.addArguments("--lang=" + value);
                        break;
//						Agregar case por cada capabilitie que se pretende agregar
                }
            }
        }

        chromeOptions.setHeadless(runHeadless);

        return chromeOptions;
    }

    private static InternetExplorerOptions getIEOptions(Map<String, String> optionsMap) {
        InternetExplorerOptions options = new InternetExplorerOptions();

        if (optionsMap != null) {
            for (Map.Entry<String, String> option : optionsMap.entrySet()) {
                String value = option.getValue();
                String key = option.getKey();
                if (value.toLowerCase().equals("true") || value.toLowerCase().equals("false")) {
                    options.setCapability(key, Boolean.valueOf(value));
                } else {
                    options.setCapability(key, value);
                }
            }
        }
        return options;
    }

    private static void setBrowserWindowSize(WebDriver driver, Config config) {
        String windowSize = config.getConfig(Config.Param.WINDOWS_SIZE.getKey());

        if (!windowSize.isEmpty()) {
            if (windowSize.equalsIgnoreCase("fullscreen")) {
                driver.manage().window().fullscreen();
            } else if (windowSize.equalsIgnoreCase("maximize")) {
                driver.manage().window().maximize();
            } else {
                String[] args = windowSize.split(",");
                int width = Integer.parseInt(args[0]);
                int height = Integer.parseInt(args[1]);
                driver.manage().window().setSize(new Dimension(width, height));
            }
        }
    }

}
