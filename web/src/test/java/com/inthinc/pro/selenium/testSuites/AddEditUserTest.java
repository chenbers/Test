package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.enums.AutomationLogins;
import com.inthinc.pro.automation.enums.LoginCapabilities;
import com.inthinc.pro.automation.utils.AutomationCalendar.TimeZones;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEntries;
import com.inthinc.pro.selenium.pageEnums.AdminUserDetailsEnum;
import com.inthinc.pro.selenium.pageObjects.PageAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

public class AddEditUserTest extends WebRallyTest {
	
	private PageMyAccount myAccount;
	private PageAddEditUser myAddEditUser;
	private PageAdminUserDetails myAdminUserDetails;
	private PageAdminUsers myAdminUsers;
	private RandomValues random;
	private String USERNAME = "tinaauto";
	private String PASSWORD = "password";
	 
	@Before
	public void setupPage() {
		random = new RandomValues();
		myAddEditUser = new PageAddEditUser();
		myAccount = new PageMyAccount();
		myAdminUsers = new PageAdminUsers();
		myAdminUserDetails = new PageAdminUserDetails();
		AutomationLogins login = AutomationLogins.getOne();
		USERNAME = login.getUserName();
		PASSWORD = login.getPassword();
	}
	
	@Test
	public void EmployeeIDUpperCase() {
		set_test_case("TC5626");
		
		//.0 Login
		myAccount.loginProcess(USERNAME, PASSWORD);
		myAccount._link().admin().click();
											
		//.1 Search for USERNAME in table and click the USERNAME link.
		
		myAdminUsers._link().adminUsers().click();
		myAdminUsers._link().editColumns().click();
	    myAdminUsers._popUp().editColumns()._checkBox().row(12).check();
	    myAdminUsers._popUp().editColumns()._button().save().click();
	    
	    
		String Name = myAdminUsers._text().tableEntry(AdminUsersEntries.FULL_NAME).row(1).getText();		
		myAdminUsers._textField().search().type(Name);
		myAdminUsers._button().search().click(); 
		myAdminUsers._link().edit().row(1).click();
		String original = myAddEditUser._textField().personFields(AdminUsersEntries.EMPLOYEE_ID).getText();
				
		String[] emp_id = {random.getCharString(10).toLowerCase()
						, random.getCharString(10).toUpperCase()
						, random.getCharString(5).toLowerCase()+ random.getCharString(5).toUpperCase()
						, original};
		//.2 Edit User's Employee ID
		for (String string: emp_id){
			myAddEditUser._textField().personFields(AdminUsersEntries.EMPLOYEE_ID).clear();
			myAddEditUser._textField().personFields(AdminUsersEntries.EMPLOYEE_ID).type(string);
			myAddEditUser._button().saveTop().click();
			
			//.2a Leave User Details page and return to verify changes saved correctly.
			myAccount._link().myAccount().click();
			myAccount._link().admin().click();
			myAdminUsers._link().adminUsers().click();
			myAdminUsers._textField().search().type(Name);
			myAdminUsers._button().search().click(); 
			myAdminUsers._text().tableEntry(AdminUsersEntries.EMPLOYEE_ID).row(1).validate(string.toUpperCase());
			myAdminUsers._link().tableEntryUserName().row(1).click();
			
			//.2b Validate employee id displays in Upper case.		
			myAdminUserDetails._text().values(AdminUserDetailsEnum.EMP_ID).validate(string.toUpperCase());
			myAdminUserDetails._button().edit().click();
		}
		myAddEditUser._button().cancelBottom().click();
	
	
	}
	
	@Test
    public void DuplicateEmployeeIDError() {
        set_test_case("TC5704");
        
        //.0 Login
        	myAccount.loginProcess(USERNAME, PASSWORD);
        	myAccount._link().admin ().click();
	
        //.1 Search for Employee ID in column and save the id to be used while creating a new user.
                         
        	myAdminUsers._link().adminUsers().click();
        	myAdminUsers._link().editColumns().click();
        	myAdminUsers._popUp().editColumns()._checkBox().row(12).check();
        	myAdminUsers._popUp().editColumns()._button().save().click();
        	myAdminUsers._link().sortByColumn(AdminUsersEntries.EMPLOYEE_ID).click();
        	myAdminUsers._link().sortByColumn(AdminUsersEntries.EMPLOYEE_ID).click();
        	String myString = myAdminUsers._text().tableEntry(AdminUsersEntries.EMPLOYEE_ID).row(1).getText();
        
        //.2 Create a new user with duplicate employee id.
               
        	myAdminUsers._link().adminAddUser().click();
        	myAddEditUser._textField().personFields(AdminUsersEntries.FIRST_NAME).type("testing");
        	myAddEditUser._textField().personFields(AdminUsersEntries.LAST_NAME).type("testing");
        	myAddEditUser._dropDown().driverTeam().select(2);
        
        	myAddEditUser._checkBox().userInformation().uncheck();
        	myAddEditUser._dropDown().timeZone().select(TimeZones.US_SAMOA);
        	myAddEditUser._textField().personFields(AdminUsersEntries.EMPLOYEE_ID).type(myString);
        	myAddEditUser._button().saveTop().click();
                
        //.3 Verify error messages display 

        	myAddEditUser._text().masterError().validate("1 error(s) occurred. Please verify all the data entered is correct.");
        	myAddEditUser._text().personError(AdminUsersEntries.EMPLOYEE_ID).validate("Employee ID is already in use" );
              
        //.4 delete or cancel new user
        boolean isDeleteBtnPresent = myAdminUserDetails._button().delete().isPresent();
		if (isDeleteBtnPresent) {
			myAdminUserDetails._button().delete().click();
		} else {
			myAddEditUser._button().cancelTop().click();
		}
	}
	
}
            

