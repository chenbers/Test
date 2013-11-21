Narrative: In order to view submitted forms within a time period
As an Administrator
I need a place to view and edit existing submitted forms.

Scenario: TCXXXX Forms Submissions Page - Forms No Data Available In Table Message
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Form TCXXXXAdd1 (1)" from the Form dropdown
And I click the Refresh button
Then I validate the 1st Row of the Form Entry text is not present
And I validate the No Records Found Error text is present
And I validate the No Records Found Error text is "No data available in table"
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

!-- Scenario: Forms Submissions Page - Generate Submissions
!-- Given I generate submissions

Scenario: TCXXXX Forms Submissions Page - UI Test
Given I am logged in
When I click the Forms link
And I click the Submissions link
Then I validate the Form dropdown is present
And I validate the Form dropdown is "All Forms"
And I validate the Date dropdown is present
And I validate the Refresh button is present
And I validate the Records Per Page dropdown is present
And I validate the Records Per Page dropdown is "10"
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

Scenario: TCXXXX Forms Submissions Page - Forms No Records Found Table Message
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select 100 days in the past from the Date dropdown
And I click the Refresh button
Then I validate the 1st Row of the Form Entry text is not present
And I validate the No Records Found Error text is present
And I validate the No Records Found Error text is "No matching records found"
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"

Scenario: TCXXXX Forms Submissions Page - Make sure dates are showing correctly in the grid
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "2013-11-19" from the Date dropdown
And I click the Refresh button
Then I validate the 1st Row of the Date Time Entry text contains "2013-11-19"
And I validate the 2nd Row of the Date Time Entry text contains "2013-11-19"
And I validate the 3rd Row of the Date Time Entry text contains "2013-11-19"
And I validate the 4th Row of the Date Time Entry text contains "2013-11-19"
And I validate the 5th Row of the Date Time Entry text contains "2013-11-19"
And I validate the 6th Row of the Date Time Entry text contains "2013-11-19"
And I validate the 7th Row of the Date Time Entry text contains "2013-11-19"
And I validate the 8th Row of the Date Time Entry text contains "2013-11-19"
And I validate the 9th Row of the Date Time Entry text contains "2013-11-19"
And I validate the 10th Row of the Date Time Entry text contains "2013-11-19"

Scenario: TCXXXX Forms Submissions Page - Click on Group Name link
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I click the Refresh button
And I save the 1st Row of the Group Entry link as GROUP
And I click the 1st Row of the Group Entry link
Then I validate I am on the Team Driver Statistics page
And I validate the Driver Team Value text is GROUP

Scenario: TCXXXX Forms Submissions Page - Click on Driver Name link
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I click the Refresh button
And I save the 1st Row of the Driver Entry link as DRIVER
And I click the 1st Row of the Driver Entry link
Then I validate I am on the Driver Performance page
And I validate the Driver Name link is DRIVER

Scenario: TCXXXX Forms Submissions Page - Click on Vehicle name link
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I click the Refresh button
And I save the 1st Row of the Vehicle Entry link as VEHICLE
And I click the 1st Row of the Vehicle Entry link
Then I validate I am on the Vehicle Performance page
And I validate the Vehicle Name link is VEHICLE

Scenario: TCXXXX Forms Submissions Page - Approve a Form checkbox
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I check the 1st Row of the Approved Entry checkbox
Then I validate the 1st Row of the Approved Entry checkbox is checked
And I uncheck the 1st Row of the Approved Entry checkbox
And I validate the 1st Row of the Approved Entry checkbox is not checked

Scenario: TCXXXX: Forms Submissions Page - Records per page test
Given I am logged in
When I click the Forms link
And I click the Submissions link
Then I validate the 11th Row of the Date Time Entry text is not present
And I validate the Entries text contains "Showing 1 to 10"
And I select "25" from the Records Per Page dropdown
And I validate the 26th Row of the Date Time Entry text is not present
And I validate the Entries text contains "Showing 1 to 25"
And I select "50" from the Records Per Page dropdown
And I validate the 51st Row of the Date Time Entry text is not present
And I validate the Entries text contains "Showing 1 to 50"
And I select "100" from the Records Per Page dropdown
And I validate the 101st Row of the Date Time Entry text is not present
And I validate the Entries text contains "Showing 1 to 100"

Scenario: TCXXXX: Forms Submissions Page - Bookmark Entry
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I validate I am on the Forms Submissions page
And I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
Then I validate I am on the Login page
When I log back in
Then I validate I am on the Forms Submissions page

Scenario: TCXXXX: Forms Submissions Page - Bookmark Entry to Different Account
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I validate I am on the Forms Submissions page
And I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
Then I validate I am on the Login page
When I log back in under the editable account
Then I validate I am on the Forms Submissions page

Scenario: TCXXXX: Forms Submissions Page - Edit Approved check (No change to Edited column)
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "no" from the Approved dropdown
And I save the 1st Row of the Date Time Entry text as DATETIME
And I save the 1st Row of the Group Entry link as GROUP
And I save the 1st Row of the Driver Entry link as DRIVER
And I save the 1st Row of the Vehicle Entry link as VEHICLE
And I save the 1st Row of the Form Entry text as FORM
And I check the 1st Row of the Approved Entry checkbox
Then I validate the 1st Row of the Approved Entry checkbox is checked
And I click the Refresh button
And I select "yes" from the Approved dropdown
And I validate the 1st Row of the Date Time Entry text is DATETIME
And I validate the 1st Row of the Group Entry link is GROUP
And I validate the 1st Row of the Driver Entry link is DRIVER
And I validate the 1st Row of the Vehicle Entry link is VEHICLE
And I validate the 1st Row of the Form Entry text is FORM
And I validate the 1st Row of the Approved Entry checkbox is checked
And I validate the 1st Row of the Edited Entry text is "no"
And I uncheck the 1st Row of the Approved Entry checkbox
And I validate the 1st Row of the Approved Entry checkbox is not checked

Scenario: TCXXXX: Forms Submissions Page - Edit Approved uncheck (No change to Edited column)
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I click the Refresh button
And I check the 1st Row of the Approved Entry checkbox
And I select "yes" from the Approved dropdown
Then I validate the 1st Row of the Edited Entry text is "no"
When I click the Refresh button
Then I validate the 1st Row of the Approved Entry checkbox is checked
And I validate the 1st Row of the Edited Entry text is "no"
When I uncheck the 1st Row of the Approved Entry checkbox
Then I validate the 1st Row of the Approved Entry checkbox is not checked
And I validate the 1st Row of the Edited Entry text is "no"
When I click the Refresh button
Then I validate the 1st Row of the Approved Entry checkbox is not checked
When I select "no" from the Approved dropdown
And I click the Refresh button
Then I validate the 1st Row of the Approved Entry checkbox is not checked
And I validate the 1st Row of the Edited Entry text is "no"

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - Forms link disappears (Requires there be form data)
Given I am logged in
When I click the Forms link
And I click the Submissions link
Then I validate the 1st Row of the Form Entry text is present
And I select "FormGeneric10 (1)" from the Form dropdown
And I click the Refresh button
And I validate the 1st Row of the Form Entry text is not present

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - All Forms (make sure you cannot do)
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I double click the 1st Row of the Form Entry text
Then I validate the 1st Row of the Text Entry textfield is not present
And I validate the 1st Row of the Numeric Entry textfield is not present
And I validate the 1st Row of the Decimal Entry textfield is not present
And I validate the 1st Row of the Date Entry dropdown is not present

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - Cancel Changes (Requires Form TCXXX Pre-Trip Required (1) is published and submitted)
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Form TCXXXX Pre-Trip Required (1)" from the Form dropdown
And I click the Refresh button
And I click the Sort By Edited link
Then I validate the 1st Row of the Edited Entry text is "no"
When I save the 1st Row of the Text Entry text as SAVEDTEXT
And I save the 1st Row of the Numeric Entry text as SAVEDNUMERIC
And I save the 1st Row of the Decimal Entry text as SAVEDDECIMAL
And I save the 1st Row of the Date Entry text as SAVEDDATE
And I save the 1st Row of the Chooseone Entry text as SAVEDCHOOSEONE
And I save the 1st Row of the Choosemany Entry text as SAVEDCHOOSEMANY
And I double click the 1st Row of the Edited Entry text
And I type "" into the 1st Row of the Text Entry textfield
And I type "" into the 1st Row of the Numeric Entry textfield
And I type "" into the 1st Row of the Decimal Entry textfield
And I select 2 days in the past from the 1st Row of the Date Entry dropdown
And I select row 2 from the Chooseone Entry dropdown
And I click the 1st Row of the Choosemany Entry checkbox
And I click the 1st Row of the Cancel button
Then I validate the 1st Row of the Text Entry text is SAVEDTEXT
And I validate the 1st Row of the Numeric Entry text is SAVEDNUMERIC
And I validate the 1st Row of the Decimal Entry text is SAVEDDECIMAL
And I validate the 1st Row of the Date Entry text is SAVEDDATE
And I validate the 1st Row of the Chooseone Entry text is SAVEDCHOOSEONE
And I validate the 1st Row of the Choosemany Entry text is SAVEDCHOOSEMANY
And I validate the 1st Row of the Edited Entry text is "no"

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - Save Changes
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Form TCXXXX Pre-Trip Required (1)" from the Form dropdown
And I click the Refresh button
And I click the Sort By Edited link
Then I validate the 1st Row of the Edited Entry text is "no"
And I save the 1st Row of the Vehicle Entry link as SAVEDVEHICLE
And I type SAVEDVEHICLE into the Vehicle textfield
And I save the 1st Row of the Date Entry text as SAVEDDATE
And I save the 1st Row of the Chooseone Entry text as SAVEDCHOOSEONE
And I save the 1st Row of the Choosemany Entry text as SAVEDCHOOSEMANY
And I double click the 1st Row of the Edited Entry text
And I type "New Text" into the 1st Row of the Text Entry textfield
And I type "2" into the 1st Row of the Numeric Entry textfield
And I type "99.99" into the 1st Row of the Decimal Entry textfield
And I select 457 days in the past from the 1st Row of the Date Entry dropdown
And I select row 4 from the Chooseone Entry dropdown
And I click the 2nd Row of the Choosemany Entry checkbox
And I click the 1st Row of the Save button
And I validate the Vehicle textfield is SAVEDVEHICLE
And I click the Sort By Edited link
And I validate the 1st Row of the Text Entry text is "New Text"
And I validate the 1st Row of the Numeric Entry text is "2"
And I validate the 1st Row of the Decimal Entry text is "99.99"
And I validate the 1st Row of the Date Entry text is not SAVEDDATE
And I validate the 1st Row of the Chooseone Entry text is not SAVEDCHOOSEONE
And I validate the 1st Row of the Choosemany Entry text is not SAVEDCHOOSEMANY
And I validate the 1st Row of the Edited Entry text is "yes"

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - Generate error messages - blank fields (required)
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Form TCXXXX Pre-Trip Required (1)" from the Form dropdown
And I click the Refresh button
And I double click the 1st Row of the Edited Entry text
And I type "" into the 1st Row of the Text Entry textfield
And I type "" into the 1st Row of the Numeric Entry textfield
And I type "" into the 1st Row of the Decimal Entry textfield
And I select 50 days in the future from the 1st Row of the Date Entry dropdown
And I uncheck the 1st Row of the Choosemany Entry checkbox
And I uncheck the 2nd Row of the Choosemany Entry checkbox
And I uncheck the 3rd Row of the Choosemany Entry checkbox
And I uncheck the 4th Row of the Choosemany Entry checkbox
And I click the 1st Row of the Save button
Then I validate the Invalid Text Error text is present
And I validate the Invalid Numeric Error text is present
And I validate the Invalid Decimal Error text is present
And I validate the Invalid Date Error text is present

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - Generate error messages - text out of range
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Form TCXXXX Pre-Trip Required (1)" from the Form dropdown
And I click the Refresh button
And I double click the 1st Row of the Edited Entry text
And I type "x" into the 1st Row of the Text Entry textfield
And I click the 1st Row of the Save button
Then I validate the Invalid Text Error text is present
And I type "TCXXXXTCXXXXTCXXXX" into the 1st Row of the Text Entry textfield
And I click the 1st Row of the Save button
And I validate the Invalid Text Error text is present

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - Generate error messages - numeric out of range
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Form TCXXXX Pre-Trip Required (1)" from the Form dropdown
And I click the Refresh button
And I double click the 1st Row of the Edited Entry text
And I type "0" into the 1st Row of the Numeric Entry textfield
And I click the 1st Row of the Save button
Then I validate the Invalid Numeric Error text is present
And I type "101" into the 1st Row of the Numeric Entry textfield
And I click the 1st Row of the Save button
And I validate the Invalid Numeric Error text is present

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - Generate error messages - decimal out of range
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Form TCXXXX Pre-Trip Required (1)" from the Form dropdown
And I click the Refresh button
And I double click the 1st Row of the Edited Entry text
And I type "-0.9" into the 1st Row of the Decimal Entry textfield
And I click the 1st Row of the Save button
Then I validate the Invalid Decimal Error text is present
And I type "100.1" into the 1st Row of the Decimal Entry textfield
And I click the 1st Row of the Save button
And I validate the Invalid Decimal Error text is present

Scenario: TCXXXX: Forms Submissions Page - Inline Edit - Generate error messages - date out of range
Given I am logged in
When I click the Forms link
And I click the Submissions link
And I select "Form TCXXXX Pre-Trip Required (1)" from the Form dropdown
And I click the Refresh button
And I double click the 1st Row of the Edited Entry text
And I select 32 days in the future from the 1st Row of the Date Entry dropdown
And I click the 1st Row of the Save button
Then I validate the Invalid Date Error text is present
And I select 180 days in the past from the 1st Row of the Date Entry dropdown
And I click the 1st Row of the Save button
And I validate the Invalid Date Error text is present