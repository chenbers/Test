Narrative:

In order to confirm the GET Route endpoint is working correctly, I have these tests

Scenario: I retrieve a route with two deliveries and a pickup activity
Given I retrieve a route with two deliveries and a pickup activity

Scenario: Retrieve route with two deliveries, a load, and a preload activity
Given I retrieve a route with two deliveries, a load, and a preload activity

Scenario: Retrieve route with two deliveries and a preload activity
Given I retrieve a route with two deliveries and a preload activity

Scenario: Retrieve route with two deliveries
Given I retrieve a route with two deliveries

Scenario: Retrieve route with two deliveries and a preload activity, then cancel the preload activity
Given I retrieve a route with two deliveries and a preload activity, then cancel the preload activity

Scenario: Retrieve route with two deliveries and a load activity and then later add a preload activity
Given I retrieve a route with two deliveries and a load activity and then later add a preload activity

Scenario: Retrieve route with three deliveries, then cancel the second delivery activity and add a new delivery into the third delivery slot
Given I retrieve a route with three deliveries, then cancel the second delivery activity and add a new delivery into the third delivery slot

Scenario: Retrieve route with three deliveries, then cancel all delivery activities and add a new delivery
Given I retrieve a route with three deliveries, then cancel all delivery activities and add a new delivery

Scenario: Receive trip data from Ortec with three deliveries and the driver delivers them out of order
Given I retrieve a route with three deliveries and the driver delivers them out of order

Scenario: Receive trip data from Ortec with three deliveries and then the driver is sent to a dump site
Given I retrieve a route with three deliveries and then the driver is sent to a dump site

Scenario: Driver partially completes the route then moves vehicles
Given I retrieve a route and then partially complete the route and move vehicles

Scenario: Driver partially completes the route then another driver picks up their route from the same vehicle
Given I retrieve a route and then partially complete the route and a different driver retrieves the route from the same vehicle

Scenario: Driver partially completes the route then another driver picks up their route from a different vehicle
Given I retrieve a route and then partially complete the route and a different driver retrieves the route from a different vehicle

Scenario: Status 400 Code 100 Invalid IMEI
Given I provide an invalid IMEI
Then I validate the Response text contains "status":404,"code":100,"message":"Device Not Found.  Lookup failed for mcmID or IMEI: XX"

Scenario: Status 400 Code 500 Route not found
Given I provide an invalid RouteID
Then I validate the Response text contains "status":400,"code":500,"message":"A route for RouteID: INVALID_ORTEC_ROUTE was not found."