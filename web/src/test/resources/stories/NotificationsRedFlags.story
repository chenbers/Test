Test Cases for TF388/TF411/TF489

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF489

Narrative:

Scenario: TC1434: Notifications - Red Flags - Bookmark Entry 
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I bookmark the page
And I click log out
And I click the bookmark I just added
And I click the Logout link
And I validate I am on the Login page
Given I am logged in
Then I validate I am on the Red Flags page

Scenario: TC1437: Notifications - Red Flags - Driver Link
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Driver link as SavedEntry
And I click the 1st Row of the Entry Driver link
Then I validate the Driver Name link is SavedEntry

Scenario: TC1450: Notifications - Red Flags - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
Then I validate the Sort By Date Time column sorts correctly
Then I validate the Sort By Driver column sorts correctly
Then I validate the Sort By Driver column sorts correctly
Then I validate the Sort By Group column sorts correctly
Then I validate the Sort By Group column sorts correctly
Then I validate the Sort By Vehicle column sorts correctly
Then I validate the Sort By Vehicle column sorts correctly

Scenario: TC1451: Notifications - Red Flags - Tools Button
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I click the Tools button
Then I validate the Email Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC1452: Notifications - Red Flags - UI
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
Then I validate I am on the Notifications Red Flags page
And I validate the Team dropdown is present
And I validate the Time Frame dropdown is present
And I validate the Refresh button is present
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Header Level text is present
And I validate the Header AlertDetails text is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present
And I validate the Header Status text is present

Scenario: TC1453: Notifications - Red Flags - Vehicle Link
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Vehicle link
And I click the Sort By Vehicle link
And I save the 1st Row of the Entry Vehicle link as SavedEntry
And I click the 1st Row of the Entry Vehicle link
Then I validate the Vehicle Name link is SavedEntry

Scenario: TC1455: Notifications - Red Flags - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Notifications link
And I click the Red Flags link
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the 1st Row of the Edit Columns checkbox
And I uncheck the 2nd Row of the Edit Columns checkbox
And I uncheck the 3rd Row of the Edit Columns checkbox
And I uncheck the 4th Row of the Edit Columns checkbox
And I uncheck the 5th Row of the Edit Columns checkbox
And I uncheck the 6th Row of the Edit Columns checkbox
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1456: Notifications - Red Flags - Edit Columns - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Red Flags link
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

Scenario: TC1457: Notifications - Red Flags - Edit Columns - Check Box Selection via Mouse
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Red Flags link
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

Scenario: TC1459: Notifications - Red Flags - Edit Columns - Current Session Retention
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Red Flags link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I click the Save button
And the Edit Columns popup closes
And I click the Reports link
And I click the Notifications link
And I click the Red Flags link
Then I validate the Sort By Date Time link is not present
And I validate the Sort By Group link is not present
And I validate the Sort By Driver link is not present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1461: Notifications - Red Flags - Edit Columns - Save Button
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Red Flags link
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

Scenario: TC1462: Notifications - Red Flags - Edit Columns - Subsequent Session Retention
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Red Flags link
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
And I click the Red Flags link
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
And I click the Red Flags link
And I validate the Sort By Date Time link is present

Scenario: TC1464: Notifications - Red Flags - Edit Columns - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Red Flags link
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

Scenario: TC1465: Notifications - Red Flags - Exclude Link - Cancel Button
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Red Flags link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I save the text in the first Entry Date Time text as SavedDateTime
And I save the text in the first Entry Detail text as SavedDetail
And I click the first Exclude link in the Status column
And the ExcludeEvent popup opens
And I click the Cancel button
And the Exclude Event popup closes
Then I validate the text in the first Entry Date Time text is SavedDateTime
And I validate the text in the first Entry Detail text is SavedDetail

Scenario: TC1469: Notifications - Red Flags - Exclude Link - OK Button
When I click the Notifications link
Given I am logged in as a "Admin" user
And I click the Red Flags link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I save the number of entries in the Red Flags table as TableCount
And I click the first Exclude link in the Status column
And the Exclude Event popup opens
And I click the Yes button
And the Exclude Event popup closes
Then I validate the number of entries in the Reg Flags table is TableCount
And I validate the first link in the Status column is "include"

Scenario: TC1472: Notifications - Red Flags - Exclude Link - UI
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Red Flags link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I save the text in the first Entry Date Time text as SavedDateTime
And I save the text in the first Entry Detail text as SavedDetail
And I click the first Exclude link in the Status column
And the ExcludeEvent popup opens
Then I validate the SavedDateTime text is present
And I validate the SavedDetail text is present
And I validate the Yes button is present
And I validate the No button is present

Scenario: TC5739: Notifications - Red Flags - Include Link
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Red Flags link
And I click the first entry in the Team dropdown
And I click "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I save the text in the first Entry Date Time text as SavedDateTime
And I save the text in the first Entry Detail text as SavedDetail
And I click the first Exclude link in the Status column
And the Exclude Event popup opens
And I validate the text SavedDateTime is present
And I validate the text SavedDetail is present
And I click the Yes button
And the Exclude Event popup closes
Then I validate the first link in the Status column is "include"
And I click the first link in the Status column
And I validate the first link in the Status column is "exclude"

Scenario: TC5744: Notifications - Red Flags - Time Frame
Given I am logged in as a "Admin" user
When I click the Notifications link
And I click the Red Flags link
And I click the first entry in the Team dropdown
And I click "Today" in the Time Frame dropdown
And I click the Refresh button
And I save the text in the first Entry Date Time text as SavedDateTime
And I click "Yesterday" in the TimeFrame dropdown
And I click the Refresh button
Then I validate the SavedDateTime text is not present