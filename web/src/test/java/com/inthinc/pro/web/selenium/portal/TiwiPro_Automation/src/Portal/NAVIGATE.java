//Maintained as .JAVA

package Portal;

import org.testng.annotations.*;
import org.openqa.selenium.server.SeleniumServer;
import com.thoughtworks.selenium.*;
import static org.testng.AssertJUnit.*;
import Portal.Singleton;

@SuppressWarnings("unused")
public class NAVIGATE
{
	//define local vars
	Portal.Singleton tvar = Portal.Singleton.getSingleton() ; 
	Selenium selenium = tvar.getSelenium();
	
	@BeforeClass
	public void setUp(String browser,String url) throws Exception {
		SeleniumServer seleniumserver=new SeleniumServer();
		//Set up tasks for all Tiwipro navigation functions
	    seleniumserver.boot();
        seleniumserver.start();
        selenium.start();
	}//end setup
	
	
	
	
	
	
		public void fforward(int times){
			selenium.open("/tiwipro/app/admin/vehicles");
			selenium.click("//a[@id='navigation:layout-navigationAdmin']/span");
			int i = 0;
			while (times>i){
				selenium.open("/tiwipro/app/admin/vehicles");
				selenium.click("//td[@onclick=\"Event.fire(this, 'rich:datascroller:onscroll', {'page': 'fastforward'});\"]");
			i = i + 1;
			};
		};
		
		public void frewind(int times){
			selenium.open("/tiwipro/app/admin/vehicles");
			selenium.click("//a[@id='navigation:layout-navigationAdmin']/span");
			int i = 0;
			while (times>i){
			selenium.click("//td[@onclick=\"Event.fire(this, 'rich:datascroller:onscroll', {'page': 'fastrewind'});\"]");
			i = i + 1;
			};	
		};
		
		public void lastpage(){
			selenium.open("/tiwipro/app/admin/vehicles");
			selenium.click("//a[@id='navigation:layout-navigationAdmin']/span");
			selenium.click("//td[@onclick=\"Event.fire(this, 'rich:datascroller:onscroll', {'page': 'last'});\"]");
			//selenium.waitForPageToLoad("10000");
		};
		
		public void firstpage(){
			selenium.open("/tiwipro/app/admin/vehicles");
			selenium.click("//a[@id='navigation:layout-navigationAdmin']/span");
			selenium.click("//td[@onclick=\"Event.fire(this, 'rich:datascroller:onscroll', {'page': 'first'});\"]");
			//selenium.waitForPageToLoad("10000");
		};
		
			
	public void login(String user,String pass) {
		//Login to Tiwipro Application
		selenium.open("login");  
		selenium.waitForPageToLoad("10000");
		selenium.type("j_username", user);
		selenium.type("j_password", pass);
		assertTrue(selenium.isElementPresent("loginLogin"));
		selenium.click("loginLogin");
		selenium.waitForPageToLoad("40000");
	}//end login
	
	

	
	public void logout(){
		//logout of Tiwipro Application
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");	
		assertEquals(selenium.getTitle(), "tiwiPRO");
		assertTrue(selenium.isTextPresent("Log In"));
	}//end logout
	
	public void homescreen(){
		//navigate to home screen by selecting 
		//Inthinc logo in upper left corner
		selenium.open("/tiwipro/app/dashboard/");
		selenium.click("//a[@id='headerForm:headerInitDashboard']/img");
		selenium.waitForPageToLoad("35000");
		assertTrue(selenium.isTextPresent("Overview"));
	}
	
	
	 @AfterClass
		//@AfterClass //Close and destroy all objects and files created
		public void tearDown(){
		 selenium.stop();
		}//tear down

}

/* This is an example of a functional area class and its supporting keyword subclasses
 * As new functional areas and keywords are created they should follow this template.
 * 
 * Every functional area will be in the FUnctional_Area package
 * Every functional area will have the same variable name for KeywordCount
 * Every functional area will have the same function names and argument names for:
 * - knums
 * - keywordSelect
 * - getKeywordCount
 * 
 * As keywords are initialized internally to the class they will simply be referred to in sequence
 * number, i.e. key1, key2, key3
 * 
 * Every keyword class will be named for the Keyword it represents
 * Every keyword class will have the same three functions, each of which is updated
 * as necessary for that class:
 *  - name
 *  - description
 *  - runKey
 *  
 * 
 */