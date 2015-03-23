package com.inthinc.pro.service.impl;

import com.inthinc.pro.dao.EventStatisticsDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.aggregation.Speed;
import com.inthinc.pro.service.PersonService;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.*;

import static junit.framework.Assert.*;

public class PersonServiceTest {
    static final String NAME_MODIFIER = "3";
    static final Integer ACCT_ID = 1;
    static final Integer maxSpeed = 100;
    static final Integer totalSpeeding = 90;
    static final Integer milesDriven = 20;

    @Mocked
    public PersonDAO mockPersonDAO;

    @Mocked
    DriverReportDAO mockDriverReportDAO;

    @Mocked
    EventStatisticsDAO mockEventStatisticsDAO;

    @Mocked
    ScoreDAO mockScoreDAO;

    private PersonService personService;

    private PersonDAOAdapter personDAOAdapter;

    private PersonDAOAdapter mockPersonDAOAdapter;

    private Map<String, Object> mockScoresMap;

    // test data
    private List<DVQMap> dvqMaps;
    private List<Person> allPeople;
    private Map<Integer, Person> testPeople;
    private Map<Integer, Speed> testSpeeds;
    private Driver driver1;
    private Driver driver2;
    private Driver driver3;
    private Person person1;
    private Person person2;
    private Person person3;
    private Speed speed1;
    private Speed speed2;
    private Speed speed3;
    private DVQMap dvqMap1;
    private DVQMap dvqMap2;
    private DVQMap dvqMap3;
    private DriveQMap driveQMap1;
    private DriveQMap driveQMap2;
    private DriveQMap driveQMap3;
    private Group group;


    @Before
    public void createTestData() {
        mockScoresMap = new HashMap<String, Object>();
        mockScoresMap.put("test", 123);

        mockPersonDAOAdapter = new PersonDAOAdapter();
        mockPersonDAOAdapter.setPersonDAO(mockPersonDAO);

        personService = new PersonServiceImpl();
        PersonServiceImpl personServiceImpl = (PersonServiceImpl) personService;
        personServiceImpl.setDao(mockPersonDAOAdapter);
        personServiceImpl.setDriverReportDAO(mockDriverReportDAO);
        personServiceImpl.setEventStatisticsDAO(mockEventStatisticsDAO);
        personServiceImpl.setScoreDAO(mockScoreDAO);

        allPeople = new ArrayList<Person>();
        dvqMaps = new ArrayList<DVQMap>();
        testPeople = new HashMap<Integer, Person>();
        testSpeeds = new HashMap<Integer, Speed>();

        driver1 = new Driver();
        driver2 = new Driver();
        driver3 = new Driver();
        person1 = new Person();
        person2 = new Person();
        person3 = new Person();

        group = new Group();
        group.setGroupID(1);

        driver1.setDriverID(1);
        person1.setPersonID(1);
        person1.setFirst("test_pers_test_1_" + NAME_MODIFIER);
        person1.setAcctID(ACCT_ID);
        person1.setLast("LAST");
        person1.setEmpid("test_emp_test_1_" + NAME_MODIFIER);
        person1.setDriver(driver1);

        driver2.setDriverID(2);
        person2.setPersonID(2);
        person2.setFirst("test_pers_test_2_" + NAME_MODIFIER);
        person2.setAcctID(ACCT_ID);
        person2.setLast("LAST");
        person2.setEmpid("test_emp_test_2_" + NAME_MODIFIER);
        person2.setDriver(driver2);

        driver3.setDriverID(3);
        person3.setPersonID(3);
        person3.setFirst("test_pers_test_3_" + NAME_MODIFIER);
        person3.setAcctID(ACCT_ID);
        person3.setLast("LAST");
        person3.setEmpid("test_emp_test_3_" + NAME_MODIFIER);
        person3.setDriver(driver3);

        // add people to list
        testPeople.put(1, person1);
        testPeople.put(2, person2);
        testPeople.put(3, person3);

        allPeople.add(person1);
        allPeople.add(person2);
        allPeople.add(person3);

        speed1 = new Speed();
        speed1.setDriverID(driver1.getDriverID());
        speed1.setMaxSpeed(100);
        speed1.setSpeedTime(10);

        speed2 = new Speed();
        speed2.setDriverID(driver2.getDriverID());
        speed2.setMaxSpeed(200);
        speed2.setSpeedTime(20);

        speed3 = new Speed();
        speed3.setDriverID(driver3.getDriverID());
        speed3.setMaxSpeed(300);
        speed3.setSpeedTime(30);

        testSpeeds.put(driver1.getDriverID(), speed1);
        testSpeeds.put(driver2.getDriverID(), speed2);
        testSpeeds.put(driver3.getDriverID(), speed3);

        driveQMap1 = new DriveQMap();
        driveQMap1.setAggressiveAccel(100);
        driveQMap1.setAggressiveAccelEvents(100);
        driveQMap1.setAggressiveBrake(100);
        driveQMap1.setAggressiveBrakeEvents(100);
        driveQMap1.setAggressiveBump(100);
        driveQMap1.setAggressiveBumpEvents(100);
        driveQMap1.setAggressiveLeftEvents(100);
        driveQMap1.setAggressiveRightEvents(100);

        driveQMap2 = new DriveQMap();
        driveQMap2.setAggressiveAccel(200);
        driveQMap2.setAggressiveAccelEvents(200);
        driveQMap2.setAggressiveBrake(200);
        driveQMap2.setAggressiveBrakeEvents(200);
        driveQMap2.setAggressiveBump(200);
        driveQMap2.setAggressiveBumpEvents(200);
        driveQMap2.setAggressiveLeftEvents(200);
        driveQMap2.setAggressiveRightEvents(200);

        driveQMap3 = new DriveQMap();
        driveQMap3.setAggressiveAccel(300);
        driveQMap3.setAggressiveAccelEvents(300);
        driveQMap3.setAggressiveBrake(300);
        driveQMap3.setAggressiveBrakeEvents(300);
        driveQMap3.setAggressiveBump(300);
        driveQMap3.setAggressiveBumpEvents(300);
        driveQMap3.setAggressiveLeftEvents(300);
        driveQMap3.setAggressiveRightEvents(300);

        dvqMap1 = new DVQMap();
        dvqMap1.setDriveQ(driveQMap1);
        dvqMap1.setDriver(driver1);

        dvqMap2 = new DVQMap();
        dvqMap2.setDriveQ(driveQMap2);
        dvqMap2.setDriver(driver2);

        dvqMap3 = new DVQMap();
        dvqMap3.setDriveQ(driveQMap3);
        dvqMap3.setDriver(driver3);

        dvqMaps.add(dvqMap1);
        dvqMaps.add(dvqMap2);
        dvqMaps.add(dvqMap3);
    }

    @After
    public void deleteTestData() {

    }

    @Test
    public void filterNonDriverTest() {
        List<Person> personList = new ArrayList<Person>();
        person1.setDriver(null);
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        PersonServiceImpl personServiceImpl = (PersonServiceImpl) personService;
        List<Person> filteredList = personServiceImpl.filterOutPersonsWithNoDriverId(personList);
        for (Person person : filteredList) {
            assertTrue(!person.getPersonID().equals(person1.getPersonID()));
        }
    }

    @Test
    public void getAllWithScore() {
        PersonServiceImpl personServiceImpl = (PersonServiceImpl) personService;
        PersonDAOAdapter personBaseDAOAdapter = new PersonDAOAdapter() {

            @Override
            public List<Person> getAll() {
                return Arrays.asList(person1, person2, person3);
            }

            @Override
            protected GenericDAO<Person, Integer> getDAO() {
                return mockPersonDAO;
            }

            @Override
            protected Integer getResourceID(Person resource) {
                return null;
            }

            @Override
            public Integer getGroupID() {
                return 1;
            }
        };


        new NonStrictExpectations() {{
            mockEventStatisticsDAO.getSpeedInfoForPersons(allPeople, 7);
            result = testSpeeds;
            mockScoreDAO.getDriveQMapList(1, 6);
            result = dvqMaps;
        }};

        personServiceImpl.setDao(personBaseDAOAdapter);
        Response response = personService.getAllWithScore(7);
        assertNotNull(response.getEntity());
        PersonScoresViewList personScoresViewList = (PersonScoresViewList) response.getEntity();
        assertNotNull(personScoresViewList);
        assertNotNull(personScoresViewList.getPersonScores());
        List<PersonScoresView> personScores = personScoresViewList.getPersonScores();
        assertEquals(3, personScores.size());

        for (int i = 0; i <= 2; i++) {
            PersonScoresView personScoresView = personScores.get(i);
            assertEquals(i + 1, personScoresView.getDriver().getDriverID().intValue());
            assertEquals(i + 1, personScoresView.getPersonID().intValue());
            assertEquals((double) (i + 1) * 100, personScoresView.getAggressiveAccel());
            assertEquals((double) (i + 1) * 100, personScoresView.getAggressiveAccelEvents());
            assertEquals((double) (i + 1) * 100, personScoresView.getAggressiveBrake());
            assertEquals((double) (i + 1) * 100, personScoresView.getAggressiveBrakeEvents());
            assertEquals((double) (i + 1) * 100, personScoresView.getAggressiveBumpEvents());
            assertEquals((double) (i + 1) * 200, personScoresView.getAggressiveTurnsEvents());
        }

        personServiceImpl.setDao(mockPersonDAOAdapter);
    }

    @Test
    public void getLocationTest() {

        new NonStrictExpectations() {{
            mockPersonDAO.findByID(person1.getPersonID());
            result = person1;
            mockDriverReportDAO.getScore(driver1.getDriverID(), Duration.SIX);
            result = mockScoresMap;
            result = milesDriven;
            mockEventStatisticsDAO.getMaxSpeedForPastDays(driver1.getDriverID(), 6, anyInt, (Date) any);
            result = maxSpeed;
            mockEventStatisticsDAO.getSpeedingTimeInSecondsForPastDays(driver1.getDriverID(), 6, anyInt, (Date) any);
            result = maxSpeed;
            mockPersonDAO.findByID(person2.getPersonID());
            result = person2;
            mockDriverReportDAO.getScore(driver2.getDriverID(), Duration.SIX);
            result = mockScoresMap;
            result = milesDriven;
            mockEventStatisticsDAO.getMaxSpeedForPastDays(driver2.getDriverID(), 6, anyInt, (Date) any);
            result = maxSpeed;
            mockEventStatisticsDAO.getSpeedingTimeInSecondsForPastDays(driver2.getDriverID(), 6, anyInt, (Date) any);
            result = maxSpeed;
            mockPersonDAO.findByID(person3.getPersonID());
            result = person3;
            mockDriverReportDAO.getScore(driver3.getDriverID(), Duration.SIX);
            result = mockScoresMap;
            result = milesDriven;
            mockEventStatisticsDAO.getMaxSpeedForPastDays(driver3.getDriverID(), 6, anyInt, (Date) any);
            result = maxSpeed;
            mockEventStatisticsDAO.getSpeedingTimeInSecondsForPastDays(driver3.getDriverID(), 6, anyInt, (Date) any);
            result = maxSpeed;
        }};

        for (int i = 1; i <= 3; i++) {
            Response response = personService.getPersonAndScores(i, 6);
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

    public DriverReportDAO getMockDriverReportDAO() {
        return mockDriverReportDAO;
    }

    public void setMockDriverReportDAO(DriverReportDAO mockDriverReportDAO) {
        this.mockDriverReportDAO = mockDriverReportDAO;
    }

    public EventStatisticsDAO getMockEventStatisticsDAO() {
        return mockEventStatisticsDAO;
    }

    public void setMockEventStatisticsDAO(EventStatisticsDAO mockEventStatisticsDAO) {
        this.mockEventStatisticsDAO = mockEventStatisticsDAO;
    }
}
