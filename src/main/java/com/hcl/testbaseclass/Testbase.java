package com.hcl.testbaseclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Testbase {
	
	public static Properties repo;
	public static WebDriver driver;
	public static File fl;
	public static FileInputStream fis;
	public String browserName;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public Testbase()
	{
		loadpropertiesfiles();
	}
	
	
	static 
	{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir") + "\\src\\test\\java\\reports\\test" + formater.format(calendar.getTime()) + ".html", false);
	}
	
	public void initialize()
	{
		loadpropertiesfiles();
		selectBrowser();
		
	}

	public static void loadpropertiesfiles() {
		try {
			repo = new Properties();
			fl = new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\hcl\\config\\config.properties");
			fis = new FileInputStream(fl);
			repo.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void selectBrowser()
	{
		browserName=repo.getProperty("browsername");
		if(browserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"\\browserDriver\\geckodriver.exe");
			driver=new FirefoxDriver();		
		}
		else if(browserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +"\\browserDriver\\chromedriver.exe");
			driver=new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") +"\\browserDriver\\MicrosoftWebDriver.exe");
			driver=new EdgeDriver();
		}
	}
	
	public void getresult(ITestResult result)
	{
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + " test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, result.getName() + " test is failed, Reason: - " + result.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}
	
	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			//String reportDirectory = "C:\\Users\\Manoj Kumar Rana\\workspace\\ExtentreportProj\\Screenshot\\";
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "\\Screenshot\\";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}
	
	@BeforeMethod
	public void  beforemethod(Method result)
	{
		test=extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName()+"test started");
	}
	
	@AfterMethod
	public void aftermethod(ITestResult result)
	{
		getresult(result);
	}
	
	@AfterClass(alwaysRun=true)
	public void endtest()
	{
		extent.endTest(test);
		extent.flush();
		driver.close();
	}

	public void initilizeallelements()
	{
		PageFactory.initElements(driver, this);
	}
}
