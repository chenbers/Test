package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.Login;
import com.inthinc.pro.selenium.testSuites.WebRallyTest;

@Ignore
public class LoginTest extends WebTest {
    Login l;
    // TODO: jwimmer: question for DTanner: I can see a benefit from having SOME of these types of things defined in a non-page-specific enum? email is a good example (actually not
    // sure there are (m)any others???_) since there are lots of pages where an email address is expected, and it could be nice to have these three quickly available for tests on
    // ANY page
    String EMAIL_KNOWN = "jwimmer@inthinc.com";
    String EMAIL_UNKNOWN = "jaaacen@gmail.com";
    String EMAIL_INVALID = "username_at_domain_dot_tld";

    @Before
    public void setupPage() {
        l = new Login();
    }

    public void test() {
        
        
    }
}
