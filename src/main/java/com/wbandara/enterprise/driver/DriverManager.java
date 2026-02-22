package com.wbandara.enterprise.driver;

import com.wbandara.enterprise.exceptions.FrameworkException;
import com.wbandara.enterprise.utils.ConfigReader;
import com.wbandara.enterprise.utils.LoggerUtils;
import org.openqa.selenium.WebDriver;

/**
 * Thread-safe WebDriver manager using ThreadLocal.
 * Ensures each thread gets its own WebDriver instance for parallel execution.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        throw new UnsupportedOperationException("DriverManager is a utility class and cannot be instantiated");
    }

    /**
     * Initialize WebDriver based on configuration.
     * Creates a new driver and stores it in ThreadLocal.
     */
    public static void initDriver() {
        if (driverThreadLocal.get() != null) {
            LoggerUtils.warn(DriverManager.class, "Driver already initialized for current thread. Quitting existing driver.");
            quitDriver();
        }

        String browser = System.getProperty("browser", ConfigReader.getInstance().getBrowser());
        WebDriver driver = DriverFactory.createDriver(browser);
        driverThreadLocal.set(driver);

        LoggerUtils.info(DriverManager.class,
                "WebDriver initialized for thread: " + Thread.currentThread().getName()
                        + " | Browser: " + browser);
    }

    /**
     * Get the WebDriver instance for the current thread.
     *
     * @return WebDriver instance
     * @throws FrameworkException if driver is not initialized
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new FrameworkException("WebDriver is not initialized. Call initDriver() first.");
        }
        return driver;
    }

    /**
     * Set a WebDriver instance for the current thread.
     *
     * @param driver the WebDriver instance to set
     */
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    /**
     * Quit and remove the WebDriver instance for the current thread.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                LoggerUtils.info(DriverManager.class,
                        "WebDriver quit successfully for thread: " + Thread.currentThread().getName());
            } catch (Exception e) {
                LoggerUtils.error(DriverManager.class, "Error quitting WebDriver", e);
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Check if driver is initialized for the current thread.
     */
    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }
}

