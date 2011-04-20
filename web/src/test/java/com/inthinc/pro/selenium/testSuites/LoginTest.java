package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.Dashboard;
import com.inthinc.pro.selenium.pageObjects.Login;
import com.inthinc.pro.selenium.testSuites.WebRallyTest;

//@Ignore
public class LoginTest extends WebRallyTest {
    Login login;
    // TODO: jwimmer: question for DTanner: I can see a benefit from having SOME of these types of things defined in a non-page-specific enum? email is a good example (actually not
    // sure there are (m)any others???_) since there are lots of pages where an email address is expected, and it could be nice to have these three quickly available for tests on
    // ANY page
    String EMAIL_KNOWN = "jwimmer@inthinc.com";
    String EMAIL_UNKNOWN = "jaaacen@gmail.com";
    String EMAIL_INVALID = "username_at_domain_dot_tld";

    @Before
    public void setupPage() {
        login = new Login();
    }

    @Test
    public void LoginButton() {
        // Set up test data
        set_test_case("TC1247");
        
        // Instantiate additional pages that this test needs
        Dashboard dash = new Dashboard();
        
        /* Input */
        login.page_login_open();//Navigate to page
        login.text_username_type("darth");//Type valid username
        login.text_password_type("wrongPassword");//Type valid password
        login.button_logIn_click();//Click Log In
        
        /* Expected Result */
        login.assertContains(login.getCurrentLocation(), "dashboard");//You are logged into the inthinc portal
//        dash.assertEquals("GroupName", dash.text_groupName_getText());//The page is the expected level/group for that user
        //TODO: dtanner: the previous line needs to be update once something is supplied in the Dashboard page object
    }
}
