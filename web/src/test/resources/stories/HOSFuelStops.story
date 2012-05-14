Test Case for TF740

Meta:
@page login
@testFolder TF740

Narrative: 

Scenario: TC5627: HOS - Fuel Stops - Add Fuel Stop: generate error messages for required fields
Given I am logged in as a "Admin" user
When I click the HOS link
And I click the FuelStops link
And I type a Valid Vehicle Name into the Vehicle textfield
And I click the Add button
And I click the Save button
Then I validate the text "2 error(s) occurred. Please verify all the data entered is correct." is in the errorMaster text
And I validate the text "VehicleFuel is required." is in the errorVehicleFuel text
And I validate the text "Driver is required" is in the errorDriver text
And I click the Cancel button
And I click the Add button
And I select a date in the future
And I type "123" into the VehicleFuel textfield
And I select a valid driver from the Driver drop down
And I click the Save button
And I validate the text "1 error(s) occurred. Please verify all the data entered is correct." is in the errorMaster text
And I validate the text "Date/Time in the future is not valid." is in the errorDate text
And I click the Cancel button

Scenario: TC5628: HOS - Fuel Stops - Cancel Add Fuel Stop
Given I am logged in as a "Admin" user
When I click the HOS link
And I click the FuelStops link
And I type a Valid Vehicle Name into the Vehicle textfield
And I click the Add button
And I validate the valueVehicle text is present
And I validate the timeMessage text is present
And I type "789" into the Trailer textfield
And I type "789" into the VehicleFuel textfield
And I type "789" into the TrailerFuel textfield
And I select a valid driver from the Driver drop down
And I validate the valueLocation text is present
And I click the Cancel button
And I click the Add button
Then I validate the Trailer textfield is ""
And I validate the VehicleFuel textfield is ""
And I validate the TrailerFuel textfield is ""
And I validate the Driver drop down is ""
And I click on the Reports link
When I click the HOS link
And I click the Fuel Stops link
And I type a Valid Vehicle Name into the Vehicle textfield
And I validate the Fuel Stops table is blank

Scenario: TC5631: HOS - Fuel Stops - Add Fuel Stop
Given I am logged in as a "Admin" user
When I click the HOS link
And I click the Fuel Stops link
And I type a valid Vehicle Name into the Vehicle textfield
And I click the Add button
And I validate the valueVehicle text is present
And I validate the timeMessage text is present
And I type "123" into the Trailer textfield
And I type "123" into the VehicleFuel textfield
And I type "123" into the TrailerFuel textfield
And I select a valid driver from the Driver drop down
And I save the Driver drop down as CurrentDriver 
And I validate the valueLocation text is present
And I click the Save button
And I click the Edit link
Then I validate the Trailer textfield is "123"
And I validate the VehicleFuel textfield is "123"
And I validate the TrailerFuel textfield is "123"
And I validate the Driver drop down is CurrentDriver
And I click on the Reports link
And I click the HOS link
And I click the Fuel Stops link
And I type a Valid Vehicle Name into the Vehicle textfield
And I validate the Fuel Stops table is not ""

Scenario: TC5632: HOS - Fuel Stops - Cancel Edit Fuel Stop
Given I am logged in as a "Admin" user
When I click the HOS link
And I click the Fuel Stops link
And I type a valid Vehicle Name into the Vehicle textfield
And I click the Add button
And I validate the valueVehicle text is present
And I validate the timeMessage text is present
And I type "123" into the Trailer textfield
And I type "123" into the VehicleFuel textfield
And I type "123" into the TrailerFuel textfield
And I select a valid driver from the Driver drop down
And I save the Driver drop down as CurrentDriver 
Then I validate the valueLocation text is present
And I click the Save button
And I save the Fuel Stops table as SavedTable
And I click the Edit link
And I move the currently selected date back by 1
And I move the currently selected hour back by 1
And I type "456" into the Trailer textfield
And I type "456" into the VehicleFuel textfield
And I type "456" into the TrailerFuel textfield
And I select a new valid driver from the Driver drop down
And I click the Cancel button
And I validate the Fuel Stop table is SavedTable

Scenario: TC5630: HOS - Fuel Stops - Edit Fuel Stop
Given I am logged in as a "Admin" user
When I click the HOS link
And I click the Fuel Stops link
And I type a valid Vehicle Name into the Vehicle textfield
And I save the Vehicle Name as CurrentVehicle
And I click the Refresh button
And I save the Fuel Stops table as SavedTable
And I click the Edit link
And I move the currently selected date back by 1
And I move the currently selected hour back by 1
And I type "789" into the Trailer textfield
And I type "789" into the VehicleFuel textfield
And I type "789" into the TrailerFuel textfield
And I select a new valid driver from the Driver drop down
And I click the Save button
Then I validate the Fuel Stop table is SavedTable
And I click on the Reports link
And I click the HOS link
And I click the Fuel Stops link
And I type CurrentVehicle into the Vehicle textfield
And I validate the Fuel Stop table is SavedTable

Scenario: TC5633: HOS - Fuel Stops - Cancel Delete Fuel Stop
Given I am logged in as a "Admin" user
When I click the HOS link
And I click the Fuel Stops link
And I type a valid "Vehicle Name WITH FUEL STOPS" into the Vehicle textfield
And I save the Vehicle Name as CurrentVehicle
And I click the Refresh button
And I save the first entry in the Fuel Stops table as SavedEntry 
And I check the checkbox of the first entry in the Fuel Stops table
And I click the Delete button
And the FuelStops popup opens
And I click the Cancel button
And the FuelStops popup closes
And I click on the Reports link
And I click the HOS link
And I click the Fuel Stops link
And I type CurrentVehicle into the Vehicle textfield
And I click the Refresh button
Then I validate the first entry in the Fuel Stop table is SavedEntry

Scenario: TC5629: HOS - Fuel Stops - Delete Fuel Stop Entry
Given I am logged in as a "Admin" user
When I click the HOS link
And I click the Fuel Stops link
And I type a valid "Vehicle Name WITH FUEL STOPS" into the Vehicle textfield
And I save the Vehicle Name as CurrentVehicle
And I click the Refresh button
And I save the first entry in the Fuel Stops table as SavedEntry 
And I check the checkbox of the first entry in the Fuel Stops table
And I click the Delete button
And the FuelStops popup opens
And I click the Delete button
And the FuelStops popup closes
And I click on the Reports link
And I click the HOS link
And I click the Fuel Stops link
And I type CurrentVehicle into the Vehicle textfield
And I click the Refresh button
Then I validate the first entry in the Fuel Stop table is not SavedEntry

Scenario: TC5702: HOS - Fuel Stops - Edit Columns
Given I am logged in as a "Admin" user
When I click the HOS link
And I click the Fuel Stops link
And I click on the Edit Columns button
And the EditColumns popup opens
And I uncheck row 3
And I uncheck row 7
And I click the Save button
And the EditColumns popup closes
Then I validate the SortVehicle column is not present
And I validate the SortVehicleFuel column is not present
And the EditColumns popup opens
And I check row 3
And I check row 7
And I click the Save button
And the EditColumns popup closes
Then I validate the SortVehicle column is present
And I validate the SortVehicleFuel column is present

Scenario: TC5700: HOS - Fuel Stops - Vehicle field Tiwi
Given I am logged in as a "Admin Tiwi" user
When I click the HOS link
And I click the Fuel Stops link
And I type "" into the Vehicle textfield
And I click the Refresh button
Then I validate the Vehicle textfield is blank
And I type a valid "Vehicle Tiwi" into the Vehicle textfield
And I validate the GetSuggestion textfield is ""
And I type "" into the Vehicle textfield
And I type a valid Vehicle Name "HOS" into the Vehicle textfield
And I save the Vehicle Name as CurrentVehicle
And I click the Refresh button
And I validate the GetSuggestion textfield is CurrentVehicle
And I type "" into the Vehicle textfield
And I type a valid Vehicle Name "HOS/IFTA" into the Vehicle textfield
And I save the Vehicle Name as CurrentVehicle2
And I click the Refresh button
And I validate the GetSuggestion textfield is CurrentVehicle2
And I click the Add button
And I validate I am on the Add Fuel page
And I click the Cancel button
And I validate I am on the Fuel Stops page

Scenario: TC5703: HOS - Fuel Stops - Fuel fields
Given I am logged in as a "Admin Tiwi" user
When I click the My Account link
And I click the Edit button
And I select "English" from the Measurement dropdown
And I click the Save button
And I click the HOS link
And I click the Fuel Stops link
And I type a valid Vehicle Name "HOS/IFTA" into the Vehicle textfield
And I click the Refresh button
And I click the Add button
And I type "123ABC" into the Trailer textfield
And I type "abcdefg" into the VehicleFuel textfield
And I type "abcdefg" into the TrailerFuel textfield
And I select a valid driver from the Driver drop down
And I click the Save button
Then I validate the text "3 error(s) occurred. Please verify all the data entered is correct." is in the errorMaster text
And I validate the text "Vehicle or TrailerFuel required." is in the errorBothVehicleAndTrailerFuel text
And I validate the text "Must be a number greater than zero" is in the errorVehicleFuel text
And I validate the text "Must be a number greater than zero" is in the errorTrailerFuel text
And I type "&$#!" into the VehicleFuel textfield
And I type "&$#!" into the TrailerFuel textfield
And I click the Save button
And I validate the text "3 error(s) occurred. Please verify all the data entered is correct." is in the errorMaster text
And I validate the text "Vehicle or TrailerFuel required." is in the errorBothVehicleAndTrailerFuel text
And I validate the text "Must be a number greater than zero" is in the errorVehicleFuel text
And I validate the text "Must be a number greater than zero" is in the errorTrailerFuel text
And I type "-1" into the VehicleFuel textfield
And I type "-1" into the TrailerFuel textfield
And I click the Save button
And I validate the text "3 error(s) occurred. Please verify all the data entered is correct." is in the errorMaster text
And I validate the text "Vehicle or TrailerFuel required." is in the errorBothVehicleAndTrailerFuel text
And I validate the text "Must be a number greater than zero" is in the errorVehicleFuel text
And I validate the text "Must be a number greater than zero" is in the errorTrailerFuel text
And I type "-1" into the VehicleFuel textfield
And I type "-1" into the TrailerFuel textfield
And I click the Save button
And I validate the text "3 error(s) occurred. Please verify all the data entered is correct." is in the errorMaster text
And I validate the text "Vehicle or TrailerFuel required." is in the errorBothVehicleAndTrailerFuel text
And I validate the text "Must be a number greater than zero" is in the errorVehicleFuel text
And I validate the text "Must be a number greater than zero" is in the errorTrailerFuel text
And I type "55.55" into the VehicleFuel textfield
And I type "55.55" into the TrailerFuel textfield
And I click the Save button
And I validate I am on the Fuel Stops page
And I validate the ValueVehicleFuel text is "55.55 Gallons"
And I validate the ValueTrailerFuel text is "55.55 Gallons"
And I click the Add button
And I type "456ABC" into the Trailer textfield
And I type "5" into the VehicleFuel textfield
And I type "5" into the TrailerFuel textfield
And I select a valid driver from the Driver drop down
And I click the Save button
And I validate the ValueVehicleFuel text is "5 Gallons"
And I validate the ValueTrailerFuel text is "5 Gallons"
And I click the My Account link
And I click the Edit button
And I select "Metric" from the Measurement dropdown
And I click the Save button
And I click the HOS link
And I click the Fuel Stops link
And I type a valid Vehicle Name "HOS/IFTA" into the Vehicle textfield
And I click the Refresh button
And I click the Add button
And I type "789ABC" into the Trailer textfield
And I type "55.55" into the VehicleFuel textfield
And I type "55.55" into the TrailerFuel textfield
And I select a valid driver from the Driver drop down
And I click the Save button
And I validate the ValueVehicleFuel text is "55.55 Liters"
And I validate the ValueTrailerFuel text is "55.55 Liters"
And I click the Add button
And I type "123DEF" into the Trailer textfield
And I type "5" into the VehicleFuel textfield
And I type "5" into the TrailerFuel textfield
And I select a valid driver from the Driver drop down
And I click the Save button
And I validate the ValueVehicleFuel text is "5 Liters"
And I validate the ValueTrailerFuel text is "5 Liters"
And I check the SelectAll checkbox
And I click the Delete button
And the HOS Fuel Stops popup opens
And I click the Delete button
And the HOS Fuel Stops popup closes

Scenario: TC5701: HOS - Fuel Stops - Date Range (IFTA Aggregation)
Given I am logged in as a "Admin" user
When I click the HOS link
And I click the Fuel Stops link
And I type a valid Vehicle Name "HOS/IFTA" into the Vehicle textfield
And I click the Refresh button
And I click the Add button
And I type "123ABC" into the Trailer textfield
And I type "6" into the VehicleFuel textfield
And I type "6" into the TrailerFuel textfield
And I select a valid driver from the Driver drop down
And I click the Save button
And I click the first Edit link
And I select the date "4/1/12"
And I click the Save button
And I set the first Date Range to "4/1/12"
And I click the Refresh button
And I validate the first Edit link is not clickable