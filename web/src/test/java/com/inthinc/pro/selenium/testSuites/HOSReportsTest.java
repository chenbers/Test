package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.enums.LoginCapabilities;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.selenium.pageObjects.HOSRecordOfDutyStatus;
import com.inthinc.pro.selenium.pageObjects.PageHOSReports;


public class HOSReportsTest extends WebRallyTest {
    
    private PageHOSReports myHOSReports;
    private AutomationUser login;


    
    @Before
    public void setupPage() {
        login = users.getOneBy(LoginCapabilities.IsDriver, LoginCapabilities.HasVehicle, LoginCapabilities.RoleAdmin, LoginCapabilities.HasWaySmart, LoginCapabilities.RoleHOS);
        myHOSReports = new PageHOSReports();
    }   
       

    @Test
    public void HOSRecordofDutyStatusReport() {
        set_test_case("TC5644");
        

        // 0.Login
        
        myHOSReports.loginProcess(login);
        myHOSReports._link().hos().click();
        myHOSReports._link().hosReports().click();
        myHOSReports._dropDown().report().select(2);
        myHOSReports._dropDown().reportOn().select(2);
        myHOSReports._dropDown().reportOnDriver().select("US 7 Day");
        pause(2,"");
        
        HOSRecordOfDutyStatus report = myHOSReports._reports()._recordOfDutyStatusReport();   
        
        //1. Open Report in HTML
        myHOSReports._link().html().click();
        //report._text().valueDate(1).validate("");
        report._text().valueRuleSet(1).waitForElement();
        report._text().valueDriverName(1).validate("US 7  Day");
        pause(2,"");
        report._text().valueRuleSet(1).validate("US 7 Day");
        report._text().valueCarrrier(1).validate("Tina's Division");
        report._text().valueMainOffice(1).validate("55555 W Lake Park BLVD, West Valley City, UT");
        report._text().valueHomeTerminal(1).validate("946 W 630 S, Pleasant Grove, UT");
        
        
     }
    
}