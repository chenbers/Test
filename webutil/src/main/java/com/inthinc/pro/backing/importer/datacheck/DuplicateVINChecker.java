package com.inthinc.pro.backing.importer.datacheck;

import java.util.List;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Vehicle;

public class DuplicateVINChecker extends DataChecker {
    @Override
    public String checkForWarnings(String... data)
    {
        String accountName = data[0];
        String VIN = data[1];
        if (accountName == null || VIN == null)
            return null;

        Account account = DataCache.getAccountMap().get(accountName);
        if (account == null)
            return null;
        
        Vehicle vehicle = DataCache.getVehicleForVIN(VIN);
        if (vehicle != null)
            return "WARNING: A Vehicle ( " + vehicle.getFullName() + ") already exists with vin " + VIN + ".  On import the vehicle will be updated.";
        
        return null;
    }

    @Override
    public String checkForErrors(String... data)
    {
        String accountName = data[0];
        String VIN = data[1];
        if (accountName == null || VIN == null)
            return null;

        Account account = DataCache.getAccountMap().get(accountName);
        if (account == null)
            return null;
        
        List<Group> accountGroupList = DataCache.getGroupList(account.getAccountID());

        Vehicle vehicle = DataCache.getVehicleForVIN(VIN);
        if (vehicle != null) {
            for (Group group : accountGroupList)
                if (group.getGroupID().equals(vehicle.getGroupID()))
                    return null;
            
            Account otherAccount = DataCache.getAccountForGroupID(vehicle.getGroupID());
            return "ERROR: The vin " + VIN + " is currently used by a vehicle in account " + otherAccount + ".  The VIN cannot be used by vehicles in 2 different accounts.";
        }
        
        return null;
    }

}
