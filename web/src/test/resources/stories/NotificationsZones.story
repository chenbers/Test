Test Cases for TF388/TF411/TF744 

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF744

Narrative:

Scenario: TC5710: Notifications - Zones - Bookmark Entry 
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
And I type "" into the Search textfield
And I click the Search button
And I bookmark the page
And I click log out
And I click the bookmark I just added
And I am on the login page
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
Then I validate I am on the Zones page
And I validate the Search textfield is ""
And I validate the Driver Report table is not ""

Scenario: TC5711: Notifications - Zones - Bookmark Entry to Different Account CANNOT BE IMPLEMENTED YET

Scenario: TC5712: Notifications - Zones - Driver Link
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the SortByDriver link
And I save the first entry in the SortByDriver column as SavedEntry
And I click the first entry in the SortByDriver column
Then I validate the Driver Performance text is SavedEntry

Scenario: TC5713: Notifications - Zones - E-mail This Report CANNOT BE IMPLEMENTED YET DUE TO NEEDING EXTERNAL EMAIL PROGRAM

Scenario: TC5714: Notifications - Zones - Export To Excel CANNOT BE IMPLEMENTED YET DUE TO EXCEL

Scenario: TC5715: Notifications - Zones - Export To PDF CANNOT BE IMPLEMENTED YET DUE TO PDF

Scenario: TC5716: Notifications - Zones - Hover Help CANNOT BE IMPLEMENTED YET DUE TO MAP

Scenario: TC5717: Notifications - Zones - Location Map Link CANNOT BE IMPLEMENTED YET DUE TO MAP

Scenario: TC5709: Notifications - Zones - Refresh CANNOT BE IMPLEMENTED YET DUE TO EVENT GENERATION

Scenario: TC5718: Notifications - Zones - Search NEEDS TO BE REWRITTEN IN RALLY, TEST NO LONGER APPLIES AS IS

Scenario: TC5719: Notifications - Zones - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
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

Scenario: TC5720: Notifications - Zones - Tools Button
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
And I click the Tools button
Then I validate the EmailReport button is present
Then I validate the ExportToPDF button is present
Then I validate the ExportToExcel button is present

Scenario: TC5721: Notifications - Zones - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
Then I validate I am on the Zones page
And I validate the Team dropdown is present
And I validate the TimeFrame dropdown is present
And I validate the Refresh button is present
And I validate the EditColumns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the SortByDateTime link is present
And I validate the SortByGroup link is present
And I validate the SortByDriver link is present
And I validate the SortByVehicle link is present
And I validate the HeaderCategory text is present
And I validate the HeaderDetail text is present
And I validate the HeaderStatus text is present

Scenario: TC5722: Notifications - Zones - Vehicle Link
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the TimeFrame dropdown
And I click the Refresh button
And I click the SortByDriver link
And I save the first entry in the SortByVehicle column as SavedEntry
And I click the first entry in the SortByVehicle column
Then I validate the Vehicle Performance text is SavedEntry

Scenario: TC5723: Notifications - Zones - Edit Columns - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
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

Scenario: TC5724: Notifications - Zones - Edit Columns - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
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

Scenario: TC5725: Notifications - Zones - Edit Columns - Check Box Selection via Mouse
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
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

Scenario: TC5726: Notifications - Zones - Edit Columns - Check Box Selection via Spacebar CANNOT IMPLEMENT YET DUE TO KEYPRESS

Scenario: TC5727: Notifications - Zones - Edit Columns - Current Session Retention
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
And I click the EditColumns link
And the EditColumns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I click the Save button
And the EditColumns popup closes
And I click the Reports link
And I click the Notifications link
And I click the Zones link
Then I validate the SortByDateTime link is not present
And I validate the SortByGroup link is not present
And I validate the SortByDriver link is not present
And I validate the SortByVehicle link is present
And I validate the HeaderCategory text is present
And I validate the HeaderDetail text is present

Scenario: TC5728: Notifications - Zones - Edit Columns - Default Command Button CANNOT IMPLEMENT YET DUE TO KEYPRESS

Scenario: TC5729: Notifications - Zones - Edit Columns - Save Button
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
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

Scenario: TC5730: Notifications - Zones - Edit Columns - Subsequent Session Retention
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
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
And I click the Zones link
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
And I click the Zones link
And I validate the SortByDateTime link is present

Scenario: TC5731: Notifications - Zones - Edit Columns - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYPRESS

Scenario: TC5732: Notifications - Zones - Edit Columns - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
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

Scenario: TC5733: Notifications - Zones - Exclude Link - Zone Arrival Event NEED HELP TO IMPLEMENT
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the TimeFrame dropdown
And I click the Refresh button
And I save the text in the first EntryDateTime text as SavedDateTime
And I save the text in the first EntryDetail text as SavedDetail
And I click the first Exclude link in the Status column
And the ExcludeEvent popup opens
Then I validate the SavedDateTime text is present
And I validate the SavedDetail text is present
And I click the Yes button
And I validate the first link in the Status column is "include"

Scenario: TC5734: Notifications - Zones - Exclude Link - Zone Departure Event NEED HELP TO IMPLEMENT
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the TimeFrame dropdown
And I click the Refresh button
And I save the text in the first EntryDateTime text as SavedDateTime
And I save the text in the first EntryDetail text as SavedDetail
And I click the first Exclude link in the Status column
And the ExcludeEvent popup opens
Then I validate the SavedDateTime text is present
And I validate the SavedDetail text is present
And I click the Yes button
And I validate the first link in the Status column is "include"

Scenario: TC5735: Notifications - Zones - Exclude Link - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the TimeFrame dropdown
And I click the Refresh button
And I save the text in the first EntryDateTime text as SavedDateTime
And I save the text in the first EntryDetail text as SavedDetail
And I click the first Exclude link in the Status column
And the ExcludeEvent popup opens
Then I validate the SavedDateTime text is present
And I validate the SavedDetail text is present
And I validate the Yes button is present
And I validate the No button is present

Scenario: TC5736: Notifications - Zones - Include Link 
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the TimeFrame dropdown
And I click the Refresh button
And I click the first Exclude link in the Status column
And the ExcludeEvent popup opens
And I click the Yes button
Then I validate the first link in the Status column is "include"
And I click the first link in the Status column
And I validate the first link in the Status column is "exclude"

Scenario: TC5741: Notifications - Zones - Time Frame
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Zones link
And I click the first entry in the Team dropdown
And I click "Today" in the TimeFrame dropdown
And I click the Refresh button
And I save the text in the first EntryDateTime text as SavedDateTime
And I click "Yesterday" in the TimeFrame dropdown
And I click the Refresh button
Then I validate the SavedDateTime text is not present
