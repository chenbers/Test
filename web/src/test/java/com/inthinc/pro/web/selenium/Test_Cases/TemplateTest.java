package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Ignore;
import org.junit.Test;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.DataReaderLib;
import com.inthinc.pro.web.selenium.InthincTest;
import com.inthinc.pro.web.selenium.portal.Masthead.*;

@Ignore
public class TemplateTest extends InthincTest {
	//instantiate VAR for data reader
	DataReaderLib testdata = new DataReaderLib();
	
		

	@Test	//Each Test Case shall have a separate function
	public void TestCaseName1() {
		//create instance of library objects
		//Login and Master Heading Screens are done as examples
		Login l = new Login();
		Masthead mh = new Masthead();
		
		//Set up test data
		set_test_case("ExcelFileName", "TestCaseName");
	
		//login to portal
		l.login_to_portal(get_data("WorksheetName","ColumnHeader"), get_data("WorksheetName","ColumnHeader"));
			
				
		/*Call Test Methods using the syntax below
		//ScreenClass.FunctionName
		//See Examples Below
		*
		*
		*
		*
		*
		*
		*/
		mh.ck_header();		//Check Header on Master Heading Screen
		mh.ck_footer();		//Check Footer on Master Heading Screen
		
		
		//exit Portal
		mh.click_logout();
		
	}
	
	@Test	//Each Test Case shall have a separate function
	public void TestCaseName2() {
		//create instance of library objects
		//Login and Master Heading Screens are done as examples
		Login l = new Login();
		Masthead mh = new Masthead();
		
		//Set up test data
		set_test_case("ExcelFileName", "TestCaseName");
	
		//login to portal
		l.login_to_portal(get_data("WorksheetName","ColumnHeader"), get_data("WorksheetName","ColumnHeader"));
			
				
		/*Call Test Methods using the syntax below
		//ScreenClass.FunctionName
		//See Examples Below
		*
		*
		*
		*
		*
		*
		*/
		mh.ck_header();		//Check Header on Master Heading Screen
		mh.ck_footer();		//Check Footer on Master Heading Screen
		
		
		//exit Portal
		mh.click_logout();
		
	}
	
	
}




