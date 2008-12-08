package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Person;

public class PersonHessianDAO extends GenericHessianDAO<Person, Integer> implements PersonDAO, FindByKey<Person>
{
    private static final Logger logger = Logger.getLogger(PersonHessianDAO.class);
    
    private static final String CENTRAL_ID_KEY = "email";

    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer groupID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getPersonsByGroupID(groupID), Person.class);
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
            Integer personId = getCentralId(returnMap);
            return findByID(personId);
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
