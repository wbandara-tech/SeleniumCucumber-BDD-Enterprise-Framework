package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Step definitions for Login scenarios.
 */
public class LoginSteps {

    private final WebDriver driver;

    public LoginSteps() {
        this.driver = DriverManager.getDriver();
    }

    @Then("the login page should be displayed")
    public void theLoginPageShouldBeDisplayed() {
        Assertions.assertTrue(isElementDisplayed(By.id("loginPageIdentifier")), "Login page is not loaded");
    }

    @Then("Login to your account text should be visible")
    public void loginToYourAccountTextShouldBeVisible() {
        Assertions.assertTrue(isElementDisplayed(By.id("loginToAccountText")), "'Login to your account' text is not visible");
    }

    @Then("New User Signup text should be visible")
    public void newUserSignupTextShouldBeVisible() {
        Assertions.assertTrue(isElementDisplayed(By.id("newUserSignupText")), "'New User Signup!' text is not visible");
    }

    @When("user enters login email {string} and password {string}")
    public void userEntersLoginEmailAndPassword(String email, String password) {
        LoggerUtils.info(LoginSteps.class, "Entering login credentials for: " + email);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
    }

    @Then("login error message should be displayed")
    public void loginErrorMessageShouldBeDisplayed() {
        Assertions.assertTrue(isElementDisplayed(By.id("loginErrorMessage")), "Login error message is not displayed");
    }

    @Then("login error message should be {string}")
    public void loginErrorMessageShouldBe(String expectedMessage) {
        String actualMessage = driver.findElement(By.id("loginErrorMessage")).getText();
        Assertions.assertEquals(actualMessage, expectedMessage, "Login error message mismatch");
    }

    @When("user enters signup name {string} and email {string}")
    public void userEntersSignupNameAndEmail(String name, String email) {
        LoggerUtils.info(LoginSteps.class, "Entering signup details for: " + name);
        driver.findElement(By.id("signupName")).sendKeys(name);
        driver.findElement(By.id("signupEmail")).sendKeys(email);
        driver.findElement(By.id("signupButton")).click();
    }

    @Then("signup error message should be displayed")
    public void signupErrorMessageShouldBeDisplayed() {
        Assertions.assertTrue(isElementDisplayed(By.id("signupErrorMessage")), "Signup error message is not displayed");
    }

    @Then("signup error message should be {string}")
    public void signupErrorMessageShouldBe(String expectedMessage) {
        String actualMessage = driver.findElement(By.id("signupErrorMessage")).getText();
        Assertions.assertEquals(actualMessage, expectedMessage, "Signup error message mismatch");
    }

    @And("user is on the login page")
    public void userIsOnTheLoginPage() {
        Assertions.assertTrue(isElementDisplayed(By.id("loginPageIdentifier")), "Login page is not loaded");
    }

    private boolean isElementDisplayed(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
