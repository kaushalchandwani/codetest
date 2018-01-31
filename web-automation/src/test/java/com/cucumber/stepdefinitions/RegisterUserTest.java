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
import com.register.test.VerifyEmail;
import com.register.test.VerifyOTPCode;
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
		System.out.println("step 1: ");
		System.out.println("\nOpening url: "+ Constant.url_nhsAccountCreation);
		
		driver.get(Constant.url_nhsAccountCreation);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Thread.sleep(1000);
				
		if(driver.getTitle().equalsIgnoreCase("Sign in to your NHS Account"))
		{
			System.out.println("\nLogin Page: Sign in to your NHS Account");
			Reporter.log( "Navigated to login Page: Sign in to your NHS Account", true );

		}
		else
		{
			throw new NoSuchElementException("Sign in url is not working");
		}
		
	}
	

	@When("^he click on register link for registration page navigation$")
	public void he_click_on_register_link_for_registration_page_navigation() throws Throwable 
	{
		System.out.println("step 2: ");

		driver.findElement(By.xpath(Constant.xpath_loginPage_registerLink)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(1000);
						
		if(driver.getTitle().equalsIgnoreCase("Create an NHS account"))
		{
			System.out.println("\nCreate an NHS account");
			Reporter.log( "Navigated to Create an NHS account Page", true );
			String passwordRulesMessage = driver.findElement(By.xpath(Constant.xpath_registerPage_passwordRules)).getText();
			if(passwordRulesMessage.equalsIgnoreCase("at least 8 characters\nat least one capital letter\nat least one number\nat least one symbol, for example: ?!£%"))
			{
				Reporter.log( "Password Rules displayed", true );
				MessageBox.printMessage("Password Rules Message :- \nYour password must have: \n"+ passwordRulesMessage,3000);
			}
			else
			{
				throw new NoSuchElementException("Password Rules message not displayed");
			}
		}
		else
		{
			throw new NoSuchElementException("Create an NHS account url not working");
		}
	}



	

	@When("^he provides the valid emailaddress as \"([^\"]*)\"$")
	public void he_provides_the_valid_emailaddress_as(String arg1) throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("step 3: "+arg1);
		driver.findElement(By.xpath(Constant.xpath_registerPage_EmailInputBox)).sendKeys(arg1);
		Reporter.log( "User Credentials for registration:- \nemail: "+arg1);

		Thread.sleep(1000);
				
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

	@When("^he provides the valid emailaddress on login page as \"([^\"]*)\"$")
	public void he_provides_the_valid_emailaddress_on_login_page_as(String arg1) throws Throwable 
	{
		driver.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).clear();
		driver.findElement(By.xpath(Constant.xpath_loginPage_EmailInputBox)).sendKeys(arg1);
		
		
	}

	@When("^he provides the valid password on login page as \"([^\"]*)\"$")
	public void he_provides_the_valid_password_on_login_page_as(String arg1) throws Throwable 
	{
		driver.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).clear();
		driver.findElement(By.xpath(Constant.xpath_loginPage_PasswordInputBox)).sendKeys(arg1);

	}

	@When("^he submit login button$")
	public void he_submit_login_button() throws Throwable 
	{
		driver.findElement(By.xpath(Constant.xpath_loginPage_loginButton)).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	
	@When("^he is navigated to twoFA page to input phoneno as \"([^\"]*)\"$")
	public void he_is_navigated_to_twoFA_page_to_input_phoneno_as(String arg1) throws Throwable   
	{
		Thread.sleep(1000);

		
		if(driver.getTitle().equalsIgnoreCase("Security code"))
		{
			String pagelegend = driver.findElement(By.xpath(Constant.xpath_otpPhoneNoPage_pagelegend)).getText();
			String pageContent = driver.findElement(By.xpath(Constant.xpath_otpPhoneNoPage_pageContent)).getText();
			
			if(pagelegend.equalsIgnoreCase("Enter your phone number") && pageContent.equalsIgnoreCase("We'll send you a security code to your mobile. This is to make sure that the mobile number you enter belongs to you.\n\nWe'll send you a code every time you sign in, to make sure its not someone pretending to be you."))
			{
				System.out.println("\n2FA message: " +pagelegend + "\n" + pageContent );
				
				System.out.println("phone no: "+arg1);
				String contactNo = arg1;
				
				Thread.sleep(1000);
				System.out.println("phone no modified"+contactNo);
				MessageBox.printMessage("Contact number: "+contactNo,3000);


				driver.findElement(By.xpath(Constant.xpath_otpPhoneNoPage_phoneNoInputBox)).sendKeys(contactNo);
				Thread.sleep(2000);

				driver.findElement(By.xpath(Constant.xpath_otpPhoneNoPage_submitButtion)).click();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				boolean elementStatus = com.register.utility.CheckElement.existsElement(Constant.xpath_otpPhoneNoPage_invalidPhoneNoError,driver);
				if(elementStatus)
				{
					if(driver.getTitle().equalsIgnoreCase("Security code") && driver.findElement(By.xpath(Constant.xpath_otpPhoneNoPage_invalidPhoneNoError)).getText().equalsIgnoreCase("Invalid mobile number. Please enter a valid UK mobile number."))
					{
						System.out.println("Invalid phone no");
						Thread.sleep(1000);

					}
					
				}
				else
				{
					System.out.println("valid phone no, navigated to security code page");

				}
			}
			else
			{
				throw new NoSuchElementException("Page content incorrect");
			}
		}
		else
		{
			throw new NoSuchElementException("Page title incorrect");
		}
		
	}



	@When("^he is navigated to twoFA page to input security code for phoneno as \"([^\"]*)\"$")
	public void he_is_navigated_to_twoFA_page_to_input_security_code_for_phoneno_as(String arg1) throws Throwable 
	{
		Thread.sleep(1000);

		if(driver.getTitle().equalsIgnoreCase("Security code"))
		{
			String pagelegend = driver.findElement(By.xpath(Constant.xpath_otpCodePage_legend)).getText();
			String pageContent = driver.findElement(By.xpath(Constant.xpath_otpCodePage_content)).getText();
			if(pagelegend.equalsIgnoreCase("Security code") && pageContent.toLowerCase().contains(("Enter security code\n\nWe have sent a security code to your phone").toLowerCase()))
			{
				String securityCode = VerifyOTPCode.fetchOTPcode(arg1);
				System.out.println("security code is: "+securityCode);
				Thread.sleep(1000);
				MessageBox.printMessage("Security code is: "+securityCode,3000);

				driver.findElement(By.xpath(Constant.xpath_otpCodePage_otpCodeInputBox)).sendKeys(securityCode);
				Thread.sleep(2000);

				driver.findElement(By.xpath(Constant.xpath_otpCodePage_continueButton)).click();

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				Thread.sleep(1000);
				System.out.println("security code entered correctly and navigated to next page");

			}
			else
			{
				throw new NoSuchElementException("Security no Page content incorrect");

			}
		}
		else
		{
			throw new NoSuchElementException("Page title incorrect");

		}
	}
	@When("^he is navigated to twoFA page to input resend security code for phoneno as \"([^\"]*)\"$")
	public void he_is_navigated_to_twoFA_page_to_input_resend_security_code_for_phoneno_as(String arg1) throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		Thread.sleep(1000);

		if(driver.getTitle().equalsIgnoreCase("Security code"))
		{
			String pagelegend = driver.findElement(By.xpath(Constant.xpath_otpCodePage_legend)).getText();
			String pageContent = driver.findElement(By.xpath(Constant.xpath_otpCodePage_content)).getText();
			if(pagelegend.equalsIgnoreCase("Security code") && pageContent.toLowerCase().contains(("Enter security code\n\nWe have sent a security code to your phone").toLowerCase()))
			{
				String securityCode = VerifyOTPCode.fetchOTPcode(arg1);
				System.out.println("security code is: "+securityCode);
				Thread.sleep(1000);
				MessageBox.printMessage("Security code is: "+securityCode,3000);
				
				String resendSecurityCode = "";
				String resendMessage = driver.findElement(By.xpath(Constant.xpath_otpCodePage_resendOtpMessage)).getText();
				String resendLinkContent = driver.findElement(By.xpath(Constant.xpath_otpCodePage_resendOtpLink)).getText();
				if(resendMessage.equalsIgnoreCase("Text messages sometimes take a few minutes to arrive. If you do not receive the text message,") && resendLinkContent.equalsIgnoreCase("you can resend it"))
				{
					
					MessageBox.printMessage("Resend seucrity code \n Waittime for 2 minutes ", 120001);
					driver.findElement(By.xpath(Constant.xpath_otpCodePage_resendOtpLink)).click();
					resendSecurityCode = VerifyOTPCode.fetchOTPcode(arg1);

				}
				
				if(securityCode.equals(resendSecurityCode))
				{
					driver.findElement(By.xpath(Constant.xpath_otpCodePage_otpCodeInputBox)).sendKeys(securityCode);
					Thread.sleep(2000);

					driver.findElement(By.xpath(Constant.xpath_otpCodePage_continueButton)).click();

					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Thread.sleep(1000);
					
					System.out.println("Resend security code entered same and navigated to next page");

					
				}
				else
				{
					//entering old security code

					driver.findElement(By.xpath(Constant.xpath_otpCodePage_otpCodeInputBox)).sendKeys(securityCode);
					Thread.sleep(2000);

					driver.findElement(By.xpath(Constant.xpath_otpCodePage_continueButton)).click();

					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Thread.sleep(1000);
					boolean elementStatus = com.register.utility.CheckElement.existsElement(Constant.xpath_otpCodePage_invalidOtpCodeError_resendMessage,driver);
					if(elementStatus)
					{
						String errorMessageInvalidOTPCode = driver.findElement(By.xpath(Constant.xpath_otpCodePage_invalidOtpCodeError_resendMessage)).getText();
						
						if(errorMessageInvalidOTPCode.equalsIgnoreCase("The code entered is not valid. Please try again."))
						{
							System.out.println("Error message: " + errorMessageInvalidOTPCode);
						}
					}
					
					//entering new security code
					driver.findElement(By.xpath(Constant.xpath_otpCodePage_otpCodeInputBox)).sendKeys(resendSecurityCode);
					Thread.sleep(1000);

					driver.findElement(By.xpath(Constant.xpath_otpCodePage_continueButton)).click();

					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Thread.sleep(1000);
					
				}
				

			}
			else
			{
				throw new NoSuchElementException("Security no Page content incorrect");

			}
		}
		else
		{
			throw new NoSuchElementException("Page title incorrect");

		}
	}
	
	@Then("^he should be navigated to account dashboard page$")
	public void he_should_be_navigated_to_account_dashboard_page() throws Throwable 
	{
		Thread.sleep(2000);
		if(driver.getTitle().equalsIgnoreCase("NHS Account Management"))
		{
			System.out.println("\nNHS Account Management Page");
			Reporter.log( "Login with valid User credentials ", true );

			String DashboardMessage = driver.findElement(By.xpath(Constant.xpath_dashboardPage_messageBanner)).getText();
			if(DashboardMessage.equalsIgnoreCase("Welcome to your NHS"))
			{
				String WelcomeMessage = driver.findElement(By.xpath(Constant.xpath_dashboardPage_userEmailId)).getText();

				
				System.out.println("\nWelcome to your NHS");
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
			throw new NoSuchElementException("\nNHS Account Management url is not working");
		}
	}

	
	@Given("^the user is login to his mail account to verify NHS account$")
	public void the_user_is_login_to_his_mail_account_to_verify_NHS_account() throws Throwable 
	{	
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
		
	}


	
	@When("^he click on verify email link to verify NHS account with valid emailaddress as \"([^\"]*)\"$")
	public void he_click_on_verify_email_link_to_verify_NHS_account_with_valid_emailaddress_as(String arg1) throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		driver = VerifyEmail.verifyEmailLink(driver,arg1);
		
	}

	@Then("^he should be navigated to Account verified page$")
	public void he_should_be_navigated_to_Account_verified_page() throws Throwable 
	{
	    // Write code here that turns the phrase above into concrete actions
		String pageTitle = driver.getTitle().toLowerCase();
		if(pageTitle.contains(("Log in to NHS").toLowerCase()))
		{
			//System.out.println("Account Verified");
			String verificationMessage  = driver.findElement(By.xpath(Constant.xpath_loginPage_validationMessageBanner)).getText();
			System.out.println("Account Verification message: "+ verificationMessage);
			Thread.sleep(2000);

		
		}
	}
}
