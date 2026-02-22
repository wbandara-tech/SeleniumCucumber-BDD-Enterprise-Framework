@regression @login
Feature: User Login
  As a registered user
  I want to log into my account
  So that I can access personalized features

  Background:
    Given user is on the home page
    When user clicks on Signup/Login link
    Then the login page should be displayed
    And Login to your account text should be visible

  @smoke @sanity
  Scenario: Login with valid credentials
    When user enters login email "testuser_auto@test.com" and password "Test@1234"
    Then user should be logged in as "TestUser"
    And Logout link should be visible

  @smoke
  Scenario: Login with invalid email
    When user enters login email "invalid_email@test.com" and password "wrongpass"
    Then login error message should be displayed
    And login error message should be "Your email or password is incorrect!"

  @regression
  Scenario: Login with invalid password
    When user enters login email "testuser_auto@test.com" and password "WrongPassword"
    Then login error message should be displayed
    And login error message should be "Your email or password is incorrect!"

  @regression
  Scenario Outline: Login with multiple invalid credentials
    When user enters login email "<email>" and password "<password>"
    Then login error message should be displayed
    And login error message should be "Your email or password is incorrect!"

    Examples:
      | email                    | password     |
      | invalid1@test.com        | password123  |
      | invalid2@test.com        | wrongpass    |
      | test@nonexistent.com     | Test@1234    |
      | empty@test.com           | empty        |

