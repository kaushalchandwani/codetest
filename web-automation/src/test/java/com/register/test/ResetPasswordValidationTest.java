package com.register.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class ResetPasswordValidationTest 
{
	private String sTestCaseName;
	private String currentPageHandle = "";
	private int iTestCaseRow;
	public static WebDriver driverEmail;
	
	  @Test(dataProvider = "Authentication")
	  public void resetPasswordEmailValidatePassword(String sEmailAddress, String sPassword, String sConfirmPassword) throws Exception 
	  {
			
			
		  	boolean clickVerifyEmail=false;
			boolean emailSender = false;
			boolean emailReceiver =false;
			boolean emailSubject = false;
			boolean emailConfirmationStatus = false;

			String verifyPageHeader="";
			String resetPasswordMessage01="";
			String verifyEmailVerificationLink="";
			String verifyPageMessage02="";
			String url_mailcatcher = "";
		  	
		  	if(Constant.environmentVariable.equalsIgnoreCase("local"))
		  	{
		  		url_mailcatcher = Constant.url__mailCatcher_local;
		  	}
		  	else
		  	{
		  		url_mailcatcher = Constant.url__mailCatcher_remote;
		  	}
	  		driverEmail.get(url_mailcatcher);

		  	driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  	driverEmail.manage().window().maximize();
		  	Thread.sleep(2000);
			
			
			if(driverEmail.getTitle().matches("MailCatcher(.*)"))
			{
				System.out.println("\nMailCatcher Application open.....");
				Reporter.log( "MailCcatcher application open.....", true );

			}
			else
			{
				throw new NoSuchElementException("MailCatcher application unable to load.....");
			}
			
			//.//*[@id='messages']/table/tbody/tr[1]/td[1]
			
			List<WebElement> rows =   (List<WebElement>) driverEmail.findElements(By.xpath(Constant.xpath_mailcatcherTable + "/tr"));
			int countRows = rows.size();
			//System.out.println("\nROW COUNT : "+countRows);
			
			List<WebElement> columns = driverEmail.findElements(By.xpath(Constant.xpath_mailcatcherTable + "/tr[1]/td"));
			int countColumns = columns.size();
			//System.out.println("\nCOL COUNT : "+countColumns);
			
			for (int i=1;i<countRows;i++)
			{
				//int index
				clickVerifyEmail=false;
				emailSender = false;
				emailReceiver =false;
				emailSubject = false;
				for (int j=1;j<=countColumns;j++)
				{
					String xpathVariable =Constant.xpath_mailcatcherTable + "/tr[" + i + "]/td["+ j +"]";
					String sColumnValue="";
					boolean elementStatus = com.register.utility.CheckElement.existsElement(xpathVariable,driverEmail);
					if(elementStatus)
					{
						sColumnValue= driverEmail.findElement(By.xpath(xpathVariable)).getText();
						//System.out.println("\nColumn value: "+ sColumnValue);
						if(sColumnValue.matches("<citizen.identity1@gmail.com>"))
						{
							//System.out.println("\nEmail sender id: "+ sColumnValue);
							emailSender = true;
						}
						
							
						if(emailSender && sColumnValue.matches("<cid.testuser1@gmail.com>"))
						{
							//System.out.println("\nEmail receipient id: "+ sColumnValue);
							emailReceiver=true;
						    
						}
						if(emailReceiver && sColumnValue.matches("Reset password"))
						{
							//System.out.println("\nSubject: "+ sColumnValue);
							emailSubject =true;
						}
						if(emailSubject)
						{
							driverEmail.findElement(By.xpath(Constant.xpath_mailcatcherTable + "/tr[" + i + "]")).click();
						  	driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

							Thread.sleep(2000);

							clickVerifyEmail = true;
							break;
						}
						
					}
					
				}
				if(clickVerifyEmail)
				{
					break;
				}
				
			}
			
			if(clickVerifyEmail)
			{
				//li[1] for html and li[2] for plain text --- two diff tabs 
				driverEmail.findElement(By.xpath(Constant.xpath_mailcatcherEmailHtmlTab)).click();
				driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				Thread.sleep(1000);
				
				driverEmail.switchTo().defaultContent();
				driverEmail.switchTo().frame(driverEmail.findElement(By.tagName(Constant.xpath_mailcatcheriframe)));

				//verifyEmailVerificationLink= driverEmail.findElement(By.xpath("xhtml:html/xhtml:body/xhtml:a")).getText();
				//System.out.println("\nVerify Email Link: " + verifyEmailVerificationLink);

				resetPasswordMessage01= driverEmail.findElement(By.xpath(Constant.xpath_mailcatcherEmailContent)).getText();
				System.out.println("\n\nReset password email content: \n"+resetPasswordMessage01 + "\n");
				Reporter.log( "Reset password email content: "+resetPasswordMessage01, true );

				//now click on verify and navigate to new link
				
				//get current page
				currentPageHandle = driverEmail.getWindowHandle();
				//String pageUrl = driverEmail.getCurrentUrl(); //pageurl
				//click the link to check 
				driverEmail.findElement(By.xpath(Constant.xpath_mailcatcherResetPasswordButton)).click();

				//add wait
				driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driverEmail.manage().window().maximize();
				Thread.sleep(2000);
				
				List<String> browserTabs = new ArrayList<String> (driverEmail.getWindowHandles());
				int tabsTotal=browserTabs.size();
				
				for(String eachTabs : browserTabs)
				{
					driverEmail.switchTo().window(eachTabs);
					
					String pageTitle = driverEmail.getTitle().toLowerCase();
					//reset password page title
					if(pageTitle.contains(("Reset password").toLowerCase()))
					{
						Reporter.log( "Reset password link clicked", true );

						
						//verify element is present on page or not
						boolean elementStatus = com.register.utility.CheckElement.existsElement(Constant.xpath_resetPasswordPage_label,driverEmail);
						Thread.sleep(1000);

						if(elementStatus)
						{
							Reporter.log( "Navigated to reset password page, please insert new password.", true );

							String resetPasswordMessage=driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_label)).getText();
							//System.out.println("\n\nSuccessful email verification message: "+resetPasswordMessage);
							Thread.sleep(1000);
							
							// password reset page  
							String newPasswordInfo = driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_contents)).getText();
							Reporter.log("Reset Password page header: "+ resetPasswordMessage + "   Password reset rules: "+ newPasswordInfo,true);
							
							Thread.sleep(2000);

							driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_newPasswordInputBox)).clear();
							driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_retypePasswordInputBox)).clear();
							driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_newPasswordInputBox)).sendKeys(sPassword);
							driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_retypePasswordInputBox)).sendKeys(sConfirmPassword);
							
							Reporter.log( "New password: "+ sPassword + "   /   Confirm password: " + sConfirmPassword, true );

							Thread.sleep(2000);
					
							driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_continueButton)).click();
							driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							Thread.sleep(2000);
							
							String newpageTitle = driverEmail.getTitle().toLowerCase();

							//check whether successfully navigated to updated account info page
							if(newpageTitle.equalsIgnoreCase("Reset password"))
							{
								System.out.println("\nNavigated to same Reset password page");
								String passwordValidationMessage = driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_validationMessage)).getText();
								
								Reporter.log("Validation message: "+ passwordValidationMessage, true );
								//now login with new credentials

								if(passwordValidationMessage.equalsIgnoreCase("Please enter a valid email address.") )
								{
									System.out.println("\nValidation message : Please enter a valid email address.");
									Reporter.log( "Validation message : Please enter a valid email address.", true );
								}
								else if(passwordValidationMessage.equalsIgnoreCase("Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.") )
								{
									System.out.println("\nValidation message : Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.");
									Reporter.log( "Validation message : Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.", true );
								}
								else if(passwordValidationMessage.equalsIgnoreCase("Your password can’t include more than 2 repeating characters.") )
								{
									System.out.println("\nValidation message : Your password can’t include more than 2 repeating characters.");
									Reporter.log( "Validation message : Your password can’t include more than 2 repeating characters.", true );

								}
								else if(passwordValidationMessage.equalsIgnoreCase("Passwords do not match.") )
								{
									System.out.println("\nValidation message : Passwords do not match.");
									Reporter.log( "Validation message : Passwords do not match.", true );

								}
								else if(passwordValidationMessage.equalsIgnoreCase("Please specify password.") )
								{
									System.out.println("\nValidation message : Please specify password.");
									Reporter.log( "Validation message : Please specify password.", true );

								}
														
								else if(passwordValidationMessage.equalsIgnoreCase("Your chosen password is too common. Chose a different password.") )
								{
									System.out.println("\nValidation message : Your chosen password is too common. Chose a different password.");
									Reporter.log( "Validation message : Your chosen password is too common. Chose a different password.", true );

								}
								else if((passwordValidationMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (passwordValidationMessage.toLowerCase().contains(("Passwords do not match.").toLowerCase())) && (passwordValidationMessage.toLowerCase().contains(("Your password can’t include more than 2 repeating characters.").toLowerCase())))
								{
									System.out.println("\nValidation message : Please enter a valid email address. Passwords do not match. Your password can’t include more than 2 repeating characters.");
									Reporter.log( "Validation message : Please enter a valid email address. Passwords do not match. Your password can’t include more than 2 repeating characters.", true );
								}
								else if((passwordValidationMessage.toLowerCase().contains(("Passwords do not match.").toLowerCase())) && (passwordValidationMessage.toLowerCase().contains(("Your password can’t include more than 2 repeating characters.").toLowerCase())))
								{
									System.out.println("\nValidation message : Passwords do not match. Your password can’t include more than 2 repeating characters.");
									Reporter.log( "Validation message : Passwords do not match. Your password can’t include more than 2 repeating characters.", true );
								}
								else if((passwordValidationMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (passwordValidationMessage.toLowerCase().contains(("Your password can’t include more than 2 repeating characters.").toLowerCase())))
								{
									System.out.println("\nValidation message : Please enter a valid email address. Your password can’t include more than 2 repeating characters.");
									Reporter.log( "Validation message : Please enter a valid email address. Your password can’t include more than 2 repeating characters.", true );

								}
								else if((passwordValidationMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (passwordValidationMessage.toLowerCase().contains(("Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.").toLowerCase())) && (passwordValidationMessage.toLowerCase().contains(("Passwords do not match.").toLowerCase())))
								{
									System.out.println("\nValidation message : Please enter a valid email address. Passwords do not match.  Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.");
									Reporter.log( "Validation message : Please enter a valid email address. Passwords do not match.  Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.", true );

								}
								else if((passwordValidationMessage.toLowerCase().contains(("Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.").toLowerCase())) && (passwordValidationMessage.toLowerCase().contains(("Passwords do not match.").toLowerCase())))
								{
									System.out.println("\nValidation message : Passwords do not match. Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.");
									Reporter.log( "Validation message : Passwords do not match. Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.", true );

								}
								else if((passwordValidationMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (passwordValidationMessage.toLowerCase().contains(("Passwords do not match.").toLowerCase())))
								{
									System.out.println("\nValidation message : Please enter a valid email address. Passwords do not match.");
									Reporter.log( "Validation message : Please enter a valid email address. Passwords do not match.", true );

								}
								else if((passwordValidationMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (passwordValidationMessage.toLowerCase().contains(("Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.").toLowerCase())))
								{
									System.out.println("\nValidation message : Please enter a valid email address.  Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.");
									Reporter.log( "Validation message : Please enter a valid email address. Your password must be at least 8 characters long, include an uppercase character, a lowercase character, a number and a symbol (for example £$%&*). Passwords can't be longer than 16 characters.", true );

								}
								
								else if((passwordValidationMessage.toLowerCase().contains(("Please enter a valid email address.").toLowerCase())) && (passwordValidationMessage.toLowerCase().contains(("Your chosen password is too common. Chose a different password.").toLowerCase())))
								{
									System.out.println("\nValidation message : Please enter a valid email address. Your chosen password is too common. Chose a different password.");
									Reporter.log( "Validation message : Please enter a valid email address. Your chosen password is too common. Chose a different password.", true );

								}
								
								else
								{
									throw new NoSuchElementException("Password validation failed");
									
								}

							}
							else
							{
								throw new NoSuchElementException("\nValidation of reset password failed");
							}
							
						 
								
							
							//closing driver of current tab
							driverEmail.close();
							//switch to old main tab
							driverEmail.switchTo().window(currentPageHandle);
							driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							Thread.sleep(1000);
					  		//driverEmail.get(url_mailcatcher);

							
							emailConfirmationStatus =true;
							
						}
						
						}
					

					}
					

				}
				
			
			
		
			
			
			
		
	  }
	  
	 
	  @BeforeMethod
	  public void beforeMethod() 
	  {
		
				
		System.out.println("\nOpening webdriver \n");
		
		if(Constant.environmentVariable.equalsIgnoreCase("local"))
	  	{
			System.setProperty("webdriver.chrome.driver", "exe/chromedriver.exe"); //chromedriver.exe set property path
			driverEmail = new ChromeDriver();
	  	}
	  	else
	  	{
	  		System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driverEmail = new FirefoxDriver(capabilities);
	  	}
		
	  }
	
	  
	  @AfterMethod
	  public void afterMethod() 
	  {
		System.out.println("\nClosing webdriver......");
		driverEmail.get("about:config");
		//driver.quit();
		driverEmail.close();
	  }
	  
	  @DataProvider
	  
	  public Object[][] Authentication() throws Exception
	  {
	 
		    // Setting up the Test Data Excel file
		  	String filename = this.getClass().getResource("/CID_TestAutomation_User_Credentials_Data.xlsx").getFile();
		 	ExcelUtility.setExcelFile(filename,"PasswordTestData");
	 
		 	sTestCaseName = this.toString();
	 
		  	// From above method we get long test case name including package and class name etc.
		 	
	 
		  	// The below method will refine your test case name, exactly the name use have used
	 
		  	sTestCaseName = ExcelUtility.getTestCaseName(this.toString());
	 
		    // Fetching the Test Case row number from the Test Data Sheet
	 
		    // Getting the Test Case name to get the TestCase row from the Test Data Excel sheet
	 
		 	//commented for all data -->iTestCaseRow = ExcelUtility.getRowContains(sTestCaseName,0);
		 	iTestCaseRow = ExcelUtility.getRowCount();
		 	
		    Object[][] testObjArray = ExcelUtility.getTableDataArray(filename,"PasswordTestData",iTestCaseRow);

		    //commented for all test data --> Object[][] testObjArray = ExcelUtility.getTableArray(filename,"TestData",iTestCaseRow);
	 
		    	return (testObjArray);
	 
		}
}
