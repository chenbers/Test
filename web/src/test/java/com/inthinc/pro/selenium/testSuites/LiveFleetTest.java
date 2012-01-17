package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageLiveFleet;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;

//@Ignore
public class LiveFleetTest extends WebRallyTest {
    PageLogin pl;
    PageLiveFleet plf;
    PageTeamDashboardStatistics ptds;
    private AutomationUser user;
    private AutomationUser userTop;

    @Before
    public void before(){
        user = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive, LoginCapability.TeamLevelLogin, LoginCapability.RoleAdmin);
        userTop = AutomationUsers.getUsers().getOneBy(user.getAccount(), LoginCapability.FleetLevelLogin, LoginCapability.RoleAdmin);
        pl = new PageLogin();
        pl.loginProcess(user);
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
        set_test_case("TC1228");
        savePageLink();
        plf._link().logout().click();
        openSavedPage();
        plf.loginProcess(user);
        assertStringContains("app/liveFleet", ptds.getCurrentLocation());
    }
    
    @Test
    public void bookmarkEntryDifferentAccountTest1229(){
        set_test_case("TC1229");
        
        savePageLink();
        plf._link().logout();
        plf.loginProcess(userTop);
        openSavedPage();
        assertStringContains("app/liveFleet", ptds.getCurrentLocation());
        plf._link().entryFleetLegend().row(2).assertPresence(true);
    }
    
    @Test
    public void tablePropertiesTest1230(){
        set_test_case("TC1230");
        
        String currentText;
        int currentPosition;
        
        currentText = "";
        currentPosition = 0;
        if(plf._text().entryPositionByPosition().row(1).isPresent()){
            currentPosition = Integer.parseInt(plf._text().entryPositionByPosition().row(1).getText());
        }
        for(int index = 2; index < 10; index++){
            if(plf._text().entryPositionByPosition().row(index).isPresent()){
                int newPosition = Integer.parseInt(plf._text().entryPositionByPosition().row(index).getText());
                if(currentPosition > newPosition){
                    addError("Positions out of order", ErrorLevel.FAIL);
                }
                currentPosition = newPosition;
            }
            else{
                break;
            }
        }
        
        plf._link().sortByNumber().click();
        plf._link().sortByNumber().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        currentPosition = 0;
        if(plf._text().entryPositionByPosition().row(1).isPresent()){
            currentPosition = Integer.parseInt(plf._text().entryPositionByPosition().row(1).getText());
        }
        for(int index = 2; index < 10; index++){
            if(plf._text().entryPositionByPosition().row(index).isPresent()){
                int newPosition = Integer.parseInt(plf._text().entryPositionByPosition().row(index).getText());
                if(currentPosition < newPosition){
                    addError("Positions out of order", ErrorLevel.FAIL);
                }
                currentPosition = newPosition;
            }
            else{
                break;
            }
        }
        
        plf._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(plf._link().entryDriverByPosition().row(1).isPresent()){
            currentText = plf._link().entryDriverByPosition().row(1).getText();
        }
        for(int index = 2; index < 10; index++){
            if(plf._link().entryDriverByPosition().row(index).isPresent()){
                String newText = plf._link().entryDriverByPosition().row(index).getText();
                if(currentText.compareToIgnoreCase(newText) > 0){
                    addError("Drivers out of order", ErrorLevel.FAIL);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        plf._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(plf._link().entryDriverByPosition().row(1).isPresent()){
            currentText = plf._link().entryDriverByPosition().row(1).getText();
        }
        for(int index = 2; index < 10; index++){
            if(plf._link().entryDriverByPosition().row(index).isPresent()){
                String newText = plf._link().entryDriverByPosition().row(index).getText();
                if(currentText.compareToIgnoreCase(newText) < 0){
                    addError("Drivers out of order", ErrorLevel.FAIL);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        plf._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(plf._link().entryGroupIconByPosition().row(1).isPresent()){
            currentText = plf._link().entryGroupIconByPosition().row(1).getText();
        }
        for(int index = 2; index < 10; index++){
            if(plf._link().entryGroupIconByPosition().row(index).isPresent()){
                String newText = plf._link().entryGroupIconByPosition().row(index).getText();
                if(currentText.compareToIgnoreCase(newText) > 0){
                    addError("Groups out of order", ErrorLevel.FAIL);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        plf._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(plf._link().entryGroupIconByPosition().row(1).isPresent()){
            currentText = plf._link().entryGroupIconByPosition().row(1).getText();
        }
        for(int index = 2; index < 10; index++){
            if(plf._link().entryGroupIconByPosition().row(index).isPresent()){
                String newText = plf._link().entryGroupIconByPosition().row(index).getText();
                if(currentText.compareToIgnoreCase(newText) < 0){
                    addError("Groups out of order", ErrorLevel.FAIL);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        
        plf._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(plf._link().entryVehicleByPosition().row(1).isPresent()){
            currentText = plf._link().entryVehicleByPosition().row(1).getText();
        }
        for(int index = 2; index < 10; index++){
            if(plf._link().entryVehicleByPosition().row(index).isPresent()){
                String newText = plf._link().entryVehicleByPosition().row(index).getText();
                if(currentText.compareToIgnoreCase(newText) > 0){
                    addError("Vehicles out of order", ErrorLevel.FAIL);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        plf._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(plf._link().entryVehicleByPosition().row(1).isPresent()){
            currentText = plf._link().entryVehicleByPosition().row(1).getText();
        }
        for(int index = 2; index < 10; index++){
            if(plf._link().entryVehicleByPosition().row(index).isPresent()){
                String newText = plf._link().entryVehicleByPosition().row(index).getText();
                if(currentText.compareToIgnoreCase(newText) < 0){
                    addError("Vehicles out of order", ErrorLevel.FAIL);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
    }
    
    @Test
    public void displayTest1231(){
        set_test_case("TC1231");
        String goal;
        
        goal = plf._link().entryVehicleByPosition().row(1).getText();
        plf._link().entryGroupIconByPosition().row(1).click();
        assertStringContains(goal, plf._link().valueMapBubbleVehicleName().getText());
        
        plf._dropDown().numNearestVehicles().select("5");
        plf._button().locate().click();
        
        plf._text().entryPositionByPosition().row(5).assertPresence(true);
        plf._text().entryPositionByPosition().row(5).assertEquals("5");
        plf._text().entryPositionByPosition().row(6).assertPresence(false);

        goal = plf._link().entryVehicleByPosition().row(5).getText();
        plf._link().entryGroupIconByPosition().row(5).click();
        pause(5, "Wait for bubble to load.");
        assertStringContains(goal, plf._link().valueMapBubbleVehicleName().getText());
        
        plf._dropDown().numNearestVehicles().select("10");
        plf._button().locate().click();
        plf._text().entryPositionByPosition().row(10).assertPresence(true);
        plf._text().entryPositionByPosition().row(10).assertEquals("10");
        plf._page().pageIndex().selectPageNumber(2).assertPresence(false);

        goal = plf._link().entryVehicleByPosition().row(10).getText();
        plf._link().entryGroupIconByPosition().row(10).click();
        pause(5, "Wait for bubble to load.");
        assertStringContains(goal, plf._link().valueMapBubbleVehicleName().getText());
        
        plf._dropDown().numNearestVehicles().select("25");
        plf._button().locate().click();
        plf._page().pageIndex().selectPageNumber(3).waitForElement();
        plf._page().pageIndex().selectPageNumber(4).assertPresence(false);
        plf._page().pageIndex().selectPageNumber(3).assertPresence(true);
        plf._page().pageIndex().selectPageNumber(3).click();
        plf._text().entryPositionByPosition().row(25).assertPresence(true);
        plf._text().entryPositionByPosition().row(26).assertPresence(false);

        goal = plf._link().entryVehicleByPosition().row(25).getText();
        plf._link().entryGroupIconByPosition().row(25).click();
        pause(5, "Wait for bubble to load.");
        assertStringContains(goal, plf._link().valueMapBubbleVehicleName().getText());
        
        plf._dropDown().numNearestVehicles().select("50");
        plf._button().locate().click();
        plf._page().pageIndex().selectPageNumber(5).waitForElement();
        plf._page().pageIndex().selectPageNumber(5).assertPresence(true);
        plf._page().pageIndex().selectPageNumber(6).assertPresence(false);
        plf._page().pageIndex().forwardAll().click();
        plf._text().entryPositionByPosition().row(50).assertEquals("50");
        
        goal = plf._link().entryVehicleByPosition().row(50).getText();
        plf._link().entryGroupIconByPosition().row(50).click();
        pause(5, "Wait for bubble to load.");
        assertStringContains(goal, plf._link().valueMapBubbleVehicleName().getText());
        
        plf._dropDown().numNearestVehicles().select("100");
        plf._button().locate().click();
        plf._page().pageIndex().selectPageNumber(10).waitForElement();
        plf._page().pageIndex().selectPageNumber(10).assertPresence(true);
        plf._page().pageIndex().selectPageNumber(11).assertPresence(false);
        plf._page().pageIndex().forwardAll().click();
        plf._text().entryPositionByPosition().row(100).assertEquals("100");

        goal = plf._link().entryVehicleByPosition().row(100).getText();
        plf._link().entryGroupIconByPosition().row(100).click();
        pause(5, "Wait for bubble to load.");
        assertStringContains(goal, plf._link().valueMapBubbleVehicleName().getText());
    }
    
    @Test
    public void driverLinkTest1232(){
        set_test_case("TC1232");
        String driverName = plf._link().entryDriverByPosition().row(1).getText();
        plf._link().entryDriverByPosition().row(1).click();
        assertStringContains("/app/driver/", ptds.getCurrentLocation());
        PageDriverPerformance pdp = new PageDriverPerformance();
        assertStringContains(driverName, pdp._link().driverName().getText());
        
    }
    
    @Test
    public void findAddressTest1233(){
        set_test_case("TC1232");
        pl.loginProcess(userTop);
        ptds._link().liveFleet().click();
        String targetVehicle = "nautilus";
        String targetLocation = "0,0";
        plf._textField().findAddress().type(targetLocation);
        plf._button().locate().click();
        pause(5, "Wait for map to load.");
        assertStringContains(targetVehicle, plf._link().entryVehicleByPosition().row(1).getText());
    }
    
    @Test
    public void teamLinkTest1236(){
        set_test_case("TC1236");
        String teamName = plf._link().entryFleetLegend().row(1).getText();
        plf._link().entryFleetLegend().row(1).click();
        assertStringContains("/app/dashboard/", ptds.getCurrentLocation());
        assertStringContains(teamName, ptds._text().teamName().getText());
        
    }
    
    @Test
    public void liveFleetUITest1237(){
        set_test_case("TC1237");
        
        plf._link().sortByNumber().assertPresence(true);
        plf._link().sortByDriver().assertPresence(true);
        plf._link().sortByVehicle().assertPresence(true);
        plf._link().sortByGroup().assertPresence(true);
        plf._text().headerFleetLegend().assertPresence(true);
        plf._link().entryFleetLegend().row(1).assertPresence( true);
        plf._textField().findAddress().assertPresence(true);
        plf._dropDown().numNearestVehicles().assertPresence(true);
        plf._button().locate().assertPresence(true);
        plf._link().entryGroupIconByPosition().row(1).click();
        pause(5, "Wait for pop-up to load.");
        plf._link().valueMapBubbleVehicleName().assertPresence(true);
    }
    
    @Test
    public void vehicleImageLinkTest1238(){
        set_test_case("TC1238");
        
        plf._link().entryGroupIconByPosition().row(1).click();
        pause(5, "Wait for pop-up to load.");
        plf._link().valueMapBubbleVehicleName().assertPresence(true);
        plf._text().labelMapBubbleDevice().assertPresence(true);
        plf._text().labelMapBubbleDriver().assertPresence(true);
        plf._text().labelMapBubbleLocation().assertPresence(true);
        plf._text().labelMapBubblePhone1().assertPresence(true);
        plf._text().labelMapBubblePhone2().assertPresence(true);
        plf._text().labelMapBubbleUpdated().assertPresence(true);
        plf._link().valueMapBubbleDriver().assertPresence(true);
        plf._text().valueMapBubbleDevice().assertPresence(true);
        plf._text().valueMapBubbleUpdated().assertPresence(true);
        plf._text().valueMapBubbleLocation().assertPresence(true);
        plf._text().valueMapBubbleDistToAddress().assertPresence(true);
    }
    
    @Test
    public void vehicleLinkTest1239(){
        set_test_case("TC1239");
        String vehicleName = plf._link().entryVehicleByPosition().row(1).getText();
        plf._link().entryVehicleByPosition().row(1).click();
        assertStringContains("/app/vehicle/", ptds.getCurrentLocation());
        PageVehiclePerformance pvp = new PageVehiclePerformance();
        assertStringContains(vehicleName, pvp._link().vehicleName().getText());
    }
    
    @Test
    public void refreshTest5740(){
        set_test_case("TC5740");
        String targetVehicle = "mannymachine";
        String targetIMEI = "999999000109750";
        TiwiProDevice tiwi = new TiwiProDevice(targetIMEI, Addresses.QA);
        pl.loginProcess(userTop);
        ptds._link().liveFleet().click();
        
        AutomationCalendar initialTime = new AutomationCalendar();
        
        tiwi.firstLocation(new GeoPoint(60, 0));
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
        tiwi.set_time(initialTime.addToSeconds(60));
        tiwi.update_location(new GeoPoint(60, 0), 15);
        tiwi.last_location(new GeoPoint(60, 0), 15);

        AutomationDeviceEvents.statistics(tiwi);
        tiwi.turn_key_off(30);
        tiwi.power_off_device(900);
        
        
        pause(25, "Wait for notes to send");
        plf._button().refresh().click();
        pause(10, "Wait for refresh.");
        plf.getLinkByText(targetVehicle).assertPresence(false);
        
        tiwi.firstLocation(new GeoPoint(40.7097, -111.9925));
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
        tiwi.set_time(initialTime.addToSeconds(120));
        tiwi.update_location(new GeoPoint(40.7097, -111.9925), 15);
        tiwi.last_location(new GeoPoint(40.7097, -111.9925), 15);
        
        AutomationDeviceEvents.statistics(tiwi);
        tiwi.turn_key_off(30);
        tiwi.power_off_device(900);
        
        
        pause(25, "Wait for note to send");
        plf._button().refresh().click();
        pause(10, "Wait for refresh.");
        plf.getLinkByText(targetVehicle).assertPresence(true);
    }
}
