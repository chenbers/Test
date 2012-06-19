Test Cases for TF388/TF411/TF744 

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF744

Narrative:

Scenario: TC5710: Notifications - Zones - Bookmark Entry 
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
Given I am logged in
Then I validate I am on the Notifications Zones page

Scenario: TC5712: Notifications - Zones - Driver Link
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Driver link as SavedEntry
And I click the 1st Row of the Entry Driver link
Then I validate the Driver Name link is SavedEntry

Scenario: TC5719: Notifications - Zones - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
Then I validate the Sort By Date Time column sorts correctly
And I validate the Sort By Driver column sorts correctly
And I validate the Sort By Group column sorts correctly
And I validate the Sort By Vehicle column sorts correctly

Scenario: TC5720: Notifications - Zones - Tools Button
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Tools button
Then I validate the Email Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC5721: Notifications - Zones - UI
Given I am logged in
When I click the Notifications link
And I click the Zones link
Then I validate I am on the Notifications Zones page
And I validate the Team dropdown is present
And I validate the Time Frame dropdown is present
And I validate the Refresh button is present
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present
And I validate the Header Status text is present

Scenario: TC5722: Notifications - Zones - Vehicle Link
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Vehicle link as SavedEntry
And I click the 1st Row of the Entry Vehicle link
Then I validate the Vehicle Name link is SavedEntry

Scenario: TC5723: Notifications - Zones - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Edit Columns checkbox
And I uncheck the 2nd Row of the Edit Columns checkbox
And I uncheck the 3rd Row of the Edit Columns checkbox
And I uncheck the 4th Row of the Edit Columns checkbox
And I uncheck the 5th Row of the Edit Columns checkbox
And I uncheck the 6th Row of the Edit Columns checkbox
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC5724: Notifications - Zones - Edit Columns - Cancel Button (No Changes)
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC5725: Notifications - Zones - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I check the 1st Row of the Edit Columns checkbox
And I validate the 1st Row of the Edit Columns checkbox is checked
And I uncheck the 1st Row of the Edit Columns checkbox
And I validate the 1st Row of the Edit Columns checkbox is not checked
And I check the 2nd Row of the Edit Columns checkbox
And I validate the 2nd Row of the Edit Columns checkbox is checked
And I uncheck the 2nd Row of the Edit Columns checkbox
And I validate the 2nd Row of the Edit Columns checkbox is not checked
And I check the 3rd Row of the Edit Columns checkbox
And I validate the 3rd Row of the Edit Columns checkbox is checked
And I uncheck the 3rd Row of the Edit Columns checkbox
And I validate the 3rd Row of the Edit Columns checkbox is not checked
And I check the 4th Row of the Edit Columns checkbox
And I validate the 4th Row of the Edit Columns checkbox is checked
And I uncheck the 4th Row of the Edit Columns checkbox
And I validate the 4th Row of the Edit Columns checkbox is not checked
And I check the 5th Row of the Edit Columns checkbox
And I validate the 5th Row of the Edit Columns checkbox is checked
And I uncheck the 5th Row of the Edit Columns checkbox
And I validate the 5th Row of the Edit Columns checkbox is not checked
And I check the 6th Row of the Edit Columns checkbox
And I validate the 6th Row of the Edit Columns checkbox is checked
And I uncheck the 6th Row of the Edit Columns checkbox
And I validate the 6th Row of the Edit Columns checkbox is not checked

Scenario: TC5727: Notifications - Zones - Edit Columns - Current Session Retention
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Edit Columns checkbox
And I uncheck the 2nd Row of the Edit Columns checkbox
And I uncheck the 3rd Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Reports link
And I click the Notifications link
And I click the Zones link
Then I validate the Sort By Date Time link is not present
And I validate the Sort By Group link is not present
And I validate the Sort By Driver link is not present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Edit Columns checkbox
And I check the 2nd Row of the Edit Columns checkbox
And I check the 3rd Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC5729: Notifications - Zones - Edit Columns - Save Button
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Edit Columns checkbox is not checked
And I check the 1st Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC5730: Notifications - Zones - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
And I click the Notifications link
And I click the Zones link
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
And I click the Notifications link
And I click the Zones link
And I validate the Sort By Date Time link is present

Scenario: TC5732: Notifications - Zones - Edit Columns - UI
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the 1st Row of the Edit Columns checkbox is present
And I validate the 2nd Row of the Edit Columns checkbox is present
And I validate the 3rd Row of the Edit Columns checkbox is present
And I validate the 4th Row of the Edit Columns checkbox is present
And I validate the 5th Row of the Edit Columns checkbox is present
And I validate the 6th Row of the Edit Columns checkbox is present
And I validate the 1st Row of the Edit Columns checkbox is checked
And I validate the 2nd Row of the Edit Columns checkbox is checked
And I validate the 3rd Row of the Edit Columns checkbox is checked
And I validate the 4th Row of the Edit Columns checkbox is checked
And I validate the 5th Row of the Edit Columns checkbox is checked
And I validate the 6th Row of the Edit Columns checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present

Scenario: TC5733: Notifications - Zones - Exclude Link - Zone Arrival Event NEED HELP TO IMPLEMENT
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I save the text in the first Entry Date Time text as SavedDateTime
And I save the text in the first Entry Detail text as SavedDetail
And I click the first Exclude link in the Status column
And the Exclude Event popup opens
Then I validate the SavedDateTime text is present
And I validate the SavedDetail text is present
And I click the Yes button
And I validate the first link in the Status column is "include"

Scenario: TC5734: Notifications - Zones - Exclude Link - Zone Departure Event NEED HELP TO IMPLEMENT
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I save the text in the first Entry Date Time text as SavedDateTime
And I save the text in the first Entry Detail text as SavedDetail
And I click the first Exclude link in the Status column
And the Exclude Event popup opens
Then I validate the SavedDateTime text is present
And I validate the SavedDetail text is present
And I click the Yes button
And I validate the first link in the Status column is "include"

Scenario: TC5735: Notifications - Zones - Exclude Link - UI
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SavedDateTime
And I save the 1st Row of the Entry Detail text as SavedDetail
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
Then I validate the SavedDateTime text is present
And I validate the SavedDetail text is present
And I validate the Yes button is present
And I validate the No button is present

Scenario: TC5736: Notifications - Zones - Include Link 
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
And I click the Yes button
Then I validate the 1st Row in the Status link is "include"
And I click the 1st Row in the Status link
And I validate the 1st Row in the Status link is "exclude"

Scenario: TC5741: Notifications - Zones - Time Frame
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I click "Today" in the TimeFrame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SavedDateTime
And I click "Yesterday" in the TimeFrame dropdown
And I click the Refresh button
Then I validate the SavedDateTime text is not present
