Login Test Cases for TF388/TF444 

Meta:
@page login
@testFolder TF388
@testFolder TF444


Narrative:

In order to show the inthinc portal login features, As a varying types of users, I want to see valid and invalid logins

Scenario: TC1241: Log In - Blank User Name and Password Error
Given I am on the Login page
When I click the Login button
Then I validate the Error text contains "Username is a required field."
And I validate the Error text contains "Password is a required field."
And I validate the User Name textfield is ""
And I validate the Password textfield is ""