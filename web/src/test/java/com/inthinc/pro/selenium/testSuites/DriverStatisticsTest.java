package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEntries;
import com.inthinc.pro.selenium.pageObjects.PageAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;

public class DriverStatisticsTest extends WebRallyTest {
    String CORRECT_USERNAME = "dastardly";
    String CORRECT_USERNAME_TOP = "pitstop";
    String CORRECT_PASSWORD = "Muttley";
    PageLogin pl;
    
    //TODO TC1698 (requires new window)
    //TODO TC4333, 4334 (requires PDF)
    //TODO Finish TC1695 (requires email)
    //TODO Finish TC4586 (requires mid-drive driver switch)
    //TODO Finish TC4625, 5515, 5516 (requires data)

    @Before
    public void before(){
        pl = new PageLogin();
    }
    
    @Test
    public void emailTest1695(){
        //set_test_case("TC1695");
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        
        //TODO Update when tools and email is on PageTeamDashboardStatistics page.
//        ptds._button().tools().click();
//        ptds._button().emailReport().click();
//        ptds._popUp().emailReport()._textField().emailAddresses().clear();
//        ptds._popUp().emailReport()._textField().emailAddresses().type("fail@a.com");
        //TODO Update when email can be checked.
    }
    
    @Test
    public void createTest4506(){
        //set_test_case("TC4506");
        pl.loginProcess(CORRECT_USERNAME_TOP, CORRECT_PASSWORD);
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        pau._link().adminAddUser().click();
        PageAddEditUser paeu = new PageAddEditUser();
        paeu._textField().personFields(AdminUsersEntries.FIRST_NAME).type("Alpha");
        paeu._textField().personFields(AdminUsersEntries.LAST_NAME).type("Betical");
        paeu._dropDown().regularDropDowns(AdminUsersEntries.TIME_ZONE).selectPartMatch("US/Mount");
        paeu._dropDown().driverTeam().select("Top - Test Group WR");
        paeu._checkBox().userInformation().uncheck();
        paeu._button().saveTop().click();
        ped._navTree().groups().click("Test Group WR");
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds.getLinkByText("Alpha Betical").assertPresence(true);
        deleteUser("Alpha Betical");
        
        //TODO test creation of vehicle, zone, zone alert, and red flag.
    }
    
    @Test
    public void editTest4507(){
        //set_test_case("TC4507");
        pl.loginProcess(CORRECT_USERNAME_TOP, CORRECT_PASSWORD);
        createDriver("Alma", "Mater", "Top - Test Group WR");
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        pau.getLinkByText("Alma Mater").click();
        PageAdminUserDetails paud = new PageAdminUserDetails();
        paud._button().edit().click();
        PageAddEditUser paeu = new PageAddEditUser();
        paeu._textField().personFields(AdminUsersEntries.FIRST_NAME).clear().type("Blooregard");
        //Middle Name fix will be in the next build.
        //paeu._textField().personFields(AdminUsersEntries.MIDDLE_NAME).type("Q");
        paeu._textField().personFields(AdminUsersEntries.LAST_NAME).clear().type("Kazoo");
        paeu._dropDown().regularDropDowns(AdminUsersEntries.GENDER).select("Male");
        paeu._button().saveBottom().click();
        //TODO test edit of vehicle.
        //TODO get driver information to check on.
        paud._navTree().groups().click("Test Group WR");
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds.getLinkByText("Blooregard Kazoo").assertPresence(true);
        ptds.getLinkByText("Alma Mater").assertPresence(false);
        deleteUser("Blooregard Kazoo");
    }
    
    @Test
    public void deleteTest4519(){
        //set_test_case("TC4519");
        
        pl.loginProcess(CORRECT_USERNAME_TOP, CORRECT_PASSWORD);
        createDriver("Alto", "Soprano", "Top - Test Group WR");
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        pau.getLinkByText("Alto Soprano").click();
        PageAdminUserDetails paud = new PageAdminUserDetails();
        paud._button().delete().click();
        paud._popUp().deleteUser()._button().delete().click();
        paud._navTree().groups().click("Test Group WR");
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds.getLinkByText("Alto Soprano").assertPresence(false);
        
        //TODO test deletion of vehicle, zone, zone alert, and red flag.
    }

    @Test
    public void bookmarkPageTest4578() {
        set_test_case("TC4578");

        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        String team = ptds._text().teamName().getText();
        savePageLink();
        ptds._link().logout().click();
        openSavedPage();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        assertStringContains("dashboard", ptds.getCurrentLocation());
        ptds._text().teamName().assertEquals(team);
        
    }
    
    @Test
    public void switchTeamTest4582(){
        set_test_case("TC4582");
    
        pl.loginProcess(CORRECT_USERNAME_TOP, CORRECT_PASSWORD);
        createDriver("Swappy", "McGee", "Top - Test Group WR");
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        pau.getLinkByText("Swappy McGee").click();
        PageAdminUserDetails paud = new PageAdminUserDetails();
        paud._button().edit().click();
        PageAddEditUser paeu = new PageAddEditUser();
        paeu._dropDown().driverTeam().select("Top - Test Group RW");
        paeu._button().saveBottom().click();
        paud._navTree().groups().click("Test Group WR");
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds.getLinkByText("Swappy McGee").assertPresence(false);
        ptds._navTree().groups().click("Test Group RW");
        ptds.getLinkByText("Swappy McGee").assertPresence(true);
        deleteUser("Swappy McGee");
    }
    
    //@Test
    public void switchDriverTest4586(){
        //set_test_case("TC4586");
        //TODO create method, requires vehicle
    }
    
    //@Test
    public void seatBeltTest4625(){
        //set_test_case("TC4625");
        //TODO create method
    }
    
    //@Test
    public void totalTripsTest5515(){
        //set_test_case("TC5515");
        //TODO create method
    }
    
    //@Test
    public void totalTripDurationTest5516(){
        //set_test_case("TC5516");
        //TODO create method
    }
    
    public void createDriver(String first, String last, String team){
        
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        pau._link().adminAddUser().click();
        PageAddEditUser paeu = new PageAddEditUser();
        paeu._textField().personFields(AdminUsersEntries.FIRST_NAME).type(first);
        paeu._textField().personFields(AdminUsersEntries.LAST_NAME).type(last);
        paeu._dropDown().regularDropDowns(AdminUsersEntries.TIME_ZONE).selectPartMatch("US/Mount");
        paeu._dropDown().driverTeam().select(team);
        paeu._checkBox().userInformation().uncheck();
        paeu._button().saveTop().click();
        
    }
    
    public void deleteUser(String fullName){
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        pau.getLinkByText(fullName).click();
        PageAdminUserDetails paud = new PageAdminUserDetails();
        paud._button().delete().click();
        paud._popUp().deleteUser()._button().delete().click();
    }

}
