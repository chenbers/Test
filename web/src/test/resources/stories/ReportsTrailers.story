Meta:
@page login

Narrative:Test the functionality of the Reports Trailers page

Scenario: Check all boxes on vehicles edit columns and save so they are visible for all the tests in this suite
Given I am logged in
When I click the Reports link
And I click the Trailers link
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
And I check the 10th Row of the Column checkbox
And I click the the Save button
And the Edit Columns popup closes

Scenario: Reports - Trailers - UI
Given I am logged in
When I click the Reports link
And I click the Trailers link
Then I validate I am on the Reports Trailers page
And I validate the Title text is "Trailer Report"
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Sort By Group link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Year Make Model link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Odometer link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I validate the Group Filter textfield is present
And I validate the Trailer Filter textfield is present
And I validate the Vehicle Filter textfield is present
And I validate the Year Make Model Filter textfield is present
And I validate the Driver Filter textfield is present
And I validate the Overall Filter dropdown is present
And I validate the Speed Filter dropdown is present
And I validate the Style Filter dropdown is present
And I validate the Group Value text table link is present
And I validate the Trailer Value text table text is present
And I validate the Vehicle Value text table link is present
And I validate the Year Make Model Value text table text is present
And I validate the Driver Value text table link is present
And I validate the Distance Driven Value text table text is present
And I validate the Odometer Value text table text is present
And I validate the Overall Value text table link is present
And I validate the Speed Value text table link is present
And I validate the Style Value text table link is present
And I click the Tools button
And I validate the Email Report button is present
And I validate the Export Excel button is present
And I validate the Export Pdf button is present

Scenario: Reports - Trailers - Edit Columns - UI
Given I am logged in
When I click the Reports link
And I click the Trailers link
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
And I validate the 10th Row of the Column checkbox is present
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I check the 4th Row of the Column checkbox
And I check the 5th Row of the Column checkbox
And I check the 6th Row of the Column checkbox
And I check the 7th Row of the Column checkbox
And I check the 8th Row of the Column checkbox
And I check the 9th Row of the Column checkbox
And I check the 10th Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I validate the 2nd Row of the Column checkbox is checked
And I validate the 3rd Row of the Column checkbox is checked
And I validate the 4th Row of the Column checkbox is checked
And I validate the 5th Row of the Column checkbox is checked
And I validate the 6th Row of the Column checkbox is checked
And I validate the 7th Row of the Column checkbox is checked
And I validate the 8th Row of the Column checkbox is checked
And I validate the 9th Row of the Column checkbox is checked
And I validate the 10th Row of the Column checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present
And I click the Save button

Scenario: Reports - Trailers - Bookmark Entry 
Given I am logged in
And I select "Trailers" from the Master Search dropdown
And I click the Master Search button
And I save the 1st Row of the Trailer Value link as SAVEDVEHICLE
When I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
When I log back in
Then I validate I am on the Reports Trailers page
And I validate the Master Search textfield is ""
And I validate that the 1st Row of the Trailer Value link is SAVEDVEHICLE

Scenario: Reports - Trailers - Driving Style Score Link
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I save the 1st Row of the Vehicle Value link as SAVEDVEHICLE
And I save the 1st Row of the Style Value link as SAVEDSTYLE
And I click the 1st Row of the Style Value link
And I click the twelve months Duration link
Then I validate the Overall Breakdown Score text is SAVEDSTYLE
And I validate the Vehicle Name link is SAVEDVEHICLE

Scenario: Reports - Trailers - Group Link
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I save the 1st Row of the Group Value link as SAVEDGROUP
And I click the the 1st Row of the Group Value link
Then I validate the Driver Team Value text is SAVEDGROUP

Scenario: Reports - Trailers - Overall Score Link
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I save the 1st Row of the Vehicle Value link as SAVEDVEHICLE
And I save the 1st Row of the Overall Value link as SAVEDOVERALL
And I click the 1st Row of the Overall Value link
And I click the twelve months OverallDuration link
Then I validate the Overall Score text is SAVEDOVERALL
And I validate the Vehicle Name link is SAVEDVEHICLE

Scenario: Reports - Trailers - Speed Score Link
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I save the 1st Row of the Vehicle Value link as SAVEDVEHICLE
And I save the 1st Row of the Speed Value link as SAVEDSPEED
And I click the 1st Row of the Vehicle Value link as SAVEDVEHICLE
And I click the twelve months Overall Duration link
Then I validate the Speed Score text is SAVEDSPEED
And I validate the Vehicle Name link is SAVEDVEHICLE

Scenario: Reports - Trailers - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I click the Sort By Trailer ID link
And I click the Sort By Vehicle ID link
And I click the Sort By Group link
And I click the Sort By Year Make Model link
And I click the Sort By Driver link
And I click the Sort By Distance Driven link
And I click the Sort By Odometer link
And I click the Sort By Overall link
And I click the Sort By Speed link
And I click the Sort By Style link

Scenario: Reports - Trailers - Vehicle ID Link
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I save the 1st Row of the Vehicle Value link as SAVEDVEHICLE
And I click the 1st Row of the Vehicle Value link
Then I validate I am on the Vehicle Performance page
And I validate the Vehicle Name link contains SAVEDVEHICLE

Scenario: Reports - Trailers - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Reports link
And I click the Trailers link
Then I validate the Sort By Group link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Year Make Model link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Odometer link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
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
And I uncheck 10th Row of the Column checkbox
And I click the Cancel button
And the Edit Columns popup closes
And I validate the Sort By Group link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Year Make Model link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Odometer link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present

Scenario: Reports - Trailers - Edit Columns - Cancel Button (No Changes)
Given I am logged in
When I click the Reports link
And I click the Trailers link
Then I validate the Sort By Group link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Year Make Model link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Odometer link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the Cancel button
And the Edit Columns popup closes
And I validate the Sort By Group link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Year Make Model link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Odometer link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present

Scenario: Reports - Trailers - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Reports link
And I click the Trailers link
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
And I uncheck 10th Row of the Column checkbox
And I validate the 10th Row of the Column checkbox is not checked
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
And I check 10th Row of the Column checkbox
And I validate the 10th Row of the Column checkbox is checked
And I click the Cancel button

Scenario: Reports - Trailers - Edit Columns - Current Session Retention
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck 1st Row of the Column checkbox
And I uncheck 2nd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the 1st Row of the Group Value link is not present
And I validate the 1st Row of the Trailer Value link is not present
And I validate the 1st Row of the Vehicle Value link is present
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
And the Edit Columns popup closes
And I validate the 1st Row of the Group Value link is present
And I validate the 1st Row of the Trailer Value link is present

!-- Scenario: Reports - Trailers - Edit Columns - Default Command Button (THERE CURRENTLY IS NO DEFAULT KEY, REMOVING THIS TEST)
!-- Given I am logged in
!-- When I click the Reports link
!-- And I click the Trailers link
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

Scenario: Reports - Trailers - Edit Columns - Save Button
Given I am logged in
When I click the Reports link
And I click the Trailers link
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

Scenario: Reports - Trailers - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Reports link
And I click the Trailers link
Then I validate the 1st Row of the Group Value link is not present
When I click the Edit Columns link
And the EditColumns popup opens
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
And I click the Reports link
And I click the Trailers link
Then I validate the 1st Row of the Group Value link is present