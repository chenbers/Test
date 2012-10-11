package com.inthinc.pro.backing.importer.row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.importer.DataRow;
import com.inthinc.pro.backing.importer.DriverTemplateFormat;
import com.inthinc.pro.backing.importer.datacheck.AccountNameChecker;
import com.inthinc.pro.backing.importer.datacheck.DuplicateEmailChecker;
import com.inthinc.pro.backing.importer.datacheck.DuplicateEmployeeIDChecker;
import com.inthinc.pro.backing.importer.datacheck.DuplicateUsernameChecker;
import com.inthinc.pro.backing.importer.datacheck.GroupPathChecker;
import com.inthinc.pro.backing.importer.datacheck.ValidRFIDBarcodeChecker;

public class DriverRowValidator extends RowValidator {

    private DuplicateEmailChecker duplicateEmailChecker;
    private DuplicateEmployeeIDChecker duplicateEmployeeIDChecker;
    private ValidRFIDBarcodeChecker validRFIDBarcodeChecker;
    private DuplicateUsernameChecker duplicateUsernameChecker;
    private AccountNameChecker accountNameChecker;
    private GroupPathChecker groupPathChecker;

    DriverTemplateFormat driverTemplateFormat;

    @Override
    public List<String> validateRow(List<String> rowData, boolean includeWarnings) {
        
        List<String> errorList = super.checkFormat(rowData, driverTemplateFormat);
        
        if (errorList.isEmpty()) {
            // check the actual data in the row
            addToList(accountNameChecker.checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX)), errorList);
            addToList(groupPathChecker.checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(DriverTemplateFormat.TEAM_PATH_IDX)), errorList);

            if (rowData.size() > DriverTemplateFormat.EMPLOYEE_ID_IDX) {
                String error = duplicateEmployeeIDChecker.checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX));
                addToList(error, errorList);
                if (includeWarnings && error == null)
                    addToList(duplicateEmployeeIDChecker.checkForWarnings(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX)), errorList);
            }
            

            if (rowData.size() > DriverTemplateFormat.BARCODE_IDX) {
                String error = validRFIDBarcodeChecker.checkForErrors(rowData.get(DriverTemplateFormat.BARCODE_IDX));
                addToList(error, errorList);
            }

            
            if (rowData.size() > DriverTemplateFormat.USERNAME_IDX ) {
                String username = rowData.get(DriverTemplateFormat.USERNAME_IDX);
                if (username != null && !username.isEmpty()) { 
                    String error = duplicateUsernameChecker.checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX),  rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX), username);
                    addToList(error, errorList);
                    if (includeWarnings && error == null) {
                        addToList(duplicateUsernameChecker.checkForWarnings(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), username), errorList);
                    }
                }
            }
            if (rowData.size() > DriverTemplateFormat.EMAIL_IDX) {
                String email = rowData.get(DriverTemplateFormat.EMAIL_IDX);
                if (email != null && !email.isEmpty()) {
                    String error = duplicateEmailChecker.checkForErrors(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX), email);
                    addToList(error, errorList);
                    if (includeWarnings && error == null) {
                        addToList(duplicateEmailChecker.checkForWarnings(rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX), email), errorList);
                    }
                }
            }
                    
        }
        return errorList;
    }
    
    @Override
    public Integer getColumnCount() {
        return getDriverTemplateFormat().getNumColumns();
    }

    public DriverTemplateFormat getDriverTemplateFormat() {
        return driverTemplateFormat;
    }

    public void setDriverTemplateFormat(DriverTemplateFormat driverTemplateFormat) {
        this.driverTemplateFormat = driverTemplateFormat;
    }

    @Override
    public List<String> validate(List<DataRow> allRows, boolean includeWarnings) {
        if (!includeWarnings)
            return null;

        Map<String, Integer> empIDMap = new HashMap<String, Integer>();
        Integer rowNumber = 2;
        List<String> warningList = new ArrayList<String>();
        for (DataRow row : allRows) {
            List<String> rowData = row.getData();
            String empID = rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX);
            if (empIDMap.get(empID) != null) {
                if (warningList.isEmpty()) {
                    warningList.add("WARNING:  Duplicate EmployeeIDs found in import document.");
                }
                addToList("The employee ID on row: " + rowNumber + " is a duplicate of the employee ID on row: " + empIDMap.get(empID) + ".  On import the vehicle on row: " + empIDMap.get(empID) + " will be overwritten.", warningList);
            }
            else {
                empIDMap.put(empID, rowNumber);
            }
            rowNumber++;
        }
        
        return warningList;
        
    }
    public DuplicateEmailChecker getDuplicateEmailChecker() {
        return duplicateEmailChecker;
    }

    public void setDuplicateEmailChecker(DuplicateEmailChecker duplicateEmailChecker) {
        this.duplicateEmailChecker = duplicateEmailChecker;
    }

    public DuplicateEmployeeIDChecker getDuplicateEmployeeIDChecker() {
        return duplicateEmployeeIDChecker;
    }

    public void setDuplicateEmployeeIDChecker(DuplicateEmployeeIDChecker duplicateEmployeeIDChecker) {
        this.duplicateEmployeeIDChecker = duplicateEmployeeIDChecker;
    }

    public ValidRFIDBarcodeChecker getValidRFIDBarcodeChecker() {
        return validRFIDBarcodeChecker;
    }

    public void setValidRFIDBarcodeChecker(ValidRFIDBarcodeChecker validRFIDBarcodeChecker) {
        this.validRFIDBarcodeChecker = validRFIDBarcodeChecker;
    }

    public DuplicateUsernameChecker getDuplicateUsernameChecker() {
        return duplicateUsernameChecker;
    }

    public void setDuplicateUsernameChecker(DuplicateUsernameChecker duplicateUsernameChecker) {
        this.duplicateUsernameChecker = duplicateUsernameChecker;
    }

    public AccountNameChecker getAccountNameChecker() {
        return accountNameChecker;
    }

    public void setAccountNameChecker(AccountNameChecker accountNameChecker) {
        this.accountNameChecker = accountNameChecker;
    }

    public GroupPathChecker getGroupPathChecker() {
        return groupPathChecker;
    }

    public void setGroupPathChecker(GroupPathChecker groupPathChecker) {
        this.groupPathChecker = groupPathChecker;
    }

}
