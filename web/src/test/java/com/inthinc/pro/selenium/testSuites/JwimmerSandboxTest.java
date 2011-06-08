package com.inthinc.pro.selenium.testSuites;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import junit.runner.Version;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.inthinc.pro.selenium.pageObjects.PageLiveFleet;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
//@RunWith(ParallelizedRunner.class)
public class JwimmerSandboxTest extends WebTest {
    private PageLogin l;
    private PageLiveFleet liveFleet;
    private String NONADMIN_USERNAME = "jwimmerCASE"; //TODO: jwimmer: dtanner: users/password The problem is with test cases using the same user they CAN end up stomping on each other and inadvertently fail/pass the test
    private String NONADMIN_PASSWORD = "password";
    private String ADMIN_USERNAME = "mraby";
    private String ADMIN_PASSWORD = "password";	
    private String EMAIL_KNOWN = "jwimmer@inthinc.com";
    private String EMAIL_UNKNOWN = "jaaacen@gmail.com";
    private String EMAIL_INVALID = "username_at_domain_dot_tld";

    private String param;
    
//    public JwimmerSandboxTest(String param) {
//        //this.param = param; //TODO: jwimmer: placeholder... determine best use for param if any to get parallelize tests... 
//        //TODO: jwimmer: might be nice if this was NOT needed in each test case...
//    }
//    
//    @Parameters
//    public static Collection data() {
//      Object[][] data = new Object[][] { { "a" } };
//      return Arrays.asList(data);
//    }

    @Before
    public void setupPage() {
    	l = new PageLogin();
    	liveFleet = new PageLiveFleet();
    }

    @Test
    public void liveFleet_byNavBar_openPage() {
        
        l.loginProcess(NONADMIN_USERNAME, NONADMIN_PASSWORD);
        liveFleet._link().liveFleet().click();
        
        liveFleet._textField().findAddress().type("Selenium navigated here by clicking on the liveFleet link... page._link.liveFleet.click() ");
    }
    
    @Ignore
    @Test
    public void liveFleet_sandbox_taeThrownError() {
        int waitTime = 6;
        System.out.println("liveFleet_sandbox_taeThrownError: ");
        l.loginProcess(NONADMIN_USERNAME, NONADMIN_PASSWORD);
        liveFleet.load();
        liveFleet._textField().findAddress().type("put some test text in findAddress box.");
//        
        liveFleet._textField().findAddress().clear();
        
        liveFleet._textField().findAddress().focus();
        liveFleet._textField().findAddress().type("put");                     pause(waitTime, "pausing to WATCH the test");
        liveFleet._textField().findAddress().type(" some ");                  pause(waitTime, "pausing to WATCH the test");
        liveFleet._textField().findAddress().type(" test text ");             pause(waitTime, "pausing to WATCH the test");
        liveFleet._textField().findAddress().type(" in the");                 pause(waitTime, "pausing to WATCH the test");
        liveFleet._textField().findAddress().type(" findAddress box ");       pause(waitTime, "pausing to WATCH the test");
        liveFleet._button().locate().click();                                 pause(waitTime, "pausing to WATCH the test");
        
        liveFleet._link().sortDispatchByDriver().click();                     pause(waitTime, "pausing to WATCH the test");
        liveFleet._link().sortDispatchByGroup().click();                      pause(waitTime, "pausing to WATCH the test");
        liveFleet._link().sortDispatchByNumber().click();                     pause(waitTime, "pausing to WATCH the test");
        liveFleet._link().sortDispatchByVehicle().click();                    pause(waitTime, "pausing to WATCH the test");
        
        
        liveFleet._select().numNearestVehicles().select(1);   liveFleet._textField().findAddress().type("index(1); five?");         pause(waitTime, "pausing to WATCH the test");
        liveFleet._select().numNearestVehicles().select(2);   liveFleet._textField().findAddress().type("index(2); ten?");          pause(waitTime, "pausing to WATCH the test");
        liveFleet._select().numNearestVehicles().select(3);   liveFleet._textField().findAddress().type("index(3); twentyfive?");   pause(waitTime, "pausing to WATCH the test");
        liveFleet._select().numNearestVehicles().select(4);   liveFleet._textField().findAddress().type("index(4): fifty?");        pause(waitTime, "pausing to WATCH the test");
        liveFleet._select().numNearestVehicles().select(5);   liveFleet._textField().findAddress().type("index(5): one hundred?");  pause(waitTime, "pausing to WATCH the test");
        
        liveFleet._select().numNearestVehicles().select(1);                           liveFleet._textField().findAddress().type("index(1): five?");                             pause(waitTime, "pausing to WATCH the test");
        liveFleet._select().numNearestVehicles().select("10");                        liveFleet._textField().findAddress().type("select(10) fullMatch; ten?");                  pause(waitTime, "pausing to WATCH the test");
        liveFleet._select().numNearestVehicles().select("25", 1);                     liveFleet._textField().findAddress().type("full(25, 1) first fullMatch ; twentyfive? ");  pause(waitTime, "pausing to WATCH the test");
        liveFleet._select().numNearestVehicles().selectPartMatch("5");                liveFleet._textField().findAddress().type("part(5);fifty?");                              pause(waitTime, "pausing to WATCH the test");
        liveFleet._select().numNearestVehicles().selectPartMatch("0", 3);             liveFleet._textField().findAddress().type("part(0,3); one hundered?");                    pause(waitTime, "pausing to WATCH the test");
        
//        for(String s: liveFleet._link().driverByName("Orson  Bugg").getMyEnum().getLocators()) {
//            System.out.println("s: "+s);
//        }
        
//        System.out.println("!!!driver by list posistion0 text: "   +liveFleet._link().driverByListPosition(0).getText());
//        System.out.println("!!!driver by list posistion0 visible: "+liveFleet._link().driverByListPosition(0).isVisible());
//        System.out.println("!!!driver by list posistion0 click: "  +liveFleet._link().driverByListPosition(0).click());
        liveFleet.load();
        
        System.out.println("!!!Orson  Bugg link text: "        +liveFleet._link().driverByName("Orson  Bugg").getText());
        System.out.println("!!!Orson Buggy link isVisible? "   +liveFleet._link().driverByName("Orson  Buggy").isVisible());
        System.out.println("!!!Orson Buggy link focus? "       +liveFleet._link().driverByName("Orson  Buggy").focus());
//        System.out.println("about to click Orson Buggy link...");pause(15);
//        System.out.println("!!!Orson Buggy link click? "       +liveFleet._link().driverByName("Orson  Buggy").click()); //TODO: jwimmer: figure out why this is NOT visible? as it appears on the page?  figure out why it isnt' clickable (related to visible I assume)
//        pause(10);System.out.println("clicked Orson Buggy link without dying?");
        liveFleet.load();
        liveFleet._link().driverByName("Orson  Buggy").click();//TODO: jwimmer: why is driverByName link not working AS expected?
        liveFleet.load();
        //liveFleet.addError("just a test error... nothing actually went wrong");

        
        //pause(60);
    }
    
    @Test
    public void liveFleet_changeDefaultViewAsNONAdmin_shouldNotSeeLink() {
        l.load();
        l.loginProcess(NONADMIN_USERNAME, NONADMIN_PASSWORD);
    	liveFleet.load();
    	System.out.println(liveFleet._link().mapBubbleDefaultChangeView().isPresent());
    	assertTrue("mapBubbleChangeView should NOT be visible", !liveFleet._link().mapBubbleDefaultChangeView().isPresent());
    }
    
    @Test
    public void liveFleet_changeDefaultViewAsAdmin_canChange() {
        l.load();
        l.loginProcess(ADMIN_USERNAME, ADMIN_PASSWORD);
        liveFleet.load();
        assertTrue(liveFleet._link().mapBubbleDefaultChangeView().isVisible());
        //TODO: jwimmer: proper test should actually CLICK the change link...
    }
    
    @Test
    public void liveFleet_fromBookmarkNotLoggedIn_mustLoginFirst() {
    	//TODO: jwimmer: dtanner: discuss if this method shows justification of page_directURL_load(String password, String usernamer)... NOT needed, we want a bookmark that returns a STRING that is the URL, that can later be used by some other method navigateTOBookmark
    	//TODO: jwimmer: dtanner: discuss if this method shows justification of page.validate()... it shouldn't be called VALIDATE unless actually validates EVERYTHING expected to be on the page.  a better sanity check is something more like "didPageLoad()" which merely checks the URL for correctness (expected) AND makes sure the appError element in NOT present
        PageLogin login = new PageLogin(); //going to use this later
    	liveFleet.load();
    	assertTrue("login form should be present since this test has NOT yet logged in", login._textField().userName().isPresent());	//test should fail UNLESS the login page was loaded (since the user has not logged in yet)
    	login.loginProcess(NONADMIN_USERNAME, NONADMIN_PASSWORD);
    	liveFleet.validateURL();   //test should fail on assertion of any page except liveFleet is loaded
    	liveFleet._text().liveFleetHeader().validate(); //small check to ensure browser is on the right page.
    }
    
    @Test
    public void liveFleet_fromBookmarkLoggedIn_goDirectlyToLiveFleet() {
        liveFleet.load();
        liveFleet.loginProcess(NONADMIN_USERNAME, NONADMIN_PASSWORD);
        liveFleet.validateURL();    
        liveFleet._text().liveFleetHeader().validate(); //small check to ensure browser is on the right page.

    }
    
    
    @Test
    public void login_nullUsernamePassword_badUserPass() {
        l.load();
        l._button().logIn().click();
        l._popUp().loginError()._text().message().validate("Incorrect user name or password.\n\nPlease try again.");
    }

    @Test
    public void login_closeBadCredModal_noModal() {
        l.load();
        l._button().logIn().click();
        l._popUp().loginError()._text().message().validate("Incorrect user name or password.\n\nPlease try again.");
        l._popUp().loginError()._button().close().click();
    }

    private void forgotPassword_Scenario_enterEmailClickSend(String emailAddress) {
        l.load();
        l._link().forgotUsernamePassword().click();
        
        l._popUp().forgotPassword()._textField().email().type(emailAddress);
        l._popUp().forgotPassword()._button().send().click();
    }

    @Test(timeout=2000) 
    public void forgotPassword_badEmailManual_incorrectFormat() {
        l.load();
        l._link().forgotUsernamePassword().click();
        l._popUp().forgotPassword()._textField().email().type(EMAIL_INVALID);
        l._popUp().forgotPassword()._button().send().click();
        
        l._popUp().forgotPassword()._text().error().validate("Incorrect format (jdoe@tiwipro.com)");
    }

    @Test
    public void forgotPassword_badEmail_incorrectFormat() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_INVALID);
        l._popUp().forgotPassword()._text().error().validate("Incorrect format (jdoe@tiwipro.com)");
    }

    @Test
    public void forgotPassword_noEmail_required() {
        forgotPassword_Scenario_enterEmailClickSend(null);
        l._popUp().forgotPassword()._text().error().validate("Required");
    }

    @Test
    public void forgotPassword_unknownEmail_incorrect() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_UNKNOWN);
        l._popUp().forgotPassword()._text().error().validate("Incorrect e-mail address"); 
    }

    @Test
    public void forgotPassword_usersEmail_success() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_KNOWN);
        l._popUp().messageSent();
    }

    @Test
    public void forgotPassword_cancel_closePopup() {//todo: jwimmer: run this again!!!
        l.load();
        l._link().forgotUsernamePassword().click();
        l._popUp().forgotPassword()._text().header().validate();
        l._popUp().forgotPassword()._text().title().validate();
        l._popUp().forgotPassword()._text().label().validate();  //TODO: jwimmer: to dTanner: FYI, it looks like this CHOKES!!!!!!!does this still choke?
        
        l._popUp().forgotPassword()._button().cancel().click();//TODO: jwimmer: to dTanner: this one fails gracefully (but probably shouldn't fail?)
        //l.validate();  //TODO: jwimmer: need/want a way to fail the test if the forgotPassword popup is STILL open        
    }

    @Test
    public void login_forgotPassClose_closePopup() {
        l.load();

        l._link().forgotUsernamePassword().click();
        pause(10, "waiting for forgotUsernamePassword popup to open");
        //TODO: jwimmer: double check the next line jason
        l._popUp().forgotPassword()._button().close().click();//TODO: jwimmer: to dTanner: this one fails gracefully (but probably shouldn't fail?)
    }
}
