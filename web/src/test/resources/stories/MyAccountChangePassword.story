Test Cases for TF388/TF394/TF395 

Meta:
@page login
@testFolder TF388
@testFolder TF394
@testFolder TF395

Narrative:

Scenario: TC1285: My Account - Change Password - Cancel Button (Changes)
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "a" into the CurrentPassword textfield
And I type "a" into the NewPassword textfield
And I type "a" into the ConfirmNewPassword textfield
And I click the Cancel button
And the ChangeMyPassword popup closes
Then I validate I am on the MyAccount page
And I validate the InfoMessage text is not visible
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1286: My Account - Change Password - Cancel Button (No Changes)
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I click the Cancel button
And the ChangeMyPassword popup closes
Then I validate I am on the MyAccount page
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1287: My Account - Change Password - Case Error
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "password" into the CurrentPassword textfield
And I type "NEWPASSWORD" into the NewPassword textfield
And I type "newpassword" into the ConfirmNewPassword textfield
And I click the Change button
And the ChangeMyPassword popup closes
Then I validate the ConfirmPasswordError text is "New and Confirm New Password do not match"
And I click the Cancel button
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1288: My Account - Change Password - Change Button
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "password" into the CurrentPassword textfield
And I type "newpassword" into the NewPassword textfield
And I type "newpassword" into the ConfirmNewPassword textfield
And I click the Change button
And the ChangeMyPassword popup closes
Then I validate the InfoMessage text is "Password successfully changed"
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "newpassword" into the CurrentPassword textfield
And I type "password" into the NewPassword textfield
And I type "password" into the ConfirmNewPassword textfield
And I click the Change button
And the ChangeMyPassword popup closes
And I validate the InfoMessage text is "Password successfully changed"

Scenario: TC1289: My Account - Change Password - Confirm New Password Error
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "password" into the CurrentPassword textfield
And I type "pass11" into the NewPassword textfield
And I type "pass22" into the ConfirmNewPassword textfield
And I click the Change button
Then I validate the ConfirmPasswordError text is "New and Confirm New Password do not match"
And I click the Cancel button
And the ChangeMyPassword popup closes
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1290: My Account - Change Password - Test New Password Required Fields
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "password" into the CurrentPassword textfield
And I type "pass22" into the ConfirmNewPassword textfield
And I click the Change button
Then I validate the NewPasswordError text is "Required"
And I validate the ConfirmNewPassword textfield is ""
And I click the Cancel button
And the ChangeMyPassword popup closes
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1291: My Account - Change Password - Confirm New Password Required Field
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "password" into the CurrentPassword textfield
And I type "pass22" into the NewPassword textfield
And I click the Change button
Then I validate the ConfirmNewPasswordError text is "Required"
And I click the Cancel button
And the ChangeMyPassword popup closes
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1292: My Account - Change Password - Current Password Error
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "notmypassword" into the CurrentPassword textfield
And I type "pass22" into the NewPassword textfield
And I type "pass22" into the ConfirmNewPassword textfield
And I click the Change button
Then I validate the ConfirmNewPasswordError text is "Current Password is incorrect"
And I click the Cancel button
And the ChangeMyPassword popup closes
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1293: My Account - Change Password - Default Command Button CANNOT IMPLEMENT YET DUE TO KEYBOARD PRESS

Scenario: TC1294: My Account - Change Password - Missing Required Field
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I click the Change button
Then I validate the CurrentPasswordError text is "Required"
And I validate the NewPasswordError text is "Required"
And I validate the ConfirmNewPasswordError text is "Required"
And I click the Cancel button
And the ChangeMyPassword popup closes
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1295: My Account - Change Password - New Password 12 characters Max
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "pass22pass222" into the NewPassword textfield
Then I validate the NewPassword textfield is "pass22pass22"
And I click the Cancel button
And the ChangeMyPassword popup closes
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1296: My Account - Change Password - New Password Min Character Error
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "pass1" into the NewPassword textfield
And I type "pass1" into the ConfirmNewPassword textfield
Then I validate the NewPasswordError text is "Must be 6 to 12 characters"
And I click the Cancel button
And the ChangeMyPassword popup closes
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1297: My Account - Change Password - Tabbing Order CANNOT IMPLEMENT YET DUE TO KEYBOARD PRESS

Scenario: TC1298: My Account - Change Password - UI
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
Then I validate the CurrentPassword textfield is present
And I validate the NewPassword textfield is present
And I validate the ConfirmNewPassword textfield is present
And I validate the Change button is present
And I validate the Cancel button is present
And I click the Cancel button
And the ChangeMyPassword popup closes
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page

Scenario: TC1299: My Account - Change Password - Validation THIS TEST IS VERY SIMILAR TO TC1288, DO WE NEED BOTH?

Scenario: TC1301: My Account - Change Password - Validation (Special Characters)
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "password" into the CurrentPassword textfield
And I type "$, #&!@%^*()" into the NewPassword textfield
And I type "$, #&!@%^*()" into the ConfirmNewPassword textfield
And I click the Change button
And the ChangeMyPassword popup closes
Then I validate the InfoMessage text is "Password successfully changed"
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "$, #&!@%^*()" into the CurrentPassword textfield
And I type "password" into the NewPassword textfield
And I type "password" into the ConfirmNewPassword textfield
And I click the Change button
And the ChangeMyPassword popup closes
And I validate the InfoMessage text is "Password successfully changed"

Scenario: TC1302: My Account - Change Password - X Window Control Button (Changes)
Given I am logged in as a "Admin" user
When I click the MyAccount link
And I click the ChangePassword button
And the ChangeMyPassword popup opens
And I type "a" into the CurrentPassword textfield
And I type "a" into the NewPassword textfield
And I type "a" into the ConfirmNewPassword textfield
And I click the Close button
And the ChangeMyPassword popup closes
Then I validate I am on the MyAccount page
And I validate the InfoMessage text is not visible
And I click the Logout link
And I am logged in as a "Admin" user
And I validate I am not on the Login page
