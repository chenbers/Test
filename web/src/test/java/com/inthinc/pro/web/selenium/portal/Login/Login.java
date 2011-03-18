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
			this(GlobalSelenium.getYourOwn());
		}
	
	public Login(GlobalSelenium tvar ){
			this(tvar.getSelenium());
		}
	
	public Login( CoreMethodLib sel ){
			selenium = sel;
		}
	
	public void page_log_in_open(){
			//go to login screen
			selenium.open("logout");  
//			ck_login_page();
		}
	
	public void portal_log_in_process(String username, String password){
			if (selenium.getLocation().indexOf("/tiwipro/login")==-1){page_log_in_open();}
			text_field_username_type(username);
			text_field_password_type(password);
			button_log_in_click();
		}

	public void button_forgot_password_close_click() {
			selenium.click(LoginEnum.FORGOT_CLOSE, "Forgot Close click");
		}

	public void button_forgot_password_cancel_click() {
			selenium.click(LoginEnum.FORGOT_CANCEL_BUTTON, "Forgot Cancel click");		
			selenium.isNotVisible(LoginEnum.FORGOT_CANCEL_BUTTON,"Forgot Cancel clicked");
		}

	public void message_forgot_password_error_validate() {
			selenium.isVisible(LoginEnum.FORGOT_ERROR, "Required Message element");
			selenium.getText(LoginEnum.FORGOT_ERROR, "Required", "Required Message text");
		}

	public void button_forgot_password_submit_click() {
			selenium.click(LoginEnum.FORGOT_SEND, "Submit Button");
		}

	public void text_field_forgot_password_email_type(String email) {
			selenium.type(LoginEnum.FORGOT_EMAIL_FIELD, email, "Email field");
		}

	public void text_field_forgot_password_password_type() {
			selenium.click(LoginEnum.FORGOT_TEXT, LoginEnum.FORGOT_TEXT.getText()+" button click");
		}
	
	public void button_log_in_click(){
			selenium.click(LoginEnum.LOGIN_BUTTON, "Login button click");
			selenium.wait_for_element_present("Admin", "link");
			String location = selenium.getLocation("tiwipro/app", "Login button click");
			if (location.indexOf("tiwipro/app") == -1){
				System.out.println(location);
				popup_error_validate();
				button_forgot_password_ok_click();
			} 
		}
	
	public void button_forgot_password_ok_click(){
			selenium.click(LoginEnum.ERROR_BUTTON, "Login Error OK button click");
			selenium.Pause(2);
			selenium.isNotVisible(LoginEnum.ERROR_HEADER.getID(), "Login Error closed by OK button");
		}
	
	public void button_forgot_password_x_click(){
			selenium.click(LoginEnum.ERROR_CLOSE,"Login Error X button click");
			selenium.Pause(2);
			selenium.isNotVisible(LoginEnum.ERROR_HEADER, "Login Error closed by X button");
		}
	
	public void text_field_username_type(String username){
			//enter tiwipro username
			selenium.type(LoginEnum.USERNAME_FIELD, username, "Username type text");
		}
	
	public void text_field_password_type(String password){
			//enter tiwipro password
			selenium.type(LoginEnum.PASSWORD_FIELD, password, "Password type text");
		}

	public void popup_forgot_password_validate() {
			//verify Forgot password window is displayed as expected
			selenium.isVisible(LoginEnum.FORGOT_TITLE, "Forgot Pop Up element visible");
			
			selenium.isElementPresent(LoginEnum.FORGOT_EMAIL_FIELD, "Email text field");
			selenium.isElementPresent(LoginEnum.FORGOT_CLOSE, "Forgot Close button" );
			
			selenium.getText(LoginEnum.FORGOT_TITLE, "Forgot Pop Up Title text present");
			selenium.getText(LoginEnum.FORGOT_MESSAGE, "Forgot message");
			selenium.getText(LoginEnum.FORGOT_SEND, "Fogot Send button");
			selenium.getText(LoginEnum.FORGOT_CANCEL_BUTTON, "Forgot Cancel button");
			selenium.getText(LoginEnum.FORGOT_EMAIL_LABEL, "E-mail Address label");
		}
	
	private void popup_error_validate(){
			//verify error message is displayed as expected
			selenium.isVisible(LoginEnum.ERROR_HEADER);
			
			selenium.isElementPresent(LoginEnum.ERROR_CLOSE, "Login Error X close element present");
			selenium.isElementPresent(LoginEnum.ERROR_BUTTON, "Login Error OK button present");
			selenium.isElementPresent(LoginEnum.ERROR_HEADER, "Login Error header present");
			
			selenium.getText(LoginEnum.ERROR_HEADER, "Log In Error", "Login Error header text");
			selenium.getText(LoginEnum.ERROR_MESSAGE, "Login Error message text");
			selenium.getText(LoginEnum.ERROR_BUTTON.getXpath(), "OK", "Login Error OK Button text");
		}
	

	public void page_log_in_validate(){
		selenium.getText(LoginEnum.FORGOT_TEXT, LoginEnum.FORGOT_TEXT.getText()+" text present");
		selenium.isElementPresent(LoginEnum.FORGOT_TEXT, LoginEnum.FORGOT_TEXT.getText()+" element present");
		
		selenium.isTextPresent("Log In", "Log In text present");
		selenium.getText(LoginEnum.LOGIN_TEXT, "Login text match");
		selenium.isElementPresent(LoginEnum.LOGIN_TEXT, "Login text element present");
		
		selenium.isElementPresent(LoginEnum.LOGIN_BUTTON, "Login field element present");
		
		selenium.isTextPresent("User Name:", "User Name: text present");
		selenium.isElementPresent(LoginEnum.USERNAME_LABEL, "User Name: element present");
		selenium.getText(LoginEnum.USERNAME_LABEL, "User Name: text match");
		
		selenium.isTextPresent("Password:", "Password: text present");
		selenium.isElementPresent(LoginEnum.PASSWORD_LABEL, "Password: element present");
		selenium.getText(LoginEnum.PASSWORD_LABEL, "Password:", "Password: text match");
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
	
	
	public void page_sent_forgot_password_validate() {
		selenium.getText(LoginEnum.MESSAGE_SENT_TITLE, "Forgot Username/Password title");
		selenium.getText(LoginEnum.FIRST_PARAGRAPH, "Forgot Username/Password sent message");
		
		selenium.getText(LoginEnum.HEADER, "Error message for Message Sent Page");
		selenium.getText(LoginEnum.BULLET_1, "First Bullet Forgot Username/Password");
		selenium.getText(LoginEnum.BULLET_2, "Second Bullet Forgot Username/Password");
		selenium.getText(LoginEnum.BULLET_3, "Third Bullet Forgot Username/Password");
	}
 
	public void text_field_new_password_type(String password) {
		selenium.type(LoginEnum.NEW_PASSWORD, password);
	}
	public void text_field_confirm_password_type(String password) {
		selenium.type(LoginEnum.CONFIRM_PASSWORD, password);
	}
	
	public void button_confirm_change_click() {
		selenium.click(LoginEnum.CHANGE_PASSWORD_BUTTON, "Change Password Button");
	}
	
	public CoreMethodLib getSelenium() {
	    return this.selenium;
	}
	

	public ErrorCatcher get_errors(){
			return selenium.getErrors();
	}
}

