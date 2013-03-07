Narrative:

In order to confirm the GET Route endpoint is working correctly, I have these tests

Scenario: Basic Route GET endpoint
Given I retrieve a standard route

Scenario: Retrieve route with two deliveries and a load activity
Given I retrieve a route with two deliveries and a load activity

Scenario: Retrieve route with two deliveries, a load, and a preload activity
Given I retrieve a route with two deliveries, a load, and a preload activity

Scenario: Retrieve route with two deliveries and a preload activity
Given I retrieve a route with two deliveries and a preload activity

//BELOW TESTS HAVE NOT BEEN FORMATTED CORRECTLY YET
Scenario: Retrieve route with two deliveries
Given a driver logs in
When the device requests a route
Then the route comes back with two deliveries (stops)

Scenario: Retrieve route with two deliveries and a preload activity, then cancel the preload activity
Given a driver logs in
When the device requests a route
Then the route comes back with two deliveries (stops)
And the route comes back with a preload activity
And the driver cancels the preload activity (or does Ortec cancel the preload activity?)
Then I validate the route data is updated correctly

Scenario: Retrieve route with two deliveries and a load activity and then later add a preload activity
Given a driver logs in
When the device requests a route
Then the route comes back with two deliveries (stops)
And the route comes back with a load activity
And the driver completes the load activity and one delivery
And the device retrieves a preload activity
And the route is updated with the preload activity
And I validate the remaining delivery activity doesn't change

Scenario: Retrieve route with three deliveries, then cancel the second delivery activity and add a new delivery into the third delivery slot
Given a driver logs in
When the device requests a route
Then the route comes back with three deliveries (stops)
And the device requests route data
And the second delivery is cancelled by Ortec (or cancelled by the driver?  Need clarification)
And I validate the route data is updated correctly
And the device requests route data
And and new third delivery activity has been added
And I validate the route data is updated correctly

Scenario: Retrieve route with three deliveries, then cancel all delivery activities and add a new delivery
Given a driver logs in
When the device requests a route
Then the route comes back with three deliveries (stops)
And the device requests route data
And all the deliveries are cancelled by Ortec (or cancelled by the driver?  Need clarification)
And I validate the route data is updated correctly
And the device requests route data
And and new delivery activity has been added
And I validate the route data is updated correctly

Scenario: Receive trip data from Ortec with three deliveries and the driver delivers them out of order
Given a driver logs in
When the device requests a route
And the route contains a trip with three deliveries (stops)
And the driver completes delivery activity two first
Then I validate the correct delivery information is sent back to the Ortec Comm Server

Scenario: Receive trip data from Ortec with three deliveries and then is sent to a dump site
Given a driver logs in
When the device requests a route
And the route contains a trip with three deliveries (stops)
And the driver completes all three delivery activites
And the vehicle has a remaining balance of product left
Then a fourth delivery activity to take the remaining product to a dump site is received
And the driver drops off their remaining product at the dump site

Scenario: Driver partially completes the route then moves vehicles
Given a driver logs in
When the device requests a route
And the route contains a trip with at least one delivery (stop)
And a driver partially completes a route
And the driver moves to a different vehicle
And the driver requests the route
Then I validate the correct route is sent to the new vehicle

Scenario: Driver partially completes the route then another driver picks up their route
Given a driver logs in
When the device requests a route
And the route contains a trip with at least one delivery (stop)
And a driver partially completes a route
And then a different driver requests the same route.
Then ? (Maybe just add something to the response that the route is currently in progress and by which driver, vehicle, device, so the device can popup a warning/confirmation dialog or something.)

Scenario: Status 400 Code 100 Invalid IMEI
Given I provide an invalid IMEI
Then I validate the Response text contains "status":404,"code":100,"message":"Device Not Found.  Lookup failed for mcmID or IMEI: XX"

Scenario: Status 400 Code 500 Route not found
Given I provide an invalid RouteID
Then I validate the Response text contains "status":400,"code":500,"message":"A route for RouteID: INVALID_ORTEC_ROUTE was not found."