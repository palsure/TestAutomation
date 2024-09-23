@login @regression
Feature: Login

  As a customer, I should be able to successfully login with valid credentials, and see error for invalid credentials.

  Background:
    Given I am in login page

  @smoke @positive
  Scenario Outline: Login Successfully as <userType> user
    When I login as a <userType> user
    Then I should see inventory page
    @good
    Examples:
      | userType |
      | standard |
      | visual   |
    @bad
    Examples:
      | userType    |
      | problem     |
      | performance |
      | error       |

  @negative
  Scenario Outline: Login as <userType> user and check the error message
    When I login as a <userType> user
    Then I should see '<error>' message

    Examples:
      | userType      | error                                       |
      | locked        | Sorry, this user has been locked out        |
      | empty         | Username is required                        |
      | passwordEmpty | Password is required                        |
      | invalid       | Username and password do not match any user |