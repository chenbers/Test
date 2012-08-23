These tests will confirm the Options Editor pop up is working correctly.
It is accessed by clicking the bulk edit link within the Options section on
the Choose One and Choose Multiple fields.

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - UI
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Options Editor Label text is present
And I validate the Presets Label text is present
And I validate the Choose A Preset Label text is present
And I validate the Choose A Preset dropdown is present
And I validate the Load Preset link is present
And I validate the Delete Preset link is present
And I validate the Save To Preset link is present
And I validate the Options Label text is present
And I validate the Option Underlying Value text is present
And I validate the Option English textfield is present
And I validate the English textfield is present
And I validate the Apply link is present
And I validate the Cancel link is present
When I click the Save To Preset link
And the Save To Preset popup opens
Then I validate the Preset textfield is present
And I validate the Cancel button is present
And I validate the OK button is present
And I type "preset TCXXXX" into the Preset textfield
When I click the OK button
And the Save To Preset popup closes
And I click the Delete Preset link
And I the Delete Preset popup opens
Then I validate the Cancel button is present
And I validate the OK button is present
And I click the OK button

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Save to Preset - Cancel (no changes)
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Save To Preset link
And the Save To Preset popup opens
And I click the Cancel button
And the Save To Preset popup closes
And I validate the Choose A Preset dropdown is "(none created yet)"
And I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Cancel link
And the Options Editor popup closes
And I validate the Option English textfield is "English1" 
And I validate the Option Underlying Value textfield is "english1"

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Save to Preset - Cancel - (changes)
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Save To Preset link
And the Save To Preset popup opens
And I type "PresetTCXXXX" into the Name textfield
And I click the Cancel button
And the Save To Preset popup closes
And I validate the Choose A Preset dropdown is "(none created yet)"
And I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I type "" into the Option English textfield
And I type "" into the Option Underlying Value textfield
And I click the Cancel link
And the Options Editor popup closes
And I validate the Option English textfield is "English1" 
And I validate the Option Underlying Value textfield is "english1"

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Save to Preset - OK (no changes)
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Save To Preset link
And the Save To Preset popup opens
And I click the OK button
And the Save To Preset popup closes
And I validate the Choose A Preset dropdown is "(none created yet)"
And I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Apply link
And the Options Editor popup closes
And I validate the Option English textfield is "English1" 
And I validate the Option Underlying Value textfield is "english1"

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Save to Preset - OK - (changes)
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Save To Preset link
And the Save To Preset popup opens
And I type "PresetTCXXXX" into the Name textfield
And I click the OK button
And the Save To Preset popup closes
And I validate the Choose A Preset dropdown is "PresetTCXXXX"
And I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I type "English2" into the Option English textfield
And I type "english2" into the Option Underlying Value textfield
And I click the Apply link
And the Options Editor popup closes
And I validate the Option English textfield is "English2"
And I validate the Option Underlying Value textfield is "english2"

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Save to Preset - OK - (changes) - Blank
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Save To Preset link
And the Save To Preset popup opens
And I type "PresetTCXXXX" into the Name textfield
And I click the OK button
And the Save To Preset popup closes
And I validate the Choose A Preset dropdown is "PresetTCXXXX"
And I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I type "" into the Option English textfield
And I type "" into the Option Underlying Value textfield
And I click the Apply link
And the Options Editor popup closes
And I validate the Option English textfield is not present
And I validate the Option Underlying Value textfield is not present

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Load Preset - Cancel
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Save To Preset link
And the Save To Preset popup opens
And I type "PresetTCXXXX" into the Name textfield
And I click the OK button
And the Save To Preset popup closes
And I select "PresetTCXXXX" from the Choose A Preset dropdown
And I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I type "" into the Option English textfield
And I type "" into the Option Underlying Value textfield
And I click the Cancel link
And the Options Editor popup closes
And I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Load Preset - Apply
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
And I type "EnglishTCXXXX" into the Option English textfield
And I type "ulvTCXXXX" into the Option Underlying Value textfield
And I click the Bulk Edit link
And the Options Editor popup opens
And I click the Save To Preset link
And the Save To Preset popup opens
And I type "PresetTCXXXX" into the Name textfield
And I click the OK button
And the Save To Preset popup closes
And I type "EnglishTCXXXX" into the Option English textfield
And I type "ulvTCXXXX" into the Option Underlying Value textfield
And I click the Save To Preset link
And the Save To Preset popup opens
And I type "PresetTCXXXX2" into the Name textfield
And I click the OK button
And the Save To Preset popup closes
And I select "PresetTCXXXX" from the Choose A Preset dropdown
And I click the Load Preset link
Then I validate the Option English textfield is "EnglishTCXXXX"
And I validate the Option Underlying Value textfield is "ulvTCXXXX"
And I select "PresetTCXXXX2" from the Choose A Preset dropdown
And I click the Load Preset link
And I validate the Option English textfield is "EnglishTCXXXX2"
And I validate the Option Underlying Value textfield is "ulvTCXXXX2"
And I click the Apply link
And the Options Editor popup closes
And I validate the Option English textfield is "EnglishTCXXXX2"
And I validate the Option Underlying Value textfield is "ulvTCXXXX2"

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Delete Preset - Cancel
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Save To Preset link
And the Save To Preset popup opens
And I type "PresetTCXXXX" into the Name textfield
And I click the OK button
And the Save To Preset popup closes
And I validate the Choose A Preset dropdown is "PresetTCXXXX"
And I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Delete Preset link
And the Delete Preset popup opens
And I click the Cancel button
And the Delete Preset popup closes
And I validate the Choose A Preset dropdown is "PresetTCXXXX"

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Delete Preset - OK
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Save To Preset link
And the Save To Preset popup opens
And I type "PresetTCXXXX" into the Name textfield
And I click the OK button
And the Save To Preset popup closes
And I validate the Choose A Preset dropdown is "PresetTCXXXX"
And I validate the Option English textfield is "English1"
And I validate the Option Underlying Value textfield is "english1"
And I click the Delete Preset link
And the Delete Preset popup opens
And I click the OK button
And the Delete Preset popup closes
And I validate the Choose A Preset dropdown does not contain "PresetTCXXXX"

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Values - Cancel (no changes)
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
Then I validate the Option English textfield is "" 
And I validate the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I click the Cancel link
And the Options Editor popup closes
Then I validate the Option English textfield is "" 
And I validate the Option Underlying Value textfield is ""

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Values - Cancel (changes)
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
Then I validate the Option English textfield is "" 
And I validate the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
And I click the Cancel link
And the Options Editor popup closes
Then I validate the Option English textfield is "" 
And I validate the Option Underlying Value textfield is ""

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Values - Apply (no changes)
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
Then I validate the Option English textfield is "" 
And I validate the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I click the Apply link
And the Options Editor popup closes
Then I validate the Option English textfield is "" 
And I validate the Option Underlying Value textfield is ""

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Values - Apply (changes)
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
Then I validate the Option English textfield is "" 
And I validate the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
And I click the Apply link
And the Options Editor popup closes
Then I validate the Option English textfield is "English1" 
And I validate the Option Underlying Value textfield is "english1"

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Type text then open pop up
Given I am logged in
When I click the Forms link
And I click the Add Form link
And I click the Choose One link
And I click the Add Option link
And I type "English1" into the Option English textfield
And I type "english1" into the Option Underlying Value textfield
When I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Option English textfield is "English1" 
And I validate the Option Underlying Value textfield is "english1"
