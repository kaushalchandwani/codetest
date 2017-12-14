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
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.register.config.Constant;

public class ResetPasswordEmailTest 
{
	  public WebDriver driverEmail;

  @Test
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
			String currentPageHandle = driverEmail.getWindowHandle();
			String pageUrl = driverEmail.getCurrentUrl(); //pageurl
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
						Reporter.log("Reset Password page header: "+ resetPasswordMessage + "   Password reset rules:"+ newPasswordInfo,true);
						
						driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_newPasswordInputBox)).clear();
						driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_retypePasswordInputBox)).clear();
						driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_newPasswordInputBox)).sendKeys(Constant.new_password_Patient);
						driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_retypePasswordInputBox)).sendKeys(Constant.new_password_Patient);
						
						Reporter.log( "User Credentials for new password reset:- \nPassword: "+Constant.new_password_Patient, true );

						Thread.sleep(2000);
				
						driverEmail.findElement(By.xpath(Constant.xpath_resetPasswordPage_continueButton)).click();
						driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						Thread.sleep(2000);
						
						String newpageTitle = driverEmail.getTitle().toLowerCase();

						//check whether successfully navigated to updated account info page
						if(newpageTitle.equalsIgnoreCase("Your account has been updated."))
						{
							System.out.println("\nYour account has been updated.");
							String accountUpdatedBanner = driverEmail.findElement(By.xpath(Constant.xpath_dashboardPage_accountUpdatedBanner)).getText();
							
							Reporter.log( accountUpdatedBanner+" Please login with new credentials", true );
							//now login with new credentials
							driverEmail.get(Constant.url_nhsAccountCreation);
							driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							Thread.sleep(2000);
							if(driverEmail.getTitle().equalsIgnoreCase("Log in to NHS"))
							{
								System.out.println("\nLogin Page");
								Reporter.log( "Navigated to login Page", true );
								driverEmail.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).clear();
								driverEmail.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).clear();

														
								driverEmail.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).sendKeys(Constant.username_Patient);
								driverEmail.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).sendKeys(Constant.new_password_Patient);
								driverEmail.findElement(By.xpath(Constant.xpath_loginPage_loginButton)).click();
								driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

								Reporter.log( "User Credentials for login:- \nemail: "+Constant.username_Patient+"\n New password: "+Constant.new_password_Patient, true );
								Thread.sleep(2000);
								if(driverEmail.getTitle().equalsIgnoreCase("NHS Account Management"))
								{
									System.out.println("\nNHS Account Management Page");
									Reporter.log( "Login with valid New User credentials ", true );
									Reporter.log( "Navigated successfully to Dashboard Page ", true );

									driverEmail.findElement(By.xpath(Constant.xpath_dashboardPage_logoutLink)).click();
									driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

									if(driverEmail.getTitle().equalsIgnoreCase("Log in to NHS"))
									{
										System.out.println("\nLogout successfully");
										Thread.sleep(2000);
										String logoutMessage = driverEmail.findElement(By.xpath(Constant.xpath_loginPage_logOutBanner)).getText();
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
									throw new NoSuchElementException("\nNHS Account Management url is not working");
								}

							}
							else
							{
								throw new NoSuchElementException("Login url is not working");
							}

						}
						else
						{
							throw new NoSuchElementException("\nNavigation to account updated info failed and reset password failed");
						}
						
						//closing driver of current tab
						driverEmail.close();
						//switch to old main tab
						driverEmail.switchTo().window(currentPageHandle);
						driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						Thread.sleep(1000);
				  		driverEmail.get(url_mailcatcher);

						
						emailConfirmationStatus =true;
						
					}
					else
					{
						//throw new NoSuchElementException("Sorry !!! \n Email verification is unsuccessfull.....");
					}
				}//reset password page link expired title
				else if(pageTitle.contains(("ERROR !").toLowerCase()))
				{
					String expiredPasswordResetHeader = driverEmail.findElement(By.xpath(Constant.xpath_expiredResetPasswordPage_label)).getText();
					//check reset password expiry message is present
					if(expiredPasswordResetHeader.toLowerCase().contains(("Reset password link has expired").toLowerCase()))
					{
						String expiredPasswordResetMessage = driverEmail.findElement(By.xpath(Constant.xpath_expiredResetPasswordPage_contents)).getText();

						Reporter.log(expiredPasswordResetMessage, true );
						//check back to login or reset link is working
						driverEmail.findElement(By.xpath(Constant.xpath_expiredResetPasswordPage_backToLoginResetLink)).click();
						
						if(driverEmail.getTitle().equalsIgnoreCase("Log in to NHS"))
						{
							System.out.println("\nLogin Page");
							Reporter.log( "Navigated to login Page", true );
						}
						else
						{
							throw new NoSuchElementException("Back to Login url is not working");
						}

					}

					//closing driver of current tab
					driverEmail.close();
					//switch to old main tab
					driverEmail.switchTo().window(currentPageHandle);
					driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Thread.sleep(1000);
			  		driverEmail.get(url_mailcatcher);

					
					emailConfirmationStatus =true;
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
  public void afterMethod() throws Exception 
  {
	  	System.out.println("\nClosing webdriver......");

	  	driverEmail.get("about:config");
		//driver.quit();
	  	driverEmail.close();
		Thread.sleep(1000);

  }

}
