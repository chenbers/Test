package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.selenium.pageEnums.LoginEnum;

/****************************************************************************************
 * Purpose: Includes all methods and variables to process the TiwiPro Login Screen
 * 
 * @author larringt , dtanner Last Update: 11/18/Added comments and made changes to adhere to Java Coding Standards
 */

public class Login extends Masthead {

    public Login popup_badCredentials_assertIsClosed() {
        if (popUp_logInError_isVisible() || header_logInError_isVisible()) {
            addError("Bad Credentials Popup", "Popup didn't close");
        }
        return this;
    }

    public Login popup_badCredentials_assertIsOpen() {
        if (!popUp_logInError_isVisible() || !header_logInError_isVisible()) {
            addError("Bad Credentials Popup", "Popup didn't open");
        }
        return this;
    }

    public Login popup_forgotPassword_assertIsClosed() {
        if (popUp_forgotPassword_isVisible()) {
            addError("Forgot Password Popup", "Popup didn't close");
        }
        return this;
    }

    public Login popup_forgotPassword_assertIsOpen() {
        if (!popUp_forgotPassword_isVisible()) {
            addError("Forgot Password Popup", "Popup didn't open");
        }
        return this;
    }

    public Login button_confirmChange_click() {
        selenium.click(LoginEnum.CHANGE_PASSWORD_BUTTON);
        return this;
    }

    public Login button_forgotPasswordCancel_click() {
        selenium.click(LoginEnum.FORGOT_CANCEL_BUTTON);
        popup_forgotPassword_assertIsClosed();
        return this;
    }

    public Login button_forgotPasswordClose_click() {
        selenium.click(LoginEnum.FORGOT_CLOSE);
        popup_forgotPassword_assertIsClosed();
        return this;
    }

    public Login button_forgotPasswordSend_click() {
        selenium.click(LoginEnum.FORGOT_SEND);
        return this;
    }

    public Login button_forgotPasswordSubmit_click() {
        selenium.click(LoginEnum.FORGOT_SEND);
        return this;
    }

    public Login button_logIn_click() {
        selenium.click(LoginEnum.LOGIN_BUTTON);
        selenium.waitForPageToLoad();

        return this;
    }

    public Login button_logInErrorOK_click() {
        selenium.click(LoginEnum.ERROR_BUTTON_OK);
        popup_badCredentials_assertIsClosed();
        return this;
    }

    public Login button_logInErrorX_click() {
        selenium.click(LoginEnum.ERROR_CLOSE);
        popup_badCredentials_assertIsClosed();
        return this;
    }

    private Boolean header_logInError_isVisible() {
        return selenium.isVisible(LoginEnum.ERROR_HEADER);
    }

    public Login link_forgotPassword_click() {
        selenium.click(LoginEnum.FORGOT_TEXT);
        popup_forgotPassword_assertIsOpen();
        popup_forgotPassword_validate();
        return this;
    }

    @Override
    public Login load() {
        selenium.open(LoginEnum.LOGIN_URL);
        return this;
    }

    public String message_forgotPasswordEmailInvalid_getText() {
        return selenium.getText(LoginEnum.FORGOT_ERROR_EMAIL_FORMAT);
    }

    public String message_forgotPasswordEmailRequired_getText() {
        return selenium.getText(LoginEnum.FORGOT_ERROR_EMAIL_UNKNOWN);
    }

    public String message_forgotPasswordEmailUnknown_getText() {
        return selenium.getText(LoginEnum.FORGOT_ERROR_EMAIL_UNKNOWN);
    }

    public Login page_login_open() {
        page_directURL_load();
        page_logIn_validate();
        return this;
    }

    public Login page_login_process(String username, String password) {
//        if (selenium.verifyLocation(LoginEnum.LOGIN_URL)) {
            page_logout_open();
//        }
        text_username_type(username);
        text_password_type(password);
        button_logIn_click();
        return this;
    }

    public Login page_logIn_validate() {
        validate();
        return this;
    }

    public Login page_logout_open() {
        selenium.open(LoginEnum.LOGOUT_URL);
        page_logIn_validate();
        return this;
    }

    public Login page_sentForgotPassword_validate() {
        title_forgotPasswordMessageSent_compareText(LoginEnum.MESSAGE_SENT_TITLE.getText());
        header_forgotPasswordMessageSent_compareText(LoginEnum.MESSAGE_SENT_HEADER.getText());
        text_forgotPasswordMessageSent_compareText(LoginEnum.MESSAGE_SENT_FIRST_PARAGRAPH.getText());

        bulletPoint_forgotPasswordMessageSentBullet1_compareText(LoginEnum.MESSAGE_SENT_BULLET_1.getText());
        bulletPoint_forgotPasswordMessageSentBullet2_compareText(LoginEnum.MESSAGE_SENT_BULLET_2.getText());
        bulletPoint_forgotPasswordMessageSentBullet3_compareText(LoginEnum.MESSAGE_SENT_BULLET_3.getText());
        return this;
    }

    public Login bulletPoint_forgotPasswordMessageSentBullet1_compareText(String expected) {
        String actual = selenium.getText(LoginEnum.MESSAGE_SENT_BULLET_1);
        if (!expected.equals(actual)) {
            addError("Message Sent Bullet 1", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public Login bulletPoint_forgotPasswordMessageSentBullet2_compareText(String expected) {
        String actual = selenium.getText(LoginEnum.MESSAGE_SENT_BULLET_2);
        if (!expected.equals(actual)) {
            addError("Message Sent Bullet 2", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public Login bulletPoint_forgotPasswordMessageSentBullet3_compareText(String expected) {
        String actual = selenium.getText(LoginEnum.MESSAGE_SENT_BULLET_3);
        if (!expected.equals(actual)) {
            addError("Message Sent Bullet 3", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public Login title_forgotPasswordMessageSent_compareText(String expected) {
        String actual = selenium.getText(LoginEnum.MESSAGE_SENT_TITLE);
        if (!expected.equals(actual)) {
            addError("Message Sent Title", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public Login header_forgotPasswordMessageSent_compareText(String expected) {
        String actual = selenium.getText(LoginEnum.MESSAGE_SENT_HEADER);
        if (!expected.equals(actual)) {
            addError("Message Sent Header", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public Login text_forgotPasswordMessageSent_compareText(String expected) {
        String actual = selenium.getText(LoginEnum.MESSAGE_SENT_FIRST_PARAGRAPH);
        if (expected == null) {
            expected = LoginEnum.MESSAGE_SENT_FIRST_PARAGRAPH.getText();
        }
        if (!expected.equals(actual)) {
            addError("Message Sent Text", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public Login popup_badCred_validate() {
        assertEquals(LoginEnum.ERROR_HEADER);
        assertEquals(LoginEnum.ERROR_MESSAGE);
        assertEquals(LoginEnum.ERROR_BUTTON_OK);
        assertEquals(LoginEnum.ERROR_CLOSE);
        return this;
    }

    public Login header_badCredentials_assertEquals(String expected) {
        String actual = selenium.getText(LoginEnum.ERROR_HEADER);
        assertEquals(expected, actual);
        return this;
    }

    public Login popup_error_validate() {
        // verify error message is displayed as expected

        selenium.isElementPresent(LoginEnum.ERROR_CLOSE);
        selenium.isElementPresent(LoginEnum.ERROR_BUTTON_OK);
        selenium.isElementPresent(LoginEnum.ERROR_HEADER);

        selenium.getText(LoginEnum.ERROR_HEADER);
        selenium.getText(LoginEnum.ERROR_MESSAGE);
        selenium.getText(LoginEnum.ERROR_BUTTON_OK.getXpath());
        return this;
    }

    private Boolean popUp_forgotPassword_isVisible() {
        return selenium.isVisible(LoginEnum.FORGOT_TITLE);
    }

    private Login popup_forgotPassword_validate() {
        // verify Forgot password window is displayed as expected

        selenium.isElementPresent(LoginEnum.FORGOT_EMAIL_FIELD);
        selenium.isElementPresent(LoginEnum.FORGOT_CLOSE);

        assertEquals(LoginEnum.FORGOT_TITLE);
        assertEquals(LoginEnum.FORGOT_MESSAGE);
        assertEquals(LoginEnum.FORGOT_SEND);
        assertEquals(LoginEnum.FORGOT_CANCEL_BUTTON);
        assertEquals(LoginEnum.FORGOT_EMAIL_LABEL);
        return this;
    }

    private Boolean popUp_logInError_isVisible() {
        return selenium.isVisible(LoginEnum.ERROR_HEADER);
    }

    public Login text_confirmPassword_type(String password) {
        selenium.type(LoginEnum.CONFIRM_PASSWORD, password);
        return this;
    }

    public Login text_forgotPasswordEmail_type(String email) {
        selenium.type(LoginEnum.FORGOT_EMAIL_FIELD, email);
        return this;
    }

    public Login text_newPassword_type(String password) {
        selenium.type(LoginEnum.NEW_PASSWORD, password);
        return this;
    }

    public Login text_password_type(String password) {
        selenium.type(LoginEnum.PASSWORD_FIELD, password);
        return this;
    }

    public Login text_username_type(String username) {
        selenium.type(LoginEnum.USERNAME_FIELD, username);
        return this;
    }

    @Override
    public Login validate() {
        assertEquals(LoginEnum.FORGOT_TEXT);

        assertEquals(LoginEnum.LOGIN_TEXT);

        selenium.isElementPresent(LoginEnum.LOGIN_BUTTON);

        assertEquals(LoginEnum.USERNAME_LABEL);

        assertEquals(LoginEnum.PASSWORD_LABEL);

        return this;
    }

    @Override
    public String getExpectedPath() {
        return LoginEnum.LOGIN_URL.getURL();
    }
}
