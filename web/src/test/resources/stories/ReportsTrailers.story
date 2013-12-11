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
And I click the the Save button
And the Edit Columns popup closes
Then I validate the Sort By Status link is present
And I validate the Sort By Group link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Assigned Status link is present
And I validate the Sort By Entry Method link is present

Scenario: Reports - Trailers - UI
Given I am logged in
When I click the Reports link
And I click the Trailers link
Then I validate I am on the Reports Trailers page
And I validate the Title text is "Trailer Report"
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Sort By Status link is present
And I validate the Sort By Group link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Assigned Status link is present
And I validate the Sort By Entry Method link is present
And I validate the Status Filter dropdown is present
And I validate the Group Filter textfield is present
And I validate the Trailer Filter textfield is present
And I validate the Vehicle Filter textfield is present
And I validate the Driver Filter textfield is present
And I validate the Assigned Status Filter dropdown is present
And I validate the Entry Method Filter dropdown is present
And I validate the 1st Row of the Status Value text is present
And I validate the 1st Row of the Group Value link is present
And I validate the 1st Row of the Trailer Value text is present
And I validate the 1st Row of the Vehicle Value link is present
And I validate the 1st Row of the Driver Value link is present
And I validate the 1st Row of the Assigned Status Value text is present
And I validate the 1st Row of the Entry Method Value text is present
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
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 5th Row of the Column checkbox
And I uncheck the 6th Row of the Column checkbox
And I uncheck the 7th Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is not checked
And I validate the 2nd Row of the Column checkbox is not checked
And I validate the 3rd Row of the Column checkbox is not checked
And I validate the 4th Row of the Column checkbox is not checked
And I validate the 5th Row of the Column checkbox is not checked
And I validate the 6th Row of the Column checkbox is not checked
And I validate the 7th Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I check the 4th Row of the Column checkbox
And I check the 5th Row of the Column checkbox
And I check the 6th Row of the Column checkbox
And I check the 7th Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I validate the 2nd Row of the Column checkbox is checked
And I validate the 3rd Row of the Column checkbox is checked
And I validate the 4th Row of the Column checkbox is checked
And I validate the 5th Row of the Column checkbox is checked
And I validate the 6th Row of the Column checkbox is checked
And I validate the 7th Row of the Column checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present
And I click the Save button

Scenario: Reports - Trailers - Bookmark Entry 
Given I am logged in
And I select "Trailers" from the Master Search dropdown
And I click the Master Search button
And I save the 1st Row of the Trailer Value text as SAVEDTRAILER
When I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
When I log back in
Then I validate I am on the Reports Trailers page
And I validate the Master Search textfield is ""
And I validate that the 1st Row of the Trailer Value text is SAVEDTRAILER

Scenario: Reports - Trailers - Group Link
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I save the 1st Row of the Group Value link as SAVEDGROUP
And I click the the 1st Row of the Group Value link
Then I validate the Driver Team Value text is SAVEDGROUP

Scenario: Reports - Trailers - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I click the Sort By Status link
And I click the Sort By Group link
And I click the Sort By Trailer ID link
And I click the Sort By Vehicle ID link
And I click the Sort By Driver link
And I click the Sort By Assigned Status link
And I click the Sort By Entry Method link

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
Then I validate the Sort By Status link is present
And I validate the Sort By Group link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Assigned Status link is present
And I validate the Sort By Entry Method link is present
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck 1st Row of the Column checkbox
And I uncheck 2nd Row of the Column checkbox
And I uncheck 3rd Row of the Column checkbox
And I uncheck 4th Row of the Column checkbox
And I uncheck 5th Row of the Column checkbox
And I uncheck 6th Row of the Column checkbox
And I uncheck 7th Row of the Column checkbox
And I click the Cancel button
And the Edit Columns popup closes
And I validate the Sort By Status link is present
And I validate the Sort By Group link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Assigned Status link is present
And I validate the Sort By Entry Method link is present

Scenario: Reports - Trailers - Edit Columns - Cancel Button (No Changes)
Given I am logged in
When I click the Reports link
And I click the Trailers link
Then I validate the Sort By Status link is present
And I validate the Sort By Group link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Assigned Status link is present
And I validate the Sort By Entry Method link is present
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the Cancel button
And the Edit Columns popup closes
And I validate the Sort By Status link is present
And I validate the Sort By Group link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Assigned Status link is present
And I validate the Sort By Entry Method link is present

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
Then I validate the the Sort By Status link is not present
And I validate the the Sort By Group link is not present
And I validate the the Sort By Trailer ID link is present
And I validate the the Sort By Vehicle ID link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Assigned Status link is present
And I validate the Sort By Entry Method link is present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check 1st Row of the Column checkbox
And I check 2nd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the the Sort By Status link is present
And I validate the the Sort By Group link is present

Scenario: Reports - Trailers - Edit Columns - Save Button
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Group link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Group link is present

Scenario: Reports - Trailers - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Reports link
And I click the Trailers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Group link is not present
And I click the Logout link
When I log back in
And I click the Reports link
And I click the Trailers link
Then I validate the Sort By Group link is not present
When I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Group link is present
And I click the Logout link
When I log back in
And I click the Reports link
And I click the Trailers link
Then I validate the Sort By Group link is present