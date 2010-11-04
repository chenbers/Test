//Maintained as .JAVA

package com.inthinc.pro.web.selenium.portal;


import org.junit.BeforeClass;
import org.openqa.selenium.server.SeleniumServer;


import com.thoughtworks.selenium.*;

public class NAVIGATE
{
	
	Selenium selenium;
	SeleniumServer seleniumserver;
	
	
	@BeforeClass
	public void setUp() throws Exception {
		seleniumserver=new SeleniumServer();
		//Set up tasks for all Tiwipro navigation functions
	    seleniumserver.boot();
        seleniumserver.start();
	}//end setup
	
	
	
	public void set_selenium(Selenium sel ){
		selenium = sel;		
	}
	
	
	public void fforward(int times){
		selenium.open("/tiwipro/app/admin/vehicles");
		selenium.click("//a[@id='navigation:layout-navigationAdmin']/span");
		int i = 0;
		while (times>i){
			selenium.open("/tiwipro/app/admin/vehicles");
			selenium.click("//td[@onclick=\"Event.fire(this, 'rich:datascroller:onscroll', {'page': 'fastforward'});\"]");
		i = i + 1;
		}
	}
	
	public void frewind(int times){
		selenium.open("/tiwipro/app/admin/vehicles");
		selenium.click("//a[@id='navigation:layout-navigationAdmin']/span");
		int i = 0;
		while (times>i){
		selenium.click("//td[@onclick=\"Event.fire(this, 'rich:datascroller:onscroll', {'page': 'fastrewind'});\"]");
		i = i + 1;
		}	
	}
	
	public void lastpage(){
		selenium.open("/tiwipro/app/admin/vehicles");
		selenium.click("//a[@id='navigation:layout-navigationAdmin']/span");
		selenium.click("//td[@onclick=\"Event.fire(this, 'rich:datascroller:onscroll', {'page': 'last'});\"]");
		//selenium.waitForPageToLoad("10000");
	}
	
	public void firstpage(){
		selenium.open("/tiwipro/app/admin/vehicles");
		selenium.click("//a[@id='navigation:layout-navigationAdmin']/span");
		selenium.click("//td[@onclick=\"Event.fire(this, 'rich:datascroller:onscroll', {'page': 'first'});\"]");
		//selenium.waitForPageToLoad("10000");
	}
		
			
	public void login(String user,String pass) {
		//Login to Tiwipro Application
		selenium.open("login");  
		selenium.waitForPageToLoad("10000");
		selenium.type("j_username", user);
		selenium.type("j_password", pass);
//		assertTrue(selenium.isElementPresent("loginLogin"));
		selenium.click("loginLogin");
		selenium.waitForPageToLoad("40000");
	}//end login
	
	

	
	public void logout(){
		//logout of Tiwipro Application
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");	
//		assertEquals(selenium.getTitle(), "tiwiPRO");
//		assertTrue(selenium.isTextPresent("Log In"));
	}//end logout
	
	public void homescreen(){
		//navigate to home screen by selecting 
		//Inthinc logo in upper left corner
		selenium.open("/tiwipro/app/dashboard/");
		selenium.click("//a[@id='headerForm:headerInitDashboard']/img");
		selenium.waitForPageToLoad("35000");
//		assertTrue(selenium.isTextPresent("Overview"));
	}
	
	
		//@AfterClass //Close and destroy all objects and files created
		public void tearDown(){
		 seleniumserver.stop();
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