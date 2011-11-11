Login Test Cases for TF388/TF444 

Meta:
@page login
@testFolder TF388
@testFolder TF444


Narrative:

In order to show the inthinc portal login features
As varying types of users
I want to see valid and invalid logins

Scenario: TC1240: Log in - Access Blocked Error
Given I am on the Login Page
And I should record these test results in Rally for TC1240
And I should associate these test results with DE6705 in Rally
When I attempt to login with a blocked username password combination
Then I should get Access Blocked message
And I should remain on the Login Page

Scenario: Valid login to the  inthinc portal
Given I am on the Login Page
When I enter a valid username password combination
And I click log in
Then I should end up on the Overview page

Scenario: TC1241: Login In - Blank User Name and Password Error
Given I am on the Login Page
When I attempt to login with a empty username password combination
Then I should get Incorrect Username or Password message
And I should remain on the Login Page

Scenario: TC1242: Log In - Bookmark Page Entry
Given I am not already logged into the portal
When I navigate directly to a previously bookmarked portal page 
And I attempt to login with a valid username password combination 
Then I should end up on the previously bookmarked portal page

Scenario: TC1243: Log In - Default Command Button
Given I am on the Login Page
When I enter a valid username password combination
And I press the enter key on my keyboard
Then I should end up on the Overview page
     
Scenario: TC1245: Log In - Invalid Password Error
Given I am on the Login Page
When I attempt to login with a valid username invalid password combination
Then I should get Incorrect Username or Password message
And I should remain on the Login Page
When I close the login error alert message
Then I should remain on the Login Page
And the Login Page fields should be empty  

Scenario: TC1246: Log In - Invalid User Name Error
Given I am on the Login Page
When I attempt to login with an invalid username valid password combination
Then I should get Incorrect Username or Password message
And I should remain on the Login Page
When I close the alert message
Then I should remain on the Login Page
And the Login Page fields should be empty  
     
Scenario: TC1247: Log In - Log In Button
Given I am on the Login Page
When I enter a valid username password combination
And I click the Login Button
Then I should end up on the Overview page
	
Scenario: TC1248: Log In - Password Incorrect Case Error
Given I am on the Login Page
When I enter a valid username password combination
And I change the password to an incorrect case
Then I should get Incorrect Username or Password message
And I should remain on the Login Page
When I close the alert message
Then I should remain on the Login Page
And the Login Page fields should be empty  	 

Scenario: TC1249: Log In - Tabbing Order
Given I am on the Login Page
And I move the focus to the User Name Field
When I hit the Tab Key
Then the focus should be on the Password Field
When I hit the Tab Key
Then the focus should be on the Log In Button
When I hit the Tab Key
Then the focus should be on the Forgot User Name or Password Link
When I hit the Tab Key
Then the focus should be on the Privacy Policy Link
When I hit the Tab Key
Then the focus should be on the Legal Notice Link
When I hit the Tab Key
Then the focus should be on the Support Link
	 
Scenario: TC1249: Log In - Tabbing Order (Table)
Given I am on the Login Page
And I move the focus to the <initialFocusedElement>
When I hit the <keyToHit>
Then the focus should be on the <finalFocusedElement>
	 
Examples:
|initialFocusedElement|keyToHit|finalFocusedElement|
|User Name Field|Tab Key|Password Field|
|Password Field|Tab Key|Log In Button|
|User Name Field|Tab Key|Forgot User Name or Password Link|
|Forgot User Name or Password Link|Tab Key|Privacy Policy Link|
|Privacy Policy Link|Tab Key|Legal Notice Link|
|Legal Notice Link|Tab Key|Support Link|

	 	 
Scenario: TC1250: Log In - UI
When I open the Login Page
Then the Login Page should render as expected
And the Login Page fields should be empty  

Scenario: TC1251: Log In - User Name Incorrect Case Error 
Given I am on the Login Page
When I enter a valid username password combination
And I change the username to an incorrect case
Then I should get Incorrect Username or Password message
And I should remain on the Login Page
When I close the alert message
Then I should remain on the Login Page
And the Login Page fields should be empty  

Scenario: Demonstrate jBehave ability to create method stubs
Given I want to learn more about jBehave
When I have not created step definitions yet
Then jBehave should do it for me