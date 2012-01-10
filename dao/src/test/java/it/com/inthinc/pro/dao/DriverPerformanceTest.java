package it.com.inthinc.pro.dao;


import it.com.inthinc.pro.dao.model.GroupListData;
import it.com.inthinc.pro.dao.model.ITData;
import it.com.inthinc.pro.dao.model.ITDataExt;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.io.InputStream;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.hessian.proserver.ReportServiceCreator;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.hessian.report.DriverPerformanceReportHessianDAO;
import com.inthinc.pro.dao.hessian.report.GroupReportHessianDAO;
import com.inthinc.pro.dao.jdbc.VehiclePerformanceJDBCDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.VehiclePerformance;
import com.inthinc.pro.model.app.States;


public class DriverPerformanceTest {
    
    private static SiloService siloService;
    private static ReportService reportService;
    private static ITDataExt itData;
    
    private static final String PERF_REPORT_BASE_DATA_XML = "PerfReportTest.xml";

    public static Integer NUM_DRIVERS_VEHICLES_DEVICES = 3;
    public static DateTimeZone dateTimeZone;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        reportService = new ReportServiceCreator(host, port).getService();
        siloService = new SiloServiceCreator(host, port).getService();
        // HessianDebug.debugIn = true;
        // HessianDebug.debugOut = true;
        // HessianDebug.debugRequest = true;

        initApp();
        itData = new ITDataExt();
        
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PERF_REPORT_BASE_DATA_XML);

        if (!itData.parseTestDataExt(stream, siloService, NUM_DRIVERS_VEHICLES_DEVICES)) {
            throw new Exception("Error parsing Test data xml file");
        }
        
        TimeZone timeZone = TimeZone.getTimeZone(ReportTestConst.TIMEZONE_STR);
        dateTimeZone = DateTimeZone.forTimeZone(timeZone);
        Integer todayInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 0, ReportTestConst.TIMEZONE_STR);
        
        DateMidnight start = new DateMidnight(new DateTime(((long)itData.startDateInSec * 1000l), dateTimeZone), dateTimeZone);
        DateTime end = new DateMidnight(((long)todayInSec * 1000l), dateTimeZone).toDateTime().plusDays(1).minus(6000);
        Interval interval  = new Interval(start, end);
//        totalDays = interval.toPeriod(PeriodType.days()).getDays();
//          if (totalDays > MAX_TOTAL_DAYS)
//              totalDays = MAX_TOTAL_DAYS;
  }

    private static void initApp() throws Exception {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);

        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
    }
    
    @Test
    public void driverPerformance() {
        DriverPerformanceReportHessianDAO driverPerformanceReportHessianDAO = new DriverPerformanceReportHessianDAO();
        GroupReportHessianDAO groupReportHessianDAO = new GroupReportHessianDAO();
        groupReportHessianDAO.setReportService(reportService);
        VehiclePerformanceJDBCDAO vehiclePerformanceDAO = new VehiclePerformanceJDBCDAO();
        vehiclePerformanceDAO.setDataSource(new ITDataSource().getRealDataSource());
        
        driverPerformanceReportHessianDAO.setGroupReportDAO(groupReportHessianDAO);
        driverPerformanceReportHessianDAO.setVehiclePerformanceDAO(vehiclePerformanceDAO);
        
        
        Interval interval = TimeFrame.TODAY.getInterval(dateTimeZone);
System.out.println("Interval: " + interval);        
//        for (int groupIdx = ITData.GOOD; groupIdx <= ITData.BAD; groupIdx++) {
      int groupIdx = ITData.GOOD;  {
            GroupListData groupListData = itData.teamGroupListData.get(groupIdx);
            Group group = groupListData.group;
            List<DriverPerformance> driverPerformanceList = driverPerformanceReportHessianDAO.getDriverPerformanceListForGroup(group.getGroupID(), group.getName(), interval);
            for (DriverPerformance dp : driverPerformanceList) {
                System.out.println(dp.dump());
                if (dp.getVehiclePerformanceBreakdown() != null) {
                    for (VehiclePerformance vehiclePerformance : dp.getVehiclePerformanceBreakdown()) {
                        System.out.println("   " + vehiclePerformance.dump());
                        
                    }
                }

            }
            
            
            
//            List<DriverPerformanceKeyMetrics> list = driverPerformanceReportHessianDAO.getDriverPerformanceKeyMetricsListForGroup(
//                    group.getGroupID(), "test division", group.getName(), TimeFrame.TODAY);
//            assertEquals("1 item in key metrics list", 1, list.size());
//            DriverPerformanceKeyMetrics metrics = list.get(0);
//            assertEquals("total miles", expectedDailyMileagePerGroup[groupIdx], metrics.getTotalMiles().longValue());
//            assertEquals("driver", itData.teamGroupData.get(groupIdx).driver.getPerson().getFullName(), metrics.getDriverName());
//            assertEquals("team name", group.getName(), metrics.getTeamName());
//            assertEquals("Lo idle time", expectedDailyLoIdle[groupIdx], metrics.getLoIdleViolationsMinutes().intValue());
//            assertEquals("Hi idle time", expectedDailyHiIdle[groupIdx], metrics.getHiIdleViolationsMinutes().intValue());
//            assertEquals("idle violations count", expectedIdleViolationCount[groupIdx], metrics.getIdleViolationsCount().intValue());
//            assertEquals("Login count", expectedLoginCount[groupIdx], metrics.getLoginCount().intValue());
//            assertEquals("Overall Score", expectedTeamOverall[groupIdx], metrics.getOverallScore());
        }
        
    }

    
}
