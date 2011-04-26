package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.PageLiveFleet;
import com.inthinc.pro.selenium.pageObjects.PageLogin;

//@Ignore
public class JwimmerSandboxTest extends WebTest {
    PageLogin l;
    PageLiveFleet liveFleet;
    String NONADMIN_USERNAME = "jwimmer"; //TODO: jwimmer: dtanner: more candiates for non-page specific enum???
    String NONADMIN_PASSWORD = "password";
    String ADMIN_USERNAME = "mraby";
    String ADMIN_PASSWORD = "password";	
    String EMAIL_KNOWN = "jwimmer@inthinc.com";
    String EMAIL_UNKNOWN = "jaaacen@gmail.com";
    String EMAIL_INVALID = "username_at_domain_dot_tld";


    @Before
    public void setupPage() {
    	l = new PageLogin();
    	liveFleet = new PageLiveFleet();
    }

    @Test
    public void pageToTest_testName_expected() {
    	
    }
    
    @Test
    public void liveFleet_testName_expected() {

    }
    
    @Test
    public void liveFleet_dynamicEnum_expected() {
        int waitTime = 6;
        System.out.println("liveFleet_dynamicEnum_expected: ");
        l.page_login_process("jwimmer", "password");
        liveFleet.page_directURL_load();
        liveFleet._textField.findAddress.type("put some test text in findAddress box.");
        
        liveFleet._textField.findAddress.clear();
        
        liveFleet._textField.findAddress.focus();
        liveFleet._textField.findAddress.type("put");                     pause(waitTime);
        liveFleet._textField.findAddress.type(" some ");                  pause(waitTime);
        liveFleet._textField.findAddress.type(" test text ");             pause(waitTime);
        liveFleet._textField.findAddress.type(" in the");                 pause(waitTime);
        liveFleet._textField.findAddress.type(" findAddress box ");       pause(waitTime);
        liveFleet._button.locate.click();                                 pause(waitTime);
        
        liveFleet._link.sortDispatchByDriver.click();                     pause(waitTime);
        liveFleet._link.sortDispatchByGroup.click();                      pause(waitTime);
        liveFleet._link.sortDispatchByNumber.click();                     pause(waitTime);
        liveFleet._link.sortDispatchByVehicle.click();                    pause(waitTime);
        
        
        liveFleet._select.numNearestVehicles.select(1);   liveFleet._textField.findAddress.type("index(1); five?");
        liveFleet._select.numNearestVehicles.select(2);   liveFleet._textField.findAddress.type("index(2); ten?");
        liveFleet._select.numNearestVehicles.select(3);   liveFleet._textField.findAddress.type("index(3); twentyfive?");
        liveFleet._select.numNearestVehicles.select(4);   liveFleet._textField.findAddress.type("index(4): fifty?");
        liveFleet._select.numNearestVehicles.select(5);   liveFleet._textField.findAddress.type("index(5): one hundred?");
        
        liveFleet._select.numNearestVehicles.select(1);                           liveFleet._textField.findAddress.type("five?"); pause(waitTime);
        liveFleet._select.numNearestVehicles.select("10");                        liveFleet._textField.findAddress.type("fullMatch; ten?"); pause(waitTime);
        liveFleet._select.numNearestVehicles.selectFullMatch("25", 1);            liveFleet._textField.findAddress.type("twentyfive? "); pause(waitTime);
        liveFleet._select.numNearestVehicles.selectPartMatch("5");                liveFleet._textField.findAddress.type("fifty?"); pause(waitTime);
        liveFleet._select.numNearestVehicles.selectPartMatch("0", 3);             liveFleet._textField.findAddress.type("one hundered"); pause(waitTime);
        liveFleet.addError("just a test error... nothing actually went wrong");
        
        
        pause(10);
    }
    
    @Test
    public void liveFleet_changeDefaultViewAsNONAdmin_shouldNotSeeLink() {
    	//TODO: jwimmer: pwehan: dtanner: discuss if this method shows justification of page_bareMinimum_validate()
    	//liveFleet.page_loginFirst_open(NONADMIN_USERNAME, NONADMIN_PASSWORD);
    	liveFleet.page_bareMinimum_validate();//only continue if the page meets bare minimum this step has more validity in LONGER tests... where we want to fail the test as early as possible
    	//assertTrue(!liveFleet.page_admin_validate()); //NON-ADMIN users should NOT see the change link
    }
    
    @Test
    public void liveFleet_changeDefaultViewAsAdmin_canChange() {
    	//TODO: jwimmer: pwehan: dtanner: discuss if this method shows justification of page_bareMinimum)_validate
    	//liveFleet.page_loginFirst_open(ADMIN_USERNAME, ADMIN_PASSWORD);
    	liveFleet.page_bareMinimum_validate();//only continue if the page meets bare minimum
    	//assertTrue(liveFleet.page_admin_validate()); //NON-ADMIN users should NOT see the change link
    }
    
    @Test
    public void liveFleet_fromBookmarkNotLoggedIn_mustLoginFirst() {
    	//TODO: jwimmer: pwehan: dtanner: discuss if this method shows justification of page_directURL_load()
    	//TODO: jwimmer: pwehan: dtanner: discuss if this method shows justification of page_URL_validate()
    	//TODO: jwimmer: pwehan: dtanner: discuss if this method shows justification of page_bareMinimum_validate()
    	liveFleet.page_directURL_load();
    	
    	//asertTrue(!liveFleet.page_URL_validate());	//test should fail on assertion if liveFleet page was loaded without requiring login first
    	//should only continue test if liveFleet was NOT loaded
    	PageLogin login = new PageLogin();
    	login.page_login_process("jwimmer","password");
    	
    	liveFleet.page_URL_validate();		//test should fail on assertion of any page except liveFleet is loaded
    	liveFleet.page_bareMinimum_validate();	//test should fail if liveFleet is loaded but not (bareMinimum)valid
    	//no need for more specific validations on THIS test... but there could be more in more specific tests
    }
    
    @Test
    public void liveFleet_fromBookmarkLoggedIn_goDirectlyToLiveFleet() {
    	//TODO: jwimmer: pwehan: dtanner: discuss if this method shows justification of page_directURL_load()
    	//TODO: jwimmer: pwehan: dtanner: discuss if this method shows justification of page_URL_validate()
    	//TODO: jwimmer: pwehan: dtanner: discuss if this method shows justification of page_bareMinimum_validate()
    	l.page_directURL_load();
    	l.page_bareMinimum_validate();
    	l.page_login_process("jwimmer", "password");
    	//dashboard.page_validate();
    	liveFleet.page_directURL_load();
    	liveFleet.page_URL_validate();		//test should fail on assertion of any page except liveFleet is loaded
    	liveFleet.page_bareMinimum_validate();	//test should fail if liveFleet is loaded but not (bareMinimum)valid
    }
    
    
    @Test
    public void login_nullUsernamePassword_appError() {
        l.page_login_open();
        l.page_logIn_validate();
        l.button_logIn_click();
        l.popup_badCred_validate();
    }

    @Test
    public void login_closeBadCredModal_noModal() {
        l.page_login_open();
        l.page_logIn_validate();
        l.button_logIn_click();
        l.popup_badCred_validate();
        l.button_logInErrorOK_click(); // TODO: jwimmer: DTanner: this method verifies that the modal/popup closed... we should talk on this.
    }

    private void forgotPassword_Scenario_enterEmailClickSend(String emailAddress) {
        l.page_login_open();
        l.link_forgotPassword_click();

        l.textField_forgotPasswordEmail_type(emailAddress);
        l.button_forgotPasswordSend_click(); // TODO: jwimmer: DTanner: this method verifies that the modal/popup closed... we should talk on this.
    }

    @Test
    public void forgotPassword_badEmailManual_incorrectFormat() {
        // login_forgotPasswordScenario_enterEmailClickSend(EMAIL_INVALID);
        l.page_login_open();
        l.link_forgotPassword_click();

        l.textField_forgotPasswordEmail_type(EMAIL_INVALID);
        l.button_forgotPasswordSend_click();
        //l.message_forgotPasswordEmailInvalid_validate();//TODO: ensure that we didn't LOSE functionality when we lost this *_validate() method
    }

    @Test
    public void forgotPassword_badEmail_incorrectFormat() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_INVALID);
        //l.message_forgotPasswordEmailInvalid_validate();//TODO: ensure that we didn't LOSE functionality when we lost this *_validate() method
    }

    @Test
    public void forgotPassword_noEmail_required() {
        forgotPassword_Scenario_enterEmailClickSend(null);
        //l.message_forgotPasswordEmailRequired_validate();//TODO: ensure that we didn't LOSE functionality when we lost this *_validate() method
    }

    @Test
    public void forgotPassword_unknownEmail_incorrect() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_UNKNOWN);
        //l.message_forgotPasswordEmailUnknown_validate();//TODO: ensure that we didn't LOSE functionality when we lost this *_validate() method
    }

    @Test
    public void forgotPassword_usersEmail_success() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_KNOWN);
        l.page_sentForgotPassword_validate();
    }

    @Test
    public void forgotPassword_cancel_closePopup() {
        l.page_login_open();
        l.link_forgotPassword_click();

        l.button_forgotPasswordCancel_click();
        l.page_logIn_validate();
    }

    @Test
    public void login_forgotPassClose_closePopup() {
        l.page_login_open();
        l.link_forgotPassword_click();

        l.button_logInErrorX_click();
        l.page_logIn_validate();
    }
}
