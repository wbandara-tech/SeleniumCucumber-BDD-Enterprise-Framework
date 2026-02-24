package com.wbandara.enterprise.driver;

import com.wbandara.enterprise.constants.FrameworkConstants;
import com.wbandara.enterprise.exceptions.FrameworkException;
import com.wbandara.enterprise.utils.ConfigReader;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Factory class for creating WebDriver instances.
 * Supports Chrome, Firefox, and Edge browsers.
 * Applies configuration from ConfigReader for headless mode and timeouts.
 */
public final class DriverFactory {

    private DriverFactory() {
        throw new UnsupportedOperationException("DriverFactory is a utility class and cannot be instantiated");
    }

    /**
     * Creates and returns a WebDriver instance based on the specified browser.
     *
     * @param browser the browser name (chrome, firefox, edge)
     * @return configured WebDriver instance
     */
    public static WebDriver createDriver(String browser) {
        WebDriver driver;
        boolean headless = ConfigReader.getInstance().isHeadless();

        LoggerUtils.info(DriverFactory.class, "Creating WebDriver for browser: " + browser + " (headless: " + headless + ")");

        switch (browser.toLowerCase().trim()) {
            case FrameworkConstants.CHROME -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = getChromeOptions(headless);
                driver = new ChromeDriver(options);
            }
            case FrameworkConstants.FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = getFirefoxOptions(headless);
                driver = new FirefoxDriver(options);
            }
            case FrameworkConstants.EDGE -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = getEdgeOptions(headless);
                driver = new EdgeDriver(options);
            }
            default -> throw new FrameworkException("Unsupported browser: " + browser
                    + ". Supported browsers: chrome, firefox, edge");
        }

        configureDriver(driver);
        LoggerUtils.info(DriverFactory.class, "WebDriver created successfully for: " + browser);
        return driver;
    }

    /**
     * Configure Chrome browser options.
     */
    private static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-gpu");
        // Block ads and tracking to prevent ad interstitial redirects
        options.addArguments("--disable-features=InterestCohort");
        options.addArguments("--host-resolver-rules=MAP *.googlesyndication.com 127.0.0.1," +
                "MAP *.googleadservices.com 127.0.0.1," +
                "MAP *.doubleclick.net 127.0.0.1," +
                "MAP *.google-analytics.com 127.0.0.1," +
                "MAP *.adservice.google.com 127.0.0.1," +
                "MAP *.pagead2.googlesyndication.com 127.0.0.1");

        // Set preferences to block ads
        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("profile.managed_default_content_settings.images", 1);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.managed_default_content_settings.popups", 2);
        options.setExperimentalOption("prefs", prefs);

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        return options;
    }

    /**
     * Configure Firefox browser options.
     */
    private static FirefoxOptions getFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }
        return options;
    }

    /**
     * Configure Edge browser options.
     */
    private static EdgeOptions getEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-features=InterestCohort");
        options.addArguments("--host-resolver-rules=MAP *.googlesyndication.com 127.0.0.1," +
                "MAP *.googleadservices.com 127.0.0.1," +
                "MAP *.doubleclick.net 127.0.0.1," +
                "MAP *.google-analytics.com 127.0.0.1," +
                "MAP *.adservice.google.com 127.0.0.1");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        return options;
    }

    /**
     * Apply common driver configurations (timeouts, window management).
     */
    private static void configureDriver(WebDriver driver) {
        ConfigReader config = ConfigReader.getInstance();
        int implicitWait = FrameworkConstants.DEFAULT_IMPLICIT_WAIT;
        int pageLoadTimeout = FrameworkConstants.DEFAULT_PAGE_LOAD_TIMEOUT;

        try {
            implicitWait = config.getInt("implicit.wait");
        } catch (Exception ignored) {
            // Use default
        }
        try {
            pageLoadTimeout = config.getInt("page.load.timeout");
        } catch (Exception ignored) {
            // Use default
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));

        if (!ConfigReader.getInstance().isHeadless()) {
            driver.manage().window().maximize();
        }
    }
}

