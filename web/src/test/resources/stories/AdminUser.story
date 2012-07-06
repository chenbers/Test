Meta:
@page Admin Users

Narrative:

In order to show the admin users access points features
As varying levels of user capabilities
I want to see all combinations of user capabilities

Scenario: Can view but not edit user info
Given I am logged in as a user in a role that does not have the UserInfo accesspoint
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should not be able to edit user information
And I should be able to edit driver information
And I should be able to edit employee information
And I should be able to edit login information
And I should be able to edit RFID information
And I should be able to edit notifications information
And I should be able to save

Scenario: Can view and edit all user info
Given I am logged in as a user in a role that has all accesspoints
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit user information
And I should be able to edit driver information
And I should be able to edit employee information
And I should be able to edit login information
And I should be able to edit RFID information
And I should be able to edit notifications information
And I should be able to save

Scenario: Can edit user info only
Given I am logged in as a user in a role that only has the UserInfo accesspoint
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit user information
And I should not be able to edit driver information
And I should not be able to edit employee information
And I should not be able to edit login information
And I should not be able to edit RFID information
And I should not be able to edit notifications information
And I should be able to save

Scenario: Can edit driver information only
Given I am logged in as a user in a role that only has Driver accesspoint
And I am on the "Admin Users" page
When I click on any user link
And I click the edit button
Then I should be able to edit driver information
And I should not be able to edit employee information
And I should not be able to edit login information
And I should not be able to edit RFID information
And I should not be able to edit notifications information
And I should not be able to edit user information
And I should be able to save

Scenario: Can edit employee information only
Given I am logged in as a user in a role that only has employee accesspoint
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit employee information
And I should not be able to edit driver information
And I should not be able to edit login information
And I should not be able to edit RFID information
And I should not be able to edit notifications information
And I should not be able to edit user information
And I should be able to save

Scenario: Can edit RFID information only
Given I am logged in as a user in a role that only has RFID accesspoint
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit RFID information
And I should not be able to edit employee information
And I should not be able to edit driver information
And I should not be able to edit login information
And I should not be able to edit notifications information
And I should not be able to edit user information
And I should be able to save

Scenario: Can edit Login information only
Given I am logged in as a user in a role that only has Login accesspoint
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit Login information
And I should not be able to edit employee information
And I should not be able to edit driver information
And I should not be able to edit RFID information
And I should not be able to edit notifications information
And I should not be able to edit user information
And I should be able to save

Scenario: Can edit Notifications information only
Given I am logged in as an user in a role that only has Notifications accesspoint
And I am on the "Admin Users" page
When I click any user link
And I click the edit button
Then I should be able to edit Notifications information
And I should not be able to edit employee information
And I should not be able to edit driver information
And I should not be able to edit RFID information
And I should not be able to edit login information
And I should not be able to edit user information
And I should be able to save