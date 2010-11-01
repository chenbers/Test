package com.inthinc.pro.web.selenium.Test_Cases;

import com.inthinc.pro.web.selenium.portal.*;
import com.inthinc.pro.web.selenium.portal.Masthead.Masthead;
import com.inthinc.pro.web.selenium.portal.Notifications.Notifications;
import com.inthinc.pro.web.selenium.portal.Login.*;
import com.thoughtworks.selenium.*;

import org.testng.annotations.*;
import static org.testng.AssertJUnit.*;

import org.testng.*;



@SuppressWarnings("unused")
@Test 
public class LoginScreen_HP extends SeleneseTestCase {
	//define local vars
	
	
	

	
	/**
	 * @throws Exception
	 */
	public static void main() throws Exception {
	//create instance of library objects
		Singleton tvar = Singleton.getSingleton() ; 
		Selenium selenium = tvar.getSelenium();
	
	
	NAVIGATE navto;
	navto = new NAVIGATE();
	
	Masthead mast;
	mast = new Masthead();
	
	Notifications N;
	N = new Notifications();

	loginScreen login;
	login = new loginScreen();

	navto.setUp("firefox","https://qa.tiwipro.com:8423/tiwipro/");
	login.login_screen();

	login.ck_Homepage();
	login.ForgotPassword();
	
	
	
	navto.tearDown();	//perform tear down tasks
	
	}
}



