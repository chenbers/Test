package it.com.inthinc.pro.dao.jdbc;

// The tests in this file  can fail sporadically when the scheduler is running on the same
// server that is is hitting (usually dev).  If this becomes a problem, we can mark them as Ignore.  
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.util.EventGenerator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RedFlagAlertHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AlertMessageJDBCDAO;
import com.inthinc.pro.map.GeonamesAddressLookup;
import com.inthinc.pro.model.AlertEscalationItem;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.FullEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.notegen.MCMSimulator;
import com.inthinc.pro.notegen.NoteGenerator;
import com.inthinc.pro.notegen.TiwiProNoteSender;
import com.inthinc.pro.notegen.WSNoteSender;
@Ignore
public class VoiceEscalationTest extends BaseJDBCTest{
    
    private static final Logger logger = Logger.getLogger(AlertMessagesTest.class);
    private static SiloService siloService;
    private static MCMSimulator mcmSim;
    private static final String XML_DATA_FILE = "AlertTest.xml";
    private static String mapServerURL;
//    private static final int DRIVERS = 1;
//    private static final int VEHICLES = 2;
//    private static final int VEHICLE_TYPES = 3;
    private static final int GROUPS = 4;
//    private static final int CONTACT_INFO = 5;
//    private static final int ANY_TIME = 6;
    private static AlertMessageJDBCDAO alertMessageDAO;
    private static EventHessianDAO eventDAO;
    private static DriverHessianDAO driverDAO;
    private static GroupHessianDAO groupDAO;
    private static PersonHessianDAO personDAO;
    private static VehicleHessianDAO vehicleDAO;
    private static GeonamesAddressLookup addressLookup;
    
    private static RedFlagAlertHessianDAO redFlagAlertHessianDAO;
    private static List<RedFlagAlert> redFlagAlerts;
    private static List<RedFlagAlert> originalAlerts;
    private static ITData itData;
    
    private static int callDelay = 1;
    private static int tryTimeLimit = 0;
    private static int tryLimit = 1;
    
    private static NoteGenerator noteGenerator;

    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        mapServerURL = config.get(IntegrationConfig.MAP_SERVER_URL).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));
        noteGenerator = new NoteGenerator();
        WSNoteSender wsNoteSender = new WSNoteSender();
        wsNoteSender.setUrl(config.get(IntegrationConfig.MINA_HOST).toString());
        wsNoteSender.setPort(Integer.valueOf(config.get(IntegrationConfig.MINA_PORT).toString()));
        noteGenerator.setWsNoteSender(wsNoteSender);
        
        TiwiProNoteSender tiwiProNoteSender = new TiwiProNoteSender();
        tiwiProNoteSender.setMcmSimulator(mcmSim);
        noteGenerator.setTiwiProNoteSender(tiwiProNoteSender);
        
        initDAOs();
        initApp();
        
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestData(stream, siloService, true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }
        initPersonPhones();
        
System.out.println("account id " + itData.account.getAccountID());        
        List<RedFlagAlert> alerts = redFlagAlertHessianDAO.getRedFlagAlerts(itData.account.getAccountID());
        originalAlerts = redFlagAlertHessianDAO.getRedFlagAlerts(itData.account.getAccountID());
        redFlagAlerts = new ArrayList<RedFlagAlert>();
        
        for (RedFlagAlert alert : alerts) {
            if (!alert.getTypes().contains(AlertMessageType.ALERT_TYPE_ENTER_ZONE) && !alert.getTypes().contains(AlertMessageType.ALERT_TYPE_EXIT_ZONE)) {
               redFlagAlerts.add(alert);
            }
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
    private static void initPersonPhones(){
        
        setPersonPriPhone(itData.districtUser.getPersonID(), "1111111111");
        setPersonPriPhone(itData.fleetUser.getPersonID(), "2222222222");
    }
    private static void setPersonPriPhone(int personID, String priPhone){
        
       Person person = new Person();
       person.setPersonID(personID);
       person.setPriPhone(priPhone);
       
       personDAO.update(person); 
    }
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        for (RedFlagAlert alert : originalAlerts) {
            redFlagAlertHessianDAO.update(alert);
        }
        resetPersonPhones();
    }
    private static void resetPersonPhones(){
        setPersonPriPhone(itData.districtUser.getPersonID(), "5555555555");
        setPersonPriPhone(itData.fleetUser.getPersonID(), "5555555555");
    }
    private static void initDAOs()
    {
        alertMessageDAO = new AlertMessageJDBCDAO();
        ((AlertMessageJDBCDAO)alertMessageDAO).setDataSource(new ITDataSource().getRealDataSource());
        eventDAO = new EventHessianDAO();
        driverDAO = new DriverHessianDAO();
        groupDAO = new GroupHessianDAO();
        personDAO = new PersonHessianDAO();
        vehicleDAO = new VehicleHessianDAO();
        
        redFlagAlertHessianDAO = new RedFlagAlertHessianDAO();
        
        driverDAO.setSiloService(siloService);
        groupDAO.setSiloService(siloService);
        eventDAO.setSiloService(siloService);
        personDAO.setSiloService(siloService);
        vehicleDAO.setSiloService(siloService);
        redFlagAlertHessianDAO.setSiloService(siloService);
    
        addressLookup = new GeonamesAddressLookup();
        addressLookup.setMapServerURLString(mapServerURL);
        
        alertMessageDAO.setDriverDAO(driverDAO);
        alertMessageDAO.setVehicleDAO(vehicleDAO);
        alertMessageDAO.setEventDAO(eventDAO);
        alertMessageDAO.setPersonDAO(personDAO);
        alertMessageDAO.setAddressLookup(addressLookup);

    }
    @Test
    public void redFlagAlerts() {
        GroupData groupData = itData.teamGroupData.get(ITData.GOOD); 
//        for (RedFlagAlert redFlagAlert : redFlagAlerts) {
        RedFlagAlert redFlagAlert = redFlagAlerts.get(0);
            boolean anyAlertsFound = false;
            modRedFlagAlertPref(GROUPS, redFlagAlert);
            List<EventType> eventTypes = getEventTypes(redFlagAlert);
            Device device = groupData.device;
            if (eventTypes.get(0).equals(EventType.NO_DRIVER))
                device = itData.noDriverDevice;
            
            genEvent(device, eventTypes.get(0));
            if (pollForMessages("Voice Escalation Test"))
                anyAlertsFound = true;
//            assertTrue("No Red Flag Alerts were generated for eventType " + eventTypes.get(0), anyAlertsFound);

//        }
    }

    private List<EventType> getEventTypes(RedFlagAlert redFlagAlert) {
        List<EventType> eventTypes = new ArrayList<EventType>();
        List<AlertMessageType>alertTypes = redFlagAlert.getTypes();
        
        for(AlertMessageType amt:alertTypes){
            switch (amt)
            {
                case ALERT_TYPE_SEATBELT:
                    eventTypes.add(EventType.SEATBELT);
                case ALERT_TYPE_HARD_ACCEL:    
                    eventTypes.add(EventType.HARD_ACCEL);
                case ALERT_TYPE_HARD_BRAKE:    
                    eventTypes.add(EventType.HARD_BRAKE);
                case ALERT_TYPE_HARD_BUMP:    
                    eventTypes.add(EventType.HARD_VERT);
                case ALERT_TYPE_HARD_TURN:    
                    eventTypes.add(EventType.HARD_TURN);
                case ALERT_TYPE_CRASH:    
                    eventTypes.add(EventType.CRASH);
                case ALERT_TYPE_TAMPERING:    
                    eventTypes.add(EventType.TAMPERING);
                case ALERT_TYPE_LOW_BATTERY:    
                    eventTypes.add(EventType.LOW_BATTERY);
                case ALERT_TYPE_NO_DRIVER:    
                    eventTypes.add(EventType.NO_DRIVER);                
                default:
                    eventTypes.add(EventType.SPEEDING);
            }
        }
        return eventTypes;
    }


    private static void modRedFlagAlertPref(int type, RedFlagAlert redFlagAlert) {
        GroupData groupData = itData.teamGroupData.get(ITData.GOOD);

        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupData.group.getGroupID());
//        List<Integer> notifyPersonIDs = new ArrayList<Integer>();
        List<Integer> emptyList = new ArrayList<Integer>();
        List<VehicleType> emptyVTList = new ArrayList<VehicleType>();
        List<AlertEscalationItem> escalationList = new ArrayList<AlertEscalationItem>();
        escalationList.add(new AlertEscalationItem(itData.districtUser.getPersonID(), 2));
        escalationList.add(new AlertEscalationItem(itData.fleetUser.getPersonID(), 1));
        escalationList.add(new AlertEscalationItem(itData.districtUser.getPersonID(), 0));
        
        redFlagAlert.setStartTOD(0);
        redFlagAlert.setStopTOD(1439);
        redFlagAlert.setDriverIDs(emptyList);
        redFlagAlert.setGroupIDs(groupIDList);
        redFlagAlert.setVehicleIDs(emptyList);
        redFlagAlert.setVehicleTypes(emptyVTList);
        redFlagAlert.setNotifyPersonIDs(emptyList);
        redFlagAlert.setEscalationList(escalationList);
        redFlagAlert.setMaxEscalationTries(tryLimit);
        redFlagAlert.setMaxEscalationTryTime(null);
        redFlagAlert.setEscalationTimeBetweenRetries(callDelay);
        RedFlagAlertHessianDAO redFlagAlertDAO = new RedFlagAlertHessianDAO();
        redFlagAlertDAO.setSiloService(siloService);
        redFlagAlertDAO.update(redFlagAlert);
    }

    private void genEvent(Device device, EventType eventType) {
        Event event = null;
        
        System.out.println("genEvent: " + eventType);       
        if (eventType.equals(EventType.SEATBELT) )
                event = new SeatBeltEvent(0l, 0, NoteType.SEATBELT, new Date(), 60, 1000, 
                        new Double(40.704246f), new Double(-111.948613f), 80, 100, 20);
        else if (eventType.equals(EventType.HARD_VERT) )
            event = new AggressiveDrivingEvent(0l, 0, NoteType.NOTEEVENT, new Date(), 60, 1000, 
                    new Double(40.704246f), new Double(-111.948613f), 80, 11, -22, -33, 30);
        else if (eventType.equals(EventType.HARD_ACCEL) )
            event = new AggressiveDrivingEvent(0l, 0, NoteType.NOTEEVENT, new Date(), 60, 1000, 
                    new Double(40.704246f), new Double(-111.948613f), 80, 100, -20, 0, 40);
        else if (eventType.equals(EventType.HARD_BRAKE) )
            event = new AggressiveDrivingEvent(0l, 0, NoteType.NOTEEVENT, new Date(), 60, 1000, 
                    new Double(40.704246f), new Double(-111.948613f), 80, -25, 22, -13, 50);
        else if (eventType.equals(EventType.HARD_TURN) )
            event = new AggressiveDrivingEvent(0l, 0, NoteType.NOTEEVENT, new Date(), 60, 1000, 
                    new Double(40.704246f), new Double(-111.948613f), 80, 24, -22, -21, 60);
        else if (eventType.equals(EventType.CRASH) )
            event = new FullEvent(0l, 0, NoteType.FULLEVENT, new Date(), 60, 1000, 
                    new Double(40.704246f), new Double(-111.948613f), 80, 24, -22, -21);
        else if (eventType.equals(EventType.TAMPERING) )
            event = new Event(0l, 0, NoteType.UNPLUGGED, new Date(), 60, 1000, 
                    new Double(40.704246f), new Double(-111.948613f));
        else if (eventType.equals(EventType.LOW_BATTERY) )
            event = new Event(0l, 0, NoteType.LOW_BATTERY, new Date(), 60, 1000, 
                    new Double(40.704246f), new Double(-111.948613f));
        else if (eventType.equals(EventType.NO_DRIVER) )
            event = new Event(0l, 0, NoteType.NO_DRIVER, new Date(), 60, 1000, 
                    new Double(40.704246f), new Double(-111.948613f));
        else if (eventType.equals(EventType.SPEEDING) )
            event = new SpeedingEvent(0l, 0, NoteType.SPEEDING_EX3, new Date(), 100, 1000, 
                    new Double(40.704246f), new Double(-111.948613f), 100, 80, 70, 100, 100);
        
        genEvent(event, device);
        
    }
    private void genEvent(Event event, Device device) {
        try {
            noteGenerator.genEvent(event, device);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail("Generate Note failed for device: " + device.getImei() + " noteType" + event.getType());
        }
    }
/*
    private boolean genEvent(Event event, String imei) {
        List<byte[]> noteList = new ArrayList<byte[]>();

        byte[] eventBytes = EventGenerator.createDataBytesFromEvent(event);
        noteList.add(eventBytes);
        boolean errorFound = false;
        int retryCnt = 0;
        while (!errorFound) {
            try {
                mcmSim.note(imei, noteList);
                break;
            }
            catch (ProxyException ex) {
                if (ex.getErrorCode() != 414) {
                    System.out.print("RETRY EVENT GEN because: " + ex.getErrorCode() + "");
                    errorFound = true;
                }
                else {
                    if (retryCnt == 300) {
                        System.out.println("Retries failed after 5 min.");
                        errorFound = true;
                    }
                    else {
                        if (retryCnt == 0)
                            System.out.println("waiting for IMEI to show up in central server");
                        try {
                            Thread.sleep(1000l);
                            retryCnt++;
                        }
                        catch (InterruptedException e) {
                            errorFound = true;
                            e.printStackTrace();
                        }
                        System.out.print(".");
                        if (retryCnt % 25 == 0)
                            System.out.println();
                    }
                }
            }
            catch (RemoteServerException re) {
                if (re.getErrorCode() != 302 ) {
                    errorFound = true;
                }
                
            }
            catch (Exception e) {
                e.printStackTrace();
                errorFound = true;
            }
        }
        return !errorFound;
    }
*/
    private boolean pollForMessages(String description) {
        //Loop through until nothing comes back then swap to email
        List<String> addresses = new ArrayList<String>();
        addresses.add("1111111111");
        addresses.add("2222222222");
        addresses.add("PersonDivisionemail85828888@example.com");
        AlertMessageDeliveryType deliveryType = AlertMessageDeliveryType.PHONE;
        EscalationState escalationState = new EscalationState(addresses);
        
        while(!escalationState.isDone()){
            
//            int secondsToWait = 10;
//            for (int i = 0; i < secondsToWait; i++) {
                escalationState.progressInTime();
                if(escalationState.expectingEmail()){
                    System.out.println("Switching to last resort email");
                    deliveryType = AlertMessageDeliveryType.EMAIL;
                }
                List<AlertMessageBuilder> msgList = alertMessageDAO.getMessageBuilders(deliveryType);
                if (msgList.size() == 0) {
//                    if (i == (secondsToWait-1)) {
                        System.out.println();
                        logger.error(description + " getMessages failed even after waiting -- most likely the scheduler picked them up");
//                    }
//                    try {
//                        Thread.sleep(1000l);
//                    }
//                    catch (InterruptedException e) {
//                        e.printStackTrace();
//                        break;
//                    }
//                    System.out.print(".");
                }
                else {
                    System.out.println(".");
                    // check it
                    AlertMessageBuilder msg = msgList.get(0);
                    assertNotNull(description, msg);
                    assertNotNull(description, msg.getAddress());
                    assertNotNull(description, msg.getParamterList());
                    System.out.println("expected address "+escalationState.getExpectedAddress());
                    System.out.println(msg.getAlertMessageType() + " " + description + "address: " + msg.getAddress() + " msg: " + msg.getParamterList() + " ");
                    assertTrue("not expected address",escalationState.getExpectedAddress().equals(msg.getAddress()));
                    if(AlertMessageDeliveryType.EMAIL.equals(deliveryType)){
                        alertMessageDAO.acknowledgeMessage(msg.getMessageID());
                    }
//                    return true;
                }
            }
//        }
        return true;
    }
    
    private class EscalationState {
        
        List<String> addresses = new ArrayList<String>();
        
        Date lastTime;
        int ordinal;
        int currentTry;
        int currentTryTime;
        boolean done;
        
        private EscalationState(List<String> addresses) {
            super();
            this.addresses = addresses;
            this.ordinal = addresses.size()-1;
            lastTime = new Date();
            done = false;
        }
        private void progressInTime(){
           
          try {
              Thread.sleep(2000l);
          }
          catch (InterruptedException e) {
              e.printStackTrace();
              return;
          }
          System.out.print(".");

           Date now = new Date();
           Calendar cal = Calendar.getInstance();
           cal.setTime(now);
           long ms = cal.getTimeInMillis();
           cal.setTime(lastTime); 
           long interval = (ms-cal.getTimeInMillis())/1000;
           
           if (interval >= callDelay){
               System.out.println("EscalationState: next interval");
               if (ordinal == 0){
                   done = true;
                   System.out.println("EscalationState: done");
                   return;
               }
               if(tryLimit != 0){
                   currentTry++;
                   
                   if (currentTry > tryLimit){
                       ordinal--; //expect the next person
                       currentTry = 0;
                       System.out.println("EscalationState: next person from count");
                   }
               }
               else{ //tryTimeLimit must be > 0
                   currentTryTime+=interval;
                   if(currentTryTime >= tryTimeLimit){
                       ordinal--;
                       currentTryTime = 0;
                       System.out.println("EscalationState: next person from time limit");
                   }
               }
           }
        }
        private String getExpectedAddress(){
           return addresses.get(addresses.size()-ordinal-1);
        }
        private boolean isDone(){
            return done;
        }
        private boolean expectingEmail(){
            return ordinal==0;
        }
    }
}
