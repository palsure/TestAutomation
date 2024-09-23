package com.test.automation.cucumber.bdd.test.stepdefinitions;

import com.test.automation.cucumber.bdd.pageobjects.CartPage;
import com.test.automation.cucumber.bdd.pageobjects.CheckoutPage;
import com.test.automation.cucumber.bdd.pageobjects.InventoryPage;
import com.test.automation.cucumber.bdd.pageobjects.LoginPage;
import com.test.automation.cucumber.bdd.utils.DriverManager;
import lombok.extern.slf4j.Slf4j;

import static com.test.automation.cucumber.bdd.utils.CommonUtil.getProperty;

@Slf4j
public class BaseTest {

    protected LoginPage loginPage = new LoginPage();
    protected InventoryPage inventoryPage = new InventoryPage();
    protected CartPage cartPage = new CartPage();
    protected CheckoutPage checkoutPage = new CheckoutPage();

}
