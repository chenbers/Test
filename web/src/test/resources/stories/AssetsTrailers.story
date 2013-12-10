Meta:
@page login

Narrative:Test the functionality of the Reports Trailers page

Scenario: Assets - Trailers - UI
Given I navigate to the assets trailers page
Then I validate I am on the Assets Trailers page
And I validate the Records Per Page Label text is present
And I validate the Records Per Page dropdown is present
And I validate the Search Label text is present
And I validate the Search textfield is present
And I validate the Show Hide Columns link is present
And I validate the Sort By Trailer ID link is present
And I validate the Sort By Team link is present
And I validate the Sort By Status link is present
And I validate the Sort By VIN link is present
And I validate the Sort By License Number link is present
And I validate the Sort By State link is present
And I validate the Sort By Year link is present
And I validate the Sort By Make link is present
And I validate the Sort By Model link is present
And I validate the Sort By Color link is present
And I validate the Sort By Weight link is present
And I validate the Sort By Odometer link is present
And I validate the Previous link is present
And I validate the Next link is present
And I validate the Select A Row Label text is present
And I validate the New button is present
And I validate the Edit button is present
And I validate the Cancel button is not present
And I validate the Save button is not present
And I validate the Vin Label text is present
And I validate the Make Label text is present
And I validate the Year Label text is present
And I validate the Color Label text is present
And I validate the Weight Label text is present
And I validate the License Number Label text is present
And I validate the State Label text is present
And I validate the Trailer Profile Label text is present
And I validate the Trailer ID Label text is present
And I validate the Status Label text is present
And I validate the Trailer Assignment Label text is present
And I validate the Team Label text is present
And I validate the Device Assignment Label text is present
And I validate the Assigned Device Label text is present
And I validate the Vehicle Assignment Label text is present
And I validate the Assigned Vehicle Label text is present
And I validate the Driver Assignment Label text is present
And I validate the Assigned Driver Label text is present
And I click the Edit button
And I validate the Cancel button is present
And I validate the Save button is present
And I validate the New button is present
And I validate the Edit button is not present
And I click the Show Hide Columns link
And I validate the Trailer ID Checkbox Label text is present
And I validate the Team Checkbox Label text is present
And I validate the Status Checkbox Label text is present
And I validate the VIN Checkbox Label text is present
And I validate the License Number Checkbox Label text is present
And I validate the State Checkbox Label text is present
And I validate the Year Checkbox Label text is present
And I validate the Make Checkbox Label text is present
And I validate the Model Checkbox Label text is present
And I validate the Color Checkbox Label text is present
And I validate the Weight Checkbox Label text is present
And I validate the Odometer Checkbox Label text is present
And I validate the Trailer ID checkbox is present
And I validate the Team checkbox is present
And I validate the Status checkbox is present
And I validate the VIN checkbox is present
And I validate the License Number checkbox is present
And I validate the State checkbox is present
And I validate the Year checkbox is present
And I validate the Make checkbox is present
And I validate the Model checkbox is present
And I validate the Color checkbox is present
And I validate the Weight checkbox is present
And I validate the Odometer checkbox is present

Scenario: Assets - Trailers - Show Hide Columns UI Interaction
Given I navigate to the assets trailers page
When I click the Show Hide Columns link
And I uncheck the Trailer ID checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Trailer ID link is not present
And I click the Show Hide Columns link
And I check the Trailer ID checkbox
And I click the Show Hide Columns link
And I validate the Sort By Trailer ID link is present
When I click the Show Hide Columns link
And I uncheck the Team checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Team link is not present
And I click the Show Hide Columns link
And I check the Team checkbox
And I click the Show Hide Columns link
And I validate the Sort By Team link is present
When I click the Show Hide Columns link
And I uncheck the Status checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Status link is not present
And I click the Show Hide Columns link
And I check the Status checkbox
And I click the Show Hide Columns link
And I validate the Sort By Status link is present
When I click the Show Hide Columns link
And I uncheck the VIN checkbox
And I click the Show Hide Columns link
Then I validate the Sort By VIN link is not present
And I click the Show Hide Columns link
And I check the VIN checkbox
And I click the Show Hide Columns link
And I validate the Sort By VIN link is present
When I click the Show Hide Columns link
And I uncheck the License Number checkbox
And I click the Show Hide Columns link
Then I validate the Sort By License Number link is not present
And I click the Show Hide Columns link
And I check the License Number checkbox
And I click the Show Hide Columns link
And I validate the Sort By License Number link is present
When I click the Show Hide Columns link
And I uncheck the State checkbox
And I click the Show Hide Columns link
Then I validate the Sort By State link is not present
And I click the Show Hide Columns link
And I check the State checkbox
And I click the Show Hide Columns link
And I validate the Sort By State link is present
When I click the Show Hide Columns link
And I uncheck the Year checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Year link is not present
And I click the Show Hide Columns link
And I check the Year checkbox
And I click the Show Hide Columns link
And I validate the Sort By Year link is present
When I click the Show Hide Columns link
And I uncheck the Make checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Make link is not present
And I click the Show Hide Columns link
And I check the Make checkbox
And I click the Show Hide Columns link
And I validate the Sort By Make link is present
When I click the Show Hide Columns link
And I uncheck the Model checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Model link is not present
And I click the Show Hide Columns link
And I check the Model checkbox
And I click the Show Hide Columns link
And I validate the Sort By Model link is present
When I click the Show Hide Columns link
And I uncheck the Color checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Color link is not present
And I click the Show Hide Columns link
And I check the Color checkbox
And I click the Show Hide Columns link
And I validate the Sort By Color link is present
When I click the Show Hide Columns link
And I uncheck the Weight checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Weight link is not present
And I click the Show Hide Columns link
And I check the Weight checkbox
And I click the Show Hide Columns link
And I validate the Sort By Weight link is present
When I click the Show Hide Columns link
And I uncheck the Odometer checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Odometer link is not present
And I click the Show Hide Columns link
And I check the Odometer checkbox
And I click the Show Hide Columns link
And I validate the Sort By Odometer link is present

Scenario: Assets - Trailers - Bookmark Entry 
Given I navigate to the assets trailers page
When I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
And I log back in
Then I validate I am on the Assets Trailers page

Scenario: Assets - Trailers - Group Link
Given I navigate to the assets trailers page
When I save the 1st Row of the Team Entry link as SAVEDGROUP
And I click the 1st Row of the Team Entry link
Then I validate the Driver Team Value text is SAVEDGROUP

Scenario: Assets - Trailers - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER
Given I navigate to the assets trailers page
When I click the Sort By Trailer ID link
And I click the Sort By Team link
And I click the Sort By Status link
And I click the Sort By VIN link
And I click the Sort By License Number link
And I click the Sort By State link
And I click the Sort By Year link
And I click the Sort By Make link
And I click the Sort By Model link
And I click the Sort By Color link
And I click the Sort By Weight link
And I click the Sort By Odometer link

Scenario: Assets - Trailers - Edit Columns - Subsequent Session Retention
Given I navigate to the assets trailers page
When I click the Show Hide Columns link
And I uncheck the Trailer ID checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Trailer ID link is not present
And I click the Logout link
Given I navigate to the assets trailers page
Then I validate the Sort By Trailer ID link is not present
And I click the Show Hide Columns link
And I check the Trailer ID checkbox
And I click the Show Hide Columns link
And I validate the Sort By Trailer ID link is present