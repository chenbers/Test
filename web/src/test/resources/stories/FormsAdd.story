Narrative: 
In order for drivers to submit electronic forms
As an Administrator
I need an interface to create a form and to forward to devices for drivers to complete

Scenario: TCXXXX Forms Add Page - UI Test - Main page
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
Then I validate the Save Top button is present
And I validate the Save Bottom button is present
And I validate the Cancel Top button is present
And I validate the Cancel Bottom button is present
And I validate the Name Label text is present
And I validate the Description Label text is present
And I validate the Trigger Label text is present
And I validate the Version Label text is present
And I validate the Filter Groups Label text is present
And I validate the Properties Label text is present
And I validate the View Properties Label text is present
And I validate the Name textfield is present
And I validate the Description textfield is present
And I validate the Trigger dropdown is present
And I validate the Version text is present
And I validate the Filter Groups textfield is present
And I validate the Text link is present
And I validate the Numeric link is present
And I validate the Date link is present
And I validate the Choose One link is present
And I validate the Select Multiple link is present

Scenario: TCXXXX Forms Add Page - Save Top Button
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "Form TCXXXX" in the Name textfield
And I click the Text link
And I click the Save Top button
Then I validate I am on the Forms Admin page
When I type "Form TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Entry Name Working text is "Form TCXXXX"
When I click the Published link
And I type "Form TCXXXX" into the Search Published textfield
Then I validate the 1st Row of the Entry Name Published text is not present

Scenario: TCXXXX Forms Add Page - Save Bottom Button
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "Form TCXXXX" in the Name textfield
And I click the Text link
And I click the Save Bottom button
Then I validate I am on the Forms Admin page
When I type "Form TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Entry Name Working text is "Form TCXXXX"
When I click the Published link
And I type "Form TCXXXX" into the Search Published textfield
Then I validate the 1st Row of the Entry Name Published text is not present

Scenario: TCXXXX Forms Add Page - Cancel Top Button
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "Form TCXXXX" in the Name textfield
And I click the Text link
And I click the Cancel Top button
Then I validate I am on the Forms Admin page
When I type "Form TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Entry Name Working text is not present

Scenario: TCXXXX Forms Add Page - Cancel Bottom Button
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "Form TCXXXX" in the Name textfield
And I click the Text link
And I click the Cancel Bottom button
Then I validate I am on the Forms Admin page
When I type "Form TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Entry Name Working text is not present

Scenario: TCXXXX Forms Add Page - UI Test - Text link
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Text link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Caption Text textfield is present
And I validate the Hint textfield is present
And I validate the Default Value textfield is present
And I validate the Read Only checkbox is present
And I validate the Required checkbox is present
And I validate the Length Enable checkbox is present
And I validate the Minimum Text Length textfield is present
And I validate the Maximum Text Length textfield is present
And I validate the Minimum Text Inclusive checkbox is present
And I validate the Maximum Text Inclusive checkbox is present
And I validate the Invalid Text textfield is present
And I validate the Advanced Arrow button is present

Scenario: TCXXXX Forms Add Page - UI Test - Numeric link
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Numeric link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Caption Text textfield is present
And I validate the Hint textfield is present
And I validate the Default Value textfield is present
And I validate the Read Only checkbox is present
And I validate the Required checkbox is present
And I validate the Range Numeric checkbox is present
And I validate the Minimum Numeric Range textfield is present
And I validate the Maximum Numeric Range textfield is present
And I validate the Minimum Numeric Inclusive checkbox is present
And I validate the Maximum Numeric Inclusive checkbox is present
And I validate the Invalid Text textfield is present
And I validate the Kind dropdown is present
And I validate the Advanced Arrow button is present

Scenario: TCXXXX Forms Add Page - UI Test - Date link
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Date link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Caption Text textfield is present
And I validate the Hint textfield is present
And I validate the Default Value textfield is present
And I validate the Read Only checkbox is present
And I validate the Required checkbox is present
And I validate the Range Date checkbox is present
And I validate the Date Minimum dropdown is present
And I validate the Date Maximum dropdown is present
And I validate the Minimum Date Inclusive checkbox is present
And I validate the Maximum Date Inclusive checkbox is present
And I validate the Invalid Text textfield is present
And I validate the Advanced Arrow button is present

Scenario: TCXXXX Forms Add Page - UI Test - Choose One link
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Choose One link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Caption Text textfield is present
And I validate the Hint textfield is present
And I validate the Default Value textfield is present
And I validate the Read Only checkbox is present
And I validate the Required checkbox is present
And I validate the Add Option link is present
And I validate the Bulk Edit link is present
And I validate the Advanced Arrow button is present

Scenario: TCXXXX Forms Add Page - UI Test - Select Multiple link
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Select Multiple link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Caption Text textfield is present
And I validate the Hint textfield is present
And I validate the Default Value textfield is present
And I validate the Read Only checkbox is present
And I validate the Required checkbox is present
And I validate the Add Option link is present
And I validate the Bulk Edit link is present
And I validate the Advanced Arrow button is present

Scenario: TCXXXX Forms Add Page - Blank Data Name Error Text Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Text link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TCXXXX Forms Add Page - Blank Data Name Error Numeric Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Numeric link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TCXXXX Forms Add Page - Blank Data Name Error Date Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Date link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TCXXXX Forms Add Page - Blank Data Name Error Choose One Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Choose One link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TCXXXX Forms Add Page - Blank Data Name Error Select Multiple Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Select Multiple link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TCXXXX Forms Add Page - Duplicate Data Name Error Text Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Text link
And I type "test" into the Data Name field
And I click the Text link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TCXXXX Forms Add Page - Duplicate Data Name Error Numeric Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Numeric link
And I type "test" into the Data Name field
And I click the Numeric link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TCXXXX Forms Add Page - Duplicate Data Name Error Date Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Date link
And I type "test" into the Data Name field
And I click the Date link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TCXXXX Forms Add Page - Duplicate Data Name Error Choose One Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Choose One link
And I type "test" into the Data Name field
And I click the Choose One link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TCXXXX Forms Add Page - Duplicate Data Name Error Select Multiple Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Select Multiple link
And I type "test" into the Data Name field
And I click the Select Multiple link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TCXXXX Forms Add Page - Duplicate Data Name Error Group Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Group link
And I type "test" into the Data Name field
And I click the Group link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the Control Flow Arrow link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TCXXXX Forms Add Page - Duplicate Data Name Error All Fields
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
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
And I click the Group link
And I type "test" into the Data Name field
Then I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 1st Row of the Preview Area link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 2nd Row of the Preview Area link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 3rd Row of the Preview Area link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 4th Row of the Preview Area link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."
And I click the 5th Row of the Preview Area link
And I validate the Data Name Error text is "This property must be unique; there is another control that conflicts with it."

Scenario: TCXXXX Forms Add Page - Space Data Name Error Text Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Text link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Space Data Name Error Numeric Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Numeric link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Space Data Name Error Date Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Date link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Space Data Name Error Choose One Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Choose One link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Space Data Name Error Select Multiple Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Select Multiple link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Space Data Name Error All Fields
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
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
Then I validate the Data Name Error text is "Only letters and numbers are allowed."
And I click the 1st Row of the Preview Area link
And I validate the Data Name Error text is "Only letters and numbers are allowed."
And I click the 2nd Row of the Preview Area link
And I validate the Data Name Error text is "Only letters and numbers are allowed."
And I click the 3rd Row of the Preview Area link
And I validate the Data Name Error text is "Only letters and numbers are allowed."
And I click the 4th Row of the Preview Area link
And I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Symbols Data Name Error Text Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Text link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Symbols Data Name Error Numeric Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Numeric link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Symbols Data Name Error Date Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Date link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Symbols Data Name Error Choose One Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Choose One link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Symbols Data Name Error Select Multiple Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Select Multiple link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Symbols Data Name Error All Fields
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
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
Then I validate the Data Name Error text is "Only letters and numbers are allowed."
And I click the 1st Row of the Preview Area link
And I validate the Data Name Error text is "Only letters and numbers are allowed."
And I click the 2nd Row of the Preview Area link
And I validate the Data Name Error text is "Only letters and numbers are allowed."
And I click the 3rd Row of the Preview Area link
And I validate the Data Name Error text is "Only letters and numbers are allowed."
And I click the 4th Row of the Preview Area link
And I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Name required error
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Text link
And I click the Save Top button
Then I validate the Name Error text contains "Name is a required field"
And I validate the Control Error text is ""

Scenario: TCXXXX Forms Add Page - Control required error
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "Form TCXXXX" into the Name textfield
And I click the Save Top button
Then I validate the Control Error text contains "Form needs at least one control"
And I validate the Name Error text is ""

Scenario: TCXXXX Forms Add Page - Name and Control Errors
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Save Top button
Then I validate the Name Error text contains "Name is a required field"
And I validate the Control Error text contains "Form needs at least one control"

Scenario: TCXXXX Forms Add Page - Control Errors Add then remove
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "Form TCXXXX" into the Name textfield
And I click the Save Top button
Then I validate the Control Error text contains "Form needs at least one control"
And I validate the Name Error text is ""
And I click the Text link
And I type "" into the Name textfield
And I click the Save Top button
And I validate the Control Error text is ""
And I validate the Name Error text contains "Name is a required field"
And I click the Delete button
And I click the Save Top button
And I validate the Name Error text contains "Name is a required field"
And I validate the Control Error text contains "Form needs at least one control"

Scenario: TCXXXX ODK Add a Name Text Field - Read Only box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Text link
And I type "an XML tag" in the Data Name textfield
And I type “Driver Name” the Caption Text textfield
And I type "Hint Text" in the Hint textfield
And I type "Default Value Text" in the Default Value textfield
And I check the Read Only checkbox
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Read Only checkbox is checked

Scenario: TCXXXX ODK Add a Name Text Field - Required box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Text link
And I type "an XML tag" in the Data Name textfield
And I type “Driver Name” the Caption Text textfield
And I type "Hint Text" in the Hint textfield
And I type "Default Value Text" in the Default Value textfield
And I check the Required checkbox
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked

Scenario: TCXXXX ODK Add a Name Text Field - Length boxes checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Text link
And I type "an XML tag" in the Data Name textfield
And I type “Driver Name” the Caption Text textfield
And I type "Hint Text" in the Hint textfield
And I type "Default Value Text" in the Default Value textfield
And I check the Length Enable checkbox
And I check the Minimum Text Inclusive checkbox
And I check the Maximum Text Inclusive checkbox
And I type "1" into the Minimum Length textfield
And I type "4" into the  Maximum Length textfield
And I type "Value out of range" into the Invalid Text textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Length Enable checkbox is checked
And I validate the Minimum Text Inclusive checkbox is checked
And I validate the Maximum Text Inclusive checkbox is checked
And I validate the Minimum Length textfield is "1"
And I validate the Maximum Length textfield is "4"
And I validate the Invalid Text textfield is "Value out of range" 

Scenario: TCXXXX ODK Add a Name Text Field - Required and Length boxes checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Text link
And I type "an XML tag" in the Data Name textfield
And I type “Driver Name” the Caption Text textfield
And I type "Hint Text" in the Hint textfield
And I type "Default Value Text" in the Default Value textfield
And I check the Required checkbox
And I check the Length Enable checkbox
And I check the Minimum Text Inclusive checkbox
And I check the Maximum Text Inclusive checkbox
And I type "2" into the Minimum Length textfield
And I type "5" into the Maximum Length textfield
And I type "Value out of range" into the Invalid Text textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked
And I validate the Length Enable checkbox is checked
And I validate the Minimum Text Inclusive checkbox is checked
And I validate the Maximum Text Inclusive checkbox is checked
And I validate the Minimum Length textfield is "2"
And I validate the Maximum Length textfield is "5"
And I validate the Invalid Text textfield is "Value out of range" 

Scenario: TCXXXX ODK Add a Name Text Field - Required and Length boxes unchecked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Text link
And I type "an XML tag" in the Data Name textfield
And I type “Driver Name” the Caption Text textfield
And I type "Hint Text" in the Hint textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is not checked
And I validate the Length Enable checkbox is not checked
And I validate the Minimum Text Inclusive checkbox is not checked
And I validate the Maximum Text Inclusive checkbox is not checked
And I validate the Minimum Length textfield is ""
And I validate the Maximum Length textfield is "" 

Scenario: TCXXXX ODK Add a Text Field - Not inclusive
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Numeric link
And I type "an XML tag" into the Data Name textfield 
And I type “Unit” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "0000" in the Default Value textfield
And I check the Length Enable checkbox
And I type "1" into the Minimum Text Inclusive textfield
And I type "999" into the Maximum Text Inclusive textfield
And I type "Value out of range" into the Invalid Text textfield
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked
And I validate the Length Enable checkbox is checked
And I validate the Minimum Text Inclusive checkbox is not checked
And I validate the Maximum Text Inclusive checkbox is not checked
And I validate the Minimum Length textfield is "1"
And I validate the Maximum Length textfield is "999"
And I validate the Invalid Text textfield is "Value out of range"

Scenario: TCXXXX ODK Add a Numeric Field - Read Only box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Numeric link
And I type "an XML tag" into the Data Name textfield 
And I type “Unit” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "0000" in the Default Value textfield
And I check the Read Only checkbox
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Read Only checkbox is checked

Scenario: TCXXXX ODK Add a Numeric Field - Required box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Numeric link
And I type "an XML tag" into the Data Name textfield 
And I type “Unit” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "0000" in the Default Value textfield
And I check the Required checkbox
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked

Scenario: TCXXXX ODK Add a Numeric Field - Range boxes checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Numeric link
And I type "an XML tag" into the Data Name textfield 
And I type “Unit” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "0000" in the Default Value textfield
And I check the Range Enable Numeric checkbox
And I check the Minimum Numeric Inclusive checkbox
And I check the Maximum Numeric Inclusive checkbox
And I type "1" into the Minimum Numeric Range textfield
And I type "999" into the Maximum Numeric Range textfield
And I type "Value out of range" into the Invalid Text textfield
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Range Enable Numeric checkbox is checked
And I validate the Minimum Numeric Inclusive checkbox is checked
And I validate the Maximum Numeric Inclusive checkbox is checked
And I validate the Minimum Numeric Range textfield is "1"
And I validate the Maximum Numeric Range textfield is "999"
And I validate the Invalid Text textfield is "Value out of range" 

Scenario: TCXXXX ODK Add a Numeric Field - Required and Range boxes checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Numeric link
And I type "an XML tag" into the Data Name textfield 
And I type “Unit” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "0000" in the Default Value textfield
And I check the Required checkbox
And I check the Range Enable Numeric checkbox
And I check the Minimum Numeric Inclusive checkbox
And I check the Maximum Numeric Inclusive checkbox
And I type "1" into the Minimum Numeric Range textfield
And I type "999" into the Maximum Numeric Range textfield
And I type "Value out of range" into the Invalid Text textfield
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked
Then I validate the Range Enable Numeric checkbox is checked
And I validate the Minimum Numeric Inclusive checkbox is checked
And I validate the Maximum Numeric Inclusive checkbox is checked
And I validate the Minimum Numeric Range textfield is "1"
And I validate the Maximum Numeric Range textfield is "999"
And I validate the Invalid Text textfield is "Value out of range" 

Scenario: TCXXXX ODK Add a Numeric Field - Required and Range boxes unchecked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Numeric link
And I type "an XML tag" into the Data Name textfield 
And I type “Unit” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "0000" in the Default Value textfield
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is not checked
And I validate the Range Enable Numeric checkbox is not checked

Scenario: TCXXXX ODK Add a Numeric Field with Range minimum - Not inclusive
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Numeric link
And I type "an XML tag" into the Data Name textfield 
And I type “Unit” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "0000" in the Default Value textfield
And I check the Range Enable Numeric checkbox
And I type "1" into the Minimum Numeric Range textfield
And I type "999" into the Maximum Numeric Range textfield
And I type "Value out of range" into the Invalid Text textfield
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Range Enable Numeric checkbox is checked
And I validate the Minimum Numeric Range textfield is "1"
And I validate the Maximum Numeric Range textfield is "999"
And I validate the Invalid Text textfield is "Value out of range" 

Scenario: TCXXXX ODK Add a Date Field - Read Only box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Date link
And I type "an XML tag" into the Data Name textfield
And I type “Date” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I select today from the Default Value textfield
And I check the Read Only checkbox
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Read Only checkbox is checked

Scenario: TCXXXX ODK Add a Date Field - Required box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Date link
And I type "an XML tag" into the Data Name textfield
And I type “Date” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I select today from the Default Value textfield
And I check the Required checkbox
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked

Scenario: TCXXXX ODK Add a Date Field - Range boxes checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Date link
And I type "an XML tag" into the Data Name textfield 
And I type “Unit” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "2012-01-01" in the Default Value textfield
And I check the Range Enable Date checkbox
And I check the Minimum Date Inclusive checkbox
And I check the Maximum Date Inclusive checkbox
And I select "2012-02-01" from the Minimum Date Range dropdown
And I select "2012-03-01" from the Maximum Date Range dropdown
And I type "Value out of range" into the Invalid Text textfield
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Range Enable Date checkbox is checked
And I validate the Minimum Date Inclusive checkbox is checked
And I validate the Maximum Date Inclusive checkbox is checked
And I validate the Minimum Date Range dropdown is "2012-02-01"
And I validate the Maximum Date Range dropdown is "2012-03-01"
And I validate the Invalid Text textfield is "Value out of range"

Scenario: TCXXXX ODK Add a Date Field - Required and Range boxes checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Date link
And I type "an XML tag" into the Data Name textfield 
And I type “Unit” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "2012-01-01" in the Default Value textfield
And I check the Required checkbox
And I check the Range Enable Date checkbox
And I check the Minimum Date Inclusive checkbox
And I check the Maximum Date Inclusive checkbox
And I select "2012-02-01" from the Minimum Date Range dropdown
And I select "2012-03-01" from the Maximum Date Range dropdown
And I type "Value out of range" into the Invalid Text textfield
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked
And I validate the Range Enable Date checkbox is checked
And I validate the Minimum Date Inclusive checkbox is checked
And I validate the Maximum Date Inclusive checkbox is checked
And I validate the Minimum Date Range dropdown is "2012-02-01"
And I validate the Maximum Date Range dropdown is "2012-03-01"
And I validate the Invalid Text textfield is "Value out of range"

Scenario: TCXXXX ODK Add a Date Field - Required and Range boxes unchecked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Date link
And I type "an XML tag" into the Data Name textfield 
And I type “Unit” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "2012-01-01" in the Default Value textfield
And I click Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is not checked
And I validate the Range Enable Date checkbox is not checked

Scenario: TCXXXX ODK Add a Choose One Field - Read Only box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Choose One link
And I type "an XML tag" into the Data Name textfield
And I type “Choose One” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "Default Value" in the Default Value textfield
And I check the Read Only checkbox
And I click the Add Option link
And I type "Option 1" in the Option textfield
And I type "optionone" in the Underlying Value textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Read Only checkbox is checked

Scenario: TCXXXX ODK Add a Choose One Field - Required box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Choose One link
And I type "an XML tag" into the Data Name textfield
And I type “Choose One” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "Default Value" in the Default Value textfield
And I check the Required checkbox
And I click the Add Option link
And I type "Option 1" in the Option textfield
And I type "optionone" in the Underlying Value textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked

Scenario: TCXXXX ODK Add a Choose One Field - Read Only and Required box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Choose One link
And I type "an XML tag" into the Data Name textfield
And I type “Choose One” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "Default Value" in the Default Value textfield
And I check the Required checkbox
And I check the Read Only checkbox
And I click the Add Option link
And I type "Option 1" in the Option textfield
And I type "optionone" in the Underlying Value textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked
And  I validate the Read Only checkbox is checked

Scenario: TCXXXX ODK Add a Choose One Field - Read Only and Required box is not checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Choose One link
And I type "an XML tag" into the Data Name textfield
And I type “Choose One” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "Default Value" in the Default Value textfield
And I click the Add Option link
And I type "Option 1" in the Option textfield
And I type "optionone" in the Underlying Value textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is not checked
And  I validate the Read Only checkbox is not checked

Scenario: TCXXXX ODK Add a Choose One Field - 30 option list
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Choose One link
And I type "an XML tag" into the Data Name textfield
And I type “Choose One” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "2012-01-01" in the Default Value textfield
And I click the Add Option link
And I type "Option 1" in the 1st Option textfield
And I type "optionone" in the 1st Underlying Value textfield
And I click the Add Option link
And I type "Option 2" in the 2nd Option textfield
And I type "optiontwo" in the 2nd Underlying Value textfield
And I click the Add Option link
And I type "Option 3" in the 3rd Option textfield
And I type "optionthree" in the 3rd Underlying Value textfield
And I click the Add Option link
And I type "Option 4" in the 4th Option textfield
And I type "optionfour" in the 4th Underlying Value textfield
And I click the Add Option link
And I type "Option 5" in the 5th Option textfield
And I type "optionfive" in the 5th Underlying Value textfield
And I click the Add Option link
And I type "Option 6" in the 6th Option textfield
And I type "optionsix" in the 6th Underlying Value textfield
And I click the Add Option link
And I type "Option 7" in the 7th Option textfield
And I type "optionseven" in the 7th Underlying Value textfield
And I click the Add Option link
And I type "Option 8" in the 8th Option textfield
And I type "optioneight" in the 8th Underlying Value textfield
And I click the Add Option link
And I type "Option 9" in the 9th Option textfield
And I type "optionnine" in the 9th Underlying Value textfield
And I click the Add Option link
And I type "Option 10" in the 10th Option textfield
And I type "optionten" in the 10th Underlying Value textfield
And I click the Add Option link
And I type "Option 11" in the 11th Option textfield
And I type "optioneleven" in the 11th Underlying Value textfield
And I click the Add Option link
And I type "Option 12" in the 12th Option textfield
And I type "optiontwelve" in the 12th Underlying Value textfield
And I click the Add Option link
And I type "Option 13" in the 13th Option textfield
And I type "optionthirteen" in the 13th Underlying Value textfield
And I click the Add Option link
And I type "Option 14" in the 14th Option textfield
And I type "optionfourteen" in the 14th Underlying Value textfield
And I click the Add Option link
And I type "Option 15" in the 15th Option textfield
And I type "optionfifteen" in the 15th Underlying Value textfield
And I click the Add Option link
And I type "Option 16" in the 16th Option textfield
And I type "optionsixteen" in the 16th Underlying Value textfield
And I click the Add Option link
And I type "Option 17" in the 17th Option textfield
And I type "optionseventeen" in the 17th Underlying Value textfield
And I click the Add Option link
And I type "Option 18" in the 18th Option textfield
And I type "optioneighteen" in the 18th Underlying Value textfield
And I click the Add Option link
And I type "Option 19" in the 19th Option textfield
And I type "optionnineteen" in the 19th Underlying Value textfield
And I click the Add Option link
And I type "Option 20" in the 20th Option textfield
And I type "optiontwenty" in the 20th Underlying Value textfield
And I click the Add Option link
And I type "Option 21" in the 21st Option textfield
And I type "optiontwentyone" in the 21st Underlying Value textfield
And I click the Add Option link
And I type "Option 22" in the 22nd Option textfield
And I type "optiontwentytwo" in the 22nd Underlying Value textfield
And I click the Add Option link
And I type "Option 23" in the 23rd Option textfield
And I type "optiontwentythree" in the 23rd Underlying Value textfield
And I click the Add Option link
And I type "Option 24" in the 24th Option textfield
And I type "optiontwentyfour" in the 24th Underlying Value textfield
And I click the Add Option link
And I type "Option 25" in the 25th Option textfield
And I type "optiontwentyfive" in the 25th Underlying Value textfield
And I click the Add Option link
And I type "Option 26" in the 26th Option textfield
And I type "optiontwentysix" in the 26th Underlying Value textfield
And I click the Add Option link
And I type "Option 27" in the 27th Option textfield
And I type "optiontwentyseven" in the 27th Underlying Value textfield
And I click the Add Option link
And I type "Option 28" in the 28th Option textfield
And I type "optiontwentyeight" in the 28th Underlying Value textfield
And I click the Add Option link
And I type "Option 29" in the 29th Option textfield
And I type "optiontwentynine" in the 29th Underlying Value textfield
And I click the Add Option link
And I type "Option 30" in the 30th Option textfield
And I type "optionthirty" in the 30th Underlying Value textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
And I validate the 1st Option textfield is "Option 1"
And I validate the 1st Underlying Value textfield is "optionone"
And I validate the 2nd Option textfield is "Option 2"
And I validate the 2nd Underlying Value textfield is "optiontwo"
And I validate the 3rd Option textfield is "Option 3"
And I validate the 3rd Underlying Value textfield is "optionthree"
And I validate the 4th Option textfield is "Option 4"
And I validate the 4th Underlying Value textfield is "optionfour"
And I validate the 5th Option textfield is "Option 5"
And I validate the 5th Underlying Value textfield is "optionfive"
And I validate the 6th Option textfield is "Option 6"
And I validate the 6th Underlying Value textfield is "optionsix"
And I validate the 7th Option textfield is "Option 7"
And I validate the 7th Underlying Value textfield is "optionseven"
And I validate the 8th Option textfield is "Option 8"
And I validate the 8th Underlying Value textfield is "optioneight"
And I validate the 9th Option textfield is "Option 9"
And I validate the 9th Underlying Value textfield is "optionnine"
And I validate the 10th Option textfield is "Option 10"
And I validate the 10th Underlying Value textfield is "optionten"
And I validate the 11th Option textfield is "Option 11"
And I validate the 11th Underlying Value textfield is "optioneleven"
And I validate the 12th Option textfield is "Option 12"
And I validate the 12th Underlying Value textfield is "optiontwelve"
And I validate the 13th Option textfield is "Option 13"
And I validate the 13th Underlying Value textfield is "optionthirteen"
And I validate the 14th Option textfield is "Option 14"
And I validate the 14th Underlying Value textfield is "optionfourteen"
And I validate the 15th Option textfield is "Option 15"
And I validate the 15th Underlying Value textfield is "optionfifteen"
And I validate the 16th Option textfield is "Option 16"
And I validate the 16th Underlying Value textfield is "optionsixteen"
And I validate the 17th Option textfield is "Option 17"
And I validate the 17th Underlying Value textfield is "optionseventeen"
And I validate the 18th Option textfield is "Option 18"
And I validate the 18th Underlying Value textfield is "optioneighteen"
And I validate the 19th Option textfield is "Option 19"
And I validate the 19th Underlying Value textfield is "optionnineteen"
And I validate the 20th Option textfield is "Option 20"
And I validate the 20th Underlying Value textfield is "optiontwenty"
And I validate the 21st Option textfield is "Option 21"
And I validate the 21st Underlying Value textfield is "optiontwentyone"
And I validate the 22nd Option textfield is "Option 22"
And I validate the 22nd Underlying Value textfield is "optiontwentytwo"
And I validate the 23rd Option textfield is "Option 23"
And I validate the 23rd Underlying Value textfield is "optiontwentythree"
And I validate the 24th Option textfield is "Option 24"
And I validate the 24th Underlying Value textfield is "optiontwentyfour"
And I validate the 25th Option textfield is "Option 25"
And I validate the 25th Underlying Value textfield is "optiontwentyfive"
And I validate the 26th Option textfield is "Option 26"
And I validate the 26th Underlying Value textfield is "optiontwentysix"
And I validate the 27th Option textfield is "Option 27"
And I validate the 27th Underlying Value textfield is "optiontwentyseven"
And I validate the 28th Option textfield is "Option 28"
And I validate the 28th Underlying Value textfield is "optiontwentyeight"
And I validate the 29th Option textfield is "Option 29"
And I validate the 29th Underlying Value textfield is "optiontwentynine"
And I validate the 30th Option textfield is "Option 30"
And I validate the 30th Underlying Value textfield is "optionthirty"

Scenario: TCXXXX ODK Add a Select Multiple Field - Read Only box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Select Multiple link
And I type "an XML tag" into the Data Name textfield
And I type “Select Multiple” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "Default Value" in the Default Value textfield
And I check the Read Only checkbox
And I click the Add Option link
And I type "Option 1" in the Option textfield
And I type "optionone" in the Underlying Value textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Read Only checkbox is checked

Scenario: TCXXXX ODK Add a Select Multiple Field - Required box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Select Multiple link
And I type "an XML tag" into the Data Name textfield
And I type “Select Multiple” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "Default Value" in the Default Value textfield
And I check the Required checkbox
And I click the Add Option link
And I type "Option 1" in the Option textfield
And I type "optionone" in the Underlying Value textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked

Scenario: TCXXXX ODK Add a Select Multiple Field - Read Only and Required box checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Select Multiple link
And I type "an XML tag" into the Data Name textfield
And I type “Select Multiple” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "Default Value" in the Default Value textfield
And I check the Required checkbox
And I check the Read Only checkbox
And I click the Add Option link
And I type "Option 1" in the Option textfield
And I type "optionone" in the Underlying Value textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is checked
And  I validate the Read Only checkbox is checked

Scenario: TCXXXX ODK Add a Select Multiple Field - Read Only and Required box is not checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Select Multiple link
And I type "an XML tag" into the Data Name textfield
And I type “Select Multiple” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "Default Value" in the Default Value textfield
And I click the Add Option link
And I type "Option 1" in the Option textfield
And I type "optionone" in the Underlying Value textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
Then I validate the Required checkbox is not checked
And  I validate the Read Only checkbox is not checked

Scenario: TCXXXX ODK Add a Select Multiple Field - 30 option list
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "TCXXXX" into the Name textfield
And I click the Select Multiple link
And I type "an XML tag" into the Data Name textfield
And I type “Select Multiple” into the Caption Text textfield
And I type "Hint" into the Hint textfield
And I type "2012-01-01" in the Default Value textfield
And I click the Add Option link
And I type "Option 1" in the 1st Option textfield
And I type "optionone" in the 1st Underlying Value textfield
And I click the Add Option link
And I type "Option 2" in the 2nd Option textfield
And I type "optiontwo" in the 2nd Underlying Value textfield
And I click the Add Option link
And I type "Option 3" in the 3rd Option textfield
And I type "optionthree" in the 3rd Underlying Value textfield
And I click the Add Option link
And I type "Option 4" in the 4th Option textfield
And I type "optionfour" in the 4th Underlying Value textfield
And I click the Add Option link
And I type "Option 5" in the 5th Option textfield
And I type "optionfive" in the 5th Underlying Value textfield
And I click the Add Option link
And I type "Option 6" in the 6th Option textfield
And I type "optionsix" in the 6th Underlying Value textfield
And I click the Add Option link
And I type "Option 7" in the 7th Option textfield
And I type "optionseven" in the 7th Underlying Value textfield
And I click the Add Option link
And I type "Option 8" in the 8th Option textfield
And I type "optioneight" in the 8th Underlying Value textfield
And I click the Add Option link
And I type "Option 9" in the 9th Option textfield
And I type "optionnine" in the 9th Underlying Value textfield
And I click the Add Option link
And I type "Option 10" in the 10th Option textfield
And I type "optionten" in the 10th Underlying Value textfield
And I click the Add Option link
And I type "Option 11" in the 11th Option textfield
And I type "optioneleven" in the 11th Underlying Value textfield
And I click the Add Option link
And I type "Option 12" in the 12th Option textfield
And I type "optiontwelve" in the 12th Underlying Value textfield
And I click the Add Option link
And I type "Option 13" in the 13th Option textfield
And I type "optionthirteen" in the 13th Underlying Value textfield
And I click the Add Option link
And I type "Option 14" in the 14th Option textfield
And I type "optionfourteen" in the 14th Underlying Value textfield
And I click the Add Option link
And I type "Option 15" in the 15th Option textfield
And I type "optionfifteen" in the 15th Underlying Value textfield
And I click the Add Option link
And I type "Option 16" in the 16th Option textfield
And I type "optionsixteen" in the 16th Underlying Value textfield
And I click the Add Option link
And I type "Option 17" in the 17th Option textfield
And I type "optionseventeen" in the 17th Underlying Value textfield
And I click the Add Option link
And I type "Option 18" in the 18th Option textfield
And I type "optioneighteen" in the 18th Underlying Value textfield
And I click the Add Option link
And I type "Option 19" in the 19th Option textfield
And I type "optionnineteen" in the 19th Underlying Value textfield
And I click the Add Option link
And I type "Option 20" in the 20th Option textfield
And I type "optiontwenty" in the 20th Underlying Value textfield
And I click the Add Option link
And I type "Option 21" in the 21st Option textfield
And I type "optiontwentyone" in the 21st Underlying Value textfield
And I click the Add Option link
And I type "Option 22" in the 22nd Option textfield
And I type "optiontwentytwo" in the 22nd Underlying Value textfield
And I click the Add Option link
And I type "Option 23" in the 23rd Option textfield
And I type "optiontwentythree" in the 23rd Underlying Value textfield
And I click the Add Option link
And I type "Option 24" in the 24th Option textfield
And I type "optiontwentyfour" in the 24th Underlying Value textfield
And I click the Add Option link
And I type "Option 25" in the 25th Option textfield
And I type "optiontwentyfive" in the 25th Underlying Value textfield
And I click the Add Option link
And I type "Option 26" in the 26th Option textfield
And I type "optiontwentysix" in the 26th Underlying Value textfield
And I click the Add Option link
And I type "Option 27" in the 27th Option textfield
And I type "optiontwentyseven" in the 27th Underlying Value textfield
And I click the Add Option link
And I type "Option 28" in the 28th Option textfield
And I type "optiontwentyeight" in the 28th Underlying Value textfield
And I click the Add Option link
And I type "Option 29" in the 29th Option textfield
And I type "optiontwentynine" in the 29th Underlying Value textfield
And I click the Add Option link
And I type "Option 30" in the 30th Option textfield
And I type "optionthirty" in the 30th Underlying Value textfield
And I click the Save Top button
And I type "TCXXXX" into the Search Working textfield
Then I validate the 1st Row of the Name Working text is "TCXXXX"
When I click the 1st Row of the Edit link
And I validate the 1st Option textfield is "Option 1"
And I validate the 1st Underlying Value textfield is "optionone"
And I validate the 2nd Option textfield is "Option 2"
And I validate the 2nd Underlying Value textfield is "optiontwo"
And I validate the 3rd Option textfield is "Option 3"
And I validate the 3rd Underlying Value textfield is "optionthree"
And I validate the 4th Option textfield is "Option 4"
And I validate the 4th Underlying Value textfield is "optionfour"
And I validate the 5th Option textfield is "Option 5"
And I validate the 5th Underlying Value textfield is "optionfive"
And I validate the 6th Option textfield is "Option 6"
And I validate the 6th Underlying Value textfield is "optionsix"
And I validate the 7th Option textfield is "Option 7"
And I validate the 7th Underlying Value textfield is "optionseven"
And I validate the 8th Option textfield is "Option 8"
And I validate the 8th Underlying Value textfield is "optioneight"
And I validate the 9th Option textfield is "Option 9"
And I validate the 9th Underlying Value textfield is "optionnine"
And I validate the 10th Option textfield is "Option 10"
And I validate the 10th Underlying Value textfield is "optionten"
And I validate the 11th Option textfield is "Option 11"
And I validate the 11th Underlying Value textfield is "optioneleven"
And I validate the 12th Option textfield is "Option 12"
And I validate the 12th Underlying Value textfield is "optiontwelve"
And I validate the 13th Option textfield is "Option 13"
And I validate the 13th Underlying Value textfield is "optionthirteen"
And I validate the 14th Option textfield is "Option 14"
And I validate the 14th Underlying Value textfield is "optionfourteen"
And I validate the 15th Option textfield is "Option 15"
And I validate the 15th Underlying Value textfield is "optionfifteen"
And I validate the 16th Option textfield is "Option 16"
And I validate the 16th Underlying Value textfield is "optionsixteen"
And I validate the 17th Option textfield is "Option 17"
And I validate the 17th Underlying Value textfield is "optionseventeen"
And I validate the 18th Option textfield is "Option 18"
And I validate the 18th Underlying Value textfield is "optioneighteen"
And I validate the 19th Option textfield is "Option 19"
And I validate the 19th Underlying Value textfield is "optionnineteen"
And I validate the 20th Option textfield is "Option 20"
And I validate the 20th Underlying Value textfield is "optiontwenty"
And I validate the 21st Option textfield is "Option 21"
And I validate the 21st Underlying Value textfield is "optiontwentyone"
And I validate the 22nd Option textfield is "Option 22"
And I validate the 22nd Underlying Value textfield is "optiontwentytwo"
And I validate the 23rd Option textfield is "Option 23"
And I validate the 23rd Underlying Value textfield is "optiontwentythree"
And I validate the 24th Option textfield is "Option 24"
And I validate the 24th Underlying Value textfield is "optiontwentyfour"
And I validate the 25th Option textfield is "Option 25"
And I validate the 25th Underlying Value textfield is "optiontwentyfive"
And I validate the 26th Option textfield is "Option 26"
And I validate the 26th Underlying Value textfield is "optiontwentysix"
And I validate the 27th Option textfield is "Option 27"
And I validate the 27th Underlying Value textfield is "optiontwentyseven"
And I validate the 28th Option textfield is "Option 28"
And I validate the 28th Underlying Value textfield is "optiontwentyeight"
And I validate the 29th Option textfield is "Option 29"
And I validate the 29th Underlying Value textfield is "optiontwentynine"
And I validate the 30th Option textfield is "Option 30"
And I validate the 30th Underlying Value textfield is "optionthirty"
//START HERE
Scenario: TCXXXX Forms Add Page - Add a Form - Pre Trip
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I type "Form TCXXXX" in the Name textfield
And I type "Form TCXXXX Description" in the Description textfield
And I select "Pre Trip" from the Trigger dropdown
And I select "Heavy Duty Only" from the HOS dropdown
And I select "some vehicle tag" from the Vehicle Tags dropdown
And I type "fleet" in the Filter Groups textfield
And I check the "some group name" checkbox
And I click the Date link
And I type "date" in the Data Name textfield
And I type "Date" in the Caption Text textfield
And I type "Please enter a valid date" into the Hint textfield
And I select 7 days in the past from the Date Minimum
And I select today from the Date Maximum
And I type "Date must be within 7 days of today (including today)" in the Invalid Text textfield
And I click the Numeric link
And I type "numeric" in the Data Name textfield
And I type "Please enter a number" in the Caption textfield
And I type "Number must be between 1 and 100" into the Hint textfield
And I check the Required checkbox
And I check the Range Numeric checkbox
And I type "1" into the Minimum Range textfield
And I type "100" into the Maximum Range textfield
And I check the Minimum Range checkbox
And I check the Maximum Range checkbox
And I type "Number must be between 1 and 100" into the Invalid Text textfield
And I click the Text link
And I type "text" in the Data Name textfield
And I type "Text Caption" in the Caption Text textfield
And I type "Text Hint" into the Hint textfield
And I click the Length Enable checkbox
And I type "1" into the Minimum Range textfield
And I type "10" into the Maximum Range textfield
And I check the Minimum Range checkbox
And I check the Maximum Range checkbox
And I type "Must be less than 10 characters" into the Invalid Text textfield
And I click the Save Top button
And I click the Admin link
Then I validate the Form link is "Form TCXXXX"
And I validate the Version text is "1"
And I validate the Description text is "Form TCXXXX Description"
And I validate the Trigger text is "Pre Trip"
And I click the "Form TCXXXX" link 
And I validate the Name textfield is "Form TCXXXX"
And I validate the Description textfield is "Form TCXXXX Description"
And I validate the Trigger dropdown is "Pre Trip"
And I validate the HOS dropdown is "Heavy Duty Only"
And I validate the Vehicle Tags dropdown is "some vehicle tag"
And I validate the Group Name text is "some group name"
And I click the 1st Date link
And I validate the Data Name textfield is "date"
And I validate the Caption Text textfield is "Date"
And I validate the Hint textfield is "Please enter a valid date"
And I validate the Date Minimum dropdown is 7 days in the past
And I validate the Date Maximum dropdown is today
And I validate the Invalid Text textfield is "Date must be within 7 days of today (including today)"
And I click the 1st Numeric link
And I validate the Data Name textfield is "numeric"
And I validate the Caption textfield is "Please enter a number"
And I validate the Hint textfield is "Number must be between 1 and 1000"
And I validate the Required checkbox is checked
And I validate the Range Numeric checkbox is checked
And I validate the Minimum Range textfield is "1"
And I validate the Maximum Range textfield is "100"
And I validate the Minimum Range checkbox is checked
And I validate the Maximum Range checkbox is checked
And I validate the Invalid Text textfield is "Number must be between 1 and 1000"
And I click the 1st Text link
And I validate the Data Name textfield is "text"
And I validate the Caption Text textfield is "Text Caption"
And I validate the Hint textfield is "Text Hint"
And I validate the Length Enable checkbox is checked
And I validate the Minimum Range textfield is "1"
And I validate the Maximum Range textfield is "10"
And I validate the Minimum Range checkbox is checked
And I validate the Maximum Range checkbox is checked
And I validate the Invalid Text textfield is "Must be less than 10 characters"
And does not get forwarded to vehicles until published  // will need to be done via android emulation

//FUTURE TESTS
Scenario: TCXXXX Forms Add Page - UI Test - Group link
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Group link
Then I validate the Delete button is present
And I validate the Data Name textfield is present
And I validate the Label textfield is present
And I validate the Looped checkbox is present
And I validate the Display On One Screen checkbox is present
And I validate the Advanced Arrow button is present

Scenario: TCXXXX Forms Add Page - Blank Data Name Error Group Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Group link
And I type "" into the Data Name field
Then I validate the Data Name Error text is "This property is required."

Scenario: TCXXXX Forms Add Page - Space Data Name Error Group Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Group link
And I type "te st" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX Forms Add Page - Symbols Data Name Error Group Field
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Group link
And I type "!" into the Data Name field
Then I validate the Data Name Error text is "Only letters and numbers are allowed."

Scenario: TCXXXX ODK Add a Group Field - Looped unchecked, Display On One Screen unchecked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Group link
And I type "an XML tag" into the Data Name textfield
And I type “Choose One” into the Label textfield
And I click the Save button
And I validate the new form displays in the list on the Admin Form page

Scenario: TCXXXX ODK Add a Group Field - Looped checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Group link
And I type "an XML tag" into the Data Name textfield
And I type “Choose One” into the Label textfield
And I check the Looped checkbox
And I click the Save button
Then I validate the new form displays in the list on the Admin Form page
And I validate the information I entered is on the form

Scenario: TCXXXX ODK Add a Group Field - Display On One Screen checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Group link
And I type "an XML tag" into the Data Name textfield
And I type “Choose One” into the Label textfield
And I check the Display On One Screen checkbox
And I click the Save button
Then I validate the new form displays in the list on the Admin Form page
And I validate the information I entered is on the form

Scenario: TCXXXX ODK Add a Group Field - Looped checked, Display On One Screen checked
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
And I click the Group link
And I type "an XML tag" into the Data Name textfield
And I type “Choose One” into the Label textfield
And I check the Looped checkbox
And I check the Display On One Screen checkbox
And I click the Save button
Then I validate the new form displays in the list on the Admin Form page
And I validate the information I entered is on the form

Scenario: Create Form using ODK templates save without Properites
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
When I click the "<formtextfield>" link
And I click Save
Then the form is saved with the selected form textfield captions

Examples:
|text|
|numeric|
|date|
|choose one|
|select multiple|
|group|