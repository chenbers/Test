package com.inthinc.pro.web.selenium.Test_Cases;

import com.inthinc.pro.web.selenium.portal.NAVIGATE;
import com.inthinc.pro.web.selenium.portal.Singleton;
import com.inthinc.pro.web.selenium.portal.Login.loginScreen;
import com.inthinc.pro.web.selenium.portal.Notifications.Notifications;
import com.thoughtworks.selenium.*;

import org.testng.annotations.*;
import static org.testng.AssertJUnit.*;
import org.testng.*;



@SuppressWarnings("unused")
@Test 
public class Test_script extends SeleneseTestCase {
	//define local vars
	
	
	

	
	public static void main() throws Exception {
	//create instance of library objects
		Singleton tvar = Singleton.getSingleton() ; 
		Selenium selenium = tvar.getSelenium();
	
	
	NAVIGATE navto;
	navto = new NAVIGATE();
	
	loginScreen ls;
	ls = new loginScreen();

	Notifications not;
	not = new Notifications();
	
	navto.setUp("firefox","https://qa.tiwipro.com:8423/tiwipro/");
	navto.login("larrington", "tekpass");

	
	
	
	
	navto.logout();		//logout of tiwipro app
	navto.tearDown();	//perform tear down tasks
	
	}
}



