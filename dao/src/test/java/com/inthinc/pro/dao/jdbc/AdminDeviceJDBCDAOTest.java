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
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Test for {@link com.inthinc.pro.dao.jdbc.AdminDeviceJDBCDAO}.
 */
public class AdminDeviceJDBCDAOTest {
    static AdminDeviceJDBCDAO deviceJDBCDAO;
    static int TEST_DEVICE_ID = 999999999;
    static int TEST_ACCOUNT_ID = 999999999;
    static String TEST_DEVICE_NAME = "test-name";

    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        deviceJDBCDAO = new AdminDeviceJDBCDAO();
        deviceJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        // ensure that at least one device is in the system before the test
        deviceJDBCDAO.createTestDevice(TEST_ACCOUNT_ID, TEST_DEVICE_ID);
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            deviceJDBCDAO.deleteTestDevice(TEST_DEVICE_ID);
        } catch (Throwable t) {/*ignore*/}
    }

    /**
     * Tests that count and list work.
     */
    @Test
    public void testCountAndList() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
        int count = deviceJDBCDAO.getCount(TEST_ACCOUNT_ID, filterList);
        assertEquals(count, 1);

        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(999999);
        pp.setFilterList(filterList);

        List<Device> deviceList = deviceJDBCDAO.getDevices(TEST_ACCOUNT_ID, pp);
        assertEquals(deviceList.size(), 1);

        Device retrievedDevice = deviceList.get(0);
        assertEquals(retrievedDevice.getName(), TEST_DEVICE_NAME);
    }
}
