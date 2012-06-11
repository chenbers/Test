Test Case for TF505

Meta:
@page login
@testFolder TF505

Narrative: 

Scenario: TC1228: Live Fleet - Bookmark Entry
Given I am logged in
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I validate I am on the Login page
Given I am logged in
Then I validate I am on the Live Fleet page

Scenario: TC1229: Live Fleet - Bookmark Entry to Different Account
Given I am logged in
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I validate I am on the Login page
Given I am logged in an account that can be edited
Then I validate I am on the Live Fleet page

Scenario: TC1232: Live Fleet - Driver Link
Given I am logged in
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I save the 1st Row of the Entry Driver By Position link as SavedEntry 
And I click the 1st Row of the Entry Driver By Position link 
Then I validate I am on the Driver Performance page
And I verify SavedEntry is on the page

Scenario: TC1236: Live Fleet - Team Link
Given I am logged in
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I save the 1st Row of the Entry Fleet Legend link as SavedEntry 
And I click the 1st Row of the Entry Fleet Legend link
Then I validate I am on the Team Driver Statistics page
And I validate the Driver Team Value text is SavedEntry

Scenario: TC1237: Live Fleet - UI
Given I am logged in
When I click the Live Fleet link
Then I validate I am on the Live Fleet page
And I validate the Sort By Number link is present
And I validate the Sort By Driver link is present
And I validate the Sort By Vehicle link is present
And I validate the Sort By Group link is present
And I validate the Header Fleet Legend text is present
And I validate the 1st Row of the Entry Fleet Legend link is present
And I validate the Find Address textfield is present
And I validate the Num Nearest Vehicles dropdown is present
And I validate the Locate button is present
And I validate the 1st Row of the Entry Group Icon By Position link is present
And I validate the Value Map Bubble Vehicle Name link is present

Scenario: TC1238: Live Fleet - Vehicle Image Link
Given I am logged in
When I click the Live Fleet link
And I click the 1st Row of the Entry Group Icon By Position link
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
Given I am logged in
When I click the Live Fleet link
And I validate I am on the Live Fleet page
And I save the 1st Row of the Entry Vehicle By Position link as SavedEntry 
And I click the 1st Row of the Entry Vehicle By Position link
Then I validate I am on the Vehicle Performance page
And I verify SavedEntry is on the page