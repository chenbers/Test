package com.inthinc.pro.web.selenium.portal.Users;

import com.inthinc.pro.web.selenium.InthincTest;
import com.inthinc.pro.web.selenium.Core;
import com.inthinc.pro.web.selenium.Singleton;
import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
/****************************************************************************************
 * @author : Lee Arrington
 * Purpose: Define methods and objects to enter and manipulate information on Admin - User Screen
 * Last Update:  11/24/10
 ****************************************************************************************/

public class User extends InthincTest {
	
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
	private final String searchbutton = "admin-table-form:personTable-adminTableSearch";
	private final String userdeletebutton = "admin-table-form:personTable-adminTableDelete";
	private final String batcheditbutton = "admin-table-form:personTable-adminTableEdit";
	private final String confirmdelbutton = "confirmDeleteForm:personTable-deleteButton";
	private final String canceldelbutton = "personTable-confirmDeleteCancel";
	
	
	//General text box
	private final String search_text_box = "admin-table-form:personTable-filterTable";
	
	protected static Core selenium;

	public User(){
			this(Singleton.getSingleton().getSelenium());
		}
	
	public User(Singleton tvar ){
			this(tvar.getSelenium());
		}
	
	public void ClickUserDelete(String errorname){
			selenium.click(userdeletebutton, errorname);
			selenium.waitForPageToLoad("30000");
		}
	
	public void ClickBatchEditButton(String errorname){
			selenium.click(batcheditbutton, errorname);
			selenium.waitForPageToLoad("30000");
		}
	
	public void ClickConfirmDelButton(String errorname){
			selenium.click(confirmdelbutton, errorname);
			selenium.waitForPageToLoad("30000");
		}
	
	public void ClickCancelDelButton(String errorname){
			selenium.click(canceldelbutton, errorname);
			selenium.waitForPageToLoad("30000");
		}
	
	public void ClickUserDelete(){
			selenium.click(savebutton, "Select Save Button");
			selenium.waitForPageToLoad("30000");
		}
	
	public void search_admin(String text, String errorname){
			selenium.type(search_text_box, text,errorname);
			selenium.click(searchbutton, "Select Search Button");
			selenium.waitForPageToLoad("30000");
		}
	
	public void ClickSave(){
			selenium.open(add_user_page);
			selenium.click(savebutton, "Select Save Button");
			selenium.waitForPageToLoad("30000");
		}
	
	public void ClickCancel(){
			selenium.open(add_user_page);
			selenium.click(cancelbutton,"Select Cancel Button");
			selenium.waitForPageToLoad("30000");
		}
	
	public void enter_rfid_information(String datasheet){
			selenium.open(add_user_page );
			selenium.type(barcode_id, get_data(datasheet,"BarCode"));
		}
	
	public void enter_notifications(String datasheet){
			selenium.open(add_user_page );
			selenium.type(email_1, get_data(datasheet,"EMAIL1"));
			selenium.type(email_2, get_data(datasheet,"EMAIL2"));
			selenium.type(text_msg_1, get_data(datasheet,"TextMsg1"));
			selenium.type(text_msg_2, get_data(datasheet,"TextMsg2"));
			selenium.type(phone_1 , get_data(datasheet,"Phone1"));
			selenium.type(phone_2, get_data(datasheet,"Phone2"));
			selenium.select(Notification_info, "label=" + get_data(datasheet,"NInfo"));
			selenium.select(Notification_warning, "label=" + get_data(datasheet,"NWarning"));
			selenium.select(Notification_critical, "label=" + get_data(datasheet,"NCritical"));
		}
	public void enter_login_information(String datasheet){
		selenium.type(username , get_data(datasheet,"UserName"));
		selenium.type(password, get_data(datasheet,"Password"));
		selenium.type(password_confirm, get_data(datasheet,"Password"));
		selenium.selectDhxCombo(get_data(datasheet,"Group"), "Login Group");
		selenium.addtoPanel(panel_id, "label=" + get_data(datasheet,"Panel"), "Login Role Panel");
		selenium.select(user_status, "label=" + get_data(datasheet,"Status"));
	}
	
	public void enter_driver_information(String datasheet){
			selenium.open(add_user_page);
			selenium.type(driver_lic,get_data(datasheet,"DrivLicNum"));
			selenium.select(driver_state, "label=" + get_data(datasheet,"DrivLicState"));
			selenium.select(lic_class, "label=" + get_data(datasheet,"LicClass"));
			selenium.type(input_date, get_data(datasheet,"InputDate"));
			selenium.type(certifications, "\"" + get_data(datasheet,"Certifications"));
			selenium.selectDhxCombo(get_data(datasheet,"Team"), "Driver Information - Team");
			selenium.select(driver_status, "label=" + get_data(datasheet,"Status"));
		}

	public void enter_user_info(String datasheet){
			selenium.open(add_user_page,"Add User Page");
			selenium.type(emp_firstname, get_data(datasheet,"FirstName"), "Employee First Name");
			selenium.type(emp_middle_name, get_data(datasheet,"MiddleName"), "Employee Middle Name");
			selenium.type(emp_lastname, get_data(datasheet,"LastName"), "Employee Last Name");
			selenium.select(emp_name_suffix, "label=" + get_data(datasheet,"Suffix"), "Employee Name Suffix");
			selenium.type(emp_DOB_inputDate, get_data(datasheet,"DOB"), "DOB");
			selenium.select(emp_gender, "label=" + get_data(datasheet,"Gender"), "Gender");
		}
		
	public void enter_employee_info(String datasheet){
			selenium.open(add_user_page);
			selenium.type(emp_id , get_data(datasheet,"EmpID"));
			selenium.type(emp_reportsTo, get_data(datasheet,"ReportsTo"));
			selenium.type(emp_title, get_data(datasheet,"Title"));
			selenium.select(emp_userlocale, "label=" + get_data(datasheet,"Locale"));
			selenium.select(emp_timezone, "label=" + get_data(datasheet,"TimeZone"));
			selenium.select(emp_measurement, "label=" + get_data(datasheet,"Measurement"));
			selenium.select(emp_fuelefficiencytype, "label=" + get_data(datasheet,"FuelEffType"));
		}
	
	public User( Core sel ){
			selenium = sel;
		}
	
	public Error_Catcher get_errors(){
			return selenium.getErrors();
		}
		


	
}

