package framework.config;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final String pathToFile = "src/main/resources/grid.properties";

    @SneakyThrows
    public static Properties getProperties() {
        Properties properties = new Properties();
        InputStream fileInput = new FileInputStream(pathToFile);
        properties.load(fileInput);
        return properties;
    }
}

