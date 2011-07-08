package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Person;

public interface PersonDAO extends GenericDAO<Person, Integer>
{
    Person findByEmail(String email);

    /**
     * Returns all people in the given group and below, based on user.groupID and driver.groupID. If the user.groupID or driver.groupID don't fall under the group hierarchy, those
     * respective objects will be null in the returned person.
     * 
     * @param groupID
     *            The ID of the group within and under which to get people.
     * @return The list of people in the group hierarchy.
     */
    List<Person> getPeopleInGroupHierarchy(Integer groupID);

    /**
     * Returns all people in the given groups and below.
     * 
     * @param userGroupID
     *            The ID of the group within and under which to get people, matching by user.groupID.
     * @param driverGroupID
     *            The ID of the group within and under which to get people, matching by driver.groupID.
     * 
     * @return The list of people in the group hierarchy.
     */
    List<Person> getPeopleInGroupHierarchy(Integer userGroupID, Integer driverGroupID);

    Integer delete(Person person);

    List<Person> getPeopleInAccount(Integer acctID);
}
