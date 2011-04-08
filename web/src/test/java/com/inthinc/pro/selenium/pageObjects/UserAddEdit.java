package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.automation.selenium.RallyTest;
/****************************************************************************************
 * @author : Lee Arrington
 * Purpose: Define methods and objects to enter and manipulate information on Admin - User Screen
 * Last Update:  11/24/10
 ****************************************************************************************/

public class UserAddEdit extends RallyTest {
	
	//Define Class Objects
	private final String add_user_page = "/tiwipro/app/admin/editPerson";
	//user information
	private final String emp_firstname = "edit-form:editPerson-first";
	private final String emp_middle_name = "edit-form:middle";
	private final String emp_lastname = "edit-form:editPerson-last";
	private final String emp_name_suffix = "edit-form:editPerson-suffix";
	private final String emp_DOB_inputDate = "edit-form:editPerson-dobInputDate";
	private final String emp_gender = "edit-form:editPerson-gender";
	//employee information
	private final String emp_id = "edit-form:editPerson-empid";
	private final String emp_reportsTo = "edit-form:editPerson-reportsTo";
	private final String emp_title = "edit-form:editPerson-title";
	private final String emp_userlocale = "edit-form:editPerson-user_locale";
	private final String emp_timezone = "edit-form:editPerson-timeZone";
	private final String emp_measurement = "edit-form:editPerson-user_person_measurementType";
	private final String emp_fuelefficiencytype = "edit-form:editPerson-user_person_fuelEfficiencyType";
	//driver information
	private final String driver_lic = "edit-form:editPerson-driver_license";
	private final String driver_state = "edit-form:editPerson-driver_state";
	private final String lic_class = "edit-form:editPerson-driver_licenseClass";
	private final String input_date = "edit-form:editPerson-driver_expirationInputDate";
	private final String certifications = "edit-form:editPerson-driver_certifications";
	private final String driver_status = "edit-form:editPerson-driver_status";
	//login information
	private final String username = "edit-form:editPerson-user_username";
	private final String password = "edit-form:editPerson-user_password";
	private final String password_confirm = "edit-form:editPerson-confirmPassword";
	private final String user_status = "edit-form:editPerson-user_status";
	private final String panel_id = "edit-form:editPerson-from";
	//RFID Information
	private final String barcode_id = "edit-form:editPerson-driver_barcode";
	//Notification Information
	private final String email_1 = "edit-form:editPerson-priEmail";
	private final String email_2 = "edit-form:editPerson-secEmail";
	private final String text_msg_1 = "edit-form:editPerson-priText";
	private final String text_msg_2 = "edit-form:editPerson-secText";
	private final String phone_1 = "edit-form:editPerson-priPhone";
	private final String phone_2 = "edit-form:editPerson-secPhone";
	private final String Notification_info = "edit-form:editPerson-info";
	private final String Notification_warning = "edit-form:editPerson-warn";
	private final String Notification_critical = "edit-form:editPerson-crit";
	// Screen Buttons
	private final String savebutton = "edit-form:editPersonSave1";
	private final String cancelbutton = "edit-form:editPersonCancel1";
	private final String lowersavebutton = "edit-form:editPersonCancel2";
	private final String lowercancelbutton = "edit-form:editPersonSave2";
	private final String driverradiobutton = "edit-form:editPerson-isDriver";
	private final String loginradiobutton = "edit-form:editPerson-isUser";
	

	protected static CoreMethodLib selenium;

	public UserAddEdit(){
		selenium = GlobalSelenium.getSelenium();
	}
	
//	public void AddUser(String datasheet){
//		//enter new user info
//		enter_user_info(datasheet);
//		enter_employee_info(datasheet);
//		enter_driver_information(datasheet);
//		enter_login_information(datasheet);
//		enter_notifications(datasheet);
//		enter_rfid_information(datasheet);
//		//save new user
//		ClickSave();
//	}

	public void confirmMessage(String msgtext, String error_name){
		selenium.isTextPresent(msgtext,error_name);
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
	
	public void chk_screen_buttons(String error_name){
		selenium.isTextPresent("Save",error_name);
		selenium.isTextPresent("Cancel",error_name);
		selenium.isTextPresent("Cancel",error_name);
		selenium.isTextPresent("Save",error_name);
	}
	

	public void chk_userInfo_labels(String error_name){
		//Title
		selenium.isTextPresent("Admin: Add User", "Title Admin"+ error_name);
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

	public void SetDriverRadioButton (int col, String error_name){
		Boolean ck = selenium.isnotChecked(driverradiobutton, error_name);
		if (ck) {
			selenium.click(driverradiobutton , error_name);
		}
	}

	public void SetLoginRadioButton (int col, String error_name){
		Boolean ck = selenium.isnotChecked(loginradiobutton , error_name);
		if (ck) {
			selenium.click(loginradiobutton , error_name);
		}
	}

	public void ClicklSaveButton(){
		selenium.click(lowersavebutton, "Select Save Button");
		selenium.waitForPageToLoad("30000");
	}

	public void ClicklCancelButton(){
		selenium.click(lowercancelbutton,"Select Cancel Button");
		selenium.waitForPageToLoad("30000");
	}

	public void ClickSave(){
		selenium.click(savebutton, "Select Save Button");
		selenium.waitForPageToLoad("30000");
	}

	public void ClickCancel(){
		selenium.click(cancelbutton,"Select Cancel Button");
		selenium.waitForPageToLoad("30000");
	}

//	public void enter_rfid_information(String datasheet){
//		selenium.type(barcode_id, get_data(datasheet,"BarCode"));
//	}
//
//	public void enter_notifications(String datasheet){
//		selenium.type(email_1, get_data(datasheet,"EMAIL1"));
//		selenium.type(email_2, get_data(datasheet,"EMAIL2"));
//		selenium.type(text_msg_1, get_data(datasheet,"TextMsg1"));
//		selenium.type(text_msg_2, get_data(datasheet,"TextMsg2"));
//		selenium.type(phone_1 , get_data(datasheet,"Phone1"));
//		selenium.type(phone_2, get_data(datasheet,"Phone2"));
//		selenium.select(Notification_info, "label=" + get_data(datasheet,"NInfo"));
//		selenium.select(Notification_warning, "label=" + get_data(datasheet,"NWarning"));
//		selenium.select(Notification_critical, "label=" + get_data(datasheet,"NCritical"));
//	}
//
//	public void enter_login_information(String datasheet){
//		selenium.type(username , get_data(datasheet,"UserName"));
//		selenium.type(password, get_data(datasheet,"Password"));
//		selenium.type(password_confirm, get_data(datasheet,"Password"));
//		selenium.selectDhxCombo(get_data(datasheet,"Group"), "Login Group");
//		selenium.addtoPanel(panel_id, "label=" + get_data(datasheet,"Panel"), "Login Role Panel");
//		selenium.select(user_status, "label=" + get_data(datasheet,"Status"));
//	}
//
//	public void enter_driver_information(String datasheet){
//		selenium.type(driver_lic,get_data(datasheet,"DrivLicNum"));
//		selenium.select(driver_state, "label=" + get_data(datasheet,"DrivLicState"));
//		selenium.select(lic_class, "label=" + get_data(datasheet,"LicClass"));
//		selenium.type(input_date, get_data(datasheet,"InputDate"));
//		selenium.type(certifications, "\"" + get_data(datasheet,"Certifications"));
//		selenium.selectDhxCombo(get_data(datasheet,"Team"), "Driver Information - Team");
//		selenium.select(driver_status, "label=" + get_data(datasheet,"Status"));
//	}
//
//	public void enter_user_info(String datasheet){
//		selenium.type(emp_firstname, get_data(datasheet,"FirstName"), "Employee First Name");
//		selenium.type(emp_middle_name, get_data(datasheet,"MiddleName"), "Employee Middle Name");
//		selenium.type(emp_lastname, get_data(datasheet,"LastName"), "Employee Last Name");
//		selenium.select(emp_name_suffix, "label=" + get_data(datasheet,"Suffix"), "Employee Name Suffix");
//		selenium.type(emp_DOB_inputDate, get_data(datasheet,"DOB"), "DOB");
//		selenium.select(emp_gender, "label=" + get_data(datasheet,"Gender"), "Gender");
//	}
//	
//	public void enter_employee_info(String datasheet){
//		selenium.type(emp_id , get_data(datasheet,"EmpID"));
//		selenium.type(emp_reportsTo, get_data(datasheet,"ReportsTo"));
//		selenium.type(emp_title, get_data(datasheet,"Title"));
//		selenium.select(emp_userlocale, "label=" + get_data(datasheet,"Locale"));
//		selenium.select(emp_timezone, "label=" + get_data(datasheet,"TimeZone"));
//		selenium.select(emp_measurement, "label=" + get_data(datasheet,"Measurement"));
//		selenium.select(emp_fuelefficiencytype, "label=" + get_data(datasheet,"FuelEffType"));
//	}

	public UserAddEdit(CoreMethodLib sel ){
		selenium = sel;
	}

	public ErrorCatcher get_errors(){
		return selenium.getErrors();
	}
}



