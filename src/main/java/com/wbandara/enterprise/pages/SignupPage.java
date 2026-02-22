package com.wbandara.enterprise.pages;

import com.wbandara.enterprise.base.BasePage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Page Object for the Signup/Registration Page.
 */
public class SignupPage extends BasePage {

    // ======================== Locators ========================
    private static final By ENTER_INFO_HEADER = By.cssSelector("h2.title b");
    private static final By TITLE_MR = By.id("id_gender1");
    private static final By TITLE_MRS = By.id("id_gender2");
    private static final By NAME_INPUT = By.id("name");
    private static final By EMAIL_INPUT = By.id("email");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By DAY_DROPDOWN = By.id("days");
    private static final By MONTH_DROPDOWN = By.id("months");
    private static final By YEAR_DROPDOWN = By.id("years");
    private static final By NEWSLETTER_CHECKBOX = By.id("newsletter");
    private static final By OFFERS_CHECKBOX = By.id("optin");
    private static final By FIRST_NAME = By.id("first_name");
    private static final By LAST_NAME = By.id("last_name");
    private static final By COMPANY = By.id("company");
    private static final By ADDRESS1 = By.id("address1");
    private static final By ADDRESS2 = By.id("address2");
    private static final By COUNTRY_DROPDOWN = By.id("country");
    private static final By STATE = By.id("state");
    private static final By CITY = By.id("city");
    private static final By ZIPCODE = By.id("zipcode");
    private static final By MOBILE_NUMBER = By.id("mobile_number");
    private static final By CREATE_ACCOUNT_BTN = By.cssSelector("button[data-qa='create-account']");
    private static final By ACCOUNT_CREATED_MSG = By.cssSelector("h2.title b");
    private static final By CONTINUE_BTN = By.cssSelector("a[data-qa='continue-button']");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    // ======================== Actions ========================

    @Step("Verify 'Enter Account Information' is visible")
    public boolean isEnterInfoHeaderVisible() {
        LoggerUtils.info(SignupPage.class, "Checking if Enter Account Info header is visible");
        return isDisplayed(ENTER_INFO_HEADER);
    }

    @Step("Fill signup form with provided details")
    public void fillSignupForm(Map<String, String> userData) {
        LoggerUtils.info(SignupPage.class, "Filling signup form");

        // Title selection
        String title = userData.getOrDefault("title", "Mr");
        if ("Mrs".equalsIgnoreCase(title)) {
            click(TITLE_MRS);
        } else {
            click(TITLE_MR);
        }

        // Password
        type(PASSWORD_INPUT, userData.getOrDefault("password", "Test@1234"));

        // Date of Birth
        selectByValue(DAY_DROPDOWN, userData.getOrDefault("day", "15"));
        selectByValue(MONTH_DROPDOWN, userData.getOrDefault("month", "6"));
        selectByValue(YEAR_DROPDOWN, userData.getOrDefault("year", "1990"));

        // Newsletter and offers
        selectCheckbox(NEWSLETTER_CHECKBOX);
        selectCheckbox(OFFERS_CHECKBOX);

        // Address Information
        type(FIRST_NAME, userData.getOrDefault("firstName", "Test"));
        type(LAST_NAME, userData.getOrDefault("lastName", "User"));
        type(COMPANY, userData.getOrDefault("company", "TestCompany"));
        type(ADDRESS1, userData.getOrDefault("address1", "123 Test Street"));
        type(ADDRESS2, userData.getOrDefault("address2", "Apt 456"));
        selectByVisibleText(COUNTRY_DROPDOWN, userData.getOrDefault("country", "United States"));
        type(STATE, userData.getOrDefault("state", "California"));
        type(CITY, userData.getOrDefault("city", "Los Angeles"));
        type(ZIPCODE, userData.getOrDefault("zipcode", "90001"));
        type(MOBILE_NUMBER, userData.getOrDefault("mobileNumber", "1234567890"));
    }

    @Step("Click Create Account button")
    public void clickCreateAccount() {
        LoggerUtils.info(SignupPage.class, "Clicking Create Account button");
        click(CREATE_ACCOUNT_BTN);
    }

    @Step("Verify account created successfully")
    public boolean isAccountCreated() {
        String text = getText(ACCOUNT_CREATED_MSG);
        return text.contains("ACCOUNT CREATED!");
    }

    @Step("Click Continue button after account creation")
    public void clickContinue() {
        click(CONTINUE_BTN);
    }

    @Step("Complete registration with provided data")
    public void completeRegistration(Map<String, String> userData) {
        fillSignupForm(userData);
        clickCreateAccount();
    }

    @Step("Get account created message")
    public String getAccountCreatedMessage() {
        return getText(ACCOUNT_CREATED_MSG);
    }
}

