package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.pages.CartPage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

/**
 * Step definitions for Cart scenarios.
 */
public class CartSteps {

    private final CartPage cartPage;

    public CartSteps() {
        this.cartPage = new CartPage(DriverManager.getDriver());
    }

    @Then("the cart page should be displayed")
    public void theCartPageShouldBeDisplayed() {
        Assert.assertTrue(cartPage.isCartPageLoaded(), "Cart page is not loaded");
    }

    @Then("cart should contain {int} item(s)")
    public void cartShouldContainItems(int expectedCount) {
        int actualCount = cartPage.getCartItemCount();
        Assert.assertEquals(actualCount, expectedCount,
                "Cart item count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount);
    }

    @Then("product {string} should be in the cart")
    public void productShouldBeInTheCart(String productName) {
        Assert.assertTrue(cartPage.isProductInCart(productName),
                "Product '" + productName + "' is not in the cart");
    }

    @Then("cart should display product names:")
    public void cartShouldDisplayProductNames(List<String> expectedNames) {
        List<String> actualNames = cartPage.getCartProductNames();
        for (String expectedName : expectedNames) {
            boolean found = actualNames.stream()
                    .anyMatch(name -> name.equalsIgnoreCase(expectedName));
            Assert.assertTrue(found, "Product '" + expectedName + "' not found in cart. Actual: " + actualNames);
        }
    }

    @When("user removes product at index {int} from cart")
    public void userRemovesProductAtIndexFromCart(int index) {
        LoggerUtils.info(CartSteps.class, "Removing product at index: " + index);
        cartPage.removeProductByIndex(index);
    }

    @When("user removes product {string} from cart")
    public void userRemovesProductFromCart(String productName) {
        LoggerUtils.info(CartSteps.class, "Removing product: " + productName);
        cartPage.removeProductByName(productName);
    }

    @Then("product {string} should not be in the cart")
    public void productShouldNotBeInTheCart(String productName) {
        // Small wait for DOM update after removal
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        Assert.assertFalse(cartPage.isProductInCart(productName),
                "Product '" + productName + "' is still in the cart");
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty");
    }

    @When("user clicks Proceed to Checkout")
    public void userClicksProceedToCheckout() {
        cartPage.proceedToCheckout();
    }

    @When("user clicks Register\\/Login in checkout modal")
    public void userClicksRegisterLoginInCheckoutModal() {
        cartPage.clickRegisterLogin();
    }

    @Then("cart product prices should be displayed")
    public void cartProductPricesShouldBeDisplayed() {
        List<String> prices = cartPage.getCartProductPrices();
        Assert.assertFalse(prices.isEmpty(), "No prices displayed in cart");
        for (String price : prices) {
            Assert.assertFalse(price.isEmpty(), "Empty price found in cart");
        }
    }

    @And("cart should not be empty")
    public void cartShouldNotBeEmpty() {
        Assert.assertFalse(cartPage.isCartEmpty(), "Cart is empty");
    }
}

