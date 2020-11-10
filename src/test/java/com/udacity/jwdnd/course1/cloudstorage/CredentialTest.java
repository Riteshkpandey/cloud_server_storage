package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjects.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private HomePage homePage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.registerNewUser("Sidak", "Pasricha", "sid", "pas");

        driver.get("http://localhost:" + port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser("sid", "pas");

        homePage = new HomePage(driver);
    }

    @AfterAll
    public static void afterAll() {
        driver.close();
    }

    private void addNewCredential(String url, String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Explicit wait to make tab click work
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        homePage.openCredentialsTab();
        wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialButton())).click();
        wait.until(ExpectedConditions.visibilityOf(homePage.getCredentialSubmit()));
        homePage.createNewCredential(url, username, password);

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
        homePage.openCredentialsTab();
        wait.until(ExpectedConditions.visibilityOf(homePage.getCredentialUrlText()));
    }

    @Test
    void addCredential() {
        addNewCredential("www.google.com", "user", "pass");

        assertEquals("www.google.com", homePage.getCredentialUrlTextValue());
        assertEquals("user", homePage.getCredentialUsernameTextValue());
        assertNotEquals("pass", homePage.getCredentialPasswordTextValue());
    }

    @Test
    public void deleteCredential(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        addNewCredential("url","user", "pass");
        homePage.deleteCredential();

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

        homePage.openCredentialsTab();
        assertThrows(Exception.class, () -> homePage.getCredentialUrlText().click());
    }

    @Test
    public void editCredential(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        addNewCredential("url","user", "pass");

        homePage.clickEditCredential();
        wait.until(ExpectedConditions.visibilityOf(homePage.getCredentialSubmit()));
        homePage.editCredential("newurl", "username", "password");

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

        homePage.openCredentialsTab();
        wait.until(ExpectedConditions.visibilityOf(homePage.getCredentialUrlText()));

        assertEquals("newurl", homePage.getCredentialUrlTextValue());
        assertEquals("username", homePage.getCredentialUsernameTextValue());
        assertNotEquals("password", homePage.getCredentialPasswordTextValue());
    }
}