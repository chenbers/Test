package com.inthinc.pro.web.selenium.portal.Login;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.*;

import com.inthinc.pro.web.selenium.Selenium_Server;
import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
import com.inthinc.pro.web.selenium.portal.Singleton;
import com.thoughtworks.selenium.*;


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
	
	private final String forgot_message_xpath = "//table[@id='forgotPasswordPanelContentTable'/tbody/tr[2]/td/div";
	private final String forgot_message_text = "Enter the e-mail address from your tiwiPRO account. Information pertaining to your user name and password will be sent to this e-mail account.";
	
	private final String login_id = "loginLogin";
	private final String login_xpath = "form[@id='loginForm']/table/tbody/tr[3]/td/button";
	private final String login_xpath_alt = "//button[@id='" +login_id+ "']";
	private final String login_text_xpath = login_form_id+ "table/tbody/tr[3]/td/button/span";
	private final String login_text_xpath_alt = "//span[@class='login']";
		
	private final String password_id = "j_password";
	private final String password_label = login_form_id+ "table/tbody/tr[2]/td[1]/input";
	private final String password_xpath = login_form_id+ "table/tbody/tr[2]/td[2]/input";
	private final String password_xpath_alt = "//input[@id='"+password_id+"']";
	
	private final String username_id = "j_username";
	private final String username_label = login_form_id+ "table/tbody/tr[1]/td[1]/input";
	private final String username_xpath = login_form_id+ "table/tbody/tr[1]/td[2]/input";
	private final String username_xpath_alt = "//input[@id='"+username_id+"']";
	

	private final String version_loc = "//li[@class='last']";
	
	
	private final Calendar cal = Calendar.getInstance();
//	private final String copyright_title = StringEscapeUtils.unescapeHtml("&copy;" + String.valueOf(cal.get(Calendar.YEAR)) + " inthinc");
	private final String copyright_title = StringEscapeUtils.unescapeHtml("&#169;2009 inthinc");
	private final String copyright_loc = "//form[@id='footerForm']/ul/li[@class='first']";
	
	private Error_Catcher errors;
	protected static Selenium selenium;

	
	
	
	public loginScreen(){
		this(Singleton.getSingleton());
	}
	
	public loginScreen( Singleton tvar ){
		
		selenium = tvar.getSelenium();
		errors = new Error_Catcher();
	}
	
	public loginScreen( Selenium sel ){
		selenium = sel;
		errors = new Error_Catcher();
	}
	
	public loginScreen(Singleton tvar, Error_Catcher errors ){
		
		selenium = tvar.getSelenium();
		this.errors = errors;
	}
	
	public loginScreen( Error_Catcher errors, Selenium sel){
		this( sel, errors );
	}
	
	public loginScreen( Selenium sel, Error_Catcher errors){
		selenium = sel;
		this.errors = errors;
	}
	

	public void open_login(){
		
		//go to login screen
		selenium.open("login");  
		//Verify Home Screen is displayed
		ck_login_page();
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
		
		//Verify Home Screen is displayed again
		ck_login_page();
		
		//request password email
		forgot_password();
		
		// email needs to be verbalized
		forgot_email_type(email);
		
		forgot_submit();
		
		if ( email=="" ){
			forgot_error();
		}
		
		
		
//		What does this section do???		
//		//attach to new tab
//		String feedWinId = selenium.getEval("{var windowId; for(var x in selenium.browserbot.openedWindows ) {windowId=x;} }");
//		selenium.selectWindow(feedWinId);
//		selenium.windowFocus();
//		//verify message
//		selenium.open("/tiwipro/sent");
//
//		assertTrue(selenium.isTextPresent("Message Sent Successfully"));
//		assertEquals(selenium.getText("//ul[@id='grid_nav']/li[2]"), "Message Sent Successfully");
		
	}

	public void forgot_close_window() {
		try{
			selenium.click(forgot_close_xpath_alt);
		}catch(SeleniumException e){
			errors.Error("Forgot Close click", e);
		}catch(Exception e){
			errors.Error("Forgot Close click", e);
		}
	}

	public void forgot_cancel() {
		
		try{
			selenium.click(forgot_cancel_id);
		}catch(SeleniumException	e){
			errors.Error("Forgot Cancel click",e);
		}catch(Exception e){
			errors.Error("Forgot Cancel click", e);
		}
		
		try{
			assertFalse(selenium.isElementPresent(forgot_cancel_id));
		}catch(AssertionError e){
			errors.Error("Forgot Cancel clicked", e);
		}catch(SeleniumException e){
			errors.Error("Forgot Cancel clicked", e);
		}catch(Exception e){
			errors.Error("Forgot Cancel clicked", e);
		}
	}

	public void forgot_error() {
		
		try{
			assertTrue(selenium.isElementPresent(forgot_error_id));
		}catch(AssertionError e){
			errors.Error("Required Message element", e);
		}catch(SeleniumException e){
			errors.Error("Required Message element", e);
		}catch(Exception e){
			errors.Error("Required Message element", e);
		}
		
		try{ 
			assertTrue(selenium.getText(forgot_error_id)=="Required");
		}catch(AssertionError e){
			errors.Error("Required Message text", selenium.getText(forgot_error_id));
			errors.Expected("Required Message text", "Required");
		}catch(SeleniumException e){
			errors.Error("Required Message text", e);
		}catch(Exception e){
			errors.Error("Required Message text", e);
		}
	}

	
	public void forgot_submit() {
		
		try{
			selenium.click(forgot_send_id);
		}catch(SeleniumException e){
			errors.Error("Submit Button",e);
		}catch(Exception e){
			errors.Error("Submit Button", e);
		}
	}

	public void forgot_email_type(String email) {
		
		try{
			selenium.type(forgot_email_field_id, email);
		}catch(SeleniumException e){
			errors.Error("email field", e);
		}catch(Exception e){
			errors.Error("email field", e);
		}
	}

	public void forgot_password() {
		
		try{
			selenium.click(forgot_text_xpath);
		}catch( SeleniumException e){ 
			errors.Error(forgot_text_title + " link", e);
		}catch(Exception e){
			errors.Error(forgot_text_title + "link", e);
		}
	}

	
	public void ck_forgot_password() {
		
		try{ //Pop up Title
			assertTrue(selenium.isTextPresent("Forgot User Name or Password?"));
		}catch(AssertionError e){
			errors.Error("Forgot Pop Up Title text", e);
		}catch(SeleniumException e){
			errors.Error("Forgot Pop Up Title text", e);
		}catch(Exception e){
			errors.Error("Forgot Pop Up Title text", e);
		}
		
		try{ //Email Field Label
			assertTrue(selenium.getText(forgot_email_label_xpath)=="E-mail Address");
		}catch(AssertionError e){
			errors.Error("E-mail Address label", selenium.getText(forgot_email_label_xpath));
			errors.Error("E-mail Address label", "E-mail Address");
		}catch(SeleniumException e){
			errors.Error("E-mail Address label", e);
		}catch(Exception e){
			errors.Error("E-mail Address label", e);
		}
		
		try{ //Email Text Field
			assertTrue(selenium.isElementPresent(forgot_email_field_id));
		}catch(AssertionError e){
			errors.Error("Email text field", e);
		}catch(SeleniumException e){
			errors.Error("Email text field", e);
		}catch(Exception e){
			errors.Error("Email text field", e);
		}
		
		try{ //Send button
			assertTrue(selenium.getText(forgot_send_id)=="Send");
		}catch(AssertionError e){ 
			errors.Error("Forgot Send button", e);
		}catch(SeleniumException e){
			errors.Error("Forgot Send button", e);
		}catch(Exception e){
			errors.Error("Forgot Send button", e);
		}
		
		try{ //Cancel Button
			assertTrue(selenium.getText(forgot_cancel_id)=="Cancel");
		}catch(AssertionError e){ 
			errors.Error("Forgot Cancel button", e);
		}catch(SeleniumException e){
			errors.Error("Forgot Cancel button", e);
		}catch(Exception e){
			errors.Error("Forgot Cancel button", e);
		}
		
		try{ //Close button
			assertFalse(selenium.isElementPresent(forgot_close_xpath));
		}catch(AssertionError e){
			errors.Error("Forgot Close button", e);
		}catch(SeleniumException e){
			errors.Error("Forgot Close button", e);
		}catch(Exception e){
			errors.Error("Forgot Close button", e);
		}
		
		try{ //Message
			assertTrue(selenium.getText(forgot_message_xpath)==forgot_message_text);
		}catch(AssertionError e){
			errors.Error("Forgot message", selenium.getText(forgot_message_xpath));
			errors.Expected("Forgot message", forgot_message_text);
		}catch(SeleniumException e){
			errors.Error("Forgot message", e);
		}catch(Exception e){
			errors.Error("Forgot message", e);
		}
	}
	

	
		
	
	public void ck_login_page(){
		
		try{
			assertTrue(selenium.getText(forgot_text_xpath)==forgot_text_title);
		}catch(AssertionError e){
			errors.Error(forgot_text_title + " text",selenium.getText(forgot_text_xpath));
			errors.Expected(forgot_text_title + " text", forgot_text_title);
		}catch(SeleniumException e){
			errors.Error(forgot_text_title + " element", e);
		}catch(Exception e){
			errors.Error(forgot_text_title, e);
		}
		
		try{
			assertTrue(selenium.isElementPresent(forgot_text_xpath));
		}
		catch(AssertionError e){
			errors.Error( forgot_text_title + " element", e);
		}
		
		
		try{
			assertEquals(selenium.getTitle(), "tiwiPRO");
		}catch(AssertionError e){
			errors.Error("Title",e);
		}
		
		try{
			assertTrue(selenium.isTextPresent("Log In"));
		}catch(AssertionError e){
			errors.Error("Log In text",e);
		}
		
		try{
			assertTrue(selenium.isTextPresent("User Name:"));
		}catch(AssertionError e){
			errors.Error("User Name: text",e);
		}
		
		try{
			assertTrue(selenium.isTextPresent("Password:"));
		}catch(AssertionError e){
			errors.Error("Password: text",e);
		}
		
		try{
			assertTrue(selenium.isElementPresent("j_username"));
		}catch(AssertionError e){
			errors.Error("User Name element",e);
		}
		
		try{
			assertTrue(selenium.isElementPresent("j_password"));
		}catch(AssertionError e){
			errors.Error("Password element",e);
		}
		
		try{
			assertTrue(selenium.getText(copyright_loc)==copyright_title);
		}catch(AssertionError e){
			errors.Error("Copyright text", selenium.getText(copyright_loc));
			errors.Expected("Copyright text", copyright_title);
		}catch(SeleniumException e){
			errors.Error("Copyright element", e);
		}catch(Exception e){
			errors.Error("Copyright", e);
		}
	}
	
	
	public void ck_sent_page(){
		try{
			assertTrue(selenium.isTextPresent("Message Sent Successfully"));
		}catch(AssertionError e){
			errors.Error("Message Sent Successfully text", e);
		}
		
		try{
			assertEquals(selenium.getText("//ul[@id='grid_nav']/li[2]"), "Message Sent Successfully");
		}catch(AssertionError e){
			errors.Error("Message Sent Successfully text", e);
		}

	}
	
	public Error_Catcher get_errors(){
		return this.errors;
	}
	
	
	@Test
	public void test_self(){
		ForgotPassword();
	}
	
	
	public static void main( String[] args){
		try {
			Selenium_Server.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loginScreen login = new loginScreen();
		try{
			login.open_login();
			login.ForgotPassword("failure@fail.com");
			loginScreen.selenium.close();
			loginScreen.selenium.stop();
			System.out.print(login.get_errors().get_errors().toString());
		}catch(Exception e){
			loginScreen.selenium.close();
			loginScreen.selenium.stop();
		}
		
		try{
			Selenium_Server.tearDown();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

