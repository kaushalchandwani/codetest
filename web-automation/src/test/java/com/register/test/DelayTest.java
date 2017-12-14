package com.register.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.register.config.Constant;
import com.register.utility.MessageBox;
public class DelayTest 
{


	  public WebDriver driver;
	
	  @Test
	  public void delayMailExpiry() throws Exception 
	  {
		
		  MessageBox.printMessage("Wait time of 1 min to expire validate email.....");
	  }
	  
	  

}
