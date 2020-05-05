package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GlobalVariablesReader {

    private final String GLOBAL_DATA_FILE_LOCATION = "../Web/src/test/resources/data/GlobalVariables.properties";
    private Properties properties;

    public GlobalVariablesReader() throws IOException {
        properties = new Properties();
        FileInputStream inputStream = new FileInputStream(GLOBAL_DATA_FILE_LOCATION);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getProperty(String key) {
        String env = properties.getProperty("env");
        String envKey = env + "." + key;
        String value = getValue(envKey);
        return value != null && !value.equalsIgnoreCase("NULL") ? value : "";
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

}
