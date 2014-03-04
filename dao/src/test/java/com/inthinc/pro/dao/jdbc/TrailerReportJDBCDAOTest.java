package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.model.TrailerReportItem;
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
 * Created by infrasoft04 on 3/4/14.
 */
public class TrailerReportJDBCDAOTest {
    static TrailerReportJDBCDAO trailerReportJDBCDAO;
    static int TEST_ACCOUNT_ID = 9999999;
    static int TEST_TRAILER_ID = 9999998;
    static int TEST_DEVICE_ID = 9999997;
    static int TEST_VEHICLE_ID = 9999996;
    static int TEST_GROUP_ID = 9999995;
    static int TEST_STATE_ID = 9999994;
    static int TEST_PERSON_ID = 9999993;
    static int TEST_ADDR_ID = 9999992;
    static int TEST_PARENT_ID = 9999991;
    static int TEST_MANAGER_ID = 9999990;
    static int TEST_DRIVER_ID = 8888888;
    static int TEST_VDDLOG_ID = 7777777;

    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        trailerReportJDBCDAO = new TrailerReportJDBCDAO();
        trailerReportJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        // ensure that at least one device is in the system before the test
        //create
        trailerReportJDBCDAO.createTestTrailerReport(TEST_ACCOUNT_ID,TEST_TRAILER_ID,TEST_DEVICE_ID,TEST_VEHICLE_ID,
                TEST_GROUP_ID,TEST_STATE_ID,TEST_PERSON_ID,TEST_ADDR_ID,TEST_PARENT_ID,TEST_MANAGER_ID,TEST_DRIVER_ID,TEST_VDDLOG_ID);
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            //delete
            trailerReportJDBCDAO.deleteTestTrailerReport(TEST_ACCOUNT_ID,TEST_TRAILER_ID, TEST_DEVICE_ID, TEST_VEHICLE_ID,
                    TEST_GROUP_ID,TEST_PERSON_ID,TEST_DRIVER_ID, TEST_VDDLOG_ID);
        } catch (Throwable t) {/*ignore*/}
    }

    @Test
    public void testTrailerReportItemByGroupPaging(){
        List<Integer> groupList = new ArrayList<Integer>();
        groupList.add(new Integer(TEST_GROUP_ID));
        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);
        List<TrailerReportItem> TrailerReportItemList = trailerReportJDBCDAO.getTrailerReportItemByGroupPaging(TEST_ACCOUNT_ID, groupList,pp);
        assertEquals(TrailerReportItemList.size(), 1);
    }
}
