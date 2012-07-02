Login Test Cases for TF448

Meta:
@page login
@testFolder TF448

Narrative: Test the forgot user name or password features

Scenario: TC1184: Log In - Forgot User Name or Password - Cancel Button
Given I am on the Login page
When I click the Forgot Username Password link
And the Forgot Password popup opens
And I type "Invalid Email Address" into the Email textfield
And I click the Cancel button
And the Forgot Password popup closes
And I validate I am on the Login page
And I click the Forgot Username Password link
And the Forgot Password popup opens
Then I validate the Email textfield is ""

Scenario: TC1185: Log In - Forgot User Name or Password - Email Address Format Error
Given I am on the Login page
When I click the Forgot Username Password link
And the Forgot Password popup opens
And I type "Invalid Format Email Address" into the Email textfield
And I click the Send button
Then I verify "Incorrect format (jdoe@tiwipro.com)" is on the page
And I type "z" into the Email textfield
And I click the Send button
Then I verify "Incorrect format (jdoe@tiwipro.com)" is on the page
And I type "test@@test.com" into the Email textfield
And I click the Send button
Then I verify "Incorrect format (jdoe@tiwipro.com)" is on the page
And I type "test@test@test.com" into the Email textfield
And I click the Send button
Then I verify "Incorrect format (jdoe@tiwipro.com)" is on the page
And I type "!@#...com" into the Email textfield
And I click the Send button
Then I verify "Incorrect format (jdoe@tiwipro.com)" is on the page
And I type "test test@email.com" into the Email textfield
And I click the Send button
Then I verify "Incorrect format (jdoe@tiwipro.com)" is on the page
And I type ""test"@email.com" into the Email textfield
And I click the Send button
Then I verify "Incorrect format (jdoe@tiwipro.com)" is on the page

Scenario: TC1186: Log In - Forgot User Name or Password - Email Address Missing Error
Given I am on the Login page
When I click the Forgot Username Password link
And the Forgot Password popup opens
And I click the Send button
Then I verify "Required" is on the page

Scenario: TC1187: Log In - Forgot User Name or Password - Email Address Verification Error
Given I am on the Login page
When I click the Forgot Username Password link
And the Forgot Password popup opens
And I type an "testtesttesttest@test.com" into the Email textfield
And I click the Send button
Then I verify "Incorrect e-mail address" is on the page

Scenario: TC1192: Log In - Forgot User Name or Password - UI
Given I am on the Login page
When I click the Forgot Username Password link
And the Forgot Password popup opens
Then I validate I am on the Forgot Password popup