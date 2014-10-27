package com.inthinc.pro.service.impl;

import com.inthinc.pro.dao.*;
import com.inthinc.pro.model.*;
import com.inthinc.pro.security.userdetails.ProUser;
import com.inthinc.pro.service.PersonService;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import com.inthinc.pro.service.it.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-dao.xml", "classpath:spring/applicationContext-beans.xml", "classpath:spring/applicationContext-security.xml"})
public class PersonServiceIntegrationTest extends BaseTest {
    static final String NAME_MODIFIER = "3";
    static final String TEST_USERNAME = "inthincTechSupportQA";
    static final String TEST_PASSWORD = "welcome456";

    @Autowired
    public UserDAO userDAO;
    @Autowired
    public PersonDAO personDAO;
    @Autowired
    public GroupDAO groupDAO;
    @Autowired
    public PersonService personService;

    private PersonDAOAdapter personDAOAdapter;

    // test data
    private Map<Integer, Person> testPeople;
    private Person person1;
    private Person person2;
    private Person person3;
    private Group group;

    private User testUser;

    @Before
    public void createTestData() {
        GrantedAuthority[] grantedAuthorities = new GrantedAuthorityImpl[1];
        grantedAuthorities[0] = new GrantedAuthorityImpl("ROLE_ADMIN");
        testUser = userDAO.findByUserName(TEST_USERNAME);
        ProUser proUser = new ProUser(testUser, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(proUser, TEST_PASSWORD));

        personDAOAdapter = new PersonDAOAdapter();
        personDAOAdapter.setPersonDAO(personDAO);

        testPeople = new HashMap<Integer, Person>();
        person1 = new Person();
        person2 = new Person();
        person3 = new Person();

        group = groupDAO.findByID(testUser.getGroupID());

        person1.setFirst("test_pers_test_1_" + NAME_MODIFIER);
        person1.setAcctID(group.getAccountID());
        person1.setLast("LAST");
        person1.setEmpid("test_emp_test_1_" + NAME_MODIFIER);
        person1.setStatus(Status.ACTIVE);
        person1.setTimeZone(TimeZone.getDefault());
        person1.setPersonID(personDAO.create(1, person1));

        person2.setFirst("test_pers_test_2_" + NAME_MODIFIER);
        person2.setAcctID(group.getAccountID());
        person2.setLast("LAST");
        person2.setEmpid("test_emp_test_2_" + NAME_MODIFIER);
        person2.setStatus(Status.ACTIVE);
        person2.setTimeZone(TimeZone.getDefault());
        person2.setPersonID(personDAO.create(1, person2));

        person3.setFirst("test_pers_test_3_" + NAME_MODIFIER);
        person3.setAcctID(group.getAccountID());
        person3.setLast("LAST");
        person3.setEmpid("test_emp_test_3_" + NAME_MODIFIER);
        person3.setStatus(Status.ACTIVE);
        person3.setTimeZone(TimeZone.getDefault());
        person3.setPersonID(personDAO.create(1, person3));

        // add people to list
        testPeople.put(1, person1);
        testPeople.put(2, person2);
        testPeople.put(3, person3);
    }

    @After
    public void deleteTestData() {
        for (Map.Entry<Integer, Person> personEntry : testPeople.entrySet()) {
            if (personEntry.getValue() != null && personEntry.getValue().getPersonID() != null)
                personDAO.deleteByID(personEntry.getValue().getPersonID());
        }
    }

    @Test
    public void getPersonAndScoresTest() {
        for (int i = 1; i <= 3; i++) {
            Response response = personService.getPersonAndScores(testPeople.get(i).getPersonID());
            assertNotNull(response.getEntity());
            PersonScoresView personScoresView = (PersonScoresView) response.getEntity();
            assertNotNull(personScoresView);
            assertEquals(personScoresView.getFirst(),"test_pers_test_"+i+"_" + NAME_MODIFIER);
        }
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public PersonDAOAdapter getPersonDAOAdapter() {
        return personDAOAdapter;
    }

    public void setPersonDAOAdapter(PersonDAOAdapter personDAOAdapter) {
        this.personDAOAdapter = personDAOAdapter;
    }

    public Map<Integer, Person> getTestPeople() {
        return testPeople;
    }

    public void setTestPeople(Map<Integer, Person> testPeople) {
        this.testPeople = testPeople;
    }

    public Person getPerson1() {
        return person1;
    }

    public void setPerson1(Person person1) {
        this.person1 = person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public void setPerson2(Person person2) {
        this.person2 = person2;
    }

    public Person getPerson3() {
        return person3;
    }

    public void setPerson3(Person person3) {
        this.person3 = person3;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getTestUser() {
        return testUser;
    }

    public void setTestUser(User testUser) {
        this.testUser = testUser;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
}
