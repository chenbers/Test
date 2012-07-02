Test Case for TF394

Meta:
@page login
@testFolder TF394

Narrative: 

Scenario: TC1266: My Account - Account Information
Given I am logged in
And I click the Admin link
And I type "secondPrime" into the Name textfield
And I press the Enter key
Then I validate the 1st Row of the Name link is "secondPrime"
When I click the 1st Row of the Name link
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
And I save the Email1 link as EMAIL1
And I save the Email2 link as EMAIL2
And I save the Text Message1 text as MESSAGE1
And I save the Text Message2 text as MESSAGE2
And I save the Phone1 text as PHONE1
And I save the Phone2 text as PHONE2
And I save the Red Flag Info text as INFORMATION
And I save the Red Flag Warn text as WARNING
And I save the Red Flag Critical text as CRITICAL
And I click the My Account link
Then I validate the Name text is FULLNAME
And I validate the Locale text is LOCALE
And I validate the Measurement text is MEASUREMENT
And I validate the Fuel Efficiency Ratio text is FUELRATIO
And I validate the User Name text is USERNAME
And I validate the Group text is GROUP
And I validate the Team text is TEAM
And I validate the Email1 link is EMAIL1
And I validate the Email2 link is EMAIL2
And I validate the Text Message1 text is MESSAGE1
And I validate the Text Message2 text is MESSAGE2
And I validate the Phone1 text is PHONE1
And I validate the Phone2 text is PHONE2
And I validate the Red Flag Info text is INFORMATION
And I validate the Red Flag Warn text is WARNING
And I validate the Red Flag Critical text is CRITICAL

Scenario: TC1268: My Account - Bookmark Entry
Given I am logged in
When I click the My Account link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Given I am logged in
Then I validate I am on the My Account page

Scenario: TC1270: My Account - Bookmark Entry to Different Account
Given I am logged in
When I click the My Account link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Given I am logged in an account that can be edited
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
And I validate the Fuel Efficiency text is present
And I validate the Email1 link is present
And I validate the Email2 link is present
And I validate the Text Message1 text is present
And I validate the Text Message2 text is present
And I validate the Phone1 text is present
And I validate the Phone2 text is present