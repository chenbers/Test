package com.inthinc.pro.backing.importer.datacheck;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;

public class DuplicateUsernameChecker extends DataChecker {
 
    @Override
    public String checkForErrors(String... data)
    {
        String accountName = data[0];
        String employeeID = data[1];
        String username = data[2];
        if (accountName == null || employeeID == null || username == null)
            return "ERROR: No account name or username or employeeID specified.";

        Account account = DataCache.getAccount(accountName);
        if (account == null)
            return null;

        
        Person person = DataCache.getPersonForEmployeeID(account.getAccountID(), employeeID);
        User user = DataCache.getUserForUsername(username);
        if (user != null) {
            if (person == null || !user.getPerson().getPersonID().equals(person.getPersonID())) {
                return "ERROR: The employeeID " + employeeID + " and the username " + username + " refer to 2 different Person records.  This is not allowed.";
            }
        }
        
        return null;
    }
}
