package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjects.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginSignupTests {

	@LocalServerPort
	private Integer port;

	private static WebDriver driver;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeAll
	public static void beforeAll(){
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll(){
		driver.close();
	}

	@Test
	public void unauthorizedAccessCheckHome(){
		driver.get("http://localhost:"+port+"/home");
		// If login page, then its assumed that login page is accessible to unauthorized
		// No need to create extra test for it
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void unauthorizedAccessCheckSignup(){
		driver.get("http://localhost:"+port+"/signup");
		assertEquals("Sign Up", driver.getTitle());
	}


	@Test
	public void completeSignupTest() {
		driver.get("http://localhost:"+port+"/signup");
		signupPage = new SignupPage(driver);
		signupPage.registerNewUser("Sidak", "Pasricha", "sid", "pas");
		assertTrue(signupPage.isSuccessful());

		driver.get("http://localhost:"+port+"/login");
		loginPage = new LoginPage(driver);
		loginPage.loginUser("sid", "pas");
		assertEquals("Home", driver.getTitle());

		homePage = new HomePage(driver);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		homePage.logOut();
		loginPage = new LoginPage(driver);
		assertTrue(loginPage.loggedOut());
	}


}
