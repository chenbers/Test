Login Test Cases for TF388/TF444 

Meta:
@page login
@testFolder TF388
@testFolder TF444


Narrative:

In order to show the inthinc portal login features, As a varying types of users, I want to see valid and invalid logins

Scenario: TC1250 - Log In - UI
Given I am on the Login page
Then I validate the User Name textfield is present
And I validate the Password textfield is present
And I validate the Login button is present
And I validate the Forgot Username Or Password link is present
And I validate the Copyright text is present
And I validate the Privacy link is present
And I validate the Terms Of Service link is present



