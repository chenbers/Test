Test Cases for TF420/TF421/TF447/TF469

Meta:
@page login
@testFolder TF420

Narrative: 

Scenario: TC147: Admin - UI
!--Given I am logged in as a "Admin" user
Given I am logged in
When I go to the Admin page
Then I confirm the Admin page contains all necessary elements


