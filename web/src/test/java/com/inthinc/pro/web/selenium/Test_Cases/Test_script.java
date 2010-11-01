package Test_Cases;

import com.thoughtworks.selenium.*;

import org.testng.annotations.*;
import Portal.NAVIGATE;
import Portal.Masthead.*;
import Portal.Login.*;
import static org.testng.AssertJUnit.*;
import org.testng.*;
import Portal.Singleton;
import Portal.Notifications.*;



@SuppressWarnings("unused")
@Test 
public class Test_script extends SeleneseTestCase {
	//define local vars
	
	
	

	
	public static void main() throws Exception {
	//create instance of library objects
		Portal.Singleton tvar = Portal.Singleton.getSingleton() ; 
		Selenium selenium = tvar.getSelenium();
	
	
	Portal.NAVIGATE navto;
	navto = new NAVIGATE();
	
	Portal.Login.loginScreen ls;
	ls = new loginScreen();

	Portal.Notifications.Notifications not;
	not = new Notifications();
	
	navto.setUp("firefox","https://qa.tiwipro.com:8423/tiwipro/");
	navto.login("larrington", "tekpass");

	
	
	
	
	
	
	
	
	
	
	
	navto.logout();		//logout of tiwipro app
	navto.tearDown();	//perform tear down tasks
	
	}
}



