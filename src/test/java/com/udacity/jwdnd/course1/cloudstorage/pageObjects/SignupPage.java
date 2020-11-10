package com.udacity.jwdnd.course1.cloudstorage.pageObjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameEdit;

    @FindBy(id = "inputLastName")
    private WebElement lastNameEdit;

    @FindBy(id = "inputUsername")
    private WebElement usernameEdit;

    @FindBy(id = "inputPassword")
    private WebElement passwordEdit;

    @FindBy(id = "signUpButton")
    private WebElement submitButton;

    @FindBy(id = "signup-success")
    private WebElement signupSuccess;

    @FindBy(id = "signup-error")
    private WebElement signupFail;

    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void registerNewUser(String firstName, String lastName, String username, String password){
        firstNameEdit.sendKeys(firstName);
        lastNameEdit.sendKeys(lastName);
        usernameEdit.sendKeys(username);
        passwordEdit.sendKeys(password);
        submitButton.click();
    }

    public boolean isSuccessful(){
        return signupSuccess.isDisplayed();
    }

    public boolean isFail(){
        return signupFail.isDisplayed();
    }
}
