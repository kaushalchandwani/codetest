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
import org.testng.annotations.Test;

import com.register.config.Constant;
import com.register.utility.MessageBox;

public class LoginTimeOutReloginTest 
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
		//driver.quit();
		driver.close();
	  }
	  
	  @Test
	  public void userSessionTimeOutReLogin() throws Exception 
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
			
			driver.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).sendKeys(Constant.username_Patient);
			driver.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).sendKeys(Constant.password_Patient);
			driver.findElement(By.xpath(Constant.xpath_loginPage_loginButton)).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			Reporter.log( "User Credentials for login:- \nemail: "+Constant.username_Patient+"\npassword: "+Constant.password_Patient, true );

			
			Thread.sleep(2000);
			if(driver.getTitle().equalsIgnoreCase("NHS Account Management"))
			{
				System.out.println("\nNHS Account Management Page");
				Reporter.log( "Login with valid User credentials ", true );

				String DashboardMessage = driver.findElement(By.xpath(Constant.xpath_dashboardPage_messageBanner)).getText();
				if(DashboardMessage.equalsIgnoreCase("Welcome to your NHS"))
				{
					String WelcomeMessage = driver.findElement(By.xpath(Constant.xpath_dashboardPage_userEmailId)).getText();

					
					System.out.println("\nWelcome to NHS");
					Thread.sleep(2000);
					Reporter.log( "Navigated successfully to Dashboard Page ", true );
					Reporter.log( "Message on Dashboard: "+WelcomeMessage, true );

					MessageBox.printMessage("delay of 1 min for session timeout.....");

					
					driver.findElement(By.xpath(Constant.xpath_dashboardPage_accountLink)).click();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

					if(driver.getTitle().equalsIgnoreCase("Log in to NHS"))
					{
						System.out.println("\nSession time out and Logout ");
						Thread.sleep(2000);
						String logoutMessage = driver.findElement(By.xpath(Constant.xpath_loginPage_logOutBanner)).getText();
						Reporter.log( "User logged out and navigated back to login page", true );
						if(logoutMessage.equalsIgnoreCase("Your session has timed out. You need to log in again."))
						{
							Reporter.log( "Logged out message: "+logoutMessage, true );
							driver.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).clear();
							driver.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).clear();
							
							driver.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).sendKeys(Constant.username_Patient);
							driver.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).sendKeys(Constant.password_Patient);
							driver.findElement(By.xpath(Constant.xpath_loginPage_loginButton)).click();
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

							Thread.sleep(2000);
							if(driver.getTitle().equalsIgnoreCase("NHS Account Management"))
							{
								System.out.println("\nAccount  Page");
								Reporter.log( "ReLogin with valid User credentials ", true );
								Thread.sleep(2000);

							}
							else
							{
								throw new NoSuchElementException("Unable to relogin back to Account Page");
							}
						}
						else
						{
							throw new NoSuchElementException("Session time out failed, session logout page not displayed");
						}
					

					}
					else
					{
						throw new NoSuchElementException("Login url is not working");
					}
				}
				else
				{
					throw new NoSuchElementException("\nWelcome to NHS message not displayed");

				}
			}
			else
			{
				throw new NoSuchElementException("\nNHS Account Management url is not working");
			}
			
		
			
	  }

}
