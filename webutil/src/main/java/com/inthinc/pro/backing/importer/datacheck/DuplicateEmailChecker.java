package com.inthinc.pro.backing.importer.datacheck;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Person;

public class DuplicateEmailChecker extends DataChecker {
    @Override
    public String checkForErrors(String... data)
    {
        String accountName = data[0];
        String employeeID = data[1];
        String email = data[2];
        if (accountName == null || email == null)
            return "ERROR: No account name or email specified.";

        Account account = dataCache.getAccount(accountName);
        if (account == null)
            return null;
        
        Person personFromEmpID = dataCache.getPersonForEmployeeID(account.getAccountID(), employeeID);
        Person person = dataCache.getPersonForEmail(email);
        if (personFromEmpID != null && person != null) {
            if (!personFromEmpID.getPersonID().equals(person.getPersonID())) {
                return "ERROR: The employeeID " + employeeID + " and the e-mail address " + email + " refer to 2 different Person records.  This is not allowed.";
            }
        }

        return null;
    }
}
