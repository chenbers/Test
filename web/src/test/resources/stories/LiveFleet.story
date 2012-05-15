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

Scenario: TC1229: Live Fleet - Bookmark Entry to Different Account
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
And I validate I am on the Login page
And I am logged in as a different "Admin" user
And I click log in
Then I validate I am on the Live Fleet page

Scenario: TC1232: Live Fleet - Driver Link
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I save the first entry in the Driver column of the Dispatch table as SavedEntry 
And I click the first entry in the Driver column of the Dispatch table
Then I validate I am on the Driver Performance page
And I validate the Driver Performance Name text is SavedEntry 

Scenario: TC1236: Live Fleet - Team Link
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I save the first entry in the EntryFleetLegend table as SavedEntry 
And I click the first entry in the EntryFleetLegend table
Then I validate I am on the Team Performance page
And I validate the Team Name text is SavedEntry 

Scenario: TC1237: Live Fleet - UI
Given I am logged in as a "Admin" user
When I click the Live Fleet link
Then I validate I am on the Live Fleet page
And I validate the SortByNumber link is present
And I validate the SortByDriver link is present
And I validate the SortByVehicle link is present
And I validate the SortByGroup link is present
And I validate the HeaderFleetLegend text is present
And I validate the EntryFleetLegend link is present
And I validate the FindAddress textfield is present
And I validate the NumNearestVehicles dropdown is present
And I validate the Locate button is present
And I validate the EntryGroupIconByPosition link is present
And I validate the ValueMapBubbleVehicleName link is present

Scenario: TC1238: Live Fleet - Vehicle Image Link
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I click the first EntryGroupIconByPosition link
Then I validate I am on the Live Fleet page
And I validate the ValueMapBubbleVehicleName link is present
And I validate the LabelMapBubbleDevice text is present
And I validate the LabelMapBubbleDriver text is present
And I validate the LabelMapBubbleLocation text is present
And I validate the LabelMapBubblePhone1 text is present
And I validate the LabelMapBubblePhone2 text is present
And I validate the LabelMapBubbleUpdated text is present
And I validate the ValueMapBubbleDriver link is present
And I validate the ValueMapBubbleDevice text is present
And I validate the ValueMapBubbleUpdated text is present
And I validate the ValueMapBubbleLocation text is present
And I validate the ValueMapBubbleDistToAddress text is present

Scenario: TC1239: Live Fleet - Vehicle Link
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I save the first entry in the Vehicle column of the Dispatch table as SavedEntry 
And I click the first entry in the Vehicle column of the Dispatch table
Then I validate I am on the Vehicle Performance page
And I validate the Vehicle Performance Name text is SavedEntry 
