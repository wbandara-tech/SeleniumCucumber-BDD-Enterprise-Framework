package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Step definitions for Product scenarios.
 */
public class ProductSteps {

    private final WebDriver driver;

    public ProductSteps() {
        this.driver = DriverManager.getDriver();
    }

    @Then("the products page should be displayed")
    public void theProductsPageShouldBeDisplayed() {
        Assertions.assertTrue(isElementVisible(By.xpath("//h1[text()='Products']")), "Products page is not loaded");
    }

    @When("user searches for product {string}")
    public void userSearchesForProduct(String productName) {
        LoggerUtils.info(ProductSteps.class, "Searching for product: " + productName);
        WebElement searchBox = driver.findElement(By.id("search-box"));
        searchBox.clear();
        searchBox.sendKeys(productName);
        driver.findElement(By.id("search-button")).click();
    }

    @Then("search results should be displayed")
    public void searchResultsShouldBeDisplayed() {
        Assertions.assertTrue(isElementVisible(By.id("search-results")), "Search results not displayed");
    }

    @Then("search results should contain {string}")
    public void searchResultsShouldContain(String productName) {
        List<WebElement> products = driver.findElements(By.cssSelector(".product-name"));
        boolean found = products.stream()
                .anyMatch(element -> element.getText().toLowerCase().contains(productName.toLowerCase()));
        Assertions.assertTrue(found, "Product '" + productName + "' not found in search results.");
    }

    @Then("products list should not be empty")
    public void productsListShouldNotBeEmpty() {
        List<WebElement> productList = driver.findElements(By.cssSelector(".product-item"));
        Assertions.assertTrue(!productList.isEmpty(), "Products list is empty");
    }

    @When("user clicks View Product for product at index {int}")
    public void userClicksViewProductForProductAtIndex(int index) {
        List<WebElement> products = driver.findElements(By.cssSelector(".product-item"));
        products.get(index).findElement(By.cssSelector(".view-product-button")).click();
    }

    @Then("product detail page should be displayed")
    public void productDetailPageShouldBeDisplayed() {
        Assertions.assertTrue(isElementVisible(By.xpath("//h1[@class='product-detail-name']")), "Product detail page not loaded");
    }

    @Then("product name should be visible")
    public void productNameShouldBeVisible() {
        String name = driver.findElement(By.xpath("//h1[@class='product-detail-name']")).getText();
        Assertions.assertNotNull(name, "Product name is null");
        Assertions.assertFalse(name.isEmpty(), "Product name is empty");
    }

    @Then("product price should be visible")
    public void productPriceShouldBeVisible() {
        String price = driver.findElement(By.xpath("//p[@class='product-detail-price']")).getText();
        Assertions.assertNotNull(price, "Product price is null");
        Assertions.assertFalse(price.isEmpty(), "Product price is empty");
    }

    @When("user adds product at index {int} to cart from products page")
    public void userAddsProductAtIndexToCartFromProductsPage(int index) {
        List<WebElement> products = driver.findElements(By.cssSelector(".product-item"));
        products.get(index).findElement(By.cssSelector(".add-to-cart-button")).click();
    }

    @When("user clicks Continue Shopping on products page")
    public void userClicksContinueShoppingOnProductsPage() {
        driver.findElement(By.id("continue-shopping")).click();
    }

    @When("user clicks View Cart on products page")
    public void userClicksViewCartOnProductsPage() {
        driver.findElement(By.id("view-cart")).click();
    }

    @And("user sets product quantity to {string}")
    public void userSetsProductQuantityTo(String quantity) {
        WebElement quantityBox = driver.findElement(By.cssSelector(".product-quantity"));
        quantityBox.clear();
        quantityBox.sendKeys(quantity);
    }

    @And("user adds product to cart from detail page")
    public void userAddsProductToCartFromDetailPage() {
        driver.findElement(By.id("add-to-cart-detail")).click();
    }

    private boolean isElementVisible(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
