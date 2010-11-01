package Test_Cases;

import com.thoughtworks.selenium.*;

import org.testng.annotations.*;
import Portal.NAVIGATE;
import Portal.Masthead.*;
import Portal.Login.*;
import Portal.Notifications.*;
import static org.testng.AssertJUnit.*;

import org.testng.*;
import Portal.Singleton;



@SuppressWarnings("unused")
@Test 
public class Notification_redflag_HP extends SeleneseTestCase {
	//define local vars
	
	
	

	
	/**
	 * @throws Exception
	 */
	public static void main() throws Exception {
	//create instance of library objects
		Portal.Singleton tvar = Portal.Singleton.getSingleton() ; 
		Selenium selenium = tvar.getSelenium();
	
	
	Portal.NAVIGATE navto;
	navto = new NAVIGATE();
	
	Portal.Masthead.Masthead mast;
	mast = new Masthead();
	
	Portal.Notifications.Notifications N;
	N = new Notifications();

	navto.setUp("firefox","https://qa.tiwipro.com:8423/tiwipro/");
	navto.login("larrington", "tekpass");

	
	
	
	mast.mainMenuItem("Notifications");
	
	N.menuItem("Red Flags");
	N.ckRedflagScr();
	
	N.selectTimeFrame("redFlags", "Today");
	
	N.enterInfo("redFlags", "vehicle", "Mustang");
	N.enterInfo("redFlags", "group", "Groupone");
	N.enterInfo("redFlags", "driver", "Lee");
	
	N.click_refresh("redFlags");
	
	navto.logout();
	navto.tearDown();	//perform tear down tasks
	
	}
}



