package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;

public class LoginTest extends WebRallyTest {
    String BLOCK_TEXT = "Your access has been blocked. If you have any questions regarding this action, contact your organization's tiwiPRO system administrator.";
    String CORRECT_USERNAME = "dastardly";
    String BLOCKED_USERNAME = "whiplash";
    String CORRECT_PASSWORD = "Muttley";
    String INCORRECT_USERNAME = "notarealusername";
    String INCORRECT_PASSWORD = "abcdef";
    String BAD_CASE_USERNAME = "DASTARDLY";
    String BAD_CASE_PASSWORD = "mUTTley";
    PageLogin pl;

    @Before
    public void before(){
        pl = new PageLogin();
    }
    
    @Test
    public void accessBlockedTest1240() {

        set_test_case("TC1240");

        pl.loginProcess(BLOCKED_USERNAME, CORRECT_PASSWORD);
        pl._popUp().loginError()._text().message().assertEquals(BLOCK_TEXT);
        pl._popUp().loginError()._button().ok().click();
        assertStringContains("login", pl.getCurrentLocation());
        pl._textField().userName().assertEquals("");
        pl._textField().password().assertEquals("");
    }

    @Test
    public void blankUsernameAndPasswordTest1241() {

        set_test_case("TC1241");

        pl.openLogout();
        pl._button().logIn().click();
        pl._popUp().loginError()._text().message().assertEquals();
        pl._popUp().loginError()._button().ok().click();
        assertStringContains("login", pl.getCurrentLocation());
        pl._textField().userName().assertEquals("");
        pl._textField().password().assertEquals("");
    }

    @Test
    public void bookmarkPageTest1242() {
        set_test_case("TC1242");

        savePageLink();

        openSavedPage();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        assertStringContains("dashboard", pl.getCurrentLocation());

        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        String team = ptds._text().teamName().getText();
        ptds._link().myAccount().click();
        PageMyAccount pma = new PageMyAccount();
        pma._text().team().assertEquals(team);
    }
    
    @Test
    public void enterKeyTest1243(){
        set_test_case("TC1243");
        //NOTE!  This test must be the main window!
        pl.openLogout();
        pl._textField().userName().type(CORRECT_USERNAME);
        pl._textField().password().type(CORRECT_PASSWORD);
        pl._textField().userName().focus();
        enterKey();

        assertStringContains("dashboard", pl.getCurrentLocation());

        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        String team = ptds._text().teamName().getText();
        ptds._link().myAccount().click();
        PageMyAccount pma = new PageMyAccount();
        pma._text().team().assertEquals(team);
    }

    @Test
    public void invalidPasswordTest1245() {

        set_test_case("TC1245");

        pl.loginProcess(CORRECT_USERNAME, INCORRECT_PASSWORD);
        pl._popUp().loginError()._text().message().assertEquals();
        pl._popUp().loginError()._button().ok().click();
        assertStringContains("login", pl.getCurrentLocation());
        pl._textField().userName().assertEquals("");
        pl._textField().password().assertEquals("");
    }

    @Test
    public void invalidUsernameTest1246() {

        set_test_case("TC1246");

        pl.loginProcess(INCORRECT_USERNAME, CORRECT_PASSWORD);
        pl._popUp().loginError()._text().message().assertEquals();
        pl._popUp().loginError()._button().ok().click();
        assertStringContains("login", pl.getCurrentLocation());
        pl._textField().userName().assertEquals("");
        pl._textField().password().assertEquals("");
    }

    @Test
    public void buttonTest1247() {
        set_test_case("TC1247");

        pl.openLogout();
        pl._textField().userName().type(CORRECT_USERNAME);// Type valid username
        pl._textField().password().type(CORRECT_PASSWORD);// Type valid password
        pl._button().logIn().click();

        assertStringContains("dashboard", pl.getCurrentLocation());

        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        String team = ptds._text().teamName().getText();
        ptds._link().myAccount().click();
        PageMyAccount pma = new PageMyAccount();
        pma._text().team().assertEquals(team);
    }

    @Test
    public void passwordIncorrectCaseTest1248() {

        set_test_case("TC1248");

        pl.loginProcess(CORRECT_USERNAME, BAD_CASE_PASSWORD);
        pl._popUp().loginError()._text().message().assertEquals();
        pl._popUp().loginError()._button().ok().click();
        assertStringContains("login", pl.getCurrentLocation());
        pl._textField().userName().assertEquals("");
        pl._textField().password().assertEquals("");
    }
    
    @Test
    public void tabOrderTest1249(){
        set_test_case("TC1249");
        //NOTE!  This test must be the main window!
        pl.openLogout();
        pl._textField().userName().focus();
        tabKey();
        if(!pl._textField().password().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on password text field.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pl._button().logIn().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on log-in button.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pl._link().forgotUsernamePassword().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on forgot username/password link.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pl._link().privacyPolicy().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on privacy policy link.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pl._link().legalNotice().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on legal notice link.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pl._link().support().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on support link.", ErrorLevel.FATAL);
        }
    }

    @Test
    public void loginUITest1250() {
        set_test_case("TC1250");
        
        pl.openLogout();

        assertStringContains("login", pl.getCurrentLocation());
        pl._textField().userName().assertPresence(true);
        pl._textField().password().assertPresence(true);
        pl._button().logIn().assertPresence(true);
        pl._link().forgotUsernamePassword().assertPresence(true);
        pl._text().version().assertPresence(true);
    }

    @Test
    public void usernameIncorrectCaseTest1251() {

        set_test_case("TC1251");

        pl.loginProcess(BAD_CASE_USERNAME, CORRECT_PASSWORD);
        pl._popUp().loginError()._text().message().assertEquals();
        pl._popUp().loginError()._button().ok().click();
        assertStringContains("login", pl.getCurrentLocation());
        pl._textField().userName().assertEquals("");
        pl._textField().password().assertEquals("");
    }
}
