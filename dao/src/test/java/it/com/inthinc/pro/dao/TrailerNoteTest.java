package it.com.inthinc.pro.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWSHttpJDBCDAO;
import com.inthinc.pro.dao.jdbc.TrailerReportJDBCDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ForwardCommandSpool;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TrailerAssignedStatus;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.TrailerDataEvent;
import com.inthinc.pro.model.event.TrailerProgrammedEvent;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.notegen.NoteGenerator;
import com.inthinc.pro.notegen.WSNoteSender;

// This test currently passes when it runs against QA with old mina and noteservice
// Fails against DEV so leaving it for now so it will be looked at with the new mina/noteservice

public class TrailerNoteTest extends SimpleJdbcDaoSupport {

    private static final Logger logger = Logger.getLogger(TrailerNoteTest.class);
    private static SiloService siloService;
    private static ITData itData;
    private static NoteGenerator noteGenerator;
    private static Integer DEFAULT_HEADING = 0; //NORTH
    private static Integer DEFAULT_SATS = 5; 
    private static Double DEFAULT_LAT = 40.704246d; 
    private static Double DEFAULT_LNG = -111.948613d; 

    private static EventHessianDAO eventDAO;
    private static TrailerReportJDBCDAO trailerReportDAO;
    private static FwdCmdSpoolWSHttpJDBCDAO fwdCmdSpoolDAO; 
    
    private static final String INTEGRATION_TEST_XML = "IntegrationTest.xml";
//    private static final String INTEGRATION_TEST_XML = "QAIntegrationTest.xml";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();

        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(INTEGRATION_TEST_XML);
//        if (!itData.parseTestData(stream, siloService, false, false, true, true)) {
        if (!itData.parseTestData(stream, siloService, true, true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }
        
        eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        
        trailerReportDAO = new TrailerReportJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        trailerReportDAO.setDataSource(dataSource);

        fwdCmdSpoolDAO = new FwdCmdSpoolWSHttpJDBCDAO();
        fwdCmdSpoolDAO.setDataSource(dataSource);
        

        noteGenerator = new NoteGenerator();
        WSNoteSender wsNoteSender = new WSNoteSender();
        wsNoteSender.setUrl(config.get(IntegrationConfig.MINA_HOST).toString());
        wsNoteSender.setPort(Integer.valueOf(config.get(IntegrationConfig.MINA_PORT).toString()));
        noteGenerator.setWsNoteSender(wsNoteSender);
       
  }
    
    
    private static void initApp() throws Exception {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);

        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);

    }

    public static final String TRAILER_NAME_1 = "TRAILER TEST 1";
    public static final String TRAILER_NAME_2 = "TRAILER TEST 2";
    public static final String TRAILER_INVALID = "TRAILER INVALID";

    @Test
    public void testTrailerProgrammedNotes() {

        // clear trailers from account
        this.deleteTrailers();

        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);

        // initial programmed
        TrailerProgrammedEvent trailerProgrammedEvent = new TrailerProgrammedEvent(0l, groupData.vehicle.getVehicleID(), NoteType.TRAILER_PROGRAMMED, new Date(), 100, 1000,
                DEFAULT_LAT, DEFAULT_LNG, TRAILER_NAME_1, null);
        trailerProgrammedEvent.setHeading(DEFAULT_HEADING);
        trailerProgrammedEvent.setSats(DEFAULT_SATS);
        groupData.device.setVehicleID(groupData.vehicle.getVehicleID());
        genEvent(trailerProgrammedEvent, groupData.device, groupData.driver);
        waitForDBUpdates(1);
        TrailerReportItem trailer = getTrailer(TRAILER_NAME_1);
        assertEquals("Trailer Name", TRAILER_NAME_1, trailer.getTrailerName());
        assertEquals("Trailer Assigned Status", TrailerAssignedStatus.ASSIGNED, trailer.getAssignedStatus());
        assertEquals("Trailer Status", Status.ACTIVE, trailer.getStatus());
        
        // reprogrammed
        TrailerProgrammedEvent trailerReprogrammedEvent = new TrailerProgrammedEvent(0l, groupData.vehicle.getVehicleID(), NoteType.TRAILER_PROGRAMMED, new Date(), 100, 1000,
                DEFAULT_LAT, DEFAULT_LNG, TRAILER_NAME_2, TRAILER_NAME_1);
        trailerReprogrammedEvent.setHeading(DEFAULT_HEADING);
        trailerReprogrammedEvent.setSats(DEFAULT_SATS);
        groupData.device.setVehicleID(groupData.vehicle.getVehicleID());
        genEvent(trailerReprogrammedEvent, groupData.device, groupData.driver);
        waitForDBUpdates(2);
        trailer = getTrailer(TRAILER_NAME_1);
        assertEquals("Trailer Name", TRAILER_NAME_1, trailer.getTrailerName());
        assertEquals("Trailer Assigned Status", TrailerAssignedStatus.NOT_ASSIGNED, trailer.getAssignedStatus());
        assertEquals("Trailer Status", Status.INACTIVE, trailer.getStatus());
        TrailerReportItem trailer2 = getTrailer(TRAILER_NAME_2);
        assertEquals("Trailer 2 Status", Status.ACTIVE, trailer2.getStatus());
        assertEquals("Trailer 2 Assigned Status", TrailerAssignedStatus.ASSIGNED, trailer2.getAssignedStatus());
        
        // unprogrammed
        TrailerProgrammedEvent trailerUnprogrammedEvent = new TrailerProgrammedEvent(0l, groupData.vehicle.getVehicleID(), NoteType.TRAILER_PROGRAMMED, new Date(), 100, 1000,
                DEFAULT_LAT, DEFAULT_LNG, "", TRAILER_NAME_2);
        trailerUnprogrammedEvent.setHeading(DEFAULT_HEADING);
        trailerUnprogrammedEvent.setSats(DEFAULT_SATS);
        groupData.device.setVehicleID(groupData.vehicle.getVehicleID());
        genEvent(trailerUnprogrammedEvent, groupData.device, groupData.driver);
        waitForDBUpdates(2);
        trailer2 = getTrailer(TRAILER_NAME_2);
        assertEquals("Trailer Status", Status.INACTIVE, trailer2.getStatus());
        assertEquals("Trailer Assigned Status", TrailerAssignedStatus.NOT_ASSIGNED, trailer2.getAssignedStatus());
        
    }

    @Test
    public void testTrailerDataNotes() {
        this.deleteTrailers();

        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);

        // initial programmed
        TrailerProgrammedEvent trailerProgrammedEvent = new TrailerProgrammedEvent(0l, groupData.vehicle.getVehicleID(), NoteType.TRAILER_PROGRAMMED, new Date(), 100, 1000,
                DEFAULT_LAT, DEFAULT_LNG, TRAILER_NAME_1, null);
        trailerProgrammedEvent.setHeading(DEFAULT_HEADING);
        trailerProgrammedEvent.setSats(DEFAULT_SATS);
        groupData.device.setVehicleID(groupData.vehicle.getVehicleID());
        genEvent(trailerProgrammedEvent, groupData.device, groupData.driver);
        waitForDBUpdates(1);
        
        // unprogram
        trailerProgrammedEvent = new TrailerProgrammedEvent(0l, groupData.vehicle.getVehicleID(), NoteType.TRAILER_PROGRAMMED, new Date(), 100, 1000,
                DEFAULT_LAT, DEFAULT_LNG, "", TRAILER_NAME_1);
        trailerProgrammedEvent.setHeading(DEFAULT_HEADING);
        trailerProgrammedEvent.setSats(DEFAULT_SATS);
        groupData.device.setVehicleID(groupData.vehicle.getVehicleID());
        genEvent(trailerProgrammedEvent, groupData.device, groupData.driver);
        waitForDBUpdates(1);
        
        
        // assign to different device
        TrailerDataEvent trailerDataEvent = new TrailerDataEvent(0l, groupData.vehicle.getVehicleID(), NoteType.TRAILER_DATA, new Date(), 100, 1000,
                DEFAULT_LAT, DEFAULT_LNG, 0, "SERVICEID", TRAILER_NAME_1);
        trailerDataEvent.setHeading(DEFAULT_HEADING);
        trailerDataEvent.setSats(DEFAULT_SATS);
        genEvent(trailerDataEvent, groupData.device, groupData.driver);
        waitForDBUpdates(1);
        TrailerReportItem trailer = getTrailer(TRAILER_NAME_1);
        assertEquals("Trailer Name", TRAILER_NAME_1, trailer.getTrailerName());
        assertEquals("Trailer Assigned Status", TrailerAssignedStatus.ASSIGNED, trailer.getAssignedStatus());
        assertEquals("Trailer Status", Status.ACTIVE, trailer.getStatus());
        assertEquals("Trailer Vehicle", groupData.vehicle.getVehicleID(), trailer.getVehicleID());
        
    }
    
    @Test
    public void testInvalidTrailerForwardCommandOnTrailerProgrammed() {

        // clear trailers from account
        this.deleteTrailers();

        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);

        // initial programmed
        Date eventDate = new Date();
        TrailerProgrammedEvent trailerProgrammedEvent = new TrailerProgrammedEvent(0l, groupData.vehicle.getVehicleID(), NoteType.TRAILER_PROGRAMMED, eventDate, 100, 1000,
                DEFAULT_LAT, DEFAULT_LNG, TRAILER_NAME_1, TRAILER_INVALID);
        trailerProgrammedEvent.setHeading(DEFAULT_HEADING);
        trailerProgrammedEvent.setSats(DEFAULT_SATS);
        groupData.device.setVehicleID(groupData.vehicle.getVehicleID());
        genEvent(trailerProgrammedEvent, groupData.device, groupData.driver);
        waitForDBUpdates(1);
        
        List<Integer> cmdIDList = new ArrayList<Integer>();
        cmdIDList.add(994);
        List<ForwardCommandSpool> fwdCmdSpool = fwdCmdSpoolDAO.getForDevice(groupData.device.getDeviceID(), cmdIDList);
        assertTrue("expected some invalid trailer forward commands", fwdCmdSpool != null && fwdCmdSpool.size() > 0);
        
        boolean found = false;
        for (ForwardCommandSpool fwd : fwdCmdSpool) {
            if (fwd.getCreated().after(eventDate)) {
                found = true;
                break;
            }
        }
        assertTrue("expected an invalid trailer forward after " + eventDate, found);
        
    }
    
    @Test
    public void testInvalidTrailerForwardCommandOnTrailerData() {

        // clear trailers from account
        this.deleteTrailers();

        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP);

        // initial programmed
        Date eventDate = new Date();
        TrailerDataEvent trailerDataEvent = new TrailerDataEvent(0l, groupData.vehicle.getVehicleID(), NoteType.TRAILER_DATA, eventDate, 100, 1000,
                DEFAULT_LAT, DEFAULT_LNG, 0, "SERVICEID", TRAILER_INVALID);
        trailerDataEvent.setHeading(DEFAULT_HEADING);
        trailerDataEvent.setSats(DEFAULT_SATS);
        groupData.device.setVehicleID(groupData.vehicle.getVehicleID());
        genEvent(trailerDataEvent, groupData.device, groupData.driver);
        waitForDBUpdates(1);
        
        List<Integer> cmdIDList = new ArrayList<Integer>();
        cmdIDList.add(994);
        List<ForwardCommandSpool> fwdCmdSpool = fwdCmdSpoolDAO.getForDevice(groupData.device.getDeviceID(), cmdIDList);
        assertTrue("expected some invalid trailer forward commands", fwdCmdSpool != null && fwdCmdSpool.size() > 0);
        
        boolean found = false;
        for (ForwardCommandSpool fwd : fwdCmdSpool) {
            if (fwd.getCreated().after(eventDate)) {
                found = true;
                break;
            }
        }
        assertTrue("expected an invalid trailer forward after " + eventDate, found);
        
    }
    
    private void genEvent(Event event, Device device, Driver driver) {
        try {
            noteGenerator.genEvent(event, device);
            pollForEvent(event, device);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail("Generate Note failed for device: " + device.getImei() + " noteType" + event.getType());
        }
        
    }
    private void pollForEvent(Event event, Device device) {
        List<NoteType> noteTypes = new ArrayList<NoteType>();
        noteTypes.add(event.getType());
        int secondsToWait = 30;
        for (int i = 0; i < secondsToWait; i++) {
            
            List<Event> events = eventDAO.getEventsForVehicle(device.getVehicleID(), new Date(event.getTime().getTime() - 5000l), new Date(event.getTime().getTime() + 5000l), noteTypes, 0);
            if (events == null || events.size() == 0) {
                if (i == (secondsToWait-1)) {
                    System.out.println();
                    logger.error(" pollForEvent failed even after waiting " + secondsToWait + " sec -- most likely queue is backed up");
                }
                try {
                    Thread.sleep(1000l);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                System.out.print(".");
            }
            else {
                break;
            }
        }
    }

    private void deleteTrailers() {
        DataSource dataSource = new ITDataSource().getRealDataSource();
        this.setDataSource(dataSource);
        SimpleJdbcTemplate template = getSimpleJdbcTemplate();
        String sql = "delete from trailer where acctID = " + itData.account.getAccountID();
        int rows = template.update(sql);
        System.out.println("Rows Deleted: " + rows);
    }
    
    private void waitForDBUpdates(int expectedCnt) {
        Map<String, Object> filterMap = new HashMap<String, Object>();
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(itData.teamGroupData.get(ITData.WS_GROUP).group.getGroupID());
        
        int cnt = 0;
        for (int i = 0; i < 10; i++) {
            if (cnt < expectedCnt) {
                try {
                    Thread.sleep(5000l);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            else {
                break;
            }
            cnt = trailerReportDAO.getTrailerReportCount(itData.account.getAccountID(), groupIDs, getFilters(filterMap));
        }
        
        
    }
    private TrailerReportItem getTrailer(String name) {

        // Test Paging count ascending
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("trailerName", name);
        PageParams params = new PageParams(0, 1, null, getFilters(filterMap));
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(itData.teamGroupData.get(ITData.WS_GROUP).group.getGroupID());
        groupIDs.add(itData.teamGroupData.get(ITData.GOOD).group.getGroupID());
        List<TrailerReportItem> trailerReportItems = trailerReportDAO.getTrailerReportItemByGroupPaging(itData.account.getAccountID(), groupIDs, params);
        return trailerReportItems.get(0);

    }
    
    private List<TableFilterField> getFilters(Map<String, Object> filterMap) {
        List<TableFilterField> retVal = new ArrayList<TableFilterField>();

        for (Map.Entry<String, Object> entry : filterMap.entrySet()) {
            TableFilterField filter = new TableFilterField(entry.getKey(), entry.getValue(), FilterOp.LIKE);
            retVal.add(filter);
        }
        return retVal;
    }
}
