package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.*;


import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.DataReaderLib;
import com.inthinc.pro.web.selenium.DataSenderLib;
import com.inthinc.pro.web.selenium.InthincTest;
import com.inthinc.pro.web.selenium.portal.Masthead.*;

//@Ignore
public class LoginTest extends InthincTest {
	//instantiate var for data reader
	DataReaderLib testdata = new DataReaderLib();
	DataSenderLib out = new DataSenderLib();
	
		

	@Test
	public void LoginButton() {
		//create instance of library objects
		Login l = new Login();
		//Set up test data
		set_test_case("TC1247");
	
		//login to portal
		l.bookmark_login_open();
		l.text_field_password_type("password");
		l.text_field_username_type("0001");
		l.button_log_in_click();
	}
	
//	@Test
	public void UI() {
		//create instance of library objects
		Login l = new Login();
		//Set up test data
		set_test_case("TC4632");
		//go to Login Screen
		l.bookmark_login_open();
		//verify login screen is displayed correctly
		l.page_log_in_validate();
			
			
		}
	
}




