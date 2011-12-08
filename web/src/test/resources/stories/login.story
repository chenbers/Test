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
Given I am on the Login Page one
And I should record these test results in Rally for TC1240
And I should associate these test results with DE6705 in Rally
When I attempt to login with a blocked username password combination
Then I should get Access Blocked message
And I should remain on the Login Page

Scenario: Valid login to the  inthinc portal
Given I am on the Login Page two
When I enter a valid username password combination
And I click log in
Then I should end up on the Overview page

