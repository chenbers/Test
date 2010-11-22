package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.Data_Reader;
import com.inthinc.pro.web.selenium.InthincTest;
import com.inthinc.pro.web.selenium.portal.Masthead.*;
import com.inthinc.pro.web.selenium.portal.Notifications.*;

@Ignore
public class NotificationTest extends InthincTest {
	//instantiate var for data reader
	Data_Reader testdata = new Data_Reader();
	
		

	@Test	//Each Test Case shall have a separate function
	public void TestCaseName1() {
		//create instance of library objects
		Login l = new Login();
		Masthead mh = new Masthead();
		Notifications N = new Notifications();
		//Set up test data
		set_test_case("Tiwi_data.xls", "TC4631");
	
		//login to portal
		l.login_to_portal(get_data("Login","USERNAME"), get_data("Login","PASSWORD"));
			

		N.menuItem("redflags");
		N.ckRedflagScr();
		N.selectTimeFrame("redFlags", "Today");
		N.enterInfo("redFlags", "vehicle", "Mustang");
		N.enterInfo("redFlags", "group", "Groupone");
		N.enterInfo("redFlags", "driver", "Lee");
		N.click_refresh("redFlags");
		
	
		//exit Portal
		mh.click_logout();
		
	}
	
	
}




