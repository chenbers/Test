Scenario: : Live Fleet - Map Marker
Given I am logged in
When I click the Live Fleet link
And I Evaluate Javascript "optimization = false;"
And I type "4225 Lake Park Blvd, West Valley City, UT 84102" into the Find Address textfield
And I click the Locate button
And I wait
And I click the Marker textlink
Then I verify "40.710575,-111.992065" is on the page