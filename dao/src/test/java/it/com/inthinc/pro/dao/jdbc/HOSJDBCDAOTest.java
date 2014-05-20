package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.Util;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;
import it.util.DataGenForHOSTesting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.inthinc.hos.ddl.HOSOccupantLog;
import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.HOSJDBCDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.InspectionType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.hos.HOSDriverLogin;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantInfo;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleMileage;


public class HOSJDBCDAOTest extends BaseJDBCTest{
    private static ITData itData;
    private static SiloService siloService;

    
    private static String XML_DATA_FILE = "HOSTest.xml";
    private static String INITIAL_LOCATION = "Initial Location";
    private static double TEST_LAT = 38.5482;
    private static double TEST_LNG = -95.8008;
 
    private static HOSDAO hosDAO; 

    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();

        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestData(stream, siloService, false, false, true)) {
            throw new Exception("Error parsing Test data xml file");
        }
      
        
      hosDAO = new HOSJDBCDAO();
      ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
//      hosDAO = new HOSJDBCDAOsp();
//      ((HOSJDBCDAOsp)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
      
      

    }


    @Test
    public void crudCreateFindNoVehicleTest() {
        
        // simplest case -- no vehicle
        GroupData testGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testDriver, hosRecordDate, null, 34.0f,45.0f); 
        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        
        HOSRecord expectedHosRecord = constructExpectedHosRecord(hosRecord, testDriver, null);

        // TODO: fix these, they should not be ignored
        String ignoreFields[] = { "originalLogTime", "vehicleIsDOT" };
        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);
        
    }


    @Test
    public void crudCreateFromNoteFindWithVehicleTest() {
        
        // case with a vehicle
        GroupData testGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        Device testDevice = testGroupData.device;
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = new HOSRecord();
        hosRecord.setDeviceID(testDevice.getDeviceID());
        hosRecord.setDriverDotType(testDriver.getDot());
        hosRecord.setDriverID(testDriver.getDriverID());
        hosRecord.setLocation(INITIAL_LOCATION);
        hosRecord.setLat(0.0f);
        hosRecord.setLng(0.0f);
        hosRecord.setLogTime(hosRecordDate);
        hosRecord.setUserEnteredLocationFlag(true);
        hosRecord.setNoteFlags((byte)0);
        hosRecord.setStateID(47);
        hosRecord.setStatus(HOSStatus.HOS_ALTERNATE_SLEEPING);
        hosRecord.setTimeZone(testDriver.getPerson().getTimeZone());
        hosRecord.setEditUserID(itData.fleetUser.getUserID());
        hosRecord.setVehicleID(testVehicle.getVehicleID());
        hosRecord.setVehicleOdometer(10000l);
        hosRecord.setTripInspectionFlag(false);
        hosRecord.setTripReportFlag(false);
        hosRecord.setMobileUnitID("Mobile123");
        hosRecord.setEmployeeID(testDriver.getPerson().getEmpid());
        Long hosLogID = hosDAO.createFromNote(hosRecord);
        assertNotNull("createFromNote", hosLogID);
        System.out.println("hosLogID: " + hosLogID + " " + hosRecordDate);
        hosRecord.setHosLogID(hosLogID);

        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        assertEquals("Expected Origin", HOSOrigin.DEVICE, foundHosRecord.getOrigin());
        assertEquals("Expected Status", HOSStatus.HOS_ALTERNATE_SLEEPING, foundHosRecord.getStatus());
        assertEquals("Expected Mobile Unit ID", "Mobile123", foundHosRecord.getMobileUnitID());
        
    }

    @Test
    public void crudCreateFindWithVehicleTest() {
        
        // case with a vehicle
        GroupData testGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testDriver, hosRecordDate, testVehicle.getVehicleID(), 34.0f,45.0f);
        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());

        HOSRecord expectedHosRecord = constructExpectedHosRecord(hosRecord, testDriver, testVehicle);

        String ignoreFields[] = { "originalLogTime", "vehicleIsDOT"};
        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);
        
    }

    @Test
    public void crudDeleteTest() {
        
        // case with a vehicle
        GroupData testGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testDriver, hosRecordDate, testVehicle.getVehicleID(), 34.0f,45.0f);
        
        hosDAO.deleteByID(hosRecord.getHosLogID());
        
        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());

        HOSRecord expectedHosRecord = constructExpectedHosRecord(hosRecord, testDriver, testVehicle);
        expectedHosRecord.setDeleted(true);
        expectedHosRecord.setChangedCnt(expectedHosRecord.getChangedCnt()+1);

        String ignoreFields[] = { "originalLogTime", "vehicleIsDOT"};

        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);
    }

    @Test
    public void crudEditSimpleTest() {
        
        // case with a vehicle
        GroupData testGoodGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testGoodDriver = fetchDriver(testGoodGroupData.driver.getDriverID());
        Vehicle testGoodVehicle = testGoodGroupData.vehicle;
        GroupData testBadGroupData = itData.teamGroupData.get(ITData.BAD);
        Vehicle testBadVehicle = testBadGroupData.vehicle;
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testGoodDriver, hosRecordDate, testGoodVehicle.getVehicleID(), 34.0f,45.0f);
        HOSRecord editHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        
        // change some of the fields (just the ones that the UI can change, except driver)
        Date newHosRecordDate = new Date(hosRecordDate.getTime()-60000l);   // one minute earlier
        editHosRecord.setLocation("new location");
        editHosRecord.setStatus(HOSStatus.HOS_ALTERNATE_SLEEPING);
        editHosRecord.setDriverDotType(RuleSetType.CANADA_2007_OIL);
        editHosRecord.setLogTime(newHosRecordDate);
        editHosRecord.setServiceID("new service id");
        editHosRecord.setTrailerID("new trailer id");
        editHosRecord.setVehicleID(testBadVehicle.getVehicleID());
        editHosRecord.setEditUserID(itData.districtUser.getUserID());
        editHosRecord.setTruckGallons(34.0f);
        editHosRecord.setTrailerGallons(45.0f);
        editHosRecord.setMobileUnitID("new mobile unit id");
        hosDAO.update(editHosRecord);

        HOSRecord expectedHosRecord = constructExpectedHosRecord(editHosRecord, testGoodDriver, testBadVehicle);
        expectedHosRecord.setEditUserName(itData.districtUser.getUsername());
        expectedHosRecord.setEditUserID(itData.districtUser.getUserID());
        expectedHosRecord.setOriginalLogTime(hosRecordDate);
        expectedHosRecord.setOriginalLocation(INITIAL_LOCATION);

        String ignoreFields[] = {"vehicleIsDOT"};
        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);
    }

    @Test
    public void crudEditDriverChangeTest() {
        
        GroupData testGoodGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testGoodDriver = fetchDriver(testGoodGroupData.driver.getDriverID());
        Vehicle testGoodVehicle = testGoodGroupData.vehicle;
        GroupData testBadGroupData = itData.teamGroupData.get(ITData.BAD);
        Driver testBadDriver = fetchDriver(testBadGroupData.driver.getDriverID());
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testGoodDriver, hosRecordDate, testGoodVehicle.getVehicleID(), HOSStatus.ON_DUTY_OCCUPANT, 34.0f,45.0f);
        HOSRecord editHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        
        editHosRecord.setEditUserID(itData.fleetUser.getUserID());
        editHosRecord.setDriverID(testBadDriver.getDriverID());
        hosDAO.update(editHosRecord);

        HOSRecord expectedHosRecord = constructExpectedHosRecord(hosRecord, testGoodDriver, testGoodVehicle);
        expectedHosRecord.setDeleted(true);
        expectedHosRecord.setChangedCnt(2);

        String ignoreFields[] = { "originalLogTime", "vehicleIsDOT"};
        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);

        Interval queryInterval = new Interval(new DateTime(hosRecordDate.getTime(), DateTimeZone.UTC), new DateTime(hosRecordDate.getTime(),DateTimeZone.UTC));
        List<HOSRecord> badDriverRecords = hosDAO.getHOSRecords(testBadDriver.getDriverID(), queryInterval, true);
        
        assertTrue("record should have been created for other driver", badDriverRecords.size() > 0);
        
        HOSRecord transferedHosRecord = badDriverRecords.get(0);
        expectedHosRecord = constructExpectedHosRecord(hosRecord, testBadDriver, testGoodVehicle);
        expectedHosRecord.setHosLogID(transferedHosRecord.getHosLogID());
        foundHosRecord = hosDAO.findByID(badDriverRecords.get(0).getHosLogID());
        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);
    }

    private static long msDelta = 2000l;

    @Test
    public void hosRecordListTest() {
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        
        long numHosRecords = Util.randomInt(1, 5);
        
        
        Date currentDate = new Date();  
        Date startDate = new Date(currentDate.getTime()/1000l * 1000l);
        
        doSleep(msDelta*(numHosRecords+1));
        
        int status = 0;
        for (long i = 0; i < numHosRecords; i++) {
            createMinimalHosRecord(hosDAO, testDriver, new Date(startDate.getTime() + i*msDelta), 
                    testVehicle.getVehicleID(), HOSStatus.values()[status++], 34.0f,45.0f);
        }
        
        Interval queryInterval = new Interval(new DateTime(startDate.getTime(), DateTimeZone.UTC), new DateTime(new Date(),DateTimeZone.UTC));
        System.out.println(" " + queryInterval);
        List<HOSRecord> driverRecords = hosDAO.getHOSRecordsFilteredByInterval(testDriver.getDriverID(), queryInterval, false);
        
        assertEquals("unexpected record count returned", numHosRecords, driverRecords.size());
    }

    @Test
    public void hosRecordsByVehicleListTest() {
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        
        long numHosRecords = Util.randomInt(1, 5);
        
        Date currentDate = new Date();  
        Date startDate = new Date(currentDate.getTime()/1000l * 1000l);
        
        try {
            // sleep so that no other records are within the time interval
//            System.out.println("sleeping for " + msDelta*(numHosRecords+1) + " ms");
            Thread.sleep(msDelta*(numHosRecords+1));
//            System.out.println("sleeping done");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        int status = 0;
        for (long i = 0; i < numHosRecords; i++) {
            createMinimalHosRecord(hosDAO, testDriver, new Date(startDate.getTime() + i*msDelta), 
                    testVehicle.getVehicleID(), HOSStatus.values()[status++], 34.0f,45.0f);
        }
        
        Interval queryInterval = new Interval(new DateTime(startDate.getTime(), DateTimeZone.UTC), new DateTime(new Date(),DateTimeZone.UTC));
        List<HOSRecord> vehicleRecords = hosDAO.getRecordsForVehicle(testVehicle.getVehicleID(), queryInterval, false);
        
       assertEquals("unexpected record count returned", numHosRecords, vehicleRecords.size()); 
    }

    @Test
    public void fuelStopByVehicleTest() {
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        
        long numHosRecords = Util.randomInt(1, 5);
        
        Date currentDate = new Date();  
        Date startDate = new Date(currentDate.getTime()/1000l * 1000l);
        
        try {
            // sleep so that no other records are within the time interval
//            System.out.println("sleeping for " + msDelta*(numHosRecords+1) + " ms");
            Thread.sleep(msDelta*(numHosRecords+1));
//            System.out.println("sleeping done");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        for (long i = 0; i < numHosRecords; i++) {
            createMinimalHosRecord(hosDAO, testDriver, new Date(startDate.getTime() + i*msDelta), 
                    testVehicle.getVehicleID(), HOSStatus.FUEL_STOP, 34.0f,45.0f);
        }
        
        Interval queryInterval = new Interval(new DateTime(startDate.getTime()), new DateTime(new Date()));
        List<HOSRecord> driverRecords = hosDAO.getFuelStopRecordsForVehicle(testVehicle.getVehicleID(), queryInterval);
        
        assertEquals("unexpected record count returned", numHosRecords, driverRecords.size());
        
        
        
    }
    
    @Test
    public void hosMileageTest() {
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Date genDataStartDate = new Date(itData.startDateInSec*1000l);
        DateTime dayStartUTC = new DateMidnight(genDataStartDate, DateTimeZone.UTC).toDateTime();

        Interval queryInterval = new Interval(new DateMidnight(dayStartUTC), new DateMidnight(dayStartUTC).toDateTime().plusDays(1).minusSeconds(1));
        boolean noDriver = false;
        List<HOSGroupMileage> groupMileageRecords = hosDAO.getHOSMileage(testGroupData.group.getGroupID(), queryInterval, noDriver);
        assertTrue("expected 1 record for 1 day/1 group", groupMileageRecords.size() == 1);
        HOSGroupMileage rec = groupMileageRecords.get(0);
        Long expectedMiles = Long.valueOf(DataGenForHOSTesting.LOGOUT_ODOMETER-DataGenForHOSTesting.LOGIN_ODOMETER);
        assertEquals("miles ", expectedMiles, rec.getDistance());
        
        // 'NO DRIVER' MILES 
        noDriver = true;
        groupMileageRecords = hosDAO.getHOSMileage(testGroupData.group.getGroupID(), queryInterval, noDriver);
        assertTrue("expected 0 no driver records for 1 day/1 group", groupMileageRecords.size() == 0);
    }
    
    @Test
    public void hosVehicleMileageTest() {
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Date genDataStartDate = new Date(itData.startDateInSec*1000l);
        DateTime dayStartUTC = new DateMidnight(genDataStartDate, DateTimeZone.UTC).toDateTime();

        Interval queryInterval = new Interval(new DateMidnight(dayStartUTC), new DateMidnight(dayStartUTC).toDateTime().plusDays(1).minusSeconds(1));
        boolean noDriver = false;
        List<HOSVehicleMileage> vehicleMileageRecords = hosDAO.getHOSVehicleMileage(testGroupData.group.getGroupID(), queryInterval, noDriver);
        assertTrue("expected 1 record for 1 day/1 group", vehicleMileageRecords.size() == 1);
        HOSVehicleMileage rec = vehicleMileageRecords.get(0);
        Long expectedMiles = Long.valueOf(DataGenForHOSTesting.LOGOUT_ODOMETER-DataGenForHOSTesting.LOGIN_ODOMETER);
        assertEquals("miles ", expectedMiles, rec.getDistance());
        
        // 'NO DRIVER' MILES 
        noDriver = true;
        vehicleMileageRecords = hosDAO.getHOSVehicleMileage(testGroupData.group.getGroupID(), queryInterval, noDriver);
        assertTrue("expected 0 no driver records for 1 day/1 group", vehicleMileageRecords.size() == 0);

    }
    
    @Test
    public void getDriverForEmpIdLastName() {
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        
        HOSDriverLogin driverLogin = hosDAO.getDriverForEmpidLastName(testDriver.getPerson().getEmpid(), testDriver.getPerson().getLast());
        
        assertEquals("driverID", testDriver.getDriverID(), driverLogin.getDriverID());
        
    }
    
    @Test
    public void hosOccupantLogsTest() {
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        GroupData testOccupantGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        Driver testOccupant = fetchDriver(testOccupantGroupData.driver.getDriverID());
        
        DateTimeZone driverTZ = DateTimeZone.forTimeZone(ReportTestConst.timeZone);
        Date genDataStartDate = new Date(itData.startDateInSec*1000l);
        DateTime dayStartDriverTZ= new DateMidnight(genDataStartDate, driverTZ).toDateTime();
        
        Interval queryInterval = new Interval(new DateMidnight(genDataStartDate, driverTZ), new DateMidnight(dayStartDriverTZ.plusDays(1),  driverTZ));

        List<HOSOccupantLog> occupantLogRecords = hosDAO.getHOSOccupantLogs(testDriver.getDriverID(), queryInterval);
        assertTrue("expected 1 occupant record for 1 day/1 driver", occupantLogRecords.size() == 1);
        
        HOSOccupantLog rec = occupantLogRecords.get(0);
        assertEquals("driverID", testOccupant.getDriverID(), rec.getDriverID());
        assertEquals("driverName", testOccupant.getPerson().getFirst() + " " + testOccupant.getPerson().getLast(), rec.getDriverName());
        assertEquals("vehicleID", testVehicle.getVehicleID(), rec.getVehicleID());
        assertTrue("returned day day is within interval", queryInterval.contains(rec.getLogTime().getTime()) || queryInterval.contains(rec.getEndTime().getTime()));
        
    }
    
    private HOSRecord createMinimalHosRecord(HOSDAO hosDAO, Driver driver, Date hosRecordDate, Integer vehicleID, Float truckGallons, Float trailerGallons) {
        return createMinimalHosRecord(hosDAO, driver, hosRecordDate, vehicleID, HOSStatus.OFF_DUTY, truckGallons,trailerGallons);
    }
    private HOSRecord createMinimalHosRecord(HOSDAO hosDAO, Driver driver, Date hosRecordDate, Integer vehicleID, HOSStatus status,Float truckGallons, Float trailerGallons) {
        
        
        HOSRecord hosRecord = new HOSRecord();
        hosRecord.setDriverDotType(driver.getDot());
        hosRecord.setDriverID(driver.getDriverID());
        hosRecord.setLocation(INITIAL_LOCATION);
        hosRecord.setLogTime(hosRecordDate);
        hosRecord.setStatus(status);
        hosRecord.setTimeZone(driver.getPerson().getTimeZone());
        hosRecord.setEditUserID(itData.fleetUser.getUserID());
        hosRecord.setVehicleID(vehicleID);
        hosRecord.setTruckGallons(truckGallons);
        hosRecord.setTrailerGallons(trailerGallons);
        hosRecord.setInspectionType(InspectionType.NONE);
        Long hosLogID = hosDAO.create(0l, hosRecord);
        System.out.println("hosLogID: " + hosLogID + " " + hosRecordDate);
        hosRecord.setHosLogID(hosLogID);
        return hosRecord;
    }

    private HOSRecord constructExpectedHosRecord(HOSRecord hosRecord, Driver driver, Vehicle vehicle) {
        HOSRecord expectedHosRecord = new HOSRecord();
        expectedHosRecord.setAddedTime(hosRecord.getAddedTime());
        expectedHosRecord.setChangedCnt(hosRecord.getChangedCnt() == null ? 1 : hosRecord.getChangedCnt());
        expectedHosRecord.setDeleted(false);
        expectedHosRecord.setDistance(null);
        expectedHosRecord.setDriverDotType(hosRecord.getDriverDotType());
        expectedHosRecord.setDriverID(driver.getDriverID());
        expectedHosRecord.setEdited(true);
        expectedHosRecord.setEditUserID(itData.fleetUser.getUserID());
        expectedHosRecord.setEditUserName(itData.fleetUser.getUsername());
        expectedHosRecord.setEmployeeID(driver.getPerson().getEmpid());
        expectedHosRecord.setHosLogID(hosRecord.getHosLogID());
        expectedHosRecord.setLat(0f);
        expectedHosRecord.setLng(0f);
        expectedHosRecord.setLocation(hosRecord.getLocation());
        expectedHosRecord.setLogTime(hosRecord.getLogTime());
        expectedHosRecord.setOrigin(HOSOrigin.PORTAL);
        expectedHosRecord.setOriginalLogTime(hosRecord.getLogTime());
        expectedHosRecord.setOriginalLocation("");
        expectedHosRecord.setServiceID(hosRecord.getServiceID() == null ? "" : hosRecord.getServiceID());
        expectedHosRecord.setSingleDriver(true);
        expectedHosRecord.setStatus(hosRecord.getStatus());
        expectedHosRecord.setTimeZone(hosRecord.getTimeZone());
        expectedHosRecord.setTrailerGallons(45.0f);
        expectedHosRecord.setTrailerID(hosRecord.getTrailerID() == null ? "" : hosRecord.getTrailerID());
        expectedHosRecord.setTripInspectionFlag(false);
        expectedHosRecord.setTripReportFlag(false);
        expectedHosRecord.setTruckGallons(34.0f);
        expectedHosRecord.setVehicleID((vehicle == null) ? 0 : vehicle.getVehicleID());
//        expectedHosRecord.setVehicleIsDOT((vehicle == null || vehicle.getDot() == null) ? false : vehicle.getDot().equals(VehicleDOTType.DOT));
        expectedHosRecord.setVehicleLicense((vehicle == null) ? "" : vehicle.getLicense());
        expectedHosRecord.setVehicleName((vehicle == null) ? "" : vehicle.getName());
        expectedHosRecord.setVehicleOdometer(0l);
        expectedHosRecord.setMobileUnitID(hosRecord.getMobileUnitID());
        expectedHosRecord.setInspectionType(InspectionType.NONE);
        return expectedHosRecord;
    }

    private Driver fetchDriver(Integer driverID) {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        
        return driverDAO.findByID(driverID);
    }



    @Test
    public void hosLogShipPackageTest() throws IOException {
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        
        RuleSetType dotType = testDriver.getDot();
        int daysBack = dotType.getLogShipDaysBack();
        Date genDataStartDate = new Date(itData.startDateInSec*1000l);
        DateTime currentDate  = new DateMidnight(genDataStartDate, DateTimeZone.UTC).toDateTime().plusDays(1);

        Interval interval = new Interval(currentDate.minusDays(daysBack), currentDate);
        List<HOSRecord> driverRecords = hosDAO.getHOSRecords(testDriver.getDriverID(), interval, true);
        assertTrue("test data record count", driverRecords.size() > 0);
        
        
        List<ByteArrayOutputStream> list = HOSUtil.packageLogsToShip(driverRecords, testDriver);
        assertTrue("list size", list.size() > 0);
        
    }
    
    
    @Test
    public void fetchMileageForDayVehicleTest() {
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Vehicle testVehicle = testGroupData.vehicle;
        Driver testDriver = testGroupData.driver;
        Date genDataStartDate = new Date(itData.startDateInSec*1000l);
        DateTime dayStartUTC = new DateMidnight(genDataStartDate, DateTimeZone.forTimeZone(ReportTestConst.timeZone)).toDateTime();
        DateTime day = dayStartUTC;

        Map<Integer, Long> mileageMap = hosDAO.fetchMileageForDayVehicle(day, testVehicle.getVehicleID());
        assertTrue("driver Milage exists", mileageMap.containsKey(testDriver.getDriverID()));
        Long driverMiles = mileageMap.get(testDriver.getDriverID());
        Long expectedMiles = Long.valueOf((ReportTestConst.MILES_PER_EVENT * (ReportTestConst.EVENTS_PER_DAY+2)));
        assertEquals("miles ", expectedMiles, driverMiles);
            
    }

    @Test
    public void originalTimeTest() {
        
        // case with a vehicle
        GroupData testGoodGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testGoodDriver = fetchDriver(testGoodGroupData.driver.getDriverID());
        Vehicle testGoodVehicle = testGoodGroupData.vehicle;
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testGoodDriver, hosRecordDate, testGoodVehicle.getVehicleID(), 34.0f,45.0f);
        HOSRecord editHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        
        // change the date
        Date newHosRecordDate = new Date(hosRecordDate.getTime()-60000l);   // one minute earlier
        editHosRecord.setLogTime(newHosRecordDate);
        editHosRecord.setEditUserID(itData.districtUser.getUserID());
        hosDAO.update(editHosRecord);

        HOSRecord expectedHosRecord = constructExpectedHosRecord(editHosRecord, testGoodDriver, testGoodVehicle);
        expectedHosRecord.setEditUserName(itData.districtUser.getUsername());
        expectedHosRecord.setEditUserID(itData.districtUser.getUserID());
        expectedHosRecord.setOriginalLogTime(hosRecordDate);

        String ignoreFields[] = { "originalLocation", "serviceID", "trailerID","vehicleIsDOT"};
        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);
    }
  
    @Test
    public void existsTestLogDateSecondsGranularity() {
        GroupData testGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testDriver, hosRecordDate, testVehicle.getVehicleID(), 34.0f,45.0f);
        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        DateTime dateTime = new DateTime(foundHosRecord.getLogTime());
        Long hosLogID = foundHosRecord.getHosLogID();
        
        boolean exists = hosDAO.otherHosRecordExistsForDriverTimestamp(testDriver.getDriverID(), dateTime.toDate(), -1L);
        assertTrue("Expected a record to exist for driver/timestamp", exists);

        exists = hosDAO.otherHosRecordExistsForDriverTimestamp(testDriver.getDriverID(), dateTime.toDate(), hosLogID);
        assertFalse("Expected no other record to exist for driver/timestamp and logID", exists);

        exists = hosDAO.otherHosRecordExistsForDriverTimestamp(testDriver.getDriverID(), dateTime.plusMillis(999).toDate(), -1L);
        assertTrue("Expected a record to exist for driver/timestamp within same second", exists);
        
        exists = hosDAO.otherHosRecordExistsForDriverTimestamp(testDriver.getDriverID(), dateTime.minusMillis(1).toDate(), -1L);
        assertFalse("Expected a record not to exist for driver/timestamp", exists);
        
        exists = hosDAO.otherHosRecordExistsForDriverTimestamp(testDriver.getDriverID(), dateTime.plusSeconds(1).toDate(), -1L);
        assertFalse("Expected a record not to exist for driver/timestamp", exists);
        
    }
    
    @Test
    public void getDriverForEmpId() {
        
        GroupData testGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Device testDevice = testGroupData.device;
        
        HOSDriverLogin driverLogin = hosDAO.getDriverForEmpid(testDevice.getImei(), testDriver.getPerson().getEmpid());
        assertNotNull("Driver Login should exist", driverLogin);
        
        assertEquals("acct ID", itData.account.getAccountID(), driverLogin.getAcctID());
        assertEquals("driver ID", testDriver.getDriverID(), driverLogin.getDriverID());
        assertEquals("driver DOT type", testDriver.getDot(), driverLogin.getDriverDotType());
        assertEquals("deviceID", testDevice.getDeviceID(), driverLogin.getDeviceID());
        assertEquals("fob id", testDriver.getFobID() == null ? "" : testDriver.getFobID(), driverLogin.getFobId());
        assertEquals("FuelEfficiencyType", testDriver.getPerson().getFuelEfficiencyType(), driverLogin.getFuelEfficiencyType());
        assertEquals("MeasurementType", testDriver.getPerson().getMeasurementType(), driverLogin.getMeasurementType());
        
        // invalid device
        driverLogin = hosDAO.getDriverForEmpid("Invalid IMEI", testDriver.getPerson().getEmpid());
        assertNotNull("Driver Login should exist", driverLogin);
        assertEquals("driver ID should be 0", 0, driverLogin.getDriverID().intValue());
        
        // invalid driver emp id
        driverLogin = hosDAO.getDriverForEmpid(testDevice.getImei(), "invalid");
        assertNotNull("Driver Login should exist", driverLogin);
        assertEquals("driver ID should be 0", 0, driverLogin.getDriverID().intValue());
    }

    @Test
    public void getOccupantInfo() {
        
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());

        HOSOccupantInfo occupantInfo = hosDAO.getOccupantInfo(testDriver.getDriverID());
        assertNotNull("occupantInfo should exist", occupantInfo);
        assertEquals("employee ID", testDriver.getPerson().getEmpid(), occupantInfo.getEmpId());
        assertEquals("full name", testDriver.getPerson().getFirst() + " " + testDriver.getPerson().getLast(), occupantInfo.getFullName());
        assertEquals("fob id", testDriver.getFobID() == null ? "" : testDriver.getFobID(), occupantInfo.getFobId());
        
        // invalid driver id
        occupantInfo = hosDAO.getOccupantInfo(0);
        assertNull("occupantInfo should not exist", occupantInfo);
    }
    @Test
    public void getHOSRecordsForCommAddress() {
        // create some data to test with
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        Device testDevice = testGroupData.device;
        
        long numHosRecords = 5;
        Date currentDate = new Date();  
        Date startDate = new Date(currentDate.getTime()/1000l * 1000l);
        doSleep(msDelta*(numHosRecords+1));
        int status = 0;
        for (long i = 0; i < numHosRecords; i++) {
            createMinimalHosRecord(hosDAO, testDriver, new Date(startDate.getTime() + i*msDelta), 
                    testVehicle.getVehicleID(), HOSStatus.values()[status++], 34.0f,45.0f);
        }
        
        Interval queryInterval = new Interval(new DateTime(startDate.getTime(), DateTimeZone.UTC), new DateTime(new Date(),DateTimeZone.UTC));
        List<HOSRecord> driverRecords = hosDAO.getHOSRecordsFilteredByInterval(testDriver.getDriverID(), queryInterval, false);
        
        List<HOSRecord> paramList = new ArrayList<HOSRecord>();
        HOSRecord param = new HOSRecord();
        param.setLogTime(driverRecords.get(0).getLogTime());    // matching record
        param.setStatus(driverRecords.get(0).getStatus());
        paramList.add(param);
        param = new HOSRecord();
        param.setLogTime(driverRecords.get(1).getLogTime());    // non-matching record (status)
        param.setStatus(driverRecords.get(2).getStatus());
        paramList.add(param);
        param = new HOSRecord();
        param.setLogTime(new Date());    // non-matching record (time)
        param.setStatus(driverRecords.get(2).getStatus());
        paramList.add(param);
        
        
        List<HOSRecord> returnList = hosDAO.getHOSRecordsForCommAddress(testDevice.getImei(), paramList);
        assertEquals("Record Count", 1, returnList.size());
        assertEquals("Record Time", driverRecords.get(0).getLogTime(), returnList.get(0).getLogTime());
        assertEquals("Record Status", driverRecords.get(0).getStatus(), returnList.get(0).getStatus());

        // no matches for different imei
        returnList = hosDAO.getHOSRecordsForCommAddress(itData.teamGroupData.get(ITData.GOOD).device.getImei(), paramList);
        assertEquals("Record Count", 0, returnList.size());
        
        // no matches
        paramList.remove(0);
        returnList = hosDAO.getHOSRecordsForCommAddress(testDevice.getImei(), paramList);
        assertEquals("Record Count", 0, returnList.size());
    }
    @Test
    public void logoutLogin() {
        GroupData testGroupData = itData.teamGroupData.get(ITData.WS_GROUP);
        Device testDevice = testGroupData.device;
        Driver testDriver = testGroupData.driver;
        
        hosDAO.logoutDriverFromDevice(testDevice.getImei(), testGroupData.driver.getPerson().getEmpid(), new Date().getTime(), 555555);
        
        doSleep(5000l);
        
        HOSDriverLogin driverLogin = hosDAO.isValidLogin(testDevice.getImei(), testDriver.getPerson().getEmpid(), new Date().getTime(), false, 555560);
        assertNotNull("Driver Login should exist", driverLogin);
        
        assertEquals("acct ID", itData.account.getAccountID(), driverLogin.getAcctID());
        assertEquals("driver ID", testDriver.getDriverID(), driverLogin.getDriverID());
        assertEquals("driver DOT type", testDriver.getDot(), driverLogin.getDriverDotType());
        assertEquals("deviceID", testDevice.getDeviceID(), driverLogin.getDeviceID());
        assertEquals("fob id", testDriver.getFobID() == null ? "" : testDriver.getFobID(), driverLogin.getFobId());
        assertEquals("FuelEfficiencyType", testDriver.getPerson().getFuelEfficiencyType(), driverLogin.getFuelEfficiencyType());
        assertEquals("MeasurementType", testDriver.getPerson().getMeasurementType(), driverLogin.getMeasurementType());
        
    }
 
    @Test
    public void getVehicleHomeOfficeLocation() {
        // create some data to test with
        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        initVehicleConfiguratorSettings(testGroupData);

        Vehicle testVehicle = testGroupData.vehicle;
        LatLng homeOfficeLocation = hosDAO.getVehicleHomeOfficeLocation(testVehicle.getVehicleID());
        assertNotNull("home office location should be set", homeOfficeLocation);
        assertEquals("home office location lat", TEST_LAT, homeOfficeLocation.getLat(), .0001);
        assertEquals("home office location lng", TEST_LNG, homeOfficeLocation.getLng(), .0001);

        clearVehicleConfiguratorSettings(testGroupData);
        homeOfficeLocation = hosDAO.getVehicleHomeOfficeLocation(testVehicle.getVehicleID());
        assertNull("home office location should not be set when no actualVSet values", homeOfficeLocation);
    }
    
    private static void initVehicleConfiguratorSettings(GroupData groupData) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new ITDataSource().getRealDataSource());
        jdbcTemplate.update("delete from actualVSet where vehicleID = " + groupData.vehicle.getVehicleID() );
        jdbcTemplate.update("insert into actualVSet (vehicleID, deviceID, modified, settingID, value, vsetHistoryID) values (" + groupData.vehicle.getVehicleID() + "," + groupData.device.getDeviceID() + ",now(),1048," + TEST_LAT + ",0)");
        jdbcTemplate.update("insert into actualVSet (vehicleID, deviceID, modified, settingID, value, vsetHistoryID) values (" + groupData.vehicle.getVehicleID() + "," + groupData.device.getDeviceID() + ",now(),1049," + TEST_LNG + ",0)");
       
    }
    
    private static void clearVehicleConfiguratorSettings(GroupData groupData) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new ITDataSource().getRealDataSource());
        jdbcTemplate.update("delete from actualVSet where vehicleID = " + groupData.vehicle.getVehicleID() );
       
    }

   private void doSleep(long ms) {
        try {
            System.out.println("sleeping for " + ms + " ms");
            Thread.sleep(ms);
            System.out.println("sleeping done");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
}