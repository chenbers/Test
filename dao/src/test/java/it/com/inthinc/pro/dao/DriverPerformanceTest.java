package it.com.inthinc.pro.dao;


import static org.junit.Assert.assertEquals;
import it.com.inthinc.pro.dao.model.GroupListData;
import it.com.inthinc.pro.dao.model.ITData;
import it.com.inthinc.pro.dao.model.ITDataExt;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.io.InputStream;
import java.util.List;
import java.util.TimeZone;

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
import com.inthinc.pro.dao.hessian.report.GroupReportHessianDAO;
import com.inthinc.pro.dao.jdbc.VehiclePerformanceJDBCDAO;
import com.inthinc.pro.dao.report.impl.DriverPerformanceDAOImpl;
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

        initApp();
        itData = new ITDataExt();
        
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PERF_REPORT_BASE_DATA_XML);

        if (!itData.parseTestDataExt(stream, siloService, NUM_DRIVERS_VEHICLES_DEVICES)) {
            throw new Exception("Error parsing Test data xml file");
        }
        
        TimeZone timeZone = TimeZone.getTimeZone(ReportTestConst.TIMEZONE_STR);
        dateTimeZone = DateTimeZone.forTimeZone(timeZone);
        
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
        DriverPerformanceDAOImpl driverPerformanceReportHessianDAO = new DriverPerformanceDAOImpl();
        GroupReportHessianDAO groupReportHessianDAO = new GroupReportHessianDAO();
        groupReportHessianDAO.setReportService(reportService);
        VehiclePerformanceJDBCDAO vehiclePerformanceDAO = new VehiclePerformanceJDBCDAO();
        vehiclePerformanceDAO.setDataSource(new ITDataSource().getRealDataSource());
        
        driverPerformanceReportHessianDAO.setGroupReportDAO(groupReportHessianDAO);
        driverPerformanceReportHessianDAO.setVehiclePerformanceDAO(vehiclePerformanceDAO);
        
        
        Interval interval = TimeFrame.TODAY.getInterval(dateTimeZone);
        int groupIdx = ITData.GOOD;  
        GroupListData groupListData = itData.teamGroupListData.get(groupIdx);
        Group group = groupListData.group;
        List<DriverPerformance> driverPerformanceList = driverPerformanceReportHessianDAO.getDriverPerformanceListForGroup(group.getGroupID(), group.getName(), interval);
        boolean first = true;
        
        for (int j=0; j<5;j++){
            int i = 0;
            for (DriverPerformance dp : driverPerformanceList){
                System.out.println(dp.getScore() +  " " + i);
            }
            i++;
            driverPerformanceList = driverPerformanceReportHessianDAO.getDriverPerformanceListForGroup(group.getGroupID(), group.getName(), interval);
        }
        
        for (DriverPerformance dp : driverPerformanceList) {
            if (first) {
                assertEquals("1st Driver Score", 27, dp.getScore().intValue());
                assertEquals("1st Driver Mileage", 29700, dp.getTotalMiles().intValue());
            }
            else {
                assertEquals("2nd/3rd Driver Score", -1, dp.getScore().intValue());
                assertEquals("2nd/3rd Driver Mileage", 0, dp.getTotalMiles().intValue());
            }
            first = false;
            VehiclePerformance vpTotals = new VehiclePerformance("", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
            if (dp.getVehiclePerformanceBreakdown() != null) {
                for (VehiclePerformance vp : dp.getVehiclePerformanceBreakdown()) {
                    vpTotals.setHardAccelCount(vpTotals.getHardAccelCount() + vp.getHardAccelCount());
                    vpTotals.setHardVerticalCount(vpTotals.getHardVerticalCount() + vp.getHardVerticalCount());
                    vpTotals.setHardBrakeCount(vpTotals.getHardBrakeCount() + vp.getHardBrakeCount());
                    vpTotals.setHardTurnCount(vpTotals.getHardTurnCount() + vp.getHardTurnCount());
                    vpTotals.setSeatbeltCount(vpTotals.getSeatbeltCount() + vp.getSeatbeltCount());
                    vpTotals.setSpeedCount0to7Over(vpTotals.getSpeedCount0to7Over() + vp.getSpeedCount0to7Over());
                    vpTotals.setSpeedCount15Over(vpTotals.getSpeedCount15Over() + vp.getSpeedCount15Over());
                    vpTotals.setSpeedCount8to14Over(vpTotals.getSpeedCount8to14Over() + vp.getSpeedCount8to14Over());
                    vpTotals.setTotalMiles(vpTotals.getTotalMiles() + vp.getTotalMiles());
                }
                
                assertEquals("HardAccelCount", dp.getHardAccelCount(), vpTotals.getHardAccelCount());
                assertEquals("HardVerticalCount", dp.getHardVerticalCount(), vpTotals.getHardVerticalCount());
                assertEquals("HardBrakeCount", dp.getHardBrakeCount(), vpTotals.getHardBrakeCount());
                assertEquals("HardTurnCount", dp.getHardTurnCount(), vpTotals.getHardTurnCount());
                assertEquals("SeatbeltCount", dp.getSeatbeltCount(), vpTotals.getSeatbeltCount());
                assertEquals("SpeedCount0to7Over", dp.getSpeedCount0to7Over(), vpTotals.getSpeedCount0to7Over());
                assertEquals("SpeedCount15Over", dp.getSpeedCount15Over(), vpTotals.getSpeedCount15Over());
                assertEquals("SpeedCount8to14Over", dp.getSpeedCount8to14Over(), vpTotals.getSpeedCount8to14Over());
                assertEquals("HardAccelCount", dp.getHardAccelCount(), vpTotals.getHardAccelCount());
                assertEquals("TotalMiles", dp.getTotalMiles(), vpTotals.getTotalMiles());
            }
        }
    }
}
