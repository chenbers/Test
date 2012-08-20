Meta:
@page forms
@testFolder 

Narrative: Test Functionality of the Forms page

Scenario: TC : Create Form link present
Given I am logged in
When I click the Forms link
And I validate I am on the Add Form page
Then I validate the Create Form link is present

Scenario: TC : Working tab present
Given I am logged in
When I click the Forms link
And I validate I am on the Add Form page
Then I validate the Edit link is present

Scenario: TC : Published tab present


Scenario: TC : Records per page dropdown present

Scenario: TC : Search field present

Scenario: TC : Select checkbox present

Scenario: TC : Name column present

Scenario: TC : Base Form ID column present

Scenario: TC : Version column present

Scenario: TC : Description column present

Scenario: TC : Trigger column present

Scenario: TC : Publish column present

Scenario: TC : Publish link present

Scenario: TC : Edit column present

Scenario: TC : Edit link present