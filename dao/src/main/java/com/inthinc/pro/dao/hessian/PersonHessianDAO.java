package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Person;

public class PersonHessianDAO extends GenericHessianDAO<Person, Integer> implements PersonDAO
{
    private static final Logger logger = Logger.getLogger(PersonHessianDAO.class);

    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer groupID)
    {
//        final List<Map<String, Object>> personIDs = getSiloService().getPersonsByGroupID(groupID);
//        final ArrayList<Person> people = new ArrayList<Person>(personIDs.size());
//        for (final Map<String, Object> map : personIDs)
//        {
//            Integer personID = getReturnKey(map);
//            people.add(findByID(personID));
//        }
//        return people;
        
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
        Integer personId = getReturnKey(getSiloService().getID("email", email));
        return findByID(personId);
    }


}
