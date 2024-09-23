package com.test.automation.cucumber.bdd.test.stepdefinitions;

import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartSteps extends BaseTest {

    @And("I proceed to checkout")
    public void proceedToCheckout() {
        cartPage.continueToCheckout();
    }

}
