/**
 * @author Kaushal Chandwani
 *
 */
package com.cucumber.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.register.config.Constant;

public class CreateAccountPage 
{
	WebDriver driver;
	@FindBy(xpath = Constant.xpath_registerPage_EmailInputBox)
	private WebElement emailTextBox;

	@FindBy(xpath = Constant.xpath_registerPage_PasswordInputBox)
	private WebElement passwordTextBox;

	@FindBy(xpath = Constant.xpath_registerPage_confirmPasswordInputBox)
	private WebElement confirmPasswordTextBox;
	
	@FindBy(xpath = Constant.xpath_registerPage_registerButton)
	private WebElement registerButton;
	
	@FindBy(xpath = Constant.xpath_registerPage_backToSignInLink)
	private WebElement backToSignInLink;
	
	public CreateAccountPage(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void user_provides_the_email_as(String email) throws Throwable 
	{	
	    emailTextBox.sendKeys(email);
	}

	public void user_provides_the_password_as(String password) throws Throwable 
	{
	    passwordTextBox.sendKeys(password);
	}

	public void user_provides_the_confirm_password_again_as(String confirmPassword) throws Throwable 
	{
	    confirmPasswordTextBox.sendKeys(confirmPassword);
	}

	public void user_register() throws Throwable 
	{
		registerButton.click();
	}
	
	public void user_back_to_signIn_Link() throws Throwable
	{
		backToSignInLink.click();
	}
	
}
