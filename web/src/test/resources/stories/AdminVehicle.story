Narrative:

In order to show the inthinc admin vehicle and admin users access points features
As varying levels of user capabilities
I want to see all combinations of user capabilities

Scenario: All Access
Given I am logged in as a Super User
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should have access to all access points
And I should be able to edit all access points 

Scenario: No Vehicle Access
Given I am logged in as an AllButVehicle user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to see all access points
And I cannot edit all access points

Scenario: Partial User
Given I am logged in as PartialVehicleUser user
And I am on the Admin Users page	
When I click any user link
And I click the edit button
Then I should be able to see all access points
And I can only edit some access points
And I cannot edit some access points

Scenario: Vehicle Only
Given I am logged in as a VehicleAccessOnly user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to edit vehicle information
And I cannot edit any other access points

Scenario: Wireline Only
Given I am logged in as a WirelineAccessOnly user
And I am on the Admin Users page
When I click on any user link
And I click the edit button
Then I should only be able to edit Wireline information
And I cannot edit any other access points

Scenario: Profile Only
Given I am logged in as an ProfileAccessOnly user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to edit Profile information
And I cannot edit any other access points

Scenario: Assignment Only
Given I am logged in as an AssignmentAccessOnly user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to edit Assignment information
And I cannot edit any other access points

Scenario: Speed and Sensitivity Only
Given I am logged in as a SpeedAndSensitivityAccessOnly user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to edit Speed and Sensitivity information
And I cannot edit any other access points