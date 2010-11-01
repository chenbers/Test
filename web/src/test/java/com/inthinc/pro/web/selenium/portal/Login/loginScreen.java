package com.inthinc.pro.web.selenium.portal.Login;

import org.openqa.selenium.server.SeleniumServer;

import com.inthinc.pro.web.selenium.portal.Singleton;
import com.thoughtworks.selenium.*;

@SuppressWarnings("unused")
public class loginScreen {

	//define local vars
	Singleton tvar = Singleton.getSingleton() ; 
	Selenium selenium = tvar.getSelenium();
	
	
	//Maintain the keyword Count
	private int KeywordCount;
	
	
	public int getKeywordCount(){
		return KeywordCount;
	}
	
	public void login_screen(){
		
		//go to login screen
		selenium.open("login");  
		selenium.waitForPageToLoad("10000");	
	}
	
	public void ForgotPassword(){
		
		//verify all screen elements and text
		selenium.open("/tiwipro/login");
		selenium.click("j_id28:loginForgotPassword");
		assertTrue(selenium.isTextPresent("exact:Forgot User Name or Password?"));
		assertTrue(selenium.isTextPresent("E-mail Address"));
		assertTrue(selenium.isElementPresent("changePasswordForm:email"));
		assertTrue(selenium.isTextPresent("Send"));
		assertTrue(selenium.isTextPresent("Cancel"));
		assertTrue(selenium.isTextPresent("exact:Forgot User Name or Password?"));
		
		
		//select Cancel Button
		selenium.click("changePasswordForm:PasswordCancelButton");
		//Verify Home Screen is displayed again
		ck_Homepage();
		
		//select close window
		selenium.click("//img[@onclick=\"Richfaces.hideModalPanel('forgotPasswordPanel')\"]");	
		//Verify Home Screen is displayed again
		ck_Homepage();
		
		//request password email
		selenium.click("j_id28:loginForgotPassword");
		// email needs to be verbalized
		selenium.type("changePasswordForm:email", "larringt@teksystems.com");
		selenium.click("changePasswordForm:PasswordSubmitButton");
		
		//attach to new tab
		String feedWinId = selenium.getEval("{var windowId; for(var x in selenium.browserbot.openedWindows ) {windowId=x;} }");
		selenium.selectWindow(feedWinId);
		selenium.windowFocus();
		//verify message
		selenium.open("/tiwipro/sent");
		assertTrue(selenium.isTextPresent("Message Sent Successfully"));
		assertEquals(selenium.getText("//ul[@id='grid_nav']/li[2]"), "Message Sent Successfully");
		
	}
	
		
	
	public void ck_Homepage(){
		selenium.open("/tiwipro/login");
		assertTrue(selenium.isTextPresent("exact:Forgot your user name or password?"));
		assertEquals(selenium.getTitle(), "tiwiPRO");
		assertTrue(selenium.isTextPresent("Log In"));
		assertTrue(selenium.isTextPresent("User Name:"));
		assertTrue(selenium.isTextPresent("exact:Password:"));
		assertTrue(selenium.isElementPresent("j_username"));
		assertTrue(selenium.isElementPresent("j_password"));
		assertTrue(selenium.isTextPresent("2009 inthinc"));
	}
	
	
	

	
	
	
}

