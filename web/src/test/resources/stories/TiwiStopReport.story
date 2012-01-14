Narrative:

In order view trips for tiwis Stops for each day
As an Administrator
I need a report that displays by team level

Scenario: TC6014:  Portal Stops Page Display
Given I am logged into the portal 
And I have selected an active team from the Home page drop-down
And I have clicked on the Stops tab
When I click on a radio button for a driver with a recent trip
Then the stop page displays with data in columns for Driver Name, Vehicle ID, Stop Location, Arrival Time
Departure Time, Duration of stop, Low Idle Time, High Idle Time, Drive Time from 1st stop to next, 
continuous times between stops, End of Day Stop show Time and Location (End Trip), 
Start of Day show Time and location (Start Trip).

Scenario: TC6015: Portal Stops Page display report in other formats
Given I am logged into the portal 
And I have selected an active team from the Home page drop-down
And I have clicked on the Stops tab
And I click on a radio button for a driver with a recent trip
Then the stop page displays with data in columns 
When I click on the icon drop-down <type>
Then the stop page opens in the chosen format with data in columns for Driver Name.Vehicle ID,Stop Location, Arrival Time
Departure Time, Duration of stop, Low Idle Time, High Idle Time, Drive Time from 1st stop to next, 
continuous times between stops, End of Day Stop show Time and Location (End Trip), 
Start of Day show Time and location (Start Trip).

Examples: 
|type|
|Export to PDF|
|Export to Excel|

Scenario: TC6016: Stops Page has clickable Vehicle link
Given I am logged into the portal 
And I have selected an active team from the Home page drop-down
And I have clicked on the Stops tab
And I click on a radio button for a driver with a recent trip
And the stop page displays with data in columns 
When I click on the Vehicle ID link
Then the Vehicle Performance page opens and displays

Scenario: TC6017: Schedule Stops Reports by Group
Given I am logged into the portal 
And I am on the Admin page
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

