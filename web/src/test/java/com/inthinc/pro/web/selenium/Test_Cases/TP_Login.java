package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Test;

import com.inthinc.pro.web.selenium.portal.NAVIGATE;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.Data_Reader;
import com.inthinc.pro.web.selenium.Data_Sender;
import com.inthinc.pro.web.selenium.portal.Masthead.*;

public class TP_Login extends NAVIGATE {
	//instantiate var for data reader
	Data_Reader testdata = new Data_Reader();
	Data_Sender out = new Data_Sender();
	
		
//	public void TF_97_Driver() {
//		
//		TC1247();
//		TC1250();
//		
//	}
	@Test
	public void TC1247() {
		System.out.println("TC1247 executed");
		
		//create instance of library objects
		Login l = new Login();
		Masthead mh = new Masthead();
		
		//Set up test data
		set_test_case("Tiwi_data.xls", "TC4631");
	
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
		
		
//		OutputStreamWriter oout = Data_Sender.OpenOutputStream("/Tiwi_output.xls");
//		Data_Sender.writeLine(oout, "TC1247 executed successfully");
//		out.closeOutputSteam(oout);
	}
	
	@Test
	public void TC1250() {
		System.out.println("TC1250 executed");
		//create instance of library objects
		Login l = new Login();
		//Set up test data
		set_test_case("Tiwi_data.xls", "TC4632");
		//go to Login Screen
		l.open_login();
		//verify login screen is displayed correctly
		l.ck_login_page();
		
			
//			OutputStreamWriter oout = Data_Sender.OpenOutputStream("C:/Program Files/Eclipse_maven/eclipse_win32/eclipse/workspace/root/web/src/test/resources/Data/Tiwi_output.xls");
//			Data_Sender.writeLine(oout, "TC1250 executed successfully");
//			out.closeOutputSteam(oout);
//			
			
		}
	
}




