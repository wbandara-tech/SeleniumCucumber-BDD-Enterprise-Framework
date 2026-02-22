package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.pages.SignupPage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Map;

/**
 * Step definitions for Signup/Registration scenarios.
 */
public class SignupSteps {

    private final SignupPage signupPage;

    public SignupSteps() {
        this.signupPage = new SignupPage(DriverManager.getDriver());
    }

    @Then("Enter Account Information text should be visible")
    public void enterAccountInformationTextShouldBeVisible() {
        Assert.assertTrue(signupPage.isEnterInfoHeaderVisible(),
                "'Enter Account Information' text is not visible");
    }

    @When("user fills the registration form with the following details:")
    public void userFillsTheRegistrationFormWithTheFollowingDetails(DataTable dataTable) {
        LoggerUtils.info(SignupSteps.class, "Filling registration form from DataTable");
        Map<String, String> userData = dataTable.asMap(String.class, String.class);
        signupPage.fillSignupForm(userData);
    }

    @When("user fills the registration form with details:")
    public void userFillsTheRegistrationFormWithDetails(Map<String, String> userData) {
        LoggerUtils.info(SignupSteps.class, "Filling registration form from map");
        signupPage.fillSignupForm(userData);
    }

    @And("user clicks Create Account button")
    public void userClicksCreateAccountButton() {
        signupPage.clickCreateAccount();
    }

    @Then("account should be created successfully")
    public void accountShouldBeCreatedSuccessfully() {
        Assert.assertTrue(signupPage.isAccountCreated(),
                "Account was not created successfully");
    }

    @Then("ACCOUNT CREATED message should be displayed")
    public void accountCreatedMessageShouldBeDisplayed() {
        String message = signupPage.getAccountCreatedMessage();
        Assert.assertTrue(message.contains("ACCOUNT CREATED"),
                "Account created message not displayed. Actual: " + message);
    }

    @And("user clicks Continue button")
    public void userClicksContinueButton() {
        signupPage.clickContinue();
    }

    @When("user completes registration with the following details:")
    public void userCompletesRegistrationWithTheFollowingDetails(DataTable dataTable) {
        Map<String, String> userData = dataTable.asMap(String.class, String.class);
        signupPage.completeRegistration(userData);
    }
}

