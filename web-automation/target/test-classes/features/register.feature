#Author: your.email@your.domain.com
#Keywords Summary :
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
@web
	Feature: User Registeration 
		As I user 
		I want to register successfully with NHS Account 

	Scenario Outline: Sign up a new user
    Given the user is on login page of a NHS acoount
    When he click on register link
    And he provides the valid emailaddress as "<emailaddress>" 
    And he provides the valid password as  "<password>" 
    And he provides the valid confirm password again as "<confirmpassword>"
    And he submit register button
    Then he should be navigated to Validate Email page
    
	Examples:
			| emailaddress         | password    | confirmpassword |
      | testuser114@gmail.com| Welcome123# | Welcome123#     |
      
      
      
      
