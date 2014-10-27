package com.inthinc.pro.service.impl;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.PersonScoresView;
import com.inthinc.pro.service.PersonService;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class PersonServiceTest {
    static final String NAME_MODIFIER = "3";
    static final Integer ACCT_ID = 1;

    @Mocked
    public PersonDAO mockPersonDAO;

    private PersonService personService;

    private PersonDAOAdapter personDAOAdapter;

    private PersonDAOAdapter mockPersonDAOAdapter;

    // test data
    private Map<Integer, Person> testPeople;
    private Person person1;
    private Person person2;
    private Person person3;
    private Group group;


    @Before
    public void createTestData() {
        mockPersonDAOAdapter = new PersonDAOAdapter();
        mockPersonDAOAdapter.setPersonDAO(mockPersonDAO);

        personService = new PersonServiceImpl();
        PersonServiceImpl personServiceImpl = (PersonServiceImpl) personService;
        personServiceImpl.setDao(mockPersonDAOAdapter);

        testPeople = new HashMap<Integer, Person>();
        person1 = new Person();
        person2 = new Person();
        person3 = new Person();

        group = new Group();
        group.setGroupID(1);

        person1.setPersonID(1);
        person1.setFirst("test_pers_test_1_" + NAME_MODIFIER);
        person1.setAcctID(ACCT_ID);
        person1.setLast("LAST");
        person1.setEmpid("test_emp_test_1_" + NAME_MODIFIER);

        person2.setPersonID(2);
        person2.setFirst("test_pers_test_2_" + NAME_MODIFIER);
        person2.setAcctID(ACCT_ID);
        person2.setLast("LAST");
        person2.setEmpid("test_emp_test_2_" + NAME_MODIFIER);

        person3.setPersonID(3);
        person3.setFirst("test_pers_test_3_" + NAME_MODIFIER);
        person3.setAcctID(ACCT_ID);
        person3.setLast("LAST");
        person3.setEmpid("test_emp_test_3_" + NAME_MODIFIER);

        // add people to list
        testPeople.put(1, person1);
        testPeople.put(2, person2);
        testPeople.put(3, person3);
    }

    @After
    public void deleteTestData() {

    }

    @Test
    public void getLocationTest() {

        new Expectations() {{
            mockPersonDAO.findByID(person1.getPersonID());
            result = person1;
            mockPersonDAO.findByID(person2.getPersonID());
            result = person2;
            mockPersonDAO.findByID(person3.getPersonID());
            result = person3;
        }};

        for (int i = 1; i <= 3; i++) {
            Response response = personService.getPersonAndScores(i);
            assertNotNull(response.getEntity());
            PersonScoresView personScoresView = (PersonScoresView) response.getEntity();
            assertNotNull(personScoresView);
            assertEquals(personScoresView.getPersonID().intValue(), i);
        }
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Person getPerson3() {
        return person3;
    }

    public void setPerson3(Person person3) {
        this.person3 = person3;
    }

    public Person getPerson2() {
        return person2;
    }

    public void setPerson2(Person person2) {
        this.person2 = person2;
    }

    public Person getPerson1() {
        return person1;
    }

    public void setPerson1(Person person1) {
        this.person1 = person1;
    }

    public Map<Integer, Person> getTestPeople() {
        return testPeople;
    }

    public void setTestPeople(Map<Integer, Person> testPeople) {
        this.testPeople = testPeople;
    }

    public PersonDAOAdapter getMockPersonDAOAdapter() {
        return mockPersonDAOAdapter;
    }

    public void setMockPersonDAOAdapter(PersonDAOAdapter mockPersonDAOAdapter) {
        this.mockPersonDAOAdapter = mockPersonDAOAdapter;
    }

    public PersonDAOAdapter getPersonDAOAdapter() {
        return personDAOAdapter;
    }

    public void setPersonDAOAdapter(PersonDAOAdapter personDAOAdapter) {
        this.personDAOAdapter = personDAOAdapter;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public PersonDAO getMockPersonDAO() {
        return mockPersonDAO;
    }

    public void setMockPersonDAO(PersonDAO mockPersonDAO) {
        this.mockPersonDAO = mockPersonDAO;
    }
}
