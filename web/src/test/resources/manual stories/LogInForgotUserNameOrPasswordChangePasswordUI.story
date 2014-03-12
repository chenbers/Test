Given I am on the login page
Then I click the forgot your user name or password link
And I type a valid email address in the email address field
And I click send
And I log in to my email account
And I open the 'Update Your Password' message
And I click on the link provided in the email message
Then the 'change password' page appears
And the page contains the user name static text string
And the page contains the new password text field
And the page contains the confirm new password text field
And the page contains the change button in the left position
And the page contains the cancel button in the right position
And the portal version is displayed in the lower right corner