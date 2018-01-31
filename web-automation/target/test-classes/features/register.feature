#Author: Kaushal.Chandwani@nhs.net
#Keywords Summary : Cucumber feature file for user registeration process to NHS account
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios 
#<> (placeholder)
#""
## (Comments)

#Feature Definition 

	Feature: User Registration 
		As I user 
		I want to register successfully with NHS Account 
@web
	Scenario Outline: Sign up a new user
    Given the user is on login page of a NHS acoount
    When he click on register link for registration page navigation
    And he provides the valid emailaddress as "<emailaddress>" 
    And he provides the valid password as  "<password>" 
    And he provides the valid confirm password again as "<confirmpassword>"
    And he submit register button
    Then he should be navigated to Validate Email page
    
	Examples:
			| emailaddress         | password    | confirmpassword |
      | testdata23@nhs.net| Welcome123# | Welcome123#     |
      
@web2
	Scenario Outline: Verify email for user registration process
    Given the user is login to his mail account to verify NHS account
    When he click on verify email link to verify NHS account with valid emailaddress as "<emailaddress>"
    Then he should be navigated to Account verified page
    
  Examples:
			| emailaddress |
			| testdata16@nhs.net|
    
@web2
  Scenario Outline: Login first time with two factor authentication process
    Given the user is on login page of a NHS acoount
    When he provides the valid emailaddress on login page as "<emailaddress>" 
    And he provides the valid password on login page as "<password>" 
    And he submit login button
    And he is navigated to twoFA page to input phoneno as "<contactno>" 
    And he is navigated to twoFA page to input security code for phoneno as "<contactno>"
    Then he should be navigated to account dashboard page
      
  Examples:
			| emailaddress         | password    | contactno |
      | testdata16@nhs.net| Welcome123# | +447567898743 |
      
@web2
  Scenario Outline: Login second time with two factor authentication process
    Given the user is on login page of a NHS acoount
    When he provides the valid emailaddress on login page as "<emailaddress>" 
    And he provides the valid password on login page as "<password>" 
    And he submit login button
    And he is navigated to twoFA page to input security code for phoneno as "<contactno>"
    Then he should be navigated to account dashboard page
    
    Examples:
			| emailaddress         | password    | contactno |
      | testdata16@nhs.net| Welcome123# | +447987898789 |

@web2
  Scenario Outline: Login second time with two factor authentication process and resend OTP code
    Given the user is on login page of a NHS acoount
    When he provides the valid emailaddress on login page as "<emailaddress>" 
    And he provides the valid password on login page as "<password>" 
    And he submit login button
    And he is navigated to twoFA page to input resend security code for phoneno as "<contactno>"
    Then he should be navigated to account dashboard page
    
    Examples:
			| emailaddress         | password    | contactno |
      | testdata10@nhs.net| Welcome123# | +447987898789 |
