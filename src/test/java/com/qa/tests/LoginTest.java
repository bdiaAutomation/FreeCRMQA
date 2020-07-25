package com.qa.tests;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.crm.qa.base.Base;
import com.crm.qa.pages.BackOfficePage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

public class LoginTest extends Base {

	HomePage homePage;
	LoginPage loginPage;
	BackOfficePage backOfficePage;

	@BeforeMethod
	public void beforeMethod(Method m) throws InterruptedException {
		homePage = new HomePage();
		Thread.sleep(7000);

		loginPage = homePage.clickOnLoginLink("Home Page click on login Link");

		System.out.println("\n" + " ******** Starting test: " + m.getName() + " ************" + "\n");

	}

	@Test
	public void successFullLogin() {

		SoftAssert soft = new SoftAssert();

		String actualLoginURL = loginPage.getURLOnLoginPage();

		String expectedLoginURL = "https://ui.freecrm.com/";

		soft.assertEquals(actualLoginURL, expectedLoginURL);

		loginPage.enterEmail("bdia.sne@gmail.com", "Entering email : bdia.sne@gmail.com");
		loginPage.enterPassword("Bamboo87", "Sending password : Bamboo87");
		backOfficePage = loginPage.pressLoginButton("Login Button pressed");
		
		String actualbackOfficeURL = backOfficePage.getBackOfficePageUrl();
		
		
		soft.assertEquals(actualbackOfficeURL, "https://ui.freecrm.com/");
		
		soft.assertAll();

	}

}
