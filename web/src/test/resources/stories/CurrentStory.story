Scenario: : Live Fleet - Map Marker
Given I am logged in
When I click the Live Fleet link
And I type "4225 Lake Park Blvd, West Valley City, UT 84102" into the Find Address textfield
And I click the Locate button
And I wait
!-- unlock after figuring out how to search for text in new google map html form
!-- Then I verify "40.710575,-111.992065" is on the page