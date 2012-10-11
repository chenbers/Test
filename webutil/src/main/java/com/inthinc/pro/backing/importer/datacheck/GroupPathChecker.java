package com.inthinc.pro.backing.importer.datacheck;

import java.util.List;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;

public class GroupPathChecker extends DataChecker {

    @Override
    public String checkForErrors(String... data) {
        
        String accountName = data[0];
        String groupPath = data[1];
        if (accountName == null || groupPath == null)
            return null;

        Account account = dataCache.getAccount(accountName);
        if (account == null)
            return null;

        boolean foundFleet = false;
        String[] groupName = groupPath.split("/");
        List<Group> groupList = dataCache.getGroupList(account.getAccountID());
        for (Group group : groupList) {
            if (group.getName().equals(groupName[0]) && group.getType() == GroupType.FLEET)
                foundFleet = true;
        }

        if (!foundFleet)
            return "ERROR: Fleet level group: " + groupName[0] + " not found.";
        
        String teamGroup = groupName[groupName.length-1];
        for (Group group : groupList) {
            if (group.getName().equals(teamGroup))
                if (group.getType() != GroupType.TEAM)
                    return "ERROR: Team level group: " + teamGroup + " expected.";
                else return null; // ok since last group is a team 
        }

        // groups will be created so walk backwards through groups and find 
        // last one that is already created, it must be a division level or fleet level
        for (int i = groupName.length-1; i > 0; i--) {
            for (Group group : groupList) {
                if (group.getName().equals(groupName[i]))
                    if (group.getType() == GroupType.TEAM)
                        return "ERROR: Groups cannot be created under a Team level group: " + group.getName();
                    else return null; // ok since we will be hanging new groups under a fleet or division
            }
            
        }
        
        
        return null;
    }
}
