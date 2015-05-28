Test Cases for TF388/TF416/TF547 

Meta:
@page login
@testFolder TF388
@testFolder TF416
@testFolder TF547

Narrative:


Scenario: TC1543: Reports - Drivers - Driver Link
Given I am logged in
When I click the Reports link
And I click the Drivers link
And I save the 1st Row of the Driver Value link as SAVEDDRIVER
And I click the 1st Row of the Driver Value link
Then I validate the Driver Name link is SAVEDDRIVER
