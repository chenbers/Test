package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Test for {@link AdminDeviceJDBCDAO}.
 */
public class DeviceJDBCDAOTest {
    static DeviceJDBCDAO deviceJDBCDAO;
    static int TEST_DEVICE_ID = 1;
    static int TEST_ACCOUNT_ID = 1;
    static String TEST_DEVICE_NAME = "test-name";

    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        deviceJDBCDAO = new DeviceJDBCDAO();
        deviceJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        // ensure that at least one device is in the system before the test
        createTestDevice();
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            deleteTestDevice();
        } catch (Throwable t) {/*ignore*/}
    }

    /**
     * Tests that count and list work.
     */
    @Test
    public void testFindById() {
        Device retrievedDevice = deviceJDBCDAO.findByID(TEST_DEVICE_ID);
        assertEquals(retrievedDevice.getName(), TEST_DEVICE_NAME);
    }

    private static void createTestDevice() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("deviceID", String.valueOf(TEST_DEVICE_ID));
        params.put("acctID", String.valueOf(TEST_ACCOUNT_ID));
        deviceJDBCDAO.getSimpleJdbcTemplate().update("insert into device (acctID, imei, name, modified, activated, serialNum) values (:acctID, 'test-imei', 'test-name', NOW(), NOW(), 1234)", params);
        TEST_DEVICE_ID = deviceJDBCDAO.getSimpleJdbcTemplate().queryForInt("select LAST_INSERT_ID()");
    }

    private static void deleteTestDevice() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("deviceID", String.valueOf(TEST_DEVICE_ID));
        deviceJDBCDAO.getSimpleJdbcTemplate().update("delete from device where deviceID = :deviceID", params);
    }
}
