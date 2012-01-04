Narrative:

In order to search for vehicle crash data
As a portal user
I want to search using all combinations of teams, time frames and drivers or vehicle names.

Scenario: Enter no search criteria returns no results
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And the Time Frame is defaulted to "Today"
And I click on the Search button
Then no crash history should be returned or displayed.

Scenario: Select a group and returns crash data for "Today"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is defaulted to "Today"
And I click on the Search button
Then any crash history for "Today" should be returned or displayed.

Scenario: Select a group and returns crash data for "Yesterday"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to "Yesterday"
And I click on the Search button
Then any crash history for "Yesterday" should be returned or displayed.

Scenario: Select a group and returns crash data for "Sunday"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to "Sunday"
And I click on the Search button
Then any crash history for "Sunday" should be returned or displayed.

Scenario: Select a group and returns crash data for "Saturday"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to "Saturday"
And I click on the Search button
Then any crash history for "Saturday" should be returned or displayed.

Scenario: Select a group and returns crash data for "Friday"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to "Friday"
And I click on the Search button
Then any crash history for "Friday" should be returned or displayed.

Scenario: Select a group and returns crash data for "Thursday"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to "Thursday"
And I click on the Search button
Then any crash history for "Thursday" should be returned or displayed.

Scenario: Select a group and returns crash data for "Wednesday"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to "Wednesday"
And I click on the Search button
Then any crash history for "Wednesday" should be returned or displayed.

Scenario: Select a group and returns crash data for "Past Week"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to "Past Week"
And I click on the Search button
Then any crash history for "Past Week" should be returned or displayed.

Scenario: Select a group and returns crash data for (current month)
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to the (current month)
And I click on the Search button
Then any crash history for the (current month) should be returned or displayed.

Scenario: Select a group and returns crash data for "Past 30 days"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to "Past 30 days"
And I click on the Search button
Then any crash history for "Past 30 days" should be returned or displayed.

Scenario: Select a group and returns crash data for "Past Year"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to "Past Year"
And I click on the Search button
Then any crash history for "Past Year" should be returned or displayed.

Scenario: Select a group and returns crash data for "All"
Given I am logged in as a user in a role that has access to Crash notifications
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the drop-down
And the Time Frame is set to "All"
And I click on the Search button
Then any crash history for "All" should be returned or displayed.