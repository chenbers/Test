Narrative: 
In order for drivers to submit electronic forms
As an Administrator
I need an interface to create a form and to forward to devices for drivers to complete

Scenario: TC6291 Forms Add Page - Add a Numeric Field - Range boxes checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add15" into the Name textfield
And I click the Numeric link
And I type "anXMLtag" into the Data Name textfield
And I type "Unit" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "0000" in the Value textfield
And I check the Range Enable Numeric checkbox
And I check the Minimum Numeric Inclusive checkbox
And I check the Maximum Numeric Inclusive checkbox
And I type "1" into the Minimum Numeric Range textfield
And I type "999" into the Maximum Numeric Range textfield
And I type "Value out of range" into the Invalid Text textfield
And I click the Save Top button
And I type "Form TC6291Add15" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add15"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Range Enable Numeric checkbox is checked
And I validate the Minimum Numeric Inclusive checkbox is checked
And I validate the Maximum Numeric Inclusive checkbox is checked
And I validate the Minimum Numeric Range textfield is "1"
And I validate the Maximum Numeric Range textfield is "999"
And I validate the Invalid Text textfield is "Value out of range"

