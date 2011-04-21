package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.selenium.pageEnums.LoginEnum;

/****************************************************************************************
 * Purpose: Includes all methods and variables to process the TiwiPro Login Screen
 * 
 * @author larringt , dtanner Last Update: 11/18/Added comments and made changes to adhere to Java Coding Standards
 */

public class PageLogin extends Masthead {

    public PageLogin bulletText_forgotPasswordMessageSentBullet1_compareText(String expected) {
        String actual = bulletText_forgotPasswordMessegeSentBullet1_getText();
        if (!expected.equals(actual)) {
            addError("Message Sent Bullet 1", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public PageLogin bulletText_forgotPasswordMessageSentBullet2_compareText(String expected) {
        String actual = bulletText_forgotPasswordMessegeSentBullet2_getText();
        if (!expected.equals(actual)) {
            addError("Message Sent Bullet 2", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public PageLogin bulletText_forgotPasswordMessageSentBullet3_compareText(String expected) {
        String actual = bulletText_forgotPasswordMessegeSentBullet3_getText();
        if (!expected.equals(actual)) {
            addError("Message Sent Bullet 3", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public String bulletText_forgotPasswordMessegeSentBullet1_getText(){
        return selenium.getText(LoginEnum.MESSAGE_SENT_BULLET_1);
    }

    public String bulletText_forgotPasswordMessegeSentBullet2_getText(){
        return selenium.getText(LoginEnum.MESSAGE_SENT_BULLET_2);
    }

    public String bulletText_forgotPasswordMessegeSentBullet3_getText(){
        return selenium.getText(LoginEnum.MESSAGE_SENT_BULLET_3);
    }

    public PageLogin button_confirmChange_click() {
        selenium.click(LoginEnum.CHANGE_PASSWORD_BUTTON);
        return this;
    }

    public PageLogin button_forgotPasswordCancel_click() {
        selenium.click(LoginEnum.FORGOT_CANCEL_BUTTON);
        popup_forgotPassword_assertIsClosed();
        return this;
    }

    public PageLogin button_forgotPasswordClose_click() {
        selenium.click(LoginEnum.FORGOT_CLOSE);
        popup_forgotPassword_assertIsClosed();
        return this;
    }

    public PageLogin button_forgotPasswordSend_click() {
        selenium.click(LoginEnum.FORGOT_SEND);
        return this;
    }

    public PageLogin button_forgotPasswordSubmit_click() {
        selenium.click(LoginEnum.FORGOT_SEND);
        return this;
    }

    public PageLogin button_logIn_click() {
        selenium.click(LoginEnum.LOGIN_BUTTON);
        selenium.waitForPageToLoad();
        return this;
    }

    public PageLogin button_logInErrorOK_click() {
        selenium.click(LoginEnum.ERROR_BUTTON_OK);
        popup_badCredentials_assertIsClosed();
        return this;
    }

    public PageLogin button_logInErrorX_click() {
        selenium.click(LoginEnum.ERROR_CLOSE);
        popup_badCredentials_assertIsClosed();
        return this;
    }

    @Override
    public String getExpectedPath() {
        return LoginEnum.LOGIN_URL.getURL();
    }

    public PageLogin header_badCredentials_assertEquals(String expected) {
        String actual = selenium.getText(LoginEnum.ERROR_HEADER);
        assertEquals(expected, actual);
        return this;
    }

    public PageLogin header_forgotPasswordMessageSent_compareText(String expected) {
        String actual = header_forgotPasswordMessageSent_getText();
        if (!expected.equals(actual)) {
            addError("Message Sent Header", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public String header_forgotPasswordMessageSent_getText(){
        return  selenium.getText(LoginEnum.MESSAGE_SENT_HEADER);
    }

    private Boolean header_logInError_isVisible() {
        return selenium.isVisible(LoginEnum.ERROR_HEADER);
    }

    public PageLogin link_forgotPassword_click() {
        selenium.click(LoginEnum.FORGOT_TEXT);
        popup_forgotPassword_assertIsOpen();
        popup_forgotPassword_validate();
        return this;
    }

    @Override
    public PageLogin load() {
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
    
    public PageLogin page_login_open() {
        page_directURL_load();
        page_logIn_validate();
        return this;
    }

    public PageLogin page_login_process(String username, String password) {
//        if (selenium.verifyLocation(LoginEnum.LOGIN_URL)) {
            page_logout_open();
//        }
        textField_username_type(username);
        textField_password_type(password);
        button_logIn_click();
        return this;
    }
    
    public PageLogin page_logIn_validate() {
        validate();
        return this;
    }

    public PageLogin page_logout_open() {
        selenium.open(LoginEnum.LOGOUT_URL);
        page_logIn_validate();
        return this;
    }
    
    public PageLogin page_sentForgotPassword_validate() {
        title_forgotPasswordMessageSent_compareText(LoginEnum.MESSAGE_SENT_TITLE.getText());
        header_forgotPasswordMessageSent_compareText(LoginEnum.MESSAGE_SENT_HEADER.getText());
        text_forgotPasswordMessageSent_compareText(LoginEnum.MESSAGE_SENT_FIRST_PARAGRAPH.getText());

        bulletText_forgotPasswordMessageSentBullet1_compareText(LoginEnum.MESSAGE_SENT_BULLET_1.getText());
        bulletText_forgotPasswordMessageSentBullet2_compareText(LoginEnum.MESSAGE_SENT_BULLET_2.getText());
        bulletText_forgotPasswordMessageSentBullet3_compareText(LoginEnum.MESSAGE_SENT_BULLET_3.getText());
        return this;
    }

    public PageLogin popup_badCred_validate() {
        assertEquals(LoginEnum.ERROR_HEADER);
        assertEquals(LoginEnum.ERROR_MESSAGE);
        assertEquals(LoginEnum.ERROR_BUTTON_OK);
        assertEquals(LoginEnum.ERROR_CLOSE);
        return this;
    }
    
    public PageLogin popup_badCredentials_assertIsClosed() {
        if (popup_logInError_isVisible() || header_logInError_isVisible()) {
            addError("Bad Credentials Popup", "Popup didn't close");
        }
        return this;
    }
    
    public PageLogin popup_badCredentials_assertIsOpen() {
        if (!popup_logInError_isVisible() || !header_logInError_isVisible()) {
            addError("Bad Credentials Popup", "Popup didn't open");
        }
        return this;
    }

    public PageLogin popup_error_validate() {
        // verify error message is displayed as expected

        selenium.isElementPresent(LoginEnum.ERROR_CLOSE);
        selenium.isElementPresent(LoginEnum.ERROR_BUTTON_OK);
        selenium.isElementPresent(LoginEnum.ERROR_HEADER);

        selenium.getText(LoginEnum.ERROR_HEADER);
        selenium.getText(LoginEnum.ERROR_MESSAGE);
        selenium.getText(LoginEnum.ERROR_BUTTON_OK.getXpath());
        return this;
    }

    public PageLogin popup_forgotPassword_assertIsClosed() {
        if (popup_forgotPassword_isVisible()) {
            addError("Forgot Password Popup", "Popup didn't close");
        }
        return this;
    }
    
    public PageLogin popup_forgotPassword_assertIsOpen() {
        if (!popup_forgotPassword_isVisible()) {
            addError("Forgot Password Popup", "Popup didn't open");
        }
        return this;
    }

    private Boolean popup_forgotPassword_isVisible() {
        return selenium.isVisible(LoginEnum.FORGOT_TITLE);
    }

    private PageLogin popup_forgotPassword_validate() {
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

    private Boolean popup_logInError_isVisible() {
        return selenium.isVisible(LoginEnum.ERROR_HEADER);
    }

    public PageLogin text_forgotPasswordMessageSent_compareText(String expected) {
        String actual = text_forgotPasswordMessageSent_getText();
        if (expected == null) {
            expected = LoginEnum.MESSAGE_SENT_FIRST_PARAGRAPH.getText();
        }
        if (!expected.equals(actual)) {
            addError("Message Sent Text", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public String text_forgotPasswordMessageSent_getText(){
        return selenium.getText(LoginEnum.MESSAGE_SENT_FIRST_PARAGRAPH);
    }

    public PageLogin textField_confirmPassword_type(String password) {
        selenium.type(LoginEnum.CONFIRM_PASSWORD, password);
        return this;
    }

    public PageLogin textField_forgotPasswordEmail_type(String email) {
        selenium.type(LoginEnum.FORGOT_EMAIL_FIELD, email);
        return this;
    }

    public PageLogin textField_newPassword_type(String password) {
        selenium.type(LoginEnum.NEW_PASSWORD, password);
        return this;
    }

    public PageLogin textField_password_type(String password) {
        selenium.type(LoginEnum.PASSWORD_FIELD, password);
        return this;
    }

    public PageLogin textField_username_type(String username) {
        selenium.type(LoginEnum.USERNAME_FIELD, username);
        return this;
    }

    public PageLogin title_forgotPasswordMessageSent_compareText(String expected) {
        String actual = title_forgotPasswordMessageSent_getText();
        if (!expected.equals(actual)) {
            addError("Message Sent Title", "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    public String title_forgotPasswordMessageSent_getText(){
        return selenium.getText(LoginEnum.MESSAGE_SENT_TITLE);
    }

    @Override
    public PageLogin validate() {
        assertEquals(LoginEnum.FORGOT_TEXT);

        assertEquals(LoginEnum.LOGIN_TEXT);

        selenium.isElementPresent(LoginEnum.LOGIN_BUTTON);

        assertEquals(LoginEnum.USERNAME_LABEL);

        assertEquals(LoginEnum.PASSWORD_LABEL);

        return this;
    }
}
