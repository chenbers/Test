package com.inthinc.pro.backing.importer.datacheck;


public class AccountNameChecker extends DataChecker {

    @Override
    public String checkForErrors(String... data) {
        
        String accountName = data[0];
        
        if (accountName == null || dataCache.getAccount(accountName) == null) {
            return "ERROR: Account " + accountName + " does not exist.";
        }

        return null;
    }

}
