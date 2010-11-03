package com.inthinc.pro.web.selenium.portal.Login;

import static org.junit.Assert.*;

import java.util.Calendar;

import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
import com.inthinc.pro.web.selenium.portal.Singleton;
import com.thoughtworks.selenium.*;


public class loginScreen {
	
	private final String forgot_text = "Forgot your user name or password?";
	private final String forgot_link = "//a[@title='" + forgot_text + "']";
	
	private final String copyright_loc = "//li[@class='first']";
	private final String version_loc = "//li[@class='last']";
	
	
	private final Calendar cal = Calendar.getInstance();
	private final String copyright = "\u00a9" + String.valueOf(cal.get(Calendar.YEAR)) + " inthinc";
	
	private Error_Catcher errors;
	public static Selenium selenium;

	
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
	

	public void login_screen(){
		
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
		
		try{
			selenium.click(this.forgot_link);
		}catch( SeleniumException e){ errors.Error("Forgot Password Link", e);
		}
		
		try{
			assertTrue(selenium.isTextPresent("Forgot User Name or Password?"));
		}catch(AssertionError e){
			errors.Error("Forgot User Name or Password Text", e);
			}
		
		try{
			assertTrue(selenium.isTextPresent("E-mail Address"));
		}catch(AssertionError e){
			errors.Error("E-mail Address", e);
		}
		
		try{
			assertTrue(selenium.isElementPresent("changePasswordForm:email"));
		}catch(AssertionError e){
			errors.Error("email element", e);
		}
		
		try{
			assertTrue(selenium.isTextPresent("Send"));
		}catch(AssertionError e){ 
			errors.Error("Send", e);
		}
		
		try{
			assertTrue(selenium.isTextPresent("Cancel"));
		}catch(AssertionError e){ 
			errors.Error("Cancel", e);
		}
		
		try{
			assertTrue(selenium.isTextPresent("Forgot User Name or Password?"));
		}catch(AssertionError e){ 
			errors.Error("Forgot User Name Password", e);
		}
		
		
		//select Cancel Button
		if (cancel){
			try{
				selenium.click("changePasswordForm:PasswordCancelButton");
			}catch(SeleniumException	e){
				errors.Error("Change Password Cancel Button",e);
			}
		}
		
		//select close window
		try{
			selenium.click("//img[@onclick=\"Richfaces.hideModalPanel('forgotPasswordPanel')\"]");
		}catch(SeleniumException e){
			errors.Error("Click X", e);
		}
		
		//Verify Home Screen is displayed again
		ck_login_page();
		
		//request password email
		try{
			selenium.click(this.forgot_link);
		}catch(SeleniumException e){
			errors.Error(this.forgot_text + " element",e);
		}
		
		// email needs to be verbalized
		try{
			selenium.type("changePasswordForm:email", email);
		}catch(SeleniumException e){
			errors.Error("email field", e);
				}
		
		try{
			selenium.click("changePasswordForm:PasswordSubmitButton");
		}catch(SeleniumException e){
			errors.Error("Click Submit",e);
		}
		
		if ( email=="" ){
			try{
				assertTrue(selenium.isElementPresent("changePasswordForm:j_id37"));
			}catch(AssertionError e){
				errors.Error("Required message", e);
			}
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
	

	
		
	
	public void ck_login_page(){
		
		try{
			assertTrue(selenium.getText(this.forgot_link)==this.forgot_text);
		}catch(AssertionError e){
			errors.Error(this.forgot_text + " text",selenium.getText(this.forgot_link));
			errors.Expected(this.forgot_text + "text", this.forgot_text);
		}catch(SeleniumException e){
			errors.Error(this.forgot_text + " element", e);
		}catch(Exception e){
			errors.Error(this.forgot_text + " element", e);
		}
		
		try{
			assertTrue(selenium.isElementPresent(this.forgot_link));
		}
		catch(AssertionError e){
			errors.Error( this.forgot_text + " element", e);
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
			
			assertTrue(selenium.getText(this.copyright_loc)==this.copyright);
		}catch(AssertionError e){
			errors.Error("Copyright text", selenium.getText(this.copyright_loc));
			errors.Expected("Copyright text", this.copyright);
		}catch(SeleniumException e){
			errors.Error("Copyright element", e);
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
	
	
	
	public static void main( String[] args){
//		NAVIGATE nav = new NAVIGATE();
//		try {
//			nav.setUp();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try{
			loginScreen login = new loginScreen();
//			nav.set_selenium(loginScreen.selenium);
			login.login_screen();
			login.ForgotPassword("failure@fail.com");
			loginScreen.selenium.close();
			loginScreen.selenium.stop();
			System.out.print(login.get_errors().get_errors().toString());
//			nav.tearDown();
//		}catch(Exception e){
//			nav.tearDown();
//		}
	}
}

