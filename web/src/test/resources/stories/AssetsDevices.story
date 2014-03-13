Scenario: Assets - Devices - Main UI
Given I am logged in
When I click the Top Devices link
Then I validate I am on the Assets Devices page
And I validate the Records Per Page Label text is present
And I validate the Records Per Page dropdown is present
And I validate the Search Label text is present
And I validate the Search textfield is present
And I validate the Show Hide Columns link is present
And I validate the Select All checkbox is present
And I validate the Sort By Device link is present
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Product link is present
And I validate the Sort By IMEI link is present
And I validate the Sort By Alternate IMEI link is present
And I validate the Sort By SIM Card link is present
And I validate the Sort By Serial Number link is not present
And I validate the Sort By Phone link is not present
And I validate the Sort By Status link is not present
And I validate the Sort By MCM ID link is not present
And I validate the Previous link is not present
And I validate the Next link is present
And I validate the Entries text is present
And I validate the Entries text contains "Showing 1 to 10 of"
And I validate the Entries text contains "entries"
And I validate the Entries text does not contain "filtered"
And I validate the Edit button is not visible
And I validate the Cancel button is not visible
And I validate the Save button is not visible
And I validate the Delete button is not visible
And I validate the Device Label text is present
And I validate the Product Label text is present
And I validate the MCM ID Label text is present
And I validate the IMEI Label text is present
And I validate the SIM Card Label text is present
And I validate the Phone Label text is present
And I validate the Alternate IMEI Label text is present
And I validate the Serial Number Label text is present
And I validate the Profile Label text is present
And I validate the Status Label text is present
And I validate the Activated Label text is present
And I validate the Assignment Label text is present
And I validate the Assigned Vehicle Label text is present
And I validate the Status dropdown is not visible
And I validate the Assigned Vehicle dropdown is not visible
And I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status text is ""
And I validate the Activated text is ""
And I validate the Assigned Vehicle text is ""

Scenario: Assets - Devices - Edit button UI
Given I am logged in
When I click the Top Devices link
Then I validate I am on the Assets Devices page
And I click the 1st Row of the Device Entry link
And I validate the Edit button is visible
And I validate the Delete button is visible
And I validate the Cancel button is not visible
And I validate the Save button is not visible
And I click the Edit button
And I validate the Edit button is not visible
And I validate the Delete button is not visible
And I validate the Cancel button is visible
And I validate the Save button is visible
And I validate the Status dropdown is visible
And I validate the Assigned Vehicle dropdown is visible
And I validate the Assigned Vehicle dropdown is not ""
And I click the Cancel button
And I validate the Edit button is visible
And I validate the Delete button is visible
And I validate the Cancel button is not visible
And I validate the Save button is not visible
And I validate the Status dropdown is not visible
And I validate the Assigned Vehicle dropdown is not visible

Scenario: Assets - Devices - Show Hide Columns link UI
Given I am logged in
When I click the Top Devices link
Then I validate I am on the Assets Devices page
And I click the Show Hide Columns link
And I validate the Vehicle ID Checkbox Label text is present
And I validate the Product Checkbox Label text is present
And I validate the IMEI Checkbox Label text is present
And I validate the Alternate IMEI Checkbox Label text is present
And I validate the SIM Card Checkbox Label text is present
And I validate the Serial Number Checkbox Label text is present
And I validate the Phone Checkbox Label text is present
And I validate the Status Checkbox Label text is present
And I validate the MCM ID Checkbox Label text is present
And I validate the Vehicle ID checkbox is present
And I validate the Vehicle ID checkbox is checked
And I validate the Product checkbox is present
And I validate the Product checkbox is checked
And I validate the IMEI checkbox is present
And I validate the IMEI checkbox is checked
And I validate the Alternate IMEI checkbox is present
And I validate the Alternate IMEI checkbox is checked
And I validate the SIM Card checkbox is present
And I validate the SIM Card checkbox is checked
And I validate the Serial Number checkbox is present
And I validate the Serial Number checkbox is not checked
And I validate the Phone checkbox is present
And I validate the Phone checkbox is not checked
And I validate the Status checkbox is present
And I validate the Status checkbox is not checked
And I validate the MCM ID checkbox is present
And I validate the MCM ID checkbox is not checked
And I validate the Restore Original link is present

Scenario: Assets - Devices - Default Records dropdowns are set to 10
Given I am logged in
When I click the Top Devices link
Then I validate the Records Per Page dropdown is "10"

Scenario: Assets - Devices - Records per page test
Given I am logged in
When I click the Top Devices link
And I select "10" from the Records Per Page dropdown 
Then I validate the 11th Row of the Device Entry link is not present
And I validate the Entries text contains "Showing 1 to 10 of"
And I select "25" from the Records Per Page dropdown 
And I validate the 26th Row of the Device Entry link is not present
And I validate the Entries text contains "Showing 1 to 25 of"
And I select "50" from the Records Per Page dropdown 
And I validate the 51st Row of the Device Entry link is not present
And I validate the Entries text contains "Showing 1 to 50 of"
And I select "100" from the Records Per Page dropdown 
And I validate the 101st Row of the Device Entry link is not present
And I validate the Entries text contains "Showing 1 to 100 of"

Scenario: Assets - Devices - Select All checkbox functionality
Given I am logged in
When I click the Top Devices link
And I click the Select All checkbox
Then I validate the 1st Row of the Entry checkbox is checked
And I validate the 2nd Row of the Entry checkbox is checked
And I validate the 3rd Row of the Entry checkbox is checked
And I validate the 4th Row of the Entry checkbox is checked
And I validate the 5th Row of the Entry checkbox is checked
And I validate the 6th Row of the Entry checkbox is checked
And I validate the 7th Row of the Entry checkbox is checked
And I validate the 8th Row of the Entry checkbox is checked
And I validate the 9th Row of the Entry checkbox is checked
And I validate the 10th Row of the Entry checkbox is checked
And I select "25" from the Records Per Page dropdown
And I validate the 1st Row of the Entry checkbox is checked
And I validate the 2nd Row of the Entry checkbox is checked
And I validate the 3rd Row of the Entry checkbox is checked
And I validate the 4th Row of the Entry checkbox is checked
And I validate the 5th Row of the Entry checkbox is checked
And I validate the 6th Row of the Entry checkbox is checked
And I validate the 7th Row of the Entry checkbox is checked
And I validate the 8th Row of the Entry checkbox is checked
And I validate the 9th Row of the Entry checkbox is checked
And I validate the 10th Row of the Entry checkbox is checked
And I validate the 11th Row of the Entry checkbox is not checked
And I click the Select All checkbox
And I validate the 1st Row of the Entry checkbox is checked
And I validate the 2nd Row of the Entry checkbox is checked
And I validate the 3rd Row of the Entry checkbox is checked
And I validate the 4th Row of the Entry checkbox is checked
And I validate the 5th Row of the Entry checkbox is checked
And I validate the 6th Row of the Entry checkbox is checked
And I validate the 7th Row of the Entry checkbox is checked
And I validate the 8th Row of the Entry checkbox is checked
And I validate the 9th Row of the Entry checkbox is checked
And I validate the 10th Row of the Entry checkbox is checked
And I validate the 11th Row of the Entry checkbox is checked
And I validate the 12th Row of the Entry checkbox is checked
And I validate the 13th Row of the Entry checkbox is checked
And I validate the 14th Row of the Entry checkbox is checked
And I validate the 15th Row of the Entry checkbox is checked
And I validate the 16th Row of the Entry checkbox is checked
And I validate the 17th Row of the Entry checkbox is checked
And I validate the 18th Row of the Entry checkbox is checked
And I validate the 19th Row of the Entry checkbox is checked
And I validate the 20th Row of the Entry checkbox is checked
And I validate the 21st Row of the Entry checkbox is checked
And I validate the 22nd Row of the Entry checkbox is checked
And I validate the 23rd Row of the Entry checkbox is checked
And I validate the 24th Row of the Entry checkbox is checked
And I validate the 25th Row of the Entry checkbox is checked
And I select "50" from the Records Per Page dropdown
And I validate the 1st Row of the Entry checkbox is checked
And I validate the 25th Row of the Entry checkbox is checked
And I validate the 26th Row of the Entry checkbox is not checked
And I validate the 50th Row of the Entry checkbox is not checked
And I click the Select All checkbox
And I validate the 1st Row of the Entry checkbox is checked
And I validate the 2nd Row of the Entry checkbox is checked
And I validate the 3rd Row of the Entry checkbox is checked
And I validate the 4th Row of the Entry checkbox is checked
And I validate the 5th Row of the Entry checkbox is checked
And I validate the 6th Row of the Entry checkbox is checked
And I validate the 7th Row of the Entry checkbox is checked
And I validate the 8th Row of the Entry checkbox is checked
And I validate the 9th Row of the Entry checkbox is checked
And I validate the 10th Row of the Entry checkbox is checked
And I validate the 11th Row of the Entry checkbox is checked
And I validate the 12th Row of the Entry checkbox is checked
And I validate the 13th Row of the Entry checkbox is checked
And I validate the 14th Row of the Entry checkbox is checked
And I validate the 15th Row of the Entry checkbox is checked
And I validate the 16th Row of the Entry checkbox is checked
And I validate the 17th Row of the Entry checkbox is checked
And I validate the 18th Row of the Entry checkbox is checked
And I validate the 19th Row of the Entry checkbox is checked
And I validate the 20th Row of the Entry checkbox is checked
And I validate the 21st Row of the Entry checkbox is checked
And I validate the 22nd Row of the Entry checkbox is checked
And I validate the 23rd Row of the Entry checkbox is checked
And I validate the 24th Row of the Entry checkbox is checked
And I validate the 25th Row of the Entry checkbox is checked
And I validate the 26th Row of the Entry checkbox is checked
And I validate the 27th Row of the Entry checkbox is checked
And I validate the 28th Row of the Entry checkbox is checked
And I validate the 29th Row of the Entry checkbox is checked
And I validate the 30th Row of the Entry checkbox is checked
And I validate the 31st Row of the Entry checkbox is checked
And I validate the 32nd Row of the Entry checkbox is checked
And I validate the 33rd Row of the Entry checkbox is checked
And I validate the 34th Row of the Entry checkbox is checked
And I validate the 35th Row of the Entry checkbox is checked
And I validate the 36th Row of the Entry checkbox is checked
And I validate the 37th Row of the Entry checkbox is checked
And I validate the 38th Row of the Entry checkbox is checked
And I validate the 39th Row of the Entry checkbox is checked
And I validate the 40th Row of the Entry checkbox is checked
And I validate the 41st Row of the Entry checkbox is checked
And I validate the 42nd Row of the Entry checkbox is checked
And I validate the 43rd Row of the Entry checkbox is checked
And I validate the 44th Row of the Entry checkbox is checked
And I validate the 45th Row of the Entry checkbox is checked
And I validate the 46th Row of the Entry checkbox is checked
And I validate the 47th Row of the Entry checkbox is checked
And I validate the 48th Row of the Entry checkbox is checked
And I validate the 49th Row of the Entry checkbox is checked
And I validate the 50th Row of the Entry checkbox is checked
And I select "100" from the Records Per Page dropdown
And I validate the 1st Row of the Entry checkbox is checked
And I validate the 26th Row of the Entry checkbox is checked
And I validate the 50th Row of the Entry checkbox is checked
And I validate the 51st Row of the Entry checkbox is not checked
And I validate the 100th Row of the Entry checkbox is not checked
And I click the Select All checkbox
And I validate the 1st Row of the Entry checkbox is checked
And I validate the 2nd Row of the Entry checkbox is checked
And I validate the 3rd Row of the Entry checkbox is checked
And I validate the 4th Row of the Entry checkbox is checked
And I validate the 5th Row of the Entry checkbox is checked
And I validate the 6th Row of the Entry checkbox is checked
And I validate the 7th Row of the Entry checkbox is checked
And I validate the 8th Row of the Entry checkbox is checked
And I validate the 9th Row of the Entry checkbox is checked
And I validate the 10th Row of the Entry checkbox is checked
And I validate the 11th Row of the Entry checkbox is checked
And I validate the 12th Row of the Entry checkbox is checked
And I validate the 13th Row of the Entry checkbox is checked
And I validate the 14th Row of the Entry checkbox is checked
And I validate the 15th Row of the Entry checkbox is checked
And I validate the 16th Row of the Entry checkbox is checked
And I validate the 17th Row of the Entry checkbox is checked
And I validate the 18th Row of the Entry checkbox is checked
And I validate the 19th Row of the Entry checkbox is checked
And I validate the 20th Row of the Entry checkbox is checked
And I validate the 21st Row of the Entry checkbox is checked
And I validate the 22nd Row of the Entry checkbox is checked
And I validate the 23rd Row of the Entry checkbox is checked
And I validate the 24th Row of the Entry checkbox is checked
And I validate the 25th Row of the Entry checkbox is checked
And I validate the 26th Row of the Entry checkbox is checked
And I validate the 27th Row of the Entry checkbox is checked
And I validate the 28th Row of the Entry checkbox is checked
And I validate the 29th Row of the Entry checkbox is checked
And I validate the 30th Row of the Entry checkbox is checked
And I validate the 31st Row of the Entry checkbox is checked
And I validate the 32nd Row of the Entry checkbox is checked
And I validate the 33rd Row of the Entry checkbox is checked
And I validate the 34th Row of the Entry checkbox is checked
And I validate the 35th Row of the Entry checkbox is checked
And I validate the 36th Row of the Entry checkbox is checked
And I validate the 37th Row of the Entry checkbox is checked
And I validate the 38th Row of the Entry checkbox is checked
And I validate the 39th Row of the Entry checkbox is checked
And I validate the 40th Row of the Entry checkbox is checked
And I validate the 41st Row of the Entry checkbox is checked
And I validate the 42nd Row of the Entry checkbox is checked
And I validate the 43rd Row of the Entry checkbox is checked
And I validate the 44th Row of the Entry checkbox is checked
And I validate the 45th Row of the Entry checkbox is checked
And I validate the 46th Row of the Entry checkbox is checked
And I validate the 47th Row of the Entry checkbox is checked
And I validate the 48th Row of the Entry checkbox is checked
And I validate the 49th Row of the Entry checkbox is checked
And I validate the 50th Row of the Entry checkbox is checked
And I validate the 51st Row of the Entry checkbox is checked
And I validate the 52nd Row of the Entry checkbox is checked
And I validate the 53rd Row of the Entry checkbox is checked
And I validate the 54th Row of the Entry checkbox is checked
And I validate the 55th Row of the Entry checkbox is checked
And I validate the 56th Row of the Entry checkbox is checked
And I validate the 57th Row of the Entry checkbox is checked
And I validate the 58th Row of the Entry checkbox is checked
And I validate the 59th Row of the Entry checkbox is checked
And I validate the 60th Row of the Entry checkbox is checked
And I validate the 61st Row of the Entry checkbox is checked
And I validate the 62nd Row of the Entry checkbox is checked
And I validate the 63rd Row of the Entry checkbox is checked
And I validate the 64th Row of the Entry checkbox is checked
And I validate the 65th Row of the Entry checkbox is checked
And I validate the 66th Row of the Entry checkbox is checked
And I validate the 67th Row of the Entry checkbox is checked
And I validate the 68th Row of the Entry checkbox is checked
And I validate the 69th Row of the Entry checkbox is checked
And I validate the 70th Row of the Entry checkbox is checked
And I validate the 71st Row of the Entry checkbox is checked
And I validate the 72nd Row of the Entry checkbox is checked
And I validate the 73rd Row of the Entry checkbox is checked
And I validate the 74th Row of the Entry checkbox is checked
And I validate the 75th Row of the Entry checkbox is checked
And I validate the 76th Row of the Entry checkbox is checked
And I validate the 77th Row of the Entry checkbox is checked
And I validate the 78th Row of the Entry checkbox is checked
And I validate the 79th Row of the Entry checkbox is checked
And I validate the 80th Row of the Entry checkbox is checked
And I validate the 81st Row of the Entry checkbox is checked
And I validate the 82nd Row of the Entry checkbox is checked
And I validate the 83rd Row of the Entry checkbox is checked
And I validate the 84th Row of the Entry checkbox is checked
And I validate the 85th Row of the Entry checkbox is checked
And I validate the 86th Row of the Entry checkbox is checked
And I validate the 87th Row of the Entry checkbox is checked
And I validate the 88th Row of the Entry checkbox is checked
And I validate the 89th Row of the Entry checkbox is checked
And I validate the 90th Row of the Entry checkbox is checked
And I validate the 91st Row of the Entry checkbox is checked
And I validate the 92nd Row of the Entry checkbox is checked
And I validate the 93rd Row of the Entry checkbox is checked
And I validate the 94th Row of the Entry checkbox is checked
And I validate the 95th Row of the Entry checkbox is checked
And I validate the 96th Row of the Entry checkbox is checked
And I validate the 97th Row of the Entry checkbox is checked
And I validate the 98th Row of the Entry checkbox is checked
And I validate the 99th Row of the Entry checkbox is checked
And I validate the 100th Row of the Entry checkbox is checked

Scenario: Assets - Devices - Empty Search
Given I am logged in
When I click the Top Devices link
And I type "randomstringthatwillnotcomeup" into the Search textfield
Then I validate the No Matching Records text is "No matching records found"
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"
And I validate the Previous link is not present
And I validate the Next link is not present
And I validate the Previous Disabled link is present
And I validate the Next Disabled link is present

Scenario: Assets - Devices - Filtered Entries Text Functions Correctly
Given I am logged in
When I click the Top Devices link
And I type "FilteredEntry" into the Search textfield
Then I validate the Entries text contains "Showing 1 to 3 of 3 entries"
And I validate the Entries text contains "filtered from"
And I validate the Entries text contains "total entries"

Scenario: Assets - Devices - Search Filter
Given I am logged in
When I click the Top Devices link
And I type "SEARCHTEXT" into the Search textfield
And I click the Top Devices link
Then I validate the Search textfield is "SEARCHTEXT"

Scenario: Assets - Devices - Search All Fields (requires specific devices are present in the table)
Given I am logged in
When I click the Top Devices link
And I click the Show Hide Columns link
And I check the Vehicle ID checkbox
And I check the Product checkbox
And I check the IMEI checkbox
And I check the Alternate IMEI checkbox
And I check the SIM Card checkbox
And I check the Serial Number checkbox
And I check the Phone checkbox
And I check the Status checkbox
And I check the MCM ID checkbox
And I click the Top Devices link
And I type "MCM990098" into the Search textfield
And I click the Sort By Device link
Then I validate the 1st Row of the Device Entry link is "MCM990098"
And I click the Sort By Device link
And I validate the 1st Row of the Device Entry link is "MCM990098"
And I type "autogen68" into the Search textfield
And I click the Sort By Vehicle ID link
And I validate the 1st Row of the Vehicle ID Entry link is "autogen68"
And I click the Sort By Vehicle ID link
And I validate the 1st Row of the Vehicle ID Entry link is "autogen68"
And I type "WAYSMART" into the Search textfield
And I click the Sort By Product link
And I validate the 1st Row of the Product Entry link is "WAYSMART"
And I click the Sort By Product link
And I validate the 1st Row of the Product Entry link is "WAYSMART"
And I type "999900000000008" into the Search textfield
And I click the Sort By IMEI link
And I validate the 1st Row of the IMEI Entry link is "999900000000008"
And I click the Sort By IMEI link
And I validate the 1st Row of the IMEI Entry link is "999900000000008"
And I type "Alternate IMEI" into the Search textfield
And I click the Sort By Alternate IMEI link
And I validate the 1st Row of the Alternate IMEI Entry link is "Alternate IMEI"
And I click the Sort By Alternate IMEI link
And I validate the 1st Row of the Alternate IMEI Entry link is "Alternate IMEI"
And I type "9999069911201002944" into the Search textfield
And I click the Sort By SIM Card link
And I validate the 1st Row of the SIM Card Entry link is "9999069911201002944"
And I click the Sort By SIM Card link
And I validate the 1st Row of the SIM Card Entry link is "9999069911201002944"
And I type "WATERWELLD" into the Search textfield
And I click the Sort By Serial Number link
And I validate the 1st Row of the Serial Number Entry link is "WATERWELLD"
And I click the Sort By Serial Number link
And I validate the 1st Row of the Serial Number Entry link is "WATERWELLD"
And I type "8885559999" into the Search textfield
And I click the Sort By Phone link
And I validate the 1st Row of the Phone Entry link is "8885559999"
And I click the Sort By Phone link
And I validate the 1st Row of the Phone Entry link is "8885559999"
And I type "AUTOMATION850" into the Search textfield
And I click the Sort By MCM ID link
And I validate the 1st Row of the MCM ID Entry link is "AUTOMATION850"
And I click the Sort By MCM ID link
And I validate the 1st Row of the MCM ID Entry link is "AUTOMATION850"

Scenario: Assets - Devices - Show Hide Columns UI Interaction
Given I am logged in
When I click the Top Devices link
And I click the Show Hide Columns link
And I uncheck the Vehicle ID checkbox
Then I validate the Sort By Vehicle ID link is not present
And I check the Vehicle ID checkbox
And I validate the Sort By Vehicle ID link is present
When I click the Show Hide Columns link
And I uncheck the Product checkbox
Then I validate the Sort By Product link is not present
And I check the Product checkbox
And I validate the Sort By Product link is present
When I click the Show Hide Columns link
And I uncheck the IMEI checkbox
Then I validate the Sort By IMEI link is not present
And I check the IMEI checkbox
And I validate the Sort By IMEI link is present
When I click the Show Hide Columns link
And I uncheck the Alternate IMEI checkbox
Then I validate the Sort By Alternate IMEI link is not present
And I check the Alternate IMEI checkbox
And I validate the Sort By Alternate IMEI link is present
When I click the Show Hide Columns link
And I uncheck the SIM Card checkbox
Then I validate the Sort By SIM Card link is not present
And I check the SIM Card checkbox
And I validate the Sort By SIM Card link is present
When I click the Show Hide Columns link
And I uncheck the Serial Number checkbox
Then I validate the Sort By Serial Number link is not present
And I check the Serial Number checkbox
And I validate the Sort By Serial Number link is present
When I click the Show Hide Columns link
And I uncheck the Phone checkbox
Then I validate the Sort By Phone link is not present
And I check the Phone checkbox
And I validate the Sort By Phone link is present
When I click the Show Hide Columns link
And I uncheck the Status checkbox
Then I validate the Sort By Status link is not present
And I check the Status checkbox
And I validate the Sort By Status link is present
When I click the Show Hide Columns link
And I uncheck the MCM ID checkbox
Then I validate the Sort By MCM ID link is not present
And I check the MCM ID checkbox
And I validate the Sort By MCM ID link is present
And I uncheck the Vehicle ID checkbox
And I uncheck the Product checkbox
And I uncheck the IMEI checkbox
And I uncheck the Alternate IMEI checkbox
And I uncheck the SIM Card checkbox
And I uncheck the Serial Number checkbox
And I uncheck the Phone checkbox
And I uncheck the Status checkbox
And I uncheck the MCM ID checkbox
And I click the Restore Original link
And I validate the Sort By Vehicle ID link is present
And I validate the Sort By Product link is present
And I validate the Sort By IMEI link is present
And I validate the Sort By Alternate IMEI link is present
And I validate the Sort By SIM Card link is present
And I validate the Sort By Serial Number link is present
And I validate the Sort By Phone link is present
And I validate the Sort By Status link is present
And I validate the Sort By MCM ID link is present

Scenario: Assets - Devices - Bookmark Entry 
Given I am logged in
When I click the Top Devices link
And I bookmark the page
And I click the Logout link
And I click the bookmark I just added
And I log back in
Then I validate I am on the Assets Devices page

Scenario: Assets - Devices - Table Properties (Removing test till it's implemented)
!-- Given I am logged in
!-- When I click the Top Devices link
!-- And I click the Show Hide Columns link
!-- And I check the Vin checkbox
!-- And I check the License checkbox
!-- And I check the State checkbox
!-- And I check the Year checkbox
!-- And I check the Make checkbox
!-- And I check the Model checkbox
!-- And I check the Color checkbox
!-- And I check the Weight checkbox
!-- And I check the Odometer checkbox
!-- And I click the Sort By Trailer link
!-- And I click the Sort By Team link
!-- And I click the Sort By Device link
!-- And I click the Sort By Vehicle link
!-- And I click the Sort By Driver link
!-- And I click the Sort By Status link
!-- And I click the Sort By Vin link
!-- And I click the Sort By License link
!-- And I click the Sort By State link
!-- And I click the Sort By Year link
!-- And I click the Sort By Make link
!-- And I click the Sort By Model link
!-- And I click the Sort By Color link
!-- And I click the Sort By Weight link
!-- And I click the Sort By Odometer link

Scenario: Assets - Devices - Show/Hide Columns - Subsequent Session Retention
Given I am logged in
When I click the Top Devices link
And I click the Show Hide Columns link
And I uncheck the Vehicle ID checkbox
And I click the Show Hide Columns link
Then I validate the Sort By Vehicle ID link is not present
And I click the Logout link
Given I am logged in
And I click the Top Devices link
Then I validate the Sort By Vehicle ID link is not present
And I click the Show Hide Columns link
And I check the Vehicle ID checkbox
And I click the Show Hide Columns link
And I validate the Sort By Vehicle ID link is present

Scenario: Assets - Devices - Select A Row, Click Edit, Then Select Another Row
Given I am logged in
When I click the Top Devices link
And I click the Show Hide Columns link
And I check the Vehicle ID checkbox
And I check the Product checkbox
And I check the IMEI checkbox
And I check the Alternate IMEI checkbox
And I check the SIM Card checkbox
And I check the Serial Number checkbox
And I check the Phone checkbox
And I check the Status checkbox
And I check the MCM ID checkbox
And I click the Top Devices link
And I click the 1st Row of the Device Entry link
And I click the Edit button
And I click the 2nd Row of the Device Entry link
Then I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status dropdown is "NEW"
And I validate the Activated text is ""
And I validate the Assigned Vehicle dropdown is disabled

Scenario: Assets - Devices - Select And Deselect A Row
Given I am logged in
When I click the Top Devices link
And I click the 1st Row of the Device Entry link
And I control click the 1st Row of the Device Entry link
Then I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status dropdown is ""
And I validate the Activated text is ""
And I validate the Assigned Vehicle text is ""

Scenario: Assets - Devices - Select A Row, Select Edit, Then Cancel
Given I am logged in
When I click the Top Devices link
And I click the Show Hide Columns link
And I check the Vehicle ID checkbox
And I check the Product checkbox
And I check the IMEI checkbox
And I check the Alternate IMEI checkbox
And I check the SIM Card checkbox
And I check the Serial Number checkbox
And I check the Phone checkbox
And I check the Status checkbox
And I check the MCM ID checkbox
And I click the Top Devices link
And I click the 1st Row of the Device Entry link
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 1st Row of the Product Entry link as SAVEDPRODUCT
And I save the 1st Row of the IMEI Entry link as SAVEDIMEI
And I save the 1st Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 1st Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 1st Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 1st Row of the Phone Entry link as SAVEDPHONE
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the MCM ID Entry link as SAVEDMCMID
And I click the Edit button
And I click the Cancel button
Then I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle ID Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Product Entry link is SAVEDPRODUCT
And I validate the 1st Row of the IMEI Entry link is SAVEDIMEI
And I validate the 1st Row of the Alternate IMEI Entry link is SAVEDALTERNATEIMEI
And I validate the 1st Row of the SIM Card Entry link is SAVEDSIMCARD
And I validate the 1st Row of the Serial Number Entry link is SAVEDSERIALNUMBER
And I validate the 1st Row of the Phone Entry link is SAVEDPHONE
And I validate the 1st Row of the Status Entry link is SAVEDSTATUS
And I validate the 1st Row of the MCM ID Entry link is SAVEDMCMID

Scenario: Assets - Devices - Select A Row, Select Edit, Deselect The Row
Given I am logged in
When I click the Top Devices link
And I click the 1st Row of the Device Entry link
And I click the Edit button
And I control click the 1st Row of the Device Entry link
Then I validate the Save button is not visible
And I validate the Edit button is not visible
And I validate the Cancel button is not visible
And I validate the Delete button is not visible
And I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status dropdown is ""
And I validate the Activated text is ""
And I validate the Assigned Vehicle text is ""

Scenario: Assets - Devices - Delete Device Test (cancel button) (need to use DeviceRestController to create the device that will be deleted)
!-- Given I am logged in
!-- When I click the Top Devices link
!-- And I click the Show Hide Columns link
!-- And I check the Vehicle ID checkbox
!-- And I check the Product checkbox
!-- And I check the IMEI checkbox
!-- And I check the Alternate IMEI checkbox
!-- And I check the SIM Card checkbox
!-- And I check the Serial Number checkbox
!-- And I check the Phone checkbox
!-- And I check the Status checkbox
!-- And I check the MCM ID checkbox
!-- And I click the Top Devices link
!-- And I type "ASSETDELETEDEVICETEST" into the Search textfield
!-- And I click the 1st Row of the Device Entry link
!-- And I save the 1st Row of the Device Entry link as SAVEDDEVICE
!-- And I click the Edit button
!-- And I select "DELETED" from the Status dropdown
!-- And I click the Cancel button
!-- Then I validate the 1st Row of the Device Entry link is SAVEDDEVICE

Scenario: Assets - Devices - Delete Device Test (save button) (need to use DeviceRestController to create the device that will be deleted)
!-- Given I am logged in
!-- When I click the Top Devices link
!-- And I click the Show Hide Columns link
!-- And I check the Vehicle ID checkbox
!-- And I check the Product checkbox
!-- And I check the IMEI checkbox
!-- And I check the Alternate IMEI checkbox
!-- And I check the SIM Card checkbox
!-- And I check the Serial Number checkbox
!-- And I check the Phone checkbox
!-- And I check the Status checkbox
!-- And I check the MCM ID checkbox
!-- And I click the Top Devices link
!-- And I type "ASSETDELETEDEVICETEST" into the Search textfield
!-- And I click the 1st Row of the Device Entry link
!-- And I save the 1st Row of the Device Entry link as SAVEDDEVICE
!-- And I click the Edit button
!-- And I select "DELETED" from the Status dropdown
!-- And I click the Save button
!-- Then I validate the 1st Row of the Device Entry link is SAVEDDEVICE

Scenario: Assets - Devices - Delete two devices back to back (need to use DeviceRestController to create the device that will be deleted)
!-- Given I am logged in
!-- When I click the Top Devices link
!-- And I type "ASSETDELDEVICETEST1" into the Search textfield
!-- And I click the 1st Row of the Device Entry link
!-- And I click the Edit button
!-- And I select "DELETED" from the Status dropdown
!-- And I click the Save button
!-- Then I validate the 1st Row of the Device Entry link is not present
!-- And I type "ASSETDELDEVICETEST2" into the Search textfield
!-- And I click the 1st Row of the Device Entry link
!-- And I click the Edit button
!-- And I select "DELETED" from the Status dropdown
!-- And I click the Save button
!-- And I validate the 1st Row of the Device Entry link is not present

Scenario: Assets - Devices - Delete entry then edit an entry (need to use DeviceRestController to create the device that will be deleted)
!-- Given I am logged in
!-- When I click the Top Devices link
!-- And I click the Show Hide Columns link
!-- And I check the Vehicle ID checkbox
!-- And I check the Product checkbox
!-- And I check the IMEI checkbox
!-- And I check the Alternate IMEI checkbox
!-- And I check the SIM Card checkbox
!-- And I check the Serial Number checkbox
!-- And I check the Phone checkbox
!-- And I check the Status checkbox
!-- And I check the MCM ID checkbox
!-- And I click the Top Devices link
!-- And I type "DELETEANDEDITDEVICE" into the Search textfield
!-- And I click the 1st Row of the Device Entry link
!-- And I click the Edit button
!-- And I select "DELETED" from the Status dropdown
!-- And I click the Save button
!-- Then I validate the 1st Row of the Device Entry link is not present
!-- And I type "" into the Search textfield
!-- And I click the 1st Row of the Entry checkbox
!-- And I save the 1st Row of the Device Entry link as SAVEDDEVICE
!-- And I save the 1st Row of the Vehicle ID Entry link as SAVEDVEHICLE
!-- And I save the 1st Row of the Product Entry link as SAVEDPRODUCT
!-- And I save the 1st Row of the IMEI Entry link as SAVEDIMEI
!-- And I save the 1st Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
!-- And I save the 1st Row of the SIM Card Entry link as SAVEDSIMCARD
!-- And I save the 1st Row of the Serial Number Entry link as SAVEDSERIALNUMBER
!-- And I save the 1st Row of the Phone Entry link as SAVEDPHONE
!-- And I save the 1st Row of the Status Entry link as SAVEDSTATUS
!-- And I save the 1st Row of the MCM ID Entry link as SAVEDMCMID
!-- And I validate the Device text is SAVEDDEVICE
!-- And I validate the Product text is SAVEDPRODUCT
!-- And I validate the MCM ID text is SAVEDMCMID
!-- And I validate the IMEI text is SAVEDIMEI
!-- And I validate the SIM Card text is SAVEDSIMCARD
!-- And I validate the Phone text is SAVEDPHONE
!-- And I validate the Alternate IMEI text is SAVEDALTERNATEIMEI
!-- And I validate the Serial Number text is SAVEDSERIALNUMBER
!-- And I validate the Status dropdown is SAVEDSTATUS
!-- And I validate the Activated text is not ""
!-- And I validate the Assigned Vehicle text is SAVEDVEHICLE
!-- And I click the Edit button
!-- And I validate the Device text is SAVEDDEVICE
!-- And I validate the Product text is SAVEDPRODUCT
!-- And I validate the MCM ID text is SAVEDMCMID
!-- And I validate the IMEI text is SAVEDIMEI
!-- And I validate the SIM Card text is SAVEDSIMCARD
!-- And I validate the Phone text is SAVEDPHONE
!-- And I validate the Alternate IMEI text is SAVEDALTERNATEIMEI
!-- And I validate the Serial Number text is SAVEDSERIALNUMBER
!-- And I validate the Status dropdown is SAVEDSTATUS
!-- And I validate the Activated text is not ""
!-- And I validate the Assigned Vehicle dropdown is SAVEDVEHICLE

Scenario: Assets - Devices - Edit Device (save button - no changes)
Given I am logged in
When I click the Top Devices link
And I click the Show Hide Columns link
And I check the Vehicle ID checkbox
And I check the Product checkbox
And I check the IMEI checkbox
And I check the Alternate IMEI checkbox
And I check the SIM Card checkbox
And I check the Serial Number checkbox
And I check the Phone checkbox
And I check the Status checkbox
And I check the MCM ID checkbox
And I click the Top Devices link
And I click the 1st Row of the Device Entry link
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 1st Row of the Product Entry link as SAVEDPRODUCT
And I save the 1st Row of the IMEI Entry link as SAVEDIMEI
And I save the 1st Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 1st Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 1st Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 1st Row of the Phone Entry link as SAVEDPHONE
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the MCM ID Entry link as SAVEDMCMID
And I click the Edit button
And I click the Save button
And I validate the Edit button is not visible
And I validate the Save button is not visible
And I validate the Cancel button is not visible
And I validate the Delete button is not visible
And I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status dropdown is ""
And I validate the Activated text is ""
And I validate the Assigned Vehicle text is ""

Scenario: Assets - Devices - Edit Device (save button - changes)
Given I am logged in
When I click the Top Devices link
And I click the Show Hide Columns link
And I check the Vehicle ID checkbox
And I check the Product checkbox
And I check the IMEI checkbox
And I check the Alternate IMEI checkbox
And I check the SIM Card checkbox
And I check the Serial Number checkbox
And I check the Phone checkbox
And I check the Status checkbox
And I check the MCM ID checkbox
And I click the Top Devices link
And I click the 1st Row of the Device Entry link
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 1st Row of the Product Entry link as SAVEDPRODUCT
And I save the 1st Row of the IMEI Entry link as SAVEDIMEI
And I save the 1st Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 1st Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 1st Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 1st Row of the Phone Entry link as SAVEDPHONE
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the MCM ID Entry link as SAVEDMCMID
And I click the Edit button
And I select "INACTIVE" from the Status dropdown
And I select "003198" from the Vehicle ID dropdown
And I click the Save button
And I type SAVEDDEVICE into the Search textfield
Then I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle ID Entry link is "003198"
And I validate the 1st Row of the Product Entry link is SAVEDPRODUCT
And I validate the 1st Row of the IMEI Entry link is SAVEDIMEI
And I validate the 1st Row of the Alternate IMEI Entry link is SAVEDALTERNATEIMEI
And I validate the 1st Row of the SIM Card Entry link is SAVEDSIMCARD
And I validate the 1st Row of the Serial Number Entry link is SAVEDSERIALNUMBER
And I validate the 1st Row of the Phone Entry link is SAVEDPHONE
And I validate the 1st Row of the Status Entry link is "INACTIVE"
And I validate the 1st Row of the MCM ID Entry link is SAVEDMCMID
And I click the 1st Row of the Device Entry link
And I click the Edit button
And I select "ACTIVE" from the Status dropdown
And I select SAVEDVEHICLE from the Vehicle ID dropdown
And I click the Save button

Scenario: Assets - Devices - Batch Edit Test (cancel button, no changes)
Given I am logged in
When I click the Top Devices link
And I click the Show Hide Columns link
And I check the Vehicle ID checkbox
And I check the Product checkbox
And I check the IMEI checkbox
And I check the Alternate IMEI checkbox
And I check the SIM Card checkbox
And I check the Serial Number checkbox
And I check the Phone checkbox
And I check the Status checkbox
And I check the MCM ID checkbox
And I click the Top Devices link
And I click the 1st Row of the Device Entry link
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 1st Row of the Product Entry link as SAVEDPRODUCT
And I save the 1st Row of the IMEI Entry link as SAVEDIMEI
And I save the 1st Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 1st Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 1st Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 1st Row of the Phone Entry link as SAVEDPHONE
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the MCM ID Entry link as SAVEDMCMID
And I control click the 2nd Row of the Device Entry link
And I save the 2nd Row of the Device Entry link as SAVEDDEVICE
And I save the 2nd Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 2nd Row of the Product Entry link as SAVEDPRODUCT
And I save the 2nd Row of the IMEI Entry link as SAVEDIMEI
And I save the 2nd Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 2nd Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 2nd Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 2nd Row of the Phone Entry link as SAVEDPHONE
And I save the 2nd Row of the Status Entry link as SAVEDSTATUS
And I save the 2nd Row of the MCM ID Entry link as SAVEDMCMID
Then I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status dropdown is ""
And I validate the Activated text is ""
And I validate the Assigned Vehicle text is ""
When I click the Edit button
And I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status dropdown is "NEW"
And I validate the Activated text is ""
And I validate the Assigned Vehicle text is disabled
And I click the Cancel button
Then I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle ID Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Product Entry link is SAVEDPRODUCT
And I validate the 1st Row of the IMEI Entry link is SAVEDIMEI
And I validate the 1st Row of the Alternate IMEI Entry link is SAVEDALTERNATEIMEI
And I validate the 1st Row of the SIM Card Entry link is SAVEDSIMCARD
And I validate the 1st Row of the Serial Number Entry link is SAVEDSERIALNUMBER
And I validate the 1st Row of the Phone Entry link is SAVEDPHONE
And I validate the 1st Row of the Status Entry link is SAVEDSTATUS
And I validate the 1st Row of the MCM ID Entry link is SAVEDMCMID
And I validate the 2nd Row of the Device Entry link is SAVEDDEVICE
And I validate the 2nd Row of the Vehicle ID Entry link is SAVEDVEHICLE
And I validate the 2nd Row of the Product Entry link is SAVEDPRODUCT
And I validate the 2nd Row of the IMEI Entry link is SAVEDIMEI
And I validate the 2nd Row of the Alternate IMEI Entry link is SAVEDALTERNATEIMEI
And I validate the 2nd Row of the SIM Card Entry link is SAVEDSIMCARD
And I validate the 2nd Row of the Serial Number Entry link is SAVEDSERIALNUMBER
And I validate the 2nd Row of the Phone Entry link is SAVEDPHONE
And I validate the 2nd Row of the Status Entry link is SAVEDSTATUS
And I validate the 2nd Row of the MCM ID Entry link is SAVEDMCMID

Scenario: Assets - Devices - Batch Edit Test (save button, no changes)
Given I am logged in
When I click the Top Devices link
And I click the Show Hide Columns link
And I check the Vehicle ID checkbox
And I check the Product checkbox
And I check the IMEI checkbox
And I check the Alternate IMEI checkbox
And I check the SIM Card checkbox
And I check the Serial Number checkbox
And I check the Phone checkbox
And I check the Status checkbox
And I check the MCM ID checkbox
And I click the Top Devices link
And I click the 1st Row of the Device Entry link
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 1st Row of the Product Entry link as SAVEDPRODUCT
And I save the 1st Row of the IMEI Entry link as SAVEDIMEI
And I save the 1st Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 1st Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 1st Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 1st Row of the Phone Entry link as SAVEDPHONE
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the MCM ID Entry link as SAVEDMCMID
And I control click the 2nd Row of the Device Entry link
And I save the 2nd Row of the Device Entry link as SAVEDDEVICE
And I save the 2nd Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 2nd Row of the Product Entry link as SAVEDPRODUCT
And I save the 2nd Row of the IMEI Entry link as SAVEDIMEI
And I save the 2nd Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 2nd Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 2nd Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 2nd Row of the Phone Entry link as SAVEDPHONE
And I save the 2nd Row of the Status Entry link as SAVEDSTATUS
And I save the 2nd Row of the MCM ID Entry link as SAVEDMCMID
Then I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status dropdown is ""
And I validate the Activated text is ""
And I validate the Assigned Vehicle text is ""
When I click the Save button
And I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status text is ""
And I validate the Activated text is ""
And I validate the Assigned Vehicle text is ""
Then I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle ID Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Product Entry link is SAVEDPRODUCT
And I validate the 1st Row of the IMEI Entry link is SAVEDIMEI
And I validate the 1st Row of the Alternate IMEI Entry link is SAVEDALTERNATEIMEI
And I validate the 1st Row of the SIM Card Entry link is SAVEDSIMCARD
And I validate the 1st Row of the Serial Number Entry link is SAVEDSERIALNUMBER
And I validate the 1st Row of the Phone Entry link is SAVEDPHONE
And I validate the 1st Row of the Status Entry link is SAVEDSTATUS
And I validate the 1st Row of the MCM ID Entry link is SAVEDMCMID
And I validate the 2nd Row of the Device Entry link is SAVEDDEVICE
And I validate the 2nd Row of the Vehicle ID Entry link is SAVEDVEHICLE
And I validate the 2nd Row of the Product Entry link is SAVEDPRODUCT
And I validate the 2nd Row of the IMEI Entry link is SAVEDIMEI
And I validate the 2nd Row of the Alternate IMEI Entry link is SAVEDALTERNATEIMEI
And I validate the 2nd Row of the SIM Card Entry link is SAVEDSIMCARD
And I validate the 2nd Row of the Serial Number Entry link is SAVEDSERIALNUMBER
And I validate the 2nd Row of the Phone Entry link is SAVEDPHONE
And I validate the 2nd Row of the Status Entry link is SAVEDSTATUS
And I validate the 2nd Row of the MCM ID Entry link is SAVEDMCMID

Scenario: Assets - Devices - Batch Edit Test (cancel button, changes)
Given I am logged in
When I click the Top Devices link
And I click the Show Hide Columns link
And I check the Vehicle ID checkbox
And I check the Product checkbox
And I check the IMEI checkbox
And I check the Alternate IMEI checkbox
And I check the SIM Card checkbox
And I check the Serial Number checkbox
And I check the Phone checkbox
And I check the Status checkbox
And I check the MCM ID checkbox
And I click the Top Devices link
And I click the 1st Row of the Device Entry link
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 1st Row of the Product Entry link as SAVEDPRODUCT
And I save the 1st Row of the IMEI Entry link as SAVEDIMEI
And I save the 1st Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 1st Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 1st Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 1st Row of the Phone Entry link as SAVEDPHONE
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the MCM ID Entry link as SAVEDMCMID
And I control click the 2nd Row of the Device Entry link
And I save the 2nd Row of the Device Entry link as SAVEDDEVICE
And I save the 2nd Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 2nd Row of the Product Entry link as SAVEDPRODUCT
And I save the 2nd Row of the IMEI Entry link as SAVEDIMEI
And I save the 2nd Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 2nd Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 2nd Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 2nd Row of the Phone Entry link as SAVEDPHONE
And I save the 2nd Row of the Status Entry link as SAVEDSTATUS
And I save the 2nd Row of the MCM ID Entry link as SAVEDMCMID
Then I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status dropdown is ""
And I validate the Activated text is ""
And I validate the Assigned Vehicle text is ""
When I click the Edit button
And I validate the Device text is ""
And I validate the Product text is ""
And I validate the MCM ID text is ""
And I validate the IMEI text is ""
And I validate the SIM Card text is ""
And I validate the Phone text is ""
And I validate the Alternate IMEI text is ""
And I validate the Serial Number text is ""
And I validate the Status dropdown is "NEW"
And I validate the Activated text is ""
And I validate the Assigned Vehicle text is disabled
And I select "NEW" from the Status dropdown 
And I click the Cancel button
Then I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle ID Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Product Entry link is SAVEDPRODUCT
And I validate the 1st Row of the IMEI Entry link is SAVEDIMEI
And I validate the 1st Row of the Alternate IMEI Entry link is SAVEDALTERNATEIMEI
And I validate the 1st Row of the SIM Card Entry link is SAVEDSIMCARD
And I validate the 1st Row of the Serial Number Entry link is SAVEDSERIALNUMBER
And I validate the 1st Row of the Phone Entry link is SAVEDPHONE
And I validate the 1st Row of the Status Entry link is SAVEDSTATUS
And I validate the 1st Row of the MCM ID Entry link is SAVEDMCMID
And I validate the 2nd Row of the Device Entry link is SAVEDDEVICE
And I validate the 2nd Row of the Vehicle ID Entry link is SAVEDVEHICLE
And I validate the 2nd Row of the Product Entry link is SAVEDPRODUCT
And I validate the 2nd Row of the IMEI Entry link is SAVEDIMEI
And I validate the 2nd Row of the Alternate IMEI Entry link is SAVEDALTERNATEIMEI
And I validate the 2nd Row of the SIM Card Entry link is SAVEDSIMCARD
And I validate the 2nd Row of the Serial Number Entry link is SAVEDSERIALNUMBER
And I validate the 2nd Row of the Phone Entry link is SAVEDPHONE
And I validate the 2nd Row of the Status Entry link is SAVEDSTATUS
And I validate the 2nd Row of the MCM ID Entry link is SAVEDMCMID

Scenario: Assets - Devices - Batch Edit and Batch Delete Test (save button, changes) (will need to use the DeviceRouteController to create devices specifically for this test)
!-- Given I am logged in
!-- When I click the Top Devices link
!-- And I click the Show Hide Columns link
!-- And I check the Vehicle ID checkbox
!-- And I check the Product checkbox
!-- And I check the IMEI checkbox
!-- And I check the Alternate IMEI checkbox
!-- And I check the SIM Card checkbox
!-- And I check the Serial Number checkbox
!-- And I check the Phone checkbox
!-- And I check the Status checkbox
!-- And I check the MCM ID checkbox
!-- And I click the Top Devices link
!-- And I type "BATCHDEVICE" into the Search textfield
And I click the 1st Row of the Device Entry link
And I save the 1st Row of the Device Entry link as SAVEDDEVICE
And I save the 1st Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 1st Row of the Product Entry link as SAVEDPRODUCT
And I save the 1st Row of the IMEI Entry link as SAVEDIMEI
And I save the 1st Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 1st Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 1st Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 1st Row of the Phone Entry link as SAVEDPHONE
And I save the 1st Row of the Status Entry link as SAVEDSTATUS
And I save the 1st Row of the MCM ID Entry link as SAVEDMCMID
And I control click the 2nd Row of the Device Entry link
And I save the 2nd Row of the Device Entry link as SAVEDDEVICE
And I save the 2nd Row of the Vehicle ID Entry link as SAVEDVEHICLE
And I save the 2nd Row of the Product Entry link as SAVEDPRODUCT
And I save the 2nd Row of the IMEI Entry link as SAVEDIMEI
And I save the 2nd Row of the Alternate IMEI Entry link as SAVEDALTERNATEIMEI
And I save the 2nd Row of the SIM Card Entry link as SAVEDSIMCARD
And I save the 2nd Row of the Serial Number Entry link as SAVEDSERIALNUMBER
And I save the 2nd Row of the Phone Entry link as SAVEDPHONE
And I save the 2nd Row of the Status Entry link as SAVEDSTATUS
And I save the 2nd Row of the MCM ID Entry link as SAVEDMCMID
And I click the Edit button
And I select "INACTIVE" from the Status dropdown
And I click the Save button
Then I validate the 1st Row of the Device Entry link is SAVEDDEVICE
And I validate the 1st Row of the Vehicle ID Entry link is SAVEDVEHICLE
And I validate the 1st Row of the Product Entry link is SAVEDPRODUCT
And I validate the 1st Row of the IMEI Entry link is SAVEDIMEI
And I validate the 1st Row of the Alternate IMEI Entry link is SAVEDALTERNATEIMEI
And I validate the 1st Row of the SIM Card Entry link is SAVEDSIMCARD
And I validate the 1st Row of the Serial Number Entry link is SAVEDSERIALNUMBER
And I validate the 1st Row of the Phone Entry link is SAVEDPHONE
And I validate the 1st Row of the Status Entry link is SAVEDSTATUS
And I validate the 1st Row of the MCM ID Entry link is SAVEDMCMID
And I validate the 2nd Row of the Device Entry link is SAVEDDEVICE
And I validate the 2nd Row of the Vehicle ID Entry link is SAVEDVEHICLE
And I validate the 2nd Row of the Product Entry link is SAVEDPRODUCT
And I validate the 2nd Row of the IMEI Entry link is SAVEDIMEI
And I validate the 2nd Row of the Alternate IMEI Entry link is SAVEDALTERNATEIMEI
And I validate the 2nd Row of the SIM Card Entry link is SAVEDSIMCARD
And I validate the 2nd Row of the Serial Number Entry link is SAVEDSERIALNUMBER
And I validate the 2nd Row of the Phone Entry link is SAVEDPHONE
And I validate the 2nd Row of the Status Entry link is SAVEDSTATUS
And I validate the 2nd Row of the MCM ID Entry link is SAVEDMCMID
!-- Now I delete the entries so it will run just fine on the next run through
And I click the Top Devices link
And I click the 1st Row of the Device Entry link
And I click the 2nd Row of the Device Entry link
And I click the Edit button
And I select "DELETED" from the Status dropdown
And I click the Save button
And I validate the Entries text contains "Showing 0 to 0 of 0 entries"