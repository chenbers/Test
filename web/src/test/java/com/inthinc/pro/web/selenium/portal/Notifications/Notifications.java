//Maintained as .JAVA

package com.inthinc.pro.web.selenium.portal.Notifications;

import org.testng.annotations.*;
import org.openqa.selenium.server.SeleniumServer;

import com.inthinc.pro.web.selenium.portal.Singleton;
import com.thoughtworks.selenium.*;
import static org.testng.AssertJUnit.*;



@SuppressWarnings("unused")
public class Notifications
{
	/* THIS CLASS IS A FUNCTIONAL AREA OF THE AUT
	 * THIS CLASS SHOULD REMAIN IN .JAVA FORM
	 * THIS CLASS SHOULD BE MAINTAINED
	 */
	
	
	//define local vars
	Singleton tvar = Singleton.getSingleton() ; 
	Selenium selenium = tvar.getSelenium();
	String curbutton = "";
	
	
	
		//Maintain the keyword Count
		private int KeywordCount;
		
		public int getKeywordCount(){
			return KeywordCount;
		}
		
		
		public void selectTeam(){
			selenium.click("//table[@id='grid_nav_group_box']/tbody/tr/td[2]/span/div/img");
			selenium.click("//div[16]/div[15]");
			selenium.click("//table[@id='grid_nav_group_box']/tbody/tr/td[2]/span/div/img");
			selenium.click("//div[16]/div[2]");
			selenium.click("//table[@id='grid_nav_group_box']/tbody/tr/td[2]/span/div/img");
			selenium.click("//div[16]/div[1]");
			
			
		}
		public void selectTimeFrame(String nSection,String time){
			//Paramaters:  nSection:  Name of Notification menuItem i.e. Redflag, Safety etc..
			//Time:  Item to be selected from drop down box
			selenium.open("/tiwipro/app/notifications/");
			selenium.select(nSection + "_search:" + nSection + "_timeframe", "label=" + time);
		}
		public void selectCategory(){
			
		}
		public void selectAlert(){
			
		}
		public void enterInfo(String nSection,String eBox,String texttoenter){
			//This function will enter information in Group, Driver, and Vehicle Edit boxes
			selenium.click(nSection + "-form:" + nSection+ ":" + eBox + "fsp");
			selenium.type(nSection + "-form:" + nSection + ":" + eBox + "fsp", texttoenter);
		}
		
		public void click_refresh(String nSection){
			selenium.click(nSection + "_search:" + nSection + "_refresh");
			
		}
		
		
		public void ckRedflagScr(){
			selenium.open("/tiwipro/app/notifications/");
			assertTrue(selenium.isTextPresent("Team"));
			assertTrue(selenium.isTextPresent("Time Frame"));
			assertTrue(selenium.isTextPresent("Custom red flags setup by your organization"));
			assertTrue(selenium.isTextPresent("Level"));
			assertTrue(selenium.isTextPresent("Alert"));
			assertTrue(selenium.isTextPresent("Date/Time"));
			assertTrue(selenium.isTextPresent("Group"));
			assertTrue(selenium.isTextPresent("Driver"));
			assertTrue(selenium.isTextPresent("Vehicle"));
			assertTrue(selenium.isTextPresent("Category"));
			assertTrue(selenium.isTextPresent("Detail"));
		}
		
		
		
		public void menuItem(String screen){
			
			if (curbutton.contentEquals("")){
				curbutton = "redFlags";
			}
						
			if (screen.contentEquals("Red Flags)")){
				selenium.open("/tiwipro/app/notifications/");
				selenium.click("//a[@id='" + curbutton + "-redFlags']/span");
				curbutton = "redFlags";
				selenium.waitForPageToLoad("30000");
				selenium.open("/tiwipro/app/notifications/redflags");
				assertTrue(selenium.isTextPresent("Red Flags"));
			}else if (screen.contentEquals("Safety")){
				//select Saftey
				selenium.click("//a[@id='" + curbutton + "-safety']/span");
				curbutton = "safety";
				selenium.waitForPageToLoad("30000");
				selenium.open("/tiwipro/app/notifications/safety");
				assertTrue(selenium.isTextPresent("Safety"));
			}else if (screen.contentEquals("Diagnostics")){
				//select Diagnostics
				selenium.click("//a[@id='" + curbutton + "-diagnostics']/span");
				curbutton = "diagnostics";
				selenium.waitForPageToLoad("30000");
				selenium.open("/tiwipro/app/notifications/diagnostics");
				assertTrue(selenium.isTextPresent("Diagnostics"));
			}else if (screen.contentEquals("Emergency")){
				//select Emergency
				selenium.click("//a[@id='" + curbutton + "-emergency']/span");
				curbutton = "emergency";
				selenium.waitForPageToLoad("30000");
				selenium.open("/tiwipro/app/notifications/emergency");
				assertTrue(selenium.isTextPresent("Emergency"));
			}else if (screen.contentEquals("Crash History")){
				//select Crash History
				selenium.open("/tiwipro/app/notifications/");
				selenium.click("//a[@id='" + curbutton + "-crashHistory']/span");
				selenium.click("//a[@id='redFlags-crashHistory']/span");
				curbutton = "crashHistory";
				selenium.waitForPageToLoad("30000");
				selenium.open("/tiwipro/app/notifications/crashHistory");
				assertTrue(selenium.isTextPresent("Crash History"));
			}	
		}}

