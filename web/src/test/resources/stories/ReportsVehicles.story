Test Cases for TF388/TF416/TF502

Meta:
@page login
@testFolder TF388
@testFolder TF416
@testFolder TF502

Narrative:

Scenario: TC1614: Reports - Vehicles - Bookmark Entry 
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I type "" into the Search textfield
And I click the Search button
And I bookmark the page
And I click log out
And I click the bookmark I just added
And I am on the login page
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
Then I validate I am on the Vehicles page
And I validate the Search textfield is ""
And I validate the Vehicle Report table is not ""

Scenario: TC1615: Reports - Vehicles - Bookmark Entry to Different Account CANNOT BE IMPLEMENTED YET
Given this step is pending

Scenario: TC1617: Reports - Vehicles - Driver Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I save the first entry in the SortByDriver column as SavedEntry
And I click the first entry in the SortByDriver column
Then I validate the Driver Performance text is SavedEntry

Scenario: TC1619: Reports - Vehicles - Driving Style Score Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I save the first entry in the SortByVehicleID column as SavedVehicle
And I save the first entry in the SortByStyle column as SavedStyle
And I click the first entry in the SortByStyle column
And I click the 12Months link
Then I validate the DrivingStyleOverall text is SavedStyle
And I validate the Vehicle Performance text is SavedVehicle

Scenario: TC1621: Reports - Vehicles - E-mail This Report CANNOT BE IMPLEMENTED YET DUE TO NEEDING EXTERNAL EMAIL PROGRAM
Given this step is pending

Scenario: TC1622: Reports - Vehicles - Export To Excel CANNOT BE IMPLEMENTED YET DUE TO EXCEL
Given this step is pending

Scenario: TC1623: Reports - Vehicles - Export To PDF CANNOT BE IMPLEMENTED YET DUE TO PDF
Given this step is pending

Scenario: TC1624: Reports - Vehicles - Group Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I save the first entry in the SortByGroup column as SavedEntry
And I click the first entry in the SortByGroup column
Then I validate the Group Performance text is SavedEntry

Scenario: TC1626: Reports - Vehicles - Hover Help CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1627: Reports - Vehicles - Overall Score Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I save the first entry in the SortByVehicleID column as SavedVehicle
And I save the first entry in the SortByOverall column as SavedOverall
And I click the first entry in the SortByOverall column
And I click the 12Months link
Then I validate the DrivingStyleOverall text is SavedOverall
And I validate the Vehicle Performance text is SavedVehicle

Scenario: TC1629: Reports - Vehicles - Page Link CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1634: Reports - Vehicles - Search NEEDS TO BE REWRITTEN IN RALLY, TEST NO LONGER APPLIES AS IS
Given this step is pending

Scenario: TC1637: Reports - Vehicles - Speed Score Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I save the first entry in the SortByVehicleID column as SavedVehicle
And I save the first entry in the SortBySpeed column as SavedSpeed
And I click the first entry in the SortBySeatbelt column
And I click the 12Months link
Then I validate the DrivingStyleSeatbelt text is SavedSpeed
And I validate the Vehicle Performance text is SavedVehicle

Scenario: TC1639: Reports - Vehicles - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the SortByVehicleID link
And I click the SortByGroup link
And I click the SortByGroup link
And I click the SortByYearMakeModel link
And I click the SortByYearMakeModel link
And I click the SortByDriver link
And I click the SortByDriver link
And I click the SortByDistanceDriven link
And I click the SortByDistanceDriven link
And I click the SortByOdometer link
And I click the SortByOdometer link
And I click the SortByOverall link
And I click the SortByOverall link
And I click the SortBySpeed link
And I click the SortBySpeed link
And I click the SortByStyle link
And I click the SortByStyle link

Scenario: TC1640: Reports - Vehicles - Tools Button
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the Tools button
Then I validate the EmailReport button is present
Then I validate the ExportToPDF button is present
Then I validate the ExportToExcel button is present

Scenario: TC1641: Reports - Vehicles - UI
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
Then I validate I am on the Vehicles page
And I validate the EditColumns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the SortByGroup link is present
And I validate the SortByVehicleID link is present
And I validate the SortByYearMakeModel link is present
And I validate the SortByDriver link is present
And I validate the SortByDistanceDriven link is present
And I validate the SortByOdometer link is present
And I validate the SortByOverall link is present
And I validate the SortBySpeed link is present
And I validate the SortByStyle link is present
And I validate the Group textfield is present
And I validate the VehicleID textfield is present
And I validate the YearMakeModel textfield is present
And I validate the Driver textfield is present
And I validate the Speed dropdown is present
And I validate the Overall dropdown is present
And I validate the Style dropdown is present

Scenario: TC1642: Reports - Vehicles - Vehicle ID Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the SortByVehicleID link
And I click the SortByVehicleID link
And I save the first entry in the SortByVehicleID column as SavedEntry
And I click the first entry in the SortByVehicleID column
Then I validate the Vehicle Performance text is SavedEntry

Scenario: TC1644: Reports - Vehicles - Edit Columns - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the EditColumns link
And the EditColumns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I uncheck the checkbox of the fourth entry
And I uncheck the checkbox of the fifth entry
And I uncheck the checkbox of the sixth entry
And I uncheck the checkbox of the seventh entry
And I uncheck the checkbox of the eighth entry
And I uncheck the checkbox of the ninth entry
And I click the Cancel button
And the EditColumns popup closes
Then I validate the SortByGroup link is present
And I validate the SortByVehicleID link is present
And I validate the SortByYearMakeModel link is present
And I validate the SortByDriver link is present
And I validate the SortByDistanceDriven link is present
And I validate the SortByOdometer link is present
And I validate the SortByOverall link is present
And I validate the SortBySpeed link is present
And I validate the SortByStyle link is present

Scenario: TC1645: Reports - Vehicles - Edit Columns - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the EditColumns link
And the EditColumns popup opens
And I click the Cancel button
And the EditColumns popup closes
Then I validate the SortByGroup link is present
And I validate the SortByVehicleID link is present
And I validate the SortByYearMakeModel link is present
And I validate the SortByDriver link is present
And I validate the SortByDistanceDriven link is present
And I validate the SortByOdometer link is present
And I validate the SortByOverall link is present
And I validate the SortBySpeed link is present
And I validate the SortByStyle link is present

Scenario: TC1646: Reports - Vehicles - Edit Columns - Check Box Selection via Mouse
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the EditColumns link
And the EditColumns popup opens
And I uncheck the checkbox of the first entry
Then I validate the checkbox of the first entry is not checked
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

Scenario: TC1647: Reports - Vehicles - Edit Columns - Check Box Selection via Spacebar CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

Scenario: TC1648: Reports - Vehicles - Edit Columns - Current Session Retention
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the EditColumns link
And the EditColumns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I click the Save button
And the EditColumns popup closes
And I click the Notifications link
And I click the Reports link
And I click the Vehicles link
Then I validate the SortByGroup link is not present
And I validate the SortByVehicleID link is not present
And I validate the SortByYearMakeModel link is not present
And I validate the SortByDriver link is present
And I validate the SortByDistanceDriven link is present
And I validate the SortByOdometer link is present
And I validate the SortByOverall link is present
And I validate the SortBySpeed link is present
And I validate the SortByStyle link is present

Scenario: Reports - Vehicles - Edit Columns - Default Command Button DOES NOT EXIST, NEEDS TO BE ADDED TO RALLY
Given this step is pending

Scenario: TC1650: Reports - Vehicles - Edit Columns - Save Button
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the EditColumns link
And the EditColumns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the EditColumns popup closes
Then I validate the SortByGroup link is not present
And I click the EditColumns link
And the EditColumns popup opens
And I validate the checkbox of the first entry is not checked
And I check the checkbox of the first entry
And I click the Save button
And the EditColumns popup closes
And I validate the SortByGroup link is present

Scenario: TC1651: Reports - Vehicles - Edit Columns - Subsequent Session Retention
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the EditColumns link
And the EditColumns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the EditColumns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
When I click the Reports link
And I click the Vehicles link
Then I validate the SortByGroup link is not present
And I click the EditColumns link
And the EditColumns popup opens
And I check the checkbox of the first entry
And I click the Save button
And the EditColumns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
When I click the Reports link
And I click the Vehicles link
And I validate the SortByGroup link is present

Scenario: Reports - Vehicles - Edit Columns - Tabbing Order DOES NOT EXIST, NEEDS TO BE ADDED TO RALLY
Given this step is pending

Scenario: TC1653: Reports - Vehicles - Edit Columns - UI
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Vehicles link
And I click the EditColumns link
And the EditColumns popup opens
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
