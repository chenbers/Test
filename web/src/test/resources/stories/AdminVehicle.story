Meta:
@page Vehicle Users

Narrative:

In order to show the inthinc admin vehicle access points features
As varying levels of user capabilities
I want to see all combinations of user capabilities

Scenario: Can view and edit all vehicle information
Given I am logged in as a user in a role that has all accesspoints
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit Vehicle information
And I should be able to edit Wireline information
And I should be able to edit Profile information
And I should be able to edit Assignment information
And I should be able to edit "Speed and Sensitivity" information
And I should be able to save

Scenario: Can't edit any vehicle information
Given I am logged in as a user in a role
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should not be able to edit Vehicle information
And I should not be able to edit Wireline information
And I should not be able to edit Profile information
And I should not be able to edit Assignment information
And I should not be able to edit "Speed and Sensitivity" information

Scenario: Can edit Vehicle information only
Given I am logged in as a VehicleAccessOnly user
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit Vehicle information
And I should not be able to edit Wireline information
And I should not be able to edit Profile information
And I should not be able to edit Assignment information
And I should not be able to edit "Speed and Sensitivity" information
And I should not be able to save

Scenario: Can edit Wireline information only
Given I am logged in as a user in a role with Wireline
And I am on the "Admin Users" page
When I click on any user link
And I click the edit button
Then I should be able to edit Wireline information
And I should not be able to edit Vehicle information
And I should not be able to edit Profile information
And I should not be able to edit Assignment information
And I should not be able to edit "Speed and Sensitivity" information
And I should not be able to save

Scenario: Can edit Profile information only
Given I am logged in as a user in a role with Profile accesspoint
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit Profile information
And I should not be able to edit Wireline information
And I should not be able to edit Vehicle information
And I should not be able to edit Assignment information
And I should not be able to edit "Speed and Sensitivity" information
And I should not be able to save

Scenario: Can edit Assignment information only
Given I am logged in as a user in a role with Assignment accesspoint
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit Assignment information
And I should not be able to edit Wireline information
And I should not be able to edit Vehicle information
And I should not be able to edit Profile information
And I should not be able to edit "Speed and Sensitivity" information
And I should not be able to save

Scenario: Can edit "Speed and Sensitivity" information Only
Given I am logged in as a user in a role with "Speed and Sensitivity" accesspoint
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit "Speed and Sensitivity" information
And I should not be able to edit Wireline information
And I should not be able to edit Vehicle information
And I should not be able to edit Assignment information
And I should not be able to edit Profile information
And I should not be able to save