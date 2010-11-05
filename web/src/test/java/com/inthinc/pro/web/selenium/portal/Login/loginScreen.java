package com.inthinc.pro.web.selenium.portal.Login;

import java.util.Calendar;

import org.apache.commons.lang.StringEscapeUtils;
import static org.junit.Assert.*;

import com.inthinc.pro.web.selenium.Core;
import com.inthinc.pro.web.selenium.Selenium_Server;
import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
import com.inthinc.pro.web.selenium.portal.Singleton;


public class loginScreen extends Selenium_Server {
	
	private final String change_password_form_id = "//form[@id='changePasswordForm']/";
	private final String login_form_id = "//form[@id='loginForm']/";
	
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
	

	private final String version_loc = "//li[@class='last']";
	
	
	private final Calendar cal = Calendar.getInstance();
	private final String copyright_title = StringEscapeUtils.unescapeHtml("&#169;" + String.valueOf(cal.get(Calendar.YEAR)) + " inthinc");
//	private final String copyright_title = StringEscapeUtils.unescapeHtml("&#169;2009 inthinc");
	private final String copyright_loc = "//form[@id='footerForm']/ul/li[@class='first']";
	
	protected static Core selenium;

	
	
	
	public loginScreen(){
		this(Singleton.getSingleton());
	}
	
	public loginScreen(Singleton tvar ){
		this(tvar.getSelenium());
	}
	
	public loginScreen( Core sel ){
		selenium = sel;
	}
	
	public void open_login(){
		
		//go to login screen
		selenium.open("login");  
		//Verify Home Screen is displayed
		ck_login_page();
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
	}
	
	public void type_username(String username){
		selenium.type(username_id, username, "Username type text");
	}
	
	public void type_password(String password){
		selenium.type(password_id, password, "Password type text");
	}

	public void ck_forgot_password(){
		ck_forgot_password(true);
	}
	
	public void ck_forgot_password(Boolean visible) {
		
		if (visible)
		selenium.isTextPresent("Forgot User Name or Password?", "Forgot Pop Up Title text present");
		selenium.getText(forgot_email_label_xpath, "E-mail Address label", "E-mail Address");
		selenium.isElementPresent(forgot_email_field_id, "Email text field");
		selenium.getText(forgot_send_id, "Fogot Send button", "Send");
		selenium.getText(forgot_cancel_id, "Forgot Cancel button", "Cancel");
		selenium.isElementPresent(forgot_close_xpath, "Forgot Close button" );
		selenium.getText(forgot_message_xpath, "Forgot message", forgot_message_text);
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
	
	public Error_Catcher get_errors(){
		return selenium.getErrors();
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
	
	
	
	public void test_self(){
		
		open_login();
		ck_login_page();
		ForgotPassword();
		
	}
	
	
	public static void main( String[] args){
		try {
			loginScreen.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		loginScreen login;
		login = new loginScreen();
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

