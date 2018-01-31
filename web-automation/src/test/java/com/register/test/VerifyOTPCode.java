/**
 * @author Kaushal Chandwani
 *
 */
package com.register.test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.register.config.Constant;

public class VerifyOTPCode 
{
	public static WebDriver driverEmail;

	
	public static String fetchOTPcode(String contactNo) throws Exception 
	  {
			System.setProperty("webdriver.chrome.driver", "exe/chromedriver.exe"); //chromedriver.exe set property path
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			driverEmail = new ChromeDriver(options);
			
		  	boolean clickVerifyEmail=false;
			boolean emailSender = false;
			boolean emailReceiver =false;
			boolean emailSubject = false;
			boolean emailConfirmationStatus = false;
			String varOTP = "";
			String OTPPageMessage="";
			
			String url_mailcatcher = Constant.url__mailCatcherOTP_aws;
		  	/*
		  	if(Constant.environmentVariable.equalsIgnoreCase("local"))
		  	{
		  		url_mailcatcher = Constant.url__mailCatcher_local;
		  	}
		  	else
		  	{
		  		url_mailcatcher = Constant.url__mailCatcher_remote;
		  	}*/
		  	driverEmail.get(url_mailcatcher);
		  	
		  	driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  	Thread.sleep(1000);
			
			
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
						if(sColumnValue.matches("<ods.test@email.net> size=264"))
						{
							//System.out.println("\nEmail sender id: "+ sColumnValue);
							emailSender = true;
						}
						
						String phoneNo = "<" + contactNo + "@sms.nhs.net>";
						String phoneValue = sColumnValue;
						if(emailSender && phoneNo.equalsIgnoreCase(phoneValue))
						{
							//System.out.println("\nEmail receipient id: "+ sColumnValue);
							emailReceiver=true;
						    
						}
						if(emailReceiver && sColumnValue.matches("CID One Time Password"))
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
				driverEmail.findElement(By.xpath(Constant.xpath_mailcatcherOTPPlainTextTab)).click();
				driverEmail.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				Thread.sleep(1000);
				
				driverEmail.switchTo().defaultContent();
				driverEmail.switchTo().frame(driverEmail.findElement(By.tagName(Constant.xpath_mailcatcherOTPiframe)));
				Thread.sleep(1000);

				//.*([0-9][0-9][0-9][0-9][0-9][0-9]).*
				
			

				OTPPageMessage= driverEmail.findElement(By.xpath(Constant.xpath_mailcatcherOTPContent)).getText();
				System.out.println("\n\nOPT email content: \n"+OTPPageMessage + "\n");

				Pattern pattern = Pattern.compile(".*([0-9][0-9][0-9][0-9][0-9][0-9]).*");
				Matcher matcher = pattern.matcher(OTPPageMessage);
				
				while (matcher.find()) 
				{
		            
					varOTP = matcher.group(1);
		            System.out.println(matcher.group(1));
		            break;
		        }
				
				System.out.println("OTP code is: "+ varOTP);
				//now click on verify button  and navigate to new link
				Thread.sleep(1000);

				
			}
			System.out.println("\nClosing webdriver......");

		  	driverEmail.get("about:config");
			//driver.quit();
		  	driverEmail.close();
			return varOTP;
			
	  }
	  
	  
}
