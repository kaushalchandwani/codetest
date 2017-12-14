package com.register.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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

public class ForgotResetPasswordDashboardLogoutTest01 
{
	  public WebDriver driver;
	
	  @Test(priority = 0)
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
	  
	  @Test(priority = 1)
	  public void resetPasswordEmail() throws Exception 
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

				resetPasswordMessage01= driver.findElement(By.xpath(Constant.xpath_mailcatcherEmailContent)).getText();
				System.out.println("\n\nReset password email content: \n"+resetPasswordMessage01 + "\n");
				Reporter.log( "Reset password email content: "+resetPasswordMessage01, true );

				//now click on verify and navigate to new link
				
				//get current page
				String currentPageHandle = driver.getWindowHandle();
				String pageUrl = driver.getCurrentUrl(); //pageurl
				//click the link to check 
				driver.findElement(By.xpath(Constant.xpath_mailcatcherResetPasswordButton)).click();

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
					//reset password page title
					if(pageTitle.contains(("Reset password").toLowerCase()))
					{
						Reporter.log( "Reset password link clicked", true );

						
						//verify element is present on page or not
						boolean elementStatus = com.register.utility.CheckElement.existsElement(Constant.xpath_resetPasswordPage_label,driver);
						Thread.sleep(1000);

						if(elementStatus)
						{
							Reporter.log( "Navigated to reset password page, please insert new password.", true );

							String resetPasswordMessage=driver.findElement(By.xpath(Constant.xpath_resetPasswordPage_label)).getText();
							//System.out.println("\n\nSuccessful email verification message: "+resetPasswordMessage);
							Thread.sleep(1000);
							
							// password reset page  
							String newPasswordInfo = driver.findElement(By.xpath(Constant.xpath_resetPasswordPage_contents)).getText();
							Reporter.log("Reset Password page header: "+ resetPasswordMessage + "   Password reset rules:"+ newPasswordInfo,true);
							
							driver.findElement(By.xpath(Constant.xpath_resetPasswordPage_newPasswordInputBox)).clear();
							driver.findElement(By.xpath(Constant.xpath_resetPasswordPage_retypePasswordInputBox)).clear();
							driver.findElement(By.xpath(Constant.xpath_resetPasswordPage_newPasswordInputBox)).sendKeys(Constant.new_password_Patient);
							driver.findElement(By.xpath(Constant.xpath_resetPasswordPage_retypePasswordInputBox)).sendKeys(Constant.new_password_Patient);
							
							Reporter.log( "User Credentials for new password reset:- \nPassword: "+Constant.new_password_Patient, true );

							Thread.sleep(2000);
					
							driver.findElement(By.xpath(Constant.xpath_resetPasswordPage_continueButton)).click();
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							Thread.sleep(2000);
							
							String newpageTitle = driver.getTitle().toLowerCase();

							//check whether successfully navigated to Dashboard page
							
									Reporter.log( "User Credentials for login:- \nemail: "+Constant.username_Patient+"\n New password: "+Constant.new_password_Patient, true );
									Thread.sleep(2000);
									if(newpageTitle.equalsIgnoreCase("NHS Account Management"))
									{
										System.out.println("\nNHS Account Management Page");
										Reporter.log( "Dashboard with New User credentials ", true );
										Reporter.log( "Navigated successfully to Dashboard Page ", true );

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
											throw new NoSuchElementException("Logout unsuccesfull, login url is not working");
										}

									}
									else
									{
										throw new NoSuchElementException("\nNHS Account Management - Dashboard url is not working");
									}

								
							
							//closing driver of current tab
							driver.close();
							//switch to old main tab
							driver.switchTo().window(currentPageHandle);
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							Thread.sleep(1000);
							driver.get(url_mailcatcher);

							
							emailConfirmationStatus =true;
							
						}
						else
						{
							//throw new NoSuchElementException("Sorry !!! \n Email verification is unsuccessfull.....");
						}
					}//reset password page link expired title
					
					

				}
				
			
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
