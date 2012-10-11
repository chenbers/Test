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
And I validate I am on the Login page
Given I am logged in
Then I validate I am on the Executive Dashboard page

Scenario: TC1243: Log In - Default Command Button
Given I am on the Login page
When I type "SecondPrime" into the User Name textfield
And I type "2ut2CFmnH$f!" into the Password textfield
And I press the Enter Key
Then I validate I am on the Executive Dashboard page

Scenario: TC1245: Log In - Invalid Password Error
Given I am on the Login page
When I type "secondPrime" into the User Name textfield
And I type "this will never be a valid password" into the Password textfield
And I click the Login button
Then I validate the Error text is "The credentials you provided cannot be determined to be authentic."
And I validate the User Name textfield is "secondPrime"
And I validate the Password textfield is ""

Scenario: TC1246: Log In - Invalid User Name Error
Given I am on the Login page
And I type "this will never be a valid User Name" into the User Name textfield
And I type "password" into the Password textfield
And I click the Login button
Then I validate the Error text is "The credentials you provided cannot be determined to be authentic."
And I validate the User Name textfield is "this will never be a valid User Name" 
And I validate the Password textfield is "" 

Scenario: TC1247: Log In - Log In Button
Given I am logged in
Then I validate I am on the Executive Dashboard page

Scenario: TC1248: Log In - Password Incorrect Case Error
Given I am on the Login page
When I type "secondPrime" into the User Name textfield
And I type "2UT2CFMNH$F!" into the Password textfield
And I click the Login button
Then I validate the Error text contains "The credentials you provided cannot be determined to be authentic."
And I validate the User Name textfield is "secondPrime" 
And I validate the Password textfield is "" 

Scenario: TC1250 - Log In - UI
Given I am on the Login page 
Then I validate the User Name textfield is present
And I validate the Password textfield is present
And I validate the Login button is present
And I validate the Legal Notice link is present
And I validate the Privacy Policy link is present
And I validate the Forgot Username Or Password link is present
And I validate the Logo text is present

Scenario: TC1251 - Log In - User Name Incorrect Case Error
Given I am on the Login page
When I type "SECONDPRIME" into the User Name textfield
And I type "2ut2CFmnH$f!" into the Password textfield
And I click the Login button
Then I validate the Error text contains "The credentials you provided cannot be determined to be authentic."
And I validate the User Name textfield is "SECONDPRIME" 
And I validate the Password textfield is "" 