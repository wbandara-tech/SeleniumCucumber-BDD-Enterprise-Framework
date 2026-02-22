package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.pages.ProductsPage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

/**
 * Step definitions for Product scenarios.
 */
public class ProductSteps {

    private final ProductsPage productsPage;

    public ProductSteps() {
        this.productsPage = new ProductsPage(DriverManager.getDriver());
    }

    @Then("the products page should be displayed")
    public void theProductsPageShouldBeDisplayed() {
        Assert.assertTrue(productsPage.isProductsPageLoaded(), "Products page is not loaded");
    }

    @When("user searches for product {string}")
    public void userSearchesForProduct(String productName) {
        LoggerUtils.info(ProductSteps.class, "Searching for product: " + productName);
        productsPage.searchProduct(productName);
    }

    @Then("search results should be displayed")
    public void searchResultsShouldBeDisplayed() {
        Assert.assertTrue(productsPage.isSearchedProductsVisible(), "Search results not displayed");
    }

    @Then("search results should contain {string}")
    public void searchResultsShouldContain(String productName) {
        List<String> products = productsPage.getProductNames();
        boolean found = products.stream()
                .anyMatch(name -> name.toLowerCase().contains(productName.toLowerCase()));
        Assert.assertTrue(found, "Product '" + productName + "' not found in search results. Found: " + products);
    }

    @Then("products list should not be empty")
    public void productsListShouldNotBeEmpty() {
        int count = productsPage.getProductCount();
        Assert.assertTrue(count > 0, "Products list is empty");
    }

    @When("user clicks View Product for product at index {int}")
    public void userClicksViewProductForProductAtIndex(int index) {
        productsPage.viewProductDetails(index);
    }

    @Then("product detail page should be displayed")
    public void productDetailPageShouldBeDisplayed() {
        Assert.assertTrue(productsPage.isProductDetailPageLoaded(), "Product detail page not loaded");
    }

    @Then("product name should be visible")
    public void productNameShouldBeVisible() {
        String name = productsPage.getProductDetailName();
        Assert.assertNotNull(name, "Product name is null");
        Assert.assertFalse(name.isEmpty(), "Product name is empty");
    }

    @Then("product price should be visible")
    public void productPriceShouldBeVisible() {
        String price = productsPage.getProductDetailPrice();
        Assert.assertNotNull(price, "Product price is null");
        Assert.assertFalse(price.isEmpty(), "Product price is empty");
    }

    @When("user adds product at index {int} to cart from products page")
    public void userAddsProductAtIndexToCartFromProductsPage(int index) {
        productsPage.addProductToCart(index);
    }

    @When("user clicks Continue Shopping on products page")
    public void userClicksContinueShoppingOnProductsPage() {
        productsPage.clickContinueShopping();
    }

    @When("user clicks View Cart on products page")
    public void userClicksViewCartOnProductsPage() {
        productsPage.clickViewCart();
    }

    @And("user sets product quantity to {string}")
    public void userSetsProductQuantityTo(String quantity) {
        productsPage.setProductQuantity(quantity);
    }

    @And("user adds product to cart from detail page")
    public void userAddsProductToCartFromDetailPage() {
        productsPage.addToCartFromDetailPage();
    }
}

