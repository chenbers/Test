package com.inthinc.pro.backing.importer.row;

import java.util.List;

import com.inthinc.pro.backing.importer.VehicleTemplateFormat;
import com.inthinc.pro.backing.importer.datacheck.AccountNameChecker;
import com.inthinc.pro.backing.importer.datacheck.DeviceSerialorIMEIChecker;
import com.inthinc.pro.backing.importer.datacheck.DuplicateVINChecker;
import com.inthinc.pro.backing.importer.datacheck.EmployeeIDExistsChecker;
import com.inthinc.pro.backing.importer.datacheck.GroupPathChecker;

public class VehicleRowValidator extends RowValidator {

    private VehicleTemplateFormat vehicleTemplateFormat;
/*
 *             new ColumnFormat("Account Name", true, 30),
            new ColumnFormat("Team (full path)", true, 255),
            new ColumnFormat("Make", false, 22),
            new ColumnFormat("Model", false, 22),
            new ColumnFormat("Year", false, 4),
            new ColumnFormat("Vehicle Name", true, 30),
            new ColumnFormat("VIN", true, 17),
            new ColumnFormat("State", false, new StateValidator()),
            new ColumnFormat("License", false, 10),
            new ColumnFormat("Device Serial Number", false, 10),
            new ColumnFormat("E-call number", false, 22),
            new ColumnFormat("Employee ID", false, 30),
    
 */
    
    public VehicleTemplateFormat getVehicleTemplateFormat() {
        return vehicleTemplateFormat;
    }

    public void setVehicleTemplateFormat(VehicleTemplateFormat vehicleTemplateFormat) {
        this.vehicleTemplateFormat = vehicleTemplateFormat;
    }

    @Override
    public List<String> validateRow(List<String> rowData, boolean includeWarnings) {
        
        List<String> errorList = super.checkFormat(rowData, vehicleTemplateFormat);
        
        if (errorList.isEmpty()) {
            // check the actual data in the row
            addToList(new AccountNameChecker().checkForErrors(rowData.get(VehicleTemplateFormat.ACCOUNT_NAME_IDX)), errorList);
            addToList(new GroupPathChecker().checkForErrors(rowData.get(VehicleTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(VehicleTemplateFormat.TEAM_PATH_IDX)), errorList);
            
            String error = new DuplicateVINChecker().checkForErrors(rowData.get(VehicleTemplateFormat.ACCOUNT_NAME_IDX),  rowData.get(VehicleTemplateFormat.VIN_IDX));
            addToList(error, errorList);
            if (includeWarnings && error == null) {
                addToList(new DuplicateVINChecker().checkForWarnings(rowData.get(VehicleTemplateFormat.ACCOUNT_NAME_IDX),  rowData.get(VehicleTemplateFormat.VIN_IDX)), errorList);
            }
            
            if (rowData.size() > VehicleTemplateFormat.DEVICE_SERIAL_NUMBER_IDX) {
                String deviceSerialorIMEI = rowData.get(VehicleTemplateFormat.DEVICE_SERIAL_NUMBER_IDX);
                if (deviceSerialorIMEI != null && !deviceSerialorIMEI.isEmpty()) {
                    error = new DeviceSerialorIMEIChecker().checkForErrors(rowData.get(VehicleTemplateFormat.ACCOUNT_NAME_IDX),  deviceSerialorIMEI);
                    addToList(error, errorList);
                }
            }

            if (rowData.size() > VehicleTemplateFormat.DRIVER_EMPLOYEE_ID_IDX) {
                String employeeID = rowData.get(VehicleTemplateFormat.DRIVER_EMPLOYEE_ID_IDX);
                if (employeeID != null && !employeeID.isEmpty()) {
                    error = new EmployeeIDExistsChecker().checkForErrors(rowData.get(VehicleTemplateFormat.ACCOUNT_NAME_IDX),  employeeID);
                    addToList(error, errorList);
                }
            }
        }
        return errorList;
    }
}
