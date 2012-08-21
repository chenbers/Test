Test Case for TF740

Meta:
@page login
@testFolder TF740

Narrative: 

Scenario: TC5627: HOS - Fuel Stops - Add Fuel Stop: generate error messages for required fields
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Add button
And I select row 1 from the Driver dropdown
And I click the Save Top button
Then I validate the Error Master text is "2 error(s) occurred. Please verify all the data entered is correct."
And I validate the Error Vehicle Fuel text is "Vehicle fuel is required."
And I validate the Error Driver text is "Driver is required"
And I click the Top Cancel button
And I click the Add button
And I select 1 day in the future from the Date dropdown
And I type "5627" into the Vehicle Fuel textfield
And I select "tiwi 00" from the Driver dropdown
And I click the Save Top button
And I validate the Error Master text is "1 error(s) occurred. Please verify all the data entered is correct." 
And I validate the Error Date text is "Date/Time in the future is not valid."

Scenario: TC5628: HOS - Fuel Stops - Cancel Add Fuel Stop
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Add button
And I validate the Entry Vehicle text is present
And I validate the Time Message text is present
And I type "5628" into the Trailer textfield
And I type "5628" into the Vehicle Fuel textfield
And I type "5628" into the Trailer Fuel textfield
And I select "tiwi 00" from the Driver dropdown
And I validate the Entry Location text is present
And I click the Top Cancel button
And I click the Add button
Then I validate the Trailer textfield is ""
And I validate the Vehicle Fuel textfield is ""
And I validate the Trailer Fuel textfield is ""
And I validate the Driver dropdown is ""
And I click the Reports link
And I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I validate the 1st Row of the Date Time text is not present

Scenario: TC5631: HOS - Fuel Stops - Add Fuel Stop
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Refresh button
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes
And I click the Add button
Then I validate the Entry Vehicle text is present
And I validate the Time Message text is present
And I type "5631" into the Trailer textfield
And I type "5,631.00" into the Vehicle Fuel textfield
And I type "5,631.00" into the Trailer Fuel textfield
And I select "tiwi 00" from the Driver dropdown
And I validate the Entry Location text is present
And I save the Date dropdown as DATE
And I save the Hours textfield as HOURS
And I save the Minutes textfield as MINUTES
And I save the Seconds textfield as SECONDS
And I save the Am Pm dropdown as AMPM
And I combine DATE with " " with HOURS with ":" with MINUTES with ":" with SECONDS with " " with AMPM and save them as DATETIME
And I click the Save Top button
And I click the Refresh button
And I click the 1st Row of the Entry Edit link
And I validate the Trailer textfield is "5631"
And I validate the Vehicle Fuel textfield is "5,631.00"
And I validate the Trailer Fuel textfield is "5,631.00"
And I validate the Driver dropdown is "tiwi 00"
And I click the Reports link
And I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I validate the 1st Row of the Date Time text contains DATETIME
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes

Scenario: TC5632: HOS - Fuel Stops - Cancel Edit Fuel Stop
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Refresh button
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes
And I click the Add button
And I validate the Entry Vehicle text is present
And I validate the Time Message text is present
And I type "111" into the Trailer textfield
And I type "111" into the Vehicle Fuel textfield
And I type "111" into the Trailer Fuel textfield
And I select "tiwi 00" from the Driver dropdown 
Then I validate the Entry Location text is present
And I click the Save Top button
And I click the Refresh button
And I save the 1st Row of the Date Time text as DATETIME
And I save the 1st Row of the Entry Driver text as DRIVER
And I save the 1st Row of the Entry Vehicle text as VEHICLE
And I save the 1st Row of the Entry Vehicle Fuel text as VEHICLEFUEL
And I save the 1st Row of the Entry Trailer Fuel text as TRAILERFUEL
And I save the 1st Row of the Entry Trailer text as TRAILER
And I save the 1st Row of the Entry Location text as LOCATION
And I save the 1st Row of the Entry Edited text as EDITED
And I click the 1st Row of the Entry Edit link
And I select 1 day in the past from the Date dropdown
And I type "1" into the Hours textfield
And I select "am" from the Am Pm dropdown
And I type "5632" into the Trailer textfield
And I type "5632" into the Vehicle Fuel textfield
And I type "5632" into the Trailer Fuel textfield
And I select "tiwi 01" from the Driver dropdown
And I click the Top Cancel button
And I validate the 1st Row of the Date Time text is DATETIME
And I validate the 1st Row of the Entry Driver text is DRIVER
And I validate the 1st Row of the Entry Vehicle text is VEHICLE
And I validate the 1st Row of the Entry Vehicle Fuel text is VEHICLEFUEL
And I validate the 1st Row of the Entry Trailer Fuel text is TRAILERFUEL
And I validate the 1st Row of the Entry Trailer text is TRAILER
And I validate the 1st Row of the Entry Location text is LOCATION
And I validate the 1st Row of the Entry Edited text is EDITED
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes

Scenario: TC5630: HOS - Fuel Stops - Edit Fuel Stop
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Refresh button
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes
And I click the Refresh button
And I click the Add button
And I type "222" into the Trailer textfield
And I type "222" into the Vehicle Fuel textfield
And I type "222" into the Trailer Fuel textfield
And I select "tiwi 00" from the Driver dropdown
And I click the Save Top button
And I click the Refresh button
And I save the 1st Row of the Date Time text as DATETIME
And I save the 1st Row of the Entry Driver text as DRIVER
And I save the 1st Row of the Entry Vehicle Fuel text as VEHICLEFUEL
And I save the 1st Row of the Entry Trailer Fuel text as TRAILERFUEL
And I save the 1st Row of the Entry Trailer text as TRAILER
And I click the 1st Row of the Entry Edit link
And I select 1 day in the past from the Date dropdown
And I type "1" into the Hours textfield
And I select "am" from the Am Pm dropdown
And I type "5630" into the Trailer textfield
And I type "5630" into the Vehicle Fuel textfield
And I type "5630" into the Trailer Fuel textfield
And I select "tiwi 01" from the Driver dropdown
And I click the Save Top button
And I click the Refresh button
Then I validate the 1st Row of the Date Time text is not DATETIME
And I validate the 1st Row of the Entry Driver text is not DRIVER
And I validate the 1st Row of the Entry Vehicle Fuel text is not VEHICLEFUEL
And I validate the 1st Row of the Entry Trailer Fuel text is not TRAILERFUEL
And I validate the 1st Row of the Entry Trailer text is not TRAILER
And I click the Reports link
And I click the HOS link
And I click the HOS Fuel Stops link
And I validate the 1st Row of the Date Time text is not DATETIME
And I validate the 1st Row of the Entry Driver text is not DRIVER
And I validate the 1st Row of the Entry Vehicle Fuel text is not VEHICLEFUEL
And I validate the 1st Row of the Entry Trailer Fuel text is not TRAILERFUEL
And I validate the 1st Row of the Entry Trailer text is not TRAILER
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes

Scenario: TC5633: HOS - Fuel Stops - Cancel Delete Fuel Stop
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Refresh button
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes
And I click the Refresh button
And I click the Add button
And I type "5633" into the Trailer textfield
And I type "5633" into the Vehicle Fuel textfield
And I type "5633" into the Trailer Fuel textfield
And I select "tiwi 00" from the Driver dropdown
And I click the Save Top button
And I click the Refresh button
And I save the 1st Row of the Date Time text as DATETIME
And I save the 1st Row of the Entry Driver text as DRIVER
And I save the 1st Row of the Entry Vehicle text as VEHICLE
And I save the 1st Row of the Entry Vehicle Fuel text as VEHICLEFUEL
And I save the 1st Row of the Entry Trailer Fuel text as TRAILERFUEL
And I save the 1st Row of the Entry Trailer text as TRAILER
And I save the 1st Row of the Entry Location text as LOCATION
And I save the 1st Row of the Entry Edited text as EDITED
And I check the 1st Row of the Entry Check Box checkbox
And I click the Delete button
And the Delete popup opens
And I click the Cancel button
And the Delete popup closes
And I click the Reports link
And I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Refresh button
Then I validate the 1st Row of the Date Time text is DATETIME
And I validate the 1st Row of the Entry Driver text is DRIVER
And I validate the 1st Row of the Entry Vehicle text is VEHICLE
And I validate the 1st Row of the Entry Vehicle Fuel text is VEHICLEFUEL
And I validate the 1st Row of the Entry Trailer Fuel text is TRAILERFUEL
And I validate the 1st Row of the Entry Trailer text is TRAILER
And I validate the 1st Row of the Entry Location text is LOCATION
And I validate the 1st Row of the Entry Edited text is EDITED
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes
And I click the Refresh button

Scenario: TC5629: HOS - Fuel Stops - Delete Fuel Stop Entry (removed checking location as there is no way to select one)
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Refresh button
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes
And I click the Refresh button
And I click the Add button
And I type "5629" into the Trailer textfield
And I type "5629" into the Vehicle Fuel textfield
And I type "5629" into the Trailer Fuel textfield
And I select "tiwi 00" from the Driver dropdown
And I click the Save Top button
And I click the Refresh button
And I click the Add button
And I type "1111" into the Trailer textfield
And I type "1111" into the Vehicle Fuel textfield
And I type "1111" into the Trailer Fuel textfield
And I select "tiwi 01" from the Driver dropdown
And I click the Save Top button
And I click the Refresh button
And I save the 1st Row of the Date Time text as DATETIME
And I save the 1st Row of the Entry Driver text as DRIVER
And I save the 1st Row of the Entry Vehicle text as VEHICLE
And I save the 1st Row of the Entry Vehicle Fuel text as VEHICLEFUEL
And I save the 1st Row of the Entry Trailer Fuel text as TRAILERFUEL
And I save the 1st Row of the Entry Trailer text as TRAILER
And I check the 1st Row of the Entry Check Box checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes
And I click the Reports link
And I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Refresh button
Then I validate the 1st Row of the Date Time text is not DATETIME
And I validate the 1st Row of the Entry Driver text is not DRIVER
And I validate the 1st Row of the Entry Vehicle text is VEHICLE
And I validate the 1st Row of the Entry Vehicle Fuel text does not contain VEHICLEFUEL
And I validate the 1st Row of the Entry Trailer Fuel text does not contain TRAILERFUEL
And I validate the 1st Row of the Entry Trailer text is not TRAILER
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes

Scenario: TC5702: HOS - Fuel Stops - Edit Columns
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I click the Edit Columns button
And the Edit Columns popup opens
And I uncheck the 1st Row of the Column checkbox
And I uncheck the 2nd Row of the Column checkbox
And I uncheck the 3rd Row of the Column checkbox
And I uncheck the 4th Row of the Column checkbox
And I uncheck the 6th Row of the Column checkbox
And I uncheck the 7th Row of the Column checkbox
And I uncheck the 8th Row of the Column checkbox
And I uncheck the 9th Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is not present
And I validate the Sort By Driver link is not present
And I validate the Sort By Vehicle link is not present
And I validate the Sort By Trailer link is not present
And I validate the Sort By Location link is not present
And I validate the Sort By Vehicle Fuel link is not present
And I validate the Sort By Trailer Fuel link is not present
And I validate the Sort By Edited link is not present
And I click the Edit Columns button
And the Edit Columns popup opens
And I check the 1st Row of the Column checkbox
And I check the 2nd Row of the Column checkbox
And I check the 3rd Row of the Column checkbox
And I check the 4th Row of the Column checkbox
And I check the 6th Row of the Column checkbox
And I check the 7th Row of the Column checkbox
And I check the 8th Row of the Column checkbox
And I check the 9th Row of the Column checkbox
And I click the Save button
And the Edit Columns popup closes
And I validate the Sort By Date Time link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Trailer link is present
And I validate the Sort By Location link is present
And I validate the Sort By Vehicle Fuel link is present
And I validate the Sort By Trailer Fuel link is present
And I validate the Sort By Edited link is present

Scenario: TC5700: HOS - Fuel Stops - Vehicle field Tiwi
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I type "" into the Vehicle textfield
And I click the Refresh button
Then I validate the Vehicle textfield is ""
And I type a valid "Vehicle Tiwi" into the Vehicle textfield
And I validate the Get Suggestion textfield is ""
And I type "" into the Vehicle textfield
And I type a "Valid Vehicle Name HOS" into the Vehicle textfield
And I save the Vehicle Name as CURRENTVEHICLE
And I click the Refresh button
And I validate the Get Suggestion textfield is CURRENTVEHICLE
And I type "" into the Vehicle textfield
And I type a "Valid Vehicle Name HOS/IFTA" into the Vehicle textfield
And I save the Vehicle Name as CURRENTVEHICLE2
And I click the Refresh button
And I validate the Get Suggestion textfield is CURRENTVEHICLE2
And I click the Add button
And I validate I am on the Add Fuel page
And I click the Top Cancel button
And I validate I am on the Fuel Stops page

Scenario: TC5703: HOS - Fuel Stops - Fuel fields
Given I am logged in
When I click the My Account link
And I click the Edit button
And I select "Metric" from the Measurement dropdown
And I click the Save button
And I click the HOS link
And I click the HOS Fuel Stops link
And I type a "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Add button
And I type "123ABC" into the Trailer textfield
And I type "abcdefg" into the Vehicle Fuel textfield
And I type "abcdefg" into the Trailer Fuel textfield
And I select "tiwi 00" from the Driver dropdown
And I click the Save Top button
Then I validate Error Master text is "3 error(s) occurred. Please verify all the data entered is correct."
And I validate Error Both Vehicle And Trailer Fuel text is "Vehicle or Trailer fuel required."
And I validate Error Vehicle Fuel text is "Must be a number greater than zero"
And I validate Error Trailer Fuel text is "Must be a number greater than zero"
And I type "&$#!" into the Vehicle Fuel textfield
And I type "&$#!" into the Trailer Fuel textfield
And I click the Save Top button
And I validate Error Master text is "3 error(s) occurred. Please verify all the data entered is correct."
And I validate Error Both Vehicle And Trailer Fuel text is "Vehicle or Trailer fuel required."
And I validate Error Vehicle Fuel text is "Must be a number greater than zero"
And I validate Error Trailer Fuel text is "Must be a number greater than zero"
And I type "-1" into the Vehicle Fuel textfield
And I type "-1" into the Trailer Fuel textfield
And I click the Save Top button
And I validate Error Master text is "3 error(s) occurred. Please verify all the data entered is correct."
And I validate Error Both Vehicle And Trailer Fuel text is "Vehicle or Trailer fuel required."
And I validate Error Vehicle Fuel text is "Must be a number greater than zero"
And I validate Error Trailer Fuel text is "Must be a number greater than zero"
And I type "55.55" into the Vehicle Fuel textfield
And I type "55.55" into the Trailer Fuel textfield
And I click the Save Top button
And I validate I am on the HOS Fuel Stops page
And I validate the 1st Row of the Entry Vehicle Fuel text contains "55.5"
And I validate the 1st Row of the Entry Vehicle Fuel text contains "Liters"
And I validate the 1st Row of the Entry Trailer Fuel text contains "55.5"
And I validate the 1st Row of the Entry Trailer Fuel text contains "Liters"
And I click the Add button
And I type "456ABC" into the Trailer textfield
And I type "5" into the Vehicle Fuel textfield
And I type "5" into the Trailer Fuel textfield
And I select "tiwi 00" from the Driver dropdown
And I click the Save Top button
And I click the Refresh button
And I validate the 1st Row of the Entry Vehicle Fuel text is "5.00 Liters"
And I validate the 1st Row of the Entry Trailer Fuel text is "5.00 Liters"
And I click the My Account link
And I click the Edit button
And I select "English" from the Measurement dropdown
And I click the Save button
And I click the HOS link
And I click the HOS Fuel Stops link
And I type "TIWI00" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Add button
And I type "789ABC" into the Trailer textfield
And I type "55.55" into the Vehicle Fuel textfield
And I type "55.55" into the Trailer Fuel textfield
And I select "tiwi 00" from the Driver dropdown
And I click the Save Top button
And I click the Refresh button
And I validate the 1st Row of the Entry Vehicle Fuel text is "55.55 Gallons"
And I validate the 1st Row of the Entry Trailer Fuel text is "55.55 Gallons"
And I click the Add button
And I type "123DEF" into the Trailer textfield
And I type "5" into the Vehicle Fuel textfield
And I type "5" into the Trailer Fuel textfield
And I select "tiwi 00" from the Driver dropdown
And I click the Save Top button
And I click the Refresh button
And I validate the 1st Row of the Entry Vehicle Fuel text is "5.00 Gallons"
And I validate the 1st Row of the Entry Trailer Fuel text is "5.00 Gallons"
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes

Scenario: TC5701: HOS - Fuel Stops - Date Range (IFTA Aggregation)
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I type "TEST VEHICLE 1" into the Vehicle textfield
And I click the suggested row 1 from the Vehicle textfield
And I click the Refresh button
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes
And I click the Add button
And I type "123ABC" into the Trailer textfield
And I type "6" into the Vehicle Fuel textfield
And I type "6" into the Trailer Fuel textfield
And I select "tiwi 01" from the Driver dropdown
And I click the Save Top button
And I click the Refresh button
And I click the 1st Row of the Entry Edit link
And I select 30 days in the past from the Date dropdown
And I click the Save Top button
And I click the Refresh button
And I select 31 days in the past from the Start Date dropdown
And I click the Refresh button
And I click the Sort By Date Time link
Then I validate the 1st Row of the Entry Edit link is ""
And I check the Check All checkbox
And I click the Delete button
And the Delete popup opens
And I click the Delete button
And the Delete popup closes