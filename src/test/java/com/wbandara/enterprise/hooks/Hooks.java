package com.wbandara.enterprise.hooks;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.utils.ConfigReader;
import com.wbandara.enterprise.utils.LoggerUtils;
import com.wbandara.enterprise.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber Hooks for setup and teardown.
 * Manages WebDriver lifecycle and screenshot capture on failure.
 */
public class Hooks {

    /**
     * Runs before each scenario.
     * Initializes WebDriver and navigates to the base URL.
     */
    @Before
    public void setUp(Scenario scenario) {
        LoggerUtils.info(Hooks.class, "========================================");
        LoggerUtils.info(Hooks.class, "STARTING SCENARIO: " + scenario.getName());
        LoggerUtils.info(Hooks.class, "Tags: " + scenario.getSourceTagNames());
        LoggerUtils.info(Hooks.class, "========================================");

        DriverManager.initDriver();
        WebDriver driver = DriverManager.getDriver();

        String baseUrl = ConfigReader.getInstance().getBaseUrl();
        driver.get(baseUrl);
        LoggerUtils.info(Hooks.class, "Navigated to: " + baseUrl);
    }

    /**
     * Runs after each scenario.
     * Captures screenshot on failure and quits WebDriver.
     */
    @After
    public void tearDown(Scenario scenario) {
        try {
            WebDriver driver = DriverManager.getDriver();

            if (scenario.isFailed()) {
                LoggerUtils.error(Hooks.class, "SCENARIO FAILED: " + scenario.getName());

                if (ConfigReader.getInstance().getBoolean("screenshot.on.failure")) {
                    // Capture screenshot and attach to Allure
                    byte[] screenshot = ScreenshotUtils.captureAndAttachToAllure(driver, scenario.getName());
                    // Also attach to Cucumber report
                    scenario.attach(screenshot, "image/png", "Failure_Screenshot_" + scenario.getName());
                    // Save to disk
                    ScreenshotUtils.captureScreenshot(driver, scenario.getName());
                }
            } else {
                LoggerUtils.info(Hooks.class, "SCENARIO PASSED: " + scenario.getName());
            }
        } catch (Exception e) {
            LoggerUtils.error(Hooks.class, "Error during teardown: " + e.getMessage(), e);
        } finally {
            DriverManager.quitDriver();
            LoggerUtils.info(Hooks.class, "========================================");
            LoggerUtils.info(Hooks.class, "COMPLETED SCENARIO: " + scenario.getName());
            LoggerUtils.info(Hooks.class, "Status: " + scenario.getStatus());
            LoggerUtils.info(Hooks.class, "========================================");
        }
    }
}

