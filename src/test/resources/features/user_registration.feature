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
  Scenario: Verify signup form is accessible
    Then New User Signup text should be visible
    And Login to your account text should be visible

  @regression
  Scenario: Navigate to signup page with valid details
    When user enters signup name "TestAutoUser" and email "testauto_unique_12345@test.com"
    Then Enter Account Information text should be visible
