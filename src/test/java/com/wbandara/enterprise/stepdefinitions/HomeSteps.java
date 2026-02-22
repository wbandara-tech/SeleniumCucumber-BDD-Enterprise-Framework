package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.pages.HomePage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for Home Page scenarios.
 */
public class HomeSteps {

    private final HomePage homePage;

    public HomeSteps() {
        this.homePage = new HomePage(DriverManager.getDriver());
    }

    @Given("user is on the home page")
    public void userIsOnTheHomePage() {
        LoggerUtils.info(HomeSteps.class, "Verifying user is on the home page");
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page is not loaded");
    }

    @Then("the home page should be displayed successfully")
    public void theHomePageShouldBeDisplayedSuccessfully() {
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page is not displayed");
    }

    @Then("the navbar should be visible with all elements")
    public void theNavbarShouldBeVisibleWithAllElements() {
        Assert.assertTrue(homePage.isNavbarComplete(), "Navbar elements are not complete");
    }

    @Then("the featured items section should be visible")
    public void theFeaturedItemsSectionShouldBeVisible() {
        Assert.assertTrue(homePage.isFeaturedItemsSectionVisible(), "Featured items section is not visible");
    }

    @Then("the category sidebar should be visible")
    public void theCategorySidebarShouldBeVisible() {
        Assert.assertTrue(homePage.isCategorySidebarVisible(), "Category sidebar is not visible");
    }

    @Then("the footer should be visible")
    public void theFooterShouldBeVisible() {
        Assert.assertTrue(homePage.isFooterVisible(), "Footer is not visible");
    }

    @When("user clicks on Signup\\/Login link")
    public void userClicksOnSignupLoginLink() {
        homePage.navigateToLoginPage();
    }

    @When("user clicks on Products link")
    public void userClicksOnProductsLink() {
        homePage.navigateToProducts();
    }

    @When("user clicks on Cart link")
    public void userClicksOnCartLink() {
        homePage.navigateToCart();
    }

    @When("user clicks on Logout link")
    public void userClicksOnLogoutLink() {
        homePage.clickLogout();
    }

    @Then("user should be logged in as {string}")
    public void userShouldBeLoggedInAs(String username) {
        Assert.assertTrue(homePage.isUserLoggedIn(), "User is not logged in");
        Assert.assertEquals(homePage.getLoggedInUsername(), username, "Logged in username mismatch");
    }

    @Then("Logout link should be visible")
    public void logoutLinkShouldBeVisible() {
        Assert.assertTrue(homePage.isLogoutLinkVisible(), "Logout link is not visible");
    }

    @Then("Signup\\/Login link should be visible")
    public void signupLoginLinkShouldBeVisible() {
        Assert.assertTrue(homePage.isSignupLoginLinkVisible(), "Signup/Login link is not visible");
    }

    @When("user adds product {int} to cart from home page")
    public void userAddsProductToCartFromHomePage(int index) {
        homePage.addProductToCart(index);
    }

    @When("user clicks Continue Shopping")
    public void userClicksContinueShopping() {
        homePage.clickContinueShopping();
    }

    @When("user clicks View Cart in modal")
    public void userClicksViewCartInModal() {
        homePage.clickViewCartInModal();
    }

    @Then("the home page should have featured products")
    public void theHomePageShouldHaveFeaturedProducts() {
        int count = homePage.getFeaturedProductCount();
        Assert.assertTrue(count > 0, "No featured products found on home page");
    }
}

