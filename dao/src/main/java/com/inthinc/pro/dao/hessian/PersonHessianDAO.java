package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;

public class PersonHessianDAO extends GenericHessianDAO<Person, Integer> implements PersonDAO, FindByKey<Person>
{
    private static final Logger logger = Logger.getLogger(PersonHessianDAO.class);
    
    private static final String CENTRAL_ID_KEY = "email";

    
    @Override
    public Integer create(Integer acctID, Person person)
    {
        if (person.getAddress() != null && person.getAddressID() == null)
        {
            Integer addressID = getReturnKey(getSiloService().createAddr(acctID, getMapper().convertToMap(person.getAddress())), Address.class);
            person.getAddress().setAddrID(addressID);
            person.setAddressID(addressID);
        }
        
        Integer personID =  super.create(acctID, person);
        
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
        Integer changedCount = super.update(person);
        
        if (person.getAddress() != null)
        {
            getSiloService().updateAddr(person.getAddress().getAddrID(), getMapper().convertToMap(person.getAddress()));
        }
        if (person.getDriver() != null)
        {
            getSiloService().updateDriver(person.getDriver().getDriverID(), getMapper().convertToMap(person.getDriver()));
        }
        if (person.getUser() != null)
        {
            getSiloService().updateUser(person.getUser().getUserID(), getMapper().convertToMap(person.getUser()));
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
        if (person.getAddressID() != null)
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
    }

    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer groupID)
    {
        try
        {
            List<Person> personList = getMapper().convertToModelObject(getSiloService().getPersonsByGroupID(groupID), Person.class);
            List<Person> returnPersonList = new ArrayList<Person>(); 
            for (Person person : personList)
            {
                populateInnerObjects(person.getPersonID(), person);
                returnPersonList.add(person);
            }
            return returnPersonList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }
    
    @Override
    public Person findByEmail(String email)
    {
        // TODO: it can take up to 5 minutes from when a person record is added until
        // it can be accessed via getID().   Should this method account for that?
        try
        {
            Map<String, Object> returnMap = getSiloService().getID(CENTRAL_ID_KEY, email);
            Integer personID = getCentralId(returnMap);
            Person person = findByID(personID);
            populateInnerObjects(personID, person);
            return person;
            
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


}
