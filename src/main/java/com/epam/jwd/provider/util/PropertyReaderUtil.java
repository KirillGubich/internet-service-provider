package com.epam.jwd.provider.util;


import com.epam.jwd.provider.domain.ConnectionPoolProperties;
import com.epam.jwd.provider.exception.PropertyLoadingException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReaderUtil {
    private static final Properties properties = new Properties();
    private static final String PROPERTY_FILE_NAME = "src/main/resources/connectionPool.properties";

    private PropertyReaderUtil() {
    }

    private static void loadProperties() {
        try (FileInputStream fileInput = new FileInputStream(PROPERTY_FILE_NAME)) {
            properties.load(fileInput);
        } catch (FileNotFoundException e) {
            throw new PropertyLoadingException("Property file can't be found");
        } catch (IOException e) {
            throw new PropertyLoadingException("Property file reading error");
        }
    }

    public static ConnectionPoolProperties retrieveProperties() {
        loadProperties();
        return new ConnectionPoolProperties(
                properties.getProperty("databaseUrl"),
                properties.getProperty("databaseUser"),
                properties.getProperty("databasePassword"),
                Integer.valueOf(properties.getProperty("defaultPoolSize")),
                Integer.valueOf(properties.getProperty("maxPoolSize")),
                Integer.valueOf(properties.getProperty("percentageForExpansion")),
                Integer.valueOf(properties.getProperty("expansionStep"))
        );
    }
}
