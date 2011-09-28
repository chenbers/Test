package com.inthinc.pro.selenium.testSuites;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.selenium.pageEnums.AdminUserDetailsEnum;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

/**
 * AdminUsersTest compares the user account to the my account for accuracy
 *
 */
@Ignore
public class AdminUsersTest extends WebRallyTest {
	
	private PageAdminUsers my;
	private PageMyAccount myAccount;
	private PageAdminUserDetails myAdminUserDetails;
	private String USERNAME = "tinaauto";
	private String PASSWORD = "password";
	
	@Before
	public void setupPage() {
		my = new PageAdminUsers();
		myAccount = new PageMyAccount();
		myAdminUserDetails = new PageAdminUserDetails();
	}
	
	/**
	 * AccountInformation test verifies MyAccount data with Admin User data.
	 * testDependencies:
	 * must have admin access
	 * 
	 */
	
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
		
		String firstname =myAdminUserDetails._text().values(AdminUserDetailsEnum.FIRST_NAME).getText();
		String middlename =myAdminUserDetails._text().values(AdminUserDetailsEnum.MIDDLE_NAME).getText();
		String lastname =myAdminUserDetails._text().values(AdminUserDetailsEnum.LAST_NAME).getText();
		String suffix =myAdminUserDetails._text().values(AdminUserDetailsEnum.SUFFIX).getText();
		
		
	/*Driver Info*/
		
        String team =myAdminUserDetails._text().values(AdminUserDetailsEnum.TEAM).getText();
        
     /*Employee Info*/
      
        String locale = myAdminUserDetails._text().values(AdminUserDetailsEnum.LOCALE).getText();
        String measurement =myAdminUserDetails._text().values(AdminUserDetailsEnum.MEASUREMENT).getText();
        String fuelratio =myAdminUserDetails._text().values(AdminUserDetailsEnum.FUEL_EFFICIENCY_RATIO).getText();

      
     /* Login Info*/
       
        String loginname =myAdminUserDetails._text().values(AdminUserDetailsEnum.USER_NAME).getText();
        String group =myAdminUserDetails._text().values(AdminUserDetailsEnum.GROUP).getText();
               
      /*Notifications*/
        
        String mailone =myAdminUserDetails._text().values(AdminUserDetailsEnum.EMAIL_1).getText();
        String mailtwo =myAdminUserDetails._text().values(AdminUserDetailsEnum.EMAIL_2).getText();
        String textone =myAdminUserDetails._text().values(AdminUserDetailsEnum.TEXT_1).getText();
        String texttwo =myAdminUserDetails._text().values(AdminUserDetailsEnum.TEXT_2).getText();
        String phoneone =myAdminUserDetails._text().values(AdminUserDetailsEnum.PHONE_1).getText();
       
        String phonetwo =myAdminUserDetails._text().values(AdminUserDetailsEnum.PHONE_2).getText();
        String information =myAdminUserDetails._text().values(AdminUserDetailsEnum.INFORMATION).getText();
        String warning =myAdminUserDetails._text().values(AdminUserDetailsEnum.WARNING).getText();
        String critical =myAdminUserDetails._text().values(AdminUserDetailsEnum.CRITICAL).getText();
        
                
        /*click into My Account to compare*/
        
        myAccount._link().myAccount().click();
               
        /*compare Account info */
        myAccount._text().name().validate(firstname +" "+ middlename+" "+lastname+" "+suffix);
        myAccount._text().group().validate(group);
        myAccount._text().team().validate(team);
               
        /*compare Login info */
        myAccount._text().userName().validate(loginname);
        myAccount._text().locale().validate(locale);
        myAccount._text().measurement().validate(measurement);
        myAccount._text().fuelEfficiency().validate(fuelratio);
                     
        /*compare Red flag preferences */
        
        myAccount._text().redFlagInfo().validate(information);
        myAccount._text().redFlagWarn().validate(warning);
        myAccount._text().redFlagCritical().validate(critical);
         
        /* compare contact info*/
        myAccount._text().email1().validate(mailone);
        myAccount._text().email2().validate(mailtwo);
        myAccount._text().phone1().validate(phoneone);
        myAccount._text().phone2().validate(phonetwo);
        myAccount._text().textMessage1().validate(textone);
        myAccount._text().textMessage2().validate(texttwo);
                
       	}   
        
}     
        
	