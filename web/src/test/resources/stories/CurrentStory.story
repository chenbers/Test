Scenario: TC1275: My Account - Edit Account - Measurement Validation for Waysmart drivers
Given I am on the login page
When I am logged in as TeamOnly user
Then I click group "Firmware Team" Groups navtree
And I click the 14th Row of the Driver Value link

And I click the Maximize Seat Belt button
And I click the twelve months Duration link
And I validate the 1st Row of the Top Speed Units text contains "kph" 
And I validate the 1st Row of the Distance Units text contains "km"
And I click the Return To Performance Page button

And I click the View All Trips link
And I verify "Total Kilometers Driven" is on the page
And I validate the 1st Row of the Distance Entry text contains "km"

And I click group "Firmware Team" Groups navtree
And I click the Vehicle Statistics link
And I validate the Distance Driven Team Value text contains "km"
And I validate the 1st Row of the Distance Driven Value text contains "km"
And I click the 14th Row of the Driver Value link
And I verify "Crashes per million kilometers" is on the page 
And I click the Maximize Speed button
And I click the twelve months Duration link
And I validate the Category One Speeds link contains "kph"
And I validate the Category Two Speeds link contains "kph"
And I validate the Category Three Speeds link contains "kph"
And I validate the Category Four Speeds link contains "kph"
And I validate the Category Five Speeds link contains "kph"
And I validate the 1st Row of the Posted Speed Units text contains "kph"
And I validate the 1st Row of the Avg Speed Units text contains "kph"
And I validate the 1st Row of the Top Speed Units text contains "kph" 
And I validate the 1st Row of the Distance Units text contains "km"
And I click the Return To Performance Page button
And I click the Maximize Driving Style button
And I click the twelve months Duration link
And I validate the 1st Row of the Speed text contains "kph"
And I click the Return To Performance Page button
And I click the Maximize Seat Belt button
And I click the twelve months Duration link
And I validate the 1st Row of the Top Speed Units text contains "kph" 
And I validate the 1st Row of the Distance Units text contains "km"

And I click the View All Trips link
And I verify "Total Kilometers Driven" is on the page
And I validate the 1st Row of the Distance Entry text contains "km"

And I click test group "Tek-QA Team" Groups navtree
And I click the Speed link
And I validate the Category One Speeds link contains "kph"
And I validate the Category Two Speeds link contains "kph"
And I validate the Category Three Speeds link contains "kph"
And I validate the Category Four Speeds link contains "kph"
And I validate the Category Five Speeds link contains "kph"
And I click on the Reports link
And I click on the Drivers link
And I validate the 1st Row of the Distance Driven Value text contains "km"
And I click on the Vehicles link
And I validate the 1st Row of the Distance Driven Value text contains "km"
And I validate the 1st Row of the Odometer Value text contains "km"
And I click on the Notifications link
And I click on the Safety link
And I select "Firmware Team" from the Team dropdown
And I click on the Refresh button
And I validate the Detail text contains "kph"
And I click the Logo button
And I verify "Crashes per million kilometers" is on the page 
And I verify "Kilometers since last crash" is on the page
And I validate the Number Speeding Total Distance Driven text contains "km"
And I validate the Number Speeding Total Distance Speeding text contains "km"
And I validate the Title Speeding text contains "km"
And I click the My Account link
And I click the Edit button
And I select "English" from the Measurement dropdown
And I click the Save button
And I click the My Messages link
And I click the My Account link
And I validate the Measurement text is "English"
And I click test group "Tek-QA Team" Groups navtree
And I verify "Crashes per million miles" is on the page
And I verify "Miles since last crash" is on the page
And I validate the Distance Driven Team Value text contains "mi"
And I validate the 1st Row of the Distance Driven Value text contains "mi"
And I click the 14th Row of the Driver Value link
And I verify "Crashes per million miles" is on the page 
And I click the Maximize Speed button
And I click the twelve months Duration link
And I validate the Category One Speeds link contains "kph"
And I validate the Category Two Speeds link contains "kph"
And I validate the Category Three Speeds link contains "kph"
And I validate the Category Four Speeds link contains "kph"
And I validate the Category Five Speeds link contains "kph"
And I validate the 1st Row of the Posted Speed Units text contains "kph"
And I validate the 1st Row of the Avg Speed Units text contains "kph"
And I validate the 1st Row of the Top Speed Units text contains "kph"  
And I validate the Distance Units text contains "mi"
And I click the Return To Performance Page button
And I click the Maximize Driving Style button
And I click the twelve months Duration link
And I validate the 1st Row of the Speed text contains "kph"
And I click the Return To Performance Page button
And I click the Maximize Seat Belt button
And I click the twelve months Duration link
And I validate the 1st Row of the Top Speed Units text contains "kph" 
And I validate the Distance Units text contains "mi"
And I click test group "Tek-QA Team" Groups navtree
And I click the Vehicle Statistics link
And I validate the Distance Driven Team Value text contains "mi"
And I validate the 1st Row of the Distance Driven Value text contains "mi"
And I click the 1st Row of the Vehicle Value link
And I verify "Crashes per million miles" is on the page 
And I click the Maximize Speed button
And I click the twelve months Duration link
And I validate the Category One Speeds link contains "mph"
And I validate the Category Two Speeds link contains "mph"
And I validate the Category Three Speeds link contains "mph"
And I validate the Category Four Speeds link contains "mph"
And I validate the Category Five Speeds link contains "mph"
And I validate the Posted Speed Units text contains "mph"
And I validate the Posted Speed text contains "mph"
And I validate the Avg Speed Units text contains "mph"
And I validate the Top Speed Units text contains "mph"
And I validate the Distance Units text contains "mi"
And I click the Return To Performance Page button
And I click the Maximize Driving Style button
And I click the twelve months Duration link
And I validate the Speed text contains "mph"
And I click the Return To Performance Page button
And I click the Maximize Seat Belt button
And I click the twelve months Duration link
And I validate the Top Speed Units text contains "mph"
And I validate the Distance Units text contains "mi"
And I click test group "Tek-QA Team" Groups navtree
And I click the Speed link
And I validate the Category One Speeds link contains "mph"
And I validate the Category Two Speeds link contains "mph"
And I validate the Category Three Speeds link contains "mph"
And I validate the Category Four Speeds link contains "mph"
And I validate the Category Five Speeds link contains "mph"
And I click on the Reports link
And I click on the Drivers link
And I validate the 1st Row of the Distance Driven Value text contains "mi"
And I click on the Vehicles link
And I validate the 1st Row of the Distance Driven Value text contains "mi"
And I validate the 1st Row of the Odometer Value text contains "mi"
And I click on the Notifications link
And I click on the Safety link
And I select "Top" from the Team dropdown
And I click on the Refresh button
And I validate the Detail text contains "mph"
And I click the Logo button
And I verify "Crashes per million miles" is on the page 
And I verify "Miles since last crash" is on the page
And I validate the Number Speeding Total Distance Driven text contains "mi"
And I validate the Number Speeding Total Distance Speeding text contains "mi"
And I validate the Title Speeding text contains "mi"