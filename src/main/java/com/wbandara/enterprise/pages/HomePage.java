package com.wbandara.enterprise.pages;

import com.wbandara.enterprise.base.BasePage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Page Object for the Home Page (https://automationexercise.com).
 */
public class HomePage extends BasePage {

    // ======================== Locators ========================
    private static final By LOGO = By.cssSelector("img[alt='Website for automation practice']");
    private static final By SLIDER = By.id("slider");
    private static final By NAV_HOME = By.cssSelector("a[href='/']");
    private static final By NAV_PRODUCTS = By.cssSelector("a[href='/products']");
    private static final By NAV_CART = By.cssSelector("a[href='/view_cart']");
    private static final By NAV_SIGNUP_LOGIN = By.cssSelector("a[href='/login']");
    private static final By NAV_LOGOUT = By.cssSelector("a[href='/logout']");
    private static final By NAV_DELETE_ACCOUNT = By.cssSelector("a[href='/delete_account']");
    private static final By NAV_CONTACT_US = By.cssSelector("a[href='/contact_us']");
    private static final By NAV_TEST_CASES = By.cssSelector("a[href='/test_cases']");
    private static final By LOGGED_IN_AS = By.cssSelector("a:has(i.fa-user)");
    private static final By FEATURES_ITEMS = By.cssSelector(".features_items");
    private static final By PRODUCT_CARDS = By.cssSelector(".features_items .col-sm-4");
    private static final By CONTINUE_SHOPPING_BTN = By.cssSelector("button.btn-success");
    private static final By VIEW_CART_LINK = By.cssSelector("div.modal-body a[href='/view_cart']");
    private static final By CATEGORY_SIDEBAR = By.id("accordian");
    private static final By FOOTER = By.id("footer");
    private static final By SUBSCRIPTION_EMAIL = By.id("susbscribe_email");
    private static final By SUBSCRIPTION_BTN = By.id("subscribe");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ======================== Actions ========================

    @Step("Verify home page is loaded")
    public boolean isHomePageLoaded() {
        LoggerUtils.info(HomePage.class, "Verifying home page is loaded");
        return isDisplayed(LOGO) && isDisplayed(SLIDER);
    }

    @Step("Navigate to Login/Signup page")
    public void navigateToLoginPage() {
        LoggerUtils.info(HomePage.class, "Clicking Signup/Login link");
        click(NAV_SIGNUP_LOGIN);
    }

    @Step("Navigate to Products page")
    public void navigateToProducts() {
        LoggerUtils.info(HomePage.class, "Clicking Products link");
        click(NAV_PRODUCTS);
    }

    @Step("Navigate to Cart page")
    public void navigateToCart() {
        LoggerUtils.info(HomePage.class, "Clicking Cart link");
        click(NAV_CART);
    }

    @Step("Navigate to Contact Us page")
    public void navigateToContactUs() {
        LoggerUtils.info(HomePage.class, "Clicking Contact Us link");
        click(NAV_CONTACT_US);
    }

    @Step("Click Logout")
    public void clickLogout() {
        LoggerUtils.info(HomePage.class, "Clicking Logout");
        click(NAV_LOGOUT);
    }

    @Step("Click Delete Account")
    public void clickDeleteAccount() {
        LoggerUtils.info(HomePage.class, "Clicking Delete Account");
        click(NAV_DELETE_ACCOUNT);
    }

    @Step("Verify user is logged in")
    public boolean isUserLoggedIn() {
        return isDisplayed(LOGGED_IN_AS);
    }

    @Step("Get logged in username")
    public String getLoggedInUsername() {
        String text = getText(LOGGED_IN_AS);
        return text.replace("Logged in as", "").trim();
    }

    @Step("Verify Logout link is visible")
    public boolean isLogoutLinkVisible() {
        return isDisplayed(NAV_LOGOUT);
    }

    @Step("Verify Signup/Login link is visible")
    public boolean isSignupLoginLinkVisible() {
        return isDisplayed(NAV_SIGNUP_LOGIN);
    }

    @Step("Add product {index} to cart from home page")
    public void addProductToCart(int index) {
        LoggerUtils.info(HomePage.class, "Adding product " + index + " to cart");
        // Remove any ad overlays first
        removeAdOverlays();

        List<WebElement> products = driver.findElements(By.cssSelector(".features_items .col-sm-4"));
        if (index < products.size()) {
            WebElement product = products.get(index);
            // Scroll to product center
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", product);
            sleep(500);

            // Try hover + overlay click approach
            try {
                new Actions(driver).moveToElement(product).perform();
                sleep(1000);
                List<WebElement> overlayBtns = product.findElements(
                        By.cssSelector(".product-overlay .overlay-content .add-to-cart"));
                if (!overlayBtns.isEmpty() && overlayBtns.get(0).isDisplayed()) {
                    overlayBtns.get(0).click();
                    LoggerUtils.info(HomePage.class, "Clicked overlay add-to-cart button");
                    sleep(1000);
                    return;
                }
            } catch (Exception e) {
                LoggerUtils.debug(HomePage.class, "Overlay approach failed: " + e.getMessage());
            }

            // Fallback: JS click on the productinfo add-to-cart link
            try {
                WebElement addBtn = product.findElement(By.cssSelector(".productinfo .add-to-cart"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                LoggerUtils.info(HomePage.class, "Used JS fallback for add-to-cart");
                sleep(1000);
            } catch (Exception e) {
                LoggerUtils.error(HomePage.class, "Failed to add product to cart: " + e.getMessage());
            }
        }
    }

    @Step("Click Continue Shopping after adding to cart")
    public void clickContinueShopping() {
        sleep(500);
        try {
            click(CONTINUE_SHOPPING_BTN);
        } catch (Exception e) {
            LoggerUtils.debug(HomePage.class, "Continue Shopping button not found, modal may have closed");
        }
        sleep(500);
    }

    @Step("Click View Cart in modal")
    public void clickViewCartInModal() {
        sleep(500);
        try {
            click(VIEW_CART_LINK);
        } catch (Exception e) {
            // Fallback: navigate directly to cart
            LoggerUtils.warn(HomePage.class, "View Cart modal link not found, navigating directly to cart");
            driver.get("https://automationexercise.com/view_cart");
        }
    }

    @Step("Verify featured items section is visible")
    public boolean isFeaturedItemsSectionVisible() {
        return isDisplayed(FEATURES_ITEMS);
    }

    @Step("Verify category sidebar is visible")
    public boolean isCategorySidebarVisible() {
        return isDisplayed(CATEGORY_SIDEBAR);
    }

    @Step("Verify footer is visible")
    public boolean isFooterVisible() {
        scrollToBottom();
        return isDisplayed(FOOTER);
    }

    @Step("Verify navigation bar elements")
    public boolean isNavbarComplete() {
        return isDisplayed(NAV_HOME)
                && isDisplayed(NAV_PRODUCTS)
                && isDisplayed(NAV_CART);
    }

    @Step("Subscribe with email: {email}")
    public void subscribeWithEmail(String email) {
        scrollToBottom();
        type(SUBSCRIPTION_EMAIL, email);
        click(SUBSCRIPTION_BTN);
    }

    @Step("Get number of featured products")
    public int getFeaturedProductCount() {
        return findElements(PRODUCT_CARDS).size();
    }

    // ======================== Private Helpers ========================

    private void removeAdOverlays() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('iframe[id^=\"google_ads\"], iframe[id^=\"aswift\"], " +
                    "ins.adsbygoogle, .google-auto-placed, div[aria-label=\"Advertisement\"]')" +
                    ".forEach(function(el) { el.remove(); });"
            );
        } catch (Exception ignored) {}
    }

    private void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) {}
    }
}
