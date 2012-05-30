package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageEnums.AdminTables.UserColumns;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;

@Ignore
public class DriverStatisticsTest extends WebRallyTest {
    private static String CORRECT_USERNAME;
    private static String CORRECT_ADMIN_USERNAME;
    private static String CORRECT_PASSWORD;
    private static String CORRECT_ADMIN_PASSWORD;
    PageLogin pl;
    
    //TODO TC1698 (requires new window)
    //TODO TC4333, 4334 (requires PDF)
    //TODO Finish TC1695 (requires email)
    //TODO Finish TC4586 (requires mid-drive driver switch)
    //TODO Finish TC4625, 5515, 5516 (requires data)

//    @BeforeClass
//    public static void beforeClass() {
//        AutomationUser login = AutomationUsers.getUsers().getOneBy(LoginCapability.NoteTesterData);
//        CORRECT_USERNAME = login.getUsername();
//        CORRECT_PASSWORD = login.getPassword();
//        AutomationUser admin = AutomationUsers.getUsers().getOneBy(LoginCapability.RoleAdmin);
//        CORRECT_ADMIN_USERNAME = admin.getUsername();
//        CORRECT_ADMIN_PASSWORD = admin.getPassword();
//    }
    
    @Before
    public void before(){
        pl = new PageLogin();
    }
    
    @Ignore
    @Test
    public void emailTest1695(){
        //set_test_case("TC1695");//TODO: when test is complete uncomment this line and remove @Ignore
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDriverStatistics ptds = new PageTeamDriverStatistics();
        
        //TODO Update when tools and email is on PageTeamDashboardStatistics page.
//        ptds._button().tools().click();
//        ptds._button().emailReport().click();
//        ptds._popUp().emailReport()._textField().emailAddresses().clear();
//        ptds._popUp().emailReport()._textField().emailAddresses().type("fail@a.com");
        //TODO Update when email can be checked.
    }
    
    @Ignore
    @Test
    public void createTest4506(){
        //set_test_case("TC4506");//TODO: when test is complete uncomment this line and remove @Ignore
        pl.loginProcess(CORRECT_ADMIN_USERNAME, CORRECT_ADMIN_PASSWORD);
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        pau._link().adminAddUser().click();
        PageAdminAddEditUser paeu = new PageAdminAddEditUser();
        paeu._textField().personFields(UserColumns.FIRST_NAME).type("Alpha");
        paeu._textField().personFields(UserColumns.LAST_NAME).type("Betical");
        paeu._dropDown().regularDropDowns(UserColumns.TIME_ZONE).selectTheOptionContaining("US/Mount", 1);
        paeu._dropDown().driverTeam().select("Top - Test Group WR");
        paeu._checkBox().userInformation().uncheck();
        paeu._button().saveTop().click();
        ped._navTree().groups().clickGroup("Test Group WR");
        PageTeamDriverStatistics ptds = new PageTeamDriverStatistics();
        ptds.getLinkByText("Alpha Betical").assertPresence(true);
        deleteUser("Alpha Betical");
        
        //TODO test creation of vehicle, zone, zone alert, and red flag.
    }
    
    @Ignore
    @Test
    public void editTest4507(){
        //set_test_case("TC4507");//TODO: when test is complete uncomment this line and remove @Ignore
        pl.loginProcess(CORRECT_ADMIN_USERNAME, CORRECT_ADMIN_PASSWORD);
        createDriver("Alma", "Mater", "Top - Test Group WR");
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        pau.getLinkByText("Alma Mater").click();
        PageAdminUserDetails paud = new PageAdminUserDetails();
        paud._button().edit().click();
        PageAdminAddEditUser paeu = new PageAdminAddEditUser();
        paeu._textField().personFields(UserColumns.FIRST_NAME).clear().type("Blooregard");
        //Middle Name fix will be in the next build.
        //paeu._textField().personFields(AdminUsersEntries.MIDDLE_NAME).type("Q");
        paeu._textField().personFields(UserColumns.LAST_NAME).clear().type("Kazoo");
        paeu._dropDown().regularDropDowns(UserColumns.GENDER).select("Male");
        paeu._button().saveBottom().click();
        //TODO test edit of vehicle.
        //TODO get driver information to check on.
        paud._navTree().groups().clickGroup("Test Group WR");
        PageTeamDriverStatistics ptds = new PageTeamDriverStatistics();
        ptds.getLinkByText("Blooregard Kazoo").assertPresence(true);
        ptds.getLinkByText("Alma Mater").assertPresence(false);
        deleteUser("Blooregard Kazoo");
    }
    
    @Ignore
    @Test
    public void deleteTest4519(){
        //set_test_case("TC4519");//TODO: when test is complete uncomment this line and remove @Ignore
        
        pl.loginProcess(CORRECT_ADMIN_USERNAME, CORRECT_ADMIN_PASSWORD);
        createDriver("Alto", "Soprano", "Top - Test Group WR");
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        pau.getLinkByText("Alto Soprano").click();
        PageAdminUserDetails paud = new PageAdminUserDetails();
        paud._button().delete().click();
        paud._popUp().deleteUser()._button().delete().click();
        paud._navTree().groups().clickGroup("Test Group WR");
        PageTeamDriverStatistics ptds = new PageTeamDriverStatistics();
        ptds.getLinkByText("Alto Soprano").assertPresence(false);
        
        //TODO test deletion of vehicle, zone, zone alert, and red flag.
    }

    @Test
    public void bookmarkPageTest4578() {
        set_test_case("TC4578");

        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        
        PageTeamDriverStatistics ptds = new PageTeamDriverStatistics();
        String team = ptds._text().teamName().getText();//TODO: this is failing so the var team is getting set to NULL
        savePageLink();
        ptds._link().logout().click();
        openSavedPage();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        assertStringContains("dashboard", ptds.getCurrentLocation());
        ptds._text().teamName().assertEquals(team);
        
    }
    
    @Test
    public void switchTeamTest4582(){
        //TODO: jwimmer: verify that this test is working

        set_test_case("TC4582");
    
        pl.loginProcess(CORRECT_ADMIN_USERNAME, CORRECT_ADMIN_PASSWORD);
        createDriver("Swappy", "McGee", "Top - Test Group WR");
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        
        //search for swappy to ensure that user is ON the page
        pau._textField().search().type("Swappy McGee");
        pau._button().search().click();
        
        pau.getLinkByText("Swappy McGee").click();
        PageAdminUserDetails paud = new PageAdminUserDetails();
        paud._button().edit().click();
        PageAdminAddEditUser paeu = new PageAdminAddEditUser();
        paeu._dropDown().driverTeam().select("Top - Test Group RW");
        paeu._button().saveBottom().click();
        paud._navTree().groups().clickGroup("Test Group WR");
        PageTeamDriverStatistics ptds = new PageTeamDriverStatistics();
        ptds.getLinkByText("Swappy McGee").assertPresence(false);
        ptds._navTree().groups().clickGroup("Test Group RW");
        ptds.getLinkByText("Swappy McGee").assertPresence(true);
        deleteUser("Swappy McGee");
    }
    
    @Ignore
    @Test
    public void switchDriverTest4586(){
        //set_test_case("TC4586");//TODO: when test is complete uncomment this line and remove @Ignore
        //TODO create method, requires vehicle
    }
    
    @Ignore
    @Test
    public void seatBeltTest4625(){
        //set_test_case("TC4625");//TODO: when test is complete uncomment this line and remove @Ignore
        //TODO create method
    }
    
    @Ignore
    @Test
    public void totalTripsTest5515(){
        //set_test_case("TC5515");//TODO: when test is complete uncomment this line and remove @Ignore
        //TODO create method
    }
    
    @Ignore
    @Test
    public void totalTripDurationTest5516(){
        //set_test_case("TC5516");//TODO: when test is complete uncomment this line and remove @Ignore
        //TODO create method
    }
    
    private void createDriver(String first, String last, String team){
        
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        pau._link().adminAddUser().click();
        PageAdminAddEditUser paeu = new PageAdminAddEditUser();
        paeu._textField().personFields(UserColumns.FIRST_NAME).type(first);
        paeu._textField().personFields(UserColumns.LAST_NAME).type(last);
        paeu._dropDown().regularDropDowns(UserColumns.TIME_ZONE).selectTheOptionContaining("US/Mount", 1);
        paeu._dropDown().driverTeam().select(team);
        paeu._checkBox().userInformation().uncheck();
        paeu._button().saveTop().click();
        
    }
    
    private void deleteUser(String fullName){ 
        PageExecutiveDashboard ped = new PageExecutiveDashboard();
        ped._link().admin().click();
        PageAdminUsers pau = new PageAdminUsers();
        //TODO: jwimmer: verify that searching for fullname before clicking delete allows this helper method to work ALL the time
        pau._textField().search().type(fullName);
        pau._button().search().click();
        pau.getLinkByText(fullName).click();
        PageAdminUserDetails paud = new PageAdminUserDetails();
        paud._button().delete().click();
        paud._popUp().deleteUser()._button().delete().click();
    }

}
