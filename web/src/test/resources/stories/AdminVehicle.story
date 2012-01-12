Meta:
@page Vehicle Users

Narrative:

In order to show the inthinc admin vehicle access points features
As varying levels of user capabilities
I want to see all combinations of user capabilities

Scenario: Can view all vehicle information
Given I am logged in as a user in a role that has the accesspoints "Vehicles: View"
And I am on the "Admin Vehicles" page
When I click any user link
And I click the edit button
Then I should not be able to edit Vehicle information
And I should not be able to edit Wireline information
And I should not be able to edit Profile information
And I should not be able to edit Assignment information
And I should not be able to edit "Speed and Sensitivity" information
And I should not be able to save

Scenario: Can edit all vehicle information
Given I am logged in as a user in a role that has the accesspoints "Vehicles: Add/Edit"
And I am on the "Admin Vehicles" page
When I click any user link
And I click the edit button
Then I should be able to edit Vehicle information
And I should be able to edit Wireline information
And I should be able to edit Profile information
And I should be able to edit Assignment information
And I should be able to edit "Speed and Sensitivity" information
And I should be able to save

Scenario: Can assign drivers to vehicles
Given I am logged in as a user in a role that has the accesspoints "Vehicles: Assign Driver"
And I am on the "Admin Vehicles" page
When I click any user link
And I click the edit button
Then I should be able to assign a driver to a vehicle
And I should not be able to edit Vehicle information
And I should not be able to edit Wireline information
And I should not be able to edit Profile information
And I should not be able to edit Assignment information
And I should not be able to edit "Speed and Sensitivity" information
And I should be able to save

Scenario: Can edit Wireline information only
Given I am logged in as a user in a role that has the accesspoints "Vehicles: Edit Wireline"
And I am on the "Admin Users" page
When I click on any user link
And I click the edit button
Then I should be able to edit Wireline information
And I should not be able to edit Vehicle information
And I should not be able to edit Profile information
And I should not be able to edit Assignment information
And I should not be able to edit "Speed and Sensitivity" information
And I should be able to save

Scenario: Can edit "Speed and Sensitivity" information Only
Given I am logged in as a user in a role that has the accesspoints "Vehicles: Edit Speed and Sensitivity"
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit "Speed and Sensitivity" information
And I should not be able to edit Wireline information
And I should not be able to edit Vehicle information
And I should not be able to edit Assignment information
And I should not be able to edit Profile information
And I should be able to save