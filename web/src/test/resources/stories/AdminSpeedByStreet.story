

Scenario: TC744: Admin - Speed By Street - Bookmark Page Entry
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click on the Speed By Street tab
And I type "4225 Lake Park Blvd Salt Lake City, UT 84120"
And I click the Find Address button
And I bookmark the page
And I click log out
And I click the bookmark I just added
And I am logged in as a "Admin" user
Then the Admin menu is highlighted
And the Speed by Street tab is highlighted
And the Speed Limit Change Request page is displayed
And the Search field is blank

Scenario: TC745: Admin - Speed By Street - Clear All Button
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click on the Speed By Street tab
And I type multiple addresses
And I click the Clear All button
Then the search results are cleared from the table

Scenario: TC746: Admin - Speed By Street - Delete Button
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click on the Speed By Street tab
And I type multiple addresses
And I checkmark the Select Box on a couple boxes
And I click the Delete button
Then the checked results are cleared from the table
And the unchecked results are still in the table

Scenario: TC747: Admin - Speed By Street - Find Address Button - HAS STEPS THAT NEED TO BE DONE MANUALLY
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click on the Speed By Street tab
And I type "4225 Lake Park Blvd Salt Lake City, UT 84120"
And I click the Find Address button
Then the map is updated with the location and is highlighted
And I confirm the table contains existing addresses
And I confirm the table contains the new addresses

Scenario: TC749-TC756 : Admin - Speed By Street - HAS STEPS THAT NEED TO BE DONE MANUALLY DUE TO THE MAP

Scenario: TC757 : Admin - Speed By Street - HAS TO BE DONE MANUALLY DUE TO EXTERNAL EMAIL PROGRAM

Scenario: TC758: Admin - Speed By Street - UI
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click on the Speed By Street tab
And I type "4225 Lake Park Blvd Salt Lake City, UT 84120"
And I click the Find Address button
Then I am on the Admin Submit Speed Limit Change Request page
And the Admin Submit Speed Limit Change Request page renders correctly

Scenario: TC5405-TC5546: Admin - Speed By Street -  HAS TO BE DONE MANUALLY DUE TO EXTERNAL EMAIL PROGRAM