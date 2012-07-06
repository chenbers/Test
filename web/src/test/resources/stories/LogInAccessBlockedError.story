//STORY WILL NOT RUN UNTIL WE FIGURE OUT HOW TO ATTEMPT TO LOG BACK IN AS THE SAME USER

Given I am logged in as a "Admin" user
When I select admin
And I select users
And I select a valid user
And I click the edit link
And I change the user's login status to inactive
And I click save
And I click log out
And I am on the login page
And I attempt to login with the same username password combination
Then I get a Access Blocked alert
And I close the login error alert message
And I should remain on the Login Page
And the name and password fields are blank