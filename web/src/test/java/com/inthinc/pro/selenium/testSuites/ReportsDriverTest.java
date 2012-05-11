package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageReportsDrivers;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;

public class ReportsDriverTest extends WebRallyTest {
//
//    private PageReportsDrivers driver = new PageReportsDrivers();
//    private PageDriverPerformance performance = new PageDriverPerformance();
//    private PageDriverPerformanceStyle style = new PageDriverPerformanceStyle();
//    private PageDriverPerformanceSeatBelt belt = new PageDriverPerformanceSeatBelt();
//    private PageDriverPerformanceSpeed speeding = new PageDriverPerformanceSpeed();
//    private PageVehiclePerformance vehicle = new PageVehiclePerformance();
//    private PageAdminUsers admin = new PageAdminUsers();
//    
//    private AutomationUser login;
//    
//
//    @Before
//    public void before() {
//        login = AutomationUsers.getUsers().getOne();
//    }
//
//    @Test
//    public void DriverLink() {
//        set_test_case("TC1543");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Select a driver name to click on
//        String drivername = driver._link().driverValue().row(1).getText();
//        driver._link().driverValue().row(1).click();
//
//        // 4- Compare to validate the correct page is pulled
//        performance._link().driverName().assertEquals(drivername);
//
//    }
//
//    @Test
//    public void DrivingStyleScore() {
//        set_test_case("TC1545");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Take note of the top score in the Driving Style column.
//        String driverstyle = driver._link().styleValue().row(1).getText();
//
//        // 4- Click on the Driving Style score box for that driver.
//        driver._link().styleValue().row(1).click();
//
//        // 5- Click on the 12 months link
//        style._link().duration(TimeDuration.MONTHS_12).click();
//
//        // 6- Verify the score is the same as in step 3.
//        style._text().drivingStyleScoreValue().assertEquals(driverstyle);
//
//    }
//
//    @Test
//    public void DriverReportEmail() {
//        set_test_case("TC1547");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Click the Tools button
//        driver._button().tools().click();
//
//        // 4- Select Email this Report
//        driver._button().exportEmail().click();
//
//        // 5- Click Email
//        driver._popUp().emailReport()._button().email().click();
//
//    }
//
//    @Test
//    public void DriverReportExcel() {
//        set_test_case("TC1548");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Click the Tools button
//        driver._button().tools().click();
//
//        // 4- Select Export to Excel
//        //driver._button().exportExcel().click(); //TODO: clicking the exportExcel link without handling the resulting "save file" dialog is problematic
//        driver._button().exportExcel().assertPresence(true);
//        //TODO: this test does nothing to validate that the excel file is generated correctly?
//    }
//
//    @Test
//    public void DriverReportPDF() {
//        set_test_case("TC1549");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Click the Tools button
//        driver._button().tools().click();
//
//        // 4- Select Export to PDF
//        //driver._button().exportPDF().click();//TODO: clicking the exportPDF link without handling the resulting "save file" dialog is problematic
//        driver._button().exportPDF().assertPresence(true);
//        //TODO: this test does nothing to validate that the .PDF file is generated correctly?
//
//    }
//
//    @Test
//    public void DriverReportOverallScore() {
//        set_test_case("TC1553");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Take note of the top score in the Overall Score column.
//        String overallscore = driver._link().overallValue().row(1).getText();
//
//        // 4- Click on the Driving Style score box for that driver.
//        driver._link().overallValue().row(1).click();
//
//        // 5- Click on the 12 months link
//        performance._link().overallDuration(TimeDuration.MONTHS_12).click();
//
//        // 6- Verify the score is the same as in step 3.
//        performance._text().overallScore().assertEquals(overallscore);
//
//    }
//
//    @Test
//    public void DriverReportSearch() {
//        set_test_case("TC1560");
//        String name = "Danni";
//
//        PageReportsDrivers driver = new PageReportsDrivers();
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Start entering information for group/driver/vehicle in a text
//        // field
//        driver._textField().driverSearch().type(name);
//
//        // 4- Remove focus
//        driver._textField().groupSearch().focus();
//
//        // 5- Let the page refresh to show results
//        pause(15, "Let the page refresh to show results");
//
//        // 6- Verify only that name shows on list
//        boolean hasOnlyExpectedDrivernames = true;
//        boolean hasThisRow = true;
//        for (int i = 1; i < 10 && hasOnlyExpectedDrivernames && hasThisRow; i++) {
//            hasThisRow = driver._link().driverValue().row(i).isPresent();
//            if (hasThisRow) {
//                hasOnlyExpectedDrivernames &= driver._link().driverValue().row(i).validateContains(name);
//            }
//        }
//    }
//
//    @Test
//    public void DriverReportSeatBelt() {
//        set_test_case("TC1562");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//        driver._button().editColumns().click();
//        driver._popUp().editColumns()._checkBox().row(9).check();
//        driver._popUp().editColumns()._button().save().click();
//
//        // 3- Take note of the top score in the Seat Belt column.
//        String seatbelt = driver._link().seatbeltValue().row(1).getText();
//
//        // 4- Click on the Seat Belt score box for that driver.
//        driver._link().seatbeltValue().row(1).click();
//
//        // 5- Click on the 12 months link
//        belt._link().duration(TimeDuration.MONTHS_12);
//
//        // 6- Verify the score is the same as in step 3.
//        belt._text().overallScoreValue().assertEquals(seatbelt);
//
//    }
//
//    @Test
//    public void DriverReportSpeed() {//TODO: this is the only one failing manually
//        set_test_case("TC1564");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//        driver._button().editColumns().click();
//        driver._popUp().editColumns()._checkBox().row(7).check();
//        driver._popUp().editColumns()._button().save().click();
//
//        // 3- Take note of the top score in the Speed column.
//        String speed = driver._link().speedValue().row(1).getText();
//
//        // 4- Click on the Speed score box for that driver.
//        driver._link().speedValue().row(1).click();
//
//        // 5- Click on the 12 months link
//        speeding._link().duration(TimeDuration.MONTHS_12);
//
//        // 6- Verify the score is the same as in step 3.
//        speeding._text().mainOverall().assertEquals(speed);
//
//    }
//
//    @Test
//    public void DriverReportVehicleLink() {
//        set_test_case("TC1569");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Select a vehicle to click on
//        driver._link().vehicleSort().click();
//        driver._link().vehicleSort().click();
//        String vehiclename = driver._link().vehicleValue().row(1).getText();
//
//        // 4- Click on Vehicle
//        driver._link().vehicleValue().row(1).click();
//
//        // 5- Compare to validate the correct page is pulled
//        vehicle._link().vehicleName().assertEquals(vehiclename);
//
//    }
//
//    @Test
//    public void EditColumnsCancel() {
//        set_test_case("TC1571");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Take note of what information shows in the columns
//        boolean originallyHadGroupColumnGroup = driver._link().groupSort().isPresent();
//        boolean originallyHadGroupColumnDriver = driver._link().driverSort().isPresent();
//        boolean originallyHadGroupColumnVehicle = driver._link().vehicleSort().isPresent();
//        boolean originallyHadGroupColumnDistance = driver._link().distanceDrivenSort().isPresent();
//        boolean originallyHadGroupColumnOverall = driver._link().overallSort().isPresent();
//        boolean originallyHadGroupColumnStyle = driver._link().styleSort().isPresent();
//        boolean originallyHadGroupColumnSeatbelt = driver._link().seatBeltSort().isPresent();
//
//        // 4- Click on the Edit Columns link
//        driver._button().editColumns().click();
//
//        // 5- Check one or more of the boxes in the pop up
//        driver._popUp().editColumns()._checkBox().row(3).click();
//        driver._popUp().editColumns()._checkBox().row(1).click();
//        driver._popUp().editColumns()._checkBox().row(5).click();
//
//        // 6- Click Cancel
//        driver._popUp().editColumns()._button().cancel().click();
//
//        // 7- Verify nothing has changed
//        if (originallyHadGroupColumnGroup != driver._link().groupSort().isPresent()) {
//            addError("expected Group column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDriver != driver._link().driverSort().isPresent()) {
//            addError("expected Driver column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != driver._link().vehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDistance != driver._link().distanceDrivenSort().isPresent()) {
//            addError("expected Distance column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnOverall != driver._link().overallSort().isPresent()) {
//            addError("expected Overall column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStyle != driver._link().styleSort().isPresent()) {
//            addError("expected Style column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnSeatbelt != driver._link().seatBeltSort().isPresent()) {
//            addError("expected Seat Belt column to remain the same", ErrorLevel.FAIL);
//        }
//
//    }
//
//    @Test
//    public void EditColumnsNoChangeCancel() {
//        set_test_case("TC1572");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Take note of what information shows in the columns
//        boolean originallyHadGroupColumnGroup = driver._link().groupSort().isPresent();
//        boolean originallyHadGroupColumnDriver = driver._link().driverSort().isPresent();
//        boolean originallyHadGroupColumnVehicle = driver._link().vehicleSort().isPresent();
//        boolean originallyHadGroupColumnDistance = driver._link().distanceDrivenSort().isPresent();
//        boolean originallyHadGroupColumnOverall = driver._link().overallSort().isPresent();
//        boolean originallyHadGroupColumnStyle = driver._link().styleSort().isPresent();
//        boolean originallyHadGroupColumnSeatbelt = driver._link().seatBeltSort().isPresent();
//
//        // 3- Click on the Edit Columns link
//        driver._button().editColumns().click();
//
//        // 4- Click Cancel
//        driver._popUp().editColumns()._button().cancel().click();
//
//        // 5- Verify nothing has changed
//        if (originallyHadGroupColumnGroup != driver._link().groupSort().isPresent()) {
//            addError("expected Group column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDriver != driver._link().driverSort().isPresent()) {
//            addError("expected Driver column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != driver._link().vehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDistance != driver._link().distanceDrivenSort().isPresent()) {
//            addError("expected Distance column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnOverall != driver._link().overallSort().isPresent()) {
//            addError("expected Overall column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStyle != driver._link().styleSort().isPresent()) {
//            addError("expected Style column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnSeatbelt != driver._link().seatBeltSort().isPresent()) {
//            addError("expected Seat Belt column to remain the same", ErrorLevel.FAIL);
//        }
//    }
//
//    @Test
//    public void EditColumnsBoxMouse() {
//        set_test_case("TC1573");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Click on the Edit Columns link
//        driver._button().editColumns().click();
//
//        // 4- Using the mouse, click on a check box
//        driver._popUp().editColumns()._checkBox().row(1).click();
//
//    }
//
//    @Test
//    public void EditColumnsRetention() {
//        set_test_case("TC1575");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Make note of what columns are currently there
//        boolean originallyHadGroupColumnGroup = driver._link().groupSort().isPresent();
//        boolean originallyHadGroupColumnDriver = driver._link().driverSort().isPresent();
//        boolean originallyHadGroupColumnVehicle = driver._link().vehicleSort().isPresent();
//        boolean originallyHadGroupColumnDistance = driver._link().distanceDrivenSort().isPresent();
//        boolean originallyHadGroupColumnOverall = driver._link().overallSort().isPresent();
//        boolean originallyHadGroupColumnStyle = driver._link().styleSort().isPresent();
//        boolean originallyHadGroupColumnSeatbelt = driver._link().seatBeltSort().isPresent();
//
//        // 4- Click on the Edit Columns link
//        driver._button().editColumns().click();
//
//        // 5- Check one or more of the boxes in the pop up
//        driver._popUp().editColumns()._checkBox().row(3).click();
//        driver._popUp().editColumns()._checkBox().row(1).click();
//        driver._popUp().editColumns()._checkBox().row(5).click();
//
//        // 6- Click Save
//        driver._popUp().editColumns()._button().save().click();
//
//        // 7- Verify changes are there
//        if (originallyHadGroupColumnGroup == driver._link().groupSort().isPresent()) {
//            addError("expected Group Column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDriver == driver._link().driverSort().isPresent()) {
//            addError("expected Driver Column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != driver._link().vehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDistance == driver._link().distanceDrivenSort().isPresent()) {
//            addError("expected Distance Column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnOverall != driver._link().overallSort().isPresent()) {
//            addError("expected Overall column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStyle != driver._link().styleSort().isPresent()) {
//            addError("expected Style column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnSeatbelt != driver._link().seatBeltSort().isPresent()) {
//            addError("expected Seat Belt column to remain the same", ErrorLevel.FAIL);
//        }
//
//        // 8- Click on any other link on the page
//        driver._link().admin().click();
//
//        // 9- Click back on the reports tab
//        admin._link().reports().click();
//
//        // 10- Verify changes are still there
//        if (originallyHadGroupColumnGroup == driver._link().groupSort().isPresent()) {
//            addError("expected Group Column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDriver == driver._link().driverSort().isPresent()) {
//            addError("expected Driver Column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != driver._link().vehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDistance == driver._link().distanceDrivenSort().isPresent()) {
//            addError("expected Distance Column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnOverall != driver._link().overallSort().isPresent()) {
//            addError("expected Overall column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStyle != driver._link().styleSort().isPresent()) {
//            addError("expected Style column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnSeatbelt != driver._link().seatBeltSort().isPresent()) {
//            addError("expected Seat Belt column to remain the same", ErrorLevel.FAIL);
//        }
//
//        // 11- Click Edit Columns again
//        driver._button().editColumns().click();
//
//        // 12- Change back to original settings
//        driver._popUp().editColumns()._checkBox().row(3).click();
//        driver._popUp().editColumns()._checkBox().row(1).click();
//        driver._popUp().editColumns()._checkBox().row(5).click();
//
//        // 13- Click Save
//        driver._popUp().editColumns()._button().save().click();
//
//    }
//
//    @Test
//    public void EditColumnsSaveButton() {
//        set_test_case("TC1577");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Make note of what columns are currently there
//        boolean originallyHadGroupColumnGroup = driver._link().groupSort().isPresent();
//        boolean originallyHadGroupColumnDriver = driver._link().driverSort().isPresent();
//        boolean originallyHadGroupColumnVehicle = driver._link().vehicleSort().isPresent();
//        boolean originallyHadGroupColumnDistance = driver._link().distanceDrivenSort().isPresent();
//        boolean originallyHadGroupColumnOverall = driver._link().overallSort().isPresent();
//        boolean originallyHadGroupColumnStyle = driver._link().styleSort().isPresent();
//        boolean originallyHadGroupColumnSeatbelt = driver._link().seatBeltSort().isPresent();
//
//        // 4- Click on the Edit Columns link
//        driver._button().editColumns().click();
//
//        // 5- Check one or more of the boxes in the pop up
//        driver._popUp().editColumns()._checkBox().row(3).click();
//
//        // 6- Click Save
//        driver._popUp().editColumns()._button().save().click();
//
//        // 7- Verify changes are there
//        if (originallyHadGroupColumnGroup != driver._link().groupSort().isPresent()) {
//            addError("expected Group column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDriver == driver._link().driverSort().isPresent()) {
//            addError("expected Driver Column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != driver._link().vehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDistance != driver._link().distanceDrivenSort().isPresent()) {
//            addError("expected Distance column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnOverall != driver._link().overallSort().isPresent()) {
//            addError("expected Overall column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStyle != driver._link().styleSort().isPresent()) {
//            addError("expected Style column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnSeatbelt != driver._link().seatBeltSort().isPresent()) {
//            addError("expected Seat Belt column to remain the same", ErrorLevel.FAIL);
//        }
//
//        // 8- Click Edit Columns again
//        driver._button().editColumns().click();
//
//        // 9- Change back to original settings
//        driver._popUp().editColumns()._checkBox().row(3).click();
//
//        // 10- Click Save
//        driver._popUp().editColumns()._button().save().click();
//    }
//
//    @Test
//    public void EditColumnsOldSession() {
//        set_test_case("TC1578");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Make note of what columns are currently there
//        boolean originallyHadGroupColumnGroup = driver._link().groupSort().isPresent();
//        boolean originallyHadGroupColumnDriver = driver._link().driverSort().isPresent();
//        boolean originallyHadGroupColumnVehicle = driver._link().vehicleSort().isPresent();
//        boolean originallyHadGroupColumnDistance = driver._link().distanceDrivenSort().isPresent();
//        boolean originallyHadGroupColumnOverall = driver._link().overallSort().isPresent();
//        boolean originallyHadGroupColumnStyle = driver._link().styleSort().isPresent();
//        boolean originallyHadGroupColumnSeatbelt = driver._link().seatBeltSort().isPresent();
//
//        // 4- Click on the Edit Columns link
//        driver._button().editColumns().click();
//
//        // 5- Check one or more of the boxes in the pop up
//        driver._popUp().editColumns()._checkBox().row(3).click();
//
//        // 6- Click Save
//        driver._popUp().editColumns()._button().save().click();
//
//        // 7- Verify changes and make note
//        if (originallyHadGroupColumnGroup != driver._link().groupSort().isPresent()) {
//            addError("expected Group column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDriver == driver._link().driverSort().isPresent()) {
//            addError("expected Driver Column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != driver._link().vehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDistance != driver._link().distanceDrivenSort().isPresent()) {
//            addError("expected Distance column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnOverall != driver._link().overallSort().isPresent()) {
//            addError("expected Overall column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStyle != driver._link().styleSort().isPresent()) {
//            addError("expected Style column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnSeatbelt != driver._link().seatBeltSort().isPresent()) {
//            addError("expected Seat Belt column to remain the same", ErrorLevel.FAIL);
//        }
//
//        // 8- Log out
//        driver._link().logout().click();
//
//        // 9- Login
//        driver.loginProcess(login);
//
//        // 10- Click back on reports link
//        driver._link().reports().click();
//
//        // 11- Verify changes are still there
//        if (originallyHadGroupColumnGroup != driver._link().groupSort().isPresent()) {
//            addError("expected Group column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDriver == driver._link().driverSort().isPresent()) {
//            addError("expected Driver Column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != driver._link().vehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDistance != driver._link().distanceDrivenSort().isPresent()) {
//            addError("expected Distance column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnOverall != driver._link().overallSort().isPresent()) {
//            addError("expected Overall column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStyle != driver._link().styleSort().isPresent()) {
//            addError("expected Style column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnSeatbelt != driver._link().seatBeltSort().isPresent()) {
//            addError("expected Seat Belt column to remain the same", ErrorLevel.FAIL);
//        }
//
//        // 12- Click Edit Columns again
//        driver._button().editColumns().click();
//
//        // 13- Change back to original settings
//        driver._popUp().editColumns()._checkBox().row(3).click();
//
//        // 14- Click Save
//        driver._popUp().editColumns()._button().save().click();
//
//    }
//
//    @Test
//    public void EditColumnsUI() {
//        set_test_case("TC1580");
//
//        // 1- Login
//        driver.loginProcess(login);
//
//        // 2- Click on Reports link
//        driver._link().reports().click();
//
//        // 3- Click on the Edit Columns link
//        driver._button().editColumns().click();
//
//        // 4- Verify the UI of the Edit Columns pop up
//        Integer group = 1;
//        Integer employee = 2;
//        Integer drive = 3;
//        Integer vehicle = 4;
//        Integer distance = 5;
//        Integer overall = 6;
//        Integer speed = 7;
//        Integer style = 8;
//        Integer belt = 9;
//        driver._popUp().editColumns()._checkBox().row(group).assertVisibility(true);
//        driver._popUp().editColumns()._checkBox().row(employee).assertVisibility(true);
//        driver._popUp().editColumns()._checkBox().row(drive).assertVisibility(true);
//        driver._popUp().editColumns()._checkBox().row(vehicle).assertVisibility(true);
//        driver._popUp().editColumns()._checkBox().row(distance).assertVisibility(true);
//        driver._popUp().editColumns()._checkBox().row(overall).assertVisibility(true);
//        driver._popUp().editColumns()._checkBox().row(speed).assertVisibility(true);
//        driver._popUp().editColumns()._checkBox().row(style).assertVisibility(true);
//        driver._popUp().editColumns()._checkBox().row(belt).assertVisibility(true);
//
//    }

}