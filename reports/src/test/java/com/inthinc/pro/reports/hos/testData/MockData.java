package com.inthinc.pro.reports.hos.testData;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.mock.data.MockStates;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;

public class MockData {
    
    
    public static Address createMockAddress(String groupName) {
        return new Address(0, "123 " + groupName, null, "City", new State(1, "Utah", "UT"), "12345", 0);
    }

    public static Account createMockAccount() {
        Account account = new Account(1, "Mock Company", Status.ACTIVE);
        account.setAddress(MockData.createMockAddress(account.getAcctName()));
        return account;
    }

    public static Group createMockGroup(Integer acctID) {
        Integer groupID = 15;
        Group group = new Group(groupID, acctID, "Fleet Group", 0);
        group.setAddress(new Address(15, "123 Group Street", "Suite 100", "Billings", MockStates.getByAbbrev("MT"), "59801", acctID));
        return group;
    }

    public static Driver createMockDriver(Integer acctID) {
        Integer driverID = 10;
        Driver driver = new Driver();
        driver.setDriverID(driverID);
        driver.setDot(RuleSetType.US);
        
        Person person = new Person();
        person.setFirst("First");
        person.setLast("Last");
        driver.setPerson(person);
        
        return driver;
    }

    public static Driver createMockDriver(Integer acctID, Integer driverID, String first, String last) {
        Driver driver = new Driver();
        driver.setDriverID(driverID);
        driver.setDot(RuleSetType.US);
        
        Person person = new Person();
        person.setFirst(first);
        person.setLast(last);
        driver.setPerson(person);
        
        return driver;
    }

}
