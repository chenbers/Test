package com.inthinc.pro.selenium.pageObjects;
//package com.inthinc.pro.web.selenium.portal;
//
//
//import static org.junit.Assert.*;
//import java.util.HashMap;
//import org.apache.commons.lang.StringEscapeUtils;
//
//import com.inthinc.pro.automation.selenium.CoreMethodLib;
//import com.inthinc.pro.automation.selenium.ErrorCatcher;
//import com.inthinc.pro.automation.selenium.GlobalSelenium;
///****************************************************************************************
// * Purpose: 
// * @author 
// * Last Update:  
// ****************************************************************************************/
//
//public class ChartMapLib{
//	
//	//Define Class Objects
//	private final String speedboxid = "//div[@id='speedScoreBox_body']/table/tbody/tr/td";
//	private final String styleboxid = "//div[@id='styleScoreBox_body']/table/tbody/tr/td";
//	private final String seatbeltboxid = "//div[@id='seatBeltScoreBox_body']/table/tbody/tr/td";
//	private final String overallboxid = "//div[@id='overallScoreBox_body']/table/tbody/tr/td[2]/table/tbody/tr/td";
//	private final String crashpermileid = "//table[@id='crashSummaryTable']/tbody/tr[2]/td[1]";
//	private final String totalcrashesid = "//table[@id='crashSummaryTable']/tbody/tr[2]/td[2]";
//	public final String trendtblid = "trendTable:executive";
//	public final String ffpageid = "//td[@onclick=\"Event.fire(this, 'rich:datascroller:onscroll', {'page': 'fastforward'});\"]";
//	
//	//overview screen xpath objects
//	private final String xchartid = "//*[@id='OverallChartId']";
//	private final String xspeedgraphid = "//*[@id='speedPercentChartdiv']";
//	private final String xtrendgraphid = "//*[@id='chartdivTrend']";
//	private final String xidilegraphid = "//*[@id='idlePercentChartdiv']";
//	private final String xoveralpiegraphid = "[@id='chartdivOverallScore']";
//	private final String xmpggraphid = "//*[@id='mpgChartDiv']";
//	private final String xfleetmapid = "//*[@id='map-canvas']";
//	
//	
//	
//	protected static CoreMethodLib selenium;
//
//	public ChartMapLib(){
//		selenium = GlobalSelenium.getSelenium();
//	}
//	
//	public ErrorCatcher get_errors(){
//		return selenium.getErrors();
//	}
//		
//	public void chkOverviewScreen(){
//		
//		//verify graphs are displayed properly
//		selenium.isElementPresent(xoveralpiegraphid, "Overal Pie Graph");
//		selenium.isVisible(xoveralpiegraphid, "Overal Pie Graph is visible");
//		selenium.isElementPresent(xspeedgraphid , "Speeding %");
//		selenium.isVisible(xspeedgraphid , "Speeding % is visible");
//		selenium.isElementPresent(xtrendgraphid, "Trend Graph");
//		selenium.isVisible(xtrendgraphid, "Trend Graph is visible");
//		selenium.isElementPresent(xidilegraphid, "Idling Percentage");
//		selenium.isVisible(xidilegraphid, "Idling Percentageis visible");
//		selenium.isElementPresent(xmpggraphid, "Fuel Efficiency");
//		selenium.isVisible(xmpggraphid, "Fuel Efficiency is visible");
//		selenium.isElementPresent(xfleetmapid, "Live Fleet Map");
//		selenium.isVisible(xfleetmapid, "Live Fleet Map is visible");
//	}
//	
//	public void chkTopAvgTableHeading(String error_name){
//		selenium.isTextPresent("Division/Team", error_name);
//		selenium.isTextPresent("Score", error_name);
//		selenium.isTextPresent("Crash/Mil", error_name);
//	}
//	
//	public String verifyTopAvgListItem(String divteam,String score, int pages, String crashmil, String error_name){
//		//Initialize local variables 	
//		String curtext = "";
//		String curscore= "";
//		String curcrashmil= "";
//		String result = "";
//		int count = 5;
//		int curpage = 1;
//		int currow;
//		//loop though list incrementing by one	
//		for ( currow = 0;currow<count; currow++){
//			//if more pages
//			if (currow<count){
//				//reset counter and search next page
//				count = 0;
//				curpage++;
//				selenium.click(ffpageid);}
//			//get text for current row
//			curtext = selenium.verifyText(trendtblid + "." + currow + ".1", error_name );
//			//when item matches set return flag and break out of loop
//			if (divteam.contentEquals(curtext)){
//				//get selected item value for current row
//				selenium.getText("//td[@id='" + trendtblid + ":" + (currow-1) + ":j_id307']/table.0.0");
//				selenium.verifyText(trendtblid  + "." + currow + ".3", error_name );
//				System.out.print(divteam + " item found in list " + error_name);
//				//verify if a match and return result
//				if (score.contentEquals(curscore) && (crashmil.contentEquals(curcrashmil))){
//					result = "passed";
//				}else if (curpage>pages) {
//					}else
//					result = "failed"; }
//			}//main for loop
//		return result;
//	}//main function
//	
//	
//	private void test_self(){
//		validateSpeedScoreBox("5.0","Test");
//	}
//	
//	public void validateSpeedScoreBox(String escore, String error_name){
//		selenium.verifyText(speedboxid, escore, error_name);
//	}
//	
//	public void validateStyleScoreBox(String escore, String error_name){
//		selenium.verifyText(styleboxid, escore, error_name);
//	}
//	
//	public void validateSeatBeltScoreBox(String escore, String error_name){
//		selenium.verifyText(seatbeltboxid, escore, error_name);
//	}
//	
//	public void validateOverallScoreBox(String escore, String error_name){
//		selenium.verifyText(overallboxid, escore, error_name);
//	}
//	
//	public void validateCrashPerMil(String emiles, String error_name){
//		selenium.verifyText(crashpermileid, emiles, error_name);
//	}
//	
//	public void validateTotalCrashes(String ecrash, String error_name){
//		selenium.verifyText(totalcrashesid, ecrash, error_name);
//	}
//	
//	
//	public static void main( String[] args){
//		try {
//			ChartMapLib.setUp();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		ChartMapLib cml;
//		cml = new ChartMapLib();
//		cml.test_self();
//		
//		Object errors = "";
//		errors = cml.get_errors().get_errors();
//		System.out.println(errors.toString());	
//		try{
//			tearDown();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		assertTrue(errors.toString()=="{}");
//	}
//}

