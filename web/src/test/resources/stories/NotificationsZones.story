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
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Driver link as SAVEDENTRY
And I click the 1st Row of the Entry Driver link
Then I validate the Driver Name link is SAVEDENTRY

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
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Category dropdown is present
And I validate the Status Filter dropdown is present

Scenario: TC5722: Notifications - Zones - Vehicle Link
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Vehicle link as SAVEDENTRY
And I click the 1st Row of the Entry Vehicle link
Then I validate the Vehicle Name link is SAVEDENTRY

Scenario: TC5723: Notifications - Zones - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 5th Row of the Column checkbox
And I uncheck the 6th Row of the Column checkbox
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present
And I validate the Header Status text is present
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Category dropdown is present
And I validate the Status Filter dropdown is present

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
And I validate the Header Status text is present
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Category dropdown is present
And I validate the Status Filter dropdown is present

Scenario: TC5725: Notifications - Zones - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I check the 1st Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I uncheck the 1st Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is not checked
And I check the 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is checked
And I uncheck the 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is not checked
And I check the 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is checked
And I uncheck the 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is not checked
And I check the 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is checked
And I uncheck the 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is not checked
And I check the 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is checked
And I uncheck the 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is not checked
And I check the 6th Row of the Column checkbox
And I validate the 6th Row of the Column checkbox is checked
And I uncheck the 6th Row of the Column checkbox
And I validate the 6th Row of the Column checkbox is not checked

Scenario: TC5727: Notifications - Zones - Edit Columns - Current Session Retention
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
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
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC5728: Notifications - Zones - Edit Columns - Default Command Button
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I press the Enter Key
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I press the Enter Key
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC5729: Notifications - Zones - Edit Columns - Save Button
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC5730: Notifications - Zones - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Zones link
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Zones link
Then I validate the Sort By Date Time link is present

Scenario: TC5732: Notifications - Zones - Edit Columns - UI
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the 1st Row of the Column checkbox is present
And I validate the 2nd Row of the Column checkbox is present
And I validate the 3rd Row of the Column checkbox is present
And I validate the 4th Row of the Column checkbox is present
And I validate the 5th Row of the Column checkbox is present
And I validate the 6th Row of the Column checkbox is present
And I validate the 1st Row of the Column checkbox is checked
And I validate the 2nd Row of the Column checkbox is checked
And I validate the 3rd Row of the Column checkbox is checked
And I validate the 4th Row of the Column checkbox is checked
And I validate the 5th Row of the Column checkbox is checked
And I validate the 6th Row of the Column checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present

Scenario: TC5733: Notifications - Zones - Exclude Link - Zone Arrival Event
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the first entry from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I select "Zone Arrival" from the Category dropdown
And I save the text in the first Entry Date Time text as SAVEDDATETIME
And I save the text in the first Entry Detail text as SAVEDDETAIL
And I click the first Exclude link in the Status column
And the Exclude Event popup opens
Then I validate the SAVEDDATETIME text is present
And I validate the SAVEDDETAIL text is present
And I click the Yes button
And I validate the first link in the Status column is "include"

Scenario: TC5734: Notifications - Zones - Exclude Link - Zone Departure Event
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the first entry in the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I select "Zone Departure" from the Category dropdown
And I save the text in the first Entry Date Time text as SAVEDDATETIME
And I save the text in the first Entry Detail text as SAVEDDETAIL
And I click the first Exclude link in the Status column
And the Exclude Event popup opens
Then I validate the SAVEDDATETIME text is present
And I validate the SAVEDDETAIL text is present
And I click the Yes button
And I validate the first link in the Status column is "include"

Scenario: TC5735: Notifications - Zones - Exclude Link - UI
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDDETAIL
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
Then I validate the Message text contains SAVEDDATETIME
And I validate the Message text contains SAVEDDETAIL
And I validate the Yes button is present
And I validate the No button is present
And I validate the Close button is present

Scenario: TC5736: Notifications - Zones - Include Link 
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDDETAIL
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
Then I validate the Message text contains SAVEDDATETIME
And I validate the Message text contains SAVEDDETAIL
And I click the Yes button
And the Exclude Event popup closes
And I validate the 1st Row of the Entry Status link is "include"
And I click the 1st Row of the Entry Status link
And I validate the 1st Row of the Entry Status link is "exclude"

Scenario: TC5741: Notifications - Zones - Time Frame
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Today" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDDATETIME
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Yesterday" from the Time Frame dropdown
And I click the Refresh button
Then I validate the 1st Row of the Entry Date Time text is not SAVEDDATETIME