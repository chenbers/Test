package com.inthinc.pro.web.selenium.Test_Cases;

import com.inthinc.pro.web.selenium.portal.NAVIGATE;
import com.inthinc.pro.web.selenium.portal.Singleton;
import com.inthinc.pro.web.selenium.portal.Masthead.Masthead;
import com.inthinc.pro.web.selenium.portal.Notifications.Notifications;
import com.thoughtworks.selenium.*;

import org.testng.annotations.*;
import static org.testng.AssertJUnit.*;

import org.testng.*;



@SuppressWarnings("unused")
@Test 
public class Notification_redflag_HP extends SeleneseTestCase {
	//define local vars
	
	
	

	
	/**
	 * @throws Exception
	 */
	public static void main() throws Exception {
	//create instance of library objects
		Singleton tvar = Singleton.getSingleton() ; 
		Selenium selenium = tvar.getSelenium();
	
//	
//	NAVIGATE navto;
//	navto = new NAVIGATE();
//	
//	Masthead mast;
//	mast = new Masthead();
//	
//	Notifications N;
//	N = new Notifications();
//
//	navto.setUp();
//	navto.login("larrington", "tekpass");
//
//	
//	
//	
//	mast.mainMenuItem("Notifications");
//	
//	N.menuItem("Red Flags");
//	N.ckRedflagScr();
//	
//	N.selectTimeFrame("redFlags", "Today");
//	
//	N.enterInfo("redFlags", "vehicle", "Mustang");
//	N.enterInfo("redFlags", "group", "Groupone");
//	N.enterInfo("redFlags", "driver", "Lee");
//	
//	N.click_refresh("redFlags");
//	
//	navto.logout();
//	navto.tearDown();	//perform tear down tasks
	
	}
}



