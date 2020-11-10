package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement usernameEdit;

    @FindBy(id = "inputPassword")
    private WebElement passwordEdit;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(id = "logged-out-message")
    private WebElement loggedOutMessage;

    @FindBy(id = "invalid-credentials-message")
    private WebElement invalidCredMessage;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void loginUser(String username, String password){
        usernameEdit.sendKeys(username);
        passwordEdit.sendKeys(password);
        loginButton.click();
    }

    public boolean invalidCredentials(){
        return invalidCredMessage.isDisplayed();
    }

    public boolean loggedOut(){
        return loggedOutMessage.isDisplayed();
    }

}
