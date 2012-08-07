Test Case for TF566

Meta:
@page login
@testFolder TF723
@testFolder TF566

Narrative: 

Scenario: TC6276: HOS Driver Logs - UI
Given I am logged in
When I click the HOS link
Then I validate I am on the HOS Driver Logs page
And I validate the Title text is present
And I validate the Driver textfield is present
And I validate the Start Date dropdown is present
And I validate the Stop Date dropdown is present
And I validate the Refresh button is present
And I validate the Add button is present
And I validate the Batch Edit button is present
And I validate the Select All checkbox is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Service link is present
And I validate the Sort By Trailer link is present
And I validate the Sort By Location link is present
And I validate the Sort By Status link is present
And I validate the Sort By Edited link is present

