Narrative:

In order view trips for Tiwi Stops for each day
As an Administrator
I need a report that displays by team level

Scenario: TC6014:  Portal Stops Page Display
Given I am logged into the portal 
And I have selected an active team from the Home page drop-down
And I have clicked on the Stops tab
When I click on a radio button for a driver with a recent trip
Then the stop page displays appropriate data in each column


Scenario: TC6015: Portal Stops Page display report in other formats
Given I am logged into the portal 
And I have selected an active team from the Home page drop-down
And I have clicked on the Stops tab
And I click on a radio button for a driver with a recent trip
Then the stop page displays with data in columns 
When I click on the icon dropdown <type>
Then the stop page opens in the chosen format and displays appropriate data in each column

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
And I am on the Administrator page
And have clicked on the Add Reports tab 
When I Add a name "Daily Stops Report"
And Select "Active" from Status drop-down
And enter "Time of Day"
And select <occurrence> from Occurrence
And select "Stop Report" from Report drop-down
And select <daysreported> from Days Reported drop-down
And select a group from the Group drop-down
And select an Owner from the Owner drop-down
And enter a valid email address
And click on Save button
Then the report will appear as scheduled in the email displaying all vehicles and drivers in the date range

Example:
|occurrence|daysreported|
|daily|today|
|daily|yesterday|
|weekly|today|
|weekly|yesterday|
|monthly|today|
|monthly|yesterday|


Scenario: TC6018: Portal Stops Page display report in E-Mail
Given I am logged into the portal 
And I have selected an active team from the Home page drop-down
And I have clicked on the Stops tab
And I click on a radio button for a driver with a recent trip
Then the stop page displays with data in columns 
When I click on the icon drop-down 
And Select "E-mail This Report"
Then the E-mail pop-up opens
And I clear any existing E-mails
And I enter my email address
And click on the E-mail button
Then login to my E-mail
And open my E-mail
And click on the report link
Then the stop page displays appropriate data in each column

Scenario: TC6019 Schedule Stops Report Change to InActive
Given I am logged into the portal 
And I am on the Administrator page
And I have clicked on the Reports tab
And have clicked on the existing Tiwi Stops Report
When Select "InActive" from Status drop-down
And I click on Save button
Then the scheduled report no longer e-mails the Tiwi Stops report


