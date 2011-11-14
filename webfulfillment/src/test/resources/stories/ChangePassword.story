Narrative:

In order to set a private secure password
As an installer
I want to change my password

Scenario: TC5927: Change Password Cancel button
Given I am on the web fulfillment Admin page
When I click on the "Change Password" link
and Change Password popup displays
and I enter my current password
and I enter a new password
and I enter to confirm the new password
and I click on the "Cancel" button
Then the popup display closes without changing my current password

Scenario: TC5963: Incorrect Current Password
Given I am on the web fulfillment Admin page
When I click on the "Change Password" link
and Change Password popup displays
and I enter an incorrect current password
and I enter a new password
and I enter to confirm the new password
and I click on the "Change" button
Then an error message displays "Current Password Incorrect"

Scenario: TC5964: New and Confirm Passwords don't match
Given I am on the web fulfillment Admin page
When I click on the "Change Password" link
and Change Password popup displays
and I enter correct current password
and I enter a new password
and I enter a different password in the confirm field
and I click on the "Change" button
Then an error message displays "New and Confirm New Password do not match"

Scenario: TC5965: Password Change Successful
Given I am on the web fulfillment Admin page
When I click on the "Change Password" link
and Change Password popup displays
and I enter correct current password
and I enter a new password
and I enter the correct new password in confirm field
and I click on the "Change" button
Then "Password successfully changed" message displays

Scenario: TC5966: Required Fields
Given I am on the web fulfillment Admin page
When I click on the "Change Password" link
and Change Password popup displays
and I click on the "Change" button
Then "!Required" message displays above each empty field.

Scenario: TC5967: Must be 6-12 Characters
Given I am on the web fulfillment Admin page
When I click on the "Change Password" link
and Change Password popup displays
and I enter correct current password
and I enter a new password with one character
and I enter the same one character password in the confirm field
and I click on the "Change" button
Then an error message displays "Must be 6 to 12 characters" over the New Password field.

Scenario: TC5968: Meets the 12 Character Max limit
Given I am on the web fulfillment Admin page
When I click on the "Change Password" link
and Change Password popup displays
and I enter correct current password
and I attempt to enter a new password with 13 characters but it only limits me to 12
and I enter the same first 12 character password in the confirm field
and I click on the "Change" button
Then "Password successfully changed" message displays



