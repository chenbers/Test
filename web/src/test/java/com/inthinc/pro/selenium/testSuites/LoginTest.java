package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.testSuites.WebRallyTest;

public class LoginTest extends WebRallyTest {
    PageLogin login;
    String EMAIL_KNOWN = "jwimmer@inthinc.com";
    String EMAIL_UNKNOWN = "jaaacen@gmail.com";
    String EMAIL_INVALID = "username_at_domain_dot_tld";

    @Before
    public void setupPage() {
        login = new PageLogin();
    }

    @Test
    public void LoginButton() {
        // Set up test data
        set_test_case("TC1247");
        
        // Instantiate additional pages that this test needs
        PageExecutiveDashboard dash = new PageExecutiveDashboard();
        
        /* Input */
        login.openLogout();//Navigate to page
        login._textField().userName().type("darth");//Type valid username
        login._textField().password().type("password");//Type valid password
        login._button().logIn().click();//Click Log In
        
        /* Expected Result */
        login.assertContains(login.getCurrentLocation(), "dashboard");//You are logged into the inthinc portal
        dash.assertEquals("Mother Group", dash._link().groupName().getText(1));//The page is the expected level/group for that user
    }
}
