package it.com.inthinc.pro.dao;

// The tests in this file  can fail sporadically when the scheduler is running on the same
// server that is is hitting (usually dev).  If this becomes a problem, we can mark them as Ignore.  
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.jdbc.BaseJDBCTest;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWSHttpJDBCDAO;
import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWSIridiumJDBCDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ForwardCommandSpool;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NewDriverEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.notegen.MCMSimulator;
import com.inthinc.pro.notegen.NoteGenerator;
import com.inthinc.pro.notegen.TiwiProNoteSender;
import com.inthinc.pro.notegen.WSNoteSender;
import it.com.inthinc.pro.dao.model.ITDataOneTeamExt;

public class DriverLogoffTest extends BaseJDBCTest{
    private static final Logger logger = Logger.getLogger(DriverLogoffTest.class);
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "OneTeamData.xml";
    private static FwdCmdSpoolWSHttpJDBCDAO httpSpoolDAO;
    private static FwdCmdSpoolWSIridiumJDBCDAO iridiumSpoolDAO;
    private static EventHessianDAO eventDAO;
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
        
        
        itData = new ITDataOneTeamExt();

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

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        
        deviceDAO.setVehicleDAO(vehicleDAO);
        vehicleDAO.setDeviceDAO(deviceDAO);
        driverDAO.setVehicleDAO(vehicleDAO);
        
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
    public void logoffForwardCommands() {
        final String EMPID = "empid123"; 
        Date startTime = new Date(); 
        
        List<Integer> cmd2144List = new ArrayList<Integer>(); 
        cmd2144List.add(2144);
        List<Integer> cmd339List = new ArrayList<Integer>(); 
        cmd339List.add(339);
        
        Vehicle vehicleTiwipro = getVehicleByName("Tiwipro");
        Driver driver = getDriver();
        vehicleDAO.setVehicleDriver(vehicleTiwipro.getVehicleID(), driver.getDriverID());
        Device deviceTiwipro = getDeviceByName("Tiwipro");

        
        //Logon empID on deviceWS2144   
        Device deviceWS2144 = getDeviceByName("Waysmart820 2144");
        deviceWS2144.setProductVersion(ProductType.WAYSMART);
        updateDeviceFirmwareVer(deviceWS2144.getDeviceID(), 1375374693);
        Vehicle vehicleWS2144 = getVehicleByName("Waysmart820 2144");
        deviceWS2144.setVehicleID(vehicleWS2144.getVehicleID());
        clearCommands(deviceTiwipro.getDeviceID());
        genNewDriverEvent(deviceWS2144, EMPID);

        //Check to see if 2144 logoff sent to deviceTiwipro
        List<ForwardCommandSpool>  fcsList = httpSpoolDAO.getForDevice(deviceTiwipro.getDeviceID(), cmd2144List);
//        assertTrue("Tiwipro 2144 Logoff command sent", commandExists(fcsList, startTime));
        assertEquals("Tiwipro 2144 Logoff command sent", fcsList.size(), 1);
        
        //Logon empID on deviceWS339   
        Device deviceWS339 = getDeviceByName("Waysmart820 339");
        deviceWS339.setProductVersion(ProductType.WAYSMART);
        Vehicle vehicleWS339 = getVehicleByName("Waysmart820 339");
        deviceWS339.setVehicleID(vehicleWS339.getVehicleID());
        clearCommands(deviceWS2144.getDeviceID());
        genNewDriverEvent(deviceWS339, EMPID);
        
        //Check to see if 2144 logoff sent to deviceWS2144
        fcsList = iridiumSpoolDAO.getForDevice(deviceWS2144.getDeviceID(), cmd2144List);
//        assertTrue("WS 2144 Logoff command sent", commandExists(fcsList, startTime));
        assertEquals("WS 2144 Logoff command sent", fcsList.size(), 1);
        
        //Set deviceWS2144 to unknown, then login to using deviceWS339 empID
        setUnknowndriverForVehicle(vehicleWS2144.getVehicleID());
        startTime = new Date();
        clearCommands(deviceWS339.getDeviceID());
        genNewDriverEvent(deviceWS2144, EMPID);
        
        //Check to see if 339 logoff sent to deviceWS339
        fcsList = iridiumSpoolDAO.getForDevice(deviceWS339.getDeviceID(), cmd339List);
//        assertTrue("339 Logoff command sent", commandExists(fcsList, startTime));
        assertEquals("339 Logoff command sent", fcsList.size(), 1);


        setUnknowndriverForVehicles();
        
        //Create WS850 and Tiwipro devices
        Device deviceWS850 = getDeviceByName("Waysmart850");
        deviceWS850.setProductVersion(ProductType.WAYSMART);
        Vehicle vehicleWS850 = getVehicleByName("Waysmart850");
        deviceWS850.setVehicleID(vehicleWS850.getVehicleID());
        
        //Logon empID on deviceWS850   
        startTime = new Date();
        clearCommands(deviceWS850.getDeviceID());
        genNewDriverEvent(deviceWS850, EMPID);
        //Logon empID on deviceTiwipro   
        genNewDriverEvent(deviceWS339, EMPID);
        //Check to see if 339 logoff sent to deviceWS850
        fcsList = iridiumSpoolDAO.getForDevice(deviceWS850.getDeviceID(), cmd339List);
//        assertTrue("WS850 339 Logoff command sent", commandExists(fcsList, startTime));
        assertEquals("339 Logoff command sent", fcsList.size(), 1);
    }

    
    private boolean commandExists(List<ForwardCommandSpool> fcsList, Date startTime)
    {
        for (ForwardCommandSpool fcs : fcsList){
            if (fcs.getModified().getTime() >= startTime.getTime())
                return true;
        }
        
        return false;
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

    private void genNewDriverEvent(Device device, String empID) {
        NewDriverEvent event =  new NewDriverEvent(0L, 0, NoteType.NEWDRIVER_HOSRULE, new Date(), 60, 1000, DEFAULT_LAT, DEFAULT_LNG, empID);
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
        int secondsToWait = 5;
        for (int i = 0; i < secondsToWait; i++) {
            
            List<Event> events = eventDAO.getEventsForVehicle(device.getVehicleID(), new Date(event.getTime().getTime() - 5000l), new Date(event.getTime().getTime() + 5000l), noteTypes, 0);
            if (events == null || events.size() == 0) {
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
        httpSpoolDAO = new FwdCmdSpoolWSHttpJDBCDAO();
        httpSpoolDAO.setDataSource(new ITDataSource().getRealDataSource());
        iridiumSpoolDAO = new FwdCmdSpoolWSIridiumJDBCDAO();
        iridiumSpoolDAO.setDataSource(new ITDataSource().getRealDataSource());
        
        eventDAO = new EventHessianDAO();
        driverDAO = new DriverHessianDAO();
        groupDAO = new GroupHessianDAO();
        personDAO = new PersonHessianDAO();
        vehicleDAO = new VehicleHessianDAO();
        groupDAO.setSiloService(siloService);
        eventDAO.setSiloService(siloService);
        personDAO.setSiloService(siloService);
        vehicleDAO.setSiloService(siloService);
    }

    
    private static final String UPDATE_DEVICEFIRMVER = "UPDATE device SET firmVer=? WHERE deviceID=?";               
    public static void updateDeviceFirmwareVer(Integer deviceID, Integer firmVer) {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        if (deviceID != 0)
        {
            try {
                conn = new ITDataSource().getRealDataSource().getConnection();;
                
                statement = (PreparedStatement) conn.prepareStatement(UPDATE_DEVICEFIRMVER);

                statement.setInt(1, firmVer);
                statement.setInt(2, deviceID);
    
                statement.executeUpdate();
            }
            catch (Throwable e)
            { // handle database alerts in the usual manner
                logger.error("Exception: " + e);
                e.printStackTrace();
    
            }   // end catch
            finally
            { // clean up and release the connection
    //              socket.close();
                try {
                    if (conn != null)
                        conn.close();
                    if (statement != null)
                        statement.close();
                    if (resultSet != null)
                        resultSet.close();
                } catch (Exception e){}
            } // end finally    
        }
    }

    private static final String CLEAR_COMMANDS_FOR_DEVICE = "DELETE FROM Fwd_WSiridium WHERE deviceID = ?";
    private static final String CLEAR_SATCOMMANDS_FOR_DEVICE = "DELETE FROM fwd WHERE deviceID = ?";
    private static void clearCommands(Integer deviceId)
    {
        Connection conn = null;
        PreparedStatement statement = null;
        
        try
        {
            conn = new ITDataSource().getRealDataSource().getConnection();
            statement = conn.prepareStatement(CLEAR_COMMANDS_FOR_DEVICE);
            statement.setInt(1, deviceId);
            logger.debug(statement.toString());
            statement.executeUpdate();
            statement.close();
            
            statement = conn.prepareStatement(CLEAR_SATCOMMANDS_FOR_DEVICE);
            statement.setInt(1, deviceId);
            logger.debug(statement.toString());
            statement.executeUpdate();
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            
            logger.error("Exception: " + e);
        }   // end catch
        finally
        { // clean up and release the connection
            try {
                if (conn != null)
                    conn.close();
                if (statement != null)
                    statement.close();
            } catch (Exception e){
                logger.error("Exception: " + e);
            }    
        } // end finally 
    }
    
}
