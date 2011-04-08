package com.inthinc.pro.backing.importer.datacheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;

public class DataCache {
    
    private static Map<String, Account> accountMap;
    private static Map<Integer, List<Group>> groupMap;
    private static Map<Integer, List<Person>> personMap;
    private AccountDAO accountDAO;
    private static GroupDAO groupDAO;
    private static PersonDAO personDAO;
    
    public void init()
    {
        initAccountMap();
        initGroupMap();
        initPersonMap();
    }

    public static Map<String, Account> getAccountMap() {
        return accountMap;
    }
    public Map<Integer, List<Group>> getGroupMap() {
        return groupMap;
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

    private void initAccountMap() {
        accountMap = new HashMap<String, Account>();
        List<Account> accountList = accountDAO.getAllAcctIDs();
        for (Account account : accountList)
            accountMap.put(account.getAcctName(), account);
    }

    private void initGroupMap() {
        groupMap = new HashMap<Integer, List<Group>>();
    }
    private void initPersonMap() {
        personMap = new HashMap<Integer, List<Person>>();
    }
    
    public static List<Group> getGroupList(Integer accountID)
    {
        
        List<Group> groups = groupMap.get(accountID);
        if (groups == null) {
            groups = groupDAO.getGroupsByAcctID(accountID);
            groupMap.put(accountID, groups);
        }
        
        return groups;
    }
    public static Group getFleetGroup(Integer accountID)
    {
        
        List<Group> groups = getGroupList(accountID);
        for (Group group : groups)
            if (group.getType() == GroupType.FLEET)
                return group;
        return null;
    }
    
    public static Person getPersonForEmployeeID(Integer accountID, String employeeID) {

        List<Person> persons = personMap.get(accountID);

        if (persons == null) {
            Group fleetGroup = getFleetGroup(accountID);
        
            persons = personDAO.getPeopleInGroupHierarchy(fleetGroup.getGroupID());
            personMap.put(accountID, persons);
        }
        
        for (Person person : persons) {
            if (person.getStatus()!=null && person.getStatus().equals(Status.ACTIVE) && employeeID.trim().equals(person.getEmpid())) {
                return person;
            }
        }
        
        return null;

    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


}
