Scenario: TC1529: Reports - Devices - UI
Given I am logged in as a "Admin" user
When I click the Reports link
And I click the Devices link
Then I validate I am on the Devices page
And I validate the Edit Columns link is present
And I validate the Tools button is present
And I validate the Counter text is present
And I validate the Sort By Device ID link is present
And I validate the Sort By Assigned Vehicle link is present
And I validate the Sort By IMEI link is present
And I validate the Sort By Device Phone link is present
And I validate the Header Status text is present
And I validate the Device ID textfield is present
And I validate the Assigned Vehicle textfield is present
And I validate the IMEI textfield is present
And I validate the Device Phone textfield is present
And I validate the Stats dropdown is present