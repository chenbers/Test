In order to view a list of Forms
As Administrator
I need a UI on the Administrator page that lists the Forms

Scenario: TCXXXX: Search - Manage tab
Given I am logged in
When I go to the forms manage page
And I type "TCXXXX1" into the Search textfield
Then I validate the 1st Row of the Entry Name text contains "TCXXXX1"
And I validate the 2nd Row of the Entry Name text contains "TCXXXX1"
And I validate the 3rd Row of the Entry Name text contains "TCXXXX1"
And I type "FORM_1" into the Search textfield
And I validate the 1st Row of the Entry Base Form ID contains "FORM_1"
And I validate the 2nd Row of the Entry Base Form ID contains "FORM_1"
And I type "Required" into the Search textfield
And I validate the 1st Row of the Entry Description contains "Required"
And I validate the 2nd Row of the Entry Description contains "Required"
And I type "Pre Trip" into the Search textfield
And I validate the 1st Row of the Entry Trigger contains "Pre Trip"
And I validate the 2nd Row of the Entry Trigger contains "Pre Trip"
And I type "Post Trip" into the Search textfield
And I validate the 1st Row of the Entry Trigger contains "Post Trip"
And I validate the 2nd Row of the Entry Trigger contains "Post Trip"

Scenario: TCXXXX: Search - Published tab stays blank
Given I am logged in
When I go to the forms manage page
And I type "Pre Trip" into the Search textfield
And I click the Published link
Then I validate the Search textfield is ""
And I click the Manage link
And I validate the Search textfield is ""

Scenario: TCXXXX: Search - Manage tab stays blank
Given I am logged in
When I go to the forms manage page
And I click the Published link
And I type "Pre Trip" into the Search textfield
And I click the Manage link
Then I validate the Search textfield is ""
And I click the Published link
And I validate the Search textfield is ""

Scenario: TCXXXX: Empty Search
Given I am logged in
When I go to the forms manage page
And I type "randomstringthatwillnotcomeup" into the Search textfield
Then I validate the No Records Manage Error text is "No matching records found"
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"
And I validate the Previous link is not clickable
And I validate the Next link is not clickable
And I validate the Page Number link is not present
And I click the Published link
And I type "randomstringthatwillnotcomeup" into the Search textfield
And I validate the No Records Published Error text is "No matching records found"
And I validate the Entries Published text contains "Showing 0 to 0 of 0 entries"
And I validate the Previous link is not clickable
And I validate the Next link is not clickable
And I validate the Page Number link is not present