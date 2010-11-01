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
public class LoginScreen_HP extends SeleneseTestCase {
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

	Portal.Login.loginScreen login;
	login = new loginScreen();

	navto.setUp("firefox","https://qa.tiwipro.com:8423/tiwipro/");
	login.login_screen();

	login.ck_Homepage();
	login.ForgotPassword();
	
	
	
	navto.tearDown();	//perform tear down tasks
	
	}
}



