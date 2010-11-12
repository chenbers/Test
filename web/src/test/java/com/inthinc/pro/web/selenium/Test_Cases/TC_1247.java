package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Test;
import com.inthinc.pro.web.selenium.portal.NAVIGATE;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.Data_Reader;
import com.inthinc.pro.web.selenium.portal.Masthead.*;



public class TC_1247 extends NAVIGATE {
	//instantiate var for data reader
	Data_Reader testdata = new Data_Reader();
	
	
	
	@Test
	public void testcase() {
	//create instance of library objects
		Login l = new Login();
		Masthead mh = new Masthead();
		
		//Set up test data
		set_test_case("C:/Program Files/Eclipse_maven/eclipse_win32/eclipse/workspace/root/web/src/test/resources/Data/Tiwi_data.xls", "TC1247");
	
		
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
	
}




