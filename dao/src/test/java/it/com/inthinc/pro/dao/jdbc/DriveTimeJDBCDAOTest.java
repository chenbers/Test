package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.*;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.VehicleEventData;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.DriveTimeJDBCDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;
import com.inthinc.pro.model.app.States;


public class DriveTimeJDBCDAOTest extends BaseJDBCTest {
    
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();
        // HessianDebug.debugIn = true;
        // HessianDebug.debugOut = true;
        // HessianDebug.debugRequest = true;

        initApp();
        itData = new ITData();
        
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }

//        TimeZone timeZone = TimeZone.getTimeZone(ReportTestConst.TIMEZONE_STR);
//        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);
//        Integer todayInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 0, ReportTestConst.TIMEZONE_STR);
        
//        DateMidnight start = new DateMidnight(new DateTime(((long)itData.startDateInSec * 1000l), dateTimeZone), dateTimeZone);
//        DateTime end = new DateMidnight(((long)todayInSec * 1000l), dateTimeZone).toDateTime().plusDays(1).minus(6000);
//        Interval interval  = new Interval(start, end);
  }

    private static void initApp() throws Exception {
    }

    @Test
    @Ignore
    public void testDriveTime() {
        
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");


        TimeZone timeZone = TimeZone.getTimeZone(ReportTestConst.TIMEZONE_STR);
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);
        Integer todayInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 0, ReportTestConst.TIMEZONE_STR);
        
        int numDays = 7;
        DateTime end = new DateMidnight(((long)todayInSec * 1000l), dateTimeZone).toDateTime();
        DateMidnight start = new DateMidnight(new DateTime(end, dateTimeZone).minusDays(numDays), dateTimeZone);
        Interval interval  = new Interval(start, end);
        
        DriveTimeDAO driveTimeDAO = new DriveTimeJDBCDAO();
        ((DriveTimeJDBCDAO)driveTimeDAO).setDataSource(new ITDataSource().getRealDataSource());

        
        for (GroupData testGroupData : itData.teamGroupData) {
            Driver testDriver = testGroupData.driver;
            Vehicle testVehicle = testGroupData.vehicle;
            
            List<DriveTimeRecord>driveTimeRecordList = driveTimeDAO.getDriveTimeRecordList(testDriver, interval);
            assertEquals("record count", numDays, driveTimeRecordList.size());

            long expectedDriveTime = 1410;
            
            DateTime startDate = start.toDateTime();
            for (DriveTimeRecord rec : driveTimeRecordList) {
                System.out.println(dateFormatter.print(rec.getDateTime()) + " " + rec.getVehicleName() + " " + rec.getDriverID() + " " + rec.getDriveTimeSeconds());
                assertEquals("date", dateFormatter.print(startDate), dateFormatter.print(rec.getDateTime()));
                assertEquals("driverID", testDriver.getDriverID(), rec.getDriverID());
                assertEquals("vehicleName", testVehicle.getName(), rec.getVehicleName());
                assertEquals("vehicleID", testVehicle.getVehicleID(), rec.getVehicleID());
                assertEquals("driveTime", expectedDriveTime, rec.getDriveTimeSeconds().longValue());
                
                startDate = startDate.plusDays(1);
                


            }
        }


    }


    @Test
    @Ignore //TODO: re-enable after determining what happened with the agg for VehicleGOOD 2-26-2014
    public void testDriveTimeTeamGroup() {
        
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        
        int numDays = 7;
        DateTime end = new DateMidnight().toDateTime();
        DateTime start = new DateTime(end).minusDays(numDays-1);
        Interval interval  = new Interval(start, end);
        
        DriveTimeDAO driveTimeDAO = new DriveTimeJDBCDAO();
        ((DriveTimeJDBCDAO)driveTimeDAO).setDataSource(new ITDataSource().getRealDataSource());

        
        for (GroupData testGroupData : itData.teamGroupData) {
            Driver testDriver = testGroupData.driver;
            Vehicle testVehicle = testGroupData.vehicle;
            
            List<DriveTimeRecord>driveTimeRecordList = driveTimeDAO.getDriveTimeRecordListForGroup(testGroupData.group.getGroupID(), interval);
            assertEquals("record count", numDays, driveTimeRecordList.size());

            long expectedDriveTime = 1410;
            
            DateTime startDate = start.toDateTime();
            for (DriveTimeRecord rec : driveTimeRecordList) {
                System.out.println(dateFormatter.print(rec.getDateTime()) + " " + rec.getVehicleName() + " " + rec.getDriverID() + " " + rec.getDriveTimeSeconds());
                assertEquals("date", dateFormatter.print(startDate), dateFormatter.print(rec.getDateTime()));
                assertEquals("driverID", testDriver.getDriverID(), rec.getDriverID());
                assertEquals("vehicleName", testVehicle.getName(), rec.getVehicleName());
                assertEquals("vehicleID", testVehicle.getVehicleID(), rec.getVehicleID());
                assertEquals("driveTime", expectedDriveTime, rec.getDriveTimeSeconds().longValue());
                
                startDate = startDate.plusDays(1);
            }
        }
    }


    @Test
    public void testGetDriveTimeSum() {
        DriveTimeDAO driveTimeDAO = new DriveTimeJDBCDAO();
        ((DriveTimeJDBCDAO)driveTimeDAO).setDataSource(new ITDataSource().getRealDataSource());
        GroupData testGroupData =itData.teamGroupData.get(0);
        Vehicle testVehicle = testGroupData.vehicle;
        Long getDriveTimeSum = driveTimeDAO.getDriveTimeSum(testVehicle);
        assertNotNull("record not null",getDriveTimeSum);
        assertEquals("record count", Long.valueOf("0"), getDriveTimeSum);
    }

    @Test
    public void testGetDriveOdometerSum() {
        DriveTimeDAO driveTimeDAO = new DriveTimeJDBCDAO();
        ((DriveTimeJDBCDAO)driveTimeDAO).setDataSource(new ITDataSource().getRealDataSource());
        GroupData testGroupData =itData.teamGroupData.get(0);
        Vehicle testVehicle = testGroupData.vehicle;
        Long getOdometerSum = driveTimeDAO.getDriveOdometerSum(testVehicle);
        assertNotNull("record not null",getOdometerSum);
        assertEquals("record count", Long.valueOf("237600"), getOdometerSum);
    }

    @Test
    public void testDriveOdometerAtDates(){
        DriveTimeDAO driveTimeDAO = new DriveTimeJDBCDAO();
        ((DriveTimeJDBCDAO)driveTimeDAO).setDataSource(new ITDataSource().getRealDataSource());
        GroupData testGroupData1 = itData.teamGroupData.get(0);
        GroupData testGroupData2 = itData.teamGroupData.get(1);
        Vehicle testVehicle1 = testGroupData1.vehicle;
        Vehicle testVehicle2 = testGroupData2.vehicle;
        Device testDevice1 = testGroupData1.device;
        Device testDevice2 = testGroupData2.device;

        VehicleEventData vehicleEventData = new VehicleEventData();
        vehicleEventData.putDate(testVehicle1.getVehicleID(), new Date());
        vehicleEventData.putDate(testVehicle2.getVehicleID(), new Date());

        vehicleEventData.putDeviceID(testVehicle1.getVehicleID(), testDevice1.getDeviceID());
        vehicleEventData.putDeviceID(testVehicle2.getVehicleID(), testDevice2.getDeviceID());

        vehicleEventData.putEventCode(testVehicle1.getVehicleID(), EventAttr.ATTR_BATTERY_VOLTAGE.getCode());
        vehicleEventData.putEventCode(testVehicle2.getVehicleID(), EventAttr.ATTR_BATTERY_VOLTAGE.getCode());

        vehicleEventData.putNoteCode(testVehicle1.getVehicleID(), 20);
        vehicleEventData.putNoteCode(testVehicle2.getVehicleID(), 20);

        vehicleEventData.putVehicle(testVehicle1.getVehicleID(), testVehicle1);
        vehicleEventData.putVehicle(testVehicle2.getVehicleID(), testVehicle2);

        Map<Integer, Date> data = driveTimeDAO.getPrevEventDates(vehicleEventData);
        assertNotNull(data);
    }

    @Test
    public void testGetPrevEventDates(){
        DriveTimeDAO driveTimeDAO = new DriveTimeJDBCDAO();
        ((DriveTimeJDBCDAO)driveTimeDAO).setDataSource(new ITDataSource().getRealDataSource());
        GroupData testGroupData1 = itData.teamGroupData.get(0);
        GroupData testGroupData2 = itData.teamGroupData.get(1);
        Vehicle testVehicle1 = testGroupData1.vehicle;
        Vehicle testVehicle2 = testGroupData2.vehicle;
        Device testDevice1 = testGroupData1.device;
        Device testDevice2 = testGroupData2.device;

        VehicleEventData vehicleEventData = new VehicleEventData();
        vehicleEventData.putDate(testVehicle1.getVehicleID(),new Date());
        vehicleEventData.putDate(testVehicle2.getVehicleID(),new Date());

        vehicleEventData.putDeviceID(testVehicle1.getVehicleID(),testDevice1.getDeviceID());
        vehicleEventData.putDeviceID(testVehicle2.getVehicleID(), testDevice2.getDeviceID());

        vehicleEventData.putEventCode(testVehicle1.getVehicleID(), EventAttr.ATTR_BATTERY_VOLTAGE.getCode());
        vehicleEventData.putEventCode(testVehicle2.getVehicleID(), EventAttr.ATTR_BATTERY_VOLTAGE.getCode());

        vehicleEventData.putNoteCode(testVehicle1.getVehicleID(), 20);
        vehicleEventData.putNoteCode(testVehicle2.getVehicleID(), 20);

        vehicleEventData.putVehicle(testVehicle1.getVehicleID(), testVehicle1);
        vehicleEventData.putVehicle(testVehicle2.getVehicleID(), testVehicle2);

        Map<Integer, Date> prevDates = new HashMap<Integer, Date>();
        prevDates.put(testVehicle1.getVehicleID(), new Date());
        prevDates.put(testVehicle2.getVehicleID(), new Date());
        vehicleEventData.setPrevEventDates(prevDates);

        Map<Integer, String> data = driveTimeDAO.getDriveOdometersAtDates(vehicleEventData);
        assertNotNull(data);
    }

    @Test
    public void testGetPrevEventLastDates(){
        DriveTimeDAO driveTimeDAO = new DriveTimeJDBCDAO();
        ((DriveTimeJDBCDAO)driveTimeDAO).setDataSource(new ITDataSource().getRealDataSource());
        GroupData testGroupData1 = itData.teamGroupData.get(0);
        GroupData testGroupData2 = itData.teamGroupData.get(1);
        Vehicle testVehicle1 = testGroupData1.vehicle;
        Vehicle testVehicle2 = testGroupData2.vehicle;
        Device testDevice1 = testGroupData1.device;
        Device testDevice2 = testGroupData2.device;

        VehicleEventData vehicleEventData = new VehicleEventData();
        vehicleEventData.putDate(testVehicle1.getVehicleID(),new Date());
        vehicleEventData.putDate(testVehicle2.getVehicleID(),new Date());

        vehicleEventData.putDeviceID(testVehicle1.getVehicleID(),testDevice1.getDeviceID());
        vehicleEventData.putDeviceID(testVehicle2.getVehicleID(), testDevice2.getDeviceID());

        vehicleEventData.putEventCode(testVehicle1.getVehicleID(), EventAttr.ATTR_BATTERY_VOLTAGE.getCode());
        vehicleEventData.putEventCode(testVehicle2.getVehicleID(), EventAttr.ATTR_BATTERY_VOLTAGE.getCode());

        vehicleEventData.putNoteCode(testVehicle1.getVehicleID(), 20);
        vehicleEventData.putNoteCode(testVehicle2.getVehicleID(), 20);

        vehicleEventData.putVehicle(testVehicle1.getVehicleID(), testVehicle1);
        vehicleEventData.putVehicle(testVehicle2.getVehicleID(), testVehicle2);

        Map<Integer, Date> prevDates = new HashMap<Integer, Date>();
        prevDates.put(testVehicle1.getVehicleID(), new Date());
        prevDates.put(testVehicle2.getVehicleID(), new Date());
        vehicleEventData.setPrevEventDates(prevDates);

        Map<Integer, String> data = driveTimeDAO.getDriveOdometersAtLastDates(vehicleEventData);
        assertNotNull(data);
    }

    @Test
    public void testGetEngineHoursAtDates(){
        DriveTimeDAO driveTimeDAO = new DriveTimeJDBCDAO();
        ((DriveTimeJDBCDAO)driveTimeDAO).setDataSource(new ITDataSource().getRealDataSource());
        GroupData testGroupData1 = itData.teamGroupData.get(0);
        GroupData testGroupData2 = itData.teamGroupData.get(1);
        Vehicle testVehicle1 = testGroupData1.vehicle;
        Vehicle testVehicle2 = testGroupData2.vehicle;
        Device testDevice1 = testGroupData1.device;
        Device testDevice2 = testGroupData2.device;

        VehicleEventData vehicleEventData = new VehicleEventData();
        vehicleEventData.putDate(testVehicle1.getVehicleID(),new Date());
        vehicleEventData.putDate(testVehicle2.getVehicleID(),new Date());

        vehicleEventData.putDeviceID(testVehicle1.getVehicleID(),testDevice1.getDeviceID());
        vehicleEventData.putDeviceID(testVehicle2.getVehicleID(), testDevice2.getDeviceID());

        vehicleEventData.putEventCode(testVehicle1.getVehicleID(), EventAttr.ATTR_BATTERY_VOLTAGE.getCode());
        vehicleEventData.putEventCode(testVehicle2.getVehicleID(), EventAttr.ATTR_BATTERY_VOLTAGE.getCode());

        vehicleEventData.putNoteCode(testVehicle1.getVehicleID(), 20);
        vehicleEventData.putNoteCode(testVehicle2.getVehicleID(), 20);

        vehicleEventData.putVehicle(testVehicle1.getVehicleID(), testVehicle1);
        vehicleEventData.putVehicle(testVehicle2.getVehicleID(), testVehicle2);

        Map<Integer, Date> prevDates = new HashMap<Integer, Date>();
        prevDates.put(testVehicle1.getVehicleID(), new Date());
        prevDates.put(testVehicle2.getVehicleID(), new Date());
        vehicleEventData.setPrevEventDates(prevDates);

        Map<Integer, String> data = driveTimeDAO.getEngineHoursAtDates(vehicleEventData);
        assertNotNull(data);
    }

    @Test
    public void testGetEngineHoursAtLastDates(){
        DriveTimeDAO driveTimeDAO = new DriveTimeJDBCDAO();
        ((DriveTimeJDBCDAO)driveTimeDAO).setDataSource(new ITDataSource().getRealDataSource());
        GroupData testGroupData1 = itData.teamGroupData.get(0);
        GroupData testGroupData2 = itData.teamGroupData.get(1);
        Vehicle testVehicle1 = testGroupData1.vehicle;
        Vehicle testVehicle2 = testGroupData2.vehicle;
        Device testDevice1 = testGroupData1.device;
        Device testDevice2 = testGroupData2.device;

        VehicleEventData vehicleEventData = new VehicleEventData();
        vehicleEventData.putDate(testVehicle1.getVehicleID(),new Date());
        vehicleEventData.putDate(testVehicle2.getVehicleID(),new Date());

        vehicleEventData.putDeviceID(testVehicle1.getVehicleID(),testDevice1.getDeviceID());
        vehicleEventData.putDeviceID(testVehicle2.getVehicleID(), testDevice2.getDeviceID());

        vehicleEventData.putEventCode(testVehicle1.getVehicleID(), EventAttr.ATTR_BATTERY_VOLTAGE.getCode());
        vehicleEventData.putEventCode(testVehicle2.getVehicleID(), EventAttr.ATTR_BATTERY_VOLTAGE.getCode());

        vehicleEventData.putNoteCode(testVehicle1.getVehicleID(), 20);
        vehicleEventData.putNoteCode(testVehicle2.getVehicleID(), 20);

        vehicleEventData.putVehicle(testVehicle1.getVehicleID(), testVehicle1);
        vehicleEventData.putVehicle(testVehicle2.getVehicleID(), testVehicle2);

        Map<Integer, Date> prevDates = new HashMap<Integer, Date>();
        prevDates.put(testVehicle1.getVehicleID(), new Date());
        prevDates.put(testVehicle2.getVehicleID(), new Date());
        vehicleEventData.setPrevEventDates(prevDates);

        Map<Integer, String> data = driveTimeDAO.getEngineHoursAtLastDates(vehicleEventData);
        assertNotNull(data);
    }
}
