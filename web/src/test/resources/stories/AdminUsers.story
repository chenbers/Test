Test Cases for TF420/TF421/TF447/TF469

Meta:
@page login
@testFolder TF420

Narrative: 

Scenario: TC759: Admin - Users - Bookmark Entry
Given I am logged in as a "Admin" user
When I am on the Admin page
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I am logged in as a "Admin" user
Then the admin page is displayed

Scenario: TC760:TC761 Admin - Users - Bookmark Entry to Different Account (CANNOT BE IMPLEMENTED YET)

Scenario: TC762: Admin - Users - Edit link
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link
And I record the name of that user
And I click the Edit link
Then I confirm the name of the user is the same as what was on the table
And I confirm the edit user page renders correctly

Scenario: TC763: Admin - Users - Email Address link (CANNOT BE IMPLEMENTED YET DUE TO EXTERNAL EMAIL PROGRAM LAUNCHING)

Scenario: TC764: Admin - Users - Name link 
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link
Then I confirm the name of the user is the same as what was on the table
And I confirm all the text on the page is static

Scenario: TC765: Admin - Users - Page link (THIS PAGE NEEDS TO BE TESTED MANUALLY BECAUSE YOU CAN'T VERIFY IF THE BAR IS ABOVE THE PAGE NUMBER WITHOUT LOOKING AT IT)

Scenario: TC766: Admin - Users - Search
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link
And I enter text in the search field corresponding to multiple records in the table
And I click search
Then all records displayed in the table correspond to the search text

Scenario: TC767: Admin - Users - Table Properties
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link
And I click the Edit Columns button
And I confirm Edit Columns popup opens
And I checkmark all boxes in the Edit Columns box
And I click the Save Button
And I confirm the Edit Columns popup closes
And I click on each of the different column headings
Then I confirm the table columns are sorted correctly

Scenario: TC768: Admin - Users - UI
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link
Then I confirm the Admin Users page renders correctly

Scenario: TC769: Admin - Users - Edit Columns - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I click the Edit Columns button
And I confirm the Edit Columns popup opens
And I uncheckmark a checkmarked box
And I confirm the box becomes unchecked
And I checkmark a uncheckmarked box
And I confirm the box becomes checked
And I click the Cancel Button
Then I confirm the Edit Columns popup closes 
And I confirm the table does not change
And I click the Edit Columns button
And I confirm the Edit Columns popup opens
And I confirm my changes were not saved

Scenario: TC770: Admin - Users - Edit Columns - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I click the Edit Columns button
And I confirm the Edit Columns popup opens
And I click the Cancel Button
Then I confirm the Edit Columns popup closes 
And I confirm no message appears

Scenario: TC771: Admin - Users - Edit Columns - Check Box Selection via Mouse
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I click the Edit Columns button
And I confirm Edit Columns popup opens
And I uncheckmark a checkmarked box
Then I confirm the box becomes unchecked
And I checkmark a uncheckmarked box
And I confirm the box becomes checked

Scenario: TC772: Admin - Users - Edit Columns - Check Box Selection via Spacebar (CANNOT IMPLEMENT KEY PRESS YET)

Scenario: TC773: Admin - Users - Edit Columns - Current Session Retention
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I click the Edit Columns button
And I confirm the Edit Columns popup opens
And I uncheckmark a selection
And I checkmark a selection
And I click the Save Button
And I confirm the Edit Columns popup closes
And I am on the Live Fleet page
And I am on the Admin page
Then I confirm the name column is no longer in the table
And the DOT column is in the table

Scenario: TC774: Admin - Users - Edit Columns - Default Command Button (CANNOT IMPLEMENT KEY PRESS YET)

Scenario: TC775: Admin - Users - Edit Columns - Save Button
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users tab
And I click the Edit Columns button
And I confirm the Edit Columns popup opens
And I uncheckmark a checkmarked box
And I confirm the box becomes unchecked
And I checkmark a uncheckmarked box
And I confirm the box becomes checked
And I click the Save Button
Then I confirm the Edit Columns popup closes 
And I confirm the table columns update correctly
And I click the Edit Columns button
And I confirm the Edit Columns popup opens
And I confirm my changes were saved

Scenario: TC776: Admin - Users - Edit Columns - Subsequent Session Retention
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I click the Edit Columns button
And I confirm the Edit Columns popup opens
And I uncheckmark a checkmarked box
And I confirm the box becomes unchecked
And I checkmark a uncheckmarked box
And I confirm the box becomes checked
And I click the Save Button
And I confirm the Edit Columns popup closes 
And I click the Logout Button
And I am logged in as a "Admin" user
And I am on the Admin page
Then I confirm the table columns update correctly

Scenario: TC777: Admin - Users - Edit Columns - Tabbing Order (CANNOT IMPLEMENT KEY PRESS YET)

Scenario: TC778: Admin - Users - Edit Columns - UI
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I click the Edit Columns button
Then I confirm the Edit Columns popup opens
And I verify the Edit Columns popup renders correctly

Scenario: TC4957: Admin - Users - DOT Rule Set for Driver
Given I am logged in as a "HOS" user
When I am on the Admin page
And I click the Add User link 
Then I confirm the DOT Dropdown exists

Scenario: TC5676-TC5691 Admin - Users - Set DOT US 7 day Rule Set for Driver (REST OF THE STEPS CANNOT BE IMPLEMENTED BECAUSE YOU HAVE TO USE THE EXTERNAL TOUCHSCREEN)
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I type a valid driver into the Search Textfield
And I click the Search Button
And I click on the Edit link
And I select "US 7 Day" from the DOT Dropdown
And I click the Save Button

Meta:
@testFolder TF421

Scenario: TC822: Admin - Users - View User - Back to Users link
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I select a valid user
And I click the Back To Users link
Then I am on the Admin Users page

Scenario: TC823: Admin - Users - View User - Bookmark Entry
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I select a valid user
And I click the Back To Users link
Then I am on the Admin Users page

Scenario: TC824:TC825: Admin - Users - View User - Bookmark Entry to a different account (CANNOT BE IMPLEMENTED YET)

Scenario: TC826: Admin - Users - View User - Cancel Button
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I select a valid user
And I click the Edit Button
And I click the Cancel Button
Then I am on the User Details page
And I confirm all the text on the page is static

Scenario: TC827: Admin - Users - View User - Delete Button
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I record the number of records in the table
And I select a valid user
And I click the Delete Button
And the Delete User pop up opens
And I click the Delete button in the Delete User pop up window
Then the Delete User pop up closes
And I confirm the text string "A User [name of user] deleted" is displayed above the table
And I confirm the deleted user is missing from the table
And I confirm the number of records in the table is decreased by one

Scenario: TC828: Admin - Users - View User - Edit Button
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I select a valid user
And I record the name of that user
And I click the Edit Button
Then I am on the Edit User page
And I confirm the name of the user is the same as what was on the table
And I confirm the correct elements are editable

Scenario: TC829: Admin - Users - View User - UI
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I select a valid user
And I record the name of that user
Then I am on the Admin Details page
And I confirm the name of the user is the same as what was on the table
And I confirm all the text on the page is static
And I confirm the Admin Users Detail Toolbar renders correctly

Meta:
@testFolder TF447

Scenario: TC477: Admin - Users - Delete User - Cancel Button
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I record the number of records in the table
And I checkmark several users
And I click the Delete Button
And I confirm the Delete Users popup opens
And I click the Cancel Button
Then I confirm the Delete Users popup closes
And I confirm the checkboxes are still selected
And I confirm the number of records in the table is the same

Scenario: TC478: Admin - Users - Delete User - Delete Button
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I record the number of records in the table
And I checkmark several users
And I record the names of those users
And I click the Delete Button
And I confirm the Delete Users popup opens
And I click the Delete Button
Then I confirm the Delete Users popup closes 
And I confirm the text string "A User [name of user] deleted" is displayed above the table for each user
And I confirm the recorded names are deleted from the table
And I confirm the number of records in the table is updated correctly

Scenario: TC479: Admin - Users - Delete User - Delete Button Activation via Spacebar (CANNOT IMPLEMENT KEY PRESS YET)

Scenario: TC481: Admin - Users - Delete User - No Selection Error
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I record the number of records in the table
And I click the Delete Button
And I confirm the Delete Users popup opens
And I click the Delete Button
Then I confirm the Delete Users popup closes 
And I confirm the text string "No items were selected" is displayed above the table
And I confirm the number of records in the table is the same

Scenario: TC482: Admin - Users - Delete User - Red Flags Interaction (Assign To)
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I checkmark a valid "Driver" user
And I record the name of that user
And I click the Delete Button
And I confirm the Delete Users popup opens
And I click the Delete Button
And I confirm the Delete Users popup closes 
And I click on the Add Red Flag link
And I select Drivers in the Assign Dropdown
Then I confirm the deleted user does not appear in the Dropdown

Scenario: TC483: Admin - Users - Delete User - Red Flags Interaction (Notification) (CANNOT IMPLEMENT REQUIRES USING AN EXTERNAL EMAIL PROGRAM)

Scenario: TC484: Admin - Users - Delete User - Red Flags Interaction (Notifications By Name)
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click on the Red Flags link
And I record the name of a user
And I click the Users link 
And I checkmark the recorded user
And I click the Delete Button
And I confirm the Delete Users popup opens
And I click the Delete Button
And I confirm the Delete Users popup closes
And I click on the Red Flags link
And I click the "All speeding" Edit link
Then I type the recored user into the Notifications by Name box
And I confirm the name does not appear

Scenario: TC485: Admin - Users - Delete User - UI
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Users link 
And I checkmark several users
And I record the names of those users
And I click the Delete Button
Then I confirm the Delete Users popup opens
And I confirm the recorded users names appear in the pop up
And I confirm the Delete Users popup renders correctly

Scenario: TC486: Admin - Users - Delete User - Add Alert Interaction (Notification) (CANNOT IMPLEMENT REQUIRES USING AN EXTERNAL EMAIL PROGRAM)

Scenario: TC487: Admin - Users - Delete User - Zone Interaction (Notifications By Name)
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click on the Red Flags link
And I record the name of a user
And I click the Users link 
And I checkmark the recorded user
And I click the Delete Button
And I confirm the Delete Users popup opens
And I click the Delete Button
And I confirm the Delete Users popup closes
And I click on the Zones link
And I click on the Add Alert Button
And I click the corresponding Zone Edit link
Then I type the recored user into the Notifications by Name box
And I confirm the name does not appear

Scenario: TC1896: Admin - Users - Delete User - Customers are able to delete the primary user in their account (UNABLE TO IMPLEMENT YET DUE TO MULTIPLE ACCOUNTS)

Meta:
@testFolder TF469

Scenario: TC163: Admin - Users - Add User - Default Command Button
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I enter data in all the required fields
And I enter data in some of the nonrequired fields
And I press the Enter Key
Then I confirm the Admin User Name Details Page appears
And I confirm the text string "User [user name] added." appears below the toolbar
And I confirm all the text on the page is static
And I click the Add User link
And I confirm all the fields where data was entered are blank

Scenario: TC164: Admin - Users - Add User - Save Button
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I enter data in all the required fields
And I enter data in some of the nonrequired fields
And I click the Save Button
Then I am on the Admin Details page
And I confirm the user name matches the user just entered
And I confirm the text string "User [user name] added." appears below the toolbar
And I confirm all the text on the page is static
And I click the Add User link
And I confirm all the fields where data was entered are blank

Scenario: TC264: Admin - Users - Add User - Assign Driver to Vehicle Interaction
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I enter data in all the required fields
And I click the Save Button
And I click the Vehicles link
And I record the name of a vehicle that has a device
And I click the Edit link of that vehicle
And I click the Assign link
Then I confirm the user appears in the table

Scenario: TC265: Admin - Users - Add User - Bookmark Entry
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I bookmark the page
And I click the Logout Button
And I click the bookmark I just added
And I am logged in as a "Admin" user
Then I confirm I am on the Add User page
And the Add User page renders correctly

Scenario: TC266:TC267 Admin - Users - Add User - Bookmark Entry to Different Account (UNABLE TO IMPLEMENT RIGHT NOW DUE TO MULTIPLE SIGN IN'S BEING NEEDED)

Scenario: TC268: Admin - Users - Add User - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I enter data in some of the nonrequired fields
And I click the Cancel Button
Then I confirm the Admin User page appears 
And I confirm a text string does not appear
And I confirm none of the entered data is in the Admin Users table
And I click the Add User link
And I confirm the Add User page renders correctly

Scenario: TC269: Admin - Users - Add User - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Zones link
And I click on the Users link
And I click on the Add User link
And I click the Cancel Button
Then I confirm I am on the Admin Users page
And I confirm a text string does not appear

Scenario: TC270: Admin - Users - Add User - Collapse Driver Information Section
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I click the Driver Information Checkbox
Then I confirm the fields in Driver Information are hidden

Scenario: TC271: Admin - Users - Add User - Collapse Log In Information Section
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I click the Log In Information Checkbox
Then I confirm the fields in Log In Information are hidden

Scenario: TC272: Admin - Users - Add User - Confirm Password Upper Case Error
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I enter data in all the required fields
And I type a lower case password in the Password textfield
And I type a upper case password in the Password Again textfield
And I click the Save button
Then I confirm the text 'Does not match password' is present

Scenario: TC273: Admin - Users - Add User - DOB Format Error
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I enter data in all the required fields
And I type a invalid date into the Date textfield
And I click the Save button
Then I confirm the text 'Incorrect format (Mar 7, 2000)' is present

Scenario: TC274: Admin - Users - Add User - Driver License # Max Character Limitation
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I type more than 20 characters into the Driver License textfield
Then I confirm the Driver License textfield has 20 characters

Scenario: TC275: Admin - Users - Add User - E-mail Address Format Error
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I type an invalid email address into the E-mail 1 textfield
And I click the Save button
Then I confirm the text 'Incorrect format (jdoe@tiwipro.com)' is present

Scenario: TC276: Admin - Users - Add User - Employee ID Max Character Limitation
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I type more than 20 characters into the Driver License textfield
Then I confirm the Employee ID textfield has 20 characters

Scenario: TC277: Admin - Users - Add User - Expand Driver Information Section
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I check the Driver Information checkbox
Then I confirm the Driver Information fields are visible

Scenario: TC278: Admin - Users - Add User - Expand Log In Information Section
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link
And I check the Login Information checkbox
Then I confirm the Login Information fields are visible

Scenario: TC279: Admin - Users - Add User - Expand/Collapse Section via Spacebar
Given I am logged in as a "Admin" user
When I am on the Admin page
And I click the Add User link























