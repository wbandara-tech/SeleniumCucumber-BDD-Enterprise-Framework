@regression @search
Feature: Product Search
  As a user
  I want to search for products on the website
  So that I can find items I want to purchase

  Background:
    Given user is on the home page
    When user clicks on Products link
    Then the products page should be displayed

  @smoke @sanity
  Scenario: Search for an existing product
    When user searches for product "Blue Top"
    Then search results should be displayed
    And search results should contain "Blue Top"

  @regression
  Scenario: Search for a product and verify results are not empty
    When user searches for product "Top"
    Then search results should be displayed
    And products list should not be empty

  @regression
  Scenario Outline: Search for different products
    When user searches for product "<productName>"
    Then search results should be displayed
    And products list should not be empty

    Examples:
      | productName      |
      | Top              |
      | Tshirt           |
      | Dress            |
      | Jeans            |

  @smoke
  Scenario: View product details
    When user clicks View Product for product at index 0
    Then product detail page should be displayed
    And product name should be visible
    And product price should be visible

