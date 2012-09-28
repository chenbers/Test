package com.inthinc.pro.backing.importer.datacheck;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;

public class DeviceSerialorIMEIChecker extends DataChecker {

    @Override
    public String checkForErrors(String... data) {
        
        String accountName = data[0];
        String serialOrIMEI = data[1];
        if (accountName == null || serialOrIMEI == null)
            return null;

        Account account = dataCache.getAccount(accountName);
        if (account == null)
            return null;
        
        Device device = dataCache.getDeviceForSerialNumber(serialOrIMEI);
        if (device == null)
            device = dataCache.getDeviceForIMEI(serialOrIMEI);
        
        if (device == null) {
            return "ERROR: The device with Serial Number or IMEI " + serialOrIMEI + " does not exist.";
        }
        
        if (!device.getAccountID().equals(account.getAccountID())) {
            Account otherAccount = dataCache.getAccountForAccountID(device.getAccountID());
            return "ERROR: The device with Serial Number or IMEI " + serialOrIMEI + " exists in a different account: " + otherAccount.toString() + " This is not allowed.";
        }
        
        return null;
    }
}
