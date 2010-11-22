package com.inthinc.pro.web.selenium.portal.Login;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.web.selenium.Core;
import com.inthinc.pro.web.selenium.Selenium_Server;
import com.inthinc.pro.web.selenium.Singleton;
import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
/****************************************************************************************
 * Purpose: Includes all methods and variables to process the TiwiPro Login Screen
 * @author larringt , dtanner
 * Last Update:  11/18/Added comments and made changes to adhere to Java Coding Standards
 */

public class Login extends Selenium_Server {
	
	
	//Object Map
	private final String change_password_form_id = "//form[@id='changePasswordForm']/";
	private final String login_form_id = "//form[@id='loginForm']/";

	private final String error_button_id = "loginErrorForm:loginOk";
	private final String error_button_xpath = "//form[@id='loginErrorForm']/div/button";
	private final String error_button_text_xpath = "//form[@id='loginErrorForm']/div/button/span";
	
	private final String error_close_onclick = "Richfaces.hideModalPanel('errorPanel')";
	private final String error_close_xpath = "//div[@id='errorPanelContentDiv']/div";
	private final String error_close_img = "//div[@id='errorPanelContentDiv']/div/img";
	
	private final String error_header_id = "errorPanelHeader";
	private final String error_header_xpath = "//table[@id='errorPanelContentTable']/tbody/tr[1]/td/div";
	
	private final String error_message_xpath = "//p";
	private final String error_message_text = StringEscapeUtils.unescapeHtml("Incorrect user name or password<br/><br/>Please try again.");
	
	private final String forgot_error_id = "changePasswordForm:j_id37";
	private final String forgot_error_xpath = change_password_form_id+ "div[1]/div/span[@id='" +forgot_error_id+ "']/span";
	
	private final String forgot_title = "//div[@id='forgotPasswordPanelHeader']";
	private final String forgot_text_title = "Forgot your user name or password?";
	private final String forgot_text_id = "j_id28:loginForgotPassword";
	private final String forgot_text_xpath = "//a[@title='" + forgot_text_title + "']";
	
	private final String forgot_email_field_id = "changePasswordForm:email";
	private final String forgot_email_field_xpath = change_password_form_id+ "div[1]/input";
	private final String forgot_email_field_xpath_alt = "//input[@id='"+forgot_email_field_id+"']";
		
	private final String forgot_email_label_xpath = change_password_form_id+ "div[1]";

	private final String forgot_close_xpath = "//div[@id='forgotPasswordPanelContentDiv']/div/img";
	private final String forgot_close_xpath_alt = "//img[@onclick=\"Richfaces.hideModalPanel('forgotPasswordPanel')\"]";
	
	private final String forgot_cancel_id = "changePasswordForm:PasswordCancelButton";
	private final String forgot_cancel_xpath = "//div[@class='popupactions']/buton[@id='" +forgot_cancel_id+ "']/span";
	private final String forgot_cancel_xpath_alt = "//span[@class='cancel']";
	
	private final String forgot_send_id = "changePasswordForm:PasswordSubmitButton";
	private final String forgot_send_xpath = "//div[@class='popupactions']/buton[@id='" +forgot_send_id+ "']/span";
	private final String forgot_send_xpath_alt = "//span[@class='retrieve_password']";
	
	private final String forgot_message_xpath = "//table[@id='forgotPasswordPanelContentTable']/tbody/tr[2]/td/div";
	private final String forgot_message_text = "Enter the e-mail address from your tiwiPRO account. Information pertaining to your user name and password will be sent to this e-mail account.";
	
	private final String login_id = "loginLogin";
	private final String login_xpath = "form[@id='loginForm']/table/tbody/tr[3]/td/button";
	private final String login_xpath_alt = "//button[@id='" +login_id+ "']";
	private final String login_text_xpath = login_form_id+ "table/tbody/tr[3]/td/button/span";
	private final String login_text_xpath_alt = "//span[@class='login']";
		
	private final String password_id = "j_password";
	private final String password_label = login_form_id+ "table/tbody/tr[2]/td[1]";
	private final String password_xpath = login_form_id+ "table/tbody/tr[2]/td[2]/input";
	private final String password_xpath_alt = "//input[@id='"+password_id+"']";
	
	private final String username_id = "j_username";
	private final String username_label = login_form_id+ "table/tbody/tr[1]/td[1]";
	private final String username_xpath = login_form_id+ "table/tbody/tr[1]/td[2]/input";
	private final String username_xpath_alt = "//input[@id='"+username_id+"']";

	protected static Core selenium;

	public Login(){
			this(Singleton.getSingleton().getSelenium());
		}
	
	public Login(Singleton tvar ){
			this(tvar.getSelenium());
		}
	
	public Login( Core sel ){
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

	public void login_to_portal(HashMap user, HashMap pass) {
			// TODO Auto-generated method stub
		}
	public void forgot_close_window() {
			selenium.click(forgot_close_xpath_alt, "Forgot Close click");
		}

	public void forgot_cancel() {
			selenium.click(forgot_cancel_id, "Forgot Cancel click");		
			selenium.isElementPresent(forgot_cancel_id,"Forgot Cancel clicked");
		}

	public void forgot_error() {
			selenium.isElementPresent(forgot_error_id, "Required Message element");
			selenium.getText(forgot_error_id, "Required", "Required Message text");
		}

	public void forgot_submit() {
			selenium.click(forgot_send_id, "Submit Button");
		}

	public void forgot_email_type(String email) {
			selenium.type(forgot_email_field_id, email, "Email field");
		}

	public void forgot_password() {
			selenium.click(forgot_text_xpath, forgot_text_title+" button click");
		}
	
	public void click_login(){
			selenium.click(login_id, "Login button click");
			selenium.wait_for_element_present("Admin", "link");
			String location = selenium.getLocation("tiwipro/app", "Login button click");
			if (location.indexOf("tiwipro/app") == -1){
				ck_error_msg();
				error_ok();
			}
		}
	
	public void error_ok(){
			selenium.click(error_button_id, "Login Error OK button click");
			selenium.Pause(2);
			selenium.isNotVisible(error_header_id, "Login Error closed by OK button");
		}
	
	public void error_close(){
			selenium.click(error_close_xpath,"Login Error X button click");
			selenium.Pause(2);
			selenium.isNotVisible(error_header_id, "Login Error closed by X button");
		}
	
//	public void click_login(String error_name){
//			selenium.click(login_id, error_name);
//			selenium.waitForPageToLoad("30000", error_name);
//			selenium.getLocation("tiwipro/app", error_name);
//		}
	
	public void type_username(String username){
			//enter tiwipro username
			selenium.type(username_id, username, "Username type text");
		}
	
	public void type_password(String password){
			//enter tiwipro password
			selenium.type(password_id, password, "Password type text");
		}

	public void ck_forgot_password() {
			//verify Forgot password window is displayed as expected
			selenium.isVisible(forgot_title, "Forgot Pop Up element visible");
			
			selenium.isElementPresent(forgot_email_field_id, "Email text field");
			selenium.isElementPresent(forgot_close_xpath, "Forgot Close button" );
			
			selenium.getText(forgot_title, "Forgot User Name or Password?", "Forgot Pop Up Title text present");
			selenium.getText(forgot_message_xpath, forgot_message_text, "Forgot message");
			selenium.getText(forgot_send_id, "Send", "Fogot Send button");
			selenium.getText(forgot_cancel_id, "Cancel", "Forgot Cancel button");
			selenium.getText(forgot_email_label_xpath, "E-mail Address:", "E-mail Address label");
		}
	
	private void ck_error_msg(){
			//verify error message is displayed as expected
			selenium.isVisible(error_header_xpath);
			
			selenium.isElementPresent(error_close_xpath, "Login Error X close element present");
			selenium.isElementPresent(error_button_id, "Login Error OK button present");
			selenium.isElementPresent(error_header_id, "Login Error header present");
			
			selenium.getText(error_header_xpath, "Log In Error", "Login Error header text");
			selenium.getText(error_message_xpath, error_message_text, "Login Error message text");
			selenium.getText(error_button_text_xpath, "OK", "Login Error OK Button text");
		}
	

	public void ck_login_page(){
		selenium.getText(forgot_text_xpath, forgot_text_title, forgot_text_title+" text present");
		selenium.isElementPresent(forgot_text_xpath, forgot_text_title +" element present");
		
		selenium.isTextPresent("Log In", "Log In text present");
		selenium.getText(login_text_xpath, "Log In", "Login text match");
		selenium.isElementPresent(login_text_xpath, "Login text element present");
		
		selenium.isElementPresent(login_id, "Login field element present");
		
		selenium.isTextPresent("User Name:", "User Name: text present");
		selenium.isElementPresent(username_id, "User Name: element present");
		selenium.getText(username_label, "User Name:", "User Name: text match");
		
		selenium.isTextPresent("Password:", "Password: text present");
		selenium.isElementPresent(password_id, "Password: element present");
		selenium.getText(password_label, "Password:", "Password: text match");
	}
	
	public void ck_sent_page(){
		selenium.isTextPresent("Message Sent Succesfullly", "Message Sent Successfully text");
		selenium.getText("//ul[@id='grid_nav']/li[2]", "Message Sent Successfully", "Message Sent Successfully text");
	}
	
	public void ForgotPassword(){
		ForgotPassword( false, "" ); //Set up a default value, so we don't always have to specify
	}
	
	public void ForgotPassword( String email ){
		ForgotPassword( false, email );
	}
	
	public void ForgotPassword( Boolean cancel ){
		ForgotPassword( cancel, "" );
	}
	
	public void ForgotPassword( String email, Boolean cancel){
		ForgotPassword( cancel, email );
	}
	
	
	public void ForgotPassword(Boolean cancel, String email){
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
	
	

	public Error_Catcher get_errors(){
			return selenium.getErrors();
		}
	
	
	private void test_self(){
			open_login();
			ck_login_page();
			ForgotPassword();
		}
	
	
	public static void main( String[] args){
			try {
						Login.setUp();
					} catch (Exception e) {
						e.printStackTrace();
					}
			Login login;
			login = new Login();
			login.test_self();
			Object errors = "";
			errors = login.get_errors().get_errors();
			System.out.println(errors.toString());	
			try{
						tearDown();
					}catch(Exception e){
						e.printStackTrace();
					}
			assertTrue(errors.toString()=="{}");
		}

	
}

