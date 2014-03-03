package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link com.inthinc.pro.dao.jdbc.DriverJDBCDAO}.
 */
public class DriverJDBCDAOTest {
    static DriverJDBCDAO driverJDBCDAO;
    static int TEST_DRIVER_ID = 999999999;
    static int TEST_GROUP_ID = 999999999;
    static int TEST_ACCT_ID = 1;


    static String TEST_DRIVER_first = "test-name";

    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        driverJDBCDAO = new DriverJDBCDAO();
        driverJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        // ensure that at least one driver is in the system before the test
        driverJDBCDAO.createTestDriver(TEST_ACCT_ID, TEST_GROUP_ID, TEST_DRIVER_ID);
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            driverJDBCDAO.deleteTestDriver(TEST_DRIVER_ID);
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
        Driver driver = drivers.get(0);
        assertTrue(driver.getDriverID() == TEST_DRIVER_ID);
    }
}
