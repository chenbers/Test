package com.inthinc.pro.selenium.testSuites;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEntries;
import com.inthinc.pro.selenium.pageObjects.PageAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleEdit;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleView;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;
import com.inthinc.pro.selenium.pageObjects.PageChangePassword;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceTrips;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageLiveFleet;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsSafety;
import com.inthinc.pro.selenium.pageObjects.PageReportsDevices;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;
import com.inthinc.pro.selenium.pageObjects.PageTeamLiveTeam;
import com.inthinc.pro.selenium.pageObjects.PageTeamOverallScore;
import com.inthinc.pro.selenium.pageObjects.PageTeamStops;
import com.inthinc.pro.selenium.pageObjects.PageTeamTrips;
import com.inthinc.pro.selenium.pageObjects.PageUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceTrips;

@Ignore
public class JwimmerSandboxTest extends WebTest 
{

    private PageLogin l = new PageLogin();
    private PageLiveFleet liveFleet;
    private String username = "jwimmer";
    private String password = "password";
    private String NONADMIN_USERNAME = "jwimmerCASE"; 
    private String NONADMIN_PASSWORD = "password";
    private String ADMIN_USERNAME = "mraby";
    private String ADMIN_PASSWORD = "password";
    private String EMAIL_KNOWN = "jwimmer@inthinc.com";
    private String EMAIL_UNKNOWN = "jaaacen@gmail.com";
    private String EMAIL_INVALID = "username_at_domain_dot_tld";
    PageAdminUsers users = new PageAdminUsers();
    private String param;

    @Before
    public void setupPage() {

    }

@Test
public void sometest() {

}


    @Test
    public void adminVehicle_smokeTest() {
        l.loginProcess(username, password);
        PageAdminVehicles list = new PageAdminVehicles();
        PageAdminVehicleView view = new PageAdminVehicleView(); 
        list._link().admin().click();
        list._link().adminVehicles().click();
        list._textField().search().type("jwimmer");
        list._button().search().click();//DONE: worked a 2 second pause into AdminTables.AdminTablesButtons.search.click()  (COULD justify creation of AjaxClickableObject?)
        //pause(2, "wait until table refreshes before trying to click on FILTERED row");
        //list._link().vehicleColumn().click(1); //this is not implemented in PageAdminVehicles (yet)
        list._link().entryVehicleId().row(1).click();
        //this.open("/app/admin/vehicle/37698"); // FORCING to page since PageAdminVehicles.java does not provide access to the table content (yet)
        //this.open("/app/admin/vehicle/37698");
        // pause(10, "did I get to my vehicle page");
        // view._link().SpeedAndSensitivityTab().click();
        // view._link().detailsTab().click();
        System.out.println(view._text().VIN().getText());
        System.out.println(view._text().make().getText());
        System.out.println(view._text().model().getText());
        System.out.println(view._text().year().getText());
        System.out.println(view._text().color().getText());
        System.out.println(view._text().weight().getText());
        System.out.println(view._text().licence().getText());
        System.out.println(view._text().state().getText());
        System.out.println(view._text().odometer().getText());
        System.out.println(view._text().zone().getText());
        System.out.println(view._text().eCallPhone().getText());
        System.out.println(view._text().autoLogOff().getText());
        System.out.println(view._text().vehicleID().getText());
        System.out.println(view._text().status().getText());
        System.out.println(view._text().team().getText());
        System.out.println(view._text().driver().getText());
        System.out.println(view._text().device().getText());

        view._link().speedAndSensitivityTab().click();
        System.out.println(view._text().hardAccel().getText());
        System.out.println(view._text().hardBump().getText());
        System.out.println(view._text().hardBrake().getText());
        System.out.println(view._text().unsafeTurn().getText());
        System.out.println(view._text().idlingThreshold().getText());
        System.out.println(view._text().unsafeTurn().getText());
        
        System.out.println("idle mentoring checkbox: "+view._checkbox().idleMentoring().isChecked());

        System.out.println("05: "+view._textField().notifyWhenExceeding05MPHBy().getText());//DONE:fixed: index was off
        System.out.println("10: "+view._textField().notifyWhenExceeding10MPHBy().getText());
        System.out.println("15: "+view._textField().notifyWhenExceeding15MPHBy().getText());
        System.out.println("20: "+view._textField().notifyWhenExceeding20MPHBy().getText());
        System.out.println("25: "+view._textField().notifyWhenExceeding25MPHBy().getText());
        System.out.println("30: "+view._textField().notifyWhenExceeding30MPHBy().getText());
        System.out.println("35: "+view._textField().notifyWhenExceeding35MPHBy().getText());
        System.out.println("40: "+view._textField().notifyWhenExceeding40MPHBy().getText());
        System.out.println("45: "+view._textField().notifyWhenExceeding45MPHBy().getText());
        System.out.println("50: "+view._textField().notifyWhenExceeding50MPHBy().getText());
        System.out.println("55: "+view._textField().notifyWhenExceeding55MPHBy().getText());
        System.out.println("60: "+view._textField().notifyWhenExceeding60MPHBy().getText());
        System.out.println("65: "+view._textField().notifyWhenExceeding65MPHBy().getText());
        System.out.println("70: "+view._textField().notifyWhenExceeding70MPHBy().getText());
        System.out.println("75: "+view._textField().notifyWhenExceeding75MPHBy().getText());
        

        view._button().delete().click();//DONE: added isVisible check to locator failover
        view._popUp().delete()._button().cancel().click();//DONE: fixed: popup needed page var

        view._button().edit().click();//DONE: added isVisible check to locator failover
        PageAdminVehicleEdit edit = new PageAdminVehicleEdit();
        // edit._textField().VIN().type("here");
        // edit._textField().make().type("here");
        // edit._textField().model().type("here");
        // edit._textField().color().type("here");
        // edit._textField().weight().type("here");
        // edit._textField().licence().type("here");
        // edit._textField().odometer().type("here");
        // edit._textField().eCallPhone().type("here");
        // edit._textField().vehicleID().type("here");
        //        
        // edit._dropDown().team().selectPartMatch("alt");
        // edit._dropDown().team().select(1);
        // edit._dropDown().team().select(3);
        // edit._dropDown().team().select(5);
        //        
        // edit._dropDown().year().select(1);
        // edit._dropDown().year().select(2);
        //        
        // edit._dropDown().state().select(1);
        // edit._dropDown().state().select(2);

//        edit._dropDown().status().select(1);
//        edit._dropDown().status().select(2);
//
//        edit._dropDown().zone().select(1);
//        edit._dropDown().zone().select(2);

        edit._link().speedAndSensitivityTab().click();
//
        edit._textField().notifyWhenExceeding05MPHBy().type("3");
        edit._textField().notifyWhenExceeding10MPHBy().type("3");
        edit._textField().notifyWhenExceeding15MPHBy().type("3");
        edit._textField().notifyWhenExceeding20MPHBy().type("3");
        edit._textField().notifyWhenExceeding25MPHBy().type("3");
        edit._textField().notifyWhenExceeding30MPHBy().type("3");
        edit._textField().notifyWhenExceeding35MPHBy().type("3");
        edit._textField().notifyWhenExceeding40MPHBy().type("3");
        edit._textField().notifyWhenExceeding45MPHBy().type("3");
        edit._textField().notifyWhenExceeding50MPHBy().type("3");
        edit._textField().notifyWhenExceeding55MPHBy().type("3");
        edit._textField().notifyWhenExceeding60MPHBy().type("3");
        edit._textField().notifyWhenExceeding65MPHBy().type("3");
        edit._textField().notifyWhenExceeding70MPHBy().type("3");
        edit._textField().notifyWhenExceeding75MPHBy().type("3");
//
//        edit._textField().notifyWhenExceeding05MPHBy().type("3");
//        edit._textField().notifyOnHardAccel().type("4");
//        edit._textField().notifyOnHardBrake().type("5");
//        edit._textField().notifyOnHardBump().type("7");
//        edit._textField().notifyOnUnsafeTurn().type("9");
        
        edit._textField().notifyOnIdlingTimeout().type("1");//TODO: slider values are a little wonky?
        edit._textField().notifyOnIdlingTimeout().type("a");
        edit._textField().notifyOnIdlingTimeout().type("b");
        edit._textField().notifyOnIdlingTimeout().type("c");
        edit._textField().notifyOnIdlingTimeout().type("d");
        edit._textField().notifyOnIdlingTimeout().type("e");
        edit._textField().notifyOnIdlingTimeout().type("0");
        edit._textField().notifyOnIdlingTimeout().type("2");
        edit._textField().notifyOnIdlingTimeout().type("2.6");
        edit._textField().notifyOnIdlingTimeout().type("0");
        edit._textField().notifyOnIdlingTimeout().type("3");
        edit._textField().notifyOnIdlingTimeout().type("3.75");
        edit._checkBox().idleMentoring().check();//DONE: checkbox ID was wrong
        edit._checkBox().idleMentoring().uncheck();
        
        pause(10, "end of test pause");


    }

  


    @Test
    public void notificationsSafety_teamDropNotReplacingLocator() {
        PageNotificationsSafety page = new PageNotificationsSafety();
        page.loginProcess("jwimmer", "password");
        page.load(); // TODO: jwimmer: WHY is this NOT loading the SAFETY page?
        page._link().safety().click();

        page._dropDown().team().selectPartMatch("Software - Salt Lake");
        pause(3, "check to see if the right team was clicked");
        page._dropDown().team().select(1);
        pause(3, "check to see if the right team was clicked");
        page._dropDown().team().select("Top");
        pause(3, "check to see if the right team was clicked");
        page._dropDown().team().select(2);
        pause(3, "check to see if the right team was clicked");
        page._dropDown().team().select(3);
        pause(3, "check to see if the right team was clicked");
        page._dropDown().team().select(4);
        pause(3, "check to see if the right team was clicked");
        page._dropDown().team().select(5);
        pause(3, "check to see if the right team was clicked");
        page._dropDown().team().select(6);
        pause(3, "check to see if the right team was clicked");
    }

   

    @Test
    public void liveFleet_sandbox_taeThrownError() {
        int waitTime = 2;
        System.out.println("liveFleet_sandbox_taeThrownError: ");
        l.loginProcess(username, password);
        liveFleet = new PageLiveFleet();
        liveFleet.load();
        liveFleet._textField().findAddress().type("put some test text in findAddress box.");
        //        
        liveFleet._textField().findAddress().clear();

//        liveFleet._textField().findAddress().focus();
//        liveFleet._textField().findAddress().type("put");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._textField().findAddress().type(" some ");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._textField().findAddress().type(" test text ");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._textField().findAddress().type(" in the");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._textField().findAddress().type(" findAddress box ");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._button().locate().click();
//        pause(waitTime, "pausing to WATCH the test");
//
//        liveFleet._link().sortByDriver().click();
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._link().sortByGroup().click();
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._link().sortByNumber().click();
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._link().sortByVehicle().click();
//        pause(waitTime, "pausing to WATCH the test");
//
//        liveFleet._dropDown().numNearestVehicles().select(1);
//        liveFleet._textField().findAddress().type("index(1); five?");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._dropDown().numNearestVehicles().select(2);
//        liveFleet._textField().findAddress().type("index(2); ten?");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._dropDown().numNearestVehicles().select(3);
//        liveFleet._textField().findAddress().type("index(3); twentyfive?");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._dropDown().numNearestVehicles().select(4);
//        liveFleet._textField().findAddress().type("index(4): fifty?");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._dropDown().numNearestVehicles().select(5);
//        liveFleet._textField().findAddress().type("index(5): one hundred?");
//        pause(waitTime, "pausing to WATCH the test");
//
//        liveFleet._dropDown().numNearestVehicles().select(1);
//        liveFleet._textField().findAddress().type("index(1): five?");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._dropDown().numNearestVehicles().select("10");
//        liveFleet._textField().findAddress().type("select(10) fullMatch; ten?");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._dropDown().numNearestVehicles().select("25", 1);
//        liveFleet._textField().findAddress().type("full(25, 1) first fullMatch ; twentyfive? ");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._dropDown().numNearestVehicles().selectPartMatch("5");
//        liveFleet._textField().findAddress().type("part(5);fifty?");
//        pause(waitTime, "pausing to WATCH the test");
//        liveFleet._dropDown().numNearestVehicles().selectPartMatch("0", 3);
//        liveFleet._textField().findAddress().type("part(0,3); one hundered?");
//        pause(waitTime, "pausing to WATCH the test");

        // for(String s: liveFleet._link().driverByName("Orson  Bugg").getMyEnum().getLocators()) {
        // System.out.println("s: "+s);
        // }

        // System.out.println("!!!driver by list posistion0 text: " +liveFleet._link().driverByListPosition(0).getText());
        // System.out.println("!!!driver by list posistion0 visible: "+liveFleet._link().driverByListPosition(0).isVisible());
        // System.out.println("!!!driver by list posistion0 click: " +liveFleet._link().driverByListPosition(0).click());
        liveFleet.load();

        System.out.println("!!!Orson  Bugg link text: " + liveFleet._link().entryDriverByPosition().row(1).getText());
        //System.out.println("!!!Orson Buggy link isVisible? " + liveFleet._link().driverByName("Orson  Buggy").isVisible());
        //System.out.println("!!!Orson Buggy link focus? " + liveFleet._link().driverByName("Orson  Buggy").focus());
        // System.out.println("about to click Orson Buggy link...");pause(15);
        // System.out.println("!!!Orson Buggy link click? " +liveFleet._link().driverByName("Orson  Buggy").click()); //TODO: jwimmer: figure out why this is NOT visible? as it
        // appears on the page? figure out why it isnt' clickable (related to visible I assume)
        // pause(10);System.out.println("clicked Orson Buggy link without dying?");
        liveFleet.load();
//        System.out.println("!!!"+liveFleet._link().entryDriverByName("Ghost Rider").getText());
//        System.out.println("!!!"+liveFleet._link().entryDriverByName("Ghost  Rider").getText());
//        System.out.println("!!!"+liveFleet._link().entryDriverByName("Steve Kumer").getText());
//        System.out.println("!!!"+liveFleet._link().entryDriverByName("Steve  Kumer").getText());
//        System.out.println("!!!"+liveFleet._link().entryDriverByName("Orson  Buggy").getText());
//        System.out.println("!!!"+liveFleet._link().entryDriverByName("Eric").getText());
//        System.out.println("!!!"+liveFleet._link().entryDriverByName("Eric  Caps").getText());// TODO: jwimmer: why is driverByName link not working AS expected?
        liveFleet.load();
        // liveFleet.addError("just a test error... nothing actually went wrong");   Orson Buggy

        // pause(60);
    }

    @Test
    @Ignore
    public void liveFleet_changeDefaultViewAsNONAdmin_shouldNotSeeLink() {
        l.load();
        l.loginProcess(NONADMIN_USERNAME, NONADMIN_PASSWORD);
        liveFleet.load();
        System.out.println(liveFleet._link().mapBubbleChangeDefaultView().isPresent());
        assertTrue(!liveFleet._link().mapBubbleChangeDefaultView().isPresent(), "mapBubbleChangeView should NOT be visible");
    }

    @Test
    @Ignore
    public void liveFleet_changeDefaultViewAsAdmin_canChange() {
        l.load();
        l.loginProcess(ADMIN_USERNAME, ADMIN_PASSWORD);
        liveFleet.load();
        liveFleet._link().mapBubbleChangeDefaultView().assertVisibility(true);
        // TODO: jwimmer: proper test should actually CLICK the change link...
    }

    @Test
    @Ignore
    public void liveFleet_fromBookmarkNotLoggedIn_mustLoginFirst() {
        // TODO: jwimmer: dtanner: discuss if this method shows justification of page_directURL_load(String password, String usernamer)... NOT needed, we want a bookmark that
        // returns a STRING that is the URL, that can later be used by some other method navigateTOBookmark
        // TODO: jwimmer: dtanner: discuss if this method shows justification of page.validate()... it shouldn't be called VALIDATE unless actually validates EVERYTHING expected to
        // be on the page. a better sanity check is something more like "didPageLoad()" which merely checks the URL for correctness (expected) AND makes sure the appError element
        // in NOT present
        PageLogin login = new PageLogin(); // going to use this later
        liveFleet.load();
        assertTrue(login._textField().userName().isPresent(), "login form should be present since this test has NOT yet logged in"); // test should fail UNLESS the login page was
                                                                                                                                     // loaded (since the user has not logged in
                                                                                                                                     // yet)
        login.loginProcess(NONADMIN_USERNAME, NONADMIN_PASSWORD);
        //liveFleet.validateURL();//TODO WHY DID VALIDATEURL GO AWAY // test should fail on assertion of any page except liveFleet is loaded
        liveFleet._text().headerLiveFleet().validate(); // small check to ensure browser is on the right page.
    }

    @Test
    @Ignore
    public void liveFleet_fromBookmarkLoggedIn_goDirectlyToLiveFleet() {
        liveFleet.load();
        liveFleet.loginProcess(NONADMIN_USERNAME, NONADMIN_PASSWORD);
        //liveFleet.validateURL();//TODO WHY DID VALIDATEURL GO AWAY
        liveFleet._text().headerLiveFleet().validate(); // small check to ensure browser is on the right page.
    }

    @Test
    @Ignore
    public void login_nullUsernamePassword_badUserPass() {
        l.load();
        l._button().logIn().click();
        l._popUp().loginError()._text().message().validate("Incorrect user name or password.\n\nPlease try again.");
    }

    @Test
    @Ignore
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

    @Test
    @Ignore
    public void forgotPassword_badEmailManual_incorrectFormat() {
        l.load();
        l._link().forgotUsernamePassword().click();
        l._popUp().forgotPassword()._textField().email().type(EMAIL_INVALID);
        l._popUp().forgotPassword()._button().send().click();

        l._popUp().forgotPassword()._text().error().validate("Incorrect format (jdoe@tiwipro.com)");
    }

    @Test
    @Ignore
    public void forgotPassword_badEmail_incorrectFormat() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_INVALID);
        l._popUp().forgotPassword()._text().error().validate("Incorrect format (jdoe@tiwipro.com)");
    }

    @Test
    @Ignore
    public void forgotPassword_noEmail_required() {
        forgotPassword_Scenario_enterEmailClickSend(null);
        l._popUp().forgotPassword()._text().error().validate("Required");
    }

    @Test
    @Ignore
    public void forgotPassword_unknownEmail_incorrect() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_UNKNOWN);
        l._popUp().forgotPassword()._text().error().validate("Incorrect e-mail address");
    }

    @Test
    @Ignore
    public void forgotPassword_usersEmail_success() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_KNOWN);
        l._popUp().messageSent();
    }

    @Test
    @Ignore
    public void forgotPassword_cancel_closePopup() {// todo: jwimmer: run this again!!!
        l.load();
        l._link().forgotUsernamePassword().click();
        l._popUp().forgotPassword()._text().header().validate();
        l._popUp().forgotPassword()._text().title().validate();
        // l._popUp().forgotPassword()._text().label().validate();//TODO: jwimmer: to dTanner: FYI, it looks like this CHOKES!!!!!!!does this still choke? YES, it looks like xPath
        // to me tanner, will you look at this one

        l._popUp().forgotPassword()._button().cancel().click();
        // l.validate(); //TODO: jwimmer: need/want a way to fail the test if the forgotPassword popup is STILL open
    }

    @Test
    @Ignore
    public void login_forgotPassClose_closePopup() {
        l.load();

        l._link().forgotUsernamePassword().click();
        pause(10, "waiting for forgotUsernamePassword popup to open");
        // TODO: jwimmer: double check the next line jason
        l._popUp().forgotPassword()._button().close().click();// TODO: jwimmer: to dTanner: this one fails gracefully (but probably shouldn't fail?)
    }

    @Test
    @Ignore
    public void adminVehicleView_sanityCheck_shouldPass() {

        PageAdminVehicleView page = new PageAdminVehicleView();
        page.loginProcess("jwimmer", "password");
        page.load(37689);

        System.out.println("autologoff: " + page._text().autoLogOff().getText());
        System.out.println("color: " + page._text().color());
        System.out.println("device: " + page._text().device());
        System.out.println("driver: " + page._text().driver());
        System.out.println("eCallPhone: " + page._text().eCallPhone());
        System.out.println("hardAccel: " + page._text().hardAccel());
        System.out.println("hardBrake: " + page._text().hardBrake());
        System.out.println("hardBump: " + page._text().hardBump());
        System.out.println("licence: " + page._text().licence());
        System.out.println("make: " + page._text().make());
        System.out.println("model: " + page._text().model());
        System.out.println("odometer: " + page._text().odometer());
        System.out.println("product: " + page._text().product());
        System.out.println("state: " + page._text().state());
        System.out.println("status: " + page._text().status());
        System.out.println("team: " + page._text().team());
        System.out.println("unsafeTurn: " + page._text().unsafeTurn());
        System.out.println("vehicleID: " + page._text().vehicleID());
        System.out.println("VIN: " + page._text().VIN());
        System.out.println("weight: " + page._text().weight());
        System.out.println("year: " + page._text().year());
        System.out.println("zone: " + page._text().zone());
        // pause(10, "end of test");
    }

    @Ignore
    @Test
    public void pageAdminVehicleView_smokeTest_informPercentageExercised() {
        PageAdminVehicleView page = new PageAdminVehicleView();
        List<String> potentialActions = getPotentialActionsByReflex(page);

        new PageLogin().loginProcess("jwimmer", "password");
        page.load(37689);

    }

    private ArrayList<String> getPotentialActionsByReflex(AbstractPage page) {
        ArrayList<String> results = new ArrayList<String>();

        int pageTypes = 0;
        int elements = 0;
        int actions = 0;
        Class<? extends AbstractPage> clazz = page.getClass();
        // System.out.println();
        // System.out.println("PageObject: "+clazz.getCanonicalName());
        // System.out.println("delcaring class? "+loadpage.getDeclaringClass().getCanonicalName());

        for (Method method : clazz.getMethods()) {
            // System.out.println("methodName: "+method.getName());
            StringBuffer params = new StringBuffer();
            for (Object o : method.getParameterTypes()) {
                // System.out.println("param"+o);
                params.append(o + "; ");
            }
            // System.out.println(clazz.getCanonicalName()+"."+method.getName()+"( "+params.toString()+") ");

            if (method.getName().startsWith("_")) {
                pageTypes++;
                // System.out.println("_type: "+method.getName());
                // System.out.println(" returns "+method.getReturnType().getCanonicalName());//method.getReturnType().getMethods()
                // System.out.println("declaredMethods.size: "+method.getReturnType().getClass().);
                // System.out.println("methods.size: "+method.getReturnType().getClass().getMethods().length);
                // System.out.println("declaredMethods.size: "+method.getReturnType().getClass().getDeclaredMethods().length);
                // System.out.println("no class methods.size: "+method.getReturnType().getMethods().length);
                // System.out.println("no class declaredMethods.size: "+method.getReturnType().getDeclaredMethods().length);
                for (Method elementName : method.getReturnType().getDeclaredMethods()) {
                    elements++;
                    // System.out.println("elementName: "+elementName.getName());

                    Class[] interfaces = elementName.getReturnType().getInterfaces();
                    for (Class interphace : interfaces) {

                        // System.out.println("interface name: "+interphace.getName());
                        // System.out.println("interface Cannonical name: "+interphace.getCanonicalName());

                        if (interphace.getCanonicalName().contains("ElementInterface")) {
                            Method[] ourMethods = interphace.getMethods();
                            for (Method ourMethod : ourMethods) {
                                // System.out.println("action: "+clazz.getCanonicalName()+"."+method.getName()+"()."+elementName.getName()+"()."+ourMethod.getName()+"()");
                                actions++;
                                results.add(clazz.getCanonicalName() + " " + method.getName() + " " + elementName.getName() + " " + ourMethod.getName());
                            }
                        }
                        // System.out.println("interphace: "+interphace);
                    }
                }
            }
        }
        System.out.println("finished PageObject: " + clazz.getCanonicalName());

        for (String result : results) {
            System.out.println("  result: " + result);
        }
        System.out.println("found: " + pageTypes + " pagetypes, " + elements + " elements, invoked " + actions + " actions.");

        return results;
    }

    @Ignore
    @Test
    public void pageAdminUsers_smokeTest() {
        PageAdminUsers page = new PageAdminUsers();
        int row = 1;
        page._text().tableEntry(AdminUsersEntries.FULL_NAME).row(row).getText();
        page._text().tableEntry(AdminUsersEntries.FULL_NAME).row(row).getText();
    }

    @Ignore
    @Test
    public void page_testScenario_expectedResult() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
        PageLogin login = new PageLogin();
        login.loginProcess(username, password);
        AbstractPage instPage = new PageAdminVehicleView();
        instPage = new PageDriverPerformanceStyle();
        instPage = new PageDriverPerformance();
        instPage = new PageChangePassword();
        instPage = new PageAdminVehicles();
        instPage = new PageAdminUsers();
        // ((PageAdminUsers)instPage)._link().tableEntry(AdminUsersEntries.FULL_NAME).getText(1);
        // PageAdminUsers page = new PageAdminUsers();
        instPage = new PageAdminUserDetails();
        instPage = new PageAddEditUser();
       // instPage = new PageDriverReport();
        instPage = new PageVehiclePerformanceStyle();
        instPage = new PageVehiclePerformanceTrips();
        instPage = new PageVehiclePerformanceSpeed();
        //instPage = new PageIdlingReport();
        instPage = new PageDriverPerformanceTrips();
        instPage = new PageDriverPerformanceSpeed();
        instPage = new PageVehiclePerformanceSeatBelt();
        instPage = new PageExecutiveDashboard();
        instPage = new PageDriverPerformanceSeatBelt();
        instPage = new PageMyAccount();
        instPage = new PageNotificationsRedFlags();
        instPage = new PageVehiclePerformance();
        instPage = new PageUserDetails();
        instPage = new PageLogin();
        //instPage = new PageVehicleReport();
        instPage = new PageLiveFleet();
        instPage = new PageTeamTrips();
        instPage = new PageTeamStops();
        instPage = new PageTeamOverallScore();
        instPage = new PageTeamLiveTeam();
        instPage = new PageTeamDashboardStatistics();
        instPage = new PageReportsDevices();
        instPage = new PageAdminVehicleView();

        System.out.println("page_testScenario_expectedResult()");
        int pages = 0;
        int pageTypes = 0;
        int elements = 0;
        int actions = 0;
        int VEHICLE_ID = 37689;
        int DRIVER_ID = 20842;

        ArrayList<String> ourMethodExceptions = new ArrayList<String>();
        ArrayList<String> ourMethodSkips = new ArrayList<String>();
        ArrayList<String> ourMethodSuccess = new ArrayList<String>();
        ArrayList<String> elementNameExceptions = new ArrayList<String>();

        try {
            for (Class<? extends AbstractPage> clazz : AbstractPage.instantiatedPages) {
                AbstractPage page = clazz.getConstructor().newInstance();
                if (clazz.getCanonicalName().endsWith("PageLogin")) {
                    // reflectively operating on the loginpage can cause the browser to no longer be logged in, which makes ALL secure page tests fail
                    continue;
                }
                System.out.println();
                System.out.println("PageObject: " + clazz.getCanonicalName());
                Method loadpage = clazz.getMethod("load");
                if (loadpage.isAnnotationPresent(Deprecated.class)) {
                    loadpage = clazz.getMethod("load", new Class[] { Integer.class });
                    if (clazz.getCanonicalName().endsWith("PageAdminVehicleView"))
                        loadpage.invoke(page, VEHICLE_ID);
                    else if (clazz.getCanonicalName().endsWith("PageDriverPerformanceStyle"))
                        loadpage.invoke(page, DRIVER_ID);

                } else {
                    loadpage.invoke(page);
                }
                pages++;
                // System.out.println("loadpage: "+loadpage);
                // System.out.println("delcaring class? "+loadpage.getDeclaringClass().getCanonicalName());

                for (Method method : clazz.getMethods()) {
                    // System.out.println("methodName: "+method.getName());
                    StringBuffer params = new StringBuffer();
                    for (Object o : method.getParameterTypes()) {
                        // System.out.println("param"+o);
                        params.append(o + "; ");
                    }
                    // System.out.println(clazz.getCanonicalName()+"."+method.getName()+"( "+params.toString()+") ");

                    if (method.getName().startsWith("_")) {
                        pageTypes++;
                        System.out.println("_type: " + method.getName());
                        Object typeObject = method.invoke(page);
                        // System.out.println(" returns "+method.getReturnType().getCanonicalName());//method.getReturnType().getMethods()
                        // System.out.println("declaredMethods.size: "+method.getReturnType().getClass().);
                        // System.out.println("methods.size: "+method.getReturnType().getClass().getMethods().length);
                        // System.out.println("declaredMethods.size: "+method.getReturnType().getClass().getDeclaredMethods().length);
                        // System.out.println("no class methods.size: "+method.getReturnType().getMethods().length);
                        // System.out.println("no class declaredMethods.size: "+method.getReturnType().getDeclaredMethods().length);
                        for (Method elementName : method.getReturnType().getDeclaredMethods()) {
                            elements++;
                            System.out.println("elementName: " + elementName.getName());
                            Object elementObject = null;
                            try {
                                elementObject = elementName.invoke(typeObject);
                            } catch (IllegalArgumentException e1) {
                                elementNameExceptions.add(clazz.getCanonicalName() + " " + method.getName() + " " + elementName.getName() + "  : " + e1);
                            }
                            Class[] interfaces = elementName.getReturnType().getInterfaces();
                            for (Class interphace : interfaces) {
                                boolean isAutomationInterface = false;
                                // System.out.println("interface name: "+interphace.getName());
                                // System.out.println("interface Cannonical name: "+interphace.getCanonicalName());
                                if (interphace.getCanonicalName().contains("ElementInterface")) {
                                    isAutomationInterface = true;
                                    // method.invoke(obj, args)
                                }
                                if (isAutomationInterface) {
                                    Method[] ourMethods = interphace.getMethods();
                                    for (Method ourMethod : ourMethods) {
                                        System.out.println("action: " + clazz.getCanonicalName() + "." + method.getName() + "()." + elementName.getName() + "()." + ourMethod.getName() + "()");
                                        if (ourMethod.getParameterTypes().length == 0) {
                                            try {
                                                System.out.println("length: " + ourMethod.getParameterTypes().length);
                                                ourMethod.invoke(elementObject);
                                                ourMethodSuccess.add(clazz.getCanonicalName() + " " + method.getName() + " " + elementName.getName() + " " + ourMethod.getName());
                                                System.out.println("~");
                                            } catch (Exception e) {
                                                ourMethodExceptions.add(clazz.getCanonicalName() + " " + method.getName() + " " + elementName.getName() + " " + ourMethod.getName() + " : " + e);
                                            }
                                            actions++;
                                        } else {
                                            StringBuffer ourMethodParams = new StringBuffer();
                                            for (Object o : ourMethod.getParameterTypes()) {
                                                // System.out.println("param: "+o);
                                                ourMethodParams.append(o + ", ");
                                            }
                                            // System.out.println("length: "+ourMethod.getParameterTypes().length);
                                            ourMethodSkips.add("could not predict params for " + ourMethod.getName() + "(" + ourMethodParams + ");");
                                        }
                                    }
                                }
                                // System.out.println("interphace: "+interphace);
                            }
                        }
                    }
                }
                System.out.println("finished PageObject: " + clazz.getCanonicalName());
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        } finally {
            System.out.println("tested: " + pages + " pages, with " + pageTypes + " pagetypes, " + elements + " elements, invoked " + actions + " actions.");
            for (String exception : ourMethodSuccess) {
                System.out.println("  success: " + exception);
            }
            for (String exception : elementNameExceptions) {
                System.out.println("  failed to invoke elementName: " + exception);
            }
            for (String exception : ourMethodExceptions) {
                System.out.println("  failed to invoke: " + exception);
            }
            for (String message : ourMethodSkips) {
                System.out.println("  skipped: " + message);
            }
        }
    }
}
