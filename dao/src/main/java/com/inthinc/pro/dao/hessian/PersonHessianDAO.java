package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.MiscUtil;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;

public class PersonHessianDAO extends GenericHessianDAO<Person, Integer> implements PersonDAO, FindByKey<Person>
{
    private static final Logger logger = Logger.getLogger(PersonHessianDAO.class);

    private static final String CENTRAL_ID_KEY = "priEmail";

    @Override
    public Integer create(Integer acctID, Person person)
    {
        if (person.getAddress() != null && person.getAddressID() == null)
        {
            Integer addressID = getReturnKey(getSiloService().createAddr(acctID, getMapper().convertToMap(person.getAddress())), Address.class);
            person.getAddress().setAddrID(addressID);
            person.setAddressID(addressID);
        }

        Integer personID = super.create(acctID, person);

        if (person.getUser() != null && (person.getUser().getUserID() == null || person.getUser().getUserID().intValue() == 0))
        {
            person.getUser().setPersonID(personID);
            Integer userID = getReturnKey(getSiloService().createUser(personID, getMapper().convertToMap(person.getUser())), User.class);
            person.getUser().setUserID(userID);
        }

        if (person.getDriver() != null && (person.getDriver().getDriverID() == null || person.getDriver().getDriverID().intValue() == 0))
        {
            person.getDriver().setPersonID(personID);
            Integer driverID = getReturnKey(getSiloService().createDriver(personID, getMapper().convertToMap(person.getDriver())), Driver.class);
            person.getDriver().setDriverID(driverID);
        }

        return personID;
    }

    @Override
    public Integer update(Person person)
    {
        

        if ((person.getAddress() != null) && !person.getAddress().isEmpty() && person.getAddress().getAddrID() != null)
        {
            getSiloService().updateAddr(person.getAddressID(), getMapper().convertToMap(person.getAddress()));
        }
        else if (person.getAddress() != null && (person.getAddressID() == null || person.getAddressID().intValue() == 0))
        {
            Integer addressID = getReturnKey(getSiloService().createAddr(person.getAcctID(), getMapper().convertToMap(person.getAddress())), Address.class);
            person.getAddress().setAddrID(addressID);
            person.setAddressID(addressID);
        }
        
        Integer changedCount = super.update(person);

        if (person.getDriver() != null)
        {
            if (person.getDriver().getDriverID() == null || person.getDriver().getDriverID().intValue() == 0)
            {
                person.getDriver().setPersonID(person.getPersonID());
                Integer driverID = getReturnKey(getSiloService().createDriver(person.getPersonID(), getMapper().convertToMap(person.getDriver())), Driver.class);
                person.getDriver().setDriverID(driverID);
            }
            else
            {
                getSiloService().updateDriver(person.getDriver().getDriverID(), getMapper().convertToMap(person.getDriver()));
            }
        }

        if (person.getUser() != null)
        {
            if (person.getUser().getUserID() == null || person.getUser().getUserID().intValue() == 0)
            {
                person.getUser().setPersonID(person.getPersonID());
                Integer userID = getReturnKey(getSiloService().createUser(person.getPersonID(), getMapper().convertToMap(person.getUser())), User.class);
                person.getUser().setUserID(userID);
            }
            else
            {
                getSiloService().updateUser(person.getUser().getUserID(), getMapper().convertToMap(person.getUser()));
            }
        }

        return changedCount;
    }

    @Override
    public Person findByID(Integer personID)
    {
        Person person = super.findByID(personID);
        if (person != null)
        {
            populateInnerObjects(personID, person);
        }
        return person;
    }

    private void populateInnerObjects(Integer personID, Person person)
    {
        // attach driver if there is one
        Driver driver = null;
        try
        {
            driver = getMapper().convertToModelObject(getSiloService().getDriverByPersonID(personID), Driver.class);
        }
        catch (EmptyResultSetException e)
        {
            // ignore -- no driver is attached to person
        }
        person.setDriver(driver);

        // attach user if there is one
        User user = null;
        try
        {
            user = getMapper().convertToModelObject(getSiloService().getUserByPersonID(personID), User.class);
        }
        catch (EmptyResultSetException e)
        {
            // ignore -- no user is attached to person
        }
        person.setUser(user);

        // attach address if there is one
        // cj 12/12/09 -- commented this out because we are not using the address anywhere and it was a big performance hit
/*        
        if (person.getAddressID() != null && person.getAddress() == null)
        {
            Address address = null;
            try
            {
                address = getMapper().convertToModelObject(getSiloService().getAddr(person.getAddressID()), Address.class);
            }
            catch (EmptyResultSetException e)
            {
                // ignore -- no address is attached to person
            }
            person.setAddress(address);
        }
*/        
    }

    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer groupID)
    {
        return getPeopleInGroupHierarchy(groupID, groupID);
    }

    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer userGroupID, Integer driverGroupID)
    {
        List<Person> returnPersonList = new ArrayList<Person>();
        try
        {
            List<User> userList = MiscUtil.removeInThinc(getMapper().convertToModelObject(getSiloService().getUsersByGroupIDDeep(userGroupID), User.class));
            for (User user : userList)
            {
                user.getPerson().setUser(user);
                returnPersonList.add(user.getPerson());
            }
        }
        catch (EmptyResultSetException e)
        {
        }

        try
        {
            List<Driver> driverList = getMapper().convertToModelObject(getSiloService().getDriversByGroupIDDeep(driverGroupID), Driver.class);
            for (Driver driver : driverList)
            {
                Person person = findPersonInList(returnPersonList, driver);
                if (person == null)
                {
                    driver.getPerson().setDriver(driver);
                    returnPersonList.add(driver.getPerson());
                }
            }
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        // cj 12/12/09 -- commented this out because we are not using the address anywhere and it was a big performance hit
/*
        for (Person person : returnPersonList)
        {
            if (person.getAddressID() != null  && person.getAddress() == null)
            {
                try
                {
                    person.setAddress(getMapper().convertToModelObject(getSiloService().getAddr(person.getAddressID()), Address.class));
                }
                catch (EmptyResultSetException e)
                {
                    // ignore -- no address is attached to person
                }
            }
        }
*/
        return returnPersonList;
    }

    private Person findPersonInList(List<Person> returnPersonList, Driver driver)
    {
        for (Person person : returnPersonList)
        {
            if (person.getPersonID().equals(driver.getPersonID()))
            {
                person.setDriver(driver);
                return person;
            }
        }
        return null;
    }

    @Override
    public Person findByEmpID( Integer acctID, String empID) {
        return getMapper().convertToModelObject(getSiloService().getPersonByEmpid(acctID, empID), Person.class);
    }
    @Override
    public Person findByEmail(String email)
    {
        // TODO: it can take up to 5 minutes from when a person record is added until
        // it can be accessed via getID(). Should this method account for that?
        try
        {
            Map<String, Object> returnMap = getSiloService().getID(CENTRAL_ID_KEY, email);
            Integer personID = getCentralId(returnMap);
            return findByID(personID);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }

    @Override
    public Person findByKey(String key)
    {
        return findByEmail(key);
    }

    @Override
    public Integer delete(Person person)
    {
        if ((person.getUser() != null) && (person.getUser().getUserID() != null))
            getSiloService().deleteUser(person.getUser().getUserID());
        if ((person.getDriver() != null) && (person.getDriver().getDriverID() != null))
            getSiloService().deleteDriver(person.getDriver().getDriverID());

        return super.deleteByID(person.getPersonID());
    }

    @Override
    public List<Person> getPeopleInAccount(Integer acctID) {
        return getMapper().convertToModelObject(getSiloService().getPersonsByAcctID(acctID), Person.class);
    }
}
