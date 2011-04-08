package com.inthinc.pro.backing.importer.datacheck;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Person;

public class DuplicateEmployeeIDChecker extends DataChecker {
    
    public String checkForWarnings(String... data)
    {
        String accountName = data[0];
        String employeeID = data[1];
        if (accountName == null || employeeID == null)
            return "ERROR: No account name or employeeID specified.";

        Account account = DataCache.getAccountMap().get(accountName);
        if (account == null)
            return null;

        
        Person person = DataCache.getPersonForEmployeeID(account.getAccountID(), employeeID);
        if (person != null)
            return "WARNING: A Person ( " + person.getFullName() + ") already exists with employeeID " + employeeID + ".  On import the person will be updated.";
        
        return null;
    }

}
