Test Cases for TF388/TF416/TF547 

Meta:
@page login
@testFolder TF388
@testFolder TF416
@testFolder TF547

Narrative:

Scenario: TC1540: Reports - Drivers - Bookmark Entry 
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I type "" into the Search textfield
And I click the Search button
And I bookmark the page
And I click log out
And I click the bookmark I just added
And I am on the login page
And I type a valid user name into the Username textfield
And I type a valid password into the Password textfield
And I click the Login button
Then I validate I am on the Drivers page
And I validate the Search textfield is ""
And I validate the Driver Report table is not ""

Scenario: TC1543: Reports - Drivers - Driver Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I save the first entry in the Sort By Driver column as SavedEntry
And I click the first entry in the Sort By Driver column
Then I validate the Driver Performance text is SavedEntry

Scenario: TC1545: Reports - Drivers - Driving Style Score Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I save the first entry in the Sort By Driver column as SavedDriver
And I save the first entry in the Sort By Style column as SavedStyle
And I click the first entry in the Sort By Style column
And I click the 12Months link
Then I validate the Driving Style Overall text is SavedStyle
And I validate the Driver Performance text is SavedDriver

Scenario: TC1550: Reports - Drivers - Group Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I save the first entry in the Sort By Group column as SavedEntry
And I click the first entry in the Sort By Group column
Then I validate the Group Performance text is SavedEntry

Scenario: TC1553: Reports - Drivers - Overall Score Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I save the first entry in the Sort By Driver column as SavedDriver
And I save the first entry in the Sort By Overall column as SavedOverall
And I click the first entry in the Sort By Overall column
And I click the 12Months link
Then I validate the Driving Style Overall text is SavedOverall
And I validate the Driver Performance text is SavedDriver

Scenario: TC1562: Reports - Drivers - Seat Belt Score Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I save the first entry in the Sort By Driver column as SavedDriver
And I save the first entry in the Sort By Seatbelt column as SavedSeatbelt
And I click the first entry in the Sort By Seatbelt column
And I click the 12Months link
Then I validate the Driving Style Seatbelt text is SavedSeatbelt
And I validate the Driver Performance text is SavedDriver

Scenario: TC1564: Reports - Drivers - Speed Score Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I save the first entry in the Sort By Driver column as SavedDriver
And I save the first entry in the Sort By Speed column as SavedSpeed
And I click the first entry in the Sort By Seatbelt column
And I click the 12Months link
Then I validate the Driving Style Seatbelt text is SavedSpeed
And I validate the Driver Performance text is SavedDriver

Scenario: TC1566: Reports - Drivers - Table Properties NEED ASSISTANCE IN IMPLEMENTING HOW TO CHECK ORDER
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Sort By Driver link
And I click the Sort By Group link
And I click the Sort By Group link
And I click the Sort By Vehicle link
And I click the Sort By Vehicle link
And I click the Sort By DistanceDriven link
And I click the Sort By DistanceDriven link
And I click the Sort By Overall link
And I click the Sort By Overall link
And I click the Sort By Speed link
And I click the Sort By Speed link
And I click the Sort By Style link
And I click the Sort By Style link
And I click the Sort By Seatbelt link
And I click the Sort By Seatbelt link

Scenario: TC1567: Reports - Drivers - Tools Button
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I click the Tools button
Then I validate the Email Report button is present
Then I validate the Export To PDF button is present
Then I validate the Export To Excel button is present

Scenario: TC1568: Reports - Drivers - UI
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
Then I validate I am on the Drivers page
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Sort By Driver link is present
And I validate the Sort By Group link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By DistanceDriven link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I validate the Sort By Seatbelt link is present
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Overall dropdown is present
And I validate the Stats dropdown is present
And I validate the Speed dropdown is present
And I validate the Style dropdown is present
And I validate the Seatbelt dropdown is present

Scenario: TC1569: Reports - Drivers - Vehicle Link
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I click the Sort By Vehicle link
And I click the Sort By Vehicle link
And I save the first entry in the Sort By Vehicle column as SavedEntry
And I click the first entry in the Sort By Vehicle column
Then I validate the Vehicle Performance text is SavedEntry

Scenario: TC1571: Reports - Drivers - Edit Columns - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I uncheck the checkbox of the fourth entry
And I uncheck the checkbox of the fifth entry
And I uncheck the checkbox of the sixth entry
And I uncheck the checkbox of the seventh entry
And I uncheck the checkbox of the eighth entry
And I uncheck the checkbox of the ninth entry
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Driver link is present
And I validate the Sort By Group link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By DistanceDriven link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I validate the Sort By Seatbelt link is present

Scenario: TC1572: Reports - Drivers - Edit Columns - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Driver link is present
And I validate the Sort By Group link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By DistanceDriven link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I validate the Sort By Seatbelt link is present

Scenario: TC1573: Reports - Drivers - Edit Columns - Check Box Selection via Mouse
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
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
And I uncheck the checkbox of the sixth entry
And I validate the checkbox of the sixth entry is not checked
And I uncheck the checkbox of the seventh entry
And I validate the checkbox of the seventh entry is not checked
And I uncheck the checkbox of the eighth entry
And I validate the checkbox of the eighth entry is not checked
And I uncheck the checkbox of the ninth entry
And I validate the checkbox of the ninth entry is not checked
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
And I check the checkbox of the sixth entry
And I validate the checkbox of the sixth entry is checked
And I check the checkbox of the seventh entry
And I validate the checkbox of the seventh entry is checked
And I check the checkbox of the eighth entry
And I validate the checkbox of the eighth entry is checked
And I check the checkbox of the ninth entry
And I validate the checkbox of the ninth entry is checked

Scenario: TC1575: Reports - Drivers - Edit Columns - Current Session Retention
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I uncheck the checkbox of the second entry
And I uncheck the checkbox of the third entry
And I click the Save button
And the Edit Columns popup closes
And I click the Notifications link
And I click the Reports link
And I click the Drivers link
Then I validate the Sort By Driver link is not present
And I validate the Sort By Group link is not present
And I validate the Sort By Vehicle link is not present
And I validate the Sort By Distance Driven link is present
And I validate the Sort By Overall link is present
And I validate the Sort By Speed link is present
And I validate the Sort By Style link is present
And I validate the Sort By Seatbelt link is present

Scenario: TC1577: Reports - Drivers - Edit Columns - Save Button
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Driver link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the checkbox of the first entry is not checked
And I check the checkbox of the first entry
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Driver link is present

Scenario: TC1578: Reports - Drivers - Edit Columns - Subsequent Session Retention
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
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
And I click the Drivers link
Then I validate the Sort By Driver link is not present
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
And I click the Drivers link
And I validate the Sort By Driver link is present

Scenario: TC1580: Reports - Drivers - Edit Columns - UI
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Drivers link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the first checkbox is present
And I validate the second checkbox is present
And I validate the third checkbox is present
And I validate the fourth checkbox is present
And I validate the fifth checkbox is present
And I validate the sixth checkbox is present
And I validate the seventh checkbox is present
And I validate the eighth checkbox is present
And I validate the ninth checkbox is present
And I validate the first checkbox is checked
And I validate the second checkbox is unchecked
And I validate the third checkbox is checked
And I validate the fourth checkbox is checked
And I validate the fifth checkbox is checked
And I validate the sixth checkbox is checked
And I validate the seventh checkbox is checked
And I validate the eighth checkbox is checked
And I validate the ninth checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present
