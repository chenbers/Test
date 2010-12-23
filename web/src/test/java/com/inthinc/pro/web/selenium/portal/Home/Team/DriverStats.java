package com.inthinc.pro.web.selenium.portal.Home.Team;


import static org.junit.Assert.*;

import java.util.HashMap;
import com.inthinc.pro.web.selenium.CoreMethodLib;
import org.apache.commons.lang.StringEscapeUtils;
import com.inthinc.pro.web.selenium.SeleniumServerLib;
import com.inthinc.pro.web.selenium.Debug.ErrorCatcher;
import com.inthinc.pro.web.selenium.GlobalSelenium;
/****************************************************************************************
 * Purpose: 
 * @author 
 * Last Update:  
 ****************************************************************************************/

public class DriverStats extends SeleniumServerLib {
	
	//Define Class Objects
	private final String driverstatsform = "teamStatisticsForm:drivers:";
	private final String totaldriverstatsform = "teamStatisticsForm:drivers:driverTotals:0:";
	private final String teamnameid = "//ul[@id='grid_nav']/li[2]";
	private final String crashpermillid = "//ul[@id='grid_nav']/li[5]";
	private final String dayssincecrashid = "//ul[@id='grid_nav']/li[6]";
	private final String milessincecrashid = "//ul[@id='grid_nav']/li[7]";
	
	protected static CoreMethodLib selenium;
	private long total;

	public DriverStats(){
		this(GlobalSelenium.getSingleton().getSelenium());
		}
	
	public DriverStats(GlobalSelenium tvar ){
		this(tvar.getSelenium());
		}
	
	public DriverStats( CoreMethodLib sel ){
			selenium = sel;
		}
	
	public ErrorCatcher get_errors(){
			return selenium.getErrors();
		}
		
	public void chk_DriverStatistics(String error_name){
			selenium.isTextPresent("Driver Statistics", error_name);
			selenium.isTextPresent("Team", error_name);
			selenium.isTextPresent("Score", error_name);
			selenium.isTextPresent("Trips", error_name);
			selenium.isTextPresent("Stops", error_name);
			selenium.isTextPresent("Distance Driven", error_name);
			selenium.isTextPresent("Duration", error_name);
			selenium.isTextPresent("Idle Time", error_name);
			selenium.isTextPresent("Low Idle", error_name);
			selenium.isTextPresent("High Idle", error_name);
			selenium.isTextPresent("Idle %", error_name);
			selenium.isTextPresent("Fuel Eff.", error_name);
			selenium.isTextPresent("Crashes", error_name);
			selenium.isTextPresent("Safety", error_name);
			selenium.isTextPresent("Driver", error_name);
		}
	
	public void chkTeamName(String team, String error_name){
			selenium.getText(teamnameid,team, error_name);
		}
	
	public void chkTeamCrashes(String crashnum , String error_name){
			selenium.getText(crashpermillid,crashnum + " Crashes per million miles", error_name);
		}
	
	public void chkTeamCrashDays(String days, String error_name){
			selenium.getText(dayssincecrashid + " Days since last crash",days, error_name);
		}
	
	public void chkTeamCrashMiles(String miles, String error_name){
			selenium.getText(milessincecrashid +  " Miles since last crash", miles, error_name);
		}
		
	public String SearchList(String searchtext, int total, String error_name){
		//Initialize local variables 
		String found = "no";	
		String curtext = "";
		//loop though list incrementing by one
		for (int currow = 0;currow<total; currow++){
			//get text for current row
			curtext = selenium.getText(driverstatsform + currow + ":j_id215", error_name );
			//when item matches set return flag and break out of loop
			if (searchtext.contentEquals(curtext)){
				found = "yes";
				System.out.print(searchtext + "found in list " + error_name);
				break;
				}
			}
		//return result
		return found;
	}
	
	public String getListValue(String searchtext, int total, String item, String error_name){
		//Initialize local variables 	
		String curtext = "";
		//loop though list incrementing by one
		for (int currow = 0;currow<total; currow++){
			//get text for current row
			curtext = selenium.getText(driverstatsform + currow + ":j_id215", error_name );
			//when item matches set return flag and break out of loop
			if (searchtext.contentEquals(curtext)){
				//get selected item value for current row
				curtext = selenium.getText(driverstatsform + currow + ":newTeamStatsTab-" + item, error_name );
				System.out.print(searchtext + " found in list " + error_name);
				break;
				}
			}
		//return result
		return curtext;
	}
	
	public long getTotalValue(int colhead, int startrow, int maxrow, String error_name){
			//maxrow = row method wlll search up too.
			//maxcol = col method wlll search up too.
			String curtext;
			int cval = 0;
			for (int currow = startrow;currow<maxrow; currow++){
					//get table cell value for desired column for current row
					curtext = selenium.getTable(driverstatsform , currow , colhead, error_name);
					// convert string to integer and add to running total
					cval = Integer.parseInt(curtext.trim());
					total = total + cval;
					System.out.print("Current total" + total);
					break;
					}
			//return total value
			return total;
		}
	
	public int getListintValue(String searchtext, int total, String item, String error_name){
		//Initialize local variables 	
		String curtext = "";
		int curint = 0;
		//loop though list incrementing by one
		for (int currow = 0;currow<total; currow++){
			//get text for current row
			curtext = selenium.getText(driverstatsform + currow + ":j_id215", error_name );
			//when item matches set return flag and break out of loop
			if (searchtext.contentEquals(curtext)){
				//get selected item value for current row
				curtext = selenium.getText(driverstatsform + currow + ":newTeamStatsTab-" + item, error_name );
				//convert text value to integer
				 try
			        {
				          // the String to int conversion happens here
				          curint = Integer.parseInt(curtext.trim());
				        }
			     catch (NumberFormatException nfe)
				        {
				          System.out.println("NumberFormatException: " + nfe.getMessage());
				        }
				System.out.print(searchtext + " item found in list " + error_name);
				break;
				}
			}
		//return result
		return curint;
	}
	 
	public void validateTotal(String value ,String colhead, String error_name){
			//verifies the enterer column total matches the value 
			//valid values : DriveTime , MilesDriven, TripStops (Trips), j_id184(Stops), 
			//IdleTime, LoIdle, HiIdle, PercentageNonZero, Crashes
			String curvalue;
			if (colhead.contentEquals("idlePercentageNonZero")|| (colhead.contentEquals("summaryMpg")||
					(colhead.contentEquals("saftyGroupLink"))|| (colhead.contentEquals("Stops")))){
			//Special Case: Change var to handle
			if (colhead.contentEquals("Stops")){colhead = "j_id184";}
				curvalue = selenium.getText(totaldriverstatsform + colhead,error_name);
			}
				curvalue = selenium.getText(totaldriverstatsform + "newTeamStatsTab-totals" + colhead,error_name);
	        System.out.println("Value found: " + curvalue);
	        //compare values
	        selenium.AssertEquals(value, curvalue, error_name);
		}
	
	public void SelectDriver(String drivername, String error_name){
				selenium.click("link=" + drivername);
				selenium.waitForPageToLoad("30000");
		}
	
	public static void main( String[] args){
			try {
				DriverStats.setUp();
					} catch (Exception e) {
						e.printStackTrace();
					}
					DriverStats login;
			login = new DriverStats();
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