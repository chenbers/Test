package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextTableLink;
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
        while(!pnz._link().entryDriver().isClickable(i)){
            i++;
        }
        String driver = pnz._link().entryDriver().getText(i);
        pnz._link().entryDriver().click(i);
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
        allCheckedHelper();
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnz._button().eventLocation().click(1);
        //TODO Location map pop-up verify.
        //pnz._popUp().
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
            for (int j=1;j<=20;j++){
                TextTableLink currentColumn = searchValues(i);
                if (currentColumn.isPresent(j)){
                    currentColumn.validateContains(j,currentSearch);
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
                if (currentColumn.isPresent(j)){
                    currentColumn.validateContains(j,currentSearch);
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
    public int compareDates(String date1, String date2){
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
    public void tablePropertiesTest5719(){
        
        set_test_case("TC5719");
        allCheckedHelper();
        String currentText;
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().zones().click();
        pnz._dropDown().team().selectPartMatch(GROUP);
        pnz._button().refresh().click();
        pause(5, "Wait for refresh.");
        
        currentText = "";
        if(pnz._text().dateTimeEntry().isPresent(1)){
            currentText = pnz._text().dateTimeEntry().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnz._text().dateTimeEntry().isPresent(index)){
                String newText = pnz._text().dateTimeEntry().getText(index);
                if(compareDates(currentText, newText) < 0){
                    print(currentText);
                    print(newText);
                    addError("Dates out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnz._link().sortByDateTime().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnz._text().dateTimeEntry().isPresent(1)){
            currentText = pnz._text().dateTimeEntry().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnz._text().dateTimeEntry().isPresent(index)){
                String newText = pnz._text().dateTimeEntry().getText(index);
                if(compareDates(currentText, newText) > 0){
                    addError("Dates out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnz._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnz._link().entryDriver().isPresent(1)){
            currentText = pnz._link().entryDriver().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnz._link().entryDriver().isPresent(index)){
                String newText = pnz._link().entryDriver().getText(index);
                if(currentText.compareToIgnoreCase(newText) > 0){
                    addError("Drivers out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnz._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnz._link().entryDriver().isPresent(1)){
            currentText = pnz._link().entryDriver().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnz._link().entryDriver().isPresent(index)){
                String newText = pnz._link().entryDriver().getText(index);
                if(currentText.compareToIgnoreCase(newText) < 0){
                    addError("Drivers out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnz._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnz._link().entryGroup().isPresent(1)){
            currentText = pnz._link().entryGroup().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnz._link().entryGroup().isPresent(index)){
                String newText = pnz._link().entryGroup().getText(index);
                if(currentText.compareToIgnoreCase(newText) > 0){
                    addError("Groups out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnz._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnz._link().entryGroup().isPresent(1)){
            currentText = pnz._link().entryGroup().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnz._link().entryGroup().isPresent(index)){
                String newText = pnz._link().entryGroup().getText(index);
                if(currentText.compareToIgnoreCase(newText) < 0){
                    addError("Groups out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        
        pnz._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnz._link().entryVehicle().isPresent(1)){
            currentText = pnz._link().entryVehicle().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnz._link().entryVehicle().isPresent(index)){
                String newText = pnz._link().entryVehicle().getText(index);
                if(currentText.compareToIgnoreCase(newText) > 0){
                    addError("Vehicles out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnz._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnz._link().entryVehicle().isPresent(1)){
            currentText = pnz._link().entryVehicle().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnz._link().entryVehicle().isPresent(index)){
                String newText = pnz._link().entryVehicle().getText(index);
                if(currentText.compareToIgnoreCase(newText) < 0){
                    addError("Vehicles out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
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
        String vehicle = pnz._link().entryVehicle().getText(1);
        pnz._link().entryVehicle().click(1);
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
        pnz._popUp().editColumns()._checkBox().click(1);
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
        
        pnz._popUp().editColumns()._checkBox().click(1);
        pnz._popUp().editColumns()._checkBox().assertChecked(1, false);
        pnz._popUp().editColumns()._checkBox().click(1);
        pnz._popUp().editColumns()._checkBox().assertChecked(1, true);
        
        pnz._popUp().editColumns()._link().entry().click(1);
        pnz._popUp().editColumns()._checkBox().assertChecked(1, false);
        pnz._popUp().editColumns()._link().entry().click(1);
        pnz._popUp().editColumns()._checkBox().assertChecked(1, true);
       
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
        pnz._popUp().editColumns()._checkBox().focus(1);
        pnz._popUp().editColumns()._checkBox().click(1);//focus toggles checkboxes currently.
        spaceBar();
        pnz._popUp().editColumns()._checkBox().assertChecked(1, false);
        spaceBar();
        pnz._popUp().editColumns()._checkBox().assertChecked(1, true);
        
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
        pnz._popUp().editColumns()._checkBox().uncheck(1);
        pnz._popUp().editColumns()._checkBox().check(4);
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
        pnz._popUp().editColumns()._checkBox().focus(1);
        pnz._popUp().editColumns()._checkBox().uncheck(1);
        pnz._popUp().editColumns()._checkBox().check(4);
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
        pnz._popUp().editColumns()._checkBox().uncheck(1);
        pnz._popUp().editColumns()._checkBox().check(4);
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
        pnz._popUp().editColumns()._checkBox().uncheck(1);
        pnz._popUp().editColumns()._checkBox().check(4);
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
        pnz._popUp().editColumns()._checkBox().focus(1);
        tabKey();
        if(!pnz._popUp().editColumns()._checkBox().hasFocus(2)){
            addError("Incorrect Focus", "Focus is expected to be on second check box.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._checkBox().hasFocus(3)){
            addError("Incorrect Focus", "Focus is expected to be on third check box.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._checkBox().hasFocus(4)){
            addError("Incorrect Focus", "Focus is expected to be on fourth check box.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._checkBox().hasFocus(5)){
            addError("Incorrect Focus", "Focus is expected to be on fifth check box.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._checkBox().hasFocus(6)){
            addError("Incorrect Focus", "Focus is expected to be on sixth check box.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._button().save().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on save button.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnz._popUp().editColumns()._button().cancel().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on cancel button.", ErrorLevel.FAIL);
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
        pnz._popUp().editColumns()._checkBox().assertVisibility(1, true);
        pnz._popUp().editColumns()._checkBox().assertVisibility(2, true);
        pnz._popUp().editColumns()._checkBox().assertVisibility(3, true);
        pnz._popUp().editColumns()._checkBox().assertVisibility(4, true);
        pnz._popUp().editColumns()._checkBox().assertVisibility(5, true);
        pnz._popUp().editColumns()._checkBox().assertVisibility(6, true);
        
        pnz._popUp().editColumns()._checkBox().assertChecked(1, b1);
        pnz._popUp().editColumns()._checkBox().assertChecked(2, b2);
        pnz._popUp().editColumns()._checkBox().assertChecked(3, b3);
        pnz._popUp().editColumns()._checkBox().assertChecked(4, b4);
        pnz._popUp().editColumns()._checkBox().assertChecked(5, b5);
        pnz._popUp().editColumns()._checkBox().assertChecked(6, b6);
        
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
        pnz._link().entryStatus().click(1);
        pause(5, "Wait for pop-up to become visible.");
        pnz._popUp().excludeEvent()._button().yes().click();
        pause(10, "Wait for page to load.");
        assertStringContains("inc", pnz._link().entryStatus().getText(1));
        pnz._link().entryStatus().click(1);
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
        pnz._link().entryStatus().click(1);
        pause(5, "Wait for pop-up to become visible.");
        pnz._popUp().excludeEvent()._button().yes().click();
        pause(10, "Wait for page to load.");
        assertStringContains("inc", pnz._link().entryStatus().getText(1));
        pnz._link().entryStatus().click(1);
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
        String date = pnz._text().dateTimeEntry().getText(1);
        String detail = pnz._text().detailEntry().getText(1);
        pnz._link().entryStatus().click(1);
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
        pnz._link().entryStatus().click(1);
        pause(5, "Wait for pop-up to become visible.");
        pnz._popUp().excludeEvent()._button().yes().click();
        pause(10, "Wait for page to load.");
        pnz._link().entryStatus().click(1);
        pause(5, "Wait for event to re-include.");
        assertStringContains("exc", pnz._link().entryStatus().getText(1));
    }
    
    public TextField searchHeader(int i){
        TextField[] searchHeaders = {pnz._textField().group(), pnz._textField().driver(), pnz._textField().vehicle()};

        return searchHeaders[i];
    }
    
    public String searchText(int i){
        int firstDriver = 1;
        while(!pnz._link().entryDriver().isClickable(firstDriver)){
            firstDriver++;
        }
        String[] searchStrings = {(String) pnz._link().entryGroup().getText(1).substring(0, 3),
                (String) pnz._link().entryDriver().getText(firstDriver).substring(0, 3), 
                (String) pnz._link().entryVehicle().getText(1).substring(0, 3)};
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
        pnz._popUp().editColumns()._checkBox().check(1);
        pnz._popUp().editColumns()._checkBox().check(2);
        pnz._popUp().editColumns()._checkBox().check(3);
        pnz._popUp().editColumns()._checkBox().check(4);
        pnz._popUp().editColumns()._checkBox().check(5);
        pnz._popUp().editColumns()._checkBox().check(6);
        pnz._popUp().editColumns()._button().save().click();
        pnz._link().logout().click();
    }
    
    public void someCheckedHelper(){
          pl.loginProcess(USERNAME, PASSWORD);
          PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
          ptds._link().notifications().click();
          pnrf._link().zones().click();
          
          pnz._link().editColumns().click();
          pnz._popUp().editColumns()._checkBox().check(1);
          pnz._popUp().editColumns()._checkBox().check(2);
          pnz._popUp().editColumns()._checkBox().check(3);
          pnz._popUp().editColumns()._checkBox().uncheck(4);
          pnz._popUp().editColumns()._checkBox().uncheck(5);
          pnz._popUp().editColumns()._checkBox().uncheck(6);
          pnz._popUp().editColumns()._button().save().click();
          pnz._link().logout().click();
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
        
        addError("Invalid month data:" + month, ErrorLevel.FAIL);
        return 0;
    }
}
