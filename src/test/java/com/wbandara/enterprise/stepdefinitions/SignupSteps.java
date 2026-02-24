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
 * Step definitions for Signup/Registration scenarios.
 */
public class SignupSteps {

    public SignupSteps() {
    }

    @Then("Enter Account Information text should be visible")
    public void enterAccountInformationTextShouldBeVisible() {
        Assertions.assertTrue(DriverManager.getDriver().getPageSource().contains("Enter Account Information"),
                "'Enter Account Information' text is not visible");
    }

    @When("user fills the registration form with the following details:")
    public void userFillsTheRegistrationFormWithTheFollowingDetails(DataTable dataTable) {
        LoggerUtils.info(SignupSteps.class, "Filling registration form from DataTable");
        Map<String, String> userData = dataTable.asMap(String.class, String.class);
        fillSignupForm(userData);
    }

    @When("user fills the registration form with details:")
    public void userFillsTheRegistrationFormWithDetails(Map<String, String> userData) {
        LoggerUtils.info(SignupSteps.class, "Filling registration form from map");
        fillSignupForm(userData);
    }

    @And("user clicks Create Account button")
    public void userClicksCreateAccountButton() {
        DriverManager.getDriver().findElement(By.id("createAccountButton")).click();
    }

    @Then("account should be created successfully")
    public void accountShouldBeCreatedSuccessfully() {
        Assertions.assertTrue(DriverManager.getDriver().getPageSource().contains("Account created successfully"),
                "Account was not created successfully");
    }

    @Then("ACCOUNT CREATED message should be displayed")
    public void accountCreatedMessageShouldBeDisplayed() {
        String message = DriverManager.getDriver().findElement(By.id("accountCreatedMessage")).getText();
        Assertions.assertTrue(message.contains("ACCOUNT CREATED"),
                "Account created message not displayed. Actual: " + message);
    }

    @And("user clicks Continue button")
    public void userClicksContinueButton() {
        DriverManager.getDriver().findElement(By.id("continueButton")).click();
    }

    @When("user completes registration with the following details:")
    public void userCompletesRegistrationWithTheFollowingDetails(DataTable dataTable) {
        Map<String, String> userData = dataTable.asMap(String.class, String.class);
        completeRegistration(userData);
    }

    private void fillSignupForm(Map<String, String> userData) {
        // Implement the method to fill the signup form using WebDriver
    }

    private void completeRegistration(Map<String, String> userData) {
        // Implement the method to complete registration using WebDriver
    }
}
