package com.wbandara.enterprise.base;

import com.wbandara.enterprise.utils.LoggerUtils;
import com.wbandara.enterprise.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Abstract base class for all Page Objects.
 * Provides reusable methods for common Selenium interactions.
 * All interactions use explicit waits for reliability.
 */
public abstract class BasePage {

    protected final WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // ======================== Core Actions ========================

    @Step("Click on element: {locator}")
    protected void click(By locator) {
        LoggerUtils.debug(BasePage.class, "Clicking element: " + locator);
        WaitUtils.waitForClickable(driver, locator).click();
    }

    @Step("Type '{text}' into element: {locator}")
    protected void type(By locator, String text) {
        LoggerUtils.debug(BasePage.class, "Typing into element: " + locator);
        WebElement element = WaitUtils.waitForVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    @Step("Get text from element: {locator}")
    protected String getText(By locator) {
        LoggerUtils.debug(BasePage.class, "Getting text from element: " + locator);
        return WaitUtils.waitForVisible(driver, locator).getText().trim();
    }

    @Step("Check if element is displayed: {locator}")
    protected boolean isDisplayed(By locator) {
        try {
            return WaitUtils.waitForVisible(driver, locator, 5).isDisplayed();
        } catch (Exception e) {
            LoggerUtils.debug(BasePage.class, "Element not displayed: " + locator);
            return false;
        }
    }

    @Step("Check if element is present in DOM: {locator}")
    protected boolean isPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // ======================== Navigation ========================

    @Step("Navigate to URL: {url}")
    protected void navigateTo(String url) {
        LoggerUtils.info(BasePage.class, "Navigating to: " + url);
        driver.get(url);
    }

    @Step("Navigate back")
    protected void navigateBack() {
        driver.navigate().back();
    }

    @Step("Refresh page")
    protected void refreshPage() {
        driver.navigate().refresh();
    }

    // ======================== Form Interactions ========================

    @Step("Select dropdown option by visible text: {text}")
    protected void selectByVisibleText(By locator, String text) {
        LoggerUtils.debug(BasePage.class, "Selecting dropdown by text: " + text);
        Select select = new Select(WaitUtils.waitForVisible(driver, locator));
        select.selectByVisibleText(text);
    }

    @Step("Select dropdown option by value: {value}")
    protected void selectByValue(By locator, String value) {
        Select select = new Select(WaitUtils.waitForVisible(driver, locator));
        select.selectByValue(value);
    }

    @Step("Select checkbox/radio: {locator}")
    protected void selectCheckbox(By locator) {
        WebElement element = WaitUtils.waitForClickable(driver, locator);
        if (!element.isSelected()) {
            element.click();
        }
    }

    // ======================== Element Retrieval ========================

    protected WebElement findElement(By locator) {
        return WaitUtils.waitForVisible(driver, locator);
    }

    protected List<WebElement> findElements(By locator) {
        return WaitUtils.waitForAllVisible(driver, locator);
    }

    // ======================== JavaScript & Scrolling ========================

    @Step("Scroll to element: {locator}")
    protected void scrollToElement(By locator) {
        WebElement element = WaitUtils.waitForPresence(driver, locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    protected void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @Step("Click element using JavaScript: {locator}")
    protected void jsClick(By locator) {
        WebElement element = WaitUtils.waitForPresence(driver, locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    // ======================== Hover & Actions ========================

    @Step("Hover over element: {locator}")
    protected void hoverOver(By locator) {
        WebElement element = WaitUtils.waitForVisible(driver, locator);
        new Actions(driver).moveToElement(element).perform();
    }

    // ======================== Waits ========================

    protected void waitForPageLoad() {
        WaitUtils.waitForTitleContains(driver, "");
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ======================== Attribute & Value ========================

    protected String getAttribute(By locator, String attribute) {
        return WaitUtils.waitForPresence(driver, locator).getAttribute(attribute);
    }

    protected String getCssValue(By locator, String property) {
        return WaitUtils.waitForPresence(driver, locator).getCssValue(property);
    }

    // ======================== Alerts ========================

    protected void acceptAlert() {
        WaitUtils.waitForAlert(driver).accept();
    }

    protected void dismissAlert() {
        WaitUtils.waitForAlert(driver).dismiss();
    }

    protected String getAlertText() {
        return WaitUtils.waitForAlert(driver).getText();
    }

    // ======================== Frames ========================

    protected void switchToFrame(By locator) {
        driver.switchTo().frame(WaitUtils.waitForPresence(driver, locator));
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
}

