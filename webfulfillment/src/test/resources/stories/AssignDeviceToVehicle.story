Narrative:

In order to set a assign a device to a vehicle
As an installer
I want to enter device serial number, IMEI, device ID and Vehicle VIN or Vehicle ID 

Scenario: TC5969: "Required field" error
Given I am on the assign Device to Vehicle page
and I have selected the correct Silo
When I click on the Assign Button
Then the "Device/Vehicle Assignment Error" displays at the top of the page
and the "Required field" error displays to the right of the Device field
and the "Required field" error displays to the right of the Vehicle field

Scenario: TC5970: "Not Found" error
Given I am on the assign Device to Vehicle page
and I have selected the correct Silo
and I enter an invalid Device serial number
and I enter an invalid Vehicle ID 
and I click on the Assign button
Then the "Device/Vehicle Assignment Error" displays at the top of the page
and the "Not Found" error displays to the right of the Device field
and the "Not Found" error displays to the right of the Vehicle field

Scenario: TC5971: Assign Device to Vehicle: Serial # to VIN #
Given I am on the assign Device to Vehicle page
and I have selected the correct Silo
and I enter a valid Device serial number
and I enter a valid Vehicle VIN number
and I click on the Assign button
Then the "Device has been assigned to vehicle" displays at the top of the page

Scenario: TC5972: Assign Device to Vehicle: IMEI to VIN #
Given I am on the assign Device to Vehicle page
and I have selected the correct Silo
and I enter a valid Device IMEI
and I enter a valid Vehicle VIN number
and I click on the Assign button
Then the "Device has been assigned to vehicle" displays at the top of the page

Scenario: TC5973: Assign Device to Vehicle: Device ID to VIN #
Given I am on the assign Device to Vehicle page
and I have selected the correct Silo
and I enter a valid Device ID
and I enter a valid Vehicle VIN number
and I click on the Assign button
Then the "Device has been assigned to vehicle" displays at the top of the page

Scenario: TC5974: Assign Device to Vehicle: Serial # to Vehicle ID
Given I am on the assign Device to Vehicle page
and I have selected the correct Silo
and I enter a valid Device serial number
and I enter a valid Vehicle ID
and I click on the Assign button
Then the "Device has been assigned to vehicle" displays at the top of the page

Scenario: TC5975: Assign Device to Vehicle: IMEI to Vehicle ID
Given I am on the assign Device to Vehicle page
and I have selected the correct Silo
and I enter a valid Device IMEI
and I enter a valid Vehicle ID
and I click on the Assign button
Then the "Device has been assigned to vehicle" displays at the top of the page

Scenario: TC5976: Assign Device to Vehicle: Device ID to Vehicle ID
Given I am on the assign Device to Vehicle page
and I have selected the correct Silo
and I enter a valid Device ID
and I enter a valid Vehicle ID
and I click on the Assign button
Then the "Device has been assigned to vehicle" displays at the top of the page

