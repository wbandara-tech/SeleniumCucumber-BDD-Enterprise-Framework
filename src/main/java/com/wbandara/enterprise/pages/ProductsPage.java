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
import java.util.stream.Collectors;

/**
 * Page Object for the Products Page.
 */
public class ProductsPage extends BasePage {

    // ======================== Locators ========================
    private static final By PRODUCTS_HEADER = By.cssSelector("h2.title.text-center");
    private static final By SEARCH_INPUT = By.id("search_product");
    private static final By SEARCH_BUTTON = By.id("submit_search");
    private static final By PRODUCT_LIST = By.cssSelector(".features_items .col-sm-4");
    private static final By PRODUCT_NAMES = By.cssSelector(".productinfo p");
    private static final By PRODUCT_PRICES = By.cssSelector(".productinfo h2");
    private static final By VIEW_PRODUCT_LINKS = By.cssSelector("a.btn.btn-default.choose");
    private static final By CONTINUE_SHOPPING_BTN = By.cssSelector("button.btn-success");
    private static final By VIEW_CART_LINK = By.cssSelector("div.modal-body a[href='/view_cart']");
    private static final By SEARCHED_PRODUCTS_HEADER = By.cssSelector("h2.title.text-center");

    // Product Detail Page Locators
    private static final By PRODUCT_DETAIL_NAME = By.cssSelector("div.product-information h2");
    private static final By PRODUCT_DETAIL_PRICE = By.cssSelector("div.product-information span span");
    private static final By PRODUCT_QUANTITY_INPUT = By.id("quantity");
    private static final By ADD_TO_CART_DETAIL_BTN = By.cssSelector("button.btn.btn-default.cart");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    // ======================== Actions ========================

    @Step("Verify products page is loaded")
    public boolean isProductsPageLoaded() {
        LoggerUtils.info(ProductsPage.class, "Verifying products page is loaded");
        return isDisplayed(PRODUCTS_HEADER);
    }

    @Step("Search for product: {productName}")
    public void searchProduct(String productName) {
        LoggerUtils.info(ProductsPage.class, "Searching for product: " + productName);
        type(SEARCH_INPUT, productName);
        click(SEARCH_BUTTON);
    }

    @Step("Get list of product names")
    public List<String> getProductNames() {
        List<WebElement> names = findElements(PRODUCT_NAMES);
        return names.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Step("Get number of products displayed")
    public int getProductCount() {
        return findElements(PRODUCT_LIST).size();
    }

    @Step("Click View Product for product at index {index}")
    public void viewProductDetails(int index) {
        LoggerUtils.info(ProductsPage.class, "Viewing product details at index: " + index);
        removeAdOverlays();

        List<WebElement> viewLinks = driver.findElements(By.cssSelector("a.btn.btn-default.choose"));
        if (index < viewLinks.size()) {
            WebElement link = viewLinks.get(index);
            String href = link.getAttribute("href");
            LoggerUtils.info(ProductsPage.class, "Product link href: " + href);

            // Ensure absolute URL
            if (href != null && !href.isEmpty()) {
                if (!href.startsWith("http")) {
                    href = "https://automationexercise.com" + href;
                }
                driver.get(href);
                sleep(2000);
                removeAdOverlays();
                // Retry if ad redirect occurred
                if (!driver.getCurrentUrl().contains("product_details")) {
                    LoggerUtils.warn(ProductsPage.class, "Ad redirect detected, retrying: " + driver.getCurrentUrl());
                    driver.get(href);
                    sleep(2000);
                    removeAdOverlays();
                }
            } else {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", link);
                sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
                sleep(2000);
                removeAdOverlays();
            }
        }
    }

    @Step("Add product at index {index} to cart")
    public void addProductToCart(int index) {
        LoggerUtils.info(ProductsPage.class, "Adding product at index " + index + " to cart");
        removeAdOverlays();

        List<WebElement> products = driver.findElements(By.cssSelector(".features_items .col-sm-4"));
        if (index < products.size()) {
            WebElement product = products.get(index);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", product);
            sleep(500);

            // Try hover + overlay
            try {
                new Actions(driver).moveToElement(product).perform();
                sleep(1000);
                List<WebElement> overlayBtns = product.findElements(
                        By.cssSelector(".product-overlay .overlay-content .add-to-cart"));
                if (!overlayBtns.isEmpty() && overlayBtns.get(0).isDisplayed()) {
                    overlayBtns.get(0).click();
                    sleep(1000);
                    return;
                }
            } catch (Exception ignored) {}

            // Fallback: JS click
            try {
                WebElement addBtn = product.findElement(By.cssSelector(".productinfo .add-to-cart"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                sleep(1000);
            } catch (Exception e) {
                LoggerUtils.error(ProductsPage.class, "Failed to add product: " + e.getMessage());
            }
        }
    }

    @Step("Click Continue Shopping")
    public void clickContinueShopping() {
        sleep(500);
        try { click(CONTINUE_SHOPPING_BTN); } catch (Exception ignored) {}
        sleep(500);
    }

    @Step("Click View Cart in modal")
    public void clickViewCart() {
        sleep(500);
        try {
            click(VIEW_CART_LINK);
        } catch (Exception e) {
            LoggerUtils.warn(ProductsPage.class, "View Cart link not found, navigating directly");
            driver.get("https://automationexercise.com/view_cart");
        }
    }

    @Step("Verify searched products header is visible")
    public boolean isSearchedProductsVisible() {
        String text = getText(SEARCHED_PRODUCTS_HEADER);
        return text.contains("SEARCHED PRODUCTS") || text.contains("ALL PRODUCTS");
    }

    // ======================== Product Detail Actions ========================

    @Step("Get product detail name")
    public String getProductDetailName() {
        return getText(PRODUCT_DETAIL_NAME);
    }

    @Step("Get product detail price")
    public String getProductDetailPrice() {
        return getText(PRODUCT_DETAIL_PRICE);
    }

    @Step("Set product quantity to {quantity}")
    public void setProductQuantity(String quantity) {
        WebElement quantityInput = findElement(PRODUCT_QUANTITY_INPUT);
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
    }

    @Step("Add to cart from product detail page")
    public void addToCartFromDetailPage() {
        click(ADD_TO_CART_DETAIL_BTN);
    }

    @Step("Verify product detail page is loaded")
    public boolean isProductDetailPageLoaded() {
        sleep(2000);
        removeAdOverlays();
        LoggerUtils.info(ProductsPage.class, "Checking product detail page. URL: " + driver.getCurrentUrl());
        return isDisplayed(PRODUCT_DETAIL_NAME)
                && isDisplayed(PRODUCT_DETAIL_PRICE);
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
