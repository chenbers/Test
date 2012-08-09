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

Scenario: TC4472: HOS Driver Logs - Add/edit HOS logs for drivers in a specified group
Given I am logged in
When I click the HOS link
Then I validate I am on the HOS Driver Logs page
When I type "hos Driver 00" into the Driver textfield
And I click the suggested row 1 from the Driver textfield
And I select 60 days in the past from the Start Date dropdown
And I click the Add button
Then I validate I am on the HOS Add Edit Driver Logs page
And I save the Date dropdown as DATE
And I save the Hours textfield as HOURS
And I save the Minutes textfield as MINUTES
And I save the Seconds textfield as SECONDS
And I save the Am Pm dropdown as AMPM
And I combine DATE with " " with HOURS with ":" with MINUTES with ":" with SECONDS with " " with AMPM and save them as DATETIME
And I save the Status dropdown as STATUS
And I type "TC4472 Trailer" into the Trailer textfield
And I type "TC4472 Service" into the Service textfield
And I save the Driver dropdown as DRIVER
And I select "hosDriver00" from the Vehicle dropdown
And I type "Salt Lake City, UT" into the Location textfield
And I click the Top Save button
Then I validate I am on the HOS Driver Logs page
When I type "hos Driver 00" into the Driver textfield
And I click the suggested row 1 from the Driver textfield
And I click the Refresh button
Then I validate the 1st Row of the Entry Date Time text contains DATETIME
And I validate the 1st Row of the Entry Driver text is DRIVER
And I validate the 1st Row of the Entry Vehicle text is "hosDriver00"
And I validate the 1st Row of the Entry Service text is "TC4472 Service"
And I validate the 1st Row of the Entry Trailer text is "TC4472 Trailer"
And I validate the 1st Row of the Entry Location text is "Salt Lake City, UT"
And I validate the 1st Row of the Entry Status text is "Off Duty"
And I validate the 1st Row of the Entry Edited text is "Yes"