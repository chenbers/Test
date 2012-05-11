package com.inthinc.pro.selenium.testSuites;

import java.util.EnumSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageEnums.EditColumnsEnums;
import com.inthinc.pro.selenium.pageObjects.PageReportsDevices;

@Ignore
public class ReportsDevicesTest extends WebRallyTest {
//
//    private PageReportsDevices device;
//    
//    private AutomationUser login;
//    
//    @Before
//    public void setupUser(){
//        login = AutomationUsers.getUsers().getOne();
//        device = new PageReportsDevices();
//    }
//
//    @Test
//    @Ignore //TODO: Needs automation for Email
//    public void ReportsDeviceEmail() {
//        set_test_case("TC1516");
//
//        // 1- Login
//        device.loginProcess(login);
//
//        // 2- Click on reports
//        device._link().reports().click();
//
//        // 3- Click on Devices
//        device._link().devices().click();
//
//        // 4- Click the Tools button
//        device._button().tools().click();
//
//        // 5- Click Email this Report
//        device._button().exportEmail().click();
//
//        // 6- Click Email
//        device._popUp().emailReport()._button().email().click();
//
//    }
//
//    @Test
//    @Ignore //TODO: Needs automation for Exporting to Excel
//    public void DeviceReportExportExcel() {
//        set_test_case("TC1517");
//
//        // 1- Login
//        device.loginProcess(login);
//
//        // 2- Click on reports
//        device._link().reports().click();
//
//        // 3- Click on Devices
//        device._link().devices().click();
//
//        // 4- Click the Tools button
//        device._button().tools().click();
//
//        // 5- Click Export to Excel
//        device._button().exportExcel().click();
//
//    }
//
//    @Test
//    @Ignore //TODO: Needs automation for exporting to PDF
//    public void DeviceReportExportPDF() {
//        set_test_case("TC1518");
//
//        // 1- Login
//        device.loginProcess(login);
//
//        // 2- Click on reports
//        device._link().reports().click();
//
//        // 3- Click on Devices
//        device._link().devices().click();
//
//        // 4- Click the Tools button
//        device._button().tools().click();
//
//        // 5- Click Export to PDF
//        device._button().exportPDF().click();
//
//    }
//
//    @Test
//    public void DeviceReportEditColumnsChange() {
//        set_test_case("TC1530");
//
//        // 1- Login
//        device.loginProcess(login);
//
//        // 2- Click on Reports link
//        device._link().reports().click();
//
//        // 3- Click on Devices
//        device._link().devices().click();
//
//        // 4- Take note of what information shows in the columns
//        boolean originallyHadGroupColumnID = device._link().deviceIDSort()
//                .isPresent();
//        boolean originallyHadGroupColumnVehicle = device._link()
//                .assignedVehicleSort().isPresent();
//        boolean originallyHadGroupColumnIMEI = device._link().imeiSort()
//                .isPresent();
//        boolean originallyHadGroupColumnPhone = device._link()
//                .devicePhoneSort().isPresent();
//        boolean originallyHadGroupColumnStatus = device._dropDown().status()
//                .isPresent();
//
//        // 5- Click on the Edit Columns link
//        device._button().editColumns().click();
//
//        // 6- Check one or more of the boxes in the pop up
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_IMEI).click();
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_DEVICE_ID).click();
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_STATUS).click();
//
//        // 7- Click Cancel
//        device._popUp().editColumns()._button().cancel().click();
//
//        // 8- Verify nothing has changed
//        if (originallyHadGroupColumnID != device._link().deviceIDSort()
//                .isPresent()) {
//            addError("expected DeviceID column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != device._link()
//                .assignedVehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnIMEI != device._link().imeiSort()
//                .isPresent()) {
//            addError("expected IMEI column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnPhone != device._link().devicePhoneSort()
//                .isPresent()) {
//            addError("expected Device Phone column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStatus != device._dropDown().status()
//                .isPresent()) {
//            addError("expected Status column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//
//    }
//
//    @Test
//    public void DeviceReportEditColumnsCancelNoChange() {
//        set_test_case("TC1531");
//
//        // 1- Login
//        device.loginProcess(login);
//
//        // 2- Click on Reports link
//        device._link().reports().click();
//
//        // 3- Click on Devices
//        device._link().devices().click();
//
//        // 4- Take note of what information shows in the columns
//        boolean originallyHadGroupColumnID = device._link().deviceIDSort()
//                .isPresent();
//        boolean originallyHadGroupColumnVehicle = device._link()
//                .assignedVehicleSort().isPresent();
//        boolean originallyHadGroupColumnIMEI = device._link().imeiSort()
//                .isPresent();
//        boolean originallyHadGroupColumnPhone = device._link()
//                .devicePhoneSort().isPresent();
//        boolean originallyHadGroupColumnStatus = device._dropDown().status()
//                .isPresent();
//
//        // 5- Click on the Edit Columns link
//        device._button().editColumns().click();
//
//        // 6- Click Cancel
//        device._popUp().editColumns()._button().cancel().click();
//
//        // 7- Verify nothing has changed
//        if (originallyHadGroupColumnID != device._link().deviceIDSort()
//                .isPresent()) {
//            addError("expected DeviceID column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != device._link()
//                .assignedVehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnIMEI != device._link().imeiSort()
//                .isPresent()) {
//            addError("expected IMEI column to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnPhone != device._link().devicePhoneSort()
//                .isPresent()) {
//            addError("expected Device Phone column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStatus != device._dropDown().status()
//                .isPresent()) {
//            addError("expected Status column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//    }
//
//    @Test
//    public void DeviceReportEditColumnsBoxMouse() {
//        set_test_case("TC1532");
//
//        // 1- Login
//        device.loginProcess(login);
//
//        // 2- Click on Reports link
//        device._link().reports().click();
//
//        // 3- Click on Devices
//        device._link().devices().click();
//
//        // 4- Click on the Edit Columns link
//        device._button().editColumns().click();
//
//        // 5- Check one or more of the boxes in the pop up with mouse
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_DEVICE_ID).click();
//
//    }
//
//    @Test
//    public void DeviceReportEditColumnsRetention() {
//        set_test_case("TC1534");
//
//        // 1- Login
//        device.loginProcess(login);
//
//        // 2- Click on Reports link
//        device._link().reports().click();
//
//        // 3- Click on Devices
//        device._link().devices().click();
//
//        // 4- Take note of what columns are there
//        boolean originallyHadGroupColumnID = device._link().deviceIDSort()
//                .isPresent();
//        boolean originallyHadGroupColumnVehicle = device._link()
//                .assignedVehicleSort().isPresent();
//        boolean originallyHadGroupColumnIMEI = device._link().imeiSort()
//                .isPresent();
//        boolean originallyHadGroupColumnPhone = device._link()
//                .devicePhoneSort().isPresent();
//        boolean originallyHadGroupColumnStatus = device._dropDown().status()
//                .isPresent();
//
//        // 5- Click on the Edit Columns link
//        device._button().editColumns().click();
//
//        // 6- Check one or more of the boxes in the pop up
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_IMEI).click();
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_DEVICE_ID).click();
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_STATUS).click();
//
//        // 7- Click Save
//        device._popUp().editColumns()._button().save().click();
//
//        // 8- Verify changes are there
//        if (originallyHadGroupColumnID == device._link().deviceIDSort()
//                .isPresent()) {
//            addError("expected DeviceID column to be different",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != device._link()
//                .assignedVehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnIMEI == device._link().imeiSort()
//                .isPresent()) {
//            addError("expected IMEI column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnPhone != device._link().devicePhoneSort()
//                .isPresent()) {
//            addError("expected Device Phone column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStatus == device._dropDown().status()
//                .isPresent()) {
//            addError("expected Status column to be different", ErrorLevel.FAIL);
//        }
//
//        // 9- Click on any other link on the page
//        device._link().admin().click();
//
//        // 10- Click back on the reports tab
//        device._link().reports().click();
//
//        // 11- Click on Devices
//        device._link().devices().click();
//
//        // 12- Verify changes are still there
//        if (originallyHadGroupColumnID == device._link().deviceIDSort()
//                .isPresent()) {
//            addError("expected DeviceID column to be different",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != device._link()
//                .assignedVehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnIMEI == device._link().imeiSort()
//                .isPresent()) {
//            addError("expected IMEI column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnPhone != device._link().devicePhoneSort()
//                .isPresent()) {
//            addError("expected Device Phone column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStatus == device._dropDown().status()
//                .isPresent()) {
//            addError("expected Status column to be different", ErrorLevel.FAIL);
//        }
//
//        // 13- Click Edit Columns
//        device._button().editColumns().click();
//
//        // 14- Change back to original settings
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_IMEI).click();
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_DEVICE_ID).click();
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_STATUS).click();
//
//        // 15- Click Save
//        device._popUp().editColumns()._button().save().click();
//
//    }
//
//    @Test
//    public void DeviceReportEditColumnsSaveButton() {
//        set_test_case("TC1536");
//
//        // 1- Login
//        device.loginProcess(login);
//
//        // 2- Click on Reports link
//        device._link().reports().click();
//
//        // 3- Click on Devices
//        device._link().devices().click();
//        device._button().editColumns().click();
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_IMEI).check();
//        device._popUp().editColumns()._button().save().click();
//
//        // 4- Take note of one column being present
//        boolean originallyHadGroupColumnIMEI = device._link().imeiSort()
//                .isPresent();
//
//        // 5- Click on the Edit Columns link
//        device._button().editColumns().click();
//
//        // 6- Check one or more of the boxes in the pop up
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_IMEI).click();
//
//        // 7- Click Save
//        device._popUp().editColumns()._button().save().click();
//
//        // 8- Verify change has saved
//        if (originallyHadGroupColumnIMEI == device._link().imeiSort()
//                .isPresent()) {
//            addError("expected IMEI column to be different", ErrorLevel.FAIL);
//        }
//
//        // 9- Click on Edit Columns link
//        device._button().editColumns().click();
//
//        // 10- Change back to original settings
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_IMEI).click();
//
//        // 11- Click save
//        device._popUp().editColumns()._button().save().click();
//    }
//
//    @Test
//    public void DeviceReportEditColumnsOldSession() {
//        set_test_case("TC1537");
//
//        // 1- Login
//        device.loginProcess(login);
//
//        // 2- Click on Reports link
//        device._link().reports().click();
//
//        // 3- Click on Devices
//        device._link().devices().click();
//
//        // 4- Take note of what columns are currently there
//        boolean originallyHadGroupColumnID = device._link().deviceIDSort()
//                .isPresent();
//        boolean originallyHadGroupColumnVehicle = device._link()
//                .assignedVehicleSort().isPresent();
//        boolean originallyHadGroupColumnIMEI = device._link().imeiSort()
//                .isPresent();
//        boolean originallyHadGroupColumnPhone = device._link()
//                .devicePhoneSort().isPresent();
//        boolean originallyHadGroupColumnStatus = device._dropDown().status()
//                .isPresent();
//
//        // 5- Click on the Edit Columns link
//        device._button().editColumns().click();
//
//        // 6- Check one or more of the boxes in the pop up
//        device._popUp().editColumns()._checkBox().row(3).click();
//
//        // 7- Click Save
//        device._popUp().editColumns()._button().save().click();
//
//        // 8- Verify changes are there
//        if (originallyHadGroupColumnID != device._link().deviceIDSort()
//                .isPresent()) {
//            addError("expected DeviceID column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != device._link()
//                .assignedVehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnIMEI == device._link().imeiSort()
//                .isPresent()) {
//            addError("expected IMEI column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnPhone != device._link().devicePhoneSort()
//                .isPresent()) {
//            addError("expected Device Phone column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStatus != device._dropDown().status()
//                .isPresent()) {
//            addError("expected Status column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//
//        // 9- Logout
//        device._link().logout().click();
//
//        // 10- Log back in
//        device.loginProcess(login);
//
//        // 11- Click back on reports
//        device._link().reports().click();
//
//        // 12- Click back on devices
//        device._link().devices().click();
//
//        // 13- Verify changes are still there
//        if (originallyHadGroupColumnID != device._link().deviceIDSort()
//                .isPresent()) {
//            addError("expected DeviceID column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnVehicle != device._link()
//                .assignedVehicleSort().isPresent()) {
//            addError("expected Vehicle column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnIMEI == device._link().imeiSort()
//                .isPresent()) {
//            addError("expected IMEI column to be different", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnPhone != device._link().devicePhoneSort()
//                .isPresent()) {
//            addError("expected Device Phone column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnStatus != device._dropDown().status()
//                .isPresent()) {
//            addError("expected Status column to remain the same",
//                    ErrorLevel.FAIL);
//        }
//
//        // 14- Click Edit Columns
//        device._button().editColumns().click();
//
//        // 15- Change back to original settings
//        device._popUp().editColumns()._checkBox().row(EditColumnsEnums.REPORTS_DEVICES_IMEI).click();
//
//        // 16- Click Save
//        device._popUp().editColumns()._button().save().click();
//
//    }
//
//    @Test
//    public void DeviceReportEditColumnsUI() {
//        set_test_case("TC1539");
//
//        // 1- Login
//        device.loginProcess(login);
//
//        // 2- Click on Reports link
//        device._link().reports().click();
//
//        // 3- Click on Devices
//        device._link().devices().click();
//
//        // 4- Click on the Edit Columns link
//        device._button().editColumns().click();
//
//        // 5- Verify the UI of the Edit Columns pop up
//        for (EditColumnsEnums entry : EnumSet.allOf(EditColumnsEnums.class)){
//            if (entry.toString().contains("Devices")){
//                Checkable row = device._popUp().editColumns()._checkBox().row(entry);
//                row.assertPresence(true);
//                row.assertVisibility(true);
//            }
//        }
//    }
}