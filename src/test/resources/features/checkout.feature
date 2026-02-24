@regression @checkout @e2e
Feature: Checkout Flow
  As a registered user
  I want to complete the checkout process
  So that I can place an order for my selected products

  @smoke @sanity
  Scenario: Checkout without login should prompt registration
    Given user is on the home page
    When user adds product 0 to cart from home page
    And user clicks View Cart in modal
    Then the cart page should be displayed
    When user clicks Proceed to Checkout
    And user clicks Register/Login in checkout modal
    Then the login page should be displayed

  @regression
  Scenario: Verify checkout redirects unauthenticated user
    Given user is on the home page
    When user adds product 0 to cart from home page
    And user clicks View Cart in modal
    Then the cart page should be displayed
    And cart should not be empty
    When user clicks Proceed to Checkout
    And user clicks Register/Login in checkout modal
    Then the login page should be displayed
    And Login to your account text should be visible
