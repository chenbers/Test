package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.web.selenium.InthincTest;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.portal.Masthead.Masthead;

public class LoginTest extends InthincTest {
    Login l;
    //TODO: jwimmer: question for DTanner: I can see a benefit from having SOME of these types of things defined in a non-page-specific enum?  email is a good example (actually not sure there are (m)any others???_) since there are lots of pages where an email address is expected, and it could be nice to have these three quickly available for tests on ANY page
    String EMAIL_KNOWN = "jwimmer@inthinc.com";
    String EMAIL_UNKNOWN = "jaaacen@gmail.com";
    String EMAIL_INVALID = "username_at_domain_dot_tld";
    @Before
    public void setupPage() {
       l = new Login();
    }

    @Test
    public void LoginButton() {
        // create instance of library objects
        //Login l = new Login();
        // Set up test data
        set_test_case("TC1247");

        // login to portal
        l.page_navigateTo();
        l.textField_fieldPassword_type("password");
        l.textField_username_type("0001");
        l.button_logIn_click();
        
        Masthead m = new Masthead();
        m.page_validate();
    }

    @Test
    public void UI() {
        // create instance of library objects
        //Login l = new Login();
        // Set up test data
        set_test_case("TC4632");
        // go to Login Screen
        l.page_navigateTo();
        // verify login screen is displayed correctly
        l.page_logIn_validate();
    }
    
    @Test
    public void login_nullUsernamePassword_appError() {
        Login l = new Login();
        set_test_case("unknown");
        l.page_navigateTo();
        l.page_logIn_validate();
        l.button_logIn_click();
        l.modal_badCred_validate();
    }
    @Test
    public void login_closeBadCredModal_noModal() {
        //Login l = new Login();
        set_test_case("none");
        l.page_navigateTo();
        l.page_logIn_validate();
        l.button_logIn_click();
        l.modal_badCred_validate();
        l.button_badCredOk_click(); //TODO: jwimmer: DTanner: this method verifies that the modal/popup closed... we should talk on this.
    }

    private void forgotPassword_Scenario_enterEmailClickSend(String emailAddress) {
        l.page_navigateTo();
        l.link_forgotPassword_click();
        
        l.popup_forgotPassword_validate();
        l.textField_forgotPasswordEmail_type(emailAddress);
        l.button_forgotPasswordSend_click(); //TODO: jwimmer: DTanner: this method verifies that the modal/popup closed... we should talk on this.
    }
    @Test
    public void forgotPassword_badEmailManual_incorrectFormat() {
        //login_forgotPasswordScenario_enterEmailClickSend(EMAIL_INVALID);
        l.page_navigateTo();
        l.link_forgotPassword_click();
        
        l.popup_forgotPassword_validate();
        l.textField_forgotPasswordEmail_type(EMAIL_INVALID);
        l.button_forgotPasswordSend_click();
        l.message_forgotPasswordEmailInvalid_validate();
    }
    @Test
    public void forgotPassword_badEmail_incorrectFormat() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_INVALID);
        l.message_forgotPasswordEmailInvalid_validate();
    }
    @Test
    public void forgotPassword_noEmail_required() {
        forgotPassword_Scenario_enterEmailClickSend(null);
        l.message_forgotPasswordEmailRequired_validate();
    }
    @Test
    public void forgotPassword_unknownEmail_incorrect() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_UNKNOWN);
        l.message_forgotPasswordEmailUnknown_validate();
    }
    @Test
    public void forgotPassword_usersEmail_success() {
        forgotPassword_Scenario_enterEmailClickSend(EMAIL_KNOWN);
        l.page_sentForgotPassword_validate();
    }
    @Test
    public void forgotPassword_cancel_closePopup() {
        l.page_navigateTo();
        l.link_forgotPassword_click();
        
        l.popup_forgotPassword_validate();
        l.button_forgotPasswordCancel_click();
        l.page_logIn_validate();
    }
    @Test
    public void login_forgotPassClose_closePopup() {
        l.page_navigateTo();
        l.link_forgotPassword_click();
        
        l.popup_forgotPassword_validate();
        l.button_forgot_password_x_click();
        l.page_logIn_validate();
    }
}
