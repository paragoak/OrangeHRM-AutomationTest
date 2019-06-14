package com.orangehrm.qa.testlisteners;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.orangehrm.qa.testbase.TestBase;

public class TestListener implements ITestListener {
	
	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getName() + " started ***");
	}

	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		// ExtentTestManager.endTest();
		// ExtentManager.getInstance().flush();
		TestBase.extent.flush();
	}

	public void onTestStart(ITestResult result) {
		System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
		// ExtentTestManager.startTest(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
		// ExtentTestManager.getTest().log(Status.PASS, "Test passed");
		TestBase.childTest.log(Status.PASS,MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));

	}

	public void onTestFailure(ITestResult result) {
		System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		// ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
		try {

			String screenShotPath = TestBase.captureScreenshot(TestBase.driver, "screenShotName");
			TestBase.childTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
			TestBase.childTest.fail(result.getThrowable());
			TestBase.childTest.fail("Snapshot below : " + TestBase.childTest.addScreenCaptureFromPath(screenShotPath));
		} catch (Exception e) {
			System.out.println("=========== Error while taking Screenshot : ===========");
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
		// ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
		TestBase.childTest.log(Status.SKIP,
				MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
		TestBase.childTest.skip(result.getThrowable());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}
}
