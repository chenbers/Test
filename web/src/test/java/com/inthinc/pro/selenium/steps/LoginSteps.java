package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyMessages;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;
import com.inthinc.pro.selenium.testSuites.WebRallyTest;

public class LoginSteps extends RallySteps { 

    PageLogin loginPage = new PageLogin();
    AutomationUser login;
    
    @Given("I am logged in as a $LoginAccessOnly user")
    @Pending
    public void givenIAmLoggedInAsALoginAccessOnlyUser(){
      // PENDING
    }

    
    
    @Given("I am logged in as a user in a role that does have the $UserInfo accesspoint")
    public void loginAsUserWithAccesspoint(String accesspointName){
        //LoginCapabilities.NoAccessPointUserInfo;
        LoginCapability hasThisCapability = null;
        if(accesspointName.equals("UserInfo"))
            hasThisCapability = LoginCapability.AccessPointUserInfo;
            
        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
        
        PageLogin login = new PageLogin();
        login.loginProcess(user);
    }
    
    @Given("I am logged in as a user in a role that does not have the $accesspointName accesspoint")
    public void loginAsUserWithoutAccesspoint(String accesspointName){
        logger.error("I am logged in as a user in a role that does not have "+ accesspointName+" accesspoint");
        LoginCapability hasThisCapability = null;
        if(accesspointName.equals("UserInfo"))
            hasThisCapability = LoginCapability.NoAccessPointUserInfo;//TODO: jwimmer: I do not like this manual mapping???
            
        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
        
        PageLogin login = new PageLogin();
        login.loginProcess(user);
    }
    
    //admin users
    @Given("I am on the \"$pageDescription\" page")
    public void givenIAmOnThePage(String pageDescription){
        logger.debug("public void givenIAmOnThePage(String "+pageDescription+")");
        boolean matched = false;
        PageTeamDashboardStatistics dummyPage  = new PageTeamDashboardStatistics();
        if(pageDescription.equalsIgnoreCase("admin users")) {
            matched = true;
            PageAdminUsers adminUsersPage = new PageAdminUsers();
            adminUsersPage.load();
            
        }
        if(!matched)
            addError("Automation Framework does not know about the '"+pageDescription+"' page");
        
        PageAdminUsers adminUsers = new PageAdminUsers();
        adminUsers.load();
    }
    @Given("I am on the $firstWord $secondWord $thirdWord page")
    public void givenIAmOnThePage(String firstWord, String secondWord, String thirdWord){
        logger.debug("public void givenIAmOnThePage(String "+firstWord+", String "+secondWord+", String "+thirdWord+")");
        PageTeamDashboardStatistics dummyPage  = new PageTeamDashboardStatistics();
        
        if(firstWord.equalsIgnoreCase("live") && secondWord.equalsIgnoreCase("fleet"))
            dummyPage._link().liveFleet().click();
        else
            addError("Automation Framework does not know about the '"+firstWord+" "+secondWord+" "+thirdWord+"' page");
        
        PageAdminUsers adminUsers = new PageAdminUsers();
        adminUsers.load();
    }
    
    @When("I open the Login Page")
    @Pending
    public void whenIOpenTheLoginPage() {
        //TODO: jwimmer: figure out if we REALLY need separate givens and whens?  (these 2 methods DO the same thing)
        loginPage.load();
    }
    @Given("I am on the Login Page $dummyText")
    public void givenIAmOnTheLoginPage(String dummyText) {
        logger.error("Given I am on the Login Page "+dummyText);
        loginPage.load();
    }

    @When("I enter a valid username password combination")
    public void whenIEnterAValidUsernamePasswordCombination() {
        login = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
        loginPage._textField().userName().type(login.getUsername());// Type valid username
        loginPage._textField().password().type(login.getPassword());// Type valid password
    }

    @When("I click log in")
    @Aliases(values={"I click sign in" 
                    ,"I click login"
                    ,"I click the Log In button"
                    ,"I click the Login Button"})
    public void whenIClickSignIn() {
        loginPage._button().logIn().click();
    }

    @Then("I should end up on the Overview page")
    public void thenIShouldEndUpOnTheOverviewPage() {
        PageTeamDashboardStatistics teamDashboardPage = new PageTeamDashboardStatistics();
        teamDashboardPage.verifyOnPage();
        String team = teamDashboardPage._text().teamName().getText();
        assertEquals(login.getTeamName(), team);
    }


    @When("I attempt to login with a blocked username password combination")
    public void whenIAttemptToLoginWithABlockedUsernamePasswordCombination() {
        login = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusInactive);
        loginPage.loginProcess(login.getUsername(), login.getPassword());
    }

    @Then("I should get Access Blocked message")
    public void thenIShouldGetAccessBlockedMessage() {
        String BLOCK_TEXT = "Your access has been blocked. If you have any questions regarding this action, contact your organization's tiwiPRO system administrator.";
        loginPage._popUp().loginError()._text().message().assertEquals(BLOCK_TEXT);
    }

    @Then("I should remain on the Login Page")
    public void thenIShouldRemainOnTheLoginPage() {
        loginPage.verifyOnPage();
    }

    @When("I attempt to login with a empty username password combination")
    public void whenIAttemptToLoginWithAEmptyUsernamePasswordCombination() {
        loginPage.loginProcess("", "");
    }

    @Then("I should get Incorrect Username or Password message")
    public void thenIShouldGetIncorrectUsernameOrPasswordMessage() {
        loginPage._popUp().loginError()._text().message().assertEquals(); //default is Incorrect username or password...
    }

    @Given("I am not already logged into the portal")
    public void givenIAmNotAlreadyLoggedIntoThePortal() {
        loginPage.verifyNotLoggedIn();
    }

    @When("I navigate directly to the $pageName page")
    public void whenINavigateDirectlyToAGivenPage(String pageName) {
        //TODO: jwimmer: this needs to Dynamically determine the pageObject to .load() based on the pageName variable
        PageMyMessages myMessagesPage = new PageMyMessages();
        myMessagesPage.load();
    }

    @When("I attempt to login with a valid username password combination")
    public void whenIAttemptToLoginWithAValidUsernamePasswordCombination() {
        login = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
        loginPage.loginProcess(login);
    }

    @Then("I should end up on the $pageName page")
    public void thenIShouldEndUpOnTheGivenPage(String pageName) {
      //TODO: jwimmer: this needs to Dynamically determine the pageObject to .verify... based on the pageName variable
        PageMyMessages myMessagesPage = new PageMyMessages();
        myMessagesPage.verifyOnPage();
    }

    @When("I attempt to login with a valid username invalid password combination")
    public void whenIAttemptToLoginWithAValidUsernameInvalidPasswordCombination() {
        String validUsername = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive).getUsername();
        String invalidPassword = AutomationUsers.getUsers().getOneBy(LoginCapability.PasswordInvalid).getPassword();
        
        loginPage.loginProcess(validUsername, invalidPassword);
    }

    @When("I attempt to login with an invalid username valid password combination")
    @Pending
    public void whenIAttemptToLoginWithAnInvalidUsernameValidPasswordCombination() {
        String invalidUsername = AutomationUsers.getUsers().getOneBy(LoginCapability.PasswordInvalid).getUsername();
        String validPassword = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive).getPassword();
        
        loginPage.loginProcess(invalidUsername, validPassword);
    }
    
    @When("I close the login error alert message")
    public void whenICloseTheAlertMessage() {
        loginPage._popUp().loginError()._button().close();
    }

    @Then("the Login Page fields should be empty")
    public void thenTheLoginPageFieldsShouldBeEmpty() {
        loginPage.verifyOnPage();
        loginPage._textField().userName().assertEquals();
        loginPage._textField().password().assertEquals();
    }

    private String flipCase(String original){
        char[] charArray = new char[original.length()];
        original.getChars(0, original.length(), charArray, 0);
        for(char ch: charArray){
            if(Character.isUpperCase(ch))
                Character.toLowerCase(ch);
            else if(Character.isLowerCase(ch))
                Character.toUpperCase(ch);
        }
        return new String(charArray);
    }
    @When("I change the password to an incorrect case")
    public void whenIChangeThePasswordToAnIncorrectCase() {
        String originalPassword = loginPage._textField().password().getText();
        logger.debug("originalPassword: "+originalPassword);

        String incorrectCasePassword = flipCase(originalPassword);
        logger.debug("incorrectCasePassword: "+incorrectCasePassword);
        //TODO: jwimmer: watch the loggers and see if this works... I wouldn't be surprised if we do NOT get the original password correctly???
        loginPage._textField().password().type(incorrectCasePassword);
    }

    @Given("I move the focus to the User Name Field")
    public void givenIMoveTheFocusToTheUserNameField() {
        loginPage._textField().userName().focus();
    }

    @Then("the focus should be on the Password Field")
    public void thenTheFocusShouldBeOnThePasswordField() {
        if(!loginPage._textField().password().hasFocus())
            addError("The Password field does NOT have focus");
    }

    @Then("the focus should be on the Log In Button")
    public void thenTheFocusShouldBeOnTheLogInButton() {
        if(!loginPage._button().logIn().hasFocus())
            addError("The log in button does NOT have focus");
    }

    @Then("the focus should be on the Forgot User Name or Password Link")
    public void thenTheFocusShouldBeOnTheForgotUserNameOrPasswordLink() {
        if(!loginPage._link().forgotUsernamePassword().hasFocus())
            addError("The forgot username or password link does NOT have focus");
    }

    @Then("the focus should be on the Privacy Policy Link")
    public void thenTheFocusShouldBeOnThePrivacyPolicyLink() {
        if(!loginPage._link().privacyPolicy().hasFocus())
            addError("The Password field does NOT have focus");
    }

    @Then("the focus should be on the Legal Notice Link")
    public void thenTheFocusShouldBeOnTheLegalNoticeLink() {
        if(!loginPage._textField().password().hasFocus())
            addError("The Legal Notice Link does NOT have focus");
    }

    @Then("the focus should be on the Support Link")
    public void thenTheFocusShouldBeOnTheSupportLink() {
        if(!loginPage._link().support().hasFocus())
            addError("The Support link does NOT have focus");
    }

    @Given("I move the focus to the <initialFocusedElement>")
    public void givenIMoveTheFocusToTheinitialFocusedElement(String initialFocusedElement) {
        //TODO: jwimmer: move focus based on initialFocusedElement String var?
        loginPage._textField().userName().focus();
    }

    @Then("the focus should be on the <finalFocusedElement>")
    public void thenTheFocusShouldBeOnThefinalFocusedElement(String finalFocusedElement) {
        //TODO: jwimmer: check focus based on finalFocusedElement String var?
        if(!loginPage._link().support().hasFocus())
            addError("The "+finalFocusedElement+" does NOT have focus");
    }

    @Then("the Login Page should render as expected")
    public void thenTheLoginPageShouldRenderAsExpected() {
        loginPage.verifyOnPage();
    }

    @When("I change the username to an incorrect case")
    public void whenIChangeTheUsernameToAnIncorrectCase() {
        String originalUserName = loginPage._textField().userName().getText();
        logger.debug("originalUserName: "+originalUserName);

        String incorrectCaseUserName = flipCase(originalUserName);
        logger.debug("incorrectCasePassword: "+incorrectCaseUserName);
        //TODO: jwimmer: watch the loggers and see if this works... I wouldn't be surprised if we do NOT get the original password correctly???
        loginPage._textField().password().type(incorrectCaseUserName);
    }

}