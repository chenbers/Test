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
And the Login Error popup opens
Then I validate the Header text is "Log In Error"
And I validate the Message text contains "Incorrect user name or password."
And I validate the Message text contains "Please try again."
And I click the Ok button
And the Login Error popup closes
And I validate I am on the Login page
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
When I type "SecondPrime" into the User Name field
And I type "" into the Password field
And I press the Enter Key
Then I validate I am on the Executive Dashboard page

Scenario: TC1245: Log In - Invalid Password Error
Given I am on the Login page
When I type "secondPrime" into the User Name textfield
And I type "this will never be a valid password" into the Password textfield
And I click the Login button
And the Login Error popup opens
Then I validate the Message text contains "Incorrect user name or password."
And I validate the Message text contains "Please try again."
And I click the Ok button
And the Login Error popup closes
And I validate I am on the Login page
And I validate the User Name textfield is "" 
And I validate the Password textfield is ""  

Scenario: TC1246: Log In - Invalid User Name Error
Given I am on the Login page
And I type "this will never be a valid User Name" into the User Name textfield
And I type "password" into the Password textfield
And I click the Login button
And the Login Error popup opens
Then I validate the Message text contains "Incorrect user name or password."
And I validate the Message text contains "Please try again."
And I click the Ok button
And the Login Error popup closes
And I validate I am on the Login page
And I validate the User Name textfield is "" 
And I validate the Password textfield is "" 

Scenario: TC1247: Log In - Log In Button
Given I am logged in
Then I validate I am on the Executive Dashboard page

Scenario: TC1248: Log In - Password Incorrect Case Error
Given I am on the Login page
When I type "secondPrime" into the User Name textfield
And I type "2UT2CFMNH$F!" into the Password textfield
And I click the Login button
And the Login Error popup opens
Then I validate the Message text contains "Incorrect user name or password."
And I validate the Message text contains "Please try again."
And I click the Ok button
And the Login Error popup closes
And I validate I am on the Login page
And I validate the User Name textfield is "" 
And I validate the Password textfield is "" 

Scenario: TC1250 - Log In - UI
Given I am on the Login page 

Scenario: TC1251 - Log In - User Name Incorrect Case Error
Given I am on the Login page
When I type "SECONDPRIME" into the User Name textfield
And I type "2ut2CFmnH$f!" into the Password textfield
And I click the Login button
And the Login Error popup opens
Then I validate the Message text contains "Incorrect user name or password."
And I validate the Message text contains "Please try again."
And I click the Ok button
And the Login Error popup closes
And I validate I am on the Login page
And I validate the User Name textfield is "" 
And I validate the Password textfield is "" 