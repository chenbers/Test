Test Cases for TTF527

Meta:
@page login
@testFolder TF527

Narrative: 

Scenario: TC5626: Admin - Edit User - Employee ID Saves in UpperCase
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Edit Columns link
And I verify the Employee ID checkbox is checked
And I click the Save button
And I capture the username of the first user in the table
And I click the Edit link of the first user in the table
And I type "" into the Employee ID box
And I type a lowercase id into the Employee ID box
And I click the Save button
And I click the My Account link
And I click the Admin link
And I type the username of the user I captured into the search box
And I click the search button
And I click on the first username in the table
Then I verify the Employee ID text is uppercase
