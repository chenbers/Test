package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;



import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;
import com.inthinc.pro.selenium.pageObjects.PageLiveFleet;

public class LiveFleetTest extends WebRallyTest {
    String CORRECT_USERNAME = "dastardly";
    String CORRECT_USERNAME_TOP = "pitstop";
    String CORRECT_PASSWORD = "Muttley";
    PageLogin pl;
    PageLiveFleet plf;
    PageTeamDashboardStatistics ptds;

    @Before
    public void before(){
        pl = new PageLogin();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        ptds = new PageTeamDashboardStatistics();
        ptds._link().liveFleet().click();
        plf = new PageLiveFleet();
    }
    
    public String numAppend(int num){
        if(num < 10){
            return "0" + num;
        }
        else {
            return Integer.toString(num);
        }
    }
    
    @Test
    public void bookmarkEntryTest1228(){
        //set_test_case("TC1228");
        savePageLink();
        plf._link().logout().click();
        openSavedPage();
        plf.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        assertStringContains("app/liveFleet", ptds.getCurrentLocation());
    }
    
    @Test
    public void bookmarkEntryDifferentAccountTest1229(){
        //set_test_case("TC1229");
        
        savePageLink();
        plf._link().logout();
        plf.loginProcess(CORRECT_USERNAME_TOP, CORRECT_PASSWORD);
        openSavedPage();
        assertStringContains("app/liveFleet", ptds.getCurrentLocation());
    }
    
    @Test
    public void tablePropertiesTest1230(){
        //set_test_case("TC1230");
        //TODO Verify sorting.
        
        plf._link().sortDispatchByNumber().click();
        
        plf._link().sortDispatchByNumber().click();
        
        plf._link().sortDispatchByDriver().click();
        
        plf._link().sortDispatchByDriver().click();
        
        plf._link().sortDispatchByVehicle().click();
        
        plf._link().sortDispatchByVehicle().click();
        
        plf._link().sortDispatchByGroup().click();
        
        plf._link().sortDispatchByGroup().click();
        
    }
    
    @Test
    public void displayTest1231(){
        //set_test_case("TC1231");
        plf._select().numNearestVehicles().select(1);
        plf._button().locate().click();
        plf._link().vehicleByListPosition(5).assertPresence(true);
        plf._link().vehicleByListPosition(6).assertPresence(false);
        //TODO Verify 5 vehicles.
        plf._select().numNearestVehicles().select(2);
        plf._button().locate().click();
        plf._link().vehicleByListPosition(10).assertPresence(true);
        plf._link().vehicleByListPosition(11).assertPresence(false);
        //TODO Verify 10 vehicles.
        plf._select().numNearestVehicles().select(3);
        plf._button().locate().click();
        pause(1, "Wait for list to load.");
        plf._page().pageIndex().selectPageNumber(3).click();
        plf._link().vehicleByListPosition(25).assertPresence(true);
        plf._link().vehicleByListPosition(26).assertPresence(false);
        //TODO Verify 25 vehicles.
        plf._select().numNearestVehicles().select(4);
        plf._button().locate().click();
        pause(1, "Wait for list to load.");
        plf._page().pageIndex().forwardAll().click();
        plf._link().vehicleByListPosition(50).assertPresence(true);
        plf._link().vehicleByListPosition(51).assertPresence(false);
        //TODO Verify 50 vehicles.
        plf._select().numNearestVehicles().select(5);
        plf._button().locate().click();
        pause(2, "Wait for list to load");
        plf._page().pageIndex().forwardAll().click();
        plf._link().vehicleByListPosition(100).assertPresence(true);
        plf._link().vehicleByListPosition(101).assertPresence(false);
        //TODO Verify 100 vehicles.
    }
    
    @Test
    public void driverLinkTest1232(){
        //set_test_case("TC1232");
        
        plf._link().driverByListPosition(1).click();
        assertStringContains("/app/driver/53144", ptds.getCurrentLocation());
    }
    
    @Test
    public void teamLinkTest1236(){
        //set_test_case("TC1236");
        //TODO Ask about the team links.
        
        plf.getLinkByText("Test Group WR").click();
        assertStringContains("/app/dashboard/5202", ptds.getCurrentLocation());
    }
    
    @Test
    public void liveFleetUITest1237(){
        //set_test_case("TC1237");
        
        plf._link().sortDispatchByNumber().assertPresence(true);
        plf._link().sortDispatchByDriver().assertPresence(true);
        plf._link().sortDispatchByVehicle().assertPresence(true);
        plf._link().sortDispatchByGroup().assertPresence(true);
        //TODO Legend stuff.
        plf._textField().findAddress().assertPresence(true);
        plf._select().numNearestVehicles().assertPresence(true);
        plf._button().locate().assertPresence(true);
        //TODO Colored marker verify.
        
    }
    
    @Test
    public void vehicleLinkTest1239(){
        //set_test_case("TC1239");
        
        plf._link().vehicleByListPosition(1).click();
        assertStringContains("/app/vehicle/37870", ptds.getCurrentLocation());
    }
}
