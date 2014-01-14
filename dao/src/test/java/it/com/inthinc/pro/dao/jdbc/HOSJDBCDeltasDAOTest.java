package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.HOSJDBCDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSDelta;
import com.inthinc.pro.model.hos.HOSDeltaRecord;
import com.inthinc.pro.model.hos.HOSRecord;


public class HOSJDBCDeltasDAOTest extends SimpleJdbcDaoSupport {
    private static ITData itData;
    private static SiloService siloService;

    
    private static String XML_DATA_FILE = "HOSTest.xml";
    
    private static DateTime testDateTime; 
    private DateTime lastUpdateTime; 
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss").withZone(DateTimeZone.UTC);

    // initial state
    // 0   8:30    SLEEPER (1)
    // 1   8:20    DRIVING (2)
    // 2   8:10    OFF_DUTY (0)
    // 3   8:00    ON_DUTY (3)
    
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
        
        testDateTime = new DateTime(itData.startDateInSec*1000l, DateTimeZone.UTC);
        
//        System.out.println("Test DateTime: " + dateTimeFormatter.print(testDateTime));
    }

    
    @Test
    // no changes
    public void testCaseNoChanges() {

        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);
        List<HOSRecord> initialList = setupforLogDeltasTest(groupData);
        List<HOSRecord> cloneList = copyLogTimeAndStatus(initialList);
        
        List<HOSRecord> deltaList = hosDAO.getHOSDeltaRecords(groupData.driver.getDriverID(), groupData.device.getDeviceID(), lastUpdateTime.toDate());
        assertNotNull("delta list should not be null", deltaList);
        assertEquals("expected no changes", 0, deltaList.size());
        
        HOSDelta hosDelta = HOSUtil.getHOSDelta(deltaList);
        assertEquals("Expected 0 delta records", 0, hosDelta.getTotalCount().intValue());
        
        checkOriginalListAtSummaryTime(hosDAO, groupData, cloneList);
    }
    
    @Test
    // Portal edit of record 1 status from Driving(2) to OFF_DUTY (0)
    public void testCaseChangeStatus() {

        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);
        List<HOSRecord> initialList = setupforLogDeltasTest(groupData);
        List<HOSRecord> cloneList = copyLogTimeAndStatus(initialList);
        
        HOSRecord editRecord = initialList.get(1);
        assertTrue("sanity check -expecting the DRIVING record", editRecord.getStatus() == HOSStatus.DRIVING);
        
        editRecord.setStatus(HOSStatus.OFF_DUTY);
        hosDAO.update(editRecord);
        
        List<HOSRecord> deltaList = hosDAO.getHOSDeltaRecords(groupData.driver.getDriverID(), groupData.device.getDeviceID(), lastUpdateTime.toDate());
        assertNotNull("delta list should not be null", deltaList);
        assertEquals("expected 1 change", 1, deltaList.size());
        HOSRecord rec = deltaList.get(0);
        assertEquals("log time should be same", rec.getLogTime(), rec.getOriginalLogTime());
        assertEquals("log status change to off duty", HOSStatus.OFF_DUTY, rec.getStatus());
        assertEquals("log status original driving", HOSStatus.DRIVING, rec.getOriginalStatus());
        
        HOSDelta hosDelta = HOSUtil.getHOSDelta(deltaList);
        assertEquals("Expected 2 delta records", 2, hosDelta.getTotalCount().intValue());
        assertEquals("Expected 1 deleted delta records", 1, hosDelta.getDeletedCount().intValue());
        assertEquals("Expected 1 added delta records", 1, hosDelta.getAddedCount().intValue());
        HOSDeltaRecord deletedDeltaRecord = hosDelta.getDeletedList().get(0);
        assertEquals("Deleted Record status", HOSStatus.DRIVING, deletedDeltaRecord.getStatus());
        assertEquals("Deleted Record time", editRecord.getLogTime(), deletedDeltaRecord.getDate());
        HOSDeltaRecord addedDeltaRecord = hosDelta.getAddedList().get(0);
        assertEquals("Added Record status", HOSStatus.OFF_DUTY, addedDeltaRecord.getStatus());
        assertEquals("Added Record time", editRecord.getLogTime(), addedDeltaRecord.getDate());

        checkOriginalListAtSummaryTime(hosDAO, groupData, cloneList);
    }

    @Test
    // Portal edit of record 1 time 5 minutes later
    public void testCaseChangeLogTime() {

        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);
        List<HOSRecord> initialList = setupforLogDeltasTest(groupData);
        List<HOSRecord> cloneList = copyLogTimeAndStatus(initialList);
        
        HOSRecord editRecord = initialList.get(1);
        assertTrue("sanity check -expecting the DRIVING record", editRecord.getStatus() == HOSStatus.DRIVING);
        
        Date originalLogTime = editRecord.getLogTime();
        Date editLogTime = new DateTime(originalLogTime).plusMinutes(5).toDate();
        editRecord.setLogTime(editLogTime);
        hosDAO.update(editRecord);
        
        List<HOSRecord> deltaList = hosDAO.getHOSDeltaRecords(groupData.driver.getDriverID(), groupData.device.getDeviceID(), lastUpdateTime.toDate());
        assertNotNull("delta list should not be null", deltaList);
        assertEquals("expected 1 change", 1, deltaList.size());
        HOSRecord rec = deltaList.get(0);
        assertEquals("log status should be same", rec.getStatus(), rec.getOriginalStatus());
        assertEquals("original log time should be different", originalLogTime, rec.getOriginalLogTime());
        assertEquals("log time should be different", editLogTime, rec.getLogTime());

        HOSDelta hosDelta = HOSUtil.getHOSDelta(deltaList);
        assertEquals("Expected 2 delta records", 2, hosDelta.getTotalCount().intValue());
        assertEquals("Expected 1 deleted delta records", 1, hosDelta.getDeletedCount().intValue());
        assertEquals("Expected 1 added delta records", 1, hosDelta.getAddedCount().intValue());
        HOSDeltaRecord deletedDeltaRecord = hosDelta.getDeletedList().get(0);
        assertEquals("Deleted Record status", HOSStatus.DRIVING, deletedDeltaRecord.getStatus());
        assertEquals("Deleted Record time", originalLogTime, deletedDeltaRecord.getDate());
        HOSDeltaRecord addedDeltaRecord = hosDelta.getAddedList().get(0);
        assertEquals("Added Record status", HOSStatus.DRIVING, addedDeltaRecord.getStatus());
        assertEquals("Added Record time", editLogTime, addedDeltaRecord.getDate());
        
        checkOriginalListAtSummaryTime(hosDAO, groupData, cloneList);
    }

    @Test
    // Kiosk add a record
    public void testCaseAddLogFromKiosk() {
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);
        Driver driver = groupData.driver;
        List<HOSRecord> initialList = setupforLogDeltasTest(groupData);
        List<HOSRecord> cloneList = copyLogTimeAndStatus(initialList);
        
        HOSRecord record = initialList.get(0);
        
        Date kioskLogTime = new DateTime(record.getLogTime()).plusMinutes(5).toDate();
        
        // add off duty from kiosk
        HOSRecord kioskRecord = new HOSRecord();
        kioskRecord.setStatus(HOSStatus.OFF_DUTY);
        kioskRecord.setLogTime(kioskLogTime);
        kioskRecord.setTimeZone(record.getTimeZone());
        kioskRecord.setDriverID(record.getDriverID());
        kioskRecord.setDriverDotType(driver.getDot() == null ? RuleSetType.NON_DOT : driver.getDot());
        kioskRecord.setEditUserID(0);
        kioskRecord.setLocation("kiosk record");
        hosDAO.create(0l, kioskRecord);
        
        List<HOSRecord> deltaList = hosDAO.getHOSDeltaRecords(groupData.driver.getDriverID(), groupData.device.getDeviceID(), lastUpdateTime.toDate());
        assertNotNull("delta list should not be null", deltaList);
        assertEquals("expected 1 change", 1, deltaList.size());
        HOSRecord rec = deltaList.get(0);
        assertEquals("status should be off duty", HOSStatus.OFF_DUTY, rec.getStatus());
        assertEquals("log time should be set", kioskLogTime, rec.getLogTime());
        assertEquals("origin should be kiosk", HOSOrigin.KIOSK, rec.getOrigin());
        assertNull("original status should be null", rec.getOriginalStatus());
        assertNull("original log time should be null", rec.getOriginalLogTime());
        
        HOSDelta hosDelta = HOSUtil.getHOSDelta(deltaList);
        assertEquals("Expected 1 delta records", 1, hosDelta.getTotalCount().intValue());
        assertEquals("Expected 0 deleted delta records", 0, hosDelta.getDeletedCount().intValue());
        assertEquals("Expected 1 added delta records", 1, hosDelta.getAddedCount().intValue());
        HOSDeltaRecord addedDeltaRecord = hosDelta.getAddedList().get(0);
        assertEquals("Added Record status", HOSStatus.OFF_DUTY, addedDeltaRecord.getStatus());
        assertEquals("Added Record time", kioskLogTime, addedDeltaRecord.getDate());

        checkOriginalListAtSummaryTime(hosDAO, groupData, cloneList);

    }


    @Test
    // Portal add a record
    public void testCaseAddLogFromPortal() {
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);
        Driver driver = groupData.driver;
        List<HOSRecord> initialList = setupforLogDeltasTest(groupData);
        List<HOSRecord> cloneList = copyLogTimeAndStatus(initialList);
        
        HOSRecord record = initialList.get(0);
        
        Date portalLogTime = new DateTime(record.getLogTime()).plusMinutes(5).toDate();
        HOSStatus portalLogStatus = HOSStatus.OFF_DUTY_AT_WELL;
        
        // add off duty from portal
        HOSRecord portalRecord = new HOSRecord();
        portalRecord.setStatus(portalLogStatus);
        portalRecord.setLogTime(portalLogTime);
        portalRecord.setTimeZone(record.getTimeZone());
        portalRecord.setDriverID(record.getDriverID());
        portalRecord.setDriverDotType(driver.getDot() == null ? RuleSetType.NON_DOT : driver.getDot());
        portalRecord.setEditUserID(itData.fleetUser.getGroupID());
        portalRecord.setLocation("portal record");
        hosDAO.create(0l, portalRecord);
        
        List<HOSRecord> deltaList = hosDAO.getHOSDeltaRecords(groupData.driver.getDriverID(), groupData.device.getDeviceID(), lastUpdateTime.toDate());
        assertNotNull("delta list should not be null", deltaList);
        assertEquals("expected 1 change", 1, deltaList.size());
        HOSRecord rec = deltaList.get(0);
        assertEquals("status should be off duty", portalLogStatus, rec.getStatus());
        assertEquals("log time should be set", portalLogTime, rec.getLogTime());
        assertEquals("origin should be kiosk", HOSOrigin.PORTAL, rec.getOrigin());
        assertNull("original status should be null", rec.getOriginalStatus());
        assertNull("original log time should be null", rec.getOriginalLogTime());
        
        HOSDelta hosDelta = HOSUtil.getHOSDelta(deltaList);
        assertEquals("Expected 1 delta records", 1, hosDelta.getTotalCount().intValue());
        assertEquals("Expected 0 deleted delta records", 0, hosDelta.getDeletedCount().intValue());
        assertEquals("Expected 1 added delta records", 1, hosDelta.getAddedCount().intValue());
        HOSDeltaRecord addedDeltaRecord = hosDelta.getAddedList().get(0);
        assertEquals("Added Record status", portalLogStatus, addedDeltaRecord.getStatus());
        assertEquals("Added Record time", portalLogTime, addedDeltaRecord.getDate());

        checkOriginalListAtSummaryTime(hosDAO, groupData, cloneList);
    }


    @Test
    // Portal delete a record
    public void testCaseDeleteLogFromPortal() {
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);
        List<HOSRecord> initialList = setupforLogDeltasTest(groupData);
        List<HOSRecord> cloneList = copyLogTimeAndStatus(initialList);
        
        HOSRecord deleteRecord = initialList.get(1);
        assertTrue("sanity check -expecting the DRIVING record", deleteRecord.getStatus() == HOSStatus.DRIVING);
        
        Date logTime = deleteRecord.getLogTime();
        hosDAO.deleteByID(deleteRecord.getHosLogID());
        
        List<HOSRecord> deltaList = hosDAO.getHOSDeltaRecords(groupData.driver.getDriverID(), groupData.device.getDeviceID(), lastUpdateTime.toDate());
        assertNotNull("delta list should not be null", deltaList);
        assertEquals("expected 1 change", 1, deltaList.size());
        HOSRecord rec = deltaList.get(0);
        assertTrue("deleted should be true", rec.getDeleted());
        assertNull("original status should be null", rec.getOriginalStatus());
        assertNull("original log time should be null", rec.getOriginalLogTime());
//        System.out.println("deleted rec: " + dateTimeFormatter.print(rec.getLogTime().getTime()) + " " + rec.getStatus() );

        HOSDelta hosDelta = HOSUtil.getHOSDelta(deltaList);
        assertEquals("Expected 1 delta records", 1, hosDelta.getTotalCount().intValue());
        assertEquals("Expected 1 deleted delta records", 1, hosDelta.getDeletedCount().intValue());
        assertEquals("Expected 0 added delta records", 0, hosDelta.getAddedCount().intValue());
        HOSDeltaRecord deletedDeltaRecord = hosDelta.getDeletedList().get(0);
//        System.out.println("deleted delta rec: " + dateTimeFormatter.print(deletedDeltaRecord.getDate().getTime()) + " " + deletedDeltaRecord.getStatus() );
        assertEquals("Deleted Record status", HOSStatus.DRIVING, deletedDeltaRecord.getStatus());
        assertEquals("Deleted Record time", logTime, deletedDeltaRecord.getDate());

        checkOriginalListAtSummaryTime(hosDAO, groupData, cloneList);
    }

    @Test
    // Portal multiple edits of record 1 time 5 minutes later, status change from driving to off duty
    public void testCaseChangeLogTimeAndStatus() {

        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);
        List<HOSRecord> initialList = setupforLogDeltasTest(groupData);
        List<HOSRecord> cloneList = copyLogTimeAndStatus(initialList);
        
        HOSRecord editRecord = initialList.get(1);
        assertTrue("sanity check -expecting the DRIVING record", editRecord.getStatus() == HOSStatus.DRIVING);
        
        Date originalLogTime = editRecord.getLogTime();
        Date editLogTime = new DateTime(originalLogTime).plusMinutes(5).toDate();
        editRecord.setLogTime(editLogTime);
        editRecord.setStatus(HOSStatus.OFF_DUTY);
        hosDAO.update(editRecord);
        wait(2);
        editLogTime = new DateTime(originalLogTime).plusMinutes(10).toDate();
        editRecord.setLogTime(editLogTime);
        editRecord.setStatus(HOSStatus.SLEEPER);
        hosDAO.update(editRecord);

        
        List<HOSRecord> deltaList = hosDAO.getHOSDeltaRecords(groupData.driver.getDriverID(), groupData.device.getDeviceID(), lastUpdateTime.toDate());
        assertNotNull("delta list should not be null", deltaList);
        assertEquals("expected 1 change", 1, deltaList.size());
        HOSRecord rec = deltaList.get(0);
        assertEquals("log status should be different", HOSStatus.DRIVING, rec.getOriginalStatus());
        assertEquals("original log time should be different", originalLogTime, rec.getOriginalLogTime());
        assertEquals("log time should be different", editLogTime, rec.getLogTime());
        assertEquals("status should be different", HOSStatus.SLEEPER, rec.getStatus());
        
        HOSDelta hosDelta = HOSUtil.getHOSDelta(deltaList);
        assertEquals("Expected 2 delta records", 2, hosDelta.getTotalCount().intValue());
        assertEquals("Expected 1 deleted delta records", 1, hosDelta.getDeletedCount().intValue());
        assertEquals("Expected 1 added delta records", 1, hosDelta.getAddedCount().intValue());
        HOSDeltaRecord deletedDeltaRecord = hosDelta.getDeletedList().get(0);
        assertEquals("Deleted Record status", HOSStatus.DRIVING, deletedDeltaRecord.getStatus());
        assertEquals("Deleted Record time", originalLogTime, deletedDeltaRecord.getDate());
        HOSDeltaRecord addedDeltaRecord = hosDelta.getAddedList().get(0);
        assertEquals("Added Record status", HOSStatus.SLEEPER, addedDeltaRecord.getStatus());
        assertEquals("Added Record time", editLogTime, addedDeltaRecord.getDate());

    
        checkOriginalListAtSummaryTime(hosDAO, groupData, cloneList);

    }


    @Test
    // Portal multiple edits of record 1 time 5 minutes later, status change from driving to off duty
    public void testCaseHOSRecordAtSummaryTime() {

        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);
        List<HOSRecord> initialList = setupforLogDeltasTest(groupData);
        List<HOSRecord> cloneList = copyLogTimeAndStatus(initialList);
        
        HOSRecord editRecord = initialList.get(1);
        assertTrue("sanity check -expecting the DRIVING record", editRecord.getStatus() == HOSStatus.DRIVING);
        
        Date originalLogTime = editRecord.getLogTime();
        Date editLogTime = new DateTime(originalLogTime).plusMinutes(5).toDate();
        editRecord.setLogTime(editLogTime);
        editRecord.setStatus(HOSStatus.OFF_DUTY);
        hosDAO.update(editRecord);
        wait(2);
        editLogTime = new DateTime(originalLogTime).plusMinutes(10).toDate();
        editRecord.setLogTime(editLogTime);
        editRecord.setStatus(HOSStatus.SLEEPER);
        hosDAO.update(editRecord);

        
        checkOriginalListAtSummaryTime(hosDAO, groupData, cloneList);
    }

    @Test
    // Portal multiple edits of record 1 time 5 minutes later, status change from driving to off duty, and then delete it
    public void testCaseMultipleChanges() {

        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());
        
        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);
        List<HOSRecord> initialList = setupforLogDeltasTest(groupData);
        List<HOSRecord> cloneList = copyLogTimeAndStatus(initialList);
        
        HOSRecord editRecord = initialList.get(1);
        assertTrue("sanity check -expecting the DRIVING record", editRecord.getStatus() == HOSStatus.DRIVING);
        
        Date originalLogTime = editRecord.getLogTime();
        Date editLogTime = new DateTime(originalLogTime).plusMinutes(5).toDate();
        editRecord.setLogTime(editLogTime);
        editRecord.setStatus(HOSStatus.OFF_DUTY);
        hosDAO.update(editRecord);
        wait(2);
        editLogTime = new DateTime(originalLogTime).plusMinutes(10).toDate();
        editRecord.setLogTime(editLogTime);
        editRecord.setStatus(HOSStatus.SLEEPER);
        hosDAO.update(editRecord);
        wait(2);
        hosDAO.deleteByID(editRecord.getHosLogID());

        checkOriginalListAtSummaryTime(hosDAO, groupData, cloneList);
    }


    private void checkOriginalListAtSummaryTime(HOSDAO hosDAO, GroupData groupData, List<HOSRecord> cloneList) {
        List<HOSRecord> recListAtSummaryTime = hosDAO.getHOSRecordAtSummaryTime(groupData.driver.getDriverID(), lastUpdateTime.toDate(), testDateTime.toDate(), testDateTime.plusHours(1).toDate());
        assertEquals("recListAtSummaryTime should match initial list", cloneList.size(), recListAtSummaryTime.size());
        
        int cnt = 0;
        for (HOSRecord rec : cloneList) {
            HOSRecord summaryRec = recListAtSummaryTime.get(cnt);
//            System.out.println("rec: " + dateTimeFormatter.print(rec.getLogTime().getTime()) + " " + rec.getStatus() );
//            System.out.println("summaryRec: " + dateTimeFormatter.print(summaryRec.getLogTime().getTime()) + " " + summaryRec.getStatus() );

            assertEquals("rec " + cnt + " status", rec.getStatus(), summaryRec.getStatus());
            assertEquals("rec " + cnt + " logTime", rec.getLogTime(), summaryRec.getLogTime());
            cnt++;
        }
    }


    private List<HOSRecord> copyLogTimeAndStatus(List<HOSRecord> initialList) {
        List <HOSRecord> cloneList = new ArrayList<HOSRecord>();
        for (HOSRecord rec : initialList) {
            HOSRecord cloneRecord = new HOSRecord();
            cloneRecord.setStatus(rec.getStatus());
            cloneRecord.setLogTime(rec.getLogTime());
            cloneList.add(cloneRecord);
        }
        return cloneList;
    }


    private void wait(int seconds) {
        
        try {
            Thread.sleep(seconds * 1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private List<HOSRecord> setupforLogDeltasTest(GroupData groupData) {
        DataSource dataSource = new ITDataSource().getRealDataSource();
        this.setDataSource(dataSource);
        SimpleJdbcTemplate template = getSimpleJdbcTemplate();
        
        // remove existing data for ws driver
        String sql = "delete from hoslog_changelog where driverID = " + groupData.driver.getDriverID();
        int rows = template.update(sql);
//        System.out.println("ChangeLog Rows Deleted: " + rows);

        sql = "delete from hoslog where driverID = " + groupData.driver.getDriverID();
        rows = template.update(sql);
//        System.out.println("HOSLog Rows Deleted: " + rows);
        
        
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());

        HOSRecord hosRecord = new HOSRecord();
        hosRecord.setDeviceID(groupData.device.getDeviceID());
        hosRecord.setVehicleID(groupData.vehicle.getVehicleID());
        hosRecord.setNoteID(0l);
        hosRecord.setVehicleOdometer(1000l);
        hosRecord.setLat(0.0f);
        hosRecord.setLng(0.0f);
        hosRecord.setLocation("test");
        hosRecord.setUserEnteredLocationFlag(Boolean.FALSE);
        hosRecord.setNoteFlags(Byte.valueOf((byte)0));
        hosRecord.setTrailerID("trailer");
        hosRecord.setServiceID("service");
        hosRecord.setEmployeeID(groupData.driver.getPerson().getEmpid().toUpperCase());
        hosRecord.setStateID(43);
        hosRecord.setTripInspectionFlag(false);
        hosRecord.setTripReportFlag(false);
        
        DateTime logStartTime = testDateTime;

        hosRecord.setLogTime(logStartTime.toDate());
        hosRecord.setStatus(HOSStatus.ON_DUTY);
        hosRecord.setStatusCode(hosRecord.getStatus().getCode());
        hosDAO.createFromNote(hosRecord);
        
        hosRecord.setLogTime(logStartTime.plusMinutes(10).toDate());
        hosRecord.setStatus(HOSStatus.OFF_DUTY);
        hosRecord.setStatusCode(hosRecord.getStatus().getCode());
        hosDAO.createFromNote(hosRecord);
        
        hosRecord.setLogTime(logStartTime.plusMinutes(20).toDate());
        hosRecord.setStatus(HOSStatus.DRIVING);
        hosRecord.setStatusCode(hosRecord.getStatus().getCode());
        hosDAO.createFromNote(hosRecord);

        hosRecord.setLogTime(logStartTime.plusMinutes(30).toDate());
        hosRecord.setStatus(HOSStatus.SLEEPER);
        hosRecord.setStatusCode(hosRecord.getStatus().getCode());
        hosDAO.createFromNote(hosRecord);

    
        lastUpdateTime = testDateTime.plusHours(1);
        sql = "UPDATE hoslog set timeAdded = '" + dateTimeFormatter.print(lastUpdateTime) + "', timeLastUpdated = '" + dateTimeFormatter.print(lastUpdateTime) + "' where driverID = " + groupData.driver.getDriverID();
        rows = template.update(sql);
//        System.out.println("HOSLog Rows Updated: " + rows);
        
        List<HOSRecord> recList = hosDAO.getHOSRecords(groupData.driver.getDriverID(), new Interval(logStartTime.minusMinutes(1), logStartTime.plusHours(1)), false);
//        for (HOSRecord rec : recList) {
//            System.out.println(dateTimeFormatter.print(rec.getLogTime().getTime()) + " " + rec.getStatus() );
//        }
        return recList;

    }
}
