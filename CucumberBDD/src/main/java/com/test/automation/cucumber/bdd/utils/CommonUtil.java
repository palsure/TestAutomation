package com.test.automation.cucumber.bdd.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@Slf4j
public class CommonUtil {

    @Getter
    private static Properties properties;

    public static void loadProperties(String propertiesFile) {
        properties = new Properties();
        try (FileInputStream dataFile = new FileInputStream(propertiesFile)) {
            properties.load(dataFile);
        } catch (IOException e) {
            log.error("Error in loading properties file: %s".formatted(propertiesFile), e);
        }
    }

    public static String getProperty(String propertyName) {
        String value = properties.getProperty(propertyName);
        log.info("%s value is: %s".formatted(propertyName, value));
        return System.getProperty(propertyName, value);
    }


    public static boolean isValidURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static URL getValidURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
