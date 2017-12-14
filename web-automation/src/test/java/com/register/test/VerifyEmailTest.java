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

public class VerifyEmailTest 
{
	  public WebDriver driverEmail;

  @Test
  public void verifyEmail() throws Exception 
  {
	  boolean clickVerifyEmail=false;
		boolean emailSender = false;
		boolean emailReceiver =false;
		boolean emailSubject = false;
		boolean emailConfirmationStatus = false;

		String verifyPageMessage01="";
		
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
					if(emailReceiver && sColumnValue.matches("Verify email"))
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

			verifyPageMessage01= driverEmail.findElement(By.xpath(Constant.xpath_mailcatcherEmailContent)).getText();
			System.out.println("\n\nVerification email content: \n"+verifyPageMessage01 + "\n");
			Reporter.log( "Verification email content: "+verifyPageMessage01, true );

			//now click on verify button  and navigate to new link
			
			//get current page
			String currentPageHandle = driverEmail.getWindowHandle();
			//String pageUrl = driverEmail.getCurrentUrl(); //pageurl
			//click the link to check 
			//driverEmail.findElement(By.xpath("xhtml:html/xhtml:body/xhtml:a")).click();
			driverEmail.findElement(By.xpath(Constant.xpath_mailcatcherVerifyButton)).click();

			//add wait
			driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driverEmail.manage().window().maximize();
			Thread.sleep(2000);
			
			List<String> browserTabs = new ArrayList<String> (driverEmail.getWindowHandles());
			//int tabsTotal=browserTabs.size();
			
			for(String eachTabs : browserTabs)
			{
				driverEmail.switchTo().window(eachTabs);
				
				String pageTitle = driverEmail.getTitle().toLowerCase();
				//verify page title
				if(pageTitle.contains(("My NHS Account").toLowerCase()))
				{
					
				}
				if(pageTitle.contains(("NHS Account Management").toLowerCase()))
				{
					System.out.println("\nNHS Account Management Page");
					Reporter.log( "Verified and navigated to  dashboard page ", true );
					
					boolean elementStatus = com.register.utility.CheckElement.existsElement(Constant.xpath_dashboardPage_messageBanner,driverEmail);
					Thread.sleep(1000);

					if(elementStatus)
					{
						String DashboardMessage = driverEmail.findElement(By.xpath(Constant.xpath_dashboardPage_messageBanner)).getText();
						System.out.println("\nWelcome to NHS");
						Thread.sleep(2000);
						Reporter.log( "Navigated successfully to Dashboard Page ", true );
						Reporter.log( DashboardMessage, true );

						
						
						//closing driver of current tab
						driverEmail.close();
						//switch to old main tab
						driverEmail.switchTo().window(currentPageHandle);
						driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						Thread.sleep(1000);
						//driver.get(url_mailcatcher);

						
						emailConfirmationStatus =true;
						
					}
					else
					{
						//throw new NoSuchElementException("Sorry !!! \n Email verification is unsuccessfull.....");
					}
				}
				else if(pageTitle.contains(("Validate email link expired").toLowerCase()))
				{
					Reporter.log( "Validated email link has expired", true );
					boolean elementStatus = com.register.utility.CheckElement.existsElement(Constant.xpath_validateEmailExpirePage_createAccountLink,driverEmail);
					Thread.sleep(1000);
					
					String DashboardMessage = driverEmail.findElement(By.xpath(Constant.xpath_validateEmailExpirePage_messageBanner)).getText();
					System.out.println("\nVerify link has expired");
					Thread.sleep(1000);
					Reporter.log( "Navigated to expired link info page ", true );
					Reporter.log( DashboardMessage, true );
					
					if(elementStatus)
					{
						

						driverEmail.findElement(By.xpath(Constant.xpath_validateEmailExpirePage_createAccountLink)).click();
						driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						Thread.sleep(1000);

						if(driverEmail.getTitle().equalsIgnoreCase("Log in to NHS"))
						{
							Reporter.log( "Create account Link selected, navigated to login page", true );

						}
						else
						{
							throw new NoSuchElementException("Create account link not working");
						}
						
						
						
						//closing driver of current tab
						driverEmail.close();
						//switch to old main tab
						driverEmail.switchTo().window(currentPageHandle);
						driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						Thread.sleep(1000);
						//driver.get(url_mailcatcher);

						
						emailConfirmationStatus =true;
						
					}
				}

				if(emailConfirmationStatus)
				{
					System.out.println("\n Additional browser tabs closed successfully");
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
