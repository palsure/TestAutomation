package com.test.automation.testng.test;

import com.test.automation.utils.dataprovider.JsonDataProvider;
import com.test.automation.utils.dataprovider.model.TestData;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static com.test.automation.testng.DataConstants.LOGIN;
import static com.test.automation.testng.DataConstants.PRODUCTS;
import static com.test.automation.testng.DataConstants.REGRESSION;
import static com.test.automation.testng.DataConstants.SMOKE;
import static com.test.automation.utils.Constants.JSON_DATA_PROVIDER;

public class LoginTest extends BaseSetup {

    @Test(groups = {SMOKE, REGRESSION, LOGIN},
            dataProvider = JSON_DATA_PROVIDER, dataProviderClass = JsonDataProvider.class)
    @Feature(LOGIN)
    public void loginSuccessTest(TestData data) {
        loginPage.login(data);
        inventoryPage.checkTitle(PRODUCTS);
    }

    @Test(groups = {SMOKE, REGRESSION, LOGIN},
            dataProvider = JSON_DATA_PROVIDER, dataProviderClass = JsonDataProvider.class)
    @Feature(LOGIN)
    public void loginFailureTest(TestData data) {
        loginPage.login(data);
        loginPage.checkError(data.getError());
    }

}
