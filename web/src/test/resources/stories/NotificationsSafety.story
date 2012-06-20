Test Cases for TF388/TF411/TF478

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF478

Narrative:

Scenario: TC1475: Notifications - Safety - Bookmark Entry 
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I validate I am on the Login page
Given I am logged in
Then I validate I am on the Notifications Safety page

Scenario: TC1437: Notifications - Safety - Driver Link
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Driver link as SAVEDENTRY
And I click the 1st Row of the Entry Driver link
Then I validate the Driver Name link is SAVEDENTRY

Scenario: TC1486: Notifications - Safety - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
Then I validate the Sort By Date Time column sorts correctly
And I validate the Sort By Driver column sorts correctly
And I validate the Sort By Driver column sorts correctly
And I validate the Sort By Group column sorts correctly
And I validate the Sort By Group column sorts correctly
And I validate the Sort By Vehicle column sorts correctly
And I validate the Sort By Vehicle column sorts correctly

Scenario: TC1487: Notifications - Safety - Tools Button
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I click the Tools button
Then I validate the Email Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC1488: Notifications - Safety - UI
Given I am logged in
When I click the Notifications link
And I click the Safety link
Then I validate I am on the Notifications Safety page
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
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Category dropdown is present
And I validate the Status Filter dropdown is present

Scenario: TC1489: Notifications - Safety - Vehicle Link
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Vehicle link as SAVEDENTRY
And I click the 1st Row of the Entry Vehicle link
Then I validate the Vehicle Name link is SAVEDENTRY

Scenario: TC1491: Notifications - Safety - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Edit Columns checkbox
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
And I validate the Header Status text is present
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Category dropdown is present
And I validate the Status Filter dropdown is present

Scenario: TC1492: Notifications - Safety - Edit Columns - Cancel Button (No Changes)
Given I am logged in
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
And I validate the Header Status text is present
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Category dropdown is present
And I validate the Status Filter dropdown is present

Scenario: TC1493: Notifications - Safety - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I check the 1st Row of the Edit Columns checkbox
And I validate the 1st Row of the Edit Columns checkbox is checked
And I uncheck the 1st Row of the Edit Columns checkbox
And I validate the 1st Row of the Edit Columns checkbox is not checked
And I check the 2nd Row of the Edit Columns checkbox
And I validate the 2nd Row of the Edit Columns checkbox is checked
And I uncheck the 2nd Row of the Edit Columns checkbox
And I validate the 2nd Row of the Edit Columns checkbox is not checked
And I check the 3rd Row of the Edit Columns checkbox
And I validate the 3rd Row of the Edit Columns checkbox is checked
And I uncheck the 3rd Row of the Edit Columns checkbox
And I validate the 3rd Row of the Edit Columns checkbox is not checked
And I check the 4th Row of the Edit Columns checkbox
And I validate the 4th Row of the Edit Columns checkbox is checked
And I uncheck the 4th Row of the Edit Columns checkbox
And I validate the 4th Row of the Edit Columns checkbox is not checked
And I check the 5th Row of the Edit Columns checkbox
And I validate the 5th Row of the Edit Columns checkbox is checked
And I uncheck the 5th Row of the Edit Columns checkbox
And I validate the 5th Row of the Edit Columns checkbox is not checked
And I check the 6th Row of the Edit Columns checkbox
And I validate the 6th Row of the Edit Columns checkbox is checked
And I uncheck the 6th Row of the Edit Columns checkbox
And I validate the 6th Row of the Edit Columns checkbox is not checked

Scenario: TC1495: Notifications - Safety - Edit Columns - Current Session Retention
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Edit Columns checkbox
And I uncheck the 2nd Row of the Edit Columns checkbox
And I uncheck the 3rd Row of the Edit Columns checkbox
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
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Edit Columns checkbox
And I check the 2nd Row of the Edit Columns checkbox
And I check the 3rd Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1497: Notifications - Safety - Edit Columns - Save Button
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Edit Columns checkbox is not checked
And I check the 1st Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC1498: Notifications - Safety - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Safety link
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Edit Columns checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Safety link
Then I validate the Sort By Date Time link is present

Scenario: TC1500: Notifications - Safety - Edit Columns - UI
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the 1st Row of the Edit Columns checkbox is present
And I validate the 2nd Row of the Edit Columns checkbox is present
And I validate the 3rd Row of the Edit Columns checkbox is present
And I validate the 4th Row of the Edit Columns checkbox is present
And I validate the 5th Row of the Edit Columns checkbox is present
And I validate the 6th Row of the Edit Columns checkbox is present
And I validate the 1st Row of the Edit Columns checkbox is checked
And I validate the 2nd Row of the Edit Columns checkbox is checked
And I validate the 3rd Row of the Edit Columns checkbox is checked
And I validate the 4th Row of the Edit Columns checkbox is checked
And I validate the 5th Row of the Edit Columns checkbox is checked
And I validate the 6th Row of the Edit Columns checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present

Scenario: TC1504: Notifications - Safety - Exclude Link - UI
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDDETAIL
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
Then I validate the Message text contains SAVEDDATETIME
And I validate the Message text contains SAVEDDETAIL
And I validate the Yes button is present
And I validate the No button is present
And I validate the Close button is present

Scenario: TC5738: Notifications - Safety - Include Link
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDDETAIL
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
Then I validate the Message text contains SAVEDDATETIME
And I validate the Message text contains SAVEDDETAIL
And I click the Yes button
And the Exclude Event popup closes
And I validate the 1st Row of the Entry Status link is "include"
And I click the 1st Row of the Entry Status link
And I validate the 1st Row of the Entry Status link is "exclude"

Scenario: TC5743: Notifications - Safety - Time Frame
Given I am logged in
When I click the Notifications link
And I click the Safety link
And I select "Top" from the Team dropdown
And I select "Today" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDDATETIME
And I click the Safety link
And I select "Top" from the Team dropdown
And I select "Yesterday" from the Time Frame dropdown
And I click the Refresh button
Then I validate the 1st Row of the Entry Date Time text is not SAVEDDATETIME