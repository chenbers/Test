Meta:
@page login

Narrative:Test the functionality of the Reports Trailers page

Scenario: Assets - Trailers - UI
Given I navigate to the assets trailers page
Then I validate I am on the Assets Trailers page
And I validate the Records Per Page Label text is present
And I validate the Records Per Page dropdown is present
And I validate the Search Label text is present
And I validate the Search textfield is present
And I validate the Show Hide Columns link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Team link is present
And I validate the Sort By Device link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Driver link is present
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
And I validate the Previous Disabled link is  present
And I validate the Next link is present
And I validate the Select A Row Label text is present
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
And I validate the Trailer Profile Label text is present
And I validate the Trailer ID Label text is present
And I validate the Status Label text is present
And I validate the Trailer Assignment Label text is present
And I validate the Team Label text is present
And I validate the Device Assignment Label text is present
And I validate the Assigned Device Label text is present
And I validate the Vehicle Assignment Label text is present
And I validate the Assigned Vehicle Label text is present
And I validate the Driver Assignment Label text is present
And I validate the Assigned Driver Label text is present
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
And I validate the New_ button is visible
And I validate the Edit button is not visible
And I validate the VIN textfield is present
And I validate the Make textfield is present
And I validate the Year textfield is present
And I validate the Color textfield is present
And I validate the Weight textfield is present
And I validate the License Number textfield is present
And I validate the Trailer ID textfield is present
And I validate the Status textfield is present
And I click the Cancel button
And I validate the Cancel button is not visible
And I validate the Save button is not visible
And I validate the New_ button is visible
And I validate the Edit button is not visible
And I validate the VIN textfield is not present
And I validate the Make textfield is not present
And I validate the Year textfield is not present
And I validate the Color textfield is not present
And I validate the Weight textfield is not present
And I validate the License Number textfield is not present
And I validate the Trailer ID textfield is not present
And I validate the Status textfield is not present
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
Then I validate the Sort By Team link is not present
And I click the Show Hide Columns link
And I check the Team checkbox
And I click the Show Hide Columns link
And I validate the Sort By Team link is present
When I click the Show Hide Columns link
And I uncheck the Device checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Device link is not present
And I click the Show Hide Columns link
And I check the Device checkbox
And I click the Show Hide Columns link
And I validate the Sort By Device link is present
When I click the Show Hide Columns link
And I uncheck the Vehicle checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Vehicle link is not present
And I click the Show Hide Columns link
And I check the Vehicle checkbox
And I click the Show Hide Columns link
And I validate the Sort By Vehicle link is present
When I click the Show Hide Columns link
And I uncheck the Driver checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Driver link is not present
And I click the Show Hide Columns link
And I check the Driver checkbox
And I click the Show Hide Columns link
And I validate the Sort By Driver link is present
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
Then I validate the Sort By State link is not present
And I click the Show Hide Columns link
And I check the State checkbox
And I click the Show Hide Columns link
And I validate the Sort By State link is present
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

Scenario: Assets - Trailers - Bookmark Entry 
Given I navigate to the assets trailers page
When I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
And I log back in
Then I validate I am on the Assets Trailers page

Scenario: Assets - Trailers - Group Link
Given I navigate to the assets trailers page
When I save the 1st Row of the Team Entry link as SAVEDGROUP
And I click the 1st Row of the Team Entry link
Then I validate the Driver Team Value text is SAVEDGROUP

Scenario: Assets - Trailers - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER
Given I navigate to the assets trailers page
When I click the Sort By Trailer ID link
And I click the Sort By Team link
And I click the Sort By Device link
And I click the Sort By Vehicle link
And I click the Sort By Driver link
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

Scenario: Assets - Trailers - New Trailer (cancel button - changes)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "FAKEVINNUMBER" into the VIN textfield
And I type "Ford" into the Make textfield
And I type "2013" into the Year textfield
And I type "Race Red" into the Color textfield
And I type "21000" into the Weight textfield
And I type "INTHINC" into the License Number textfield
And I type "Active" into the Status textfield
And I click the Cancel button
And I type "FAKEVINNUMBER" into the Search textfield
Then I validate the No Matching Records text is present

Scenario: Assets - Trailers - New Trailer (cancel button - no changes)
Given I navigate to the assets trailers page
When I click the New_ button
Then I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the Trailer ID textfield is ""
And I validate the Status textfield is ""
And I click the Cancel button
And I click the New_ button
And I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the Trailer ID textfield is ""
And I validate the Status textfield is ""

Scenario: Assets - Trailers - New Trailer (edit button - no changes) TEST WILL FAIL TILL SOME THINGS ARE FIXED ON THE PAGE
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
And I validate the State text is SAVEDSTATE
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
And I validate the State text is SAVEDSTATE
And I validate the Trailer ID textfield is SAVEDTRAILER
And I validate the Status textfield is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I click the Cancel button
And I validate the VIN text is SAVEDVIN
And I validate the Make text is SAVEDMAKE
And I validate the Year text is SAVEDYEAR
And I validate the Color text is SAVEDCOLOR
And I validate the Weight text is SAVEDWEIGHT
And I validate the License Number text is SAVEDLICENSE
And I validate the State text is SAVEDSTATE
And I validate the Trailer ID text is SAVEDTRAILER
And I validate the Status text is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER

Scenario: Assets - Trailers - New Trailer (edit button - changes) TEST WILL FAIL TILL SOME THINGS ARE FIXED ON THE PAGE
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
And I validate the State text is SAVEDSTATE
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
And I validate the State text is SAVEDSTATE
And I validate the Trailer ID textfield is SAVEDTRAILER
And I validate the Status textfield is SAVEDSTATUS
And I validate the Team text is SAVEDTEAM
And I validate the Assigned Device text is SAVEDDEVICE
And I validate the Assigned Vehicle text is SAVEDVEHICLE
And I validate the Assigned Driver text is SAVEDDRIVER
And I type "ANEWVINNUMBER" into the VIN textfield
And I type "ANEWMAKE" into the Make textfield
And I type "ANEWYEAR" into the Year textfield
And I type "ANEWCOLOR" into the Color textfield
And I type "ANEWWEIGHT" into the Weight textfield
And I type "ANEWLICENSE" into the License Number textfield
And I type "ANEWTRAILER" into the Trailer ID textfield
And I type "ANEWSTATUS" into the Status textfield
And I click the Save button
And I validate the VIN text is "ANEWVINNUMBER"
And I validate the Make text is "ANEWMAKE"
And I validate the Year text is "ANEWYEAR"
And I validate the Color text is "ANEWCOLOR"
And I validate the Weight text is "ANEWWEIGHT"
And I validate the License Number text is "ANEWLICENSE"
And I validate the Trailer ID text is "ANEWTRAILER"
And I validate the Status text is "ANEWSTATUS"
And I validate the 1st Row of the Trailer ID Entry link is "ANEWTRAILER"
And I validate the 1st Row of the Team Entry link is SAVEDTEAM
And I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Driver Entry link is SAVEDDRIVER
And I validate the 1st Row of the Status Entry link is SAVEDSTATUS
And I validate the 1st Row of the VIN Entry link is "ANEWVINNUMBER"
And I validate the 1st Row of the License Entry link is "ANEWLICENSE"
And I validate the 1st Row of the State Entry link is SAVEDSTATE
And I validate the 1st Row of the Year Entry link is "ANEWYEAR"
And I validate the 1st Row of the Make Entry link is "ANEWMAKE"
And I validate the 1st Row of the Model Entry link is SAVEDMODEL
And I validate the 1st Row of the Color Entry link is "ANEWCOLOR"
And I validate the 1st Row of the Weight Entry link is "ANEWWEIGHT"
And I validate the 1st Row of the Odometer Entry link is SAVEDODOMETER

Scenario: Assets - Trailers - New Trailer (save button - changes)
Given I navigate to the assets trailers page
When I click the New_ button
And I type "FAKEVINNUMBER" into the VIN textfield
And I type "Ford" into the Make textfield
And I type "2013" into the Year textfield
And I type "Race Red" into the Color textfield
And I type "21000" into the Weight textfield
And I type "INTHINC" into the License Number textfield
And I type "Active" into the Status textfield
And I click the Save button
And I type "FAKEVINNUMBER" into the Search textfield
Then I validate the 1st Row of the VIN textfield is "FAKEVINNUMBER"
And I validate the 1st Row of the Make textfield is "Ford"
And I validate the 1st Row of the Year textfield is "2013"
And I validate the 1st Row of the Color textfield is "Race Red"
And I validate the 1st Row of the Weight textfield is "21000"
And I validate the 1st Row of the License Number textfield is "INTHINC"
And I validate the 1st Row of the Status textfield is "Active"

Scenario: Assets - Trailers - New Trailer (save button - error message)
Given I navigate to the assets trailers page
When I click the New_ button
And I click the Save button
Then I verify "Please include all required fields" is on the page
And I press the Enter key
And I validate the VIN textfield is ""
And I validate the Make textfield is ""
And I validate the Year textfield is ""
And I validate the Color textfield is ""
And I validate the Weight textfield is ""
And I validate the License Number textfield is ""
And I validate the Status textfield is ""