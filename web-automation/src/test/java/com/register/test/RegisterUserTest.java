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
import com.register.utility.MessageBox;

public class RegisterUserTest 
{
	  public WebDriver driver;
	
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
				String passwordRulesMessage = driver.findElement(By.xpath(Constant.xpath_registerPage_passwordRules)).getText();
				if(passwordRulesMessage.equalsIgnoreCase("at least 8 characters\nat least one capital letter\nat least one number\nat least one symbol, for example: ?!£%"))
				{
					Reporter.log( "Password Rules displayed", true );
					MessageBox.printMessage("Password Rules Message :- \nYour password must have: \n"+ passwordRulesMessage);

				}
				else
				{
					throw new NoSuchElementException("Password Rules message not displayed");
				}
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
			
	
			//check page title and then check confirm message
			if(driver.getTitle().equalsIgnoreCase("Validate your email"))
			{
				System.out.println("\nValidate email Page");
				Reporter.log( "Navigated to Validate email Page", true );

				String confirmMailMessage = driver.findElement(By.xpath(Constant.xpath_confirmEmailPage_messageBanner)).getText();
				String confirmMessage = driver.findElement(By.xpath(Constant.xpath_confirmEmailPage_messageHeader)).getText();
				//re-send mail .//*[@id='content']/div/div/div[1]/div/div[2]/p[1]/a
				
				if(confirmMessage.equalsIgnoreCase("Validate your email") && confirmMailMessage.equalsIgnoreCase("You need to validate your email address before you can log in. Please check your email."))
				{
					System.out.println("\nValidate email Message displayed");
					Reporter.log( "Verification email sent to user", true );

				}
				else
				{
					throw new NoSuchElementException("Validate email not send");
				}
	
			}
			else
			{
				throw new NoSuchElementException("Validate email url not working");
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

}
