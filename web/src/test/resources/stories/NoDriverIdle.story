Meta:
@page "What Happened..."

Narrative:

As a customer, I need to be see idling for vehicles even with no driver logged in,
so that I don't have to rely on drivers logged in to see idling statistics for vehicles.
On the "What Happened..." page, create a tab for Vehicle Statistics, 
the same format as the Driver Statistics page but with the Driver and Vehicle columns swapped.

Scenario: Can view Vehicle idle times
Given I am logged into a user in a role that has team page access
And there is a Vehicle with no driver
And the vehicle has been driven
And the vehicle has been idle for an amount of time
And I am on the "What Happened..." page
When I click the "Vehicle Statistics" tab
Then I should be able to view idle time for a vehicle with no driver

