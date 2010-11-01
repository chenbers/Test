package Test_Cases;

import com.thoughtworks.selenium.*;

import org.testng.annotations.*;
import Portal.NAVIGATE;
import Portal.Masthead.*;
import static org.testng.AssertJUnit.*;
import org.testng.*;
import Portal.Singleton;
import Portal.Login.*;



@SuppressWarnings("unused")
@Test 
public class MastHead_HP extends SeleneseTestCase {
	//define local vars
	
	public static void main() throws Exception {
	//create instance of library objects
		Portal.Singleton tvar = Portal.Singleton.getSingleton() ; 
		Selenium selenium = tvar.getSelenium();
	
	Portal.NAVIGATE navto;
	navto = new NAVIGATE();
	Portal.Login.loginScreen ls;
	ls = new loginScreen();

	navto.setUp("firefox","https://qa.tiwipro.com:8423/tiwipro/");
	ls.login_screen();		// open browser to login screen
	
	ls.ck_Homepage();		//verify homepage 
	ls.ForgotPassword();	//verify Forgotpassword functionality
	
	navto.tearDown();		//perform tear down tasks
	
	}
}



