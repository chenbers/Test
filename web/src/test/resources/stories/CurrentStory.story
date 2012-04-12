Scenario: TC823: Admin - Users - View User - Bookmark Entry
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users tab
And I select a valid user
And I click the Back To Users link
Then I am on the Admin Users page
