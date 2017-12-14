
package com.register.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.register.config.Constant;
import com.register.utility.ExcelUtility;
import com.register.utility.SwingProgressBar;

public class AccountLockTest 
{
	  public WebDriver driver;
	  private String sTestCaseName;
	  private int iTestCaseRow;
	 
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
	  
	  @Test(dataProvider = "Authentication")
	  public void accountLockPasswordWrongAttempt(String sEmailAddress, String sPassword, String sConfirmPassword) throws Exception
	  {
		
			System.out.println("\nUser emailid: "+ Constant.username_Patient);
					
			driver.get(Constant.url_nhsAccountCreation);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Thread.sleep(3000);
			Reporter.log( "Opening url: "+ Constant.url_nhsAccountCreation, true );

			
			
			if(driver.getTitle().equalsIgnoreCase("Log in to NHS"))
			{
				System.out.println("\nLogin Page");
				Reporter.log( "Navigated to login Page", true );

			}
			else
			{
				throw new NoSuchElementException("Login url is not working");
			}
			
			driver.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).clear();
			driver.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).clear();
			
			driver.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).sendKeys(sEmailAddress);
			driver.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).sendKeys(sPassword);
			driver.findElement(By.xpath(Constant.xpath_loginPage_loginButton)).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			
			
			if(driver.getTitle().equalsIgnoreCase("Log in to NHS"))
			{
				
				String invalidInputMessage = driver.findElement(By.xpath(Constant.xpath_loginPage_validationMessageBanner)).getText();
				if(invalidInputMessage.equalsIgnoreCase("Your email or password is incorrect."))
				{
					if(sPassword.equals(Constant.password_Patient))
					{
						Reporter.log("User Valid Credentials for login:- email: "+sEmailAddress+" ------ password: "+sPassword, true );
						Reporter.log("Account locked after 3 attempts and unable to login with valid credentials", true );
					}
					else
					{
						Reporter.log( "User Invalid Credentials for login:- email: "+sEmailAddress+" ------ password: "+sPassword, true );
						Reporter.log("Validation message displayed: "+ invalidInputMessage, true );

					}	
				}
				else
				{
					throw new NoSuchElementException("Validation message not displayed");
				}
			}
			else
			{
				throw new NoSuchElementException("\nNHS Account lock  is not working");
			}

	  }
	  @DataProvider
	  public Object[][] Authentication() throws Exception
	  {
	 
		  	
		    // Setting up the Test Data Excel file
		  	String filename = this.getClass().getResource("/CID_TestAutomation_User_Credentials_Data.xlsx").getFile();
		 	ExcelUtility.setExcelFile(filename,"AccountLockTemp");
	 
		 	sTestCaseName = this.toString();
	 
		  	// From above method we get long test case name including package and class name etc.
		 	
	 
		  	// The below method will refine your test case name, exactly the name use have used
	 
		  	sTestCaseName = ExcelUtility.getTestCaseName(this.toString());
	 
		    // Fetching the Test Case row number from the Test Data Sheet
	 
		    // Getting the Test Case name to get the TestCase row from the Test Data Excel sheet
	 
		 	//commented for all data -->iTestCaseRow = ExcelUtility.getRowContains(sTestCaseName,0);
		 	iTestCaseRow = ExcelUtility.getRowCount();
		 	
		    Object[][] testObjArray = ExcelUtility.getTableDataArray(filename,"AccountLockTemp",iTestCaseRow);

		    //commented for all test data --> Object[][] testObjArray = ExcelUtility.getTableArray(filename,"TestData",iTestCaseRow);
	 
		    	return (testObjArray);
	 
		}
	  
}
