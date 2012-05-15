Login Test Cases for TF448

Meta:
@page login
@testFolder TF448

Narrative: Test the forgot user name or password features

Scenario: TC1184: Log In - Forgot User Name or Password - Cancel Button
Given I am on the Login page
When I click the Forgot Password link
And the Forgot Password popup opens
And I type an "Invalid Email Address" into the Email Address textfield
And I click the Cancel button
And the Forgot Password popup closes
And I validate I am on the Login page
And I click the Forgot Password link
Then I validate the Email Address textfield is ""

Scenario: TC1185: Log In - Forgot User Name or Password - Email Address Format Error
Given I am on the Login page
When I click the Forgot Password link
And the Forgot Password popup opens
And I type an "Invalid Format Email Address" into the Email Address textfield
And I click the Send button
Then I validate the text "Incorrect format" is present

Scenario: TC1186: Log In - Forgot User Name or Password - Email Address Missing Error
Given I am on the Login page
When I click the Forgot Password link
And the Forgot Password popup opens
And I click the Send button
Then I validate the text "Required" is present

Scenario: TC1187: Log In - Forgot User Name or Password - Email Address Verification Error
Given I am on the Login page
When I click the Forgot Password link
And the Forgot Password popup opens
And I type an "Invalid Email Address" into the Email Address textfield
And I click the Send button
Then I validate the text "Incorrect e-mail address" is present

Scenario: TC1192: Log In - Forgot User Name or Password - UI
Given I am on the Login page
When I click the Forgot Password link
And the Forgot Password popup opens
Then I validate I am on the Forgot Password pop up