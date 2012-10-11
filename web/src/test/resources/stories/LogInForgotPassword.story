Login Test Cases for TF448

Meta:
@page login
@testFolder TF448

Narrative: Test the forgot user name or password features

Scenario: TC1185: Log In - Forgot User Name or Password - Email Address Format Error
Given I am on the Login page
When I click the Forgot Username Or Password link
And I type an "Invalid Format Email Address" into the Email Address textfield
And I click the Send button
Then I validate the Error text is "Incorrect format (jdoe@tiwipro.com)"

Scenario: TC1186: Log In - Forgot User Name or Password - Email Address Missing Error
Given I am on the Login page
When I click the Forgot Username Or Password link
And I click the Send button
Then I validate the Error text is "Required"

Scenario: TC1187: Log In - Forgot User Name or Password - Email Address Verification Error
Given I am on the Login page
When I click the Forgot Username Or Password link
And I type an "testtesttesttest@test.com" into the Email Address textfield
And I click the Send button
Then I validate the Error text is "Incorrect e-mail address"

Scenario: TC1192: Log In - Forgot User Name or Password - UI
Given I am on the Login page
When I click the Forgot Username Or Password link
Then I validate the Header text is present
And I validate the Information text is present
And I validate the Email Address textfield is present
And I validate the Send button is present