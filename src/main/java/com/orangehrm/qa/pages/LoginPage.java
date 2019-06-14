package com.orangehrm.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	protected WebDriver driver;

	public static Logger log = Logger.getLogger(LoginPage.class.getName());

	// Initializing the Page Objects:
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		log.debug("Initializing the Login Page Objects");
	}
	
	

}
