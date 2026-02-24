package com.wbandara.enterprise.stepdefinitions;

import com.wbandara.enterprise.driver.DriverManager;
import com.wbandara.enterprise.pages.HomePage;
import com.wbandara.enterprise.utils.LoggerUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

/**
 * Step definitions for Home Page scenarios.
 */
public class HomeSteps {

    private final HomePage homePage;

    public HomeSteps() {
        this.homePage = new HomePage(DriverManager.getDriver());
        // Remove setHeadless(false) as it's not defined
    }

    @Given("user is on the home page")
    public void userIsOnTheHomePage() {
        LoggerUtils.info(HomeSteps.class, "Verifying user is on the home page");
        assertTrue("Home page is not loaded", homePage.isHomePageLoaded());
    }

    @Then("the home page should be displayed successfully")
    public void theHomePageShouldBeDisplayedSuccessfully() {
        assertTrue("Home page is not displayed", homePage.isHomePageLoaded());
    }

    @Then("the navbar should be visible with all elements")
    public void theNavbarShouldBeVisibleWithAllElements() {
        assertTrue("Navbar elements are not complete", homePage.isNavbarComplete());
    }

    @Then("the featured items section should be visible")
    public void theFeaturedItemsSectionShouldBeVisible() {
        assertTrue("Featured items section is not visible", homePage.isFeaturedItemsSectionVisible());
    }

    @Then("the category sidebar should be visible")
    public void theCategorySidebarShouldBeVisible() {
        assertTrue("Category sidebar is not visible", homePage.isCategorySidebarVisible());
    }

    @Then("the footer should be visible")
    public void theFooterShouldBeVisible() {
        assertTrue("Footer is not visible", homePage.isFooterVisible());
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
        assertTrue("User is not logged in", homePage.isUserLoggedIn());
        assertEquals("Logged in username mismatch", username, homePage.getLoggedInUsername());
    }

    @Then("Logout link should be visible")
    public void logoutLinkShouldBeVisible() {
        assertTrue("Logout link is not visible", homePage.isLogoutLinkVisible());
    }

    @Then("Signup\\/Login link should be visible")
    public void signupLoginLinkShouldBeVisible() {
        assertTrue("Signup/Login link is not visible", homePage.isSignupLoginLinkVisible());
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
        assertTrue("No featured products found on home page", count > 0);
    }
}
