package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;


import com.inthinc.pro.selenium.pageObjects.HOSRecordOfDutyStatus;
import com.inthinc.pro.selenium.pageObjects.PageHOSReports;


public class HOSReports extends WebRallyTest {
    
 
    private PageHOSReports myHOSReports;
    private String USERNAME = "tinaauto";
    private String PASSWORD = "password";

    @Before
    public void setupPage() {
        myHOSReports = new PageHOSReports();
        
    }

    @Test
    public void sevenDayRecordofDutyStatusReport() {
        set_test_case("TC5644");

        // 0.Login
        myHOSReports.loginProcess(USERNAME, PASSWORD);
        myHOSReports._link().hos().click();
        myHOSReports._link().hosReports().click();
        myHOSReports._dropDown().report().select(2);
        myHOSReports._dropDown().reportOn().select(2);
        myHOSReports._dropDown().reportOnDriver().select("US 7 Day");
        pause(2,"");
        
        HOSRecordOfDutyStatus report = myHOSReports._reports()._recordOfDutyStatusReport();   
        
        //1. Open Report in HTML
        myHOSReports._link().html().click();
        
        report._text().valueRuleSet(1).waitForElement();
        report._text().valueDriverName(1).validate("US 7  Day");
        report._text().valueRuleSet(1).validate("US 7 Day");
        report._text().valueCarrrier(1).validate("new automation");
        
        
     }
    
}