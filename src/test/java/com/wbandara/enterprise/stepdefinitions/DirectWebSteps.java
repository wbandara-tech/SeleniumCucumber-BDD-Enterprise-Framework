package com.wbandara.enterprise.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;

public class DirectWebSteps {
    private WebDriver driver;

    @Given("user opens the browser and navigates to {string}")
    public void user_opens_browser_and_navigates(String url) {
        driver = new ChromeDriver();
        driver.get(url);
    }

    @When("user enters {string} in the search box and clicks search")
    public void user_enters_in_search_box_and_clicks_search(String query) {
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys(query);
        searchBox.submit();
    }

    @Then("search results should be displayed")
    public void search_results_should_be_displayed() {
        WebElement results = driver.findElement(By.id("results"));
        Assert.assertTrue(results.isDisplayed());
    }

    @Then("close the browser")
    public void close_the_browser() {
        driver.quit();
    }
}

