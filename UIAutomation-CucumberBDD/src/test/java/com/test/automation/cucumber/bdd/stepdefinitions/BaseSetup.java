package com.test.automation.cucumber.bdd.stepdefinitions;

import com.test.automation.cucumber.bdd.PageObjects;
import com.test.automation.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

import static com.test.automation.utils.CSVDataUtil.loadAccounts;
import static com.test.automation.utils.CommonUtil.getProperty;
import static com.test.automation.utils.CommonUtil.loadProperties;

public class BaseSetup extends PageObjects {

    private final DriverManager driverManager;

    public BaseSetup(DriverManager driverManager) {
        this.driverManager = driverManager;
    }

    @BeforeAll
    public static void suiteSetup() {
        loadProperties("src/test/resources/config.properties");
        String accountsCSV = getProperty("accountsCSV");
        loadAccounts(accountsCSV);
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        driverManager.initializeDriver();
    }

    @After
    public void afterScenario(Scenario scenario) {
        driverManager.stopDriver();
    }

    @AfterAll
    public static void suiteTearDown() {

    }
}
