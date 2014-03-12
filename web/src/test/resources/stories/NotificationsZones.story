Test Cases for TF388/TF411/TF744 

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF744

Narrative:

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
And I validate the Records text is present
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
And I validate the Status dropdown is present

Scenario: TC5710: Notifications - Zones - Bookmark Entry 
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
When I log back in
Then I validate I am on the Notifications Zones page

Scenario: Notifications - Zones - Bookmark Entry to Different Account
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I log back in under the editable account
Then I validate I am on the Notifications Zones page

Scenario: TC5712: Notifications - Zones - Driver Link
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Driver link as SAVEDZONESDRIVERENTRY
And I click the 1st Row of the Entry Driver link
Then I validate the Driver Name link is SAVEDZONESDRIVERENTRY

Scenario: Notifications - Zones - Group Link
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Group link
And I save the 1st Row of the Entry Group link as SAVEDZONESGROUPENTRY
And I click the 1st Row of the Entry Group link
And I validate the Driver Team Value text is SAVEDZONESGROUPENTRY
Then I validate the Team Name text is SAVEDZONESGROUPENTRY

Scenario: TC5719: Notifications - Zones - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
!-- Given I am logged in
!-- When I click the Notifications link
!-- And I click the Zones link
!-- And I select "Top" from the Team dropdown
!-- And I select "Past 30 Days" in the Time Frame dropdown
!-- And I click the Refresh button
!-- Then I validate the Sort By Date Time column sorts correctly
!-- And I validate the Sort By Driver column sorts correctly
!-- And I validate the Sort By Group column sorts correctly
!-- And I validate the Sort By Vehicle column sorts correctly

Scenario: TC5720: Notifications - Zones - Tools Button
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I click the Tools button
Then I validate the Email This Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC5722: Notifications - Zones - Vehicle Link
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Vehicle link as SAVEDZONESVEHICLEENTRY
And I click the 1st Row of the Entry Vehicle link
Then I validate I am on the Vehicle Performance page
And I validate the Vehicle Name link contains SAVEDZONESVEHICLEENTRY

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
And I validate the Status dropdown is present

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
And I validate the Status dropdown is present

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
!-- Given I am logged in
!-- When I click the Notifications link
!-- And I click the Zones link
!-- And I click the Edit Columns link
!-- And the Edit Columns popup opens
!-- And I uncheck the 1st Row of the Column checkbox
!-- And I press the Enter Key
!-- And the Edit Columns popup closes
!-- Then I validate the Sort By Date Time link is not present
!-- And I click the Edit Columns link
!-- And the Edit Columns popup opens
!-- And I validate the 1st Row of the Column checkbox is not checked
!-- And I check the 1st Row of the Column checkbox
!-- And I press the Enter Key
!-- And the Edit Columns popup closes
!-- And I validate the Sort By Date Time link is present

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

Scenario: Notifications - Zones - Exclude Link - UI
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDZONESDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDZONESDETAIL
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
Then I validate the Message text contains SAVEDZONESDATETIME
And I validate the Message text contains SAVEDZONESDETAIL
And I validate the Yes button is present
And I validate the No button is present
And I validate the Close button is present

Scenario: Notifications - Zones - Exclude Link - No Button - Zone Arrival Event
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I select "Zone Arrival" from the Category dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDZONESDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDZONESDETAIL
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
And I click the No button
And the Exclude Event popup closes
Then I validate the 1st Row of the Entry Date Time text is SAVEDZONESDATETIME
And I validate the 1st Row of the Entry Detail text is SAVEDZONESDETAIL

Scenario: Notifications - Zones - Exclude Link - No Button - Zone Departure Event
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I select "Zone Departure" from the Category dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDZONESDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDZONESDETAIL
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
And I click the No button
And the Exclude Event popup closes
Then I validate the 1st Row of the Entry Date Time text is SAVEDZONESDATETIME
And I validate the 1st Row of the Entry Detail text is SAVEDZONESDETAIL

Scenario: TC5733: Notifications - Zones - Exclude and Include Links - Zone Arrival Event
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I save the Records text as ZONESTABLECOUNT
And I select "Zone Arrival" from the Category dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDZONESDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDZONESDETAIL
And I save the 1st Row of the Entry Category text as SAVEDZONESCATEGORY
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
And I click the Yes button
And the Exclude Event popup closes
Then I validate the Records text is ZONESTABLECOUNT
And I select "excluded" from the Status dropdown
And I validate the 1st Row of the Entry Date Time text is SAVEDZONESDATETIME
And I validate the 1st Row of the Entry Detail text is SAVEDZONESDETAIL
And I validate the 1st Row of the Entry Category text is SAVEDZONESCATEGORY
And I validate the 1st Row of the Entry Status link is "include"
And I click the 1st Row of the Entry Status link
And I validate the 1st Row of the Entry Status link is not present
And I select "included" from the Status dropdown
And I click the Refresh button
And I validate the 1st Row of the Entry Status link is "exclude"
And I validate the 2nd Row of the Entry Status link is "exclude"
And I validate the 3rd Row of the Entry Status link is "exclude"
And I validate the 4th Row of the Entry Status link is "exclude"
And I validate the 5th Row of the Entry Status link is "exclude"

Scenario: TC5734: Notifications - Zones - Exclude Link - Zone Departure Event
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I select "Zone Departure" from the Category dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDZONESDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDZONESDETAIL
And I save the 1st Row of the Entry Category text as SAVEDZONESCATEGORY
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
And I click the Yes button
And the Exclude Event popup closes
Then I validate the Records text is ZONESTABLECOUNT
And I select "excluded" from the Status dropdown
And I validate the 1st Row of the Entry Date Time text is SAVEDZONESDATETIME
And I validate the 1st Row of the Entry Detail text is SAVEDZONESDETAIL
And I validate the 1st Row of the Entry Category text is SAVEDZONESCATEGORY
And I validate the 1st Row of the Entry Status link is "include"
And I click the 1st Row of the Entry Status link
And I validate the 1st Row of the Entry Status link is not present
And I select "included" from the Status dropdown
And I click the Refresh button
And I validate the 1st Row of the Entry Status link is "exclude"
And I validate the 2nd Row of the Entry Status link is "exclude"
And I validate the 3rd Row of the Entry Status link is "exclude"
And I validate the 4th Row of the Entry Status link is "exclude"
And I validate the 5th Row of the Entry Status link is "exclude"

Scenario: TC5736: Notifications - Zones - Include Link (The exclude link tests also test the include link so I'm excluding this test for now)
!-- Given I am logged in
!-- When I click the Notifications link
!-- And I click the Zones link
!-- And I select "Top" from the Team dropdown
!-- And I select "Past 30 Days" from the Time Frame dropdown
!-- And I click the Refresh button
!-- And I save the 1st Row of the Entry Date Time text as SAVEDZONESDATETIME
!-- And I save the 1st Row of the Entry Detail text as SAVEDZONESDETAIL
!-- And I combine SAVEDZONESDETAIL with "
!-- " with SAVEDZONESDATETIME and save them as SAVEDZONESCOMBINED
!-- And I click the 1st Row of the Entry Status link
!-- And the Exclude Event popup opens
!-- Then I validate the Message text is SAVEDZONESCOMBINED
!-- And I click the Yes button
!-- And the Exclude Event popup closes
!-- And I click the Refresh button
!-- And I validate the 1st Row of the Entry Status link is "include"
!-- And I click the 1st Row of the Entry Status link
!-- And I validate the 1st Row of the Entry Status link is "exclude"

Scenario: TC5741: Notifications - Zones - Time Frame (Requires data from today and yesterday)
Given I am logged in
When I click the Notifications link
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Today" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDZONESDATETIME
And I click the Zones link
And I select "Top" from the Team dropdown
And I select "Yesterday" from the Time Frame dropdown
And I click the Refresh button
Then I validate the 1st Row of the Entry Date Time text is not SAVEDZONESDATETIME