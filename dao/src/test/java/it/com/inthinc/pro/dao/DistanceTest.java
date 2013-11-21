package it.com.inthinc.pro.dao;

// The tests in this file  can fail sporadically when the scheduler is running on the same
// server that is is hitting (usually dev).  If this becomes a problem, we can mark them as Ignore.  
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.jdbc.BaseJDBCTest;
import it.com.inthinc.pro.dao.model.ITDataOneTeamExt;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.cassandra.CassandraDB;
import com.inthinc.pro.dao.cassandra.EventCassandraDAO;
import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.notegen.MCMSimulator;
import com.inthinc.pro.notegen.NoteGenerator;
import com.inthinc.pro.notegen.TiwiProNoteSender;
import com.inthinc.pro.notegen.WSNoteSender;

public class DistanceTest extends BaseJDBCTest{
    private static final Logger logger = Logger.getLogger(DistanceTest.class);
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "OneTeamData.xml";
    private static CassandraDB cassandraDB;
    private static EventCassandraDAO eventDAO;
    private static DriverHessianDAO driverDAO;
    private static GroupHessianDAO groupDAO;
    private static PersonHessianDAO personDAO;
    private static VehicleHessianDAO vehicleDAO;
    private static ITDataOneTeamExt itData;
    
    private static NoteGenerator noteGenerator;
    private static Integer DEFAULT_HEADING = 0; //NORTH
    private static Integer DEFAULT_SATS = 5; 
    private static Double DEFAULT_LAT = 40.704246d; 
    private static Double DEFAULT_LNG = -111.948613d; 
    private static Integer unkDriverID = 0;

    private static String minaHost;
    private static Integer minaPort;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        minaHost = config.get(IntegrationConfig.MINA_HOST).toString();
        minaPort = Integer.valueOf(config.get(IntegrationConfig.MINA_PORT).toString());
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));

        noteGenerator = new NoteGenerator();
        WSNoteSender wsNoteSender = new WSNoteSender();
        wsNoteSender.setUrl(minaHost);
        wsNoteSender.setPort(minaPort);
        noteGenerator.setWsNoteSender(wsNoteSender);
        
        TiwiProNoteSender tiwiProNoteSender = new TiwiProNoteSender();
        tiwiProNoteSender.setMcmSimulator(mcmSim);
        noteGenerator.setTiwiProNoteSender(tiwiProNoteSender);
        
        itData = new ITDataOneTeamExt();


        cassandraDB = new CassandraDB(true, config.getProperty(IntegrationConfig.CASSANDRA_CLUSTER), config.getProperty(IntegrationConfig.CASSANDRA_KEYSPACE), config.getProperty(IntegrationConfig.CASSANDRA_CACHE_KEYSPACE), config.getProperty(IntegrationConfig.CASSANDRA_NODE_ADDRESS), 10, false, false);
        EventCassandraDAO dao = new EventCassandraDAO();
        dao.setCassandraDB(cassandraDB);
        dao.setVehicleDAO(vehicleDAO);

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestDataExt(stream, siloService)) {
            throw new Exception("Error parsing Test data xml file");
        }
     
        initDAOs();
        initApp();
    }

    private static void initApp() {
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();
        
        Device device = getDeviceByName("Waysmart820 2144");
        Account account = accountDAO.findByID(device.getAccountID());
        unkDriverID = account.getUnkDriverID();
        //Init vehicles to unknown driver
        setUnknowndriverForVehicles();
        sleep(2000);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void distanceTest() {

        Date noteTime = new Date();
        int wsDistance = 50;
        Device deviceWS2144 = getDeviceByName("Waysmart820 2144");
        deviceWS2144.setProductVersion(ProductType.WAYSMART);
        Vehicle vehicleWS2144 = getVehicleByName("Waysmart820 2144");
        deviceWS2144.setVehicleID(vehicleWS2144.getVehicleID());
        genSpeedingEvent(deviceWS2144, noteTime, wsDistance);

        
        Vehicle vehicleTiwipro = getVehicleByName("Tiwipro");
        Driver driver = getDriver();
        vehicleDAO.setVehicleDriver(vehicleTiwipro.getVehicleID(), driver.getDriverID());
        Device deviceTiwipro = getDeviceByName("Tiwipro");
        deviceTiwipro.setProductVersion(ProductType.TIWIPRO);
        deviceTiwipro.setVehicleID(vehicleTiwipro.getVehicleID());

        int tiwiDistance = 500;
        
        genSpeedingEvent(deviceTiwipro, noteTime, tiwiDistance);

        


        List<NoteType> noteTypeList = new ArrayList<NoteType>();
        noteTypeList.add(NoteType.SPEEDING_EX3);
        List<Event> events = eventDAO.getEventsForVehicle(vehicleTiwipro.getVehicleID(), noteTime, new Date(), noteTypeList, 1);
        assertTrue("1 speeding event", events.size() == 1);
        SpeedingEvent tiwiSpeedingEvent = (SpeedingEvent)events.get(0);
    
        events = eventDAO.getEventsForVehicle(deviceWS2144.getVehicleID(), noteTime, new Date(), noteTypeList, 1);
        assertTrue("1 speeding event", events.size() == 1);
        SpeedingEvent wsSpeedingEvent = (SpeedingEvent) events.get(0);

        assertTrue("Tiwi and WS distance the same", tiwiSpeedingEvent.getDistance().intValue() == wsSpeedingEvent.getDistance().intValue());
        
    }


    private static Group getTeam() {
        return itData.teamGroupListData.get(0).group;
    }
    private static Device getDeviceByName(String name){
        for (Device device : itData.teamGroupListData.get(0).deviceList) {
            if (device.getName().equalsIgnoreCase(name))
                return device;
        }    
        return null;
    }
    
    private static Vehicle getVehicleByName(String name){
        for (Vehicle vehicle : itData.teamGroupListData.get(0).vehicleList) {
            if (vehicle.getName().equalsIgnoreCase(name))
                return vehicle;
        }    
        return null;
    }

    private static Driver getDriver(){
        return itData.teamGroupListData.get(0).driverList.get(0);
    }

    private void genSpeedingEvent(Device device, Date time, int distance) {
        Event event = new SpeedingEvent(0L, 0, NoteType.SPEEDING_EX3, time, 60, 10000, DEFAULT_LAT, DEFAULT_LNG, 85, 60, 60, distance, 100);

        event.setHeading(DEFAULT_HEADING);
        event.setSats(DEFAULT_SATS);
        genEvent(event, device);
    }

    private static void setUnknowndriverForVehicles() {
        //Init vehicles to unknown driver
        for (Vehicle vehicle : itData.teamGroupListData.get(0).vehicleList) {
            vehicleDAO.setVehicleDriver(vehicle.getVehicleID(), unkDriverID, new Date());
        }    
    } 

    private static void setUnknowndriverForVehicle(Integer vehicleID) {
        //Init vehicles to unknown driver
        for (Vehicle vehicle : itData.teamGroupListData.get(0).vehicleList) {
            if (vehicle.getVehicleID() == vehicleID.intValue())
                vehicleDAO.setVehicleDriver(vehicle.getVehicleID(), unkDriverID, new Date());
        }    
    } 

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms * 1l);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    private void genEvent(Event event, Device device) {
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
            
            @SuppressWarnings("unchecked")
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

    private static void initDAOs()
    {
        driverDAO = new DriverHessianDAO();
        groupDAO = new GroupHessianDAO();
        personDAO = new PersonHessianDAO();
        vehicleDAO = new VehicleHessianDAO();
        groupDAO.setSiloService(siloService);
        driverDAO.setSiloService(siloService);
        personDAO.setSiloService(siloService);
        vehicleDAO.setSiloService(siloService);
        eventDAO = new EventCassandraDAO();
        eventDAO.setCassandraDB(cassandraDB);
        eventDAO.setVehicleDAO(vehicleDAO);
    }
}
