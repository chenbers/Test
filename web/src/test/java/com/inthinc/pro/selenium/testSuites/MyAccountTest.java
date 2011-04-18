package com.inthinc.pro.selenium.testSuites;

import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.Login;
import com.inthinc.pro.selenium.pageObjects.MyAccount;

/*My Account test*/
public class MyAccountTest extends WebRallyTest {
	
	@Test
	public void AccountInformation(){
		set_test_case("TC1266");
		MyAccount my = new MyAccount();
		Login login = new Login();
		login.page_login_process("tnilson", "password");
		my.link_myAccount_click();
		
	/*Login Info*/
		String username = my.textField_userName_getText();
		my.assertEquals("tnilson", username);
		String locale = my.textField_locale_getText();
		my.assertEquals("English (United States)", locale); 
		String measurement = my.textField_measurement_getText();
		my.assertEquals("English", measurement);
		String fuel = my.textField_fuelEfficiency_getText();
		my.assertEquals ("Miles Per Gallon (US)", fuel);
		
	/*Account Info*/
		String name = my.textField_name_getText();
		my.assertEquals("Tina L Nilson", name);
		String group = my.textField_group_getText();
		my.assertEquals("Top", group);
		String team = my.textField_team_getText();
		my.assertEquals ("Skip's Team", team);
	
	/*Red Flags*/
		String info = my.textField_informationRedFlag_getText();
		my.assertEquals("E-mail 1", info);
		String warn = my.textField_warningRedFlag_getText();
		my.assertEquals("E-mail 1", warn);
		String critical = my.textField_criticalRedFlag_getText();
		my.assertEquals("E-mail 1", critical);
		
	/*Contact Info*/
		String email1 = my.textField_emailAddress1_getText();
		my.assertEquals("tnilson@inthinc.com", email1);
		String email2 = my.textField_emailAddress2_getText();
		my.assertEquals("", email2);
		String phone1 = my.textField_phoneNumber1_getText();
		my.assertEquals("", phone1);
		String phone2 = my.textField_phoneNumber2_getText();
		my.assertEquals("", phone2);
		String textmsg1 = my.textField_textMessage1_getText();
		my.assertEquals("", textmsg1);
		String textmsg2 = my.textField_textMessage2_getText();
		my.assertEquals ("", textmsg2);
						
	}

}
