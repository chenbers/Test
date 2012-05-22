Test Case for TF523

Meta:
@page login
@testFolder TF523

Narrative: 

Scenario: TC1275: My Account - Edit Account - Measurement Validation
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I select "Metric" from the Measurement dropdown
And I click the Save button
And I click the My Messages link
And I click the My Account link
Then I validate the Measurement text is "Metric"
And I click the Logo button
And I verify "Crashes per million kilometers" is on the page
And I verify "Kilometers since last crash" is on the page
And I validate the Distance Driven Team Header text contains "km"
And I validate the Distance Driven Team Value text contains "km"
And I click the 1st Row of the Driver Value link
And I verify "Crashes per million kilometers" is on the page 
And I click the Maximize Speed button
And I validate the Posted Speed Units text contains "kph"
And I validate the Avg Speed text contains "kph"
And I validate the Top Speed text contains "kph" 
And I validate the Distance text contains "km"
And I click the Return To Performance Page button
And I click the Maximize Driving Style button
And I validate the Speed text contains "kph"
And I click the Return To Performance Page button
And I click the Maximize Seat Belt button
And I validate Avg Speed text contains "kph"
And I validate the Top Speed text contains "kph"
And I validate the Distance text contains "km"
And I click the Logo button
And I click the Vehicle Statistics link
And I validate the Distance Driven Team Header text contains "km"
And I validate the Distance Driven Team Value text contains "km"
And I click the 1st Row of the Entry Vehicle By Position link
And I verify "Crashes per million kilometers" is on the page 
And I click the Maximize Speed button
And I validate the Posted Speed text contains "kph"
And I validate the Avg Speed text contains "kph"
And I validate the Top Speed text contains "kph"
And I validate the Distance text contains "km"
And I click the Return To Performance Page button
And I click the Maximize Driving Style button
And I validate the Speed text contains "kph"
And I click the Return To Performance Page button
And I click the Maximize Seat Belt button
And I validate the Avg Speed text contains "kph"
And I validate the Top Speed text contains "kph"
And I validate the Distance text contains "km"
And I click the Logo button
And I validate the Speeding Distance By Speed Limit Header text contains "kph"
And I validate the Speeding Distance By Speed Limit text contains "kph" 
And I validate the Limit Table text contains "kph"
And I click on the Reports link
And I click on the Drivers link
And I validate the Distance Driven text contains "km"
And I click on the Vehicles link
And I validate the Distance Driven text contains "km"
And I validate the Odometer text contains "km"
And I click on the Notifications link
And I click on the Safety link
And I select "Top" from the Team dropdown
And I click on the Refresh button
And I validate the Detail text contains "kph"
And I validate the Detail text contains "kilometers"
And I click the Logo button
And I verify "Crashes per million kilometers" is on the page 
And I verify "Kilometers since last crash" is on the page
And I validate the Total Distance Driven text contains "km"
And I validate the Total Distance Speeding text contains "km"
And I verify "Speeding % (km)" is on the page 
And I click the My Account link
And I click the Edit button
And I select "English" from the Measurement dropdown
And I click the Save button
And I click the My Messages link
And I click the My Account link
And I validate the Measurement text is "English"
And I click the Logo button
And I verify "Crashes per million miles" is on the page
And I verify "Miles since last crash" is on the page
And I validate the Distance Driven column contains "mi"
And I click the 1st Row of the Driver Name link
And I verify "Crashes per million kilometers" is on the page
And I click the Maximize Speed button
And I validate the Posted Speed text contains "mph"
And I validate the Avg Speed text contains "mph"
And I validate the Top Speed text contains "mph"
And I validate the Distance text contains "mi"
And I click the Return To Performance Page button
And I click the Maximize Driving Style button
And I validate the Speed text contains "mph"
And I click the Return To Performance Page button
And I click the Maximize Seat Belt button
And I validate the Avg Speed text contains "mph"
And I validate the Top Speed text contains "mph"
And I validate the Distance text contains "mi"
And I click the Logo button
And I click the Vehicle Statistics link
And I validate the Distance Driven column contains "mi"
And I click the 1st Row of the Vehicle Name link
And I verify "Crashes per million Miles" is on the page 
And I click the Maximize Speed button
And I validate the Posted Speed text contains "mph"
And I validate the Avg Speed text contains "mph"
And I validate the Top Speed text contains "mph"
And I validate the Distance text contains "mi"
And I click the Return To Performance Page button
And I click the Maximize Driving Style button
And I validate the Speed text contains "mph"
And I click the Return To Performance Page button
And I click the Maximize Seat Belt button
And I validate the Avg Speed text contains "mph"
And I validate the Top Speed text contains "mph"
And I validate the Distance text contains "mi"
And I click the Logo button
And I validate the Speeding Distance By Speed Limit Header text contains "mph"
And I validate the Speeding Distance By Speed Limit text contains "mph" 
And I validate the Limit Table text contains "mph"
And I click on the Reports link
And I click on the Drivers link
And I validate the Distance Driven text contains "mi"
And I click on the Vehicles link
And I validate the Distance Driven text contains "mi"
And I validate the Odometer text contains "mi"
And I click on the Notifications link
And I click on the Safety link
And I select "Top" from the Team dropdown
And I click on the Refresh button
And I validate the Detail text contains "mi"
And I click the Logo button
And I am on the Overview page
And I verify "Crashes per million miles" is on the page
And I verify "Miles since last crash" is on the page
And I validate the Total Distance Driven text contains "mi"
And I validate the Total Distance Speeding text contains "mi"
And I verify "Speeding % (mi)" is on the page 

Scenario: TC1273: My Account - Edit Account - Fuel Efficiency Ratio Validation
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I select "Metric" from the Measurement dropdown
And I click the Save button
And I click the My Messages link
And I click the My Account link
Then I validate the Measurement text is "Metric"
And I click the Logo button
And I click the Driver Statistics link
And I click the 365 Days link
And I save the Fuel Efficiency Team Total as DFE1
And I click the Vehicle Statistics link
And I click the 365 Days link
And I save the Fuel Efficiency Team Total as DFE2
And I click the My Account link
And I click the Edit button
And I select "English" from the Measurement dropdown
And I click the Save button
And I click the My Messages link
And I click the My Account link
And I validate the Measurement text is "English"
And I click the Logo button
And I click the Driver Statistics link
And I click the 365 Days link
And I validate the Fuel Efficiency Team Total is not DFE1
And I click the Vehicle Statistics link
And I click the 365 Days link
And I validate the Fuel Efficiency Team Total is not DFE2

Scenario: TC1276: My Account - Edit Account - Missing Required Field Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "" into the Email1 field
And I type "" into the Email2 field
And I type "" into the Phone1 field
And I type "" into the Phone2 field
And I type "" into the Text Message1 field
And I type "" into the Text Message2 field
And I click the Save button
Then I validate the Error Email1 text is "Required"

Scenario: TC1277: My Account - Edit Account - Phone Max Characters Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "0000000000000000" into the Phone1 field
And I type "1111111111111111" into the Phone2 field
And I click the Save button
Then I validate the Error Phone1 text is "Must consist of up to 15 numeric characters"
And I validate the Error Phone2 text is "Must consist of up to 15 numeric characters"

Scenario: TC1278: My Account - Edit Account - Phone Missing Character Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "00" into the Phone1 field
And I type "11" into the Phone2 field
And I click the Save button
Then I validate the Error Phone1 text is "Must consist of up to 15 numeric characters"
And I validate the Error Phone2 text is "Must consist of up to 15 numeric characters"

Scenario: TC1279: My Account - Edit Account - Phone Special Character Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "& ^ $" into the Phone1 field
And I type "& ^ $" into the Phone2 field
And I click the Save button
Then I validate the Error Phone1 text is "Must consist of up to 15 numeric characters"
And I validate the Error Phone2 text is "Must consist of up to 15 numeric characters"

Scenario: TC1282: My Account - Edit Account - Text Message Format Error
Given I am logged in as a "Admin" user
When I click the My Account link
And I click the Edit button
And I type "8015551234 @domain.com" into the Text Message1 field
And I type "801555123411111@domain@domain.com" into the Text Message2 field
And I click the Save button
Then I validate the Error Text1 text is "Incorrect format (8015551212@tmomail.com)"
And I validate the Error Text2 text is "Incorrect format (8015551212@tmomail.com)"

Scenario: TC1280: My Account - Edit Account - Save Button
Given I am logged in as a "Admin" user
When I click the My Account link
And I save the Information drop down as origInfodd
And I save the Warning drop down as origWarndd
And I save the Critical drop down as origCritdd
And I save the Map Type drop down as origMapTypedd
And I save the Map Layers drop down as origMapLayersdd
And I save the Locale drop down as origLocaledd
And I save the Measurement drop down as origMeasurementdd
And I save the Fuel Efficiency Ratio drop down as origFERdd
And I save the Email1 textfield as origEmail1
And I save the Email2 textfield as origEmail2
And I save the Phone1 textfield as origPhone1
And I save the Phone2 textfield as origPhone2
And I save the Text Message1 textfield as origTextMessage1
And I save the Text Message2 textfield as origTextMessage2
And I click the Edit button
And I select "E-mail 1" from the Information drop down
And I select "E-mail 2" from the Warning drop down
And I select "Phone 1" from the Critical drop down
And I select "Hyb (Hybrid)" from the Map Type drop down
And I check "Test Maps" from the Map Layers drop down
And I select "Deutsch" from the Locale drop down
And I select "Metric" from the Measurement drop down
And I select "Kilometers Per Liter" from the Fuel Efficiency Ratio drop down
And I type "test@test.com" into the Email1 textfield
And I type "test2@test.com" into the Email2 textfield
And I type "0000000000" into the Phone1 textfield
And I type "1111111111" into the Phone2 textfield
And I type "8015551212@tmomail.com" into the Text Message1 textfield
And I type "8015551213@tmomail.com" into the Text Message2 textfield
And I click the Save button
Then I validate the text origInfodd is not in the Information drop down
And I validate the text origWarndd is not in the Warning drop down
And I validate the text origCritdd is not in the Critical drop down
And I validate the text origMapTypedd is not in the Map Type drop down
And I validate the text origMapLayersdd is not in the Map Layers drop down
And I validate the text origLocaledd is not in the Locale drop down
And I validate the text origMeasurementdd is not in the Measurement drop down
And I validate the text origFERdd is not in the Fuel Efficiency Ratio drop down
And I validate the text origEmail1 is not in the Email1 textfield
And I validate the text origEmail2 is not in the Email2 textfield
And I validate the text origPhone1 is not in the Phone1 textfield
And I validate the text origPhone2 is not in the Phone2 textfield
And I validate the text origTextMessage1 is not in the Text Message1 textfield
And I validate the text origTextMessage2 is not in the Text Message2 textfield
And I click the Edit button
And I set the Information drop down as origInfodd
And I set the Warning drop down as origWarndd
And I set the Critical drop down as origCritdd
And I set the Map Type drop down as origMapTypedd
And I set the Map Layers drop down as origMapLayersdd
And I set the Locale drop down as origLocaledd
And I set the Measurement drop down as origMeasurementdd
And I set the Fuel Efficiency Ratio drop down as origFERdd
And I set the Email1 textfield as origEmail1
And I set the Email2 textfield as origEmail2
And I set the Phone1 textfield as origPhone1
And I set the Phone2 textfield as origPhone2
And I set the Text Message1 textfield as origTextMessage1
And I set the Text Message2 textfield as origTextMessage2
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
And I save the Information drop down as origInfodd
And I save the Warning drop down as origWarndd
And I save the Critical drop down as origCritdd
And I save the Map Type drop down as origMapTypedd
And I save the Map Layers drop down as origMapLayersdd
And I save the Locale drop down as origLocaledd
And I save the Measurement drop down as origMeasurementdd
And I save the Fuel Efficiency Ratio drop down as origFERdd
And I save the Email1 textfield as origEmail1
And I save the Email2 textfield as origEmail2
And I save the Phone1 textfield as origPhone1
And I save the Phone2 textfield as origPhone2
And I save the Text Message1 textfield as origTextMessage1
And I save the Text Message2 textfield as origTextMessage2
And I click the Edit button
And I select "E-mail 1" from the Information drop down
And I select "E-mail 2" from the Warning drop down
And I select "Phone 1" from the Critical drop down
And I select "Hyb (Hybrid)" from the Map Type drop down
And I check "Test Maps" from the Map Layers drop down
And I select "Deutsch" from the Locale drop down
And I select "Metric" from the Measurement drop down
And I select "Kilometers Per Liter" from the Fuel Efficiency Ratio drop down
And I type "test@test.com" into the Email1 textfield
And I type "test2@test.com" into the Email2 textfield
And I type "0000000000" into the Phone1 textfield
And I type "1111111111" into the Phone2 textfield
And I type "8015551212@tmomail.com" into the Text Message1 textfield
And I type "8015551213@tmomail.com" into the Text Message2 textfield
And I click the Cancel button
Then I validate the text origInfodd is in the Information drop down
And I validate the text origWarndd is in the Warning drop down
And I validate the text origCritdd is in the Critical drop down
And I validate the text origMapTypedd is in the Map Type drop down
And I validate the text origMapLayersdd is in the Map Layers drop down
And I validate the text origLocaledd is in the Locale drop down
And I validate the text origMeasurementdd is in the Measurement drop down
And I validate the text origFERdd is in the Fuel Efficiency Ratio drop down
And I validate the text origEmail1 is in the Email1 textfield
And I validate the text origEmail2 is in the Email2 textfield
And I validate the text origPhone1 is in the Phone1 textfield
And I validate the text origPhone2 is in the Phone2 textfield
And I validate the text origTextMessage1 is in the Text Message1 textfield
And I validate the text origTextMessage2 is in the Text Message2 textfield


