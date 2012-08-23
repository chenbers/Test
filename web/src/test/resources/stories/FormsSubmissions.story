Narrative: In order to view submitted forms within a time period
As an Administrator
I need a place to view and edit existing submitted forms.

Scenario: TCXXXX Forms Submissions Page - UI Test
Given I am logged in
When I click the Forms link
And I click the Submissions link
Then I validate the Form dropdown is present
And I validate the Date dropdown is present
And I validate the Refresh button is present
And I validate the Records Per Page dropdown is present
And I validate the Sort By Date Time link is present
And I validate the Sort By Group link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Form link is present
And I validate the Sort By Edited link is present
And I validate the Sort By Approved link is present
And I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Edited dropdown is present
And I validate the Approved dropdown is present
And I validate the Entries text is present

Scenario: TCXXXX Default Forms dropdown set to All Forms
Given I am logged in
When I click the Forms link
And I click the Submissions link
Then I validate the Form dropdown is "All Forms"

Scenario: TCXXXX Default Records dropdown set to 10
Given I am logged in
When I click the Forms link
And I click the Submissions link
Then I validate the Records dropdown is "10"

Scenario: TCXXXX Forms Blank Table Message
Given I am logged in
When I click the Forms link
And I click the Submissions link
Then I validate the 1st Row of the Form Entry text is not present
And I validate the No Records Found Error text is present
And I validate the No Records Found Error text is "No data available in table"
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

Scenario: TCXXXX Forms No Records Found Table Message
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Today" from the Date dropdown
And I click the Refresh button
Then I validate the 1st Row of the Form Entry text is not present
And I validate the No Records Found Error text is present
And I validate the No Records Found Error text is "No matching records found"
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

Scenario: TCXXXX Select a Specific Form from dropdown
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "PreTrip" from the Form dropdown
And I validate the 1st Row of the Form link is "PreTrip"
And I validate the 2nd Row of the Form link is "PreTrip"
And I validate the 3rd Row of the Form link is "PreTrip"
And I validate the 4th Row of the Form link is "PreTrip"
And I validate the 5th Row of the Form link is "PreTrip"
And I validate the 6th Row of the Form link is "PreTrip"
And I validate the 7th Row of the Form link is "PreTrip"
And I validate the 8th Row of the Form link is "PreTrip"
And I validate the 9th Row of the Form link is "PreTrip"
And I validate the 10th Row of the Form link is "PreTrip"
And I click the Sort By Form link
And I validate the 1st Row of the Form link is "PreTrip"
And I validate the 2nd Row of the Form link is "PreTrip"
And I validate the 3rd Row of the Form link is "PreTrip"
And I validate the 4th Row of the Form link is "PreTrip"
And I validate the 5th Row of the Form link is "PreTrip"
And I validate the 6th Row of the Form link is "PreTrip"
And I validate the 7th Row of the Form link is "PreTrip"
And I validate the 8th Row of the Form link is "PreTrip"
And I validate the 9th Row of the Form link is "PreTrip"
And I validate the 10th Row of the Form link is "PreTrip"

Scenario: TCXXXX Click on Group Name link
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I click the 1st Row of the Group Entry link
Then I validate I am on the Team Driver Statistics page

Scenario: TCXXXX Click on Driver Name link
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I click the 1st Row of the Driver Entry link
Then I validate I am on the Driver Performance page

Scenario: TCXXXX Click on Vehicle name link
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I click the 1st Row of the Vehicle Entry link
Then I validate I am on the Vehicle Performance page

Scenario: TCXXXX Approve a Form checkbox
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I check the 1st Row of the Approved Entry checkbox
Then I validate the 1st Row of the Approved Entry checkbox is checked

Scenario: TCXXXX Cancel Approved (Change back to Submitted)
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I check the 1st Row of the Approved Entry checkbox
Then I validate the 1st Row of the Approved Entry checkbox is checked
And I uncheck the 1st Row of the Approved checkbox
And I validate the 1st Row of the Approved checkbox is not checked

Scenario: TCXXXX: Forms Submissions Page - Records per page test
Given I am logged in
When I click the Forms link
And I click the Submissions link
Then I validate the 11th Row of the Date Time Entry text is not present
And I validate the Entries text contains "Showing 1 to 10"
And I select 25 from the Records Per Page dropdown
And I validate the 26th Row of the Date Time Entry text is not present
And I validate the Entries text contains "Showing 1 to 25"
And I select 50 from the Records Per Page dropdown
And I validate the 51st Row of the Date Time Entry text is not present
And I validate the Entries text contains "Showing 1 to 50"
And I select 100 from the Records Per Page dropdown
And I validate the 101st Row of the Date Time Entry text is not present
And I validate the Entries text contains "Showing 1 to 100"

Scenario: TCXXXX: Forms Submissions Page - Bookmark Entry
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I validate I am on the Forms Submissions page
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I validate I am on the Login page
Given I am logged in
Then I validate I am on the Submissions page

Scenario: TCXXXX: Forms Submissions Page - Bookmark Entry to Different Account
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I validate I am on the Forms Submissions page
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I validate I am on the Login page
Given I am logged in an account that can be edited
Then I validate I am on the Forms Submissions page

Scenario: TCXXXX: Forms Submissions Page - Edit Approved check (No change to Edited column)
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I check the 1st Row of the Approved Entry checkbox
Then I validate the 1st Row of the Approved Entry checkbox is checked
And I validate the Edited Entry text is "no"

Scenario: TCXXXX: Forms Submissions Page - Edit Approved check (Change to Edited column)
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I check the 1st Row of the Approved Entry checkbox
Then I validate the 1st Row of the Approved Entry checkbox is checked
And I click the Refresh button
And I validate the Edited Entry text is "yes"

Scenario: TCXXXX: Forms Submissions Page - Edit Approved uncheck (No change to Edited column)
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I uncheck the 1st Row of the Approved Entry checkbox
Then I validate the 1st Row of the Approved Entry checkbox is unchecked
And I click the Refresh button
And I validate the Edited Entry text is "yes"

Scenario: TCXXXX: Forms Submissions Page - Table Properties NEED TO IMPLEMENT CHECKING ALPHABETICAL ORDER IN A NEW STEP
Given I am logged in
When I click the Forms link
And I click the Submissions link
Then I validate the Sort By Date Time column sorts correctly
And I click the Sort By Group link
And I validate the Sort By Group column sorts correctly
And I click the Sort By Driver link
And I validate the Sort By Driver column sorts correctly
And I click the Sort By Vehicle link
And I validate the Sort By Vehicle column sorts correctly
And I click the Sort By Form link
And I validate the Sort By Form column sorts correctly
And I click the Sort By Edited link
And I validate the Sort By Edited column sorts correctly
And I click the Sort By Approved link
And I validate the Sort By Approved column sorts correctly

//ADD INLINE TESTS HERE
Scenario: TCXXXX: Forms Submissions Page - Inline Edit - All Forms (make sure you cannot do)
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "All Forms" from the Form dropdown
And I click the Refresh button
And I double click the 1st Row of the Form text
Then I validate the Group textfield is present
And I validate the Driver textfield is present
And I validate the Vehicle textfield is present
And I validate the Edited dropdown is present
And I validate the Approved dropdown is present

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - Cancel Changes
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Inline Edit Test Form" from the Form dropdown
And I click the Refresh button
And I click the Sort By Edited link
Then I validate the 1st Row of the Edited text is "no"
When I save the 1st Row of the Text text as SAVEDTEXT
And I save the 1st Row of the Numeric text as SAVEDNUMERIC
And I save the 1st Row of the Decimal text as SAVEDDECIMAL
And I save the 1st Row of the Date text as SAVEDDATE
And I save the 1st Row of the Chooseone text as SAVEDCHOOSEONE
And I save the 1st Row of the Choosemany text as SAVEDCHOOSEMANY
And I double click the 1st Row of the Date Time text
And I type "" into the 1st Row of the Text textfield
And I type "" into the 1st Row of the Numeric textfield
And I type "" into the 1st Row of the Decimal textfield
And I select today from the 1st Row of the Inline Date dropdown
And I select "No" from the 1st Row of the Chooseone dropdown
And I select Option 1 from the Choosemany scrollbox
And I click the 1st Row of the Cancel button
Then I validate the 1st Row of the Text text is SAVEDTEXT
And I validate the 1st Row of the Numeric text is SAVEDNUMERIC
And I validate the 1st Row of the Decimal text is SAVEDDECIMAL
And I validate the 1st Row of the Date text is SAVEDDATE
And I validate the 1st Row of the Chooseone text is SAVEDCHOOSEONE
And I validate the 1st Row of the Choosemany text is SAVEDCHOOSEMANY

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - Approve Changes
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Inline Edit Test Form" from the Form dropdown
And I click the Refresh button
And I click the Sort By Edited link
Then I validate the 1st Row of the Edited text is "no"
When I save the 1st Row of the Text text as SAVEDTEXT
And I save the 1st Row of the Numeric text as SAVEDNUMERIC
And I save the 1st Row of the Decimal text as SAVEDDECIMAL
And I save the 1st Row of the Date text as SAVEDDATE
And I save the 1st Row of the Chooseone text as SAVEDCHOOSEONE
And I save the 1st Row of the Choosemany text as SAVEDCHOOSEMANY
And I double click the 1st Row of the Date Time text
And I type "" into the 1st Row of the Text textfield
And I type "" into the 1st Row of the Numeric textfield
And I type "" into the 1st Row of the Decimal textfield
And I select today from the 1st Row of the Inline Date dropdown
And I select "No" from the 1st Row of the Chooseone dropdown
And I select Option 1 from the Choosemany scrollbox
And I click the 1st Row of the OK button
Then I validate the 1st Row of the Text text is not SAVEDTEXT
And I validate the 1st Row of the Numeric text is not SAVEDNUMERIC
And I validate the 1st Row of the Decimal text is not SAVEDDECIMAL
And I validate the 1st Row of the Date text is not SAVEDDATE
And I validate the 1st Row of the Chooseone text is not SAVEDCHOOSEONE
And I validate the 1st Row of the Choosemany text is not SAVEDCHOOSEMANY
//These tests below need to be changed once in line edit has been fully implemented, however some of them may not be needed
Scenario: TCXXXX: Forms Submissions Page - generate error messages for required fields

Scenario: TCXXXX: Edit form with date in the future
Given I am on the Edit Submissions page
When I select 1 day in the future from the Date dropdown
And I click the Refresh button
Then a validation error displays

//Future Release Test
Scenario: TCXXXX Click on Form Name link
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I click the 1st Row of the Form link
Then I validate I am on the Edit Form page
