package com.inthinc.pro.selenium.testSuites;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.elements.ClickableObject;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageVehicleReport;

public class Reports_Vehicles extends WebRallyTest {
    private String username = "prime";
    private String password = "password";
    private PageVehicleReport reports_vehicles;
    private PageLogin login_page;
    private PageDriverPerformance driver_performance;
    private PageTeamDashboardStatistics team_dashboard;
    private PageVehiclePerformanceSpeed vehicle_performance_speed;
    private PageVehiclePerformanceStyle vehicle_performance_style;
    private PageVehiclePerformance  vehicle_performance;
    private PageAdminUsers  admin_users;
    
    @Before
    public void setuppage() {
        reports_vehicles = new PageVehicleReport();
        login_page = new PageLogin();
        driver_performance = new PageDriverPerformance();
        team_dashboard = new PageTeamDashboardStatistics();
        vehicle_performance_speed = new PageVehiclePerformanceSpeed();
        vehicle_performance_style = new PageVehiclePerformanceStyle();
        vehicle_performance = new PageVehiclePerformance();
        admin_users = new PageAdminUsers();
    }

    @Test
    public void bookmarkEntry() {
        set_test_case("TC1614");

        //Login
        reports_vehicles.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        
        //Verify correct location
        reports_vehicles.verifyOnPage();
        
        //Get and save current URL
        savePageLink();
  
        //Logout
        reports_vehicles._link().logout().click();
        
        //Open saved URL and login
        openSavedPage();                                
        
        //Validation to check that the login page is loaded.
        login_page.verifyOnPage();
        
        //login
        login_page._textField().userName().type(username);
        login_page._textField().password().type(password);
        login_page._button().logIn().click();
        
        reports_vehicles._text().title().assertVisibility(true);
    }
    
    @Test
    public void bookmarkEntryDifferentAccount() {
        set_test_case("TC1615");
        
        //Login
        reports_vehicles.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        
        //Verify correct location.
        reports_vehicles.verifyOnPage();
        
        //Book mark page
        savePageLink();
        
        //Logout
        reports_vehicles._link().logout().click();
        
        //Login to different account.                           
        String temp_user = "0004";
        
        reports_vehicles.loginProcess(temp_user, password);
        openSavedPage();
        
        //validation of location
        reports_vehicles.verifyOnPage();
    }
    
    @Test
    public void driverLink() {              
        set_test_case("TC1617");            
      

        //login
        reports_vehicles.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();        

        int row;

        for ( row=1;row<=20;row++){
            pause(10,"");
            print(row);
            String driverID;
            if (reports_vehicles._link().driverValue().isClickable(row)){
                driverID = reports_vehicles._link().driverValue().getText(row);
                reports_vehicles._link().driverValue().click(row);
                pause(10,"");
                driver_performance.verifyOnPage();
                driver_performance._link().driverName().assertEquals(driverID);
                driver_performance._link().reports().click();
                reports_vehicles._link().vehicles().click();
                pause(10,"");
            }else if(!reports_vehicles._link().driverValue().isPresent(row)){
                break;
            }
        }

//        //Get search criteria from selected row.
//        String vehicleID = reports_vehicles._link().vehicleValue().getText(row);
//        //Extract first four characters
//        if (vehicleID.length() > 4){
//            vehicle = vehicleID.substring(0,3);
//        }else{
//            vehicle = vehicleID;
//        }
//        //search for vehicle IDs
//        reports_vehicles._textField().driverSearch().type(vehicle);
//        pause(10,"");
//        //Find a vehicle with a driver assigned to it.
//        for (int j=1;j<=20;j++){
//            pause(10,"");
//            String driverID;
//            if(reports_vehicles._link().driverValue().isPresent(j)){
//                driverID = reports_vehicles._link().driverValue().getText(j);
//                reports_vehicles._link().driverValue().click(j);
//                driver_performance.verifyOnPage();
//                driver_performance._text().overallTitle().validateContains(driverID);
//                break;
//            }else{
//                addError("No Drivers assigned to any vehicles within search criteria", ErrorLevel.ERROR );
//                print("No Drivers assigned to any vehicles within search criteria");
//                break;
//            }
        }
        
    @Test
    public void drivingStyleScoreLink() {
        set_test_case("TC1619");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();  
        
        //Search and verify present
        
        //Search loop
        String style_value;
        int row;
        for ( row=1;row<=20;row++){
            pause(10,"");
            print(row);
            if (reports_vehicles._link().styleValue().isPresent(row)){
                style_value =reports_vehicles._link().styleValue().getText(row);
                reports_vehicles._link().styleValue().click(row);
                pause(10,"");
                
                //Verify location, driver performance page.
                vehicle_performance_style.verifyOnPage();
                
                //Select 12 months option and compare values.
                vehicle_performance_style._link().duration(TimeDuration.MONTHS_12).click();
                vehicle_performance_style._text().drivingStyleScoreValue().assertEquals(style_value);//compares values
                
                //return to vehicle reports page
                vehicle_performance_style._link().reports().click();
                reports_vehicles._link().vehicles().click();
                pause(10,"");
        }else{
            break;
        }
    }
//    @Test
//    public void EmailThisReport() {       
//        set_test_case("TC1621");
//                                                                //TODO: dtanner: Currently does not function
//        //Login                                                       //because report cannot be pulled up.
//        login_page.loginProcess(username, password);
//                                                                //TODO: dmonk: change email to correct 
//        //Navigate to reports>vehicles>tools page               //      automation email. Test is not Complete.
//        reports_vehicles._link().reports().click();
//        reports_vehicles._link().vehicles().click();
//        reports_vehicles._button().emailReport().click();
//        
//        //Email report to automation email account.
//        reports_vehicles._popUp().emailReport()._textField().emailAddresses().type("automation@inthinc.com");
//        reports_vehicles._popUp().emailReport()._button().email().click();
//    }
//         
//    @Test
//    public void VehicleReportsExportedExcel(){
//        set_test_case("TC1622");
//                                                                //TODO: dtanner: Currently does not function
//        //Login                                                 //      because report cannot be pulled up.
//        login_page.loginProcess(username, password);
//                                                                //TODO: dmonk: Test is not Complete.  
//        //Navigate to reports>vehicles>tools page
//        reports_vehicles._link().reports().click();
//        reports_vehicles._link().vehicles().click();
//        reports_vehicles._button().exportExcel().click();
//    }
//    @Test
//    public void VehicleReportsExportedPDF() {
//        set_test_case("TC1623");
//                                                                //TODO: dtanner: Currently does not function
//        //Login                                                 //      because report cannot be pulled up.
//        login_page.loginProcess(username, password);
//                                                                //TODO: dmonk: Test is not Complete.  
//        //Navigate to reports>vehicles>tools page
//        reports_vehicles._link().reports().click();
//        reports_vehicles._link().vehicles().click();
//        reports_vehicles._button().exportPDF().click();
//    }
    }
    @Test
    public void groupLink(){                                  
        set_test_case("TC1624");

        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();  
        
        //Check that group link is present and is linked to correct page
        int row;
        String groupname;
        String tema;
        for ( row=1;row<=20;row++){
            print(row);
            if (reports_vehicles._link().groupValue().isPresent(row)){
                groupname = reports_vehicles._link().groupValue().getText(row);
                reports_vehicles._link().groupValue().click(row);
                pause(10,"");
                team_dashboard.verifyOnPage();
                tema = team_dashboard._text().teamName().getText();                
                print(tema);
                team_dashboard._text().teamName().assertEquals(groupname);
                print(groupname);
            }else if(!reports_vehicles._link().groupValue().isPresent(row)){
                break;
            }
        }
    }
    
//    @Test
//    public void Hoverhelp() {
//        set_test_case("TC1626");
//                                                  //TODO: dtanner: mouse over function not yet available.
//        //Login                                   //TODO: dmonk: finish writing test.
//        login_page.loginProcess(username, password);
//        
//        //Navigate to reports>vehicles page
//        reports_vehicles._link().reports().click();
//        reports_vehicles._link().vehicles().click();  
//       
//        //Use mouse over function to test drop down menu on tools button.
//        reports_vehicles._button().tools().
//    }
    @Test
    public void overallScoreLink() {
        set_test_case("TC1627");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();  
        
        //Search and verify present
        int row;
        String overall;
        for ( row=1;row<=20;row++){
            print(row);
            if (reports_vehicles._link().overallValue().isPresent(row)){
                //Compare score from vehicle reports page to score on vehicle performance page.
                overall = reports_vehicles._link().overallValue().getText(row);
                reports_vehicles._link().overallValue().click(row);
                pause(10,"");
                vehicle_performance.verifyOnPage();
                vehicle_performance._link().overallDuration(TimeDuration.MONTHS_12).click();
                vehicle_performance._text().overallScore().assertEquals(overall);
                //Return to vehicle reports page.
                vehicle_performance._link().reports().click();
                reports_vehicles._link().vehicles().click();
                pause(10,"");
            }else if(!reports_vehicles._link().overallValue().isPresent(row)){
                break;
            }
        }
    }

    @Test
    public void PageLink(){                         
        set_test_case("TC1629");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();  
        
        //click on next page of vehicles
        String sample;
        sample = reports_vehicles._link().vehicleValue().getText(1);
        reports_vehicles._page().pageIndex().forwardOne().click();
        if(reports_vehicles._link().vehicleValue().equals(sample)){
            addError("Page did not change or contains same data as previous", ErrorLevel.FAIL );
        }
    }
    
    @Test
    public void Search() {
        set_test_case("TC1634");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        
        Map<TextField, TextTable> values = new HashMap<TextField, TextTable>();
        Map<TextField, String[]> strings = new HashMap<TextField, String[]>();
        
        TextField[] columns = { reports_vehicles._textField().groupSearch(),
                reports_vehicles._textField().vehicleSearch(),
                reports_vehicles._textField().yearMakeModelSearch(),
                reports_vehicles._textField().driverSearch() };
        
        //Get search criteria for group from selected row.
        String groupID = reports_vehicles._link().groupValue().getText(1);
        //Extract first four characters
        String group;
        if (groupID.length() > 4){
            group = groupID.substring(0,3);
          }else{
          group = groupID;
          }
        
        //Get search criteria for vehicles from selected row.
        String vehicleID = reports_vehicles._link().vehicleValue().getText(1);
        //Extract first four characters
        String vehicle;
        if (vehicleID.length() > 4){
            vehicle = vehicleID.substring(0,3);
        }else{
            vehicle = vehicleID;
            }
        
        //Get search criteria for yearmakemodel from selected row.
        String yearmakemodelID = reports_vehicles._text().yearMakeModelValue().getText(1);
        //Extract first four characters
        String yearmakemodel;
        if (yearmakemodelID.length() > 4){
            yearmakemodel = yearmakemodelID.substring(0,3);
        }else{
            yearmakemodel = yearmakemodelID;
            }
        
        //Get search criteria for driverID from selected row.
        String driverID = reports_vehicles._link().driverValue().getText(1);
        //Extract first four characters
        String driver;
        if (driverID.length() > 4){
            driver = driverID.substring(0,3);
        }else{
            driver = driverID;
            }
        values.put(columns[0], reports_vehicles._link().groupValue());
        strings.put(columns[0], new String[]{groupID, group});
        
        values.put(columns[1], reports_vehicles._link().vehicleValue());
        strings.put(columns[1], new String[]{vehicleID, vehicle});

        values.put(columns[2], reports_vehicles._text().yearMakeModelValue());
        strings.put(columns[2], new String[]{yearmakemodelID, yearmakemodel});

        values.put(columns[3], reports_vehicles._link().driverValue());
        strings.put(columns[3], new String[]{driverID, driver});

        for (int i=0;i<columns.length;i++){
            String[] myStrings = strings.get(columns[i]);
            TextTable value = values.get(columns[i]);
            for (String searchString: myStrings){
                // Do some loop through all available rows
                for (int j=1;j<=20;j++){
                    pause(10,"");
                    if (value.isPresent(j)){
                        value.validateContains(j,searchString);
                    } else {
                        break;
                    }
                }
            }
        }
    }
    @Test
    public void speedScoreLink() {
        set_test_case("TC1637");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        
        //Search and verify present
        int row;
        String speed_value;
        for ( row=1;row<=20;row++){
            if (reports_vehicles._link().speedValue().isPresent(row)){
                speed_value = reports_vehicles._link().speedValue().getText(row);   
                print(speed_value);
                reports_vehicles._link().speedValue().click(row);
                pause(10,"");
                
                vehicle_performance_speed.verifyOnPage();

                vehicle_performance_speed._link().duration(TimeDuration.MONTHS_12).click();
                vehicle_performance_speed._text().mainOverall().assertEquals(speed_value);

            }else if(!reports_vehicles._link().speedValue().isPresent(row)){
                break;
            }
        }
    }
    
    @Test
    public void tableProperties() {
        set_test_case("TC1639");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        
        //Click edit columns link
        reports_vehicles._button().editColumns().assertVisibility(true);
        checkAllColumns();
        
        //Click columns once to sort by ascending order.
        reports_vehicles._link().groupSort().click();

        Map<ClickableObject, TextTable> values = new HashMap<ClickableObject, TextTable>();
        
        TextLink[] columns = { reports_vehicles._link().groupSort(),
                reports_vehicles._link().vehicleIDSort(),
                reports_vehicles._link().yearMakeModelSort(),
                reports_vehicles._link().driverSort(),
                reports_vehicles._link().distanceDrivenSort(),
                reports_vehicles._link().odometerSort(),
                reports_vehicles._link().overallSort(),
                reports_vehicles._link().speedSort(),
                reports_vehicles._link().styleSort()};
        
        values.put(columns[0], reports_vehicles._link().groupValue());
        
        values.put(columns[1], reports_vehicles._link().vehicleValue());

        values.put(columns[2], reports_vehicles._text().yearMakeModelValue());

        values.put(columns[3], reports_vehicles._link().driverValue());
        
        values.put(columns[4], reports_vehicles._text().distanceDrivenValue());
        
        values.put(columns[5], reports_vehicles._text().odometerValue());
        
        values.put(columns[6], reports_vehicles._link().overallValue());
        
        values.put(columns[7], reports_vehicles._link().speedValue());
        
        values.put(columns[8], reports_vehicles._link().styleValue());

        for (int i=0;i<columns.length;i++){
            columns[i].click();
            pause(5,"");
            TextTable value = values.get(columns[i]);
            TextTable value2 = values.get(columns[i+1]);
            // Do some loop through all available rows
            for (int j=1;j<=20;j++){
                pause(10,"");
                if (value.isPresent(j) && value.isPresent(j+1)){
                    int sorted = value.getText(j).compareToIgnoreCase(value2.getText(j+1));
                    assertTrue(sorted >= 0,"List not in alphabetical order for column: "+ columns[i].getMyEnum().toString());
                } else {
                    break;
                }
            }
            columns[i].click();
            pause(5,"");
            for (int j=1;j<=20;j++){
                pause(10,"");
                if (value.isPresent(j) && value.isPresent(j+1)){
                    int sorted = value.getText(j).compareToIgnoreCase(value2.getText(j+1));
                    assertTrue(sorted <= 0,"List not in alphabetical order for column: "+ columns[i].getMyEnum().toString());
                } else {
                    break;
                }
            }
        }
    }
    
    @Test
    public void toolsButton(){
        set_test_case("TC1640");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        
        //Check that 3 option menu appears when tools button is clicked.
        reports_vehicles._button().tools().assertVisibility(true);
        reports_vehicles._button().tools().click();
        reports_vehicles._button().emailReport().assertVisibility(true);
        reports_vehicles._button().exportPDF().assertVisibility(true);
        reports_vehicles._button().exportExcel().assertVisibility(true);
        
    }
    
    @Test
    public void uiVehicleReports(){
        set_test_case("TC1641");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        
        //Verify correct page and different attributes.
        reports_vehicles.verifyOnPage();
        reports_vehicles._button().editColumns().assertVisibility(true);
        reports_vehicles._button().tools().assertVisibility(true);
        reports_vehicles._textField().groupSearch().assertVisibility(true);
        reports_vehicles._textField().vehicleSearch().assertVisibility(true);
        reports_vehicles._textField().yearMakeModelSearch().assertVisibility(true);
        reports_vehicles._textField().driverSearch().assertVisibility(true);
        
        reports_vehicles._link().groupSort().assertVisibility(true);
        reports_vehicles._link().vehicleIDSort().assertVisibility(true);
        reports_vehicles._link().yearMakeModelSort().assertVisibility(true);
//        reports_vehicles._link().driverSort().assertVisibility(true);
        reports_vehicles._link().distanceDrivenSort().assertVisibility(true);
        reports_vehicles._link().overallSort().assertVisibility(true);
        reports_vehicles._link().speedSort().assertVisibility(true);
        reports_vehicles._link().styleSort().assertVisibility(true);
        
    }
    
    @Test
    public void vehicleIDLink(){
        set_test_case("TC1642");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        
        //Search and verify present
        int row;
        String vehicle;
        String actual;
        for ( row=1;row<=20;row++){
            if(reports_vehicles._link().vehicleValue().isPresent(row)){
                vehicle = reports_vehicles._link().vehicleValue().getText(row);
                reports_vehicles._link().vehicleValue().click(row);
                vehicle_performance.verifyOnPage();
                print(vehicle);
                actual = vehicle_performance._link().vehicleName().getText();
                print(actual);
                vehicle_performance._link().vehicleName().assertEquals(vehicle);
                vehicle_performance._link().reports().click();
                reports_vehicles._link().vehicles().click();
                pause(10,"");
            }else if(!reports_vehicles._link().vehicleValue().isPresent(row)){
                break;
            }
        }
    }
    
    @Test
    public void cancelButton(){
        set_test_case("TC1644");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();

        //Check that all columns are present
        checkAllColumns();
 
        //Click edit columns and cancel edits
        reports_vehicles._button().editColumns().click();
        reports_vehicles._popUp().editColumns()._checkBox().check(1);
        reports_vehicles._popUp().editColumns()._checkBox().check(2);
        reports_vehicles._popUp().editColumns()._checkBox().check(3);
        reports_vehicles._popUp().editColumns()._checkBox().check(4);
        reports_vehicles._popUp().editColumns()._checkBox().check(5);
        reports_vehicles._popUp().editColumns()._button().cancel().click();

        //Check that all columns are still visible.
        reports_vehicles._link().groupSort().assertPresence(true);
        reports_vehicles._link().vehicleIDSort().assertPresence(true);
        reports_vehicles._link().yearMakeModelSort().assertPresence(true);
//        reports_vehicles._link().driverSort().assertPresence(true);
        reports_vehicles._link().distanceDrivenSort().assertPresence(true);
        reports_vehicles._link().odometerSort().assertPresence(true);
        reports_vehicles._link().overallSort().assertPresence(true);
        reports_vehicles._link().speedSort().assertPresence(true);
        reports_vehicles._link().styleSort().assertPresence(true);

    }
    
    @Test
    public void cancelButtonNoChanges(){
        set_test_case("TC1645");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        checkAllColumns();
        reports_vehicles._button().editColumns().click();
        reports_vehicles._popUp().editColumns()._button().cancel().assertPresence(true);
        reports_vehicles._popUp().editColumns()._button().cancel().click();
        reports_vehicles._popUp().editColumns()._button().cancel().assertPresence(false);
        
    }
    
    @Test
    public void currentSessionRetention(){
        set_test_case("TC1648");
 
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();

        //Check that all columns are present
        checkAllColumns();
 
        //Click edit columns and cancel edits
        reports_vehicles._button().editColumns().click();
        reports_vehicles._popUp().editColumns()._checkBox().check(1);
        reports_vehicles._popUp().editColumns()._checkBox().check(2);
        reports_vehicles._popUp().editColumns()._checkBox().check(3);
        reports_vehicles._popUp().editColumns()._checkBox().check(4);
        reports_vehicles._popUp().editColumns()._checkBox().check(5);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(6);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(7);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(8);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(9);
        reports_vehicles._popUp().editColumns()._button().save().click();

        //Check that all columns are still visible.
        reports_vehicles._link().groupSort().assertPresence(true);
        reports_vehicles._link().vehicleIDSort().assertPresence(true);
        reports_vehicles._link().yearMakeModelSort().assertPresence(true);
        reports_vehicles._link().driverSort().assertPresence(true);
        reports_vehicles._link().distanceDrivenSort().assertPresence(true);
        reports_vehicles._link().odometerSort().assertPresence(false);
        reports_vehicles._link().overallSort().assertPresence(false);
        reports_vehicles._link().speedSort().assertPresence(false);
        reports_vehicles._link().styleSort().assertPresence(false);

        //Navigate away from vehicle reports page
        reports_vehicles._link().admin().click();
        admin_users.verifyOnPage();
        
        //Return to vehicle reports page and check that the same columns are present.
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
pause(10,"");
        reports_vehicles._link().groupSort().assertPresence(true);
        reports_vehicles._link().vehicleIDSort().assertPresence(true);
        reports_vehicles._link().yearMakeModelSort().assertPresence(true);
        reports_vehicles._link().driverSort().assertPresence(true);
        reports_vehicles._link().distanceDrivenSort().assertPresence(true);
        reports_vehicles._link().odometerSort().assertPresence(false);
        reports_vehicles._link().overallSort().assertPresence(false);
        reports_vehicles._link().speedSort().assertPresence(false);
        reports_vehicles._link().styleSort().assertPresence(false);

    }
    
    @Test
    public void defaultCommandButton(){
        set_test_case("TC1650");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        
        //check different columns to show in edit columns, save.
        reports_vehicles._button().editColumns().click();
        reports_vehicles._popUp().editColumns()._checkBox().check(1);
        reports_vehicles._popUp().editColumns()._checkBox().check(2);
        reports_vehicles._popUp().editColumns()._checkBox().check(3);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(4);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(5);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(6);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(7);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(8);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(9);
        reports_vehicles._popUp().editColumns()._button().save().click();

        //Check that correct columns are shown.
        reports_vehicles._link().groupSort().assertPresence(true);
        reports_vehicles._link().vehicleIDSort().assertPresence(true);
        reports_vehicles._link().yearMakeModelSort().assertPresence(true);
        reports_vehicles._link().driverSort().assertPresence(false);
        reports_vehicles._link().odometerSort().assertPresence(false);
        reports_vehicles._link().distanceDrivenSort().assertPresence(false);
        reports_vehicles._link().overallSort().assertPresence(false);
        reports_vehicles._link().speedSort().assertPresence(false);
        reports_vehicles._link().styleSort().assertPresence(false);

        //Check that the correct boxes are still check in edit columns pop-up.
        reports_vehicles._button().editColumns().click();
        reports_vehicles._popUp().editColumns()._checkBox().assertChecked(1,true);
        reports_vehicles._popUp().editColumns()._checkBox().assertChecked(2,true);
        reports_vehicles._popUp().editColumns()._checkBox().assertChecked(3,true);
        reports_vehicles._popUp().editColumns()._checkBox().assertChecked(4,false);
        reports_vehicles._popUp().editColumns()._checkBox().assertChecked(5,false);
        reports_vehicles._popUp().editColumns()._checkBox().assertChecked(6,false);
        reports_vehicles._popUp().editColumns()._checkBox().assertChecked(7,false);
        reports_vehicles._popUp().editColumns()._checkBox().assertChecked(8,false);
        reports_vehicles._popUp().editColumns()._checkBox().assertChecked(9,false);

    }
    
    @Test
    public void editColumnsSavebutton(){
        set_test_case("TC1651");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();

        //Check random boxes in the edit columns pop-up.
        //check different columns to show in edit columns, save.
        reports_vehicles._button().editColumns().click();
        reports_vehicles._popUp().editColumns()._checkBox().check(1);
        reports_vehicles._popUp().editColumns()._checkBox().check(2);
        reports_vehicles._popUp().editColumns()._checkBox().check(3);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(4);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(5);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(6);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(7);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(8);
        reports_vehicles._popUp().editColumns()._checkBox().uncheck(9);
        reports_vehicles._popUp().editColumns()._button().save().click();

        //Check that correct columns are shown.
        reports_vehicles._link().groupSort().assertPresence(true);
        reports_vehicles._link().vehicleIDSort().assertPresence(true);
        reports_vehicles._link().yearMakeModelSort().assertPresence(true);
        reports_vehicles._link().driverSort().assertPresence(false);
        reports_vehicles._link().odometerSort().assertPresence(false);
        reports_vehicles._link().distanceDrivenSort().assertPresence(false);
        reports_vehicles._link().overallSort().assertPresence(false);
        reports_vehicles._link().speedSort().assertPresence(false);
        reports_vehicles._link().styleSort().assertPresence(false);

        //Log out, log back in.
        reports_vehicles._link().logout().click();
        login_page.verifyOnPage();
        login_page._textField().userName().type(username);
        login_page._textField().password().type(password);
        login_page._button().logIn().click();
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();

        //Check that the same columns are still shown.
        reports_vehicles._link().groupSort().assertPresence(true);
        reports_vehicles._link().vehicleIDSort().assertPresence(true);
        reports_vehicles._link().yearMakeModelSort().assertPresence(true);
        reports_vehicles._link().driverSort().assertPresence(false);
        reports_vehicles._link().odometerSort().assertPresence(false);
        reports_vehicles._link().distanceDrivenSort().assertPresence(false);
        reports_vehicles._link().overallSort().assertPresence(false);
        reports_vehicles._link().speedSort().assertPresence(false);
        reports_vehicles._link().styleSort().assertPresence(false);

    }
    @Test
    public void editColumnsUI(){
        set_test_case("TC1653");
        
        //Login
        login_page.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();

        //Verify contents of edit columns pop-up.
        reports_vehicles._button().editColumns().click();

        reports_vehicles._popUp().editColumns()._checkBox().assertVisibility(1, true);
        reports_vehicles._popUp().editColumns()._checkBox().assertVisibility(2, true);
        reports_vehicles._popUp().editColumns()._checkBox().assertVisibility(3, true);
        reports_vehicles._popUp().editColumns()._checkBox().assertVisibility(4, true);
        reports_vehicles._popUp().editColumns()._checkBox().assertVisibility(5, true);
        reports_vehicles._popUp().editColumns()._checkBox().assertVisibility(6, true);
        reports_vehicles._popUp().editColumns()._checkBox().assertVisibility(7, true);
        reports_vehicles._popUp().editColumns()._checkBox().assertVisibility(8, true);
        reports_vehicles._popUp().editColumns()._checkBox().assertVisibility(9, true);

    }
    
    public void checkAllColumns() {
        reports_vehicles._button().editColumns().click();
        
        //Check all options in edit columns pop up.
        reports_vehicles._popUp().editColumns()._checkBox().check(1);
        reports_vehicles._popUp().editColumns()._checkBox().check(2);
        reports_vehicles._popUp().editColumns()._checkBox().check(3);
        reports_vehicles._popUp().editColumns()._checkBox().check(4);
        reports_vehicles._popUp().editColumns()._checkBox().check(5);
        reports_vehicles._popUp().editColumns()._checkBox().check(6);
        reports_vehicles._popUp().editColumns()._checkBox().check(7);
        reports_vehicles._popUp().editColumns()._checkBox().check(8);
        reports_vehicles._popUp().editColumns()._checkBox().check(9);
        reports_vehicles._popUp().editColumns()._button().save().click();
        
        reports_vehicles._link().groupSort().assertPresence(true);
        reports_vehicles._link().vehicleIDSort().assertPresence(true);
        reports_vehicles._link().yearMakeModelSort().assertPresence(true);
        reports_vehicles._link().driverSort().assertPresence(true);
        reports_vehicles._link().odometerSort().assertPresence(true);
        reports_vehicles._link().distanceDrivenSort().assertPresence(true);
        reports_vehicles._link().overallSort().assertPresence(true);
        reports_vehicles._link().speedSort().assertPresence(true);
        reports_vehicles._link().styleSort().assertPresence(true);

    }
}
