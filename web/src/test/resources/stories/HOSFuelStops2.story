Test Case for TF740

Meta:
@page login
@testFolder TF740

Narrative: 

Scenario: TC5627: HOS - Fuel Stops - Add Fuel Stop: generate error messages for required fields
Given I am logged in
When I click the HOS link
And I click the HOS Fuel Stops link
And I type "waysmart01" into the Vehicle textfield
!--And I click the 1st row from the Vehicle textfield
!--And I click the Add button
!--And I select row 1 from the Driver dropdown
!--And I click the Save Top button
And I click the refresh button

