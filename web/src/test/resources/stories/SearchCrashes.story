Narrative:

In order to search for vehicle crash data
As an Administrator
I want to search using all combinations of teams, time frames and drivers or vehicle names.

Scenario: Enter no search criteria returns no results
Given I am logged in as an Administrator on the Notifications page
When I click on on the Crash History tab
And the Time Frame is defaulted to "Today"
And I click on the Search button
Then no crash history should be returned or displayed.

Scenario: Search by group and returns crash data for selected Time Frame
Given Given I am logged in as an Administrator on the Notifications page
And I am on the "Notifications" page
When I click on on the Crash History tab
And I select an active group of drivers from the group drop-down
And the Time Frame is selected to <type>
And I click on the Search button
Then any crash history for <type> should be returned or displayed.


Examples:
|type|
|Today|
|Yesterday|
|Sunday|
|Saturday|
|Friday|
|Thursday|
|Wednesday|
|Past Week|
|Current Month|
|Past 30 days|
|Past Year|
|All|




