package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount.RedFlagPrefs;

public class EditMyAccountTest extends WebRallyTest {
	
	private PageMyAccount my;
	private PageLogin login;
	
	
	@Before
	public void setupPage(){
		my = new PageMyAccount();
		login = new PageLogin();
	}
	
	@Test
	public void CancelButton_Changes(){
		set_test_case("TC1271");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
	
	/*Edit button*/
		my.button_edit_click();
		
	/*Login Info*/
			
		my.dropDown_locale_selectText("English");		
		my.dropDown_measurement_selectText("Metric");
		my.dropDown_fuelEfficiency_selectText("Liters Per 100 Kilometers");
		
	/*Contact Info*/
		
		my.textField_emailAddress1_type("tina1965@test.com");		
		my.textField_emailAddress2_type("tlc1965@test.com");
		my.textField_phoneNumber1_type("801-777-7777");
		my.textField_phoneNumber2_type("801-999-9999");
		my.textField_textMessage1_type("801-777-9999@tmomail.net");
		my.textField_textMessage2_type("801-999-7777@tmomail.net");
		
	/*Red Flags*/
		my.dropDown_information_selectValue(RedFlagPrefs.TEXT1);
		my.dropDown_warning_selectValue(RedFlagPrefs.EMAIL1);
		my.dropDown_critical_selectValue(RedFlagPrefs.PHONE1);
			
	/* Cancel Changes */
		
		my.button_cancel_click();
		
/*Verify Changes did not take effect*/			
	/*Login Info*/
		String username = my.text_userName_getText();
		my.assertEquals("tnilson", username);
		String locale = my.text_locale_getText();
		my.assertEquals("English (United States)", locale); 
		String measurement = my.text_measurement_getText();
		my.assertEquals("English", measurement);
		String fuel = my.text_fuelEfficiency_getText();
		my.assertEquals ("Miles Per Gallon (US)", fuel);
		
	/*Account Info*/
		String name = my.text_name_getText();
		my.assertEquals("Tina L Nilson", name);
		String group = my.text_group_getText();
		my.assertEquals("Top", group);
		String team = my.text_team_getText();
		my.assertEquals ("Skip's Team", team);
	
	/*Red Flags*/
		String info = my.text_informationRedFlag_getText();
		my.assertEquals("E-mail 1", info);
		String warn = my.text_warningRedFlag_getText();
		my.assertEquals("E-mail 1", warn);
		String critical = my.text_criticalRedFlag_getText();
		my.assertEquals("E-mail 1", critical);
		
	/*Contact Info*/
		String email1 = my.text_emailAddress1_getText();
		my.assertEquals("tnilson@inthinc.com", email1);
		String email2 = my.text_emailAddress2_getText();
		my.assertEquals("", email2);
		String phone1 = my.text_phoneNumber1_getText();
		my.assertEquals("", phone1);
		String phone2 = my.text_phoneNumber2_getText();
		my.assertEquals("", phone2);
		String textmsg1 = my.text_textMessage1_getText();
		my.assertEquals("", textmsg1);
		String textmsg2 = my.text_textMessage2_getText();
		my.assertEquals ("", textmsg2);	
	}

	@Test
	public void SaveButton_Changes(){
		set_test_case("TC1280");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
	
	/*Edit button*/
		my.button_edit_click();
		
	/*Login Info*/
			
		my.dropDown_locale_selectText("English (United States)");	
		my.dropDown_measurement_selectText("Metric");
		my.dropDown_fuelEfficiency_selectText("Liters Per 100 Kilometers");
		
	/*Contact Info*/
		
		my.textField_emailAddress1_type("tina1965@test.com");		
		my.textField_emailAddress2_type("tlc1965@test.com");
		my.textField_phoneNumber1_type("801-777-7777");
		my.textField_phoneNumber2_type("801-999-9999");
		my.textField_textMessage1_type("801-777-9999@tmomail.net");
		my.textField_textMessage2_type("801-999-7777@tmomail.net");
		
	/*Red Flags*/
		my.dropDown_information_selectValue(RedFlagPrefs.TEXT1);
		my.dropDown_warning_selectValue(RedFlagPrefs.EMAIL1);
		my.dropDown_critical_selectValue(RedFlagPrefs.PHONE1);
			
	/* Save Changes */
		my.button_save_click();
		
/*Verify Changes Display*/			
	/*Login Info*/
		String username = my.text_userName_getText();
		my.assertEquals("tnilson", username);
		String locale = my.text_locale_getText();
		my.assertEquals("English (United States)", locale);
		String measurement = my.text_measurement_getText();
		my.assertEquals("Metric", measurement);
		String fuel = my.text_fuelEfficiency_getText();
		my.assertEquals ("Liters Per 100 Kilometers", fuel);
		
	/*Account Info*/
		String name = my.text_name_getText();
		my.assertEquals("Tina L Nilson", name);
		String group = my.text_group_getText();
		my.assertEquals("Top", group);
		String team = my.text_team_getText();
		my.assertEquals ("Skip's Team", team);
	
	/*Red Flags*/
		String info = my.text_informationRedFlag_getText();
		my.assertEquals("Text Message 1", info);
		String warn = my.text_warningRedFlag_getText();
		my.assertEquals("E-mail 1", warn);
		String critical = my.text_criticalRedFlag_getText();
		my.assertEquals("Phone 1", critical);
		
	/*Contact Info*/
		String email1 = my.text_emailAddress1_getText();
		my.assertEquals("tina1965@test.com", email1);
		String email2 = my.text_emailAddress2_getText();
		my.assertEquals("tlc1965@test.com", email2);
		String phone1 = my.text_phoneNumber1_getText();
		my.assertEquals("801-777-7777", phone1);
		String phone2 = my.text_phoneNumber2_getText();
		my.assertEquals("801-999-9999", phone2);
		String textmsg1 = my.text_textMessage1_getText();
		my.assertEquals("801-777-9999@tmomail.net", textmsg1);
		String textmsg2 = my.text_textMessage2_getText();
		my.assertEquals ("801-999-7777@tmomail.net", textmsg2);	
	}
	
	@Test
	public void EmailFormatError(){
		set_test_case("TC1271");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
		my.button_edit_click();
		my.textField_emailAddress1_type("tina1965test.com");
		my.button_save_click();
		
		
		
		//Clear?//
		my.textField_emailAddress1_type("tina1965@test.com");
		my.button_save_click();
		String email1 = my.text_emailAddress1_getText();
		my.assertEquals("tnilson@inthinc.com", email1);
		
		my.button_edit_click();
		my.textField_emailAddress2_type("tlc1965@test");
		my.button_save_click();		
		//Validate Error//
		//Clear?//
		my.textField_emailAddress2_type("tlc1965@test.com");
		my.button_save_click();
		String email2 = my.text_emailAddress2_getText();
		my.assertEquals("", email2);
				
	}

	@Test
	public void MeasurementValidation(){
		set_test_case("TC1275");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
	
		my.button_edit_click();
		my.dropDown_measurement_selectText("Metric");
		my.dropDown_fuelEfficiency_selectText("Kilometers Per Liter");	
		my.button_save_click();
		
	//validate pages for Metric Units Goes Here//
		
		my.button_edit_click();
		my.dropDown_measurement_selectText("English");
		my.dropDown_fuelEfficiency_selectText("Miles Per Gallon (US)");	
		my.button_save_click();
		
	//validation for English Units Goes Here//
		
		
	}
	
	@Test
	public void FuelRatioValidation(){
		set_test_case("TC1273");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
		
		my.button_edit_click();
		my.dropDown_measurement_selectText("Metric");
		my.dropDown_fuelEfficiency_selectText("Kilometers Per Liter");	
		my.button_save_click();
		//Validate KM Per Liter Here//		
		
		my.button_edit_click();
		my.dropDown_measurement_selectText("English");
		my.dropDown_fuelEfficiency_selectText("Miles Per Gallon (US)");	
		my.button_save_click();
		//Validate MPG Here//
		
	}
	
	@Test
	public void ClearFieldsValidation(){
		set_test_case("TC1276");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
	
		my.button_edit_click();
		
		
	/*Contact Info*/
		
		my.textField_emailAddress1_type("");		
		my.textField_emailAddress2_type("");
		my.textField_phoneNumber1_type("");
		my.textField_phoneNumber2_type("");
		my.textField_textMessage1_type("");
		my.textField_textMessage2_type("");
			
	/* Save Empty Fields */
		
		my.button_save_click();
	
		//Email Required// 
	}
	@Test
	public void PhoneMaxCharError(){
		set_test_case("TC1277");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
		my.button_edit_click();
		my.textField_phoneNumber1_type("801-777-7777-44444421");
		my.button_save_click();
		//Validate Error//
		//Clear fields?//
		my.textField_phoneNumber1_type("801-777-7777");
		my.button_save_click();
		String phone1 = my.text_phoneNumber1_getText();
		my.assertEquals("801-777-7777", phone1);
		
		my.button_edit_click();
		my.textField_phoneNumber2_type("8");
		my.button_save_click();
		//Validate Error//
		//Clear fields?//
		my.textField_phoneNumber2_type("801-999-9999");
		my.button_save_click();
		String phone2 = my.text_phoneNumber2_getText();
		my.assertEquals("801-999-9999", phone2);
	}
			
	@Test
	public void PhoneMissingCharError(){
		set_test_case("TC1278");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
	
		my.button_edit_click();
		my.textField_phoneNumber1_type("801-777-777");
		my.button_save_click();
		//Validate Error//
		//Clear fields?//
		my.textField_phoneNumber1_type("801-777-7777");
		my.button_save_click();
		String phone1 = my.text_phoneNumber1_getText();
		my.assertEquals("801-777-7777", phone1);
		
		my.button_edit_click();
		my.textField_phoneNumber2_type("801-999-999");
		my.button_save_click();
		//Validate Error//
		//Clear fields?//		
		my.textField_phoneNumber2_type("801-999-9999");
		my.button_save_click();
		String phone2 = my.text_phoneNumber2_getText();
		my.assertEquals("801-999-9999", phone2);
		
	}
	
	@Test
	public void PhoneSpecialCharError(){
		set_test_case("TC1279");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
		my.button_edit_click();
		my.textField_phoneNumber1_type("801-@$%-777&");
		my.button_save_click();
		//Validate Error//
		//Clear fields?//
		my.textField_phoneNumber1_type("801-777-7777");
		my.button_save_click();
		String phone1 = my.text_phoneNumber1_getText();
		my.assertEquals("801-777-7777", phone1);
		
		my.button_edit_click();
		my.textField_phoneNumber2_type("801-ABC-$%+");
		my.button_save_click();
		//Validate Error//
		//Clear fields?//
		my.textField_phoneNumber2_type("801-999-9999");
		my.button_save_click();
		String phone2 = my.text_phoneNumber2_getText();
		my.assertEquals("801-999-9999", phone2);
	}
	
	@Test
	public void TextMsgFormatError(){
		set_test_case("TC1282");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
	
		my.button_edit_click();
		my.textField_textMessage1_type("801-777-9999tmomail.net");
		my.button_save_click();
		//Validate Error //
		//Clear fields?//
		my.textField_textMessage1_type("801-777-9999@tmomail.net");
		my.button_save_click();
		String phone1 = my.text_phoneNumber1_getText();
		my.assertEquals("801-777-9999@tmomail.net", phone1);
		
		my.button_edit_click();
		my.textField_textMessage2_type("801-999-7777@tmomail");
		my.button_save_click();
		//Validate Error //
		//Clear fields?//
		my.button_edit_click();
		my.textField_textMessage2_type("801-999-7777@tmomail.net");
		my.button_save_click();
		String phone2 = my.text_phoneNumber2_getText();
		my.assertEquals("801-999-7777@tmomail.net", phone2);
		
	}
	
	
}

	
	
		
	