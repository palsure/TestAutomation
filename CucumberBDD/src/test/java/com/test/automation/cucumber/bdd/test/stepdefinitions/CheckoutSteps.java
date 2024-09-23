package com.test.automation.cucumber.bdd.test.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.test.automation.cucumber.bdd.utils.DriverManager.getScreenshot;

@Slf4j
public class CheckoutSteps extends BaseTest {

    @And("I enter customer information")
    public void enterCustomerInfo() {
        Map<String, String> data = Map.of("firstName", "test", "lastName", "test", "zipCode", "19000");
        checkoutPage.enterCustomerInfo(data);
    }


    @And("I review & submit the order")
    public void iReviewSubmitTheOrder() {
        checkoutPage.submitOrder();
    }

    @Then("I should see order confirmation")
    public void iShouldSeeOrderConfirmation() {
        checkoutPage.checkOrderConfirmation();
        getScreenshot();
    }
}
