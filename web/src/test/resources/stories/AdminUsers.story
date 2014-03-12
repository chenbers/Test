Login Test Cases for TF391

Meta:
@page login
@testFolder TF391

Narrative: Test the admin users page

Scenario: Check all the edit columns links to make sure they are present for all the tests in this suite
Given I am logged in
When I click the Admin link
And I click the Admin Users link
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I check the 4th Row of the Column checkbox
And I check the 5th Row of the Column checkbox
And I check the 6th Row of the Column checkbox
And I check the 7th Row of the Column checkbox
And I check the 8th Row of the Column checkbox
And I check the 9th Row of the Column checkbox
And I check the 10th Row of the Column checkbox
And I check the 11th Row of the Column checkbox
And I check the 12th Row of the Column checkbox
And I check the 13th Row of the Column checkbox
And I check the 14th Row of the Column checkbox
And I check the 15th Row of the Column checkbox
And I check the 16th Row of the Column checkbox
And I check the 17th Row of the Column checkbox
And I check the 18th Row of the Column checkbox
And I check the 19th Row of the Column checkbox
And I check the 20th Row of the Column checkbox
And I check the 21st Row of the Column checkbox
And I check the 22nd Row of the Column checkbox
And I check the 23rd Row of the Column checkbox
And I check the 24th Row of the Column checkbox
And I check the 25th Row of the Column checkbox
And I check the 26th Row of the Column checkbox
And I check the 27th Row of the Column checkbox
And I check the 28th Row of the Column checkbox
And I check the 29th Row of the Column checkbox
And I check the 30th Row of the Column checkbox
And I check the 31st Row of the Column checkbox
And I check the 32nd Row of the Column checkbox
And I check the 33rd Row of the Column checkbox
And I check the 34th Row of the Column checkbox
And I check the 35th Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes

Scenario: TC14033: Admin - UI
Given I am logged in
When I click the Admin link
And I click the Admin Users link
Then I validate I am on the Admin Users page
And I validate the Title text is present
And I validate the Admin Users link is present
And I validate the Admin Add User link is present
And I validate the Admin Vehicles link is present
And I validate the Admin Devices link is present
And I validate the Admin Zones link is present
And I validate the Admin Road Hazards link is present
And I validate the Admin Red Flags link is present
And I validate the Admin Reports link is present
And I validate the Admin Organization link is present
And I validate the Admin Custom Roles link is present
And I validate the Admin Speed By Street link is present
And I validate the Admin Account link is present
And I validate the Delete button is present
And I validate the Batch Edit button is present
And I validate the Edit Columns link is present
And I validate the Records text is present
And I validate the Check All checkbox is present
And I validate the Sort By Name link is present
And I validate the User Status Header text is present
And I validate the Sort By User Name link is present
And I validate the User Group Header text is present
And I validate the Roles Header text is present
And I validate the Sort By Phone One link is present
And I validate the Sort By Phone Two link is present
And I validate the Sort By Email One link is present
And I validate the Sort By Email Two link is present
And I validate the Sort By Text Message One link is present
And I validate the Sort By Text Message Two link is present
And I validate the Information Alerts Header text is present
And I validate the Warning Alerts Header text is present
And I validate the Critical Alerts Header text is present
And I validate the Sort By Time Zone link is present
And I validate the Sort By Employee ID link is present
And I validate the Sort By Reports To link is present
And I validate the Sort By Title link is present
And I validate the Sort By DOB link is present
And I validate the Sort By Gender link is present
And I validate the Sort By Bar Code link is present
And I validate the Sort By RFID One link is present
And I validate the Sort By RFID Two link is present
And I validate the Sort By One Wire ID link is present
And I validate the Sort By Locale link is present
And I validate the Measurement Type Header text is present
And I validate the Fuel Efficiency Ratio Header text is present
And I validate the Driver Status Header text is present
And I validate the Sort By Driver License Number link is present
And I validate the Sort By License Class link is present
And I validate the Sort By License State link is present
And I validate the Sort By License Expiration link is present
And I validate the Sort By Certifications link is present
And I validate the DOT Header text is present
And I validate the Driver Team Header text is present
And I validate the Admin Add Vehicle link is not present
And I validate the Admin Add Hazard link is not present
And I validate the Admin Route Hazards link is not present
And I validate the Admin Add Red Flag link is not present
And I validate the Admin Add Report link is not present
And I validate the Admin Add Custom Role link is not present

Scenario: Admin - User Details UI
Given I am logged in
When I click the Admin link
And I click the Admin Users link
Then I validate I am on the Admin Users page
And I click the 1st Row of the Name link
And I validate I am on the Admin User Details page
And I validate the Title text is present
And I validate the Back To Users link is present
And I validate the Delete button is present
And I validate the Edit button is present
And I validate the User Information Header text is present
And I validate the First Name Header text is present
And I validate the Middle Name Header text is present
And I validate the Last Name Header text is present
And I validate the Suffix Header text is present
And I validate the DOB Header text is present
And I validate the Gender Header text is present
And I validate the Driver Information Header text is present
And I validate the Driver License Number Header text is present
And I validate the Class Header text is present
And I validate the State Header text is present
And I validate the Expiration Header text is present
And I validate the Certifications Header text is present
And I validate the DOT Header text is present
And I validate the Team Header text is present
And I validate the Driver Status Header text is present
And I validate the Driver Identification Header text is present
And I validate the RFID Bar Code Header text is present
And I validate the RFID One Header text is present
And I validate the RFID Two Header text is present
And I validate the One Wire ID Header text is present
And I validate the Employee Information Header text is present
And I validate the Employee ID Header text is present
And I validate the Reports To Header text is present
And I validate the Title Header text is present
And I validate the Locale Header text is present
And I validate the Time Zone Header text is present
And I validate the Measurement Header text is present
And I validate the Fuel Efficiency Ratio Header text is present
And I validate the User Name Header text is present
And I validate the Group Header text is present
And I validate the Roles Header text is present
And I validate the User Status Header text is present
And I validate the Notifications Header text is present
And I validate the Email One Header text is present
And I validate the Email Two Header text is present
And I validate the Text Message One Header text is present
And I validate the Text Message Two Header text is present
And I validate the Phone One Header text is present
And I validate the Phone Two Header text is present
And I validate the Information Header text is present
And I validate the Warning Header text is present
And I validate the Critical Header text is present
And I validate the Admin Users link is present
And I validate the Admin Add User link is present
And I validate the Admin Vehicles link is present
And I validate the Admin Devices link is present
And I validate the Admin Zones link is present
And I validate the Admin Road Hazards link is present
And I validate the Admin Red Flags link is present
And I validate the Admin Reports link is present
And I validate the Admin Organization link is present
And I validate the Admin Custom Roles link is present
And I validate the Admin Speed By Street link is present
And I validate the Admin Account link is present

Scenario: Admin - Add Edit User UI
Given I am logged in
When I click the Admin link
And I click the Admin Users link
Then I validate I am on the Admin Users page
And I click the 1st Row of the Edit link
And I validate I am on the Admin Add Edit User page
And I validate the Title text is present
And I validate the Save Top button is present
And I validate the Cancel Top button is present
And I validate the Save Bottom button is present
And I validate the Cancel Bottom button is present
And I validate the User Information Header text is present
And I validate the First Name Header text is present
And I validate the First Name field is present
And I validate the Middle Name Header text is present
And I validate the Middle Name field is present
And I validate the Last Name Header text is present
And I validate the Last Name field is present
And I validate the Suffix Header text is present
And I validate the Suffix dropdown is present
And I validate the DOB Header text is present
And I validate the DOB field is present
And I validate the Gender Header text is present
And I validate the Gender dropdown is present
And I validate the Driver Information Header text is present
And I validate the Driver Information checkbox is present
And I validate the Driver License Number Header text is present
And I validate the Driver License Number field is present
And I validate the Class Header text is present
And I validate the Class field is present
And I validate the State Header text is present
And I validate the State dropdown is present
And I validate the Expiration Header text is present
And I validate the Expiration field is present
And I validate the Certifications Header text is present
And I validate the Certifications field is present
And I validate the DOT Header text is present
And I validate the DOT dropdown is present
And I validate the Team Header text is present
And I validate the Team dropdown is present
And I validate the Driver Status Header text is present
And I validate the Driver Status dropdown is present
And I validate the Driver Identification Header text is present
And I validate the RFID Bar Code Header text is present
And I validate the RFID Bar Code field is present
And I validate the RFID One Header text is present
And I validate the RFID One text is present
And I validate the RFID Two Header text is present
And I validate the RFID Two text is present
And I validate the One Wire ID Header text is present
And I validate the One Wire ID field is present
And I validate the Employee Information Header text is present
And I validate the Employee ID Header text is present
And I validate the Employee ID field is present
And I validate the Reports To Header text is present
And I validate the Reports To field is present
And I validate the Title Header text is present
And I validate the Title field is present
And I validate the Locale Header text is present
And I validate the Locale dropdown is present
And I validate the Time Zone Header text is present
And I validate the Time Zone dropdown is present
And I validate the Measurement Header text is present
And I validate the Measurement dropdown is present
And I validate the Fuel Efficiency Ratio Header text is present
And I validate the Fuel Efficiency Ratio dropdown is present
And I validate the User Name Header text is present
And I validate the Group Header text is present
And I validate the Roles Header text is present
And I validate the User Status Header text is present
And I validate the Notifications Header text is present
And I validate the Email One Header text is present
And I validate the Email Two Header text is present
And I validate the Text Message One Header text is present
And I validate the Text Message Two Header text is present
And I validate the Phone One Header text is present
And I validate the Phone Two Header text is present
And I validate the Information Header text is present
And I validate the Warning Header text is present
And I validate the Critical Header text is present
And I validate the Admin Users link is present
And I validate the Admin Add User link is present
And I validate the Admin Vehicles link is present
And I validate the Admin Devices link is present
And I validate the Admin Zones link is present
And I validate the Admin Road Hazards link is present
And I validate the Admin Red Flags link is present
And I validate the Admin Reports link is present
And I validate the Admin Organization link is present
And I validate the Admin Custom Roles link is present
And I validate the Admin Speed By Street link is present
And I validate the Admin Account link is present

Scenario: Admin - Edit Columns UI
Given I am logged in
When I click the Admin link
And I click the Admin Users link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the 1st Row of the Column checkbox is present
And I validate the 2nd Row of the Column checkbox is present
And I validate the 3rd Row of the Column checkbox is present
And I validate the 4th Row of the Column checkbox is present
And I validate the 5th Row of the Column checkbox is present
And I validate the 6th Row of the Column checkbox is present
And I validate the 7th Row of the Column checkbox is present
And I validate the 8th Row of the Column checkbox is present
And I validate the 9th Row of the Column checkbox is present
And I validate the 10th Row of the Column checkbox is present
And I validate the 11th Row of the Column checkbox is present
And I validate the 12th Row of the Column checkbox is present
And I validate the 13th Row of the Column checkbox is present
And I validate the 14th Row of the Column checkbox is present
And I validate the 15th Row of the Column checkbox is present
And I validate the 16th Row of the Column checkbox is present
And I validate the 17th Row of the Column checkbox is present
And I validate the 18th Row of the Column checkbox is present
And I validate the 19th Row of the Column checkbox is present
And I validate the 20th Row of the Column checkbox is present
And I validate the 21st Row of the Column checkbox is present
And I validate the 22nd Row of the Column checkbox is present
And I validate the 23rd Row of the Column checkbox is present
And I validate the 24th Row of the Column checkbox is present
And I validate the 25th Row of the Column checkbox is present
And I validate the 26th Row of the Column checkbox is present
And I validate the 27th Row of the Column checkbox is present
And I validate the 28th Row of the Column checkbox is present
And I validate the 29th Row of the Column checkbox is present
And I validate the 30th Row of the Column checkbox is present
And I validate the 31st Row of the Column checkbox is present
And I validate the 32nd Row of the Column checkbox is present
And I validate the 33rd Row of the Column checkbox is present
And I validate the 34th Row of the Column checkbox is present
And I validate the 35th Row of the Column checkbox is present
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 5th Row of the Column checkbox
And I uncheck the 6th Row of the Column checkbox
And I uncheck the 7th Row of the Column checkbox
And I uncheck the 8th Row of the Column checkbox
And I uncheck the 9th Row of the Column checkbox
And I uncheck the 10th Row of the Column checkbox
And I uncheck the 11th Row of the Column checkbox
And I uncheck the 12th Row of the Column checkbox
And I uncheck the 13th Row of the Column checkbox
And I uncheck the 14th Row of the Column checkbox
And I uncheck the 15th Row of the Column checkbox
And I uncheck the 16th Row of the Column checkbox
And I uncheck the 17th Row of the Column checkbox
And I uncheck the 18th Row of the Column checkbox
And I uncheck the 19th Row of the Column checkbox
And I uncheck the 20th Row of the Column checkbox
And I uncheck the 21st Row of the Column checkbox
And I uncheck the 22nd Row of the Column checkbox
And I uncheck the 23rd Row of the Column checkbox
And I uncheck the 24th Row of the Column checkbox
And I uncheck the 25th Row of the Column checkbox
And I uncheck the 26th Row of the Column checkbox
And I uncheck the 27th Row of the Column checkbox
And I uncheck the 28th Row of the Column checkbox
And I uncheck the 29th Row of the Column checkbox
And I uncheck the 30th Row of the Column checkbox
And I uncheck the 31st Row of the Column checkbox
And I uncheck the 32nd Row of the Column checkbox
And I uncheck the 33rd Row of the Column checkbox
And I uncheck the 34th Row of the Column checkbox
And I uncheck the 35th Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is not checked
And I validate the 2nd Row of the Column checkbox is not checked
And I validate the 3rd Row of the Column checkbox is not checked
And I validate the 4th Row of the Column checkbox is not checked
And I validate the 5th Row of the Column checkbox is not checked
And I validate the 6th Row of the Column checkbox is not checked
And I validate the 7th Row of the Column checkbox is not checked
And I validate the 8th Row of the Column checkbox is not checked
And I validate the 9th Row of the Column checkbox is not checked
And I validate the 10th Row of the Column checkbox is not checked
And I validate the 11th Row of the Column checkbox is not checked
And I validate the 12th Row of the Column checkbox is not checked
And I validate the 13th Row of the Column checkbox is not checked
And I validate the 14th Row of the Column checkbox is not checked
And I validate the 15th Row of the Column checkbox is not checked
And I validate the 16th Row of the Column checkbox is not checked
And I validate the 17th Row of the Column checkbox is not checked
And I validate the 18th Row of the Column checkbox is not checked
And I validate the 19th Row of the Column checkbox is not checked
And I validate the 20th Row of the Column checkbox is not checked
And I validate the 21st Row of the Column checkbox is not checked
And I validate the 22nd Row of the Column checkbox is not checked
And I validate the 23rd Row of the Column checkbox is not checked
And I validate the 24th Row of the Column checkbox is not checked
And I validate the 25th Row of the Column checkbox is not checked
And I validate the 26th Row of the Column checkbox is not checked
And I validate the 27th Row of the Column checkbox is not checked
And I validate the 28th Row of the Column checkbox is not checked
And I validate the 29th Row of the Column checkbox is not checked
And I validate the 30th Row of the Column checkbox is not checked
And I validate the 31st Row of the Column checkbox is not checked
And I validate the 32nd Row of the Column checkbox is not checked
And I validate the 33rd Row of the Column checkbox is not checked
And I validate the 34th Row of the Column checkbox is not checked
And I validate the 35th Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I check the 4th Row of the Column checkbox
And I check the 5th Row of the Column checkbox
And I check the 6th Row of the Column checkbox
And I check the 7th Row of the Column checkbox
And I check the 8th Row of the Column checkbox
And I check the 9th Row of the Column checkbox
And I check the 10th Row of the Column checkbox
And I check the 11th Row of the Column checkbox
And I check the 12th Row of the Column checkbox
And I check the 13th Row of the Column checkbox
And I check the 14th Row of the Column checkbox
And I check the 15th Row of the Column checkbox
And I check the 16th Row of the Column checkbox
And I check the 17th Row of the Column checkbox
And I check the 18th Row of the Column checkbox
And I check the 19th Row of the Column checkbox
And I check the 20th Row of the Column checkbox
And I check the 21st Row of the Column checkbox
And I check the 22nd Row of the Column checkbox
And I check the 23rd Row of the Column checkbox
And I check the 24th Row of the Column checkbox
And I check the 25th Row of the Column checkbox
And I check the 26th Row of the Column checkbox
And I check the 27th Row of the Column checkbox
And I check the 28th Row of the Column checkbox
And I check the 29th Row of the Column checkbox
And I check the 30th Row of the Column checkbox
And I check the 31st Row of the Column checkbox
And I check the 32nd Row of the Column checkbox
And I check the 33rd Row of the Column checkbox
And I check the 34th Row of the Column checkbox
And I check the 35th Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I validate the 2nd Row of the Column checkbox is checked
And I validate the 3rd Row of the Column checkbox is checked
And I validate the 4th Row of the Column checkbox is checked
And I validate the 5th Row of the Column checkbox is checked
And I validate the 6th Row of the Column checkbox is checked
And I validate the 7th Row of the Column checkbox is checked
And I validate the 8th Row of the Column checkbox is checked
And I validate the 9th Row of the Column checkbox is checked
And I validate the 10th Row of the Column checkbox is checked
And I validate the 11th Row of the Column checkbox is checked
And I validate the 12th Row of the Column checkbox is checked
And I validate the 13th Row of the Column checkbox is checked
And I validate the 14th Row of the Column checkbox is checked
And I validate the 15th Row of the Column checkbox is checked
And I validate the 16th Row of the Column checkbox is checked
And I validate the 17th Row of the Column checkbox is checked
And I validate the 18th Row of the Column checkbox is checked
And I validate the 19th Row of the Column checkbox is checked
And I validate the 20th Row of the Column checkbox is checked
And I validate the 21st Row of the Column checkbox is checked
And I validate the 22nd Row of the Column checkbox is checked
And I validate the 23rd Row of the Column checkbox is checked
And I validate the 24th Row of the Column checkbox is checked
And I validate the 25th Row of the Column checkbox is checked
And I validate the 26th Row of the Column checkbox is checked
And I validate the 27th Row of the Column checkbox is checked
And I validate the 28th Row of the Column checkbox is checked
And I validate the 29th Row of the Column checkbox is checked
And I validate the 30th Row of the Column checkbox is checked
And I validate the 31st Row of the Column checkbox is checked
And I validate the 32nd Row of the Column checkbox is checked
And I validate the 33rd Row of the Column checkbox is checked
And I validate the 34th Row of the Column checkbox is checked
And I validate the 35th Row of the Column checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present

Scenario: Admin - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Admin link
And I click the Admin Users link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 5th Row of the Column checkbox
And I uncheck the 6th Row of the Column checkbox
And I uncheck the 7th Row of the Column checkbox
And I uncheck the 8th Row of the Column checkbox
And I uncheck the 9th Row of the Column checkbox
And I uncheck the 10th Row of the Column checkbox
And I uncheck the 11th Row of the Column checkbox
And I uncheck the 12th Row of the Column checkbox
And I uncheck the 13th Row of the Column checkbox
And I uncheck the 14th Row of the Column checkbox
And I uncheck the 15th Row of the Column checkbox
And I uncheck the 16th Row of the Column checkbox
And I uncheck the 17th Row of the Column checkbox
And I uncheck the 18th Row of the Column checkbox
And I uncheck the 19th Row of the Column checkbox
And I uncheck the 20th Row of the Column checkbox
And I uncheck the 21st Row of the Column checkbox
And I uncheck the 22nd Row of the Column checkbox
And I uncheck the 23rd Row of the Column checkbox
And I uncheck the 24th Row of the Column checkbox
And I uncheck the 25th Row of the Column checkbox
And I uncheck the 26th Row of the Column checkbox
And I uncheck the 27th Row of the Column checkbox
And I uncheck the 28th Row of the Column checkbox
And I uncheck the 29th Row of the Column checkbox
And I uncheck the 30th Row of the Column checkbox
And I uncheck the 31st Row of the Column checkbox
And I uncheck the 32nd Row of the Column checkbox
And I uncheck the 33rd Row of the Column checkbox
And I uncheck the 34th Row of the Column checkbox
And I uncheck the 35th Row of the Column checkbox
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Name link is present
And I validate the User Status Header text is present
And I validate the Sort By User Name link is present
And I validate the User Group Header text is present
And I validate the Roles Header text is present
And I validate the Sort By Phone One link is present
And I validate the Sort By Phone Two link is present
And I validate the Sort By Email One link is present
And I validate the Sort By Email Two link is present
And I validate the Sort By Text Message One link is present
And I validate the Sort By Text Message Two link is present
And I validate the Information Alerts Header text is present
And I validate the Warning Alerts Header text is present
And I validate the Critical Alerts Header text is present
And I validate the Sort By Time Zone link is present
And I validate the Sort By Employee ID link is present
And I validate the Sort By Reports To link is present
And I validate the Sort By Title link is present
And I validate the Sort By DOB link is present
And I validate the Sort By Gender link is present
And I validate the Sort By Bar Code link is present
And I validate the Sort By RFID One link is present
And I validate the Sort By RFID Two link is present
And I validate the Sort By One Wire ID link is present
And I validate the Sort By Locale link is present
And I validate the Measurement Type Header text is present
And I validate the Fuel Efficiency Ratio Header text is present
And I validate the Driver Status Header text is present
And I validate the Sort By Driver License Number link is present
And I validate the Sort By License Class link is present
And I validate the Sort By License State link is present
And I validate the Sort By License Expiration link is present
And I validate the Sort By Certifications link is present
And I validate the DOT Header text is present
And I validate the Driver Team Header text is present

Scenario: Admin - Edit Columns - Save Button (Changes)
Given I am logged in
When I click the Admin link
And I click the Admin Users link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 5th Row of the Column checkbox
And I uncheck the 6th Row of the Column checkbox
And I uncheck the 7th Row of the Column checkbox
And I uncheck the 8th Row of the Column checkbox
And I uncheck the 9th Row of the Column checkbox
And I uncheck the 10th Row of the Column checkbox
And I uncheck the 11th Row of the Column checkbox
And I uncheck the 12th Row of the Column checkbox
And I uncheck the 13th Row of the Column checkbox
And I uncheck the 14th Row of the Column checkbox
And I uncheck the 15th Row of the Column checkbox
And I uncheck the 16th Row of the Column checkbox
And I uncheck the 17th Row of the Column checkbox
And I uncheck the 18th Row of the Column checkbox
And I uncheck the 19th Row of the Column checkbox
And I uncheck the 20th Row of the Column checkbox
And I uncheck the 21st Row of the Column checkbox
And I uncheck the 22nd Row of the Column checkbox
And I uncheck the 23rd Row of the Column checkbox
And I uncheck the 24th Row of the Column checkbox
And I uncheck the 25th Row of the Column checkbox
And I uncheck the 26th Row of the Column checkbox
And I uncheck the 27th Row of the Column checkbox
And I uncheck the 28th Row of the Column checkbox
And I uncheck the 29th Row of the Column checkbox
And I uncheck the 30th Row of the Column checkbox
And I uncheck the 31st Row of the Column checkbox
And I uncheck the 32nd Row of the Column checkbox
And I uncheck the 33rd Row of the Column checkbox
And I uncheck the 34th Row of the Column checkbox
And I uncheck the 35th Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Name link is not present
And I validate the User Status Header text is not present
And I validate the Sort By User Name link is not present
And I validate the User Group Header text is not present
And I validate the Roles Header text is not present
And I validate the Sort By Phone One link is not present
And I validate the Sort By Phone Two link is not present
And I validate the Sort By Email One link is not present
And I validate the Sort By Email Two link is not present
And I validate the Sort By Text Message One link is not present
And I validate the Sort By Text Message Two link is not present
And I validate the Information Alerts Header text is not present
And I validate the Warning Alerts Header text is not present
And I validate the Critical Alerts Header text is not present
And I validate the Sort By Time Zone link is not present
And I validate the Sort By Employee ID link is not present
And I validate the Sort By Reports To link is not present
And I validate the Sort By Title link is not present
And I validate the Sort By DOB link is not present
And I validate the Sort By Gender link is not present
And I validate the Sort By Bar Code link is not present
And I validate the Sort By RFID One link is not present
And I validate the Sort By RFID Two link is not present
And I validate the Sort By One Wire ID link is not present
And I validate the Sort By Locale link is not present
And I validate the Measurement Type Header text is not present
And I validate the Fuel Efficiency Ratio Header text is not present
And I validate the Driver Status Header text is not present
And I validate the Sort By Driver License Number link is not present
And I validate the Sort By License Class link is not present
And I validate the Sort By License State link is not present
And I validate the Sort By License Expiration link is not present
And I validate the Sort By Certifications link is not present
And I validate the DOT Header text is not present
And I validate the Driver Team Header text is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I check the 4th Row of the Column checkbox
And I check the 5th Row of the Column checkbox
And I check the 6th Row of the Column checkbox
And I check the 7th Row of the Column checkbox
And I check the 8th Row of the Column checkbox
And I check the 9th Row of the Column checkbox
And I check the 10th Row of the Column checkbox
And I check the 11th Row of the Column checkbox
And I check the 12th Row of the Column checkbox
And I check the 13th Row of the Column checkbox
And I check the 14th Row of the Column checkbox
And I check the 15th Row of the Column checkbox
And I check the 16th Row of the Column checkbox
And I check the 17th Row of the Column checkbox
And I check the 18th Row of the Column checkbox
And I check the 19th Row of the Column checkbox
And I check the 20th Row of the Column checkbox
And I check the 21st Row of the Column checkbox
And I check the 22nd Row of the Column checkbox
And I check the 23rd Row of the Column checkbox
And I check the 24th Row of the Column checkbox
And I check the 25th Row of the Column checkbox
And I check the 26th Row of the Column checkbox
And I check the 27th Row of the Column checkbox
And I check the 28th Row of the Column checkbox
And I check the 29th Row of the Column checkbox
And I check the 30th Row of the Column checkbox
And I check the 31st Row of the Column checkbox
And I check the 32nd Row of the Column checkbox
And I check the 33rd Row of the Column checkbox
And I check the 34th Row of the Column checkbox
And I check the 35th Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Name link is present
And I validate the User Status Header text is present
And I validate the Sort By User Name link is present
And I validate the User Group Header text is present
And I validate the Roles Header text is present
And I validate the Sort By Phone One link is present
And I validate the Sort By Phone Two link is present
And I validate the Sort By Email One link is present
And I validate the Sort By Email Two link is present
And I validate the Sort By Text Message One link is present
And I validate the Sort By Text Message Two link is present
And I validate the Information Alerts Header text is present
And I validate the Warning Alerts Header text is present
And I validate the Critical Alerts Header text is present
And I validate the Sort By Time Zone link is present
And I validate the Sort By Employee ID link is present
And I validate the Sort By Reports To link is present
And I validate the Sort By Title link is present
And I validate the Sort By DOB link is present
And I validate the Sort By Gender link is present
And I validate the Sort By Bar Code link is present
And I validate the Sort By RFID One link is present
And I validate the Sort By RFID Two link is present
And I validate the Sort By One Wire ID link is present
And I validate the Sort By Locale link is present
And I validate the Measurement Type Header text is present
And I validate the Fuel Efficiency Ratio Header text is present
And I validate the Driver Status Header text is present
And I validate the Sort By Driver License Number link is present
And I validate the Sort By License Class link is present
And I validate the Sort By License State link is present
And I validate the Sort By License Expiration link is present
And I validate the Sort By Certifications link is present
And I validate the DOT Header text is present
And I validate the Driver Team Header text is present

Scenario: Admin - Add User with all fields filled out and then navigate to all pages where it's used to make sure the info is correct.
Given I am logged in
When I click the Admin link
And I click the Admin Add User link
And I type "Add" into the First Name field
And I type "All" into the Middle Name field
And I type "Fields" into the Last Name field
And I select "Jr." from the Suffix dropdown
And I type "Jan 1, 1980" into the DOB field
And I select "Male" from the Gender dropdown
Then I validate the Driver Information checkbox is checked
And I type "AllFieldsDriversLi1" into the Driver License Number field
And I type "A" into the Class field
And I select "Utah" from the Driver State dropdown
And I type "Jan 1, 2015" into the Expiration field
And I type "Test Certification" into the Certifications field
And I select "Texas" from the DOT dropdown
And I select "Top - Automation Test Team" from the Team dropdown
And I select "Top - Automation Test Team" from the Group dropdown
And I select "Active" from the Driver Status dropdown
And I type "" into the RFID Bar Code field
And I type "2" into the One Wire ID field
And I type "ADDALLFIEL" into the Employee ID field
And I type "The Man" into the Reports To field
And I type "Runt" into the Title field
And I validate the Locale dropdown is "English (United States)"
And I select "US/Mountain (GMT-7:0)" from the Time Zone dropdown
And I validate the Measurement dropdown is "English"
And I validate the Fuel Efficiency Ratio dropdown is "Miles Per Gallon (US)"
And I validate the Login Information checkbox is checked
And I type "AddAllFieldsUser" into the User Name field
And I type "password" into the Password field
And I type "password" into the Password Again field
And I select "Active" from the User Status dropdown
And I type "fakeemail@fakeemailadd.com" into the Email One field
And I type "fakeemail2@fakeemailadd.com" into the Email Two field
And I type "1111111111@fakeemailadd.com" into the Text Message One field
And I type "2222222222@fakeemailadd.com" into the Text Message Two field
And I type "1111111111" into the Phone One field
And I type "2222222222" into the Phone Two field
And I validate the Information dropdown is "None"
And I validate the Warning dropdown is "None"
And I validate the Critical dropdown is "None"
And I click the Save Bottom button
And I validate I am on the Admin User Details Page
And I validate the First Name text is "Add"
And I validate the Middle Name text is "All"
And I validate the Last Name text is "Fields"
And I validate the Suffix text is "Jr."
And I validate the DOB text is "Jan 1, 1980"
And I validate the Gender text is "Male"
And I validate the Driver License Number text is "AllFieldsDriversLi1"
And I validate the Class text is "A"
And I validate the State text is "Utah"
And I validate the Expiration text is "Jan 1, 2015"
And I validate the Certifications text is "Test Certification"
And I validate the DOT text is "Texas"
And I validate the Team text is "Top - Automation Test Team"
And I validate the Driver Status text is "Active"
And I validate the RFID Bar Code text is ""
And I validate the RFID One text is ""
And I validate the RFID Two text is ""
And I validate the One Wire ID text is "2"
And I validate the Employee ID text is "ADDALLFIEL"
And I validate the Reports To text is "The Man"
And I validate the Title text is "Runt"
And I validate the Locale text is "English (United States)"
And I validate the Time Zone text is "US/Mountain (GMT-7:0)"
And I validate the Measurement text is "English"
And I validate the Fuel Efficiency Ratio text is "Miles Per Gallon (US)"
And I validate the User Name text is "AddAllFieldsUser"
And I validate the Group text is "Top - Automation Test Team"
And I validate the Roles text is "Normal"
And I validate the User Status text is "Active"
And I validate the Email One link is "fakeemail@fakeemailadd.com"
And I validate the Email Two link is "fakeemail2@fakeemailadd.com"
And I validate the Text Message One text is "1111111111@fakeemailadd.com"
And I validate the Text Message Two text is ""2222222222@fakeemailadd.com"
And I validate the Phone One text is "1111111111"
And I validate the Phone Two text is "2222222222"
And I validate the Information text is "None"
And I validate the Warning text is "None"
And I validate the Critical text is "None"
And I click the Edit button
And I validate I am on the Admin Add Edit User page
And I validate the First Name field is "Add"
And I validate the Middle Name field is "All"
And I validate the Last Name field is "Fields"
And I validate the Suffix dropdown is "Jr."
And I validate the DOB field is "Jan 1, 1980"
And I validate the Gender dropdown is "Male"
And I validate the Driver License Number field is "AllFieldsDriversLi1"
And I validate the Class field is "A"
And I validate the State dropdown is "Utah"
And I validate the Expiration field is "Jan 1, 2015"
And I validate the Certifications field is "Test Certification"
And I validate the DOT dropdown is "Texas"
And I validate the Team dropdown is "Top - Automation Test Team"
And I validate the Driver Status dropdown is "Active"
And I validate the RFID Bar Code field is ""
And I validate the RFID One text is ""
And I validate the RFID Two text is ""
And I validate the One Wire ID field is "2"
And I validate the Employee ID field is "ADDALLFIEL"
And I validate the Reports To field is "The Man"
And I validate the Title field is "Runt"
And I validate the Locale dropdown is "English (United States)"
And I validate the Time Zone dropdown is "US/Mountain (GMT-7:0)"
And I validate the Measurement dropdown is "English"
And I validate the Fuel Efficiency Ratio dropdown is "Miles Per Gallon (US)"
And I validate the User Name field is "AddAllFieldsUser"
And I validate the Password field is not ""
And I validate the Password Again field is not ""
And I validate the Group dropdown is "Top - Automation Test Team"
And I validate the User Status dropdown is "Active"
And I validate the Email One field is "fakeemail@fakeemailadd.com"
And I validate the Email Two field is "fakeemail2@fakeemailadd.com"
And I validate the Text Message One field is "1111111111@fakeemailadd.com"
And I validate the Text Message Two field is ""2222222222@fakeemailadd.com"
And I validate the Phone One field is "1111111111"
And I validate the Phone Two field is "2222222222"
And I validate the Information dropdown is "None"
And I validate the Warning dropdown is "None"
And I validate the Critical dropdown is "None"
And I click the Cancel Top button
And I click the Back To Users link
And I validate I am on the Admin Users page
And I type "Add All Fields Jr." into the Name textfield
And I validate the 1st Row of the Name link is "Add All Fields Jr."
And I validate the 1st Row of the User Status text is "Active"
And I validate the 1st Row of the User Name text is "AddAllFieldsUser"
And I validate the 1st Row of the User Group text is "Automation Test Team"
And I validate the 1st Row of the Roles text is "Normal"
And I validate the 1st Row of the Phone One text is "1111111111"
And I validate the 1st Row of the Phone Two text is "2222222222"
And I validate the 1st Row of the Email One link is "fakeemail@fakeemailadd.com"
And I validate the 1st Row of the Email Two link is "fakeemail2@fakeemailadd.com"
And I validate the 1st Row of the Text Message One text is "1111111111@fakeemailadd.com"
And I validate the 1st Row of the Text Message Two text is ""2222222222@fakeemailadd.com"
And I validate the 1st Row of the Information Alerts text is "None"
And I validate the 1st Row of the Warning Alerts text is "None"
And I validate the 1st Row of the Critical Alerts text is "None"
And I validate the 1st Row of the Time Zone text is "US/Mountain (GMT-7:0)"
And I validate the 1st Row of the Employee ID text is "ADDALLFIEL"
And I validate the 1st Row of the Reports To text is "The Man"
And I validate the 1st Row of the Title text is "Runt"
And I validate the 1st Row of the DOB text is "Jan 1, 1980"
And I validate the 1st Row of the Gender text is "Male"
And I validate the 1st Row of the Bar Code text is ""
And I validate the 1st Row of the RFID One text is ""
And I validate the 1st Row of the RFID Two text is ""
And I validate the 1st Row of the One Wire ID text is "2"
And I validate the 1st Row of the Locale text is "English (United States)"
And I validate the 1st Row of the Measurement Type text is "English"
And I validate the 1st Row of the Fuel Efficiency Ratio text is "Miles Per Gallon (US)"
And I validate the 1st Row of the Driver Status text is "Active"
And I validate the 1st Row of the Driver License Number text is "AllFieldsDriversLi1"
And I validate the 1st Row of the License Class text is "A"
And I validate the 1st Row of the License State text is "Utah"
And I validate the 1st Row of the License Expiration text is "Jan 1, 2015"
And I validate the 1st Row of the Certifications text is "Test Certification"
And I validate the 1st Row of the DOT text is "Texas"
And I validate the 1st Row of the Driver Team text is "Automation Test Team"
And I check the 1st Row of the Check checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes
And I type "Add All Fields Jr." into the Name textfield
And I validate the 1st Row of the Name link is not "Add All Fields Jr."