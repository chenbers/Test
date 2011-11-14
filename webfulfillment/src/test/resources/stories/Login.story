Narrative:

In order to show the web fulfillment login features
As Superuser or Team Member
I want to see valid and invalid logins

Scenario: TC5926: All fields are blank error message
Given I am on the login page
and the username field is blank
and the password field is blank
When I click on the Sign In button
Then error message "Your login information was incorrect. Please try again" displays

Scenario: TC5958: Missing Username field error message on submit
Given I am on the login page
and the username field is blank
When I Enter a password
and I click on the Sign In button
Then error message "Your login information was incorrect. Please try again" displays

Scenario: TC5959: Missing Password field error message on submit
Given I am on the login page
and the Password field is blank
When I Enter a Username
and I click on the Sign In button
Then error message "Your login information was incorrect. Please try again" displays

Scenario: TC5960: Incorrect Username
Given I am on the login page
When I Enter an Incorrect Username
and a correct password
and I click on the Sign In button
Then error message "Your login information was incorrect. Please try again" displays

Scenario: TC5961: Incorrect Password
Given I am on the login page
When I Enter an Correct Username
and an Incorrect Password
and I click on the Sign In button
Then error message "Your login information was incorrect. Please try again" displays

Scenario: TC5962: Correct Username and Password
Given I am on the login page
When I Enter an Correct Username
and an Correct Password
and I click on the Sign In button
Then I am on the Webfulfillment Home page



