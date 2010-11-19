package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.*;
import com.inthinc.pro.web.selenium.portal.InthincTest;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.portal.Masthead.*;


@Ignore
public class Log_In extends InthincTest {
	//create instance of library objects
	Login l;
	Masthead mh; 
	
	
	
	@Test
	public void TC1247() {
		//create instance of library objects
		l = new Login();
		mh = new Masthead();
		
		//Set up test data
		set_test_case("Data/Tiwi_data.xls", "TC1247");
		
		//login to portal
		l.login_to_portal(get_data("Login","USERNAME"), get_data("Login","PASSWORD"));
		
		//Verify Mast Head Screen
		mh.ck_header();
		mh.ck_footer();
		mh.click_support();
		mh.click_privacy();
		mh.click_legal();
	}
}




