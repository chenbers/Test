Meta:
@page login
@testFolder TF388
@testFolder TF416
@testFolder TF502

Narrative:

Scenario: Check all boxes on vehicles edit columns and save
Given I am on the Login page
When I type "cchen" into the User Name textfield
And I type "password" into the Password textfield
And I press the Enter Key
When I click the Reports link
And I click the Idling Vehicles link
And I click the 2nd Row of the Driver value link

