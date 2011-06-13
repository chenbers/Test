package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageEnums.AdminUsersEnum;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageVehicleReport;

public class ChangePassword extends WebRallyTest {
	
	private PageMyAccount myAccountPage;
	private RandomValues random;
	private String USERNAME = "tnilson";
	private String PASSWORD = "password123";
	
	@Before
	public void setupPage() {
		random = new RandomValues();
		myAccountPage = new PageMyAccount();
	}
	
	@Test
	public void CancelPasswordChanges() {
		set_test_case("TC1285");
		//0. login
		myAccountPage.loginProcess(USERNAME, PASSWORD);
		
		//1.
		String password=random.getMixedString(10);
		myAccountPage._link().myAccount().click();
		myAccountPage._button().change().click();
		myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
		myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(password);
		myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(password);
		myAccountPage._popUp().changeMyPassword()._button().cancel().click();
		
	}
	@Test
	public void CancelChange() {
		set_test_case("TC1286");
		//0. login
		myAccountPage.loginProcess(USERNAME, PASSWORD);
		
		//1.
		myAccountPage._link().myAccount().click();
		myAccountPage._button().change().click();
		myAccountPage._popUp().changeMyPassword()._button().cancel().click();
	}
		
	@Test
	public void CaseError() {
		set_test_case("TC1287");
		
		//0. login
		myAccountPage.loginProcess(USERNAME, PASSWORD);
			
		String password=random.getMixedString(10);
		myAccountPage._link().myAccount().click();
		myAccountPage._button().change().click();
		myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
		myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(password.toUpperCase()) ;
		myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(password.toLowerCase()) ;
		myAccountPage._popUp().changeMyPassword()._button().change().click();
		pause(10, "");
		myAccountPage._popUp().changeMyPassword()._text().confirmPasswordError().validate("New and Confirm New Password do not match");
		myAccountPage._popUp().changeMyPassword()._button().cancel().click();
	
		
	}
	
	@Test
	public void ChangeButton() {
		set_test_case("TC1288");
		
		//0. login
		myAccountPage.loginProcess(USERNAME, PASSWORD);
		
		String password=random.getMixedString(10);
		myAccountPage._link().myAccount().click();
		myAccountPage._button().change().click();
		myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
		myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(password);
		myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(password);
		myAccountPage._popUp().changeMyPassword()._button().change().click();
		myAccountPage._text().infoMessage().validate("Password successfully changed");
		
		
		//1. Verify new password
		myAccountPage.loginProcess(USERNAME, password);		
		
		//2. Reset password to original
		
		myAccountPage._link().myAccount().click();
		myAccountPage._button().change().click();
		myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(password);
		myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(PASSWORD);
		myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(PASSWORD);
		myAccountPage._popUp().changeMyPassword()._button().change().click();
		
	}
	@Test
	public void ConfirmNewPasswordError() {
		set_test_case("TC1289");
			
	//0. login
	myAccountPage.loginProcess(USERNAME, PASSWORD);
		
	myAccountPage._link().myAccount().click();
	myAccountPage._button().change().click();
	myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
	myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(random.getMixedString(12)) ;
	myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(random.getMixedString(12)) ;
	myAccountPage._popUp().changeMyPassword()._button().change().click();
	pause(10, "");
	myAccountPage._popUp().changeMyPassword()._text().confirmPasswordError().validate("New and Confirm New Password do not match");
	myAccountPage._popUp().changeMyPassword()._button().cancel().click();
	
	}	
	
	
	
	
	
	
}


