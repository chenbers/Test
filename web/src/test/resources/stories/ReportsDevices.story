Test Cases for TF388/TF416/TF543 

Meta:
@page login
@testFolder TF388
@testFolder TF416
@testFolder TF543

Narrative:

Scenario: TC1513: Reports - Devices - Bookmark Entry 
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Devices link
And I type "" into the Search textfield
And I click the Search button
And I bookmark the page
And I click log out
And I click the bookmark I just added
And I am on the login page
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
Then I validate I am on the Devices page
And I validate the Search textfield is ""
And I validate the Driver Report table is not ""

Scenario: TC1527: Reports - Devices - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in as a "Admin" user
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
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Devices link
And I click the Tools button
Then I validate the Email Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC1529: Reports - Devices - UI
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Devices link
Then I validate I am on the Devices page
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Sort By Device ID link is present
And I validate the Sort By Assigned Vehicle link is present
And I validate the Sort By IMEI link is present
And I validate the Sort By Device Phone link is present
And I validate the Header Status text is present
And I validate the Device ID textfield is present
And I validate the Assigned Vehicle textfield is present
And I validate the IMEI textfield is present
And I validate the Device Phone textfield is present
And I validate the Stats dropdown is present

Scenario: TC1530: Reports - Devices - Edit Columns - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I uncheck the checkbox of the fourth entry
And I uncheck the checkbox of the fifth entry
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Device ID link is present
And I validate the Sort By Assigned Vehicle link is present
And I validate the Sort By IMEI link is present
And I validate the Sort By Device Phone link is present
And I validate the Header Status text is present

Scenario: TC1531: Reports - Devices - Edit Columns - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
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
And I validate the Header Status text is present

Scenario: TC1532: Reports - Devices - Edit Columns - Check Box Selection via Mouse
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Devices link
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

Scenario: TC1534: Reports - Devices - Edit Columns - Current Session Retention
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I click the Save button
And the Edit Columns popup closes
And I click the Notifications link
And I click the Reports link
And I click the Devices link
Then I validate the Sort By Device ID link is not present
And I validate the Sort By Assigned Vehicle link is not present
And I validate the Sort By IMEI link is not present
And I validate the Sort By Device Phone link is present
And I validate the Header Status text is present

Scenario: TC1536: Reports - Devices - Edit Columns - Save Button
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Device ID link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the checkbox of the first entry is not checked
And I check the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Device ID link is present

Scenario: TC1537: Reports - Devices - Edit Columns - Subsequent Session Retention
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
When I click the Reports link
And I click the Devices link
Then I validate the Sort By Device ID link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
When I click the Reports link
And I click the Devices link
And I validate the Sort By Device ID link is present

Scenario: TC1539: Reports - Devices - Edit Columns - UI
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Devices link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the first checkbox is present
And I validate the second checkbox is present
And I validate the third checkbox is present
And I validate the fourth checkbox is present
And I validate the fifth checkbox is present
And I validate the first checkbox is checked
And I validate the second checkbox is checked
And I validate the third checkbox is checked
And I validate the fourth checkbox is checked
And I validate the fifth checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present