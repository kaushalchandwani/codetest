package com.register.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.register.config.Constant;
import com.register.utility.ExcelUtility;

public class RegisterUserTest04 
{
	private String sTestCaseName;
	 
	private int iTestCaseRow;
	public WebDriver driver;
	
	  @Test(dataProvider = "Authentication")
	  public void userRegistration(String sEmailAddress, String sPassword, String sConfirmPassword) throws Exception 
	  {
			
			System.out.println("\nEmailid: "+ sEmailAddress);
			System.out.println("\nPassword: "+ sPassword);
			System.out.println("\nConfirm Password: "+ sConfirmPassword);

					
			driver.get("https://blue.testlab.nhs.uk/cicauth/realms/NHS/account/");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Thread.sleep(2000);
			
			
			if(driver.getTitle().equalsIgnoreCase("Log in to NHS"))
			{
				System.out.println("\nLogin Page");
				Reporter.log( "Navigated to login Page", true );

			}
			else
			{
				throw new NoSuchElementException("Login url is not working");
			}
			
			
			
			driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div/div[2]/div/a/label")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Thread.sleep(2000);
			
			

			
			if(driver.getTitle().equalsIgnoreCase("Register with NHS"))
			{
				System.out.println("\nRegister Page");
				Reporter.log( "Navigated to Register Page", true );

	
			}
			else
			{
				throw new NoSuchElementException("Register url not working");
			}
			
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(sEmailAddress);
			driver.findElement(By.xpath(".//*[@id='password']")).sendKeys(sPassword);
			driver.findElement(By.xpath(".//*[@id='password-confirm']")).sendKeys(sConfirmPassword);
			Reporter.log( "User Credentials for registration:- \nEmail: "+sEmailAddress+"\n ------ Password: "+sPassword, true );

			Thread.sleep(1000);
	
			driver.findElement(By.xpath(".//*[@id='kc-form-wrapper']/form/div[4]/div[1]/input")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			Thread.sleep(2000);
			
			//check page title and then check confirm message
			if(driver.getTitle().equalsIgnoreCase("Confirm your email"))
			{
				System.out.println("\nConfirm email Page");
				Reporter.log( "Navigated to Confirm email Page", true );

				String confirmMailMessage = driver.findElement(By.xpath("html/body/div[1]/div/div[1]/div/div/div")).getText();
				String confirmMessage = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div/div[1]/div/div")).getText();
				
				if(confirmMailMessage.equalsIgnoreCase("Confirm your email") && confirmMessage.equalsIgnoreCase("We need to verify your email address. Please check your email."))
				{
					System.out.println("\nConfirm email Message displayed");
					Reporter.log( "Verification email sent to user", true );

				}
				else
				{
					throw new NoSuchElementException("Confirm email not send");
				}
	
			}
			else
			{
				throw new NoSuchElementException("Confirm email url not working");
			}
		
	  }
	  
	  @BeforeMethod
	  public void beforeMethod() 
	  {
		
				
		System.out.println("\nOpening webdriver \n");
		
		if(Constant.environmentVariable.equalsIgnoreCase("local"))
	  	{
			System.setProperty("webdriver.chrome.driver", "exe/chromedriver.exe"); //chromedriver.exe set property path
			driver = new ChromeDriver();
	  	}
	  	else
	  	{
	  		System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driver = new FirefoxDriver(capabilities);
	  	}
		
	  }
	
	  
	  @AfterMethod
	  public void afterMethod() 
	  {
		System.out.println("\nClosing webdriver......");
		driver.get("about:config");
		//driver.quit();
		driver.close();
	  }
	  
	  @DataProvider
	  
	  public Object[][] Authentication() throws Exception
	  {
	 
		    // Setting up the Test Data Excel file
		  	String filename = this.getClass().getResource("/CID_TestAutomation_User_Credentials_Data.xlsx").getFile();
		 	ExcelUtility.setExcelFile(filename,"ValidTestData");
	 
		 	sTestCaseName = this.toString();
	 
		  	// From above method we get long test case name including package and class name etc.
		 	
	 
		  	// The below method will refine your test case name, exactly the name use have used
	 
		  	sTestCaseName = ExcelUtility.getTestCaseName(this.toString());
	 
		    // Fetching the Test Case row number from the Test Data Sheet
	 
		    // Getting the Test Case name to get the TestCase row from the Test Data Excel sheet
	 
		 	//commented for all data -->iTestCaseRow = ExcelUtility.getRowContains(sTestCaseName,0);
		 	iTestCaseRow = ExcelUtility.getRowCount();
		 	
		    Object[][] testObjArray = ExcelUtility.getTableDataArray(filename,"ValidTestData",iTestCaseRow);

		    //commented for all test data --> Object[][] testObjArray = ExcelUtility.getTableArray(filename,"TestData",iTestCaseRow);
	 
		    	return (testObjArray);
	 
		}
}
