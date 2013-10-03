package it.com.inthinc.pro.dao;

// The tests in this file  can fail sporadically when the scheduler is running on the same
// server that is is hitting (usually dev).  If this becomes a problem, we can mark them as Ignore.  
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.jdbc.BaseJDBCTest;
import it.com.inthinc.pro.dao.model.ITDataOneTeamExt;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.mutable.MutableInt;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.caucho.hessian.io.HessianOutput;
import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.CrashReportHessianDAO;
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
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ForgivenType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.FullEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.notegen.MCMSimulator;
import com.inthinc.pro.notegen.NoteGenerator;
import com.inthinc.pro.notegen.TiwiProNoteSender;
import com.inthinc.pro.notegen.WSNoteSender;

public class CrashTest extends BaseJDBCTest{
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
    private static CrashReportHessianDAO crashReportHessianDAO; 
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
    public void crash() {
        Vehicle vehicleTiwipro = getVehicleByName("Tiwipro");
        Driver driver = getDriver();
        vehicleDAO.setVehicleDriver(vehicleTiwipro.getVehicleID(), driver.getDriverID());
//        sleep(2000);
        Device deviceTiwipro = getDeviceByName("Tiwipro");
        deviceTiwipro.setProductVersion(ProductType.TIWIPRO);
        deviceTiwipro.setVehicleID(vehicleTiwipro.getVehicleID());
        
        Date crashTime = new Date();
        int crashTs = ((int)(crashTime.getTime()/1000));
        genFullEvent(deviceTiwipro, crashTime);

        
        MutableInt ts = new MutableInt(crashTs-20);

        byte[] packet1 = createCrashData(crashTs, ts);
        byte[] packet2 = createCrashData(crashTs, ts);
        byte[] packet3 = createCrashData(crashTs, ts);

        int retVal = sendCrashData(deviceTiwipro.getImei(), packet1);
        assertTrue("retVal==0", retVal == 0);
        retVal = sendCrashData(deviceTiwipro.getImei(), packet2);
        assertTrue("retVal==0", retVal == 0);
        retVal = sendCrashData(deviceTiwipro.getImei(), packet3);
        assertTrue("retVal==0", retVal == 0);
        
        Date startTime = new Date((crashTs-30)*1000L);
        Date endTime = new Date(ts.intValue()*1000L);
        List<CrashReport> crashReportList = crashReportHessianDAO.findByGroupID(getTeam().getGroupID(), startTime, endTime, ForgivenType.INCLUDE);
        assertTrue("One crash generated", crashReportList.size() == 1);
        
        CrashReport crashReport = crashReportList.get(0);
        assertTrue("51 crash points", crashReport.getCrashDataPoints().size() == 51);

        //Sending again to check duplicate crashes/points not being created    
        retVal = sendCrashData(deviceTiwipro.getImei(), packet1);
        assertTrue("retVal==0", retVal == 0);
        crashReportList = crashReportHessianDAO.findByGroupID(getTeam().getGroupID(), startTime, endTime, ForgivenType.INCLUDE);
        assertTrue("One crash generated", crashReportList.size() == 1);
        crashReport = crashReportList.get(0);
        assertTrue("51 crash points", crashReport.getCrashDataPoints().size() == 51);
        
        
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

    private void genFullEvent(Device device, Date time) {
        Event event = new FullEvent(0L, 0, NoteType.FULLEVENT, time, 60, 10000, DEFAULT_LAT, DEFAULT_LNG, 5, 3, 1, 40);
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
        httpSpoolDAO = new FwdCmdSpoolWSHttpJDBCDAO();
        httpSpoolDAO.setDataSource(new ITDataSource().getRealDataSource());
        iridiumSpoolDAO = new FwdCmdSpoolWSIridiumJDBCDAO();
        iridiumSpoolDAO.setDataSource(new ITDataSource().getRealDataSource());
        
        eventDAO = new EventHessianDAO();
        driverDAO = new DriverHessianDAO();
        groupDAO = new GroupHessianDAO();
        personDAO = new PersonHessianDAO();
        vehicleDAO = new VehicleHessianDAO();
        crashReportHessianDAO = new CrashReportHessianDAO();
        groupDAO.setSiloService(siloService);
        eventDAO.setSiloService(siloService);
        driverDAO.setSiloService(siloService);
        personDAO.setSiloService(siloService);
        vehicleDAO.setSiloService(siloService);
        crashReportHessianDAO.setSiloService(siloService);
        crashReportHessianDAO.setVehicleDAO(vehicleDAO);
        crashReportHessianDAO.setDriverDAO(driverDAO);
    }

    private static int sendCrashData(String address, byte[] crashData)
    {
        int tiwiReturnCode = 1000;
        logger.info("Sending crash data: " + address);

        ByteArrayOutputStream packetStream = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(packetStream);
        
        try {
            ho.startCall();
            ho.writeMethod("crash");
            ho.writeString(address);
            int recordCount = crashData.length/17;
            logger.debug("recordCount: " + recordCount + " crashData.length: " + crashData.length);
            ho.writeListBegin(recordCount, null);
            for (int i = 0; i < recordCount; i++) {
                byte[] record = Arrays.copyOfRange(crashData, i*17, (i*17)+17);
                int crashTS = read(record, 0, 4);
                if (crashTS < 0)
                    break;
                
                ho.writeBytes(record);
            }    
            ho.writeListEnd();
    
    
            ho.completeCall();
            ho.flush();
            
        } catch (Exception e)
        {
            logger.error("Exception creating Hessian packet: " + e);
        }
        
        ByteArrayOutputStream returnData = new  ByteArrayOutputStream();
        byte[] returnChunk = new byte[100];

        Socket socket = null;
        ByteArrayOutputStream out = null;
        try {
            socket = new Socket(minaHost, 8090);
            socket.setReuseAddress(true);
            out = new ByteArrayOutputStream();
    
            out.write(packetStream.toByteArray(), 0, packetStream.size());
            out.writeTo(socket.getOutputStream());
            out.flush();

            socket.shutdownOutput();
    
            socket.setSoTimeout(10000);
            
            InputStream in = socket.getInputStream();
            int totalBytesRead = 0;
            int bytesRead = in.read(returnChunk);
            while (bytesRead != -1)
            {
                totalBytesRead += bytesRead;
                returnData.write(returnChunk, 0, bytesRead);
                bytesRead = in.read(returnChunk);
            }
            socket.shutdownInput();

            byte[] arrReturnData = returnData.toByteArray();
            byte type = arrReturnData[3];
            if (type != (byte)'I')
                logger.info("No Tiwi Proxy Return Code: " + type);
            else
            {
                tiwiReturnCode = read(arrReturnData, 4, 4);
                logger.info("Tiwi Proxy Return Code: " + tiwiReturnCode);
            }
        } catch(Exception e){
            e.printStackTrace();
        } 
        finally{
            try {
                if (socket != null)
                    socket.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket = null;
            out = null;
        }
        return tiwiReturnCode;
    }

    private byte[] createCrashData(Integer crashTs,  MutableInt ts) {
        ByteArrayOutputStream crashStream = new ByteArrayOutputStream();
        try {
            for (int i = 0; i < 17; i++) {
                crashStream.write(convertIntToBytes(ts.intValue()));
                crashStream.write(encodeLat(DEFAULT_LAT));
                crashStream.write(encodeLng(DEFAULT_LNG));
                short rpm = (short)(100+i);
                crashStream.write(convertShortToBytes(rpm));
                crashStream.write(convertByteToBytes((byte) 60));  //gps speed
                crashStream.write(convertByteToBytes((byte) 61));  //obd speed
    
                if (crashTs == ts.intValue())
                    crashStream.write(convertByteToBytes((byte) 4));  //impact pt.
                else
                    crashStream.write(convertByteToBytes((byte) 3));  //impact pt.
                
                ts.add(1);
            }
            crashStream.flush();
            crashStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crashStream.toByteArray();
    }
    
    public static byte[] encodeLng(double lng)
    {
        return convertIntToBytes((int)(lng  / 360.0 * 4294967295L));      

    }

    public static byte[] encodeLat(double lat)
    {
        return convertIntToBytes((int)((90.0 - lat ) / 180.0 * 4294967295L));      
    }

    private static byte[] convertIntToBytes(int intData)
    {
        byte[] dataBytes = new byte[4];
        dataBytes[3] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[2] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[1] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[0] = (byte) (intData & 0x000000FF);
        return dataBytes;
    }

    private static byte[] convertShortToBytes(short data)
    {
        byte[] dataBytes = new byte[2];
        dataBytes[0] = (byte)(data & 0xff);
        dataBytes[1] = (byte)((data >> 8) & 0xff);
        return dataBytes;
    }

    private static byte[] convertByteToBytes(byte data)
    {
        byte[] dataBytes = new byte[1];
        dataBytes[0] = (byte)(data);
        return dataBytes;
    }
    
    private static int read(byte[] data, int startPos, int length)
    {
        int intVal = 0x00000000;
        for (int i = 0; i < length; i++)
        {
            intVal = intVal << 8;
            intVal = intVal | (data[startPos + i] & 0x000000FF);
        }
        return intVal;
    }

    
}
