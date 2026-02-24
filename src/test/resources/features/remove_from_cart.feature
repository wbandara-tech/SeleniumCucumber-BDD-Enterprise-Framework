@regression @cart @remove
Feature: Remove Product from Cart
  As a user
  I want to remove products from my cart
  So that I can update my order before checkout

  @smoke @sanity
  Scenario: Remove a product from cart
    Given user is on the home page
    When user adds product 0 to cart from home page
    And user clicks View Cart in modal
    Then the cart page should be displayed
    And cart should not be empty
    When user removes product at index 0 from cart
    Then the cart should be empty

  @regression
  Scenario: Verify cart after adding and removing products
    Given user is on the home page
    When user adds product 0 to cart from home page
    And user clicks Continue Shopping
    And user adds product 1 to cart from home page
    And user clicks View Cart in modal
    Then the cart page should be displayed
    And cart should not be empty
    When user removes product at index 0 from cart
    Then cart should not be empty
