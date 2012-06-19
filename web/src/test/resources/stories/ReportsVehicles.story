Test Cases for TF388/TF416/TF502

Meta:
@page login
@testFolder TF388
@testFolder TF416
@testFolder TF502

Narrative:

Scenario: TC1614: Reports - Vehicles - Bookmark Entry 
Given I am logged in
And I select "Vehicles" from the Master Search dropdown
And I click the Master Search button
And I save the 1st Row of the Vehicle Value link as SavedVehicle
When I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
Given I am logged in
Then I validate I am on the Reports Vehicles page
And I validate the Master Search textfield is ""
And I validate that the 1st Row of the Vehicle Value link is SavedVehicle

Scenario: TC1617: Reports - Vehicles - Driver Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I save the 1st Row of the Driver Value link as SavedEntry
And I click the 1st Row of the Driver Value link
Then I validate the Driver Name textlink is SavedEntry

Scenario: TC1619: Reports - Vehicles - Driving Style Score Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I save the 1st Row of the Vehicle Value link as SavedVehicle
And I save the 1st Row of the Style Value link as SavedStyle
And I click the 1st Row of the Style Value link
And I click the twelve months Duration link
Then I validate the Overall Breakdown Score text is SavedStyle
And I validate the Vehicle Name link is SavedVehicle

Scenario: TC1624: Reports - Vehicles - Group Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I save the 1st Row of the Group Value link as SavedEntry
And I click the the 1st Row of the Group Value link
Then I validate the Driver Team Value text is SavedEntry

Scenario: TC1627: Reports - Vehicles - Overall Score Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I save the 1st Row of the Vehicle Value textlink as SavedVehicle
And I save the 1st Row of the Overall Value textlink as SavedOverall
And I click the 1st Row of the Overall Value textlink
And I click the twelve months OverallDuration link
Then I validate the Overall Score text is SavedOverall
And I validate the Vehicle Name textlink is SavedVehicle

Scenario: TC1637: Reports - Vehicles - Speed Score Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I save the 1st Row of the Vehicle Value textlink as SavedVehicle
And I save the 1st Row of the Speed Value textlink as SavedSpeed
And I click the 1st Row of the Vehicle Value textlink as SavedVehicle
And I click the twelve months Overall Duration link
Then I validate the Speed Score text is SavedSpeed
And I validate the Vehicle Name textlink is SavedVehicle

Scenario: TC1639: Reports - Vehicles - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Sort By Vehicle ID link
And I click the Sort By Group link
And I click the Sort By Year Make Model link
*And I click the Sort By Driver link
And I click the Sort By Distance Driven link
And I click the Sort By Odometer link
And I click the Sort By Overall link
And I click the Sort By Speed link
And I click the Sort By Style link

Scenario: TC1640: Reports - Vehicles - Tools Button
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Tools button
Then I validate the Email Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC1641: Reports - Vehicles - UI
Given I am logged in
When I click the Reports link
And I click the Vehicles link
Then I validate I am on the Reports Vehicles page
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
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
And I validate the Year Make Model textlink is present
And I validate the Driver Sort textlink is present
And I validate the Overall Filter dropdown is present
And I validate the Speed Filter dropdown is present
And I validate the Style Filter dropdown is present

Scenario: TC1642: Reports - Vehicles - Vehicle ID Link
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Sort By Vehicle ID link
And I click the Sort By Vehicle ID link
And I save the first entry in the Sort By Vehicle ID column as SavedEntry
*And I click the first entry in the Sort By Vehicle ID column
Then I validate the Vehicle Performance text is SavedEntry

Scenario: TC1644: Reports - Vehicles - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
*And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I uncheck the checkbox of the fourth entry
And I uncheck the checkbox of the fifth entry
And I uncheck the checkbox of the sixth entry
And I uncheck the checkbox of the seventh entry
And I uncheck the checkbox of the eighth entry
And I uncheck the checkbox of the ninth entry
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
*And the Edit Columns popup closes
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
And I uncheck the checkbox of the first entry
*Then I validate the checkbox of the first entry is not checked
And I uncheck the checkbox of the second entry
And I validate the checkbox of the second entry is not checked
And I uncheck the checkbox of the third entry
And I validate the checkbox of the third entry is not checked
And I uncheck the checkbox of the fourth entry
And I validate the checkbox of the fourth entry is not checked
And I uncheck the checkbox of the fifth entry
And I validate the checkbox of the fifth entry is not checked
And I uncheck the checkbox of the sixth entry
And I validate the checkbox of the sixth entry is not checked
And I uncheck the checkbox of the seventh entry
And I validate the checkbox of the seventh entry is not checked
And I uncheck the checkbox of the eighth entry
And I validate the checkbox of the eighth entry is not checked
And I uncheck the checkbox of the ninth entry
And I validate the checkbox of the ninth entry is not checked
And I check the checkbox of the first entry
And I validate the checkbox of the first entry is checked
And I check the checkbox of the second entry
And I validate the checkbox of the second entry is checked
And I check the checkbox of the third entry
And I validate the checkbox of the third entry is checked
And I check the checkbox of the fourth entry
And I validate the checkbox of the fourth entry is checked
And I check the checkbox of the fifth entry
And I validate the checkbox of the fifth entry is checked
And I check the checkbox of the sixth entry
And I validate the checkbox of the sixth entry is checked
And I check the checkbox of the seventh entry
And I validate the checkbox of the seventh entry is checked
And I check the checkbox of the eighth entry
And I validate the checkbox of the eighth entry is checked
And I check the checkbox of the ninth entry
And I validate the checkbox of the ninth entry is checked

Scenario: TC1648: Reports - Vehicles - Edit Columns - Current Session Retention
Given I am logged in
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
*And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I click the Save button
And the Edit Columns popup closes
And I click the Notifications link
And I click the Reports link
And I click the Vehicles link
Then I validate the Sort By Group link is not present
And I validate the Sort By Vehicle ID link is not present
And I validate the Sort By Year Make Model link is not present
And I validate the Sort By Driver link is present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Odometer link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present

Scenario: TC1650: Reports - Vehicles - Edit Columns - Save Button
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Group link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the checkbox of the first entry is not checked
And I check the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Group link is present

Scenario: TC1651: Reports - Vehicles - Edit Columns - Subsequent Session Retention
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
When I click the Reports link
And I click the Vehicles link
Then I validate the Sort By Group link is not present
When I click the Edit Columns link
And the EditColumns popup opens
And I check the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
And I click the Reports link
And I click the Vehicles link
Then I validate the Sort By Group link is present

Scenario: TC1653: Reports - Vehicles - Edit Columns - UI
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the first checkbox is present
And I validate the second checkbox is present
And I validate the third checkbox is present
And I validate the fourth checkbox is present
And I validate the fifth checkbox is present
And I validate the sixth checkbox is present
And I validate the seventh checkbox is present
And I validate the eighth checkbox is present
And I validate the ninth checkbox is present
And I validate the first checkbox is checked
And I validate the second checkbox is unchecked
And I validate the third checkbox is checked
And I validate the fourth checkbox is checked
And I validate the fifth checkbox is checked
And I validate the sixth checkbox is checked
And I validate the seventh checkbox is checked
And I validate the eighth checkbox is checked
And I validate the ninth checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present
