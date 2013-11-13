In order to view a list of Forms
As Administrator
I need to generate the needed forms for testing

Scenario: Forms Manage Page - Generate needed forms
Given I am logged in
When I click the Forms link
Then I generate 100 forms for the manage page test

Scenario: Forms Published Page - Generate needed forms
Given I am logged in
When I click the Forms link
Then I generate 100 forms for the publish page test