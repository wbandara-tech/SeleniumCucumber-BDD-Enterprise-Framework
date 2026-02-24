package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.List;

/**
 * Step definitions for Cart scenarios.
 */
public class CartSteps {

    @Then("the cart page should be displayed")
    public void theCartPageShouldBeDisplayed() {
        Assertions.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("cart"),
                "Cart page is not loaded");
    }

    @Then("cart should contain {int} item\\(s)")
    public void cartShouldContainItems(int expectedCount) {
        int actualCount = DriverManager.getDriver().findElements(By.cssSelector(".cart-item")).size();
        Assertions.assertEquals(actualCount, expectedCount,
                "Cart item count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount);
    }

    @Then("product {string} should be in the cart")
    public void productShouldBeInTheCart(String productName) {
        Assertions.assertTrue(DriverManager.getDriver().getPageSource().contains(productName),
                "Product '" + productName + "' is not in the cart");
    }

    @Then("cart should display product names:")
    public void cartShouldDisplayProductNames(List<String> expectedNames) {
        List<String> actualNames = DriverManager.getDriver().findElements(By.cssSelector(".cart-item-name"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
        for (String expectedName : expectedNames) {
            boolean found = actualNames.stream()
                    .anyMatch(name -> name.equalsIgnoreCase(expectedName));
            Assertions.assertTrue(found, "Product '" + expectedName + "' not found in cart. Actual: " + actualNames);
        }
    }

    @When("user removes product at index {int} from cart")
    public void userRemovesProductAtIndexFromCart(int index) {
        LoggerUtils.info(CartSteps.class, "Removing product at index: " + index);
        List<WebElement> removeButtons = DriverManager.getDriver().findElements(By.cssSelector(".remove-from-cart"));
        if (index >= 0 && index < removeButtons.size()) {
            removeButtons.get(index).click();
        } else {
            Assertions.fail("Index out of bounds: " + index);
        }
    }

    @When("user removes product {string} from cart")
    public void userRemovesProductFromCart(String productName) {
        LoggerUtils.info(CartSteps.class, "Removing product: " + productName);
        List<WebElement> cartItems = DriverManager.getDriver().findElements(By.cssSelector(".cart-item"));
        for (WebElement item : cartItems) {
            if (item.getText().contains(productName)) {
                item.findElement(By.cssSelector(".remove-from-cart")).click();
                break;
            }
        }
    }

    @Then("product {string} should not be in the cart")
    public void productShouldNotBeInTheCart(String productName) {
        // Small wait for DOM update after removal
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        Assertions.assertFalse(DriverManager.getDriver().getPageSource().contains(productName),
                "Product '" + productName + "' is still in the cart");
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        Assertions.assertTrue(DriverManager.getDriver().findElements(By.cssSelector(".cart-item")).isEmpty(),
                "Cart is not empty");
    }

    @When("user clicks Proceed to Checkout")
    public void userClicksProceedToCheckout() {
        DriverManager.getDriver().findElement(By.cssSelector(".proceed-to-checkout")).click();
    }

    @When("user clicks Register\\/Login in checkout modal")
    public void userClicksRegisterLoginInCheckoutModal() {
        DriverManager.getDriver().findElement(By.cssSelector(".register-login")).click();
    }

    @Then("cart product prices should be displayed")
    public void cartProductPricesShouldBeDisplayed() {
        List<String> prices = DriverManager.getDriver().findElements(By.cssSelector(".cart-item-price"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
        Assertions.assertFalse(prices.isEmpty(), "No prices displayed in cart");
        for (String price : prices) {
            Assertions.assertFalse(price.isEmpty(), "Empty price found in cart");
        }
    }

    @And("cart should not be empty")
    public void cartShouldNotBeEmpty() {
        Assertions.assertFalse(DriverManager.getDriver().findElements(By.cssSelector(".cart-item")).isEmpty(),
                "Cart is empty");
    }
}
