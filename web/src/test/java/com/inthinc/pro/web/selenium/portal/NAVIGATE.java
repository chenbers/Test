//Maintained as .JAVA

package com.inthinc.pro.web.selenium.portal;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.server.SeleniumServer;


import com.inthinc.pro.web.selenium.Core;
import com.inthinc.pro.web.selenium.Data_Reader;

public class NAVIGATE
{
	
	private static HashMap<String, HashMap<String, String>> testCase;
	private static Data_Reader data_file;
	
	Core selenium;
	static SeleniumServer seleniumserver;
	
	
	
	@BeforeClass
	public static void start_server() throws Exception {
		seleniumserver = new SeleniumServer();
        seleniumserver.start();
        
	}//end setup
	
	
	@Before
	public void start_selenium(){
		selenium = Singleton.getSingleton().getSelenium();        
	}
	
	
	public void set_selenium(Core sel ){
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
	
	
	@AfterClass //Close and destroy all objects and files created
	public static void stop_server(){
		 seleniumserver.stop();
		
	}//tear down
	
	@After
	public void stop_selenium(){
		selenium.stop();
	}
	
	public String set_test_case(String file_name, String test_case){
		String success = "Success";
		if (data_file==null){
			data_file = new Data_Reader();	
		}
		
		try{
			testCase = data_file.get_testcase_data(file_name, test_case);
		}catch(NullPointerException e){
			System.out.println("That Test Case ID was not found in the file.");
			return "That Test Case ID was not found in the file.";
		}
		return success;
	}
	
	public String get_data( String sheet, String header){
		String value = "";
				
		try{
			value = testCase.get(sheet).get(header); 
		}catch(NullPointerException e){
			System.out.println("That Entry was not found on the specified sheet");
			return "That Entry was not found on the specified sheet";
		}
		
		return value; 
	}
	
	
	
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