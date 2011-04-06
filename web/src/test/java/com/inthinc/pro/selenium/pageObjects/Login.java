package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.Page;
import com.inthinc.pro.automation.selenium.PageObject;
import com.inthinc.pro.selenium.pageEnums.LoginEnum;

/****************************************************************************************
 * Purpose: Includes all methods and variables to process the TiwiPro Login Screen
 * 
 * @author larringt , dtanner Last Update: 11/18/Added comments and made changes to adhere to Java Coding Standards
 */

public class Login extends AbstractPage{
    

    // public void load(); also page_load
    // public boolean isLoaded(); also page_isLoaded
    // public void validate(); also page_validate
    // private


	@Override
	public Page load() {
        selenium.open(LoginEnum.LOGIN_URL);
        setCurrentPage();
		return this;
	}

	@Override
	public boolean validate() {
		try {
			selenium.verifyText(LoginEnum.FORGOT_TEXT);
	        selenium.isElementPresent(LoginEnum.FORGOT_TEXT);
	
	        selenium.isTextPresent("Log In");
	        selenium.verifyText(LoginEnum.LOGIN_TEXT);
	        selenium.isElementPresent(LoginEnum.LOGIN_TEXT);
	
	        selenium.isElementPresent(LoginEnum.LOGIN_BUTTON);
	
	        selenium.isTextPresent("User Name:");
	        selenium.isElementPresent(LoginEnum.USERNAME_LABEL);
	        selenium.verifyText(LoginEnum.USERNAME_LABEL);
	
	        selenium.isTextPresent("Password:");
	        selenium.isElementPresent(LoginEnum.PASSWORD_LABEL);
	        selenium.verifyText(LoginEnum.PASSWORD_LABEL);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
    public void page_login_open() {
    	page_load();
    }
    
    public void page_logout_open(){
        selenium.open(LoginEnum.LOGOUT_URL);
        setCurrentPage();
    }

    public void page_login_process(String username, String password) {
        if (selenium.verifyLocation(LoginEnum.LOGIN_URL)) {
            page_logout_open();
        }
        textField_username_type(username);
        textField_fieldPassword_type(password);
        button_logIn_click();
    }

    public void button_forgotPasswordClose_click() {
        selenium.click(LoginEnum.FORGOT_CLOSE);
    }

    public void button_logInErrorX_click() {
        selenium.click(LoginEnum.ERROR_CLOSE);
    }
    
    public Boolean popUp_logInError_isVisible(){
        return selenium.isVisible(LoginEnum.ERROR_HEADER);
    }

    public void button_forgotPasswordCancel_click() {
        selenium.click(LoginEnum.FORGOT_CANCEL_BUTTON);
    }
    
    public Boolean popUp_forgotPassword_isVisible(){
        return selenium.isVisible(LoginEnum.FORGOT_TITLE);
    }

    public void button_forgotPasswordSubmit_click() {
        selenium.click(LoginEnum.FORGOT_SEND);
    }

    public void textField_forgotPasswordEmail_type(String email) {
        selenium.type(LoginEnum.FORGOT_EMAIL_FIELD, email);
    }

    public void link_forgotPassword_click() {
        selenium.click(LoginEnum.FORGOT_TEXT);
    }

    public void button_logIn_click() {
        selenium.click(LoginEnum.LOGIN_BUTTON);
        selenium.isElementNotPresent(LoginEnum.LOGIN_BUTTON);
        selenium.waitForPageToLoad(LoginEnum.LOGIN_BUTTON);
    }

    public Boolean header_logInError_isVisible() {
        return selenium.isVisible(LoginEnum.ERROR_HEADER);
    }

    public void button_forgotPasswordSend_click() {
        selenium.click(LoginEnum.FORGOT_SEND);
    }

    public void button_badCredentialsOk_click() {
        selenium.click(LoginEnum.ERROR_BUTTON);
    }

    public void message_forgotPasswordEmailUnknown_validate() {
        selenium.verifyText(LoginEnum.FORGOT_ERROR_EMAIL_UNKNOWN);
    }

    public void message_forgotPasswordEmailInvalid_validate() {
        selenium.verifyText(LoginEnum.FORGOT_ERROR_EMAIL_FORMAT);
    }

    public void message_forgotPasswordEmailRequired_validate() {
        selenium.verifyText(LoginEnum.FORGOT_ERROR_EMAIL_UNKNOWN);
    }

    public void textField_username_type(String username) {
        selenium.type(LoginEnum.USERNAME_FIELD, username);
    }

    public void textField_fieldPassword_type(String password) {
        selenium.type(LoginEnum.PASSWORD_FIELD, password);
    }

    public void popup_forgotPassword_validate() {
        // verify Forgot password window is displayed as expected

        selenium.isElementPresent(LoginEnum.FORGOT_EMAIL_FIELD);
        selenium.isElementPresent(LoginEnum.FORGOT_CLOSE);

        selenium.verifyText(LoginEnum.FORGOT_TITLE);
        selenium.verifyText(LoginEnum.FORGOT_MESSAGE);
        selenium.verifyText(LoginEnum.FORGOT_SEND);
        selenium.verifyText(LoginEnum.FORGOT_CANCEL_BUTTON);
        selenium.verifyText(LoginEnum.FORGOT_EMAIL_LABEL);
    }

    public void popup_error_validate() {
        // verify error message is displayed as expected

        selenium.isElementPresent(LoginEnum.ERROR_CLOSE);
        selenium.isElementPresent(LoginEnum.ERROR_BUTTON);
        selenium.isElementPresent(LoginEnum.ERROR_HEADER);

        selenium.verifyText(LoginEnum.ERROR_HEADER);
        selenium.verifyText(LoginEnum.ERROR_MESSAGE);
        selenium.getText(LoginEnum.ERROR_BUTTON.getXpath());
    }

    public void page_logIn_validate() {
        validate();
    }


    public void page_sentForgotPassword_validate() {
        selenium.verifyText(LoginEnum.MESSAGE_SENT_TITLE);
        selenium.verifyText(LoginEnum.MESSAGE_SENT_HEADER);
        selenium.verifyText(LoginEnum.MESSAGE_SENT_FIRST_PARAGRAPH);

        selenium.verifyText(LoginEnum.MESSAGE_SENT_BULLET_1);
        selenium.verifyText(LoginEnum.MESSAGE_SENT_BULLET_2);
        selenium.verifyText(LoginEnum.MESSAGE_SENT_BULLET_3);
    }

    public void textField_newPassword_type(String password) {
        selenium.type(LoginEnum.NEW_PASSWORD, password);
    }

    public void textField_confirmPassword_type(String password) {
        selenium.type(LoginEnum.CONFIRM_PASSWORD, password);
    }

    public void button_confirmChange_click() {
        selenium.click(LoginEnum.CHANGE_PASSWORD_BUTTON);
    }

    public ErrorCatcher get_errors() {
        return selenium.getErrors();
    }

    public void popup_badCred_validate() {
        selenium.verifyText(LoginEnum.ERROR_HEADER);
        selenium.verifyText(LoginEnum.ERROR_MESSAGE);
        selenium.verifyText(LoginEnum.ERROR_BUTTON);
        selenium.verifyText(LoginEnum.ERROR_CLOSE);
    }
}