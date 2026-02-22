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
    private static final By ADD_TO_CART_BUTTONS = By.cssSelector(".productinfo .add-to-cart");
    private static final By CONTINUE_SHOPPING_BTN = By.cssSelector("button.btn-success");
    private static final By VIEW_CART_LINK = By.cssSelector("div.modal-body a[href='/view_cart']");
    private static final By SEARCHED_PRODUCTS_HEADER = By.cssSelector("h2.title.text-center");

    // Product Detail Page Locators
    private static final By PRODUCT_DETAIL_NAME = By.cssSelector("div.product-information h2");
    private static final By PRODUCT_DETAIL_CATEGORY = By.cssSelector("div.product-information p:nth-of-type(1)");
    private static final By PRODUCT_DETAIL_PRICE = By.cssSelector("div.product-information span span");
    private static final By PRODUCT_DETAIL_AVAILABILITY = By.cssSelector("div.product-information p:nth-of-type(2)");
    private static final By PRODUCT_DETAIL_CONDITION = By.cssSelector("div.product-information p:nth-of-type(3)");
    private static final By PRODUCT_DETAIL_BRAND = By.cssSelector("div.product-information p:nth-of-type(4)");
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
        List<WebElement> viewLinks = findElements(VIEW_PRODUCT_LINKS);
        if (index < viewLinks.size()) {
            scrollToElement(By.cssSelector(".features_items .col-sm-4:nth-child(" + (index + 1) + ")"));
            viewLinks.get(index).click();
        }
    }

    @Step("Add product at index {index} to cart")
    public void addProductToCart(int index) {
        LoggerUtils.info(ProductsPage.class, "Adding product at index " + index + " to cart");
        List<WebElement> products = findElements(PRODUCT_LIST);
        if (index < products.size()) {
            WebElement product = products.get(index);
            hoverOver(By.cssSelector(".features_items .col-sm-4:nth-child(" + (index + 1) + ")"));
            WebElement addToCartBtn = product.findElement(By.cssSelector(".productinfo .add-to-cart"));
            addToCartBtn.click();
        }
    }

    @Step("Click Continue Shopping")
    public void clickContinueShopping() {
        click(CONTINUE_SHOPPING_BTN);
    }

    @Step("Click View Cart in modal")
    public void clickViewCart() {
        click(VIEW_CART_LINK);
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
        return isDisplayed(PRODUCT_DETAIL_NAME)
                && isDisplayed(PRODUCT_DETAIL_PRICE);
    }
}

