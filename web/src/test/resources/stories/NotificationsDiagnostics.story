Test Cases for TF388/TF411/TF491 

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF491

Narrative:

Scenario: TC1368: Notifications - Diagnostics - Bookmark Entry
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I validate I am on the Login page
Given I am logged in
Then I validate I am on the Notifications Diagnostics page

Scenario: TC1371: Notifications - Diagnostics - Driver Link
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Driver link as SavedEntry
And I click the 1st Row of the Entry Driver link
Then I validate the Driver Name link is SavedEntry

Scenario: TC1381: Notifications - Diagnostics - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER IN A NEW STEP
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
Then I validate the Sort By Date Time column sorts correctly
And I validate the Sort By Driver column sorts correctly
And I validate the Sort By Driver column sorts correctly
And I validate the Sort By Group column sorts correctly
And I validate the Sort By Group column sorts correctly
And I validate the Sort By Vehicle column sorts correctly
And I validate the Sort By Vehicle column sorts correctly

Scenario: TC1382: Notifications - Diagnostics - Tools Button
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I click the Tools button
Then I validate the Email Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC1383: Notifications - Diagnostics - UI
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
Then I validate I am on the Notifications Diagnostics page
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
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" in the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Vehicle link as SavedEntry
And I click the 1st Row of the Entry Vehicle link
Then I validate the Vehicle Name link is SavedEntry

Scenario: TC1386: Notifications - Diagnostics - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck all of the Notifications Diagnostics Edit Columns checkboxes
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1387: Notifications - Diagnostics - Edit Columns - Cancel Button (No Changes)
Given I am logged in
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
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
Then TC1388

Scenario: TC1390: Notifications - Diagnostics - Edit Columns - Current Session Retention
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
And TC1390
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
And I click the Edit Columns link
And the Edit Columns popup opens
And TC1390
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1392: Notifications - Diagnostics - Edit Columns - Save Button
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
And TC1392
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And TC1392
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC1393: Notifications - Diagnostics - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
And TC1393
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And TC1393
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I validate the Sort By Date Time link is present

Scenario: TC1395: Notifications - Diagnostics - Edit Columns - UI
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I click the Edit Columns link
And the Edit Columns popup opens
Then TC1395
And I validate the Save button is present
And I validate the Cancel button is present

Scenario: TC1399: Notifications - Diagnostics - Exclude Link - UI
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SavedDateTime
And I save the 1st Row of the Entry Detail text as SavedDetail
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
Then I validate the Saved Date Time text is present
And I validate the Saved Detail text is present
And I validate the Yes button is present
And I validate the No button is present

Scenario: TC5742: Notifications - Diagnostics - Time Frame
Given I am logged in
When I click the Notifications link
And I click the Diagnostics link
And I select "Top" from the Team dropdown
And I select "Today" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SavedDateTime
And I select "Yesterday" from the Time Frame dropdown
And I click the Refresh button
Then I validate the SavedDateTime text is not present
