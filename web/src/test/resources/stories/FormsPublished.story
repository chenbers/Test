In order to view a list of Forms
As Administrator
I need a UI on the Administrator page that lists the Forms

Scenario: Forms Published Page - Generate needed forms
Given I am logged in
When I go to the forms manage page
Then I generate 100 forms for the publish page test

Scenario: TCXXXX: Forms Published Page - Table View - UI
Given I am logged in
When I go to the forms manage page
And I click the Published link
Then I validate the Manage link is present
And I validate the Published link is present
And I validate the Submissions link is present
And I validate the Customers link is present
And I validate the Records Per Page dropdown is present
And I validate the Search textfield is present
And I validate the Sort By Name link is present
And I validate the Sort By Base Form ID link is present
And I validate the Sort By Version link is present
And I validate the Sort By Description link is present
And I validate the Sort By Trigger link is present
And I validate the Entries text is present

Scenario: TCXXXX: Forms Published Page - Default Records dropdowns are set to 10
Given I am logged in
When I go to the forms manage page
And I click the Published link
Then I validate the Records Per Page dropdown is "10"

Scenario: TCXXXX: Forms Published Page - Records per page test
Given I am logged in
When I go to the forms manage page
And I click the Published link
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

Scenario: TCXXXX: Search - Forms Published Page - Create and publish a form
Given I am logged in
When I go to the forms manage page
And I click the New Form button
And I type "A Published Test Form" into the Name textfield
And I click the Groups checkbox
And I click the Text link
And I click the Save Top button
And I click the 1st Row of the Gear button
And I click the 1st Row of the Publish link
And I click the Published link
Then I validate the 1st Row of the Entry Name text is "A Published Test Form"
And I click the Manage link
And I validate the 1st Row of the Entry Version text is "2"

Scenario: TCXXXX: Search - Published tab
Given I am logged in
When I go to the forms manage page
And I click the Published link
And I type "FormPublish" into the Search textfield
Then I validate the 1st Row of the Entry Name text contains "FormPublish"
And I validate the 2nd Row of the Entry Name text contains "FormPublish"
And I validate the 3rd Row of the Entry Name text contains "FormPublish"
And I validate the 4th Row of the Entry Name text contains "FormPublish"
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
And I type "Pre Trip" into the Search textfield
And I validate the 1st Row of the Entry Trigger text contains "Pre Trip"
And I validate the 2nd Row of the Entry Trigger text contains "Pre Trip"
And I validate the 3rd Row of the Entry Trigger text contains "Pre Trip"
And I validate the 4th Row of the Entry Trigger text contains "Pre Trip"
And I type "Post Trip" into the Search textfield
And I validate the 1st Row of the Entry Trigger text contains "Post Trip"
And I validate the 2nd Row of the Entry Trigger text contains "Post Trip"
And I validate the 3rd Row of the Entry Trigger text contains "Post Trip"
And I validate the 4th Row of the Entry Trigger text contains "Post Trip"

Scenario: TCXXXX: Search - Published tab stays blank
Given I am logged in
When I go to the forms manage page
And I type "Pre Trip" into the Search textfield
And I click the Published link
Then I validate the Search textfield is ""
And I click the Manage link
And I validate the Search textfield is ""

Scenario: TCXXXX: Empty Search
Given I am logged in
When I go to the forms manage page
And I click the Published link
And I type "randomstringthatwillnotcomeup" into the Search textfield
And I validate the No Records Error text is "No matching records found"
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"
And I validate the Previous link is not present
And I validate the Next link is not present