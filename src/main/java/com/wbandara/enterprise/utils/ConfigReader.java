package com.wbandara.enterprise.utils;

import com.wbandara.enterprise.constants.FrameworkConstants;
import com.wbandara.enterprise.exceptions.FrameworkException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Centralized configuration reader.
 * Loads environment-specific properties files based on system property 'env'.
 * Thread-safe singleton implementation.
 */
public final class ConfigReader {

    private static ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();
        String env = System.getProperty("env", FrameworkConstants.DEFAULT_ENV);
        String configPath = FrameworkConstants.CONFIG_DIR + env + FrameworkConstants.CONFIG_FILE_EXTENSION;

        try (FileInputStream fis = new FileInputStream(configPath)) {
            properties.load(fis);
            LoggerUtils.info(ConfigReader.class, "Loaded configuration from: " + configPath);
        } catch (IOException e) {
            LoggerUtils.error(ConfigReader.class, "Failed to load config file: " + configPath, e);
            throw new FrameworkException("Unable to load configuration file: " + configPath, e);
        }
    }

    /**
     * Returns the singleton instance of ConfigReader.
     * Double-checked locking for thread safety.
     */
    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    /**
     * Get a property value by key.
     *
     * @param key the property key
     * @return the property value
     */
    public String get(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        String value = properties.getProperty(key);
        if (value == null) {
            LoggerUtils.warn(ConfigReader.class, "Property not found: " + key);
        }
        return value;
    }

    /**
     * Get a property value as integer.
     */
    public int getInt(String key) {
        String value = get(key);
        if (value == null) {
            throw new FrameworkException("Integer property not found: " + key);
        }
        return Integer.parseInt(value.trim());
    }

    /**
     * Get a property value as boolean.
     */
    public boolean getBoolean(String key) {
        String value = get(key);
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value.trim());
    }

    /**
     * Get base URL from config.
     */
    public String getBaseUrl() {
        return get("base.url");
    }

    /**
     * Get browser name from config.
     */
    public String getBrowser() {
        return get("browser");
    }

    /**
     * Check if headless mode is enabled.
     */
    public boolean isHeadless() {
        return getBoolean("headless");
    }

    /**
     * Get explicit wait timeout.
     */
    public int getExplicitWait() {
        String value = get("explicit.wait");
        return value != null ? Integer.parseInt(value.trim()) : FrameworkConstants.DEFAULT_EXPLICIT_WAIT;
    }

    /**
     * Reset instance (useful for testing).
     */
    public static synchronized void reset() {
        instance = null;
    }
}

