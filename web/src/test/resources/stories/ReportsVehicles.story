Meta:
@page login
@testFolder TF388
@testFolder TF416
@testFolder TF502

Narrative:

Scenario: Check all boxes on vehicles edit columns and save
Given I am logged in
When I click the Reports link
And I click the Vehicles link
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
And I check the 9th Row of the Column checkbox
And I click the the Save button

Scenario: TC1641: Reports - Vehicles - UI
Given I am logged in
When I click the Reports link
And I click the Vehicles link
Then I validate I am on the Reports Vehicles page
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Records text is present
And I validate the Sort By Group link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Year Make Model link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Odometer link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I validate the Group Value text table link is present
And I validate the Vehicle Value text table link is present
And I validate the Year Make Model link is present
And I validate the Sort By Driver link is present
And I validate the Overall dropdown is present
And I validate the Speed dropdown is present
And I validate the Style dropdown is present

Scenario: TC1614: Reports - Vehicles - Bookmark Entry 
Given I am logged in
And I select "Vehicles" from the Master Search dropdown
And I click the Master Search button
And I save the 1st Row of the Vehicle Value link as SAVEDREPORTSVEHICLESVEHICLE1
When I bookmark the page
And I click the Logout link
And I click the bookmark I just added
When I log back in
Then I validate I am on the Reports Vehicles page
And I validate the Master Search textfield is ""
And I validate that the 1st Row of the Vehicle Value link is SAVEDREPORTSVEHICLESVEHICLE1

Scenario: Reports - Vehicles - Bookmark Entry to Different Account
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I log back in under the editable account
Then I validate I am on the Reports Vehicles page

Scenario: TC1619: Reports - Vehicles - Driving Style Score Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I save the 1st Row of the Vehicle Value link as SAVEDREPORTSVEHICLESVEHICLE2
And I save the 1st Row of the Style Value link as SAVEDREPORTSVEHICLESSTYLE2
And I click the 1st Row of the Style Value link
And I click the twelve months Duration link
Then I validate the Overall Breakdown Score text is SAVEDREPORTSVEHICLESSTYLE2
And I validate the Vehicle Name link is SAVEDREPORTSVEHICLESVEHICLE2

Scenario: TC1624: Reports - Vehicles - Group Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I save the 1st Row of the Group Value link as SAVEDREPORTSVEHICLESGROUP3
And I click the the 1st Row of the Group Value link
Then I validate the Driver Team Value text is SAVEDREPORTSVEHICLESGROUP3

Scenario: TC1627: Reports - Vehicles - Overall Score Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I save the 1st Row of the Vehicle Value link as SAVEDREPORTSVEHICLESVEHICLE4
And I save the 1st Row of the Overall Value link as SAVEDREPORTSVEHICLESOVERALL4
And I click the 1st Row of the Overall Value link
And I click the twelve months Overall Duration link
Then I validate the Overall Score text is SAVEDREPORTSVEHICLESOVERALL4
And I validate the Vehicle Name link is SAVEDREPORTSVEHICLESVEHICLE4

Scenario: TC1637: Reports - Vehicles - Speed Score Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I save the 1st Row of the Vehicle Value link as SAVEDREPORTSVEHICLESVEHICLE5
And I save the 1st Row of the Speed Value link as SAVEDREPORTSVEHICLESSPEED5
And I click the 1st Row of the Overall Value link
And I click the twelve months Overall Duration link
Then I validate the Speed Score text is SAVEDREPORTSVEHICLESSPEED5
And I validate the Vehicle Name link is SAVEDREPORTSVEHICLESVEHICLE5

Scenario: TC1639: Reports - Vehicles - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
!-- Given I am logged in
!-- When I click the Reports link
!-- And I click the Vehicles link
!-- And I click the Sort By Vehicle ID link
!-- And I click the Sort By Group link
!-- And I click the Sort By Year Make Model link
!-- And I click the Sort By Driver link
!-- And I click the Sort By Distance Driven link
!-- And I click the Sort By Odometer link
!-- And I click the Sort By Overall link
!-- And I click the Sort By Speed link
!-- And I click the Sort By Style link

Scenario: TC1640: Reports - Vehicles - Tools Button
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Tools button
Then I validate the Email This Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC1642: Reports - Vehicles - Vehicle ID Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I save the 1st Row of the Vehicle Value link as SAVEDREPORTSVEHICLESVEHICLE6
And I click the 1st Row of the Vehicle Value link
Then I validate I am on the Vehicle Performance page
And I validate the Vehicle Name link contains SAVEDREPORTSVEHICLESVEHICLE6

Scenario: Reports - Vehicles - Driver Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Sort By Driver link
And I click the Sort By Driver link
And I save the 1st Row of the Driver Value link as SAVEDREPORTSVEHICLESDRIVER7
And I click the 1st Row of the Driver Value link
Then I validate I am on the Driver Performance page
And I validate the Vehicle Name link contains SAVEDREPORTSVEHICLESDRIVER7

Scenario: TC1644: Reports - Vehicles - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck 1st Row of the Column checkbox
And I uncheck 2nd Row of the Column checkbox
And I uncheck 3rd Row of the Column checkbox
And I uncheck 4th Row of the Column checkbox
And I uncheck 5th Row of the Column checkbox
And I uncheck 6th Row of the Column checkbox
And I uncheck 7th Row of the Column checkbox
And I uncheck 8th Row of the Column checkbox
And I uncheck 9th Row of the Column checkbox
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Group link is present
And I validate the Sort By VehicleID link is present
And I validate the Sort By YearMakeModel link is present
And I validate the Sort By Driver link is present
And I validate the Sort By DistanceDriven link is present
And I validate the Sort By Odometer link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present

Scenario: TC1645: Reports - Vehicles - Edit Columns - Cancel Button (No Changes)
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Group link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Year Make Model link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Odometer link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present

Scenario: TC1646: Reports - Vehicles - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck 1st Row of the Column checkbox
Then I validate the 1st Row of the Column checkbox is not checked
And I uncheck 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is not checked
And I uncheck 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is not checked
And I uncheck 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is not checked
And I uncheck 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is not checked
And I uncheck 6th Row of the Column checkbox
And I validate the 6th Row of the Column checkbox is not checked
And I uncheck 7th Row of the Column checkbox
And I validate the 7th Row of the Column checkbox is not checked
And I uncheck 8th Row of the Column checkbox
And I validate the 8th Row of the Column checkbox is not checked
And I uncheck 9th Row of the Column checkbox
And I validate the 9th Row of the Column checkbox is not checked
And I check 1st Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I check 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is checked
And I check 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is checked
And I check 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is checked
And I check 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is checked
And I check 6th Row of the Column checkbox
And I validate the 6th Row of the Column checkbox is checked
And I check 7th Row of the Column checkbox
And I validate the 7th Row of the Column checkbox is checked
And I check 8th Row of the Column checkbox
And I validate the 8th Row of the Column checkbox is checked
And I check 9th Row of the Column checkbox
And I validate the 9th Row of the Column checkbox is checked
And I click the Cancel button

Scenario: TC1648: Reports - Vehicles - Edit Columns - Current Session Retention
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck 1st Row of the Column checkbox
And I uncheck 2nd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the 1st Row of the Group Value link is not present
And I validate the 1st Row of the Vehicle Value link is not present
And I validate the 1st Row of the Sort By Year Make Model link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Odometer link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check 1st Row of the Column checkbox
And I check 2nd Row of the Column checkbox
And I click the Save button

Scenario: TC6275: Reports - Vehicles - Edit Columns - Default Command Button (THERE CURRENTLY IS NO DEFAULT KEY, REMOVING THIS TEST)
!-- Given I am logged in
!-- When I click the Reports link
!-- And I click the Vehicles link
!-- And I click the Edit Columns link
!-- And the Edit Columns popup opens
!-- And I uncheck the 1st Row of the Column checkbox
!-- And I press the Enter Key
!-- And the Edit Columns popup closes
!-- Then I validate the 1st Row of the Group Value link is not present
!-- And I click the Edit Columns link
!-- And the Edit Columns popup opens
!-- And I validate the 1st Row of the Column checkbox is not checked
!-- And I check the 1st Row of the Column checkbox
!-- And I press the Enter Key
!-- And the Edit Columns popup closes
!-- And I validate the 1st Row of the Group Value link is present

Scenario: TC1650: Reports - Vehicles - Edit Columns - Save Button
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the 1st Row of the Group Value link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the 1st Row of the Group Value link is present

Scenario: TC1651: Reports - Vehicles - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
Then I validate the 1st Row of the Group Value link is not present
When I click the Edit Columns link
And the EditColumns popup opens
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
And I click the Reports link
And I click the Vehicles link
Then I validate the 1st Row of the Group Value link is present

Scenario: TC1653: Reports - Vehicles - Edit Columns - UI
Given I am logged in
When I click the Reports link
And I click the Vehicles link
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
And I validate the 9th Row of the Column checkbox is present
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I check the 4th Row of the Column checkbox
And I check the 5th Row of the Column checkbox
And I check the 6th Row of the Column checkbox
And I check the 7th Row of the Column checkbox
And I check the 8th Row of the Column checkbox
And I check the 9th Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I validate the 2nd Row of the Column checkbox is checked
And I validate the 3rd Row of the Column checkbox is checked
And I validate the 4th Row of the Column checkbox is checked
And I validate the 5th Row of the Column checkbox is checked
And I validate the 6th Row of the Column checkbox is checked
And I validate the 7th Row of the Column checkbox is checked
And I validate the 8th Row of the Column checkbox is checked
And I validate the 9th Row of the Column checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present
And I click the Save button