package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.ReportHessianDAO;
import com.inthinc.pro.dao.hessian.mapper.DriverPerformanceMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.ReportJDBCDAO;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ReportJDBCDAOTest {
    static ReportJDBCDAO reportJDBCDAO;
    //for testdev.inthink test_group_id = 35
//    static int TEST_GROUP_ID = 35;
    static int TEST_GROUP_ID = 1;
    private static SiloService siloService;
    IntegrationConfig config = new IntegrationConfig();
    String host = config.get(IntegrationConfig.SILO_HOST).toString();
    Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

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
        //settings for Hessian
        siloService = new SiloServiceCreator(host, port).getService();

        ReportHessianDAO reportDAO = new ReportHessianDAO();
        reportDAO.setSiloService(siloService);
        DriverPerformanceMapper driverPerformanceMapper = new DriverPerformanceMapper();
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        driverPerformanceMapper.setDriverDAO(driverDAO);
        reportDAO.setDriverPerformanceMapper(driverPerformanceMapper);

        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
// for testdev.inthink
//        filterList.add(new TableFilterField("driverID", 97));
        //filterList.add(new TableFilterField("driverID", 1352));
        int count = reportJDBCDAO.getDriverReportCount(TEST_GROUP_ID, filterList);
        //hessian
        int countHesian = reportDAO.getDriverReportCount(TEST_GROUP_ID, filterList);
        // Compare count for ReportJDBCDAO and  ReportHessianDAO
        assertEquals("expected the getDriverReportCount for JDBC impl to match Hessian Impl", count, countHesian);

        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);
        pp.setFilterList(filterList);

        List<DriverReportItem> driverReportList = reportJDBCDAO.getDriverReportPage(TEST_GROUP_ID, pp);
        int driverReportListCount = driverReportList.size();
        assertTrue("expected not to be empty", !driverReportList.isEmpty());
        //HESSIAN
        List<DriverReportItem> driverReportListHessian = reportDAO.getDriverReportPage(TEST_GROUP_ID, pp);
        int driverReportListHessianCount = driverReportListHessian.size();
        assertTrue("expected not to be empty", !driverReportListHessian.isEmpty());
        // Compare lists for ReportJDBCDAO and  ReportHessianDAO
        assertEquals("expected the getDriverReportCount for JDBC impl to match Hessian Impl", driverReportListCount, driverReportListHessianCount);

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
        assertTrue("expected not to be empty", !driverReportList.isEmpty());
    }

    @Test
    public void testDeviceCountAndList() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
// for testdev.inthinc v_011596000035091
//        filterList.add(new TableFilterField("vehicleName", "v_01"));
        filterList.add(new TableFilterField("devicePhone", "8011"));
        filterList.add(new TableFilterField("deviceName", "850"));
        int count = reportJDBCDAO.getDeviceReportCount(TEST_GROUP_ID, filterList);
        assertTrue("expected to be 1 or >1", count > 0);

        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);
        pp.setFilterList(filterList);

        List<DeviceReportItem> driverReportList = reportJDBCDAO.getDeviceReportPage(TEST_GROUP_ID, pp);
        assertTrue("expected not to be empty", !driverReportList.isEmpty());

    }

    @Test
    public void testIdleDriverCountAndList() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
// for testdev.inthinc d 011596000035091
        //      filterList.add(new TableFilterField("driverName", "d 011"));
        filterList.add(new TableFilterField("driverName", "MIKE"));

        DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
        Interval interval = new Interval(new DateMidnight(new org.joda.time.DateTime().minusDays(7), dateTimeZone), new DateMidnight(new org.joda.time.DateTime(), dateTimeZone));

        int count = reportJDBCDAO.getIdlingReportCount(TEST_GROUP_ID, interval, filterList);
        assertTrue("expected to be 1 or >1", count > 0);

        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);
        pp.setFilterList(filterList);

        List<IdlingReportItem> driverReportList = reportJDBCDAO.getIdlingReportPage(TEST_GROUP_ID, interval, pp);
        assertTrue("expected not to be empty", !driverReportList.isEmpty());
    }

    @Test
    public void testIdleVehicleCountAndList() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
        filterList.add(new TableFilterField("driverName", "Speed"));
        DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
        Interval interval = new Interval(new DateMidnight(new org.joda.time.DateTime().minusYears(1), dateTimeZone), new DateMidnight(new org.joda.time.DateTime(), dateTimeZone));

        int count = reportJDBCDAO.getIdlingVehicleReportCount(TEST_GROUP_ID, interval, filterList);
        assertTrue("expected to be 1 or >1", count > 0);

        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);
        pp.setFilterList(filterList);

        List<IdlingReportItem> driverReportList = reportJDBCDAO.getIdlingVehicleReportPage(TEST_GROUP_ID, interval, pp);
        assertTrue("expected not to be empty", !driverReportList.isEmpty());

    }

    @Test
    public void testIdleVehicleCountByVehicleName() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
        filterList.add(new TableFilterField("vehicleName", "C"));
        DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
        Interval interval = new Interval(new DateMidnight(new org.joda.time.DateTime().minusYears(1), dateTimeZone), new DateMidnight(new org.joda.time.DateTime(), dateTimeZone));

        int count = reportJDBCDAO.getIdlingVehicleReportCount(TEST_GROUP_ID, interval, filterList);
        assertTrue("expected to be 1 or >1", count > 0);
    }
    
    @Test
    public void testIdlingVehicleReportSupportsIdleStatsCount() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
        filterList.add(new TableFilterField("driverName", "Tina"));
        DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
        Interval interval = new Interval(new DateMidnight(new org.joda.time.DateTime().minusDays(10), dateTimeZone), new DateMidnight(new org.joda.time.DateTime(), dateTimeZone));

        int count = reportJDBCDAO.getIdlingReportSupportsIdleStatsCount(TEST_GROUP_ID, interval, filterList);
        assertTrue("expected to be 1 or >1", count > 0);
    }

    @Test
    public void testIdlingReportSupportsIdleStatsCount() {
        List<TableFilterField> filterList = new ArrayList<TableFilterField>();
        filterList.add(new TableFilterField("driverName", "Tina"));
        DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
        Interval interval = new Interval(new DateMidnight(new org.joda.time.DateTime().minusDays(10), dateTimeZone), new DateMidnight(new org.joda.time.DateTime(), dateTimeZone));

        int count = reportJDBCDAO.getIdlingReportSupportsIdleStatsCount(TEST_GROUP_ID, interval, filterList);
        assertTrue("expected to be 1 or >1", count > 0);
    }
}
