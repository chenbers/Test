/****************************************************************************************
 * Purpose: Includes all methods and variables to process Notification Tab
 * @author larringt 
 * Last Update:  11/18/Added comments and made changes to adhere to Java Coding Standards
 */

package com.inthinc.pro.web.selenium.portal.Notifications;
import com.inthinc.pro.web.selenium.CoreMethodLib;
import com.inthinc.pro.web.selenium.GlobalSelenium;

public class Notifications
{
	//Object Map
	
	private final String notifications_page = "/tiwipro/app/notifications/";
	
	
	
	
	protected static CoreMethodLib selenium;
	String curbutton = "";
	

		public Notifications(){
				this(GlobalSelenium.getSingleton().getSelenium());
			}
		
		public Notifications(GlobalSelenium tvar ){
				this(tvar.getSelenium());
			}
		
		public Notifications( CoreMethodLib sel ){
				selenium = sel;
			}
		public void selectTimeFrame(String nSection,String time){
			//Parameters:  nSection:  Name of Notification menuItem i.e. Redflag, Safety etc..
			//Time:  Item to be selected from drop down box
			selenium.open(notifications_page);
			selenium.select(nSection + "_search:" + nSection + "_timeframe", "label=" + time);
		}
	
	public void selectCategory(){
			
		}
	
	public void selectAlert(){
			
		}
	
	public void enterInfo(String nSection,String eBox,String texttoenter){
			//This function will enter information in Group, Driver, and Vehicle Edit boxes
				selenium.click(nSection + "-form:" + nSection+ ":" + eBox + "fsp","Click_" + nSection);
				selenium.type(nSection + "-form:" + nSection + ":" + eBox + "fsp", texttoenter, "Type"+ texttoenter);	
		}
	
	public void click_refresh(String nSection){
				selenium.click(nSection + "_search:" + nSection + "_refresh", "Click Refresh Button");	
		}
	
	public void ckRedflagScr(){
			//Verify Red Flag Screen is displayed as expected
			selenium.open(notifications_page,"Open Notification");
			selenium.isTextPresent("Team","Team");
			selenium.isTextPresent("Time Frame", "Time Frame");
			selenium.isTextPresent("Custom red flags setup by your organization", "Red Flag Setup");
			selenium.isTextPresent("Level", "Level");
			selenium.isTextPresent("Alert");
			selenium.isTextPresent("Date/Time","Date/Time");
			selenium.isTextPresent("Group","Group");
			selenium.isTextPresent("Driver","Driver");
			selenium.isTextPresent("Vehicle","Vehicle");
			selenium.isTextPresent("Category","Category");
			selenium.isTextPresent("Detail","Detail");
		}
	
	//select sub-menu item from Notifications screen
	public void menuItem(String screen){
		//Since each button's id changes depending on what is currently select
		//a var is set to the curbutton is used to append to the name of the button selected.
		if (curbutton.contentEquals("")){
				curbutton = "redFlags";
			}		
		if (screen.contentEquals("Red Flags)")){
					//Select Red Flag Menu	
					selenium.open(notifications_page, "RedFlags");
					selenium.click("//a[@id='" + curbutton + "-redFlags']/span", "Click RedFlags");
					curbutton = "redFlags";
					selenium.waitForPageToLoad("30000");
					selenium.open(notifications_page + "redflags","Refresh Button on Section:RedFlags");
					selenium.isTextPresent("Red Flags","Red Flags");
			}else if (screen.contentEquals("Safety")){
						//select Safety Menu
						selenium.click("//a[@id='" + curbutton + "-safety']/span", "Click Safety");
						curbutton = "safety";
						selenium.waitForPageToLoad("30000");
						selenium.open(notifications_page + "safety", "Open Safety");
						selenium.isTextPresent("Safety","Safety");
				}else if (screen.contentEquals("Diagnostics")){
						//select Diagnostics
						selenium.click("//a[@id='" + curbutton + "-diagnostics']/span", "Diagnostics");
						curbutton = "diagnostics";
						selenium.waitForPageToLoad("30000");
						selenium.open(notifications_page + "diagnostics", "Diagnostics");
						selenium.isTextPresent("Diagnostics","Diagnostics");
				}else if (screen.contentEquals("Emergency")){
						//select Emergency
						selenium.click("//a[@id='" + curbutton + "-emergency']/span","Emergency");
						curbutton = "emergency";
						selenium.waitForPageToLoad("30000");
						selenium.open(notifications_page + "emergency", "Emergency");
						selenium.isTextPresent("Emergency","Emergency");
				}else if (screen.contentEquals("Crash History")){
				//select Crash History
					selenium.open(notifications_page, "Crash History");
					selenium.click("//a[@id='" + curbutton + "-crashHistory']/span", "Crash History");
					curbutton = "crashHistory";
					selenium.waitForPageToLoad("30000");
					selenium.open(notifications_page + "crashHistory","Crash History");
					selenium.isTextPresent("Crash History","Crash History");
	}}}



