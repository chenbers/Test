package it.com.inthinc.pro.dao;

import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianJdbcHybridDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.notegen.MCMSimulator;
import com.inthinc.pro.notegen.NoteGenerator;
import com.inthinc.pro.notegen.TiwiProNoteSender;
import com.inthinc.pro.notegen.WSNoteSender;

public class ForgiveEventTest extends SimpleJdbcDaoSupport {

    private static final Logger logger = Logger.getLogger(SiloServiceTest.class);
    private static SiloService siloService;
    private static NoteGenerator noteGenerator;
    private static EventHessianJdbcHybridDAO eventDAO;
    private static ITData itData;

    private static Double DEFAULT_LAT = 40.704246d; 
    private static Double DEFAULT_LNG = -111.948613d; 
    private static final String DISPLAY_DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter dayFormatter = DateTimeFormat.forPattern(DISPLAY_DATE_FORMAT);

    private static final String XML_DATA_FILE = "AlertTest.xml";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));

        noteGenerator = new NoteGenerator();
        WSNoteSender wsNoteSender = new WSNoteSender();
        wsNoteSender.setUrl(config.get(IntegrationConfig.MINA_HOST).toString());
        wsNoteSender.setPort(Integer.valueOf(config.get(IntegrationConfig.MINA_PORT).toString()));
        noteGenerator.setWsNoteSender(wsNoteSender);
        
        TiwiProNoteSender tiwiProNoteSender = new TiwiProNoteSender();
        tiwiProNoteSender.setMcmSimulator(mcmSim);
        noteGenerator.setTiwiProNoteSender(tiwiProNoteSender);
        
        
        eventDAO = new EventHessianJdbcHybridDAO();
        eventDAO.setSiloService(siloService);
        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestData(stream, siloService, true, true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }
    }
    
    private static void initApp() {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    

    @Test
    public void testForgiveThreaded() {
        
        Integer noteCount = 10;

        DateTime dateTime = new DateTime().minusMinutes(noteCount);
        Driver driver = itData.teamGroupData.get(ITData.GOOD).driver;
        Vehicle vehicle = itData.teamGroupData.get(ITData.GOOD).vehicle;
        Device device = itData.teamGroupData.get(ITData.GOOD).device;
        device.setVehicleID(vehicle.getVehicleID());
        cleanup(vehicle, device);
        
        // generate 10 seatbelt events each 1 minute apart
        List<Long> noteIDs = new ArrayList<Long>();
        for (int i = 0; i < noteCount; i++) {
            SeatBeltEvent event = new SeatBeltEvent(0l, vehicle.getVehicleID(), NoteType.SEATBELT, dateTime.plusMinutes(i).toDate(), 60, 1000, 
                DEFAULT_LAT, DEFAULT_LNG, 80, 100, 20);
            long noteID = genEvent(event, device);
            noteIDs.add(noteID);
        }
        
        // check the agg shard for the 10 events
        LocalDate localDate = new LocalDate(new DateMidnight(dateTime, DateTimeZone.UTC));
        DateTime day = localDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()));
        checkAggTableSeatbeltEvents(device, dayFormatter.print(day), noteCount);
        
        // start separate threads to forgive each note (simulating the web server)
        List<ForgiveThread> threads = new ArrayList<ForgiveThread>();
        for (Long noteID : noteIDs) {
            ForgiveThread thread = new ForgiveThread(driver.getDriverID(), noteID, eventDAO);
            threads.add(thread);
            thread.start();
        }
        
        // wait for all threads to complete
        for (ForgiveThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // after all threads complete the forgive process the agg count should be 0
        checkAggTableSeatbeltEvents(device, dayFormatter.print(day), 0);
    }
    
    class ForgiveThread extends Thread {
        
        Integer driverID;
        Long noteID;
        EventDAO eventDAO;
        public ForgiveThread(Integer driverID, Long noteID, EventDAO eventDAO) {
            this.driverID = driverID;
            this.noteID = noteID;
            this.eventDAO = eventDAO;
        }

        @Override
        public void run() {
            logger.info("forgive: " + noteID);
            eventDAO.forgive(driverID, noteID);
        }
        
    }
    private void checkAggTableSeatbeltEvents(Device device, String aggDate, Integer expectedSeatbeltCount) {
        
        int shard = device.getDeviceID() % 32;
        String aggTable = "agg" + (shard < 10 ? "0"+shard : shard);
        String sql = "select seatbeltEvents from " + aggTable + " where deviceID = ? and aggDate = ?";
        Object[] args = new Object[] { device.getDeviceID(), aggDate };
        try {

            DataSource dataSource = new ITDataSource().getRealDataSource();
            this.setDataSource(dataSource);
            SimpleJdbcTemplate template = getSimpleJdbcTemplate();
            Integer result = template.queryForInt(sql, args);
            Assert.assertEquals("Expected Seatbelt count ", expectedSeatbeltCount, result);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void cleanup(Vehicle vehicle, Device device) {
        int shard = device.getDeviceID() % 32;
        String aggTable = "agg" + (shard < 10 ? "0"+shard : shard);
        String noteTable = "note" + (shard < 10 ? "0"+shard : shard);
        
        Object[] vargs = new Object[] { vehicle.getVehicleID()};
        try {

            DataSource dataSource = new ITDataSource().getRealDataSource();
            setDataSource(dataSource);
            SimpleJdbcTemplate template = getSimpleJdbcTemplate();
            template.update("delete from " + noteTable + " where type=3 and vehicleID = ?", vargs);
            template.update("delete from cachedNote where type=3 and vehicleID = ?", vargs);
            template.update("delete from " + aggTable + " where vehicleID = ?", vargs);
            template.update("delete from agg where vehicleID = ?", vargs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    private long genEvent(Event event, Device device) {
        try {
            noteGenerator.genEvent(event, device);
            return pollForEvent(event, device);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail("Generate Note failed for device: " + device.getImei() + " noteType" + event.getType());
        }
        return 0l;
    }
    private long pollForEvent(Event event, Device device) {
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
                return events.get(0).getNoteID();
            }
        }
        return 0l;
    }


}
