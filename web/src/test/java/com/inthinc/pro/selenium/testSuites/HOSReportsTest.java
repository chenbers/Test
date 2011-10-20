package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.enums.LoginCapabilities;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.selenium.pageObjects.HOSRecordOfDutyStatus;
import com.inthinc.pro.selenium.pageObjects.PageHOSReports;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;


public class HOSReportsTest extends WebRallyTest {
    
    private PageHOSReports myHOSReports;
    private PageAdminUsers myAdminUsers;
    private PageAdminUserDetails myAdminUserDetails;
    private AutomationUser user;
    private AutomationUser driver;
    
    
    @Before
    public void setupPage() {
        user = users.getOneBy( LoginCapabilities.RoleAdmin, LoginCapabilities.RoleHOS);
        driver = users.getOneBy(LoginCapabilities.IsDriver, LoginCapabilities.HasVehicle, LoginCapabilities.HasWaySmart, LoginCapabilities.RoleHOS);
        myHOSReports = new PageHOSReports();
    }   
       

    @Test
    @Ignore
    public void HOSRecordofDutyStatusReport() {
        set_test_case("TC5644");
        
    
        // 0.Login
    
        myHOSReports.loginProcess(user);
                    
      //1. Go to user and grab team name; then go to organization page, find team address and top level address. Save for later comparison.
        myAdminUsers._link().admin().click();
        myAdminUsers._textField().search().type(driver.getUsername());
        myAdminUsers._button().search().click();
       // myAdminUsers._link().
        
              
        
        //2. Click on HOS Reports  
        
        myHOSReports._link().hos().click();
        myHOSReports._link().hosReports().click();
        myHOSReports._dropDown().report().select(2);
        myHOSReports._dropDown().reportOn().select(2);
        myHOSReports._dropDown().reportOnDriver().select(driver.getFullName());
        pause(2,"");
        
           
        HOSRecordOfDutyStatus report = myHOSReports._reports()._recordOfDutyStatusReport();
              
               
        //3. Open Report in HTML
        myHOSReports._link().html().click();
        //report._text().valueDate(1).validate("");
        report._text().valueStartOdometer(1).validate();//TODO: need to validate something here.
        report._text().valueRuleSet(1).waitForElement();
        report._text().valueDriverName(1).validate(driver.getFullName()); //Changed this to driver name.
        pause(2,"");
        report._text().valueRuleSet(1).validate(driver.getFullName());//TODO: This should be Ruleset name, not user name
        report._text().valueCarrrier(1).validate("Tina's Division");// TODO: Tina: This needs to be updated so it is not hardcoded
        report._text().valueMainOffice(1).validate("55555 W Lake Park BLVD, West Valley City, UT"); 
        report._text().valueHomeTerminal(1).validate("946 W 630 S, Pleasant Grove, UT");// TODO: Tina: This needs to be updated so it is not hardcoded
        
        
     }
    
}