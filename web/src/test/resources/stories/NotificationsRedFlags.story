Test Cases for TF388/TF411/TF489

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF489

Narrative:

Scenario: TC1434: Notifications - Red Flags - Bookmark Entry 
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I type "" into the Search textfield
And I click the Search button
And I bookmark the page
And I click log out
And I click the bookmark I just added
And I am on the login page
And I type a valid user name
And I type a valid password
And I click log in
Then I validate I am on the RedFlags page
And I validate the Search textfield is ""
And I validate the Driver Report table is not ""

Scenario: TC1435: Notifications - Red Flags - Bookmark Entry to Different Account CANNOT BE IMPLEMENTED YET

Scenario: TC1437: Notifications - Red Flags - Driver Link
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the SortByDriver link
And I save the first entry in the SortByDriver column as SavedEntry
And I click the first entry in the SortByDriver column
Then I validate the Driver Performance text is SavedEntry

Scenario: TC1439: Notifications - Red Flags - E-mail This Report CANNOT BE IMPLEMENTED YET DUE TO NEEDING EXTERNAL EMAIL PROGRAM

Scenario: TC1440: Notifications - Red Flags - Export To Excel CANNOT BE IMPLEMENTED YET DUE TO EXCEL

Scenario: TC1441: Notifications - Red Flags - Export To PDF CANNOT BE IMPLEMENTED YET DUE TO PDF

Scenario: TC1442: Notifications - Red Flags - Hover Help CANNOT BE IMPLEMENTED YET DUE TO MAP

Scenario: TC1443: Notifications - Red Flags - Location Map Driver Link CANNOT BE IMPLEMENTED YET DUE TO MAP

Scenario: TC1445: Notifications - Red Flags - Location Map Link CANNOT BE IMPLEMENTED YET DUE TO MAP

Scenario: TC1446: Notifications - Red Flags - Page Link CANNOT BE IMPLEMENTED YET DUE TO MAP

Scenario: TC1447: Notifications - Red Flags - Refresh CANNOT BE IMPLEMENTED YET DUE TO EVENT GENERATION

Scenario: TC1448: Notifications - Red Flags - Search NEEDS TO BE REWRITTEN IN RALLY, TEST NO LONGER APPLIES AS IS

Scenario: TC1450: Notifications - Red Flags - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the TimeFrame dropdown
And I click the Refresh button
And I click the SortByDateTime link
And I click the sortByDriver link
And I click the sortByDriver link
And I click the sortByGroup link
And I click the sortByGroup link
And I click the sortByVehicle link
And I click the sortByVehicle link

Scenario: TC1451: Notifications - Red Flags - Tools Button
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I click the Tools button
Then I validate the EmailReport button is present
Then I validate the ExportToPDF button is present
Then I validate the ExportToExcel button is present

Scenario: TC1452: Notifications - Red Flags - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
Then I validate I am on the Red Flags page
And I validate the Team dropdown is present
And I validate the TimeFrame dropdown is present
And I validate the Refresh button is present
And I validate the EditColumns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the HeaderLevel text is present
And I validate the HeaderAlertDetails text is present
And I validate the SortByDateTime link is present
And I validate the SortByGroup link is present
And I validate the SortByDriver link is present
And I validate the SortByVehicle link is present
And I validate the HeaderCategory text is present
And I validate the HeaderDetail text is present
And I validate the HeaderStatus text is present

Scenario: TC1453: Notifications - Red Flags - Vehicle Link
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the TimeFrame dropdown
And I click the Refresh button
And I click the SortByDriver link
And I save the first entry in the SortByVehicle column as SavedEntry
And I click the first entry in the SortByVehicle column
Then I validate the Vehicle Performance text is SavedEntry

Scenario: TC1455: Notifications - Red Flags - Edit Columns - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I click the EditColumns link
And the EditColumns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I uncheck the checkbox of the fourth entry
And I uncheck the checkbox of the fifth entry
And I uncheck the checkbox of the sixth entry
And I click the Cancel button
And the EditColumns popup closes
Then I validate the SortByDateTime link is present
And I validate the SortByGroup link is present
And I validate the SortByDriver link is present
And I validate the SortByVehicle link is present
And I validate the HeaderCategory text is present
And I validate the HeaderDetail text is present

Scenario: TC1456: Notifications - Red Flags - Edit Columns - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I click the EditColumns link
And the EditColumns popup opens
And I click the Cancel button
And the EditColumns popup closes
Then I validate the SortByDateTime link is present
And I validate the SortByGroup link is present
And I validate the SortByDriver link is present
And I validate the SortByVehicle link is present
And I validate the HeaderCategory text is present
And I validate the HeaderDetail text is present

Scenario: TC1457: Notifications - Red Flags - Edit Columns - Check Box Selection via Mouse
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
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

Scenario: TC1458: Notifications - Red Flags - Edit Columns - Check Box Selection via Spacebar CANNOT IMPLEMENT YET DUE TO KEYPRESS

Scenario: TC1459: Notifications - Red Flags - Edit Columns - Current Session Retention
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I click the EditColumns link
And the EditColumns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I click the Save button
And the EditColumns popup closes
And I click the Reports link
And I click the Notifications link
And I click the RedFlags link
Then I validate the SortByDateTime link is not present
And I validate the SortByGroup link is not present
And I validate the SortByDriver link is not present
And I validate the SortByVehicle link is present
And I validate the HeaderCategory text is present
And I validate the HeaderDetail text is present

Scenario: TC1460: Notifications - Red Flags - Edit Columns - Default Command Button CANNOT IMPLEMENT YET DUE TO KEYPRESS

Scenario: TC1461: Notifications - Red Flags - Edit Columns - Save Button
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I click the EditColumns link
And the EditColumns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the EditColumns popup closes
Then I validate the SortByDateTime link is not present
And I click the EditColumns link
And the EditColumns popup opens
And I validate the checkbox of the first entry is not checked
And I check the checkbox of the first entry
And I click the Save button
And the EditColumns popup closes
And I validate the SortByDateTime link is present

Scenario: TC1462: Notifications - Red Flags - Edit Columns - Subsequent Session Retention
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I click the EditColumns link
And the EditColumns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the EditColumns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
When I click the Notifications link
And I click the RedFlags link
Then I validate the SortByDateTime link is not present
And I click the EditColumns link
And the EditColumns popup opens
And I check the checkbox of the first entry
And I click the Save button
And the EditColumns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
When I click the Notifications link
And I click the RedFlags link
And I validate the SortByDateTime link is present

Scenario: TC1463: Notifications - Red Flags - Edit Columns - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYPRESS

Scenario: TC1464: Notifications - Red Flags - Edit Columns - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the RedFlags link
And I click the EditColumns link
And the EditColumns popup opens
Then I validate the first checkbox is present
And I validate the second checkbox is present
And I validate the third checkbox is present
And I validate the fourth checkbox is present
And I validate the fifth checkbox is present
And I validate the sixth checkbox is present
And I validate the first checkbox is checked
And I validate the second checkbox is checked
And I validate the third checkbox is checked
And I validate the fourth checkbox is checked
And I validate the fifth checkbox is checked
And I validate the sixth checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present

Scenario: TC1465: Notifications - Red Flags - Exclude Link - Cancel Button
When I click the Notifications link
And I click the RedFlags link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the TimeFrame dropdown
And I click the Refresh button
And I save the text in the first EntryDateTime text as SavedDateTime
And I save the text in the first EntryDetail text as SavedDetail
And I click the first Exclude link in the Status column
And the ExcludeEvent popup opens
And I click the Cancel button
And the ExcludeEvent popup closes
Then I validate the text in the first EntryDateTime text is SavedDateTime
And I validate the text in the first EntryDetail text is SavedDetail

Scenario: TC1466: Notifications - Red Flags - Exclude Link - Crash Event Interaction HAS NOT BEEN IMPLEMENTED YET

Scenario: TC1467: Notifications - Red Flags - Exclude Link  - Default Command Button CANNOT IMPLEMENT YET DUE TO KEYPRESS

Scenario: TC1468: Notifications - Red Flags - Exclude Link - Driving Style Event Interaction NEED HELP TO IMPLEMENT






































