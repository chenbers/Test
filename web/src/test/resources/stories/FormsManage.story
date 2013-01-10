In order to view a list of Forms
As Administrator
I need a UI on the Administrator page that lists the Forms

Scenario: Forms Manage Page - Generate needed forms
Given I am logged in
When I go to the forms manage page
Then I generate 100 forms for the manage page test

Scenario: TCXXXX: Forms Manage Page - Table View - UI
Given I am logged in
When I go to the forms manage page
Then I validate the New Form button is present
And I validate the Manage link is present
And I validate the Published link is present
And I validate the Submissions link is present
And I validate the Customers link is present
And I validate the Records Per Page dropdown is present
And I validate the Search textfield is present
And I validate the Sort By Name link is present
And I validate the Sort By Base Form ID link is present
And I validate the Sort By Version link is present
And I validate the Sort By Description link is present
And I validate the Sort By Status link is present
And I validate the Sort By Trigger link is present
And I validate the Entries text is present

Scenario: TCXXXX: Default Records dropdowns are set to 10
Given I am logged in
When I go to the forms manage page
Then I validate the Records Per Page dropdown is "10"

Scenario: TCXXXX: Forms Manage Page - Records per page test
Given I am logged in
When I go to the forms manage page
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

Scenario: TCXXXX: Forms Manage Page - New Form button
Given I am logged in
When I go to the forms manage page
And I click the New Form button
Then I validate I am on the Forms Add page

Scenario: TCXXXX: Forms Manage Page - Forms Edit link
Given I am logged in
When I go to the forms manage page
And I save the 1st Row of the Entry Name text as SAVEDFORM
And I click the 1st Row of the Gear button
And I click the 1st Row of the Edit link
Then I validate I am on the Edit Form page
And I validate the Name field is SAVEDFORM

Scenario: TCXXXX: Search - Manage tab
Given I am logged in
When I go to the forms manage page
And I type "FormPreTrip" into the Search textfield
Then I validate the 1st Row of the Entry Name text contains "FormPreTrip"
And I validate the 2nd Row of the Entry Name text contains "FormPreTrip"
And I validate the 3rd Row of the Entry Name text contains "FormPreTrip"
And I type "FormPostTrip" into the Search textfield
Then I validate the 1st Row of the Entry Name text contains "FormPostTrip"
And I validate the 2nd Row of the Entry Name text contains "FormPostTrip"
And I validate the 3rd Row of the Entry Name text contains "FormPostTrip"
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
And I type "Inactive" into the Search textfield
And I validate the 1st Row of the Entry Status text contains "Inactive"
And I validate the 2nd Row of the Entry Status text contains "Inactive"
And I validate the 3rd Row of the Entry Status text contains "Inactive"
And I validate the 4th Row of the Entry Status text contains "Inactive"
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
Then I validate the No Records Error text is "No matching records found"
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"
And I validate the Previous link is not present
And I validate the Next link is not present

Scenario: TC6291: Copy Form with all fields and Validate
Given I am logged in
When I go to the forms manage page
And I type "XYZ_AllFieldsTest" into the Search field
Then I validate the 1st Row of Entry Name text is "XYZ_AllFieldsTest"
When I click the 1st Row of the Gear button
When I click the 1st Row of the Copy link
Then I validate I am on the Edit Form page
And I validate the Name field is "XYZ_AllFieldsTest"
And I validate the Group Name text is "826"
And I validate the Description field is "Fill All Fields"
And I validate the Trigger dropdown is "Route"
And I validate the Trigger Product dropdown is "Oxygen"
And I validate the Trigger Action dropdown is "pickup"
And I validate the Status dropdown is "Inactive"
And I click the First Text link
And I validate the Data Name field is "TextName"
And I validate the Caption Text field is "Text English Caption"
And I validate the Hint field is "Text English Hint"
And I validate the Value field is "Default Value"
And I validate the Read Only checkbox is checked
And I validate the Required checkbox is checked
And I validate the Length Enable checkbox is checked
And I validate the Minimum Text Length field is "1"
And I validate the Minimum Text Inclusive checkbox is checked
And I validate the Maximum Text Length field is "35"
And I validate the Maximum Text Inclusive checkbox is checked
And I validate the Invalid Text field is "One to Thirty-five alpha characters"
And I click the First Numeric link
And I validate the Data Name field is "NumericName"
And I validate the Caption Text field is "Numeric English Caption"
And I validate the Hint field is "Numeric English Hint"
And I validate the Value field is "Numeric Default"
And I validate the Read Only checkbox is checked
And I validate the Required checkbox is checked
And I validate the Range Enable Numeric checkbox is checked
And I validate the Minimum Numeric Range field is "11"
And I validate the Minimum Numeric Inclusive checkbox is checked
And I validate the Maximum Numeric Range field is "37"
And I validate the Maximum Numeric Inclusive checkbox is checked
And I validate the Invalid Text field is "Numeric Invalid English"
And I validate the Kind dropdown is "Decimal"
And I click First Date link
And I validate the Data Name field is "DateName"
And I validate the Caption Text field is "Date English Caption"
And I validate the Hint field is "Date English Hint"
And I validate the Value field is "Date Default"
And I validate the Read Only checkbox is checked
And I validate the Required checkbox is checked
And I validate the Range Enable Date checkbox is checked
And I validate the Minimum Date Range field is "2014-07-31" 
And I validate the Maximum Date Range field is "2014-09-01" 
And I validate the Minimum Date Inclusive checkbox is checked
And I validate the Maximum Date Inclusive checkbox is checked
And I validate the Invalid Text field is "Date Invalid"
And I click the First Select One link
And I validate the Data Name field is "ChooseOne"
And I validate the Caption Text field is "One Caption"
And I validate the Hint field is "One Hint"
And I validate the Value field is "One Default"
And I validate the Read Only checkbox is checked
And I validate the Required checkbox is checked
And I validate the 1st Row of the Option English field is "First Opt"
And I validate the 1st Row of the Option Underlying Value field is "First"
And I validate the 2nd Row of the Option English field is "Second Opt"
And I validate the 2nd Row of the Option Underlying Value field is "Second"
And I validate the 3rd Row of the Option English field is "Third Opt"
And I validate the 3rd Row of the Option Underlying Value field is "Third"
And I validate the 4th Row of the Option English field is "Fourth Opt"
And I validate the 4th Row of the Option Underlying Value field is "Fourth" 
And I click the First Select Multiple link
And I validate the Data Name field is "Multiple" 
And I validate the Caption Text field is "Multiple English Caption"
And I validate the Hint field is "Multiples"
And I validate the Value field is "Defaults"
And I validate the Read Only checkbox is checked
And I validate the Required checkbox is checked
And I validate the 1st Row of the Option English field is "First Opts"
And I validate the 1st Row of the Option Underlying Value field is "1st"
And I validate the 2nd Row of the Option English field is "Second Opts"
And I validate the 2nd Row of the Option Underlying Value field is "2nd"
And I validate the 3rd Row of the Option English field is "Third Opts"
And I validate the 3rd Row of the Option Underlying Value field is "3rd"
And I validate the 4th Row of the Option English field is "Fourth Opts"
And I validate the 4th Row of the Option Underlying Value field is "4th" 
When I type "XYZ_AllFieldsTest_COPY" into the Name field
And I type "Copy All Fields" into the Description field
And I click the Save Bottom button
When I validate the Success Alert text is present
When I validate I am on the Forms Manage page
Then I type "XYZ_AllFieldsTest_COPY" into the Search field
And I validate the 1st Row of the Entry Base Form Id text is not "FORM_1354824643694"
When I edit the last form
When I validate I am on the Edit Form page
And I validate the Name field is "XYZ_AllFieldsTest_COPY"
And I validate the Description field is "Copy All Fields"
And I validate the Group Name text is "826"
And I validate the Trigger dropdown is "Route"
And I validate the Trigger Product dropdown is "Oxygen"
And I validate the Trigger Action dropdown is "pickup"
And I validate the Status dropdown is "Inactive"
And I click the First Text link
And I validate the Data Name field is "TextName"
And I validate the Caption Text field is "Text English Caption"
And I validate the Hint field is "Text English Hint"
And I validate the Value field is "Default Value"
And I validate the Read Only checkbox is checked
And I validate the Required checkbox is checked
And I validate the Length Enable checkbox is checked
And I validate the Minimum Text Length field is "1"
And I validate the Minimum Text Inclusive checkbox is checked
And I validate the Maximum Text Length field is "35"
And I validate the Maximum Text Inclusive checkbox is checked
And I validate the Invalid Text field is "One to Thirty-five alpha characters"
And I click the First Numeric link
And I validate the Data Name field is "NumericName"
And I validate the Caption Text field is "Numeric English Caption"
And I validate the Hint field is "Numeric English Hint"
And I validate the Value field is "Numeric Default"
And I validate the Read Only checkbox is checked
And I validate the Required checkbox is checked
And I validate the Range Enable Numeric checkbox is checked
And I validate the Minimum Numeric Range field is "11"
And I validate the Minimum Numeric Inclusive checkbox is checked
And I validate the Maximum Numeric Range field is "37"
And I validate the Maximum Numeric Inclusive checkbox is checked
And I validate the Invalid Text field is "Numeric Invalid English"
And I validate the Kind dropdown is "Decimal"
And I click First Date link
And I validate the Data Name field is "DateName"
And I validate the Caption Text field is "Date English Caption"
And I validate the Hint field is "Date English Hint"
And I validate the Value field is "Date Default"
And I validate the Read Only checkbox is checked
And I validate the Required checkbox is checked
And I validate the Range Enable Date checkbox is checked
And I validate the Minimum Date Range field is "2014-07-31" 
And I validate the Maximum Date Range field is "2014-09-01" 
And I validate the Minimum Date Inclusive checkbox is checked
And I validate the Maximum Date Inclusive checkbox is checked
And I validate the Invalid Text field is "Date Invalid"
And I click the First Select One link
And I validate the Data Name field is "ChooseOne"
And I validate the Caption Text field is "One Caption"
And I validate the Hint field is "One Hint"
And I validate the Value field is "One Default"
And I validate the Read Only checkbox is checked
And I validate the Required checkbox is checked
And I validate the 1st Row of the Option English field is "First Opt"
And I validate the 1st Row of the Option Underlying Value field is "First"
And I validate the 2nd Row of the Option English field is "Second Opt"
And I validate the 2nd Row of the Option Underlying Value field is "Second"
And I validate the 3rd Row of the Option English field is "Third Opt"
And I validate the 3rd Row of the Option Underlying Value field is "Third"
And I validate the 4th Row of the Option English field is "Fourth Opt"
And I validate the 4th Row of the Option Underlying Value field is "Fourth" 
And I click the First Select Multiple link
And I validate the Data Name field is "Multiple" 
And I validate the Caption Text field is "Multiple English Caption"
And I validate the Hint field is "Multiples"
And I validate the Value field is "Defaults"
And I validate the Read Only checkbox is checked
And I validate the Required checkbox is checked
And I validate the 1st Row of the Option English field is "First Opts"
And I validate the 1st Row of the Option Underlying Value field is "1st"
And I validate the 2nd Row of the Option English field is "Second Opts"
And I validate the 2nd Row of the Option Underlying Value field is "2nd"
And I validate the 3rd Row of the Option English field is "Third Opts"
And I validate the 3rd Row of the Option Underlying Value field is "3rd"
And I validate the 4th Row of the Option English field is "Fourth Opts"
And I validate the 4th Row of the Option Underlying Value field is "4th" 
