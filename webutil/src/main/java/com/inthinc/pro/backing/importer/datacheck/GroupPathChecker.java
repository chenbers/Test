package com.inthinc.pro.backing.importer.datacheck;

import java.util.List;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Group;

public class GroupPathChecker extends DataChecker {

    @Override
    public String checkForErrors(String... data) {
        
        String accountName = data[0];
        String groupPath = data[1];
        if (accountName == null || groupPath == null)
            return "ERROR: No account name or group path specified.";

        Account account = DataCache.getAccountMap().get(accountName);
        if (account == null)
            return null;

        boolean foundFleet = false;
        String[] groupName = groupPath.split("/");
        List<Group> groupList = DataCache.getGroupList(account.getAccountID());
        for (Group group : groupList) {
            if (group.getName().equals(groupName[0]))
                foundFleet = true;
        }

        if (!foundFleet)
            return "ERROR: Fleet level group: " + groupName[0] + " not found.";
        
        // TODO: check that group specified is a team or a team that can be created
        
        return null;
    }
}
