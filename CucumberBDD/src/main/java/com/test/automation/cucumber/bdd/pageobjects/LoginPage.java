package com.test.automation.cucumber.bdd.pageobjects;

import com.test.automation.cucumber.bdd.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginPage extends DriverManager {

    public LoginPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(id = "user-name")
    private WebElement userNameBox;

    @FindBy(css = "#password")
    private WebElement passwordBox;

    @FindBy(name = "login-button")
    private WebElement loginBtn;

    @FindBy(css = "[data-test='error'")
    private WebElement error;

    public void login(String userName, String password) {
        userNameBox.sendKeys(userName);
        passwordBox.sendKeys(password);
        loginBtn.click();
    }

    public void checkError(String expectedError) {
        String actualError = error.getText();
        assertTrue(actualError.contains(expectedError),
                "Check error displayed in login: '%s' has '%s'".formatted(actualError, expectedError));
    }
}
