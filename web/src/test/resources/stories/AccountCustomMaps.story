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
And I am on the Manage Custom Maps page
And I have selected an Account name from the dropdown
When I click on Add button
Then I enter <display name>
And I enter the <URL>
And I check the Png Format tiles box
And I select the Zoom <min> slider
And I select the Zoom <max> slider
And I select the <opacity> slider
And I click on Save button


Examples:
|maps|display_name|URL|min|max|opacity|layer|map_pages|
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
And I am on the Manage Custom Maps page 
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
And I am on the Manage Custom Maps page
And I select an Account from the dropdown
And I select a map from the dropdown
When I click on Remove
Then a popup to confirm deletion displays
And I click on the Yes button
And the map is deleted

Scenario TC6046 Cancel Add
Given I am logged in as SuperUser
And I am on the Manage Custom Maps page
And I have selected an Account name from the dropdown
When I click on Add button
Then I enter <display name>
And I enter the <URL>
And I check the Png Format tiles box
And I select the Zoom <min> slider
And I select the Zoom <max> slider
And I select the <opacity> slider
When I click on Cancel button
Then the Add is canceled

Examples:
|maps|display_name|URL|min|max|opacity|layer|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|

Scenario TC6045 Cancel Remove
Given I am logged in as a SuperUser
And I am on the Manage Custom Maps page
And I select an Account from the dropdown
When I click on Remove
Then a popup to confirm deletion displays
And I click on the NO button
Then the Remove is canceled

Scenario: TC6049  Verify Maps Saved in Portal
Given a mapName has been saved
And I am logged into the portal
When I click to view the maps on <pageName>
Then the maps dropdown displays mapName
And maps display with checkbox


Examples:
|pageName|
|live_fleet_map|
|main_live_fleet|
|driver_trip|
|vehicle_trip|
|last_trip|
|team_trip|
|zone_map|

Scenario: TC6050  Verify Deleted Maps are Removed from Portal
Given a mapName has been deleted
And I am logged into the portal
When I click to view the maps on <pageName>
Then the mapName is removed from the maps dropdown display

Examples:
|pageName|
|live_fleet_map|
|main_live_fleet|
|driver_trip|
|vehicle_trip|
|last_trip|
|team_trip|
|zone_map|

Scenario: TC6052 Verify Maps can be selected from dropdown in Portal
Given a mapName has been saved
And I am logged into the portal
When I click to view the maps on <pageName>
And the maps dropdown displays mapName
And maps display with checkbox
And I check a map checkbox
Then the map overlay displays


Examples:
|pageName|
|live_fleet_map|
|main_live_fleet|
|driver_trip|
|vehicle_trip|
|last_trip|
|team_trip|
|zone_map|


Scenario: TC6059 Test custom layers display correctly in Portal- Home Page
Given I log in to the home page
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6061 Test custom layers display correctly in Portal- Live Fleet page
Given I log in to the live fleet page
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6062 Test custom layers display correctly in Portal - Admin - Zones
Given I log in to the home page
And I click on the admin tab
And I click on the zones tab
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6063 Test custom layers display correctly in Portal- Admin - Zones - Add Zone
Given I log in to the home page
And I click on the admin tab
And I click on the zones tab
And I click on the add zone button
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6064 Test custom layers display correctly in Portal - Admin - Zones - Add Alert
Given I log in to the home page
And I click on the admin tab
And I click on the zones tab
And I click on the add alert button
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6065 Test custom layers display correctly in Portal - Admin - Organization
Given I log in to the home page
And I click on the admin tab
And I click on the organization tab
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6066 Test custom layers display correctly in Portal - Admin - Speed by Street
Given I log in to the home page
And I click on the admin tab
And I click on the speed by street tab
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6067 Test custom layers display correctly in Portal- Home Tab - Pick a team - Live Team Tab
Given I log in to the home page
And I hover over the home tab
And I click on a team
When map is selected by default
And I click on Live Team tab
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6068 Test custom layers display correctly in Portal - Reports Tab - Drivers Tab - Pick a driver with a list trip
Given I log in to the home page
And I click on the reports tab
And I click on the drivers tab
And I select a driver with a last trip
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6069 Test custom layers display correctly in Portal - Reports Tab - Drivers Tab - Pick a driver with a list trip - View all trips
Given I log in to the home page
And I click on the reports tab
And I click on the drivers tab
And I select a driver with a last trip
And I click on view all trips link
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6070 Test custom layers display correctly in Portal - Reports Tab - Vehicles Tab - Pick a vehicle with a list trip
Given I log in to the home page
And I click on the reports tab
And I click on the vehicles tab
And I select a vehicle with a last trip
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6071 Test custom layers display correctly in Portal - Reports Tab - Vehicles Tab - Pick a vehicle with a list trip - View all trips
Given I log in to the home page
And I click on the reports tab
And I click on the vehicles tab
And I select a vehicle with a last trip
And I click on view all trips link
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6072 Test custom layers display correctly in Portal - Notifications Tab - Red Flags Tab
Given I log in to the home page
And I click on the notifications tab
And I click on the red flags tab
And I select a team
And I select a time frame
And I click refresh
And I click the map icon
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6073 Test custom layers display correctly in Portal - Notifications Tab - Safety Tab
Given I log in to the home page
And I click on the notifications tab
And I click on the safety tab
And I select a team
And I select a time frame
And I click refresh
And I click the map icon
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6074 Test custom layers display correctly in Portal - Notifications Tab - Diagnostics Tab
Given I log in to the home page
And I click on the notifications tab
And I click on the diagnostics tab
And I select a team
And I select a time frame
And I click refresh
And I click the map icon
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6075 Test custom layers display correctly in Portal - Notifications Tab - Zones Tab
Given I log in to the home page
And I click on the notifications tab
And I click on the zones tab
And I select a team
And I select a time frame
And I click refresh
And I click the map icon
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6076 Test custom layers display correctly in Portal - Notifications Tab - HOS Exceptions Tab
Given I log in to the home page
And I click on the notifications tab
And I click on the hos exceptions tab
And I select a team
And I select a time frame
And I click refresh
And I click the map icon
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6077 Test custom layers display correctly in Portal - Notifications Tab - Emergency Tab
Given I log in to the home page
And I click on the notifications tab
And I click on the emergency tab
And I select a team
And I select a time frame
And I click refresh
And I click the map icon
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6078 Test custom layers display correctly in Portal - Notifications Tab - Crash History
Given I log in to the home page
And I click on the notifications tab
And I click on the crash history tab
And I select a team
And I select a time frame
And I click refresh
And I click the map icon
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|

Scenario: TC6079 Test custom layers display correctly in Portal - Notifications Tab - Driver Logins
Given I log in to the home page
And I click on the notifications tab
And I click on the driver logins tab
And I select a team
And I select a time frame
And I click refresh
And I click the map icon
When map is selected by default
And I have selected a <maptype> from the dropdown
Then map is updated correctly

Example
|maps|display_name|URL|min|max|opacity|layer|map_pages|
|map|barrick1|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|0|
|sat|barrick2|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|1|
|hyb|barrick3|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|2|
|map|barrick4|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|3|
|sat|barrick5|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|4|
|hyb|barrick6|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|5|
|map|barrick7|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|6|
|sat|barrick8|http://tile.openstreetmap.org/{Z}/{X}/{Y}.png|1|20|.5|7|
