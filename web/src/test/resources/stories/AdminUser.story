Test Cases for US4735/US4739

Meta:
@page Admin Users

Narrative:

In order to show the inthinc admin vehicle and admin users access points features
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
And I should be able to edit notification information

