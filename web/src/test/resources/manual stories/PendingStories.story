This file is used to store the story files that cannot yet be implemented.

LiveFleet.story:

Scenario: TC1230: Live Fleet - Dispatch Table Properties NEED HELP IMPLEMENTING, NOT SURE HOW TO DO THIS
Given this step is pending

Scenario: TC1231: Live Fleet - Display NEED HELP IMPLEMENTING, NOT SURE HOW TO DO THIS
Given this step is pending

Scenario: TC1233: Live Fleet - Find Address NEED HELP IMPLEMENTING, NOT SURE HOW TO DO THIS
Given this step is pending

Scenario: TC1234: Live Fleet - Hover Help REQUIRES MAP INTERACTION
Given this step is pending

Scenario: TC1235: Live Fleet - Map Navigation REQUIRES MAP INTERACTION
Given this step is pending

Scenario: TC5740: Live Fleet - Refresh NEED HELP IMPLEMENTING, NOT SURE HOW TO DO THIS
Given this step is pending

login.story:

Scenario: TC1240: Log In - Access Blocked Error CAN'T IMPLEMENT YET
Given this step is pending

Scenario: TC1249: Log In - Tabbing Order CAN'T IMPLEMENT YET
Given this step is pending

MyAccountChangePassword.story:

Scenario: TC1297: My Account - Change Password - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYBOARD PRESS
Given this step is pending

Scenario: TC1299: My Account - Change Password - Validation THIS TEST IS VERY SIMILAR TO TC1288, DO WE NEED BOTH?
Given this step is pending

LogInForgotPassword.story:

//This test is no longer valid because there is no longer a cancel button on the forgot password page
Scenario: TC1184: Log In - Forgot User Name or Password - Cancel Button
Given I am on the Login page
When I click the Forgot Username Or Password link
And I type "Invalid Email Address" into the Email textfield
And I click the Cancel button
And the Forgot Password popup closes
And I validate I am on the Login page
And I click the Forgot Username Or Password link
And the Forgot Password popup opens
Then I validate the Email textfield is ""

NotificationsDiagnostics.story:

Scenario: TC1369: Notifications - Diagnostics - Bookmark Entry to Different Account CANNOT BE IMPLEMENTED YET
Given this step is pending

Scenario: TC1373: Notifications - Diagnostics - E-mail This Report CANNOT BE IMPLEMENTED YET DUE TO NEEDING EXTERNAL EMAIL PROGRAM
Given this step is pending

Scenario: TC1374: Notifications - Diagnostics - Export To Excel CANNOT BE IMPLEMENTED YET DUE TO EXCEL
Given this step is pending

Scenario: TC1375: Notifications - Diagnostics - Export To PDF CANNOT BE IMPLEMENTED YET DUE TO PDF
Given this step is pending

Scenario: TC1376: Notifications - Diagnostics - Hover Help CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1377: Notifications - Diagnostics - Location Map Link CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1378: Notifications - Diagnostics - Refresh CANNOT BE IMPLEMENTED YET DUE TO EVENT GENERATION
Given this step is pending

Scenario: TC1379: Notifications - Diagnostics - Search NEEDS TO BE REWRITTEN IN RALLY, TEST NO LONGER APPLIES AS IS
Given this step is pending

Scenario: TC1389: Notifications - Diagnostics - Edit Columns - Check Box Selection via Spacebar
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I check the 4th Row of the Column checkbox
And I check the 5th Row of the Column checkbox
And I check the 6th Row of the Column checkbox
And I press the Tab Key
And I press the Tab Key
And I press the Tab Key
And I press the Tab Key
And I press the Spacebar Key
Then I validate the 1st Row of the Column checkbox is not checked
And I press the Spacebar Key
Then I validate the 1st Row of the Column checkbox is checked
And I press the Tab Key
And I press the Spacebar Key
And I validate the 2nd Row of the Column checkbox is not checked
And I press the Spacebar Key
And I validate the 2nd Row of the Column checkbox is checked

Scenario: TC1394: Notifications - Diagnostics - Edit Columns - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

Scenario: TC1397: Notifications - Diagnostics - Exclude Link - Idling Event NEED HELP TO IMPLEMENT
Given this step is pending

Scenario: TC1398: Notifications - Diagnostics - Exclude Link -Tampering Event NEED HELP TO IMPLEMENT
Given this step is pending

Scenario: TC5737: Notifications - Diagnostics - Include Link NEED HELP WITH THIS ONE, STEPS IN RALLY DON'T MATCH PORTAL
Given this step is pending

NotificationsRedFlags.story:

Scenario: TC1435: Notifications - Red Flags - Bookmark Entry to Different Account CANNOT BE IMPLEMENTED YET
Given this step is pending

Scenario: TC1439: Notifications - Red Flags - E-mail This Report CANNOT BE IMPLEMENTED YET DUE TO NEEDING EXTERNAL EMAIL PROGRAM
Given this step is pending

Scenario: TC1440: Notifications - Red Flags - Export To Excel CANNOT BE IMPLEMENTED YET DUE TO EXCEL
Given this step is pending

Scenario: TC1441: Notifications - Red Flags - Export To PDF CANNOT BE IMPLEMENTED YET DUE TO PDF
Given this step is pending

Scenario: TC1442: Notifications - Red Flags - Hover Help CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1443: Notifications - Red Flags - Location Map Driver Link CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1445: Notifications - Red Flags - Location Map Link CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1446: Notifications - Red Flags - Page Link CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1447: Notifications - Red Flags - Refresh CANNOT BE IMPLEMENTED YET DUE TO EVENT GENERATION
Given this step is pending

Scenario: TC1448: Notifications - Red Flags - Search NEEDS TO BE REWRITTEN IN RALLY, TEST NO LONGER APPLIES AS IS
Given this step is pending

Scenario: TC1458: Notifications - Red Flags - Edit Columns - Check Box Selection via Spacebar CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

Scenario: TC1463: Notifications - Red Flags - Edit Columns - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

Scenario: TC1466: Notifications - Red Flags - Exclude Link - Crash Event Interaction HAS NOT BEEN IMPLEMENTED YET
Given this step is pending

Scenario: TC1468: Notifications - Red Flags - Exclude Link - Driving Style Event Interaction NEED HELP TO IMPLEMENT
Given this step is pending

Scenario: TC1470: Notifications - Red Flags - Exclude Link - Seat Belt Event Interaction NEED HELP TO IMPLEMENT
Given this step is pending

Scenario: TC1471: Notifications - Red Flags - Exclude Link - Speeding Event Interaction NEED HELP TO IMPLEMENT
Given this step is pending

NotificationsSafety.story:

Scenario: TC1476: Notifications - Safety - Bookmark Entry to Different Account CANNOT BE IMPLEMENTED YET
Given this step is pending

Scenario: TC1478: Notifications - Safety - E-mail This Report CANNOT BE IMPLEMENTED YET DUE TO NEEDING EXTERNAL EMAIL PROGRAM
Given this step is pending

Scenario: TC1479: Notifications - Safety - Export To Excel CANNOT BE IMPLEMENTED YET DUE TO EXCEL
Given this step is pending

Scenario: TC1480: Notifications - Safety - Export To PDF CANNOT BE IMPLEMENTED YET DUE TO PDF
Given this step is pending

Scenario: TC1481: Notifications - Safety - Hover Help CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1482: Notifications - Safety - Location Map Link CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1483: Notifications - Safety - Refresh CANNOT BE IMPLEMENTED YET DUE TO EVENT GENERATION
Given this step is pending

Scenario: TC1484: Notifications - Safety - Search NEEDS TO BE REWRITTEN IN RALLY, TEST NO LONGER APPLIES AS IS
Given this step is pending

Scenario: TC1494: Notifications - Safety - Edit Columns - Check Box Selection via Spacebar CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

Scenario: TC1499: Notifications - Safety - Edit Columns - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYPRESS

Scenario: TC1501: Notifications - Safety - Exclude Link - Driving Style Event Interaction NEED HELP TO IMPLEMENT

Scenario: TC1502: Notifications - Safety - Exclude Link - Seat Belt Event Interaction NEED HELP TO IMPLEMENT

Scenario: TC1503: Notifications - Safety - Exclude Link - Speeding Event Interaction NEED HELP TO IMPLEMENT

NotificationsZones.story:

Scenario: TC5711: Notifications - Zones - Bookmark Entry to Different Account CANNOT BE IMPLEMENTED YET
Given this step is pending

Scenario: TC5713: Notifications - Zones - E-mail This Report CANNOT BE IMPLEMENTED YET DUE TO NEEDING EXTERNAL EMAIL PROGRAM
Given this step is pending

Scenario: TC5714: Notifications - Zones - Export To Excel CANNOT BE IMPLEMENTED YET DUE TO EXCEL
Given this step is pending

Scenario: TC5715: Notifications - Zones - Export To PDF CANNOT BE IMPLEMENTED YET DUE TO PDF
Given this step is pending

Scenario: TC5716: Notifications - Zones - Hover Help CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC5717: Notifications - Zones - Location Map Link CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC5709: Notifications - Zones - Refresh CANNOT BE IMPLEMENTED YET DUE TO EVENT GENERATION
Given this step is pending

Scenario: TC5718: Notifications - Zones - Search NEEDS TO BE REWRITTEN IN RALLY, TEST NO LONGER APPLIES AS IS
Given this step is pending

Scenario: TC5726: Notifications - Zones - Edit Columns - Check Box Selection via Spacebar CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

Scenario: TC5731: Notifications - Zones - Edit Columns - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

ReportsDevices.story:

Scenario: TC1514: Reports - Devices - Bookmark Entry to Different Account CANNOT BE IMPLEMENTED YET
Given this step is pending

Scenario: TC1516: Reports - Devices - E-mail This Report CANNOT BE IMPLEMENTED YET DUE TO NEEDING EXTERNAL EMAIL PROGRAM
Given this step is pending

Scenario: TC1517: Reports - Devices - Export To Excel CANNOT BE IMPLEMENTED YET DUE TO EXCEL
Given this step is pending

Scenario: TC1518: Reports - Devices - Export To PDF CANNOT BE IMPLEMENTED YET DUE TO PDF
Given this step is pending

Scenario: TC1519: Reports - Devices - Hover Help CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1520: Reports - Devices - Page Link CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1533: Reports - Devices - Edit Columns - Check Box Selection via Spacebar CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

Scenario: TC5731: Reports - Devices - Edit Columns - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

ReportsDriver.story:

Scenario: TC1541: Reports - Drivers - Bookmark Entry to Different Account CANNOT BE IMPLEMENTED YET
Given this step is pending

Scenario: TC1547: Reports - Drivers - E-mail This Report CANNOT BE IMPLEMENTED YET DUE TO NEEDING EXTERNAL EMAIL PROGRAM
Given this step is pending

Scenario: TC1548: Reports - Drivers - Export To Excel CANNOT BE IMPLEMENTED YET DUE TO EXCEL
Given this step is pending

Scenario: TC1549: Reports - Drivers - Export To PDF CANNOT BE IMPLEMENTED YET DUE TO PDF
Given this step is pending

Scenario: TC1552: Reports - Drivers - Hover Help CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1555: Reports - Drivers - Page Link CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1560: Reports - Drivers - Search NEEDS TO BE REWRITTEN IN RALLY, TEST NO LONGER APPLIES AS IS
Given this step is pending

Scenario: TC1574: Reports - Drivers - Edit Columns - Check Box Selection via Spacebar CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

Scenario: TC1579: Reports - Drivers - Edit Columns - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

ReportsVehicles.story:

Scenario: TC1615: Reports - Vehicles - Bookmark Entry to Different Account CANNOT BE IMPLEMENTED YET
Given this step is pending

Scenario: TC1621: Reports - Vehicles - E-mail This Report CANNOT BE IMPLEMENTED YET DUE TO NEEDING EXTERNAL EMAIL PROGRAM
Given this step is pending

Scenario: TC1622: Reports - Vehicles - Export To Excel CANNOT BE IMPLEMENTED YET DUE TO EXCEL
Given this step is pending

Scenario: TC1623: Reports - Vehicles - Export To PDF CANNOT BE IMPLEMENTED YET DUE TO PDF
Given this step is pending

Scenario: TC1626: Reports - Vehicles - Hover Help CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1629: Reports - Vehicles - Page Link CANNOT BE IMPLEMENTED YET DUE TO MAP
Given this step is pending

Scenario: TC1634: Reports - Vehicles - Search NEEDS TO BE REWRITTEN IN RALLY, TEST NO LONGER APPLIES AS IS
Given this step is pending

Scenario: TC1647: Reports - Vehicles - Edit Columns - Check Box Selection via Spacebar CANNOT IMPLEMENT YET DUE TO KEYPRESS
Given this step is pending

Scenario: Reports - Vehicles - Edit Columns - Tabbing Order DOES NOT EXIST, NEEDS TO BE ADDED TO RALLY
Given this step is pending

NotificationsCrashHistory.story

Scenario: TC1342: Notifications - Crash History - E-mail This Report
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Tools button
And I click the Email This Report button
//need to be able to test external email program

Scenario: TC1343: Notifications - Crash History - Export To Excel
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Tools button
And I click the Export To Excel button
//need to be able to test external excel program

Scenario: TC1344: Notifications - Crash History - Export To PDF
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Tools button
And I click the Export To PDF button
//need to be able to test external excel program

Scenario: TC1345: Notifications - Crash History - Hover Help
//need a way to test hover

Scenario: TC1346: Notifications - Crash History - Location Map Link
//need A map of the surrounding area and a marker indicating where the 
//event occurred appears in the pop-up window.

Scenario: TC1347: Notifications - Crash History - Refresh
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Forward One pagescroller
And I select "Top - Test Group WR" from the Team dropdown
And I select "All" from the Time Frame dropdown
And I type "TIWI00" into the Search textfield
And I click the Search button
And I click the Forward One pagescroller
And I click the Sort Status link
And I save the Records text as RECORDS
And I click the Add Crash Report link
And I save the Date Time dropdown as DATETIME
And I select the text containing "TIWI02" from the Vehicle dropdown
And I select "Alma Mater" from the Driver dropdown
And I click the Find Address button
And I type "4225 Lake Park Blvd" into the Find Address textfield
And I click the Locate button
And I click the Save Top button
And I click the Back link
And I select "Top - Test Group WR" from the Team dropdown
And I type "TIWI00" into the Search textfield
And I select "All" from the Time Frame dropdown
And I click the Search button
Then I validate the Records text is not RECORDS
And I validate the "1" text of the pagescroller is not clickable
And I validate the 1st Row of the Date Time text is DATETIME
And I validate the 1st Row of the Driver link is "Alma Mater"
And I validate the 1st Row of the Vehicle link is "TIWI02"
And I validate the Date Time column sorts correctly //need to get sorting to work

Scenario: TC1348: Notifications - Crash History - Search
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I select "Top - Test Group WR" from the Team dropdown
And I select "All" from the Time Frame dropdown
And I type "New" into the Search textfield
And I click the Search button
Then I validate the 1st Row of the Status text contains "New"
And I type "Rollover" into the Search textfield
And I click the Search button
And I validate the 1st Row of the Status text contains "Rollover"
And I type "Aggressive Driving" into the Search textfield
And I click the Search button
And I validate the 1st Row of the Status text contains "Aggressive Driving"
And I type "Panic" into the Search textfield
And I click the Search button
And I validate the 1st Row of the Status text contains "Panic"
And I validate the Date Time column sorts correctly //need to get sorting to work

Scenario: TC1357: Notifications > Crash History - Edit Columns - Check Box Selection via Spacebar

Scenario: TC1362: Reports - Crash History - Edit Columns - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYPRESS

NotificationsEmergency.story

Scenario: TC1407: Notifications > Emergency - E-mail This Report

Scenario: TC1408: Notifications > Emergency - Export To Excel

Scenario: TC1409: Notifications > Emergency - Export To PDF

Scenario: TC1410: Notifications > Emergency - Hover Help

Scenario: TC1411: Notifications > Emergency - Location Map Link

Scenario: TC1412: Notifications > Emergency - Refresh

Scenario: TC1413: Notifications > Emergency - Search

Scenario: TC1423: Notifications > Emergency - Edit Columns - Check Box Selection via Spacebar

FormsManage.story

Scenario: TCXXXX: Forms Admin Page - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER IN A NEW STEP
Given I am logged in
When I go to the forms admin page
And I validate the Sort By Name Working column sorts correctly
And I validate the Sort By Base Form ID Working column sorts correctly
And I validate the Sort By Version Working column sorts correctly
And I validate the Sort By Description Working column sorts correctly
And I validate the Sort By Status Working column sorts correctly
And I validate the Sort By Trigger Working column sorts correctly
And I validate the Sort By Publish Working column sorts correctly
And I validate the Sort By Edit Working column sorts correctly
And I validate the Sort By Name Published column sorts correctly
And I validate the Sort By Base Form ID Published column sorts correctly
And I validate the Sort By Version column Published sorts correctly
And I validate the Sort By Description Published column sorts correctly
And I validate the Sort By Trigger Published column sorts correctly

//Future test, not currently implemented on this page
Scenario: TCXXXX: Edit columns
Given I am on the Admin page Forms tab
When I click on the Edit Columns link
And a popup opens to display the selections of columns
And I select the checkboxes for the columns I want to display
And click on Save button
Then the columns I selected display on the Forms page

//This delete button does not currently exist
Scenario: TCXXXX: Delete a selected Form
Given I am logged in
When I click the Forms link
And I save the 1st Row of the Name link as SAVEDFORM
And I click the 1st Row of the Select checkbox
And click the Delete button
Then I validate the 1st Row of the Name link is not SAVEDFORM

//Add test for previous and next and page number functionality in table

//Add test for checking filtering text when using the search field

FormsPublished.story

FormsAdd.story

//FUTURE TESTS FOR GROUP FUNCTION

Scenario: TCXXXX Forms Add Page - Duplicate Data Name Error Group Field
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Group link
And I type "test" into the Data Name field
And I click the Group link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 1st Row of the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TCXXXX Forms Add Page - UI Test - Group link
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Group link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Label textfield is present
And I validate the Looped checkbox is present
And I validate the Display On One Screen checkbox is present
And I validate the Advanced Arrow button is present

Scenario: TCXXXX Forms Add Page - Blank Data Name Error Group Field
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Group link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TCXXXX Forms Add Page - Space Data Name Error Group Field
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Group link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Symbols Data Name Error Group Field
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Group link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Add a Group Field - Looped unchecked, Display On One Screen unchecked
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Group link
And I type "anXMLtag" into the Data Name textfield
And I type "Choose One" into the Label textfield
And I click the Save button
And I validate the new form displays in the list on the Admin Form page

Scenario: TCXXXX Forms Add Page - Add a Group Field - Looped checked
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Group link
And I type "anXMLtag" into the Data Name textfield
And I type "Choose One" into the Label textfield
And I check the Looped checkbox
And I click the Save button
Then I validate the new form displays in the list on the Admin Form page
And I validate the information I entered is on the form

Scenario: TCXXXX Forms Add Page - Add a Group Field - Display On One Screen checked
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Group link
And I type "anXMLtag" into the Data Name textfield
And I type "Choose One" into the Label textfield
And I check the Display On One Screen checkbox
And I click the Save button
Then I validate the new form displays in the list on the Admin Form page
And I validate the information I entered is on the form

Scenario: TCXXXX Forms Add Page - Add a Group Field - Looped checked, Display On One Screen checked
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Group link
And I type "anXMLtag" into the Data Name textfield
And I type "Choose One" into the Label textfield
And I check the Looped checkbox
And I check the Display On One Screen checkbox
And I click the Save button
Then I validate the new form displays in the list on the Admin Form page
And I validate the information I entered is on the form

FormsSubmissions.story

Scenario: TCXXXX: Forms Submissions Page - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER IN A NEW STEP
Given I am logged in
When I go to the forms submissions page
And I click the Refresh button
Then I validate the Sort By Date Time column sorts correctly
And I click the Sort By Group link
And I validate the Sort By Group column sorts correctly
And I click the Sort By Driver link
And I validate the Sort By Driver column sorts correctly
And I click the Sort By Vehicle link
And I validate the Sort By Vehicle column sorts correctly
And I click the Sort By Form link
And I validate the Sort By Form column sorts correctly
And I click the Sort By Edited link
And I validate the Sort By Edited column sorts correctly
And I click the Sort By Approved link
And I validate the Sort By Approved column sorts correctly
