In order to view a list of Forms
As Administrator
I need a UI on the Administrator page that lists the Forms

Scenario: TCXXXX: Forms Admin Page - Table View - UI
Given I am logged in
When I go to the forms admin page
Then I validate the Title text is "Forms"
And I validate the Add Form Top link is present
And I validate the Manage link is present
And I validate the Published link is present
And I validate the Records Per Page Manage dropdown is present
And I validate the Search Manage textfield is present
And I validate the Sort By Name Manage link is present
And I validate the Sort By Base Form ID Manage link is present
And I validate the Sort By Version Manage link is present
And I validate the Sort By Description Manage link is present
And I validate the Sort By Status Manage link is present
And I validate the Sort By Trigger Manage link is present
And I validate the Sort By Publish link is present
And I validate the Sort By Edit link is present
And I validate the Entries Manage text is present
And I click the Published link
And I validate the Records Per Page Published dropdown is present
And I validate the Search Published textfield is present
And I validate the Sort By Name Published link is present
And I validate the Sort By Base Form ID Published link is present
And I validate the Sort By Version Published link is present
And I validate the Sort By Description Published link is present
And I validate the Sort By Trigger Published link is present
And I validate the Entries Published text is present

Scenario: TCXXXX: Default Records dropdowns are set to 10
Given I am logged in
When I go to the forms admin page
Then I validate the Records Per Page Manage dropdown is "10"
And I click the Published link
And I validate the Records Per Page Published dropdown is "10"

Scenario: TCXXXX: Forms Admin Page - Records per page test
Given I am logged in
When I go to the forms admin page
And I select "100" from the Records Per Page Manage dropdown
And I click the 1st Row of the Entry Publish link
And I click the Manage link
And I click the 2nd Row of the Entry Publish link
And I click the Manage link
And I click the 3rd Row of the Entry Publish link
And I click the Manage link
And I click the 4th Row of the Entry Publish link
And I click the Manage link
And I click the 5th Row of the Entry Publish link
And I click the Manage link
And I click the 6th Row of the Entry Publish link
And I click the Manage link
And I click the 7th Row of the Entry Publish link
And I click the Manage link
And I click the 8th Row of the Entry Publish link
And I click the Manage link
And I click the 9th Row of the Entry Publish link
And I click the Manage link
And I click the 10th Row of the Entry Publish link
And I click the Manage link
And I click the 11th Row of the Entry Publish link
And I click the Manage link
And I click the 12th Row of the Entry Publish link
And I click the Manage link
And I click the 13th Row of the Entry Publish link
And I click the Manage link
And I click the 14th Row of the Entry Publish link
And I click the Manage link
And I click the 15th Row of the Entry Publish link
And I click the Manage link
And I click the 16th Row of the Entry Publish link
And I click the Manage link
And I click the 17th Row of the Entry Publish link
And I click the Manage link
And I click the 18th Row of the Entry Publish link
And I click the Manage link
And I click the 19th Row of the Entry Publish link
And I click the Manage link
And I click the 20th Row of the Entry Publish link
And I click the Manage link
And I click the 21st Row of the Entry Publish link
And I click the Manage link
And I click the 22nd Row of the Entry Publish link
And I click the Manage link
And I click the 23rd Row of the Entry Publish link
And I click the Manage link
And I click the 24th Row of the Entry Publish link
And I click the Manage link
And I click the 25th Row of the Entry Publish link
And I click the Manage link
And I click the 26th Row of the Entry Publish link
And I click the Manage link
And I click the 27th Row of the Entry Publish link
And I click the Manage link
And I click the 28th Row of the Entry Publish link
And I click the Manage link
And I click the 29th Row of the Entry Publish link
And I click the Manage link
And I click the 30th Row of the Entry Publish link
And I click the Manage link
And I click the 31st Row of the Entry Publish link
And I click the Manage link
And I click the 32nd Row of the Entry Publish link
And I click the Manage link
And I click the 33rd Row of the Entry Publish link
And I click the Manage link
And I click the 34th Row of the Entry Publish link
And I click the Manage link
And I click the 35th Row of the Entry Publish link
And I click the Manage link
And I click the 36th Row of the Entry Publish link
And I click the Manage link
And I click the 37th Row of the Entry Publish link
And I click the Manage link
And I click the 38th Row of the Entry Publish link
And I click the Manage link
And I click the 39th Row of the Entry Publish link
And I click the Manage link
And I click the 40th Row of the Entry Publish link
And I click the Manage link
And I click the 41st Row of the Entry Publish link
And I click the Manage link
And I click the 42nd Row of the Entry Publish link
And I click the Manage link
And I click the 43rd Row of the Entry Publish link
And I click the Manage link
And I click the 44th Row of the Entry Publish link
And I click the Manage link
And I click the 45th Row of the Entry Publish link
And I click the Manage link
And I click the 46th Row of the Entry Publish link
And I click the Manage link
And I click the 47th Row of the Entry Publish link
And I click the Manage link
And I click the 48th Row of the Entry Publish link
And I click the Manage link
And I click the 49th Row of the Entry Publish link
And I click the Manage link
And I click the 50th Row of the Entry Publish link
And I click the Manage link
And I click the 51st Row of the Entry Publish link
And I click the Manage link
And I click the 52nd Row of the Entry Publish link
And I click the Manage link
And I click the 53rd Row of the Entry Publish link
And I click the Manage link
And I click the 54th Row of the Entry Publish link
And I click the Manage link
And I click the 55th Row of the Entry Publish link
And I click the Manage link
And I click the 56th Row of the Entry Publish link
And I click the Manage link
And I click the 57th Row of the Entry Publish link
And I click the Manage link
And I click the 58th Row of the Entry Publish link
And I click the Manage link
And I click the 59th Row of the Entry Publish link
And I click the Manage link
And I click the 60th Row of the Entry Publish link
And I click the Manage link
And I click the 61st Row of the Entry Publish link
And I click the Manage link
And I click the 62nd Row of the Entry Publish link
And I click the Manage link
And I click the 63rd Row of the Entry Publish link
And I click the Manage link
And I click the 64th Row of the Entry Publish link
And I click the Manage link
And I click the 65th Row of the Entry Publish link
And I click the Manage link
And I click the 66th Row of the Entry Publish link
And I click the Manage link
And I click the 67th Row of the Entry Publish link
And I click the Manage link
And I click the 68th Row of the Entry Publish link
And I click the Manage link
And I click the 69th Row of the Entry Publish link
And I click the Manage link
And I click the 70th Row of the Entry Publish link
And I click the Manage link
And I click the 71st Row of the Entry Publish link
And I click the Manage link
And I click the 72nd Row of the Entry Publish link
And I click the Manage link
And I click the 73rd Row of the Entry Publish link
And I click the Manage link
And I click the 74th Row of the Entry Publish link
And I click the Manage link
And I click the 75th Row of the Entry Publish link
And I click the Manage link
And I click the 76th Row of the Entry Publish link
And I click the Manage link
And I click the 77th Row of the Entry Publish link
And I click the Manage link
And I click the 78th Row of the Entry Publish link
And I click the Manage link
And I click the 79th Row of the Entry Publish link
And I click the Manage link
And I click the 80th Row of the Entry Publish link
And I click the Manage link
And I click the 81st Row of the Entry Publish link
And I click the Manage link
And I click the 82nd Row of the Entry Publish link
And I click the Manage link
And I click the 83rd Row of the Entry Publish link
And I click the Manage link
And I click the 84th Row of the Entry Publish link
And I click the Manage link
And I click the 85th Row of the Entry Publish link
And I click the Manage link
And I click the 86th Row of the Entry Publish link
And I click the Manage link
And I click the 87th Row of the Entry Publish link
And I click the Manage link
And I click the 88th Row of the Entry Publish link
And I click the Manage link
And I click the 89th Row of the Entry Publish link
And I click the Manage link
And I click the 90th Row of the Entry Publish link
And I click the Manage link
And I click the 91st Row of the Entry Publish link
And I click the Manage link
And I click the 92nd Row of the Entry Publish link
And I click the Manage link
And I click the 93rd Row of the Entry Publish link
And I click the Manage link
And I click the 94th Row of the Entry Publish link
And I click the Manage link
And I click the 95th Row of the Entry Publish link
And I click the Manage link
And I click the 96th Row of the Entry Publish link
And I click the Manage link
And I click the 97th Row of the Entry Publish link
And I click the Manage link
And I click the 98th Row of the Entry Publish link
And I click the Manage link
And I click the 99th Row of the Entry Publish link
And I click the Manage link
And I click the 100th Row of the Entry Publish link
And I click the Manage link
And I select "10" from the Records Per Page Manage dropdown
Then I validate the 11th Row of the Entry Name Manage text is not present
And I validate the Entries Manage text contains "Showing 1 to 10"
And I select "25" from the Records Per Page Manage dropdown
And I validate the 26th Row of the Entry Name Manage text is not present
And I validate the Entries Manage text contains "Showing 1 to 25"
And I select "50" from the Records Per Page Manage dropdown
And I validate the 51st Row of the Entry Name Manage text is not present
And I validate the Entries Manage text contains "Showing 1 to 50"
And I select "100" from the Records Per Page Manage dropdown
And I validate the 101st Row of the Entry Name Manage text is not present
And I validate the Entries Manage text contains "Showing 1 to 100"
And I click the Published link
And I validate the 11th Row of the Entry Name Published text is not present
And I validate the Entries Published text contains "Showing 1 to 10"
And I select "25" from the Records Per Page Published dropdown
And I validate the 26th Row of the Entry Name Published text is not present
And I validate the Entries Published text contains "Showing 1 to 25"
And I select "50" from the Records Per Page Published dropdown
And I validate the 51st Row of the Entry Name Published text is not present
And I validate the Entries Published text contains "Showing 1 to 50"
And I select "100" from the Records Per Page Published dropdown
And I validate the 101st Row of the Entry Name Published text is not present
And I validate the Entries Published text contains "Showing 1 to 100"

Scenario: TCXXXX: Forms Admin Page - Add Form link top
Given I am logged in
When I go to the forms admin page
And I click the Add Form Top link
Then I validate I am on the Forms Add page

Scenario: TCXXXX: Forms Admin Page - Add Form link top (cancel - no changes)
Given I am logged in
When I go to the forms admin page
And I click the Add Form Top link
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the View Properties Label text is present
When I click the Cancel Top button
Then I validate I am on the Forms Admin page
When I click the Add Form Top link
Then I validate I am on the Forms Add page
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the View Properties Label text is present

Scenario: TCXXXX: Forms Admin Page - Add Form link top (cancel - changes)
Given I am logged in
When I go to the forms admin page
And I click the Add Form Top link
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the View Properties Label text is present
When I type "TCXXXX" into the Name field
And I type "TCXXXX" into the Description field
And I select "Post Trip" from the Trigger dropdown
And I type "fleet" into the Filter Groups field
And I check the 1st Row of the Groups checkbox
And I click the Cancel Top button
Then I validate I am on the Forms Admin page
When I click the Add Form Top link
Then I validate I am on the Forms Add page
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the View Properties Label text is present

Scenario: TCXXXX: Forms Admin Page - Add Form link bottom (cancel - no changes)
Given I am logged in
When I go to the forms admin page
And I click the New Form button
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the View Properties Label text is present
When I click the Cancel Bottom button
Then I validate I am on the Forms Admin page
When I click the New Form button
Then I validate I am on the Forms Add page
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the View Properties Label text is present

Scenario: TCXXXX: Forms Admin Page - Add Form link bottom (cancel - changes)
Given I am logged in
When I go to the forms admin page
And I click the New Form button
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the View Properties Label text is present
When I type "TCXXXX" into the Name field
And I type "TCXXXX" into the Description field
And I select "Post Trip" from the Trigger dropdown
And I type "fleet" into the Filter Groups field
And I check the 1st Row of the Groups checkbox
And I click the Cancel Bottom button
Then I validate I am on the Forms Admin page
When I click the New Form button
Then I validate I am on the Forms Add page
And I validate the Name field is ""
And I validate the Description field is ""
And I validate the Trigger dropdown is "Pre Trip"
And I validate the Version text is ""
And I validate the Filter Groups field is ""
And I validate the 1st Row of the Groups checkbox is not checked
And I validate the View Properties Label text is present

Scenario: TCXXXX: Forms Admin Page - Forms Edit link
Given I am logged in
When I go to the forms admin page
And I save the 1st Row of the Entry Name Manage text as SAVEDFORM
And I click the 1st Row of the Entry Edit link
Then I validate I am on the Edit Form page
And I validate the Name field is SAVEDFORM

Scenario: TCXXXX: Search - Manage tab
Given I am logged in
When I go to the forms admin page
And I type "TCXXXX1" into the Search Manage textfield
Then I validate the 1st Row of the Entry Name Manage text contains "TCXXXX1"
And I validate the 2nd Row of the Entry Name Manage text contains "TCXXXX1"
And I validate the 3rd Row of the Entry Name Manage text contains "TCXXXX1"
And I type "FORM_1347" into the Search Manage textfield
And I validate the 1st Row of the Entry Base Form ID Manage text contains "FORM_1347"
And I validate the 2nd Row of the Entry Base Form ID Manage text contains "FORM_1347"
And I type "Required" into the Search Manage textfield
And I validate the 1st Row of the Entry Description Manage text contains "Required"
And I validate the 2nd Row of the Entry Description Manage text contains "Required"
And I type "Pre Trip" into the Search Manage textfield
And I validate the 1st Row of the Entry Trigger Manage text contains "Pre Trip"
And I validate the 2nd Row of the Entry Trigger Manage text contains "Pre Trip"
And I type "Post Trip" into the Search Manage textfield
And I validate the 1st Row of the Entry Trigger Manage text contains "Post Trip"
And I validate the 2nd Row of the Entry Trigger Manage text contains "Post Trip"

Scenario: TCXXXX: Search - Published tab
Given I am logged in
When I go to the forms admin page
And I click the Published link
And I type "TCXXXX1" into the Search Published textfield
Then I validate the 1st Row of the Entry Name Published text contains "TCXXXX1"
And I validate the 2nd Row of the Entry Name Published text contains "TCXXXX1"
And I type "FORM_1347" into the Search Published textfield
And I validate the 1st Row of the Entry Base Form ID Published text contains "FORM_1347"
And I validate the 2nd Row of the Entry Base Form ID Published text contains "FORM_1347"
And I type "Required" into the Search Published textfield
And I validate the 1st Row of the Entry Description Published text contains "Required"
And I validate the 2nd Row of the Entry Description Published text contains "Required"
And I type "Pre Trip" into the Search Published textfield
And I validate the 1st Row of the Entry Trigger Published text contains "Pre Trip"
And I validate the 2nd Row of the Entry Trigger Published text contains "Pre Trip"
And I type "Post Trip" into the Search Published textfield
And I validate the 1st Row of the Entry Trigger Published text contains "Post Trip"
And I validate the 2nd Row of the Entry Trigger Published text contains "Post Trip"

Scenario: TCXXXX: Search - Published tab stays blank
Given I am logged in
When I go to the forms admin page
And I type "Pre Trip" into the Search Manage textfield
And I click the Published link
Then I validate the Search Published textfield is ""

Scenario: TCXXXX: Search - Manage tab stays blank
Given I am logged in
When I go to the forms admin page
And I click the Published link
And I type "Pre Trip" into the Search Published textfield
And I click the Manage link
Then I validate the Search Manage textfield is ""

Scenario: TCXXXX: Empty Search
Given I am logged in
When I go to the forms admin page
And I type "randomstringthatwillnotcomeup" into the Search Manage textfield
Then I validate the No Records Manage Error text is "No matching records found"
And I validate the Entries Manage text contains "Showing 0 to 0 of 0 entries"
And I click the Published link
And I type "randomstringthatwillnotcomeup" into the Search Published textfield
And I validate the No Records Published Error text is "No matching records found"
And I validate the Entries Published text contains "Showing 0 to 0 of 0 entries"