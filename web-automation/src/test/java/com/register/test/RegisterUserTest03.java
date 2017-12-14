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

public class RegisterUserTest03 
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
	
			}
			else
			{
				throw new NoSuchElementException("Register url not working");
			}
			
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(sEmailAddress);
			driver.findElement(By.xpath(".//*[@id='password']")).sendKeys(sPassword);
			driver.findElement(By.xpath(".//*[@id='password-confirm']")).sendKeys(sConfirmPassword);
	
			Thread.sleep(1000);
	
			driver.findElement(By.xpath(".//*[@id='kc-form-wrapper']/form/div[4]/div[1]/input")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			Thread.sleep(2000);
			
			if(driver.getTitle().equalsIgnoreCase("Register with NHS"))
			{
				System.out.println("\nRemain on Register Page");
				String errorMessage = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div/div[1]/div/div")).getText();
				//System.out.println(errorMessage);
				//System.out.println(errorMessage.contains("Please enter a valid email address.Passwords do not match."));

				if(errorMessage.equalsIgnoreCase("Please enter a valid email address.") )
				{
					System.out.println("\nValidation message : Please enter a valid email address.");
					Reporter.log( "Validation message : Please enter a valid email address.", true );
				}
				else if(errorMessage.equalsIgnoreCase("Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.") )
				{
					System.out.println("\nValidation message : Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.");
					Reporter.log( "Validation message : Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.", true );
				}
				else if(errorMessage.equalsIgnoreCase("Your password can’t include more than 2 repeating characters.") )
				{
					System.out.println("\nValidation message : Your password can’t include more than 2 repeating characters.");
					Reporter.log( "Validation message : Your password can’t include more than 2 repeating characters.", true );

				}
				else if(errorMessage.equalsIgnoreCase("Passwords do not match.") )
				{
					System.out.println("\nValidation message : Passwords do not match.");
					Reporter.log( "Validation message : Passwords do not match.", true );

				}
				else if(errorMessage.equalsIgnoreCase("Your chosen password is too common. Chose a different password.") )
				{
					System.out.println("\nValidation message : Your chosen password is too common. Chose a different password.");
					Reporter.log( "Validation message : Your chosen password is too common. Chose a different password.", true );

				}
				else if((errorMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Passwords do not match.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Your password can’t include more than 2 repeating characters.").toLowerCase())))
				{
					System.out.println("\nValidation message : Please enter a valid email address. Passwords do not match. Your password can’t include more than 2 repeating characters.");
					Reporter.log( "Validation message : Please enter a valid email address. Passwords do not match. Your password can’t include more than 2 repeating characters.", true );
				}
				else if((errorMessage.toLowerCase().contains(("Passwords do not match.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Your password can’t include more than 2 repeating characters.").toLowerCase())))
				{
					System.out.println("\nValidation message : Passwords do not match. Your password can’t include more than 2 repeating characters.");
					Reporter.log( "Validation message : Passwords do not match. Your password can’t include more than 2 repeating characters.", true );
				}
				else if((errorMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Your password can’t include more than 2 repeating characters.").toLowerCase())))
				{
					System.out.println("\nValidation message : Please enter a valid email address. Your password can’t include more than 2 repeating characters.");
					Reporter.log( "Validation message : Please enter a valid email address. Your password can’t include more than 2 repeating characters.", true );

				}
				else if((errorMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Passwords do not match.").toLowerCase())))
				{
					System.out.println("\nValidation message : Please enter a valid email address. Passwords do not match.  Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.");
					Reporter.log( "Validation message : Please enter a valid email address. Passwords do not match.  Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.", true );

				}
				else if((errorMessage.toLowerCase().contains(("Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Passwords do not match.").toLowerCase())))
				{
					System.out.println("\nValidation message : Passwords do not match. Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.");
					Reporter.log( "Validation message : Passwords do not match. Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.", true );

				}
				else if((errorMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Passwords do not match.").toLowerCase())))
				{
					System.out.println("\nValidation message : Please enter a valid email address. Passwords do not match.");
					Reporter.log( "Validation message : Please enter a valid email address. Passwords do not match.", true );

				}
				else if((errorMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.").toLowerCase())))
				{
					System.out.println("\nValidation message : Please enter a valid email address.  Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.");
					Reporter.log( "Validation message : Please enter a valid email address. Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.", true );

				}
				
				else if((errorMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Your chosen password is too common. Chose a different password.").toLowerCase())))
				{
					System.out.println("\nValidation message : Please enter a valid email address. Your chosen password is too common. Chose a different password.");
					Reporter.log( "Validation message : Please enter a valid email address. Your chosen password is too common. Chose a different password.", true );

				}
				
				else
				{
					throw new NoSuchElementException("Email or password validation failed");
					
				}
			}
			else
			{
				throw new NoSuchElementException("Register url not working");
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
		 	ExcelUtility.setExcelFile(filename,"TestData");
	 
		 	sTestCaseName = this.toString();
	 
		  	// From above method we get long test case name including package and class name etc.
		 	
	 
		  	// The below method will refine your test case name, exactly the name use have used
	 
		  	sTestCaseName = ExcelUtility.getTestCaseName(this.toString());
	 
		    // Fetching the Test Case row number from the Test Data Sheet
	 
		    // Getting the Test Case name to get the TestCase row from the Test Data Excel sheet
	 
		 	//commented for all data -->iTestCaseRow = ExcelUtility.getRowContains(sTestCaseName,0);
		 	iTestCaseRow = ExcelUtility.getRowCount();
		 	
		    Object[][] testObjArray = ExcelUtility.getTableDataArray(filename,"TestData",iTestCaseRow);

		    //commented for all test data --> Object[][] testObjArray = ExcelUtility.getTableArray(filename,"TestData",iTestCaseRow);
	 
		    	return (testObjArray);
	 
		}
}
