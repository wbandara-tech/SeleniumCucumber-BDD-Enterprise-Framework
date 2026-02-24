@regression @cart
Feature: Add Product to Cart
  As a user
  I want to add products to my shopping cart
  So that I can purchase them later

  @smoke @sanity
  Scenario: Add a single product to cart from home page
    Given user is on the home page
    When user adds product 0 to cart from home page
    And user clicks View Cart in modal
    Then the cart page should be displayed
    And cart should not be empty

  @regression
  Scenario: Add multiple products to cart
    Given user is on the home page
    When user adds product 0 to cart from home page
    And user clicks Continue Shopping
    And user adds product 1 to cart from home page
    And user clicks View Cart in modal
    Then the cart page should be displayed
    And cart should not be empty

  @regression
  Scenario: Add product to cart from products page
    Given user is on the home page
    When user clicks on Products link
    Then the products page should be displayed
    When user adds product at index 0 to cart from products page
    And user clicks View Cart on products page
    Then the cart page should be displayed
    And cart should not be empty

  @regression
  Scenario: Verify cart displays product prices
    Given user is on the home page
    When user adds product 0 to cart from home page
    And user clicks View Cart in modal
    Then the cart page should be displayed
    And cart product prices should be displayed
