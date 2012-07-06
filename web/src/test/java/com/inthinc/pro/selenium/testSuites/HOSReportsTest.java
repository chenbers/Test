package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageHOSAddEditDriverLogs;
import com.inthinc.pro.selenium.pageObjects.PageHOSAddEditFuelStops;
import com.inthinc.pro.selenium.pageObjects.PageHOSDriverLogs;
import com.inthinc.pro.selenium.pageObjects.PageHOSFuelStops;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.steps.LoginSteps;

@UsingSteps(instances={LoginSteps.class})
@PageObjects(list={PageLogin.class, PageExecutiveDashboard.class, PageAdminUsers.class, PageHOSDriverLogs.class, PageHOSFuelStops.class, PageHOSAddEditFuelStops.class,
        PageHOSAddEditDriverLogs.class})
@StoryPath(path="HOSReports.story")
public class HOSReportsTest extends WebStories {
    
    @Test
    public void test(){}

}

//@Ignore
//public class HOSReportsTest extends WebRallyTest {
//TODO: We can add this test case as well as the ones it depends on  back in once we have the other test cases working
//    private PageHOSReports myHOSReports;
//    private PageAdminUsers myAdminUsers;
//    private PageAdminOrganization myAdminOrg;
//    private PageAdminUserDetails myAdminUserDetails;
//    private AutomationUser user;
//    private AutomationUser driver;
//    
//    
//    @Before
//    public void setupPage() {
//        user = AutomationUsers.getUsers().getOneBy( LoginCapability.RoleAdmin, LoginCapability.RoleHOS);
//        driver = AutomationUsers.getUsers().getOneBy(LoginCapability.IsDriver, LoginCapability.HasVehicle, LoginCapability.HasWaySmart, LoginCapability.RoleHOS);
//        myHOSReports = new PageHOSReports();
//    }   
//       
//
//    @Test
//    @Ignore
//    public void HOSRecordofDutyStatusReport() {
//        set_test_case("TC5644");
//        
//    
//      // 0.Login
//    
//        myHOSReports.loginProcess(user);
//                    
//      //1. Go to user and grab org division address;  Save for later comparison.
//        
//        myAdminUsers._link().admin().click();
//        myAdminUsers._link().adminOrganization().click();
//        myAdminOrg.getFleet().getDivision(1).text().click();
//        myAdminOrg._text().labelAddressOne().getText();
//        String valueAddressOne = myAdminOrg._text().valueAddressOne().getText();
//        myAdminOrg._text().labelCity().getText();
//        String valueCity = myAdminOrg._text().valueCity().getText();
//        myAdminOrg._text().labelState().getText();
//        String valueState = myAdminOrg._text().valueState().getText();
//        myAdminOrg._text().labelZipCode().getText();
//        String valueZipCode = myAdminOrg._text().valueZipCode().getText();
//        
//        
//      //2. Search for driver user name.
//        
//        myAdminUsers._textField().search().type(driver.getUsername());
//        myAdminUsers._button().search().click();
//        myAdminUsers._link().tableEntryUserFullName().row(1).click();
//        
//        
//      // 3. grab team name and address; Save for later comparison. 
//        
//        String valueTeam = myAdminUserDetails._text().values(UserColumns.TEAM).getText();
//       
//        myAdminUsers._link().adminOrganization().click();
//        myAdminOrg.getFleet().getDivision(1).arrow().click();
//        myAdminOrg.getFleet().getTeam(valueTeam);
//        //TODO: Waiting on new page object method so I can get the team from the organization page.
//        
//            
//              
//        
//        //4. Click on HOS Reports  
//        
//        myHOSReports._link().hos().click();
//        myHOSReports._link().hosReports().click();
//        myHOSReports._dropDown().report().select(2);
//        myHOSReports._dropDown().reportOn().select(2);
//        myHOSReports._dropDown().reportOnDriver().select(driver.getFullName());
//        pause(2,"");
//        
//           
//        HOSRecordOfDutyStatus report = myHOSReports._reports()._recordOfDutyStatusReport();
//              
//               
//        //5. Open Report in HTML
//        myHOSReports._link().html().click();
//        //report._text().valueDate(1).validate("");
//        report._text().valueStartOdometer(1).validate();//TODO: need to validate something here.
//        report._text().valueRuleSet(1).waitForElement();
//        report._text().valueDriverName(1).validate(driver.getFullName()); //Changed this to driver name.
//        pause(2,"");
//        report._text().valueRuleSet(1).validate(driver.getFullName());//TODO: This should be Ruleset name, not user name
//        report._text().valueCarrrier(1).validate("Tina's Division");// TODO: Tina: This needs to be updated so it is not hardcoded
//        report._text().valueMainOffice(1).validate("55555 W Lake Park BLVD, West Valley City, UT"); 
//        report._text().valueHomeTerminal(1).validate("946 W 630 S, Pleasant Grove, UT");// TODO: Tina: This needs to be updated so it is not hardcoded
//        
//        
//     }
    
//}