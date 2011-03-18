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
	
	public void open_login(){
			//go to login screen
			selenium.open("logout");  
			ck_login_page();
		}
	
	public void login_to_portal(String username, String password){
			if (selenium.getLocation().indexOf("/tiwipro/login")==-1){open_login();}
			type_username(username);
			type_password(password);
			click_login();
		}

	public void forgot_close_window() {
			selenium.click(LoginEnum.FORGOT_CLOSE, "Forgot Close click");
		}

	public void forgot_cancel() {
			selenium.click(LoginEnum.FORGOT_CANCEL_BUTTON, "Forgot Cancel click");		
			selenium.isNotVisible(LoginEnum.FORGOT_CANCEL_BUTTON,"Forgot Cancel clicked");
		}

	public void forgot_error() {
			selenium.isVisible(LoginEnum.FORGOT_ERROR, "Required Message element");
			selenium.getText(LoginEnum.FORGOT_ERROR, "Required", "Required Message text");
		}

	public void forgot_submit() {
			selenium.click(LoginEnum.FORGOT_SEND, "Submit Button");
		}

	public void forgot_email_type(String email) {
			selenium.type(LoginEnum.FORGOT_EMAIL_FIELD, email, "Email field");
		}

	public void forgot_password() {
			selenium.click(LoginEnum.FORGOT_TEXT, LoginEnum.FORGOT_TEXT.getText()+" button click");
		}
	
	public void click_login(){
			selenium.click(LoginEnum.LOGIN_BUTTON, "Login button click");
			selenium.wait_for_element_present("Admin", "link");
			String location = selenium.getLocation("tiwipro/app", "Login button click");
			if (location.indexOf("tiwipro/app") == -1){
				ck_error_msg();
				error_ok();
			} 
		}
	
	public void error_ok(){
			selenium.click(LoginEnum.ERROR_BUTTON, "Login Error OK button click");
			selenium.Pause(2);
			selenium.isNotVisible(LoginEnum.ERROR_HEADER.getID(), "Login Error closed by OK button");
		}
	
	public void error_close(){
			selenium.click(LoginEnum.ERROR_CLOSE,"Login Error X button click");
			selenium.Pause(2);
			selenium.isNotVisible(LoginEnum.ERROR_HEADER, "Login Error closed by X button");
		}
	
//	public void click_login(String error_name){
//			selenium.click(login_id, error_name);
//			selenium.waitForPageToLoad("30000", error_name);
//			selenium.getLocation("tiwipro/app", error_name);
//		}
	
	public void type_username(String username){
			//enter tiwipro username
			selenium.type(LoginEnum.USERNAME_FIELD, username, "Username type text");
		}
	
	public void type_password(String password){
			//enter tiwipro password
			selenium.type(LoginEnum.PASSWORD_FIELD, password, "Password type text");
		}

	public void ck_forgot_password() {
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
	
	private void ck_error_msg(){
			//verify error message is displayed as expected
			selenium.isVisible(LoginEnum.ERROR_HEADER);
			
			selenium.isElementPresent(LoginEnum.ERROR_CLOSE, "Login Error X close element present");
			selenium.isElementPresent(LoginEnum.ERROR_BUTTON, "Login Error OK button present");
			selenium.isElementPresent(LoginEnum.ERROR_HEADER, "Login Error header present");
			
			selenium.getText(LoginEnum.ERROR_HEADER, "Log In Error", "Login Error header text");
			selenium.getText(LoginEnum.ERROR_MESSAGE, "Login Error message text");
			selenium.getText(LoginEnum.ERROR_BUTTON.getXpath(), "OK", "Login Error OK Button text");
		}
	

	public void ck_login_page(){
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
	
	public void ck_sent_page(){
		selenium.isTextPresent("Message Sent Succesfullly", "Message Sent Successfully text");
		selenium.getText("//ul[@id='grid_nav']/li[2]", "Message Sent Successfully", "Message Sent Successfully text");
	}
	
	public void forgotPassword(){
		forgotPassword( false, "" ); //Set up a default value, so we don't always have to specify
	}
	
	public void forgotPassword( String email ){
		forgotPassword( false, email );
	}
	
	public void forgotPassword( Boolean cancel ){
		forgotPassword( cancel, "" );
	}
	
	public void forgotPassword( String email, Boolean cancel){
		forgotPassword( cancel, email );
	}
	
	
	
	public void forgotPassword(Boolean cancel, String email){
		//verify all screen elements and text
		selenium.open("/tiwipro/login");
		forgot_password();
		ck_forgot_password();
				//select Cancel Button
				if (cancel){
						forgot_cancel();
					}
						//select close window
						forgot_close_window();
						//request password email
						forgot_password();
						// email needs to be verbalized
						forgot_email_type(email);
						forgot_submit();
						
				if ( email=="" ){
						forgot_error();
					}
			}
	
	
	public void forgotMessageSent() {
		selenium.getText(LoginEnum.MESSAGE_SENT_TITLE, "Forgot Username/Password title");
		selenium.getText(LoginEnum.FIRST_PARAGRAPH, "Forgot Username/Password sent message");
		
		selenium.getText(LoginEnum.HEADER, "Error message for Message Sent Page");
		selenium.getText(LoginEnum.BULLET_1, "First Bullet Forgot Username/Password");
		selenium.getText(LoginEnum.BULLET_2, "Second Bullet Forgot Username/Password");
		selenium.getText(LoginEnum.BULLET_3, "Third Bullet Forgot Username/Password");
	}
	
	public void ck_change_password() {
		
	}
	
	public void type_new_password(String password) {
		selenium.type(LoginEnum.NEW_PASSWORD, password);
	}
	public void type_confirm_password(String password) {
		selenium.type(LoginEnum.CONFIRM_PASSWORD, password);
	}
	
	public void click_change_password() {
		selenium.click(LoginEnum.CHANGE_PASSWORD_BUTTON, "Change Password Button");
	}
	
	public CoreMethodLib getSelenium() {
	    return this.selenium;
	}
	

	public ErrorCatcher get_errors(){
			return selenium.getErrors();
	}
}

