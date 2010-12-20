package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.web.selenium.InthincTest;
import com.inthinc.pro.web.selenium.portal.*;
import com.inthinc.pro.web.selenium.portal.Login.*;


@Ignore
public class LoginScreen_HP extends InthincTest {

	@Test
	public void testme(){
		
		Login l = new Login();		
		l.login_to_portal("larrington", "tekpass");

	}
}