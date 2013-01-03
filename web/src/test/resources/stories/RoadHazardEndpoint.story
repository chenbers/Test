Narrative: 
In order to supply a driver with information about road hazards, a device will make an HTTP request (post) to the server
and will be delivered all road hazard data for their account within a 200 mile radius.

<--Given I verify endpoint data for MCMID_IMEA "MCM100343" and latitude "18.9" and longitude "-89.67"
<--Given I verify endpoint data for MCMID_IMEA "135798462" and latitude "40.9" and longitude "-111.67"

Scenario: TA14552: Test getRH endpoint
Given I verify endpoint data for MCMID_IMEA "MCM073869" and latitude "18.9" and longitude "-89.67"
 