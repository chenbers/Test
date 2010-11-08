package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Test;

import com.inthinc.pro.web.selenium.portal.NAVIGATE;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.portal.Masthead.Masthead;



public class Testing extends NAVIGATE {

	@Test
	public void test_me(){
		Login l = new Login();
		Masthead m = new Masthead();
		l.open_login();
		m.ck_footer();
		l.ck_login_page();
		l.type_username("Automation1");
		l.type_password("password");
		l.click_login();
		m.ck_header();
	}

}
