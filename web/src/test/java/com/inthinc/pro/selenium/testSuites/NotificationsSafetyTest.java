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
import com.inthinc.pro.selenium.pageObjects.PageNotificationsSafety;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;


public class NotificationsSafetyTest extends WebRallyTest {
    String USERNAME = "dastardly";
    String USERNAME_TOP = "pitstop";
    String USERNAME_2 = "CaptainNemo";
    String PASSWORD = "Muttley";
    String GROUP = "Test Group WR";
    PageLogin pl;
    PageNotificationsRedFlags pnrf;
    PageNotificationsSafety pns;

    @Before
    public void before(){
        pl = new PageLogin();
        pnrf = new PageNotificationsRedFlags();
        pns = new PageNotificationsSafety();
    }
    
    @Test
    public void bookmarkEntryTest1475(){
        set_test_case("TC1475");

        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        savePageLink();
        String correctURL = pnrf.getCurrentLocation();
        pnrf._link().logout().click();
        openSavedPage();
        pnrf.loginProcess(USERNAME, PASSWORD);
        assertStringContains(correctURL, ptds.getCurrentLocation());
    }
    
    @Test
    public void bookmarkEntryDifferentAccountTest1476(){
        set_test_case("TC1476");
        
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        savePageLink();
        String correctURL = pnrf.getCurrentLocation();
        pnrf._link().logout().click();
        pnrf.loginProcess(USERNAME_2, PASSWORD);
        String team2 = ptds._text().teamName().getText();
        openSavedPage();
        assertStringContains(correctURL, ptds.getCurrentLocation());
        assertStringContains(team2, pns._dropDown().team().getText(2));
        
    }
    
    @Test
    public void driverLinkTest1473(){
        set_test_case("TC1473");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._dropDown().team().selectPartMatch(GROUP);
        pns._button().refresh().click();
        pause(10, "Wait for page to load.");
        int i = 1;
        while(!pns._link().entryDriver().row(i).isClickable()){
            i++;
        }
        pns._link().entryDriver().row(i).click();
        assertStringContains("app/driver", pns.getCurrentLocation());
    }
    
    @Test
    public void emailTest1478(){
        //set_test_case("TC1474");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._button().tools().click();
        pns._button().emailReport().click();
        //TODO .clear does not work currently.
        pns._popUp().emailReport()._textField().emailAddresses().clear();
        pns._popUp().emailReport()._textField().emailAddresses().type("fail@a.com");
        //TODO Update when email can be checked.
    }
    
    @Test
    public void locationMapLinkTest1482(){
      //set_test_case("TC1482");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._dropDown().team().selectPartMatch(GROUP);
        pns._button().refresh().click();
        pause(10, "Wait for page to load.");
        pns._button().eventLocation().row(1).click();
        //TODO Location map pop-up verify.
        //pns._popUp().
    }
    
    @Test
    public void searchTest1484(){
        set_test_case("TC1484");
        allCheckedHelper();
        int length = 3;
        
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        pns._dropDown().team().selectPartMatch(GROUP);
        pns._button().refresh().click();
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
    public void tablePropertiesTest1486(){
        
        set_test_case("TC1486");
        allCheckedHelper();
        String currentText = "";
        AutomationCalendar currentDate = null;
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();

        pns._dropDown().team().selectPartMatch(GROUP);
        pns._button().refresh().click();
        pns._text().dateTimeEntry().row(1).waitForElement();
        
        Iterator<TextBased> itr = pns._text().dateTimeEntry().iterator();
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
        
        pns._link().sortByDateTime().click();
        pause(5, "Wait for refresh.");
        
        itr = pns._text().dateTimeEntry().iterator();
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
        
        pns._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        
        Iterator<ClickableTextBased> citr = pns._link().entryDriver().iterator();
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
        
        pns._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pns._link().entryDriver().iterator();
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
        
        pns._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pns._link().entryGroup().iterator();
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
        
        pns._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pns._link().entryGroup().iterator();
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
        
        
        pns._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pns._link().entryVehicle().iterator();
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
        
        pns._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pns._link().entryVehicle().iterator();
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
    public void toolsButtonTest1487(){
        set_test_case("TC1487");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._button().tools().click();
        pns._button().emailReport().assertPresence(true);
        pns._button().exportToPDF().assertPresence(true);
        pns._button().exportToExcel().assertPresence(true);
    }
    
    @Test
    public void safetyUITest1488(){
        set_test_case("TC1488");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._dropDown().team().assertPresence(true);
        pns._dropDown().timeFrame().assertPresence(true);
        pns._button().refresh().assertPresence(true);
        pns._link().editColumns().assertPresence(true);
        pns._button().tools().assertPresence(true);
        pns._text().counter().assertPresence(true);
        pns._text().counter().assertEquals("Showing 0 to 0 of 0 records");
        pns._link().sortByDateTime().assertPresence(true);
        pns._link().sortByGroup().assertPresence(true);
        pns._link().sortByDriver().assertPresence(true);
        pns._link().sortByVehicle().assertPresence(true);
        pns._text().headerCategory().assertPresence(true);
        pns._text().headerDetail().assertPresence(true);
        pns._text().headerStatus().assertPresence(true);
        
    }
    
    @Test
    public void vehicleLinkTest1489(){
        set_test_case("TC1489");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._dropDown().team().selectPartMatch(GROUP);
        pns._button().refresh().click();
        pause(10, "Wait for page to load.");
        pns._link().entryVehicle().row(1).click();
        assertStringContains("app/vehicle", pns.getCurrentLocation());
    }
    
    @Test
    public void cancelChangesTest1491(){
        set_test_case("TC1491");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        
        boolean b1 = pns._link().sortByDateTime().isPresent();
        boolean b2 = pns._link().sortByGroup().isPresent();
        boolean b3 = pns._link().sortByDriver().isPresent();
        boolean b4 = pns._link().sortByVehicle().isPresent();
        boolean b5 = pns._text().headerCategory().isPresent();
        boolean b6 = pns._text().headerDetail().isPresent();
        
        pns._link().editColumns().click();
        pns._popUp().editColumns()._checkBox().row(1).click();
        pns._popUp().editColumns()._button().cancel().click();
        
        pns._link().sortByDateTime().assertPresence(b1);
        pns._link().sortByGroup().assertPresence(b2);
        pns._link().sortByDriver().assertPresence(b3);
        pns._link().sortByVehicle().assertPresence(b4);
        pns._text().headerCategory().assertPresence(b5);
        pns._text().headerDetail().assertPresence(b6);

    }
    
    @Test
    public void cancelNoChangesTest1492(){
        set_test_case("TC1492");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        
        boolean b1 = pns._link().sortByDateTime().isPresent();
        boolean b2 = pns._link().sortByGroup().isPresent();
        boolean b3 = pns._link().sortByDriver().isPresent();
        boolean b4 = pns._link().sortByVehicle().isPresent();
        boolean b5 = pns._text().headerCategory().isPresent();
        boolean b6 = pns._text().headerDetail().isPresent();
        
        pns._link().editColumns().click();
        pns._popUp().editColumns()._button().cancel().click();
        
        pns._link().sortByDateTime().assertPresence(b1);
        pns._link().sortByGroup().assertPresence(b2);
        pns._link().sortByDriver().assertPresence(b3);
        pns._link().sortByVehicle().assertPresence(b4);
        pns._text().headerCategory().assertPresence(b5);
        pns._text().headerDetail().assertPresence(b6);
    }
    
    @Test
    public void mouseCheckBoxSelectionTest1493(){
        set_test_case("TC1493");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._link().editColumns().click();
        
        pns._popUp().editColumns()._checkBox().row(1).click();
        pns._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        pns._popUp().editColumns()._checkBox().row(1).click();
        pns._popUp().editColumns()._checkBox().row(1).assertChecked(true);
        
        pns._popUp().editColumns()._link().entry().row(1).click();
        pns._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        pns._popUp().editColumns()._link().entry().row(1).click();
        pns._popUp().editColumns()._checkBox().row(1).assertChecked(true);
       
    }
    
    @Test
    public void spacebarCheckBoxSelectionTest1494(){
        set_test_case("TC1494");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._link().editColumns().click();
        pns._popUp().editColumns()._checkBox().row(1).focus();
        pns._popUp().editColumns()._checkBox().row(1).click();//focus toggles checkboxes currently.
        spaceBar();
        pns._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        spaceBar();
        pns._popUp().editColumns()._checkBox().row(1).assertChecked(true);
        
    }
    
    
    @Test
    public void currentSessionRetentionTest1495(){
        set_test_case("TC1495");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._link().editColumns().click();
        pns._popUp().editColumns()._checkBox().row(1).uncheck();
        pns._popUp().editColumns()._checkBox().row(4).check();
        pns._popUp().editColumns()._button().save().click();
        pns._link().reports().click();
        pns._link().notifications().click();
        pns._link().safety().click();
        
        pns._link().sortByDateTime().assertPresence(false);
        pns._link().sortByGroup().assertPresence(true);
        pns._link().sortByDriver().assertPresence(true);
        pns._link().sortByVehicle().assertPresence(true);
        pns._text().headerCategory().assertPresence(false);
        pns._text().headerDetail().assertPresence(false);
        
    }
    
    @Test
    public void enterKeyTest1496(){
        set_test_case("TC1496");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._link().editColumns().click();
        pns._popUp().editColumns()._checkBox().row(1).focus();
        pns._popUp().editColumns()._checkBox().row(1).uncheck();
        pns._popUp().editColumns()._checkBox().row(4).check();
        enterKey();
        pause(2, "Waiting for columns to update.");
        pns._link().sortByDateTime().assertPresence(false);
        pns._link().sortByGroup().assertPresence(true);
        pns._link().sortByDriver().assertPresence(true);
        pns._link().sortByVehicle().assertPresence(true);
        pns._text().headerCategory().assertPresence(false);
        pns._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void saveButtonTest1497(){
        set_test_case("TC1497");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._link().editColumns().click();
        pns._popUp().editColumns()._checkBox().row(1).uncheck();
        pns._popUp().editColumns()._checkBox().row(4).check();
        pns._popUp().editColumns()._button().save().click();
        pns._link().sortByDateTime().assertPresence(false);
        pns._link().sortByGroup().assertPresence(true);
        pns._link().sortByDriver().assertPresence(true);
        pns._link().sortByVehicle().assertPresence(true);
        pns._text().headerCategory().assertPresence(false);
        pns._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void subsequentSessionRetentionTest1498(){
        set_test_case("TC1498");
        someCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._link().editColumns().click();
        pns._popUp().editColumns()._checkBox().row(1).uncheck();
        pns._popUp().editColumns()._checkBox().row(4).check();
        pns._popUp().editColumns()._button().save().click();
        pns._link().logout().click();
        pl.loginProcess(USERNAME, PASSWORD);
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._link().sortByDateTime().assertPresence(false);
        pns._link().sortByGroup().assertPresence(true);
        pns._link().sortByDriver().assertPresence(true);
        pns._link().sortByVehicle().assertPresence(true);
        pns._text().headerCategory().assertPresence(false);
        pns._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void tabbingOrderTest1499(){
        set_test_case("TC1499");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._link().editColumns().click();
        pns._popUp().editColumns()._checkBox().row(1).focus();
        tabKey();
        if(!pns._popUp().editColumns()._checkBox().row(2).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on second check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._checkBox().row(3).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on third check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._checkBox().row(4).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on fourth check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._checkBox().row(5).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on fifth check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._checkBox().row(6).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on sixth check box.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._button().save().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on save button.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._button().cancel().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on cancel button.", ErrorLevel.FATAL);
        }
    }
    
    @Test
    public void editColumnsUITest1500(){
        set_test_case("TC1500");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        
        boolean b1 = pns._link().sortByDateTime().isPresent();
        boolean b2 = pns._link().sortByGroup().isPresent();
        boolean b3 = pns._link().sortByDriver().isPresent();
        boolean b4 = pns._link().sortByVehicle().isPresent();
        boolean b5 = pns._text().headerCategory().isPresent();
        boolean b6 = pns._text().headerDetail().isPresent();
        
        pns._link().editColumns().click();
        pns._popUp().editColumns()._checkBox().row(1).assertVisibility(true);
        pns._popUp().editColumns()._checkBox().row(2).assertVisibility(true);
        pns._popUp().editColumns()._checkBox().row(3).assertVisibility(true);
        pns._popUp().editColumns()._checkBox().row(4).assertVisibility(true);
        pns._popUp().editColumns()._checkBox().row(5).assertVisibility(true);
        pns._popUp().editColumns()._checkBox().row(6).assertVisibility(true);
        
        pns._popUp().editColumns()._checkBox().row(1).assertChecked(b1);
        pns._popUp().editColumns()._checkBox().row(2).assertChecked(b2);
        pns._popUp().editColumns()._checkBox().row(3).assertChecked(b3);
        pns._popUp().editColumns()._checkBox().row(4).assertChecked(b4);
        pns._popUp().editColumns()._checkBox().row(5).assertChecked(b5);
        pns._popUp().editColumns()._checkBox().row(6).assertChecked(b6);
        
        pns._popUp().editColumns()._button().save().assertVisibility(true);
        pns._popUp().editColumns()._button().cancel().assertVisibility(true);
    }
    
    @Test
    public void excludeLinkDrivingStyleTest1501(){
        set_test_case("TC1501");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._dropDown().team().selectPartMatch(GROUP);
        pns._button().refresh().click();
        pns._dropDown().category().select(2);
        pause(10, "Wait for page to load.");
        if(pns._link().entryStatus().row(1).isPresent()){
            pns._link().entryStatus().row(1).click();
            pause(5, "Wait for pop-up to become visible.");
            pns._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            assertStringContains("inc", pns._link().entryStatus().row(1).getText());
            pns._link().entryStatus().row(1).click();
        }
        else{
            //TODO Make the test result Inconclusive instead of Fail.
            addError("Driving Style event not present to test with.", ErrorLevel.WARN);
        }
    }
    
    @Test
    public void excludeLinkSeatBeltTest1502(){
        set_test_case("TC1502");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._dropDown().team().selectPartMatch(GROUP);
        pns._button().refresh().click();
        pns._dropDown().category().select(7);
        pause(10, "Wait for page to load.");
        if(pns._link().entryStatus().row(1).isPresent()){
            pns._link().entryStatus().row(1).click();
            pause(5, "Wait for pop-up to become visible.");
            pns._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            assertStringContains("inc", pns._link().entryStatus().row(1).getText());
            pns._link().entryStatus().row(1).click();
        }
        else{
            addError("Seat Belt event not present to test with.", ErrorLevel.WARN);
        }
    }
    
    @Test
    public void excludeLinkSpeedingTest1503(){
        set_test_case("TC1503");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._dropDown().team().selectPartMatch(GROUP);
        pns._button().refresh().click();
        pns._dropDown().category().select(6);
        pause(10, "Wait for page to load.");
        if(pns._link().entryStatus().row(1).isPresent()){
            pns._link().entryStatus().row(1).click();
            pause(5, "Wait for pop-up to become visible.");
            pns._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            assertStringContains("inc", pns._link().entryStatus().row(1).getText());
            pns._link().entryStatus().row(1).click();
        }
        else{
            addError("Speeding event not present to test with.", ErrorLevel.WARN);
        }
    }
    
    @Test
    public void excludeLinkUITest1504(){
        set_test_case("TC1504");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._dropDown().team().selectPartMatch(GROUP);
        pns._dropDown().statusFilter().select("included");
        pns._button().refresh().click();
        pause(10, "Wait for page to load.");
        String date = pns._text().dateTimeEntry().row(1).getText();
        String detail = pns._text().detailEntry().row(1).getText();
        pns._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        assertStringContains(date, pns._popUp().excludeEvent()._text().message().getText());
        assertStringContains(detail, pns._popUp().excludeEvent()._text().message().getText());
        pns._popUp().excludeEvent()._button().yes().assertVisibility(true);
        pns._popUp().excludeEvent()._button().no().assertVisibility(true);
    }
    
    @Test
    public void includeLinkTest5738(){
        set_test_case("TC5738");
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._dropDown().team().selectPartMatch(GROUP);
        pns._button().refresh().click();
        pause(10, "Wait for page to load.");
        pns._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        pns._popUp().excludeEvent()._button().yes().click();
        pause(10, "Wait for page to load.");
        pns._link().entryStatus().row(1).click();
        pause(5, "Wait for event to re-include.");
        assertStringContains("exc", pns._link().entryStatus().row(1).getText());
    }
    
    @Test
    public void timeFrameTest5743(){
        set_test_case("TC5743");
        
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        pns._dropDown().team().selectPartMatch(GROUP);
        pns._button().refresh().click();
        pause(5, "Wait for refresh.");
        AutomationCalendar todayCal = new AutomationCalendar(WebDateFormat.NOTE_DATE_TIME);
        if(!todayCal.compareDays(pns._text().dateTimeEntry().row(1).getText())){
            addError("Today's date does not match today's date on the portal.", ErrorLevel.FATAL);
        }
        todayCal.addToDay(-1);
        pns._dropDown().timeFrame().selectPartMatch("Yesterday");
        pns._button().refresh().click();
        pause(10, "Wait for refresh.");
        
        if(!todayCal.compareDays(pns._text().dateTimeEntry().row(1).getText())){
            addError("Yesterday's date does not match yesterday's date on the portal.", ErrorLevel.FATAL);
        }
    }
    
    public TextField searchHeader(int i){
        TextField[] searchHeaders = {pns._textField().group(), pns._textField().driver(), pns._textField().vehicle()};

        return searchHeaders[i];
    }
    
    public String searchText(int i){
        int firstDriver = 1;
        while(!pns._link().entryDriver().row(firstDriver).isClickable()){
            firstDriver++;
        }
        String[] searchStrings = {(String) pns._link().entryGroup().row(1).getText().substring(0, 3),
                (String) pns._link().entryDriver().row(firstDriver).getText().substring(0, 3), 
                (String) pns._link().entryVehicle().row(1).getText().substring(0, 3)};
        return searchStrings[i];
    }
    
    public String badSearchText(int i){
        return "ZZZZZZZZ";
    }
    
    public TextTableLink searchValues(int i){
        TextTableLink[] tableValues = {pns._link().entryGroup(), pns._link().entryDriver(), pns._link().entryVehicle()};
        return tableValues[i];
    }
    
    public void allCheckedHelper(){
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().safety().click();
        
        pns._link().editColumns().click();
        pns._popUp().editColumns()._checkBox().row(1).check();
        pns._popUp().editColumns()._checkBox().row(2).check();
        pns._popUp().editColumns()._checkBox().row(3).check();
        pns._popUp().editColumns()._checkBox().row(4).check();
        pns._popUp().editColumns()._checkBox().row(5).check();
        pns._popUp().editColumns()._checkBox().row(6).check();
        pns._popUp().editColumns()._button().save().click();
        pns._link().logout().click();
    }
    
    public void someCheckedHelper(){
          pl.loginProcess(USERNAME, PASSWORD);
          PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
          ptds._link().notifications().click();
          pnrf._link().safety().click();
          
          pns._link().editColumns().click();
          pns._popUp().editColumns()._checkBox().row(1).check();
          pns._popUp().editColumns()._checkBox().row(2).check();
          pns._popUp().editColumns()._checkBox().row(3).check();
          pns._popUp().editColumns()._checkBox().row(4).uncheck();
          pns._popUp().editColumns()._checkBox().row(5).uncheck();
          pns._popUp().editColumns()._checkBox().row(6).uncheck();
          pns._popUp().editColumns()._button().save().click();
          pns._link().logout().click();
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
}
