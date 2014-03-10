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

Scenario: Admin - Add User with all fields filled out (first half of this test works, I'm working on the other half in the currentstory file)
Given I navigate to localhost
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


