Test Cases for TF420/TF421/TF447/TF469

Meta:
@page login
@testFolder TF420

Narrative: 

Scenario: TC147: Admin - UI
Given I am logged in as a "Admin" user
When I am on the Admin page
Then I confirm the Admin page contains all necessary elements

Scenario: TC759: Admin - Users - Bookmark Entry
Given I am logged in
When I click the Admin link
When I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Given I am logged in
Then the Admin Users page is displayed

Scenario: TC762: Admin - Users - Edit link
Given I am logged in
When I click the Admin link
When I save the 1st Row of the Name link as FULLNAME
And I split FULLNAME with " " and save it again as FIRSTNAME, MIDDLENAME, LASTNAME, SUFFIXNAME
And I click the 1st Row of the Edit link
Then I validate I am on the Admin Add Edit User page
And I validate the First Name textfield is FIRSTNAME
And I validate the Middle Name textfield is MIDDLENAME
And I validate the Last Name textfield is LASTNAME
And I validate the Suffix textfield is SUFFIXNAME

Scenario: TC764: Admin - Users - Name link 
Given I am logged in
When I click the Admin link
And I save the 1st Row of the Name link as FULLNAME
And I split FULLNAME with " " and save it again as FIRSTNAME, MIDDLENAME, LASTNAME, SUFFIXNAME
And I click the 1st Row of the Name link
Then I validate I am on the Admin Add Edit User page
And I validate the First Name textfield is FIRSTNAME
And I validate the Middle Name textfield is MIDDLENAME
And I validate the Last Name textfield is LASTNAME
And I validate the Suffix textfield is SUFFIXNAME
And I validate the DOB text is not clickable
And I validate the Gender text is not clickable
And I validate the Driver License text is not clickable
And I validate the Class text is not clickable
And I validate the State text is not clickable
And I validate the Expiration text is not clickable
And I validate the Certifications text is not clickable
And I validate the DOT text is not clickable
And I validate the Team text is not clickable
And I validate the Status text is not clickable
And I validate the Employee ID text is not clickable
And I validate the Reports To text is not clickable
And I validate the Title text is not clickable
And I validate the Locale text is not clickable
And I validate the Time Zone text is not clickable
And I validate the Measurement text is not clickable
And I validate the Fuel Efficiency Ratio text is not clickable
And I validate the User Name text is not clickable
And I validate the Group text is not clickable
And I validate the Roles text is not clickable
And I validate the Email 1 text is clickable
And I validate the Email 2 text is clickable
And I validate the Text Message 1 text is not clickable
And I validate the Text Message 2 text is not clickable
And I validate the Phone 1 text is not clickable
And I validate the Phone 2 text is not clickable
And I validate the Information text is not clickable
And I validate the Warning text is not clickable
And I validate the Critical text is not clickable

Scenario: TC766: Admin - Users - Search
Given I am logged in
When I click the Admin link
When I type "tiwi" into the Name Search textfield
And I press the enter key
Then I validate the 1st Row of the Name link contains "tiwi"
And I validate the 2nd Row of the Name link contains "tiwi"
And I validate the 3rd Row of the Name link contains "tiwi"
And I validate the 4th Row of the Name link contains "tiwi"
When I type "AG0" into the Employee ID textfield
And I press the enter key
Then I validate the 1st Row of the Name link contains "AG0"
And I validate the 2nd Row of the Name link contains "AG0"
And I validate the 3rd Row of the Name link contains "AG0"
And I validate the 4th Row of the Name link contains "AG0"


Scenario: TC767: Admin - Users - Table Properties
Given I am logged in
When I click the Admin link
And I click the Edit Columns link
And I the Edit Columns popup opens
And I check the 1st Row of the Edit Columns checkbox
And I check the 2nd Row of the Edit Columns checkbox
And I check the 3rd Row of the Edit Columns checkbox
And I check the 4th Row of the Edit Columns checkbox
And I check the 5th Row of the Edit Columns checkbox
And I check the 6th Row of the Edit Columns checkbox
And I check the 7th Row of the Edit Columns checkbox
And I check the 8th Row of the Edit Columns checkbox
And I check the 9th Row of the Edit Columns checkbox
And I check the 10th Row of the Edit Columns checkbox
And I click the Save Button
And I the Edit Columns popup closes
Then I validate the table columns are sorted correctly

Scenario: TC768: Admin - Users - UI
Given I am logged in
When I click the Admin link
And I validate the Admin Users page renders correctly

Scenario: TC769: Admin - Users - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Admin link
When I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 3rd Row of the Edit Columns checkbox
Then I validate the 3rd Row of the Edit Columns checkbox is unchecked
And I check the 10th Row of the Edit Columns checkbox
And I validate the 10th Row of the Edit Columns checkbox is checked
And I click the Cancel Button
And the Edit Columns popup closes 
And I validate the Sort By IMEI link is present
And I validate the Sort By Product link is not present
And I click the Edit Columns link
And I validate the Edit Columns popup opens
And I validate my changes were not saved

Scenario: TC770: Admin - Users - Edit Columns - Cancel Button (No Changes)
Given I am logged in
When I click the Admin link
And I click the Edit Columns link
And I validate the Edit Columns popup opens
And I click the Cancel Button
Then I validate the Edit Columns popup closes 
And I validate no message appears

Scenario: TC771: Admin - Users - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Admin link 
And I click the Edit Columns link
And I validate the Edit Columns popup opens
And I check the 1st Row of the Edit Columns checkbox
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
And I check the 7th Row of the Edit Columns checkbox
And I validate the 7th Row of the Edit Columns checkbox is checked
And I uncheck the 7th Row of the Edit Columns checkbox
And I validate the 7th Row of the Edit Columns checkbox is not checked
And I check the 8th Row of the Edit Columns checkbox
And I validate the 8th Row of the Edit Columns checkbox is checked
And I uncheck the 8th Row of the Edit Columns checkbox
And I validate the 8th Row of the Edit Columns checkbox is not checked
And I check the 9th Row of the Edit Columns checkbox
And I validate the 9th Row of the Edit Columns checkbox is checked
And I uncheck the 9th Row of the Edit Columns checkbox
And I validate the 9th Row of the Edit Columns checkbox is not checked
And I check the 10th Row of the Edit Columns checkbox
And I validate the 10th Row of the Edit Columns checkbox is checked
And I uncheck the 10th Row of the Edit Columns checkbox
And I validate the 10th Row of the Edit Columns checkbox is not checked

Scenario: TC773: Admin - Users - Edit Columns - Current Session Retention
Given I am logged in
When I click the Admin link 
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 3rd Row of the Edit Columns checkbox
And I check the 10th Row of the Edit Columns checkbox
And I click the Save Button
And the Edit Columns popup closes
And I click the Live Fleet link
And I click the Admin link
Then I validate the Sort By IMEI link is not present
And I validate the Sort By Product link is present

Scenario: TC775: Admin - Users - Edit Columns - Save Button
Given I am logged in
When I click the Admin link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 3rd Row of the Edit Columns checkbox
Then I validate the 3rd Row of the Edit Columns checkbox is unchecked
And I check the 10th Row of the Edit Columns checkbox
And I validate the 10th Row of the Edit Columns checkbox is checked
And I click the Save Button
And the Edit Columns popup closes 
And I validate the Sort By IMEI link is not present
And I validate the Sort By Product link is present
And I click the Edit Columns link
And I validate the Edit Columns popup opens
And I validate the 3rd Row of the Edit Columns checkbox is unchecked
And I validate the 10th Row of the Edit Columns checkbox is checked

Scenario: TC776: Admin - Users - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Admin link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 3rd Row of the Edit Columns checkbox
Then I validate the 3rd Row of the Edit Columns checkbox is unchecked
And I check the 10th Row of the Edit Columns checkbox
And I validate the 10th Row of the Edit Columns checkbox is checked
And I click the Save Button
And the Edit Columns popup closes 
And I click the Logout Button
Given I am logged in
When I click the Admin link
Then I validate the 3rd Row of the Edit Columns checkbox is unchecked
And I validate the 10th Row of the Edit Columns checkbox is checked

Scenario: TC778: Admin - Users - Edit Columns - UI
Given I am logged in
When I click the Admin link
And I click the Edit Columns link
Then I validate the Edit Columns popup opens
nd I check the 1st Row of the Edit Columns checkbox
And I validate the 1st Row of the Edit Columns checkbox is present
And I validate the 2nd Row of the Edit Columns checkbox is present
And I validate the 3rd Row of the Edit Columns checkbox is present
And I validate the 4th Row of the Edit Columns checkbox is present
And I validate the 5th Row of the Edit Columns checkbox is present
And I validate the 6th Row of the Edit Columns checkbox is present
And I validate the 7th Row of the Edit Columns checkbox is present
And I validate the 8th Row of the Edit Columns checkbox is present
And I validate the 9th Row of the Edit Columns checkbox is present
And I validate the 10th Row of the Edit Columns checkbox is present
And I validate the Save button is present
And I validate the Cancel button is present

Scenario: TC4957: Admin - Users - DOT Rule Set for Driver
Given I am logged in
When I click the Admin link
And I click the Add User link 
Then I validate the Dot dropdown exists

Scenario: TC5676-TC5691 Admin - Users - Set DOT US 7 day Rule Set for Driver (REST OF THE STEPS CANNOT BE IMPLEMENTED BECAUSE YOU HAVE TO USE THE EXTERNAL TOUCHSCREEN)
Given I am logged in
When I click the Admin link
And I type "tiwi 02" into the Name Search textfield
And I press the Enter key
And I click on the 1st Row of the Edit link
And I select "US 7 Day" from the Dot dropdown
And I click the Save Button

Meta:
@testFolder TF421

Scenario: TC822: Admin - Users - View User - Back to Users link
Given I am logged in
When I click the Admin link
And I click the 1st Row of the Name link
And I click the Back To Users link
Then I validate I am on the Admin Users page

Scenario: TC823: Admin - Users - View User - Bookmark Entry
Given I am logged in
When I click the Admin link
And I validate I am on the Admin Users page
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I validate I am on the Login page
Given I am logged in
Then I validate I am on the Admin Users page

Scenario: TC826: Admin - Users - View User - Cancel Button
Given I am logged in
When I click the Admin link
And I save the 1st Row of the Name link as SAVEDNAME
And I split SAVEDNAME with " " and save it again as FIRSTNAME, MIDDLENAME, LASTNAME, SUFFIXNAME
And I click the 1st Row of the Name link
And I click the Edit Button
And I click the Cancel Button
Then I validate I am on the User Details page
And I verify "SAVEDNAME details" is on the page
And I validate I am on the Admin Add Edit User page
And I validate the First Name textfield is FIRSTNAME
And I validate the Middle Name textfield is MIDDLENAME
And I validate the Last Name textfield is LASTNAME
And I validate the Suffix textfield is SUFFIXNAME
And I validate the DOB text is not clickable
And I validate the Gender text is not clickable
And I validate the Driver License text is not clickable
And I validate the Class text is not clickable
And I validate the State text is not clickable
And I validate the Expiration text is not clickable
And I validate the Certifications text is not clickable
And I validate the DOT text is not clickable
And I validate the Team text is not clickable
And I validate the Status text is not clickable
And I validate the Employee ID text is not clickable
And I validate the Reports To text is not clickable
And I validate the Title text is not clickable
And I validate the Locale text is not clickable
And I validate the Time Zone text is not clickable
And I validate the Measurement text is not clickable
And I validate the Fuel Efficiency Ratio text is not clickable
And I validate the User Name text is not clickable
And I validate the Group text is not clickable
And I validate the Roles text is not clickable
And I validate the Email 1 text is clickable
And I validate the Email 2 text is clickable
And I validate the Text Message 1 text is not clickable
And I validate the Text Message 2 text is not clickable
And I validate the Phone 1 text is not clickable
And I validate the Phone 2 text is not clickable
And I validate the Information text is not clickable
And I validate the Warning text is not clickable
And I validate the Critical text is not clickable

Scenario: TC827: Admin - Users - View User - Delete Button
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type TC827 into the First Name textfield
And I type TC827 into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I save the Records text as SAVEDRECORDS
And I type "TC827" into the Name textfield
And I press the Enter key
And I save the 1st Row of the Name link as SAVEDNAME
And I click the 1st Row of the Name link
And I click the Delete Button
And the Delete User pop up opens
And I click the Delete button
And the Delete User pop up closes
Then I validate the Message text is "A User SAVEDNAME deleted"
And I type "TC827" into the Name textfield
And I press the Enter key
And I validate the 1st Row of the Name link is not present
And I validate the Records text is not SAVEDRECORDS

Scenario: TC828: Admin - Users - View User - Edit Button
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type TC828 into the First Name textfield
And I type TC828 into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I type TC828 into the Name textfield
And I press the Enter key
And I click the 1st Row of the Name link
And I click the Edit Button
Then I validate I am on the Admin Edit User page
And I validate the First Name textfield is TC828
And I validate the Last Name textfield is TC828
And I validate the DOB text is not clickable
And I validate the Gender text is not clickable
And I validate the Driver License text is not clickable
And I validate the Class text is not clickable
And I validate the State text is not clickable
And I validate the Expiration text is not clickable
And I validate the Certifications text is not clickable
And I validate the DOT text is not clickable
And I validate the Team text is not clickable
And I validate the Status text is not clickable
And I validate the Employee ID text is not clickable
And I validate the Reports To text is not clickable
And I validate the Title text is not clickable
And I validate the Locale text is not clickable
And I validate the Time Zone text is not clickable
And I validate the Measurement text is not clickable
And I validate the Fuel Efficiency Ratio text is not clickable
And I validate the User Name text is not clickable
And I validate the Group text is not clickable
And I validate the Roles text is not clickable
And I validate the Email 1 text is clickable
And I validate the Email 2 text is clickable
And I validate the Text Message 1 text is not clickable
And I validate the Text Message 2 text is not clickable
And I validate the Phone 1 text is not clickable
And I validate the Phone 2 text is not clickable
And I validate the Information text is not clickable
And I validate the Warning text is not clickable
And I validate the Critical text is not clickable

Scenario: TC829: Admin - Users - View User - UI
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type TC829 into the First Name textfield
And I type TC829 into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I type TC829 into the Name textfield
And I press the Enter key
And I click the 1st Row of the Name link
Then I validate I am on the User Details page
And I validate the First Name textfield is TC829
And I validate the Last Name textfield is TC829
And I validate the DOB text is not clickable
And I validate the Gender text is not clickable
And I validate the Driver License text is not clickable
And I validate the Class text is not clickable
And I validate the State text is not clickable
And I validate the Expiration text is not clickable
And I validate the Certifications text is not clickable
And I validate the DOT text is not clickable
And I validate the Team text is not clickable
And I validate the Status text is not clickable
And I validate the Employee ID text is not clickable
And I validate the Reports To text is not clickable
And I validate the Title text is not clickable
And I validate the Locale text is not clickable
And I validate the Time Zone text is not clickable
And I validate the Measurement text is not clickable
And I validate the Fuel Efficiency Ratio text is not clickable
And I validate the User Name text is not clickable
And I validate the Group text is not clickable
And I validate the Roles text is not clickable
And I validate the Email 1 text is clickable
And I validate the Email 2 text is clickable
And I validate the Text Message 1 text is not clickable
And I validate the Text Message 2 text is not clickable
And I validate the Phone 1 text is not clickable
And I validate the Phone 2 text is not clickable
And I validate the Information text is not clickable
And I validate the Warning text is not clickable
And I validate the Critical text is not clickable

Meta:
@testFolder TF447

Scenario: TC477: Admin - Users - Delete User - Cancel Button
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type TC477A into the First Name textfield
And I type TC477A into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I click the Add User link
And I type TC477B into the First Name textfield
And I type TC477B into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I click the Add User link
And I type TC477C into the First Name textfield
And I type TC477C into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I click the Add User link
And I type TC477D into the First Name textfield
And I type TC477D into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I save the Records text as SAVEDRECORDS
And I type TC477 into the Name textfield
And I press the Enter key
And I click the 1st Row of the Entry Check Row checkbox
And I click the 2nd Row of the Entry Check Row checkbox
And I click the 3rd Row of the Entry Check Row checkbox
And I click the 4th Row of the Entry Check Row checkbox
And I click the Delete Button
And the Delete popup opens
And I click the Cancel Button
Then the Delete Users popup closes
And I validate the 1st Row of the Entry Check Row checkbox is checked
And I validate the 2nd Row of the Entry Check Row checkbox is checked
And I validate the 3rd Row of the Entry Check Row checkbox is checked
And I validate the 4th Row of the Entry Check Row checkbox is checked
And I validate the Records text is SAVEDRECORDS
And I click the Delete Button
And the Delete popup opens
And I click the Delete Button

Scenario: TC478: Admin - Users - Delete User - Delete Button
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type TC478A into the First Name textfield
And I type TC478A into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I click the Add User link
And I type TC478B into the First Name textfield
And I type TC478B into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I click the Add User link
And I type TC478C into the First Name textfield
And I type TC478C into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I click the Add User link
And I type TC478D into the First Name textfield
And I type TC478D into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Samoa (GMT-11:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I click the Back To Users link
And I save the Records text as SAVEDRECORDS
And I type TC478 into the Name textfield
And I press the Enter key
And I click the 1st Row of the Entry Check Row checkbox
And I click the 2nd Row of the Entry Check Row checkbox
And I click the 3rd Row of the Entry Check Row checkbox
And I click the 4th Row of the Entry Check Row checkbox
And I click the Delete Button
And the Delete popup opens
And I click the Delete Button
And the Delete Users popup closes
Then I validate the Message text "A User TC478A deleted" is present
And I validate the Message text "A User TC478B deleted" is present
And I validate the Message text "A User TC478C deleted" is present
And I validate the Message text "A User TC478D deleted" is present
And I type TC478 into the Name textfield
And I press the Enter key
And I validate the 1st row of the Name link is not present
And I validate the Records text is SAVEDRECORDS

Scenario: TC481: Admin - Users - Delete User - No Selection Error
Given I am logged in
When I click the Admin link
And I save the Records text as SAVEDRECORDS
And I click the Delete Button
And I validate the Delete popup opens
And I click the Delete Button
Then I validate the Delete popup closes 
And I validate the Error Message text is "No items were selected"
And I validate the Records text is SAVEDRECORDS

Scenario: TC482: Admin - Users - Delete User - Red Flags Interaction (Assign To)
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type "TC482" into the First Name textfield
And I type "TC482" into the Last Name textfield
And I select "Top - Test Group RW" from the Team dropdown
And I select "US/Mountain (GMT-7:00)" from the Time Zone dropdown
And I uncheck the Login Information checkbox
And I click the Bottom Save button
And I type "TC482" into the Name texfield
And I press the Enter key
And I check the 1st Row of the Entry checkbox
And I click the Delete Button
And the Delete popup opens
And I validate the User text contains "TC482"
And I click the Delete Button
And the Delete popup closes 
And I click on the Red Flag link
And I click on the Add Red Flag link
And I select Drivers from the Assign dropdown
Then I validate the Users list does not contain "TC482"
And I validate the Alert Owner dropdown does not contain "TC482"

Scenario: TC484: Admin - Users - Delete User - Red Flags Interaction (Notifications By Name)
Given I am logged in
When I click the Admin link
And I click on the Red Flags link
And I record the name of a user
And I checkmark the recorded user
And I click the Delete Button
And I validate the Delete Users popup opens
And I click the Delete Button
And I validate the Delete Users popup closes
And I click on the Red Flags link
And I click the "All speeding" Edit link
Then I type the recored user into the Notifications by Name box
And I validate the name does not appear

Scenario: TC485: Admin - Users - Delete User - UI
Given I am logged in
When I click the Admin link
And I checkmark several users
And I record the names of those users
And I click the Delete Button
Then I validate the Delete Users popup opens
And I validate the recorded users names appear in the pop up
And I validate the Delete Users popup renders correctly

Scenario: TC487: Admin - Users - Delete User - Zone Interaction (Notifications By Name)
Given I am logged in
When I click the Admin link
And I click on the Red Flags link
And I record the name of a user
And I checkmark the recorded user
And I click the Delete Button
And I validate the Delete Users popup opens
And I click the Delete Button
And I validate the Delete Users popup closes
And I click on the Zones link
And I click on the Add Alert Button
And I click the corresponding Zone Edit link
Then I type the recored user into the Notifications by Name box
And I validate the name does not appear

Meta:
@testFolder TF469

Scenario: TC163: Admin - Users - Add User - Default Command Button
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type data in all the required fields
And I type data in some of the nonrequired fields
And I press the Enter Key
Then I validate the Admin User Name Details Page appears
And I validate the text string "User [user name] added." appears below the toolbar
And I validate all the text on the page is static
And I click the Add User link
And I validate all the fields where data was entered are blank

Scenario: TC164: Admin - Users - Add User - Save Button
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type data in all the required fields
And I type data in some of the nonrequired fields
And I click the Save Button
Then I am on the Admin Details page
And I validate the user name matches the user just entered
And I validate the text string "User [user name] added." appears below the toolbar
And I validate all the text on the page is static
And I click the Add User link
And I validate all the fields where data was entered are blank

Scenario: TC264: Admin - Users - Add User - Assign Driver to Vehicle Interaction
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type data in all the required fields
And I click the Save Button
And I click the Vehicles link
And I record the name of a vehicle that has a device
And I click the Edit link of that vehicle
And I click the Assign link
Then I validate the user appears in the table

Scenario: TC265: Admin - Users - Add User - Bookmark Entry
Given I am logged in
When I click the Admin link
And I click the Add User link
And I bookmark the page
And I click the Logout Button
And I click the bookmark I just added
And I am logged in as a "Admin" user
Then I validate I am on the Add User page
And the Add User page renders correctly

Scenario: TC268: Admin - Users - Add User - Cancel Button (Changes)
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type data in some of the nonrequired fields
And I click the Cancel Button
Then I validate the Admin User page appears 
And I validate a text string does not appear
And I validate none of the entered data is in the Admin Users table
And I click the Add User link
And I validate the Add User page renders correctly

Scenario: TC269: Admin - Users - Add User - Cancel Button (No Changes)
Given I am logged in
When I click the Admin link
And I click the Zones link
And I click on the Users link
And I click on the Add User link
And I click the Cancel Button
When I click the Admin link
And I validate a text string does not appear

Scenario: TC270: Admin - Users - Add User - Collapse Driver Information Section
Given I am logged in
When I click the Admin link
And I click the Add User link
And I click the Driver Information Checkbox
Then I validate the fields in Driver Information are hidden

Scenario: TC271: Admin - Users - Add User - Collapse Log In Information Section
Given I am logged in
When I click the Admin link
And I click the Add User link
And I click the Log In Information Checkbox
Then I validate the fields in Log In Information are hidden

Scenario: TC272: Admin - Users - Add User - validate Password Upper Case Error
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type data in all the required fields
And I type a lower case password in the Password textfield
And I type a upper case password in the Password Again textfield
And I click the Save button
Then I validate the text 'Does not match password' is present

Scenario: TC273: Admin - Users - Add User - DOB Format Error
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type data in all the required fields
And I type a invalid date into the Date textfield
And I click the Save button
Then I validate the text 'Incorrect format (Mar 7, 2000)' is present

Scenario: TC274: Admin - Users - Add User - Driver License # Max Character Limitation
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type more than 20 characters into the Driver License textfield
Then I validate the Driver License textfield has 20 characters

Scenario: TC275: Admin - Users - Add User - E-mail Address Format Error
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type an invalid email address into the E-mail 1 textfield
And I click the Save button
Then I validate the text 'Incorrect format (jdoe@tiwipro.com)' is present

Scenario: TC276: Admin - Users - Add User - Employee ID Max Character Limitation
Given I am logged in
When I click the Admin link
And I click the Add User link
And I type more than 20 characters into the Driver License textfield
Then I validate the Employee ID textfield has 20 characters

Scenario: TC277: Admin - Users - Add User - Expand Driver Information Section
Given I am logged in
When I click the Admin link
And I click the Add User link
And I check the Driver Information checkbox
Then I validate the Driver Information fields are visible

Scenario: TC278: Admin - Users - Add User - Expand Log In Information Section
Given I am logged in
When I click the Admin link
And I click the Add User link
And I check the Login Information checkbox
Then I validate the Login Information fields are visible

Scenario: TC279: Admin - Users - Add User - Expand/Collapse Section via Spacebar
Given I am logged in
When I click the Admin link
And I click the Add User link

