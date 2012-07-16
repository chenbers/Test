Test Cases for TF388/TF394/TF395 

Meta:
@page login
@testFolder TF388
@testFolder TF394
@testFolder TF395

Narrative:

Scenario: TC1285: My Account - Change Password - Cancel Button (Changes)
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "a" into the Current Password textfield
And I type "a" into the New Password textfield
And I type "a" into the Confirm New Password textfield
And I click the Cancel button
And the Change My Password popup closes
Then I validate I am on the My Account page
And I validate the Info Message text is not present
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1286: My Account - Change Password - Cancel Button (No Changes)
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I click the Cancel button
And the Change My Password popup closes
Then I validate I am on the My Account page
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1287: My Account - Change Password - Case Error
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "2ut2CFmnH$f!" into the Current Password textfield
And I type "NEWPASSWORD" into the New Password textfield
And I type "newpassword" into the Confirm New Password textfield
And I click the Change button
Then I validate the Confirm Password Error text is "New and Confirm New Password do not match"
And I click the Cancel button
And the Change My Password popup closes
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1288: My Account - Change Password - Change Button
Given I am logged in an account that can be edited
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "2ut2CFmnH$f!" into the Current Password textfield
And I type "newpassword" into the New Password textfield
And I type "newpassword" into the Confirm New Password textfield
And I click the Change button
And the Change My Password popup closes
Then I validate the Info Message text is "Password successfully changed"
And I click the Change Password button
And the Change My Password popup opens
And I type "newpassword" into the Current Password textfield
And I type "2ut2CFmnH$f!" into the New Password textfield
And I type "2ut2CFmnH$f!" into the Confirm New Password textfield
And I click the Change button
And the Change My Password popup closes
And I validate the Info Message text is "Password successfully changed"

Scenario: TC1289: My Account - Change Password - Confirm New Password Error
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "2ut2CFmnH$f!" into the Current Password textfield
And I type "pass11" into the New Password textfield
And I type "pass22" into the Confirm New Password textfield
And I click the Change button
Then I validate the Confirm Password Error text is "New and Confirm New Password do not match"
And I click the Cancel button
And the Change My Password popup closes
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1290: My Account - Change Password - Test New Password Required Fields
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "2ut2CFmnH$f!" into the Current Password textfield
And I type "pass22" into the Confirm New Password textfield
And I click the Change button
Then I validate the New Password Error text is "Required"
And I validate the Confirm New Password textfield is ""
And I click the Cancel button
And the Change My Password popup closes
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1291: My Account - Change Password - Confirm New Password Required Field
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "2ut2CFmnH$f!" into the Current Password textfield
And I type "pass22" into the New Password textfield
And I click the Change button
Then I validate the Confirm Password Error text is "Required"
And I click the Cancel button
And the Change My Password popup closes
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1292: My Account - Change Password - Current Password Error
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "notmypassword" into the Current Password textfield
And I type "pass22" into the New Password textfield
And I type "pass22" into the Confirm New Password textfield
And I click the Change button
Then I validate the Current Password Error text is "Current Password is incorrect"
And I click the Cancel button
And the Change My Password popup closes
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1294: My Account - Change Password - Missing Required Field
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I click the Change button
Then I validate the Current Password Error text is "Required"
And I validate the New Password Error text is "Required"
And I validate the Confirm Password Error text is "Required"
And I click the Cancel button
And the Change My Password popup closes
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1295: My Account - Change Password - New Password 12 characters Max
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "pass22pass222" into the New Password textfield
Then I validate the New Password textfield is "pass22pass22"
And I click the Cancel button
And the Change My Password popup closes
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1296: My Account - Change Password - New Password Min Character Error
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "pass1" into the New Password textfield
And I type "pass1" into the Confirm New Password textfield
And I click the Change button
Then I validate the New Password Error text is "Must be 6 to 12 characters"
And I click the Cancel button
And the Change My Password popup closes
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1298: My Account - Change Password - UI
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
Then I validate the Current Password textfield is present
And I validate the New Password textfield is present
And I validate the Confirm New Password textfield is present
And I validate the Change button is present
And I validate the Cancel button is present
And I click the Cancel button
And the Change My Password popup closes
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page

Scenario: TC1301: My Account - Change Password - Validation (Special Characters)
Given I am logged in an account that can be edited
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "2ut2CFmnH$f!" into the Current Password textfield
And I type "$, #&!@%^*()" into the New Password textfield
And I type "$, #&!@%^*()" into the Confirm New Password textfield
And I click the Change button
And the Change My Password popup closes
Then I validate the Info Message text is "Password successfully changed"
And I click the Change Password button
And the Change My Password popup opens
And I type "$, #&!@%^*()" into the Current Password textfield
And I type "2ut2CFmnH$f!" into the New Password textfield
And I type "2ut2CFmnH$f!" into the Confirm New Password textfield
And I click the Change button
And the Change My Password popup closes
And I validate the Info Message text is "Password successfully changed"

Scenario: TC1302: My Account - Change Password - X Window Control Button (Changes)
Given I am logged in
When I click the My Account link
And I click the Change Password button
And the Change My Password popup opens
And I type "a" into the Current Password textfield
And I type "a" into the New Password textfield
And I type "a" into the Confirm New Password textfield
And I click the Close button
And the Change My Password popup closes
Then I validate I am on the My Account page
And I validate the Info Message text is not present
And I click the Logout link
Given I am logged in
Then I validate I am not on the Login page