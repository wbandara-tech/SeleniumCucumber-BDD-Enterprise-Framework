package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.pages.LoginPage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for Login scenarios.
 */
public class LoginSteps {

    private final LoginPage loginPage;

    public LoginSteps() {
        this.loginPage = new LoginPage(DriverManager.getDriver());
    }

    @Then("the login page should be displayed")
    public void theLoginPageShouldBeDisplayed() {
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page is not loaded");
    }

    @Then("Login to your account text should be visible")
    public void loginToYourAccountTextShouldBeVisible() {
        Assert.assertTrue(loginPage.isLoginToAccountVisible(), "'Login to your account' text is not visible");
    }

    @Then("New User Signup text should be visible")
    public void newUserSignupTextShouldBeVisible() {
        Assert.assertTrue(loginPage.isNewUserSignupVisible(), "'New User Signup!' text is not visible");
    }

    @When("user enters login email {string} and password {string}")
    public void userEntersLoginEmailAndPassword(String email, String password) {
        LoggerUtils.info(LoginSteps.class, "Entering login credentials for: " + email);
        loginPage.login(email, password);
    }

    @Then("login error message should be displayed")
    public void loginErrorMessageShouldBeDisplayed() {
        Assert.assertTrue(loginPage.isLoginErrorDisplayed(), "Login error message is not displayed");
    }

    @Then("login error message should be {string}")
    public void loginErrorMessageShouldBe(String expectedMessage) {
        String actualMessage = loginPage.getLoginErrorMessage();
        Assert.assertEquals(actualMessage, expectedMessage, "Login error message mismatch");
    }

    @When("user enters signup name {string} and email {string}")
    public void userEntersSignupNameAndEmail(String name, String email) {
        LoggerUtils.info(LoginSteps.class, "Entering signup details for: " + name);
        loginPage.enterSignupDetails(name, email);
    }

    @Then("signup error message should be displayed")
    public void signupErrorMessageShouldBeDisplayed() {
        Assert.assertTrue(loginPage.isSignupErrorDisplayed(), "Signup error message is not displayed");
    }

    @Then("signup error message should be {string}")
    public void signupErrorMessageShouldBe(String expectedMessage) {
        String actualMessage = loginPage.getSignupErrorMessage();
        Assert.assertEquals(actualMessage, expectedMessage, "Signup error message mismatch");
    }

    @And("user is on the login page")
    public void userIsOnTheLoginPage() {
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page is not loaded");
    }
}

