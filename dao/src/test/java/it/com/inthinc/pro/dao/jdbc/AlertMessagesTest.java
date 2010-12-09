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
import it.util.MCMSimulator;

import java.io.InputStream;
import java.util.ArrayList;
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
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AlertMessageJDBCDAO;
import com.inthinc.pro.map.GeonamesAddressLookup;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.AlertMessageType;
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
import com.inthinc.pro.model.event.ZoneArrivalEvent;
import com.inthinc.pro.model.event.ZoneDepartureEvent;

@Ignore
public class AlertMessagesTest extends BaseJDBCTest{
    private static final Logger logger = Logger.getLogger(AlertMessagesTest.class);
    private static SiloService siloService;
    private static MCMSimulator mcmSim;
    private static final String XML_DATA_FILE = "AlertTest.xml";
    private static String mapServerURL;
    private static final int DRIVERS = 1;
    private static final int VEHICLES = 2;
    private static final int VEHICLE_TYPES = 3;
    private static final int GROUPS = 4;
    private static final int CONTACT_INFO = 5;
    private static final int ANY_TIME = 6;
    private static AlertMessageJDBCDAO alertMessageDAO;
    private static EventHessianDAO eventDAO;
    private static DriverHessianDAO driverDAO;
    private static GroupHessianDAO groupDAO;
    private static PersonHessianDAO personDAO;
    private static VehicleHessianDAO vehicleDAO;
    private static ZoneHessianDAO zoneHessianDAO;
    private static GeonamesAddressLookup addressLookup;
    
    private static RedFlagAlertHessianDAO zoneAlertHessianDAO;
    private static RedFlagAlertHessianDAO redFlagAlertHessianDAO;
    private static List<RedFlagAlert> zoneAlerts;
    private static List<RedFlagAlert> redFlagAlerts;
    private static List<RedFlagAlert> originalZoneAlerts;
    private static List<RedFlagAlert> originalRedFlagAlerts;
    private static ITData itData;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        mapServerURL = config.get(IntegrationConfig.MAP_SERVER_URL).toString();
        siloService = new SiloServiceCreator(host, port).getService();
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));
        
        initDAOs();
        initApp();
        
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestData(stream, siloService, true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }
    	zoneAlerts = zoneAlertHessianDAO.getRedFlagAlerts(itData.account.getAcctID());
        originalZoneAlerts = zoneAlertHessianDAO.getRedFlagAlerts(itData.account.getAcctID());
    	
    	redFlagAlerts = redFlagAlertHessianDAO.getRedFlagAlerts(itData.account.getAcctID());
        originalRedFlagAlerts = redFlagAlertHessianDAO.getRedFlagAlerts(itData.account.getAcctID());
    }

    private static void initApp() {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
//        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
//        mapping.setDeviceDAO(deviceDAO);
//        mapping.init();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // save original settings
    	for (RedFlagAlert zoneAlert : originalZoneAlerts) {
    	    zoneAlertHessianDAO.update(zoneAlert);
    	}
    	for (RedFlagAlert redFlagAlert : originalRedFlagAlerts) {
    	    redFlagAlertHessianDAO.update(redFlagAlert);
    	}
    }

    @Ignore
    @Test
    public void zoneAlerts() {
//    	for (RedFlagAlert zoneAlert : zoneAlerts) {
//    		EventType eventType = getEventType(zoneAlert);
//	    	Integer zoneID = itData.zone.getZoneID();
//	        String IMEI = itData.teamGroupData.get(ITData.GOOD).device.getImei();
//	
//	        boolean anyAlertsFound = false;
//	        // generate zone arrival/departure event
//	        if (!genZoneEvent(IMEI, zoneID, eventType))
//	            fail("Unable to generate zone arrival event");
//	        if (pollForMessages("Zone Alert Groups Set"))
//	        	anyAlertsFound = true;
	        
//	        modZoneAlertPref(DRIVERS, zoneAlert);
//	        if (!genZoneEvent(IMEI, zoneID, eventType))
//	            fail("Unable to generate zone arrival event");
//	        if (pollForMessages("Zone Alert Drivers Set"))
//	        	anyAlertsFound = true;
//	        modZoneAlertPref(VEHICLES, zoneAlert);
//	        if (!genZoneEvent(IMEI, zoneID, eventType))
//	            fail("Unable to generate zone arrival event");
//	        if (pollForMessages("Zone Alert Vehicles Set"))
//	        	anyAlertsFound = true;
//	        modZoneAlertPref(VEHICLE_TYPES, zoneAlert);
//	        if (!genZoneEvent(IMEI, zoneID, eventType))
//	            fail("Unable to generate zone arrival event");
//	        if (pollForMessages("Zone Alert Vehicle Types Set"))
//	        	anyAlertsFound = true;
//	        modZoneAlertPref(CONTACT_INFO, zoneAlert);
//	        if (!genZoneEvent(IMEI, zoneID, eventType))
//	            fail("Unable to generate zone arrival event");
//	        if (pollForMessages("Zone Alert Contact Info Set"))
//	        	anyAlertsFound = true;
//	        modZoneAlertPref(ANY_TIME, zoneAlert);
//	        if (!genZoneEvent(IMEI, zoneID, eventType))
//	            fail("Unable to generate zone arrival event");
//	        if (pollForMessages("Zone Alert ANY TIME (0,0) Set"))
//	        	anyAlertsFound = true;
//	        assertTrue("No Zone Alerts were generated", anyAlertsFound);
//    	}
    }

    @Test
    public void alertsUnknownDriver() {
        boolean anyAlertsFound = false;
    	for (RedFlagAlert zoneAlert : zoneAlerts) {
    		List<EventType> eventTypes = getZoneEventTypes(zoneAlert);
	    	Integer zoneID = itData.zone.getZoneID();
	        String noDriverDeviceIMEI = itData.noDriverDevice.getImei();
	        
	        // generate zone arrival/departure event
	        if (!genZoneEvent(noDriverDeviceIMEI, zoneID, eventTypes.get(0)))
	            fail("Unable to generate zone arrival event");
	        if (pollForMessages("Zone Alert Groups Set"))
	        	anyAlertsFound = true;
	
	        
    	}
    	for (RedFlagAlert redFlagAlert : redFlagAlerts) {
    		List<EventType> eventTypes = getEventTypes(redFlagAlert);
	        String noDriverDeviceIMEI = itData.noDriverDevice.getImei();
	        
	        if (!genEvent(noDriverDeviceIMEI, eventTypes.get(0)))
	            fail("Unable to generate no driver event");
	        if (pollForMessages("Red Flag Alert Groups Set"))
	        	anyAlertsFound = true;
    	}
        assertTrue("No Alerts were generated for No Driver", anyAlertsFound);
    }
    
    @Test
    public void redFlagAlerts() {
    	GroupData groupData = itData.teamGroupData.get(ITData.GOOD); 
    	for (RedFlagAlert redFlagAlert : redFlagAlerts) {
            boolean anyAlertsFound = false;
	        modRedFlagAlertPref(GROUPS, redFlagAlert);
	        List<EventType> eventTypes = getEventTypes(redFlagAlert);
	        String IMEI = groupData.device.getImei();
	        if (eventTypes.get(0).equals(EventType.NO_DRIVER))
	        	IMEI = itData.noDriverDevice.getImei();

	        
	        if (!genEvent(IMEI, eventTypes.get(0)))
	            fail("Unable to generate event of type " + eventTypes.get(0));
	        if (pollForMessages("Red Flag Alert Groups Set"))
	        	anyAlertsFound = true;
	        modRedFlagAlertPref(DRIVERS, redFlagAlert);
	        if (!genEvent(IMEI, eventTypes.get(0)))
	            fail("Unable to generate event of type " + eventTypes.get(0));
	        if (pollForMessages("Red Flag Alert Drivers Set"))
	        	anyAlertsFound = true;

	        modRedFlagAlertPref(VEHICLES, redFlagAlert);
	        if (!genEvent(IMEI, eventTypes.get(0)))
	            fail("Unable to generate event of type " + eventTypes.get(0));
	        if (pollForMessages("Red Flag Alert Vehicles Set"))
	        	anyAlertsFound = true;

	        modRedFlagAlertPref(VEHICLE_TYPES, redFlagAlert);
	        if (!genEvent(IMEI, eventTypes.get(0)))
	            fail("Unable to generate event of type " + eventTypes.get(0));
	        if (pollForMessages("Red Flag Alert Vehicle Types Set"))
	        	anyAlertsFound = true;

	        modRedFlagAlertPref(ANY_TIME, redFlagAlert);
	        if (!genEvent(IMEI, eventTypes.get(0)))
	            fail("Unable to generate event of type " + eventTypes.get(0));
	        if (pollForMessages("Red Flag Alert Any Time Info Set"))
	        	anyAlertsFound = true;

	        modRedFlagAlertPref(CONTACT_INFO, redFlagAlert);
	        if (!genEvent(IMEI, eventTypes.get(0)))
	            fail("Unable to generate event of type " + eventTypes.get(0));
	        if (pollForMessages("Red Flag Alert Contact Info Set"))
	        	anyAlertsFound = true;
	        assertTrue("No Red Flag Alerts were generated for eventType " + eventTypes.get(0), anyAlertsFound);
//System.out.println("anyAlertsFound: " + anyAlertsFound);	        

    	}
    }

    @Test
    public void escalationPhoneTest()
    {
        List<AlertMessageBuilder> msgList = alertMessageDAO.getMessageBuilders(AlertMessageDeliveryType.PHONE);
        assertTrue(msgList!=null);
    }
    
    private List<EventType> getZoneEventTypes(RedFlagAlert zoneAlert) {
        List<EventType> eventTypes = new ArrayList<EventType>();
    	if (zoneAlert.getTypes().contains(AlertMessageType.ALERT_TYPE_ENTER_ZONE)){
    	    eventTypes.add(EventType.ZONES_ARRIVAL);
    	}
        if (zoneAlert.getTypes().contains(AlertMessageType.ALERT_TYPE_EXIT_ZONE)){
            eventTypes.add(EventType.ZONES_DEPARTURE);
        }
    	return eventTypes;
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

    private static void modZoneAlertPref(int type, RedFlagAlert zoneAlert) {
    	// original settings are any group and driver,vehicle, vehicle types lists are null
    	GroupData groupData = itData.teamGroupData.get(ITData.GOOD);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupData.group.getGroupID());
        List<Integer> notifyPersonIDs = new ArrayList<Integer>();
        notifyPersonIDs.add(itData.fleetUser.getPersonID());
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
        zoneAlert.setStartTOD(0);
        zoneAlert.setStopTOD(1439);
        List<Integer> emptyList = new ArrayList<Integer>();
        List<VehicleType> emptyVTList = new ArrayList<VehicleType>();
        switch (type) {
            case DRIVERS:
                List<Integer> driverIDs = new ArrayList<Integer>();
                driverIDs.add(groupData.driver.getDriverID());
                zoneAlert.setDriverIDs(driverIDs);
                zoneAlert.setGroupIDs(emptyList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setEmailTo(emailList);
                break;
            case VEHICLES:
                List<Integer> vehicleIDs = new ArrayList<Integer>();
                vehicleIDs.add(groupData.vehicle.getVehicleID());
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(emptyList);
                zoneAlert.setVehicleIDs(vehicleIDs);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setEmailTo(emailList);
                break;
            case VEHICLE_TYPES:
                List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
                vehicleTypes.add(groupData.vehicle.getVtype());
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(emptyList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(vehicleTypes);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setEmailTo(emailList);
                break;
            case GROUPS:
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(groupIDList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setEmailTo(emailList);
                break;
            case CONTACT_INFO:
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(groupIDList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setEmailTo(new ArrayList<String>());
                break;
            case ANY_TIME:
                zoneAlert.setDriverIDs(null);
                zoneAlert.setGroupIDs(groupIDList);
                zoneAlert.setVehicleIDs(null);
                zoneAlert.setVehicleTypes(null);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setStartTOD(0);
                zoneAlert.setStopTOD(0);
                zoneAlert.setEmailTo(emailList);
                break;
        }
        RedFlagAlertHessianDAO zoneAlertDAO = new RedFlagAlertHessianDAO();
        zoneAlertDAO.setSiloService(siloService);
        zoneAlertDAO.update(zoneAlert);
    }

    private static void modRedFlagAlertPref(int type, RedFlagAlert redFlagAlert) {
    	GroupData groupData = itData.teamGroupData.get(ITData.GOOD);

        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupData.group.getGroupID());
        List<Integer> notifyPersonIDs = new ArrayList<Integer>();
        notifyPersonIDs.add(itData.fleetUser.getPersonID());
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
        List<Integer> emptyList = new ArrayList<Integer>();
        List<VehicleType> emptyVTList = new ArrayList<VehicleType>();
        
        

        redFlagAlert.setStartTOD(0);
        redFlagAlert.setStopTOD(1439);
        switch (type) {
            case DRIVERS:
                List<Integer> driverIDs = new ArrayList<Integer>();
                driverIDs.add(groupData.driver.getDriverID());
                redFlagAlert.setDriverIDs(driverIDs);
                redFlagAlert.setGroupIDs(emptyList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(emailList);
                break;
            case VEHICLES:
                List<Integer> vehicleIDs = new ArrayList<Integer>();
                vehicleIDs.add(groupData.vehicle.getVehicleID());
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(emptyList);
                redFlagAlert.setVehicleIDs(vehicleIDs);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(emailList);
                break;
            case VEHICLE_TYPES:
                List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
                vehicleTypes.add(groupData.vehicle.getVtype());
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(emptyList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(vehicleTypes);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(emailList);
                break;
            case GROUPS:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(groupIDList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(emailList);
                break;
            case CONTACT_INFO:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(groupIDList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(new ArrayList<String>());
                break;
            case ANY_TIME:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(groupIDList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(emailList);
                redFlagAlert.setStartTOD(0);
                redFlagAlert.setStopTOD(1439);
                break;
        }
        RedFlagAlertHessianDAO redFlagAlertDAO = new RedFlagAlertHessianDAO();
        redFlagAlertDAO.setSiloService(siloService);
        redFlagAlertDAO.update(redFlagAlert);
    }

    private boolean genZoneEvent(String imei, Integer zoneID, EventType eventType) {
        System.out.println("IMEI: " + imei);
        System.out.println("ZoneID: " + zoneID);
        Event event = null;
        if (eventType.equals(EventType.ZONES_ARRIVAL))
        	event = new ZoneArrivalEvent(0l, 0, NoteType.WSZONES_ARRIVAL_EX, new Date(), 60, 1000, new Double(40.704246f), new Double(-111.948613f),
                zoneID);
        else 
        	event = new ZoneDepartureEvent(0l, 0, NoteType.WSZONES_DEPARTURE_EX, new Date(), 60, 1000, new Double(40.704246f), new Double(-111.948613f),
                zoneID);
        return genEvent(event, imei);
    }

    private boolean genEvent(String imei, EventType eventType) {
    	Event event = null;
    	
/*
 * def iComputeSeverity(deltax, deltay, deltaz):
    "Compute the severity from the 3 delta values"
    delta = max(abs(deltax), abs(deltay), abs(deltaz)) / 10
    if delta > 20:
        delta = 20
    if delta > 7:
        severity = ((delta-7) / 3) + 1
    else:
        severity = 1
    return severity    	
 */
//System.out.println("genEvent: " + eventType);    	
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
    	
    	return genEvent(event, imei);
    }

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

    private static void initDAOs()
    {
        alertMessageDAO = new AlertMessageJDBCDAO();
        ((AlertMessageJDBCDAO)alertMessageDAO).setDataSource(new ITDataSource().getRealDataSource());
        eventDAO = new EventHessianDAO();
        driverDAO = new DriverHessianDAO();
        groupDAO = new GroupHessianDAO();
        personDAO = new PersonHessianDAO();
        vehicleDAO = new VehicleHessianDAO();
        zoneHessianDAO = new ZoneHessianDAO();
        
        zoneAlertHessianDAO = new RedFlagAlertHessianDAO();
        redFlagAlertHessianDAO = new RedFlagAlertHessianDAO();
        
        addressLookup = new GeonamesAddressLookup();
        addressLookup.setMapServerURLString(mapServerURL);
        driverDAO.setSiloService(siloService);
        groupDAO.setSiloService(siloService);
        eventDAO.setSiloService(siloService);
        personDAO.setSiloService(siloService);
        vehicleDAO.setSiloService(siloService);
        zoneHessianDAO.setSiloService(siloService);
        zoneAlertHessianDAO.setSiloService(siloService);
        redFlagAlertHessianDAO.setSiloService(siloService);
    
        alertMessageDAO.setDriverDAO(driverDAO);
        alertMessageDAO.setVehicleDAO(vehicleDAO);
        alertMessageDAO.setEventDAO(eventDAO);
        alertMessageDAO.setPersonDAO(personDAO);
        alertMessageDAO.setZoneDAO(zoneHessianDAO);
        alertMessageDAO.setAddressLookup(addressLookup);

    }
    
    private boolean pollForMessages(String description) {
        int secondsToWait = 10;
        for (int i = 0; i < secondsToWait; i++) {
            List<AlertMessageBuilder> msgList = alertMessageDAO.getMessageBuilders(AlertMessageDeliveryType.EMAIL);
            if (msgList.size() == 0) {
                if (i == (secondsToWait-1)) {
                	System.out.println();
                	logger.error(description + " getMessages failed even after waiting " + secondsToWait + " sec -- most likely the scheduler picked them up");
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
                System.out.println(".");
                // check it
                AlertMessageBuilder msg = msgList.get(0);
                assertNotNull(description, msg);
                assertNotNull(description, msg.getAddress());
                assertNotNull(description, msg.getParamterList());
                for(AlertMessageBuilder amb:msgList)
                {
                    alertMessageDAO.acknowledgeMessage(amb.getMessageID());
                }
//                System.out.println(msg.getAlertMessageType() + " " + description + "address: " + msg.getAddress() + " msg: " + msg.getParamterList() + " ");
                // logger.debug(description + "address: " + msg.getAddress() + " msg: " + msg.getParamterList());
                return true;
            }
        }
        
        return false;
    }
}
