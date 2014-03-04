Login Test Cases for TF391

Meta:
@page login
@testFolder TF391

Narrative: Test the admin users page

Scenario: TC14033: Admin - UI
Given I am logged in
When I click the Admin link
And I click the Users link
Then I validate I am on the Admin Users page
And I validate the Title text is present
And I validate the Users link is present
And I validate the Add User link is present
And I validate the Vehicles link is present
And I validate the Devices link is present
And I validate the Zones link is present
And I validate the Road Hazards link is present
And I validate the Red Flags link is present
And I validate the Reports link is present
And I validate the Organization link is present
And I validate the Custom Roles link is present
And I validate the Speed By Street link is present
And I validate the Account link is present
And I validate the Delete button is present
And I validate the Batch Edit button is present
And I validate the Edit Columns link is present
And I validate the Records text is present
And I validate the Select All checkbox is present
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
And I validate the Driver Name Header text is present
And I validate the Add Vehicle link is not present
And I validate the Add Hazard link is not present
And I validate the Route Hazards link is not present
And I validate the Add Red Flag link is not present
And I validate the Add Report link is not present
And I validate the Add Custom Role link is not present

Scenario: Admin - Edit Columns UI
Given I am logged in
When I click the Admin link
And I click the Users link
And I click the Edit Columns link
And the Edit Columns Popup opens
