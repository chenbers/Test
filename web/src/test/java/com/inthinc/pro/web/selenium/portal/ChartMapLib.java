package com.inthinc.pro.web.selenium.portal;


import static org.junit.Assert.*;
import java.util.HashMap;
import org.apache.commons.lang.StringEscapeUtils;
import com.inthinc.pro.web.selenium.Core;
import com.inthinc.pro.web.selenium.Selenium_Server;
import com.inthinc.pro.web.selenium.Singleton;
import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
/****************************************************************************************
 * Purpose: 
 * @author 
 * Last Update:  
 ****************************************************************************************/

public class ChartMapLib extends Selenium_Server {
	
	//Define Class Objects
	private final String speedboxid = "//div[@id='speedScoreBox_body']/table/tbody/tr/td";
	private final String styleboxid = "//div[@id='styleScoreBox_body']/table/tbody/tr/td";
	private final String seatbeltboxid = "//div[@id='seatBeltScoreBox_body']/table/tbody/tr/td";
	private final String overallboxid = "//div[@id='overallScoreBox_body']/table/tbody/tr/td[2]/table/tbody/tr/td";
	private final String crashpermileid = "//table[@id='crashSummaryTable']/tbody/tr[2]/td[1]";
	private final String totalcrashesid = "//table[@id='crashSummaryTable']/tbody/tr[2]/td[2]";
	
	protected static Core selenium;

	public ChartMapLib(){
			this(Singleton.getSingleton().getSelenium());
		}
	
	public ChartMapLib(Singleton tvar ){
			this(tvar.getSelenium());
		}
	
	public ChartMapLib( Core sel ){
			selenium = sel;
		}
	
	public Error_Catcher get_errors(){
			return selenium.getErrors();
		}
		
	
	public void validateSpeedScoreBox(String escore, String error_name){
			selenium.getText(speedboxid, escore, error_name);
		}
	
	public void validateStyleScoreBox(String escore, String error_name){
			selenium.getText(styleboxid, escore, error_name);
		}
	
	public void validateSeatBeltScoreBox(String escore, String error_name){
			selenium.getText(seatbeltboxid, escore, error_name);
		}
	
	public void validateOverallScoreBox(String escore, String error_name){
			selenium.getText(overallboxid, escore, error_name);
		}
	
	public void validateCrashPerMil(String emiles, String error_name){
			selenium.getText(crashpermileid, emiles, error_name);
		}
	
	public void validateTotalCrashes(String ecrash, String error_name){
			selenium.getText(totalcrashesid, ecrash, error_name);
		}
	
	
	public static void main( String[] args){
			try {
						ChartMapLib.setUp();
					} catch (Exception e) {
						e.printStackTrace();
					}
					ChartMapLib login;
			login = new ChartMapLib();
			//login.test_self();
			Object errors = "";
			errors = login.get_errors().get_errors();
			System.out.println(errors.toString());	
			try{
						tearDown();
					}catch(Exception e){
						e.printStackTrace();
					}
			assertTrue(errors.toString()=="{}");
		}

	
}

