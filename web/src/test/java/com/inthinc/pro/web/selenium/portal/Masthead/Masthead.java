//Master Heading Window 
//Maintained as .JAVA

package com.inthinc.pro.web.selenium.portal.Masthead;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.server.SeleniumServer;

import com.inthinc.pro.web.selenium.portal.Singleton;
import com.thoughtworks.selenium.*;

@SuppressWarnings("unused")
public class Masthead
{	//define local vars
	Singleton tvar = Singleton.getSingleton() ; 
	Selenium selenium = tvar.getSelenium();
	
	
	//Maintain the keyword Count
	private int KeywordCount;
	
	
	public int getKeywordCount(){
		return KeywordCount;
	}
	
@Test
public void mainMenuItem(String screen){
		
		if (screen.contentEquals("Reports)")){
		//select Reports
		selenium.open("/tiwipro/app/dashboard/");
		selenium.click("//a[@id='navigation:layout-navigationReports']/span");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Driver Report"));
		}else if (screen.contentEquals("Notifications")){
			//select Notifications
			selenium.click("//a[@id='navigation:layout-navigationNotifications']/span");
			selenium.waitForPageToLoad("30000");
			assertTrue(selenium.isTextPresent("Red Flags"));
			selenium.open("/tiwipro/app/notifications/");
		}else if (screen.contentEquals("LiveFleet")){
			//select Live Fleet
			selenium.click("//a[@id='navigation:layout-navigationLiveFleet']/span");
			selenium.waitForPageToLoad("30000");
			assertTrue(selenium.isTextPresent("Live Fleet"));
			selenium.open("/tiwipro/app/liveFleet");
		}else if (screen.contentEquals("Admin")){
			//select Admin
			selenium.click("//a[@id='navigation:layout-navigationAdmin']/span");
			selenium.waitForPageToLoad("30000");
			assertTrue(selenium.isTextPresent("exact:Admin: Users"));
			selenium.open("/tiwipro/app/admin/");
		}
		
	}
	
	@Test
	public void Select_Link(String linkname, String checktext, String PopuporTab)
	{
		selenium.open("/tiwipro/app/dashboard/");
		selenium.click(linkname);
			//get popup window name and attach
			String feedWinId = selenium.getEval("{var windowId; for(var x in selenium.browserbot.openedWindows ) {windowId=x;} }");
			selenium.selectWindow(feedWinId);
			selenium.windowFocus();
			if (PopuporTab.contentEquals("Popup")){
				checkPopup(checktext);
			} else if (PopuporTab.contentEquals("NewTab")) {
				checktab(checktext);
			}
		// select original window
		selenium.selectWindow(null);
		
	}
	
	public void checktab(String texttocheck){
		//This function verifies text on new tab 
		if (texttocheck.contentEquals("Customer Support")){
			assertEquals(selenium.getTitle(), "tiwiPRO");
		} else {
		assertTrue(selenium.isTextPresent(texttocheck));
	}}
	
	public void checkPopup(String texttocheck){
		//This function verifies text on pop up window 
		//"Actual New window not new tab.
		if (texttocheck.contentEquals("LEGAL NOTICE")){
			selenium.open("/tiwipro/html/inthincCustomerTermsOfService.html");
		}else if (texttocheck.contentEquals("Privacy Policy")){
			selenium.open("/tiwipro/html/inthincPrivacyPolicy.html");
		}
		assertEquals(selenium.getText("//b"), texttocheck);
	}
	
	@Test
	public void ValidateScreen(){
		//Validate Home Screen
		selenium.open("/tiwipro/app/dashboard/");
		assertEquals(selenium.getTitle(), "tiwiPRO");
		assertTrue(selenium.isTextPresent(""));
		assertTrue(selenium.isTextPresent("Help"));
		assertTrue(selenium.isElementPresent("link=Help"));
		assertTrue(selenium.isTextPresent("My Messages"));
		assertTrue(selenium.isElementPresent("headerForm:headerMyMessages"));
		assertTrue(selenium.isTextPresent("My Account"));
		assertTrue(selenium.isElementPresent("headerForm:headerMyAccount"));
		assertTrue(selenium.isTextPresent("Log Out"));
		assertTrue(selenium.isElementPresent("link=Log Out"));
		assertTrue(selenium.isTextPresent("2009 inthinc"));
		assertTrue(selenium.isTextPresent("Privacy Policy"));
		assertTrue(selenium.isElementPresent("footerForm:privacy"));
		assertTrue(selenium.isTextPresent("Legal Notice"));
		assertTrue(selenium.isElementPresent("footerForm:legal"));
		assertTrue(selenium.isTextPresent("Support"));
		assertTrue(selenium.isElementPresent("footerForm:customerSupport"));
		assertTrue(selenium.isElementPresent("//span[@id='tree_link']/span/small"));
		assertTrue(selenium.isTextPresent("Reports"));
		assertTrue(selenium.isElementPresent("//a[@id='navigation:layout-navigationReports']/span"));
		assertTrue(selenium.isTextPresent("Notifications"));
		assertTrue(selenium.isElementPresent("//a[@id='navigation:layout-navigationNotifications']/span"));
		assertTrue(selenium.isTextPresent("Live Fleet"));
		assertTrue(selenium.isElementPresent("//a[@id='navigation:layout-navigationLiveFleet']/span"));
		assertTrue(selenium.isTextPresent("Admin"));
		assertTrue(selenium.isElementPresent("//a[@id='navigation:layout-navigationAdmin']/span"));
		assertTrue(selenium.isTextPresent("Home"));
		assertEquals(selenium.getValue("navigation:layout-redirectSearch"), "");
		assertTrue(selenium.isElementPresent("navigation:layout-redirectSearch"));
		assertTrue(selenium.isTextPresent("Drivers Devices Idling Vehicles Waysmart"));
		assertTrue(selenium.isElementPresent("navigation:layout-navigationRedirectTo"));
		assertEquals(selenium.getValue("navigation:layout-navigation_search_button"), "");
		assertTrue(selenium.isElementPresent("navigation:layout-navigation_search_button"));
	}
	
	
	
	
}


