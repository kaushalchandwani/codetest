package com.register.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.register.config.*;

public class ForgotPasswordTest 
{
	  public WebDriver driver;
	
	  @Test
	  public void forgotPassword() throws Exception 
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
			
			
			
			driver.findElement(By.xpath(Constant.xpath_loginPage_forgotPasswordLink)).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Thread.sleep(3000);
			
			

			
			if(driver.getTitle().equalsIgnoreCase("Forgotten password"))
			{
				System.out.println("\nForgotten password");
				Reporter.log( "Navigated to Forgotten password Page", true );

	
			}
			else
			{
				throw new NoSuchElementException("Forgotton Password url not working");
			}
			
			String forgotPasswordPageLabel1 = driver.findElement(By.xpath(Constant.xpath_forgotPasswordPage_label1)).getText();
			String forgotPasswordPageLabel2 = driver.findElement(By.xpath(Constant.xpath_forgotPasswordPage_label2)).getText();
			
			if((forgotPasswordPageLabel1.toLowerCase()).contains(("Enter your email address").toLowerCase()) && (forgotPasswordPageLabel2.toLowerCase()).contains(("We will send you instructions on how to reset your password to your email").toLowerCase()))
			{
				driver.findElement(By.xpath(Constant.xpath_forgotPasswordPage_emailIdInputBox)).clear();
				driver.findElement(By.xpath(Constant.xpath_forgotPasswordPage_emailIdInputBox)).sendKeys(Constant.username_Patient);
				
				Reporter.log( "User Credentials for password reset:- \nemail: "+Constant.username_Patient, true );

				Thread.sleep(2000);
		
				driver.findElement(By.xpath(Constant.xpath_forgotPasswordPage_submitButton)).click();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				Thread.sleep(2000);
			}
			
			
	
			//check page title and then check confirm message
			if(driver.getTitle().equalsIgnoreCase("Log in to NHS"))
			{
				System.out.println("\nLogin Page");
				Reporter.log( "Navigated to login Page", true );
				String resetPasswordBanner = driver.findElement(By.xpath(Constant.xpath_loginPage_resetPasswordLinkBanner)).getText();
				String forgotPasswordBanner = driver.findElement(By.xpath(Constant.xpath_loginPage_fogottenPasswordHeader)).getText();
				String checkEmailBanner = driver.findElement(By.xpath(Constant.xpath_loginPage_checkEmailBanner)).getText();
				
				
				if((resetPasswordBanner.toLowerCase()).contains(("We have sent you instructions on how to reset your password to your email.").toLowerCase()) && (resetPasswordBanner.toLowerCase()).contains(("You have 48 hours to reset your password before the link is deactivated.").toLowerCase()) && (forgotPasswordBanner.toLowerCase()).contains(("Forgotten password").toLowerCase()) && (checkEmailBanner.toLowerCase()).contains(("Check your email").toLowerCase()))
				{
					System.out.println("\nYou should receive an email shortly with further instructions.");
					Reporter.log( forgotPasswordBanner + ". " + checkEmailBanner + ":- " + resetPasswordBanner,true);
					  
				}
				else
				{
					throw new NoSuchElementException("Password reset link email message not displayed");
				}
				

			}
			else
			{
				throw new NoSuchElementException("Login url is not working");
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
