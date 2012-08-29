In order to view a list of Forms
As Administrator
I need a UI on the Administrator page that lists the Forms

Scenario: TCXXXX: Forms Admin Page - Table View - UI
Given I am logged in
When I click the Forms link
Then I validate the Create Form Top link is present
And I validate the Create Form Bottom link is present
And I validate the Working link is present
And I validate the Published link is present
And I validate the Records Per Page Working dropdown is present
And I validate the Search Working textfield is present
And I validate the Working Check All checkbox is present
And I validate the Sort By Select Working link is present
And I validate the Sort By Name Working link is present
And I validate the Sort By Base Form ID Working link is present
And I validate the Sort By Version Working link is present
And I validate the Sort By Description Working link is present
And I validate the Sort By Trigger Working link is present
And I validate the Sort By Publish link is present
And I validate the Sort By Edit link is present
And I validate the Entries Working text is present
And I click the Published link
And I validate the Records Per Page Published dropdown is present
And I validate the Search Published textfield is present
And I validate the Published Check All checkbox is present
And I validate the Sort By Select Published link is present
And I validate the Sort By Name Published link is present
And I validate the Sort By Base Form ID Published link is present
And I validate the Sort By Version Published link is present
And I validate the Sort By Description Published link is present
And I validate the Sort By Trigger Published link is present
And I validate the Entries Published text is present

Scenario: TCXXXX: Default Records dropdowns are set to 10
Given I am logged in
When I click the Forms link
Then I validate the Records Per Page Working dropdown is "10"
And I click the Published link
And I validate the Records Per Page Published dropdown is "10"

Scenario: TCXXXX: Forms Admin Page - Records per page test
Given I am logged in
When I click the Forms link
Then I validate the 11th Row of the Entry Name Working text is not present
And I validate the Entries text contains "Showing 1 to 10"
And I select "25" from the Records Per Page Working dropdown
And I validate the 26th Row of the Entry Name Working text is not present
And I validate the Entries text contains "Showing 1 to 25"
And I select "50" from the Records Per Page Working dropdown
And I validate the 51st Row of the Entry Name Working text is not present
And I validate the Entries text contains "Showing 1 to 50"
And I select "100" from the Records Per Page Working dropdown
And I validate the 101st Row of the Entry Name Working text is not present
And I validate the Entries text contains "Showing 1 to 100"
And I click the Published link
Then I validate the 11th Row of the Entry Name Published text is not present
And I validate the Entries text contains "Showing 1 to 10"
And I select "25" from the Records Per Page Published dropdown
And I validate the 26th Row of the Entry Name Published text is not present
And I validate the Entries text contains "Showing 1 to 25"
And I select "50" from the Records Per Page Published dropdown
And I validate the 51st Row of the Entry Name Published text is not present
And I validate the Entries text contains "Showing 1 to 50"
And I select "100" from the Records Per Page Published dropdown
And I validate the 101st Row of the Entry Name Published text is not present
And I validate the Entries text contains "Showing 1 to 100"

Scenario: TCXXXX: Forms Admin Page - Create Form link top
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
Then I validate I am on the Forms Add page

Scenario: TCXXXX: Forms Admin Page - Create Form link bottom
Given I am logged in
When I click the Forms link
And I click the Create Form Bottom link
Then I validate I am on the Forms Add page

Scenario: TCXXXX: Forms Admin Page - Create Form link top (cancel - no changes)
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the View Properties Label text is present
When I click the Cancel Top button
Then I validate I am on the Forms Admin page
When I click the Create Form Top link
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the View Properties Label text is present

Scenario: TCXXXX: Forms Admin Page - Create Form link top (cancel - changes)
Given I am logged in
When I click the Forms link
And I click the Create Form Top link
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the View Properties Label text is present
And I type "TCXXXX" into the Name field
And I type "TCXXXX" into the Description field
And I select "Post Trip" from the Trigger dropdown
And I type "Group 1" into the Filter Groups field
When I click the Cancel Top button
Then I validate I am on the Forms Admin page
When I click the Create Form Top link
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the View Properties Label text is present

Scenario: TCXXXX: Forms Admin Page - Create Form link bottom (cancel - no changes)
Given I am logged in
When I click the Forms link
And I click the Create Form Bottom link
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the View Properties Label text is present
When I click the Cancel Bottom button
Then I validate I am on the Forms Admin page
When I click the Create Form Bottom link
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the View Properties Label text is present

Scenario: TCXXXX: Forms Admin Page - Create Form link bottom (cancel - changes)
Given I am logged in
When I click the Forms link
And I click the Create Form Bottom link
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the View Properties Label text is present
And I type "TCXXXX" into the Name field
And I type "TCXXXX" into the Description field
And I select "Post Trip" from the Trigger dropdown
And I type "Group 1" into the Filter Groups field
When I click the Cancel Bottom button
Then I validate I am on the Forms Admin page
When I click the Create Form Bottom link
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the View Properties Label text is present

Scenario: TCXXXX: Forms Admin Page - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER IN A NEW STEP
Given I am logged in
When I click the Forms link
Then I validate the Sort By Select Working column sorts correctly
And I validate the Sort By Name Working column sorts correctly
And I validate the Sort By Base Form ID Working column sorts correctly
And I validate the Sort By Version Working column sorts correctly
And I validate the Sort By Description Working column sorts correctly
And I validate the Sort By Trigger Working column sorts correctly
And I validate the Sort By Publish Working column sorts correctly
And I validate the Sort By Edit Working column sorts correctly
And I validate the Sort By Select Published column sorts correctly
And I validate the Sort By Name Published column sorts correctly
And I validate the Sort By Base Form ID Published column sorts correctly
And I validate the Sort By Version column Published sorts correctly
And I validate the Sort By Description Published column sorts correctly
And I validate the Sort By Trigger Published column sorts correctly

Scenario: TCXXXX: Forms Admin Page - Forms Edit link
Given I am logged in
When I click the Forms link
And I save the 1st Row of the Entry Name Working text as SAVEDFORM
And I click the 1st Row of the Entry Edit link
Then I validate I am on the Edit Form page
And I validate the Name field is SAVEDFORM

Scenario: TCXXXX: Search - Working tab
Given I am logged in
When I click the Forms link
And I type "pretrip" into the Search Working textfield
Then I validate the 1st Row of the Entry Name Working text is "pretrip"
And I type "2140" into the Search Working textfield
And I validate the 1st Row of the Entry Base Form ID Working text contains "2140"
And I validate the 2nd Row of the Entry Base Form ID Working text contains "2140"
And I type "Schlumberger" into the Search Working textfield
And I validate the 1st Row of the Entry Description Working text contains "Schlumberger"
And I validate the 2nd Row of the Entry Description Working text contains "Schlumberger"
And I type "Post Trip" into the Search Working textfield
And I validate the 1st Row of the Entry Trigger Working text contains "Post Trip"
And I validate the 2nd Row of the Entry Trigger Working text contains "Post Trip"
And I validate the 3rd Row of the Entry Trigger Working text contains "Post Trip"

Scenario: TCXXXX: Search - Published tab
Given I am logged in
When I click the Forms link
And I click the Published link
And I type "testform205" into the Search Published textfield
Then I validate the 1st Row of the Entry Name Published text is "testform205"
And I validate the 2nd Row of the Entry Name Published text is "testform205"
And I type "283" into the Search Published textfield
And I validate the 1st Row of the Entry Base Form ID Published text contains "283"
And I validate the 2nd Row of the Entry Base Form ID Published text contains "283"
And I validate the 3rd Row of the Entry Base Form ID Published text contains "283"
And I type "Schlumberger" into the Search Published textfield
And I validate the 1st Row of the Entry Description Published text contains "Schlumberger"
And I validate the 2nd Row of the Entry Description Published text contains "Schlumberger"
And I type "Post Trip" into the Search Published textfield
And I validate the 1st Row of the Entry Trigger Published text contains "Post Trip"
And I validate the 2nd Row of the Entry Trigger Published text contains "Post Trip"
And I validate the 3rd Row of the Entry Trigger Published text contains "Post Trip"

Scenario: TCXXXX: Search - Published tab stays blank
Given I am logged in
When I click the Forms link
And I type "pretrip" into the Search Working textfield
Then I validate the 1st Row of the Entry Name Working text is "pretrip"
And I click the Published link
And I validate the Search Published textfield is ""

Scenario: TCXXXX: Search - Working tab stays blank
Given I am logged in
When I click the Forms link
And I click the Published link
And I type "testform205" into the Search Published textfield
Then I validate the 1st Row of the Entry Name Published text is "testform205"
And I click the Working link
And I validate the Search Working textfield is ""

Scenario: TCXXXX: Empty Search
Given I am logged in
When I click the Forms link
And I type "randomstringthatwillnotcomeup" into the Search Working textfield
Then I validate the No Records Working Error text is "No matching records found"
And I validate the Entries Working text contains "Showing 0 to 0 of 0 entries"
And I click the Published link
And I type "randomstringthatwillnotcomeup" into the Search Published textfield
And I validate the No Records Published Error text is "No matching records found"
And I validate the Entries Published text contains "Showing 0 to 0 of 0 entries"

//Future test, not currently implemented on this page
Scenario: TCXXXX: Edit columns
Given I am on the Admin page Forms tab
When I click on the Edit Columns link
And a popup opens to display the selections of columns
And I select the checkboxes for the columns I want to display
And click on Save button
Then the columns I selected display on the Forms page

//This delete button does not currently exist
Scenario: TCXXXX: Delete a selected Form
Given I am logged in
When I click the Forms link
And I save the 1st Row of the Name link as SAVEDFORM
And I click the 1st Row of the Select checkbox
And click the Delete button
Then I validate the 1st Row of the Name link is not SAVEDFORM