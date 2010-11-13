package com.inthinc.pro.web.selenium.Test_Cases;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

//import com.inthinc.pro.web.selenium.Core;
import com.inthinc.pro.web.selenium.XsdDatetimeFormat;
import com.inthinc.pro.web.selenium.portal.NAVIGATE;
//import com.inthinc.pro.web.selenium.portal.Singleton;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.portal.Masthead.Masthead;



public class Testing extends NAVIGATE {
	

	Login l = new Login();
	Masthead m = new Masthead();
	private static Calendar c;

	@Test
	public void test_me(){
		set_test_case("src/test/resources/Test/Tiwi_data.xls", "TC1187");
		System.out.println(get_data("Login", "USERNAME"));
		System.out.println(get_data("Login", "PASSWORD"));
		System.out.println(get_data("Login", "EMAIL_ADDR"));
//		l.open_login();
//		m.ck_footer();
//		l.ck_login_page();
//		l.type_username("Automation1");
//		l.type_password("password");
//		l.click_login();
//		m.ck_header();
//		m.click_logout();
	}
	
//	@Test
	public void test_again(){
		l.open_login();
		l.login_to_portal("Automation1", "password");
		m.click_my_account();
		m.click_logout();
		m.ck_footer();
	}
	
	public static void main( String[] args){
		
		c = Calendar.getInstance(TimeZone.getTimeZone("PST"));
        c.set(Calendar.YEAR, 2050);
        c.set(Calendar.MONTH, 11); //months start from zero!
        c.set(Calendar.DAY_OF_MONTH, 6);
        c.set(Calendar.HOUR_OF_DAY, 21);
        c.set(Calendar.MINUTE, 30);
        c.set(Calendar.SECOND, 15);
        c.set(Calendar.MILLISECOND, 0);
        
		XsdDatetimeFormat xdf = new XsdDatetimeFormat ();
        xdf.setTimeZone("MST");
        
        System.out.println(c.toString());
        String s = xdf.format(c.getTime());
        System.out.println(s);
        assert("2050-12-06T21:30:15-08:00".compareTo(s)==0);
		
	}
}
