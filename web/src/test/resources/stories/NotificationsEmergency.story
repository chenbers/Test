Test Cases for TF388/TF411/TF460 

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF460

Narrative:

Scenario: TC1402: Notifications - Emergency - Bookmark Entry
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
Given I am logged in
Then I validate I am on the Notifications Emergency page

Scenario: TC1403: Notifications - Emergency - Bookmark Entry to Different Account
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
Given I am logged in an account that can be edited
Then I validate I am on the Notifications Emergency page

Scenario: TC1405: Notifications - Emergency - Driver Link
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I select "Top" from the Team dropdown
And I select "Past Year" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Driver link as DRIVERNAME
And I click the 1st Row of the Entry Driver link
Then I validate the Driver Name link is DRIVERNAME

Scenario: TC1416: Notifications - Emergency - Tools Button
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I click the Tools button
Then I validate the Email Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC1417: Notifications - Emergency - UI
Given I am logged in
When I click the Notifications link
And I click the Emergency link
Then I validate I am on the Notifications Emergency page
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

Scenario: TC1418: Notifications - Emergency - Vehicle Link
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I select "Top" from the Team dropdown
And I select "Past Year" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Vehicle link as SAVEDENTRY
And I click the 1st Row of the Entry Vehicle link
Then I validate the Vehicle Name link is SAVEDENTRY

Scenario: TC1420: Notifications - Emergency - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 5th Row of the Column checkbox
And I uncheck the 6th Row of the Column checkbox
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

Scenario: TC1421: Notifications - Emergency - Edit Columns - Cancel Button (No Changes)
Given I am logged in
When I click the Notifications link
And I click the Emergency link
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

Scenario: TC1422: Notifications - Emergency - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I check the 1st Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I uncheck the 1st Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is not checked
And I check the 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is checked
And I uncheck the 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is not checked
And I check the 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is checked
And I uncheck the 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is not checked
And I check the 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is checked
And I uncheck the 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is not checked
And I check the 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is checked
And I uncheck the 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is not checked
And I check the 6th Row of the Column checkbox
And I validate the 6th Row of the Column checkbox is checked
And I uncheck the 6th Row of the Column checkbox
And I validate the 6th Row of the Column checkbox is not checked

Scenario: TC1424: Notifications - Emergency - Edit Columns - Current Session Retention
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Reports link
And I click the Notifications link
And I click the Emergency link
Then I validate the Sort By Date Time link is not present
And I validate the Sort By Group link is not present
And I validate the Sort By Driver link is not present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Header Category text is present
And I validate the Header Detail text is present

Scenario: TC1425: Notifications - Emergency - Edit Columns - Default Command Button
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I press the Enter Key
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I press the Enter Key
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC1426: Notifications - Emergency - Edit Columns - Save Button
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC1427: Notifications - Emergency - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Emergency link
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Emergency link
Then I validate the Sort By Date Time link is present


Scenario: TC1429: Notifications - Emergency - Edit Columns - UI
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the 1st Row of the Column checkbox is present
And I validate the 2nd Row of the Column checkbox is present
And I validate the 3rd Row of the Column checkbox is present
And I validate the 4th Row of the Column checkbox is present
And I validate the 5th Row of the Column checkbox is present
And I validate the 6th Row of the Column checkbox is present
And I validate the 1st Row of the Column checkbox is checked
And I validate the 2nd Row of the Column checkbox is checked
And I validate the 3rd Row of the Column checkbox is checked
And I validate the 4th Row of the Column checkbox is checked
And I validate the 5th Row of the Column checkbox is checked
And I validate the 6th Row of the Column checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present

Scenario: TC1430: Notifications - Emergency - Exclude Link - Cancel Button
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I select "Top" from the Team dropdown
And I select "Past Year" from the Time Frame dropdown
And I click the Refresh button
And I save the 1st Row of the Entry Date Time text as SAVEDDATETIME
And I save the 1st Row of the Entry Detail text as SAVEDDETAIL
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
And I click the No button
And the Exclude Event popup closes
Then I validate the 1st Row of the Entry Date Time text is SAVEDDATETIME
And I validate the 1st Row of the Entry Detail text is SAVEDDETAIL

Scenario: TC1431: Notifications - Emergency - Exclude Link - Default Command Button
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I save the Counter text as TABLECOUNT
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
And I press the Enter Key
And the Exclude Event popup closes
Then I validate the Counter text is TABLECOUNT
And I validate the 1st Row of the Entry Status link is "include"

Scenario: TC1432: Notifications - Emergency - Exclude Link - OK Button
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I select "Top" from the Team dropdown
And I select "Past 30 Days" from the Time Frame dropdown
And I click the Refresh button
And I save the Counter text as TABLECOUNT
And I click the 1st Row of the Entry Status link
And the Exclude Event popup opens
And I click the Yes button
And the Exclude Event popup closes
Then I validate the Counter text is TABLECOUNT
And I validate the 1st Row of the Entry Status link is "include"

Scenario: TC1433: Notifications - Emergency - Exclude Link - UI
Given I am logged in
When I click the Notifications link
And I click the Emergency link
And I select "Top" from the Team dropdown
And I select "Past Year" from the Time Frame dropdown
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
