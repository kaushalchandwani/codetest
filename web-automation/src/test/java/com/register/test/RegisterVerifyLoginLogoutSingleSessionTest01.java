package com.register.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.register.config.*;

public class RegisterVerifyLoginLogoutSingleSessionTest01 
{
	  public WebDriver driver;
	
	  @Test(priority = 0)
	  public void userRegistration() throws Exception 
	  {
		
			System.out.println("\nUser emailid: "+ Constant.username_Patient);
					
			driver.get(Constant.url_nhsAccountCreation);
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
			Reporter.log( "User Credentials for registration:- \nemail: "+Constant.username_Patient+"\n \t\tpassword: "+Constant.password_Patient, true );

			Thread.sleep(2000);
	
			driver.findElement(By.xpath(Constant.xpath_registerPage_registerButton)).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			Thread.sleep(2000);
			
	
			//check page title and then check confirm message
			if(driver.getTitle().equalsIgnoreCase("Validate your email"))
			{
				System.out.println("\nConfirm email Page");
				Reporter.log( "Navigated to Confirm email Page", true );

				String confirmMailMessage = driver.findElement(By.xpath(Constant.xpath_confirmEmailPage_messageBanner)).getText();
				String confirmMessage = driver.findElement(By.xpath(Constant.xpath_confirmEmailPage_messageHeader)).getText();
				//re-send mail .//*[@id='content']/div/div/div[1]/div/div[2]/p[1]/a
				
				if(confirmMessage.equalsIgnoreCase("Validate your email") && confirmMailMessage.equalsIgnoreCase("You need to validate your email address before you can log in. Please check your email."))
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
			
			
			
		/*
				 html/body/div[1]/div/div[1]/div/div/div --> create nhs account 
				 .//*[@id='kc-form-wrapper']/form/div[1]/label --> email add label 
				 .//*[@id='kc-form-wrapper']/form/div[2]/label --> password label 
				 .//*[@id='email']
				 .//*[@id='password']
				 .//*[@id='kc-form-wrapper']/form/div[4]/div[2]/input --register
				 
				 */
	  }
	  
	  @Test(priority = 1)
	  public void verifyEmail() throws Exception 
	  {
		  	boolean clickVerifyEmail=false;
			boolean emailSender = false;
			boolean emailReceiver =false;
			boolean emailSubject = false;
			boolean emailConfirmationStatus = false;

			String verifyPageHeader="";
			String verifyPageMessage01="";
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
		  	driver.get(url_mailcatcher);

		  	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  	driver.manage().window().maximize();
		  	Thread.sleep(2000);
			
			
			if(driver.getTitle().matches("MailCatcher(.*)"))
			{
				System.out.println("\nMailCatcher Application open.....");
				Reporter.log( "MailCcatcher application open.....", true );

			}
			else
			{
				throw new NoSuchElementException("MailCatcher application unable to load.....");
			}
			
			//.//*[@id='messages']/table/tbody/tr[1]/td[1]
			
			List<WebElement> rows =   (List<WebElement>) driver.findElements(By.xpath(Constant.xpath_mailcatcherTable + "/tr"));
			int countRows = rows.size();
			//System.out.println("\nROW COUNT : "+countRows);
			
			List<WebElement> columns = driver.findElements(By.xpath(Constant.xpath_mailcatcherTable + "/tr[1]/td"));
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
					boolean elementStatus = com.register.utility.CheckElement.existsElement(xpathVariable,driver);
					if(elementStatus)
					{
						sColumnValue= driver.findElement(By.xpath(xpathVariable)).getText();
						//System.out.println("\nColumn value: "+ sColumnValue);
						if(sColumnValue.matches("<citizen.identity1@gmail.com>"))
						{
							//System.out.println("\nEmail sender id: "+ sColumnValue);
							emailSender = true;
						}
						
							
						if(emailSender && sColumnValue.matches("<"+Constant.username_Patient +">"))
						{
							//System.out.println("\nEmail receipient id: "+ sColumnValue);
							emailReceiver=true;
						    
						}
						if(emailReceiver && sColumnValue.matches("Verify email"))
						{
							//System.out.println("\nSubject: "+ sColumnValue);
							emailSubject =true;
						}
						if(emailSubject)
						{
							driver.findElement(By.xpath(Constant.xpath_mailcatcherTable + "/tr[" + i + "]")).click();
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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
				driver.findElement(By.xpath(Constant.xpath_mailcatcherEmailHtmlTab)).click();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				Thread.sleep(1000);
				
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.tagName(Constant.xpath_mailcatcheriframe)));

				
				//verifyEmailVerificationLink= driverEmail.findElement(By.xpath("xhtml:html/xhtml:body/xhtml:a")).getText();
				//System.out.println("\nVerify Email Link: " + verifyEmailVerificationLink);

				verifyPageMessage01= driver.findElement(By.xpath(Constant.xpath_mailcatcherEmailContent)).getText();
				System.out.println("\n\nVerification email content: \n"+verifyPageMessage01 + "\n");
				Reporter.log( "Verification email content: "+verifyPageMessage01, true );

				//now click on verify button  and navigate to new link
				
				//get current page
				String currentPageHandle = driver.getWindowHandle();
				String pageUrl = driver.getCurrentUrl(); //pageurl
				//click the link to check 
				//driverEmail.findElement(By.xpath("xhtml:html/xhtml:body/xhtml:a")).click();
				driver.findElement(By.xpath(Constant.xpath_mailcatcherVerifyButton)).click();

				//add wait
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				Thread.sleep(2000);
				
				List<String> browserTabs = new ArrayList<String> (driver.getWindowHandles());
				int tabsTotal=browserTabs.size();
				
				for(String eachTabs : browserTabs)
				{
					driver.switchTo().window(eachTabs);
					
					String pageTitle = driver.getTitle().toLowerCase();
					//verify page title
					if(pageTitle.contains(("NHS Account Management").toLowerCase()))
					{
						System.out.println("\nNHS Account Management Page");
						Reporter.log( "Verified and navigated to  dashboard page ", true );
						
						boolean elementStatus = com.register.utility.CheckElement.existsElement(Constant.xpath_dashboardPage_messageBanner,driver);
						Thread.sleep(1000);

						if(elementStatus)
						{
							String DashboardMessage = driver.findElement(By.xpath(Constant.xpath_dashboardPage_messageBanner)).getText();
							System.out.println("\nWelcome to NHS");
							Thread.sleep(2000);
							Reporter.log( "Navigated successfully to Dashboard Page ", true );
							Reporter.log( DashboardMessage, true );

							
							
							//closing driver of current tab
							driver.close();
							//switch to old main tab
							driver.switchTo().window(currentPageHandle);
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							Thread.sleep(1000);
							//driver.get(url_mailcatcher);

							
							emailConfirmationStatus =true;
							
						}
						else
						{
							//throw new NoSuchElementException("Sorry !!! \n Email verification is unsuccessfull.....");
						}
					}
				}	
					//now goto iframe and click on verify link shown in mail
					driver.switchTo().frame(driver.findElement(By.tagName(Constant.xpath_mailcatcheriframe)));
					//now click on verify link  and navigate to new link
					
					//get current page
					String currentNewPageHandle = driver.getWindowHandle();
					String pageNewUrl = driver.getCurrentUrl(); //pageurl
					//click the link to check 
					//driverEmail.findElement(By.xpath("xhtml:html/xhtml:body/xhtml:a")).click();
					driver.findElement(By.xpath(Constant.xpath_mailcatcherVerifyLink)).click();

					//add wait
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					driver.manage().window().maximize();
					Thread.sleep(2000);
					
					List<String> browserNewTabs = new ArrayList<String> (driver.getWindowHandles());
					int newTabsTotal=browserNewTabs.size();
					
					for(String eachNewTabs : browserNewTabs)
					{
						driver.switchTo().window(eachNewTabs);
						
						String pageNewTitle = driver.getTitle().toLowerCase();
						//verify page title
						if(pageNewTitle.contains(("NHS Account Management").toLowerCase()))
						{
							System.out.println("\nNHS Account Management Page");
							//Reporter.log( "Verified and navigated to  dashboard page ", true );
							
							boolean elementStatus = com.register.utility.CheckElement.existsElement(Constant.xpath_dashboardPage_messageBanner,driver);
							Thread.sleep(1000);

							if(elementStatus)
							{
								String DashboardMessage = driver.findElement(By.xpath(Constant.xpath_dashboardPage_messageBanner)).getText();
								System.out.println("\nWelcome to NHS");
								Thread.sleep(2000);
								//Reporter.log( "Navigated successfully to Dashboard Page ", true );
								//Reporter.log( DashboardMessage, true );

								
								
								//closing driver of current tab
								driver.close();
								//switch to old main tab
								driver.switchTo().window(currentPageHandle);
								driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								Thread.sleep(1000);
								//driver.get(url_mailcatcher);

								
								emailConfirmationStatus =true;
								
							}
							else
							{
								//throw new NoSuchElementException("Sorry !!! \n Email verification is unsuccessfull.....");
							}
						}
						
					}
				
			
				
			}
			
	  }
	  
	  @Test(priority = 2)
	  public void userLogin() throws Exception 
	  {
		 
			//System.out.println("\nUser emailid: "+ Constant.username_Patient);
			driver.get(driver.getCurrentUrl());
			Thread.sleep(3000);
			String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,"t");
			driver.findElement(By.tagName("body")).sendKeys(selectLinkOpeninNewTab);
/*
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");        
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		    driver.switchTo().window(tabs.get(1));*/
			driver.get(Constant.url_nhsAccountCreation);
			
			//driver.get(Constant.url_nhsAccountCreation);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Thread.sleep(3000);
			Reporter.log( "Opening url: "+ Constant.url_nhsAccountCreation, true );

			boolean loginStatus =false;
			
			if(driver.getTitle().equalsIgnoreCase("Log in to NHS"))
			{
				System.out.println("\nLogin Page");
				Reporter.log( "Navigated to login Page", true );
				driver.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).clear();
				driver.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).clear();
				
				driver.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).sendKeys(Constant.username_Patient);
				driver.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).sendKeys(Constant.password_Patient);
				driver.findElement(By.xpath(Constant.xpath_loginPage_loginButton)).click();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				Reporter.log( "User Credentials for login:- \nemail: "+Constant.username_Patient+"\npassword: "+Constant.password_Patient, true );
				if(driver.getTitle().equalsIgnoreCase("NHS Account Management"))
				{
					loginStatus = true;
				}
				

			}
			else if(driver.getTitle().equalsIgnoreCase("NHS Account Management"))
			{
				loginStatus = true;

			}
			else
			{
				throw new NoSuchElementException("Login url is not working");
			}
			
			
			Thread.sleep(2000);
			if(loginStatus)
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

					driver.findElement(By.xpath(Constant.xpath_dashboardPage_logoutLink)).click();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

					if(driver.getTitle().equalsIgnoreCase("Log in to NHS"))
					{
						System.out.println("\nLogout successfully");
						Thread.sleep(2000);
						String logoutMessage = driver.findElement(By.xpath(Constant.xpath_loginPage_logOutBanner)).getText();
						Reporter.log( "User logged out and navigated back to login page", true );
						
						Reporter.log( "Logged out message: "+logoutMessage, true );

						


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
				throw new NoSuchElementException("\nIssue with login feature");
			}
			
		
			
	  }
	  
	  @BeforeTest
	  public void beforeMethod() 
	  {
		
		
		
		System.out.println("\nOpening webdriver \n");

		
		if(Constant.environmentVariable.equalsIgnoreCase("local"))
	  	{
			System.setProperty("webdriver.chrome.driver", "exe/chromedriver.exe"); //chromedriver.exe set property path
			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
	  	}
	  	else
	  	{
	  		System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driver = new FirefoxDriver(capabilities);
			
	  	}
		
	  }
	
	  @AfterTest
	  public void afterMethod() 
	  {
		  System.out.println("\nClosing webdriver......");

		driver.get("about:config");
		//driver.quit();
		driver.close();
	  }

}

