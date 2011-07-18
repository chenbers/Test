package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsDiagnostics;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;


public class NotificationsDiagnosticsTest extends WebRallyTest {
    String CORRECT_USERNAME = "dastardly";
    String CORRECT_USERNAME_TOP = "pitstop";
    String CORRECT_PASSWORD = "Muttley";
    //TODO Add data accessible by the automation accounts.
    String DATA_USERNAME = "skumer1";
    String DATA_PASSWORD = "ROBOT_ROCK";
    String DATA_GROUP = "Skip";
    PageLogin pl;
    PageNotificationsRedFlags pnrf;
    PageNotificationsDiagnostics pnd;

    @Before
    public void before(){
        pl = new PageLogin();
        pnrf = new PageNotificationsRedFlags();
        pnd = new PageNotificationsDiagnostics();
    }
    
    @Test
    public void bookmarkEntryTest1368(){
        set_test_case("TC1368");

        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        savePageLink();
        String correctURL = pnrf.getCurrentLocation();
        pnrf._link().logout().click();
        openSavedPage();
        pnrf.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        assertStringContains(correctURL, ptds.getCurrentLocation());
    }
    
    @Test
    public void bookmarkEntryDifferentAccountTest1369(){
        set_test_case("TC1369");
        
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        savePageLink();
        String correctURL = pnrf.getCurrentLocation();
        pnrf._link().logout().click();
        pnrf.loginProcess(DATA_USERNAME, DATA_PASSWORD);
        String team2 = ptds._text().teamName().getText();
        openSavedPage();
        assertStringContains(correctURL, ptds.getCurrentLocation());
        assertStringContains(team2, pnd._dropDown().team().getText(2));
        
    }
    
    @Test
    public void driverLinkTest1371(){
      set_test_case("TC1371");
        allCheckedHelper();
        pl.loginProcess(DATA_USERNAME, DATA_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(DATA_GROUP);
        pnd._button().refresh().click();
        pause(10, "Wait for page to load.");
        int i = 1;
        while(!pnd._link().entryDriver().isClickable(i)){
            i++;
        }
        pnd._link().entryDriver().click(i);
        assertStringContains("app/driver", pnd.getCurrentLocation());
    }
    
    @Test
    public void emailTest1373(){
        //set_test_case("TC1373");
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._button().tools().click();
        pnd._button().emailReport().click();
        //TODO .clear does not work currently.
        pnd._popUp().emailReport()._textField().emailAddresses().clear();
        pnd._popUp().emailReport()._textField().emailAddresses().type("fail@a.com");
        //TODO Update when email can be checked.
    }
    
    @Test
    public void locationMapLinkTest1377(){
      //set_test_case("TC1377");
        allCheckedHelper();
        pl.loginProcess(DATA_USERNAME, DATA_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(DATA_GROUP);
        pnd._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnd._button().eventLocation().click(1);
        //TODO Location map pop-up verify.
        //pnd._popUp().
    }
    
    @Test
    public void searchTest1379(){
        set_test_case("TC1379");
        int length = 3;
        
        pl.loginProcess(DATA_USERNAME, DATA_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        pnd._dropDown().team().selectPartMatch(DATA_GROUP);
        pnd._button().refresh().click();
        
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
            return 1;
        }
        if(hour2 > hour1){
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
    public void tablePropertiesTest1381(){
        
        set_test_case("TC1381");
        String currentText;
        pl.loginProcess(DATA_USERNAME, DATA_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        pnd._dropDown().team().selectPartMatch(DATA_GROUP);
        pnd._button().refresh().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnd._text().dateTimeEntry().isPresent(1)){
            currentText = pnd._text().dateTimeEntry().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnd._text().dateTimeEntry().isPresent(index)){
                String newText = pnd._text().dateTimeEntry().getText(index);
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
        
        pnd._link().sortByDateTime().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnd._text().dateTimeEntry().isPresent(1)){
            currentText = pnd._text().dateTimeEntry().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnd._text().dateTimeEntry().isPresent(index)){
                String newText = pnd._text().dateTimeEntry().getText(index);
                if(compareDates(currentText, newText) > 0){
                    addError("Dates out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnd._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnd._link().entryDriver().isPresent(1)){
            currentText = pnd._link().entryDriver().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnd._link().entryDriver().isPresent(index)){
                String newText = pnd._link().entryDriver().getText(index);
                if(currentText.compareToIgnoreCase(newText) > 0){
                    addError("Drivers out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnd._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnd._link().entryDriver().isPresent(1)){
            currentText = pnd._link().entryDriver().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnd._link().entryDriver().isPresent(index)){
                String newText = pnd._link().entryDriver().getText(index);
                if(currentText.compareToIgnoreCase(newText) < 0){
                    addError("Drivers out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnd._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnd._link().entryGroup().isPresent(1)){
            currentText = pnd._link().entryGroup().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnd._link().entryGroup().isPresent(index)){
                String newText = pnd._link().entryGroup().getText(index);
                if(currentText.compareToIgnoreCase(newText) > 0){
                    addError("Groups out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnd._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnd._link().entryGroup().isPresent(1)){
            currentText = pnd._link().entryGroup().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnd._link().entryGroup().isPresent(index)){
                String newText = pnd._link().entryGroup().getText(index);
                if(currentText.compareToIgnoreCase(newText) < 0){
                    addError("Groups out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        
        pnd._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnd._link().entryVehicle().isPresent(1)){
            currentText = pnd._link().entryVehicle().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnd._link().entryVehicle().isPresent(index)){
                String newText = pnd._link().entryVehicle().getText(index);
                if(currentText.compareToIgnoreCase(newText) > 0){
                    addError("Vehicles out of order", ErrorLevel.ERROR);
                }
                currentText = newText;
            }
            else{
                break;
            }
        }
        
        pnd._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        if(pnd._link().entryVehicle().isPresent(1)){
            currentText = pnd._link().entryVehicle().getText(1);
        }
        for(int index = 2; index < 20; index++){
            if(pnd._link().entryVehicle().isPresent(index)){
                String newText = pnd._link().entryVehicle().getText(index);
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
    public void toolsButtonTest1382(){
        set_test_case("TC1382");
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._button().tools().click();
        pnd._button().emailReport().assertPresence(true);
        pnd._button().exportToPDF().assertPresence(true);
        pnd._button().exportToExcel().assertPresence(true);
    }
    
    @Test
    public void diagnosticsUITest1383(){
        set_test_case("TC1383");
        allCheckedHelper();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().assertPresence(true);
        pnd._dropDown().timeFrame().assertPresence(true);
        pnd._button().refresh().assertPresence(true);
        pnd._link().editColumns().assertPresence(true);
        pnd._button().tools().assertPresence(true);
        pnd._text().counter().assertPresence(true);
        pnd._text().counter().assertEquals("Showing 0 to 0 of 0 records");
        pnd._link().sortByDateTime().assertPresence(true);
        pnd._link().sortByGroup().assertPresence(true);
        pnd._link().sortByDriver().assertPresence(true);
        pnd._link().sortByVehicle().assertPresence(true);
        pnd._text().headerCategory().assertPresence(true);
        pnd._text().headerDetail().assertPresence(true);
        pnd._text().headerStatus().assertPresence(true);
        
    }
    
    @Test
    public void vehicleLinkTest1384(){
        set_test_case("TC1384");
        allCheckedHelper();
        pl.loginProcess(DATA_USERNAME, DATA_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(DATA_GROUP);
        pnd._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnd._link().entryVehicle().click(1);
        assertStringContains("app/vehicle", pnd.getCurrentLocation());
    }
    
    @Test
    public void cancelChangesTest1386(){
        set_test_case("TC1386");
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        
        boolean b1 = pnd._link().sortByDateTime().isPresent();
        boolean b2 = pnd._link().sortByGroup().isPresent();
        boolean b3 = pnd._link().sortByDriver().isPresent();
        boolean b4 = pnd._link().sortByVehicle().isPresent();
        boolean b5 = pnd._text().headerCategory().isPresent();
        boolean b6 = pnd._text().headerDetail().isPresent();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().click(1);
        pnd._popUp().editColumns()._button().cancel().click();
        
        pnd._link().sortByDateTime().assertPresence(b1);
        pnd._link().sortByGroup().assertPresence(b2);
        pnd._link().sortByDriver().assertPresence(b3);
        pnd._link().sortByVehicle().assertPresence(b4);
        pnd._text().headerCategory().assertPresence(b5);
        pnd._text().headerDetail().assertPresence(b6);

    }
    
    @Test
    public void cancelNoChangesTest1387(){
        set_test_case("TC1387");
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        
        boolean b1 = pnd._link().sortByDateTime().isPresent();
        boolean b2 = pnd._link().sortByGroup().isPresent();
        boolean b3 = pnd._link().sortByDriver().isPresent();
        boolean b4 = pnd._link().sortByVehicle().isPresent();
        boolean b5 = pnd._text().headerCategory().isPresent();
        boolean b6 = pnd._text().headerDetail().isPresent();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._button().cancel().click();
        
        pnd._link().sortByDateTime().assertPresence(b1);
        pnd._link().sortByGroup().assertPresence(b2);
        pnd._link().sortByDriver().assertPresence(b3);
        pnd._link().sortByVehicle().assertPresence(b4);
        pnd._text().headerCategory().assertPresence(b5);
        pnd._text().headerDetail().assertPresence(b6);
    }
    
    @Test
    public void mouseCheckBoxSelectionTest1388(){
        set_test_case("TC1388");
        someCheckedHelper();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        
        pnd._popUp().editColumns()._checkBox().click(1);
        pnd._popUp().editColumns()._checkBox().assertChecked(1, false);
        pnd._popUp().editColumns()._checkBox().click(1);
        pnd._popUp().editColumns()._checkBox().assertChecked(1, true);
        
        pnd._popUp().editColumns()._link().entry().click(1);
        pnd._popUp().editColumns()._checkBox().assertChecked(1, false);
        pnd._popUp().editColumns()._link().entry().click(1);
        pnd._popUp().editColumns()._checkBox().assertChecked(1, true);
       
    }
    
    @Test
    public void spacebarCheckBoxSelectionTest1389(){
        set_test_case("TC1389");
        someCheckedHelper();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().focus(1);
        pnd._popUp().editColumns()._checkBox().click(1);//focus toggles checkboxes currently.
        spaceBar();
        pnd._popUp().editColumns()._checkBox().assertChecked(1, false);
        spaceBar();
        pnd._popUp().editColumns()._checkBox().assertChecked(1, true);
        
    }
    
    
    @Test
    public void currentSessionRetentionTest1390(){
        set_test_case("TC1390");
        someCheckedHelper();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().uncheck(1);
        pnd._popUp().editColumns()._checkBox().check(4);
        pnd._popUp().editColumns()._button().save().click();
        pnd._link().reports().click();
        pnd._link().notifications().click();
        pnd._link().diagnostics().click();
        
        pnd._link().sortByDateTime().assertPresence(false);
        pnd._link().sortByGroup().assertPresence(true);
        pnd._link().sortByDriver().assertPresence(true);
        pnd._link().sortByVehicle().assertPresence(true);
        pnd._text().headerCategory().assertPresence(false);
        pnd._text().headerDetail().assertPresence(false);
        
    }
    
    @Test
    public void enterKeyTest1391(){
        set_test_case("TC1391");
        someCheckedHelper();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().focus(1);
        pnd._popUp().editColumns()._checkBox().uncheck(1);
        pnd._popUp().editColumns()._checkBox().check(4);
        enterKey();
        pause(2, "Waiting for columns to update.");
        pnd._link().sortByDateTime().assertPresence(false);
        pnd._link().sortByGroup().assertPresence(true);
        pnd._link().sortByDriver().assertPresence(true);
        pnd._link().sortByVehicle().assertPresence(true);
        pnd._text().headerCategory().assertPresence(false);
        pnd._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void saveButtonTest1392(){
        set_test_case("TC1392");
        someCheckedHelper();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().uncheck(1);
        pnd._popUp().editColumns()._checkBox().check(4);
        pnd._popUp().editColumns()._button().save().click();
        pnd._link().sortByDateTime().assertPresence(false);
        pnd._link().sortByGroup().assertPresence(true);
        pnd._link().sortByDriver().assertPresence(true);
        pnd._link().sortByVehicle().assertPresence(true);
        pnd._text().headerCategory().assertPresence(false);
        pnd._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void subsequentSessionRetentionTest1393(){
        set_test_case("TC1393");
        someCheckedHelper();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().uncheck(1);
        pnd._popUp().editColumns()._checkBox().check(4);
        pnd._popUp().editColumns()._button().save().click();
        pnd._link().logout().click();
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().sortByDateTime().assertPresence(false);
        pnd._link().sortByGroup().assertPresence(true);
        pnd._link().sortByDriver().assertPresence(true);
        pnd._link().sortByVehicle().assertPresence(true);
        pnd._text().headerCategory().assertPresence(false);
        pnd._text().headerDetail().assertPresence(false);
    }
    
    @Test
    public void tabbingOrderTest1394(){
        set_test_case("TC1394");
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().focus(1);
        tabKey();
        if(!pnd._popUp().editColumns()._checkBox().hasFocus(2)){
            addError("Incorrect Focus", "Focus is expected to be on second check box.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnd._popUp().editColumns()._checkBox().hasFocus(3)){
            addError("Incorrect Focus", "Focus is expected to be on third check box.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnd._popUp().editColumns()._checkBox().hasFocus(4)){
            addError("Incorrect Focus", "Focus is expected to be on fourth check box.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnd._popUp().editColumns()._checkBox().hasFocus(5)){
            addError("Incorrect Focus", "Focus is expected to be on fifth check box.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnd._popUp().editColumns()._checkBox().hasFocus(6)){
            addError("Incorrect Focus", "Focus is expected to be on sixth check box.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnd._popUp().editColumns()._button().save().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on save button.", ErrorLevel.FAIL);
        }
        tabKey();
        if(!pnd._popUp().editColumns()._button().cancel().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on cancel button.", ErrorLevel.FAIL);
        }
    }
    
    @Test
    public void editColumnsUITest1395(){
        set_test_case("TC1395");
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        
        boolean b1 = pnd._link().sortByDateTime().isPresent();
        boolean b2 = pnd._link().sortByGroup().isPresent();
        boolean b3 = pnd._link().sortByDriver().isPresent();
        boolean b4 = pnd._link().sortByVehicle().isPresent();
        boolean b5 = pnd._text().headerCategory().isPresent();
        boolean b6 = pnd._text().headerDetail().isPresent();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().assertVisibility(1, true);
        pnd._popUp().editColumns()._checkBox().assertVisibility(2, true);
        pnd._popUp().editColumns()._checkBox().assertVisibility(3, true);
        pnd._popUp().editColumns()._checkBox().assertVisibility(4, true);
        pnd._popUp().editColumns()._checkBox().assertVisibility(5, true);
        pnd._popUp().editColumns()._checkBox().assertVisibility(6, true);
        
        pnd._popUp().editColumns()._checkBox().assertChecked(1, b1);
        pnd._popUp().editColumns()._checkBox().assertChecked(2, b2);
        pnd._popUp().editColumns()._checkBox().assertChecked(3, b3);
        pnd._popUp().editColumns()._checkBox().assertChecked(4, b4);
        pnd._popUp().editColumns()._checkBox().assertChecked(5, b5);
        pnd._popUp().editColumns()._checkBox().assertChecked(6, b6);
        
        pnd._popUp().editColumns()._button().save().assertVisibility(true);
        pnd._popUp().editColumns()._button().cancel().assertVisibility(true);
    }
    
    @Test
    public void excludeLinkIdlingTest1397(){
        set_test_case("TC1397");
        pl.loginProcess(DATA_USERNAME, DATA_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(DATA_GROUP);
        pnd._button().refresh().click();
        pnd._dropDown().category().select(5);
        pause(10, "Wait for page to load.");
        if(pnd._link().entryStatus().isPresent(1)){
            pnd._link().entryStatus().click(1);
            pnd._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            //TODO Verify option is greyed out.
            pnd._link().entryStatus().click(1);
        }
        else{
            //TODO Make the test result Inconclusive instead of Fail.
            addError("Idling event not present to test with.", ErrorLevel.WARN);
        }
    }
    
    @Test
    public void excludeLinkTamperingTest1398(){
        set_test_case("TC1398");
        pl.loginProcess(DATA_USERNAME, DATA_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(DATA_GROUP);
        pnd._button().refresh().click();
        pnd._dropDown().category().select(2);
        pause(10, "Wait for page to load.");
        if(pnd._link().entryStatus().isPresent(1)){
            pnd._link().entryStatus().click(1);
            pnd._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            //TODO Verify option is greyed out.
            pnd._link().entryStatus().click(1);
        }
        else{
            addError("Tampering event not present to test with.", ErrorLevel.WARN);
        }
    }
    
    @Test
    public void excludeLinkUITest1399(){
        set_test_case("TC1399");
        pl.loginProcess(DATA_USERNAME, DATA_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(DATA_GROUP);
        pnd._dropDown().statusFilter().select("included");
        pnd._button().refresh().click();
        pause(10, "Wait for page to load.");
        String date = pnd._text().dateTimeEntry().getText(1);
        String detail = pnd._text().detailEntry().getText(1);
        pnd._link().entryStatus().click(1);
        assertStringContains(date, pnd._popUp().excludeEvent()._text().message().getText());
        assertStringContains(detail, pnd._popUp().excludeEvent()._text().message().getText());
        pnd._popUp().excludeEvent()._button().yes().assertVisibility(true);
        pnd._popUp().excludeEvent()._button().no().assertVisibility(true);
    }
    
    public TextField searchHeader(int i){
        TextField[] searchHeaders = {pnd._textField().group(), pnd._textField().driver(), pnd._textField().vehicle()};

        return searchHeaders[i];
    }
    
    public String searchText(int i){
        int firstDriver = 1;
        while(!pnd._link().entryDriver().isClickable(firstDriver)){
            firstDriver++;
        }
        String[] searchStrings = {(String) pnd._link().entryGroup().getText(1).substring(0, 3),
                (String) pnd._link().entryDriver().getText(firstDriver).substring(0, 3), 
                (String) pnd._link().entryVehicle().getText(1).substring(0, 3)};
        return searchStrings[i];
    }
    
    public String badSearchText(int i){
        return "ZZZZZZZZ";
    }
    
    public TextTableLink searchValues(int i){
        TextTableLink[] tableValues = {pnd._link().entryGroup(), pnd._link().entryDriver(), pnd._link().entryVehicle()};
        return tableValues[i];
    }
    
    public void allCheckedHelper(){
        pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().check(1);
        pnd._popUp().editColumns()._checkBox().check(2);
        pnd._popUp().editColumns()._checkBox().check(3);
        pnd._popUp().editColumns()._checkBox().check(4);
        pnd._popUp().editColumns()._checkBox().check(5);
        pnd._popUp().editColumns()._checkBox().check(6);
        pnd._popUp().editColumns()._button().save().click();
        pnd._link().logout().click();
    }
    
    public void someCheckedHelper(){
          pl.loginProcess(CORRECT_USERNAME, CORRECT_PASSWORD);
          PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
          ptds._link().notifications().click();
          pnrf._link().diagnostics().click();
          
          pnd._link().editColumns().click();
          pnd._popUp().editColumns()._checkBox().check(1);
          pnd._popUp().editColumns()._checkBox().check(2);
          pnd._popUp().editColumns()._checkBox().check(3);
          pnd._popUp().editColumns()._checkBox().uncheck(4);
          pnd._popUp().editColumns()._checkBox().uncheck(5);
          pnd._popUp().editColumns()._checkBox().uncheck(6);
          pnd._popUp().editColumns()._button().save().click();
          pnd._link().logout().click();
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
