These tests will confirm the Options Editor pop up is working correctly.
It is accessed by clicking the bulk edit link within the Options section on
the Choose One and Choose Multiple fields.

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - UI
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Choose One link
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Options Editor Label text is present
And I validate the Options Label text is present
And I validate the 1st Row of the Option Underlying Value textfield is present
And I validate the 1st Row of the Option English textfield is present
And I validate the Apply link is present
And I validate the Cancel link is present

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Values - Cancel (no changes)
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Choose One link
And I click the Add Option link
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I click the Cancel link
And the Options Editor popup closes
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Values - Cancel (changes)
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Choose One link
And I click the Add Option link
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I type "English1" into the 1st Row of the Option English textfield
And I type "underlyingvalue1" into the 1st Row of the Option Underlying Value textfield
And I click the Cancel link
And the Options Editor popup closes
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Values - Apply (no changes)
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Choose One link
And I click the Add Option link
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I click the Apply link
And the Options Editor popup closes
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Values - Apply (changes)
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Choose One link
And I click the Add Option link
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I type "English1" into the 1st Row of the Option English textfield
And I type "underlyingvalue1" into the 1st Row of the Option Underlying Value textfield
And I click the Apply link
And the Options Editor popup closes
Then I validate the 1st Row of the Option English textfield is "English1" 
And I validate the 1st Row of the Option Underlying Value textfield is "underlyingvalue1"

Scenario: TCXXXX Add Form - Choose One - Options Editor popup - Type text then open pop up
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Choose One link
And I click the Add Option link
And I type "English1" into the 1st Row of the Option English textfield
And I type "underlyingvalue1" into the 1st Row of the Option Underlying Value textfield
When I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the 1st Row of the Option English textfield is "English1" 
And I validate the 1st Row of the Option Underlying Value textfield is "underlyingvalue1"

Scenario: TCXXXX Add Form - Select Multiple - Options Editor popup - UI
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Select Multiple link
And I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the Options Editor Label text is present
And I validate the Options Label text is present
And I validate the 1st Row of the Option Underlying Value textfield is present
And I validate the 1st Row of the Option English textfield is present
And I validate the Apply link is present
And I validate the Cancel link is present

Scenario: TCXXXX Add Form - Select Multiple - Options Editor popup - Values - Cancel (no changes)
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Select Multiple link
And I click the Add Option link
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I click the Cancel link
And the Options Editor popup closes
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""

Scenario: TCXXXX Add Form - Select Multiple - Options Editor popup - Values - Cancel (changes)
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Select Multiple link
And I click the Add Option link
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I type "English1" into the 1st Row of the Option English textfield
And I type "underlyingvalue1" into the 1st Row of the Option Underlying Value textfield
And I click the Cancel link
And the Options Editor popup closes
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""

Scenario: TCXXXX Add Form - Select Multiple - Options Editor popup - Values - Apply (no changes)
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Select Multiple link
And I click the Add Option link
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I click the Apply link
And the Options Editor popup closes
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""

Scenario: TCXXXX Add Form - Select Multiple - Options Editor popup - Values - Apply (changes)
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Select Multiple link
And I click the Add Option link
Then I validate the 1st Row of the Option English textfield is "" 
And I validate the 1st Row of the Option Underlying Value textfield is ""
When I click the Bulk Edit link
And the Options Editor popup opens
And I type "English1" into the 1st Row of the Option English textfield
And I type "underlyingvalue1" into the 1st Row of the Option Underlying Value textfield
And I click the Apply link
And the Options Editor popup closes
Then I validate the 1st Row of the Option English textfield is "English1" 
And I validate the 1st Row of the Option Underlying Value textfield is "underlyingvalue1"

Scenario: TCXXXX Add Form - Select Multiple - Options Editor popup - Type text then open pop up
Given I am logged in
When I go to the forms admin page
And I click the Create Form Top link
And I click the Select Multiple link
And I click the Add Option link
And I type "English1" into the 1st Row of the Option English textfield
And I type "underlyingvalue1" into the 1st Row of the Option Underlying Value textfield
When I click the Bulk Edit link
And the Options Editor popup opens
Then I validate the 1st Row of the Option English textfield is "English1" 
And I validate the 1st Row of the Option Underlying Value textfield is "underlyingvalue1"
