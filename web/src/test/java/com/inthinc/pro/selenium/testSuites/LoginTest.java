//NO LONGER NEEDED, STORY FILE WRITTEN

//package com.inthinc.pro.selenium.testSuites;
//
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import com.inthinc.pro.automation.enums.ErrorLevel;
//import com.inthinc.pro.automation.enums.LoginCapability;
//import com.inthinc.pro.automation.models.AutomationUser;
//import com.inthinc.pro.automation.objects.AutomationUsers;
//import com.inthinc.pro.automation.selenium.Page;
//import com.inthinc.pro.selenium.pageObjects.PageLogin;
//import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
//import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;
//
//public class LoginTest extends WebRallyTest {
//    private String BLOCK_TEXT = "Your access has been blocked. If you have any questions regarding this action, contact your organization's tiwiPRO system administrator.";
//    private String INCORRECT_USERNAME = "notarealusername";
//    private String INCORRECT_PASSWORD = "abcdef";
//    private PageLogin pl;
//
//    @Before
//    public void before(){
//        pl = new PageLogin();
//    }
//    
//    @Test
//    public void accessBlockedTest1240() {
//        set_defect("DE6705");
//        set_test_case("TC1240");
//        AutomationUser login = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusInactive);
//        pl.loginProcess(login);
//        pl._popUp().loginError()._text().message().assertEquals(BLOCK_TEXT);
//        pl._popUp().loginError()._button().ok().click();
//        assertStringContains("login", pl.getCurrentLocation());
//        pl._textField().userName().assertEquals("");
//        pl._textField().password().assertEquals("");
//    }
//
//    @Test
//    public void blankUsernameAndPasswordTest1241() {
//
//        set_test_case("TC1241");
//
//        pl.openLogout();
//        pl._button().logIn().click();
//        pl._popUp().loginError()._text().message().assertEquals();
//        pl._popUp().loginError()._button().ok().click();
//        assertStringContains("login", pl.getCurrentLocation());
//        pl._textField().userName().assertEquals("");
//        pl._textField().password().assertEquals("");
//    }
//
//    @Test
//    public void bookmarkPageTest1242() {
//        set_test_case("TC1242");
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive, LoginCapability.TeamLevelLogin);
//        pl.loginProcess(user);
//        savePageLink();
//        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
//        String team = ptds._text().teamName().getText();
//        assertEquals(user.getTeamName(), team);
//        
//        pl._link().logout().click();
//        
//        openSavedPage();
//        pl.loginProcess(user);
//        assertStringContains("dashboard", pl.getCurrentLocation());
//
//        ptds._link().myAccount().click();
//        PageMyAccount pma = new PageMyAccount();
//        pma._text().team().assertEquals(team);
//    }
//    
//    @Test
//    public void enterKeyTest1243(){
//        set_test_case("TC1243");
//        //NOTE!  This test must be the main window!
//        pl.openLogout();
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive, LoginCapability.TeamLevelLogin);
//        pl._textField().userName().type(user.getUsername());// Type valid username
//        pl._textField().password().type(user.getPassword());// Type valid password
//        pl._textField().userName().focus();
//        enterKey();
//
//        assertStringContains("dashboard", pl.getCurrentLocation());
//
//        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
//        String team = ptds._text().teamName().getText();
//        assertEquals(user.getTeamName(), team);
//        ptds._link().myAccount().click();
//        PageMyAccount pma = new PageMyAccount();
//        pma._text().team().assertEquals(team);
//    }
//
//    @Test
//    public void invalidPasswordTest1245() {
//
//        set_test_case("TC1245");
//
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
//        pl.loginProcess(user.getUsername(), INCORRECT_PASSWORD);
//        pl._popUp().loginError()._text().message().assertEquals();
//        pl._popUp().loginError()._button().ok().click();
//        assertStringContains("login", pl.getCurrentLocation());
//        pl._textField().userName().assertEquals("");
//        pl._textField().password().assertEquals("");
//    }
//
//    @Test
//    public void invalidUsernameTest1246() {
//
//        set_test_case("TC1246");
//
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
//        pl.loginProcess(INCORRECT_USERNAME, user.getPassword());
//        pl._popUp().loginError()._text().message().assertEquals();
//        pl._popUp().loginError()._button().ok().click();
//        assertStringContains("login", pl.getCurrentLocation());
//        pl._textField().userName().assertEquals("");
//        pl._textField().password().assertEquals("");
//    }
//
//    @Test
//    public void buttonTest1247() {
//        set_test_case("TC1247");
//
//        pl.openLogout();
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive, LoginCapability.TeamLevelLogin);
//        pl._textField().userName().type(user.getUsername());// Type valid username
//        pl._textField().password().type(user.getPassword());// Type valid password
//        pl._button().logIn().click();
//
//        assertStringContains("dashboard", pl.getCurrentLocation());
//
//        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
//        String team = ptds._text().teamName().getText();
//        assertEquals(user.getTeamName(), team);
//        ptds._link().myAccount().click();
//        PageMyAccount pma = new PageMyAccount();
//        pma._text().team().assertEquals(team);
//    }
//
//    @Test
//    public void passwordIncorrectCaseTest1248() {
//
//        set_test_case("TC1248");
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
//        pl.loginProcess(user.getUsername(), switchCase(user.getPassword()));
//        pl._popUp().loginError()._text().message().assertEquals();
//        pl._popUp().loginError()._button().ok().click();
//        assertStringContains("login", pl.getCurrentLocation());
//        pl._textField().userName().assertEquals("");
//        pl._textField().password().assertEquals("");
//    }
//    
//    @Ignore  //TODO: run in cloud to determine if tabKey() is working in non-firefox configurations
//    @Test
//    public void tabOrderTest1249(){
//        set_test_case("TC1249");
//        //NOTE!  This test must be the main window!
//        pl.openLogout();
//        pl._textField().userName().focus();
//        tabKey();
//        if(!pl._textField().password().hasFocus()){
//            addError("Incorrect Focus", "Focus is expected to be on password text field.", ErrorLevel.FATAL);
//        }
//        tabKey();
//        if(!pl._button().logIn().hasFocus()){
//            addError("Incorrect Focus", "Focus is expected to be on log-in button.", ErrorLevel.FATAL);
//        }
//        tabKey();
//        if(!pl._link().forgotUsernamePassword().hasFocus()){
//            addError("Incorrect Focus", "Focus is expected to be on forgot username/password link.", ErrorLevel.FATAL);
//        }
//        tabKey();
//        if(!pl._link().privacyPolicy().hasFocus()){
//            addError("Incorrect Focus", "Focus is expected to be on privacy policy link.", ErrorLevel.FATAL);
//        }
//        tabKey();
//        if(!pl._link().legalNotice().hasFocus()){
//            addError("Incorrect Focus", "Focus is expected to be on legal notice link.", ErrorLevel.FATAL);
//        }
//        tabKey();
//        if(!pl._link().support().hasFocus()){
//            addError("Incorrect Focus", "Focus is expected to be on support link.", ErrorLevel.FATAL);
//        }
//    }
//
//    @Test
//    public void loginUITest1250() {
//        set_test_case("TC1250");
//        
//        pl.openLogout();
//
//        assertStringContains("login", pl.getCurrentLocation());
//        pl._textField().userName().assertPresence(true);
//        pl._textField().password().assertPresence(true);
//        pl._button().logIn().assertPresence(true);
//        pl._link().forgotUsernamePassword().assertPresence(true);
//        pl._text().version().assertPresence(true);
//    }
//
//    @Test
//    public void usernameIncorrectCaseTest1251() {
//
//        set_test_case("TC1251");
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
//        pl.loginProcess(switchCase(user.getUsername()), user.getPassword());        
//       
//        pl._popUp().loginError()._text().message().assertEquals();
//        pl._popUp().loginError()._button().ok().click();
//        assertStringContains("login", pl.getCurrentLocation());
//        pl._textField().userName().assertEquals("");
//        pl._textField().password().assertEquals("");
//    }
//    
//    
//    
//    @Test
//    public void cucmberLike_enterKeyTest1243() {
//        //list/create pages that this test will interact with
//        PageLogin loginPage = new PageLogin();
//        PageTeamDashboardStatistics overviewPage = new PageTeamDashboardStatistics();
//        //list/create users that are needed for this test
//        AutomationUser validUser = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
//        loginToUse = validUser;
//        String validUsername = validUser.getUsername();
//        String validPassword = validUser.getPassword();
//        
//        
//        //this is the part ANYONE can read/understand
//        givenI().amOnThe(loginPage);
//        whenI().enterUsernamePassword(validUsername, validPassword);
//        whenI().pressEnterKey();
//        thenI().endUpOnThe(overviewPage);
//    }
//    protected AutomationUser loginToUse = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
//    public LoginActionSteps givenI(){
//        return new LoginActionSteps();
//    }
//    public LoginActionSteps whenI(){
//        return new LoginActionSteps();
//    }
//    public LoginVerifySteps thenI(){
//        return new LoginVerifySteps();
//    }
//    public class MasterVerifySteps {
//        
//    }
//    public class MasterActionSteps{
//        public void pressEnterKey() {
//            pressEnterKey();
//        }
//    }
//    public class PageVerifySteps extends MasterVerifySteps{
//        public void endUpOnThe(Page page){
//            page.validate();
//        }
//    }
//    public class PageActionSteps extends MasterActionSteps{
//        public void amOnThe(Page page){
//            page.load();
//            loginIfNecessary();
//            page.validate();
//        }
//        public void loginIfNecessary(){
//            loginIfNecessary(loginToUse);
//        }
//        public void loginIfNecessary(AutomationUser user){
//            PageLogin loginPage = new PageLogin();
//            if(loginPage._textField().userName().isPresent()){
//                loginPage.loginProcess(user);
//            }   
//        }
//    }
//    public class LoginActionSteps extends PageActionSteps{
//        public void enterUsernamePassword(String username, String password) {
//            PageLogin loginPage = new PageLogin();
//            if(!loginPage._textField().userName().isPresent())
//                loginPage.openLogout();
//            loginPage._textField().userName().type(username);
//            loginPage._textField().password().type(password);
//        }       
//    }
//    public class LoginVerifySteps extends PageVerifySteps{
//        
//    }
//}
