@regression @registration
Feature: User Registration
  As a new user
  I want to register an account on Automation Exercise
  So that I can access all features of the website

  Background:
    Given user is on the home page
    When user clicks on Signup/Login link
    Then the login page should be displayed
    And New User Signup text should be visible

  @smoke @sanity
  Scenario: Register a new user with valid details
    When user enters signup name "TestUser" and email "testuser_auto_001@test.com"
    Then Enter Account Information text should be visible
    When user fills the registration form with the following details:
      | title        | Mr            |
      | password     | Test@1234     |
      | day          | 15            |
      | month        | 6             |
      | year         | 1990          |
      | firstName    | Test          |
      | lastName     | User          |
      | company      | TestCorp      |
      | address1     | 123 Main St   |
      | address2     | Suite 100     |
      | country      | United States |
      | state        | California    |
      | city         | Los Angeles   |
      | zipcode      | 90001         |
      | mobileNumber | 5551234567    |
    And user clicks Create Account button
    Then ACCOUNT CREATED message should be displayed
    When user clicks Continue button
    Then user should be logged in as "TestUser"

  @regression
  Scenario Outline: Register with different user details
    When user enters signup name "<name>" and email "<email>"
    Then Enter Account Information text should be visible
    When user fills the registration form with the following details:
      | title        | <title>       |
      | password     | <password>    |
      | day          | 10            |
      | month        | 3             |
      | year         | 1995          |
      | firstName    | <firstName>   |
      | lastName     | <lastName>    |
      | company      | <company>     |
      | address1     | <address>     |
      | address2     | Apt 1         |
      | country      | <country>     |
      | state        | <state>       |
      | city         | <city>        |
      | zipcode      | <zipcode>     |
      | mobileNumber | <phone>       |
    And user clicks Create Account button
    Then ACCOUNT CREATED message should be displayed

    Examples:
      | name     | email                        | title | password   | firstName | lastName | company  | address      | country       | state      | city        | zipcode | phone      |
      | UserOne  | user_one_auto_001@test.com   | Mr    | Pass@111   | User      | One      | CompOne  | 111 First St | United States | New York   | New York    | 10001   | 5551111111 |
      | UserTwo  | user_two_auto_001@test.com   | Mrs   | Pass@222   | User      | Two      | CompTwo  | 222 Second St| Canada        | Ontario    | Toronto     | M5V2T6  | 5552222222 |

  @regression
  Scenario: Register with existing email
    When user enters signup name "ExistingUser" and email "testuser_auto@test.com"
    Then signup error message should be displayed

