package com.wbandara.enterprise.hooks;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.utils.ConfigReader;
import com.wbandara.enterprise.utils.LoggerUtils;
import com.wbandara.enterprise.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

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

        // Handle Google Ads consent popup and ad overlays
        dismissAdsAndConsent(driver);
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
                    byte[] screenshot = ScreenshotUtils.captureAndAttachToAllure(driver, scenario.getName());
                    scenario.attach(screenshot, "image/png", "Failure_Screenshot_" + scenario.getName());
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

    /**
     * Dismisses Google Ads consent dialog and removes ad overlays.
     * automationexercise.com loads Google Ads that can block test interactions.
     */
    private void dismissAdsAndConsent(WebDriver driver) {
        try {
            Thread.sleep(1000);

            // Quickly remove any ad overlays via JavaScript
            ((JavascriptExecutor) driver).executeScript(
                    "var ads = document.querySelectorAll(" +
                    "'iframe[id^=\"google_ads\"], iframe[id^=\"aswift\"], " +
                    "div[id^=\"google_ads\"], ins.adsbygoogle, " +
                    "div.ad_box, #ad_position_box, .google-auto-placed, " +
                    "div[aria-label=\"Advertisement\"]');" +
                    "ads.forEach(function(ad) { ad.remove(); });" +
                    "document.querySelectorAll('body > div').forEach(function(el) {" +
                    "  var style = window.getComputedStyle(el);" +
                    "  if (style.position === 'fixed' && parseInt(style.zIndex) > 999) { el.remove(); }" +
                    "});"
            );

            // Try to dismiss Google consent iframe (with short timeout)
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(1));
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (WebElement iframe : iframes) {
                try {
                    String src = iframe.getAttribute("src");
                    if (src != null && src.contains("consent")) {
                        driver.switchTo().frame(iframe);
                        List<WebElement> consentBtns = driver.findElements(
                                By.cssSelector("button.fc-cta-consent, button[aria-label='Consent'], .fc-button-label"));
                        if (!consentBtns.isEmpty()) {
                            consentBtns.get(0).click();
                            LoggerUtils.info(Hooks.class, "Dismissed consent dialog");
                        }
                        driver.switchTo().defaultContent();
                        break;
                    }
                } catch (Exception ignored) {
                    driver.switchTo().defaultContent();
                }
            }
            // Restore implicit wait
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

            LoggerUtils.info(Hooks.class, "Ad cleanup completed");
        } catch (Exception e) {
            LoggerUtils.debug(Hooks.class, "Ad handling: " + e.getMessage());
            try {
                driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
            } catch (Exception ignored) {}
        }
    }
}
