Test Cases for TF388/TF411/TF478

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF478

Narrative:

Scenario: TC1475: Notifications - Safety - Bookmark Entry 
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
And I type "" into the Search textfield
And I click the Search button
And I bookmark the page
And I click log out
And I click the bookmark I just added
And I am on the login page
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
Then I validate I am on the Safety page
And I validate the Search textfield is ""
And I validate the Driver Report table is not ""

Scenario: TC1437: Notifications - Safety - Driver Link
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
And I select the first entry in the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the first entry in the Sort By Driver column as SavedEntry
And I click the first entry in the Sort By Driver column
Then I validate the Driver Performance text is SavedEntry

Scenario: TC1486: Notifications - Safety - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
And I select the first entry in the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Date Time link
And I click the Sort By Driver link
And I click the Sort By Driver link
And I click the Sort By Group link
And I click the Sort By Group link
And I click the Sort By Vehicle link
And I click the Sort By Vehicle link

Scenario: TC1487: Notifications - Safety - Tools Button
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
And I click the Tools button
Then I validate the Email Report button is present
Then I validate the Export To PDF button is present
Then I validate the Export To Excel button is present

Scenario: TC1488: Notifications - Safety - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
Then I validate I am on the Safety page
And I validate the Team dropdown is present
And I validate the Time Frame dropdown is present
And I validate the Refresh button is present
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present
And I validate the Header Status text is present

Scenario: TC1489: Notifications - Safety - Vehicle Link
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
And I select the first entry in the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the first entry in the Sort By Vehicle column as SavedEntry
And I click the first entry in the Sort By Vehicle column
Then I validate the Vehicle Performance text is SavedEntry

Scenario: TC1491: Notifications - Safety - Edit Columns - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
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

Scenario: TC1492: Notifications - Safety - Edit Columns - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
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

Scenario: TC1493: Notifications - Safety - Edit Columns - Check Box Selection via Mouse
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
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

Scenario: TC1495: Notifications - Safety - Edit Columns - Current Session Retention
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I click the Save button
And the Edit Columns popup closes
And I click the Reports link
And I click the Notifications link
And I click the Safety link
Then I validate the Sort By Date Time link is not present
And I validate the Sort By Group link is not present
And I validate the Sort By Driver link is not present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1497: Notifications - Safety - Edit Columns - Save Button
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
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
And the EditColumns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC1498: Notifications - Safety - Edit Columns - Subsequent Session Retention
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
When I click the Notifications link
And I click the Safety link
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
When I click the Notifications link
And I click the Safety link
And I validate the Sort By Date Time link is present

Scenario: TC1500: Notifications - Safety - Edit Columns - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
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

Scenario: TC1504: Notifications - Safety - Exclude Link - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
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

Scenario: TC5738: Notifications - Safety - Include Link
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the first Exclude link in the Status column
And the Exclude Event popup opens
And I click the Yes button
Then I validate the first link in the Status column is "include"
And I click the first link in the Status column
And I validate the first link in the Status column is "exclude"

Scenario: TC5743: Notifications - Safety - Time Frame
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Safety link
And I click the first entry in the Team dropdown
And I click "Today" in the TimeFrame dropdown
And I click the Refresh button
And I save the text in the first Entry Date Time text as SavedDateTime
And I click "Yesterday" in the Time Frame dropdown
And I click the Refresh button
Then I validate the SavedDateTime text is not present
