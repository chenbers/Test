package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Test;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.InthincTest;
import com.inthinc.pro.web.selenium.portal.Masthead.*;
import com.inthinc.pro.web.selenium.portal.Navigate;
import com.inthinc.pro.web.selenium.portal.Home.Team.DriverStats;
import com.inthinc.pro.web.selenium.portal.ChartMapLib;
import com.inthinc.pro.web.selenium.portal.Users.*;

public class User_AdminTest extends InthincTest {
	//instantiate var for data reader
//	Data_Reader testdata = new Data_Reader();
	
		
	
	@Test	//Each Test Case shall have a separate function
	public void TestCaseName2() {
		//create instance of library objects
		//Login and Master Heading Screens are done as examples
		Login l = new Login();
		Masthead mh = new Masthead();
		Navigate nav = new Navigate();
		DriverStats dstat = new DriverStats();
		ChartMapLib cml = new ChartMapLib();
		
		set_test_case("Tiwi_data.xls", "TC4630");
		
		//login to portal
		l.login_to_portal("0001", "password");
//		l.login_to_portal(get_data("Login","USERNAME"), get_data("Login","PASSWORD"));
				
		nav.select_home_tree("A Skip's Division", "Salt Lake City", "Skip's Team","Select Skip's Team");
		
		dstat.chk_DriverStatistics("Before Adding Something");
	
		dstat.chkTeamCrashDays("3", "Before the changes");
		dstat.chkTeamCrashes("50", "Before the changes");
		dstat.chkTeamCrashMiles("260.1", "Before the changes");
		dstat.chkTeamName("Skip's Team", "Before the changes");
	
		dstat.SelectDriver("Billy Aikin", "Select Levi Sorenson");
		
		cml.pieGraph();
		
//		String ans = dstat.SearchList("Levi Sorenson", 5, " Negative Search");
//        System.out.println(ans);
//		String safteyvalue = dstat.getListValue("Levi Sorenson", 5, " safety","After changing something");
//        System.out.println(safteyvalue);
//		
//        dstat.validateTotal("0.2 mi","MilesDriven", "Something");
	
		//exit Portal
		mh.click_logout();
		
	}
	

//	@Test	//Each Test Case shall have a separate function
	public void TestCaseName1() {
	//create instance of library objects
		//Login and Master Heading Screens are done as examples
		Login l = new Login();
		Masthead mh = new Masthead();
		UserAdmin ad = new UserAdmin();
		UserAddEdit ae = new UserAddEdit();
		Navigate nav = new Navigate();
	
		set_test_case("Tiwi_data.xls", "TC4630");
	
	//login to portal
		l.login_to_portal(get_data("Login","USERNAME"), get_data("Login","PASSWORD"));
			
		nav.main_menu_select("Admin", "Select Admin Menu");
		ad.chk_User_Admin_Screen("UserScreenTC:4630");
		String text;
		text = ad.SearchList("Admin Edit Vehicle", 54, "Search for:Admin Edit Vehicle user");
		System.out.print(text);
		
		nav.admin_menu_select("Add User", "Select Admin Menu","80000");
		

		ae.chk_screen_buttons("UserEditButtons:TC4630");
		ae.chk_screen_headings();
	ae.chk_userInfo_labels("UserEditButtons:TC4630");
	
	
		
	
	//exit Portal
		mh.click_logout();
		
	}
	
	
	
	
}




