package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.aggregation.Speed;
import com.inthinc.pro.model.event.NoteType;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link com.inthinc.pro.dao.jdbc.EventStatisticsJDBCDAO}.
 */
public class EventStatisticsJDBCDAOTest {
    static EventStatisticsJDBCDAO eventStatisticsJDBCDAO;
    static DriverJDBCDAO driverJDBCDAO;
    static int TEST_DRIVER_ID;
    static int TEST_PERSON_ID;
    static int TEST_EVENT_ID = 1;
    static int TEST_ACCT_ID = 1;
    static int TEST_GROUP_ID = 1;
    static String TEST_DRIVER_first = "test-name";


    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        eventStatisticsJDBCDAO = new EventStatisticsJDBCDAO();
        driverJDBCDAO = new DriverJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        eventStatisticsJDBCDAO.setDataSource(dataSource);
        driverJDBCDAO.setDataSource(dataSource);

        // create test data
        createTestData();
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            deleteTestData();
        } catch (Throwable t) {/*ignore*/}
    }

    @Test
    public void testGetMaxSpeedForPastDays(){
        DateTime nowAndAFewMinutesAfter = new DateTime();
        nowAndAFewMinutesAfter = nowAndAFewMinutesAfter.plusMinutes(20);

        int maxSpeed = eventStatisticsJDBCDAO.getMaxSpeedForPastDays(TEST_DRIVER_ID, 6, 1, nowAndAFewMinutesAfter.toDate());
        assertEquals(99, maxSpeed);
    }

    @Test
    public void getSpeedingTimeInSecondsForPastDays(){
        DateTime nowAndAFewMinutesAfter = new DateTime();
        nowAndAFewMinutesAfter = nowAndAFewMinutesAfter.plusMinutes(20);

        int speedingTime = eventStatisticsJDBCDAO.getSpeedingTimeInSecondsForPastDays(TEST_DRIVER_ID, 6, 1, nowAndAFewMinutesAfter.toDate());
        assertEquals(100, speedingTime);
    }

    @Test
    public void getSpeedForPastDays(){
        DateTime nowAndAFewMinutesAfter = new DateTime();
        nowAndAFewMinutesAfter = nowAndAFewMinutesAfter.plusMinutes(20);
        Speed speed = eventStatisticsJDBCDAO.getSpeedInfoForPastDays(TEST_DRIVER_ID, MeasurementType.ENGLISH, 6, 1, nowAndAFewMinutesAfter.toDate());
        assertEquals(99, speed.getMaxSpeed().intValue());
        assertEquals(100, speed.getSpeedTime().intValue());
    }

    @Test
    public void getSpeedForAll(){
        Person person = new Person();
        Driver driver = new Driver();
        driver.setDriverID(TEST_DRIVER_ID);
        person.setPersonID(TEST_PERSON_ID);
        person.setDriver(driver);
        Map<Integer, Speed> speeds = eventStatisticsJDBCDAO.getSpeedInfoForPersons(Arrays.asList(person), 6);
        for (Map.Entry<Integer, Speed> speedEntry: speeds.entrySet()){
            assertEquals(TEST_DRIVER_ID, speedEntry.getKey().intValue());
            Speed speed = speedEntry.getValue();
            assertEquals(99, speed.getMaxSpeed().intValue());
            assertEquals(100, speed.getSpeedTime().intValue());
        }
    }

    /**
     * Tests that mile sum works.
     */
    @Test
    public void testSumMiles() {
        Integer aDriverId = eventStatisticsJDBCDAO.getSimpleJdbcTemplate().queryForInt("select a.driverID from " +
                "(select sum(coalesce(mileage,0)) mileageSum, driverID from trip " +
                "where startTime > CURRENT_DATE() - 10 group by driverID) a " +
                "where mileageSum > 0 limit 1");

        Integer foundTripMileage = eventStatisticsJDBCDAO.getTripMileageCountForDriver(aDriverId, new DateTime().minusDays(10).toDate(), new DateTime().toDate());
        Assert.assertNotNull(foundTripMileage);
        Assert.assertTrue(foundTripMileage > 0);
    }

    private static void createTestData() {

        // insert person
        Map<String, String> params = new HashMap<String, String>();
        params.put("acctID", String.valueOf(TEST_ACCT_ID));
        driverJDBCDAO.getSimpleJdbcTemplate().update("INSERT INTO person (acctID, tzID, modified, status, measureType, fuelEffType) " +
                " VALUES (:acctID, 1, NOW(), 1, 1, 1)", params);
        TEST_PERSON_ID = driverJDBCDAO.getSimpleJdbcTemplate().queryForInt("SELECT LAST_INSERT_ID()");


        // insert driver
        params = new HashMap<String, String>();
        params.put("personID", String.valueOf(TEST_PERSON_ID));
        params.put("groupID", String.valueOf(TEST_GROUP_ID));
        driverJDBCDAO.getSimpleJdbcTemplate().update("INSERT INTO driver (groupID, personID, groupPath, rfid1, rfid2, barcode, modified) " +
                " VALUES (:groupID, :personID, 'fleet', 12345, 56789, 'abc1234', NOW())", params);

        TEST_DRIVER_ID = driverJDBCDAO.getSimpleJdbcTemplate().queryForInt("SELECT LAST_INSERT_ID()");

        // insert event
        params = new HashMap<String, String>();
        params.put("driverID", String.valueOf(TEST_DRIVER_ID));
        params.put("noteID", String.valueOf(TEST_EVENT_ID));
        params.put("type", NoteType.SPEEDING.getCode().toString());
        eventStatisticsJDBCDAO.getSimpleJdbcTemplate().update("INSERT INTO cachedNote (noteID, driverID, vehicleID, topSpeed, duration, time, type, xtime, speed, flags, latitude, longitude) " +
                "VALUES(:noteID, :driverID, 0, 99, 100, NOW(), :type, 0, 0, 0, 0, 0)", params);
        eventStatisticsJDBCDAO.getSimpleJdbcTemplate().queryForInt("SELECT LAST_INSERT_ID()");
    }


    private static void deleteTestData() {
        // delete event
        Map<String, String> params = new HashMap<String, String>();
        params.put("noteID", String.valueOf(TEST_EVENT_ID));
        eventStatisticsJDBCDAO.getSimpleJdbcTemplate().update("DELETE FROM cachedNote WHERE noteID = :noteID", params);

        // delete driver
        params = new HashMap<String, String>();
        params.put("driverID", String.valueOf(TEST_DRIVER_ID));
        driverJDBCDAO.getSimpleJdbcTemplate().update("DELETE FROM driver WHERE driverID = :driverID", params);

        // delete person
        params = new HashMap<String, String>();
        params.put("personID", String.valueOf(TEST_PERSON_ID));
        driverJDBCDAO.getSimpleJdbcTemplate().update("DELETE FROM person WHERE personID = :personID", params);
    }
}
