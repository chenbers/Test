Narrative: 
In order for drivers to submit electronic forms
As an Administrator
I need an interface to create a form and to forward to devices for drivers to complete

!-- removed "And I validate the Advanced Arrow button is present"
!-- watch out for the logic of "Then I validate the 1st Row of the PublishDisabled link is not present", which returns a FALSE, though it IS present --reverse logic
Scenario: TC6291 Forms Add Page - Save Top Button
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add1" in the Name textfield
And I click the Text link
And I click the Save Top button
Then I validate I am on the Forms Manage page
When I type "Form TC6291Add1" into the Search field
Then I validate the 1st Row of Entry Name text is "Form TC6291Add1"
When I click the Published link
Then I validate I am on the Forms Published page
When I type "Form TC6291Add1" into the Search textfield
Then I validate the 1st Row of the Entry Base Form Id text is not present
Given I click the Manage link
Then I validate I am on the Forms Manage page
And I type "Form TC6291Add1" into the Search field
And I validate the 1st Row of the Entry Version text is "1"
And I click the 1st Row of the Gear button
Then I click the 1st Row of the Publish link
And I validate I am on the Forms Manage page
Then I validate the 1st Row of the PublishDisabled link is not present
And I validate the 1st Row of the Entry Version text is "2"

Scenario: TC6291 Forms Add Page - Save Bottom Button
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add2" in the Name textfield
And I click the Text link
And I click the Save Bottom button
Then I validate I am on the Forms Manage page
When I type "Form TC6291Add2" into the Search field
Then I validate the 1st Row of Entry Name text is "Form TC6291Add2"

Scenario: TC6291 Forms Add Page - Cancel Top Button
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add3" in the Name field
And I click the Text link
And I click the Cancel Top button
Then I validate I am on the Forms Manage page
When I type "Form TC6291Add3" into the Search field
Then I validate the 1st Row of the Entry Base Form Id text is not present
When I click the Published link
And I type "Form TC6291Add3" into the Search field
Then I validate the 1st Row of the Entry Base Form Id text is not present

Scenario: TC6291 Forms Add Page - Cancel Bottom Button
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add3" in the Name field
And I click the Text link
And I click the Cancel Top button
Then I validate I am on the Forms Manage page
When I type "Form TC6291Add3" into the Search field
Then I validate the 1st Row of the Entry Base Form Id text is not present
When I click the Published link
And I type "Form TC6291Add3" into the Search field
Then I validate the 1st Row of the Entry Base Form Id text is not present

Scenario: TC6291: Forms Add Page - Add Form link top (cancel - no changes)
Given I am logged in
When I go to the forms manage page
And I click the New Form button
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "No Trigger"
And I validate the Version text is ""
And I validate the Status dropdown is "Active"
And I validate the Filter Groups field is ""
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the View Properties Label text is present
When I click the Cancel Top button
Then I validate I am on the Forms Manage page
When I click the New Form button
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "No Trigger"
And I validate the Version text is ""
And I validate the Status dropdown is "Active"
And I validate the Filter Groups field is ""
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the View Properties Label text is present

Scenario: TC6291: Forms Add Page - Add Form link bottom (cancel - no changes)
Given I am logged in
When I go to the forms manage page
And I click the New Form button
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "No Trigger"
And I validate the Version text is ""
And I validate the Status dropdown is "Active"
And I validate the Filter Groups field is ""
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the View Properties Label text is present
When I click the Cancel Bottom button
Then I validate I am on the Forms Manage page
When I click the New Form button
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "No Trigger"
And I validate the Version text is ""
And I validate the Status dropdown is "Active"
And I validate the Filter Groups field is ""
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the View Properties Label text is present

!-- removed attempts to select from dropdowns for now *******************************************************
Scenario: TC6291: Forms Add Page - Add Form link top (cancel - changes)
Given I am logged in
When I go to the forms manage page
And I click the New Form button
Then I validate I am on the Forms Add page
And I validate the Name field is ""
When I type "TC6291" into the Name field
And I type "TC6291" into the Description field
And I type "fleet" into the Filter Groups field
And I check the 1st Row of the Groups checkbox
And I click the Cancel Top button
Then I validate I am on the Forms Manage page
When I click the New Form button
Then I validate I am on the Forms Add page
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "No Trigger"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the Status dropdown is "Active"

Scenario: TC6291: Forms Add Page - Add Form link bottom (cancel - changes)
Given I am logged in
When I go to the forms manage page
And I click the New Form button
Then I validate I am on the Forms Add page
And I validate the Name field is ""
When I type "TC6291" into the Name field
And I type "TC6291" into the Description field
And I type "fleet" into the Filter Groups field
And I check the 1st Row of the Groups checkbox
And I click the Cancel Bottom button
Then I validate I am on the Forms Manage page
When I click the New Form button
Then I validate I am on the Forms Add page
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "No Trigger"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the Status dropdown is "Active"

Scenario: TC6291 Forms Add Page - UI Test - Text link
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
Then I validate the Data Name textfield is present
And I validate the Caption Text textfield is present
And I validate the Hint textfield is present
And I validate the Value textfield is present
And I validate the Read Only checkbox is present
And I validate the Required checkbox is present
And I validate the Length Enable checkbox is present
And I validate the Minimum Text Length textfield is present
And I validate the Maximum Text Length textfield is present
And I validate the Minimum Text Inclusive checkbox is present
And I validate the Maximum Text Inclusive checkbox is present
And I validate the Invalid Text textfield is present
When I click the Text link
Then I validate the 1st Row of the Delete Row link is present
And I validate the 1st Row of the Control Flow Arrow link is present

Scenario: TC6291 Forms Add Page - UI Test - Numeric link
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Numeric link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Caption Text textfield is present
And I validate the Hint textfield is present
And I validate the Value textfield is present
And I validate the Read Only checkbox is present
And I validate the Required checkbox is present
And I validate the Range Enable Numeric checkbox is present
And I validate the Minimum Numeric Range textfield is present
And I validate the Maximum Numeric Range textfield is present
And I validate the Minimum Numeric Inclusive checkbox is present
And I validate the Maximum Numeric Inclusive checkbox is present
And I validate the Invalid Text textfield is present
And I validate the Kind dropdown is present
When I click the Numeric link
Then I validate the 1st Row of the Delete Row link is present
And I validate the 1st Row of the Control Flow Arrow link is present

Scenario: TC6291 Forms Add Page - UI Test - Date link
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Date link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Caption Text textfield is present
And I validate the Hint textfield is present
And I validate the Value textfield is present
And I validate the Read Only checkbox is present
And I validate the Required checkbox is present
And I validate the Range Enable Date checkbox is present
And I validate the Minimum Date Range textfield is present
And I validate the Maximum Date Range textfield is present
And I validate the Minimum Date Inclusive checkbox is present
And I validate the Maximum Date Inclusive checkbox is present
And I validate the Invalid Text textfield is present
When I click the Date link
Then I validate the 1st Row of the Delete Row link is present
And I validate the 1st Row of the Control Flow Arrow link is present

Scenario: TC6291 Forms Add Page - UI Test - Choose One link
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Choose One link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Caption Text textfield is present
And I validate the Hint textfield is present
And I validate the Value textfield is present
And I validate the Read Only checkbox is present
And I validate the Required checkbox is present
And I validate the Add Option link is present
When I click the Choose One link
Then I validate the 1st Row of the Delete Row link is present
And I validate the 1st Row of the Control Flow Arrow link is present

Scenario: TC6291 Forms Add Page - UI Test - Select Multiple link
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Select Multiple link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Caption Text textfield is present
And I validate the Hint textfield is present
And I validate the Value textfield is present
And I validate the Read Only checkbox is present
And I validate the Required checkbox is present
And I validate the Add Option link is present
When I click the Select Multiple link
Then I validate the 1st Row of the Delete Row link is present
And I validate the 1st Row of the Control Flow Arrow link is present

Scenario: TC6291 Forms Add Page - Blank Data Name Error Text Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TC6291 Forms Add Page - Blank Data Name Error Numeric Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Numeric link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TC6291 Forms Add Page - Blank Data Name Error Date Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Date link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TC6291 Forms Add Page - Blank Data Name Error Choose One Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Choose One link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TC6291 Forms Add Page - Blank Data Name Error Select Multiple Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Select Multiple link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TC6291 Forms Add Page - Duplicate Data Name Error Text Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
And I type "test" into the Data Name field
And I click the Text link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 1st Row of the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TC6291 Forms Add Page - Duplicate Data Name Error Numeric Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Numeric link
And I type "test" into the Data Name field
And I click the Numeric link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 1st Row of the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TC6291 Forms Add Page - Duplicate Data Name Error Date Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Date link
And I type "test" into the Data Name field
And I click the Date link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 1st Row of the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TC6291 Forms Add Page - Duplicate Data Name Error Choose One Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Choose One link
And I type "test" into the Data Name field
And I click the Choose One link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 1st Row of the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TC6291 Forms Add Page - Duplicate Data Name Error Select Multiple Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Select Multiple link
And I type "test" into the Data Name field
And I click the Select Multiple link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 1st Row of the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TC6291 Forms Add Page - Duplicate Data Name Error All Fields
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
And I type "test" into the Data Name field
And I click the Numeric link
And I type "test" into the Data Name field
And I click the Date link
And I type "test" into the Data Name field
And I click the Choose One link
And I type "test" into the Data Name field
And I click the Select Multiple link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 1st Row of the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 2nd Row of the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 3rd Row of the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 4th Row of the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TC6291 Forms Add Page - Space Data Name Error Text Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Space Data Name Error Numeric Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Numeric link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Space Data Name Error Date Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Date link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Space Data Name Error Choose One Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Choose One link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Space Data Name Error Select Multiple Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Select Multiple link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Space Data Name Error All Fields
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
And I type "te st" into the Data Name field
And I click the Numeric link
And I type "te st" into the Data Name field
And I click the Date link
And I type "te st" into the Data Name field
And I click the Choose One link
And I type "te st" into the Data Name field
And I click the Select Multiple link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text contains "Only letters and numbers are allowed."
And I click the 1st Row of the Control Flow Arrow link
And I validate the Data Name Error text contains "Only letters and numbers are allowed."
And I click the 2nd Row of the Control Flow Arrow link
And I validate the Data Name Error text contains "Only letters and numbers are allowed."
And I click the 3rd Row of the Control Flow Arrow link
And I validate the Data Name Error text contains "Only letters and numbers are allowed."
And I click the 4th Row of the Control Flow Arrow link
And I validate the Data Name Error text contains "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Symbols Data Name Error Text Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Symbols Data Name Error Numeric Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Numeric link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Symbols Data Name Error Date Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Date link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Symbols Data Name Error Choose One Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Choose One link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Symbols Data Name Error Select Multiple Field
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Select Multiple link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Symbols Data Name Error All Fields
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
And I type "!" into the Data Name field
And I click the Numeric link
And I type "!" into the Data Name field
And I click the Date link
And I type "!" into the Data Name field
And I click the Choose One link
And I type "!" into the Data Name field
And I click the Select Multiple link
And I type "!" into the Data Name field
Then I validate the Data Name Error text contains "Only letters and numbers are allowed."
And I click the 1st Row of the Control Flow Arrow link
And I validate the Data Name Error text contains "Only letters and numbers are allowed."
And I click the 2nd Row of the Control Flow Arrow link
And I validate the Data Name Error text contains "Only letters and numbers are allowed."
And I click the 3rd Row of the Control Flow Arrow link
And I validate the Data Name Error text contains "Only letters and numbers are allowed."
And I click the 4th Row of the Control Flow Arrow link
And I validate the Data Name Error text contains "Only letters and numbers are allowed."

Scenario: TC6291 Forms Add Page - Name required error
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
And I click the Save Top button
Then I validate the Name Error text contains "Name is a required field."
And I validate the Control Error text is not present
And I validate the Group Error text is not present

Scenario: TC6291 Forms Add Page - Name required error
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
And I click the Save Top button
Then I validate the Name Error text contains "Name is a required field."
And I validate the Control Error text is not present
And I validate the Group Error text is not present

Scenario: TC6291 Forms Add Page - Name required error
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I click the Text link
And I click the Save Top button
Then I validate the Name Error text contains "Name is a required field."
And I validate the Control Error text is not present
And I validate the Group Error text is not present

Scenario: TC6291 Forms Add Page - Control required error
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add5" into the Name textfield
And I click the Save Top button
Then I validate the Control Error text contains "Form must have at least one control"
And I validate the Name Error text is not present
And I validate the Group Error text is not present

Scenario: TC6291 Forms Add Page - Blank Group Checkbox Error 
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I click the Text link
And I type "Form TC6291Add5" into the Name textfield
And I click the Save Top button
Then I validate the Group Error text contains "Must select at least one group"
And I validate the Name Error text is not present
And I validate the Control Error text is not present

Scenario: TC6291 Forms Add Page - Name, Control, and Group Errors
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I click the Save Top button
Then I validate the Name Error text contains "Name is a required field."
And I validate the Control Error text contains "Form must have at least one control."
Then I validate the Group Error text contains "Must select at least one group."

!-- TODO: Need to handle tree checkboxes: the current checkbox cannot state (checked, unchecked) cannot be recognized.
!-- workaround is to check twice, if we need to uncheck in the 15th line of this test
Scenario: TC6291 Forms Add Page - Name, Control, and Group Errors Add then remove
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I click the Save Top button
Then I validate the Control Error text contains "Form must have at least one control"
And I validate the Name Error text contains "Name is a required field"
And I validate the Group Error text contains "Must select at least one group"
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add6" into the Name textfield
And I click the Save Top button
And I validate the Control Error text contains "Form must have at least one control"
And I validate the Name Error text is not present
And I validate the Group Error text is not present
And I click the Text link
And I check the 1st Row of the Groups checkbox
And I click the Save Top button
And I validate the Group Error text contains "Must select at least one group"
And I validate the Control Error text is not present
And I validate the Name Error text is not present
And I check the 1st Row of the Groups checkbox
And I type "" into the Name textfield
And I click the Save Top button
And I validate the Name Error text contains "Name is a required field"
And I validate the Control Error text is not present
And I validate the Group Error text is not present

Scenario: TC6291 Forms Add Page - Add a Name Text Field - Read Only box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add7" into the Name textfield
And I click the Text link
And I type "anXMLtag" in the Data Name textfield
And I type "Driver Name" in the Caption Text textfield
And I type "Hint Text" in the Hint textfield
And I type "Default Value Text" in the Value textfield
And I check the Read Only checkbox
And I click the Save Top button
And I validate I am on the Forms Manage page
And I type "Form TC6291Add7" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add7"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
Then I validate the Read Only checkbox is checked

Scenario: TC6291 Forms Add Page - Add a Name Text Field - Required box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add8" into the Name textfield
And I click the Text link
And I type "anXMLtag" in the Data Name textfield
And I type "Driver Name" in the Caption Text textfield
And I type "Hint Text" in the Hint textfield
And I type "Default Value Text" in the Value textfield
And I check the Required checkbox
And I click the Save Top button
And I type "Form TC6291Add8" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add8"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked

Scenario: TC6291 Forms Add Page - Add a Name Text Field - Length boxes checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add9" into the Name textfield
And I click the Text link
And I type "anXMLtag" in the Data Name textfield
And I type "Driver Name" in the Caption Text textfield
And I type "Hint Text" in the Hint textfield
And I type "Default Value Text" in the Value textfield
And I check the Length Enable checkbox
And I check the Minimum Text Inclusive checkbox
And I check the Maximum Text Inclusive checkbox
And I type "1" into the Minimum Text Length textfield
And I type "4" into the Maximum Text Length textfield
And I type "Value out of range" into the Invalid Text textfield
And I click the Save Top button
And I type "Form TC6291Add9" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add9"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
Then I validate the Length Enable checkbox is checked
And I validate the Minimum Text Inclusive checkbox is checked
And I validate the Maximum Text Inclusive checkbox is checked
And I validate the Minimum Text Length textfield is "1"
And I validate the Maximum Text Length textfield is "4"
And I validate the Invalid Text textfield is "Value out of range"

Scenario: TC6291 Forms Add Page - Add a Name Text Field - Required and Length boxes checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add10" into the Name textfield
And I click the Text link
And I type "anXMLtag" in the Data Name textfield
And I type "Driver Name" in the Caption Text textfield
And I type "Hint Text" in the Hint textfield
And I type "Default Value Text" in the Value textfield
And I check the Required checkbox
And I check the Length Enable checkbox
And I check the Minimum Text Inclusive checkbox
And I check the Maximum Text Inclusive checkbox
And I type "2" into the Minimum Text Length textfield
And I type "5" into the Maximum Text Length textfield
And I type "Value out of range" into the Invalid Text textfield
And I click the Save Top button
And I type "Form TC6291Add10" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add10"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked
And I validate the Length Enable checkbox is checked
And I validate the Minimum Text Inclusive checkbox is checked
And I validate the Maximum Text Inclusive checkbox is checked
And I validate the Minimum Text Length textfield is "2"
And I validate the Maximum Text Length textfield is "5"
And I validate the Invalid Text textfield is "Value out of range"

Scenario: TC6291 Forms Add Page - Add a Name Text Field - Required and Length boxes unchecked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add11" into the Name textfield
And I click the Text link
And I type "anXMLtag" in the Data Name field
And I type "Driver Name" in the Caption Text field
And I type "Hint Text" in the Hint field
And I click the Save Top button
And I type "Form TC6291Add11" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add11"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
Then I validate the Name field is "Form TC6291Add11"
Then I validate the Data Name field is "anXMLtag"
Then I validate the Caption Text field is "Driver Name"
Then I validate the Hint field is "Hint Text"
Then I validate the Required checkbox is not checked
And I validate the Length Enable checkbox is not checked
And I validate the Minimum Text Inclusive checkbox is not checked
And I validate the Maximum Text Inclusive checkbox is not checked
When I click the Length Enable checkbox
Then I validate the Minimum Text Length field is ""
Then I validate the Maximum Text Length field is ""
And I click the Cancel Bottom button
And I type "Form TC6291Add11" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add11"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Copy link
Then I validate the Required checkbox is not checked
And I validate the Name field is "Form TC6291Add11"
And I validate the Data Name field is "anXMLtag"
And I validate the Caption Text field is "Driver Name"
And I validate the Hint field is "Hint Text"
And I validate the Length Enable checkbox is not checked
And I validate the Minimum Text Inclusive checkbox is not checked
And I validate the Maximum Text Inclusive checkbox is not checked
When I click the Length Enable checkbox
Then I validate the Minimum Text Length field is ""
Then I validate the Maximum Text Length field is ""

Scenario: TC6291 Forms Add Page - Add a Text Field - Not inclusive
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add12" into the Name textfield
And I click the Numeric link
And I type "anXMLtag" into the Data Name textfield
And I type "Unit" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "0000" in the Value textfield
And I check the Length Enable checkbox
And I type "1" into the Minimum Text Length textfield
And I type "999" into the Maximum Text Length textfield
And I type "Value out of range" into the Invalid Text textfield
And I click the Save Top button
And I type "Form TC6291Add12" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add12"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
Then I validate the Length Enable checkbox is checked
And I validate the Minimum Text Inclusive checkbox is not checked
And I validate the Maximum Text Inclusive checkbox is not checked
And I validate the Minimum Text Length textfield is "1"
And I validate the Maximum Text Length textfield is "999"
And I validate the Invalid Text textfield is "Value out of range"

Scenario: TC6291 Forms Add Page - Add a Numeric Field - Read Only box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add13" into the Name textfield
And I click the Numeric link
And I type "anXMLtag" into the Data Name textfield
And I type "Unit" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "0000" in the Value textfield
And I check the Read Only checkbox
And I click the Save Top button
And I type "Form TC6291Add13" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add13"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate the Read Only checkbox is checked

Scenario: TC6291 Forms Add Page - Add a Numeric Field - Required box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add14" into the Name textfield
And I click the Numeric link
And I type "anXMLtag" into the Data Name textfield
And I type "Unit" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "0000" in the Value textfield
And I check the Required checkbox
And I click the Save Top button
And I type "Form TC6291Add14" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add14"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate the Required checkbox is checked

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

Scenario: TC6291 Forms Add Page - Add a Numeric Field - Required and Range boxes checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add16" into the Name textfield
And I click the Numeric link
And I type "anXMLtag" into the Data Name textfield
And I type "Unit" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "0000" in the Value textfield
And I check the Required checkbox
And I check the Range Enable Numeric checkbox
And I check the Minimum Numeric Inclusive checkbox
And I check the Maximum Numeric Inclusive checkbox
And I type "1" into the Minimum Numeric Range textfield
And I type "999" into the Maximum Numeric Range textfield
And I type "Value out of range" into the Invalid Text textfield
And I click the Save Top button
And I type "Form TC6291Add16" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add16"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is checked
And I validate the Range Enable Numeric checkbox is checked
And I validate the Minimum Numeric Inclusive checkbox is checked
And I validate the Maximum Numeric Inclusive checkbox is checked
And I validate the Minimum Numeric Range field is "1"
And I validate the Maximum Numeric Range field is "999"
And I validate the Invalid Text field is "Value out of range"

Scenario: TC6291 Forms Add Page - Add a Numeric Field - Required and Range boxes unchecked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add17" into the Name textfield
And I click the Numeric link
And I type "anXMLtag" into the Data Name textfield
And I type "Unit" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "0000" in the Value textfield
And I click the Save Top button
And I type "Form TC6291Add17" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add17"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is not checked
And I validate the Range Enable Numeric checkbox is not checked

Scenario: TC6291 Forms Add Page - Add a Numeric Field with Range minimum - Not inclusive
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add18" into the Name textfield
And I click the Numeric link
And I type "anXMLtag" into the Data Name textfield
And I type "Unit" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "0000" in the Value textfield
And I check the Range Enable Numeric checkbox
And I type "1" into the Minimum Numeric Range textfield
And I type "999" into the Maximum Numeric Range textfield
And I type "Value out of range" into the Invalid Text textfield
And I click the Save Top button
And I type "Form TC6291Add18" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add18"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Range Enable Numeric checkbox is checked
And I validate the Minimum Numeric Range textfield is "1"
And I validate the Maximum Numeric Range textfield is "999"
And I validate the Invalid Text textfield is "Value out of range"

Scenario: TC6291 Forms Add Page - Add a Date Field - Read Only box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add19" into the Name textfield
And I click the Date link
And I type "anXMLtag" into the Data Name textfield
And I type "Date" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "2012-01-01" into the Value textfield
And I check the Read Only checkbox
And I click the Save Top button
And I type "Form TC6291Add19" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add19"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Read Only checkbox is checked

Scenario: TC6291 Forms Add Page - Add a Date Field - Required box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add20" into the Name textfield
And I click the Date link
And I type "anXMLtag" into the Data Name textfield
And I type "Date" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "2012-02-02" into the Value textfield
And I check the Required checkbox
And I click the Save Top button
And I type "Form TC6291Add20" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add20"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is checked

Scenario: TC6291 Forms Add Page - Add a Date Field - Range boxes checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add21" into the Name textfield
And I click the Date link
And I type "anXMLtag" into the Data Name textfield
And I type "Unit" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "2012-02-02" in the Value textfield
And I check the Range Enable Date checkbox
And I check the Minimum Date Inclusive checkbox
And I check the Maximum Date Inclusive checkbox
And I type "2012-02-01" into the Minimum Date Range textfield
And I type "2012-03-01" into the Maximum Date Range textfield
And I type "Value out of range" into the Invalid Text textfield
And I click the Save Top button
And I type "Form TC6291Add21" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add21"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Range Enable Date checkbox is checked
And I validate the Minimum Date Inclusive checkbox is checked
And I validate the Maximum Date Inclusive checkbox is checked
And I validate the Minimum Date Range textfield is "2012-02-01"
And I validate the Maximum Date Range textfield is "2012-03-01"
And I validate the Invalid Text textfield is "Value out of range"

Scenario: TC6291 Forms Add Page - Add a Date Field - Required and Range boxes checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add22" into the Name textfield
And I click the Date link
And I type "anXMLtag" into the Data Name textfield
And I type "Unit" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "2012-02-01" in the Value textfield
And I check the Required checkbox
And I check the Range Enable Date checkbox
And I check the Minimum Date Inclusive checkbox
And I check the Maximum Date Inclusive checkbox
And I type "2012-02-01" into the the Minimum Date Range textfield
And I type "2012-03-01" into the Maximum Date Range textfield
And I type "Value out of range" into the Invalid Text textfield
And I click the Save Top button
And I type "Form TC6291Add22" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add22"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is checked
And I validate the Range Enable Date checkbox is checked
And I validate the Minimum Date Inclusive checkbox is checked
And I validate the Maximum Date Inclusive checkbox is checked
And I validate the Minimum Date Range textfield is "2012-02-01"
And I validate the Maximum Date Range textfield is "2012-03-01"
And I validate the Invalid Text textfield is "Value out of range"

Scenario: TC6291 Forms Add Page - Add a Date Field - Required and Range boxes unchecked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add23" into the Name textfield
And I click the Date link
And I type "anXMLtag" into the Data Name textfield
And I type "Unit" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "2012-01-01" into the Value textfield
And I click the Save Top button
And I type "Form TC6291Add23" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add23"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is not checked
And I validate the Range Enable Date checkbox is not checked

Scenario: TC6291 Forms Add Page - Add a Choose One Field - Read Only box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add24" into the Name textfield
And I click the Choose One link
And I type "anXMLtag" into the Data Name textfield
And I type "Choose One" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "Default Value" in the Value textfield
And I check the Read Only checkbox
And I click the Add Option link
And I type "Option 1" in the 1st Row of the Option English textfield
And I type "optionone" in the 1st Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Form TC6291Add24" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add24"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Read Only checkbox is checked
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"

Scenario: TC6291 Forms Add Page - Add a Choose One Field - Required box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add25" into the Name textfield
And I click the Choose One link
And I type "anXMLtag" into the Data Name textfield
And I type "Choose One" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "Default Value" in the Value textfield
And I check the Required checkbox
And I click the Add Option link
And I type "Option 1" in the 1st Row of the Option English textfield
And I type "optionone" in the 1st Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Form TC6291Add25" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add25"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is checked
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"

Scenario: TC6291 Forms Add Page - Add a Choose One Field - Read Only and Required box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add26" into the Name textfield
And I click the Choose One link
And I type "anXMLtag" into the Data Name textfield
And I type "Choose One" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "Default Value" in the Value textfield
And I check the Required checkbox
And I check the Read Only checkbox
And I click the Add Option link
And I type "Option 1" in the 1st Row of the Option English textfield
And I type "optionone" in the 1st Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Form TC6291Add26" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add26"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is checked
And I validate the Read Only checkbox is checked
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"

Scenario: TC6291 Forms Add Page - Add a Choose One Field - Read Only and Required box is not checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add27" into the Name textfield
And I click the Choose One link
And I type "anXMLtag" into the Data Name textfield
And I type "Choose One" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "Default Value" in the Value textfield
And I click the Add Option link
And I type "Option 1" in the 1st Row of the Option English textfield
And I type "optionone" in the 1st Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Form TC6291Add27" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add27"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is not checked
And I validate the Read Only checkbox is not checked
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"

Scenario: TC6291 Forms Add Page - Add a Choose One Field - 30 option list
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add28" into the Name textfield
And I click the Choose One link
And I type "anXMLtag" into the Data Name textfield
And I type "Choose One" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "value" in the Value textfield
And I click the Add Option link
And I type "Option 1" in the 1st Row of the Option English textfield
And I type "optionone" in the 1st Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 2" in the 2nd Row of the Option English textfield
And I type "optiontwo" in the 2nd Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 3" in the 3rd Row of the Option English textfield
And I type "optionthree" in the 3rd Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 4" in the 4th Row of the Option English textfield
And I type "optionfour" in the 4th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 5" in the 5th Row of the Option English textfield
And I type "optionfive" in the 5th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 6" in the 6th Row of the Option English textfield
And I type "optionsix" in the 6th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 7" in the 7th Row of the Option English textfield
And I type "optionseven" in the 7th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 8" in the 8th Row of the Option English textfield
And I type "optioneight" in the 8th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 9" in the 9th Row of the Option English textfield
And I type "optionnine" in the 9th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 10" in the 10th Row of the Option English textfield
And I type "optionten" in the 10th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 11" in the 11th Row of the Option English textfield
And I type "optioneleven" in the 11th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 12" in the 12th Row of the Option English textfield
And I type "optiontwelve" in the 12th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 13" in the 13th Row of the Option English textfield
And I type "optionthirteen" in the 13th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 14" in the 14th Row of the Option English textfield
And I type "optionfourteen" in the 14th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 15" in the 15th Row of the Option English textfield
And I type "optionfifteen" in the 15th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 16" in the 16th Row of the Option English textfield
And I type "optionsixteen" in the 16th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 17" in the 17th Row of the Option English textfield
And I type "optionseventeen" in the 17th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 18" in the 18th Row of the Option English textfield
And I type "optioneighteen" in the 18th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 19" in the 19th Row of the Option English textfield
And I type "optionnineteen" in the 19th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 20" in the 20th Row of the Option English textfield
And I type "optiontwenty" in the 20th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 21" in the 21th Row of the Option English textfield
And I type "optiontwentyone" in the 21th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 22" in the 22th Row of the Option English textfield
And I type "optiontwentytwo" in the 22th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 23" in the 23th Row of the Option English textfield
And I type "optiontwentythree" in the 23th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 24" in the 24th Row of the Option English textfield
And I type "optiontwentyfour" in the 24th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 25" in the 25th Row of the Option English textfield
And I type "optiontwentyfive" in the 25th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 26" in the 26th Row of the Option English textfield
And I type "optiontwentysix" in the 26th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 27" in the 27th Row of the Option English textfield
And I type "optiontwentyseven" in the 27th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 28" in the 28th Row of the Option English textfield
And I type "optiontwentyeight" in the 28th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 29" in the 29th Row of the Option English textfield
And I type "optiontwentynine" in the 29th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 30" in the 30th Row of the Option English textfield
And I type "optionthirty" in the 30th Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Form TC6291Add28" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add28"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"
And I validate the 2nd Row of the Option English textfield is "Option 2"
And I validate the 2nd Row of the Option Underlying Value textfield is "optiontwo"
And I validate the 3rd Row of the Option English textfield is "Option 3"
And I validate the 3rd Row of the Option Underlying Value textfield is "optionthree"
And I validate the 4th Row of the Option English textfield is "Option 4"
And I validate the 4th Row of the Option Underlying Value textfield is "optionfour"
And I validate the 5th Row of the Option English textfield is "Option 5"
And I validate the 5th Row of the Option Underlying Value textfield is "optionfive"
And I validate the 6th Row of the Option English textfield is "Option 6"
And I validate the 6th Row of the Option Underlying Value textfield is "optionsix"
And I validate the 7th Row of the Option English textfield is "Option 7"
And I validate the 7th Row of the Option Underlying Value textfield is "optionseven"
And I validate the 8th Row of the Option English textfield is "Option 8"
And I validate the 8th Row of the Option Underlying Value textfield is "optioneight"
And I validate the 9th Row of the Option English textfield is "Option 9"
And I validate the 9th Row of the Option Underlying Value textfield is "optionnine"
And I validate the 10th Row of the Option English textfield is "Option 10"
And I validate the 10th Row of the Option Underlying Value textfield is "optionten"
And I validate the 11th Row of the Option English textfield is "Option 11"
And I validate the 11th Row of the Option Underlying Value textfield is "optioneleven"
And I validate the 12th Row of the Option English textfield is "Option 12"
And I validate the 12th Row of the Option Underlying Value textfield is "optiontwelve"
And I validate the 13th Row of the Option English textfield is "Option 13"
And I validate the 13th Row of the Option Underlying Value textfield is "optionthirteen"
And I validate the 14th Row of the Option English textfield is "Option 14"
And I validate the 14th Row of the Option Underlying Value textfield is "optionfourteen"
And I validate the 15th Row of the Option English textfield is "Option 15"
And I validate the 15th Row of the Option Underlying Value textfield is "optionfifteen"
And I validate the 16th Row of the Option English textfield is "Option 16"
And I validate the 16th Row of the Option Underlying Value textfield is "optionsixteen"
And I validate the 17th Row of the Option English textfield is "Option 17"
And I validate the 17th Row of the Option Underlying Value textfield is "optionseventeen"
And I validate the 18th Row of the Option English textfield is "Option 18"
And I validate the 18th Row of the Option Underlying Value textfield is "optioneighteen"
And I validate the 19th Row of the Option English textfield is "Option 19"
And I validate the 19th Row of the Option Underlying Value textfield is "optionnineteen"
And I validate the 20th Row of the Option English textfield is "Option 20"
And I validate the 20th Row of the Option Underlying Value textfield is "optiontwenty"
And I validate the 21st Row of the Option English textfield is "Option 21"
And I validate the 21st Row of the Option Underlying Value textfield is "optiontwentyone"
And I validate the 22nd Row of the Option English textfield is "Option 22"
And I validate the 22nd Row of the Option Underlying Value textfield is "optiontwentytwo"
And I validate the 23rd Row of the Option English textfield is "Option 23"
And I validate the 23rd Row of the Option Underlying Value textfield is "optiontwentythree"
And I validate the 24th Row of the Option English textfield is "Option 24"
And I validate the 24th Row of the Option Underlying Value textfield is "optiontwentyfour"
And I validate the 25th Row of the Option English textfield is "Option 25"
And I validate the 25th Row of the Option Underlying Value textfield is "optiontwentyfive"
And I validate the 26th Row of the Option English textfield is "Option 26"
And I validate the 26th Row of the Option Underlying Value textfield is "optiontwentysix"
And I validate the 27th Row of the Option English textfield is "Option 27"
And I validate the 27th Row of the Option Underlying Value textfield is "optiontwentyseven"
And I validate the 28th Row of the Option English textfield is "Option 28"
And I validate the 28th Row of the Option Underlying Value textfield is "optiontwentyeight"
And I validate the 29th Row of the Option English textfield is "Option 29"
And I validate the 29th Row of the Option Underlying Value textfield is "optiontwentynine"
And I validate the 30th Row of the Option English textfield is "Option 30"
And I validate the 30th Row of the Option Underlying Value textfield is "optionthirty"

Scenario: TC6291 Forms Add Page - Add a Select Multiple Field - Read Only box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add29" into the Name textfield
And I click the Select Multiple link
And I type "anXMLtag" into the Data Name textfield
And I type "Select Multiple" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "Default Value" in the Value textfield
And I check the Read Only checkbox
And I click the Add Option link
And I type "Option 1" in the 1st Row of the Option English textfield
And I type "optionone" in the 1st Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Form TC6291Add29" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add29"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Read Only checkbox is checked
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"
And I click the Cancel Top button

Scenario: TC6291 Forms Add Page - Add a Select Multiple Field - Required box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add30" into the Name textfield
And I click the Select Multiple link
And I type "anXMLtag" into the Data Name textfield
And I type "Select Multiple" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "Default Value" in the Value textfield
And I check the Required checkbox
And I click the Add Option link
And I type "Option 1" in the 1st Row of the Option English textfield
And I type "optionone" in the 1st Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Form TC6291Add30" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add30"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is checked
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"

Scenario: TC6291 Forms Add Page - Add a Select Multiple Field - Read Only and Required box checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add31" into the Name textfield
And I click the Select Multiple link
And I type "anXMLtag" into the Data Name textfield
And I type "Select Multiple" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "Default Value" in the Value textfield
And I check the Required checkbox
And I check the Read Only checkbox
And I click the Add Option link
And I type "Option 1" in the 1st Row of the Option English textfield
And I type "optionone" in the 1st Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Form TC6291Add31" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add31"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is checked
And I validate the Read Only checkbox is checked
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"

Scenario: TC6291 Forms Add Page - Add a Select Multiple Field - Read Only and Required box is not checked
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add32" into the Name textfield
And I click the Select Multiple link
And I type "anXMLtag" into the Data Name textfield
And I type "Select Multiple" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "Default Value" in the Value textfield
And I click the Add Option link
And I type "Option 1" in the 1st Row of the Option English textfield
And I type "optionone" in the 1st Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Form TC6291Add32" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add32"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
Then I validate the Required checkbox is not checked
And I validate the Read Only checkbox is not checked
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"

Scenario: TC6291 Forms Add Page - Add a Select Multiple Field - 30 option list
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291Add33" into the Name textfield
And I click the Select Multiple link
And I type "anXMLtag" into the Data Name textfield
And I type "Select Multiple" in the Caption Text textfield
And I type "Hint" in the Hint textfield
And I type "value" in the Value textfield
And I click the Add Option link
And I type "Option 1" in the 1st Row of the Option English textfield
And I type "optionone" in the 1st Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 2" in the 2nd Row of the Option English textfield
And I type "optiontwo" in the 2nd Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 3" in the 3rd Row of the Option English textfield
And I type "optionthree" in the 3rd Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 4" in the 4th Row of the Option English textfield
And I type "optionfour" in the 4th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 5" in the 5th Row of the Option English textfield
And I type "optionfive" in the 5th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 6" in the 6th Row of the Option English textfield
And I type "optionsix" in the 6th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 7" in the 7th Row of the Option English textfield
And I type "optionseven" in the 7th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 8" in the 8th Row of the Option English textfield
And I type "optioneight" in the 8th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 9" in the 9th Row of the Option English textfield
And I type "optionnine" in the 9th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 10" in the 10th Row of the Option English textfield
And I type "optionten" in the 10th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 11" in the 11th Row of the Option English textfield
And I type "optioneleven" in the 11th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 12" in the 12th Row of the Option English textfield
And I type "optiontwelve" in the 12th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 13" in the 13th Row of the Option English textfield
And I type "optionthirteen" in the 13th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 14" in the 14th Row of the Option English textfield
And I type "optionfourteen" in the 14th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 15" in the 15th Row of the Option English textfield
And I type "optionfifteen" in the 15th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 16" in the 16th Row of the Option English textfield
And I type "optionsixteen" in the 16th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 17" in the 17th Row of the Option English textfield
And I type "optionseventeen" in the 17th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 18" in the 18th Row of the Option English textfield
And I type "optioneighteen" in the 18th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 19" in the 19th Row of the Option English textfield
And I type "optionnineteen" in the 19th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 20" in the 20th Row of the Option English textfield
And I type "optiontwenty" in the 20th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 21" in the 21th Row of the Option English textfield
And I type "optiontwentyone" in the 21th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 22" in the 22th Row of the Option English textfield
And I type "optiontwentytwo" in the 22th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 23" in the 23th Row of the Option English textfield
And I type "optiontwentythree" in the 23th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 24" in the 24th Row of the Option English textfield
And I type "optiontwentyfour" in the 24th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 25" in the 25th Row of the Option English textfield
And I type "optiontwentyfive" in the 25th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 26" in the 26th Row of the Option English textfield
And I type "optiontwentysix" in the 26th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 27" in the 27th Row of the Option English textfield
And I type "optiontwentyseven" in the 27th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 28" in the 28th Row of the Option English textfield
And I type "optiontwentyeight" in the 28th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 29" in the 29th Row of the Option English textfield
And I type "optiontwentynine" in the 29th Row of the Option Underlying Value textfield
And I click the Add Option link
And I type "Option 30" in the 30th Row of the Option English textfield
And I type "optionthirty" in the 30th Row of the Option Underlying Value textfield
And I click the Save Top button
And I type "Form TC6291Add33" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291Add33"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
When I validate I am on the Edit Form page
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"
And I validate the 2nd Row of the Option English textfield is "Option 2"
And I validate the 2nd Row of the Option Underlying Value textfield is "optiontwo"
And I validate the 3rd Row of the Option English textfield is "Option 3"
And I validate the 3rd Row of the Option Underlying Value textfield is "optionthree"
And I validate the 4th Row of the Option English textfield is "Option 4"
And I validate the 4th Row of the Option Underlying Value textfield is "optionfour"
And I validate the 5th Row of the Option English textfield is "Option 5"
And I validate the 5th Row of the Option Underlying Value textfield is "optionfive"
And I validate the 6th Row of the Option English textfield is "Option 6"
And I validate the 6th Row of the Option Underlying Value textfield is "optionsix"
And I validate the 7th Row of the Option English textfield is "Option 7"
And I validate the 7th Row of the Option Underlying Value textfield is "optionseven"
And I validate the 8th Row of the Option English textfield is "Option 8"
And I validate the 8th Row of the Option Underlying Value textfield is "optioneight"
And I validate the 9th Row of the Option English textfield is "Option 9"
And I validate the 9th Row of the Option Underlying Value textfield is "optionnine"
And I validate the 10th Row of the Option English textfield is "Option 10"
And I validate the 10th Row of the Option Underlying Value textfield is "optionten"
And I validate the 11th Row of the Option English textfield is "Option 11"
And I validate the 11th Row of the Option Underlying Value textfield is "optioneleven"
And I validate the 12th Row of the Option English textfield is "Option 12"
And I validate the 12th Row of the Option Underlying Value textfield is "optiontwelve"
And I validate the 13th Row of the Option English textfield is "Option 13"
And I validate the 13th Row of the Option Underlying Value textfield is "optionthirteen"
And I validate the 14th Row of the Option English textfield is "Option 14"
And I validate the 14th Row of the Option Underlying Value textfield is "optionfourteen"
And I validate the 15th Row of the Option English textfield is "Option 15"
And I validate the 15th Row of the Option Underlying Value textfield is "optionfifteen"
And I validate the 16th Row of the Option English textfield is "Option 16"
And I validate the 16th Row of the Option Underlying Value textfield is "optionsixteen"
And I validate the 17th Row of the Option English textfield is "Option 17"
And I validate the 17th Row of the Option Underlying Value textfield is "optionseventeen"
And I validate the 18th Row of the Option English textfield is "Option 18"
And I validate the 18th Row of the Option Underlying Value textfield is "optioneighteen"
And I validate the 19th Row of the Option English textfield is "Option 19"
And I validate the 19th Row of the Option Underlying Value textfield is "optionnineteen"
And I validate the 20th Row of the Option English textfield is "Option 20"
And I validate the 20th Row of the Option Underlying Value textfield is "optiontwenty"
And I validate the 21st Row of the Option English textfield is "Option 21"
And I validate the 21st Row of the Option Underlying Value textfield is "optiontwentyone"
And I validate the 22nd Row of the Option English textfield is "Option 22"
And I validate the 22nd Row of the Option Underlying Value textfield is "optiontwentytwo"
And I validate the 23rd Row of the Option English textfield is "Option 23"
And I validate the 23rd Row of the Option Underlying Value textfield is "optiontwentythree"
And I validate the 24th Row of the Option English textfield is "Option 24"
And I validate the 24th Row of the Option Underlying Value textfield is "optiontwentyfour"
And I validate the 25th Row of the Option English textfield is "Option 25"
And I validate the 25th Row of the Option Underlying Value textfield is "optiontwentyfive"
And I validate the 26th Row of the Option English textfield is "Option 26"
And I validate the 26th Row of the Option Underlying Value textfield is "optiontwentysix"
And I validate the 27th Row of the Option English textfield is "Option 27"
And I validate the 27th Row of the Option Underlying Value textfield is "optiontwentyseven"
And I validate the 28th Row of the Option English textfield is "Option 28"
And I validate the 28th Row of the Option Underlying Value textfield is "optiontwentyeight"
And I validate the 29th Row of the Option English textfield is "Option 29"
And I validate the 29th Row of the Option Underlying Value textfield is "optiontwentynine"
And I validate the 30th Row of the Option English textfield is "Option 30"
And I validate the 30th Row of the Option Underlying Value textfield is "optionthirty"

Scenario: TC6291 Forms Add Page - Add a Form - Create a fully functional Pre Trip form with all options
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291 Pre Trip Required" in the Name textfield
And I type "Form TC6291 Pre Trip Required Description" in the Description textfield
And I select the option containing "Pre Trip" from the Trigger dropdown
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
And I type "Form TC6291 Pre Trip Required" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291 Pre Trip Required"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
Then I validate the Name textfield is "Form TC6291 Pre Trip Required"
And I validate the Description textfield is "Form TC6291 Pre Trip Required Description"
And I validate the Trigger dropdown is "Pre Trip"
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

Scenario: TC6291 Forms Add Page - Add a Form - Create a fully functional Pre Trip form with no required boxes
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I check the 1st Row of the Groups checkbox
And I check the 1st Row of the Groups checkbox
And I type "Form TC6291 Pre Trip No Required" in the Name textfield
And I type "Form TC6291 Pre Trip No Required Description" in the Description textfield
And I select the option containing "Pre Trip" from the Trigger dropdown
And I select the option containing "Active" from the Status dropdown
And I click the Date link
And I type "date" in the Data Name textfield
And I type "Date" in the Caption Text textfield
And I type "Please enter a valid date" in the Hint textfield
And I check the Range Enable Date checkbox
And I type "2012-07-31" into the Minimum Date Range textfield
And I type "2012-09-01" into the Maximum Date Range textfield
And I type "Date must be within 2012-08-01 and 2012-08-31" into the Invalid Text textfield
And I click the Numeric link
And I type "integer" in the Data Name textfield
And I type "Please enter a integer" in the Caption Text textfield
And I type "Integer must be between 1 and 100" in the Hint textfield
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
And I type "Form TC6291 Pre Trip No Required" into the Search field
Then I validate the 1st Row of the Entry Name text is "Form TC6291 Pre Trip No Required"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Edit link
Then I validate the Name textfield is "Form TC6291 Pre Trip No Required"
And I validate the Description textfield is "Form TC6291 Pre Trip No Required Description"
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is "2"
And I validate the Data Name textfield is "selectMultiple"
And I validate the Caption Text textfield is "Select Multiple Caption"
And I validate the Hint textfield is "Select Multiple Hint"
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
And I validate the Range Enable Date checkbox is checked
And I validate the Minimum Date Range textfield is "2012-07-31"
And I validate the Maximum Date Range textfield is "2012-09-01"
And I validate the Invalid Text textfield is "Date must be within 2012-08-01 and 2012-08-31"
And I click the 2nd Row of the Control Flow Arrow link
And I validate the Data Name textfield is "integer"
And I validate the Caption Text textfield is "Please enter a integer"
And I validate the Hint textfield is "Integer must be between 1 and 100"
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
And I validate the 1st Row of the Option English textfield is "Option 1"
And I validate the 1st Row of the Option Underlying Value textfield is "optionone"
And I validate the 2nd Row of the Option English textfield is "Option 2"
And I validate the 2nd Row of the Option Underlying Value textfield is "optiontwo"
And I validate the 3rd Row of the Option English textfield is "Option 3"
And I validate the 3rd Row of the Option Underlying Value textfield is "optionthree"
And I validate the 4th Row of the Option English textfield is "Option 4"
And I validate the 4th Row of the Option Underlying Value textfield is "optionfour" 

