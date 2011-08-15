package com.inthinc.pro.selenium.testSuites;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;


public class NotificationsRedFlagsTest extends WebRallyTest {
    String USERNAME = "dastardly";
    String USERNAME_TOP = "pitstop";
    String USERNAME_2 = "CaptainNemo";
    String PASSWORD = "Muttley";
    String GROUP = "Test Group WR";
    PageLogin pl;
    PageNotificationsRedFlags pnrf;

    @Before
    public void before(){
        pl = new PageLogin();
        pnrf = new PageNotificationsRedFlags();
    }
    
    @Test
    public void bookmarkEntryTest1434(){
        set_test_case("TC1434");

        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        savePageLink();
        String correctURL = pnrf.getCurrentLocation();
        pnrf._link().logout().click();
        openSavedPage();
        pnrf.loginProcess(USERNAME, PASSWORD);
        assertStringContains(correctURL, ptds.getCurrentLocation());
    }
    
    @Test
    public void bookmarkEntryDifferentAccountTest1435(){
        set_test_case("TC1435");
        
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        savePageLink();
        String correctURL = pnrf.getCurrentLocation();
        pnrf._link().logout().click();
        pnrf.loginProcess(USERNAME_2, PASSWORD);
        String team2 = ptds._text().teamName().getText();
        openSavedPage();
        assertStringContains(correctURL, ptds.getCurrentLocation());
        assertStringContains(team2, pnrf._dropDown().team().getText(2));
        
    }
    
    @Test
    public void driverLinkTest1437(){
        set_test_case("TC1437");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pause(10, "Wait for page to load.");
        int i = 1;
        while(!pnrf._link().entryDriver().row(i).isClickable()){
            i++;
        }
        pnrf._link().entryDriver().row(i).click();
        assertStringContains("app/driver", pnrf.getCurrentLocation());
    }
    
    @Test
    public void emailTest1439(){
        //set_test_case("TC1439");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._button().tools().click();
        pnrf._button().emailReport().click();
        //TODO .clear does not work currently.
        pnrf._popUp().emailReport()._textField().emailAddresses().clear();
        pnrf._popUp().emailReport()._textField().emailAddresses().type("fail@a.com");
        //TODO Update when email can be checked.
    }
    
    @Test
    public void locationMapLinkTest1445(){
        set_test_case("TC1445");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pnrf._text().entryDateTime().row(1).waitForElement();
        String dateTime = pnrf._text().entryDateTime().row(1).getText();
        String driver = pnrf._link().entryDriver().row(1).getText();
        String detail = pnrf._text().entryDetail().row(1).getText();
        pnrf._button().eventLocation().row(1).click();
        pnrf._popUp().location()._text().title().assertVisibility(true);
        pnrf._popUp().location()._text().bubbleValueName().assertEquals(driver);
        pnrf._popUp().location()._text().bubbleValueDateTime().assertEquals(dateTime);
        pnrf._popUp().location()._text().bubbleValueDetail().assertEquals(detail);
        pnrf._popUp().location()._button().closeLocationPopUp().assertVisibility(true);
    }
    
    @Test
    public void pageLinkTest1446(){
        //set_test_case("TC1446");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnrf._page().pageIndex().selectPageNumber(2);
        //TODO Verify page does change.
    }
    
    @Test
    public void searchTest1448(){
        set_test_case("TC1448");
        allCheckedHelper();
        int length = 3;
        
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pause(5, "Wait for page to load.");
        
        for (int i=0;i<length;i++){
            String currentSearch = searchText(i);
            searchHeader(i).type(currentSearch);
            pause(10,"");
            for (int j=1;j<=20;j++){
                TextTableLink currentColumn = searchValues(i);
                if (currentColumn.row(j).isPresent()){
                    currentColumn.row(j).validateContains(currentSearch);
                } else {
                    break;
                }
            }
            searchHeader(i).clear();
        }
        
        for (int i=0;i<length;i++){
            String currentSearch = badSearchText(i);
            searchHeader(i).type(currentSearch);
            pause(10,"");
            for (int j=1;j<=20;j++){
                TextTableLink currentColumn = searchValues(i);
                if (currentColumn.row(j).isPresent()){
                    currentColumn.row(j).validateContains(currentSearch);
                } else {
                    break;
                }
            }
            searchHeader(i).clear();
        }
        
    }
    
    /*
     * Compares the dates.
     * 
     * @param date1
     * @param date2
     * @return 0 if date1 = date2, -1 if date1 is BEFORE date2, 1 if date1 is AFTER date2.
     */
    public int cruddyCompareDates(String date1, String date2){
        //Set up comparison values.
        int month1 = monthToInt(date1.substring(0,3));
        int month2 = monthToInt(date2.substring(0,3));
        int day1;
        int day2;
        int year1;
        int year2;
        String dateStripped1;
        String dateStripped2;
        
        if(date1.charAt(6) == ','){
            day1 = Integer.parseInt(date1.substring(4,6));
            year1 = Integer.parseInt(date1.substring(8,12));
            dateStripped1 = date1.substring(13);
        }
        else{
            day1 = Integer.parseInt(date1.substring(4,5));
            year1 = Integer.parseInt(date1.substring(7,11));
            dateStripped1 = date1.substring(12);
        }
        if(date2.charAt(6) == ','){
            day2 = Integer.parseInt(date2.substring(4,6));
            year2 = Integer.parseInt(date2.substring(8,12));
            dateStripped2 = date2.substring(13);
        }
        else{
            day2 = Integer.parseInt(date2.substring(4,5));
            year2 = Integer.parseInt(date2.substring(7,11));
            dateStripped2 = date2.substring(12);
        }
        
        int hour1;
        int hour2;
        int minute1;
        int minute2;
        String meridian1;
        String meridian2;
        
        if(dateStripped1.charAt(1) == ':'){
            hour1 = Integer.parseInt(dateStripped1.substring(0,1));
            minute1 = Integer.parseInt(dateStripped1.substring(2,4));
            meridian1 = dateStripped1.substring(5,7);
        }
        else{
            hour1 = Integer.parseInt(dateStripped1.substring(0,2));
            minute1 = Integer.parseInt(dateStripped1.substring(3,5));
            meridian1 = dateStripped1.substring(6,8);
        }
        if(dateStripped2.charAt(1) == ':'){
            hour2 = Integer.parseInt(dateStripped2.substring(0,1));
            minute2 = Integer.parseInt(dateStripped2.substring(2,4));
            meridian2 = dateStripped2.substring(5,7);
        }
        else{
            hour2 = Integer.parseInt(dateStripped2.substring(0,2));
            minute2 = Integer.parseInt(dateStripped2.substring(3,5));
            meridian2 = dateStripped2.substring(6,8);
        }
        
        //Compare years.
        if(year1 != year2){
            if(year1 > year2)
                return 1;
            return -1;
        }
        
        //Compare months.
        if(month1 > month2){
            return 1;
        }
        if(month2 > month1){
            return -1;
        }
        
        //Compare days.
        if(day1 > day2){
            return 1;
        }
        if(day2 > day1){
            return -1;
        }
        
        //Compare meridians.
        if(meridian1.equals(meridian2)){}
        else{
            if(meridian1.equals("PM")){
                return 1;
            }
            else{
                return -1;
            }
        }
        
        //Compare hours.
        if(hour1 > hour2){
            if(hour1 == 12)
                return -1;
            return 1;
        }
        if(hour2 > hour1){
            if(hour2 == 12)
                return 1;
            return -1;
        }
        
        //Compare minutes.
        if(minute1 > minute2){
            return 1;
        }
        if(minute2 > minute1){
            return -1;
        }
        
        return 0;
    }
    
    @Test
    public void tablePropertiesTest1450(){
        
        set_test_case("TC1450");
        allCheckedHelper();
        String currentText;
        AutomationCalendar currentDate = null;
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pnrf._text().entryDateTime().row(1).waitForElement();
        
        currentText = "";
        
        Iterator<TextBased> itr = pnrf._text().entryDateTime().iterator();
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
        
        pnrf._link().sortByDateTime().click();
        pause(5, "Wait for refresh.");
        
        itr = pnrf._text().entryDateTime().iterator();
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
        
        pnrf._link().sortByDriver().click();
        pause(5, "Wait for refresh.");

        currentText = "";
        
        Iterator<ClickableTextBased> citr = pnrf._link().entryDriver().iterator();
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
        
        pnrf._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnrf._link().entryDriver().iterator();
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
        
        pnrf._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnrf._link().entryGroup().iterator();
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
        
        pnrf._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnrf._link().entryGroup().iterator();
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
        
        
        pnrf._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnrf._link().entryVehicle().iterator();
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
        
        pnrf._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnrf._link().entryVehicle().iterator();
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
    public void toolsButtonTest1451(){
        set_test_case("TC1451");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._button().tools().click();
        pnrf._button().emailReport().assertPresence(true);
        pnrf._button().exportToPDF().assertPresence(true);
        pnrf._button().exportToExcel().assertPresence(true);
    }
    
    @Test
    public void redFlagsUITest1452(){
        set_test_case("TC1452");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._dropDown().team().assertPresence(true);
        pnrf._dropDown().timeFrame().assertPresence(true);
        pnrf._button().refresh().assertPresence(true);
        pnrf._link().editColumns().assertPresence(true);
        pnrf._button().tools().assertPresence(true);
        pnrf._text().counter().assertPresence(true);
        pnrf._text().counter().assertEquals("Showing 0 to 0 of 0 records");
        pnrf._text().headerLevel().assertPresence(true);
        pnrf._text().headerAlertDetails().assertPresence(true);
        pnrf._link().sortByDateTime().assertPresence(true);
        pnrf._link().sortByGroup().assertPresence(true);
        pnrf._link().sortByDriver().assertPresence(true);
        pnrf._link().sortByVehicle().assertPresence(true);
        pnrf._text().headerCategory().assertPresence(true);
        pnrf._text().headerDetail().assertPresence(true);
        pnrf._text().headerStatus().assertPresence(true);
        
    }
    
    @Test
    public void vehicleLinkTest1453(){
        set_test_case("TC1453");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnrf._link().entryVehicle().row(1).click();
        assertStringContains("app/vehicle", pnrf.getCurrentLocation());
    }
    
    @Test
    public void cancelChangesTest1455(){
        set_test_case("TC1455");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        
        boolean b1 = pnrf._text().headerLevel().isPresent();
        boolean b2 = pnrf._text().headerAlertDetails().isPresent();
        boolean b3 = pnrf._link().sortByDateTime().isPresent();
        boolean b4 = pnrf._link().sortByGroup().isPresent();
        boolean b5 = pnrf._link().sortByDriver().isPresent();
        boolean b6 = pnrf._link().sortByVehicle().isPresent();
        boolean b7 = pnrf._text().headerCategory().isPresent();
        boolean b8 = pnrf._text().headerDetail().isPresent();
        
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._checkBox().row(1).click();
        pnrf._popUp().editColumns()._button().cancel().click();
        
        pnrf._text().headerLevel().assertPresence(b1);
        pnrf._text().headerAlertDetails().assertPresence(b2);
        pnrf._link().sortByDateTime().assertPresence(b3);
        pnrf._link().sortByGroup().assertPresence(b4);
        pnrf._link().sortByDriver().assertPresence(b5);
        pnrf._link().sortByVehicle().assertPresence(b6);
        pnrf._text().headerCategory().assertPresence(b7);
        pnrf._text().headerDetail().assertPresence(b8);

    }
    
    @Test
    public void cancelNoChangesTest1456(){
        set_test_case("TC1456");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        
        boolean b1 = pnrf._text().headerLevel().isPresent();
        boolean b2 = pnrf._text().headerAlertDetails().isPresent();
        boolean b3 = pnrf._link().sortByDateTime().isPresent();
        boolean b4 = pnrf._link().sortByGroup().isPresent();
        boolean b5 = pnrf._link().sortByDriver().isPresent();
        boolean b6 = pnrf._link().sortByVehicle().isPresent();
        boolean b7 = pnrf._text().headerCategory().isPresent();
        boolean b8 = pnrf._text().headerDetail().isPresent();
        
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._button().cancel().click();
        
        pnrf._text().headerLevel().assertPresence(b1);
        pnrf._text().headerAlertDetails().assertPresence(b2);
        pnrf._link().sortByDateTime().assertPresence(b3);
        pnrf._link().sortByGroup().assertPresence(b4);
        pnrf._link().sortByDriver().assertPresence(b5);
        pnrf._link().sortByVehicle().assertPresence(b6);
        pnrf._text().headerCategory().assertPresence(b7);
        pnrf._text().headerDetail().assertPresence(b8);
    }
    
    @Test
    public void mouseCheckBoxSelectionTest1457(){
        set_test_case("TC1457");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._checkBox().row(1).check();
        
        pnrf._popUp().editColumns()._checkBox().row(1).click();
        pnrf._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        pnrf._popUp().editColumns()._checkBox().row(1).click();
        pnrf._popUp().editColumns()._checkBox().row(1).assertChecked(true);
        
        pnrf._popUp().editColumns()._link().entry().row(1).click();
        pnrf._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        pnrf._popUp().editColumns()._link().entry().row(1).click();
        pnrf._popUp().editColumns()._checkBox().row(1).assertChecked(true);
       
    }
    
    @Test
    public void spacebarCheckBoxSelectionTest1458(){
        set_test_case("TC1458");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._checkBox().row(1).focus();
        pnrf._popUp().editColumns()._checkBox().row(1).click();//focus tooggles checkboxes currently.
        spaceBar();
        pnrf._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        spaceBar();
        pnrf._popUp().editColumns()._checkBox().row(1).assertChecked(true);
        
    }
    
    
    @Test
    public void currentSessionRetentionTest1459(){
        set_test_case("TC1459");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._checkBox().row(1).uncheck();
        pnrf._popUp().editColumns()._checkBox().row(5).check();
        pnrf._popUp().editColumns()._button().save().click();
        pnrf._link().reports().click();
        pnrf._link().notifications().click();
        
        pnrf._text().headerLevel().assertPresence(false);
        pnrf._text().headerAlertDetails().assertPresence(true);
        pnrf._link().sortByDateTime().assertPresence(true);
        pnrf._link().sortByGroup().assertPresence(true);
        pnrf._link().sortByDriver().assertPresence(true);
        pnrf._link().sortByVehicle().assertPresence(false);
        pnrf._text().headerCategory().assertPresence(false);
        pnrf._text().headerDetail().assertPresence(false);
        
    }
    
    @Test
    public void enterKeyTest1460(){
        set_test_case("TC1460");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._checkBox().row(1).focus();
        pnrf._popUp().editColumns()._checkBox().row(1).uncheck();
        pnrf._popUp().editColumns()._checkBox().row(5).check();
        enterKey();
        pause(2, "Waiting for columns to update.");
        pnrf._text().headerLevel().assertPresence(false);
        pnrf._text().headerAlertDetails().assertPresence(true);
        pnrf._link().sortByDateTime().assertPresence(true);
        pnrf._link().sortByGroup().assertPresence(true);
        pnrf._link().sortByDriver().assertPresence(true);
        pnrf._link().sortByVehicle().assertPresence(false);
        pnrf._text().headerCategory().assertPresence(false);
        pnrf._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void saveButtonTest1461(){
        set_test_case("TC1461");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._checkBox().row(1).uncheck();
        pnrf._popUp().editColumns()._checkBox().row(5).check();
        pnrf._popUp().editColumns()._button().save().click();
        pnrf._text().headerLevel().assertPresence(false);
        pnrf._text().headerAlertDetails().assertPresence(true);
        pnrf._link().sortByDateTime().assertPresence(true);
        pnrf._link().sortByGroup().assertPresence(true);
        pnrf._link().sortByDriver().assertPresence(true);
        pnrf._link().sortByVehicle().assertPresence(false);
        pnrf._text().headerCategory().assertPresence(false);
        pnrf._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void subsequentSessionRetentionTest1462(){
        set_test_case("TC1462");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._checkBox().row(1).uncheck();
        pnrf._popUp().editColumns()._checkBox().row(5).check();
        pnrf._popUp().editColumns()._button().save().click();
        pnrf._link().logout().click();
        pl.loginProcess(USERNAME, PASSWORD);
        ptds._link().notifications().click();
        
        pnrf._text().headerLevel().assertPresence(false);
        pnrf._text().headerAlertDetails().assertPresence(true);
        pnrf._link().sortByDateTime().assertPresence(true);
        pnrf._link().sortByGroup().assertPresence(true);
        pnrf._link().sortByDriver().assertPresence(true);
        pnrf._link().sortByVehicle().assertPresence(false);
        pnrf._text().headerCategory().assertPresence(false);
        pnrf._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void tabbingOrderTest1463(){
        set_test_case("TC1463");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._checkBox().row(1).focus();
        tabKey();
        if(!pnrf._popUp().editColumns()._checkBox().row(2).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on second check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnrf._popUp().editColumns()._checkBox().row(3).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on third check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnrf._popUp().editColumns()._checkBox().row(4).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on fourth check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnrf._popUp().editColumns()._checkBox().row(5).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on fifth check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnrf._popUp().editColumns()._checkBox().row(6).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on sixth check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnrf._popUp().editColumns()._checkBox().row(7).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on seventh check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnrf._popUp().editColumns()._checkBox().row(8).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on eigth check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnrf._popUp().editColumns()._button().save().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on save button.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pnrf._popUp().editColumns()._button().cancel().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on cancel button.", ErrorLevel.FATAL);
        }
    }
    
    @Test
    public void editColumnsUITest1464(){
        set_test_case("TC1464");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        
        boolean b1 = pnrf._text().headerLevel().isPresent();
        boolean b2 = pnrf._text().headerAlertDetails().isPresent();
        boolean b3 = pnrf._link().sortByDateTime().isPresent();
        boolean b4 = pnrf._link().sortByGroup().isPresent();
        boolean b5 = pnrf._link().sortByDriver().isPresent();
        boolean b6 = pnrf._link().sortByVehicle().isPresent();
        boolean b7 = pnrf._text().headerCategory().isPresent();
        boolean b8 = pnrf._text().headerDetail().isPresent();
        
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._checkBox().row(1).assertVisibility(true);
        pnrf._popUp().editColumns()._checkBox().row(2).assertVisibility(true);
        pnrf._popUp().editColumns()._checkBox().row(3).assertVisibility(true);
        pnrf._popUp().editColumns()._checkBox().row(4).assertVisibility(true);
        pnrf._popUp().editColumns()._checkBox().row(5).assertVisibility(true);
        pnrf._popUp().editColumns()._checkBox().row(6).assertVisibility(true);
        pnrf._popUp().editColumns()._checkBox().row(7).assertVisibility(true);
        pnrf._popUp().editColumns()._checkBox().row(8).assertVisibility(true);
        
        pnrf._popUp().editColumns()._checkBox().row(1).assertChecked(b1);
        pnrf._popUp().editColumns()._checkBox().row(2).assertChecked(b2);
        pnrf._popUp().editColumns()._checkBox().row(3).assertChecked(b3);
        pnrf._popUp().editColumns()._checkBox().row(4).assertChecked(b4);
        pnrf._popUp().editColumns()._checkBox().row(5).assertChecked(b5);
        pnrf._popUp().editColumns()._checkBox().row(6).assertChecked(b6);
        pnrf._popUp().editColumns()._checkBox().row(7).assertChecked(b7);
        pnrf._popUp().editColumns()._checkBox().row(8).assertChecked(b8);
        
        pnrf._popUp().editColumns()._button().save().assertVisibility(true);
        pnrf._popUp().editColumns()._button().cancel().assertVisibility(true);
    }
    
    @Test
    public void excludeLinkCancelTest1465(){
        set_test_case("TC1465");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._dropDown().statusFilter().select("included");
        pnrf._button().refresh().click();
        pause(10, "Wait for page to load.");
        String date = pnrf._text().entryDateTime().row(1).getText();
        String detail = pnrf._text().entryDetail().row(1).getText();
        pnrf._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        pnrf._popUp().excludeEvent()._button().no().click();
        assertStringContains(date, pnrf._text().entryDateTime().row(1).getText());
        assertStringContains(detail, pnrf._text().entryDetail().row(1).getText());
    }
    
    @Test
    public void excludeLinkCrashTest1466(){
        set_test_case("TC1466");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pnrf._dropDown().category().select(11);
        pause(10, "Wait for page to load.");
        if(pnrf._link().entryStatus().row(1).isPresent()){
            pnrf._link().entryStatus().row(1).click();
            pause(5, "Wait for pop-up to become visible.");
            pnrf._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            assertStringContains("inc", pnrf._link().entryStatus().row(1).getText());
            pnrf._link().entryStatus().row(1).click();
        }
        else{
            //TODO Make the test result Inconclusive instead of Fail.
            addError("Crash event not present to test with.", ErrorLevel.WARN);
        }
    }
    
    @Test
    public void excludeLinkEnterKeyTest1467(){
        set_test_case("TC1467");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._dropDown().statusFilter().select("included");
        pnrf._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnrf._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        pnrf._popUp().excludeEvent()._text().header().focus();
        enterKey();
        pause(10, "Wait for page to load.");
        assertStringContains("inc", pnrf._link().entryStatus().row(1).getText());
        pnrf._link().entryStatus().row(1).click();
    }
    
    @Test
    public void excludeLinkDrivingStyleTest1468(){
        set_test_case("TC1468");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pnrf._dropDown().category().select(2);
        pause(10, "Wait for page to load.");
        if(pnrf._link().entryStatus().row(1).isPresent()){
            pnrf._link().entryStatus().row(1).click();
            pause(5, "Wait for pop-up to become visible.");
            pnrf._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            assertStringContains("inc", pnrf._link().entryStatus().row(1).getText());
            pnrf._link().entryStatus().row(1).click();
        }
        else{
            addError("Driving style event not present to test with.", ErrorLevel.WARN);
        }
    }
    
    @Test
    public void excludeLinkOKButtonTest1469(){
        set_test_case("TC1469");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._dropDown().statusFilter().select("included");
        pnrf._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnrf._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        pnrf._popUp().excludeEvent()._button().yes().click();
        pause(10, "Wait for page to load.");
        assertStringContains("inc", pnrf._link().entryStatus().row(1).getText());
        pnrf._link().entryStatus().row(1).click();
    }
    
    @Test
    public void excludeLinkSeatBeltTest1470(){
        set_test_case("TC1470");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pnrf._dropDown().category().select(7);
        pause(10, "Wait for page to load.");
        if(pnrf._link().entryStatus().row(1).isPresent()){
            pnrf._link().entryStatus().row(1).click();
            pause(5, "Wait for pop-up to become visible.");
            pnrf._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            assertStringContains("inc", pnrf._link().entryStatus().row(1).getText());
            pnrf._link().entryStatus().row(1).click();
        }
        else{
            addError("Seat belt event not present to test with.", ErrorLevel.WARN);
        }
    }
    
    @Test
    public void excludeLinkSpeedingTest1471(){
        set_test_case("TC1471");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pnrf._dropDown().category().select(6);
        pause(10, "Wait for page to load.");
        if(pnrf._link().entryStatus().row(1).isPresent()){
            pnrf._link().entryStatus().row(1).click();
            pause(5, "Wait for pop-up to become visible.");
            pnrf._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            assertStringContains("inc", pnrf._link().entryStatus().row(1).getText());
            pnrf._link().entryStatus().row(1).click();
        }
        else{
            addError("Speeding event not present to test with.", ErrorLevel.WARN);
        }
    }
    
    @Test
    public void excludeLinkUITest1472(){
        set_test_case("TC1472");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._dropDown().statusFilter().select("included");
        pnrf._button().refresh().click();
        pause(10, "Wait for page to load.");
        String date = pnrf._text().entryDateTime().row(1).getText();
        String detail = pnrf._text().entryDetail().row(1).getText();
        pnrf._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        assertStringContains(date, pnrf._popUp().excludeEvent()._text().message().getText());
        assertStringContains(detail, pnrf._popUp().excludeEvent()._text().message().getText());
        pnrf._popUp().excludeEvent()._button().yes().assertVisibility(true);
        pnrf._popUp().excludeEvent()._button().no().assertVisibility(true);
    }
    
    @Test
    public void includeLinkTest5739(){
        set_test_case("TC5739");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnrf._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        pnrf._popUp().excludeEvent()._button().yes().click();
        pause(10, "Wait for page to load.");
        pnrf._link().entryStatus().row(1).click();
        pause(5, "Wait for event to re-include.");
        assertStringContains("exc", pnrf._link().entryStatus().row(1).getText());
    }
    
    @Test
    public void timeFrameTest5744(){
        set_test_case("TC5744");
        
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._dropDown().team().selectPartMatch(GROUP);
        pnrf._button().refresh().click();
        pause(5, "Wait for refresh.");
        AutomationCalendar todayCal = new AutomationCalendar(WebDateFormat.NOTE_DATE_TIME);
        if(!todayCal.compareDays(pnrf._text().entryDateTime().row(1).getText())){
            addError("Today's date does not match today's date on the portal.", ErrorLevel.FATAL);
        }
        todayCal.addToDay(-1);
        pnrf._dropDown().timeFrame().selectPartMatch("Yesterday");
        pnrf._button().refresh().click();
        pause(10, "Wait for refresh.");
        
        if(!todayCal.compareDays(pnrf._text().entryDateTime().row(1).getText())){
            addError("Yesterday's date does not match yesterday's date on the portal.", ErrorLevel.FATAL);
        }
    }
    
    public TextField searchHeader(int i){
        TextField[] searchHeaders = {pnrf._textField().group(), pnrf._textField().driver(), pnrf._textField().vehicle()};

        return searchHeaders[i];
    }
    
    public String searchText(int i){
        int firstDriver = 1;
        while(!pnrf._link().entryDriver().row(firstDriver).isClickable()){
            firstDriver++;
        }
        String[] searchStrings = {(String) pnrf._link().entryGroup().row(1).getText().substring(0, 3),
                (String) pnrf._link().entryDriver().row(firstDriver).getText().substring(0, 3), 
                (String) pnrf._link().entryVehicle().row(1).getText().substring(0, 3)};
        return searchStrings[i];
    }
    
    public String badSearchText(int i){
        return "ZZZZZZZZ";
    }
    
    public TextTableLink searchValues(int i){
        TextTableLink[] tableValues = {pnrf._link().entryGroup(), pnrf._link().entryDriver(), pnrf._link().entryVehicle()};
        return tableValues[i];
    }
    
    public int monthToInt(String month){
        if(month.equals("Jan"))
            return 1;
        if(month.equals("Feb"))
            return 2;
        if(month.equals("Mar"))
            return 3;
        if(month.equals("Apr"))
            return 4;
        if(month.equals("May"))
            return 5;
        if(month.equals("Jun"))
            return 6;
        if(month.equals("Jul"))
            return 7;
        if(month.equals("Aug"))
            return 8;
        if(month.equals("Sep"))
            return 9;
        if(month.equals("Oct"))
            return 10;
        if(month.equals("Nov"))
            return 11;
        if(month.equals("Dec"))
            return 12;
        
        addError("Invalid month data:" + month, ErrorLevel.FATAL);
        return 0;
    }
    
    public void allCheckedHelper(){
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
        pnrf._link().editColumns().click();
        pnrf._popUp().editColumns()._checkBox().row(1).check();
        pnrf._popUp().editColumns()._checkBox().row(2).check();
        pnrf._popUp().editColumns()._checkBox().row(3).check();
        pnrf._popUp().editColumns()._checkBox().row(4).check();
        pnrf._popUp().editColumns()._checkBox().row(5).check();
        pnrf._popUp().editColumns()._checkBox().row(6).check();
        pnrf._popUp().editColumns()._checkBox().row(7).check();
        pnrf._popUp().editColumns()._checkBox().row(8).check();
        pnrf._popUp().editColumns()._button().save().click();
        pnrf._link().logout();
    }
    
    public void someCheckedHelper(){
          pl.loginProcess(USERNAME, PASSWORD);
          PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
          ptds._link().notifications().click();
          PageNotificationsRedFlags pnrf = new PageNotificationsRedFlags();
          pnrf._link().editColumns().click();
          pnrf._popUp().editColumns()._checkBox().row(1).check();
          pnrf._popUp().editColumns()._checkBox().row(2).check();
          pnrf._popUp().editColumns()._checkBox().row(3).check();
          pnrf._popUp().editColumns()._checkBox().row(4).check();
          pnrf._popUp().editColumns()._checkBox().row(5).uncheck();
          pnrf._popUp().editColumns()._checkBox().row(6).uncheck();
          pnrf._popUp().editColumns()._checkBox().row(7).uncheck();
          pnrf._popUp().editColumns()._checkBox().row(8).uncheck();
          pnrf._popUp().editColumns()._button().save().click();
          pnrf._link().logout();
    }
}
