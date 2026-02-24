@regression @logout
Feature: User Logout
  As a logged-in user
  I want to log out of my account
  So that my session is securely ended

  @smoke @sanity
  Scenario: Verify login page redirects after invalid login attempt
    Given user is on the home page
    When user clicks on Signup/Login link
    Then the login page should be displayed
    And Login to your account text should be visible
    When user enters login email "invalid_logout@test.com" and password "wrongpass"
    Then login error message should be displayed
    And Signup/Login link should be visible
