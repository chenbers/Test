package com.inthinc.pro.selenium.pageObjects;
import static org.junit.Assert.*;

import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.automation.selenium.InthincTest;
/****************************************************************************************
 * @author : Lee Arrington
 * Purpose: Define methods and objects to enter and manipulate information on Admin - User Screen
 * Last Update:  11/24/10
 ****************************************************************************************/

public class UserDetails extends InthincTest {
	
	//Define Class Objects
	private final String add_user_page = "/tiwipro/app/admin/editPerson";
	// Screen Buttons
	private final String userdetaileditbutton = "display-form:personEdit";
	private final String userdetaildeletebutton = "display-form:personDelete";
	private final String confirmdelcancelbutton = "person-confirmDeleteCancel";
	private final String confirmuserdelbutton = "confirmDeleteForm:person-deleteButtonDirect";
	//Links
	private final String backtouserlink = "display-form:personCancel";
	
	protected static CoreMethodLib selenium;

	public UserDetails(){
		selenium = GlobalSelenium.getSelenium();
	}
	
	public void chk_screen_links(String emaillink, String error_name){
		//verify links
		selenium.isTextPresent("< Back to Users", "Back to users link");
		selenium.isElementPresent(emaillink, error_name);
	}
	
	public void chk_screen_headings(){
		//verify headings
		selenium.isTextPresent("User Information","User Detail: Information heading");
		selenium.isTextPresent("Employee Information", "User Detail Emp Info heading");
		selenium.isTextPresent("Driver Information", "User Detail Driver Info heading");
		selenium.isTextPresent("Login Information", "User Detail Login Info heading");
		selenium.isTextPresent("RFID Information", "User Detail RFID Information");
		selenium.isTextPresent("Notifications", "User Detail Notifications");
	}
	
	public void chk_userInfo_labels(String error_name){
		//Title
		selenium.isTextPresent("Admin:", "Title Admin"+ error_name);
		selenium.isTextPresent("Details","Title Details" + error_name);
		//User Information
		selenium.isTextPresent("First Name:", "First name:" + error_name);
		selenium.isTextPresent("Middle Name:","Middle Name:" + error_name);
		selenium.isTextPresent("Last Name:","Last Name:" + error_name);
		selenium.isTextPresent("Suffix:","Suffix:" + error_name);
		selenium.isTextPresent("DOB:","DOB:" + error_name);
		selenium.isTextPresent("Gender:","Gender:" + error_name);
		//Employee Information
		selenium.isTextPresent("Employee ID:", "Employee ID:" + error_name);
		selenium.isTextPresent("Reports To:","Reports To:" + error_name);
		selenium.isTextPresent("Title:","Title:" + error_name);
		selenium.isTextPresent("Locale:","Locale:" + error_name);
		selenium.isTextPresent("Time Zone:","Time Zone:"+ error_name);
		selenium.isTextPresent("Measurement:","Measurement:"+ error_name);
		selenium.isTextPresent("Fuel Efficiency Ratio:","Fuel Efficiency Ratio:" + error_name);
		//Driver Information
		selenium.isTextPresent("Driver License #:","Driver License #:" + error_name );
		selenium.isTextPresent("Class:","Class:" + error_name );
		selenium.isTextPresent("State:","State:" + error_name );
		selenium.isTextPresent("Expiration:","Expiration:" + error_name );
		selenium.isTextPresent("Certifications:","Certifications:" + error_name );
		selenium.isTextPresent("DOT:","DOT:" + error_name );
		selenium.isTextPresent("Team:","Team:"+ error_name );
		selenium.isTextPresent("Status:","Status:" + error_name );
		//Login Information
		selenium.isTextPresent("User Name:", "User Name:" + error_name);
		selenium.isTextPresent("Group:", "Group" + error_name);
		selenium.isTextPresent("Roles:", "Roles" + error_name);
		selenium.isTextPresent("Status:", "Status" + error_name);
		//RFID Information
		selenium.isTextPresent("Bar Code:", "Bar Code:" + error_name);
		selenium.isTextPresent("ID 1:", "ID 1:" + error_name);
		selenium.isTextPresent("ID 2:", "ID 2:" + error_name);
		//Notifications
		selenium.isTextPresent("E-mail 1:", "Email1" + error_name);
		selenium.isTextPresent("E-mail 2:", "Email2" + error_name);
		selenium.isTextPresent("Text Message 1:", "Text Message 1:" + error_name);
		selenium.isTextPresent("Text Message 2:", "Text Message 2:" + error_name);
		selenium.isTextPresent("Phone 1:", "Phone 1:" + error_name);
		selenium.isTextPresent("Phone 2:", "Phone 2:" + error_name);
		selenium.isTextPresent("exact:Information:", "exact:Information:" + error_name);
		selenium.isTextPresent("exact:Warning:", "exact:Warning:"+ error_name);
		selenium.isTextPresent("exact:Critical:", "exact:Critical:" + error_name);
	}
	
	public void ClickBacktoUsersLlink(String error_name){
		selenium.click(backtouserlink,error_name);
		selenium.waitForPageToLoad("30000");
	}
		
	public void ClickUsrEditButton(String errorname){
		selenium.click(userdetaileditbutton,errorname);
		selenium.waitForPageToLoad("30000");
	}
	
	public void ClickUsrDeleteButton(String errorname){
		selenium.click(userdetaildeletebutton,errorname );
		selenium.waitForPageToLoad("30000");
	}
	
	public void ClickCnfrmDelButton(String errorname){
		selenium.click(confirmuserdelbutton);
		selenium.waitForPageToLoad("30000");
	}
	
	public void ClickCnfrmDelCancel(String errorname){
		selenium.click(confirmdelcancelbutton);
		selenium.waitForPageToLoad("30000");
	}
	
	public UserDetails( CoreMethodLib sel ){
		selenium = sel;
	}
	
	public ErrorCatcher get_errors(){
		return selenium.getErrors();
	}
		


	
}

