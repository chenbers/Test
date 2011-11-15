Narrative:

In order to assign a device to a vehicle
As an installer
I want to view Device information on a laptop or iPhone application

Scenario: TC5953: Look-up Serial Number
Given I have already logged in
and I am on the Device Look-up Page
and I have selected the correct Silo
and I have entered my device serial number
When I click on the "Look-up" button
Then the page populates with Device Details already in expanded view
When I click on Vehicle Details expand button
Then Vehicle Details display
When I click on Last Location expand button
Then Last Location details display
and a link to google map displays
When I click on the map link
Then the map displays
When I click on Driver Details expand button
Then Driver Details display
When I click on Account Details expand button
Then Account Details display

Scenario: TC5954: Look-up by IMEI
Given I have already logged in
and I am on the Device Look-up Page
and I have selected the correct Silo
and I have entered my device IMEI number
When I click on the "Look-up" button
Then the page populates with Device Details already in expanded view
When I click on Vehicle Details expand button
Then Vehicle Details display
When I click on Last Location expand button
Then Last Location details display
and a link to google map displays
When I click on the map link
Then the map displays
When I click on Driver Details expand button
Then Driver Details display
When I click on Account Details expand button
Then Account Details display

Scenario: TC5955: Look-up by Device ID
Given I have already logged in
and I am on the Device Look-up Page
and I have selected the correct Silo
and I have entered my device ID
When I click on the "Look-up" button
Then the page populates with Device Details already in expanded view
When I click on Vehicle Details expand button
Then  Vehicle Details display
When I click on Last Location expand button
Then Last Location details display
and a link to google map displays
When I click on the map link
Then the map displays
When  I click on Driver Details expand button
Then Driver Details display
When I click on Account Details expand button
Then Account Details display

Scenario: TC5956: Empty Field Error
Given I have already logged in
and I am on the Device Look-up Page
and I have  selected a Silo
and left the device field blank
When I click on the "Look-up" button
Then the Error message "Device Lookup Error" at top of page
and "Required field" displays under the device field

Scenario: TC5957: Not Found Error
Given I have already logged in
and I am on the Device Look-up Page
and I have  selected a Silo
and I enter an incorrect ID
When I click on the "Look-up" button
Then the Error message "Device Lookup Error" at top of page
and "Not Found" displays under the device field