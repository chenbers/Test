package com.inthinc.pro.backing.importer.row;

import java.util.List;

import com.inthinc.pro.backing.importer.DriverTemplateFormat;
import com.inthinc.pro.backing.importer.datacheck.AccountNameChecker;
import com.inthinc.pro.backing.importer.datacheck.DuplicateEmailChecker;
import com.inthinc.pro.backing.importer.datacheck.DuplicateEmployeeIDChecker;
import com.inthinc.pro.backing.importer.datacheck.DuplicateUsernameChecker;
import com.inthinc.pro.backing.importer.datacheck.GroupPathChecker;

public class DriverRowValidator extends RowValidator {

    
    DriverTemplateFormat driverTemplateFormat;

    @Override
    public List<String> validateRow(List<String> rowData, boolean includeWarnings) {
        
        List<String> errorList = super.checkFormat(rowData, driverTemplateFormat);
        
        if (errorList.isEmpty()) {
            // check the actual data in the row
            addToList(new AccountNameChecker().checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX)), errorList);
            addToList(new GroupPathChecker().checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(DriverTemplateFormat.TEAM_PATH_IDX)), errorList);

            if (rowData.size() > DriverTemplateFormat.EMPLOYEE_ID_IDX) {
                String error = new DuplicateEmployeeIDChecker().checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX));
                addToList(error, errorList);
                if (includeWarnings && error == null)
                    addToList(new DuplicateEmployeeIDChecker().checkForWarnings(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX)), errorList);
            }
            
            if (rowData.size() > DriverTemplateFormat.USERNAME_IDX ) {
                String username = rowData.get(DriverTemplateFormat.USERNAME_IDX);
                if (username != null && !username.isEmpty()) { 
                    String error = new DuplicateUsernameChecker().checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX),  rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX), username);
                    addToList(error, errorList);
                    if (includeWarnings && error == null) {
                        addToList(new DuplicateUsernameChecker().checkForWarnings(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), username), errorList);
                    }
                }
            }
            if (rowData.size() > DriverTemplateFormat.EMAIL_IDX) {
                String email = rowData.get(DriverTemplateFormat.EMAIL_IDX);
                if (email != null && !email.isEmpty()) {
                    String error = new DuplicateEmailChecker().checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX), email);
                    addToList(error, errorList);
                    if (includeWarnings && error == null) {
                        addToList(new DuplicateEmailChecker().checkForWarnings(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), email), errorList);
                    }
                }
            }
                    
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
