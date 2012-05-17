Meta:
@page "Reports"

Narrative:

As a customer, I need to be see idling for vehicles even with no driver logged in,
so that I don't have to rely on drivers logged in to see idling statistics for vehicles.
On the "What Happened..." page, create a tab for Vehicle Statistics, 
with the same format as the Driver Statistics page but with the Driver and Vehicle columns swapped.

Scenario: Can access Idling Vehicles page
Given I am logged in as a user that has access to idling pages
When I click Idling Vehicles on the reports bar
Then I should be on the Idling Vehicles page

Scenario: Can access Idling Drivers page
Given I am logged in as a user that has access to idling pages
When I click Idling Drivers on the reports bar
Then I should be on the Idling Drivers page

Scenario: Vehicles with no driver visible on Idling Vehicles page
Given I am logged in as a user that has access to idling pages
When I click Idling Vehicles on the reports bar
Then vehicles with "Unknown Driver" should be visible in the table

Scenario: Can view Vehicle idle times on team page
Given I am logged in as user who can see a team with a vehicle with no driver that has idling time in the last 365 days
And I am on the "What Happened..." page
When I click the "Vehicle Statistics" tab
Then I should be able to view idle time for a vehicle with no driver

Scenario: Can view Vehicle idle times on Idling Report
Given I am logged in as user who can view an idling report with a vehicle with no driver that has idling time in the last 365 days
And I am on the "Idling Report" page
When I specify a date range in the last 365 days
And I click refresh
Then I should be able to view a column with vehicles that have an idle time

Scenario: Correct Date Range on Idling Drivers Report
Given I am logged in as a user that has access to idling pages
When I choose a date range
Then I can only see table entries that occur within the specified date range

Scenario: "Group" column only appears in report when selected from edit columns
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I remove the check from "Group" check box, 
Then the "Group" column should not be visible in the table

Scenario: "Idle Support" column only appears in report when selected from edit columns
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I remove the check from "Idle Support" check box, 
Then the "Idle Support" column should not be visible in the table

Scenario: "High Idle Hrs." column only appears in report when selected from edit columns
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I remove the check from "High Idle Hours" check box, 
Then the "High Idle Hours" column should not be visible in the table

Scenario: "Total %" column only appears in report when selected from edit columns
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I remove the check from "Total %" check box, 
Then the "Total %" column should not be visible in the table

Scenario: "Driver" column only appears in report when selected from edit columns
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I remove the check from "Driver" check box, 
Then the "Driver" column should not be visible in the table

Scenario: "Low Idle Hrs." column only appears in report when selected from edit columns
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I remove the check from "Low Idle Hrs." check box, 
Then the "Low Idle Hrs." column should not be visible in the table

Scenario: "High %" column only appears in report when selected from edit columns
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I remove the check from "High %" check box, 
Then the "High %" column should not be visible in the table

Scenario: "Duration" column only appears in report when selected from edit columns
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I remove the check from "Duration" check box, 
Then the "Duration" column should not be visible in the table

Scenario: "Low %" column only appears in report when selected from edit columns
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I remove the check from "Low %" check box, 
Then the "Low %" column should not be visible in the table

Scenario: "Total Idle Hrs." column only appears in report when selected from edit columns
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I remove the check from "Total Idle Hrs." check box, 
Then the "Total Idle Hrs." column should not be visible in the table

Scenario: "Group" search bar works correctly
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I enter a string in the "Group" search bar
And I press enter
Then items in the corresponding column should populate with related terms

Scenario: "Driver" search bar works correctly
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I enter a string in the "Driver" search bar
And I press enter
Then items in the corresponding column should populate with related terms

Scenario: "Vehicle" search bar works correctly
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I enter a string in the "Vehicle" search bar
And I press enter
Then items in the corresponding column should populate with related terms

Scenario: "Group" column links take the user to a corresponding page
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I click any link in the "Group" column
Then I should be redirected to that groups team page

Scenario: "Driver" column links take the user to a corresponding page
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I click any link in the "Driver" column
Then I should be redirected to that driver's page

Scenario: "Vehicle" column links take the user to a corresponding page
Given I am logged in as a user that has access to idling pages
When I navigate to the "Idling Vehicles" page
And I click any link in the "Vehicle" column
Then I should be redirected to that vehicle's page