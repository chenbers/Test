package com.inthinc.pro.selenium.testSuites;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageEnums.AdminUserDetailsEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.Fuel_Ratio;
import com.inthinc.pro.selenium.pageEnums.TAE.Locale;
import com.inthinc.pro.selenium.pageEnums.TAE.Measurement;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;

/*My Account test*/
public class AdminUsersTest extends WebRallyTest {
	
	
	private PageAdminUsers my;
	private PageAdminUserDetails myAdminUserDetails;
	private RandomValues random;
	private String USERNAME = "tinaauto";
	private String PASSWORD = "password";
	
	@Before
	public void setupPage() {
		random = new RandomValues();
		my = new PageAdminUsers();
		myAdminUserDetails = new PageAdminUserDetails();
	}
	
	@Test
	public void AccountInformation(){
	  	set_test_case("TC1266");
				
		my = new PageAdminUsers ();
		
		my.loginProcess(USERNAME, PASSWORD);
		my._link().admin().click();
		boolean clicked = false;
		Iterator<ClickableTextBased> iter = my._link().tableEntryUserName().iterator();
		while(iter.hasNext()&&!clicked){
		    ClickableTextBased username = iter.next();
		    
		    if(username.getText().equalsIgnoreCase("Tina Test Automation Jr.")){
		        username.click();
		        clicked = true;
		    }
		}
		if(!clicked)
		    addError("couldn't find user name", ErrorLevel.FAIL);
	
		
	/*User Info*/
		myAdminUserDetails._text().labels(AdminUserDetailsEnum.FIRST_NAME).validate("First Name:");
		myAdminUserDetails._text().values(AdminUserDetailsEnum.FIRST_NAME).validate("Tina");
		myAdminUserDetails._text().labels(AdminUserDetailsEnum.MIDDLE_NAME).validate("Middle Name:");
		myAdminUserDetails._text().values(AdminUserDetailsEnum.MIDDLE_NAME).validate("Test");
		myAdminUserDetails._text().labels(AdminUserDetailsEnum.LAST_NAME).validate("Last Name:");
		myAdminUserDetails._text().values(AdminUserDetailsEnum.LAST_NAME).validate("Automation");
		myAdminUserDetails._text().labels(AdminUserDetailsEnum.SUFFIX).validate("Suffix:");
		myAdminUserDetails._text().values(AdminUserDetailsEnum.SUFFIX).validate("Jr.");
		myAdminUserDetails._text().labels(AdminUserDetailsEnum.DOB).validate("DOB:");
		myAdminUserDetails._text().values(AdminUserDetailsEnum.DOB).validate("Sep 1, 1980");
		myAdminUserDetails._text().labels(AdminUserDetailsEnum.GENDER).validate("Gender:");
		myAdminUserDetails._text().values(AdminUserDetailsEnum.GENDER).validate("Female");
		
	/*Driver Info*/
		myAdminUserDetails._text().labels(AdminUserDetailsEnum.LICENSE_NUMBER).validate("Driver License #:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.LICENSE_NUMBER).validate("123456789");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.CLASS).validate("Class:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.CLASS).validate("C");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.STATE).validate("State:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.STATE).validate("Utah");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.EXPIRATION).validate("Expiration:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.EXPIRATION).validate("Sep 1, 2020");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.CERTIFICATIONS).validate("Certifications:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.CERTIFICATIONS).validate("HAZMAT");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.DOT).validate("DOT:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.DOT).validate("US Oil 8 Day");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.TEAM).validate("Team:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.TEAM).validate("Tina's Auto Team");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.DRIVER_STATUS).validate("Status:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.DRIVER_STATUS).validate("Active");
        
     /*Employee Info*/
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.EMP_ID).validate("Employee ID:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.EMP_ID).validate("TINAAUTO");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.REPORTS_TO).validate("Reports To:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.REPORTS_TO).validate("David");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.TITLE).validate("Title:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.TITLE).validate("Driver Manager");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.LOCALE).validate("Locale:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.LOCALE).validate("English (United States)");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.TIME_ZONE).validate("Time Zone:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.TIME_ZONE).validate("US/Mountain");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.MEASUREMENT).validate("Measurement:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.MEASUREMENT).validate("English");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.FUEL_EFFICIENCY_RATIO).validate("Fuel Efficiency Ratio:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.FUEL_EFFICIENCY_RATIO).validate("Miles Per Gallon (US)");

      
     /* Login Info*/
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.USER_NAME).validate("User Name:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.USER_NAME).validate("tinaauto");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.GROUP).validate("Group:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.GROUP).validate("Tina's Auto Team");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.ROLES).validate("Roles:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.ROLES).validate("Normal Admin");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.USER_STATUS).validate("Status:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.USER_STATUS).validate("Active");  
        
      /*Notifications*/
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.EMAIL_1).validate("E-mail 1:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.EMAIL_1).validate("tinaauto@test.com");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.EMAIL_2).validate("E-mail 2:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.EMAIL_2).validate("712@test.com");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.TEXT_1).validate("Text Message 1:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.TEXT_1).validate("2223334444@tmomail.com");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.TEXT_2).validate("Text Message 2:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.TEXT_2).validate("5556667777@tmomail.com");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.PHONE_1).validate("Phone 1:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.PHONE_1).validate("111-222-3333");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.PHONE_2).validate("Phone 2:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.PHONE_2).validate("444-555-6666");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.INFORMATION).validate("Information:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.INFORMATION).validate("E-mail 1");
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.WARNING).validate("Warning:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.WARNING).validate("Phone 2"); 
        myAdminUserDetails._text().labels(AdminUserDetailsEnum.CRITICAL).validate("Critical:");
        myAdminUserDetails._text().values(AdminUserDetailsEnum.CRITICAL).validate("Text Message 1"); 
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
	}   
        
}     
        
	