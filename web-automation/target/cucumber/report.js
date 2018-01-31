$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("register.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#Author: Kaushal.Chandwani@nhs.net"
    },
    {
      "line": 2,
      "value": "#Keywords Summary : Cucumber feature file for user registeration process to NHS account"
    },
    {
      "line": 3,
      "value": "#Feature: List of scenarios."
    },
    {
      "line": 4,
      "value": "#Scenario: Business rule through list of steps with arguments."
    },
    {
      "line": 5,
      "value": "#Given: Some precondition step"
    },
    {
      "line": 6,
      "value": "#When: Some key actions"
    },
    {
      "line": 7,
      "value": "#Then: To observe outcomes or validation"
    },
    {
      "line": 8,
      "value": "#And,But: To enumerate more Given,When,Then steps"
    },
    {
      "line": 9,
      "value": "#Scenario Outline: List of steps for data-driven as an Examples and \u003cplaceholder\u003e"
    },
    {
      "line": 10,
      "value": "#Examples: Container for s table"
    },
    {
      "line": 11,
      "value": "#Background: List of steps run before each of the scenarios"
    },
    {
      "line": 12,
      "value": "#\"\"\" (Doc Strings)"
    },
    {
      "line": 13,
      "value": "#| (Data Tables)"
    },
    {
      "line": 14,
      "value": "#@ (Tags/Labels):To group Scenarios"
    },
    {
      "line": 15,
      "value": "#\u003c\u003e (placeholder)"
    },
    {
      "line": 16,
      "value": "#\"\""
    },
    {
      "line": 17,
      "value": "## (Comments)"
    },
    {
      "line": 19,
      "value": "#Feature Definition"
    }
  ],
  "line": 21,
  "name": "User Registration",
  "description": "As I user \r\nI want to register successfully with NHS Account",
  "id": "user-registration",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 25,
  "name": "Sign up a new user",
  "description": "",
  "id": "user-registration;sign-up-a-new-user",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 24,
      "name": "@web"
    }
  ]
});
formatter.step({
  "line": 26,
  "name": "the user is on login page of a NHS acoount",
  "keyword": "Given "
});
formatter.step({
  "line": 27,
  "name": "he click on register link for registration page navigation",
  "keyword": "When "
});
formatter.step({
  "line": 28,
  "name": "he provides the valid emailaddress as \"\u003cemailaddress\u003e\"",
  "keyword": "And "
});
formatter.step({
  "line": 29,
  "name": "he provides the valid password as  \"\u003cpassword\u003e\"",
  "keyword": "And "
});
formatter.step({
  "line": 30,
  "name": "he provides the valid confirm password again as \"\u003cconfirmpassword\u003e\"",
  "keyword": "And "
});
formatter.step({
  "line": 31,
  "name": "he submit register button",
  "keyword": "And "
});
formatter.step({
  "line": 32,
  "name": "he should be navigated to Validate Email page",
  "keyword": "Then "
});
formatter.examples({
  "line": 34,
  "name": "",
  "description": "",
  "id": "user-registration;sign-up-a-new-user;",
  "rows": [
    {
      "cells": [
        "emailaddress",
        "password",
        "confirmpassword"
      ],
      "line": 35,
      "id": "user-registration;sign-up-a-new-user;;1"
    },
    {
      "cells": [
        "testdata23@nhs.net",
        "Welcome123#",
        "Welcome123#"
      ],
      "line": 36,
      "id": "user-registration;sign-up-a-new-user;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 3071837653,
  "status": "passed"
});
formatter.scenario({
  "line": 36,
  "name": "Sign up a new user",
  "description": "",
  "id": "user-registration;sign-up-a-new-user;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 24,
      "name": "@web"
    }
  ]
});
formatter.step({
  "line": 26,
  "name": "the user is on login page of a NHS acoount",
  "keyword": "Given "
});
formatter.step({
  "line": 27,
  "name": "he click on register link for registration page navigation",
  "keyword": "When "
});
formatter.step({
  "line": 28,
  "name": "he provides the valid emailaddress as \"testdata23@nhs.net\"",
  "matchedColumns": [
    0
  ],
  "keyword": "And "
});
formatter.step({
  "line": 29,
  "name": "he provides the valid password as  \"Welcome123#\"",
  "matchedColumns": [
    1
  ],
  "keyword": "And "
});
formatter.step({
  "line": 30,
  "name": "he provides the valid confirm password again as \"Welcome123#\"",
  "matchedColumns": [
    2
  ],
  "keyword": "And "
});
formatter.step({
  "line": 31,
  "name": "he submit register button",
  "keyword": "And "
});
formatter.step({
  "line": 32,
  "name": "he should be navigated to Validate Email page",
  "keyword": "Then "
});
formatter.match({
  "location": "RegisterUserTest.the_user_is_on_login_page_of_a_NHS_acoount()"
});
formatter.result({
  "duration": 2884829849,
  "status": "passed"
});
formatter.match({
  "location": "RegisterUserTest.he_click_on_register_link_for_registration_page_navigation()"
});
formatter.result({
  "duration": 4804689277,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "testdata23@nhs.net",
      "offset": 39
    }
  ],
  "location": "RegisterUserTest.he_provides_the_valid_emailaddress_as(String)"
});
formatter.result({
  "duration": 1162445962,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Welcome123#",
      "offset": 36
    }
  ],
  "location": "RegisterUserTest.he_provides_the_valid_password_as(String)"
});
formatter.result({
  "duration": 140543648,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Welcome123#",
      "offset": 49
    }
  ],
  "location": "RegisterUserTest.he_provides_the_valid_confirm_password_again_as(String)"
});
formatter.result({
  "duration": 82047976,
  "status": "passed"
});
formatter.match({
  "location": "RegisterUserTest.he_submit_register_button()"
});
formatter.result({
  "duration": 2581852966,
  "status": "passed"
});
formatter.match({
  "location": "RegisterUserTest.he_should_be_navigated_to_Validate_Email_page()"
});
formatter.result({
  "duration": 112761057,
  "status": "passed"
});
formatter.after({
  "duration": 459945726,
  "status": "passed"
});
});