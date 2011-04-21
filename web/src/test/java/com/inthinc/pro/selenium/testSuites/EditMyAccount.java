package com.inthinc.pro.selenium.testSuites;

import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount.RedFlagPrefs;

public class EditMyAccount extends WebRallyTest {
	
	@Test
	public void CancelButton_Changes(){
		set_test_case("TC1271");
		PageMyAccount my = new PageMyAccount();
		PageLogin login = new PageLogin();
		login.page_login_process("tnilson", "password");
		my.link_myAccount_click();
	
	/*Edit button*/
		my.button_edit_click();
		
	/*Login Info*/
			
		my.dropDown_locale_selectText("Deutsch");		
		my.dropDown_measurement_selectText("Metric");
		my.dropDown_fuelEfficiency_selectText("Kilometers Per Liter");
		
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

}

	
	
		
	