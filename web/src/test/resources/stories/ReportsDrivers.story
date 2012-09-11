Test Cases for TF388/TF416/TF547 

Meta:
@page login
@testFolder TF388
@testFolder TF416
@testFolder TF547

Narrative:

Scenario: TC1540: Reports - Drivers - Bookmark Entry 
Given I am logged in
And I click the Master Search button
And I save the 1st Row of the Driver Value link as SAVEDDRIVER
And I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
Given I am logged in
Then I validate I am on the Reports Drivers page
And I validate the Master Search textfield is ""
And I validate that the 1st Row of the Driver Value link is SAVEDDRIVER

Scenario: TC1543: Reports - Drivers - Driver Link
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I save the 1st Row of the Driver Value link as SAVEDDRIVER
And I click the 1st Row of the Driver Value link
Then I validate the Driver Name link is SAVEDDRIVER

Scenario: TC1545: Reports - Drivers - Driving Style Score Link
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I save the 1st Row of the Driver Value link as SAVEDDRIVER
And I save the 1st Row of the Style Value link as SAVEDSTYLE
And I click the 1st Row of the Style Value link
And I click the twelve months Duration link
Then I validate the Driving Style Score Value text is SAVEDSTYLE
And I validate the Driver Name link is SAVEDDRIVER

Scenario: TC1550: Reports - Drivers - Group Link
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I save the 1st Row of the Group Value link as SAVEDENTRY
And I click the 1st Row of the Group Value link
Then I validate the Driver Team Value text is SAVEDENTRY

Scenario: TC1553: Reports - Drivers - Overall Score Link
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I save the 1st Row of the Driver Value link as SAVEDDRIVER
And I save the 1st Row of the Overall Value link as SAVEDOVERALL
And I click the 1st Row of the Overall Value link
And I click the twelve months Overall Duration link
Then I validate the Overall Score text is SAVEDOVERALL
And I validate the Driver Name link is SAVEDDRIVER

Scenario: TC1562: Reports - Drivers - Seat Belt Score Link
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I save the 1st Row of the Driver Value link as SAVEDDRIVER
And I save the 1st Row of the Seatbelt Value link as SAVEDSEATBELT
And I click the 1st Row of the Seatbelt Value link
And I click the twelve months Duration link
Then I validate the Overall Score Value text is SAVEDSEATBELT
And I validate the Driver Name link is SAVEDDRIVER

Scenario: TC1564: Reports - Drivers - Speed Score Link
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I save the 1st Row of the Driver Value link as SAVEDDRIVER
And I save the 1st Row of the Speed Value link as SAVEDSPEED
And I click the 1st Row of the Seatbelt Value link
And I click the twelve months Duration link
Then I validate the Overall Score Value text is SAVEDSPEED
And I validate the Driver Name link is SAVEDDRIVER

Scenario: TC1566: Reports - Drivers - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in
When I click the Reports link
And I click the Sort By Driver link
And I click the Sort By Group link
And I click the Sort By Group link
And I click the Sort By Vehicle link
And I click the Sort By Vehicle link
And I click the Sort By Distance Driven link
And I click the Sort By Distance Driven link
And I click the Sort By Overall link
And I click the Sort By Overall link
And I click the Sort By Speed link
And I click the Sort By Speed link
And I click the Sort By Style link
And I click the Sort By Style link
And I click the Sort By Seatbelt link
And I click the Sort By Seatbelt link

Scenario: TC1567: Reports - Drivers - Tools Button
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I click the Tools button
Then I validate the Export Email button is present
And I validate the Export PDF button is present
And I validate the Export Excel button is present

Scenario: TC1568: Reports - Drivers - UI
Given I am logged in
When I click the Reports link
And I click the Drivers link
Then I validate I am on the Drivers page
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Sort By Driver link is present
And I validate the Sort By Group link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I validate the Sort By Seatbelt link is present
And I validate the Group Search textfield is present
And I validate the Driver Search textfield is present
And I validate the Vehicle Search textfield is present
And I validate the Overall Filter dropdown is present
And I validate the Speed Filter dropdown is present
And I validate the Style Filter dropdown is present
And I validate the Seatbelt Filter dropdown is present

Scenario: TC1569: Reports - Drivers - Vehicle Link
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I click the Sort By Vehicle link
And I click the Sort By Vehicle link
And I save the 1st Row of the Vehicle Value link as SAVEDENTRY
And I click the 1st Row of the Vehicle Value link
Then I validate the Vehicle Name link is SAVEDENTRY

Scenario: TC1571: Reports - Drivers - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Reports link
And I click the Drivers link
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
And I uncheck the 9th Row of the Column checkbox
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Driver link is present
And I validate the Sort By Group link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I validate the Sort By Seatbelt link is present

Scenario: TC1572: Reports - Drivers - Edit Columns - Cancel Button (No Changes)
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Driver link is present
And I validate the Sort By Group link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I validate the Sort By Seatbelt link is present

Scenario: TC1573: Reports - Drivers - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
Then I validate the 1st Row of the Column checkbox is not checked
And I uncheck the 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is not checked
And I uncheck the 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is not checked
And I uncheck the 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is not checked
And I uncheck the 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is not checked
And I uncheck the 6th Row of the Column checkbox
And I validate the 6th Row of the Column checkbox is not checked
And I uncheck the 7th Row of the Column checkbox
And I validate the 7th Row of the Column checkbox is not checked
And I uncheck the 8th Row of the Column checkbox
And I validate the 8th Row of the Column checkbox is not checked
And I uncheck the 9th Row of the Column checkbox
And I validate the 9th Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I check the 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is checked
And I check the 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is checked
And I check the 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is checked
And I check the 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is checked
And I check the 6th Row of the Column checkbox
And I validate the 6th Row of the Column checkbox is checked
And I check the 7th Row of the Column checkbox
And I validate the 7th Row of the Column checkbox is checked
And I check the 8th Row of the Column checkbox
And I validate the 8th Row of the Column checkbox is checked
And I check the 9th Row of the Column checkbox
And I validate the 9th Row of the Column checkbox is checked
And I click the Cancel button

Scenario: TC1575: Reports - Drivers - Edit Columns - Current Session Retention
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Notifications link
And I click the Reports link
And I click the Drivers link
Then I validate the Sort By Driver link is not present
And I validate the Sort By Group link is not present
And I validate the Sort By Employee ID link is not present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I validate the Sort By Seatbelt link is present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Driver link is present
And I validate the Sort By Group link is present
And I validate the Sort By Employee ID link is present

Scenario: TC1576: Reports - Drivers - Edit Columns - Default Command Button
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I press the Enter Key
And the Edit Columns popup closes
Then I validate the Sort By Group link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I press the Enter Key
And the Edit Columns popup closes
And I validate the Sort By Group link is present

Scenario: TC1577: Reports - Drivers - Edit Columns - Save Button
Given I am logged in
When I click the Reports link
And I click the Drivers link
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
And I validate the Sort By Group link is present

Scenario: TC1578: Reports - Drivers - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Reports link
And I click the Drivers link
Then I validate the Sort By Group link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Reports link
And I click the Drivers link
Then I validate the Sort By Group link is present

Scenario: TC1580: Reports - Drivers - Edit Columns - UI
Given I am logged in
When I click the Reports link
And I click the Drivers link
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
And I validate the 1st Row of the Column checkbox is checked
And I validate the 2nd Row of the Column checkbox is not checked
And I validate the 3rd Row of the Column checkbox is checked
And I validate the 4th Row of the Column checkbox is checked
And I validate the 5th Row of the Column checkbox is checked
And I validate the 6th Row of the Column checkbox is checked
And I validate the 7th Row of the Column checkbox is checked
And I validate the 8th Row of the Column checkbox is checked
And I validate the 9th Row of the Column checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present
