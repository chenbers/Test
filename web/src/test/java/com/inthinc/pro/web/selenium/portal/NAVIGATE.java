//Maintained as .JAVA

package com.inthinc.pro.web.selenium.portal;


import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.notification.StoppedByUserException;

import org.openqa.selenium.server.SeleniumServer;


import com.inthinc.pro.web.selenium.Core;
import com.inthinc.pro.web.selenium.Data_Reader;
import com.inthinc.pro.web.selenium.Rally_API;

public class NAVIGATE
{
	
	private static HashMap<String, HashMap<String, String>> testCase;
	private static Data_Reader data_file;
	private static Rally_API rally;
	
	private String testCaseID;
	private String testVersion="No Version Found";
	private String testVerdict="Error";
	
	private final static String username = "dtanner@inthinc.com";
	private final static String password = "aOURh7PL5v";
	private final static String workspace = "Inthinc";
	
	private static Core selenium;
	static SeleniumServer seleniumserver;
	
	
	
	@BeforeClass
	public static void start_server(){
		System.out.println("BeforeClass executed");
		try{
			seleniumserver = new SeleniumServer();
	        seleniumserver.start();
			rally = new Rally_API(username, password);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new StoppedByUserException();
		}
        
	}//end setup
	
	
	@Before
	public void start_selenium(){
		System.out.println("Before executed");
		selenium = Singleton.getSingleton().getSelenium();
		selenium.start();
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
	

	@After
	public void stop_selenium(){
		System.out.println("After executed");
		selenium.stop();
		record_results();
		data_file=null;
		testCase=null;
	}
	
	
	@AfterClass
	public static void stop_server(){
		System.out.println("AfterClass executed");
		seleniumserver.stop();
		
	}//tear down
	
	
	public String set_test_case(String file_name, String test_case){
		String success = "Success";
		testCaseID=test_case;
		file_name = "src/test/resources/Data/" + file_name;
		
		if (data_file==null){
			data_file = new Data_Reader();	
		}
		
		try{
			testCase = data_file.get_testcase_data(file_name, testCaseID);
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
	
	public void record_results(){
		String errors = selenium.getErrors().get_errors().toString();
		if (errors.compareTo("")==0)testVerdict = "Pass";
		else if (errors.compareTo("")!=0)testVerdict = "Fail";
		try{
			rally.createJSON(workspace, testCaseID, testVersion, (GregorianCalendar) GregorianCalendar.getInstance(), errors, testVerdict);
		}catch(Exception e){
			e.printStackTrace();
			try {
				//TODO Add a data writer in case the rally call fails
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
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