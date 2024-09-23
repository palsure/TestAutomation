package com.test.automation.cucumber.bdd.test.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventorySteps extends BaseTest {

    @Then("I should see inventory page")
    public void checkInventoryPage() {
        inventoryPage.checkTitle("Products");
    }


    @When("I add {string} products to cart")
    public void iAddProductsToCart(String product) {
        String[] products = product.split(",");
        inventoryPage.addProductsToCart(products);
    }
}
