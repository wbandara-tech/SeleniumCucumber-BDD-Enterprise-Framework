package com.wbandara.enterprise.pages;

import com.wbandara.enterprise.base.BasePage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Login/Signup Page.
 */
public class LoginPage extends BasePage {

    // ======================== Login Form Locators ========================
    private static final By LOGIN_HEADER = By.cssSelector("div.login-form h2");
    private static final By LOGIN_EMAIL = By.cssSelector("input[data-qa='login-email']");
    private static final By LOGIN_PASSWORD = By.cssSelector("input[data-qa='login-password']");
    private static final By LOGIN_BUTTON = By.cssSelector("button[data-qa='login-button']");
    private static final By LOGIN_ERROR_MSG = By.cssSelector("div.login-form p");

    // ======================== Signup Form Locators ========================
    private static final By SIGNUP_HEADER = By.cssSelector("div.signup-form h2");
    private static final By SIGNUP_NAME = By.cssSelector("input[data-qa='signup-name']");
    private static final By SIGNUP_EMAIL = By.cssSelector("input[data-qa='signup-email']");
    private static final By SIGNUP_BUTTON = By.cssSelector("button[data-qa='signup-button']");
    private static final By SIGNUP_ERROR_MSG = By.cssSelector("div.signup-form p");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ======================== Login Actions ========================

    @Step("Verify login page is loaded")
    public boolean isLoginPageLoaded() {
        LoggerUtils.info(LoginPage.class, "Verifying login page is loaded");
        return isDisplayed(LOGIN_HEADER) && isDisplayed(SIGNUP_HEADER);
    }

    @Step("Login with email: {email}")
    public void login(String email, String password) {
        LoggerUtils.info(LoginPage.class, "Logging in with email: " + email);
        type(LOGIN_EMAIL, email);
        type(LOGIN_PASSWORD, password);
        click(LOGIN_BUTTON);
    }

    @Step("Get login error message")
    public String getLoginErrorMessage() {
        return getText(LOGIN_ERROR_MSG);
    }

    @Step("Verify login error message is displayed")
    public boolean isLoginErrorDisplayed() {
        return isDisplayed(LOGIN_ERROR_MSG);
    }

    @Step("Get login header text")
    public String getLoginHeaderText() {
        return getText(LOGIN_HEADER);
    }

    // ======================== Signup Actions ========================

    @Step("Enter signup details - Name: {name}, Email: {email}")
    public void enterSignupDetails(String name, String email) {
        LoggerUtils.info(LoginPage.class, "Entering signup details for: " + name);
        type(SIGNUP_NAME, name);
        type(SIGNUP_EMAIL, email);
        click(SIGNUP_BUTTON);
    }

    @Step("Get signup error message")
    public String getSignupErrorMessage() {
        return getText(SIGNUP_ERROR_MSG);
    }

    @Step("Verify signup error message is displayed")
    public boolean isSignupErrorDisplayed() {
        return isDisplayed(SIGNUP_ERROR_MSG);
    }

    @Step("Get signup header text")
    public String getSignupHeaderText() {
        return getText(SIGNUP_HEADER);
    }

    @Step("Verify 'Login to your account' text is visible")
    public boolean isLoginToAccountVisible() {
        String text = getLoginHeaderText();
        return text.contains("Login to your account");
    }

    @Step("Verify 'New User Signup!' text is visible")
    public boolean isNewUserSignupVisible() {
        String text = getSignupHeaderText();
        return text.contains("New User Signup!");
    }
}

