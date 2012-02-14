Narrative:

In order for the tiwipro portal to display custom maps on customer basis 
As a SuperUser or Customer Administrator
I need to be able to set the custom map for the account

Scenario: TC6041 SuperUser Access
Given I am logged in as SuperUser
When I am on the Account Options page
Then I can select an Account from the dropdown

Scenario TC6042 Customer Administrator Access
Given I am logged in as Customer Administrator
When I am on the Account page
Then I am in my own account 
And no dropdown displays for other accounts

Scenario TC6043 Add and Save Map
Given I am logged in as SuperUser
And I am on the Account page
And I have selected an Account name from the dropdown
And I have selected a <maps> from the dropdown
When I click on Add button
Then I enter <display name>
And I enter the <URL>
And I check the Png Format tiles box
And I select the Zoom <min> slider
And I select the Zoom <max> slider
And I select the <opacity> slider
And I select the <layer> Over from the dropdown
And I click on Save button

Examples:
|maps|display_name|URL|min|max|opacity|layer|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario TC6044 Edit and Save Map
Given I am logged in as SuperUser
And I am on the Account page
And I have selected an Account name from the dropdown
And I have selected a <maps> from the dropdown
When I click on Edit button
And I clear the existing fields
Then I enter <display name>
And I enter the <URL>
And I check the Png Format tiles box
And I select the Zoom <min> slider
And I select the Zoom <max> slider
And I select the <opacity> slider
And I select the <layer> Over from the dropdown
And I click on Save button

Examples:
|maps|display_name|URL|min|max|opacity|layer|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|2|30|1.0|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|2|30|1.0|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|2|30|1.0|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|2|30|1.0|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|2|30|1.0|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|2|30|1.0|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|2|30|1.0|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|2|30|1.0|7|

Scenario TC6045 Remove Map
Given I am logged in as a SuperUser
And I am on the Account page
And I select an Account from the dropdown
When I click on Remove
Then something happens

Scenario TC6046 Cancel Add
Given I am logged in as SuperUser
And I am on the Account page
And I have selected an Account name from the dropdown
And I have selected a <maps> from the dropdown
When I click on Add button
Then I enter <display name>
And I enter the <URL>
And I check the Png Format tiles box
And I select the Zoom <min> slider
And I select the Zoom <max> slider
And I select the <opacity> slider
And I select the <layer> Over from the dropdown
When I click on Cancel button
Then the Add is canceled

Examples:
|maps|display_name|URL|min|max|opacity|layer|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|

Scenario TC6045 Cancel Remove
Given I am logged in as a SuperUser
And I am on the Account page
And I select an Account from the dropdown
When I click on Remove
Then something happens
When I click on Cancel
Then the Remove is canceled
