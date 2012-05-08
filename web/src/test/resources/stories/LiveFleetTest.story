Test Case for TF505

Meta:
@page login
@testFolder TF505

Narrative: 

Scenario: TC1228: Live Fleet - Bookmark Entry
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
And I validate I am on the Login page
And I type a valid user name
And I type a valid password
And I click log in
Then I validate I am on the Live Fleet page



