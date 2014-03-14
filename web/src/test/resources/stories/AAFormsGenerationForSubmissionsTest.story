Scenario: Forms Submissions Page - Add a Form - Create a fully functional Pre Trip form with all options for submissions generation
Given I am logged in
When I click the Forms link
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Submissions Form Pre-Trip Required" in the Name textfield
And I type "Submissions Form Description" in the Description textfield
And I select the option containing "Pre-Trip" from the Trigger dropdown
And I click the Date link
And I type "date" in the Data Name textfield
And I type "Date" in the Caption Text textfield
And I type "Please enter a valid date" in the Hint textfield
And I check the Required checkbox
And I check the Range Enable Date checkbox
And I type "2012-07-31" into the Minimum Date Range textfield
And I type "2012-09-01" into the Maximum Date Range textfield
And I type "Date must be within 2012-08-01 and 2012-08-31" into the Invalid Text textfield
And I click the Numeric link
And I type "integer" in the Data Name textfield
And I type "Please enter a integer" in the Caption Text textfield
And I type "Integer must be between 1 and 100" in the Hint textfield
And I check the Required checkbox
And I check the Range Enable Numeric checkbox
And I type "1" into the Minimum Numeric Range textfield
And I type "100" into the Maximum Numeric Range textfield
And I check the Minimum Numeric Inclusive checkbox
And I check the Maximum Numeric Inclusive checkbox
And I type "Integer must be between 1 and 100" into the Invalid Text textfield
And I click the Numeric link
And I type "decimal" in the Data Name textfield
And I type "Please enter a decimal" in the Caption Text textfield
And I type "Decimal must be between 1 and 100" in the Hint textfield
And I check the Required checkbox
And I check the Range Enable Numeric checkbox
And I type "1" into the Minimum Numeric Range textfield
And I type "100" into the Maximum Numeric Range textfield
And I check the Minimum Numeric Inclusive checkbox
And I check the Maximum Numeric Inclusive checkbox
And I type "Decimal must be between 1 and 100" into the Invalid Text textfield
And I select "Decimal" from the Kind dropdown
And I click the Text link
And I type "text" in the Data Name textfield
And I type "Text Caption" in the Caption Text textfield
And I type "Text Hint" in the Hint textfield
And I check the Required checkbox
And I click the Length Enable checkbox
And I type "1" into the Minimum Text Length textfield
And I type "10" into the Maximum Text Length textfield
And I check the Minimum Text Inclusive checkbox
And I check the Maximum Text Inclusive checkbox
And I type "Must be less than 10 characters" into the Invalid Text textfield
And I click the Choose One link
And I type "chooseOne" in the Data Name textfield
And I type "Choose One Caption" in the Caption Text textfield
And I type "Choose One Hint" in the Hint textfield
And I check the Required checkbox
And I type "Option 1" into the 1st Row of the Option English textfield
And I type "optionone" into the 1st Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 2" into the 2nd Row of the Option English textfield
And I type "optiontwo" into the 2nd Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 3" into the 3rd Row of the Option English textfield
And I type "optionthree" into the 3rd Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 4" into the 4th Row of the Option English textfield
And I type "optionfour" into the 4th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 5" into the 5th Row of the Option English textfield
And I type "optionfive" into the 5th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 6" into the 6th Row of the Option English textfield
And I type "optionsix" into the 6th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 7" into the 7th Row of the Option English textfield
And I type "optionseven" into the 7th Row of the Option Underlying Value textfield
And I click the Select Multiple link
And I type "selectMultiple" in the Data Name textfield
And I type "Select Multiple Caption" in the Caption Text textfield
And I type "Select Multiple Hint" in the Hint textfield
And I check the Required checkbox
And I type "Option 1" into the 1st Row of the Option English textfield
And I type "optionone" into the 1st Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 2" into the 2nd Row of the Option English textfield
And I type "optiontwo" into the 2nd Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 3" into the 3rd Row of the Option English textfield
And I type "optionthree" into the 3rd Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 4" into the 4th Row of the Option English textfield
And I type "optionfour" into the 4th Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Submissions Form Pre-Trip Required" into the Search field
Then I validate the 1st Row of the Entry Name text is "Submissions Form Pre-Trip Required"
And I click the 1st Row of the Gear button
And I click the 1st Row of the Publish link
And I type "Submissions Form Pre-Trip Required" into the Search field
And I validate the 1st Row of the Entry Name text is "Submissions Form Pre-Trip Required"

!-- Need to add a step to generate the submissions once the form has been created and published