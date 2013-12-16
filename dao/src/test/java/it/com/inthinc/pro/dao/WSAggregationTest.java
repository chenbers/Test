package it.com.inthinc.pro.dao;

// The tests in this file  can fail sporadically when the scheduler is running on the same
// server that is is hitting (usually dev).  If this becomes a problem, we can mark them as Ignore.  
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.jdbc.BaseJDBCTest;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.aggregation.TripWS;
import com.inthinc.pro.aggregation.db.DBUtil;
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
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.NewDriverEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.notegen.MCMSimulator;
import com.inthinc.pro.notegen.NoteGenerator;
import com.inthinc.pro.notegen.TiwiProNoteSender;
import com.inthinc.pro.notegen.WSNoteSender;
import it.com.inthinc.pro.dao.model.ITDataOneTeamExt;

// TODO:  TEMPORARY -- NEED TO FIGURE OUT AND FIX!!!
@Ignore 

public class WSAggregationTest extends BaseJDBCTest{
    private static final Logger logger = Logger.getLogger(WSAggregationTest.class);
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "OneTeamData.xml";
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
    public void checkAgg() {
        final String EMPID = "empid123"; 
        //Create WS850
        Device deviceWS850 = getDeviceByName("Waysmart850");
        
        deviceWS850.setProductVersion(ProductType.WAYSMART);
        Vehicle vehicleWS850 = getVehicleByName("Waysmart850");
        deviceWS850.setVehicleID(vehicleWS850.getVehicleID());

        try {
            clearNotes(deviceWS850.getDeviceID());
        } catch(Exception e)
        {}

        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(ReportTestConst.timeZone);
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(ReportTestConst.timeZone);
        cal.setTime(getStartOfDay(today, ReportTestConst.timeZone));
        String endDay = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1); //Start of yesterday;
        String startDay = dateFormat.format(cal.getTime());
        
        cal.add(Calendar.HOUR, 21);
        int odometer = 10;
        Event event = new Event(0L, 0, NoteType.IGNITION_ON, cal.getTime(), 60, odometer, DEFAULT_LAT, DEFAULT_LNG);
        event.setHeading(DEFAULT_HEADING);
        event.setSats(DEFAULT_SATS);
        genEvent(event, deviceWS850);

        cal.add(Calendar.MINUTE, 1);
        event =  new NewDriverEvent(0L, 0, NoteType.NEWDRIVER_HOSRULE, cal.getTime(), 60, odometer, DEFAULT_LAT, DEFAULT_LNG, EMPID);
        event.setHeading(DEFAULT_HEADING);
        event.setSats(DEFAULT_SATS);
        genEvent(event, deviceWS850);
        
        cal.add(Calendar.MINUTE, 59);
        odometer += 50;
        event =  new IdleEvent(0L, 0, NoteType.IDLE, cal.getTime(), 60, odometer, DEFAULT_LAT, DEFAULT_LNG, 100, 100);
        event.setHeading(DEFAULT_HEADING);
        event.setSats(DEFAULT_SATS);
        genEvent(event, deviceWS850);

        cal.add(Calendar.HOUR, 2);
        odometer += 100;
        event =  new Event(0L, 0, NoteType.TIMESTAMP, cal.getTime(), 60, odometer, DEFAULT_LAT, DEFAULT_LNG);
        event.setHeading(DEFAULT_HEADING);
        event.setSats(DEFAULT_SATS);
        genEvent(event, deviceWS850);
        
        cal.add(Calendar.HOUR, 6);
        odometer += 300;
        event =  new Event(0L, 0, NoteType.IGNITION_OFF, cal.getTime(), 60, odometer, DEFAULT_LAT, DEFAULT_LNG);
        event.setHeading(DEFAULT_HEADING);
        event.setSats(DEFAULT_SATS);
        genEvent(event, deviceWS850);
        
        DBUtil.setDataSource(new ITDataSource().getRealDataSource());
        TripWS tripWS = new TripWS();
        try {
            insertDeviceDay2Agg(deviceWS850.getDeviceID(), startDay);
            insertDeviceDay2Agg(deviceWS850.getDeviceID(), endDay);
            tripWS.updateTripsWS();
            assertTrue("Miles start day", getAggMiles(deviceWS850.getDeviceID(), startDay) == 15000); 
            assertTrue("Miles start day", getAggMiles(deviceWS850.getDeviceID(), endDay) == 30000); 
        } catch(Exception e)
        {}
    }

    
    private static final String INSERT_DEVICEDAY2AGG = "INSERT IGNORE INTO deviceDay2Agg(deviceID, day) VALUES(?,?)";               
    public static void insertDeviceDay2Agg(Integer deviceID, String day) {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        if (deviceID != 0)
        {
            try {
                conn = new ITDataSource().getRealDataSource().getConnection();;
                
                statement = (PreparedStatement) conn.prepareStatement(INSERT_DEVICEDAY2AGG);
                statement.setLong(1, deviceID);
                statement.setString(2, day);
    
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

    
    private static final String SELECT_AGG_MILES = "SELECT sum(odometer6) FROM  agg%02d WHERE deviceID = ? AND aggDate = ?";
    private static int getAggMiles(Integer deviceId, String day)  throws SQLException
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int odometer = 0;

        int shard = (int) (deviceId%32);
        String sqlStatement = String.format(SELECT_AGG_MILES, shard);
        
        try
        {
            
            conn = new ITDataSource().getRealDataSource().getConnection();
            statement = conn.prepareStatement(sqlStatement);
            statement.setInt(1, deviceId);
            statement.setString(2, day);
            
            logger.debug(statement.toString());

            resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                odometer = resultSet.getInt(1);
                
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
            if (conn != null)
                conn.close();
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
        } // end finally 
        return odometer;
    }
    
    private static final String CLEAR_NOTES_FOR_DEVICE = "DELETE FROM note%02d WHERE deviceID = ?";
    private static void clearNotes(Integer deviceId)  throws SQLException
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int shard = (int) (deviceId%32);
        String sqlStatement = String.format(CLEAR_NOTES_FOR_DEVICE, shard);
        
        try
        {
            
            conn = new ITDataSource().getRealDataSource().getConnection();
            statement = conn.prepareStatement(sqlStatement);
            statement.setInt(1, deviceId);
            
            logger.debug(statement.toString());

            statement.executeUpdate();
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
            if (conn != null)
                conn.close();
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
        } // end finally 
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
        
        eventDAO = new EventHessianDAO();
        driverDAO = new DriverHessianDAO();
        groupDAO = new GroupHessianDAO();
        personDAO = new PersonHessianDAO();
        vehicleDAO = new VehicleHessianDAO();
        groupDAO.setSiloService(siloService);
        eventDAO.setSiloService(siloService);
        driverDAO.setSiloService(siloService);
        personDAO.setSiloService(siloService);
        vehicleDAO.setSiloService(siloService);
    }

    private static Date getStartOfDay(Date date, TimeZone timeZone)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        
        String timeAsStr = dateFormat.format(date);
        timeAsStr = timeAsStr.substring(0, 10) + " 00:00:00";
        try
        {
            Date startOfDay = dateFormat.parse(timeAsStr);
            return startOfDay;
        }
        catch (ParseException e)
        {
            logger.error(e);
        }

        return null;
    }

}
