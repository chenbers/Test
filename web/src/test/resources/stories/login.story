Login Test Cases for TF388/TF444 

Meta:
@page login
@testFolder TF388
@testFolder TF444


Narrative:

In order to show the inthinc portal login features
As varying types of users
I want to see valid and invalid logins

Scenario: TC1240: Log In - Access Blocked Error CAN'T IMPLEMENT YET

Scenario: TC1241: Log In - Blank User Name and Password Error
Given I am on the login page
When I click on the Login button
I get an alert 'Incorrect user name or password. Please try again.'
And I close the login error alert message
And I should remain on the Login Page
And the name and password fields are blank

Scenario: TC1242: Log In - Bookmark Page Entry
Given I log in to the login page
When I bookmark the page
And I click log out
And I click the bookmark I just added
And I am on the login page
And I type a valid user name
And I type a valid password
And I click log in
Then the Overview page is displayed

Scenario: TC1243: Log In - Default Command Button
Given I am on the login page
When I type a valid user name
And I type a valid password
And I press the enter key
Then I should end up on the Overview page

Scenario: TC1245: Log In - Invalid Password Error
Given I am on the login page
When I type a valid user name
And I type an invalid password
And I click log in
Then I get an alert 'Incorrect user name or password. Please try again.'
And I close the login error alert message
And I should remain on the Login Page
And the name and password fields are blank

Scenario: TC1246: Log In - Invalid User Name Error
Given I am on the login page
When I type an invalid user name
And I type a valid password
And I click log in
Then I get an alert 'Incorrect user name or password. Please try again.'
And I close the login error alert message
And I am still on the Login Page 
And the name and password fields are blank

Scenario: TC1247: Log In - Log In Button
Given I am on the login page
When I type a valid user name
And I type a valid password
And I click log in
Then I should end up on the Overview page

Scenario: TC1248: Log In - Password Incorrect Case Error
Given I am on the login page
When I type a valid user name
And I type a password in the wrong case
And I click log in
Then I get an alert 'Incorrect user name or password. Please try again.'
And I close the login error alert message
And I should remain on the Login Page
And the name and password fields are blank

Scenario: TC1249: Log In - Tabbing Order CAN'T IMPLEMENT YET

Scenario: TC1250 - Log In - UI
Given I am on the login page 
When the focus should be on the User Name Field
Then the Login Page should render as expected

Scenario: TC1251 - Log In - User Name Incorrect Case Error
Given I am on the login page
When I type an user name in the wrong case
And I type a valid password
And I click log in
Then I get an alert 'Incorrect user name or password. Please try again.'
And I close the login error alert message
And I should remain on the Login Page 
And the name and password fields are blank
