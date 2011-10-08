package com.inthinc.pro.selenium.testSuites;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.elements.ClickableText;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.AutomationLogins;
import com.inthinc.pro.automation.enums.LoginCapabilities;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsSafety;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;

@Ignore
public class NotificationsSafetyTest extends WebRallyTest {
    private static String USERNAME;
    private static String USERNAME_2;
    private static String PASSWORD;
    private static String PASSWORD_2;
    private static String GROUP;
    
    PageLogin pl;
    PageNotificationsRedFlags pnrf;
    PageNotificationsSafety pns;

    @BeforeClass
    public static void beforeClass(){
        List<AutomationLogins> logins = AutomationLogins.getAllBy(LoginCapabilities.NoteTesterData);
        if(logins.size() > 1){
            USERNAME = logins.get(0).getUserName();
            PASSWORD = logins.get(0).getPassword();
            GROUP = logins.get(0).getGroup();
            
            USERNAME_2 = logins.get(1).getUserName();
            PASSWORD_2 = logins.get(1).getPassword();
            
        }else{
            throw new AssertionError("Account Error, there are not enough accounts with NoteTesterData");
        }
    }
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
        pnrf.loginProcess(USERNAME_2, PASSWORD_2);
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
        pns._link().entryDriver().getFirstClickableLink().click();
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
        pns._text().entryDateTime().row(1).waitForElement();//TODO: testFailing: run manually.  what is the purpose of waitingFor row1 to show up?
        
        Iterator<TextBased> itr = pns._text().entryDateTime().iterator();
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
        
        itr = pns._text().entryDateTime().iterator();
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
        pns._link().entryVehicle().row(1).click();//TODO: testFailing: is there an entryVehicle on row1?
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
        pns._popUp().editColumns()._checkBox().row(1).assertChecked(true);//TODO: testFailing: does spaceBar() work ?
        
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
        enterKey();//TODO: testFailing: npe, does enterKey work?
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
        pns._link().sortByDateTime().assertPresence(false);//TODO: testFailing: run manually.  is sortByDateTime actually there?
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
        if(!pns._popUp().editColumns()._checkBox().row(1).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on first check box. Possible indication that .focus() or .hasFocus() does not work.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._checkBox().row(2).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on second check box. Possible indication that tabKey() does not work.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._checkBox().row(3).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on third check box. Possible indication that tabKey() does not work.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._checkBox().row(4).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on fourth check box. Possible indication that tabKey() does not work.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._checkBox().row(5).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on fifth check box. Possible indication that tabKey() does not work.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._checkBox().row(6).hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on sixth check box. Possible indication that tabKey() does not work.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._button().save().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on save button. Possible indication that tabKey() does not work.", ErrorLevel.FATAL);
        }
        tabKey();
        if(!pns._popUp().editColumns()._button().cancel().hasFocus()){
            addError("Incorrect Focus", "Focus is expected to be on cancel button. Possible indication that tabKey() does not work.", ErrorLevel.FATAL);
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
            addError("Driving Style event not present to test with.", ErrorLevel.INCONCLUSIVE);
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
            addError("Seat Belt event not present to test with.", ErrorLevel.INCONCLUSIVE);
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
            addError("Speeding event not present to test with.", ErrorLevel.INCONCLUSIVE);
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
        pns._dropDown().statusFilter().select("included");//TODO: testFailing: is "included" actually an option? run manually
        pns._button().refresh().click();
        pause(10, "Wait for page to load.");
        String date = pns._text().entryDateTime().row(1).getText();
        String detail = pns._text().entryDetail().row(1).getText();
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
        assertStringContains("exc", pns._link().entryStatus().row(1).getText());//TODO: testFailing: run manually.  did click put us someplace unexpected?  or were we not able to click?
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
        if(!todayCal.compareDays(pns._text().entryDateTime().row(1).getText())){//TODO: testFailing: NPE: getText() looks to be sending null?
            addError("Today's date does not match today's date on the portal.", ErrorLevel.FATAL);
        }
        todayCal.addToDay(-1);
        pns._dropDown().timeFrame().selectPartMatch("Yesterday");
        pns._button().refresh().click();
        pause(10, "Wait for refresh.");
        
        if(!todayCal.compareDays(pns._text().entryDateTime().row(1).getText())){
            addError("Yesterday's date does not match yesterday's date on the portal.", ErrorLevel.FATAL);
        }
    }
    
    public TextField searchHeader(int i){
        TextField[] searchHeaders = {pns._textField().group(), pns._textField().driver(), pns._textField().vehicle()};

        return searchHeaders[i];
    }
    
    public String searchText(int i){
        int firstDriver = 1;
        ClickableText theFirstClickableDriver = pns._link().entryDriver().getFirstClickableLink();
        while(!pns._link().entryDriver().row(firstDriver).isClickable()){//TODO: testFailing: same loop problem ? try iterator instead
            firstDriver++;
        }
        String[] searchStrings = {(String) pns._link().entryGroup().row(1).getText().substring(0, 3),
                (String) theFirstClickableDriver.getText().substring(0, 3), 
                (String) pns._link().entryVehicle().row(1).getText().substring(0, 3)};
        return searchStrings[i];
    }
    
    private String badSearchText(int i){
        return "ZZZZZZZZ";
    }
    
    private TextTableLink searchValues(int i){
        TextTableLink[] tableValues = {pns._link().entryGroup(), pns._link().entryDriver(), pns._link().entryVehicle()};
        return tableValues[i];
    }
    
    private void allCheckedHelper(){
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
    
    private void someCheckedHelper(){
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
}
