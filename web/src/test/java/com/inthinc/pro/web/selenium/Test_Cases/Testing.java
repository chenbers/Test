package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Test;

//import com.inthinc.pro.web.selenium.Core;
import com.inthinc.pro.web.selenium.portal.NAVIGATE;
//import com.inthinc.pro.web.selenium.portal.Singleton;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.portal.Masthead.Masthead;



public class Testing extends NAVIGATE {
	

	Login l = new Login();
	Masthead m = new Masthead();

	@Test
	public void test_me(){
		set_test_case("c:/Tiwi_data.xls", "TC1187");
		System.out.println(get_data("Login", "USERNAME"));
		System.out.println(get_data("Login", "PASSWORD"));
		System.out.println(get_data("Login", "EMAIL_ADDR"));
		l.open_login();
		m.ck_footer();
		l.ck_login_page();
		l.type_username("Automation1");
		l.type_password("password");
		l.click_login();
		m.ck_header();
		m.click_logout();
	}
	
//	@Test
	public void test_again(){
		l.open_login();
		l.login_to_portal("Automation1", "password");
		m.click_my_account();
		m.click_logout();
		m.ck_footer();
	}
	
//	public static void main( String[] args){
//		Core selenium = Singleton.getSingleton().getSelenium();
//		Login l = new Login();
//		l.login_to_portal("Automation1", "password");
//		selenium.click("link=Notifications");
//		selenium.waitForPageToLoad("30000");
//		selenium.selectDhxCombo("Top", "Group select");
//		selenium.close();
//		selenium.stop();
//	}
}
