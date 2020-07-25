package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.crm.qa.base.Base;

public class HomePage extends Base {
	
	
	@FindBy(xpath="//a[contains(text(),'Log In')]") 
	@CacheLookup
	private WebElement  loginLink;
	
	
	
	/*
	 * Click on login link
	 */
	
	public LoginPage clickOnLoginLink(String msg) {
		
		click(loginLink, msg);
		
		return new LoginPage();
	}

}
