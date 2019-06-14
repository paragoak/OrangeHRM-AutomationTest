package com.orangehrm.qa.testbase;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.orangehrm.qa.pages.LoginPage;
import com.orangehrm.qa.testlisteners.WebEventListener;
import com.orangehrm.qa.utilities.FileManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class TestBase extends FileManager {

	public static WebDriver driver;
	public WebDriverWait wait;

	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;

	public static ExtentTest parentTest;
	public static ExtentTest childTest;
	public static ExtentTest test;

//	public SoftAssert sa = new SoftAssert();

	public static Logger log = Logger.getLogger(TestBase.class);

	public LoginPage loginPage = new LoginPage(driver);

//	public ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);

	@Parameters({ "browserType", "appURL" })
	@BeforeClass
	public WebDriver setup(String browserName, String applicationURL) {
		if (browserName.equalsIgnoreCase("Chrome")) {
			// To Load Chrome driver Instance.
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			driver = new ChromeDriver(chromeOptions);
			chromeOptions.addArguments("--headless");
			 WebDriver driver = new ChromeDriver();
			//System.out.println("Chrome browser loaded");
			log.debug("++++++ Chrome browser loaded ++++++");
			// et.log(LogStatus.INFO, "Chrome browser loaded");
		} else if (browserName.equalsIgnoreCase("FireFox")) {
			// To Load FireFox driver Instance.
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			//System.out.println("FireFox browser loaded");
			log.debug("++++++ FireFox browser loaded ++++++");
			// et.log(LogStatus.INFO, "FireFox browser loaded");
		} else if (browserName.equalsIgnoreCase("Edge")) {
			// To Load Edge driver Instance.
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			//System.out.println("Edge browser loaded");
			log.debug("++++++ Edge browser loaded ++++++");
			// et.log(LogStatus.INFO, "Edge browser loaded");
		} else if (browserName.equalsIgnoreCase("Safari")) {
			// To Load Safari driver Instance.
			driver = new SafariDriver();
			//System.out.println("Safari browser loaded");
			log.debug("++++++ Safari browser loaded ++++++");
			// et.log(LogStatus.INFO, "Safari browser loaded");
		}

		if (driver != null) {
			
			driver.get(applicationURL);
			log.debug("Getting Application URL: " + applicationURL);

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

			e_driver = new EventFiringWebDriver(driver);
			eventListener = new WebEventListener();

			e_driver.register(eventListener);

			driver = e_driver;

			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			
			/*
			 * waitForLoad(driver);
			 * 
			 * driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
			 * 
			 * driver.manage().timeouts().pageLoadTimeout(prjprop.getProperty(
			 * "PAGE_LOAD_TIMEOUT"), TimeUnit.SECONDS);
			 * log.debug("Page load timeout is set to: " +
			 * prjprop.getProperty("PAGE_LOAD_TIMEOUT"));
			 * 
			 * driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			 * log.debug("Implicit Wait time is set to: " +
			 * prjprop.getProperty("IMPLICIT_WAIT"));
			 */

		} else {
			driver.close();
			log.debug("Browser closed");
			// et.log(LogStatus.INFO, "Clicking on : " + locator);
			// Null browser Instance when close.
			driver = null;
			log.debug("Browser set as NULL");
		}
		return driver;

	}

	@BeforeSuite

	public void extentSetUp() {

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + prjprop.getProperty("REPORT_LOCATION"));
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Host Name", "Parag");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User Name", "Parag Oak");

		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("HMS Test Automation Report");
		htmlReporter.config().setReportName("Test Execution Report ");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
	}

	@AfterClass
	public void tearDown() {

		// assertTrue(loginPage.userlogout(), "User still login");
		//childTest.log(Status.INFO, "User logged out.");
		driver.quit();
		childTest.log(Status.INFO, "Browser quit successfully");
		log.debug("Browser quit successfully");
		// et.log(LogStatus.INFO, "Clicking on : " + locator);

	}

	public static String captureScreenshot(WebDriver driver, String screenShotName) throws Exception {

		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver);

		String dest = System.getProperty("user.dir") + prjprop.getProperty("SCREEN_SHOT_PATH") + screenShotName
				+ System.currentTimeMillis() + ".png";

		System.out.println("Screenshot captured at location: " + dest);
		log.debug(" ########### Screenshot captured at location: ########### " + dest);

		ImageIO.write(screenshot.getImage(), "PNG", new File(dest));

		return dest;

	}

	public boolean isElementPresent(WebElement we) {
		try {
			we.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void highLighterMethod(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
	}

}
