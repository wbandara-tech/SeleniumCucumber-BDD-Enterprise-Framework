package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

/**
 * Step definitions for Checkout scenarios.
 */
public class CheckoutSteps {

    @Then("the checkout page should be displayed")
    public void theCheckoutPageShouldBeDisplayed() {
        Assertions.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("checkout"),
                "Checkout page is not loaded");
    }

    @Then("delivery address should be displayed")
    public void deliveryAddressShouldBeDisplayed() {
        Assertions.assertTrue(DriverManager.getDriver().findElement(By.id("delivery-address")).isDisplayed(),
                "Delivery address is not displayed");
    }

    @Then("billing address should be displayed")
    public void billingAddressShouldBeDisplayed() {
        Assertions.assertTrue(DriverManager.getDriver().findElement(By.id("billing-address")).isDisplayed(),
                "Billing address is not displayed");
    }

    @Then("delivery address should contain {string}")
    public void deliveryAddressShouldContain(String expectedText) {
        String addressText = DriverManager.getDriver().findElement(By.id("delivery-address")).getText();
        Assertions.assertTrue(addressText.contains(expectedText),
                "Delivery address does not contain: " + expectedText);
    }

    @When("user adds order comment {string}")
    public void userAddsOrderComment(String comment) {
        LoggerUtils.info(CheckoutSteps.class, "Adding order comment: " + comment);
        DriverManager.getDriver().findElement(By.id("order-comment")).sendKeys(comment);
    }

    @When("user clicks Place Order")
    public void userClicksPlaceOrder() {
        DriverManager.getDriver().findElement(By.id("place-order")).click();
    }

    @When("user enters payment details:")
    public void userEntersPaymentDetails(DataTable dataTable) {
        Map<String, String> paymentData = dataTable.asMap(String.class, String.class);
        LoggerUtils.info(CheckoutSteps.class, "Entering payment details");
        DriverManager.getDriver().findElement(By.id("name-on-card")).sendKeys(paymentData.get("nameOnCard"));
        DriverManager.getDriver().findElement(By.id("card-number")).sendKeys(paymentData.get("cardNumber"));
        DriverManager.getDriver().findElement(By.id("cvc")).sendKeys(paymentData.get("cvc"));
        DriverManager.getDriver().findElement(By.id("expiry-month")).sendKeys(paymentData.get("expiryMonth"));
        DriverManager.getDriver().findElement(By.id("expiry-year")).sendKeys(paymentData.get("expiryYear"));
    }

    @When("user enters payment with name {string} card {string} cvc {string} expiry {string}\\/{string}")
    public void userEntersPaymentDetails(String name, String card, String cvc, String month, String year) {
        DriverManager.getDriver().findElement(By.id("name-on-card")).sendKeys(name);
        DriverManager.getDriver().findElement(By.id("card-number")).sendKeys(card);
        DriverManager.getDriver().findElement(By.id("cvc")).sendKeys(cvc);
        DriverManager.getDriver().findElement(By.id("expiry-month")).sendKeys(month);
        DriverManager.getDriver().findElement(By.id("expiry-year")).sendKeys(year);
    }

    @And("user clicks Pay and Confirm Order")
    public void userClicksPayAndConfirmOrder() {
        DriverManager.getDriver().findElement(By.id("pay-and-confirm")).click();
    }

    @Then("order should be placed successfully")
    public void orderShouldBePlacedSuccessfully() {
        Assertions.assertTrue(DriverManager.getDriver().findElement(By.id("order-success")).isDisplayed(),
                "Order was not placed successfully");
    }

    @Then("order confirmation message should be displayed")
    public void orderConfirmationMessageShouldBeDisplayed() {
        String message = DriverManager.getDriver().findElement(By.id("order-confirmation-message")).getText();
        Assertions.assertNotNull(message, "Order confirmation message is null");
        Assertions.assertFalse(message.isEmpty(), "Order confirmation message is empty");
    }

    @And("user clicks Continue after order")
    public void userClicksContinueAfterOrder() {
        DriverManager.getDriver().findElement(By.id("continue-after-order")).click();
    }

    @Then("checkout should have {int} item(s)")
    public void checkoutShouldHaveItems(int expectedCount) {
        int actualCount = DriverManager.getDriver().findElements(By.className("checkout-item")).size();
        Assertions.assertTrue(actualCount >= expectedCount,
                "Checkout items mismatch. Expected at least: " + expectedCount + ", Actual: " + actualCount);
    }
}
