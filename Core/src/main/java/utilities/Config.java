package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_FILE_LOCATION = "../Web/src/test/resources/config/Configuration.properties";
//            "../Test-Automation-Framework/Web/src/test/resources/config/Configuration.properties";

    private Properties properties = null;

    public Config() {
        properties = new Properties();
    }

    /**
     * Will load the properties for the browser
     */
    public void loadProperties(String driverType, String browser, String runHeadless, String environment,String mobileEmulation, String deviceName, String custom_capabilities,
                               String platformName, String platformVersion, String serverUrl, String deviceOrientation, String deviceGroup, String browserName, String app,
                               String appPackage, String appActivity, String autoAcceptAlerts, String autoGrantPermissions, String appWaitActivity,String desiredCapabilities, String noReset, String fullReset) {
        InputStream input = null;
        try {
            input = new FileInputStream(PROPERTIES_FILE_LOCATION);
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!driverType.isEmpty()) {
            properties.put(Param.DRIVERTYPE.getKey(), driverType);
        }
        if (!browser.isEmpty()) {
            properties.put(Param.BROWSER.getKey(), browser);
        }
        if (!runHeadless.isEmpty()) {
            properties.put(Param.RUNHEADLESS.getKey(), runHeadless);
        }
        if (!environment.isEmpty()) {
            properties.put(Param.ENVIRONMENT.getKey(), environment);
        }
        if (!mobileEmulation.isEmpty()) {
            properties.put(Param.MOBILEEMULATION.getKey(), mobileEmulation);
        }
        if (!deviceName.isEmpty()) {
            properties.put(Param.DEVICENAME.getKey(), deviceName);
        }
        if (!custom_capabilities.isEmpty()) {
            properties.put(Param.CUSTOM_CAPABILITIES.getKey(), custom_capabilities);
        }
        if (!platformName.isEmpty()) {
            properties.put(Param.PLATFORMNAME.getKey(), platformName);
        }
        if (!platformVersion.isEmpty()) {
            properties.put(Param.PLATFORMVERSION.getKey(), platformVersion);
        }
        if (!serverUrl.isEmpty()) {
            properties.put(Param.KOBITONSERVERURL.getKey(), serverUrl);
        }
        if (!deviceOrientation.isEmpty()) {
            properties.put(Param.DEVICEORIENTATION.getKey(), deviceOrientation);
        }
        if (!deviceGroup.isEmpty()) {
            properties.put(Param.DEVICEGROUP.getKey(), deviceGroup);
        }
        if (!browserName.isEmpty()) {
            properties.put(Param.BROWSERNAME.getKey(), browserName);
        }
        if (!app.isEmpty()) {
            properties.put(Param.APP.getKey(), app);
        }
        if (!appPackage.isEmpty()) {
            properties.put(Param.APPPACKAGE.getKey(), appPackage);
        }
        if (!appActivity.isEmpty()) {
            properties.put(Param.APPACTIVITY.getKey(), appActivity);
        }
        if (!autoAcceptAlerts.isEmpty()) {
            properties.put(Param.AUTOACCEPTALERTS.getKey(), autoAcceptAlerts);
        }
        if (!autoGrantPermissions.isEmpty()) {
            properties.put(Param.AUTOGRANTPERMISSIONS.getKey(), autoGrantPermissions);
        }
        if (!appWaitActivity.isEmpty()) {
            properties.put(Param.APPWAITACTIVITY.getKey(), appWaitActivity);
        }
        if (!desiredCapabilities.isEmpty()) {
            properties.put(Param.DESIREDCAPABILITIES.getKey(), desiredCapabilities);
        }
        if (!noReset.isEmpty()) {
            properties.put(Param.NORESET.getKey(), noReset);
        }
        if (!fullReset.isEmpty()) {
            properties.put(Param.FULLRESET.getKey(), fullReset);
        }
    }

    public String getConfig(String param){
        return properties.getProperty(param);
    }

    public void setConfig(String param, String value){
        properties.put(param, value);
    }

    public enum Param {
        BROWSER("browser"),
        WINDOWS_SIZE("windowSize"),
        DRIVERTYPE("driverType"),
        RUNHEADLESS("runHeadless"),
        ENVIRONMENT("environment"),
        MOBILEEMULATION("mobileEmulation"),
        DEVICENAME("deviceName"),
        CUSTOM_CAPABILITIES("custom_capabilities"),
        PLATFORMNAME("platformName"),
        PLATFORMVERSION("platformVersion"),
        KOBITONSERVERURL("kobitonServerUrl"),
        DEVICEORIENTATION("deviceOrientation"),
        CAPTURESCREENSHOTS("captureScreenshots"),
        DEVICEGROUP("deviceGroup"),
        BROWSERNAME("browserName"),
        APP("app"),
        APPPACKAGE("appPackage"),
        APPACTIVITY("appActivity"),
        AUTOACCEPTALERTS("autoAcceptAlerts"),
        AUTOGRANTPERMISSIONS("autoGrantPermissions"),
        APPWAITACTIVITY("appWaitActivity"),
        DESIREDCAPABILITIES("desiredCapabilities"),
        NORESET("noReset"),
        FULLRESET("fullReset");

        private final String key;

        Param(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
