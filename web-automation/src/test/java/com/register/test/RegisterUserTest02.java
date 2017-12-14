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

import com.register.config.Constant;

public class RegisterUserTest02 
{
	public WebDriver driver;
	
	  @Test
	  public void userRegistration() throws Exception 
	  {
			
			System.out.println("\nUser emailid: "+ Constant.username_Patient);
					
			driver.get("https://blue.testlab.nhs.uk/cicauth/realms/NHS/account/");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Thread.sleep(3000);
			
			
			if(driver.getTitle().equalsIgnoreCase("Log in to NHS"))
			{
				System.out.println("\nLogin Page");
			}
			else
			{
				throw new NoSuchElementException("Login url is not working");
			}
			
			
			
			driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div/div[2]/div/a/label")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Thread.sleep(2000);
			
			

			
			if(driver.getTitle().equalsIgnoreCase("Register with NHS"))
			{
				System.out.println("\nRegister Page");
	
			}
			else
			{
				throw new NoSuchElementException("Register url not working");
			}
			//clear fields
			driver.findElement(By.xpath(".//*[@id='email']")).clear();
			driver.findElement(By.xpath(".//*[@id='password']")).clear();
			driver.findElement(By.xpath(".//*[@id='password-confirm']")).clear();
			//type only username 
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys("dummy"+Constant.username_Patient);
			
	
			Thread.sleep(2000);
	
			driver.findElement(By.xpath(".//*[@id='kc-form-wrapper']/form/div[4]/div[1]/input")).click();
			Thread.sleep(2000);
			
		
			if(driver.getTitle().equalsIgnoreCase("Register with NHS"))
			{
				System.out.println("\nRemain on Register Page");
				String errorMessage = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div/div[1]/div/div")).getText();
				
				
				if(errorMessage.toLowerCase().contains(("Please specify password.").toLowerCase())) 
				{
					System.out.println("\nPlease specify password.");
					Reporter.log("\n\nScenario 1: Valid email and blank password ",true);
					Reporter.log("Email address: "+ "dummy"+Constant.username_Patient,true);
					Reporter.log("Password: ",true);
					Reporter.log( "Validation message : Please specify password.", true );
					Reporter.log("-------------------------------------------------------------------------------",true);

				}
				else
				{
					throw new NoSuchElementException("Validation check failed");
				}
			}
			else
			{
				throw new NoSuchElementException("Register url not working");
			}
			driver.navigate().refresh();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			//clear fields
			driver.findElement(By.xpath(".//*[@id='email']")).clear();
			driver.findElement(By.xpath(".//*[@id='password']")).clear();
			driver.findElement(By.xpath(".//*[@id='password-confirm']")).clear();
			//type only passwords 
			driver.findElement(By.xpath(".//*[@id='password']")).sendKeys(Constant.password_Patient);
			driver.findElement(By.xpath(".//*[@id='password-confirm']")).sendKeys(Constant.password_Patient);
			Thread.sleep(2000);
			
			driver.findElement(By.xpath(".//*[@id='kc-form-wrapper']/form/div[4]/div[1]/input")).click();
			Thread.sleep(2000);
			
			if(driver.getTitle().equalsIgnoreCase("Register with NHS"))
			{
				System.out.println("\nRemain on Register Page");
				String errorMessage = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div/div[1]/div/div")).getText();
				
				
				if(errorMessage.toLowerCase().contains(("Please specify email.").toLowerCase())) 
				{
					System.out.println("\nPlease specify email.");
					Reporter.log("\n\nScenario 2: Blank email and valid password ",true);
					Reporter.log("Email address: ",true);
					Reporter.log("Password: " +Constant.password_Patient,true  );
					Reporter.log( "Validation message : Please specify email.", true );
					Reporter.log("-------------------------------------------------------------------------------",true);

				}
				else
				{
					throw new NoSuchElementException("Validation check failed");
				}
			}
			else
			{
				throw new NoSuchElementException("Register url not working");
			}
			
			
			
			driver.navigate().refresh();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			
			//clear fields and click on submit, dont type any inputs 
			driver.findElement(By.xpath(".//*[@id='email']")).clear();
			driver.findElement(By.xpath(".//*[@id='password']")).clear();
			driver.findElement(By.xpath(".//*[@id='password-confirm']")).clear(); 
			driver.findElement(By.xpath(".//*[@id='kc-form-wrapper']/form/div[4]/div[1]/input")).click();
			Thread.sleep(2000);
			if(driver.getTitle().equalsIgnoreCase("Register with NHS"))
			{
				System.out.println("\nRemain on Register Page");
				String errorMessage = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div/div[1]/div/div")).getText();
				
				
				if((errorMessage.toLowerCase().contains(("Please specify password.").toLowerCase())) && (errorMessage.toLowerCase().contains(("Please specify email.").toLowerCase())) )
				{
					System.out.println("\nPlease specify email. Please specify password.");
					Reporter.log("\n\nScenario 3: Blank email and password ",true);

					Reporter.log("Email address: ",true);
					Reporter.log("Password: ",true );
					Reporter.log( "Validation message : Please specify email. Please specify password.", true );
				}
				else
				{
					throw new NoSuchElementException("Validation check failed");
				}
			}
			else
			{
				throw new NoSuchElementException("Register url not working");
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
