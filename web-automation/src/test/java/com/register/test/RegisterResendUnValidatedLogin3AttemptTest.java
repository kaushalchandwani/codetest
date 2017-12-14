package com.register.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.register.config.*;

public class RegisterResendUnValidatedLogin3AttemptTest 
{
	public WebDriver driver;
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
		//	driver.quit();
		driver.close();
	}
	
	@Test
	public void userRegistration() throws Exception 
	  {
		
			System.out.println("\nUser emailid: "+ Constant.username_Patient);
					
			driver.get(Constant.url_nhsAccountCreation);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Thread.sleep(3000);
			
			
			if(driver.getTitle().equalsIgnoreCase("Log in to NHS"))
			{
				System.out.println("\nLogin Page");
				Reporter.log( "Navigated to login Page", true );

			}
			else
			{
				throw new NoSuchElementException("Login url is not working");
			}
			
			
			
			driver.findElement(By.xpath(Constant.xpath_loginPage_registerLink)).click();
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
			
			driver.findElement(By.xpath(Constant.xpath_registerPage_EmailInputBox)).sendKeys(Constant.username_Patient);
			driver.findElement(By.xpath(Constant.xpath_registerPage_PasswordInputBox)).sendKeys(Constant.password_Patient);
			driver.findElement(By.xpath(Constant.xpath_registerPage_confirmPasswordInputBox)).sendKeys(Constant.password_Patient);
			Reporter.log( "User Credentials for registration:- \nemail: "+Constant.username_Patient+"\n ----- password: "+Constant.password_Patient, true );

			Thread.sleep(2000);
	
			driver.findElement(By.xpath(Constant.xpath_registerPage_registerButton)).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			Thread.sleep(2000);
			//check for 1st attempt during registration
			 if(driver.getTitle().equalsIgnoreCase("Validate your email"))
				{
					System.out.println("\nValidate email Page");
					Reporter.log( "Navigated to Validate email Page", true );
					boolean elementStatusOtherAttempt = com.register.utility.CheckElement.existsElement(Constant.xpath_confirmEmailPage_messageHeader,driver);
					
					if(elementStatusOtherAttempt)
					{
						String confirmMailMessage = driver.findElement(By.xpath(Constant.xpath_confirmEmailPage_messageBanner)).getText();
						String confirmMessage = driver.findElement(By.xpath(Constant.xpath_confirmEmailPage_messageHeader)).getText();
						if(confirmMessage.equalsIgnoreCase("Validate your email") && confirmMailMessage.equalsIgnoreCase("You need to validate your email address before you can log in. Please check your email."))
						{
							System.out.println("\nValidate email Message displayed");
							Reporter.log( "Verification email sent to user", true );
							driver.findElement(By.xpath(Constant.xpath_confirmEmailPage_resendEmailLink)).click();
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							
							//check for 2nd attempt during resend link click
							if(driver.getTitle().equalsIgnoreCase("Validate your email"))
							{
								Reporter.log( "Validate email resend and navigated back to same Validate email Page", true );
							}
							else
							{
								throw new NoSuchElementException("Validate email resend  not working");
							}
							driver.findElement(By.xpath(Constant.xpath_confirmEmailPage_loginLink)).click();
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

							//login button click and back to login page
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
							
							driver.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).sendKeys(Constant.username_Patient);
							driver.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).sendKeys(Constant.password_Patient);
							driver.findElement(By.xpath(Constant.xpath_loginPage_loginButton)).click();
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

							Reporter.log( "User Credentials for login:- \nemail: "+Constant.username_Patient+"\npassword: "+Constant.password_Patient, true );
							
							//check for 3rd attempt during un-validated login and navigated to 3rd attempt message validate email page
							if(driver.getTitle().equalsIgnoreCase("Validate your email"))
							{
								Reporter.log( "Navigated to Validate email Page with 3rd attempt", true );
								boolean elementStatus3rdAttempt = com.register.utility.CheckElement.existsElement(Constant.xpath_validateEmailPage3rdAttempt_messageHeader,driver);
								
								if(elementStatus3rdAttempt)
								{
									confirmMailMessage = driver.findElement(By.xpath(Constant.xpath_validateEmailPage3rdAttempt_messageBanner)).getText();
									confirmMessage = driver.findElement(By.xpath(Constant.xpath_validateEmailPage3rdAttempt_messageHeader)).getText();
									if(confirmMessage.equalsIgnoreCase("This is your 3rd attempt") && confirmMailMessage.equalsIgnoreCase("If you still havent received an email from us please can you email Michael.Tattersall@nhs.net or call 07402 833373."))
									{
										System.out.println("\nThis is your 3rd attempt");
										Reporter.log( "This is your 3rd attempt message displayed", true );
										driver.findElement(By.xpath(Constant.xpath_validateEmailPage3rdAttempt_loginLink)).click();
										driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
										Thread.sleep(1000);
										
										if(driver.getTitle().equalsIgnoreCase("Log in to NHS"))
										{
											System.out.println("\nLogin Page");
											Reporter.log( "Navigated to login Page after 3 attempts", true );

										}
										else
										{
											throw new NoSuchElementException("Login url is not working at 3 attempts message page");
										}
										
									}
									else
									{
										throw new NoSuchElementException("Third attempt page not displayed");
									}
								}

							}
						}
						else
						{
							throw new NoSuchElementException("Validate email not send");
						}
					}
				}
	  }
	
}
