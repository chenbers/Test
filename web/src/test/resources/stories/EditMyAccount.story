Test Case for TF523

Meta:
@page login
@testFolder TF523

Narrative: 

Scenario: TC1275: My Account - Edit Account - Measurement Validation for Waysmart drivers
Given I am on the login page
When I am logged in as TeamOnly user
And I click the My Account link
And I click the Edit button
And I select "Metric" from the Measurement dropdown
And I click the Save button
And I click the My Messages link
And I click the My Account link
Then I validate the Measurement text is "Metric"
And I click group "Firmware Team" Groups navtree
And I verify "Crashes per million kilometers" is on the page
And I verify "Kilometers since last crash" is on the page
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
And I click the Return To Performance Page button
And I click the View All Trips link
And I verify "Total Kilometers Driven" is on the page
And I validate the 1st Row of the Distance Entry text contains "km"
And I click group "Firmware Team" Groups navtree
And I click the Speed link
And I validate the Category One Speeds text contains "kph"
And I validate the Category Two Speeds text contains "kph"
And I validate the Category Three Speeds text contains "kph"
And I validate the Category Four Speeds text contains "kph"
And I validate the Category Five Speeds text contains "kph"
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
And I validate the 1st Row of the Entry Detail text contains "kph"
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
And I click group "Firmware Team" Groups navtree
And I verify "Crashes per million miles" is on the page
And I verify "Miles since last crash" is on the page
And I validate the Distance Driven Team Value text contains "mi"
And I validate the 1st Row of the Distance Driven Value text contains "mi"
And I click the 14th Row of the Driver Value link
And I verify "Crashes per million miles" is on the page 
And I click the Maximize Speed button
And I click the twelve months Duration link
And I validate the Category One Speeds link contains "mph"
And I validate the Category Two Speeds link contains "mph"
And I validate the Category Three Speeds link contains "mph"
And I validate the Category Four Speeds link contains "mph"
And I validate the Category Five Speeds link contains "mph"
And I validate the 1st Row of the Posted Speed Units text contains "mph"
And I validate the 1st Row of the Avg Speed Units text contains "mph"
And I validate the 1st Row of the Top Speed Units text contains "mph"  
And I validate the Distance Units text contains "mi"
And I click the Return To Performance Page button
And I click the Maximize Driving Style button
And I click the twelve months Duration link
And I validate the 1st Row of the Speed text contains "mph"
And I click the Return To Performance Page button
And I click the Maximize Seat Belt button
And I click the twelve months Duration link
And I validate the 1st Row of the Top Speed Units text contains "mph" 
And I validate the 1st Row of the Distance Units text contains "mi"
And I click the Return To Performance Page button
And I click the View All Trips link
And I verify "Total Miles Driven" is on the page
And I validate the 1st Row of the Distance Entry text contains "mi"
And I click group "Firmware Team" Groups navtree
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
And I validate the 1st Row of the Top Speed Units text contains "mph" 
And I validate the 1st Row of the Distance Units text contains "mi"
And I click the Return To Performance Page button
And I click the View All Trips link
And I verify "Total Miles Driven" is on the page
And I validate the 1st Row of the Distance Entry text contains "mi"
And I click group "Firmware Team" Groups navtree
And I click the Speed link
And I validate the Category One Speeds text contains "mph"
And I validate the Category Two Speeds text contains "mph"
And I validate the Category Three Speeds text contains "mph"
And I validate the Category Four Speeds text contains "mph"
And I validate the Category Five Speeds text contains "mph"
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
And I validate the 1st Row of the Entry Detail text contains "mph"
And I click the Logo button
And I verify "Crashes per million miles" is on the page 
And I verify "Miles since last crash" is on the page
And I validate the Number Speeding Total Distance Driven text contains "mi"
And I validate the Number Speeding Total Distance Speeding text contains "mi"
And I validate the Title Speeding text contains "mi"

Scenario: TC6139: My Account - Edit Account - Measurement Validation for Tiwi drivers
Given I am on the login page
When I am logged in as TeamOnly user
And I click the My Account link
And I click the Edit button
And I select "Metric" from the Measurement dropdown
And I click the Save button
And I click the My Messages link
And I click the My Account link
Then I validate the Measurement text is "Metric"
And I click group "Firmware Team" Groups navtree
And I verify "Crashes per million kilometers" is on the page
And I verify "Kilometers since last crash" is on the page
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
And I validate the 1st Row of the Avg Speed Units text contains "kph"
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
And I validate the 1st Row of the Avg Speed Units text contains "kph"
And I validate the 1st Row of the Top Speed Units text contains "kph" 
And I validate the 1st Row of the Distance Units text contains "km"
And I click the Return To Performance Page button
And I click the View All Trips link
And I verify "Total Kilometers Driven" is on the page
And I validate the 1st Row of the Distance Entry text contains "km"
And I click group "Firmware Team" Groups navtree
And I click the Speed link
And I validate the Category One Speeds text contains "kph"
And I validate the Category Two Speeds text contains "kph"
And I validate the Category Three Speeds text contains "kph"
And I validate the Category Four Speeds text contains "kph"
And I validate the Category Five Speeds text contains "kph"
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
And I validate the 1st Row of the Entry Detail text contains "kph"
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
And I click group "Firmware Team" Groups navtree
And I verify "Crashes per million miles" is on the page
And I verify "Miles since last crash" is on the page
And I validate the Distance Driven Team Value text contains "mi"
And I validate the 1st Row of the Distance Driven Value text contains "mi"
And I click the 14th Row of the Driver Value link
And I verify "Crashes per million miles" is on the page 
And I click the Maximize Speed button
And I click the twelve months Duration link
And I validate the Category One Speeds link contains "mph"
And I validate the Category Two Speeds link contains "mph"
And I validate the Category Three Speeds link contains "mph"
And I validate the Category Four Speeds link contains "mph"
And I validate the Category Five Speeds link contains "mph"
And I validate the 1st Row of the Posted Speed Units text contains "mph"
And I validate the 1st Row of the Avg Speed Units text contains "mph"
And I validate the 1st Row of the Top Speed Units text contains "mph"  
And I validate the Distance Units text contains "mi"
And I click the Return To Performance Page button
And I click the Maximize Driving Style button
And I click the twelve months Duration link
And I validate the 1st Row of the Speed text contains "mph"
And I click the Return To Performance Page button
And I click the Maximize Seat Belt button
And I click the twelve months Duration link
And I validate the 1st Row of the Avg Speed Units text contains "mph"
And I validate the 1st Row of the Top Speed Units text contains "mph" 
And I validate the 1st Row of the Distance Units text contains "mi"
And I click the Return To Performance Page button
And I click the View All Trips link
And I verify "Total Miles Driven" is on the page
And I validate the 1st Row of the Distance Entry text contains "mi"
And I click group "Firmware Team" Groups navtree
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
And I validate the 1st Row of the Avg Speed Units text contains "mph"
And I validate the 1st Row of the Top Speed Units text contains "mph" 
And I validate the 1st Row of the Distance Units text contains "mi"
And I click the Return To Performance Page button
And I click the View All Trips link
And I verify "Total Miles Driven" is on the page
And I validate the 1st Row of the Distance Entry text contains "mi"
And I click group "Firmware Team" Groups navtree
And I click the Speed link
And I validate the Category One Speeds text contains "mph"
And I validate the Category Two Speeds text contains "mph"
And I validate the Category Three Speeds text contains "mph"
And I validate the Category Four Speeds text contains "mph"
And I validate the Category Five Speeds text contains "mph"
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
And I validate the 1st Row of the Entry Detail text contains "mph"
And I click the Logo button
And I verify "Crashes per million miles" is on the page 
And I verify "Miles since last crash" is on the page
And I validate the Number Speeding Total Distance Driven text contains "mi"
And I validate the Number Speeding Total Distance Speeding text contains "mi"
And I validate the Title Speeding text contains "mi"

Scenario: TC1273: My Account - Edit Account - Fuel Efficiency Ratio Validation
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I select "Metric" from the Measurement dropdown
And I click the Save button
And I click the My Messages link
And I click the My Account link
Then I validate the Measurement text is "Metric"
And I click group "Firmware Team" Groups navtree
And I click the year Duration link
And I save the 1st Row of the Fuel Efficiency Team Value text as DFE1
And I click the Vehicle Statistics link
And I save the 1st Row of the Fuel Efficiency Team Value text as DFE2
And I click the My Account link
And I click the Edit button
And I select "English" from the Measurement dropdown
And I click the Save button
And I click the My Messages link
And I click the My Account link
And I validate the Measurement text is "English"
And I click the Logo button
And I click the year Duration link
And I validate the 1st Row of the Fuel Efficiency Team Value text is not DFE1
And I click the Vehicle Statistics link
And I validate the 1st Row of the Fuel Efficiency Team Value text is not DFE2

Scenario: TC1276: My Account - Edit Account - Missing Required Field Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "" into the Email1 textfield
And I type "" into the Email2 textfield
And I type "" into the Phone1 textfield
And I type "" into the Phone2 textfield
And I type "" into the Text Message1 textfield
And I type "" into the Text Message2 textfield
And I click the Save button
Then I validate the Error Email1 text is "Required"

Scenario: TC1277: My Account - Edit Account - Phone Max Characters Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "0000000000000000" into the Phone1 textfield
And I type "1111111111111111" into the Phone2 textfield
And I click the Save button
Then I validate the Error Phone1 text is "Must consist of up to 15 numeric characters"
And I validate the Error Phone2 text is "Must consist of up to 15 numeric characters"

Scenario: TC1278: My Account - Edit Account - Phone Missing Character Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "00" into the Phone1 textfield
And I type "11" into the Phone2 textfield
And I click the Save button
Then I validate the Error Phone1 text is "Must consist of up to 15 numeric characters"
And I validate the Error Phone2 text is "Must consist of up to 15 numeric characters"

Scenario: TC1279: My Account - Edit Account - Phone Special Character Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "& ^ $" into the Phone1 textfield
And I type "& ^ $" into the Phone2 textfield
And I click the Save button
Then I validate the Error Phone1 text is "Must consist of up to 15 numeric characters"
And I validate the Error Phone2 text is "Must consist of up to 15 numeric characters"

Scenario: TC1282: My Account - Edit Account - Text Message Format Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "8015551234 @domain.com" into the Text Message1 textfield
And I type "801555123411111@domain@domain.com" into the Text Message2 textfield
And I click the Save button
Then I validate the Error Text1 text is "Incorrect format (8015551212@tmomail.com)"
And I validate the Error Text2 text is "Incorrect format (8015551212@tmomail.com)"

Scenario: TC1280: My Account - Edit Account - Save Button
Given I am logged in as a "Admin" user
When I click the My Account link
And I save the Red Flag Info text as ORIGINALinfo
And I save the Red Flag Warn text as ORIGINALwarn
And I save the Red Flag Critical text as ORIGINALcrit
And I save the Map Type text as ORIGINALmapType
And I save the Map Layers text as ORIGINALmapLayers
And I save the Locale text as ORIGINALlocale
And I save the Measurement text as ORIGINALmeasurement
And I save the Fuel Efficiency text as ORIGINALfuelEff
And I save the Email1 text as ORIGINALemailOne
And I save the Email2 text as ORIGINALemailTwo
And I save the Phone1 text as ORIGINALphoneOne
And I save the Phone2 text as ORIGINALphoneTwo
And I save the Text Message1 text as ORIGINALtextOne
And I save the Text Message2 text as ORIGINALtextTwo
And I click the Edit button
And I select partial text "E-mail 1" from the Information drop down
And I select partial text "E-mail 2" from the Warning drop down
And I select partial text "Phone 1" from the Critical drop down
And I select "Hyb (Hybrid)" from the Map Type drop down
And I click the 1st Row of the Map Layer checkbox
And I select "Deutsch" from the Locale drop down
And I select "Metric" from the Measurement drop down
And I select "Kilometers Per Liter" from the Fuel Efficiency drop down
And I type "test@test.com" into the Email1 textfield
And I type "test2@test.com" into the Email2 textfield
And I type "0000000000" into the Phone1 textfield
And I type "1111111111" into the Phone2 textfield
And I type "8015551212@tmomail.com" into the Text Message1 textfield
And I type "8015551213@tmomail.com" into the Text Message2 textfield
And I click the Save button
Then I validate the Red Flag Info text is not ORIGINALinfo
And I validate the Red Flag Warn text is not ORIGINALwarn
And I validate the Red Flag Critical text is not ORIGINALcrit
And I validate the Map Type text is not ORIGINALmapType
And I validate the Map Layers text is not ORIGINALmapLayers
And I validate the Locale text is not ORIGINALlocale
And I validate the Measurement text is not ORIGINALmeasurement
And I validate the Fuel Efficiency text is not ORIGINALfuelEff
And I validate the Email1 text is not ORIGINALemailOne
And I validate the Email2 text is not ORIGINALemailTwo
And I validate the Phone1 text is not ORIGINALphoneOne
And I validate the Phone2 text is not ORIGINALphoneTwo
And I validate the Text Message1 text is not ORIGINALtextOne
And I validate the Text Message2 text is not ORIGINALtextTwo
And I click the Edit button
And I select ORIGINALinfo from the Information drop down
And I select ORIGINALwarn from the Warning drop down
And I select ORIGINALcrit from the Critical drop down
And I select ORIGINALmapType from the Map Type drop down
And I click the 1st Row of the Map Layer checkbox
And I select ORIGINALlocale from the Locale drop down
And I select ORIGINALmeasurement from the Measurement drop down
And I select ORIGINALfuelEff from the Fuel Efficiency Ratio drop down
And I type ORIGINALemailOne into the Email1 textfield
And I type ORIGINALemailTwo into the Email2 textfield
And I type ORIGINALphoneOne into the Phone1 textfield
And I type ORIGINALphoneTwo into the Phone2 textfield
And I type ORIGINALtextOne into the Text Message1 textfield
And I type ORIGINALtextTwo into the Text Message2 textfield
And I click the Save button

Scenario: TC1272: My Account - Edit Account - E-mail Address Format Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "tlc1960@test" into the Email1 textfield
And I type "tina1960test.com" into the Email2 textfield
And I click the Save button
Then I validate the Error Email1 text is "Incorrect format (jdoe@tiwipro.com)"
And I validate the Error Email2 text is "Incorrect format (jdoe@tiwipro.com)"

Scenario: TC1271: My Account - Edit Account - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I click the My Account link
And I save the Red Flag Info text as ORIGINALinfo
And I save the Red Flag Warn text as ORIGINALwarn
And I save the Red Flag Critical text as ORIGINALcrit
And I save the Map Type text as ORIGINALmapType
And I save the Map Layers text as ORIGINALmapLayers
And I save the Locale text as ORIGINALlocale
And I save the Measurement text as ORIGINALmeasurement
And I save the Fuel Efficiency text as ORIGINALfuelEff
And I save the Email1 text as ORIGINALemailOne
And I save the Email2 text as ORIGINALemailTwo
And I save the Phone1 text as ORIGINALphoneOne
And I save the Phone2 text as ORIGINALphoneTwo
And I save the Text Message1 text as ORIGINALtextOne
And I save the Text Message2 text as ORIGINALtextTwo
And I click the Edit button
And I select the option containing "E-mail 1" from the Information drop down
And I select the option containing "E-mail 2" from the Warning drop down
And I select the option containing "Phone 1" from the Critical drop down
And I select "Hyb (Hybrid)" from the Map Type drop down
And I click the 1st Row of the Map Layer checkbox
And I select "Deutsch" from the Locale drop down
And I select "Metric" from the Measurement drop down
And I select "Kilometers Per Liter" from the Fuel Efficiency drop down
And I type "test@test.com" into the Email1 textfield
And I type "test2@test.com" into the Email2 textfield
And I type "0000000000" into the Phone1 textfield
And I type "1111111111" into the Phone2 textfield
And I type "8015551212@tmomail.com" into the Text Message1 textfield
And I type "8015551213@tmomail.com" into the Text Message2 textfield
And I click the Cancel button
Then I validate the Red Flag Info text is ORIGINALinfo
And I validate the Red Flag Warn text is ORIGINALwarn
And I validate the Red Flag Critical text is ORIGINALcrit
And I validate the Map Type text is ORIGINALmapType
And I validate the Map Layers text is ORIGINALmapLayers
And I validate the Locale text is ORIGINALlocale
And I validate the Measurement text is ORIGINALmeasurement
And I validate the Fuel Efficiency text is ORIGINALfuelEff
And I validate the Email1 text is ORIGINALemailOne
And I validate the Email2 text is ORIGINALemailTwo
And I validate the Phone1 text is ORIGINALphoneOne
And I validate the Phone2 text is ORIGINALphoneTwo
And I validate the Text Message1 text is ORIGINALtextOne
And I validate the Text Message2 text is ORIGINALtextTwo
