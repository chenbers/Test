Narrative:Test the functionality of the Assets Trailers page

Scenario: Assets - Trailers - UI
Given I navigate to the assets trailers page
Then I validate I am on the Assets Trailers page
And I validate the Records Per Page Label text is present
And I validate the Records Per Page dropdown is present
And I validate the Search Label text is present
And I validate the Search textfield is present
And I validate the Show Hide Columns link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Team text is present
And I validate the Sort By Device text is present
And I validate the Sort By Vehicle text is present
And I validate the Sort By Driver text is present
And I validate the Sort By Status link is present
And I validate the Sort By VIN link is present
And I validate the Sort By License Number link is present
And I validate the Sort By State text is present
And I validate the Sort By Year link is present
And I validate the Sort By Make link is present
And I validate the Sort By Model link is present
And I validate the Sort By Color link is present
And I validate the Sort By Weight link is present
And I validate the Sort By Odometer link is present
And I validate the Previous Disabled link is  present
And I validate the Next link is present
And I validate the New_ button is present
And I validate the New_ button is visible
And I validate the Edit button is not visible
And I validate the Cancel button is not visible
And I validate the Save button is not visible
And I validate the Vin Label text is present
And I validate the Make Label text is present
And I validate the Year Label text is present
And I validate the Color Label text is present
And I validate the Weight Label text is present
And I validate the License Number Label text is present
And I validate the State Label text is present
And I validate the Details Header text is present
And I validate the Trailer ID Label text is present
And I validate the Status Label text is present
And I validate the Assignment Label text is present
And I validate the Team Label text is present
And I validate the Assigned Device Label text is present
And I validate the Assigned Vehicle Label text is present
And I validate the Assigned Driver Label text is present
And I validate the VIN textfield is not visible
And I validate the Make textfield is not visible
And I validate the Year textfield is not visible
And I validate the Color textfield is not visible
And I validate the Weight textfield is not visible
And I validate the License Number textfield is not visible
And I validate the State dropdown is not visible
And I validate the Trailer ID textfield is not visible
And I validate the Status dropdown is not visible
And I validate the Assigned Device dropdown is not visible
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""
And I click the New_ button
And I validate the Cancel button is visible
And I validate the Save button is visible
And I validate the New_ button is not visible
And I validate the Edit button is not visible
And I validate the VIN textfield is visible
And I validate the Make textfield is visible
And I validate the Year textfield is visible
And I validate the Color textfield is visible
And I validate the Weight textfield is visible
And I validate the License Number textfield is visible
And I validate the State dropdown is visible
And I validate the Trailer ID textfield is visible
And I validate the Status dropdown is visible
And I validate the Assigned Device dropdown is visible
And I click the Cancel button
And I validate the Cancel button is not visible
And I validate the Save button is not visible
And I validate the New_ button is visible
And I validate the Edit button is not visible
And I validate the VIN textfield is not visible
And I validate the Make textfield is not visible
And I validate the Year textfield is not visible
And I validate the Color textfield is not visible
And I validate the Weight textfield is not visible
And I validate the License Number textfield is not visible
And I validate the State dropdown is not visible
And I validate the Trailer ID textfield is not visible
And I validate the Status dropdown is not visible
And I validate the Assigned Device dropdown is not visible
And I click the Show Hide Columns link
And I validate the Trailer ID Checkbox Label text is present
And I validate the Team Checkbox Label text is present
And I validate the Device Checkbox Label text is present
And I validate the Vehicle Checkbox Label text is present
And I validate the Driver Checkbox Label text is present
And I validate the Status Checkbox Label text is present
And I validate the VIN Checkbox Label text is present
And I validate the License Number Checkbox Label text is present
And I validate the State Checkbox Label text is present
And I validate the Year Checkbox Label text is present
And I validate the Make Checkbox Label text is present
And I validate the Model Checkbox Label text is present
And I validate the Color Checkbox Label text is present
And I validate the Weight Checkbox Label text is present
And I validate the Odometer Checkbox Label text is present
And I validate the Trailer ID checkbox is present
And I validate the Team checkbox is present
And I validate the Device checkbox is present
And I validate the Vehicle checkbox is present
And I validate the Driver checkbox is present
And I validate the Status checkbox is present
And I validate the VIN checkbox is present
And I validate the License Number checkbox is present
And I validate the State checkbox is present
And I validate the Year checkbox is present
And I validate the Make checkbox is present
And I validate the Model checkbox is present
And I validate the Color checkbox is present
And I validate the Weight checkbox is present
And I validate the Odometer checkbox is present

Scenario: Assets - Trailers - Default Records dropdowns are set to 10
Given I navigate to the assets trailers page
Then I validate the Records Per Page dropdown is "10"

Scenario: Assets - Trailers - Records per page test
Given I navigate to the assets trailers page
When I select "10" from the Records Per Page dropdown 
Then I validate the 11th Row of the Trailer ID Entry link is not present
And I validate the Entries text contains "Showing 1 to 10 of"
And I select "25" from the Records Per Page dropdown 
And I validate the 26th Row of the Trailer ID Entry link is not present
And I validate the Entries text contains "Showing 1 to 25 of"
And I select "50" from the Records Per Page dropdown 
And I validate the 51st Row of the Trailer ID Entry link is not present
And I validate the Entries text contains "Showing 1 to 50 of"
And I select "100" from the Records Per Page dropdown 
And I validate the 101st Row of the Trailer ID Entry link is not present
And I validate the Entries text contains "Showing 1 to 100 of"

Scenario: Assets - Trailers -  Empty Search
Given I navigate to the assets trailers page
When I type "randomstringthatwillnotcomeup" into the Search textfield
Then I validate the No Matching Records text is "No matching records found"
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"
And I validate the Previous link is not present
And I validate the Next link is not present
And I validate the Previous Disabled link is present
And I validate the Next Disabled link is present

!-- Scenario: Assets - Trailers - Filtered Entries Text Functions Correctly
!-- Given I navigate to the assets trailers page
!-- Then I validate the Entries text contains "filtered from X total entries"

Scenario: Assets - Trailers - Search Filter (clear or not clear test depending on how we change things, at the moment the test checks for not cleared text)
Given I navigate to the assets trailers page
When I type "SEARCHTEXT" into the Search textfield
And I click the Top Trailers link
Then I validate the Search textfield is "SEARCHTEXT"

Scenario: Assets - Trailers - Show Hide Columns UI Interaction
Given I navigate to the assets trailers page
When I click the Show Hide Columns link
And I uncheck the Trailer ID checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Trailer ID link is not present
And I click the Show Hide Columns link
And I check the Trailer ID checkbox
And I click the Show Hide Columns link
And I validate the Sort By Trailer ID link is present
When I click the Show Hide Columns link
And I uncheck the Team checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Team text is not present
And I click the Show Hide Columns link
And I check the Team checkbox
And I click the Show Hide Columns link
And I validate the Sort By Team text is present
When I click the Show Hide Columns link
And I uncheck the Device checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Device text is not present
And I click the Show Hide Columns link
And I check the Device checkbox
And I click the Show Hide Columns link
And I validate the Sort By Device text is present
When I click the Show Hide Columns link
And I uncheck the Vehicle checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Vehicle text is not present
And I click the Show Hide Columns link
And I check the Vehicle checkbox
And I click the Show Hide Columns link
And I validate the Sort By Vehicle text is present
When I click the Show Hide Columns link
And I uncheck the Driver checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Driver text is not present
And I click the Show Hide Columns link
And I check the Driver checkbox
And I click the Show Hide Columns link
And I validate the Sort By Driver text is present
When I click the Show Hide Columns link
And I uncheck the Status checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Status link is not present
And I click the Show Hide Columns link
And I check the Status checkbox
And I click the Show Hide Columns link
And I validate the Sort By Status link is present
When I click the Show Hide Columns link
And I uncheck the VIN checkbox
And I click the Show Hide Columns link
Then I validate the Sort By VIN link is not present
And I click the Show Hide Columns link
And I check the VIN checkbox
And I click the Show Hide Columns link
And I validate the Sort By VIN link is present
When I click the Show Hide Columns link
And I uncheck the License Number checkbox
And I click the Show Hide Columns link
Then I validate the Sort By License Number link is not present
And I click the Show Hide Columns link
And I check the License Number checkbox
And I click the Show Hide Columns link
And I validate the Sort By License Number link is present
When I click the Show Hide Columns link
And I uncheck the State checkbox
And I click the Show Hide Columns link
Then I validate the Sort By State text is not present
And I click the Show Hide Columns link
And I check the State checkbox
And I click the Show Hide Columns link
And I validate the Sort By State text is present
When I click the Show Hide Columns link
And I uncheck the Year checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Year link is not present
And I click the Show Hide Columns link
And I check the Year checkbox
And I click the Show Hide Columns link
And I validate the Sort By Year link is present
When I click the Show Hide Columns link
And I uncheck the Make checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Make link is not present
And I click the Show Hide Columns link
And I check the Make checkbox
And I click the Show Hide Columns link
And I validate the Sort By Make link is present
When I click the Show Hide Columns link
And I uncheck the Model checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Model link is not present
And I click the Show Hide Columns link
And I check the Model checkbox
And I click the Show Hide Columns link
And I validate the Sort By Model link is present
When I click the Show Hide Columns link
And I uncheck the Color checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Color link is not present
And I click the Show Hide Columns link
And I check the Color checkbox
And I click the Show Hide Columns link
And I validate the Sort By Color link is present
When I click the Show Hide Columns link
And I uncheck the Weight checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Weight link is not present
And I click the Show Hide Columns link
And I check the Weight checkbox
And I click the Show Hide Columns link
And I validate the Sort By Weight link is present
When I click the Show Hide Columns link
And I uncheck the Odometer checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Odometer link is not present
And I click the Show Hide Columns link
And I check the Odometer checkbox
And I click the Show Hide Columns link
And I validate the Sort By Odometer link is present
And I uncheck the Trailer ID checkbox
And I uncheck the Team checkbox
And I uncheck the Device checkbox
And I uncheck the Vehicle checkbox
And I uncheck the Driver checkbox
And I uncheck the Status checkbox
And I uncheck the VIN checkbox
And I uncheck the License Number checkbox
And I uncheck the State checkbox
And I uncheck the Year checkbox
And I uncheck the Make checkbox
And I uncheck the Model checkbox
And I uncheck the Color checkbox
And I uncheck the Weight checkbox
And I uncheck the Odometer checkbox
And I click the Restore Original link
And I click the Show Hide Columns link
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Team text is present
And I validate the Sort By Device text is present
And I validate the Sort By Vehicle text is present
And I validate the Sort By Driver text is present
And I validate the Sort By Status link is present
And I validate the Sort By VIN link is present
And I validate the Sort By License Number link is present
And I validate the Sort By State text is present
And I validate the Sort By Year link is present
And I validate the Sort By Make link is present
And I validate the Sort By Model link is present
And I validate the Sort By Color link is present
And I validate the Sort By Weight link is present
And I validate the Sort By Odometer link is present

Scenario: Assets - Trailers - Bookmark Entry 
Given I navigate to the assets trailers page
When I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I log back in
Then I validate I am on the Assets Trailers page

Scenario: Assets - Trailers - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER
Given I navigate to the assets trailers page
When I click the Sort By Trailer ID link
!-- And I click the Sort By Team link
!-- And I click the Sort By Device text
!-- And I click the Sort By Vehicle text
!-- And I click the Sort By Driver text
And I click the Sort By Status link
And I click the Sort By VIN link
And I click the Sort By License Number link
!-- And I click the Sort By State text
And I click the Sort By Year link
And I click the Sort By Make link
And I click the Sort By Model link
And I click the Sort By Color link
And I click the Sort By Weight link
And I click the Sort By Odometer link

Scenario: Assets - Trailers - Show/Hide Columns - Subsequent Session Retention
Given I navigate to the assets trailers page
When I click the Show Hide Columns link
And I uncheck the Trailer ID checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Trailer ID link is not present
And I click the Logout link
Given I navigate to the assets trailers page
Then I validate the Sort By Trailer ID link is not present
And I click the Show Hide Columns link
And I check the Trailer ID checkbox
And I click the Show Hide Columns link
And I validate the Sort By Trailer ID link is present

Scenario: Assets - Trailers - Click New Button, Then Try And Select A Row In The Table
Given I navigate to the assets trailers page
When I click the New_ button
And I click the 1st Row of the Trailer ID Entry link
Then I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the State dropdown contains "--- Select ---"
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
And I validate the Assigned Device dropdown is ""

Scenario: Assets - Trailers - Select A Row, Click Edit, Then Select Another Row
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I save the 1st Row of the Trailer ID Entry link as SAVEDTRAILER
And I save the 1st Row of the Team Entry link as SAVEDTEAM
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle Entry link as SAVEDVEHICLE
And I save the 1st Row of the Driver Entry link as SAVEDDRIVER
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the VIN Entry link as SAVEDVIN
And I save the 1st Row of the License Entry link as SAVEDLICENSE
And I save the 1st Row of the State Entry link as SAVEDSTATE
And I save the 1st Row of the Year Entry link as SAVEDYEAR
And I save the 1st Row of the Make Entry link as SAVEDMAKE
And I save the 1st Row of the Model Entry link as SAVEDMODEL
And I save the 1st Row of the Color Entry link as SAVEDCOLOR
And I save the 1st Row of the Weight Entry link as SAVEDWEIGHT
And I save the 1st Row of the Odometer Entry link as SAVEDODOMETER
And I save the 2nd Row of the Trailer ID Entry link as SAVEDTRAILER2
And I save the 2nd Row of the Team Entry link as SAVEDTEAM2
And I save the 2nd Row of the Device Entry link as SAVEDDEVICE2
And I save the 2nd Row of the Vehicle Entry link as SAVEDVEHICLE2
And I save the 2nd Row of the Driver Entry link as SAVEDDRIVER2
And I save the 2nd Row of the Status Entry link as SAVEDSTATUS2
And I save the 2nd Row of the VIN Entry link as SAVEDVIN2
And I save the 2nd Row of the License Entry link as SAVEDLICENSE2
And I save the 2nd Row of the State Entry link as SAVEDSTATE2
And I save the 2nd Row of the Year Entry link as SAVEDYEAR2
And I save the 2nd Row of the Make Entry link as SAVEDMAKE2
And I save the 2nd Row of the Model Entry link as SAVEDMODEL2
And I save the 2nd Row of the Color Entry link as SAVEDCOLOR2
And I save the 2nd Row of the Weight Entry link as SAVEDWEIGHT2
And I save the 2nd Row of the Odometer Entry link as SAVEDODOMETER2
And I click the Edit button
And I click the 2nd Row of the Trailer ID Entry link
Then I validate the VIN text is SAVEDVIN2
And I validate the Make text is SAVEDMAKE2
And I validate the Year text is SAVEDYEAR2
And I validate the Color text is SAVEDCOLOR2
And I validate the Weight text is SAVEDWEIGHT2
And I validate the License Number text is SAVEDLICENSE2
!-- And I validate the State text is SAVEDSTATE2
And I validate the Trailer ID text is SAVEDTRAILER2
And I validate the Status text is SAVEDSTATUS2
And I validate the Team text is SAVEDTEAM2
And I validate the Assigned Device text is SAVEDDEVICE2
And I validate the Assigned Vehicle text is SAVEDVEHICLE2
And I validate the Assigned Driver text is SAVEDDRIVER2

Scenario: Assets - Trailers - Select And Deselect A Row
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the 1st Row of the Trailer ID Entry link
Then I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""

Scenario: Assets - Trailers - Select And Deselect A Row Then Click The New Button
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the 1st Row of the Trailer ID Entry link
And I click the New_ button
Then I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the State dropdown contains "--- Select ---"
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
And I validate the Assigned Device dropdown is ""

Scenario: Assets - Trailers - Select A Row, Select Edit, Then Cancel
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I save the 1st Row of the Trailer ID Entry link as SAVEDTRAILER
And I save the 1st Row of the Team Entry link as SAVEDTEAM
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle Entry link as SAVEDVEHICLE
And I save the 1st Row of the Driver Entry link as SAVEDDRIVER
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the VIN Entry link as SAVEDVIN
And I save the 1st Row of the License Entry link as SAVEDLICENSE
And I save the 1st Row of the State Entry link as SAVEDSTATE
And I save the 1st Row of the Year Entry link as SAVEDYEAR
And I save the 1st Row of the Make Entry link as SAVEDMAKE
And I save the 1st Row of the Model Entry link as SAVEDMODEL
And I save the 1st Row of the Color Entry link as SAVEDCOLOR
And I save the 1st Row of the Weight Entry link as SAVEDWEIGHT
And I save the 1st Row of the Odometer Entry link as SAVEDODOMETER
And I click the Edit button
And I click the Cancel button
Then I validate the VIN text is SAVEDVIN
And I validate the Make text is SAVEDMAKE
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
!-- And I validate the State text is SAVEDSTATE
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER

Scenario: Assets - Trailers - Select A Row, Select Edit, Deselect The Row
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I click the 1st Row of the Trailer ID Entry link
Then I validate the New button is visible
And I validate the Save button is not visible
And I validate the Edit button is not visible
And I validate the Cancel button is not visible
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""

Scenario: Assets - Trailers - Delete Trailer Test (cancel button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I save the 1st Row of the Trailer ID Entry link as SAVEDTRAILER
And I click the Edit button
And I select DELETED from the Status dropdown
And I click the Cancel button
Then I validate the 1st Row of the Trailer ID Entry link is SAVEDTRAILER

Scenario: Assets - Trailers - Delete Trailer Test (save button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I save the 1st Row of the Trailer ID Entry link as SAVEDTRAILER
And I click the Edit button
And I select DELETED from the Status dropdown
And I click the Save button
And I type SAVEDTRAILER into the Search textfield
Then I validate the 1st Row of the Trailer ID Entry link is not SAVEDTRAILER

Scenario: Assets - Trailers - New Trailer (cancel button - no changes)
Given I navigate to the assets trailers page
When I click the New_ button
Then I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the State dropdown contains "--- Select ---"
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
And I validate the Assigned Device dropdown is ""
And I click the Cancel button
And I click the New_ button
And I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the State dropdown contains "--- Select ---"
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
And I validate the Assigned Device dropdown is ""

Scenario: Assets - Trailers - New Trailer (cancel button - changes)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "FAKEVINNUMBER" into the VIN textfield
And I type "Ford" into the Make textfield
And I type "2013" into the Year textfield
And I type "Race Red" into the Color textfield
And I type "21000" into the Weight textfield
And I type "INTHINC" into the License Number textfield
And I select "Alabama" from the State dropdown
And I type "FAKETRAILERNAME" into the Trailer ID textfield
And I select "ACTIVE" from the Status dropdown
And I select "MCM990000" from the Assigned Device dropdown
And I click the Cancel button
And I type "FAKETRAILERNAME" into the Search textfield
Then I validate the No Matching Records text is present
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""
And I click the New_ button
And I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the State dropdown contains "--- Select ---"
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
And I validate the Assigned Device dropdown is ""

Scenario: Assets - Trailers - New Trailer (save button - changes)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "SAVECHANGESVIN" into the VIN textfield
And I type "Kia" into the Make textfield
And I type "2008" into the Year textfield
And I type "Purple" into the Color textfield
And I type "15000" into the Weight textfield
And I type "INTHINC1" into the License Number textfield
And I select "Vermont" from the State dropdown
And I type "SAVECHANGESNAME" into the Trailer ID textfield
And I select "ACTIVE" from the Status dropdown
And I select "MCM990001" from the Assigned Device dropdown
And I click the Save button
Then I validate the New_ button is visible
And I validate the Edit button is not visible
And I validate the Cancel button is not visible
And I validate the Save button is not visible
And I type "SAVECHANGESNAME" into the Search textfield
And I validate the Entries text contains "Showing 1 to 1 of 1 entries"
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""
And I validate the 1st Row of the Trailer ID Entry link is "SAVECHANGESNAME"
And I validate the 1st Row of the Team Entry link is "Test Group WR"
And I validate the 1st Row of the Device Entry link is "MCM990001"
And I validate the 1st Row of the Vehicle Entry link is "autogen01"
And I validate the 1st Row of the Driver Entry link is "Automatically Generated01"
And I validate the 1st Row of the Status Entry link is "ACTIVE"
And I validate the 1st Row of the VIN Entry link is "SAVECHANGESVIN"
And I validate the 1st Row of the License Entry link is "INTHINC1"
And I validate the 1st Row of the State Entry link is "VT"
And I validate the 1st Row of the Year Entry link is "2008"
And I validate the 1st Row of the Make Entry link is "Kia"
And I validate the 1st Row of the Model Entry link is SAVEDMODEL
And I validate the 1st Row of the Color Entry link is "Purple"
And I validate the 1st Row of the Weight Entry link is "15000"
And I click the 1st Row of the Trailer Id Entry link
And I validate the VIN text is "SAVECHANGESVIN"
And I validate the Make text is "Kia"
And I validate the Year text is "2008"
And I validate the Color text is "Purple"
And I validate the Weight text is "15000"
And I validate the License Number text is "INTHINC1"
And I validate the State text is "Vermont"
And I validate the Trailer ID text is "SAVECHANGESNAME"
And I validate the Status text is "ACTIVE"
And I validate the Team text is "Test Group WR"
And I validate the Assigned Device text is "MCM990001"
And I validate the Assigned Vehicle text is "autogen01"
And I validate the Assigned Driver text is "Automatically Generated01"

!-- Scenario: Assets - Trailers - New Trailer (save button - no changes)
!-- Given I navigate to the assets trailers page
!-- When I click the New_ button
!-- Then I validate the VIN textfield is ""
!-- And I validate the Make textfield is ""
!-- And I validate the Year textfield is ""
!-- And I validate the Color textfield is ""
!-- And I validate the Weight textfield is ""
!-- And I validate the License Number textfield is ""
!-- And I validate the State dropdown contains "--- Select ---"
!-- And I validate the Trailer ID textfield is ""
!-- And I validate the Status dropdown is "ACTIVE"
!-- And I validate the Assigned Device dropdown is ""
!-- And I click the Save button
!-- And I validate the Edit button is not visible
!-- And I validate the Cancel button is visible
!-- And I validate the Save button is visible
!-- I figure some sort of validation message is supposed to appear here to tell the user they hit the save button without entering anything
!-- And I validate the VIN textfield is ""
!-- And I validate the Make textfield is ""
!-- And I validate the Year textfield is ""
!-- And I validate the Color textfield is ""
!-- And I validate the Weight textfield is ""
!-- And I validate the License Number textfield is ""
!-- And I validate the State dropdown contains "--- Select ---"
!-- And I validate the Trailer ID textfield is ""
!-- And I validate the Status dropdown is "ACTIVE"
!-- And I validate the Assigned Device dropdown is ""

Scenario: Assets - Trailers - Edit Trailer (edit button - no changes)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer Id Entry link
And I save the 1st Row of the Trailer ID Entry link as SAVEDTRAILER
And I save the 1st Row of the Team Entry link as SAVEDTEAM
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle Entry link as SAVEDVEHICLE
And I save the 1st Row of the Driver Entry link as SAVEDDRIVER
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the VIN Entry link as SAVEDVIN
And I save the 1st Row of the License Entry link as SAVEDLICENSE
And I save the 1st Row of the State Entry link as SAVEDSTATE
And I save the 1st Row of the Year Entry link as SAVEDYEAR
And I save the 1st Row of the Make Entry link as SAVEDMAKE
And I save the 1st Row of the Model Entry link as SAVEDMODEL
And I save the 1st Row of the Color Entry link as SAVEDCOLOR
And I save the 1st Row of the Weight Entry link as SAVEDWEIGHT
And I save the 1st Row of the Odometer Entry link as SAVEDODOMETER
Then I validate the VIN text is SAVEDVIN
And I validate the Make text is SAVEDMAKE
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
!-- And I validate the State text is SAVEDSTATE
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I click the Edit button
And I validate the VIN textfield is SAVEDVIN
And I validate the Make textfield is SAVEDMAKE
And I validate the Year textfield is SAVEDYEAR
And I validate the Color textfield is SAVEDCOLOR
And I validate the Weight textfield is SAVEDWEIGHT
And I validate the License Number textfield is SAVEDLICENSE
!-- And I validate the State dropdown is SAVEDSTATE
And I validate the Trailer ID textfield is SAVEDTRAILER
And I validate the Status dropdown is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device dropdown is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I click the Cancel button
And I validate the Edit button is visible
And I validate the VIN text is SAVEDVIN
And I validate the Make text is SAVEDMAKE
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
!-- And I validate the State text is SAVEDSTATE
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER

Scenario: Assets - Trailers - Edit Trailer (edit button - save changes)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer Id Entry link
And I save the 1st Row of the Trailer ID Entry link as SAVEDTRAILER
And I save the 1st Row of the Team Entry link as SAVEDTEAM
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle Entry link as SAVEDVEHICLE
And I save the 1st Row of the Driver Entry link as SAVEDDRIVER
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the VIN Entry link as SAVEDVIN
And I save the 1st Row of the License Entry link as SAVEDLICENSE
And I save the 1st Row of the State Entry link as SAVEDSTATE
And I save the 1st Row of the Year Entry link as SAVEDYEAR
And I save the 1st Row of the Make Entry link as SAVEDMAKE
And I save the 1st Row of the Model Entry link as SAVEDMODEL
And I save the 1st Row of the Color Entry link as SAVEDCOLOR
And I save the 1st Row of the Weight Entry link as SAVEDWEIGHT
And I save the 1st Row of the Odometer Entry link as SAVEDODOMETER
Then I validate the VIN text is SAVEDVIN
And I validate the Make text is SAVEDMAKE
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
!-- And I validate the State text is SAVEDSTATE
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I click the Edit button
And I validate the VIN textfield is SAVEDVIN
And I validate the Make textfield is SAVEDMAKE
And I validate the Year textfield is SAVEDYEAR
And I validate the Color textfield is SAVEDCOLOR
And I validate the Weight textfield is SAVEDWEIGHT
And I validate the License Number textfield is SAVEDLICENSE
!-- And I validate the State dropdown is SAVEDSTATE
And I validate the Trailer ID textfield is SAVEDTRAILER
And I validate the Status dropdown is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device dropdown is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I type "ANEWVINNUMBER" into the VIN textfield
And I type "ANEWMAKE" into the Make textfield
And I type "2000" into the Year textfield
And I type "ANEWCOLOR" into the Color textfield
And I type "55" into the Weight textfield
And I type "ANEWLICENSE" into the License Number textfield
And I select "Alaska" from the State dropdown
And I type "ANEWTRAILER" into the Trailer ID textfield
And I select "INACTIVE" from the Status dropdown
And I click the Save button
And I validate the Edit button is visible
And I validate the VIN text is "ANEWVINNUMBER"
And I validate the Make text is "ANEWMAKE"
And I validate the Year text is "2000"
And I validate the Color text is "ANEWCOLOR"
And I validate the Weight text is "55"
And I validate the License Number text is "ANEWLICENSE"
And I validate the State text is "Alaska"
And I validate the Trailer ID text is "ANEWTRAILER"
And I validate the Status text is "INACTIVE"
And I validate the Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""
And I type "ANEWMAKE" into the Search textfield
And I validate the 1st Row of the Trailer ID Entry link is "ANEWTRAILER"
And I validate the 1st Row of the Team Entry link is SAVEDTEAM
And I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Driver Entry link is SAVEDDRIVER
And I validate the 1st Row of the Status Entry link is "INACTIVE"
And I validate the 1st Row of the VIN Entry link is "ANEWVINNUMBER"
And I validate the 1st Row of the License Entry link is "ANEWLICENSE"
And I validate the 1st Row of the State Entry link is "AK"
And I validate the 1st Row of the Year Entry link is "2000"
And I validate the 1st Row of the Make Entry link is "ANEWMAKE"
And I validate the 1st Row of the Model Entry link is SAVEDMODEL
And I validate the 1st Row of the Color Entry link is "ANEWCOLOR"
And I validate the 1st Row of the Weight Entry link is "55"

!-- Scenario: Assets - Trailers - Edit Trailer (edit button - save changes, but changes are invalid)


Scenario: Assets - Trailers - Edit trailer then create a new trailer
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer Id Entry link
And I save the 1st Row of the Trailer ID Entry link as SAVEDTRAILER
And I save the 1st Row of the Team Entry link as SAVEDTEAM
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle Entry link as SAVEDVEHICLE
And I save the 1st Row of the Driver Entry link as SAVEDDRIVER
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the VIN Entry link as SAVEDVIN
And I save the 1st Row of the License Entry link as SAVEDLICENSE
!-- And I save the 1st Row of the State Entry link as SAVEDSTATE
And I save the 1st Row of the Year Entry link as SAVEDYEAR
And I save the 1st Row of the Make Entry link as SAVEDMAKE
And I save the 1st Row of the Model Entry link as SAVEDMODEL
And I save the 1st Row of the Color Entry link as SAVEDCOLOR
And I save the 1st Row of the Weight Entry link as SAVEDWEIGHT
And I save the 1st Row of the Odometer Entry link as SAVEDODOMETER
Then I validate the VIN text is SAVEDVIN
And I validate the Make text is SAVEDMAKE
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
And I validate the State text is "Alabama"
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I click the Edit button
And I validate the VIN textfield is SAVEDVIN
And I validate the Make textfield is SAVEDMAKE
And I validate the Year textfield is SAVEDYEAR
And I validate the Color textfield is SAVEDCOLOR
And I validate the Weight textfield is SAVEDWEIGHT
And I validate the License Number textfield is SAVEDLICENSE
And I validate the State dropdown is "Alabama"
And I validate the Trailer ID textfield is SAVEDTRAILER
And I validate the Status dropdown is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device dropdown is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I type "ANEWVINNUMBER2" into the VIN textfield
And I type "ANEWMAKE2" into the Make textfield
And I type "2001" into the Year textfield
And I type "ANEWERCOLOR" into the Color textfield
And I type "60" into the Weight textfield
And I type "ANEWLICENSE2" into the License Number textfield
And I select "Texas" from the State dropdown
And I type "ANEWTRAILER2" into the Trailer ID textfield
And I select "ACTIVE" from the Status dropdown
And I click the Save button
!-- Need to determine if we are going to show the new values after saving or just have it reset back to blank.
And I validate the VIN text is "ANEWVINNUMBER2"
And I validate the Make text is "ANEWMAKE2"
And I validate the Year text is "2001"
And I validate the Color text is "ANEWERCOLOR"
And I validate the Weight text is "60"
And I validate the License Number text is "ANEWLICENSE2"
And I validate the State text is "Texas"
And I validate the Trailer ID text is "ANEWTRAILER2"
And I validate the Status text is "ACTIVE"
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I type "ANEWMAKE2" into the Search textfield
And I validate the 1st Row of the Trailer ID Entry link is "ANEWTRAILER2"
And I validate the 1st Row of the Team Entry link is SAVEDTEAM
And I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Driver Entry link is SAVEDDRIVER
And I validate the 1st Row of the Status Entry link is "ACTIVE"
And I validate the 1st Row of the VIN Entry link is "ANEWVINNUMBER2"
And I validate the 1st Row of the License Entry link is "ANEWLICENSE2"
And I validate the 1st Row of the State Entry link is "Texas"
And I validate the 1st Row of the Year Entry link is "2001"
And I validate the 1st Row of the Make Entry link is "ANEWMAK2E"
And I validate the 1st Row of the Model Entry link is SAVEDMODEL
And I validate the 1st Row of the Color Entry link is "ANEWERCOLOR"
And I validate the 1st Row of the Weight Entry link is "60"

Scenario: Assets - Trailers - Clear Device Association link
Given I navigate to the assets trailers page
When I click the New_ button
And I type "CLEARDEVICEVIN" into the VIN textfield
And I type "CLEARDEVICEMAKE" into the Make textfield
And I type "2003" into the Year textfield
And I type "CLEARDEVICECOLOR" into the Color textfield
And I type "15003" into the Weight textfield
And I type "CLEARDEVICELICNUM" into the License Number textfield
And I select "Virginia" from the State dropdown
And I type "CLEARDEVICETEST" into the Trailer ID textfield
And I select "ACTIVE" from the Status dropdown
And I select "MCM990098" from the Assigned Device dropdown
And I click the Save button
And I type "CLEARDEVICETEST" into the Search textfield
And I click the Edit button
And I select "Remove Device Assignment" from the Assigned Device dropdown
And I click the Save button
Then I validate the Assigned Device text is "none assigned"
And I validate the 1st Row of the Device Entry link is "none assigned"
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
And I type "CLEARDEVICETEST" into the Search textfield
And I validate the Entries text contains "Showing 0 to 0 of"

!-- Scenario: Assets - Trailers - Batch Edit Test (no changes)


!-- Scenario: Assets - Trailers - Batch Edit Test (changes)


Scenario: Assets - Trailers - VIN exceeds 17 characters (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "1" into the VIN textfield
Then I validate the VIN Error text is not present
And I type "12" into the VIN textfield
And I validate the VIN Error text is not present
And I type "123" into the VIN textfield
And I validate the VIN Error text is not present
And I type "1234" into the VIN textfield
And I validate the VIN Error text is not present
And I type "12345" into the VIN textfield
And I validate the VIN Error text is not present
And I type "123456" into the VIN textfield
And I validate the VIN Error text is not present
And I type "1234567" into the VIN textfield
And I validate the VIN Error text is not present
And I type "12345678" into the VIN textfield
And I validate the VIN Error text is not present
And I type "123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "0123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "00123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "0000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "00000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "000000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "0000000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "00000000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "000000000123456789" into the VIN textfield
And I validate the VIN Error text is present
And I validate the VIN Error text is "The trailer vin exceeds 17 characters."
And I click the Cancel button
And I click the New_ button
And I validate the VIN Error text is not present

Scenario: Assets - Trailers - VIN exceeds 17 characters (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "1" into the VIN textfield
Then I validate the VIN Error text is not present
And I type "12" into the VIN textfield
And I validate the VIN Error text is not present
And I type "123" into the VIN textfield
And I validate the VIN Error text is not present
And I type "1234" into the VIN textfield
And I validate the VIN Error text is not present
And I type "12345" into the VIN textfield
And I validate the VIN Error text is not present
And I type "123456" into the VIN textfield
And I validate the VIN Error text is not present
And I type "1234567" into the VIN textfield
And I validate the VIN Error text is not present
And I type "12345678" into the VIN textfield
And I validate the VIN Error text is not present
And I type "123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "0123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "00123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "0000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "00000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "000000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "0000000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "00000000123456789" into the VIN textfield
And I validate the VIN Error text is not present
And I type "000000000123456789" into the VIN textfield
And I validate the VIN Error text is present
And I validate the VIN Error text is "The trailer vin exceeds 17 characters."
And I click the Cancel button
And I click the Edit button
And I validate the VIN Error text is not present

Scenario: Assets - Trailers - Year is not 4 characters (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "0" into the Year textfield
Then I validate the Year Error text is present
And I validate the Year Error text is "The trailer year can be empty or contain 4 numbers."
And I type "01" into the Year textfield
And I validate the Year Error text is present
And I type "012" into the Year textfield
And I validate the Year Error text is present
And I type "0123" into the Year textfield
And I validate the Year Error text is not present
And I type "01234" into the Year textfield
And I validate the Year Error text is present
And I click the Cancel button
And I click the New_ button
And I validate the Year Error text is not present

Scenario: Assets - Trailers - Year is not 4 characters (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "0" into the Year textfield
Then I validate the Year Error text is present
And I validate the Year Error text is "The trailer year can be empty or contain 4 numbers."
And I type "01" into the Year textfield
And I validate the Year Error text is present
And I type "012" into the Year textfield
And I validate the Year Error text is present
And I type "0123" into the Year textfield
And I validate the Year Error text is not present
And I type "01234" into the Year textfield
And I validate the Year Error text is present
And I click the Cancel button
And I click the Edit button
And I validate the Year Error text is not present

Scenario: Assets - Trailers - Year is alpha characters (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "a" into the Year textfield
Then I validate the Year Error text is present
And I validate the Year Error text is "The trailer year can be empty or contain 4 numbers."
And I click the Cancel button
And I click the New_ button
And I validate the Year Error text is not present

Scenario: Assets - Trailers - Year is alpha characters (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "a" into the Year textfield
Then I validate the Year Error text is present
And I validate the Year Error text is "The trailer year can be empty or contain 4 numbers."
And I click the Cancel button
And I click the Edit button
And I validate the Year Error text is not present

Scenario: Assets - Trailers - Weight is not numerical (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "a" into the Weight textfield
Then I validate the Weight Error text is present
And I validate the Weight Error text is "The trailer weight can be empty or contain only numbers."
And I click the Cancel button
And I click the New_ button
And I validate the Weight Error text is not present

Scenario: Assets - Trailers - Weight is not numerical (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "a" into the Weight textfield
Then I validate the Weight Error text is present
And I validate the Weight Error text is "The trailer weight can be empty or contain only numbers."
And I click the Cancel button
And I click the Edit button
And I validate the Weight Error text is not present


