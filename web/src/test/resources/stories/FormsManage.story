Scenario: TCXXXX: Forms Manage Page - Table View - UI
Given I am logged in
When I click the Forms link
Then I validate the New Form button is present
And I validate the Manage link is present
And I validate the Published link is present
And I validate the Submissions link is present
And I validate the Records Per Page dropdown is present
And I validate the Search textfield is present
And I validate the Sort By Name link is present
And I validate the Sort By Base Form ID link is present
And I validate the Sort By Version link is present
And I validate the Sort By Description link is present
And I validate the Sort By Status link is present
And I validate the Sort By Trigger link is present
And I validate the Entries text is present

Scenario: TCXXXX: Default Records dropdowns are set to 10
Given I am logged in
When I click the Forms link
Then I validate the Records Per Page dropdown is "10"

Scenario: TCXXXX: Forms Manage Page - Records per page test
Given I am logged in
When I click the Forms link
And I select "10" from the Records Per Page dropdown 
Then I validate the 11th Row of the Entry Name text is not present
And I validate the Entries text contains "Showing 1 to 10 of"
And I select "25" from the Records Per Page dropdown 
And I validate the 26th Row of the Entry Name text is not present
And I validate the Entries text contains "Showing 1 to 25 of"
And I select "50" from the Records Per Page dropdown 
And I validate the 51st Row of the Entry Name text is not present
And I validate the Entries text contains "Showing 1 to 50 of"
And I select "100" from the Records Per Page dropdown 
And I validate the 101st Row of the Entry Name text is not present
And I validate the Entries text contains "Showing 1 to 100 of"

Scenario: TCXXXX: Forms Manage Page - New Form button
Given I am logged in
When I click the Forms link
And I click the New Form button
Then I validate I am on the Forms Add page

Scenario: TCXXXX: Forms Manage Page - Forms Edit link
Given I am logged in
When I click the Forms link
And I save the 1st Row of the Entry Name text as SAVEDFORM
And I click the 1st Row of the Gear button
And I click the 1st Row of the Edit link
Then I validate I am on the Forms Edit page
And I validate the Name field is SAVEDFORM

Scenario: Forms Manage Page - Search
Given I am logged in
When I click the Forms link
And I type "FormPreTrip" into the Search textfield
Then I validate the 1st Row of the Entry Name text contains "FormPreTrip"
And I validate the 2nd Row of the Entry Name text contains "FormPreTrip"
And I validate the 3rd Row of the Entry Name text contains "FormPreTrip"
And I type "FormPostTrip" into the Search textfield
Then I validate the 1st Row of the Entry Name text contains "FormPostTrip"
And I validate the 2nd Row of the Entry Name text contains "FormPostTrip"
And I validate the 3rd Row of the Entry Name text contains "FormPostTrip"
And I type "FORM_1" into the Search textfield
And I validate the 1st Row of the Entry Base Form ID text contains "FORM_1"
And I validate the 2nd Row of the Entry Base Form ID text contains "FORM_1"
And I validate the 3rd Row of the Entry Base Form ID text contains "FORM_1"
And I validate the 4th Row of the Entry Base Form ID text contains "FORM_1"
And I type "Required" into the Search textfield
And I validate the 1st Row of the Entry Description text contains "Required"
And I validate the 2nd Row of the Entry Description text contains "Required"
And I validate the 3rd Row of the Entry Description text contains "Required"
And I validate the 4th Row of the Entry Description text contains "Required"
And I type "Inactive" into the Search textfield
And I validate the 1st Row of the Entry Status text contains "Inactive"
And I validate the 2nd Row of the Entry Status text contains "Inactive"
And I validate the 3rd Row of the Entry Status text contains "Inactive"
And I validate the 4th Row of the Entry Status text contains "Inactive"
And I type "Pre-Trip" into the Search textfield
And I validate the 1st Row of the Entry Trigger text contains "Pre-Trip"
And I validate the 2nd Row of the Entry Trigger text contains "Pre-Trip"
And I validate the 3rd Row of the Entry Trigger text contains "Pre-Trip"
And I validate the 4th Row of the Entry Trigger text contains "Pre-Trip"
And I type "Post-Trip" into the Search textfield
And I validate the 1st Row of the Entry Trigger text contains "Post-Trip"
And I validate the 2nd Row of the Entry Trigger text contains "Post-Trip"
And I validate the 3rd Row of the Entry Trigger text contains "Post-Trip"
And I validate the 4th Row of the Entry Trigger text contains "Post-Trip"

Scenario: Forms Manage Page - Search - Manage page stays blank
Given I am logged in
When I click the Forms link
And I click the Published link
And I type "Pre-Trip" into the Search textfield
And I click the Manage link
Then I validate the Search textfield is ""
And I click the Published link
And I validate the Search textfield is ""

Scenario: TCXXXX: Empty Search
Given I am logged in
When I click the Forms link
And I type "randomstringthatwillnotcomeup" into the Search textfield
Then I validate the No Records Error text is "No matching records found"
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"
And I validate the Previous link is not present
And I validate the Next link is not present

Scenario: TCXXXX: Forms Manage Page - Copy Form with all fields and Validate
Given I am logged in
When I click the Forms link
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TCXXXX Pre-Trip Required Copy" in the Name textfield
And I type "Form TCXXXX Pre-Trip Required Copy Description" in the Description textfield
And I select the option containing "Pre-Trip" from the Trigger dropdown
And I select the option containing "Inactive" from the Status dropdown
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
And I click the Add Option link
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
And I click the Select Multiple link
And I type "selectMultiple" in the Data Name textfield
And I type "Select Multiple Caption" in the Caption Text textfield
And I type "Select Multiple Hint" in the Hint textfield
And I check the Required checkbox
And I click the Add Option link
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
And I type "Form TCXXXX Pre-Trip Required Copy" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TCXXXX Pre-Trip Required Copy"
And I click the 1st Row of the Gear button
And I click the 1st Row of the Copy link
And I validate the Name textfield is "Form TCXXXX Pre-Trip Required Copy"
And I validate the Description textfield is "Form TCXXXX Pre-Trip Required Copy Description"
And I validate the Trigger dropdown is "Pre-Trip"
And I validate the Version text is "1"
And I validate the Status dropdown is "Inactive"
And I validate the Data Name textfield is "selectMultiple"
And I validate the Caption Text textfield is "Select Multiple Caption"
And I validate the Hint textfield is "Select Multiple Hint"
And I validate the Required checkbox is checked
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"
And I validate the 2nd Row of the Option English textfield is "Option 2"
And I validate the 2nd Row of the Option Underlying Value textfield is "optiontwo"
And I validate the 3rd Row of the Option English textfield is "Option 3"
And I validate the 3rd Row of the Option Underlying Value textfield is "optionthree"
And I validate the 4th Row of the Option English textfield is "Option 4"
And I validate the 4th Row of the Option Underlying Value textfield is "optionfour" 
And I click the 1st Row of the Control Flow Arrow link
And I validate the Data Name textfield is "date"
And I validate the Caption Text textfield is "Date"
And I validate the Hint textfield is "Please enter a valid date"
And I validate the Required checkbox is checked
And I validate the Range Enable Date checkbox is checked
And I validate the Minimum Date Range textfield is "2012-07-31"
And I validate the Maximum Date Range textfield is "2012-09-01"
And I validate the Invalid Text textfield is "Date must be within 2012-08-01 and 2012-08-31"
And I click the 2nd Row of the Control Flow Arrow link
And I validate the Data Name textfield is "integer"
And I validate the Caption Text textfield is "Please enter a integer"
And I validate the Hint textfield is "Integer must be between 1 and 100"
And I validate the Required checkbox is checked
And I validate the Range Enable Numeric checkbox is checked
And I validate the Minimum Numeric Range textfield is "1"
And I validate the Maximum Numeric Range textfield is "100"
And I validate the Minimum Numeric Inclusive checkbox is checked
And I validate the Maximum Numeric Inclusive checkbox is checked
And I validate the Invalid Text textfield is "Integer must be between 1 and 100"
And I click the 3rd Row of the Control Flow Arrow link
And I validate the Data Name textfield is "decimal"
And I validate the Caption Text textfield is "Please enter a decimal"
And I validate the Hint textfield is "Decimal must be between 1 and 100"
And I validate the Required checkbox is checked
And I validate the Range Enable Numeric checkbox is checked
And I validate the Minimum Numeric Range textfield is "1"
And I validate the Maximum Numeric Range textfield is "100"
And I validate the Minimum Numeric Inclusive checkbox is checked
And I validate the Maximum Numeric Inclusive checkbox is checked
And I validate the Invalid Text textfield is "Decimal must be between 1 and 100"
And I click the 4th Row of the Control Flow Arrow link
And I validate the Data Name textfield is "text"
And I validate the Caption Text textfield is "Text Caption"
And I validate the Hint textfield is "Text Hint"
And I validate the Required checkbox is checked
And I validate the Length Enable checkbox is checked
And I validate the Minimum Text Length textfield is "1"
And I validate the Maximum Text Length textfield is "10"
And I validate the Minimum Text Inclusive checkbox is checked
And I validate the Maximum Text Inclusive checkbox is checked
And I validate the Invalid Text textfield is "Must be less than 10 characters"
And I click the 5th Row of the Control Flow Arrow link
And I validate the Data Name textfield is "chooseOne"
And I validate the Caption Text textfield is "Choose One Caption"
And I validate the Hint textfield is "Choose One Hint"
And I validate the Required checkbox is checked
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"
And I validate the 2nd Row of the Option English textfield is "Option 2"
And I validate the 2nd Row of the Option Underlying Value textfield is "optiontwo"
And I validate the 3rd Row of the Option English textfield is "Option 3"
And I validate the 3rd Row of the Option Underlying Value textfield is "optionthree"
And I validate the 4th Row of the Option English textfield is "Option 4"
And I validate the 4th Row of the Option Underlying Value textfield is "optionfour"