package com.wbandara.enterprise.utils;

import com.wbandara.enterprise.constants.FrameworkConstants;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing and managing screenshots.
 * Supports timestamped file saves and Allure report attachments.
 */
public final class ScreenshotUtils {

    private ScreenshotUtils() {
        throw new UnsupportedOperationException("ScreenshotUtils is a utility class and cannot be instantiated");
    }

    /**
     * Captures a screenshot and saves to the screenshots directory with a timestamp.
     *
     * @param driver       the WebDriver instance
     * @param scenarioName the name of the scenario
     * @return the file path of the saved screenshot
     */
    public static String captureScreenshot(WebDriver driver, String scenarioName) {
        try {
            createScreenshotDirectory();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(FrameworkConstants.TIMESTAMP_FORMAT));
            String sanitizedName = scenarioName.replaceAll("[^a-zA-Z0-9_-]", "_");
            String fileName = sanitizedName + "_" + timestamp + "." + FrameworkConstants.SCREENSHOT_FORMAT;

            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Path filePath = Paths.get(FrameworkConstants.SCREENSHOT_DIR, fileName);
            Files.write(filePath, screenshotBytes);

            LoggerUtils.info(ScreenshotUtils.class, "Screenshot saved: " + filePath);
            return filePath.toString();
        } catch (IOException e) {
            LoggerUtils.error(ScreenshotUtils.class, "Failed to save screenshot for: " + scenarioName, e);
            return null;
        }
    }

    /**
     * Captures a screenshot and attaches it to the Allure report.
     *
     * @param driver       the WebDriver instance
     * @param scenarioName the name of the scenario
     * @return screenshot as byte array
     */
    public static byte[] captureAndAttachToAllure(WebDriver driver, String scenarioName) {
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(
                "Screenshot - " + scenarioName,
                "image/png",
                new ByteArrayInputStream(screenshotBytes),
                "png"
        );
        LoggerUtils.info(ScreenshotUtils.class, "Screenshot attached to Allure report for: " + scenarioName);
        return screenshotBytes;
    }

    /**
     * Captures screenshot, saves to disk, and attaches to Allure.
     */
    public static String captureScreenshotFull(WebDriver driver, String scenarioName) {
        captureAndAttachToAllure(driver, scenarioName);
        return captureScreenshot(driver, scenarioName);
    }

    /**
     * Creates the screenshot directory if it doesn't exist.
     */
    private static void createScreenshotDirectory() {
        File dir = new File(FrameworkConstants.SCREENSHOT_DIR);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                LoggerUtils.info(ScreenshotUtils.class, "Created screenshot directory: " + FrameworkConstants.SCREENSHOT_DIR);
            }
        }
    }
}

