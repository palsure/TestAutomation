package com.test.automation.pageobjects;

import com.test.automation.utils.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.testng.Assert.assertEquals;

public class CartPage extends DriverManager {

//    public CartPage() {
//        PageFactory.initElements(getDriver(), this);
//    }

    @FindBy(id = "checkout")
    private WebElement checkoutBtn;

    public void continueToCheckout() {
        checkoutBtn.click();
    }
}
