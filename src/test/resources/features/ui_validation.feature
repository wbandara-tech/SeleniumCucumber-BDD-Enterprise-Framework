@regression @ui
Feature: UI Validation
  As a user
  I want to verify that key UI elements are displayed correctly
  So that I can have a good user experience

  @smoke @sanity
  Scenario: Verify home page UI elements
    Given user is on the home page
    Then the home page should be displayed successfully
    And the navbar should be visible with all elements
    And the featured items section should be visible
    And the category sidebar should be visible
    And the footer should be visible
    And the home page should have featured products

  @regression
  Scenario: Verify login page UI elements
    Given user is on the home page
    When user clicks on Signup/Login link
    Then the login page should be displayed
    And Login to your account text should be visible
    And New User Signup text should be visible

  @regression
  Scenario: Verify products page UI elements
    Given user is on the home page
    When user clicks on Products link
    Then the products page should be displayed
    And products list should not be empty

  @regression
  Scenario: Verify cart page UI elements
    Given user is on the home page
    When user clicks on Cart link
    Then the cart page should be displayed

