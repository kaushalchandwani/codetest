/**
 * @author Kaushal Chandwani
 *
 */
package com.cucumber.stepdefinitions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;




import org.testng.Reporter;

import com.register.config.Constant;
import com.register.utility.MessageBox;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RegisterUserTest 
{
	public WebDriver driver;

	@Before
	public void before() 
	{
		System.out.println("\nOpening webdriver \n");
		
		if(Constant.environmentVariable.equalsIgnoreCase("local"))
	  	{
			System.setProperty("webdriver.chrome.driver", "exe/chromedriver.exe"); //chromedriver.exe set property path
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			driver = new ChromeDriver(options);
			//driver = new ChromeDriver();
	  	}
	  	else
	  	{
	  		System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driver = new FirefoxDriver(capabilities);
	  	}    
	}
	 
	@After
	public void after() 
	{
		System.out.println("\nClosing webdriver......");
		driver.get("about:config");
		//driver.quit();
		driver.close();
	}
	    
	@Given("^the user is on login page of a NHS acoount$")
	public void the_user_is_on_login_page_of_a_NHS_acoount() throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("step 1: ");
		System.out.println("\nOpening url: "+ Constant.url_nhsAccountCreation);
		
		driver.get(Constant.url_nhsAccountCreation);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
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
		
	}

	@When("^he click on register link$")
	public void he_click_on_register_link() throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("step 2: ");

		driver.findElement(By.xpath(Constant.xpath_loginPage_registerLink)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
		Thread.sleep(2000);
						
		if(driver.getTitle().equalsIgnoreCase("Register with NHS"))
		{
			System.out.println("\nRegister Page");
			Reporter.log( "Navigated to Register Page", true );
			String passwordRulesMessage = driver.findElement(By.xpath(Constant.xpath_registerPage_passwordRules)).getText();
			if(passwordRulesMessage.equalsIgnoreCase("at least 8 characters\nat least one capital letter\nat least one number\nat least one symbol, for example: ?!£%"))
			{
				Reporter.log( "Password Rules displayed", true );
				MessageBox.printMessage("Password Rules Message :- \nYour password must have: \n"+ passwordRulesMessage);
			}
			else
			{
				throw new NoSuchElementException("Password Rules message not displayed");
			}
		}
		else
		{
			throw new NoSuchElementException("Register url not working");
		}
	}

	@When("^he provides the valid emailaddress as \"([^\"]*)\"$")
	public void he_provides_the_valid_emailaddress_as(String arg1) throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("step 3: "+arg1);
		driver.findElement(By.xpath(Constant.xpath_registerPage_EmailInputBox)).sendKeys(arg1);
		Reporter.log( "User Credentials for registration:- \nemail: "+arg1);

		Thread.sleep(2000);
				
	}

	@When("^he provides the valid password as  \"([^\"]*)\"$")
	public void he_provides_the_valid_password_as(String arg1) throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("step 4: "+arg1);
		driver.findElement(By.xpath(Constant.xpath_registerPage_PasswordInputBox)).sendKeys(arg1);
		Reporter.log( "\n Password: "+arg1, true );

	}

	@When("^he provides the valid confirm password again as \"([^\"]*)\"$")
	public void he_provides_the_valid_confirm_password_again_as(String arg1) throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("step 5: "+arg1);
		driver.findElement(By.xpath(Constant.xpath_registerPage_confirmPasswordInputBox)).sendKeys(arg1);
		Reporter.log( "\nConfirm Password: "+arg1, true );

	}

	@When("^he submit register button$")
	public void he_submit_register_button() throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("step 6: ");
		driver.findElement(By.xpath(Constant.xpath_registerPage_registerButton)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		Thread.sleep(2000);
	}

	@Then("^he should be navigated to Validate Email page$")
	public void he_should_be_navigated_to_Validate_Email_page() throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("step 7: ");
		if(driver.getTitle().equalsIgnoreCase("Validate your email"))
		{
			System.out.println("\nValidate email Page");
			Reporter.log( "Navigated to Validate email Page", true );

			String confirmMailMessage = driver.findElement(By.xpath(Constant.xpath_confirmEmailPage_messageBanner)).getText();
			String confirmMessage = driver.findElement(By.xpath(Constant.xpath_confirmEmailPage_messageHeader)).getText();
			//re-send mail .//*[@id='content']/div/div/div[1]/div/div[2]/p[1]/a
			
			if(confirmMessage.equalsIgnoreCase("Validate your email") && confirmMailMessage.equalsIgnoreCase("You need to validate your email address before you can log in. Please check your email."))
			{
				System.out.println("\nValidate email Message displayed");
				Reporter.log( "Verification email sent to user", true );

			}
			else
			{
				throw new NoSuchElementException("Validate email not send");
			}

		}
		else
		{
			throw new NoSuchElementException("Validate email url not working");
		}
	}


}
