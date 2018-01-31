/**
 * @author Kaushal Chandwani
 *
 */
package com.register.config;


public class Constant 
{
	public static final String url_nhsAccountCreation = "https://keycloak.dev1.signin.nhs.uk/cicauth/realms/NHS/account/dashboard";
	public static final String url_nhsAccountRealmAdmin = "https://keycloak.dev1.signin.nhs.uk/cicauth/admin/NHS/console";
	public static final String environmentVariable = "local";
	//public static final String environmentVariable = "remote";

	public static final String url__mailCatcherOTP_aws = "https://mail.dev1.signin.nhs.uk/";

	public static final String url__mailCatcher_local = "https://mail.dev1.signin.nhs.uk/";
	public static final String url__mailCatcher_remote = "http://172.17.0.1:1080";
	public static final String username_realmAdmin = "realmadmin";
	public static final String password_realmAdmin = "Welcome123";
	public static final String username_Patient = "cid.testuser3@gmail.com"; 
	public static final String password_Patient = "Welcome123#";
	public static final String new_password_Patient = "Backtologin123#"; 
	
	public static final String path_test_data ="src/test/resources/";
	
	public static final String xpath_loginPage_EmailInputBox = ".//*[@id='username']";
	public static final String xpath_loginPage_PasswordInputBox = ".//*[@id='password']";
	public static final String xpath_loginPage_loginButton = ".//*[@id='kc-login']";
	public static final String xpath_loginPage_registerLink = ".//*[@id='content']/div/div/div[1]/div/div/div/p/a/label";
	public static final String xpath_loginPage_logOutBanner = ".//*[@id='content']/div/div/div[1]/div/div[1]/div";
	public static final String xpath_loginPage_forgotPasswordLink = ".//*[@id='content']/div/div/div[1]/div/form/p/a";
	public static final String xpath_loginPage_resetPasswordLinkBanner = ".//*[@id='content']/div/div/div[1]/div/div[1]/p";
	public static final String xpath_loginPage_fogottenPasswordHeader = ".//*[@id='content']/div/div/div[1]/div/div[1]/h3";
	public static final String xpath_loginPage_checkEmailBanner = ".//*[@id='content']/div/div/div[1]/div/div[1]/h2";
	public static final String xpath_loginPage_emailVerificationBanner = ".//*[@id='content']/div/div/div[1]/div/div[1]/div";
	public static final String xpath_loginPage_validationMessageBanner = ".//*[@id='content']/div/div/div[1]/div/div[1]/div";
	
	public static final String xpath_registerPage_EmailInputBox = ".//*[@id='email']";
	public static final String xpath_registerPage_PasswordInputBox = ".//*[@id='password']";
	public static final String xpath_registerPage_confirmPasswordInputBox = ".//*[@id='password-confirm']";
	public static final String xpath_registerPage_registerButton = ".//*[@id='content']/div/div/div[1]/div/form/fieldset/fieldset/input";
	public static final String xpath_registerPage_passwordRules = ".//*[@id='content']/div/div/div[1]/div/form/fieldset/div[2]/ul";
	public static final String xpath_registerPage_backToSignInLink = ".//*[@id='content']/div/div/div[1]/div/form/fieldset/fieldset/p/a";
	
	public static final String xpath_confirmEmailPage_messageBanner = ".//*[@id='content']/div/div/div[1]/div/div[1]/div";
	public static final String xpath_confirmEmailPage_messageHeader = ".//*[@id='content']/div/div/div[1]/div/div[2]/h1";
	public static final String xpath_confirmEmailPage_resendEmailLink = ".//*[@id='content']/div/div/div[1]/div/div[2]/p[2]/a";
	public static final String xpath_confirmEmailPage_loginLink =".//*[@id='content']/div/div/div[1]/div/div[2]/p[3]/a";
	public static final String xpath_validateEmailPage3rdAttempt_messageBanner =".//*[@id='content']/div/div/div[1]/div/div/p[1]";
	public static final String xpath_validateEmailPage3rdAttempt_messageHeader =".//*[@id='content']/div/div/div[1]/div/div/h1/legend";
	public static final String xpath_validateEmailPage3rdAttempt_loginLink = ".//*[@id='content']/div/div/div[1]/div/div/p[2]/a";
	
	
	
	
	
	public static final String xpath_validateEmailExpirePage_messageBanner = ".//*[@id='content']/div/div/div[1]/div/div";
	public static final String xpath_validateEmailExpirePage_messageHeader = ".//*[@id='content']/div/div/div[1]/div/div/h1";
	public static final String xpath_validateEmailExpirePage_createAccountLink = ".//*[@id='content']/div/div/div[1]/div/p/a";
	
	
	public static final String xpath_dashboardPage_messageBanner = ".//*[@id='content']/div/div/div[1]/div/div/h1/legend";
	public static final String xpath_dashboardPage_userEmailId = ".//*[@id='content']/div/div/div[1]/div/div/p";
	public static final String xpath_dashboardPage_logoutLink = ".//*[@id='global-header']/div/div/p/a[3]";
	public static final String xpath_dashboardPage_accountUpdatedBanner = ".//*[@id='content']/div/div/div[1]/div/div/h1/legend";
	public static final String xpath_dashboardPage_accountLink = ".//*[@id='global-header']/div/div/p/a[2]";
	
	//Your account has been updated.
	
	public static final String xpath_mailcatcherTable = ".//*[@id='messages']/table/tbody";
	public static final String xpath_mailcatcherEmailContent = "html/body/table/tbody/tr/td/div/table/tbody/tr/td/table/tbody/tr/td/span";
	public static final String xpath_mailcatcherEmailHtmlTab = ".//*[@id='message']/header/nav/ul/li[1]/a";
	public static final String xpath_mailcatcheriframe = "iframe";
	public static final String xpath_mailcatcherVerifyButton = "html/body/table/tbody/tr/td/div/table/tbody/tr/td/table/tbody/tr/td/span/a[1]";
	public static final String xpath_mailcatcherVerifyLink = "html/body/table/tbody/tr/td/div/table/tbody/tr/td/table/tbody/tr/td/span/a[2]";
	public static final String xpath_mailcatcherResetPasswordButton = "html/body/table/tbody/tr/td/div/table/tbody/tr/td/table/tbody/tr/td/span/a";
	public static final String xpath_mailcatcherResetPasswordLink = "html/body/table/tbody/tr/td/div/table/tbody/tr/td/table/tbody/tr/td/span/strong/a";
	
	public static final String xpath_mailcatcherOTPPlainTextTab = ".//*[@id='message']/header/nav/ul/li[2]/a";
	public static final String xpath_mailcatcherOTPContent = "xhtml:html/xhtml:body";
	public static final String xpath_mailcatcherOTPiframe = "iframe";

	
	
	public static final String xpath_forgotPasswordPage_label1 = ".//*[@id='content']/div/div/div[2]/div/form/fieldset/h2";
	public static final String xpath_forgotPasswordPage_label2 = ".//*[@id='content']/div/div/div[2]/div/form/fieldset/p/label";
	public static final String xpath_forgotPasswordPage_emailIdInputBox = ".//*[@id='username']";
	public static final String xpath_forgotPasswordPage_submitButton = ".//*[@id='content']/div/div/div[2]/div/form/fieldset/div[2]/div[1]/input";
	public static final String xpath_forgotPasswordPage_backToLoginLink = ".//*[@id='content']/div/div/div[2]/div/form/fieldset/div[2]/div[2]/div/p/a";
	
	public static final String xpath_resetPasswordPage_label = ".//*[@id='content']/div/div/div[1]/div/form/fieldset/h1/legend";
	public static final String xpath_resetPasswordPage_contents = ".//*[@id='content']/div/div/div[1]/div/form/fieldset/div[1]";
	public static final String xpath_resetPasswordPage_newPasswordInputBox = ".//*[@id='password-new']";
	public static final String xpath_resetPasswordPage_retypePasswordInputBox = ".//*[@id='password-confirm']";
	public static final String xpath_resetPasswordPage_continueButton = ".//*[@id='content']/div/div/div[1]/div/form/input[3]";
	public static final String xpath_resetPasswordPage_validationMessage = ".//*[@id='content']/div/div/div[1]/div/div/div";
	
	
	
	public static final String xpath_expiredResetPasswordPage_label =".//*[@id='content']/div/div/div[1]/div/div/h2";
	public static final String xpath_expiredResetPasswordPage_contents = ".//*[@id='content']/div/div/div[1]/div/div";
	public static final String xpath_expiredResetPasswordPage_backToLoginResetLink = "	.//*[@id='backToApplication']";
	

	//Invalid mobile number. Please enter a valid UK mobile number.
	
	public static final String xpath_otpCodePage_legend = ".//*[@id='content']/div/div/div[1]/div/form/fieldset/h2";
	public static final String xpath_otpCodePage_content = ".//*[@id='content']/div/div/div[1]/div/form/fieldset/p/label";
	public static final String xpath_otpCodePage_otpCodeInputBox = ".//*[@id='otp_entered']";
	public static final String xpath_otpCodePage_continueButton = ".//*[@id='content']/div/div/div[1]/div/form/fieldset/div[2]/div/input";
	public static final String xpath_otpCodePage_invalidOtpCodeError_resendMessage = ".//*[@id='content']/div/div/div[1]/div/div/div";
	public static final String xpath_otpCodePage_resendOtpLink = ".//*[@id='content']/div/div/div[1]/div/p/a";
	public static final String xpath_otpCodePage_resendOtpMessage = ".//*[@id='content']/div/div/div[1]/div/p/label";

	//-->.//*[@id='content']/div/div/div[2]/div/form/fieldset/p
	public static final String xpath_otpPhoneNoPage_phoneNoInputBox = ".//*[@id='mobile_number']";
	public static final String xpath_otpPhoneNoPage_pageContent = ".//*[@id='content']/div/div/div[2]/div/form/fieldset/p/label";
	public static final String xpath_otpPhoneNoPage_pagelegend = ".//*[@id='content']/div/div/div[2]/div/form/fieldset/h2";
	public static final String xpath_otpPhoneNoPage_submitButtion = ".//*[@id='content']/div/div/div[2]/div/form/fieldset/div[2]/div/input";
	public static final String xpath_otpPhoneNoPage_invalidPhoneNoError = ".//*[@id='content']/div/div/div[2]/div/div/div";

	

	//Enter security code  We have sent a security code to your phone 074XXXXX456 
	
	
}
