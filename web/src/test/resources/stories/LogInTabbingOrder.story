Scenario: TC1249: Log In - Tabbing Order
Given I am on the login page
When the focus should be on the User Name Field
And I press the tab key 
Then the focus should be on the Password Field
And I press the tab key
And the focus should be on the Log In Button
And I press the tab key
And the focus should be on the Forgot User Name or Password Link
And I press the tab key
And the focus should be on the Privacy Policy Link
And I press the tab key
And the focus should be on the Legal Notice Link
And I press the tab key
And the focus should be on the Support Link