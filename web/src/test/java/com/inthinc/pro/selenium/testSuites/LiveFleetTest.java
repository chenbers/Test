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
        
        plf._link().sortByNumber().click();
        
        plf._link().sortByNumber().click();
        
        plf._link().sortByDriver().click();
        
        plf._link().sortByDriver().click();
        
        plf._link().sortByVehicle().click();
        
        plf._link().sortByVehicle().click();
        
        plf._link().sortByGroup().click();
        
        plf._link().sortByGroup().click();
        
    }
    
    @Test
    public void displayTest1231(){
        //set_test_case("TC1231");
        
        
        plf._dropDown().numNearestVehicles().select("5");
        plf._button().locate().click();
        plf._text().entryPositionByPosition().assertPresence(5, true);
        plf._text().entryPositionByPosition().assertEquals(5, "5");
        plf._text().entryPositionByPosition().assertPresence(6, false);
        //TODO Verify 5 vehicles.
        
        plf._dropDown().numNearestVehicles().select("10");
        plf._button().locate().click();
        plf._text().entryPositionByPosition().assertPresence(10, true);
        plf._text().entryPositionByPosition().assertEquals(10, "10");
        plf._page().pageIndex().selectPageNumber(2).assertPresence(false);
        //TODO Verify 10 vehicles.
        
        plf._dropDown().numNearestVehicles().select("25");
        plf._button().locate().click();
        plf._page().pageIndex().selectPageNumber(3).waitForElement();
        plf._page().pageIndex().selectPageNumber(4).assertPresence(false);
        plf._page().pageIndex().selectPageNumber(3).assertPresence(true);
        plf._page().pageIndex().selectPageNumber(3).click();
        plf._text().entryPositionByPosition().assertPresence(25, true);
        plf._text().entryPositionByPosition().assertPresence(26, false);
        //TODO Verify 25 vehicles.
        
        plf._dropDown().numNearestVehicles().select("50");
        plf._button().locate().click();
        plf._page().pageIndex().selectPageNumber(5).waitForElement();
        plf._page().pageIndex().selectPageNumber(5).assertPresence(true);
        plf._page().pageIndex().selectPageNumber(6).assertPresence(false);
        plf._page().pageIndex().forwardAll().click();
        plf._text().entryPositionByPosition().assertEquals(50, "50");
        //TODO Verify 50 vehicles.
        
        plf._dropDown().numNearestVehicles().select("100");
        plf._button().locate().click();
        plf._page().pageIndex().selectPageNumber(10).waitForElement();
        plf._page().pageIndex().selectPageNumber(10).assertPresence(true);
        plf._page().pageIndex().selectPageNumber(11).assertPresence(false);
        plf._page().pageIndex().forwardAll().click();
        plf._text().entryPositionByPosition().assertEquals(100, "100");
        //TODO Verify 100 vehicles.
    }
    
    @Test
    public void driverLinkTest1232(){
        //set_test_case("TC1232");
        
        plf._link().entryDriverByPosition().click(1);
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
        
        plf._link().sortByNumber().assertPresence(true);
        plf._link().sortByDriver().assertPresence(true);
        plf._link().sortByVehicle().assertPresence(true);
        plf._link().sortByGroup().assertPresence(true);
        //TODO Legend stuff.
        plf._textField().findAddress().assertPresence(true);
        plf._dropDown().numNearestVehicles().assertPresence(true);
        plf._button().locate().assertPresence(true);
        //TODO Colored marker verify.
        
    }
    
    @Test
    public void vehicleLinkTest1239(){
        //set_test_case("TC1239");
        
        plf._link().entryVehicleByPosition().click(1);
        assertStringContains("/app/vehicle/37870", ptds.getCurrentLocation());
    }
}
