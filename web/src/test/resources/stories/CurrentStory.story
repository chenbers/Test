Scenario: TCXXXX Forms Add Page - Save Top Button
Given I am logged in
When I click the Forms link
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TCXXXXAdd1" in the Name textfield
And I click the Text link
And I click the Save Top button
Then I validate I am on the Forms Manage page
And I type "Form TCXXXXAdd1" into the Search field
And I validate the 1st Row of Entry Name text is "Form TCXXXXAdd1"
And I validate the 1st Row of the Entry Version text is "1"
And I click the Published link
And I validate I am on the Forms Published page
And I type "Form TCXXXXAdd1" into the Search textfield
And I validate the 1st Row of the Entry Base Form Id text is not present
And I click the Manage link
And I validate I am on the Forms Manage page
And I type "Form TCXXXXAdd1" into the Search field
And I click the 1st Row of the Gear button
And I click the 1st Row of the Publish link
And I validate I am on the Forms Published page
And I type "Form TCXXXXAdd1" into the Search field
And I validate the 1st Row of the Entry Name text is "Form TCXXXXAdd1"
And I click the Manage link
And I type "Form TCXXXXAdd1" into the Search field
And I validate the 1st Row of the Entry Version text is "2"