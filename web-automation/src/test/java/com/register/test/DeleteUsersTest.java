package com.register.test;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.register.config.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DeleteUsersTest 
{
	  public WebDriver driverUsers;

		 
	    @Test
		public void deleteUsersProfile() throws Exception
		{
		
					
			//String url_admin_console ="https://blue.testlab.nhs.uk/cicauth/admin/NHS/console";
			//String UserName_realm_admin="realmadmin";
			//String Password_realm_admin ="Welcome123";
			//String Patient_email_id="cid.testuser1@gmail.com";
			boolean deleteUserStatus = false;	
			
				
				
				
				driverUsers.get(Constant.url_nhsAccountRealmAdmin);
				driverUsers.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driverUsers.manage().window().maximize();

				driverUsers.findElement(By.xpath(".//*[@id='username']")).sendKeys(Constant.username_realmAdmin);
				driverUsers.findElement(By.xpath(".//*[@id='password']")).sendKeys(Constant.password_realmAdmin);	
				Thread.sleep(2000);
				driverUsers.findElement(By.xpath(".//*[@id='kc-login']")).click();
				driverUsers.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			    Thread.sleep(2000);
			    //user tab on left
				driverUsers.findElement(By.xpath(".//*[@id='view']/div[2]/div[3]/ul/li[2]/a")).click();
				driverUsers.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			    Thread.sleep(1000);
			    //view all user link
			    driverUsers.findElement(By.xpath(".//*[@id='viewAllUsers']")).click();
				driverUsers.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			    Thread.sleep(1000);
			    
			    //---> .//*[@id='view']/div[1]/table/tbody/tr[1]/td[1]
			    
				List<WebElement> rows =   (List<WebElement>) driverUsers.findElements(By.xpath(".//*[@id='view']/div[1]/table/tbody/tr"));
				int countRows = rows.size();
				//System.out.println("ROW COUNT : "+countRows);
				
				List<WebElement> columns = driverUsers.findElements(By.xpath(".//*[@id='view']/div[1]/table/tbody/tr[1]/td"));
				int countColumns = columns.size();
				//System.out.println("ROW COUNT : "+countColumns);
				
				for (int i=1;i<countRows;i++)
				{
					boolean deleteVariable=false;
					for (int j=1;j<=countColumns;j++)
					{
						String xpathVariable =".//*[@id='view']/div[1]/table/tbody/tr[" + i + "]/td["+ j +"]";
						String sColumnValue="";
						boolean elementStatus = com.register.utility.CheckElement.existsElement(xpathVariable,driverUsers);
						if(elementStatus)
						{
							sColumnValue= driverUsers.findElement(By.xpath(xpathVariable)).getText();
							//System.out.println("Column value: "+ sColumnValue);
							if(sColumnValue.equalsIgnoreCase(Constant.username_Patient))
							{
								deleteVariable = true;
							}
							if(deleteVariable && sColumnValue.equalsIgnoreCase("Delete"))
							{
							    driverUsers.findElement(By.xpath(xpathVariable)).click();
								Thread.sleep(1000);
	
							    //html/body/div[5]/div/div/div[3]/button[2] delete button 
							    //html/body/div[5]/div/div/div[2] delete message
							    //html/body/div[5]/div/div/div[3]/button[1] cancel button
							    //html/body/div[5]/div/div/div[1] delete user label
							    String deletePopup = driverUsers.findElement(By.xpath("html/body/div[5]/div/div/div[1]")).getText();
							    if(deletePopup.equalsIgnoreCase("Delete User"))
							    {
								    driverUsers.findElement(By.xpath("html/body/div[5]/div/div/div[3]/button[2]")).click();
								    deleteUserStatus = true;
									Thread.sleep(1000);
	
								    break;
							    }
							    
							}
							//summaryDescription = summaryDescription + "  " + sColumnValue;
							
						}
						if(deleteUserStatus)
						{
							break;
						}
					}
					
				}
				//users profile tab
				driverUsers.findElement(By.xpath("html/body/nav/div[2]/ul/li/a")).click();
				Thread.sleep(3000);
				//logout
				driverUsers.findElement(By.xpath("html/body/nav/div[2]/ul/li/ul/li[4]/a")).click();
				Thread.sleep(3000);
	
				if(!deleteUserStatus)
				{
					System.out.println("User already deleted or not present");
					Reporter.log("User already deleted or not present", true );
				}
				else
				{
					System.out.println("User successfully deleted");
					Reporter.log("User successfully deleted from admin console", true );

				}
				
				//driverUsers.close();
				//driverUsers.quit();
				//return deleteUserStatus;
			
		
			
		    
		}
		

		  @BeforeMethod
		  public void beforeMethod() 
		  {
			
			
			System.out.println("\nOpening webdriver \n");

			
			if(Constant.environmentVariable.equalsIgnoreCase("local"))
		  	{
				System.setProperty("webdriver.chrome.driver", "exe/chromedriver.exe"); //chromedriver.exe set property path
				driverUsers = new ChromeDriver();
		  	}
		  	else
		  	{
		  		System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", true);
				driverUsers = new FirefoxDriver(capabilities);
		  	}
			
		  }
		
		  @AfterMethod
		  public void afterMethod() 
		  {
			  System.out.println("\nClosing webdriver......");

			  driverUsers.get("about:config");
			  driverUsers.close();
		  }
		
}
