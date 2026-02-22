package com.wbandara.enterprise.pages;

import com.wbandara.enterprise.base.BasePage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
    private static final By CART_PRODUCT_TOTALS = By.cssSelector("#cart_info_table tbody tr td.cart_total p");
    private static final By REMOVE_BUTTONS = By.cssSelector(".cart_quantity_delete a");
    private static final By PROCEED_TO_CHECKOUT_BTN = By.cssSelector("a.btn.btn-default.check_out");
    private static final By EMPTY_CART_MSG = By.cssSelector("#empty_cart");
    private static final By REGISTER_LOGIN_LINK = By.cssSelector("#checkoutModal a[href='/login']");
    private static final By SHOPPING_CART_HEADER = By.cssSelector("li.active");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ======================== Actions ========================

    @Step("Verify cart page is loaded")
    public boolean isCartPageLoaded() {
        LoggerUtils.info(CartPage.class, "Verifying cart page is loaded");
        return isDisplayed(CART_TABLE) || isDisplayed(EMPTY_CART_MSG);
    }

    @Step("Get number of items in cart")
    public int getCartItemCount() {
        try {
            List<WebElement> items = findElements(CART_ITEMS);
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Step("Get product names in cart")
    public List<String> getCartProductNames() {
        List<WebElement> names = findElements(CART_PRODUCT_NAMES);
        return names.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Step("Get product prices in cart")
    public List<String> getCartProductPrices() {
        List<WebElement> prices = findElements(CART_PRODUCT_PRICES);
        return prices.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
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
        List<WebElement> removeButtons = findElements(REMOVE_BUTTONS);
        if (index < removeButtons.size()) {
            removeButtons.get(index).click();
        }
    }

    @Step("Remove product '{productName}' from cart")
    public void removeProductByName(String productName) {
        LoggerUtils.info(CartPage.class, "Removing product: " + productName);
        List<WebElement> items = findElements(CART_ITEMS);
        for (WebElement item : items) {
            String name = item.findElement(By.cssSelector("td.cart_description h4 a")).getText().trim();
            if (name.equalsIgnoreCase(productName)) {
                item.findElement(By.cssSelector(".cart_quantity_delete a")).click();
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
        return isDisplayed(EMPTY_CART_MSG) || getCartItemCount() == 0;
    }

    @Step("Get product quantity in cart for item at index {index}")
    public String getProductQuantity(int index) {
        List<WebElement> quantities = findElements(CART_PRODUCT_QUANTITIES);
        if (index < quantities.size()) {
            return quantities.get(index).getText().trim();
        }
        return "0";
    }
}

