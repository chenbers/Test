package com.inthinc.pro.backing.importer.row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.security.Role;

public abstract class RowImporter {
    
    private Map<String, Account> accountMap;
    private Map<Integer, List<Group>> groupMap = new HashMap<Integer, List<Group>>();
    private Map<Integer, Role> roleMap = new HashMap<Integer, Role>();
    private AccountDAO accountDAO;
    private GroupDAO groupDAO;
    private RoleDAO roleDAO;

    public abstract String importRow(List<String> rowData); 

    public Map<String, Account> getAccountMap() {
        if (accountMap == null) {
            accountMap = new HashMap<String, Account>();
            List<Account> accountList = accountDAO.getAllAcctIDs();
            for (Account account : accountList)
                accountMap.put(account.getAcctName(), account);
        }
        return accountMap;
    }

    public void setAccountMap(Map<String, Account> accountMap) {
        this.accountMap = accountMap;
    }


    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }


    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public RoleDAO getRoleDAO() {
        return roleDAO;
    }

    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }


    public Role getAdminRole(Integer accountID) {
        Role role = roleMap.get(accountID);
        if (role == null) {
            List<Role> roles = roleDAO.getRoles(accountID);
            for (Role r : roles) {
                if (r.getName().toUpperCase().startsWith("ADMIN")) {
                    roleMap.put(accountID, r);
                    return r;
                }
            }
        }
        return role;
    }
    
    private List<Group> getGroupList(Integer accountID) {
        List<Group> groupList = groupMap.get(accountID);
        if (groupList == null) {
            groupList = groupDAO.getGroupsByAcctID(accountID);
            groupMap.put(accountID, groupList);
        }
        return groupList;
    }
    private void clearGroupList(Integer accountID) {
        groupMap.put(accountID, null);
    }
    
    public Integer getTopGroupID(Integer accountID)
    {
        List<Group> groups = getGroupList(accountID);
        for (Group group : groups)
            if (group.getParentID().equals(0))
                return group.getGroupID();
        
        return -1;
    }
    
    public Integer findOrCreateGroupByPath(String groupPath, Integer accountID)
    {
        List<Group> groups = getGroupList(accountID);
        String[] groupNameHierarchy = groupPath.split("/");
        
        Integer groupID =  createGroupByHierarchy(accountID, groups, groupNameHierarchy);
        clearGroupList(accountID);
        return groupID;
    }

    private Integer createGroupByHierarchy(Integer accountID, List<Group> groups, String[] groupNameHierarchy) {
        
        Integer parentID = 0;
        Integer groupID = -1;
        for (int i = 0; i <  groupNameHierarchy.length; i++) {
            String groupName = groupNameHierarchy[i];
            groupID = -1;
            for (Group group : groups) {
                if (group.getName().equalsIgnoreCase(groupName) && group.getParentID().equals(parentID)) {
                    parentID = group.getGroupID();
                    groupID = group.getGroupID();
                    break;
                }
                if (i == 0 && groupID == -1)
                    return null;
            }
            if (groupID.equals(-1)) {
                Group group = new Group(-1, accountID, groupName, parentID);
                GroupType type = GroupType.DIVISION;
                if (i == (groupNameHierarchy.length-1))
                    type=GroupType.TEAM;
                group.setType(type);

                groupID = groupDAO.create(accountID, group);
                group.setGroupID(groupID);
                groups.add(group);
                parentID = groupID;
            }
        }
        
        return groupID;
    }


}
