package com.wbandara.enterprise.constants;

/**
 * Centralized framework constants.
 * All configuration paths, timeouts, and directory locations are managed here.
 */
public final class FrameworkConstants {

    private FrameworkConstants() {
        throw new UnsupportedOperationException("FrameworkConstants is a utility class and cannot be instantiated");
    }

    // Configuration
    public static final String CONFIG_DIR = "src/test/resources/config/";
    public static final String DEFAULT_ENV = "qa";
    public static final String CONFIG_FILE_EXTENSION = ".properties";

    // Timeouts (seconds)
    public static final int DEFAULT_IMPLICIT_WAIT = 10;
    public static final int DEFAULT_EXPLICIT_WAIT = 15;
    public static final int DEFAULT_PAGE_LOAD_TIMEOUT = 30;
    public static final int POLLING_INTERVAL_MS = 500;

    // Directories
    public static final String SCREENSHOT_DIR = "screenshots/";
    public static final String TEST_DATA_DIR = "src/test/resources/testdata/";
    public static final String FEATURES_DIR = "src/test/resources/features";
    public static final String LOG_DIR = "logs/";

    // Test Data Files
    public static final String USERS_CSV = TEST_DATA_DIR + "users.csv";
    public static final String PRODUCTS_JSON = TEST_DATA_DIR + "products.json";

    // Browser Constants
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String EDGE = "edge";

    // Date/Time Format
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    public static final String SCREENSHOT_FORMAT = "png";
}

