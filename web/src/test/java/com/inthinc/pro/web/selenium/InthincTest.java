/****************************************************************************************
 * Purpose: To standardize the setup and teardown for System Test Automation tests
 * <p>
 * Update:  11/18/Added comments and made changes to adhere to Java Coding Standards<br />
 * Update:  11/19/Changed name to InthincTest and removed previous functionality that<br />
 * 				is no longer being used.  Also fixed start_selenium() so if we can't<br />
 * 				start the selenium instance we will fail the test, move to <br />
 * 				stop_selenium(), and skip the Rally stuff.<br />
 * 
 * @author larringt , dtanner
 */

package com.inthinc.pro.web.selenium;

import java.util.GregorianCalendar;
import java.util.HashMap;
import org.junit.*;
import org.junit.runner.notification.StoppedByUserException;
import org.openqa.selenium.server.SeleniumServer;

public abstract class InthincTest
{
	
	private GregorianCalendar currentTime;
	private static HashMap<String, HashMap<String, String>> testCase, errors;
	private static DataReaderLib data_file;
	private static RallyAPILib rally;
	
	private String testCaseID;
	private String testVersion="No Version Found";
	private String testVerdict="Error";
	
	private Boolean skip = false;
	
	private final static String username = "dtanner@inthinc.com";
	private final static String password = "aOURh7PL5v";
	private final static String workspace = "Inthinc";
	
	private static CoreMethodLib selenium;
	private static SeleniumServer seleniumserver;
	
	@BeforeClass
	public static void start_server(){
		try{
				seleniumserver = new SeleniumServer();
		        seleniumserver.start();
				rally = new RallyAPILib(username, password);
			}catch (Exception e) {
				e.printStackTrace();
				throw new StoppedByUserException();
		}
        
	}//end setup
	
	@Before
	public void start_selenium(){
		selenium = GlobalSelenium.getSingleton().getSelenium();
		try{
			selenium.start();
			currentTime = (GregorianCalendar) GregorianCalendar.getInstance();
		}catch(Exception e){
			e.printStackTrace();
			skip = true;
			throw new StoppedByUserException();
		}
		
	}
	
	@After
	public void stop_selenium(){
		if (!skip){
			try{
				//get version
				testVersion = selenium.getText("footerForm:version", "Getting version from Portal");
			}catch(Exception e){
				e.printStackTrace();
			}
			//reset output vars for next test and record results for execute test
			selenium.stop();
			record_results();
		}
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
			data_file = new DataReaderLib();	
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
		}
	}//end record results
}
