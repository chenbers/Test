package com.inthinc.pro.selenium.testSuites;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.enums.AutomationLogins;
import com.inthinc.pro.automation.enums.LoginCapabilities;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsDiagnostics;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;

/**
 * depends:
 * -NoteTesterGeneration.java must have run for many of these tests to work
 * -logins used for these tests must have access to the drivers/vehicles/devices used in NoteTesterGeneration
 *
 */
@Ignore //TODO: un-ignore once rally vs hudson results but fixed
public class NotificationsDiagnosticsTest extends WebRallyTest {
    private static String USERNAME;        
    private static String USERNAME_2;    
    private static String PASSWORD;          
    private static String PASSWORD_2;        
    private static String GROUP;          
    
    private PageLogin pl;
    private PageNotificationsRedFlags pnrf;
    private PageNotificationsDiagnostics pnd;
    
    @BeforeClass
    public static void beforeClass() {
        List<AutomationLogins> logins = AutomationLogins.getAllBy(LoginCapabilities.NoteTesterData);
        if(logins.size() > 1){
            USERNAME = logins.get(0).getUserName();
            PASSWORD = logins.get(0).getPassword();
            GROUP = logins.get(0).getGroup();
            
            USERNAME_2 = logins.get(1).getUserName();
            PASSWORD_2 = logins.get(1).getPassword();
            
        }else{
            addError("Account Error", "there are not enough accounts with NoteTesterData", ErrorLevel.FAIL);
        }     
    }

    @Before
    public void before(){
        pl = new PageLogin();
        pnrf = new PageNotificationsRedFlags();
        pnd = new PageNotificationsDiagnostics();
    }
    
    @Test
    public void bookmarkEntryTest1368(){
        set_test_case("TC1368");

        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        savePageLink();
        String correctURL = pnrf.getCurrentLocation();
        pnrf._link().logout().click();
        openSavedPage();
        pnrf.loginProcess(USERNAME, PASSWORD);
        assertStringContains(correctURL, ptds.getCurrentLocation());
    }

    /**
     * Notifications > Diagnostics - Bookmark Entry to Different Account. 
     * NOTE: TC1369 as it is currently written in Rally does NOT work. This test only validates that if user 1
     * bookmarks the Notifications > Diagnostics page, any user can use that bookmark to directly navigate to said same page. depends: -uses two different accounts
     */
    @Test
    public void bookmarkEntryDifferentAccountTest1369() {
        set_test_case("TC1369");

        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        savePageLink();
        String correctURL = pnrf.getCurrentLocation();
        pnrf._link().logout().click();
        pnrf.loginProcess(USERNAME_2, PASSWORD_2);
        openSavedPage();
        assertStringContains(correctURL, ptds.getCurrentLocation());
        pnd.verifyOnPage();
        pnd._textField().masterSearch().assertEquals("");
    }
    
    /**
     * depends:
     * -GROUP must be accessible for USERNAME
     * -NoteTesterGeneration must be run for GROUP
     */
    @Test
    public void driverLinkTest1371(){
      set_test_case("TC1371");
        allCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        pnrf._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(GROUP);
        pnd._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnd._link().entryDriver().getFirstClickableLink().click();
        assertStringContains("app/driver", pnd.getCurrentLocation());
    }
    
    @Test
    @Ignore
    public void emailTest1373(){
        set_test_case("TC1373");
        pl.loginProcess(USERNAME, PASSWORD);
        pnrf._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._button().tools().click();
        pnd._button().emailReport().click();
        //TODO .clear does not work currently.
        pnd._popUp().emailReport()._textField().emailAddresses().clear();
        pnd._popUp().emailReport()._textField().emailAddresses().type("fail@a.com");
        //TODO Update when email can be checked.
    }
    
    @Test
    @Ignore
    public void locationMapLinkTest1377(){
        set_test_case("TC1377");
        allCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        pnrf._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(GROUP);
        pnd._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnd._button().eventLocation().row(1).click();
        //TODO Location map pop-up verify.
        //pnd._popUp().
    }
    
    /**
     * depends:
     * -GROUP must be accessible for USERNAME
     * -NoteTesterGeneration must be run for GROUP
     */
    @Test
    @Ignore //TODO: only temporarily ignored
    public void searchTest1379(){
        set_test_case("TC1379");
        allCheckedHelper(USERNAME, PASSWORD);
        int length = 3;
        
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        pnd._dropDown().team().selectPartMatch(GROUP);
        pnd._button().refresh().click();
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
    
    
    /**
     * depends:
     * -GROUP must be accessible for USERNAME
     * -NoteTesterGeneration must be run for GROUP
     */
    @Test
    @Ignore //TODO: only temporarily ignored
    public void tablePropertiesTest1381(){
        
        set_test_case("TC1381");
        allCheckedHelper(USERNAME, PASSWORD);
        String currentText;
        AutomationCalendar currentDate = null;
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        pnd._dropDown().team().selectPartMatch(GROUP);
        pnd._button().refresh().click();
        pnd._text().entryDateTime().row(1).waitForElement();
        
        currentText = "";
        
        Iterator<TextBased> itr = pnd._text().entryDateTime().iterator();
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
        
        pnd._link().sortByDateTime().click();
        pause(5, "Wait for refresh.");
        
        itr = pnd._text().entryDateTime().iterator();
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
        
        pnd._link().sortByDriver().click();
        pause(5, "Wait for refresh.");

        currentText = "";
        
        Iterator<ClickableTextBased> citr = pnd._link().entryDriver().iterator();
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
        
        pnd._link().sortByDriver().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnd._link().entryDriver().iterator();
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
        
        pnd._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnd._link().entryGroup().iterator();
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
        
        pnd._link().sortByGroup().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnd._link().entryGroup().iterator();
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
        
        
        pnd._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnd._link().entryVehicle().iterator();
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
        
        pnd._link().sortByVehicle().click();
        pause(5, "Wait for refresh.");
        currentText = "";
        citr = pnd._link().entryVehicle().iterator();
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
    @Ignore //TODO: only temporarily ignored
    public void toolsButtonTest1382(){
        set_test_case("TC1382");
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._button().tools().click();
        pnd._button().emailReport().assertPresence(true);
        pnd._button().exportToPDF().assertPresence(true);
        pnd._button().exportToExcel().assertPresence(true);
    }
    
    @Test
    @Ignore //TODO: only temporarily ignored
    public void diagnosticsUITest1383(){
        set_test_case("TC1383");
        allCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
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
    
    /**
     * Tests that the Vehicle Link navigates to the Vehicle performance page
     * depends: 
     * -NoteTesterGeneration.java must have run for GROUP
     * -GROUP must be accessible for USERNAME
     */
    @Test
    @Ignore //TODO: only temporarily ignored
    public void vehicleLinkTest1384(){
        set_test_case("TC1384");
        allCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(GROUP);
        pnd._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnd._link().entryVehicle().row(1).click();
        PageVehiclePerformance pvp = new PageVehiclePerformance();
        pvp.validate();
    }
    
    @Test
    @Ignore //TODO: only temporarily ignored
    public void cancelChangesTest1386(){
        set_test_case("TC1386");
        pl.loginProcess(USERNAME, PASSWORD);
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
        pnd._popUp().editColumns()._checkBox().row(1).click();
        pnd._popUp().editColumns()._button().cancel().click();
        
        pnd._link().sortByDateTime().assertPresence(b1);
        pnd._link().sortByGroup().assertPresence(b2);
        pnd._link().sortByDriver().assertPresence(b3);
        pnd._link().sortByVehicle().assertPresence(b4);
        pnd._text().headerCategory().assertPresence(b5);
        pnd._text().headerDetail().assertPresence(b6);

    }
    
    @Test
    @Ignore //TODO: only temporarily ignored
    public void cancelNoChangesTest1387(){
        set_test_case("TC1387");
        pl.loginProcess(USERNAME, PASSWORD);
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
    @Ignore //TODO: only temporarily ignored
    public void mouseCheckBoxSelectionTest1388(){
        set_test_case("TC1388");
        someCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        
        pnd._popUp().editColumns()._checkBox().row(1).click();
        pnd._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        pnd._popUp().editColumns()._checkBox().row(1).click();
        pnd._popUp().editColumns()._checkBox().row(1).assertChecked(true);
        
        pnd._popUp().editColumns()._link().entry().row(1).click();
        pnd._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        pnd._popUp().editColumns()._link().entry().row(1).click();
        pnd._popUp().editColumns()._checkBox().row(1).assertChecked(true);
       
    }
    
    @Test
    @Ignore //TODO: only temporarily ignored
    public void spacebarCheckBoxSelectionTest1389(){
        set_test_case("TC1389");
        someCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().row(1).focus();
        pnd._popUp().editColumns()._checkBox().row(1).click();//focus toggles checkboxes currently.
        spaceBar();
        pnd._popUp().editColumns()._checkBox().row(1).assertChecked(false);
        spaceBar();
        pnd._popUp().editColumns()._checkBox().row(1).assertChecked(true);//TODO: testFailing: dtanner: does the spaceBar() method WORK?
        
    }
    
    
    @Test
    @Ignore //TODO: only temporarily ignored
    public void currentSessionRetentionTest1390(){
        set_test_case("TC1390");
        someCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().row(1).uncheck();
        pnd._popUp().editColumns()._checkBox().row(4).check();
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
    @Ignore
    public void enterKeyTest1391(){
        set_test_case("TC1391");
        someCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().row(1).focus();
        pnd._popUp().editColumns()._checkBox().row(1).uncheck();
        pnd._popUp().editColumns()._checkBox().row(4).check();
        enterKey();
        pause(2, "Waiting for columns to update.");
        pnd._link().sortByDateTime().assertPresence(false);//TODO: testFailing: dtanner: looks like it is because enterKey() doesn't work???
        pnd._link().sortByGroup().assertPresence(true);
        pnd._link().sortByDriver().assertPresence(true);
        pnd._link().sortByVehicle().assertPresence(true);
        pnd._text().headerCategory().assertPresence(false);
        pnd._text().headerDetail().assertPresence(false);
    }
    
    @Test
    @Ignore //TODO: only temporarily ignored
    public void saveButtonTest1392(){
        set_test_case("TC1392");
        someCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().row(1).uncheck();
        pnd._popUp().editColumns()._checkBox().row(4).check();
        pnd._popUp().editColumns()._button().save().click();
        pnd._link().sortByDateTime().assertPresence(false);
        pnd._link().sortByGroup().assertPresence(true);
        pnd._link().sortByDriver().assertPresence(true);
        pnd._link().sortByVehicle().assertPresence(true);
        pnd._text().headerCategory().assertPresence(false);
        pnd._text().headerDetail().assertPresence(false);
    }
    
    @Test
    @Ignore //TODO: only temporarily ignored
    public void subsequentSessionRetentionTest1393(){
        set_test_case("TC1393");
        someCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().row(1).uncheck();
        pnd._popUp().editColumns()._checkBox().row(4).check();
        pnd._popUp().editColumns()._button().save().click();
        pnd._link().logout().click();
        pl.loginProcess(USERNAME, PASSWORD);
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
    @Ignore
    public void tabbingOrderTest1394(){
        set_test_case("TC1394");
        
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().row(1).focus();
        if(!pnd._popUp().editColumns()._checkBox().row(1).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on second check box.", ErrorLevel.FATAL);
        }
        tabKey();
        pause(10, "");
        if(!pnd._popUp().editColumns()._checkBox().row(2).hasFocus()){//TODO: dtanner: testFailing: does tabKey() work ?
            addError("Incorrect Focus", "Focus is expected to be on second check box. Possible that tabKey() didn't work.", ErrorLevel.ERROR);
            pnd._popUp().editColumns()._checkBox().row(2).focus();
        }
        tabKey();
        if(!pnd._popUp().editColumns()._checkBox().row(3).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on third check box. Possible that tabKey() didn't work.", ErrorLevel.ERROR);
            pnd._popUp().editColumns()._checkBox().row(3).focus();
        }
        tabKey();
        if(!pnd._popUp().editColumns()._checkBox().row(4).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on fourth check box. Possible that tabKey() didn't work.", ErrorLevel.ERROR);
            pnd._popUp().editColumns()._checkBox().row(4).focus();
        }
        tabKey();
        if(!pnd._popUp().editColumns()._checkBox().row(5).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on fifth check box. Possible that tabKey() didn't work.", ErrorLevel.ERROR);
            pnd._popUp().editColumns()._checkBox().row(5).focus();
        }
        tabKey();
        if(!pnd._popUp().editColumns()._checkBox().row(6).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on sixth check box. Possible that tabKey() didn't work.", ErrorLevel.ERROR);
            pnd._popUp().editColumns()._checkBox().row(6).focus();
        }
        tabKey();
        if(!pnd._popUp().editColumns()._button().save().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on save button. Possible that tabKey() didn't work.", ErrorLevel.ERROR);
            pnd._popUp().editColumns()._button().save().focus();
        }
        tabKey();
        if(!pnd._popUp().editColumns()._button().cancel().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on cancel button. Possible that tabKey() didn't work.", ErrorLevel.ERROR);
        }
    }
    
    @Test
    @Ignore //TODO: only temporarily ignored
    public void editColumnsUITest1395(){
        set_test_case("TC1395");
        pl.loginProcess(USERNAME, PASSWORD);
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
        pnd._popUp().editColumns()._checkBox().row(1).assertVisibility(true);
        pnd._popUp().editColumns()._checkBox().row(2).assertVisibility(true);
        pnd._popUp().editColumns()._checkBox().row(3).assertVisibility(true);
        pnd._popUp().editColumns()._checkBox().row(4).assertVisibility(true);
        pnd._popUp().editColumns()._checkBox().row(5).assertVisibility(true);
        pnd._popUp().editColumns()._checkBox().row(6).assertVisibility(true);
        
        pnd._popUp().editColumns()._checkBox().row(1).assertChecked(b1);
        pnd._popUp().editColumns()._checkBox().row(2).assertChecked(b2);
        pnd._popUp().editColumns()._checkBox().row(3).assertChecked(b3);
        pnd._popUp().editColumns()._checkBox().row(4).assertChecked(b4);
        pnd._popUp().editColumns()._checkBox().row(5).assertChecked(b5);
        pnd._popUp().editColumns()._checkBox().row(6).assertChecked(b6);
        
        pnd._popUp().editColumns()._button().save().assertVisibility(true);
        pnd._popUp().editColumns()._button().cancel().assertVisibility(true);
    }
    
    /**
     * depends:
     * -GROUP must be accessible for USERNAME
     * -NoteTesterGeneration must be run for GROUP
     */
    @Test
    @Ignore //TODO: only temporarily ignored
    public void excludeLinkIdlingTest1397(){
        set_test_case("TC1397");
        allCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(GROUP);
        pnd._button().refresh().click();
        pnd._dropDown().category().select(5);
        pause(10, "Wait for page to load.");
        if(pnd._link().entryStatus().row(1).isPresent()){
            pnd._link().entryStatus().row(1).click();
            pause(5, "Wait for pop-up to become visible.");
            pnd._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            assertStringContains("inc", pnd._link().entryStatus().row(1).getText());
            pnd._link().entryStatus().row(1).click();
        }
        else{
            addError("Idling event not present to test with.", ErrorLevel.INCONCLUSIVE);
        }
    }
    
    /**
     * depends:
     * -GROUP must be accessible for USERNAME
     * -NoteTesterGeneration must be run for GROUP
     */
    @Test
    @Ignore //TODO: only temporarily ignored
    public void excludeLinkTamperingTest1398(){
        set_test_case("TC1398");
        allCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(GROUP);
        pnd._button().refresh().click();
        pnd._dropDown().category().select(2);
        pause(10, "Wait for page to load.");
        if(pnd._link().entryStatus().row(1).isPresent()){
            pnd._link().entryStatus().row(1).click();
            pause(5, "Wait for pop-up to become visible.");
            pnd._popUp().excludeEvent()._button().yes().click();
            pause(10, "Wait for page to load.");
            assertStringContains("inc", pnd._link().entryStatus().row(1).getText());
            pnd._link().entryStatus().row(1).click();
        }
        else{
            addError("Tampering event not present to test with.", ErrorLevel.WARN);
        }
    }
    
    /**
     * depends:
     * -GROUP must be accessible for USERNAME
     * -NoteTesterGeneration must be run for GROUP
     */
    @Test
    @Ignore //TODO: only temporarily ignored
    public void excludeLinkUITest1399(){
        set_test_case("TC1399");
        allCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(GROUP);
        pnd._dropDown().statusFilter().select("included");
        pnd._button().refresh().click();
        pause(10, "Wait for page to load.");
        String date = pnd._text().entryDateTime().row(1).getText();
        String detail = pnd._text().entryDetail().row(1).getText();
        pnd._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");

        assertStringContains(date, pnd._popUp().excludeEvent()._text().message().getText());
        assertStringContains(detail, pnd._popUp().excludeEvent()._text().message().getText());
        pnd._popUp().excludeEvent()._button().yes().assertVisibility(true);
        pnd._popUp().excludeEvent()._button().no().assertVisibility(true);
    }
    
    /**
     * depends:
     * -GROUP must be accessible for USERNAME
     * -NoteTesterGeneration must be run for GROUP
     */
    @Test
    @Ignore //TODO: only temporarily ignored
    public void includeLinkTest5737(){
        set_test_case("TC5737");
        allCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._dropDown().team().selectPartMatch(GROUP);
        pnd._button().refresh().click();
        pause(10, "Wait for page to load.");
        pnd._link().entryStatus().row(1).click();
        pause(5, "Wait for pop-up to become visible.");
        pnd._popUp().excludeEvent()._button().yes().click();
        pause(10, "Wait for page to load.");
        pnd._link().entryStatus().row(1).click();
        pause(5, "Wait for event to re-include.");
        assertStringContains("exc", pnd._link().entryStatus().row(1).getText());
    }
    
    /**
     * depends:
     * -NoteTesterGeneration must have run TODAY
     * -NoteTesterGeneration must have run YESTERDAY
     */
    @Test
    @Ignore //TODO: only temporarily ignored
    public void timeFrameTest5742() {
        set_test_case("TC5742");

        allCheckedHelper(USERNAME, PASSWORD);
        pl.loginProcess(USERNAME, PASSWORD);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        pnd._dropDown().team().selectPartMatch(GROUP);
        pnd._button().refresh().click();
        pause(5, "Wait for refresh.");
        if (!pnd._link().entryGroup().isEmpty()) {
            AutomationCalendar todayCal = new AutomationCalendar(WebDateFormat.NOTE_DATE_TIME);
            if (!todayCal.compareDays(pnd._text().entryDateTime().row(1).getText())) {
                addError("Today's date does not match today's date on the portal.", ErrorLevel.FATAL);
            }
            todayCal.addToDay(-1);
            pnd._dropDown().timeFrame().selectPartMatch("Yesterday");
            pnd._button().refresh().click();
            pause(10, "Wait for refresh.");

            if (!todayCal.compareDays(pnd._text().entryDateTime().row(1).getText())) {
                addError("Yesterday's date does not match yesterday's date on the portal.", ErrorLevel.FATAL);
            }
        } else {
            addError("TC5742 depends on NoteTesterGeneration having run today AND yesterday", ErrorLevel.INCONCLUSIVE);
        }

    }
    
    public TextField searchHeader(int i){
        TextField[] searchHeaders = {pnd._textField().group(), pnd._textField().driver(), pnd._textField().vehicle()};

        return searchHeaders[i];
    }
    
    public String searchText(int i){
        String[] searchStrings = {(String) pnd._link().entryGroup().row(1).getText().substring(0, 3),
                (String) pnd._link().entryDriver().getFirstClickableLink().getText().substring(0, 3), 
                (String) pnd._link().entryVehicle().row(1).getText().substring(0, 3)};
        return searchStrings[i];
    }
    
    public String badSearchText(int i){
        return "ZZZZZZZZ";
    }
    
    public TextTableLink searchValues(int i){
        TextTableLink[] tableValues = {pnd._link().entryGroup(), pnd._link().entryDriver(), pnd._link().entryVehicle()};
        return tableValues[i];
    }
    
    private void allCheckedHelper(String username, String password){
        pl.loginProcess(username, password);
        PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
        ptds._link().notifications().click();
        pnrf._link().diagnostics().click();
        
        pnd._link().editColumns().click();
        pnd._popUp().editColumns()._checkBox().row(1).check();
        pnd._popUp().editColumns()._checkBox().row(2).check();
        pnd._popUp().editColumns()._checkBox().row(3).check();
        pnd._popUp().editColumns()._checkBox().row(4).check();
        pnd._popUp().editColumns()._checkBox().row(5).check();
        pnd._popUp().editColumns()._checkBox().row(6).check();
        pnd._popUp().editColumns()._button().save().click();
        pnd._link().logout().click();
    }
    
    private void someCheckedHelper(String username, String password){
          pl.loginProcess(username, password);
          PageTeamDashboardStatistics ptds = new PageTeamDashboardStatistics();
          ptds._link().notifications().click();
          pnrf._link().diagnostics().click();
          
          pnd._link().editColumns().click();
          pnd._popUp().editColumns()._checkBox().row(1).check();
          pnd._popUp().editColumns()._checkBox().row(2).check();
          pnd._popUp().editColumns()._checkBox().row(3).check();
          pnd._popUp().editColumns()._checkBox().row(4).uncheck();
          pnd._popUp().editColumns()._checkBox().row(5).uncheck();
          pnd._popUp().editColumns()._checkBox().row(6).uncheck();
          pnd._popUp().editColumns()._button().save().click();
          pnd._link().logout().click();
      }
    
}
