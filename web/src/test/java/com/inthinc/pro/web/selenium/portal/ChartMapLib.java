package com.inthinc.pro.web.selenium.portal;


import static org.junit.Assert.*;
import java.util.HashMap;
import org.apache.commons.lang.StringEscapeUtils;
import com.inthinc.pro.web.selenium.CoreMethodLib;
import com.inthinc.pro.web.selenium.SeleniumServerLib;
import com.inthinc.pro.web.selenium.GlobalSelenium;
import com.inthinc.pro.web.selenium.Debug.ErrorCatcher;
/****************************************************************************************
 * Purpose: 
 * @author 
 * Last Update:  
 ****************************************************************************************/

public class ChartMapLib extends SeleniumServerLib {
	
	//Define Class Objects
	private final String speedboxid = "//div[@id='speedScoreBox_body']/table/tbody/tr/td";
	private final String styleboxid = "//div[@id='styleScoreBox_body']/table/tbody/tr/td";
	private final String seatbeltboxid = "//div[@id='seatBeltScoreBox_body']/table/tbody/tr/td";
	private final String overallboxid = "//div[@id='overallScoreBox_body']/table/tbody/tr/td[2]/table/tbody/tr/td";
	private final String crashpermileid = "//table[@id='crashSummaryTable']/tbody/tr[2]/td[1]";
	private final String totalcrashesid = "//table[@id='crashSummaryTable']/tbody/tr[2]/td[2]";
	
	private final String overallchartxpath = "[@id='chartdivOverallScore']";
	
	protected static CoreMethodLib selenium;

	public ChartMapLib(){
			this(GlobalSelenium.getSingleton().getSelenium());
		}
	
	public ChartMapLib(GlobalSelenium tvar ){
			this(tvar.getSelenium());
		}
	
	public ChartMapLib( CoreMethodLib sel ){
			selenium = sel;
		}
	
	public ErrorCatcher get_errors(){
			return selenium.getErrors();
		}
		
	public void pieGraph(){
		Number test;
		selenium.click("//*[@id='overallScoreChart_chartVarId']", "teste");
	
		String itemNameFromTable=selenium.getText("//*[@id='driverSpeedChart_chart_div_id']");
		System.out.println(itemNameFromTable);
	}
	
	private void test_self(){
		validateSpeedScoreBox("5.0","Test");
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
					ChartMapLib cml;
			cml = new ChartMapLib();
			cml.test_self();
			
			Object errors = "";
			errors = cml.get_errors().get_errors();
			System.out.println(errors.toString());	
			try{
						tearDown();
					}catch(Exception e){
						e.printStackTrace();
					}
			assertTrue(errors.toString()=="{}");
		}

	
}

