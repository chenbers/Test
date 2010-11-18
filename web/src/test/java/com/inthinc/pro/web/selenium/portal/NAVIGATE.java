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
	
	private GregorianCalendar currentTime;
	private static HashMap<String, HashMap<String, String>> testCase, errors;
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
		selenium = Singleton.getSingleton().getSelenium();
		selenium.start();
		currentTime = (GregorianCalendar) GregorianCalendar.getInstance();
	}
	
	public void set_selenium(Core sel ){
		selenium = sel;		
	}//end before
	
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
			
	public void logout(){
		//logout of Tiwipro Application
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
		stop_selenium();
		stop_server();
	}//end logout
	

	@After
	public void stop_selenium(){
		try{
			//get version
			testVersion = selenium.getText("footerForm:version", "Getting version from Portal");
		}catch(Exception e){
			e.printStackTrace();
		}
		//rest output vars for next test and record results for execute test
		selenium.stop();
		record_results();
		data_file=null;
		testCase=null;
		currentTime=null;
		
	}
	
	@AfterClass
	public static void stop_server(){
		seleniumserver.stop();
		
	}//tear down
	
	public String set_test_case(String file_name, String test_case){
		//takes data file and test case Id as entries
		//then builds HASHMap for the test case passed into method
		String success = "Success";
		testCaseID=test_case;
		//append system path to file name
		file_name = "src/test/resources/Data/" + file_name;
		
		if (data_file==null){
			//instantiate data reader if needed
			data_file = new Data_Reader();	
		}
		
		try{
			//build test case HASHmap 
			testCase = data_file.get_testcase_data(file_name, testCaseID);
		}catch(NullPointerException e){
			System.out.println("That Test Case ID was not found in the file.");
			return "That Test Case ID was not found in the file.";
		}
		return success;
	}//end set test case
	
	public void set_test_case(String test_case){
		testCaseID=test_case;
	}
	
	public String get_data( String sheet, String header){
		//used to pull test data from HASHMap Variable Type
		String value = "";		
		try{
			value = testCase.get(sheet).get(header); 
		}catch(NullPointerException e){
			System.out.println("That Entry was not found on the specified sheet");
			return "That Entry was not found on the specified sheet";
		}
		
		return value; 
	}//end set test case
	
	public void record_results(){
		errors = selenium.getErrors().get_errors();
		//check error var for entries
		if (errors.isEmpty())testVerdict = "Pass"; //no errors = pass
			else if (!errors.isEmpty())testVerdict = "Fail"; //errors = fail
		try{
			//send test result to Rally
			rally.createJSON(workspace, testCaseID, testVersion, currentTime, errors, testVerdict);
		}catch(Exception e1){
			e1.printStackTrace();
			try {
				// TODO Add a data writer in case the rally call fails
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}//end record results
}

