package com.inthinc.pro.web.selenium.Test_Cases;


import org.junit.*;

import com.inthinc.pro.web.selenium.portal.InthincTest;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.portal.Masthead.Masthead;


public class Testing extends InthincTest {
	

	Login l = new Login();
	Masthead m = new Masthead();

	@Test
	public void test_me(){
		set_test_case("Tiwi_data.xls", "TC1187");
		System.out.println(get_data("Login", "USERNAME"));
		System.out.println(get_data("Login", "PASSWORD"));
		System.out.println(get_data("Login", "EMAIL_ADDR"));
		l.open_login();
		m.ck_footer();
		l.ck_login_page();
		l.type_username(get_data("Login","USERNAME"));
		l.type_password(get_data("Login","PASSWORD"));
		l.click_login();
		m.ck_header();
		m.click_logout();
	}
	
	@Test
	public void test_again(){
		set_test_case("Tiwi_data.xls", "TC1187");
		l.open_login();
		l.login_to_portal(get_data("Login","USERNAME"), get_data("Login","PASSWORD"));
		m.click_my_account();
		m.click_logout();
		m.ck_footer();
	}
	
}
