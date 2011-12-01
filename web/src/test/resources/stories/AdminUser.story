Test Cases for US4735/US4739

Meta:
@page Admin Users

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

Scenario: No User Access
Given I am logged in as an AllButUser user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to see all access points
And I cannot edit all access points

Scenario: Partial User
Given I am logged in as PartialUser user
And I am on the Admin Users page	
When I click any user link
And I click the edit button
Then I should be able to see all access points
And I can only edit some access points
And I cannot edit some access points

Scenario: User Only
Given I am logged in as a UserAccessOnly user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to edit user information
And I cannot edit any other access points

Scenario: Driver Only
Given I am logged in as a DriverAccessOnly user
And I am on the Admin Users page
When I click on any user link
And I click the edit button
Then I should only be able to edit driver information
And I cannot edit any other access points

Scenario: Employee Only
Given I am logged in as an EmployeeAccessOnly user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to edit Employee information
And I cannot edit any other access points

Scenario: RFID Only
Given I am logged in as an RFIDAccessOnly user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to edit RFID information
And I cannot edit any other access points

Scenario: Login Only
Given I am logged in as a LoginAccessOnly user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to edit Login information
And I cannot edit any other access points

Scenario: Notifications Only
Given I am logged in as an NotificationsAccessOnly user
And I am on the Admin Users page
When I click any user link
And I click the edit button
Then I should be able to edit Notifications information
And I cannot edit any other access points