package com.orangehrm.qa.testcases;

import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.orangehrm.qa.pages.LoginPage;
import com.orangehrm.qa.testbase.TestBase;

public class LoginPageTest extends TestBase {

	public static Logger log = Logger.getLogger(LoginPageTest.class.getName());

	@BeforeMethod
	public void pageSetup() {
		loginPage = new LoginPage(driver);
	}

	@Test(priority = 0, description = "Verify LoginPage UI", groups = { "Smoke" })
	public void verifyLoginPageTitle() {

		TestBase.parentTest = TestBase.extent.createTest("Login Page Test");
		TestBase.parentTest.assignCategory("Smoke Test");
		TestBase.childTest = TestBase.parentTest.createNode("Verify Login Page");

		TestBase.childTest.log(Status.INFO, "Verifying LoginPage Title");

		assertTrue(driver.getTitle().contains("OrangeHRM"));

	}
	
	@Test(priority = 1, description = "Verify LoginPage UI", groups = { "Smoke" })
	public void verifyLoginPage() {

		TestBase.parentTest = TestBase.extent.createTest("Login Test");
		TestBase.parentTest.assignCategory("Smoke Test");
		TestBase.childTest = TestBase.parentTest.createNode("Verify Login Functionality");

		TestBase.childTest.log(Status.INFO, "Verifying Login functionality");

		assertTrue(driver.getTitle().contains("OrangeHRM"));

	}

}
