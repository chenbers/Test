package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.enums.DriverCapabilities;
import com.inthinc.pro.automation.enums.LoginCapabilities;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.selenium.pageObjects.HOSRecordOfDutyStatus;
import com.inthinc.pro.selenium.pageObjects.PageHOSReports;

@Ignore
public class HOSReportsTest extends WebRallyTest {
    
    private PageHOSReports myHOSReports;
    private AutomationUser user;

    @Before
    public void setupPage() {
        user = users.getOneBy(LoginCapabilities.HasWaySmart, LoginCapabilities.RoleHOS, DriverCapabilities.RuleSet_US7Day);
        myHOSReports = new PageHOSReports();
        
    }

    @Test
    public void sevenDayRecordOfDutyStatusReport() {
        set_test_case("TC5644");

        // 0.Login
        myHOSReports.loginProcess(user);
        myHOSReports._link().hos().click();
        myHOSReports._link().hosReports().click();
        myHOSReports._dropDown().report().select(2);
        myHOSReports._dropDown().reportOn().select(2);
        myHOSReports._dropDown().reportOnDriver().select(user.getFullName());
        pause(2,"");
        
        HOSRecordOfDutyStatus report = myHOSReports._reports()._recordOfDutyStatusReport();   
        
        //1. Open Report in HTML
        myHOSReports._link().html().click();
        
        report._text().valueRuleSet(1).waitForElement();
        report._text().valueDriverName(1).validate(user.getFullName());
        pause(2,"");
        report._text().valueRuleSet(1).validate(user.getFullName());
        report._text().valueCarrrier(1).validate("Tina's Division");// TODO: Tina: This needs to be updated so it is not hardcoded
        //report._text().valueDate(1).validate("");
        report._text().valueHomeTerminal(1).validate("946 W 630 S, Pleasant Grove, UT");// TODO: Tina: This needs to be updated so it is not hardcoded
        
        
     }
    
}