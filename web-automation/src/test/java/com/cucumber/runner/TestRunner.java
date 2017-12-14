/**
 * @author Kaushal Chandwani
 *
 */
package com.cucumber.runner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.register.config.Constant;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;



// Uncomment @RunWith if you are using Junit to run Test 
// @RunWith(Cucumber.class)
@CucumberOptions(features={"src//test//resources//features"}
,glue={"com.cucumber.stepdefinitions"}
,plugin = {"pretty", "html:target/cucumber"}
, tags ={"@web"}
)

@Test
public class TestRunner extends AbstractTestNGCucumberTests
{

	@BeforeClass
	public void before() {
	    System.out.println("------------------------------");
	    System.out.println("Starting -");
	    System.out.println("------------------------------");
	    
	}
	
	@AfterClass
	public void after() 
	{
		
	    System.out.println("------------------------------");
	    System.out.println("Ending -");
	    System.out.println("------------------------------");
	}
}


