Test Cases for TF388/TF411/TF491 

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF491

Narrative:

Scenario: TC1368: Notifications - Diagnostics - Bookmark Entry
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I type "" into the Search textfield
And I click the Search button
And I bookmark the page
And I click log out
And I click the bookmark I just added
And I am on the login page
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
Then I validate I am on the Diagnostics page
And I validate the Search textfield is ""
And I validate the Driver Report table is not ""

Scenario: TC1371: Notifications - Diagnostics - Driver Link
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I select the first entry in the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the first entry in the Sort By Driver column as SavedEntry
And I click the first entry in the Sort By Driver column
Then I validate the Driver Performance text is SavedEntry

Scenario: TC1381: Notifications - Diagnostics - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I select the first entry in the Team dropdown
And I select "Past 30 Days" in the TimeFrame dropdown
And I click the Refresh button
And I click the Sort By Date Time link
And I click the Sort By Driver link
And I click the Sort By Driver link
And I click the Sort By Group link
And I click the Sort By Group link
And I click the Sort By Vehicle link
And I click the Sort By Vehicle link

Scenario: TC1382: Notifications - Diagnostics - Tools Button
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I click the Tools button
Then I validate the Email Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC1383: Notifications - Diagnostics - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
Then I validate I am on the Diagnostics page
And I validate the Team dropdown is present
And I validate the Time Frame dropdown is present
And I validate the Refresh button is present
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Sort By DateTime link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present
And I validate the Header Status text is present

Scenario: TC1384: Notifications - Diagnostics - Vehicle Link
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I select the first entry in the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the first entry in the Sort By Vehicle column as SavedEntry
And I click the first entry in the Sort By Vehicle column
Then I validate the Vehicle Performance text is SavedEntry

Scenario: TC1386: Notifications - Diagnostics - Edit Columns - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I uncheck the checkbox of the fourth entry
And I uncheck the checkbox of the fifth entry
And I uncheck the checkbox of the sixth entry
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1387: Notifications - Diagnostics - Edit Columns - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1388: Notifications - Diagnostics - Edit Columns - Check Box Selection via Mouse
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
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

Scenario: TC1390: Notifications - Diagnostics - Edit Columns - Current Session Retention
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I click the Save button
And the Edit Columns popup closes
And I click the Reports link
And I click the Notifications link
And I click the Diagnostics link
Then I validate the Sort By Date Time link is not present
And I validate the Sort By Group link is not present
And I validate the Sort By Driver link is not present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1392: Notifications - Diagnostics - Edit Columns - Save Button
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the checkbox of the first entry is not checked
And I check the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC1393: Notifications - Diagnostics - Edit Columns - Subsequent Session Retention
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
And I click the Notifications link
And I click the Diagnostics link
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
And I click the Notifications link
And I click the Diagnostics link
And I validate the Sort By Date Time link is present

Scenario: TC1395: Notifications - Diagnostics - Edit Columns - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
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

Scenario: TC1399: Notifications - Diagnostics - Exclude Link - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I save the text in the first Entry Date Time text as SavedDateTime
And I save the text in the first Entry Detail text as SavedDetail
And I click the first Exclude link in the Status column
And the Exclude Event popup opens
Then I validate the SavedDateTime text is present
And I validate the SavedDetail text is present
And I validate the Yes button is present
And I validate the No button is present

Scenario: TC5742: Notifications - Diagnostics - Time Frame
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Diagnostics link
And I click the first entry in the Team dropdown
And I click "Today" in the Time Frame dropdown
And I click the Refresh button
And I save the text in the first Entry Date Time text as SavedDateTime
And I click "Yesterday" in the Time Frame dropdown
And I click the Refresh button
Then I validate the SavedDateTime text is not present