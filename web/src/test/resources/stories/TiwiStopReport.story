Narrative:

In order view trips for tiwis Stops for each day
As an Administrator
I need a report that displays by group and or by team level

Scenario: TC6014:  Portal Stops Page display
Given I am logged into the portal 
and I have selected a group from the Home page drop-down
and I have clicked on the Stops tab
When I click on a driver radio button 
Then the stop page displays with data in columns for  
Driver Name
and Vehicle ID
and Stop Location
and Arrival Time
and Departure Time
and Duration of stop
and Low Idle Time
and High Idle Time
and Drive Time from 1st stop to next and continuous times between stops
and End of Day Stop needs to show Time and Location (End Trip)
and Start of Day needs to show Time and location (Start Trip)

Scenario: TC6015: Portal Stops Page display report in other formats
Given I am logged into the portal 
and I have selected a group from the Home page drop-down
and I have clicked on the Stops tab
When I click on a driver radio button 
Then the stop page displays with data in columns 
When I click on the icon drop-down <type>
Then it displays the report with data in columns for 
Driver Name
and Vehicle ID
and Stop Location
and Arrival Time
and Departure Time
and Duration of stop
and Low Idle Time
and High Idle Time
and Drive Time from 1st stop to next and continuous times between stops
and End of Day Stop needs to show Time and Location (End Trip)
and Start of Day needs to show Time and location (Start Trip)

Examples: 
|type|
|E-mail This Report|
|Export to PDF|
|Export to Excel|

Scenario: TC6016: Stops Page has clickable Vehicle link
Given I am logged into the portal 
and I have selected a group from the Home page drop-down
and I have clicked on the Stops tab
and I click on a driver radio button 
and the stop page displays with data in columns for  
Driver Name
and Vehicle ID
and Stop Location
and Arrival Time
and Departure Time
and Duration of stop
and Low Idle Time
and High Idle Time
and Drive Time from 1st stop to next and continuous times between stops
and End of Day Stop needs to show Time and Location (End Trip)
and Start of Day needs to show Time and location (Start Trip)
When I click on the Vehicle ID
Then the Vehicle Performance page opens and displays

Scenario: TC6017: Schedule Stops Reports by Group
Given I am logged into the portal 
and I am on the Admin page
And have clicked on the Add Reports tab 
When I Add a name "Daily Stops Report"
And Select "Active" from Status drop-down
And enter "Time of Day"
And select "Daily" from Occurrence
And select "Stop Report" from Report drop-down
And select <type> from Days Reported drop-down
And select a group from the Group drop-down
And select an Owner from the Owner drop-down
And enter a valid email address
And click on Save button
Then the report will appear as scheduled in the email displaying all vehicles and drivers in the date range

Example:
|value|
|Today|
|Yesterday|

