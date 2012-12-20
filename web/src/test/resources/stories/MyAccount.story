Test Case for TF394

Meta:
@page login
@testFolder TF394

Narrative: 

Scenario: TC1266: My Account - Account Information (needs to be updated once new admin page is done)
Given I am logged in
And I click the Admin link
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 21st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I type "MyAccountTest" into the User Name textfield
And I click the Sort By Name link
Then I validate the 1st Row of the Entry User Name text is "MyAccountTest"
When I click the 1st Row of the Entry Name link
And I save the First Name text as FIRSTNAME
And I save the Middle Name text as MIDDLENAME
And I save the Last Name text as LASTNAME
And I combine FIRSTNAME with " " with MIDDLENAME with " " with LASTNAME and save them as FULLNAME
And I save the Locale text as LOCALE
And I save the Measurement text as MEASUREMENT
And I save the Fuel Efficiency Ratio text as FUELRATIO
And I save the User Name text as USERNAME
And I save the Group text as GROUP
And I save the Team text as TEAM
And I save the Email One link as EMAIL1
And I save the Email Two link as EMAIL2
And I save the Text Message One text as MESSAGE1
And I save the Text Message Two text as MESSAGE2
And I save the Phone One text as PHONE1
And I save the Phone Two text as PHONE2
And I save the Red Flag Info text as INFORMATION
And I save the Red Flag Warn text as WARNING
And I save the Red Flag Critical text as CRITICAL
And I click the Logout link
And I type "MyAccountTest" into the User Name field
And I type "password" into the Password field
And I click the Login button
And I click the My Account link
Then I validate the Name text is FULLNAME
And I validate the Locale text is LOCALE
And I validate the Measurement text is MEASUREMENT
And I validate the Fuel Efficiency Ratio text is FUELRATIO
And I validate the User Name text is USERNAME
And I validate the Group text is GROUP
And I validate the Team text is TEAM
And I validate the Email One text is EMAIL1
And I validate the Email Two text is EMAIL2
And I validate the Text Message One text is MESSAGE1
And I validate the Text Message Two text is MESSAGE2
And I validate the Phone One text is PHONE1
And I validate the Phone Two text is PHONE2
And I validate the Red Flag Info text is INFORMATION
And I validate the Red Flag Warn text is WARNING
And I validate the Red Flag Critical text is CRITICAL

Scenario: TC1268: My Account - Bookmark Entry
Given I am logged in
When I click the My Account link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I log back in
Then I validate I am on the My Account page

Scenario: TC1270: My Account - Bookmark Entry to Different Account
Given I am logged in
When I click the My Account link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I log back in under the editable account
Then I validate I am on the My Account page

Scenario: TC1284: My Account - UI
Given I am logged in
When I click the My Account link
Then I validate I am on the My Account page
And I validate the Change Password button is present
And I validate the Edit button is present
And I validate the Name text is present
And I validate the Group text is present
And I validate the Team text is present
And I validate the Red Flag Info text is present
And I validate the Red Flag Warn text is present
And I validate the Red Flag Critical text is present
And I validate the Map Type text is present
And I validate the Map Layers text is present
And I validate the User Name text is present
And I validate the Locale text is present
And I validate the Measurement text is present
And I validate the Fuel Efficiency Ratio text is present
And I validate the Email One text is present
And I validate the Email Two text is present
And I validate the Text Message One text is present
And I validate the Text Message Two text is present
And I validate the Phone One text is present
And I validate the Phone Two text is present