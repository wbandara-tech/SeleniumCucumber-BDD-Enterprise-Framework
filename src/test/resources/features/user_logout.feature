@regression @logout
Feature: User Logout
  As a logged-in user
  I want to log out of my account
  So that my session is securely ended

  @smoke @sanity
  Scenario: Successful logout after login
    Given user is on the home page
    When user clicks on Signup/Login link
    Then the login page should be displayed
    When user enters login email "testuser_auto@test.com" and password "Test@1234"
    Then user should be logged in as "TestUser"
    And Logout link should be visible
    When user clicks on Logout link
    Then the login page should be displayed
    And Signup/Login link should be visible

