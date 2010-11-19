package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.*;


import com.inthinc.pro.web.selenium.portal.InthincTest;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.Data_Reader;
import com.inthinc.pro.web.selenium.Data_Sender;
import com.inthinc.pro.web.selenium.portal.Masthead.*;

@Ignore
public class LoginTest extends InthincTest {
	//instantiate var for data reader
	Data_Reader testdata = new Data_Reader();
	Data_Sender out = new Data_Sender();
	
		

	@Test
	public void LoginButton() {
		//create instance of library objects
		Login l = new Login();
		Masthead mh = new Masthead();
		
		//Set up test data
		set_test_case("Tiwi_data.xls", "TC1247");
	
		//login to portal
		l.login_to_portal(get_data("Login","USERNAME"), get_data("Login","PASSWORD"));
			
				
		//Verify Mast Head Screen
		mh.ck_header();
		mh.ck_footer();
		mh.click_support();
		mh.click_privacy();
		mh.click_legal();
		
		//exit Portal
		mh.click_logout();
		
	}
	
	@Test
	public void UI() {
		//create instance of library objects
		Login l = new Login();
		//Set up test data
		set_test_case("Tiwi_data.xls", "TC4632");
		//go to Login Screen
		l.open_login();
		//verify login screen is displayed correctly
		l.ck_login_page();
			
			
		}
	
}




