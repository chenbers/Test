package com.inthinc.pro.selenium.testSuites;

import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

/*My Account test*/
public class MyAccountTest extends WebRallyTest {
	
	@Test
	public void AccountInformation(){
		set_test_case("TC1266");
		PageMyAccount my = new PageMyAccount();
		PageLogin login = new PageLogin();
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		//my.link_myAccount_click();
		
	/*Login Info*/
		String username = my._text().userName().getText();
		my.assertEquals("tnilson", username);
		String locale = my._text().locale().getText();
		my.assertEquals("English (United States)", locale); 
		String measurement = my._text().measurement().getText();
		my.assertEquals("English", measurement);
		String fuel = my._text().fuelEfficiency().getText();
		my.assertEquals ("Miles Per Gallon (US)", fuel);
		
	/*Account Info*/
		String name = my._text().name().getText();
		my.assertEquals("Tina L Nilson", name);
		String group = my._text().group().getText();
		my.assertEquals("Top", group);
		String team = my._text().team().getText();
		my.assertEquals ("Skip's Team", team);
	
	/*Red Flags*/
		String info = my._text().redFlagInfo().getText();
		my.assertEquals("E-mail 1", info);
		String warn = my._text().redFlagWarn().getText();
		my.assertEquals("E-mail 1", warn);
		String critical = my._text().redFlagCritical().getText();
		my.assertEquals("E-mail 1", critical);
		
	/*Contact Info*/
		String email1 = my._text().email1().getText();
		my.assertEquals("tnilson@inthinc.com", email1);
		String email2 = my._text().email2().getText();
		my.assertEquals("", email2);
		String phone1 = my._text().phone1().getText();
		my.assertEquals("", phone1);
		String phone2 = my._text().phone2().getText();
		my.assertEquals("", phone2);
		String textmsg1 = my._text().textMessage1().getText();
		my.assertEquals("", textmsg1);
		String textmsg2 = my._text().textMessage2().getText();
		my.assertEquals ("", textmsg2);
						
	}

}
