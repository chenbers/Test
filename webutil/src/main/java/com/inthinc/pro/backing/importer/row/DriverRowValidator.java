package com.inthinc.pro.backing.importer.row;

import java.util.List;

import com.inthinc.pro.backing.importer.DriverTemplateFormat;
import com.inthinc.pro.backing.importer.datacheck.AccountNameChecker;
import com.inthinc.pro.backing.importer.datacheck.DuplicateEmployeeIDChecker;
import com.inthinc.pro.backing.importer.datacheck.GroupPathChecker;

public class DriverRowValidator extends RowValidator {

    
    DriverTemplateFormat driverTemplateFormat;
    /*
            new ColumnFormat("Account Name", true, 30),
            new ColumnFormat("Team (full path)", true, 255),
            new ColumnFormat("Last Name", true, 30),
            new ColumnFormat("First Name", true, 30),
            new ColumnFormat("Middle Name", false, 30),
            new ColumnFormat("Employee ID", true, 30),
            new ColumnFormat("RFID Barcode", false, 19),
            new ColumnFormat("username", false, 30),
            new ColumnFormat("password", false, 64),
            new ColumnFormat("e-mail", false, 62),
            new ColumnFormat("TimeZone", true, new TimeZoneValidator()),
            new ColumnFormat("Language", true, new LanguageValidator()),
            new ColumnFormat("Country", true, new CountryValidator()),
            new ColumnFormat("Measurement", true, new MeasurementTypeValidator()),
            new ColumnFormat("Fuel Efficiency", true, new FuelEfficiencyTypeValidator()),
     */
    @Override
    public List<String> validateRow(List<String> rowData, boolean includeWarnings) {
        
        List<String> errorList = super.checkFormat(rowData, driverTemplateFormat);
        
        if (errorList.isEmpty()) {
            // check the actual data in the row

            String error = new AccountNameChecker().checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX));
            if (error != null)
                errorList.add(error);
            
            error = new GroupPathChecker().checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(DriverTemplateFormat.TEAM_PATH_IDX));
            if (error != null)
                errorList.add(error);
        }
        if (includeWarnings) {
            
            String warning = new DuplicateEmployeeIDChecker().checkForWarnings(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX));
            if (warning != null)
                errorList.add(warning);
            
        }
        return errorList;
    }
    
    public DriverTemplateFormat getDriverTemplateFormat() {
        return driverTemplateFormat;
    }

    public void setDriverTemplateFormat(DriverTemplateFormat driverTemplateFormat) {
        this.driverTemplateFormat = driverTemplateFormat;
    }

}
