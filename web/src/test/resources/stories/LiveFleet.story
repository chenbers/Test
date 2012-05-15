Test Case for TF505

Meta:
@page login
@testFolder TF505

Narrative: 

Scenario: TC1228: Live Fleet - Bookmark Entry
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
And I validate I am on the Login page
And I type a valid user name
And I type a valid password
And I click log in
Then I validate I am on the Live Fleet page

Scenario: TC1229: Live Fleet - Bookmark Entry to Different Account
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I bookmark the page
And I click the Log Out link
And I click the bookmark I just added
And I validate I am on the Login page
And I am logged in as a different "Admin" user
And I click log in
Then I validate I am on the Live Fleet page

Scenario: TC1232: Live Fleet - Driver Link
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I save the first entry in the Driver column of the Dispatch table as SavedEntry 
And I click the first entry in the Driver column of the Dispatch table
Then I validate I am on the Driver Performance page
And I validate the Driver Performance Name text is SavedEntry 

Scenario: TC1236: Live Fleet - Team Link
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I save the first entry in the Entry Fleet Legend table as SavedEntry 
And I click the first entry in the Entry Fleet Legend table
Then I validate I am on the Team Performance page
And I validate the Team Name text is SavedEntry 

Scenario: TC1237: Live Fleet - UI
Given I am logged in as a "Admin" user
When I click the Live Fleet link
Then I validate I am on the Live Fleet page
And I validate the Sort By Number link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Group link is present
And I validate the Header Fleet Legend text is present
And I validate the Entry Fleet Legend link is present
And I validate the Find Address textfield is present
And I validate the Num Nearest Vehicles dropdown is present
And I validate the Locate button is present
And I validate the Entry Group Icon By Position link is present
And I validate the Value Map Bubble Vehicle Name link is present

Scenario: TC1238: Live Fleet - Vehicle Image Link
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I click the first Entry Group Icon By Position link
Then I validate I am on the Live Fleet page
And I validate the Value Map Bubble Vehicle Name link is present
And I validate the Label Map Bubble Device text is present
And I validate the Label Map Bubble Driver text is present
And I validate the Label Map Bubble Location text is present
And I validate the Label Map Bubble Phone1 text is present
And I validate the Label Map Bubble Phone2 text is present
And I validate the Label Map Bubble Updated text is present
And I validate the Value Map Bubble Driver link is present
And I validate the Value Map Bubble Device text is present
And I validate the Value Map Bubble Updated text is present
And I validate the Value Map Bubble Location text is present
And I validate the Value Map Bubble Dist To Address text is present

Scenario: TC1239: Live Fleet - Vehicle Link
Given I am logged in as a "Admin" user
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I save the first entry in the Vehicle column of the Dispatch table as SavedEntry 
And I click the first entry in the Vehicle column of the Dispatch table
Then I validate I am on the Vehicle Performance page
And I validate the Vehicle Performance Name text is SavedEntry 
