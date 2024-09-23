package com.test.automation.cucumber.bdd.test.stepdefinitions;

import com.test.automation.cucumber.bdd.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

import static com.test.automation.cucumber.bdd.utils.CSVDataUtil.loadAccounts;
import static com.test.automation.cucumber.bdd.utils.CommonUtil.getProperty;
import static com.test.automation.cucumber.bdd.utils.CommonUtil.loadProperties;

public class BaseSetup {

    private DriverManager driverManager;

    public BaseSetup(DriverManager webDriverUtil) {
        this.driverManager = webDriverUtil;
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
