package com.inthinc.pro.selenium.testSuites;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsZones;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;

public class NotificationsZonesTest extends WebRallyTest {
    String USERNAME = "dastardly";
    String USERNAME_2 = "CaptainNemo";
    String USERNAME_TOP = "pitstop";
    String PASSWORD = "Muttley";
    String GROUP = "Test Group WR";
    PageLogin pl;
    PageNotificationsRedFlags pnrf;
    PageNotificationsZones pnz;

    @Before
    public void before(){
        pl = new PageLogin();
        pnrf = new PageNotificationsRedFlags();
        pnz = new PageNotificationsZones();
    }
    
    @Test
    public void bookmarkEntryTest5710(){
        set_test_case("TC5710");

        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        savePageLink();
        String correctURL = pnrf.getCurrentLocation();
        pnrf._link().logout().click();
        openSavedPage();
        pnrf.loginProcess(USERNAME, PASSWORD);
        assertStringContains(correctURL, ptds.getCurrentLocation());
    }
    
    @Test
    public void bookmarkEntryDifferentAccountTest5711(){
        set_test_case("TC5711");
        
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        savePageLink();
        String correctURL = pnrf.getCurrentLocation();
        pnrf._link().logout().click();
        pnrf.loginProcess(USERNAME_2, PASSWORD);
        String team2 = ptds._text().teamName().getText();
        openSavedPage();
        assertStringContains(correctURL, ptds.getCurrentLocation());
        assertStringContains(team2, pnz._dropDown().team().getText(2));
        
    }
    
    @Test
    public void driverLinkTest5712(){
        set_test_case("TC5712");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._button().refresh().click();
        pause(10, "Wait for page to load.");
        int i = 1;
        while(!pnz._link().entryDriver().row(i).isClickable()){
            i++;
        }
        String driver = pnz._link().entryDriver().row(i).getText();
        pnz._link().entryDriver().row(i).click();
        assertStringContains("app/driver", pnz.getCurrentLocation());
        PageDriverPerformance pdp = new PageDriverPerformance();
        assertStringContains(driver, pdp._link().driverName().getText());
    }
    
    @Test
    public void emailTest5713(){
        //set_test_case("TC5713");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._button().tools().click();
        pnz._button().emailReport().click();
        //TODO .clear does not work currently.
        pnz._popUp().emailReport()._textField().emailAddresses().clear();
        pnz._popUp().emailReport()._textField().emailAddresses().type("fail@a.com");
        //TODO Update when email can be checked.
    }
    
    @Test
    public void locationMapLinkTest5717(){
      //set_test_case("TC5717");
        //allCheckedHelper();
        //TODO Look into the close button.
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnz._button().eventLocation().row(1).click();
        pause(10, "Wait for bubble to load.");
        pnz._popUp().location()._text().title().assertPresence(true);
        pnz._popUp().location()._button().closeLocationPopUp().assertPresence(true);
    }
    
    @Test
    public void searchTest5718(){
        set_test_case("TC5718");
        allCheckedHelper();
        int length = 3;
        
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._button().refresh().click();
        pause(5, "Wait for page to load.");
        
        for (int i=0;i<length;i++){
            String currentSearch = searchText(i);
            searchHeader(i).type(currentSearch);
            pause(10,"");
            Iterator<ClickableTextBased> gitr = searchValues(i).iterator();
            while(gitr.hasNext()){
                gitr.next().validateContains(currentSearch);
            }
            searchHeader(i).clear();
        }
        
        for (int i=0;i<length;i++){
            String currentSearch = badSearchText(i);
            searchHeader(i).type(currentSearch);
            pause(10,"");
            Iterator<ClickableTextBased> bitr = searchValues(i).iterator();
            while(bitr.hasNext()){
                bitr.next().validateContains(currentSearch);
            }
            searchHeader(i).clear();
            searchHeader(i).clear();
        }
        
    }
    
    @Test
    public void tablePropertiesTest5719(){
        
        set_test_case("TC5719");
        allCheckedHelper();
        String currentText = "";
        AutomationCalendar currentDate = null;
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._button().refresh().click();
        pnz._text().dateTimeEntry().row(1).waitForElement();
        
        Iterator<TextBased> itr = pnz._text().dateTimeEntry().iterator();
        if(itr.hasNext()){
            currentDate = new AutomationCalendar(itr.next().getText(), WebDateFormat.NOTE_DATE_TIME);
        }
        while(itr.hasNext()){
            AutomationCalendar newDate = new AutomationCalendar(itr.next().getText(), WebDateFormat.NOTE_DATE_TIME);
            if(currentDate.compareTo(newDate) < 0){
                    print(currentDate.toString());
                    print(newDate.toString());
                    addError("Dates out of order", ErrorLevel.FAIL);
            }
            currentDate = newDate;
        }
        
        pnz._link().sortByDateTime().click();
        pause(5, "Wait for refresh.");
        
        itr = pnz._text().dateTimeEntry().iterator();
        if(itr.hasNext()){
            currentDate = new AutomationCalendar(itr.next().getText(), WebDateFormat.NOTE_DATE_TIME);
        }
        while(itr.hasNext()){
            AutomationCalendar newDate = new AutomationCalendar(itr.next().getText(), WebDateFormat.NOTE_DATE_TIME);
            if(currentDate.compareTo(newDate) > 0){
                    print(currentDate.toString());
                    print(newDate.toString());
                    addError("Dates out of order", ErrorLevel.FAIL);
            }
            currentDate = newDate;
        }
        
        pnz._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        
        Iterator<ClickableTextBased> citr = pnz._link().entryDriver().iterator();
        if(citr.hasNext()){
            currentText = citr.next().getText();
        }
        while(citr.hasNext()){
            String newText = citr.next().getText();
            if(currentText.compareToIgnoreCase(newText) > 0){
                    print(currentText);
                    print(newText);
                    addError("Drivers out of order", ErrorLevel.FAIL);
            }
            currentText = newText;
        }
        
        pnz._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnz._link().entryDriver().iterator();
        if(citr.hasNext()){
            currentText = citr.next().getText();
        }
        while(citr.hasNext()){
            String newText = citr.next().getText();
            if(currentText.compareToIgnoreCase(newText) < 0){
                    print(currentText);
                    print(newText);
                    addError("Drivers out of order", ErrorLevel.FAIL);
            }
            currentText = newText;
        }
        
        pnz._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnz._link().entryGroup().iterator();
        if(citr.hasNext()){
            currentText = citr.next().getText();
        }
        while(citr.hasNext()){
            String newText = citr.next().getText();
            if(currentText.compareToIgnoreCase(newText) > 0){
                    print(currentText);
                    print(newText);
                    addError("Drivers out of order", ErrorLevel.FAIL);
            }
            currentText = newText;
        }
        
        pnz._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnz._link().entryGroup().iterator();
        if(citr.hasNext()){
            currentText = citr.next().getText();
        }
        while(citr.hasNext()){
            String newText = citr.next().getText();
            if(currentText.compareToIgnoreCase(newText) < 0){
                    print(currentText);
                    print(newText);
                    addError("Drivers out of order", ErrorLevel.FAIL);
            }
            currentText = newText;
        }
        
        
        pnz._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnz._link().entryVehicle().iterator();
        if(citr.hasNext()){
            currentText = citr.next().getText();
        }
        while(citr.hasNext()){
            String newText = citr.next().getText();
            if(currentText.compareToIgnoreCase(newText) > 0){
                    print(currentText);
                    print(newText);
                    addError("Drivers out of order", ErrorLevel.FAIL);
            }
            currentText = newText;
        }
        
        pnz._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnz._link().entryVehicle().iterator();
        if(citr.hasNext()){
            currentText = citr.next().getText();
        }
        while(citr.hasNext()){
            String newText = citr.next().getText();
            if(currentText.compareToIgnoreCase(newText) < 0){
                    print(currentText);
                    print(newText);
                    addError("Drivers out of order", ErrorLevel.FAIL);
            }
            currentText = newText;
        }
    }
    
    @Test
    public void toolsButtonTest5720(){
        set_test_case("TC5720");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._button().tools().click();
        pnz._button().emailReport().assertPresence(true);
        pnz._button().exportToPDF().assertPresence(true);
        pnz._button().exportToExcel().assertPresence(true);
    }
    
    @Test
    public void zonesUITest5721(){
        set_test_case("TC5721");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._dropDown().team().assertPresence(true);
        pnz._dropDown().timeFrame().assertPresence(true);
        pnz._button().refresh().assertPresence(true);
        pnz._link().editColumns().assertPresence(true);
        pnz._button().tools().assertPresence(true);
        pnz._text().counter().assertPresence(true);
        pnz._text().counter().assertEquals("Showing 0 to 0 of 0 records");
        pnz._link().sortByDateTime().assertPresence(true);
        pnz._link().sortByGroup().assertPresence(true);
        pnz._link().sortByDriver().assertPresence(true);
        pnz._link().sortByVehicle().assertPresence(true);
        pnz._text().headerCategory().assertPresence(true);
        pnz._text().headerDetail().assertPresence(true);
        pnz._text().headerStatus().assertPresence(true);
        
    }
    
    @Test
    public void vehicleLinkTest5722(){
        set_test_case("TC5722");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._button().refresh().click();
        pause(10, "Wait for page to load.");
        String vehicle = pnz._link().entryVehicle().row(1).getText();
        pnz._link().entryVehicle().row(1).click();
        assertStringContains("app/vehicle", pnz.getCurrentLocation());
        PageVehiclePerformance pvp = new PageVehiclePerformance();
        assertStringContains(vehicle, pvp._link().vehicleName().getText());
    }
    
    @Test
    public void cancelChangesTest5723(){
        set_test_case("TC5723");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        
        boolean b1 = pnz._link().sortByDateTime().isPresent();
        boolean b2 = pnz._link().sortByGroup().isPresent();
        boolean b3 = pnz._link().sortByDriver().isPresent();
        boolean b4 = pnz._link().sortByVehicle().isPresent();
        boolean b5 = pnz._text().headerCategory().isPresent();
        boolean b6 = pnz._text().headerDetail().isPresent();
        
        pnz._link().editColumns().click();
        pnz._popUp().editColumns()._checkBox().row(1).click();
        pnz._popUp().editColumns()._button().cancel().click();
        
        pnz._link().sortByDateTime().assertPresence(b1);
        pnz._link().sortByGroup().assertPresence(b2);
        pnz._link().sortByDriver().assertPresence(b3);
        pnz._link().sortByVehicle().assertPresence(b4);
        pnz._text().headerCategory().assertPresence(b5);
        pnz._text().headerDetail().assertPresence(b6);

    }
    
    @Test
    public void cancelNoChangesTest5724(){
        set_test_case("TC5724");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        
        boolean b1 = pnz._link().sortByDateTime().isPresent();
        boolean b2 = pnz._link().sortByGroup().isPresent();
        boolean b3 = pnz._link().sortByDriver().isPresent();
        boolean b4 = pnz._link().sortByVehicle().isPresent();
        boolean b5 = pnz._text().headerCategory().isPresent();
        boolean b6 = pnz._text().headerDetail().isPresent();
        
        pnz._link().editColumns().click();
        pnz._popUp().editColumns()._button().cancel().click();
        
        pnz._link().sortByDateTime().assertPresence(b1);
        pnz._link().sortByGroup().assertPresence(b2);
        pnz._link().sortByDriver().assertPresence(b3);
        pnz._link().sortByVehicle().assertPresence(b4);
        pnz._text().headerCategory().assertPresence(b5);
        pnz._text().headerDetail().assertPresence(b6);
    }
    
    @Test
    public void mouseCheckBoxSelectionTest5725(){
        set_test_case("TC5725");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._link().editColumns().click();
        
        pnz._popUp().editColumns()._checkBox().row(1).click();
        pnz._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        pnz._popUp().editColumns()._checkBox().row(1).click();
        pnz._popUp().editColumns()._checkBox().row(1).assertChecked(true);
        
        pnz._popUp().editColumns()._link().entry().row(1).click();
        pnz._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        pnz._popUp().editColumns()._link().entry().row(1).click();
        pnz._popUp().editColumns()._checkBox().row(1).assertChecked(true);
       
    }
    
    @Test
    public void spacebarCheckBoxSelectionTest5726(){
        set_test_case("TC5726");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._link().editColumns().click();
        pnz._popUp().editColumns()._checkBox().row(1).focus();
        pnz._popUp().editColumns()._checkBox().row(1).click();//focus toggles checkboxes currently.
        spaceBar();
        pnz._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        spaceBar();
        pnz._popUp().editColumns()._checkBox().row(1).assertChecked(true);
        
    }
    
    
    @Test
    public void currentSessionRetentionTest5727(){
        set_test_case("TC5727");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._link().editColumns().click();
        pnz._popUp().editColumns()._checkBox().row(1).uncheck();
        pnz._popUp().editColumns()._checkBox().row(4).check();
        pnz._popUp().editColumns()._button().save().click();
        pnz._link().reports().click();
        pnz._link().notifications().click();
        pnz._link().zones().click();
        
        pnz._link().sortByDateTime().assertPresence(false);
        pnz._link().sortByGroup().assertPresence(true);
        pnz._link().sortByDriver().assertPresence(true);
        pnz._link().sortByVehicle().assertPresence(true);
        pnz._text().headerCategory().assertPresence(false);
        pnz._text().headerDetail().assertPresence(false);
        
    }
    
    @Test
    public void enterKeyTest5728(){
        set_test_case("TC5728");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._link().editColumns().click();
        pnz._popUp().editColumns()._checkBox().row(1).focus();
        pnz._popUp().editColumns()._checkBox().row(1).uncheck();
        pnz._popUp().editColumns()._checkBox().row(4).check();
        enterKey();
        pause(2, "Waiting for columns to update.");
        pnz._link().sortByDateTime().assertPresence(false);
        pnz._link().sortByGroup().assertPresence(true);
        pnz._link().sortByDriver().assertPresence(true);
        pnz._link().sortByVehicle().assertPresence(true);
        pnz._text().headerCategory().assertPresence(false);
        pnz._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void saveButtonTest5729(){
        set_test_case("TC5729");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._link().editColumns().click();
        pnz._popUp().editColumns()._checkBox().row(1).uncheck();
        pnz._popUp().editColumns()._checkBox().row(4).check();
        pnz._popUp().editColumns()._button().save().click();
        pnz._link().sortByDateTime().assertPresence(false);
        pnz._link().sortByGroup().assertPresence(true);
        pnz._link().sortByDriver().assertPresence(true);
        pnz._link().sortByVehicle().assertPresence(true);
        pnz._text().headerCategory().assertPresence(false);
        pnz._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void subsequentSessionRetentionTest5730(){
        set_test_case("TC5730");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._link().editColumns().click();
        pnz._popUp().editColumns()._checkBox().row(1).uncheck();
        pnz._popUp().editColumns()._checkBox().row(4).check();
        pnz._popUp().editColumns()._button().save().click();
        pnz._link().logout().click();
        pl.loginProcess(USERNAME, PASSWORD);
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._link().sortByDateTime().assertPresence(false);
        pnz._link().sortByGroup().assertPresence(true);
        pnz._link().sortByDriver().assertPresence(true);
        pnz._link().sortByVehicle().assertPresence(true);
        pnz._text().headerCategory().assertPresence(false);
        pnz._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void tabbingOrderTest5731(){
        set_test_case("TC5731");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._link().editColumns().click();
        pnz._popUp().editColumns()._checkBox().row(1).focus();
        tabKey();
        if(!pnz._popUp().editColumns()._checkBox().row(2).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on second check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._checkBox().row(3).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on third check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._checkBox().row(4).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on fourth check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._checkBox().row(5).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on fifth check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._checkBox().row(6).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on sixth check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._button().save().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on save button.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._button().cancel().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on cancel button.", ErrorLevel.FATAL);
        }
    }
    
    @Test
    public void editColumnsUITest5732(){
        set_test_case("TC5732");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        
        boolean b1 = pnz._link().sortByDateTime().isPresent();
        boolean b2 = pnz._link().sortByGroup().isPresent();
        boolean b3 = pnz._link().sortByDriver().isPresent();
        boolean b4 = pnz._link().sortByVehicle().isPresent();
        boolean b5 = pnz._text().headerCategory().isPresent();
        boolean b6 = pnz._text().headerDetail().isPresent();
        
        pnz._link().editColumns().click();
        pnz._popUp().editColumns()._checkBox().row(1).assertVisibility(true);
        pnz._popUp().editColumns()._checkBox().row(2).assertVisibility(true);
        pnz._popUp().editColumns()._checkBox().row(3).assertVisibility(true);
        pnz._popUp().editColumns()._checkBox().row(4).assertVisibility(true);
        pnz._popUp().editColumns()._checkBox().row(5).assertVisibility(true);
        pnz._popUp().editColumns()._checkBox().row(6).assertVisibility(true);
        
        pnz._popUp().editColumns()._checkBox().row(1).assertChecked(b1);
        pnz._popUp().editColumns()._checkBox().row(2).assertChecked(b2);
        pnz._popUp().editColumns()._checkBox().row(3).assertChecked(b3);
        pnz._popUp().editColumns()._checkBox().row(4).assertChecked(b4);
        pnz._popUp().editColumns()._checkBox().row(5).assertChecked(b5);
        pnz._popUp().editColumns()._checkBox().row(6).assertChecked(b6);
        
        pnz._popUp().editColumns()._button().save().assertVisibility(true);
        pnz._popUp().editColumns()._button().cancel().assertVisibility(true);
    }
    
    @Test
    public void excludeLinkArrivalTest5733(){
        set_test_case("TC5733");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._dropDown().category().select(2);
        pnz._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnz._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        pnz._popUp().excludeEvent()._button().yes().click();
        pause(10, "Wait for page to load.");
        assertStringContains("inc", pnz._link().entryStatus().row(1).getText());
        pnz._link().entryStatus().row(1).click();
    }
    
    @Test
    public void excludeLinkDepartureTest5734(){
        set_test_case("TC5734");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._dropDown().category().select(3);
        pnz._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnz._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        pnz._popUp().excludeEvent()._button().yes().click();
        pause(10, "Wait for page to load.");
        assertStringContains("inc", pnz._link().entryStatus().row(1).getText());
        pnz._link().entryStatus().row(1).click();
    }
    
    @Test
    public void excludeLinkUITest5735(){
        set_test_case("TC5735");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._dropDown().statusFilter().select("included");
        pnz._button().refresh().click();
        pause(10, "Wait for page to load.");
        String date = pnz._text().dateTimeEntry().row(1).getText();
        String detail = pnz._text().detailEntry().row(1).getText();
        pnz._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        assertStringContains(date, pnz._popUp().excludeEvent()._text().message().getText());
        assertStringContains(detail, pnz._popUp().excludeEvent()._text().message().getText());
        pnz._popUp().excludeEvent()._button().yes().assertVisibility(true);
        pnz._popUp().excludeEvent()._button().no().assertVisibility(true);
    }
    
    @Test
    public void includeLinkTest5736(){
        set_test_case("TC5736");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnz._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        pnz._popUp().excludeEvent()._button().yes().click();
        pause(10, "Wait for page to load.");
        pnz._link().entryStatus().row(1).click();
        pause(5, "Wait for event to re-include.");
        assertStringContains("exc", pnz._link().entryStatus().row(1).getText());
    }
    
    @Test
    public void timeFrameTest5741(){
        set_test_case("TC5741");
        
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._button().refresh().click();
        pause(5, "Wait for refresh.");
        AutomationCalendar todayCal = new AutomationCalendar(WebDateFormat.NOTE_DATE_TIME);
        if(!todayCal.compareDays(pnz._text().dateTimeEntry().row(1).getText())){
            addError("Today's date does not match today's date on the portal.", ErrorLevel.FATAL);
        }
        todayCal.addToDay(-1);
        pnz._dropDown().timeFrame().selectPartMatch("Yesterday");
        pnz._button().refresh().click();
        pause(10, "Wait for refresh.");
        
        if(!todayCal.compareDays(pnz._text().dateTimeEntry().row(1).getText())){
            addError("Yesterday's date does not match yesterday's date on the portal.", ErrorLevel.FATAL);
        }
        
    }
    
    public TextField searchHeader(int i){
        TextField[] searchHeaders = {pnz._textField().group(), pnz._textField().driver(), pnz._textField().vehicle()};

        return searchHeaders[i];
    }
    
    public String searchText(int i){
        int firstDriver = 1;
        while(!pnz._link().entryDriver().row(firstDriver).isClickable()){
            firstDriver++;
        }
        String[] searchStrings = {(String) pnz._link().entryGroup().row(1).getText().substring(0, 3),
                (String) pnz._link().entryDriver().row(firstDriver).getText().substring(0, 3), 
                (String) pnz._link().entryVehicle().row(1).getText().substring(0, 3)};
        return searchStrings[i];
    }
    
    public String badSearchText(int i){
        return "ZZZZZZZZ";
    }
    
    public TextTableLink searchValues(int i){
        TextTableLink[] tableValues = {pnz._link().entryGroup(), pnz._link().entryDriver(), pnz._link().entryVehicle()};
        return tableValues[i];
    }
    
    public void allCheckedHelper(){
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._link().editColumns().click();
        pnz._popUp().editColumns()._checkBox().row(1).check();
        pnz._popUp().editColumns()._checkBox().row(2).check();
        pnz._popUp().editColumns()._checkBox().row(3).check();
        pnz._popUp().editColumns()._checkBox().row(4).check();
        pnz._popUp().editColumns()._checkBox().row(5).check();
        pnz._popUp().editColumns()._checkBox().row(6).check();
        pnz._popUp().editColumns()._button().save().click();
        pnz._link().logout().click();
    }
    
    public void someCheckedHelper(){
          pl.loginProcess(USERNAME, PASSWORD);
          PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
          ptds._link().notifications().click();
          pnrf._link().zones().click();
          
          pnz._link().editColumns().click();
          pnz._popUp().editColumns()._checkBox().row(1).check();
          pnz._popUp().editColumns()._checkBox().row(2).check();
          pnz._popUp().editColumns()._checkBox().row(3).check();
          pnz._popUp().editColumns()._checkBox().row(4).uncheck();
          pnz._popUp().editColumns()._checkBox().row(5).uncheck();
          pnz._popUp().editColumns()._checkBox().row(6).uncheck();
          pnz._popUp().editColumns()._button().save().click();
          pnz._link().logout().click();
      }
}