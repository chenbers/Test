Login Test Cases for TF448

Meta:
@page login
@testFolder TF448

Narrative: Test the forgot user name or password features

Scenario: TC1184: Log In - Forgot User Name or Password - Cancel Button
Given I am on the login page
When I click the 'Forgot your user name or password?' link
And I enter a email address not in the database into the email address field
And I click cancel
Then the 'Forgot you user name or password?' window is closed
And I am on the login page
And I click the 'Forgot your user name or password?' link
And the email address text field is blank

Scenario: TC1185: Log In - Forgot User Name or Password - Email Address Format Error
Given I am on the login page
When I click the 'Forgot your user name or password?' link
And I enter non valid email text into the email address field
And I click send
Then the alert 'Incorrect format' appears above the email address field

Scenario: TC1186: Log In - Forgot User Name or Password - Email Address Missing Error
Given I am on the login page
When I click the 'Forgot your user name or password?' link
And I click send
Then the alert 'Required' appears above the email address field

Scenario: TC1187: Log In - Forgot User Name or Password - Email Address Verification Error
Given I am on the login page
When I click the 'Forgot your user name or password?' link
And I enter a email address not in the database into the email address field
And I click send
Then the alert 'Incorrect e-mail address' appears above the email address field

Scenario: TC1192: Log In - Forgot User Name or Password - UI
Given I am on the login page
When I click the 'Forgot your user name or password?' link
Then the 'Forgot user name or password' pop up displays correctly with all elements