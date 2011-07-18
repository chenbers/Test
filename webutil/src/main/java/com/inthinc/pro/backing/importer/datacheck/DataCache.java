package com.inthinc.pro.backing.importer.datacheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;

public class DataCache {
    
    private Map<String, Account> accountMap;
    private Map<Integer, List<Group>> groupMap;
    private Map<Integer, List<Person>> personMap;
    private AccountDAO accountDAO;
    private GroupDAO groupDAO;
    private PersonDAO personDAO;
    private UserDAO userDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private DeviceDAO deviceDAO;
    
    public void init()
    {
        initAccountMap();
        initGroupMap();
        initPersonMap();
    }

    public Map<Integer, List<Group>> getGroupMap() {
        return groupMap;
    }
    
    public  Account getAccount(String accountName) {
        return accountMap.get(accountName);
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
    
    public  List<Group> getGroupList(Integer accountID)
    {
        
        List<Group> groups = groupMap.get(accountID);
        if (groups == null) {
            groups = groupDAO.getGroupsByAcctID(accountID);
            groupMap.put(accountID, groups);
        }
        
        return groups;
    }
    public  Group getFleetGroup(Integer accountID)
    {
        
        List<Group> groups = getGroupList(accountID);
        for (Group group : groups)
            if (group.getType() == GroupType.FLEET)
                return group;
        return null;
    }
    
    public  Person getPersonForEmployeeID(Integer accountID, String employeeID) {

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
    
    public  Account getAccountForGroupID(Integer groupID) {
        Group group = groupDAO.findByID(groupID);
        if (group != null) {
            return getAccountForAccountID(group.getAccountID());
        }
        return null;
    }

    public  User getUserForUsername(String username) {
        return userDAO.findByUserName(username);
    }

    public  Person getPersonForEmail(String email) {
        return personDAO.findByEmail(email);
    }

    public  Account getAccountForAccountID(Integer accountID) {
        return accountDAO.findByID(accountID);
    }

    public  Driver getDriverForBarCode(String barcode) {
        Integer driverID = driverDAO.getDriverIDByBarcode(barcode);
        if (driverID == null)
            return null;
        
        return driverDAO.findByID(driverID);
    }

    public  Vehicle getVehicleForVIN(String vin) {
        return vehicleDAO.findByVIN(vin);
    }
    
    public Device getDeviceForSerialNumber(String serialNumber) {
        
        return deviceDAO.findBySerialNum(serialNumber);
    }

    public Device getDeviceForIMEI(String imei) {
        
        return deviceDAO.findByIMEI(imei);
    }
    
    public  boolean isValidRFIDBarcode(String barcode)
    {
        List<Long> rfidList = driverDAO.getRfidsByBarcode(barcode);
        
        return !(rfidList == null || rfidList.isEmpty());
    }
    
    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
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
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }
    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }


    




}
