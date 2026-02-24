package com.wbandara.enterprise.pages;

import com.wbandara.enterprise.base.BasePage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for the Cart Page.
 */
public class CartPage extends BasePage {

    // ======================== Locators ========================
    private static final By CART_TABLE = By.id("cart_info_table");
    private static final By CART_ITEMS = By.cssSelector("#cart_info_table tbody tr");
    private static final By CART_PRODUCT_NAMES = By.cssSelector("#cart_info_table tbody tr td.cart_description h4 a");
    private static final By CART_PRODUCT_PRICES = By.cssSelector("#cart_info_table tbody tr td.cart_price p");
    private static final By CART_PRODUCT_QUANTITIES = By.cssSelector("#cart_info_table tbody tr td.cart_quantity button");
    private static final By REMOVE_BUTTONS = By.cssSelector(".cart_quantity_delete a");
    private static final By PROCEED_TO_CHECKOUT_BTN = By.cssSelector("a.btn.btn-default.check_out");
    private static final By EMPTY_CART_MSG = By.id("empty_cart");
    private static final By REGISTER_LOGIN_LINK = By.cssSelector("#checkoutModal a[href='/login']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ======================== Actions ========================

    @Step("Verify cart page is loaded")
    public boolean isCartPageLoaded() {
        LoggerUtils.info(CartPage.class, "Verifying cart page is loaded");
        sleep(2000);
        // Remove ad overlays on cart page too
        removeAdOverlays();
        // Check if we're on the cart page or got redirected by an ad
        String url = driver.getCurrentUrl();
        if (!url.contains("view_cart")) {
            LoggerUtils.warn(CartPage.class, "Not on cart page, URL: " + url + ". Navigating directly.");
            driver.get("https://automationexercise.com/view_cart");
            sleep(2000);
            removeAdOverlays();
        }
        return driver.getCurrentUrl().contains("view_cart");
    }

    @Step("Get number of items in cart")
    public int getCartItemCount() {
        try {
            sleep(2000);
            // Use explicit wait for cart items
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> items = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(CART_ITEMS));
            LoggerUtils.info(CartPage.class, "Cart item count: " + items.size());
            return items.size();
        } catch (Exception e) {
            LoggerUtils.debug(CartPage.class, "No cart items found: " + e.getMessage());
            return 0;
        }
    }

    @Step("Get product names in cart")
    public List<String> getCartProductNames() {
        try {
            sleep(1000);
            List<WebElement> names = driver.findElements(CART_PRODUCT_NAMES);
            return names.stream()
                    .map(WebElement::getText)
                    .map(String::trim)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    @Step("Get product prices in cart")
    public List<String> getCartProductPrices() {
        try {
            List<WebElement> prices = driver.findElements(CART_PRODUCT_PRICES);
            return prices.stream()
                    .map(WebElement::getText)
                    .map(String::trim)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    @Step("Verify product '{productName}' is in cart")
    public boolean isProductInCart(String productName) {
        List<String> products = getCartProductNames();
        return products.stream()
                .anyMatch(name -> name.equalsIgnoreCase(productName));
    }

    @Step("Remove product at index {index} from cart")
    public void removeProductByIndex(int index) {
        LoggerUtils.info(CartPage.class, "Removing product at index: " + index);
        List<WebElement> removeButtons = driver.findElements(REMOVE_BUTTONS);
        if (index < removeButtons.size()) {
            removeButtons.get(index).click();
            sleep(3000); // Wait for AJAX delete and animation
            // Navigate fresh to cart to get updated DOM
            driver.get("https://automationexercise.com/view_cart");
            sleep(2000);
            removeAdOverlays();
            LoggerUtils.info(CartPage.class, "Product removed at index: " + index);
        }
    }

    @Step("Remove product '{productName}' from cart")
    public void removeProductByName(String productName) {
        LoggerUtils.info(CartPage.class, "Removing product: " + productName);
        List<WebElement> items = driver.findElements(CART_ITEMS);
        for (WebElement item : items) {
            String name = item.findElement(By.cssSelector("td.cart_description h4 a")).getText().trim();
            if (name.equalsIgnoreCase(productName)) {
                item.findElement(By.cssSelector(".cart_quantity_delete a")).click();
                sleep(3000);
                driver.get("https://automationexercise.com/view_cart");
                sleep(2000);
                removeAdOverlays();
                LoggerUtils.info(CartPage.class, "Product removed: " + productName);
                return;
            }
        }
        LoggerUtils.warn(CartPage.class, "Product not found in cart: " + productName);
    }

    @Step("Click Proceed to Checkout")
    public void proceedToCheckout() {
        LoggerUtils.info(CartPage.class, "Clicking Proceed to Checkout");
        click(PROCEED_TO_CHECKOUT_BTN);
    }

    @Step("Click Register/Login link in checkout modal")
    public void clickRegisterLogin() {
        click(REGISTER_LOGIN_LINK);
    }

    @Step("Verify cart is empty")
    public boolean isCartEmpty() {
        sleep(1000);
        // Ensure we're actually on the cart page
        if (!driver.getCurrentUrl().contains("view_cart")) {
            driver.get("https://automationexercise.com/view_cart");
            sleep(2000);
            removeAdOverlays();
        }
        // Check if empty_cart element is displayed (shows when all items removed)
        try {
            WebElement emptyCart = driver.findElement(EMPTY_CART_MSG);
            if (emptyCart.isDisplayed()) {
                LoggerUtils.info(CartPage.class, "isCartEmpty: empty_cart element is displayed");
                return true;
            }
        } catch (Exception ignored) {}
        // Also check page source for "Cart is empty" text
        String pageSource = driver.getPageSource();
        if (pageSource.contains("Cart is empty!")) {
            LoggerUtils.info(CartPage.class, "isCartEmpty: 'Cart is empty!' found in page source");
            return true;
        }
        // Use short implicit wait so we don't wait 10s for elements that should be absent
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        List<WebElement> items = driver.findElements(CART_ITEMS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        LoggerUtils.info(CartPage.class, "isCartEmpty check - item count: " + items.size());
        return items.isEmpty();
    }

    @Step("Get product quantity in cart for item at index {index}")
    public String getProductQuantity(int index) {
        List<WebElement> quantities = driver.findElements(CART_PRODUCT_QUANTITIES);
        if (index < quantities.size()) {
            return quantities.get(index).getText().trim();
        }
        return "0";
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
