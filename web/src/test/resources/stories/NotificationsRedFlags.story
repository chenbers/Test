Test Cases for TF388/TF411/TF489

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF489

Narrative:

Scenario: TC1452: Notifications - Red Flags - UI
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I check the 4th Row of the Column checkbox
And I check the 5th Row of the Column checkbox
And I check the 6th Row of the Column checkbox
And I check the 7th Row of the Column checkbox
And I check the 8th Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate I am on the Notifications Red Flags page
And I validate the Team dropdown is present
And I validate the Time Frame dropdown is present
And I validate the Refresh button is present
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Records text is present
And I validate the Header Level text is present
And I validate the Header Alert Details text is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present
And I validate the Header Status text is present
And I validate the Level Filter dropdown is present
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Category dropdown is present
And I validate the Status dropdown is present

Scenario: TC1464: Notifications - Red Flags - Edit Columns - UI
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the 1st Row of the Column checkbox is present
And I validate the 2nd Row of the Column checkbox is present
And I validate the 3rd Row of the Column checkbox is present
And I validate the 4th Row of the Column checkbox is present
And I validate the 5th Row of the Column checkbox is present
And I validate the 6th Row of the Column checkbox is present
And I validate the 7th Row of the Column checkbox is present
And I validate the 8th Row of the Column checkbox is present
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 5th Row of the Column checkbox
And I uncheck the 6th Row of the Column checkbox
And I uncheck the 7th Row of the Column checkbox
And I uncheck the 8th Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is not checked
And I validate the 2nd Row of the Column checkbox is not checked
And I validate the 3rd Row of the Column checkbox is not checked
And I validate the 4th Row of the Column checkbox is not checked
And I validate the 5th Row of the Column checkbox is not checked
And I validate the 6th Row of the Column checkbox is not checked
And I validate the 7th Row of the Column checkbox is not checked
And I validate the 8th Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I check the 4th Row of the Column checkbox
And I check the 5th Row of the Column checkbox
And I check the 6th Row of the Column checkbox
And I check the 7th Row of the Column checkbox
And I check the 8th Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I validate the 2nd Row of the Column checkbox is checked
And I validate the 3rd Row of the Column checkbox is checked
And I validate the 4th Row of the Column checkbox is checked
And I validate the 5th Row of the Column checkbox is checked
And I validate the 6th Row of the Column checkbox is checked
And I validate the 7th Row of the Column checkbox is checked
And I validate the 8th Row of the Column checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present

Scenario: TC1434: Notifications - Red Flags - Bookmark Entry 
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I log back in
Then I validate I am on the Notifications Red Flags page

Scenario: Notifications - Red Flags - Bookmark Entry to Different Account
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I log back in under the editable account
Then I validate I am on the Notifications Red Flags page

Scenario: TC1437: Notifications - Red Flags - Driver Link
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Past Year" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Driver link as SAVEDREDFLAGSDRIVERENTRY
And I click the 1st Row of the Entry Driver link
Then I validate the Driver Name link contains SAVEDREDFLAGSDRIVERENTRY

Scenario: Notifications - Red Flags - Group Link
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Past Year" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Group link
And I save the 1st Row of the Entry Group link as SAVEDREDFLAGSGROUPENTRY
And I click the 1st Row of the Entry Group link
And I validate the Driver Team Value text is SAVEDREDFLAGSGROUPENTRY
Then I validate the Team Name text is SAVEDREDFLAGSGROUPENTRY

Scenario: TC1450: Notifications - Red Flags - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
!-- Given I am logged in
!-- When I click the Notifications link
!-- And I click the Red Flags link
!-- And I select "Top" from the Team dropdown
!-- And I select "Past Year" from the Time Frame dropdown
!-- And I click the Refresh button
!-- Then I validate the Sort By Date Time column sorts correctly
!-- And I validate the Sort By Driver column sorts correctly
!-- And I validate the Sort By Driver column sorts correctly
!-- And I validate the Sort By Group column sorts correctly
!-- And I validate the Sort By Group column sorts correctly
!-- And I validate the Sort By Vehicle column sorts correctly
!-- And I validate the Sort By Vehicle column sorts correctly

Scenario: TC1451: Notifications - Red Flags - Tools Button
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I click the Tools button
Then I validate the Email This Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC1453: Notifications - Red Flags - Vehicle Link
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Past Year" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Vehicle link
And I click the Sort By Vehicle link
And I save the 1st Row of the Entry Vehicle link as SAVEDREDFLAGSVEHICLEENTRY
And I click the 1st Row of the Entry Vehicle link
Then I validate I am on the Vehicle Performance page
And I validate the Vehicle Name link contains SAVEDREDFLAGSVEHICLEENTRY

Scenario: TC1455: Notifications - Red Flags - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 5th Row of the Column checkbox
And I uncheck the 6th Row of the Column checkbox
And I uncheck the 7th Row of the Column checkbox
And I uncheck the 8th Row of the Column checkbox
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Header Level text is present
And I validate the Header Alert Details text is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present
And I validate the Header Status text is present
And I validate the Level Filter dropdown is present
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Category dropdown is present
And I validate the Status dropdown is present

Scenario: TC1456: Notifications - Red Flags - Edit Columns - Cancel Button (No Changes)
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Header Level text is present
And I validate the Header Alert Details text is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present
And I validate the Header Status text is present
And I validate the Level Filter dropdown is present
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Category dropdown is present
And I validate the Status dropdown is present

Scenario: TC1457: Notifications - Red Flags - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
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
And I check the 7th Row of the Column checkbox
And I validate the 7th Row of the Column checkbox is checked
And I uncheck the 7th Row of the Column checkbox
And I validate the 7th Row of the Column checkbox is not checked
And I check the 8th Row of the Column checkbox
And I validate the 8th Row of the Column checkbox is checked
And I uncheck the 8th Row of the Column checkbox
And I validate the 8th Row of the Column checkbox is not checked

Scenario: TC1459: Notifications - Red Flags - Edit Columns - Current Session Retention
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Reports link
And I click the Notifications link
And I click the Red Flags link
Then I validate the Header Level text is not present
And I validate the Header Alert Details text is not present
And I validate the Sort By Date Time link is not present
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
Then I validate the Header Level text is present
And I validate the Header Alert Details text is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1460: Notifications - Red Flags - Edit Columns - Default Command Button
!-- Given I am logged in
!-- When I click the Notifications link
!-- And I click the Red Flags link
!-- And I click the Edit Columns link
!-- And the Edit Columns popup opens
!-- And I uncheck the 1st Row of the Column checkbox
!-- And I click the Save button
!-- And the Edit Columns popup closes
!-- Then I validate the Header Level text is not present
!-- And I click the Edit Columns link
!-- And the Edit Columns popup opens
!-- And I validate the 1st Row of the Column checkbox is not checked
!-- And I check the 1st Row of the Column checkbox
!-- And I press the Enter Key
!-- And the Edit Columns popup closes
!-- And I validate the Header Level text is present

Scenario: TC1461: Notifications - Red Flags - Edit Columns - Save Button
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Header Level text is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Header Level text is present

Scenario: TC1462: Notifications - Red Flags - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
Then I validate the Header Level text is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
Then I validate the Header Level text is present

Scenario: TC1467: Notifications - Red Flags - Exclude Link  - Default Command Button
!-- Given I am logged in
!-- When I click the Notifications link
!-- And I click the Red Flags link
!-- And I select "Top" from the Team dropdown
!-- And I select "Past Year" from the Time Frame dropdown
!-- And I click the Refresh button
!-- And I save the Records text as TABLECOUNT
!-- And I click the 1st Row of the Entry Status link
!-- And the Exclude Event popup opens
!-- And I press the Enter key
!-- And the Exclude Event popup closes
!-- Then I validate the Records text is TABLECOUNT
!-- And I validate the 1st Row of the Entry Status link is "include"

Scenario: TC1472: Notifications - Red Flags - Exclude Link - UI
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Past Year" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDREDFLAGSDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDREDFLAGSDETAIL
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
Then I validate the Message text contains SAVEDREDFLAGSDATETIME
And I validate the Message text contains SAVEDREDFLAGSDETAIL
And I validate the Yes button is present
And I validate the No button is present
And I validate the Close button is present

Scenario: TC1465: Notifications - Red Flags - Exclude Link - No Button
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Past Year" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDREDFLAGSDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDREDFLAGSDETAIL
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
And I click the No button
And the Exclude Event popup closes
Then I validate the 1st Row of the Entry Date Time text is SAVEDREDFLAGSDATETIME
And I validate the 1st Row of the Entry Detail text is SAVEDREDFLAGSDETAIL

Scenario: TC1469: Notifications - Red Flags - Exclude and Include links - Yes Button
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Past Year" from the Time Frame dropdown
And I click the Refresh button
And I save the Records text as REDFLAGSTABLECOUNT
And I save the 1st Row of the Entry Date Time text as SAVEDREDFLAGSDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDREDFLAGSDETAIL
And I save the 1st Row of the Entry Category text as SAVEDREDFLAGSCATEGORY
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
And I click the Yes button
And the Exclude Event popup closes
Then I validate the Records text is REDFLAGSTABLECOUNT
And I select "excluded" from the Status dropdown
And I validate the 1st Row of the Entry Date Time text is SAVEDREDFLAGSDATETIME
And I validate the 1st Row of the Entry Detail text is SAVEDREDFLAGSDETAIL
And I validate the 1st Row of the Entry Category text is SAVEDREDFLAGSCATEGORY
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

Scenario: TC5739: Notifications - Red Flags - Include Link (The exclude link tests also test the include link so I'm excluding this test for now)
!-- Given I am logged in
!-- When I click the Notifications link
!-- And I click the Red Flags link
!-- And I select "Top" from the Team dropdown
!-- And I select "Past Year" from the Time Frame dropdown
!-- And I click the Refresh button
!-- And I click the 1st Row of the Entry Status link
!-- And the Exclude Event popup opens
!-- And I click the Yes button
!-- And the Exclude Event popup closes
!-- Then I validate the 1st Row of the Entry Status link is "include"
!-- And I click the 1st Row of the Entry Status link
!-- And I validate the 1st Row of the Entry Status link is "exclude"

Scenario: TC5744: Notifications - Red Flags - Time Frame (requires entries from today and yesterday)
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Today" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDREDFLAGSDATETIME
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Yesterday" from the Time Frame dropdown
And I click the Refresh button
Then I validate the 1st Row of the Entry Date Time text is not SAVEDREDFLAGSDATETIME