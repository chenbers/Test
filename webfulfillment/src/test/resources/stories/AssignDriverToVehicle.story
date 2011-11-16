Narrative:

In order to assign a driver to a vehicle
as an installer
I want to enter the driver's Employee ID, email, rfid barcode or Driver ID and the Vehicle VIN or ID 
and click a button to assign.

Scenario: TC5977: "Required field" error
Given I am on the assign Driver to Vehicle page
and I have selected the correct Silo
When I click on the Assign Button
Then the "Driver/Vehicle Assignment Error" displays at the top of the page
and the "Required field" error displays to the right of the Driver field
and the "Required field" error displays to the right of the Vehicle field

Scenario: TC5978: "Not Found" error
Given I am on the assign Driver to Vehicle page
and I have selected the correct Silo
and I enter an invalid Driver ID
and I enter an invalid Vehicle ID 
and I click on the Assign button
Then the "Driver/Vehicle Assignment Error" displays at the top of the page
and the "Not Found" error displays to the right of the Driver field
and the "Not Found" error displays to the right of the Vehicle field

Scenario: TC5979: Assign Driver to Vehicle: Employee ID to VIN #
Given I am on the assign Driver to Vehicle page
and I have selected the correct Silo
and I enter a valid Employee ID
and I enter a valid Vehicle VIN number
and I click on the Assign button
Then the "Driver has been assigned to vehicle" displays at the top of the page

Scenario: TC5980: Assign Driver to Vehicle: EMail to VIN #
Given I am on the assign Driver to Vehicle page
and I have selected the correct Silo
and I enter a valid Email
and I enter a valid Vehicle VIN number
and I click on the Assign button
Then the "Driver has been assigned to vehicle" displays at the top of the page

Scenario: TC5981: Assign Driver to Vehicle: RFID Barcode to VIN #
Given I am on the assign Driver to Vehicle page
and I have selected the correct Silo
and I enter a valid RFID Barcode 
and I enter a valid Vehicle VIN number
and I click on the Assign button
Then the "Driver has been assigned to vehicle" displays at the top of the page

Scenario: TC5982: Assign Driver to Vehicle: Driver ID to VIN #
Given I am on the assign Driver to Vehicle page
and I have selected the correct Silo
and I enter a valid Driver ID 
and I enter a valid Vehicle VIN number
and I click on the Assign button
Then the "Driver has been assigned to vehicle" displays at the top of the page

Scenario: TC5983: Assign Driver to Vehicle: Employee ID to Vehicle ID
Given I am on the assign Driver to Vehicle page
and I have selected the correct Silo
and I enter a valid Employee ID
and I enter a valid Vehicle ID
and I click on the Assign button
Then the "Driver has been assigned to vehicle" displays at the top of the page

Scenario: TC5984: Assign Driver to Vehicle: EMail to Vehicle ID
Given I am on the assign Driver to Vehicle page
and I have selected the correct Silo
and I enter a valid Email
and I enter a valid Vehicle ID
and I click on the Assign button
Then the "Driver has been assigned to vehicle" displays at the top of the page

Scenario: TC5985: Assign Driver to Vehicle: RFID Barcode to Vehicle ID
Given I am on the assign Driver to Vehicle page
and I have selected the correct Silo
and I enter a valid RFID Barcode 
and I enter a valid Vehicle ID 
and I click on the Assign button
Then the "Driver has been assigned to vehicle" displays at the top of the page

Scenario: TC5986: Assign Driver to Vehicle: Driver ID to Vehicle ID
Given I am on the assign Driver to Vehicle page
and I have selected the correct Silo
and I enter a valid Driver ID 
and I enter a valid Vehicle ID 
and I click on the Assign button
Then the "Driver has been assigned to vehicle" displays at the top of the page