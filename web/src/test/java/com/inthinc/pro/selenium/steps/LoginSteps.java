package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyMessages;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;
import com.inthinc.pro.selenium.pageObjects.PopUps;

public class LoginSteps extends WebSteps {

    PageLogin loginPage = new PageLogin();
    AutomationUser login;

    private static final PageLogin page = new PageLogin();
    private static final PopUps popup = new PopUps();

    // private static final AutomationUser autouser = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
    // REPLACED BY AUTOSTORY
    // @Given("I log in to the login page")
    // public void givenIAmLoggedInToTheLoginPage() {
    // if (!page.verifyOnPage()) {
    // page.load();
    // page._textField().userName().type(autouser.getUsername());
    // page._textField().password().type(autouser.getPassword());
    // whenIClickSignIn();
    // }
    // }

    @Given("I am on the login page")
    @Aliases(values = { "I open the Login Page" })
    public void givenIAmOnTheLogInPage() {
        if (!page.verifyOnPage()) {
            page.load();
        }
    }

    @When("I am on the login page")
    @Aliases(values = { "I open the Login Page" })
    public void whenIAmOnTheLogInPage() {
        if (!page.verifyOnPage()) {
            page.load();
        }
    }

    @Then("I should remain on the Login Page")
    @Alias("I am still on the Login Page")
    public void thenIShouldRemainOnTheLoginPage() {
        if (!page.verifyOnPage()) {
            page.load();
        }
    }

    // @When("I type an user name in the wrong case")
    // public void whenITypeAnUserNameInTheWrongCase() {
    // page._textField().userName().type(MasterTest.switchCase(autouser.getUsername()));
    //
    // }

    // @When("I type a valid user name")
    // public void whenITypeAValidUserName() {
    // page._textField().userName().type(autouser.getUsername());
    // }

    @When("I type an invalid user name")
    public void whenITypeAnInvalidUserName() {
        page._textField().userName().type("thiswillneverbeavalidusername");
    }

    // @When("I type a valid password")
    // public void whenITypeAValidPassword() {
    // page._textField().password().type(autouser.getPassword());
    // }

    @When("I type an invalid password")
    public void whenITypeAnInvalidPassword() {
        page._textField().password().type("invalidpasswordentered");
    }

    // @When("I type a password in the wrong case")
    // public void whenITypeAPasswordInTheWrongCase() {
    // page._textField().password().type(MasterTest.switchCase(autouser.getPassword()));
    // }

    @When("I close the login error alert message")
    @Then("I close the login error alert message")
    public void whenICloseTheAlertMessage() {
        loginPage._popUp().loginError()._button().ok().click();
    }

    @Then("I get an alert 'Incorrect user name or password. Please try again.'")
    public void thenIGetAnAlertIncorrectUserNameOrPasswordPleaseTryAgain() {
        page._popUp().loginError()._text().message().assertEquals();
    }

    @Then("the name and password fields are blank")
    public void thenTheNameAndPasswordFieldsAreBlank() {

        validateUserNameField("");
        validateUserPasswordField("");

    }

    @Then("the name field is $text")
    public void validateUserNameField(String text) {

        page._textField().userName().validate(text);
        page._textField().userName().focus();

    }

    @Then("the password field is $password")
    public void validateUserPasswordField(String password) {
        page._textField().password().validate(password);
    }

    @When("I press the enter key")
    public void whenIPressTheEnterKey() {
        page.enterKey();
    }

    // Tab key is not working yet
    @When("I press the tab key")
    public void whenIPressTheTabKey() {
        test.tabKey();
    }

    // REPLACED BY AUTOSTORY
    @Then("I confirm the page contains all necessary elements")
    public void thenIConfirmThePageContainsAllNecessaryElements() {
        Boolean validate = page._textField().userName().isPresent() & page._textField().userName().focus().isPresent() & page._textField().password().isPresent() & page._button().logIn().isPresent()
                & page._link().forgotUsernamePassword().isPresent() & page._text().version().isPresent();

        page.validateTrue(validate, "All elements exist on this page");
    }

    @Then("I click the 'Forgot your user name or password?' link")
    @When("I click the 'Forgot your user name or password?' link")
    public void whenIClickTheForgotYourUserNameOrPasswordLink() {
        page._link().forgotUsernamePassword().click();
    }

    @When("I enter a email address not in the database into the email address field")
    public void whenIEnterTextIntoTheEmailAddressField() {
        page._popUp().forgotPassword()._textField().email().type("test@test.com");
    }

    @When("I click cancel")
    public void whenIClickCancel() {
        page._popUp().forgotPassword()._button().cancel().click();
    }

    @Then("the 'Forgot you user name or password?' window is closed")
    public void thenTheForgotYouUserNameOrPasswordWindowIsClosed() {
        page._popUp().forgotPassword()._text().header().validateVisibility(false);
    }

    @Then("I am on the login page")
    @Aliases(values = { "I open the Login Page" })
    public void thenIAmOnTheLogInPage() {
        if (!page.verifyOnPage()) {
            page.load();
        }
    }

    @Then("the email address text field is blank")
    public void thenTheEmailAddressTextFieldIsBlank() {
        page._popUp().forgotPassword()._textField().email().validate("");
    }

    @When("I enter non valid email text into the email address field")
    public void whenIEnterNonValidEmailTextIntoTheEmailAddressField() {
        page._popUp().forgotPassword()._textField().email().type("z"); // doesn't conform to rule * x to y characters ??
        page._popUp().forgotPassword()._button().send().click();
        page._popUp().forgotPassword()._textField().email().type("test@@test.com"); /*
                                                                                     * doesn't conform to rule * Only one commercial at (@) is allowed to separate the user name
                                                                                     * from the domain name User Name
                                                                                     */
        page._popUp().forgotPassword()._button().send().click();
        page._popUp().forgotPassword()._textField().email().type("test@test@test.com"); /*
                                                                                         * doesn't conform to rule * Only one commercial at (@) is allowed to separate the user name
                                                                                         * from the domain name User Name
                                                                                         */
        page._popUp().forgotPassword()._button().send().click();
        page._popUp().forgotPassword()._textField().email().type("!@#...com"); /*
                                                                                * doesn't conform to rule * A - Z, a - z, 0 - 9, underscore (_), hyphen (-), and period (.)
                                                                                * characters are allowed (Note: A period at the beginning, a period at the end, and 2 consecutive
                                                                                * periods are allowed.)
                                                                                */
        page._popUp().forgotPassword()._button().send().click();
        page._popUp().forgotPassword()._textField().email().type("test test@email.com"); /*
                                                                                          * doesn't conform to rule * All other characters, inclusive of a blank space and a quoted
                                                                                          * string (i.e. between double quotes), are NOT allowed Domain Name
                                                                                          */
        page._popUp().forgotPassword()._button().send().click();
        page._popUp().forgotPassword()._textField().email().type("\"test\"@email.com"); /*
                                                                                         * doesn't conform to rule * All other characters, inclusive of a blank space and a quoted
                                                                                         * string (i.e. between double quotes), are NOT allowed Domain Name
                                                                                         */
    }

    @When("I click send")
    public void whenIClickSend() {
        page._popUp().forgotPassword()._button().send().click();
    }

    @Then("the alert 'Incorrect format' appears above the email address field")
    public void thenTheAlertIncorrectFormatAppearsAboveTheEmailAddressField() {
        page._popUp().forgotPassword()._text().error().validate("Incorrect format (jdoe@tiwipro.com)");
    }

    @Then("the alert 'Required' appears above the email address field")
    public void thenTheAlertRequiredAppearsAboveTheEmailAddressField() {
        page._popUp().forgotPassword()._text().error().validate("Required");
    }

    @Then("the alert 'Incorrect e-mail address' appears above the email address field")
    public void thenTheAlertIncorrectEmailAddressAppearsAboveTheEmailAddressField() {
        page._popUp().forgotPassword()._text().error().validate("Incorrect e-mail address");
    }

    @Then("the 'Forgot user name or password' pop up displays correctly with all elements")
    public void thenTheForgotUserNameOrPasswordPopUpDisplaysCorrectlyWithAllElements() {
        page._popUp().forgotPassword().equals(popup);

    }

    //
    // @Given("I am logged in as a user in a role that does have the $accessPointName accesspoint")
    // public void loginAsUserWithAccesspoint(String accesspointName){
    // //LoginCapabilities.NoAccessPointUserInfo;
    // LoginCapability hasThisCapability = null;
    // if(accesspointName.equals("UserInfo"))
    // hasThisCapability = LoginCapability.AccessPointUserInfo;
    //
    // AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
    //
    // PageLogin login = new PageLogin();
    // login.loginProcess(user);
    // }

    @When("I am logged in as TeamOnly user")
    public void loggedInAsTeamOnlyUser() {
        page._textField().userName().type("CaptainNemo");
        page._textField().password().type("Muttley");
        page._button().logIn().click();
    }

    // @Given("I am logged in as a \"$roleName\" user")
    // @When("I am logged in as a \"$roleName\" user")
    // public void loginAsAUserofRole(String roleName) {
    // LoginCapability hasThisCapability = null;
    // //TODO: FIGURE OUT AUTOMATED USERS, IN THE MEANTIME, USE THIS CODE:
    // // if(roleName.equals("TopUser"))
    // // {
    // // page._textField().userName().type("danniauto");
    // // page._textField().password().type("password");
    // // page._button().logIn().click();
    // // }
    // // if(roleName.equals("TeamOnly"))
    // // {
    // // page._textField().userName().type("CaptainNemo");
    // // page._textField().password().type("Muttley");
    // // page._button().logIn().click();
    // // }
    // if(roleName.equals("Admin"))
    // hasThisCapability = LoginCapability.RoleAdmin;
    // else if(roleName.equals("HOS"))
    // hasThisCapability = LoginCapability.RoleHOS;
    // else if(roleName.equals("Live Fleet"))
    // hasThisCapability = LoginCapability.RoleLiveFleet;
    // else
    // test.addError("there is no defined role of "+roleName, ErrorLevel.ERROR);
    //
    // AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
    //
    // PageLogin login = new PageLogin();
    // login.loginProcess(user);
    // }

    // @Given("I am logged in as a user in a role that does not have the $accesspointName accesspoint")
    // public void loginAsUserWithoutAccesspoint(String accesspointName){
    // Log.error("I am logged in as a user in a role that does not have "+ accesspointName+" accesspoint");
    // LoginCapability hasThisCapability = null;
    // if(accesspointName.equals("UserInfo"))
    // hasThisCapability = LoginCapability.NoAccessPointUserInfo;//TODO: jwimmer: I do not like this manual mapping???
    //
    // AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
    //
    // PageLogin login = new PageLogin();
    // login.loginProcess(user);
    // }

    // admin users
    // @Given("I am on the \"$pageDescription\" page")
    // public void givenIAmOnThePage(String pageDescription){
    // MasterTest.print("public void givenIAmOnThePage(String "+pageDescription+")", Level.DEBUG);
    // boolean matched = false;
    // PageTeamDashboardStatistics dummyPage = new PageTeamDashboardStatistics();
    // if(pageDescription.equalsIgnoreCase("admin users")) {
    // matched = true;
    // PageAdminUsers adminUsersPage = new PageAdminUsers();
    // adminUsersPage.load();
    // }
    // if(pageDescription.startsWith("Admin -")){
    // matched = true;
    // if(pageDescription.endsWith("- Users"))
    // new PageAdminCustomRoleAddEdit().load();
    // else if(pageDescription.endsWith("- Add User"))
    // new PageAdminUserAddEdit();
    // else if(pageDescription.endsWith("- Vehicles"))
    // new PageAdminVehicles();
    // else if(pageDescription.endsWith("- Add Vehicle"))
    // new PageAdminVehicleEdit().load();
    // else if(pageDescription.endsWith("- Devices"))
    // new PageAdminDevices().load();
    // else if(pageDescription.endsWith("- Zones"))
    // new PageAdminZones().load();
    // else if(pageDescription.endsWith("- Red Flags"))
    // new PageAdminRedFlags().load();
    // else if(pageDescription.endsWith("- Add Red Flag"))
    // new PageAdminRedFlagAddEdit().load();
    // else if(pageDescription.endsWith("- Reports"))
    // new PageAdminReports().load();
    // else if(pageDescription.endsWith("- Add Report")){
    // matched = false;
    // test.addError("Admin - Add Report does not have a PageObject ???");
    // }
    // else if(pageDescription.endsWith("- Organization"))
    // new PageAdminOrganization().load();
    // else if(pageDescription.endsWith("- Custom Roles"))
    // new PageAdminCustomRoles().load();
    // else if(pageDescription.endsWith("- Add Custom Role"))
    // new PageAdminCustomRoleAddEdit().load();
    // else if(pageDescription.endsWith("- Speed By Street")){
    // matched = false;
    // test.addError("Admin - Speed by Street does not have a PageObject ???");
    // }
    // else if(pageDescription.endsWith("- Account Details"))
    // new PageAdminAccountDetails().load();
    // else
    // matched = false;
    // }
    //
    // if(!matched)
    // test.addError("Automation Framework does not know about the '"+pageDescription+"' page");
    //
    // PageAdminUsers adminUsers = new PageAdminUsers();
    // adminUsers.load();
    // }

    // @Given("I am on the $firstWord $secondWord $thirdWord page")
    // public void givenIAmOnThePage(String firstWord, String secondWord, String thirdWord){
    // MasterTest.print("public void givenIAmOnThePage(String "+firstWord+", String "+secondWord+", String "+thirdWord+")", Level.DEBUG);
    // PageTeamDashboardStatistics dummyPage = new PageTeamDashboardStatistics();
    //
    // if(firstWord.equalsIgnoreCase("live") && secondWord.equalsIgnoreCase("fleet"))
    // dummyPage._link().liveFleet().click();
    // else
    // test.addError("Automation Framework does not know about the '"+firstWord+" "+secondWord+" "+thirdWord+"' page");
    //
    // PageAdminUsers adminUsers = new PageAdminUsers();
    // adminUsers.load();
    // }

    // @When("I enter a valid username password combination")
    // public void whenIEnterAValidUsernamePasswordCombination() {
    // login = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
    // loginPage._textField().userName().type(login.getUsername());// Type valid username
    // loginPage._textField().password().type(login.getPassword());// Type valid password
    // }

    // @When("I attempt to login with a blocked username password combination")
    // public void whenIAttemptToLoginWithABlockedUsernamePasswordCombination() {
    // login = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusInactive);
    // loginPage.loginProcess(login.getUsername(), login.getPassword());
    // }

    @Then("I should get the Access Blocked message")
    @Aliases(values = { "I get the Access Blocked message", "I get the Access Blocked alert", "I get a Access Blocked alert" })
    public void thenIShouldGetAccessBlockedMessage() {
        String BLOCK_TEXT = "Your access has been blocked. If you have any questions regarding this action, contact your organization's tiwiPRO system administrator.";
        loginPage._popUp().loginError()._text().message().assertEquals(BLOCK_TEXT);
    }

    @When("I attempt to login with a empty username password combination")
    public void whenIAttemptToLoginWithAEmptyUsernamePasswordCombination() {
        loginPage.loginProcess("", "");
    }

    @Given("I am not already logged into the portal")
    public void givenIAmNotAlreadyLoggedIntoThePortal() {
        loginPage.verifyNotLoggedIn();
    }

    @When("I navigate directly to the $pageName page")
    public void whenINavigateDirectlyToAGivenPage(String pageName) {
        // TODO: jwimmer: this needs to Dynamically determine the pageObject to .load() based on the pageName variable
        PageMyMessages myMessagesPage = new PageMyMessages();
        myMessagesPage.load();
    }

//    @When("I attempt to login with a valid username password combination")
//    public void whenIAttemptToLoginWithAValidUsernamePasswordCombination() {
//        login = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
//        loginPage.loginProcess(login);
//    }

    @Then("I should end up on the $pageName page")
    public void thenIShouldEndUpOnTheGivenPage(String pageName) {
        // TODO: jwimmer: this needs to Dynamically determine the pageObject to .verify... based on the pageName variable
        PageMyMessages myMessagesPage = new PageMyMessages();
        myMessagesPage.verifyOnPage();
    }

//    @When("I attempt to login with a valid username invalid password combination")
//    public void whenIAttemptToLoginWithAValidUsernameInvalidPasswordCombination() {
//        String validUsername = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive).getUsername();
//        String invalidPassword = AutomationUsers.getUsers().getOneBy(LoginCapability.PasswordInvalid).getPassword();
//
//        loginPage.loginProcess(validUsername, invalidPassword);
//    }

//    @When("I attempt to login with an invalid username valid password combination")
//    public void whenIAttemptToLoginWithAnInvalidUsernameValidPasswordCombination() {
//        String invalidUsername = AutomationUsers.getUsers().getOneBy(LoginCapability.PasswordInvalid).getUsername();
//        String validPassword = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive).getPassword();
//
//        loginPage.loginProcess(invalidUsername, validPassword);
//    }

    @Then("the Login Page fields should be empty")
    public void thenTheLoginPageFieldsShouldBeEmpty() {
        loginPage.verifyOnPage();
        loginPage._textField().userName().assertEquals();
        loginPage._textField().password().assertEquals();
    }

    private String flipCase(String original) {
        char[] charArray = new char[original.length()];
        original.getChars(0, original.length(), charArray, 0);
        for (char ch : charArray) {
            if (Character.isUpperCase(ch))
                Character.toLowerCase(ch);
            else if (Character.isLowerCase(ch))
                Character.toUpperCase(ch);
        }
        return new String(charArray);
    }

    @When("I change the password to an incorrect case")
    public void whenIChangeThePasswordToAnIncorrectCase() {
        String originalPassword = loginPage._textField().password().getText();
        Log.debug("originalPassword: " + originalPassword);

        String incorrectCasePassword = flipCase(originalPassword);
        Log.debug("incorrectCasePassword: " + incorrectCasePassword);
        // TODO: jwimmer: watch the loggers and see if this works... I wouldn't be surprised if we do NOT get the original password correctly???
        loginPage._textField().password().type(incorrectCasePassword);
    }

    @When("the focus should be on the User Name Field")
    public void whenTheFocusShouldBeOnUserNameField() {
        if (!page._textField().userName().hasFocus())
            test.addError("The User Name field does NOT have focus");
    }

    @Then("the focus should be on the Password Field")
    public void thenTheFocusShouldBeOnThePasswordField() {
        if (!loginPage._textField().password().hasFocus())
            test.addError("The Password field does NOT have focus");
    }

    @Then("the focus should be on the Log In Button")
    public void thenTheFocusShouldBeOnTheLogInButton() {
        if (!loginPage._button().logIn().hasFocus())
            test.addError("The log in button does NOT have focus");
    }

    @Then("the focus should be on the Forgot User Name or Password Link")
    public void thenTheFocusShouldBeOnTheForgotUserNameOrPasswordLink() {
        if (!loginPage._link().forgotUsernamePassword().hasFocus())
            test.addError("The forgot username or password link does NOT have focus");
    }

    @Then("the focus should be on the Privacy Policy Link")
    public void thenTheFocusShouldBeOnThePrivacyPolicyLink() {
        if (!loginPage._link().privacyPolicy().hasFocus())
            test.addError("The Password field does NOT have focus");
    }

    @Then("the focus should be on the Legal Notice Link")
    public void thenTheFocusShouldBeOnTheLegalNoticeLink() {
        if (!loginPage._textField().password().hasFocus())
            test.addError("The Legal Notice Link does NOT have focus");
    }

    @Then("the focus should be on the Support Link")
    public void thenTheFocusShouldBeOnTheSupportLink() {
        if (!loginPage._link().support().hasFocus())
            test.addError("The Support link does NOT have focus");
    }

    @Given("I move the focus to the <initialFocusedElement>")
    public void givenIMoveTheFocusToTheinitialFocusedElement(String initialFocusedElement) {
        // TODO: jwimmer: move focus based on initialFocusedElement String var?
        loginPage._textField().userName().focus();
    }

    @Then("the focus should be on the <finalFocusedElement>")
    public void thenTheFocusShouldBeOnThefinalFocusedElement(String finalFocusedElement) {
        // TODO: jwimmer: check focus based on finalFocusedElement String var?
        if (!loginPage._link().support().hasFocus())
            test.addError("The " + finalFocusedElement + " does NOT have focus");
    }

    // REPLACED BY AUTOSTORY
    // @Then("the Login Page should render as expected")
    // public void thenTheLoginPageShouldRenderAsExpected() {
    // loginPage.verifyOnPage();
    // }

    @When("I change the username to an incorrect case")
    public void whenIChangeTheUsernameToAnIncorrectCase() {
        String originalUserName = loginPage._textField().userName().getText();
        Log.debug("originalUserName: " + originalUserName);

        String incorrectCaseUserName = flipCase(originalUserName);
        Log.debug("incorrectCasePassword: " + incorrectCaseUserName);
        // TODO: jwimmer: watch the loggers and see if this works... I wouldn't be surprised if we do NOT get the original password correctly???
        loginPage._textField().password().type(incorrectCaseUserName);
    }

}