package com.crm.qa.base;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

import com.crm.qa.utils.TestUtils;

public class Base {

	public static WebDriver driver;
	static Properties prop;
	TestUtils utils = new TestUtils();

	/*
	 * Initialze object Page Factory
	 */

	public Base() {
		PageFactory.initElements(driver, this);
	}

	@BeforeTest
	public void invokeWebriver() throws Exception {

		InputStream file = null;
		String propertyFileName = "config.properties";

		try {
			file = getClass().getClassLoader().getResourceAsStream(propertyFileName);

			prop = new Properties();
			prop.load(file);
			// Get property from dta.properties
			String browserName = prop.getProperty("browser");

			// Get data from maven commande using System.property
			// mvn test -Dbrowser=chrome

			// String browserName = System.getProperty("browser");

			if (browserName.equalsIgnoreCase("chrome")) {
				String path = System.getProperty("user.dir") + "/src/main//resources/chromedriver";
				System.out.println(path);
				System.setProperty("webdriver.chrome.driver", path);
				driver = new ChromeDriver();

			} else if (browserName.equalsIgnoreCase("firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/src/main/resources/geckodriver");
				driver = new FirefoxDriver();

			} else if (browserName.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "/src/main/resources/IEDriverServer.exe");
				driver = new InternetExplorerDriver();

			}

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(TestUtils.WAIT, TimeUnit.SECONDS);

			driver.get(prop.getProperty("url"));

		} catch (Exception e) {

			System.out.println("Driver initialization ABORT !!!!!" + "\n");
			throw e;

		} finally {
			if (file != null) {
				file.close();
			}
		}

	}

	/**
	 * 
	 * override some methods to use log4j2 and extentReport
	 * 
	 */

	public void sendKeys(WebElement e, String keysToSend, String msg) {

		System.out.println(msg);
		e.sendKeys(keysToSend);

	}

	public void click(WebElement e, String msg) {

		System.out.println(msg);
		e.click();

	}

	/*
	 *  Getter driver Method
	 */
	
	public WebDriver getDriver() {
		
		return driver;
	}
	
	
	
	
	
	
	
	/*
	 * Close the driver
	 */
	
	@AfterClass
	public void tearDown() {
		driver.close();
	}

}
