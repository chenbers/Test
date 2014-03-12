Narrative:

In order view trips for Tiwi Stops for each day
As an Administrator
I need a report that displays by team level

Scenario: TC6014:  Portal Stops Page Display
Given I am logged into the portal 
And I have selected Skip's team from the Home page drop-down
And I have clicked on the Stops tab
When I click on a radio button for Orson Buggy
Then the stop page displays appropriate data in each column


Scenario: TC6015: Portal Stops Page display report in other formats
Given I am logged into the portal 
And I have selected Skip's team from the Home page drop-down
And I have clicked on the Stops tab
And I click on a radio button for Orson Buggy
Then the stop page displays with data in columns 
When I click on the <toolicon> dropdown
Then the stop page opens in the chosen format and displays appropriate data in each column

Examples: 
|toolicon|
|ExporttoExcel|

Scenario: TC6016: Stops Page has clickable Vehicle link
Given I am logged into the portal 
And I have selected an active team from the Home page drop-down
And I have clicked on the Stops tab
And I click on a radio button for Orson Buggy
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
And I have selected Skip's team from the Home page drop-down
And I have clicked on the Stops tab
And I click on a radio button for Orson Buggy with a recent trip
Then the stop page displays with data in columns 
When I click on the tool icon drop-down 
And Select "E-mail This Report"
Then the E-mail pop-up opens
And I clear any existing E-mails
And I enter my email address
And click on the E-mail button
Then login to my E-mail
And open my E-mail
And click on the report link
Then the stop page displays appropriate data in each column

Scenario: TC6019: Performance Stops Reports
Given I am logged into the portal as an Administrator
And I am on the Reports tab
And I have clicked the Performance page
When I have selected the Driver Stops Report
And selected the <timeframe> from the dropdown
And selected Skip's Team from the group dropdown
And click on an <exporticon>
Then the report will appear in the selected format

Example:
|timeframe|exporticon|
|Today|Excel|
|Today|HTML|
|Yesterday|Excel|
|Yesterday|HTML|
|Past7Days|Excel|
|Past7Days|HTML|

Scenario: TC6020: Performance Stops Reports sent EMail
Given I am logged into the portal as an Administrator
And I am on the Reports tab
And I have clicked the Performance page
When I have selected the Driver Stops Report
And selected the <timeframe> from the dropdown
And selected Skip's Team from the group dropdown
And click on EMail icon
Then the E-mail pop-up opens
And I clear any existing E-mails
And I enter my email address
And click on the E-mail button
Then login to my E-mail
And open my E-mail
And click on the View link
Then the stop page displays appropriate data in each column

Example:
|timeframe|
|Today|
|Yesterday|
|Past7Days|



