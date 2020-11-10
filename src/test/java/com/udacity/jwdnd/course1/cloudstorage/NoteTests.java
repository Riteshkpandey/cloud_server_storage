package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjects.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private HomePage homePage;

    @BeforeAll
    public static void beforeAll(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void beforeEach(){
        driver.get("http://localhost:"+port+"/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.registerNewUser("Sidak", "Pasricha", "sid", "pas");

        driver.get("http://localhost:"+port+"/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser("sid", "pas");

        homePage = new HomePage(driver);
    }

    @AfterAll
    public static void afterAll(){
        driver.close();
    }

    private void addNewNote(String title, String desc){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Explicit wait to make tab click work
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        homePage.openNotesTab();
        wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddNoteButton())).click();
        wait.until(ExpectedConditions.visibilityOf(homePage.getNoteSubmit()));
        homePage.createNewNote(title, desc);

        ResultPage resultPage = new ResultPage(driver);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resultPage.getContinueLink().click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        homePage.openNotesTab();
        wait.until(ExpectedConditions.visibilityOf(homePage.getNoteTitleText()));
    }

    @Test
    public void addNote(){

        addNewNote("Title", "Desc");

        assertEquals("Title", homePage.getNoteTitleTextValue());
        assertEquals("Desc", homePage.getNoteDescriptionTextValue());
    }

    @Test
    public void deleteNote(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        addNewNote("Title", "Description");
        homePage.deleteNote();

        ResultPage resultPage = new ResultPage(driver);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("Success", resultPage.getStatus());
        resultPage.getContinueLink().click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        homePage.openNotesTab();
        assertThrows(Exception.class, () -> homePage.getNoteTitleText().click());
    }

    @Test
    public void editNote(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        addNewNote("Title", "Description");

        homePage.clickEditNote();
        wait.until(ExpectedConditions.visibilityOf(homePage.getNoteSubmit()));
        homePage.editNote("NewTitle", "Desc");

        ResultPage resultPage = new ResultPage(driver);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("Success", resultPage.getStatus());
        resultPage.getContinueLink().click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        homePage.openNotesTab();
        wait.until(ExpectedConditions.visibilityOf(homePage.getNoteTitleText()));

        assertEquals("NewTitle", homePage.getNoteTitleTextValue());
        assertEquals("Desc", homePage.getNoteDescriptionTextValue());
    }
}
