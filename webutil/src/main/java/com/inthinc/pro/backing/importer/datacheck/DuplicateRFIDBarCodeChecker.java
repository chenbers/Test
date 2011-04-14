package com.inthinc.pro.backing.importer.datacheck;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;


public class DuplicateRFIDBarCodeChecker extends DataChecker {
    
    @Override
    public String checkForWarnings(String... data)
    {
        String accountName = data[0];
        String barCode = data[1];
        if (accountName == null || barCode == null)
            return "ERROR: No account name or barCode specified.";

        Account account = DataCache.getAccount(accountName);
        if (account == null)
            return null;

        Driver driver = DataCache.getDriverForBarCode(barCode);
        if (driver != null && driver.getPerson().getAccountID().equals(account.getAccountID()))
            return "WARNING: A Driver ( " + driver.getPerson().getFullName() + ") already exists with RFID bar code " + barCode + ".  On import the driver will be updated.";
        
        return null;
    }

    @Override
    public String checkForErrors(String... data)
    {
        String accountName = data[0];
        String barCode = data[1];
        if (accountName == null || barCode == null)
            return "ERROR: No account name or barCode specified.";

        Account account = DataCache.getAccount(accountName);
        if (account == null)
            return null;
        
        Driver driver = DataCache.getDriverForBarCode(barCode);
        if (driver != null && !driver.getPerson().getAccountID().equals(account.getAccountID())) {
            Account otherAccount = DataCache.getAccountForAccountID(driver.getPerson().getAccountID());
            return "ERROR: The barcode " + barCode + " is not available for use because it already exists in a different account (" + otherAccount.getAcctName() + ").";
        }
        return null;
    }

}
