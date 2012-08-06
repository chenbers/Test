Test Cases for TF388/TF416/TF543 

Meta:
@page login
@testFolder TF388
@testFolder TF416
@testFolder TF543

Narrative:

Scenario: TC1513: Reports - Devices - Bookmark Entry 
Given I am logged in
When I click the Reports link
And I click the Devices link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
Given I am logged in
Then I validate I am on the Reports Devices page

Scenario: TC1527: Reports - Devices - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in
When I click the Reports link
And I click the Devices link
And I click the Sort By Device ID link
And I click the Sort By Assigned Vehicle link
And I click the Sort By Assigned Vehicle link
And I click the Sort By IMEI link
And I click the Sort By IMEI link
And I click the Sort By Device Phone link
And I click the Sort By Device Phone link
And I click the Sort By Status link
And I click the Sort By Status link

Scenario: TC1528: Reports - Devices - Tools Button
Given I am logged in
When I click the Reports link
And I click the Devices link
And I click the Tools button
Then I validate the Export Email button is present
And I validate the Export PDF button is present
And I validate the Export Excel button is present

Scenario: TC1529: Reports - Devices - UI
Given I am logged in
When I click the Reports link
And I click the Devices link
Then I validate I am on the Reports Devices page
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Device ID Sort link is present
And I validate the Assigned Vehicle Sort link is present
And I validate the IMEI Sort link is present
And I validate the Device Phone Sort link is present
And I validate the Status Header text is present
And I validate the Device ID Search textfield is present
And I validate the Assigned Vehicle Search textfield is present
And I validate the IMEI Search textfield is present
And I validate the Device Phone Number Search textfield is present
And I validate the Status dropdown is present

Scenario: TC1530: Reports - Devices - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 5th Row of the Column checkbox
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Device ID link is present
And I validate the Sort By Assigned Vehicle link is present
And I validate the Sort By IMEI link is present
And I validate the Sort By Device Phone link is present
And I validate the Status Header text is present

Scenario: TC1531: Reports - Devices - Edit Columns - Cancel Button (No Changes)
Given I am logged in
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Device ID link is present
And I validate the Sort By Assigned Vehicle link is present
And I validate the Sort By IMEI link is present
And I validate the Sort By Device Phone link is present
And I validate the Status Header text is present

Scenario: TC1532: Reports - Devices - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
Then I validate the 1st Row of the Column checkbox is not checked
And I uncheck the 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is not checked
And I uncheck the 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is not checked
And I uncheck the 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is not checked
And I uncheck the 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I check the 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is checked
And I check the 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is checked
And I check the 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is checked
And I check the 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is checked

Scenario: TC1534: Reports - Devices - Edit Columns - Current Session Retention
Given I am logged in
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Notifications link
And I click the Reports link
And I click the Devices link
Then I validate the Sort By Device ID link is not present
And I validate the Sort By Assigned Vehicle link is not present
And I validate the Sort By IMEI link is not present
And I validate the Sort By Device Phone link is present
And I validate the Status Header text is present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Device ID link is present
And I validate the Sort By Assigned Vehicle link is present
And I validate the Sort By IMEI link is present

Scenario: TC1535: Reports - Devices - Edit Columns - Default Command Button
Given I am logged in
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I press the Enter Key
And the Edit Columns popup closes
Then I validate the Sort By Device ID link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I press the Enter Key
And the Edit Columns popup closes
And I validate the Sort By Device ID link is present

Scenario: TC1536: Reports - Devices - Edit Columns - Save Button
Given I am logged in
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Device ID link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Device ID link is present

Scenario: TC1537: Reports - Devices - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Reports link
And I click the Devices link
Then I validate the Sort By Device ID link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Reports link
And I click the Devices link
Then I validate the Sort By Device ID link is present

Scenario: TC1539: Reports - Devices - Edit Columns - UI
Given I am logged in
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the 1st Row of the Column checkbox is present
And I validate the 2nd Row of the Column checkbox is present
And I validate the 3rd Row of the Column checkbox is present
And I validate the 4th Row of the Column checkbox is present
And I validate the 5th Row of the Column checkbox is present
And I validate the 1st Row of the Column checkbox is checked
And I validate the 2nd Row of the Column checkbox is checked
And I validate the 3rd Row of the Column checkbox is checked
And I validate the 4th Row of the Column checkbox is checked
And I validate the 5th Row of the Column checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present