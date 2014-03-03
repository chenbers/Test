package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;
import jxl.write.DateTime;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class ReportJDBCDAOTest {
    static ReportJDBCDAO reportJDBCDAO;
//for testdev.inthink test_group_id = 35
//    static int TEST_GROUP_ID = 35;
    static int TEST_GROUP_ID = 1;

    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        reportJDBCDAO = new ReportJDBCDAO();
        reportJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
    }

    /**
     * Tests that count and list work.
     */
    @Test
    public void testDriverCountAndList() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
// for testdev.inthink
//        filterList.add(new TableFilterField("driverID", 97));
        filterList.add(new TableFilterField("driverID", 1352));
        int count = reportJDBCDAO.getDriverReportCount(TEST_GROUP_ID, filterList);
        assertTrue(count > 0);

        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);
        pp.setFilterList(filterList);

        List<DriverReportItem> driverReportList = reportJDBCDAO.getDriverReportPage(TEST_GROUP_ID, pp);
        assertTrue(!driverReportList.isEmpty());
    }

    @Test
    public void testVehicleCountAndList() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
// for testdev.inthinc
//        filterList.add(new TableFilterField("groupName", "2009"));
        filterList.add(new TableFilterField("groupName", "looo"));
        int count = reportJDBCDAO.getVehicleReportCount(TEST_GROUP_ID, filterList);
        assertTrue(count > 0);

        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);
        pp.setFilterList(filterList);

        List<VehicleReportItem> driverReportList = reportJDBCDAO.getVehicleReportPage(TEST_GROUP_ID, pp);
        assertTrue(!driverReportList.isEmpty());
    }

    @Test
    public void testDeviceCountAndList() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
// for testdev.inthinc v_011596000035091
//        filterList.add(new TableFilterField("vehicleName", "v_01"));
        filterList.add(new TableFilterField("devicePhone", "8011"));
        filterList.add(new TableFilterField("deviceName", "MIKE"));
        int count = reportJDBCDAO.getDeviceReportCount(TEST_GROUP_ID, filterList);
        assertTrue(count > 0);

        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);
        pp.setFilterList(filterList);

        List<DeviceReportItem> driverReportList = reportJDBCDAO.getDeviceReportPage(TEST_GROUP_ID, pp);
        assertTrue(!driverReportList.isEmpty());

    }

    @Test
    public void testIdleDriverCountAndList() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
// for testdev.inthinc d 011596000035091
//       filterList.add(new TableFilterField("driverName", "d 011"));
        filterList.add(new TableFilterField("driverName", "MIKE"));
//      filterList.add(new TableFilterField("hasRPM","1"));

        DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
        Interval interval = new Interval(new DateMidnight(new org.joda.time.DateTime().minusDays(1), dateTimeZone), new DateMidnight(new org.joda.time.DateTime(), dateTimeZone));

        int count = reportJDBCDAO.getIdlingReportCount(TEST_GROUP_ID, interval, filterList);
        assertTrue(count > 0);

        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);
        pp.setFilterList(filterList);

        List<IdlingReportItem> driverReportList = reportJDBCDAO.getIdlingReportPage(TEST_GROUP_ID, interval, pp);
        assertTrue(!driverReportList.isEmpty());
    }

//    @Test
//    public void testIdleVehicleCountAndList() {
//        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
//        filterList.add(new TableFilterField("driverName", "Speed"));
//        DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
//        Interval interval = new Interval(new DateMidnight(new org.joda.time.DateTime().minusDays(10), dateTimeZone), new DateMidnight(new org.joda.time.DateTime(), dateTimeZone));
//
//        int count = reportJDBCDAO.getIdlingVehicleReportCount(TEST_GROUP_ID, interval, filterList);
//        assertTrue(count > 0);
//
//        PageParams pp = new PageParams();
//        pp.setStartRow(0);
//        pp.setEndRow(20);
//        pp.setFilterList(filterList);
//
//        List<IdlingReportItem> driverReportList = reportJDBCDAO.getIdlingVehicleReportPage(TEST_GROUP_ID, interval, pp);
//        assertTrue(!driverReportList.isEmpty());
//
//    }

    @Test
    public void testIdlingVehicleReportSupportsIdleStatsCount() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
        filterList.add(new TableFilterField("driverName", "Tina"));
        DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
        Interval interval = new Interval(new DateMidnight(new org.joda.time.DateTime().minusDays(10), dateTimeZone), new DateMidnight(new org.joda.time.DateTime(), dateTimeZone));

        int count = reportJDBCDAO.getIdlingReportSupportsIdleStatsCount(TEST_GROUP_ID, interval, filterList);
        assertTrue(count > 0);
    }

    @Test
    public void testIdlingReportSupportsIdleStatsCount() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
        filterList.add(new TableFilterField("driverName", "Tina"));
        DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
        Interval interval = new Interval(new DateMidnight(new org.joda.time.DateTime().minusDays(10), dateTimeZone), new DateMidnight(new org.joda.time.DateTime(), dateTimeZone));

        int count = reportJDBCDAO.getIdlingReportSupportsIdleStatsCount(TEST_GROUP_ID, interval, filterList);
        assertTrue(count > 0);
    }
}
