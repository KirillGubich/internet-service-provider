package com.epam.jwd.provider.util;


import com.epam.jwd.provider.exception.PropertyLoadingException;
import com.epam.jwd.provider.model.entity.ConnectionPoolProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReaderUtil {
    private static final Properties properties = new Properties();
    private static final String PROPERTY_FILE_NAME = "src/main/resources/connectionPool.properties";
    private static final String DATABASE_URL_PROPERTY_NAME = "databaseUrl";
    private static final String DATABASE_USER_PROPERTY_NAME = "databaseUser";
    private static final String DATABASE_PASSWORD_PROPERTY_NAME = "databasePassword";
    private static final String DEFAULT_POOL_SIZE_PROPERTY_NAME = "defaultPoolSize";
    private static final String MAX_POOL_SIZE_PROPERTY_NAME = "maxPoolSize";
    private static final String PERCENTAGE_FOR_EXPANSION_PROPERTY_NAME = "percentageForExpansion";
    private static final String EXPANSION_STEP_PROPERTY_NAME = "expansionStep";

    private PropertyReaderUtil() {
    }

    private static void loadProperties() throws PropertyLoadingException {
        try (FileInputStream fileInput = new FileInputStream(PROPERTY_FILE_NAME)) {
            properties.load(fileInput);
        } catch (FileNotFoundException e) {
            throw new PropertyLoadingException("Property file can't be found");
        } catch (IOException e) {
            throw new PropertyLoadingException("Property file reading error");
        }
    }

    public static ConnectionPoolProperties retrieveProperties() throws PropertyLoadingException {
        loadProperties();
        return new ConnectionPoolProperties(
                properties.getProperty(DATABASE_URL_PROPERTY_NAME),
                properties.getProperty(DATABASE_USER_PROPERTY_NAME),
                properties.getProperty(DATABASE_PASSWORD_PROPERTY_NAME),
                Integer.valueOf(properties.getProperty(DEFAULT_POOL_SIZE_PROPERTY_NAME)),
                Integer.valueOf(properties.getProperty(MAX_POOL_SIZE_PROPERTY_NAME)),
                Integer.valueOf(properties.getProperty(PERCENTAGE_FOR_EXPANSION_PROPERTY_NAME)),
                Integer.valueOf(properties.getProperty(EXPANSION_STEP_PROPERTY_NAME))
        );
    }
}
