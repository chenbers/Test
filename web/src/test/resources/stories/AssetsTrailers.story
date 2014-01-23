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
And I validate the Sort By State link is present
And I validate the Sort By Year link is present
And I validate the Sort By Make link is present
And I validate the Sort By Model link is present
And I validate the Sort By Color link is present
And I validate the Sort By Weight link is present
And I validate the Sort By Odometer link is present
And I validate the Previous Disabled link is present
And I validate the Next link is present
And I validate the Entries text is present
And I validate the Entries text contains "Showing 1 to 10 of"
And I validate the Entries text contains "entries"
And I validate the Entries text does not contain "filtered"
And I validate the New_ button is present
And I validate the New_ button is visible
And I validate the Edit button is not visible
And I validate the Cancel button is not visible
And I validate the Save button is not visible
And I validate the Details Header text is present
And I validate the Trailer ID Label text is present
And I validate the Status Label text is present
And I validate the Vin Label text is present
And I validate the Make Label text is present
And I validate the Model Label text is present
And I validate the Odometer Label text is present
And I validate the Year Label text is present
And I validate the Color Label text is present
And I validate the Weight Label text is present
And I validate the License Number Label text is present
And I validate the State Label text is present
And I validate the Assignment Label text is present
And I validate the Assigned Device Label text is present
And I validate the Assigned Vehicle Label text is present
And I validate the Assigned Team Label text is present
And I validate the Assigned Driver Label text is present
And I validate the Trailer ID textfield is not visible
And I validate the Status dropdown is not visible
And I validate the VIN textfield is not visible
And I validate the Make textfield is not visible
And I validate the Model textfield is not visible
And I validate the Odometer textfield is not visible
And I validate the Year textfield is not visible
And I validate the Color textfield is not visible
And I validate the Weight textfield is not visible
And I validate the License Number textfield is not visible
And I validate the State dropdown is not visible
And I validate the Assigned Device dropdown is not visible
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Driver text is ""
And I click the New_ button
And I validate the Cancel button is visible
And I validate the Save button is visible
And I validate the New_ button is visible
And I validate the Edit button is not visible
And I validate the Trailer ID textfield is visible
And I validate the Status dropdown is visible
And I validate the VIN textfield is visible
And I validate the Make textfield is visible
And I validate the Model textfield is visible
And I validate the Odometer textfield is visible
And I validate the Year textfield is visible
And I validate the Color textfield is visible
And I validate the Weight textfield is visible
And I validate the License Number textfield is visible
And I validate the State dropdown is visible
And I validate the Assigned Device dropdown is visible
And I validate the Assigned Device dropdown is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Driver text is ""
And I click the Cancel button
And I validate the Cancel button is not visible
And I validate the Save button is not visible
And I validate the New_ button is visible
And I validate the Edit button is not visible
And I validate the Trailer ID textfield is not visible
And I validate the Status dropdown is not visible
And I validate the VIN textfield is not visible
And I validate the Make textfield is not visible
And I validate the Model textfield is not visible
And I validate the Odometer textfield is not visible
And I validate the Year textfield is not visible
And I validate the Color textfield is not visible
And I validate the Weight textfield is not visible
And I validate the License Number textfield is not visible
And I validate the State dropdown is not visible
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
And I validate the Restore Original link is present

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

Scenario: Assets - Trailers - Filtered Entries Text Functions Correctly
Given I navigate to the assets trailers page
When I type "FilteredEntry" into the Search textfield
Then I validate the Entries text contains "Showing 1 to 3 of 3 entries"
And I validate the Entries text contains "filtered from"
And I validate the Entries text contains "total entries"

Scenario: Assets - Trailers - Search Filter (clear or not clear test depending on how we change things, at the moment the test checks for not cleared text)
Given I navigate to the assets trailers page
When I type "SEARCHTEXT" into the Search textfield
And I click the Top Trailers link
Then I validate the Search textfield is "SEARCHTEXT"

Scenario: Assets - Trailers - Search All Fields
Given I navigate to the assets trailers page
When I click the New_ button
And I type "SEARCHVEH1" into the Trailer ID textfield
And I type "SEARCHVIN1" into the VIN textfield
And I type "SEARCHMAKE1" into the Make textfield
And I type "SEARCHMODEL1" into the Model textfield
And I type "333555" into the Odometer textfield
And I type "3055" into the Year textfield
And I type "SEARCHCOLO1" into the Color textfield
And I type "987654321" into the Weight textfield
And I type "SEARCHLIC1" into the License Number textfield
And I select "Federated States of Micronesia" from the State dropdown
And I select "MCM990075" from the Assigned Device dropdown
And I click the Save button
And I type "SEARCHVEH1" into the Search textfield
And I click the Sort By Trailer ID link
Then I validate the 1st Row of the Trailer ID Entry link is "SEARCHVEH1"
And I click the Sort By Trailer ID link
And I validate the 1st Row of the Trailer ID Entry link is "SEARCHVEH1"
And I type "Test Group WR" into the Search textfield
And I click the Sort By Team link
And I validate the 1st Row of the Team Entry link is "Test Group WR"
And I click the Sort By Team link
And I validate the 1st Row of the Team Entry link is "Test Group WR"
And I type "MCM990075" into the Search textfield
And I click the Sort By Device link
And I validate the 1st Row of the Device Entry link is "MCM990075"
And I click the Sort By Device link
And I validate the 1st Row of the Device Entry link is "MCM990075"
And I type "autogen75" into the Search textfield
And I click the Sort By Vehicle link
And I validate the 1st Row of the Vehicle Entry link is "autogen75"
And I click the Sort By Vehicle link
And I validate the 1st Row of the Vehicle Entry link is "autogen75"
And I type "Automatically Generated75" into the Search textfield
And I click the Sort By Driver link
And I validate the 1st Row of the Driver Entry link is "Automatically Generated75"
And I click the Sort By Driver link
And I validate the 1st Row of the Driver Entry link is "Automatically Generated75"
And I type "ACTIVE" into the Search textfield
And I click the Sort By Status link
And I validate the 1st Row of the Status Entry link is "ACTIVE"
And I click the Sort By Status link
And I validate the 1st Row of the Status Entry link is "ACTIVE"
And I type "SEARCHVIN1" into the Search textfield
And I click the Sort By VIN link
And I validate the 1st Row of the VIN Entry link is "SEARCHVIN1"
And I click the Sort By VIN link
And I validate the 1st Row of the VIN Entry link is "SEARCHVIN1"
And I type "SEARCHLIC1" into the Search textfield
And I click the Sort By License link
And I validate the 1st Row of the License Entry link is "SEARCHLIC1"
And I click the Sort By License link
And I validate the 1st Row of the License Entry link is "SEARCHLIC1"
And I type "Federated States of Micronesia" into the Search textfield
And I click the Sort By State link
And I validate the 1st Row of the State Entry link is "Federated States of Micronesia"
And I click the Sort By State link
And I validate the 1st Row of the State Entry link is "Federated States of Micronesia"
And I type "3055" into the Search textfield
And I click the Sort By Year link
And I validate the 1st Row of the Year Entry link is "3055"
And I click the Sort By Year link
And I validate the 1st Row of the Year Entry link is "3055"
And I type "SEARCHMAKE1" into the Search textfield
And I click the Sort By Make link
And I validate the 1st Row of the Make Entry link is "SEARCHMAKE1"
And I click the Sort By Make link
And I validate the 1st Row of the Make Entry link is "SEARCHMAKE1"
And I type "SEARCHMODEL1" into the Search textfield
And I click the Sort By Model link
And I validate the 1st Row of the Model Entry link is "SEARCHMODEL1"
And I click the Sort By Model link
And I validate the 1st Row of the Model Entry link is "SEARCHMODEL1"
And I type "SEARCHCOLO1" into the Search textfield
And I click the Sort By Color link
And I validate the 1st Row of the Color Entry link is "SEARCHCOLO1"
And I click the Sort By Color link
And I validate the 1st Row of the Color Entry link is "SEARCHCOLO1"
And I type "987654321" into the Search textfield
And I click the Sort By Weight link
And I validate the 1st Row of the Weight Entry link is "987654321"
And I click the Sort By Weight link
And I validate the 1st Row of the Weight Entry link is "987654321"
And I type "333555" into the Search textfield
And I click the Sort By Odometer link
And I validate the 1st Row of the Odometer Entry link is "333555"
And I click the Sort By Odometer link
And I validate the 1st Row of the Odometer Entry link is "333555"
!-- Now I delete the entries so it will run just fine on the next run through
And I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

Scenario: Assets - Trailers - Show Hide Columns UI Interaction
Given I navigate to the assets trailers page
When I click the Show Hide Columns link
And I uncheck the Trailer ID checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Trailer ID link is not present
And I check the Trailer ID checkbox
And I validate the Sort By Trailer ID link is present
When I click the Show Hide Columns link
And I uncheck the Team checkbox
Then I validate the Sort By Team text is not present
And I check the Team checkbox
And I validate the Sort By Team text is present
When I click the Show Hide Columns link
And I uncheck the Device checkbox
Then I validate the Sort By Device text is not present
And I check the Device checkbox
And I validate the Sort By Device text is present
When I click the Show Hide Columns link
And I uncheck the Vehicle checkbox
Then I validate the Sort By Vehicle text is not present
And I check the Vehicle checkbox
And I validate the Sort By Vehicle text is present
When I click the Show Hide Columns link
And I uncheck the Driver checkbox
Then I validate the Sort By Driver text is not present
And I check the Driver checkbox
And I validate the Sort By Driver text is present
When I click the Show Hide Columns link
And I uncheck the Status checkbox
Then I validate the Sort By Status link is not present
And I check the Status checkbox
And I validate the Sort By Status link is present
When I click the Show Hide Columns link
And I uncheck the VIN checkbox
Then I validate the Sort By VIN link is not present
And I check the VIN checkbox
And I validate the Sort By VIN link is present
When I click the Show Hide Columns link
And I uncheck the License Number checkbox
Then I validate the Sort By License Number link is not present
And I check the License Number checkbox
And I validate the Sort By License Number link is present
When I click the Show Hide Columns link
And I uncheck the State checkbox
Then I validate the Sort By State link is not present
And I check the State checkbox
And I validate the Sort By State link is present
When I click the Show Hide Columns link
And I uncheck the Year checkbox
Then I validate the Sort By Year link is not present
And I check the Year checkbox
And I validate the Sort By Year link is present
When I click the Show Hide Columns link
And I uncheck the Make checkbox
Then I validate the Sort By Make link is not present
And I check the Make checkbox
And I validate the Sort By Make link is present
When I click the Show Hide Columns link
And I uncheck the Model checkbox
Then I validate the Sort By Model link is not present
And I check the Model checkbox
And I validate the Sort By Model link is present
When I click the Show Hide Columns link
And I uncheck the Color checkbox
Then I validate the Sort By Color link is not present
And I check the Color checkbox
And I validate the Sort By Color link is present
When I click the Show Hide Columns link
And I uncheck the Weight checkbox
Then I validate the Sort By Weight link is not present
And I check the Weight checkbox
And I validate the Sort By Weight link is present
When I click the Show Hide Columns link
And I uncheck the Odometer checkbox
Then I validate the Sort By Odometer link is not present
And I check the Odometer checkbox
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
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Team text is present
And I validate the Sort By Device text is present
And I validate the Sort By Vehicle text is present
And I validate the Sort By Driver text is present
And I validate the Sort By Status link is present
And I validate the Sort By VIN link is present
And I validate the Sort By License Number link is present
And I validate the Sort By State link is present
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
And I click the Sort By State link
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
When I save the 1st Row of the Trailer ID Entry link as SAVEDTRAILER
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
And I click the New_ button
And I click the 1st Row of the Trailer ID Entry link
Then I validate the Cancel button is not visible
And I validate the Save button is not visible
And I validate the Edit button is visible
And I validate the VIN text is SAVEDVIN
And I validate the Make text is SAVEDMAKE
And I validate the Model text is SAVEDMODEL
And I validate the Odometer text is SAVEDODOMETER
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
!-- And I validate the State text is SAVEDSTATE
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER

Scenario: Assets - Trailers - Select A Row, Click Edit, Then Select Another Row
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
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
Then I validate the VIN textfield is SAVEDVIN2
And I validate the Make textfield is SAVEDMAKE2
And I validate the Model textfield is SAVEDMODEL2
And I validate the Odometer textfield is SAVEDODOMETER2
And I validate the Year textfield is SAVEDYEAR2
And I validate the Color textfield is SAVEDCOLOR2
And I validate the Weight textfield is SAVEDWEIGHT2
And I validate the License Number textfield is SAVEDLICENSE2
!-- And I validate the State dropdown is SAVEDSTATE2
And I validate the Trailer ID textfield is SAVEDTRAILER2
And I validate the Status dropdown is SAVEDSTATUS2
And I validate the Assigned Team text is SAVEDTEAM2
!-- And I validate the Assigned Device dropdown is ""
And I validate the Assigned Vehicle text is SAVEDVEHICLE2
And I validate the Assigned Driver text is SAVEDDRIVER2

Scenario: Assets - Trailers - Select And Deselect A Row
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I control click the 1st Row of the Trailer ID Entry link
Then I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""

Scenario: Assets - Trailers - Select And Deselect A Row Then Click The New Button
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I control click the 1st Row of the Trailer ID Entry link
And I click the New_ button
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
Then I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Model textfield is ""
And I validate the Odometer textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the State dropdown contains "--- Select ---"
And I validate the Assigned Device dropdown is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""

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
And I validate the Model text is SAVEDMODEL
And I validate the Odometer text is SAVEDODOMETER
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
!-- And I validate the State text is SAVEDSTATE
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER

Scenario: Assets - Trailers - Select A Row, Select Edit, Deselect The Row
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I control click the 1st Row of the Trailer ID Entry link
Then I validate the New_ button is visible
And I validate the Save button is not visible
And I validate the Edit button is not visible
And I validate the Cancel button is not visible
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""

Scenario: Assets - Trailers - Delete Trailer Test (cancel button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I save the 1st Row of the Trailer ID Entry link as SAVEDTRAILER
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Cancel button
Then I validate the 1st Row of the Trailer ID Entry link is SAVEDTRAILER

Scenario: Assets - Trailers - Delete Trailer Test (save button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "DELETETRAILERNAME" into the Trailer ID textfield
And I type "DELETEVIN" into the VIN textfield
And I type "DELETEFord" into the Make textfield
And I type "DELETEFocus" into the Model textfield
And I type "999" into the Odometer textfield
And I type "1961" into the Year textfield
And I type "DELETE Red" into the Color textfield
And I type "230519" into the Weight textfield
And I type "DELETELIC" into the License Number textfield
And I select "New York" from the State dropdown
And I select "MCM990050" from the Assigned Device dropdown
And I click the Save button
And I type "DELETETRAILERNAME" into the Search textfield
And I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
Then I validate the 1st Row of the Trailer ID Entry link is not "DELETETRAILERNAME"

Scenario: Assets - Trailers - New Trailer (cancel button - no changes)
Given I navigate to the assets trailers page
When I click the New_ button
Then I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
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
And I validate the Model text is ""
And I validate the Odometer text is ""
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
And I type "FAKETRAILERNAME" into the Trailer ID textfield
And I select "ACTIVE" from the Status dropdown
And I type "FAKEVINNUMBER" into the VIN textfield
And I type "Ford" into the Make textfield
And I type "Focus" into the Model textfield
And I type "129018" into the Odometer textfield
And I type "1985" into the Year textfield
And I type "Race Red" into the Color textfield
And I type "21000" into the Weight textfield
And I type "INTHINCLIC" into the License Number textfield
And I select "Alabama" from the State dropdown
And I select "MCM990000" from the Assigned Device dropdown
And I click the Cancel button
And I type "FAKETRAILERNAME" into the Search textfield
Then I validate the No Matching Records text is present
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""
And I click the New_ button
And I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
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
And I type "SaveChangesModel" into the Model textfield
And I type "1010" into the Odometer textfield
And I type "2008" into the Year textfield
And I type "Purple" into the Color textfield
And I type "15000" into the Weight textfield
And I type "INTHINCLI1" into the License Number textfield
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
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Driver text is ""
And I validate the 1st Row of the Trailer ID Entry link is "SAVECHANGESNAME"
And I validate the 1st Row of the Team Entry link is "Test Group WR"
And I validate the 1st Row of the Device Entry link is "MCM990001"
And I validate the 1st Row of the Vehicle Entry link is "autogen01"
And I validate the 1st Row of the Driver Entry link is "Automatically Generated01"
And I validate the 1st Row of the Status Entry link is "ACTIVE"
And I validate the 1st Row of the VIN Entry link is "SAVECHANGESVIN"
And I validate the 1st Row of the License Entry link is "INTHINCLI1"
And I validate the 1st Row of the State Entry link is "VT"
And I validate the 1st Row of the Year Entry link is "2008"
And I validate the 1st Row of the Make Entry link is "Kia"
And I validate the 1st Row of the Model Entry link is "SaveChangesModel"
And I validate the 1st Row of the Color Entry link is "Purple"
And I validate the 1st Row of the Weight Entry link is "15000"
And I validate the 1st Row of the Odometer Entry link is "1010"
And I click the 1st Row of the Trailer Id Entry link
And I validate the Trailer ID text is "SAVECHANGESNAME"
And I validate the Status text is "ACTIVE"
And I validate the VIN text is "SAVECHANGESVIN"
And I validate the Make text is "Kia"
And I validate the Model text is "SaveChangesModel"
And I validate the Odometer text is "1010"
And I validate the Year text is "2008"
And I validate the Color text is "Purple"
And I validate the Weight text is "15000"
And I validate the License Number text is "INTHINCLI1"
And I validate the State text is "Vermont"
And I validate the Assigned Team text is "Test Group WR"
And I validate the Assigned Device text is "MCM990001"
And I validate the Assigned Vehicle text is "autogen01"
And I validate the Assigned Driver text is "Automatically Generated01"
!-- Now I delete the entry so it will run just fine on the next run through
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

Scenario: Assets - Trailers - New Trailer (save button - no changes)
Given I navigate to the assets trailers page
When I click the New_ button
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
Then I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Model textfield is ""
And I validate the Odometer textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the State dropdown contains "--- Select ---"
And I validate the Assigned Device dropdown is ""
And I click the Save button
And I validate the Trailer ID Error text is present
And I validate the Edit button is not visible
And I validate the Cancel button is visible
And I validate the Save button is visible
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
And I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Model textfield is ""
And I validate the Odometer textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the State dropdown contains "--- Select ---"
And I validate the Assigned Device dropdown is ""

Scenario: Assets - Trailers - Edit Trailer (save button - no changes)
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
And I click the Save button
And I validate the Edit button is not visible
And I validate the Save button is not visible
And I validate the Cancel button is not visible
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""

Scenario: Assets - Trailers - Edit Trailer (save button - blank trailer id error message)
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
And I click the Edit button
And I type "" into the Trailer ID textfield
And I click the Save button
Then I validate the Trailer ID textfield is ""
And I validate the VIN textfield is SAVEDVIN
And I validate the Make textfield is SAVEDMAKE
And I validate the Model textfield is SAVEDMODEL
And I validate the Odometer textfield is SAVEDODOMETER
And I validate the Year textfield is SAVEDYEAR
And I validate the Color textfield is SAVEDCOLOR
And I validate the Weight textfield is SAVEDWEIGHT
And I validate the License Number textfield is SAVEDLICENSE
!-- And I validate the State dropdown is SAVEDSTATE
And I validate the Status dropdown is SAVEDSTATUS
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Device dropdown is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER

Scenario: Assets - Trailers - Edit Trailer (edit button - save changes)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "EANDSTRAILERID" into the Trailer ID textfield
And I type "EDITANDSAVE" into the VIN textfield
And I type "EANDSMAKE" into the Make textfield
And I type "EANDSMODEL" into the Model textfield
And I type "505" into the Odometer textfield
And I type "1952" into the Year textfield
And I type "Puse" into the Color textfield
And I type "15032" into the Weight textfield
And I type "EANDSLN" into the License Number textfield
And I select "Washington" from the State dropdown
And I select "MCM990058" from the Assigned Device dropdown
And I click the Save button
And I type "EANDSTRAILERID" into the Search textfield
And I click the 1st Row of the Trailer Id Entry link
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
Then I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the VIN text is SAVEDVIN
And I validate the Make text is SAVEDMAKE
And I validate the Model text is SAVEDMODEL
And I validate the Odometer text is SAVEDODOMETER
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
!-- And I validate the State text is SAVEDSTATE
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I click the Edit button
And I validate the VIN textfield is SAVEDVIN
And I validate the Make textfield is SAVEDMAKE
And I validate the Model textfield is SAVEDMODEL
And I validate the Odometer textfield is SAVEDODOMETER
And I validate the Year textfield is SAVEDYEAR
And I validate the Color textfield is SAVEDCOLOR
And I validate the Weight textfield is SAVEDWEIGHT
And I validate the License Number textfield is SAVEDLICENSE
!-- And I validate the State dropdown is SAVEDSTATE
And I validate the Trailer ID textfield is SAVEDTRAILER
And I validate the Status dropdown is SAVEDSTATUS
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Device dropdown is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I type "ANEWTRAILER" into the Trailer ID textfield
And I select "INACTIVE" from the Status dropdown
And I type "ANEWVINNUMBER" into the VIN textfield
And I type "ANEWMAKE" into the Make textfield
And I type "ANEWMODEL" into the Model textfield
And I type "1059753" into the Odometer textfield
And I type "1900" into the Year textfield
And I type "ANEWCOLOR" into the Color textfield
And I type "55" into the Weight textfield
And I type "ANEWLIS" into the License Number textfield
And I select "Alaska" from the State dropdown
And I click the Save button
And I validate the Edit button is not visible
And I validate the Save button is not visible
And I validate the Cancel button is not visible
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""
And I type "ANEWMAKE" into the Search textfield
And I validate the 1st Row of the Trailer ID Entry link is "ANEWTRAILER"
And I validate the 1st Row of the Status Entry link is "INACTIVE"
And I validate the 1st Row of the VIN Entry link is "ANEWVINNUMBER"
And I validate the 1st Row of the Make Entry link is "ANEWMAKE"
And I validate the 1st Row of the Model Entry link is "ANEWMODEL"
And I validate the 1st Row of the Odometer Entry link is "1059753"
And I validate the 1st Row of the Year Entry link is "1900"
And I validate the 1st Row of the Color Entry link is "ANEWCOLOR"
And I validate the 1st Row of the Weight Entry link is "55"
And I validate the 1st Row of the License Entry link is "ANEWLIS"
And I validate the 1st Row of the State Entry link is "AK"
And I validate the 1st Row of the Team Entry link is SAVEDTEAM
And I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Driver Entry link is SAVEDDRIVER
!-- Now I delete the entry so it will run just fine on the next run through
And I click the 1st Row of the Trailer Id Entry link
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

Scenario: Assets - Trailers - New Trailer (new button - save changes, but changes are invalid)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "PREINVALIDID" into the Trailer ID textfield
And I type "INVALIDVIN" into the VIN textfield
And I type "INVALIDNMAKE" into the Make textfield
And I type "INVALIDMODEL" into the Model textfield
And I type "516132" into the Odometer textfield
And I type "1772" into the Year textfield
And I type "Gurple" into the Color textfield
And I type "116631" into the Weight textfield
And I type "INVALIDLN" into the License Number textfield
And I select "Pennsylvania" from the State dropdown
And I select "MCM990066" from the Assigned Device dropdown
And I click the Save button
And I type "PREINVALIDID" into the Search textfield
And I click the 1st Row of the Trailer Id Entry link
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
And I validate the Model text is SAVEDMODEL
And I validate the Odometer text is SAVEDODOMETER
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
And I validate the State text is "Pennsylvania"
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I click the Edit button
And I type "" into the Trailer ID textfield
And I type "ANINVALIDVINNUMBERTOOLONG" into the VIN textfield
And I type "WAYTOOLONGMAKEFORVALIDITY" into the Make textfield
And I type "WAYTOOLONGMODELFORVALIDITY" into the Model textfield
And I type "a" into the Odometer textfield
And I type "19101" into the Year textfield
And I type "ATOOLONGCOLORNAME" into the Color textfield
And I type "a" into the Weight textfield
And I type "ATOOLONGLICENSENUMBER" into the License Number textfield
And I click the Save button
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
And I validate the VIN textfield is "ANINVALIDVINNUMBERTOOLONG"
And I validate the Make textfield is "WAYTOOLONGMAKEFORVALIDITY"
And I validate the Model textfield is "WAYTOOLONGMODELFORVALIDITY"
And I validate the Odometer textfield is "a"
And I validate the Year textfield is "19101"
And I validate the Color textfield is "ATOOLONGCOLORNAME"
And I validate the Weight textfield is "a"
And I validate the License Number textfield is "ATOOLONGLICENSENUMBER"
And I validate the State dropdown is "Pennsylvania"
And I validate the Assigned Device dropdown is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Driver text is SAVEDDRIVER
!-- Now I delete the entry so it will run just fine on the next run through
And I click the 1st Row of the Trailer Id Entry link
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

Scenario: Assets - Trailers - New Trailer (new button - save changes, but changes are invalid)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "ANINVALIDVINNUMBERTOOLONG" into the VIN textfield
And I type "WAYTOOLONGMAKEFORVALIDITY" into the Make textfield
And I type "WAYTOOLONGMODELFORVALIDITY" into the Model textfield
And I type "a" into the Odometer textfield
And I type "19101" into the Year textfield
And I type "ATOOLONGCOLORNAME" into the Color textfield
And I type "a" into the Weight textfield
And I type "ATOOLONGLICENSENUMBER" into the License Number textfield
And I select "MCM990011" from the Assigned Device dropdown
And I click the Save button
Then I validate the Trailer ID textfield is ""
And I validate the Trailer ID Error text is visible
And I validate the Status dropdown is "ACTIVE"
And I validate the VIN textfield is "ANINVALIDVINNUMBERTOOLONG"
And I validate the VIN Error text is visible
And I validate the Make textfield is "WAYTOOLONGMAKEFORVALIDITY"
And I validate the Make Error text is visible
And I validate the Model textfield is "WAYTOOLONGMODELFORVALIDITY"
And I validate the Model Error text is visible
And I validate the Odometer textfield is "a"
And I validate the Odometer Error text is visible
And I validate the Year textfield is "19101"
And I validate the Year Error text is visible
And I validate the Color textfield is "ATOOLONGCOLORNAME"
And I validate the Color Error text is visible
And I validate the Weight textfield is "a"
And I validate the Weight Error text is visible
And I validate the License Number textfield is "ATOOLONGLICENSENUMBER"
And I validate the License Number Error text is visible
And I validate the State dropdown is "--- Select ---"
And I validate the Assigned Device dropdown is "MCM990011"
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Driver text is ""

Scenario: Assets - Trailers - Edit Trailer (edit button - save changes, but changes are invalid) (TEST WILL FAIL TILL BUG IS FIXED)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "PREINVALIDID" into the Trailer ID textfield
And I type "INVALIDVIN" into the VIN textfield
And I type "INVALIDNMAKE" into the Make textfield
And I type "INVALIDMODEL" into the Model textfield
And I type "516132" into the Odometer textfield
And I type "1772" into the Year textfield
And I type "Gurple" into the Color textfield
And I type "116631" into the Weight textfield
And I type "INVALIDLN" into the License Number textfield
And I select "Pennsylvania" from the State dropdown
And I select "MCM990066" from the Assigned Device dropdown
And I click the Save button
And I type "PREINVALIDID" into the Search textfield
And I click the 1st Row of the Trailer Id Entry link
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
And I validate the Model text is SAVEDMODEL
And I validate the Odometer text is SAVEDODOMETER
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
And I validate the State text is "Pennsylvania"
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I click the Edit button
And I type "" into the Trailer ID textfield
And I type "ANINVALIDVINNUMBERTOOLONG" into the VIN textfield
And I type "WAYTOOLONGMAKEFORVALIDITY" into the Make textfield
And I type "WAYTOOLONGMODELFORVALIDITY" into the Model textfield
And I type "a" into the Odometer textfield
And I type "19101" into the Year textfield
And I type "ATOOLONGCOLORNAME" into the Color textfield
And I type "a" into the Weight textfield
And I type "ATOOLONGLICENSENUMBER" into the License Number textfield
And I click the Save button
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
And I validate the VIN textfield is "ANINVALIDVINNUMBERTOOLONG"
And I validate the Make textfield is "WAYTOOLONGMAKEFORVALIDITY"
And I validate the Model textfield is "WAYTOOLONGMODELFORVALIDITY"
And I validate the Odometer textfield is "a"
And I validate the Year textfield is "19101"
And I validate the Color textfield is "ATOOLONGCOLORNAME"
And I validate the Weight textfield is "a"
And I validate the License Number textfield is "ATOOLONGLICENSENUMBER"
And I validate the State dropdown is "Pennsylvania"
And I validate the Assigned Device dropdown is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Driver text is SAVEDDRIVER
!-- Now I delete the entry so it will run just fine on the next run through
And I click the 1st Row of the Trailer Id Entry link
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

Scenario: Assets - Trailers - Edit trailer then create a new trailer
Given I navigate to the assets trailers page
When I click the New_ button
And I type "EANDCNTRAILERID" into the Trailer ID textfield
And I type "EANDCNVIN" into the VIN textfield
And I type "EANDCNMAKE" into the Make textfield
And I type "EANDCNMODEL" into the Model textfield
And I type "115" into the Odometer textfield
And I type "1852" into the Year textfield
And I type "Parchment" into the Color textfield
And I type "150632" into the Weight textfield
And I type "EANDCNLN" into the License Number textfield
And I select "Wisconsin" from the State dropdown
And I select "MCM990061" from the Assigned Device dropdown
And I click the Save button
And I type "EANDCNTRAILERID" into the Search textfield
And I click the 1st Row of the Trailer Id Entry link
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
And I validate the Model text is SAVEDMODEL
And I validate the Odometer text is SAVEDODOMETER
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
!-- And I validate the State text is "Alabama"
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I click the Edit button
And I validate the VIN textfield is SAVEDVIN
And I validate the Make textfield is SAVEDMAKE
And I validate the Model textfield is SAVEDMODEL
And I validate the Odometer textfield is SAVEDODOMETER
And I validate the Year textfield is SAVEDYEAR
And I validate the Color textfield is SAVEDCOLOR
And I validate the Weight textfield is SAVEDWEIGHT
And I validate the License Number textfield is SAVEDLICENSE
!-- And I validate the State dropdown is "Alabama"
And I validate the Trailer ID textfield is SAVEDTRAILER
And I validate the Status dropdown is SAVEDSTATUS
And I validate the Assigned Device dropdown is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Team text is SAVEDTEAM
And I validate the Assigned Driver text is SAVEDDRIVER
And I type "ANEWTRAILER2" into the Trailer ID textfield
And I select "INACTIVE" from the Status dropdown
And I type "ANEWVINNUMBER2" into the VIN textfield
And I type "ANEWMAKE2" into the Make textfield
And I type "ANEWMODEL2" into the Model textfield
And I type "102385" into the Odometer textfield
And I type "1910" into the Year textfield
And I type "ANEWERCOLOR" into the Color textfield
And I type "60" into the Weight textfield
And I type "NEWLICENS2" into the License Number textfield
And I select "Texas" from the State dropdown
And I click the Save button
And I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Driver text is ""
And I type "ANEWMAKE2" into the Search textfield
And I validate the 1st Row of the Trailer ID Entry link is "ANEWTRAILER2"
And I validate the 1st Row of the Status Entry link is "INACTIVE"
And I validate the 1st Row of the VIN Entry link is "ANEWVINNUMBER2"
And I validate the 1st Row of the License Entry link is "NEWLICENS2"
And I validate the 1st Row of the State Entry link is "TX"
And I validate the 1st Row of the Year Entry link is "1910"
And I validate the 1st Row of the Make Entry link is "ANEWMAKE2"
And I validate the 1st Row of the Model Entry link is "ANEWMODEL2"
And I validate the 1st Row of the Odometer Entry link is "102385"
And I validate the 1st Row of the Color Entry link is "ANEWERCOLOR"
And I validate the 1st Row of the Weight Entry link is "60"
And I validate the 1st Row of the Team Entry link is SAVEDTEAM
And I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Driver Entry link is SAVEDDRIVER
!-- Now I delete the entry so it will run just fine on the next run through
And I click the 1st Row of the Trailer Id Entry link
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

Scenario: Assets - Trailers - Clear Device Association link
Given I navigate to the assets trailers page
When I click the New_ button
And I type "CLEARDEVICETEST" into the Trailer ID textfield
And I select "ACTIVE" from the Status dropdown
And I type "CLEARDEVICEVIN" into the VIN textfield
And I type "CLEARDEVICEMAKE" into the Make textfield
And I type "CLEARDEVICEMODEL" into the Model textfield
And I type "159702" into the Odometer textfield
And I type "2003" into the Year textfield
And I type "CLEARDEVICECOL" into the Color textfield
And I type "15003" into the Weight textfield
And I type "CLEARDEVIC" into the License Number textfield
And I select "Virginia" from the State dropdown
And I select "MCM990098" from the Assigned Device dropdown
And I click the Save button
And I type "CLEARDEVICETEST" into the Search textfield
And I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I select "Remove Device Assignment" from the Assigned Device dropdown
And I click the Save button
And I click the 1st Row of the Trailer ID Entry link
Then I validate the Assigned Device text is "none assigned"
And I validate the 1st Row of the Device Entry link is "none assigned"
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
And I type "CLEARDEVICETEST" into the Search textfield
And I validate the Entries text contains "Showing 0 to 0 of"

Scenario: Assets - Trailers - Batch Edit Test (cancel button, no changes)
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
And I control click the 2nd Row of the Trailer ID Entry link
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
Then I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Driver text is ""
When I click the Edit button
Then I validate the Trailer ID textfield is disabled
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
And I validate the VIN textfield is disabled
And I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Model textfield is ""
And I validate the Odometer textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is disabled
And I validate the License Number textfield is ""
And I validate the State dropdown is "--- Select ---"
And I validate the Assigned Device dropdown is disabled
And I validate the Assigned Device dropdown is ""
And I click the Cancel button
And I validate the 1st Row of the Trailer ID Entry link is SAVEDTRAILER
And I validate the 1st Row of the Team Entry link is SAVEDTEAM
And I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Driver Entry link is SAVEDDRIVER
And I validate the 1st Row of the Status Entry link is SAVEDSTATUS
And I validate the 1st Row of the VIN Entry link is SAVEDVIN
And I validate the 1st Row of the License Entry link is SAVEDLICENSE
And I validate the 1st Row of the State Entry link is SAVEDSTATE
And I validate the 1st Row of the Year Entry link is SAVEDYEAR
And I validate the 1st Row of the Make Entry link is SAVEDMAKE
And I validate the 1st Row of the Model Entry link is SAVEDMODEL
And I validate the 1st Row of the Color Entry link is SAVEDCOLOR
And I validate the 1st Row of the Weight Entry link is SAVEDWEIGHT
And I validate the 1st Row of the Odometer Entry link is SAVEDODOMETER
And I validate the 2nd Row of the Trailer ID Entry link is SAVEDTRAILER2
And I validate the 2nd Row of the Team Entry link is SAVEDTEAM2
And I validate the 2nd Row of the Device Entry link is SAVEDDEVICE2
And I validate the 2nd Row of the Vehicle Entry link is SAVEDVEHICLE2
And I validate the 2nd Row of the Driver Entry link is SAVEDDRIVER2
And I validate the 2nd Row of the Status Entry link is SAVEDSTATUS2
And I validate the 2nd Row of the VIN Entry link is SAVEDVIN2
And I validate the 2nd Row of the License Entry link is SAVEDLICENSE2
And I validate the 2nd Row of the State Entry link is SAVEDSTATE2
And I validate the 2nd Row of the Year Entry link is SAVEDYEAR2
And I validate the 2nd Row of the Make Entry link is SAVEDMAKE2
And I validate the 2nd Row of the Model Entry link is SAVEDMODEL2
And I validate the 2nd Row of the Color Entry link is SAVEDCOLOR2
And I validate the 2nd Row of the Weight Entry link is SAVEDWEIGHT2
And I validate the 2nd Row of the Odometer Entry link is SAVEDODOMETER2

Scenario: Assets - Trailers - Batch Edit Test (save button, no changes)
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
And I control click the 2nd Row of the Trailer ID Entry link
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
Then I validate the Trailer ID text is ""
And I validate the Status text is ""
And I validate the VIN text is ""
And I validate the Make text is ""
And I validate the Model text is ""
And I validate the Odometer text is ""
And I validate the Year text is ""
And I validate the Color text is ""
And I validate the Weight text is ""
And I validate the License Number text is ""
And I validate the State text is ""
And I validate the Assigned Device text is ""
And I validate the Assigned Vehicle text is ""
And I validate the Assigned Team text is ""
And I validate the Assigned Driver text is ""
When I click the Edit button
Then I validate the Trailer ID textfield is disabled
And I validate the Trailer ID textfield is ""
And I validate the Status dropdown is "ACTIVE"
And I validate the VIN textfield is disabled
And I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Model textfield is ""
And I validate the Odometer textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is disabled
And I validate the License Number textfield is ""
And I validate the State dropdown is "--- Select ---"
And I validate the Assigned Device dropdown is disabled
And I validate the Assigned Device dropdown is ""
And I click the Save button
And I validate the 1st Row of the Trailer ID Entry link is SAVEDTRAILER
And I validate the 1st Row of the Team Entry link is SAVEDTEAM
And I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Driver Entry link is SAVEDDRIVER
And I validate the 1st Row of the Status Entry link is SAVEDSTATUS
And I validate the 1st Row of the VIN Entry link is SAVEDVIN
And I validate the 1st Row of the License Entry link is SAVEDLICENSE
And I validate the 1st Row of the State Entry link is SAVEDSTATE
And I validate the 1st Row of the Year Entry link is SAVEDYEAR
And I validate the 1st Row of the Make Entry link is SAVEDMAKE
And I validate the 1st Row of the Model Entry link is SAVEDMODEL
And I validate the 1st Row of the Color Entry link is SAVEDCOLOR
And I validate the 1st Row of the Weight Entry link is SAVEDWEIGHT
And I validate the 1st Row of the Odometer Entry link is SAVEDODOMETER
And I validate the 2nd Row of the Trailer ID Entry link is SAVEDTRAILER2
And I validate the 2nd Row of the Team Entry link is SAVEDTEAM2
And I validate the 2nd Row of the Device Entry link is SAVEDDEVICE2
And I validate the 2nd Row of the Vehicle Entry link is SAVEDVEHICLE2
And I validate the 2nd Row of the Driver Entry link is SAVEDDRIVER2
And I validate the 2nd Row of the Status Entry link is SAVEDSTATUS2
And I validate the 2nd Row of the VIN Entry link is SAVEDVIN2
And I validate the 2nd Row of the License Entry link is SAVEDLICENSE2
And I validate the 2nd Row of the State Entry link is SAVEDSTATE2
And I validate the 2nd Row of the Year Entry link is SAVEDYEAR2
And I validate the 2nd Row of the Make Entry link is SAVEDMAKE2
And I validate the 2nd Row of the Model Entry link is SAVEDMODEL2
And I validate the 2nd Row of the Color Entry link is SAVEDCOLOR2
And I validate the 2nd Row of the Weight Entry link is SAVEDWEIGHT2
And I validate the 2nd Row of the Odometer Entry link is SAVEDODOMETER2

Scenario: Assets - Trailers - Batch Edit Test (cancel button, changes)
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
And I control click the 2nd Row of the Trailer ID Entry link
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
And I select "INACTIVE" from the Status dropdown
And I type "CLEARDEVICEMAKE" into the Make textfield
And I type "CLEARDEVICEMODEL" into the Model textfield
And I type "159702" into the Odometer textfield
And I type "2003" into the Year textfield
And I type "CLEARDEVICECOL" into the Color textfield
And I type "15003" into the Weight textfield
And I select "Idaho" from the State dropdown
And I click the Cancel button
Then I validate the 1st Row of the Trailer ID Entry link is SAVEDTRAILER
And I validate the 1st Row of the Team Entry link is SAVEDTEAM
And I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Driver Entry link is SAVEDDRIVER
And I validate the 1st Row of the Status Entry link is SAVEDSTATUS
And I validate the 1st Row of the VIN Entry link is SAVEDVIN
And I validate the 1st Row of the License Entry link is SAVEDLICENSE
And I validate the 1st Row of the State Entry link is SAVEDSTATE
And I validate the 1st Row of the Year Entry link is SAVEDYEAR
And I validate the 1st Row of the Make Entry link is SAVEDMAKE
And I validate the 1st Row of the Model Entry link is SAVEDMODEL
And I validate the 1st Row of the Color Entry link is SAVEDCOLOR
And I validate the 1st Row of the Weight Entry link is SAVEDWEIGHT
And I validate the 1st Row of the Odometer Entry link is SAVEDODOMETER
And I validate the 2nd Row of the Trailer ID Entry link is SAVEDTRAILER2
And I validate the 2nd Row of the Team Entry link is SAVEDTEAM2
And I validate the 2nd Row of the Device Entry link is SAVEDDEVICE2
And I validate the 2nd Row of the Vehicle Entry link is SAVEDVEHICLE2
And I validate the 2nd Row of the Driver Entry link is SAVEDDRIVER2
And I validate the 2nd Row of the Status Entry link is SAVEDSTATUS2
And I validate the 2nd Row of the VIN Entry link is SAVEDVIN2
And I validate the 2nd Row of the License Entry link is SAVEDLICENSE2
And I validate the 2nd Row of the State Entry link is SAVEDSTATE2
And I validate the 2nd Row of the Year Entry link is SAVEDYEAR2
And I validate the 2nd Row of the Make Entry link is SAVEDMAKE2
And I validate the 2nd Row of the Model Entry link is SAVEDMODEL2
And I validate the 2nd Row of the Color Entry link is SAVEDCOLOR2
And I validate the 2nd Row of the Weight Entry link is SAVEDWEIGHT2
And I validate the 2nd Row of the Odometer Entry link is SAVEDODOMETER2

Scenario: Assets - Trailers - Batch Edit and Batch Delete Test (save button, changes)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "BATCHSAVE1" into the Trailer ID textfield
And I type "BATCHVIN1" into the VIN textfield
And I type "BATCHMAKE1" into the Make textfield
And I type "BATCHMODEL1" into the Model textfield
And I type "666" into the Odometer textfield
And I type "1955" into the Year textfield
And I type "BATCHCOLO1" into the Color textfield
And I type "100001" into the Weight textfield
And I type "BATCHLIC1" into the License Number textfield
And I select "New York" from the State dropdown
And I select "MCM990030" from the Assigned Device dropdown
And I click the Save button
When I click the New_ button
And I type "BATCHSAVE2" into the Trailer ID textfield
And I type "BATCHVIN2" into the VIN textfield
And I type "BATCHMAKE2" into the Make textfield
And I type "BATCHMODEL2" into the Model textfield
And I type "777" into the Odometer textfield
And I type "1956" into the Year textfield
And I type "BATCHCOLO2" into the Color textfield
And I type "100002" into the Weight textfield
And I type "BATCHLIC2" into the License Number textfield
And I select "New Jersey" from the State dropdown
And I select "MCM990031" from the Assigned Device dropdown
And I click the Save button
And I type "BATCHSAVE" into the Search textfield
And I click the 1st Row of the Trailer ID Entry link
And I save the 1st Row of the Trailer ID Entry link as SAVEDTRAILER
And I save the 1st Row of the Team Entry link as SAVEDTEAM
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle Entry link as SAVEDVEHICLE
And I save the 1st Row of the Driver Entry link as SAVEDDRIVER
And I save the 1st Row of the VIN Entry link as SAVEDVIN
And I save the 1st Row of the License Entry link as SAVEDLICENSE
And I control click the 2nd Row of the Trailer ID Entry link
And I save the 2nd Row of the Trailer ID Entry link as SAVEDTRAILER2
And I save the 2nd Row of the Team Entry link as SAVEDTEAM2
And I save the 2nd Row of the Device Entry link as SAVEDDEVICE2
And I save the 2nd Row of the Vehicle Entry link as SAVEDVEHICLE2
And I save the 2nd Row of the Driver Entry link as SAVEDDRIVER2
And I save the 2nd Row of the VIN Entry link as SAVEDVIN2
And I save the 2nd Row of the License Entry link as SAVEDLICENSE2
And I click the Edit button
And I select "INACTIVE" from the Status dropdown
And I type "BATCHMAKE" into the Make textfield
And I type "BATCHMODEL" into the Model textfield
And I type "999999" into the Odometer textfield
And I type "1111" into the Year textfield
And I type "BATCHCOLOR" into the Color textfield
And I type "1111" into the Weight textfield
And I select "Indiana" from the State dropdown
And I click the Save button
Then I validate the 1st Row of the Trailer ID Entry link is SAVEDTRAILER
And I validate the 1st Row of the Team Entry link is SAVEDTEAM
And I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Driver Entry link is SAVEDDRIVER
And I validate the 1st Row of the Status Entry link is "INACTIVE"
And I validate the 1st Row of the VIN Entry link is SAVEDVIN
And I validate the 1st Row of the License Entry link is SAVEDLICENSE
And I validate the 1st Row of the State Entry link is "IN"
And I validate the 1st Row of the Year Entry link is "1111"
And I validate the 1st Row of the Make Entry link is "BATCHMAKE"
And I validate the 1st Row of the Model Entry link is "BATCHMODEL"
And I validate the 1st Row of the Color Entry link is "BATCHCOLOR"
And I validate the 1st Row of the Weight Entry link is "1111"
And I validate the 1st Row of the Odometer Entry link is "999999"
And I validate the 2nd Row of the Trailer ID Entry link is SAVEDTRAILER2
And I validate the 2nd Row of the Team Entry link is SAVEDTEAM2
And I validate the 2nd Row of the Device Entry link is SAVEDDEVICE2
And I validate the 2nd Row of the Vehicle Entry link is SAVEDVEHICLE2
And I validate the 2nd Row of the Driver Entry link is SAVEDDRIVER2
And I validate the 2nd Row of the Status Entry link is "INACTIVE"
And I validate the 2nd Row of the VIN Entry link is SAVEDVIN2
And I validate the 2nd Row of the License Entry link is SAVEDLICENSE2
And I validate the 2nd Row of the State Entry link is "IN"
And I validate the 2nd Row of the Year Entry link is "1111"
And I validate the 2nd Row of the Make Entry link is "BATCHMAKE"
And I validate the 2nd Row of the Model Entry link is "BATCHMODEL"
And I validate the 2nd Row of the Color Entry link is "BATCHCOLOR"
And I validate the 2nd Row of the Weight Entry link is "1111"
And I validate the 2nd Row of the Odometer Entry link is "999999"
!-- Now I delete the entries so it will run just fine on the next run through
And I click the 1st Row of the Trailer ID Entry link
And I control click the 2nd Row of the Trailer ID Entry link
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

Scenario: Assets - Trailers - Trailer ID is more than 30 characters (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "1234567890123456789012345678901" into the Trailer ID textfield
Then I validate the Trailer ID Error text is present
And I validate the Trailer ID Error text is "The trailer id exceeds 30 characters."
And I click the Cancel button
And I click the New_ button
And I validate the Trailer ID Error text is not visible

Scenario: Assets - Trailers - Trailer ID is more than 30 characters (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "1234567890123456789012345678901" into the Trailer ID textfield
Then I validate the Trailer ID Error text is present
And I validate the Trailer ID Error text is "The trailer id exceeds 30 characters."
And I click the Cancel button
And I click the Edit button
And I validate the Trailer ID Error text is not visible

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
And I validate the VIN Error text is not visible

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
And I validate the VIN Error text is not visible

Scenario: Assets - Trailers - Make is more than 22 characters (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "abcdefghijklmn123456789" into the Make textfield
Then I validate the Make Error text is present
And I validate the Make Error text is "The trailer make exceeds 22 characters."
And I click the Cancel button
And I click the New_ button
And I validate the Make Error text is not visible

Scenario: Assets - Trailers - Make is more than 22 characters (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "abcdefghijklmn123456789" into the Make textfield
Then I validate the Make Error text is present
And I validate the Make Error text is "The trailer make exceeds 22 characters."
And I click the Cancel button
And I click the Edit button
And I validate the Make Error text is not visible

Scenario: Assets - Trailers - Model is more than 22 characters (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "abcdefghijklmn123456789" into the Model textfield
Then I validate the Model Error text is present
And I validate the Model Error text is "The trailer model exceeds 22 characters."
And I click the Cancel button
And I click the New_ button
And I validate the Model Error text is not visible

Scenario: Assets - Trailers - Model is more than 22 characters (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "abcdefghijklmn123456789" into the Model textfield
Then I validate the Model Error text is present
And I validate the Model Error text is "The trailer model exceeds 22 characters."
And I click the Cancel button
And I click the Edit button
And I validate the Model Error text is not visible

Scenario: Assets - Trailers - Odometer is non-numeric characters (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "a" into the Odometer textfield
Then I validate the Odometer Error text is present
And I validate the Odometer Error text is "The trailer odometer can be empty or contain only numbers."
And I click the Cancel button
And I click the New_ button
And I validate the Odometer Error text is not visible

Scenario: Assets - Trailers - Odometer is non-numeric characters (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "a" into the Odometer textfield
Then I validate the Odometer Error text is present
And I validate the Odometer Error text is "The trailer odometer can be empty or contain only numbers."
And I click the Cancel button
And I click the Edit button
And I validate the Odometer Error text is not visible

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
And I validate the Year Error text is not visible
And I type "01234" into the Year textfield
And I validate the Year Error text is present
And I click the Cancel button
And I click the New_ button
And I validate the Year Error text is not visible

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
And I validate the Year Error text is not visible
And I type "01234" into the Year textfield
And I validate the Year Error text is present
And I click the Cancel button
And I click the Edit button
And I validate the Year Error text is not visible

Scenario: Assets - Trailers - Year is alpha characters (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "a" into the Year textfield
Then I validate the Year Error text is present
And I validate the Year Error text is "The trailer year can be empty or contain 4 numbers."
And I click the Cancel button
And I click the New_ button
And I validate the Year Error text is not visible

Scenario: Assets - Trailers - Year is alpha characters (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "a" into the Year textfield
Then I validate the Year Error text is present
And I validate the Year Error text is "The trailer year can be empty or contain 4 numbers."
And I click the Cancel button
And I click the Edit button
And I validate the Year Error text is not visible

Scenario: Assets - Trailers - Color exceeds 14 characters (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "abcdef123456789" into the Color textfield
Then I validate the Color Error text is present
And I validate the Color Error text is "The trailer color exceeds 14 characters."
And I click the Cancel button
And I click the New_ button
And I validate the Color Error text is not visible

Scenario: Assets - Trailers - Color exceeds 14 characters (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "abcdef123456789" into the Color textfield
Then I validate the Color Error text is present
And I validate the Color Error text is "The trailer color exceeds 14 characters."
And I click the Cancel button
And I click the Edit button
And I validate the Color Error text is not visible

Scenario: Assets - Trailers - Weight is not numerical (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "a" into the Weight textfield
Then I validate the Weight Error text is present
And I validate the Weight Error text is "The trailer weight can be empty or contain only numbers."
And I click the Cancel button
And I click the New_ button
And I validate the Weight Error text is not visible

Scenario: Assets - Trailers - Weight is not numerical (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "a" into the Weight textfield
Then I validate the Weight Error text is present
And I validate the Weight Error text is "The trailer weight can be empty or contain only numbers."
And I click the Cancel button
And I click the Edit button
And I validate the Weight Error text is not visible

Scenario: Assets - Trailers - License Number exceeds 10 characters (new button)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "a0123456789" into the License Number textfield
Then I validate the License Number Error text is present
And I validate the License Number Error text is "The trailer license exceeds 10 characters."
And I click the Cancel button
And I click the New_ button
And I validate the License Number Error text is not visible

Scenario: Assets - Trailers - License Number exceeds 10 characters (edit button)
Given I navigate to the assets trailers page
When I click the 1st Row of the Trailer ID Entry link
And I click the Edit button
And I type "a0123456789" into the License Number textfield
Then I validate the License Number Error text is present
And I validate the License Number Error text is "The trailer license exceeds 10 characters."
And I click the Cancel button
And I click the Edit button
And I validate the License Number Error text is not visible

