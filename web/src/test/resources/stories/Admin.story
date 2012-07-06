Login Test Cases for TF391

Meta:
@page login
@testFolder TF391

Narrative: Test the admin users page to see that it exists

Scenario: TC147: Admin - UI
Given I am logged in as a "Admin" user
When I am on the Admin page
Then I confirm the Admin page contains all necessary elements