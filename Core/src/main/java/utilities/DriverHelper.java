package utilities;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.io.IOException;
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
