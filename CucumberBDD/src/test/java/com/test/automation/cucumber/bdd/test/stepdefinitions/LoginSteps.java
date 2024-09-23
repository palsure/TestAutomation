package com.test.automation.cucumber.bdd.test.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.test.automation.cucumber.bdd.utils.CSVDataUtil.getAccount;
import static com.test.automation.cucumber.bdd.utils.DriverManager.loadUrl;

@Slf4j
public class LoginSteps extends BaseTest {

    @Given("I am in login page")
    public void loginPage() {
        loadUrl();
    }

    @When("I login as a {} user")
    public void loginAsAUser(String userType) {
        Map<String, String> account = getAccount(userType);
        loginPage.login(account.get("userName"), account.get("password"));
    }

    @Then("I should see {string} message")
    public void iShouldSeeMessage(String expectedError) {
        loginPage.checkError(expectedError);
    }
}
