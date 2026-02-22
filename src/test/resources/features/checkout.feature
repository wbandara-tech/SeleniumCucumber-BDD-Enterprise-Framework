@regression @checkout @e2e
Feature: Checkout Flow
  As a registered user
  I want to complete the checkout process
  So that I can place an order for my selected products

  @smoke @sanity
  Scenario: Complete checkout flow with payment
    Given user is on the home page
    When user clicks on Signup/Login link
    Then the login page should be displayed
    When user enters login email "testuser_auto@test.com" and password "Test@1234"
    Then user should be logged in as "TestUser"
    When user adds product 0 to cart from home page
    And user clicks View Cart in modal
    Then the cart page should be displayed
    And cart should not be empty
    When user clicks Proceed to Checkout
    Then the checkout page should be displayed
    And delivery address should be displayed
    And billing address should be displayed
    When user adds order comment "Please deliver between 9 AM - 5 PM"
    And user clicks Place Order
    And user enters payment details:
      | nameOnCard  | Test User        |
      | cardNumber  | 4111111111111111 |
      | cvc         | 123              |
      | expiryMonth | 12               |
      | expiryYear  | 2030             |
    And user clicks Pay and Confirm Order
    Then order should be placed successfully

  @regression
  Scenario: Checkout flow - verify address details
    Given user is on the home page
    When user clicks on Signup/Login link
    And user enters login email "testuser_auto@test.com" and password "Test@1234"
    Then user should be logged in as "TestUser"
    When user adds product 0 to cart from home page
    And user clicks View Cart in modal
    And user clicks Proceed to Checkout
    Then the checkout page should be displayed
    And delivery address should be displayed
    And billing address should be displayed

  @regression
  Scenario: Checkout without login should prompt registration
    Given user is on the home page
    When user adds product 0 to cart from home page
    And user clicks View Cart in modal
    Then the cart page should be displayed
    When user clicks Proceed to Checkout
    And user clicks Register/Login in checkout modal
    Then the login page should be displayed

