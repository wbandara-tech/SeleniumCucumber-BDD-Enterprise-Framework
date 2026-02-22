package com.wbandara.enterprise.pages;

import com.wbandara.enterprise.base.BasePage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Checkout Page.
 */
public class CheckoutPage extends BasePage {

    // ======================== Locators ========================
    private static final By DELIVERY_ADDRESS = By.id("address_delivery");
    private static final By BILLING_ADDRESS = By.id("address_invoice");
    private static final By DELIVERY_FIRST_LAST_NAME = By.cssSelector("#address_delivery .address_firstname");
    private static final By DELIVERY_ADDRESS_LINE1 = By.cssSelector("#address_delivery .address_address1:nth-of-type(4)");
    private static final By DELIVERY_CITY_STATE_ZIP = By.cssSelector("#address_delivery .address_city");
    private static final By DELIVERY_COUNTRY = By.cssSelector("#address_delivery .address_country_name");
    private static final By DELIVERY_PHONE = By.cssSelector("#address_delivery .address_phone");
    private static final By ORDER_COMMENT = By.cssSelector("textarea[name='message']");
    private static final By PLACE_ORDER_BTN = By.cssSelector("a.btn.btn-default.check_out");
    private static final By CART_ITEMS_IN_CHECKOUT = By.cssSelector("#cart_info table tbody tr");
    private static final By TOTAL_AMOUNT = By.cssSelector("#cart_info table tbody .cart_total_price");

    // Payment Page Locators
    private static final By NAME_ON_CARD = By.cssSelector("input[data-qa='name-on-card']");
    private static final By CARD_NUMBER = By.cssSelector("input[data-qa='card-number']");
    private static final By CVC = By.cssSelector("input[data-qa='cvc']");
    private static final By EXPIRY_MONTH = By.cssSelector("input[data-qa='expiry-month']");
    private static final By EXPIRY_YEAR = By.cssSelector("input[data-qa='expiry-year']");
    private static final By PAY_CONFIRM_BTN = By.cssSelector("button[data-qa='pay-button']");
    private static final By ORDER_SUCCESS_MSG = By.cssSelector("div.col-sm-9 p");
    private static final By ORDER_PLACED_HEADER = By.cssSelector("h2.title b");
    private static final By DOWNLOAD_INVOICE = By.cssSelector("a.btn.btn-default.check_out");
    private static final By CONTINUE_AFTER_ORDER = By.cssSelector("a[data-qa='continue-button']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // ======================== Actions ========================

    @Step("Verify checkout page is loaded")
    public boolean isCheckoutPageLoaded() {
        LoggerUtils.info(CheckoutPage.class, "Verifying checkout page is loaded");
        return isDisplayed(DELIVERY_ADDRESS);
    }

    @Step("Verify delivery address is displayed")
    public boolean isDeliveryAddressDisplayed() {
        return isDisplayed(DELIVERY_ADDRESS);
    }

    @Step("Verify billing address is displayed")
    public boolean isBillingAddressDisplayed() {
        return isDisplayed(BILLING_ADDRESS);
    }

    @Step("Get delivery name")
    public String getDeliveryName() {
        return getText(DELIVERY_FIRST_LAST_NAME);
    }

    @Step("Get delivery country")
    public String getDeliveryCountry() {
        return getText(DELIVERY_COUNTRY);
    }

    @Step("Get delivery phone")
    public String getDeliveryPhone() {
        return getText(DELIVERY_PHONE);
    }

    @Step("Add order comment: {comment}")
    public void addComment(String comment) {
        LoggerUtils.info(CheckoutPage.class, "Adding order comment");
        type(ORDER_COMMENT, comment);
    }

    @Step("Click Place Order button")
    public void clickPlaceOrder() {
        LoggerUtils.info(CheckoutPage.class, "Clicking Place Order");
        click(PLACE_ORDER_BTN);
    }

    @Step("Enter payment details")
    public void enterPaymentDetails(String nameOnCard, String cardNumber,
                                     String cvc, String expiryMonth, String expiryYear) {
        LoggerUtils.info(CheckoutPage.class, "Entering payment details");
        type(NAME_ON_CARD, nameOnCard);
        type(CARD_NUMBER, cardNumber);
        type(CVC, cvc);
        type(EXPIRY_MONTH, expiryMonth);
        type(EXPIRY_YEAR, expiryYear);
    }

    @Step("Click Pay and Confirm Order")
    public void clickPayAndConfirm() {
        LoggerUtils.info(CheckoutPage.class, "Clicking Pay and Confirm Order");
        click(PAY_CONFIRM_BTN);
    }

    @Step("Verify order placed successfully")
    public boolean isOrderPlacedSuccessfully() {
        try {
            String msg = getText(ORDER_SUCCESS_MSG);
            return msg.contains("Congratulations") || msg.contains("placed");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get order confirmation message")
    public String getOrderConfirmationMessage() {
        return getText(ORDER_PLACED_HEADER);
    }

    @Step("Click Continue after order placement")
    public void clickContinueAfterOrder() {
        click(CONTINUE_AFTER_ORDER);
    }

    @Step("Get number of items in checkout")
    public int getCheckoutItemCount() {
        try {
            return findElements(CART_ITEMS_IN_CHECKOUT).size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Step("Verify address details contain: {expectedText}")
    public boolean verifyAddressContains(String expectedText) {
        String addressText = getText(DELIVERY_ADDRESS);
        return addressText.contains(expectedText);
    }
}

