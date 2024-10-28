package com.test.automation.pageobjects;

import com.test.automation.utils.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class CheckoutPage extends DriverManager {

//    public CheckoutPage() {
//        PageFactory.initElements(getDriver(), this);
//    }

    @FindBy(id = "first-name")
    private WebElement firstName;

    @FindBy(css = "input[data-test='lastName']")
    private WebElement lastName;

    @FindBy(xpath = "//input[@data-test='postalCode']")
    private WebElement zipCode;

    @FindBy(className = "submit-button")
    private WebElement continueBtn;

    @FindBy(name = "finish")
    private WebElement finishBtn;

    @FindBy(className = "title")
    private WebElement title;

    @FindBy(css = "[data-test='pony-express']")
    private WebElement completeImg;

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(className = "complete-text")
    private WebElement completeDesc;

    public void enterCustomerInfo(Map<String, String> data) {
        firstName.sendKeys(data.get("firstName"));
        lastName.sendKeys(data.get("lastName"));
        zipCode.sendKeys(data.get("zipCode"));
        continueBtn.click();
    }

    public void submitOrder() {
        finishBtn.click();
    }

    public void checkOrderConfirmation() {
        assertEquals(title.getText(), "Checkout: Complete!");
        assertEquals(completeImg.getAttribute("alt"), "Pony Express");
        assertEquals(completeHeader.getText(), "Thank you for your order!");
        assertEquals(completeDesc.getText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
    }

}
