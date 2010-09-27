package it.com.inthinc.pro.dao;

// The tests in this file  can fail sporadically when the scheduler is running on the same
// server that is is hitting (usually dev).  If this becomes a problem, we can mark them as Ignore.  
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
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

import com.inthinc.pro.dao.hessian.AlertMessageHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RedFlagAlertHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneAlertHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.map.GeonamesAddressLookup;
import com.inthinc.pro.model.AggressiveDrivingEvent;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.EventType;
import com.inthinc.pro.model.FullEvent;
import com.inthinc.pro.model.LowBatteryEvent;
import com.inthinc.pro.model.NoDriverEvent;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.SeatBeltEvent;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.model.TamperingEvent;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.model.ZoneArrivalEvent;
import com.inthinc.pro.model.ZoneDepartureEvent;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.States;

public class AlertMessagesTest {
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
    private static AlertMessageHessianDAO alertMessageDAO;
    private static EventHessianDAO eventDAO;
    private static DriverHessianDAO driverDAO;
    private static GroupHessianDAO groupDAO;
    private static PersonHessianDAO personDAO;
    private static VehicleHessianDAO vehicleDAO;
    private static ZoneHessianDAO zoneHessianDAO;
    private static GeonamesAddressLookup addressLookup;
    
    private static ZoneAlertHessianDAO zoneAlertHessianDAO;
    private static RedFlagAlertHessianDAO redFlagAlertHessianDAO;
    private static List<ZoneAlert> zoneAlerts;
    private static List<RedFlagAlert> redFlagAlerts;
    private static List<ZoneAlert> originalZoneAlerts;
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
    	zoneAlerts = zoneAlertHessianDAO.getZoneAlerts(itData.account.getAcctID());
        originalZoneAlerts = zoneAlertHessianDAO.getZoneAlerts(itData.account.getAcctID());
    	
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
        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
        mapping.setDeviceDAO(deviceDAO);
        mapping.init();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // save original settings
    	for (ZoneAlert zoneAlert : originalZoneAlerts) {
    	    zoneAlertHessianDAO.update(zoneAlert);
    	}
    	for (RedFlagAlert redFlagAlert : originalRedFlagAlerts) {
    	    redFlagAlertHessianDAO.update(redFlagAlert);
    	}
    }

    @Test
    public void zoneAlerts() {
    	for (ZoneAlert zoneAlert : zoneAlerts) {
    		EventType eventType = getEventType(zoneAlert);
	    	Integer zoneID = itData.zone.getZoneID();
	        String IMEI = itData.teamGroupData.get(ITData.GOOD).device.getImei();
	
	        boolean anyAlertsFound = false;
	        // generate zone arrival/departure event
	        if (!genZoneEvent(IMEI, zoneID, eventType))
	            fail("Unable to generate zone arrival event");
	        if (pollForMessages("Zone Alert Groups Set"))
	        	anyAlertsFound = true;
	        
	        modZoneAlertPref(DRIVERS, zoneAlert);
	        if (!genZoneEvent(IMEI, zoneID, eventType))
	            fail("Unable to generate zone arrival event");
	        if (pollForMessages("Zone Alert Drivers Set"))
	        	anyAlertsFound = true;
	        modZoneAlertPref(VEHICLES, zoneAlert);
	        if (!genZoneEvent(IMEI, zoneID, eventType))
	            fail("Unable to generate zone arrival event");
	        if (pollForMessages("Zone Alert Vehicles Set"))
	        	anyAlertsFound = true;
	        modZoneAlertPref(VEHICLE_TYPES, zoneAlert);
	        if (!genZoneEvent(IMEI, zoneID, eventType))
	            fail("Unable to generate zone arrival event");
	        if (pollForMessages("Zone Alert Vehicle Types Set"))
	        	anyAlertsFound = true;
	        modZoneAlertPref(CONTACT_INFO, zoneAlert);
	        if (!genZoneEvent(IMEI, zoneID, eventType))
	            fail("Unable to generate zone arrival event");
	        if (pollForMessages("Zone Alert Contact Info Set"))
	        	anyAlertsFound = true;
	        modZoneAlertPref(ANY_TIME, zoneAlert);
	        if (!genZoneEvent(IMEI, zoneID, eventType))
	            fail("Unable to generate zone arrival event");
	        if (pollForMessages("Zone Alert ANY TIME (0,0) Set"))
	        	anyAlertsFound = true;
	        assertTrue("No Zone Alerts were generated", anyAlertsFound);
    	}
    }

    @Test
    public void alertsUnknownDriver() {
        boolean anyAlertsFound = false;
    	for (ZoneAlert zoneAlert : zoneAlerts) {
    		EventType eventType = getEventType(zoneAlert);
	    	Integer zoneID = itData.zone.getZoneID();
	        String noDriverDeviceIMEI = itData.noDriverDevice.getImei();
	        
	        // generate zone arrival/departure event
	        if (!genZoneEvent(noDriverDeviceIMEI, zoneID, eventType))
	            fail("Unable to generate zone arrival event");
	        if (pollForMessages("Zone Alert Groups Set"))
	        	anyAlertsFound = true;
	
	        
    	}
    	for (RedFlagAlert redFlagAlert : redFlagAlerts) {
    		EventType eventType = getEventType(redFlagAlert);
	        String noDriverDeviceIMEI = itData.noDriverDevice.getImei();
	        
	        if (!genEvent(noDriverDeviceIMEI, eventType))
	            fail("Unable to generate seatbelt event");
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
    		EventType eventType = getEventType(redFlagAlert);
	        String IMEI = groupData.device.getImei();
	        if (eventType.equals(EventType.NO_DRIVER))
	        	IMEI = itData.noDriverDevice.getImei();

	        
	        if (!genEvent(IMEI, eventType))
	            fail("Unable to generate event of type " + eventType);
	        if (pollForMessages("Red Flag Alert Groups Set"))
	        	anyAlertsFound = true;
	        modRedFlagAlertPref(DRIVERS, redFlagAlert);
	        if (!genEvent(IMEI, eventType))
	            fail("Unable to generate event of type " + eventType);
	        if (pollForMessages("Red Flag Alert Drivers Set"))
	        	anyAlertsFound = true;

	        modRedFlagAlertPref(VEHICLES, redFlagAlert);
	        if (!genEvent(IMEI, eventType))
	            fail("Unable to generate event of type " + eventType);
	        if (pollForMessages("Red Flag Alert Vehicles Set"))
	        	anyAlertsFound = true;

	        modRedFlagAlertPref(VEHICLE_TYPES, redFlagAlert);
	        if (!genEvent(IMEI, eventType))
	            fail("Unable to generate event of type " + eventType);
	        if (pollForMessages("Red Flag Alert Vehicle Types Set"))
	        	anyAlertsFound = true;

	        modRedFlagAlertPref(ANY_TIME, redFlagAlert);
	        if (!genEvent(IMEI, eventType))
	            fail("Unable to generate event of type " + eventType);
	        if (pollForMessages("Red Flag Alert Any Time Info Set"))
	        	anyAlertsFound = true;

	        modRedFlagAlertPref(CONTACT_INFO, redFlagAlert);
	        if (!genEvent(IMEI, eventType))
	            fail("Unable to generate event of type " + eventType);
	        if (pollForMessages("Red Flag Alert Contact Info Set"))
	        	anyAlertsFound = true;
	        assertTrue("No Red Flag Alerts were generated for eventType " + eventType, anyAlertsFound);
//System.out.println("anyAlertsFound: " + anyAlertsFound);	        

    	}
    }

    private EventType getEventType(ZoneAlert zoneAlert) {
    	if (zoneAlert.getArrival())
    		return EventType.ZONES_ARRIVAL;
    	return EventType.ZONES_DEPARTURE;
    }
    
    private EventType getEventType(RedFlagAlert redFlagAlert) {
            switch (redFlagAlert.getType())
            {
                case ALERT_TYPE_SEATBELT:
                    return EventType.SEATBELT;
                case ALERT_TYPE_HARD_ACCELL:    
                    return EventType.HARD_ACCEL;
                case ALERT_TYPE_HARD_BRAKE:    
                    return EventType.HARD_BRAKE;
                case ALERT_TYPE_HARD_BUMP:    
                    return EventType.HARD_VERT;
                case ALERT_TYPE_HARD_TURN:    
                    return EventType.HARD_TURN;
                case ALERT_TYPE_CRASH:    
                    return EventType.CRASH;
                case ALERT_TYPE_TAMPERING:    
                    return EventType.TAMPERING;
                case ALERT_TYPE_LOW_BATTERY:    
                    return EventType.LOW_BATTERY;
                case ALERT_TYPE_NO_DRIVER:    
                    return EventType.NO_DRIVER;                
                default:
                    return EventType.SPEEDING;
            }
    }

    private static void modZoneAlertPref(int type, ZoneAlert zoneAlert) {
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
        ZoneAlertHessianDAO zoneAlertDAO = new ZoneAlertHessianDAO();
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
        	event = new ZoneArrivalEvent(0l, 0, EventMapper.TIWIPRO_EVENT_WSZONES_ARRIVAL_EX, new Date(), 60, 1000, new Double(40.704246f), new Double(-111.948613f),
                zoneID);
        else 
        	event = new ZoneDepartureEvent(0l, 0, EventMapper.TIWIPRO_EVENT_WSZONES_DEPARTURE_EX, new Date(), 60, 1000, new Double(40.704246f), new Double(-111.948613f),
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
    			event = new SeatBeltEvent(0l, 0, EventMapper.TIWIPRO_EVENT_SEATBELT, new Date(), 60, 1000, 
    					new Double(40.704246f), new Double(-111.948613f), 80, 100, 20);
    	else if (eventType.equals(EventType.HARD_VERT) )
			event = new AggressiveDrivingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_NOTEEVENT, new Date(), 60, 1000, 
					new Double(40.704246f), new Double(-111.948613f), 80, 11, -22, -33, 30);
    	else if (eventType.equals(EventType.HARD_ACCEL) )
			event = new AggressiveDrivingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_NOTEEVENT, new Date(), 60, 1000, 
					new Double(40.704246f), new Double(-111.948613f), 80, 100, -20, 0, 40);
    	else if (eventType.equals(EventType.HARD_BRAKE) )
			event = new AggressiveDrivingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_NOTEEVENT, new Date(), 60, 1000, 
					new Double(40.704246f), new Double(-111.948613f), 80, -25, 22, -13, 50);
    	else if (eventType.equals(EventType.HARD_TURN) )
			event = new AggressiveDrivingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_NOTEEVENT, new Date(), 60, 1000, 
					new Double(40.704246f), new Double(-111.948613f), 80, 24, -22, -21, 60);
    	else if (eventType.equals(EventType.CRASH) )
			event = new FullEvent(0l, 0, EventMapper.TIWIPRO_EVENT_FULLEVENT, new Date(), 60, 1000, 
					new Double(40.704246f), new Double(-111.948613f), 80, 24, -22, -21);
    	else if (eventType.equals(EventType.TAMPERING) )
			event = new TamperingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_UNPLUGGED, new Date(), 60, 1000, 
					new Double(40.704246f), new Double(-111.948613f));
    	else if (eventType.equals(EventType.LOW_BATTERY) )
			event = new LowBatteryEvent(0l, 0, EventMapper.TIWIPRO_EVENT_LOW_BATTERY, new Date(), 60, 1000, 
					new Double(40.704246f), new Double(-111.948613f));
    	else if (eventType.equals(EventType.NO_DRIVER) )
			event = new NoDriverEvent(0l, 0, EventMapper.TIWIPRO_EVENT_NO_DRIVER, new Date(), 60, 1000, 
					new Double(40.704246f), new Double(-111.948613f));
    	else if (eventType.equals(EventType.SPEEDING) )
			event = new SpeedingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_SPEEDING_EX3, new Date(), 100, 1000, 
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
        alertMessageDAO = new AlertMessageHessianDAO();
        alertMessageDAO.setSiloService(siloService);
        eventDAO = new EventHessianDAO();
        driverDAO = new DriverHessianDAO();
        groupDAO = new GroupHessianDAO();
        personDAO = new PersonHessianDAO();
        vehicleDAO = new VehicleHessianDAO();
        zoneHessianDAO = new ZoneHessianDAO();
        
        zoneAlertHessianDAO = new ZoneAlertHessianDAO();
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
        alertMessageDAO.setGroupDAO(groupDAO);
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
//                System.out.println(msg.getAlertMessageType() + " " + description + "address: " + msg.getAddress() + " msg: " + msg.getParamterList() + " ");
                // logger.debug(description + "address: " + msg.getAddress() + " msg: " + msg.getParamterList());
                return true;
            }
        }
        
        return false;
    }
}
