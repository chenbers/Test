package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEntries;
import com.inthinc.pro.selenium.pageEnums.AdminUserDetailsEnum;
import com.inthinc.pro.selenium.pageEnums.AdminUsersEnum;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageAddEditUser;



public class AddEditUser extends WebRallyTest {
	
	private PageMyAccount myAccount;
	private PageAddEditUser myAddEditUser;
	private PageAdminUserDetails myAdminUserDetails;
	private PageAdminUsers myAdminUsers;
	private RandomValues random;
	private String USERNAME = "prime";
	private String PASSWORD = "password";
	 
	@Before
	public void setupPage() {
		random = new RandomValues();
		myAddEditUser = new PageAddEditUser();
		myAccount = new PageMyAccount();
		myAdminUsers = new PageAdminUsers();
		myAdminUserDetails = new PageAdminUserDetails();
	}
	
	@Test
	public void EmployeeIDUpperCase() {
		set_test_case("TC5626");
		
		//.0 Login
		myAccount.loginProcess(USERNAME, PASSWORD);
		myAccount._link().admin().click();
											
		//.1 Search for USERNAME in table and click the USERNAME link.
		
		myAdminUsers._link().adminUsers().click();
		myAdminUsers._textField().search().type("Tina");
		myAdminUsers._button().search().click(); 
		myAdminUsers.getLinkByText("Tina Automated").click();
		myAdminUserDetails._button().edit().click();
		String original = myAddEditUser._textField().personFields(AdminUsersEntries.EMP_ID_EDIT).getText();
				
		String[] emp_id = {random.getCharString(10).toLowerCase(), random.getCharString(10).toUpperCase(),random.getCharString(5).toLowerCase()+ random.getCharString(5).toUpperCase(), original};
		//.2 Edit User's Employee ID
		for (String string: emp_id){
			myAddEditUser._textField().personFields(AdminUsersEntries.EMP_ID_EDIT).clear();
			myAddEditUser._textField().personFields(AdminUsersEntries.EMP_ID_EDIT).type(string);
			myAddEditUser._button().saveTop().click();
			
			//.2a Leave User Details page and return to verify changes saved correctly.
			myAccount._link().myAccount().click();
			myAccount._link().admin().click();
			myAdminUsers._link().adminUsers().click();
			myAdminUsers._textField().search().type("Tina");
			myAdminUsers._button().search().click(); 
			myAdminUsers.getLinkByText("Tina Automated").click();
			
			//.2b Validate employee id displays in Upper case.		
			myAdminUserDetails._text().values(AdminUserDetailsEnum.EMP_ID).validate(string.toUpperCase());
			myAdminUserDetails._button().edit().click();
		}
		myAddEditUser._button().cancelBottom().click();
	
	
	}
	

	
}
