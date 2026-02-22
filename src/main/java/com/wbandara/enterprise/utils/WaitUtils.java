package com.wbandara.enterprise.utils;

import com.wbandara.enterprise.constants.FrameworkConstants;
import com.wbandara.enterprise.exceptions.ElementNotFoundException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Explicit wait utility wrapper.
 * Provides reusable wait methods for common Selenium operations.
 */
public final class WaitUtils {

    private WaitUtils() {
        throw new UnsupportedOperationException("WaitUtils is a utility class and cannot be instantiated");
    }

    private static WebDriverWait getWait(WebDriver driver) {
        int timeout = ConfigReader.getInstance().getExplicitWait();
        return new WebDriverWait(driver, Duration.ofSeconds(timeout),
                Duration.ofMillis(FrameworkConstants.POLLING_INTERVAL_MS));
    }

    private static WebDriverWait getWait(WebDriver driver, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds),
                Duration.ofMillis(FrameworkConstants.POLLING_INTERVAL_MS));
    }

    /**
     * Wait for element to be visible.
     */
    public static WebElement waitForVisible(WebDriver driver, By locator) {
        try {
            LoggerUtils.debug(WaitUtils.class, "Waiting for element to be visible: " + locator);
            return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new ElementNotFoundException("Element not visible after wait: " + locator, e);
        }
    }

    /**
     * Wait for element to be visible with custom timeout.
     */
    public static WebElement waitForVisible(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            return getWait(driver, timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new ElementNotFoundException("Element not visible after " + timeoutSeconds + "s: " + locator, e);
        }
    }

    /**
     * Wait for element to be clickable.
     */
    public static WebElement waitForClickable(WebDriver driver, By locator) {
        try {
            LoggerUtils.debug(WaitUtils.class, "Waiting for element to be clickable: " + locator);
            return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            throw new ElementNotFoundException("Element not clickable after wait: " + locator, e);
        }
    }

    /**
     * Wait for element to be present in DOM.
     */
    public static WebElement waitForPresence(WebDriver driver, By locator) {
        try {
            LoggerUtils.debug(WaitUtils.class, "Waiting for element presence: " + locator);
            return getWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new ElementNotFoundException("Element not present after wait: " + locator, e);
        }
    }

    /**
     * Wait for element to become invisible.
     */
    public static boolean waitForInvisible(WebDriver driver, By locator) {
        try {
            LoggerUtils.debug(WaitUtils.class, "Waiting for element to become invisible: " + locator);
            return getWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            LoggerUtils.warn(WaitUtils.class, "Element still visible after wait: " + locator);
            return false;
        }
    }

    /**
     * Wait for all elements to be visible.
     */
    public static List<WebElement> waitForAllVisible(WebDriver driver, By locator) {
        try {
            return getWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            throw new ElementNotFoundException("Elements not visible after wait: " + locator, e);
        }
    }

    /**
     * Wait for text to be present in element.
     */
    public static boolean waitForTextPresent(WebDriver driver, By locator, String text) {
        try {
            return getWait(driver).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (TimeoutException e) {
            LoggerUtils.warn(WaitUtils.class, "Text '" + text + "' not found in element: " + locator);
            return false;
        }
    }

    /**
     * Wait for URL to contain a specific string.
     */
    public static boolean waitForUrlContains(WebDriver driver, String urlFragment) {
        try {
            return getWait(driver).until(ExpectedConditions.urlContains(urlFragment));
        } catch (TimeoutException e) {
            LoggerUtils.warn(WaitUtils.class, "URL does not contain: " + urlFragment);
            return false;
        }
    }

    /**
     * Wait for page title to contain text.
     */
    public static boolean waitForTitleContains(WebDriver driver, String title) {
        try {
            return getWait(driver).until(ExpectedConditions.titleContains(title));
        } catch (TimeoutException e) {
            LoggerUtils.warn(WaitUtils.class, "Title does not contain: " + title);
            return false;
        }
    }

    /**
     * Wait for an alert to be present.
     */
    public static Alert waitForAlert(WebDriver driver) {
        try {
            return getWait(driver).until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException e) {
            throw new ElementNotFoundException("Alert not present after wait", e);
        }
    }
}

