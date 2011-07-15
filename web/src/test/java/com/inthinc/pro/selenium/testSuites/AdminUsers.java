package com.inthinc.pro.selenium.testSuites;

import org.junit.Test;

import com.inthinc.pro.selenium.pageEnums.AdminUserDetailsEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEntries;
import com.inthinc.pro.selenium.pageObjects.PageAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;

public class AdminUsers extends WebRallyTest {

	private String username = "danniauto";
	private String password = "password";
	private PageAdminUsers users = new PageAdminUsers();
	private PageAddEditUser edituser = new PageAddEditUser();
	private PageAdminUserDetails details = new PageAdminUserDetails();
	
	
	@Test
	public void usersNameLink(){
		//set_test_case("TC764");
		
		//1- Login
		users.loginProcess(username, password);
		
		//2- Click on Admin
		users._link().admin().click();
		
		//3- Take note of the top user's name
		String fullname = users._link().tableEntry(AdminUsersEntries.FULL_NAME).getText(1);
		
		//4- Click on the top name
		users._link().tableEntry(AdminUsersEntries.FULL_NAME).click(1);
		
		//5- Verify the page is the correct page
		  String firstName = details._text().values(AdminUserDetailsEnum.FIRST_NAME).getText();
		  String middleName =details._text().values(AdminUserDetailsEnum.MIDDLE_NAME).getText();
		  String lastName = details._text().values(AdminUserDetailsEnum.LAST_NAME).getText();
		  String suffix = details._text().values(AdminUserDetailsEnum.SUFFIX).getText();
		  assertEquals(fullname, (firstName +" "+middleName +" "+lastName +" "+suffix).replace("  ", " ").trim());
		
	}
	
	@Test
	public void usersEditLink(){
		//set_test_case("TC762");
		
		//1- Login
		users.loginProcess(username, password);
		
		//2- Click on Admin
		users._link().admin().click();
		
		//3- Take note of the top user's employee ID
		String employeeID = users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).getText(1);
		
		//4- Click on the Edit link for the top name
		users._link().edit().click(1);
		
		//5- Verify the page is the correct page
		edituser._textField().personFields(AdminUsersEntries.EMPLOYEE_ID).assertEquals(employeeID);
		
		
	}
	
	@Test
	public void usersSearch(){
		//set_test_case("TC766");
		
		//1- Login
		users.loginProcess(username, password);
		
		//2- Click on Admin
		users._link().admin().click();
		
		//3- Enter a user name into the search field
		users._textField().search().type("Danni");
		
		//4- Click Search
		users._button().search().click();
		
		//5- Verify only that name shows on the list
		String name = "Danni";
		boolean hasOnlyExpectedUsernames = true;
		boolean hasThisRow = true;
		for (int i = 1; i < 10 && hasOnlyExpectedUsernames && hasThisRow; i++){
			hasThisRow = users._link().tableEntry(AdminUsersEntries.FULL_NAME).isPresent(i);
			if(hasThisRow){
				hasOnlyExpectedUsernames &= users._link().tableEntry(AdminUsersEntries.FULL_NAME).validateContains(i, name);
				
			}
		}
	}
	
	@Test
	public void usersEditColumnsCancel(){
		//set_test_case("TC769");
		
		//1- Login
		users.loginProcess(username, password);
		
		//2- Click on Admin
		users._link().admin().click();
		
		//3- Take note of what information shows in the columns
		boolean originallyHadGroupColumnName = users._link().tableEntry(AdminUsersEntries.FULL_NAME).isPresent(1);
		boolean originallyHadGroupColumnEmpID = users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1);
		boolean originallyHadGroupColumnDOB = users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1);
		boolean originallyHadGroupColumnLicense = users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1);
		
		//4- Click on Edit Columns
		users._link().editColumns().click();
		
		//5- Click on one or more boxes
		users._popUp().editColumns()._checkBox().click(1);
		users._popUp().editColumns()._checkBox().click(12);
		users._popUp().editColumns()._checkBox().click(15);
		
		//6- Click Cancel
		users._popUp().editColumns()._button().cancel().click();
		
		//7- Verify nothing has changed
		if (originallyHadGroupColumnName != users._link().tableEntry(AdminUsersEntries.FULL_NAME).isPresent(1)) {
            addError("expected line to remain the same", ErrorLevel.ERROR);
        }
		if (originallyHadGroupColumnEmpID != users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnDOB != users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnLicense != users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
	}
	
	@Test
	public void usersEditColumnsCancelNoChange(){
		//set_test_case("TC770");
		
		//1- Login
		users.loginProcess(username, password);
		
		//2- Click on Admin
		users._link().admin().click();
		
		//3- Take note of what information shows in the columns
		boolean originallyHadGroupColumnName = users._link().tableEntry(AdminUsersEntries.FULL_NAME).isPresent(1);
		boolean originallyHadGroupColumnEmpID = users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1);
		boolean originallyHadGroupColumnDOB = users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1);
		boolean originallyHadGroupColumnLicense = users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1);
		
		//4- Click on Edit Columns
		users._link().editColumns().click();
		
		//5- Click Cancel
		users._popUp().editColumns()._button().cancel().click();
		
		//6- Verify nothing has changed
		if (originallyHadGroupColumnName != users._link().tableEntry(AdminUsersEntries.FULL_NAME).isPresent(1)) {
            addError("something", ErrorLevel.ERROR);
        }
		if (originallyHadGroupColumnEmpID != users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnDOB != users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnLicense != users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
	}
	
	@Test
	public void usersEditColumnsRetention(){
		//set_test_case("TC773");
		
		//1- Login
		users.loginProcess(username, password);
		
		//2- Click on Admin
		users._link().admin().click();
		
		//3- Take note of what information shows in the columns
		boolean originallyHadGroupColumnName = users._link().tableEntry(AdminUsersEntries.FULL_NAME).isPresent(1);
		boolean originallyHadGroupColumnEmpID = users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1);
		boolean originallyHadGroupColumnDOB = users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1);
		boolean originallyHadGroupColumnLicense = users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1);
		
		//4- Click on Edit Columns
		users._link().editColumns().click();
		
		//5- Click on one or more boxes
		users._popUp().editColumns()._checkBox().click(1);
		users._popUp().editColumns()._checkBox().click(12);
		users._popUp().editColumns()._checkBox().click(15);
		
		//6- Click Save
		users._popUp().editColumns()._button().save().click();
		
		//7- Verify changes are there
		if (originallyHadGroupColumnName == users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnEmpID == users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnDOB == users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnLicense != users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
		
		//8- Click on another page
		users._link().hos().click();
		
		//9- Go back to the Admin page
		users._link().admin().click();
		
		//10- Verify changes remain
		if (originallyHadGroupColumnName == users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnEmpID == users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnDOB == users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnLicense != users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
		
		//11- Click on Edit Columns
		users._link().editColumns().click();
		
		//12- Click on the same boxes again
		users._popUp().editColumns()._checkBox().click(1);
		users._popUp().editColumns()._checkBox().click(3);
		users._popUp().editColumns()._checkBox().click(5);
		
		//13- Click Save to return to default
		users._popUp().editColumns()._button().save().click();
				
	}
	
	@Test
	public void usersEditColumnsSave(){
		//set_test_case("TC775");
		
		//1- Login
		users.loginProcess(username, password);
		
		//2- Click on Admin
		users._link().admin().click();
		
		//3- Take note of what information shows in the columns
		boolean originallyHadGroupColumnName = users._link().tableEntry(AdminUsersEntries.FULL_NAME).isPresent(1);
		boolean originallyHadGroupColumnEmpID = users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1);
		boolean originallyHadGroupColumnDOB = users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1);
		boolean originallyHadGroupColumnLicense = users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1);
		
		//4- Click on Edit Columns
		users._link().editColumns().click();
		
		//5- Click on one or more boxes
		users._popUp().editColumns()._checkBox().click(1);
		users._popUp().editColumns()._checkBox().click(12);
		users._popUp().editColumns()._checkBox().click(15);
		
		//6- Click Save
		users._popUp().editColumns()._button().save().click();
		
		//7- Verify changes are there
		if (originallyHadGroupColumnName == users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnEmpID == users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnDOB == users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnLicense != users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
		
		//8- Click on Edit Columns
		users._link().editColumns().click();
		
		//9- Click on the same boxes again
		users._popUp().editColumns()._checkBox().click(1);
		users._popUp().editColumns()._checkBox().click(3);
		users._popUp().editColumns()._checkBox().click(5);
		
		//10- Click Save to return to default
		users._popUp().editColumns()._button().save().click();
	}
	
	@Test
	public void usersEditColumnsNewSession(){
		//set_test_case("TC776");
		
		//1- Login
		users.loginProcess(username, password);
		
		//2- Click on Admin
		users._link().admin().click();
		
		//3- Take note of what information shows in the columns
		boolean originallyHadGroupColumnName = users._link().tableEntry(AdminUsersEntries.FULL_NAME).isPresent(1);
		boolean originallyHadGroupColumnEmpID = users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1);
		boolean originallyHadGroupColumnDOB = users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1);
		boolean originallyHadGroupColumnLicense = users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1);
		
		//4- Click on Edit Columns
		users._link().editColumns().click();
		
		//5- Click on one or more boxes
		users._popUp().editColumns()._checkBox().click(1);
		users._popUp().editColumns()._checkBox().click(12);
		users._popUp().editColumns()._checkBox().click(15);
		
		//6- Click Save
		users._popUp().editColumns()._button().save().click();
		
		//7- Verify changes are there
		if (originallyHadGroupColumnName == users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnEmpID == users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnDOB == users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnLicense != users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
	
		//8- Logout
		users._link().logout().click();
		
		//9- Log back in
		users.loginProcess(username, password);
		
		//10- Click on Admin
		users._link().admin().click();
		
		//11- Verify changes are still there
		if (originallyHadGroupColumnName == users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnEmpID == users._link().tableEntry(AdminUsersEntries.EMPLOYEE_ID).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnDOB == users._link().tableEntry(AdminUsersEntries.DOB_MAIN).isPresent(1)) {
			addError("Expected line to change", ErrorLevel.ERROR);
		}
		if (originallyHadGroupColumnLicense != users._link().tableEntry(AdminUsersEntries.LICENSE_NUMBER).isPresent(1)) {
			addError("expected line to remain the same", ErrorLevel.ERROR);
		}
		
		//12- Click on Edit Columns
		users._link().editColumns().click();
		
		//13- Click on the same boxes again
		users._popUp().editColumns()._checkBox().click(1);
		users._popUp().editColumns()._checkBox().click(3);
		users._popUp().editColumns()._checkBox().click(5);
		
		//14- Click Save to return to default
		users._popUp().editColumns()._button().save().click();
		
	}
	
	
}
