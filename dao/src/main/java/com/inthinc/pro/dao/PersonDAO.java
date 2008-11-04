package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Person;

public interface PersonDAO extends GenericDAO<Person, Integer>
{
    List<Person> getPeopleInGroupHierarchy(Integer groupID);
}
