package com.inthinc.pro.backing.importer.datacheck;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Person;

public class EmployeeIDExistsChecker extends DataChecker {

    @Override
    public String checkForErrors(String... data) {
        
        String accountName = data[0];
        String employeeID = data[1];
        if (accountName == null || employeeID == null)
            return null;

        Account account = dataCache.getAccount(accountName);
        if (account == null)
            return null;
        
        Person person = dataCache.getPersonForEmployeeID(account.getAccountID(), employeeID);
        
        if (person == null) {
            return "ERROR: A person does not exist with employeeID: " + employeeID;
        }
        if (person.getDriver() == null)
            return "ERROR: A driver does not exist with employeeID: " + employeeID;
        return null;
    }
}
