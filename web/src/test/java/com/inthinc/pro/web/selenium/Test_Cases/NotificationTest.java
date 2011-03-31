package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.selenium.DataReaderLib;
import com.inthinc.pro.automation.selenium.InthincTest;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.portal.Masthead.*;
import com.inthinc.pro.web.selenium.portal.Notifications.*;

@Ignore
public class NotificationTest extends InthincTest {
	//instantiate VAR for data reader
	DataReaderLib testdata = new DataReaderLib();
	
		

	@Test	//Each Test Case shall have a separate function
	public void TestCaseName1() {
		//create instance of library objects
		Login l = new Login();
		Masthead mh = new Masthead();
		Notifications N = new Notifications();
		//Set up test data
		
		set_test_case("TC4631");		
	
		l.page_login("0001", "password");

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




