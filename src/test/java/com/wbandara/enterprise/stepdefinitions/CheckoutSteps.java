package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.pages.CheckoutPage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Map;

/**
 * Step definitions for Checkout scenarios.
 */
public class CheckoutSteps {

    private final CheckoutPage checkoutPage;

    public CheckoutSteps() {
        this.checkoutPage = new CheckoutPage(DriverManager.getDriver());
    }

    @Then("the checkout page should be displayed")
    public void theCheckoutPageShouldBeDisplayed() {
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page is not loaded");
    }

    @Then("delivery address should be displayed")
    public void deliveryAddressShouldBeDisplayed() {
        Assert.assertTrue(checkoutPage.isDeliveryAddressDisplayed(), "Delivery address is not displayed");
    }

    @Then("billing address should be displayed")
    public void billingAddressShouldBeDisplayed() {
        Assert.assertTrue(checkoutPage.isBillingAddressDisplayed(), "Billing address is not displayed");
    }

    @Then("delivery address should contain {string}")
    public void deliveryAddressShouldContain(String expectedText) {
        Assert.assertTrue(checkoutPage.verifyAddressContains(expectedText),
                "Delivery address does not contain: " + expectedText);
    }

    @When("user adds order comment {string}")
    public void userAddsOrderComment(String comment) {
        LoggerUtils.info(CheckoutSteps.class, "Adding order comment: " + comment);
        checkoutPage.addComment(comment);
    }

    @When("user clicks Place Order")
    public void userClicksPlaceOrder() {
        checkoutPage.clickPlaceOrder();
    }

    @When("user enters payment details:")
    public void userEntersPaymentDetails(DataTable dataTable) {
        Map<String, String> paymentData = dataTable.asMap(String.class, String.class);
        LoggerUtils.info(CheckoutSteps.class, "Entering payment details");
        checkoutPage.enterPaymentDetails(
                paymentData.get("nameOnCard"),
                paymentData.get("cardNumber"),
                paymentData.get("cvc"),
                paymentData.get("expiryMonth"),
                paymentData.get("expiryYear")
        );
    }

    @When("user enters payment with name {string} card {string} cvc {string} expiry {string}\\/{string}")
    public void userEntersPaymentDetails(String name, String card, String cvc, String month, String year) {
        checkoutPage.enterPaymentDetails(name, card, cvc, month, year);
    }

    @And("user clicks Pay and Confirm Order")
    public void userClicksPayAndConfirmOrder() {
        checkoutPage.clickPayAndConfirm();
    }

    @Then("order should be placed successfully")
    public void orderShouldBePlacedSuccessfully() {
        Assert.assertTrue(checkoutPage.isOrderPlacedSuccessfully(),
                "Order was not placed successfully");
    }

    @Then("order confirmation message should be displayed")
    public void orderConfirmationMessageShouldBeDisplayed() {
        String message = checkoutPage.getOrderConfirmationMessage();
        Assert.assertNotNull(message, "Order confirmation message is null");
        Assert.assertFalse(message.isEmpty(), "Order confirmation message is empty");
    }

    @And("user clicks Continue after order")
    public void userClicksContinueAfterOrder() {
        checkoutPage.clickContinueAfterOrder();
    }

    @Then("checkout should have {int} item(s)")
    public void checkoutShouldHaveItems(int expectedCount) {
        int actualCount = checkoutPage.getCheckoutItemCount();
        Assert.assertTrue(actualCount >= expectedCount,
                "Checkout items mismatch. Expected at least: " + expectedCount + ", Actual: " + actualCount);
    }
}

