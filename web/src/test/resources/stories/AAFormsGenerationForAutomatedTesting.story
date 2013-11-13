In order to view a list of Forms
As Administrator
I need a UI on the Administrator page that lists the Forms

Scenario: Forms Manage Page - Generate needed forms
Given I am logged in
When I click the Forms link
Then I generate 100 forms for the manage page test

Scenario: Forms Published Page - Generate needed forms
Given I am logged in
When I click the Forms link
Then I generate 100 forms for the publish page test