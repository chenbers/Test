Test Cases for TF388/TF411/TF412 

Meta:
@page login
@testFolder TF388
@testFolder TF411
@testFolder TF412

Narrative:

Scenario: TC1335: Notifications - Crash History - Add Crash Report Link
Given I am logged in
When I click the Notifications link
And I click the Crash History link
Then I validate I am on the Notifications Crash History Add Edit page

Scenario: TC1336: Notifications - Crash History - Bookmark Entry
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
Given I am logged in
Then I validate I am on the Notifications Crash History page

Scenario: TC1337: Notifications - Crash History - Bookmark Entry to Different Account
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
Given I am logged in an account that can be edited
Then I validate I am on the Notifications Crash History page

Scenario: TC1339: Notifications - Crash History - Bookmark Entry with Search
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I select "Top - Test Group WR" from the Team dropdown
And I type "TIWI00" into the Search textfield
And I click the Search button
Then I validate the 1st Row of the Vehicle link is present
When I bookmark the page
And I click the Logout link
And I click the bookmark I just added
Then I validate I am on the Login page
Given I am logged in
Then I validate I am on the Notifications Crash History page
And I validate the Team dropdown is ""
And I validate the Search textfield is ""
And I validate the 1st Row of the Vehicle link is not present

Scenario: TC1340: Notifications - Crash History - Details Link
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I select "Top - Test Group WR" from the Team dropdown
And I type "TIWI00" into the Search textfield
And I click the Search button
And I click the 1st Row of the Details link
Then I validate I am on the Notifications Crash Report page

Scenario: TC1341: Notifications - Crash History - Driver Link
Given I am logged in
When I click the Notifications link
And I click the Crash History link
And I select "Top - Test Group WR" from the Team dropdown
And I type "TIWI00" into the Search textfield
And I click the Search button
And I save the 1st Row of the Driver Name link as SAVEDNAME
And I click the 1st Row of the Driver Name link
Then I validate I am on the Driver Performance page
And I validate the Driver Name link is SAVEDNAME
































