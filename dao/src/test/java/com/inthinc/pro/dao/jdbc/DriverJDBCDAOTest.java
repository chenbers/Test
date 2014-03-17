package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.model.Driver;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link com.inthinc.pro.dao.jdbc.DriverJDBCDAO}.
 */
public class DriverJDBCDAOTest {
    static DriverJDBCDAO driverJDBCDAO;
    static int TEST_DRIVER_ID;
    static int TEST_PERSON_ID;
    static int TEST_ACCT_ID = 1;
    static int TEST_GROUP_ID = 1;


    static String TEST_DRIVER_first = "test-name";

    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        driverJDBCDAO = new DriverJDBCDAO();
        driverJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        // ensure that at least one driver is in the system before the test
        createTestDriver();
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            deleteTestDriver();
        } catch (Throwable t) {/*ignore*/}
    }

    /**
     * Tests that list work.
     */
    @Test
    public void testList() {
        List<Driver> drivers = driverJDBCDAO.getDriversInGroupIDList(Arrays.asList(TEST_GROUP_ID));
        assertFalse(drivers == null);
        assertFalse(drivers.isEmpty());

        boolean found = false;

        for (Driver driver: drivers){
            if(driver.getDriverID() == TEST_DRIVER_ID){
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    private static void createTestDriver() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("acctID", String.valueOf(TEST_ACCT_ID));
        driverJDBCDAO.getSimpleJdbcTemplate().update("insert into person (acctID, tzID, modified, status, measureType, fuelEffType) " +
                " values (:acctID, 1, NOW(), 1, 1, 1)", params);
        TEST_PERSON_ID = driverJDBCDAO.getSimpleJdbcTemplate().queryForInt("select LAST_INSERT_ID()");

        params = new HashMap<String, String>();
        params.put("personID", String.valueOf(TEST_PERSON_ID));
        params.put("groupID", String.valueOf(TEST_GROUP_ID));
        driverJDBCDAO.getSimpleJdbcTemplate().update("insert into driver (groupID, personID, groupPath, rfid1, rfid2, barcode, modified) " +
                " values (:groupID, :personID, 'fleet', 12345, 56789, 'abc123', NOW())", params);

        TEST_DRIVER_ID = driverJDBCDAO.getSimpleJdbcTemplate().queryForInt("select LAST_INSERT_ID()");
    }


    private static void deleteTestDriver() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("driverID", String.valueOf(TEST_DRIVER_ID));
        driverJDBCDAO.getSimpleJdbcTemplate().update("delete from driver where driverID = :driverID", params);

        params = new HashMap<String, String>();
        params.put("personID", String.valueOf(TEST_PERSON_ID));
        driverJDBCDAO.getSimpleJdbcTemplate().update("delete from person where personID = :personID", params);
    }
}
