package com.inthinc.pro.web.selenium.portal.Login;

import com.inthinc.pro.web.selenium.CoreMethodLib;
import com.inthinc.pro.web.selenium.GlobalSelenium;
import com.inthinc.pro.web.selenium.Debug.ErrorCatcher;
import com.inthinc.pro.web.selenium.portal.Login.LoginEnum;

/****************************************************************************************
 * Purpose: Includes all methods and variables to process the TiwiPro Login Screen
 * @author larringt , dtanner
 * Last Update:  11/18/Added comments and made changes to adhere to Java Coding Standards
 */

public class Login {
	
	protected static CoreMethodLib selenium;

	public Login(){
		selenium = GlobalSelenium.getSelenium();
	}
	
	//public void load(); also page_load
	//public boolean isLoaded(); also page_isLoaded
	//public void validate(); also page_validate
	//private 
	
	public void page_navigateTo(){
		//go to login screen
		selenium.open(LoginEnum.URL.getText());//TODO: jwimmer: DTanner I pulled this into the <page>Enum... I'm OK if it live somewhere else, but would like to maintain the GOAL of NO hardcoded strings in the FRAMEWORK code  (script code I think we can make the argument to let hardcoded strings fly
		//TODO: jwimmer: DTanner, you'll also note that I set this to login as opposed to logout... It seems to me that there will/should be test cases where you want to navigate to login without logging out first (there is a modal telling the user they are already logged in, and a return to dashboard link ...)
//		ck_login_page();
	}
	
	public void page_login(String username, String password){
		if (selenium.getLocation().indexOf("login")==-1){page_navigateTo();}//TODO: jwimmer: DTanner: no hard coded String(s) in FRAMEWORK
		textField_username_type(username);
		textField_fieldPassword_type(password);
		button_logIn_click();
	}

	public void button_forgotPasswordClose_click() {
		selenium.click(LoginEnum.FORGOT_CLOSE);
	}
   public void button_forgot_password_x_click(){//TODO: jwimmer: DTanner: I moved this method to try to determine how it relates to button_forgotPasswordClose_click???
        selenium.click(LoginEnum.ERROR_CLOSE);
        selenium.Pause(2);
        selenium.isNotVisible(LoginEnum.ERROR_HEADER);
    }

	public void button_forgotPasswordCancel_click() {
		selenium.click(LoginEnum.FORGOT_CANCEL_BUTTON);		
		selenium.isNotVisible(LoginEnum.FORGOT_CANCEL_BUTTON);
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
	
	public void button_logIn_click(){
		selenium.click(LoginEnum.LOGIN_BUTTON);
		//selenium.wait_for_element_present("Admin", "link");//TODO: jwimmer: DTanner: these Strings should be in the enum, no? and not every login sees Admin do they???
//		String location = selenium.getLocation("tiwipro/app", "Login button click");//TODO: jwimmer: DTanner, from this line down I have questions... usecases I can see where user clicks login success = some /app page, but there are a few reasons they might not get in, right? bad credentials, expired password, expired login , ...??? do you want to validate any/all of those in this method or provide validate methods for each outcome???
//		if (location.indexOf("tiwipro/app") == -1){
//			System.out.println(location);
//			popup_error_validate();
//			button_forgotPasswordOk_click();
//		} 
	}
	
	public void button_forgotPasswordSend_click(){
		selenium.click(LoginEnum.FORGOT_SEND);
	}
	
    public void button_badCredOk_click() {
        selenium.click(LoginEnum.ERROR_BUTTON);
        selenium.Pause(2);
        selenium.isNotVisible(LoginEnum.ERROR_HEADER);
    }

	public void message_forgotPasswordEmailUnknown_validate(){
	    selenium.isVisible(LoginEnum.FORGOT_ERROR_EMAIL_UNKNOWN); //TODO: jwimmer: question for Dtanner: seeing/using this pattern a lot... is there a reason that the selenium.getText method shouldn't just test for isVisible first?  reprhased: is there ever a scenario where we don't want getText to check isVisible first?
	    selenium.getText(LoginEnum.FORGOT_ERROR_EMAIL_UNKNOWN);
	}
	public void message_forgotPasswordEmailInvalid_validate(){
	    selenium.isVisible(LoginEnum.FORGOT_ERROR_EMAIL_FORMAT);
        selenium.getText(LoginEnum.FORGOT_ERROR_EMAIL_FORMAT);
    }
	public void message_forgotPasswordEmailRequired_validate(){
	    selenium.isVisible(LoginEnum.FORGOT_ERROR_EMAIL_UNKNOWN);
        selenium.getText(LoginEnum.FORGOT_ERROR_EMAIL_UNKNOWN);
    }
	public void textField_username_type(String username){
		//enter tiwipro username
		selenium.type(LoginEnum.USERNAME_FIELD, username);
	}
	
	public void textField_fieldPassword_type(String password){
		//enter tiwipro password
		selenium.type(LoginEnum.PASSWORD_FIELD, password);
	}

	public void popup_forgotPassword_validate() {
		//verify Forgot password window is displayed as expected
		selenium.isVisible(LoginEnum.FORGOT_TITLE);
		
		selenium.isElementPresent(LoginEnum.FORGOT_EMAIL_FIELD);
		selenium.isElementPresent(LoginEnum.FORGOT_CLOSE );
		
		selenium.getText(LoginEnum.FORGOT_TITLE);
		selenium.getText(LoginEnum.FORGOT_MESSAGE);
		selenium.getText(LoginEnum.FORGOT_SEND);
		selenium.getText(LoginEnum.FORGOT_CANCEL_BUTTON);
		selenium.getText(LoginEnum.FORGOT_EMAIL_LABEL);
	}
	
	public void popup_error_validate(){
		//verify error message is displayed as expected
		selenium.isVisible(LoginEnum.ERROR_HEADER);
		
		selenium.isElementPresent(LoginEnum.ERROR_CLOSE);
		selenium.isElementPresent(LoginEnum.ERROR_BUTTON);
		selenium.isElementPresent(LoginEnum.ERROR_HEADER);
		
		selenium.getText(LoginEnum.ERROR_HEADER);
		selenium.getText(LoginEnum.ERROR_MESSAGE);
		selenium.getText(LoginEnum.ERROR_BUTTON.getXpath());
	}
	
	public void page_logIn_validate(){
		selenium.getText(LoginEnum.FORGOT_TEXT);
		selenium.isElementPresent(LoginEnum.FORGOT_TEXT);
		
		selenium.isTextPresent("Log In");
		selenium.getText(LoginEnum.LOGIN_TEXT);
		selenium.isElementPresent(LoginEnum.LOGIN_TEXT);
		
		selenium.isElementPresent(LoginEnum.LOGIN_BUTTON);
		
		selenium.isTextPresent("User Name:");
		selenium.isElementPresent(LoginEnum.USERNAME_LABEL);
		selenium.getText(LoginEnum.USERNAME_LABEL);
		
		selenium.isTextPresent("Password:");
		selenium.isElementPresent(LoginEnum.PASSWORD_LABEL);
		selenium.getText(LoginEnum.PASSWORD_LABEL);
	}
	
//	public void forgotPassword(){
//		forgotPassword( false, "" ); //Set up a default value, so we don't always have to specify
//	}
//	
//	public void forgotPassword( String email ){
//		forgotPassword( false, email );
//	}
//	
//	public void forgotPassword( Boolean cancel ){
//		forgotPassword( cancel, "" );
//	}
//	
//	public void forgotPassword( String email, Boolean cancel){
//		forgotPassword( cancel, email );
//	}
//	
//	
//	
//	public void forgotPassword(Boolean cancel, String email){
//		//verify all screen elements and text
//		selenium.open("/tiwipro/login");
//		popup_text_field_forgot_password_password_type();
//		popup_forgot_password_validate();
//				//select Cancel Button
//				if (cancel){
//						popup_button_forgot_password_cancel_click();
//					}
//						//select close window
//						popup_button_forgot_password_close_click();
//						//request password email
//						popup_text_field_forgot_password_password_type();
//						// email needs to be verbalized
//						popup_text_field_forgot_password_email_type(email);
//						popup_button_forgot_password_submit_click();
//						
//				if ( email=="" ){
//						popup_message_forgot_password_error_validate();
//					}
//			}
	
    public void page_sentForgotPassword_validate() {
        selenium.Pause(2);//TODO: Dtanner: I added this pause, and the test using this method is working now... is there a less ham handed way of achieving this? waitUntilPresent ???
        selenium.getText(LoginEnum.MESSAGE_SENT_TITLE);
        selenium.getText(LoginEnum.MESSAGE_SENT_HEADER);
        selenium.getText(LoginEnum.MESSAGE_SENT_FIRST_PARAGRAPH);

        selenium.getText(LoginEnum.MESSAGE_SENT_BULLET_1);
        selenium.getText(LoginEnum.MESSAGE_SENT_BULLET_2);
        selenium.getText(LoginEnum.MESSAGE_SENT_BULLET_3);
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

	public ErrorCatcher get_errors(){
	    return selenium.getErrors();
	}

    public void modal_badCred_validate() {
        selenium.getText(LoginEnum.ERROR_HEADER);
        selenium.getText(LoginEnum.ERROR_MESSAGE);        
    }
}