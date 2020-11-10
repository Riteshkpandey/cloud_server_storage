package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    // There will always be only 1 of each at a time

    @FindBy(tagName = "h1")
    private WebElement statusText;

    @FindBy(tagName = "a")
    private WebElement continueLink;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getStatus() {
        return statusText.getText();
    }

    public WebElement getContinueLink() {
        return continueLink;
    }
}
