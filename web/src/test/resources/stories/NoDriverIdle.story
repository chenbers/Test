Meta:
@page "What Happened..."

Narrative:

As a customer, I need to be see idling for vehicles even with no driver logged in,
so that I don't have to rely on drivers logged in to see idling statistics for vehicles.
On the "What Happened..." page, create a tab for Vehicle Statistics, 
the same format as the Driver Statistics page but with the Driver and Vehicle columns swapped.

Scenario: Can view Vehicle idle times on team page
Given I am logged in as user who can see a team with a driverless vehicle that has idling time in the last 365 days
And I am on the "What Happened..." page
When I click the "Vehicle Statistics" tab
Then I should be able to view idle time for a vehicle with no driver

Scenario: Can view Vehicle idle times on Idling Report
Given I am logged in as user who can view an idling report with a driverless vehicle that has idling time in the last 365 days
And I am on the "Idling Report" page
When I specify a date range in the last 365 days
And I click refresh
Then I should be able to view a column with vehicles that have an idle time