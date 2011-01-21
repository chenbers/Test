package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.Util;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.util.DataGenForHOSTesting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.HOSJDBCDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.hos.HOSDriverLogin;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.model.hos.HOSVehicleMileage;

//@Ignore
public class HOSJDBCDAOTest extends BaseJDBCTest{
    private static ITData itData;
    private static SiloService siloService;

    
    private static String XML_DATA_FILE = "HOSTest.xml";
    private static String INITIAL_LOCATION = "Initial Location";
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();

        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }
    }

    @Test
    public void crudCreateFindNoVehicleTest() {
        
        // simplest case -- no vehicle
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testDriver, hosRecordDate, null); 
        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        
        HOSRecord expectedHosRecord = constructExpectedHosRecord(hosRecord, testDriver, null);

        // TODO: fix these, they should not be ignored
        // vehicleIsDOT is hardcoded in stored proc to 1
        String ignoreFields[] = { "originalLogTime", "vehicleIsDOT" };

        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);
        
    }


    @Test
    public void crudCreateFindWithVehicleTest() {
        
        // case with a vehicle
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testDriver, hosRecordDate, testVehicle.getVehicleID());
        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());

        HOSRecord expectedHosRecord = constructExpectedHosRecord(hosRecord, testDriver, testVehicle);

        // TODO: fix these, they should not be ignored
        // vehicleIsDOT is hardcoded in stored proc to 1
        String ignoreFields[] = { "originalLogTime", "vehicleIsDOT" };

        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);
        
    }

    @Test
    public void crudDeleteTest() {
        
        // case with a vehicle
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testDriver, hosRecordDate, testVehicle.getVehicleID());
        
        hosDAO.deleteByID(hosRecord.getHosLogID());
        
        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());

        HOSRecord expectedHosRecord = constructExpectedHosRecord(hosRecord, testDriver, testVehicle);
        expectedHosRecord.setDeleted(true);

        // TODO: fix these, they should not be ignored
        // vehicleIsDOT is hardcoded in stored proc to 1
        String ignoreFields[] = { "originalLogTime", "vehicleIsDOT" };

        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);
        
    }

    @Test
    public void crudEditSimpleTest() {
        
        // case with a vehicle
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGoodGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testGoodDriver = fetchDriver(testGoodGroupData.driver.getDriverID());
        Vehicle testGoodVehicle = testGoodGroupData.vehicle;
        GroupData testBadGroupData = itData.teamGroupData.get(ITData.BAD);
        Driver testBadDriver = fetchDriver(testBadGroupData.driver.getDriverID());
        Vehicle testBadVehicle = testBadGroupData.vehicle;
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testGoodDriver, hosRecordDate, testGoodVehicle.getVehicleID());
        HOSRecord editHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        
System.out.println("fleet user " + itData.fleetUser.getUserID());
System.out.println("district user " + itData.districtUser.getUserID());
        // change some of the fields (just the ones that the UI can change, except driver)
        Date newHosRecordDate = new Date(hosRecordDate.getTime()-60000l);   // one minute earlier
        editHosRecord.setLocation("new location");
        editHosRecord.setStatus(HOSStatus.ON_DUTY);
        editHosRecord.setDriverDotType(RuleSetType.CANADA_2007_OIL);
        editHosRecord.setLogTime(newHosRecordDate);
        editHosRecord.setServiceID("new service id");
        editHosRecord.setTrailerID("new trailer id");
        editHosRecord.setVehicleID(testBadVehicle.getVehicleID());
        editHosRecord.setEditUserID(itData.districtUser.getUserID());
        hosDAO.update(editHosRecord);

        HOSRecord expectedHosRecord = constructExpectedHosRecord(editHosRecord, testGoodDriver, testBadVehicle);
        expectedHosRecord.setEditUserName(itData.districtUser.getUsername());
        expectedHosRecord.setEditUserID(itData.districtUser.getUserID());
        expectedHosRecord.setOriginalLogTime(hosRecordDate);
        expectedHosRecord.setOriginalLocation(INITIAL_LOCATION);

        // TODO: fix these, they should not be ignored
        // vehicleIsDOT is hardcoded in stored proc to 1
        String ignoreFields[] = { "originalLogTime", "vehicleIsDOT" };

        HOSRecord foundHosRecord = hosDAO.findByID(hosRecord.getHosLogID());

        Util.compareObjects(expectedHosRecord, foundHosRecord, ignoreFields);
    }

    @Test
    public void crudEditDriverChangeTest() {
        
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGoodGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testGoodDriver = fetchDriver(testGoodGroupData.driver.getDriverID());
        Vehicle testGoodVehicle = testGoodGroupData.vehicle;
        GroupData testBadGroupData = itData.teamGroupData.get(ITData.BAD);
        Driver testBadDriver = fetchDriver(testBadGroupData.driver.getDriverID());
        Vehicle testBadVehicle = testBadGroupData.vehicle;
        
        Date hosRecordDate = new Date();
        HOSRecord hosRecord = createMinimalHosRecord(hosDAO, testGoodDriver, hosRecordDate, testGoodVehicle.getVehicleID(), HOSStatus.ON_DUTY_OCCUPANT);
        HOSRecord editHosRecord = hosDAO.findByID(hosRecord.getHosLogID());
        
        editHosRecord.setEditUserID(itData.fleetUser.getUserID());
        editHosRecord.setDriverID(testBadDriver.getDriverID());
        hosDAO.update(editHosRecord);

        HOSRecord expectedHosRecord = constructExpectedHosRecord(hosRecord, testGoodDriver, testGoodVehicle);
        expectedHosRecord.setDeleted(true);
        expectedHosRecord.setChangedCnt(2);

        // TODO: fix these, they should not be ignored
        // vehicleIsDOT is hardcoded in stored proc to 1
        String ignoreFields[] = { "originalLogTime", "vehicleIsDOT" };
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
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        
        long numHosRecords = Util.randomInt(1, 5);
        
System.out.println("numHosRecords " + numHosRecords);        
        
        
        Date currentDate = new Date();  
        Date startDate = new Date(currentDate.getTime()/1000l * 1000l);
        
        try {
            // sleep so that no other records are within the time interval
            System.out.println("sleeping for " + msDelta*(numHosRecords+1) + " ms");
            Thread.sleep(msDelta*(numHosRecords+1));
            System.out.println("sleeping done");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        int status = 0;
        for (long i = 0; i < numHosRecords; i++) {
            createMinimalHosRecord(hosDAO, testDriver, new Date(startDate.getTime() + i*msDelta), 
                    testVehicle.getVehicleID(), HOSStatus.values()[status++]);
        }
        
        Interval queryInterval = new Interval(new DateTime(startDate.getTime(), DateTimeZone.UTC), new DateTime(new Date(),DateTimeZone.UTC));
        System.out.println(" " + queryInterval);
        List<HOSRecord> driverRecords = hosDAO.getHOSRecordsFilteredByInterval(testDriver.getDriverID(), queryInterval, false);
        
        assertEquals("unexpected record count returned", numHosRecords, driverRecords.size());
        
        
        
    }

    @Test
    public void hosVehicleDataByDayTest() {
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        Date genDataStartDate = new Date(itData.startDateInSec*1000l);
        DateTime dayStartUTC = new DateMidnight(genDataStartDate, DateTimeZone.UTC).toDateTime();
        
        Interval queryInterval = new Interval(new DateMidnight(dayStartUTC).minusDays(1), new DateMidnight(dayStartUTC).toDateTime().minusSeconds(1));
//System.out.println("queryInterval: " + queryInterval);
        List<HOSVehicleDayData> vehicleDayDataRecords = hosDAO.getHOSVehicleDataByDay(testDriver.getDriverID(), queryInterval);
        assertTrue("expected 1 record for 1 day/1 driver", vehicleDayDataRecords.size() == 1);
        HOSVehicleDayData rec = vehicleDayDataRecords.get(0);
        assertEquals("vehicleName", testVehicle.getName(), rec.getVehicleName());
        assertEquals("vehicleID", testVehicle.getVehicleID(), rec.getVehicleID());
//System.out.println("DAY: " + new DateTime(rec.getDay(), DateTimeZone.UTC) + " ms: " + rec.getDay().getTime());
        assertTrue("returned day day is within interval", queryInterval.contains(rec.getDay().getTime()));
        assertEquals("odometer ", Long.valueOf(DataGenForHOSTesting.LOGIN_ODOMETER), rec.getStartOdometer());
        Long expectedMiles = Long.valueOf(DataGenForHOSTesting.LOGOUT_ODOMETER-DataGenForHOSTesting.LOGIN_ODOMETER);
//TODO: THIS IS NO LONGER WORKING -- NEED TO TALK TO BILL        
//        assertEquals("miles ", expectedMiles, rec.getMilesDriven());
        
    }

    @Test
    @Ignore
    public void hosMileageTest() {
        // don't think this is ready yet
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Date genDataStartDate = new Date(itData.startDateInSec*1000l);
        DateTime dayStartUTC = new DateMidnight(genDataStartDate, DateTimeZone.UTC).toDateTime();

        Interval queryInterval = new Interval(new DateMidnight(dayStartUTC).minusDays(1), new DateMidnight(dayStartUTC).toDateTime().minusSeconds(1));
        System.out.println("queryInterval: " + queryInterval);
        List<HOSGroupMileage> groupMileageRecords = hosDAO.getHOSMileage(testGroupData.group.getGroupID(), queryInterval, true);
        assertTrue("expected 1 record for 1 day/1 group", groupMileageRecords.size() == 1);
        HOSGroupMileage rec = groupMileageRecords.get(0);
        Long expectedMiles = Long.valueOf(DataGenForHOSTesting.LOGOUT_ODOMETER-DataGenForHOSTesting.LOGIN_ODOMETER);
        assertEquals("miles ", expectedMiles, rec.getDistance());
        

    }
    
    @Test
    @Ignore
    public void hosVehicleMileageTest() {
        // don't think this is ready yet
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Date genDataStartDate = new Date(itData.startDateInSec*1000l);
        DateTime dayStartUTC = new DateMidnight(genDataStartDate, DateTimeZone.UTC).toDateTime();

        Interval queryInterval = new Interval(new DateMidnight(dayStartUTC).minusDays(1), new DateMidnight(dayStartUTC).toDateTime().minusSeconds(1));
        System.out.println("queryInterval: " + queryInterval);
        List<HOSVehicleMileage> vehicleMileageRecords = hosDAO.getHOSVehicleMileage(testGroupData.group.getGroupID(), queryInterval, true);
        assertTrue("expected 1 record for 1 day/1 group", vehicleMileageRecords.size() == 1);
        HOSVehicleMileage rec = vehicleMileageRecords.get(0);
        Long expectedMiles = Long.valueOf(DataGenForHOSTesting.LOGOUT_ODOMETER-DataGenForHOSTesting.LOGIN_ODOMETER);
        assertEquals("miles ", expectedMiles, rec.getDistance());
        

    }
    
    @Test
    public void getDriverForEmpIdLastName() {
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        
        HOSDriverLogin driverLogin = hosDAO.getDriverForEmpidLastName(testDriver.getPerson().getEmpid(), testDriver.getPerson().getLast());
        
        assertEquals("driverID", testDriver.getDriverID(), driverLogin.getDriverID());
        
    }

    @Test
    public void hosOccupantLogsTest() {
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        GroupData testOccupantGroupData = itData.teamGroupData.get(ITData.GOOD);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        Driver testOccupant = fetchDriver(testOccupantGroupData.driver.getDriverID());
        Date genDataStartDate = new Date(itData.startDateInSec*1000l);
        DateTime dayStartUTC = new DateMidnight(genDataStartDate, DateTimeZone.UTC).toDateTime();
        
        Interval queryInterval = new Interval(new DateMidnight(dayStartUTC).minusDays(1), new DateMidnight(dayStartUTC).toDateTime().minusSeconds(1));
        List<HOSOccupantLog> occupantLogRecords = hosDAO.getHOSOccupantLogs(testDriver.getDriverID(), queryInterval);
        assertTrue("expected 1 occupant record for 1 day/1 driver", occupantLogRecords.size() == 1);
        HOSOccupantLog rec = occupantLogRecords.get(0);
        assertEquals("driverID", testOccupant.getDriverID(), rec.getDriverID());
        assertEquals("driverName", testOccupant.getPerson().getFirst() + " " + testOccupant.getPerson().getLast(), rec.getDriverName());
        assertEquals("vehicleID", testVehicle.getVehicleID(), rec.getVehicleID());
        assertTrue("returned day day is within interval", queryInterval.contains(rec.getLogTime().getTime()) || queryInterval.contains(rec.getEndTime().getTime()));
        
    }
    
    private HOSRecord createMinimalHosRecord(HOSDAO hosDAO, Driver driver, Date hosRecordDate, Integer vehicleID) {
        return createMinimalHosRecord(hosDAO, driver, hosRecordDate, vehicleID, HOSStatus.OFF_DUTY);
    }
    private HOSRecord createMinimalHosRecord(HOSDAO hosDAO, Driver driver, Date hosRecordDate, Integer vehicleID, HOSStatus status) {
        
        
        HOSRecord hosRecord = new HOSRecord();
        hosRecord.setDriverDotType(driver.getDriverDOTType());
        hosRecord.setDriverID(driver.getDriverID());
        hosRecord.setLocation(INITIAL_LOCATION);
        hosRecord.setLogTime(hosRecordDate);
        hosRecord.setStatus(status);
        hosRecord.setTimeZone(driver.getPerson().getTimeZone());
        hosRecord.setEditUserID(itData.fleetUser.getUserID());
        hosRecord.setVehicleID(vehicleID);
        Integer hosLogID = hosDAO.create(-1, hosRecord);
        System.out.println("hosLogID: " + hosLogID + " " + hosRecordDate);
        hosRecord.setHosLogID(hosLogID);
        return hosRecord;
    }

    private HOSRecord constructExpectedHosRecord(HOSRecord hosRecord, Driver driver, Vehicle vehicle) {
        HOSRecord expectedHosRecord = new HOSRecord();
        expectedHosRecord.setAddedTime(hosRecord.getAddedTime());
        expectedHosRecord.setChangedCnt(hosRecord.getChangedCnt() == null ? 1 : hosRecord.getChangedCnt() + 1);
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
        expectedHosRecord.setOriginalLocation(null);
        expectedHosRecord.setServiceID(hosRecord.getServiceID());
        expectedHosRecord.setSingleDriver(true);
        expectedHosRecord.setStatus(hosRecord.getStatus());
        expectedHosRecord.setTimeZone(hosRecord.getTimeZone());
        expectedHosRecord.setTrailerGallons(0f);
        expectedHosRecord.setTrailerID(hosRecord.getTrailerID());
        expectedHosRecord.setTripInspectionFlag(false);
        expectedHosRecord.setTripReportFlag(false);
        expectedHosRecord.setTruckGallons(0f);
        expectedHosRecord.setVehicleID((vehicle == null) ? 0 : vehicle.getVehicleID());
        expectedHosRecord.setVehicleIsDOT((vehicle == null || vehicle.getDot() == null) ? false : vehicle.getDot());
        expectedHosRecord.setVehicleLicense((vehicle == null) ? "" : vehicle.getLicense());
        expectedHosRecord.setVehicleName((vehicle == null) ? "" : vehicle.getName());
        expectedHosRecord.setVehicleOdometer(0l);
        return expectedHosRecord;
    }

    private Driver fetchDriver(Integer driverID) {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        
        return driverDAO.findByID(driverID);
    }



    // TODO: need to make a real test from this
    @Test
    @Ignore
    public void hosLogShipPackageTest() throws IOException {
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        GroupData testGroupData = itData.teamGroupData.get(ITData.INTERMEDIATE);
        Driver testDriver = fetchDriver(testGroupData.driver.getDriverID());
        Vehicle testVehicle = testGroupData.vehicle;
        
        RuleSetType dotType = testDriver.getDriverDOTType() == null ? RuleSetType.NON_DOT : testDriver.getDriverDOTType();
        int daysBack = dotType.getLogShipDaysBack();
        
        System.out.println("daysBack " + daysBack);

        DateTime currentDate = new DateTime();
        Interval interval = new Interval(currentDate.minusDays(daysBack), currentDate);

        System.out.println("interval " + interval);

        List<HOSRecord> driverRecords = hosDAO.getHOSRecords(testDriver.getDriverID(), interval, true);
        
        System.out.println("driverRecords size " + driverRecords.size());
        
        
        List<ByteArrayOutputStream> list = HOSUtil.packageLogsToShip(driverRecords, testDriver);
        
        System.out.println("list size " + list.size());
        
    }

}
