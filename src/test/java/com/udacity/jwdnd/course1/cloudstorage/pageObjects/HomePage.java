package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"logoutDiv\"]//button")
    private WebElement logOutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    public void logOut(){
        logOutButton.click();
    }

    public WebElement getNotesTab() {
        return notesTab;
    }

    public WebElement getCredentialsTab() {
        return credentialsTab;
    }

    public void openNotesTab(){
        notesTab.click();
    }

    public void openCredentialsTab(){
        credentialsTab.click();
    }

    // Notes Tab

    @FindBy(id = "add-note")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "save-note")
    private WebElement noteSubmit;

    @FindBy(xpath = "//*[@id=\"nav-notes\"]//tbody/tr/td/button")
    private WebElement editNoteButton;

    @FindBy(xpath = "//*[@id=\"nav-notes\"]//tbody/tr/td/a")
    private WebElement deleteNoteButton;

    @FindBy(xpath = "//*[@id=\"nav-notes\"]//tbody/tr/th")
    private WebElement noteTitleText;

    @FindBy(xpath = "//*[@id=\"nav-notes\"]//tbody/tr/td[2]")
    private WebElement noteDescriptionText;

    public WebElement getAddNoteButton() {
        return addNoteButton;
    }

    public WebElement getNoteSubmit() {
        return noteSubmit;
    }

    public WebElement getNoteTitleText() {
        return noteTitleText;
    }

    public void createNewNote(String title, String description){
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        noteSubmit.click();
    }

    public void editNote(String title, String description){
        noteTitle.clear();
        noteDescription.clear();
        createNewNote(title, description);

    }

    public String getNoteTitleTextValue() {
        return noteTitleText.getText();
    }

    public String getNoteDescriptionTextValue() {
        return noteDescriptionText.getText();
    }

    public void clickEditNote(){
        editNoteButton.click();
    }

    public void deleteNote(){
        deleteNoteButton.click();
    }

    // Credentials Tab

    @FindBy(id = "add-credential")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "save-credential")
    private WebElement credentialSubmit;

    @FindBy(xpath = "//*[@id=\"nav-credentials\"]//tbody/tr/td/button")
    private WebElement editCredentialButton;

    @FindBy(xpath = "//*[@id=\"nav-credentials\"]//tbody/tr/td/a")
    private WebElement deleteCredentialButton;

    @FindBy(xpath = "//*[@id=\"nav-credentials\"]//tbody/tr/th")
    private WebElement credentialUrlText;

    @FindBy(xpath = "//*[@id=\"nav-credentials\"]//tbody/tr/td[2]")
    private WebElement credentialUsernameText;

    @FindBy(xpath = "//*[@id=\"nav-credentials\"]//tbody/tr/td[3]")
    private WebElement credentialPasswordText;

    public WebElement getAddCredentialButton() {
        return addCredentialButton;
    }

    public WebElement getCredentialSubmit() {
        return credentialSubmit;
    }

    public WebElement getCredentialUrlText() {
        return credentialUrlText;
    }

    public void createNewCredential(String url, String username, String password){
        credentialUrl.sendKeys(url);
        credentialUsername.sendKeys(username);
        credentialPassword.sendKeys(password);
        credentialSubmit.click();
    }

    public void editCredential(String url, String username, String password){
        credentialUrl.clear();
        credentialUsername.clear();
        credentialPassword.clear();
        createNewCredential(url, username, password);
    }

    public String getCredentialUrlTextValue(){
        return credentialUrlText.getText();
    }

    public String getCredentialUsernameTextValue(){
        return credentialUsernameText.getText();
    }

    public String getCredentialPasswordTextValue(){
        return credentialPasswordText.getText();
    }

    public void clickEditCredential(){
        editCredentialButton.click();
    }

    public void deleteCredential(){
        deleteCredentialButton.click();
    }
}
