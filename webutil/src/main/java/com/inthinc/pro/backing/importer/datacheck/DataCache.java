package com.inthinc.pro.backing.importer.datacheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;

public class DataCache {
    
    private static Map<String, Account> accountMap;
    private static Map<Integer, List<Group>> groupMap;
    private static Map<Integer, List<Person>> personMap;
    private static AccountDAO accountDAO;
    private static GroupDAO groupDAO;
    private static PersonDAO personDAO;
    private static UserDAO userDAO;
    private static DriverDAO driverDAO;
    
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
    public static User getUserForUsername(String username) {
        return userDAO.findByUserName(username);
    }

    public static Person getPersonForEmail(String email) {
        return personDAO.findByEmail(email);
    }

    public static Account getAccountForAccountID(Integer accountID) {
        return accountDAO.findByID(accountID);
    }

    public static Driver getDriverForBarCode(String barcode) {
        Integer driverID = driverDAO.getDriverIDByBarcode(barcode);
        if (driverID == null)
            return null;
        
        return driverDAO.findByID(driverID);
    }


    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        DataCache.personDAO = personDAO;
    }
    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        DataCache.userDAO = userDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        DataCache.driverDAO = driverDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }
    public void setAccountDAO(AccountDAO accountDAO) {
        DataCache.accountDAO = accountDAO;
    }
    public GroupDAO getGroupDAO() {
        return groupDAO;
    }
    public void setGroupDAO(GroupDAO groupDAO) {
        DataCache.groupDAO = groupDAO;
    }



}
