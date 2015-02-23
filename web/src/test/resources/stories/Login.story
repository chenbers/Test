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

Scenario: TC1242: Log In - Bookmark Page Entry
Given I am logged in
When I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I log back in
Then I validate I am on the Executive Dashboard page

Scenario: Log In - Bookmark Entry to Different Account - Need to create a test case
Given I am logged in
When I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I log back in under the editable account
Then I validate I am on the Executive Dashboard page