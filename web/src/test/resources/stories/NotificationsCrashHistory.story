Test Cases for TF388/TF411/TF412 

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF412

Narrative:

Scenario: TC1335: Notifications - Crash History - Add Crash Report Link
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Add Crash Report link
Then I validate I am on the Notifications Crash History Add Edit page

Scenario: TC1336: Notifications - Crash History - Bookmark Entry
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
Given I am logged in
Then I validate I am on the Notifications Crash History page

Scenario: TC1337: Notifications - Crash History - Bookmark Entry to Different Account
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
Given I am logged in an account that can be edited
Then I validate I am on the Notifications Crash History page

Scenario: TC1339: Notifications - Crash History - Bookmark Entry with Search
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I select "Top - Test Group WR" from the Team dropdown
And I type "TIWI00" into the Search textfield
And I click the Search button
Then I validate the 1st Row of the Vehicle link is present
When I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
Given I am logged in
Then I validate I am on the Notifications Crash History page
And I validate the Team dropdown is ""
And I validate the Search textfield is ""
And I validate the 1st Row of the Entry Vehicle link is not present

Scenario: TC1340: Notifications - Crash History - Details Link
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I select "Top - Test Group WR" from the Team dropdown
And I select "All" from the Time Frame dropdown
And I click the Refresh button
And I click the 1st Row of the Entry Details link
Then I validate I am on the Notifications Crash Report page

Scenario: TC1341: Notifications - Crash History - Driver Link
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I select "Top - Test Group WR" from the Team dropdown
And I select "All" from the Time Frame dropdown
And I type "TIWI00" into the Search textfield
And I click the Search button
And I save the 1st Row of the Entry Driver link as SAVEDNAME
And I click the 1st Row of the Entry Driver link
Then I validate I am on the Driver Performance page
And I validate the Driver Name link is SAVEDNAME

Scenario: TC1349: Notifications - Crash History - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER IN A NEW STEP
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I select "Top - Test Group WR" from the Team dropdown
And I select "All" from the Time Frame dropdown
And I click the Refresh button
Then I validate the Sort By Date Time column is sorted in ascending order
And I click the Sort By Date Time link
And I validate the Sort By Date Time column is sorted in descending order
And I click the Sort By Group link
And I validate the Sort By Group column is sorted in ascending order
And I click the Sort By Group link
And I validate the Sort By Group column is sorted in descending order
And I click the Sort By Driver link
And I validate the Sort By Driver column is sorted in ascending order
And I click the Sort By Driver link
And I validate the Sort By Driver column is sorted in descending order
And I click the Sort By Vehicle link
And I validate the Sort By Vehicle column is sorted in ascending order
And I click the Sort By Vehicle link
And I validate the Sort By Vehicle column is sorted in descending order
And I click the Sort By Occupants link
And I validate the Sort By Occupants column is sorted in ascending order
And I click the Sort By Occupants link
And I validate the Sort By Occupants column is sorted in descending order
And I click the Sort By Status link
And I validate the Sort By Status column is sorted in ascending order
And I click the Sort By Status link
And I validate the Sort By Status column is sorted in descending order
And I click the Sort By Weather link
And I validate the Sort By Weather column is sorted in ascending order
And I click the Sort By Weather link
And I validate the Sort By Weather column is sorted in descending order

Scenario: TC1350: Notifications - Crash History - Tools Button
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Tools button
Then I validate the Email This Report button is present
And I validate the Export To PDF button is present
And I validate the Export To Excel button is present

Scenario: TC1351: Notifications - Crash History - UI (With Admin Rights)
Given I am logged in
When I click the Notifications link
And I click the Crash History link
Then I validate the Team dropdown is present
And I validate the Time Frame dropdown is present
And I validate the Refresh button is present
And I validate the Search textfield is present
And I validate the Search button is present
And I validate the Edit Columns link is present
And I validate the Add Crash Report link is present
And I validate the Tools Button is present
And I validate the Records text is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Occupants link is present
And I validate the Sort By Status link is present
And I validate the Sort By Weather link is present

Scenario: TC1352: Notifications - Crash History - UI (Without Admin Rights)
Given I am logged in an account without admin rights
When I click the Notifications link
And I click the Crash History link
Then I validate the Team dropdown is present
And I validate the Time Frame dropdown is present
And I validate the Refresh button is present
And I validate the Search textfield is present
And I validate the Search button is present
And I validate the Edit Columns link is present
And I validate the Add Crash Report link is not present
And I validate the Tools Button is present
And I validate the Records text is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Occupants link is present
And I validate the Sort By Status link is present
And I validate the Sort By Weather link is present

Scenario: TC1353: Notifications - Crash History - Vehicle Link
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I select "Top - Test Group WR" from the Team dropdown
And I select "All" from the Time Frame dropdown
And I click the Refresh button
And I click the Sort By Driver link
And I save the 1st Row of the Entry Vehicle link as SAVEDENTRY
And I click the 1st Row of the Entry Vehicle link
Then I validate I am on the Vehicle Performance page
And I validate the Vehicle Name link is SAVEDENTRY

Scenario: TC1354: Notifications - Crash History - Edit Columns - Cancel Button (Changes)
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 5th Row of the Column checkbox
And I uncheck the 6th Row of the Column checkbox
And I uncheck the 7th Row of the Column checkbox
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Occupants link is present
And I validate the Sort By Status link is present
And I validate the Sort By Weather link is present

Scenario: TC1355: Notifications - Crash History - Edit Columns - Cancel Button (No Changes)
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Edit Columns link
And the Edit Columns popup opens
And I click the Cancel button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Occupants link is present
And I validate the Sort By Status link is present
And I validate the Sort By Weather link is present

Scenario: TC1356: Notifications - Crash History - Edit Columns - Check Box Selection via Mouse
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I check the 1st Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is checked
And I uncheck the 1st Row of the Column checkbox
And I validate the 1st Row of the Column checkbox is not checked
And I check the 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is checked
And I uncheck the 2nd Row of the Column checkbox
And I validate the 2nd Row of the Column checkbox is not checked
And I check the 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is checked
And I uncheck the 3rd Row of the Column checkbox
And I validate the 3rd Row of the Column checkbox is not checked
And I check the 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is checked
And I uncheck the 4th Row of the Column checkbox
And I validate the 4th Row of the Column checkbox is not checked
And I check the 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is checked
And I uncheck the 5th Row of the Column checkbox
And I validate the 5th Row of the Column checkbox is not checked
And I check the 6th Row of the Column checkbox
And I validate the 6th Row of the Column checkbox is checked
And I uncheck the 6th Row of the Column checkbox
And I validate the 6th Row of the Column checkbox is not checked
And I check the 7th Row of the Column checkbox
And I validate the 7th Row of the Column checkbox is checked
And I uncheck the 7th Row of the Column checkbox
And I validate the 7th Row of the Column checkbox is not checked

Scenario: TC1358: Notifications - Crash History - Edit Columns - Current Session Retention
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Reports link
And I click the Notifications link
And I click the Crash History link
Then I validate the Sort By Date Time link is not present
And I validate the Sort By Group link is not present
And I validate the Sort By Driver link is not present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Occupants link is present
And I validate the Sort By Status link is present
And I validate the Sort By Weather link is present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is  present
And I validate the Sort By Group link is  present
And I validate the Sort By Driver link is  present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Occupants link is present
And I validate the Sort By Status link is present
And I validate the Sort By Weather link is present

Scenario: TC1359: Notifications - Crash History - Edit Columns - Default Command Button
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I press the Enter Key
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is not present
When I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I press the Enter Key
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC1360: Notifications - Crash History - Edit Columns - Save Button
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I validate the 1st Row of the Column checkbox is not checked
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present

Scenario: TC1361: Notifications - Crash History - Edit Columns - Subsequent Session Retention
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Edit Columns link
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Crash History link
Then I validate the Sort By Date Time link is not present
And I click the Edit Columns link
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I click the Logout link
Given I am logged in
When I click the Notifications link
And I click the Crash History link
Then I validate the Sort By Date Time link is present

Scenario: TC1363: Notifications - Crash History - Edit Columns - UI
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Edit Columns link
And the Edit Columns popup opens
Then I validate the 1st Row of the Column checkbox is present
And I validate the 2nd Row of the Column checkbox is present
And I validate the 3rd Row of the Column checkbox is present
And I validate the 4th Row of the Column checkbox is present
And I validate the 5th Row of the Column checkbox is present
And I validate the 6th Row of the Column checkbox is present
And I validate the 7th Row of the Column checkbox is present
And I validate the 1st Row of the Column checkbox is checked
And I validate the 2nd Row of the Column checkbox is checked
And I validate the 3rd Row of the Column checkbox is checked
And I validate the 4th Row of the Column checkbox is checked
And I validate the 5th Row of the Column checkbox is checked
And I validate the 6th Row of the Column checkbox is checked
And I validate the 7th Row of the Column checkbox is checked
And I validate the Save button is present
And I validate the Cancel button is present

Scenario: TC5168: Notifications - Crash History - Add Crash Report
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I click the Add Crash Report link
Then I validate I am on the Notifications Crash History Add Edit page
When I select "New" from the Crash Report Status dropdown
And I save the Date Time dropdown as SAVEDDATETIME
And I select "TIWI00 - 2012 a make a model" from the Vehicle dropdown
And I select "tiwi 00" from the Driver dropdown
And I type "rain" into the Weather dropdown
And I select "1" from the Occupant count dropdown
And I type "testing Add Crash Report TC5168" into the Description textfield
And I click the Find Address button
And I type "4225 West Lake Park Blvd, West Valley, UT" into the address textfield
And I click the Locate button
And I click the Bottom Save button
Then I validate I am on the Crash Report page
And I validate the Crash Report Status text is "New"
And I validate the Date Time text contains SAVEDDATETIME
And I validate the Vehicle link is "TIWI00"
And I validate the Driver link is "tiwi 00"
And I validate the Weather link is "rain"
And I validate the Occupant count is "1"
And I validate the Description text is "testing Add Crash Report TC5168"

Scenario: TC5169: Notifications - Crash History - Edit and Confirm Crash Report
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I select "Top - Test Group WR" from the Team dropdown
And I select "All" from the Time Frame dropdown
And I click the Refresh button
And I click the 1st Row of the Details link
Then I validate I am on the Notifications Crash History page
When I click the Edit button
Then I validate I am on the Notifications Crash History Add Edit page
When I select "Confirmed" from the Crash Report Status dropdown
And I click the Date Time dropdown
And I click the Time link
And I type "01" into the Hours textfield
And I click the Ok button
And I save the Date Time dropdown as SAVEDDATETIME
Then I validate the Vehicle dropdown is not clickable
And I validate the Driver dropdown is not clickable
When I type "snow" into the Weather dropdown
And I select "2" from the Occupant count dropdown
And I type "" into the Description textfield
And I type "testing Edit Crash Report TC5169" into the Description textfield
And I click the Trips button
And I click the Driver button
Then I validate the Sort By Start Time link is present
And I validate the End Time text is present
And I validate the 1st Row of the Start time link is present
And I validate the 1st Row of the End Time link is present
When I click the 1st Row of the Start Time link
And I click the Bottom Save button
Then I validate I am on the Crash Report page
And I validate the Crash Report Status text is "Confirmed"
And I validate the Date Time text contains SAVEDDATETIME
And I validate the Vehicle link is "TIWI00"
And I validate the Driver link is "tiwi 00"
And I validate the Weather link is "snow"
And I validate the Occupant count is "2"
And I validate the Description text is "testing Add Crash Report TC5168"
When I click the Back To Crash History List link
And I select "Top - Test Group WR" from the Team dropdown
And I select "All" from the Time Frame dropdown
And I click the Refresh button
Then I validate the 1st Row of the Date Time text contains SAVEDDATETIME
And I validate the 1st Row of the Group link is "Test Group WR"
And I validate the 1st Row of the Driver link is "tiwi 00"
And I validate the 1st Row of the Vehicle link is "TIWI00"
And I validate the 1st Row of the Occupants text is "2"
And I validate the 1st Row of the Status text is "Confirmed"
And I validate the 1st Row of the Weather text is "snow"
