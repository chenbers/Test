Meta:
@page Vehicle Users

Narrative:
In order to show the inthinc admin vehicle access points features
As varying levels of user capabilities
I want to see all combinations of user capabilities

Scenario: TA14313: Can change DOT and Zone Type for 850 Device
Given I am on the Login page 
When I type "QA" into the Username field 
When I type "password" into the Password field 
When I click the Login button
When I go to the admin vehicles page
When I select the 5th Row of the Product dropdown
And I click the 1st Row of the Edit Vehicle Link link
When I select "Light" from the Zone Type dropdown
When I select "Heavy Duty" from the Dot dropdown
When I click the Ifta checkbox
Then I click the Speed And Sensitivity Tab link
And I validate that the Hard Accel text is present
And I validate that the Max Speed field is present
And I click the Cancel button


 
