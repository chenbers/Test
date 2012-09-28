Narrative:

In order for my daily driver logs to reflect the correct home and terminal addresses 
As HOS Manager
I need to be able to set the main office address on more than just two levels of the organization structure

Scenario: TC6022 Set Address
Given I am logged in as HOS manager 
And I am on the Organization page
And I have selected a <groupname> 
And I have clicked on Edit button
And I have cleared the existing fields
And I am Adding <address1><address2><city><state><zipcode> for my group
When I select the <DOTtype> from the dropdown
And click on Save
Then verify the <groupname><address1><address2><city><state><zipcode> were saved

Example:
|groupname|address1|address2|city|state|zipcode|DOTtype|
|SLB|4225 West Lake Park Blvd|blank|West Valley|Utah|84120|main|
|Kenai|210 Fidalgo Avenue Park Blvd|blank|Kenai|Alaska|99611|terminal|
|PPS|4225 West Lake Park Blvd|blank|West Valley|Utah|84120|neither|
|Canada1|550 Burrard Street|blank|Vancouver|British Columbia|V6C 0B3|main|
|Inuvik1|100 – 5603 50th Ave|blank|Yellowknife|Northwest Territories|X1A 1E8|terminal|
|British Columbia|550 Burrard Street|blank|Vancouver|British Columbia|V6C 0B3|neither|
|Canada2|525 3rd Avenue SW|blank|Calgary|Alberta|T2P 0G4|main|
|Inuvik2|102 Borden Drive|blank|Yellowknife|Northwest Territories|T2P 0G4|terminal|
|NAL|525 3rd Avenue SW|blank|Calgary|Alberta|T2P 0G4|neither|
|Miswaco|700-2nd Floor Street SW|blank|Calgary|Alberta|T2P 2W2|main|
|Kenai2|37915 Loriwood Drive|blank|Kenai|Alaska|99611|terminal|
|Yukon|700-2nd Floor Street SW|blank|Calgary|Alberta|T2P 2W2||neither|
|Canada3|31 Alder Dr|blank|Inuvik|Northwest Territories|X0E 0T0|main|
|Inuvik3|133 MacKenzie Rd|blank|Inuvik|Northwest Territories|X0E 0T0|terminal|
|Alaska2|31 Alder Dr|blank|Inuvik|NT|X0E 0T0|neither|


Scenario: TC6023 Address Required Error Message
Given I am logged in as HOS manager 
And I am on the Organization page
And I have selected a <groupname>
And I have clicked on Edit button
And I have cleared the existing address fields
When I select the <DOTtype> from the dropdown
And click on Save
Then an error displays with "Address is required"

Example:
|groupname|DOTtype|
|SLB|main|
|Kenai|terminal|

Scenario: TC6024 Address Displays on Daily Driver Log
Given I am logged in as HOS Manager
And I am on the HOS Reports Tab
And I have selected the "HOS Record of Duty Status Report" 
And I have selected Driver from the ReportOn dropdown
And I have selected a driver <fname><lname> from <divisionname> <driverteam>
When I click on HTML
Then the report displays with the same main/terminal address as the organization page for driver <fname><lname> from <divisionname> <driverteam>

Example:
|fname|lname|divisonname|driverteam|
|Jerome|Iginla|Canada1|Inuvik1|
|Blair|Jones|Alaska|Kenai|
|Lance|Bouma|Canada2|Inuvik2|
|Burke|Dales|Alaska2|Calgary||Kenai2|
|Chris|Butler|Canada3|Inuvik3|
 
 
Scenario: TC6025 Address Displays on Daily Driver Log for Groups
Given I am logged in as HOS Manager
And I am on the HOS Reports Tab
And I have selected the "HOS Record of Duty Status Report" 
And I have selected Groups from the ReportOn dropdown
And I have checked a <divisionname>
When I click on HTML
Then the report displays with the same main/terminal address as the organization page for <divisionname><driverteam>


Example:
|divisionname|driverteam|
|Alaska|Kenai|
|Canada1|Inuvik1|
|Canada2|Inuvik2|
|Alaska2|Kenai2|
|Canada3|Inuvik3|


Scenario: TC6026 Email Daily Driver log
Given I am logged in as HOS Manager
And I am on the HOS Reports Tab
And I have selected the "HOS Record of Duty Status Report" 
And I have selected Report On
And I have selected Driver
And I have selected a name
When I click on Email icon
And the EMail modal popup displays
And I enter my email address
And I click on Email button
Then I log into my email address
And I open the emailed report
And it displays the daily driver log with the main address<maddress1><maddress2><mcity><mstate><mzipcode>and terminal address <taddress1><taddress2><tcity><tstate><tzipcode> in the organization


Scenario: TC6027 Edit Group Name
Given I am logged in as HOS manager 
And I am on the Organization page
And I have selected a <groupname> 
And I have clicked on Edit button
And I have cleared the existing <name> fields
And I edit the name for my group to <name>
When click on Save
Then verify the <name><address1><address2><city><state><zipcode> were saved

Example:
|groupname|name|address1|address2|city|state|zipcode|
|SLB|Schlumberger|4225 West Lake Park Blvd|blank|West Valley|Utah|84120|



