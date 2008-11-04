package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Person;

public class PersonHessianDAO extends GenericHessianDAO<Person, Integer, CentralService> implements PersonDAO
{
    private static final Logger logger = Logger.getLogger(PersonHessianDAO.class);

    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer groupID)
    {
        final List<Map<String, Object>> personIDs = getSiloService().getPersonIDsInGroupHierarchy(groupID);
        final ArrayList<Person> people = new ArrayList<Person>(personIDs.size());
        for (final Map<String, Object> map : personIDs)
        {
            Integer personID = getReturnKey(map);
            people.add(findByID(personID));
        }
        return people;
    }
}
