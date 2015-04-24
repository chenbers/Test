Login Test Cases for TF448

Meta:
@page login
@testFolder TF448

Narrative: Test the forgot user name or password features

Scenario: TC1185: Log In - Forgot User Name or Password - Email Address Format Error
Given I am on the Login page
When I click the Forgot Username Or Password link
And I type an "Invalid Format Email Address" into the Email Address textfield
And I click the Send button
Then I validate the Error text is "Incorrect format (jdoe@tiwipro.com)"
