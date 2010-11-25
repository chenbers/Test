/**
 * 
 */
package com.inthinc.pro.service.test.mock;

import java.util.List;

import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Person;

/**
 * @author dfreitas
 * 
 */
@Component
public class PersonDaoStub implements PersonDAO {

    private Person expectedPerson;

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.PersonDAO#delete(com.inthinc.pro.model.Person)
     */
    @Override
    public Integer delete(Person person) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.PersonDAO#findByEmail(java.lang.String)
     */
    @Override
    public Person findByEmail(String email) {
        return expectedPerson;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.PersonDAO#getPeopleInGroupHierarchy(java.lang.Integer)
     */
    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer groupID) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.PersonDAO#getPeopleInGroupHierarchy(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer userGroupID, Integer driverGroupID) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#create(java.lang.Object, java.lang.Object)
     */
    @Override
    public Integer create(Integer id, Person entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#deleteByID(java.lang.Object)
     */
    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#findByID(java.lang.Object)
     */
    @Override
    public Person findByID(Integer id) {
        return expectedPerson;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#update(java.lang.Object)
     */
    @Override
    public Integer update(Person entity) {
        return 0;
    }

    public Person getExpectedPerson() {
        return expectedPerson;
    }

    public void setExpectedPerson(Person expectedPerson) {
        this.expectedPerson = expectedPerson;
    }

}
