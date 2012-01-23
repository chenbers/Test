package it.com.inthinc.pro.dao.jdbc;

// The tests in this file  can fail sporadically when the scheduler is running on the same
// server that is is hitting (usually dev).  If this becomes a problem, we can mark them as Ignore.  
import static org.junit.Assert.assertEquals;
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
import com.inthinc.pro.model.AlertEscalationItem;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.DOTStoppedEvent;
import com.inthinc.pro.model.event.DOTStoppedState;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.FirmwareVersionEvent;
import com.inthinc.pro.model.event.FullEvent;
import com.inthinc.pro.model.event.HOSNoHoursEvent;
import com.inthinc.pro.model.event.HOSNoHoursState;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.ParkingBrakeEvent;
import com.inthinc.pro.model.event.ParkingBrakeState;
import com.inthinc.pro.model.event.QSIVersionEvent;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.model.event.VersionState;
import com.inthinc.pro.model.event.WitnessVersionEvent;
import com.inthinc.pro.model.event.ZoneArrivalEvent;
import com.inthinc.pro.model.event.ZoneDepartureEvent;
import com.inthinc.pro.model.event.ZonesVersionEvent;
import com.inthinc.pro.notegen.NoteGenerator;
import com.inthinc.pro.notegen.WSNoteSender;

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
    
    private static RedFlagAlertHessianDAO redFlagAlertHessianDAO;
    private static List<RedFlagAlert> zoneAlerts;
    private static List<RedFlagAlert> redFlagAlerts;
    private static List<RedFlagAlert> originalAlerts;
    private static ITData itData;
    
    private static NoteGenerator noteGenerator;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        mapServerURL = config.get(IntegrationConfig.MAP_SERVER_URL).toString();
        siloService = new SiloServiceCreator(host, port).getService();
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));

        // TODO: CJ add to config properties
        noteGenerator = new NoteGenerator();
        WSNoteSender wsNoteSender = new WSNoteSender();
        wsNoteSender.setUrl("dev.tiwipro.com");
        wsNoteSender.setPort(8888);
        noteGenerator.setWsNoteSender(wsNoteSender);
        
        
        
        
        
        initDAOs();
        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestData(stream, siloService, true, true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }
System.out.println("account id " + itData.account.getAccountID());        
        List<RedFlagAlert> alerts = redFlagAlertHessianDAO.getRedFlagAlerts(itData.account.getAccountID());
        originalAlerts = redFlagAlertHessianDAO.getRedFlagAlerts(itData.account.getAccountID());
        zoneAlerts = new ArrayList<RedFlagAlert>();
        redFlagAlerts = new ArrayList<RedFlagAlert>();
    	
        for (RedFlagAlert alert : alerts) {
            if (alert.getTypes().contains(AlertMessageType.ALERT_TYPE_ENTER_ZONE) || alert.getTypes().contains(AlertMessageType.ALERT_TYPE_EXIT_ZONE)) {
                zoneAlerts.add(alert);
            }
            else redFlagAlerts.add(alert);
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
    	for (RedFlagAlert alert : originalAlerts) {
    	    redFlagAlertHessianDAO.update(alert);
    	}
    }

    @Test
    //@Ignore
    public void zoneAlerts() {
    	for (RedFlagAlert zoneAlert : zoneAlerts) {
    		EventType eventType = getEventTypes(zoneAlert).get(0);
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
    //@Ignore
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
//    @Ignore
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
//  de7164
    public void redFlagSpeedAlerts() {
      GroupData groupData = itData.teamGroupData.get(ITData.GOOD); 
      for (RedFlagAlert redFlagAlert : redFlagAlerts) {
          List<EventType> eventTypes = getEventTypes(redFlagAlert);
          if (!eventTypes.get(0).equals(EventType.SPEEDING))
              continue;
          boolean anyAlertsFound = false;
          modRedFlagAlertPref(GROUPS, redFlagAlert);
          String IMEI = groupData.device.getImei();
          
          Event event = new SpeedingEvent(0l, 0, NoteType.SPEEDING_EX3, new Date(), 100, 1000, 
                  new Double(40.704246f), new Double(-111.948613f), 11, 11, 5, 100, 100);
      
          if (!genEvent(event, IMEI))
              fail("Unable to generate event of type " + eventTypes.get(0));
          if (pollForMessages("Red Flag Alert Groups Set"))
              anyAlertsFound = true;
          assertTrue("Expect no alerts for speeding 11 mph in a 5 mph zone ",!anyAlertsFound);

          event = new SpeedingEvent(0l, 0, NoteType.SPEEDING_EX3, new Date(), 100, 1000, 
                  new Double(40.704246f), new Double(-111.948613f), 16, 16, 10, 100, 100);
      
          if (!genEvent(event, IMEI))
              fail("Unable to generate event of type " + eventTypes.get(0));
          if (pollForMessages("Red Flag Alert Groups Set"))
              anyAlertsFound = true;

          assertTrue("Expect alert for speeding 16 mph in a 10 mph zone " + eventTypes.get(0), anyAlertsFound);
      }
  }
    @Test
//  us4832
    public void redFlagMaxSpeedAlerts() {
        // max speeding alert is set to 76 mph
      GroupData groupData = itData.teamGroupData.get(ITData.GOOD); 
      for (RedFlagAlert redFlagAlert : redFlagAlerts) {
          List<EventType> eventTypes = getEventTypes(redFlagAlert);
          if (!eventTypes.get(0).equals(EventType.SPEEDING))
              continue;
          boolean anyAlertsFound = false;
          modRedFlagAlertPref(GROUPS, redFlagAlert);
          String IMEI = groupData.device.getImei();
          
          Event event = new SpeedingEvent(0l, 0, NoteType.SPEEDING_EX3, new Date(), 100, 1000, 
                  new Double(40.704246f), new Double(-111.948613f), 77,77, 75, 100, 100);
      
          if (!genEvent(event, IMEI))
              fail("Unable to generate event of type " + eventTypes.get(0));
          if (pollForMessages("Red Flag Alert Groups Set"))
              anyAlertsFound = true;
          assertTrue("Expect alert for speeding  mph in any zone ",anyAlertsFound);

      }
  }

    private static Integer DEFAULT_HEADING = 0; //NORTH
    private static Integer DEFAULT_SATS = 5; 
    private static Double DEFAULT_LAT = 40.704246d; 
    private static Double DEFAULT_LNG = -111.948613d; 

/* todo    
    ALERT_TYPE_INSTALL
    NOTE_TYPE_INSTALL (35)
ALERT_TYPE_IGNITION_ON
    NOTE_TYPE_IGNITION_ON (19)
*/    
    MiscAlertInfo miscAlertInfoList[] = {
//            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_PANIC, new Event[] {
//                    new Event(0l, 0, NoteType.WAYSMART_PANIC, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG)}),
            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE, new Event[] {
                    new Event(0l, 0, NoteType.WAYSMART_NO_INTERNAL_THUMB_DRIVE, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG)}),
//            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_MAN_DOWN_OK, new Event[] {
//                    new Event(0l, 0, NoteType.WAYSMART_AUTO_MAN_OK, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG),
//                    new Event(0l, 0, NoteType.WAYSMART_MAN_OK, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG),
//                    new Event(0l, 0, NoteType.WAYSMART_REMOTE_OK_MANDOWN, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG)}),
//            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_MAN_DOWN, new Event[] {
//                    new Event(0l, 0, NoteType.WAYSMART_AUTOMANDOWN, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG),
//                    new Event(0l, 0, NoteType.WAYSMART_MANDOWN, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG),
//                    new Event(0l, 0, NoteType.WAYSMART_REMOTE_MAN_MANDOWN, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG),
//                    new Event(0l, 0, NoteType.WAYSMART_REMOTE_AUTO_MANDOWN, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG),}),
//            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_DSS_MICROSLEEP, new Event[] {
//                    new Event(0l, 0, NoteType.WAYSMART_DSS_MICROSLEEP, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG)}),
//            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_FIRMWARE_CURRENT, new Event[] {
//                    new FirmwareVersionEvent(0l, 0, NoteType.WAYSMART_MCM_APP_FIRMWARE_UP_TO_DATE, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG, VersionState.CURRENT)}),
            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_HOS_NO_HOURS_REMAINING, new Event[] {
                    new HOSNoHoursEvent(0l, 0, NoteType.WAYSMART_HOS_NO_HOURS, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG, HOSNoHoursState.DRIVING)}),
            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_HOS_DOT_STOPPED, new Event[] {
                    new DOTStoppedEvent(0l, 0, NoteType.WAYSMART_DOT_STOPPED, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG, DOTStoppedState.DOT_INSPECTION)}),
//            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_OFF_HOURS, new Event[] {
//                    new Event(0l, 0, NoteType.WAYSMART_OFF_HOURS_DRIVING, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG)}),
            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_PARKING_BRAKE, new Event[] {
                    new ParkingBrakeEvent(0l, 0, NoteType.WAYSMART_PARKINGBRAKE, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG, ParkingBrakeState.DRIVING)}),
            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_QSI_UPDATED, new Event[] {
                    new QSIVersionEvent(0l, 0, NoteType.WAYSMART_QSI_UP_TO_DATE, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG, VersionState.CURRENT)}),
//            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_WIRELINE_ALARM, new Event[] {
//                    new Event(0l, 0, NoteType.WAYSMART_WIRELINE_ALARM, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG)}),
//            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION, new Event[] {
//                    new Event(0l, 0, NoteType.WAYSMART_WITNESS_HEARTBEAT_VIOLATION, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG)}),
            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_WITNESS_UPDATED, new Event[] {
                    new WitnessVersionEvent(0l, 0, NoteType.WITNESS_UP_TO_DATE, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG, VersionState.CURRENT)}),
            new MiscAlertInfo(AlertMessageType.ALERT_TYPE_ZONES_CURRENT, new Event[] {
                    new ZonesVersionEvent(0l, 0, NoteType.ZONES_UP_TO_DATE, new Date(), 100, 1000, DEFAULT_LAT, DEFAULT_LNG, VersionState.SERVER_OLDER)}),
    };
    
    @Ignore
    @Test 
    public void miscWSAlert() {
        GroupData groupData = itData.teamGroupData.get(ITData.WS_GROUP); 
        String IMEI = groupData.device.getImei();
        Device device = groupData.device;
        
        RedFlagAlert redFlagAlert = redFlagAlerts.get(redFlagAlerts.size()-1);
        for (MiscAlertInfo miscAlertInfo : miscAlertInfoList) {
            List<AlertMessageType> typeList = new ArrayList<AlertMessageType>();
            typeList.add(miscAlertInfo.alertMessageType);
            redFlagAlert.setTypes(typeList);
            redFlagAlertHessianDAO.update(redFlagAlert);
            
            for (Event event : miscAlertInfo.events) {
                event.setHeading(DEFAULT_HEADING);
                event.setSats(DEFAULT_SATS);
                for (int attempt = 0; attempt < 6; attempt++) {
                    if (attempt == 5) {
                        fail("Unable to get alert message after 5 attempts for type: " + miscAlertInfo.alertMessageType);
                    }
                    if (!genWSEvent(event.getType(), event, device))
                        fail("Unable to generate event of type " + event.getType());
                    //break;
                    AlertMessageBuilder msg = pollForMessagesBuilder("AlertType: " + miscAlertInfo.alertMessageType);
                    if (msg != null && msg.getAlertMessageType() == miscAlertInfo.alertMessageType) {
                        
                        List<String> params = msg.getParamterList();
                        if (miscAlertInfo.alertMessageType == AlertMessageType.ALERT_TYPE_WITNESS_UPDATED ||
                                miscAlertInfo.alertMessageType == AlertMessageType.ALERT_TYPE_FIRMWARE_CURRENT ||
                                miscAlertInfo.alertMessageType == AlertMessageType.ALERT_TYPE_ZONES_CURRENT ||
                                miscAlertInfo.alertMessageType == AlertMessageType.ALERT_TYPE_QSI_UPDATED) {
                            assertEquals("number of params", 5, params.size());
                            String version = params.get(4);
                            assertTrue("expected a version param", version.startsWith("VersionState"));
                        }
                        else 
                            assertEquals("number of params", 4, params.size());
                        assertEquals("driverName", groupData.driver.getPerson().getFullName(), params.get(1));
                        assertEquals("vehicleName", groupData.vehicle.getName(), params.get(2));
                        String[] latLng = params.get(3).split(",");
                        assertTrue("location - lat", latLng[0].trim().startsWith("40.7"));
                        assertTrue("location - lng", latLng[1].trim().startsWith("-111.9"));

                        break;
                    }
                }
                
            }
        }
    }
    
    class MiscAlertInfo {
        AlertMessageType alertMessageType;
        Event events[];
        public MiscAlertInfo(AlertMessageType alertMessageType, Event[] events) {
            super();
            this.alertMessageType = alertMessageType;
            this.events = events;
        }
        
    }
    
    
    @Test 
    public void alertMessageBuilder() {
        
        List<AlertMessageBuilder> list = alertMessageDAO.getMessageBuilders(AlertMessageDeliveryType.EMAIL); 
        
        System.out.println("list" + list.size());

    }

    @Test
    @Ignore
    public void escalationPhoneTest()
    {
        // TODO: this was just fetching one that had been inserted in db, need a better test for this
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
                    break;
                case ALERT_TYPE_HARD_ACCEL:    
                    eventTypes.add(EventType.HARD_ACCEL);
                    break;
                case ALERT_TYPE_HARD_BRAKE:    
                    eventTypes.add(EventType.HARD_BRAKE);
                    break;
                case ALERT_TYPE_HARD_BUMP:    
                    eventTypes.add(EventType.HARD_VERT);
                    break;
                case ALERT_TYPE_HARD_TURN:    
                    eventTypes.add(EventType.HARD_TURN);
                    break;
                case ALERT_TYPE_CRASH:    
                    eventTypes.add(EventType.CRASH);
                    break;
                case ALERT_TYPE_TAMPERING:    
                    eventTypes.add(EventType.TAMPERING);
                    break;
               case ALERT_TYPE_LOW_BATTERY:    
                    eventTypes.add(EventType.LOW_BATTERY);
                    break;
               case ALERT_TYPE_NO_DRIVER:    
                    eventTypes.add(EventType.NO_DRIVER); 
                    break;
                case ALERT_TYPE_ENTER_ZONE:
                    eventTypes.add(EventType.ZONES_ARRIVAL); 
                    break;
                case ALERT_TYPE_EXIT_ZONE:
                    eventTypes.add(EventType.ZONES_DEPARTURE); 
                    break;
                case ALERT_TYPE_PANIC:
                    eventTypes.add(EventType.PANIC); 
                    break;
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
        List<AlertEscalationItem> escalationList = new ArrayList<AlertEscalationItem>();
        escalationList.add(new AlertEscalationItem(itData.fleetUser.getPersonID(), 1));
        escalationList.add(new AlertEscalationItem(itData.districtUser.getPersonID(), 0));
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
//                zoneAlert.setEmailTo(emailList);
                zoneAlert.setEscalationList(escalationList);
                zoneAlert.setMaxEscalationTries(5);
                zoneAlert.setMaxEscalationTryTime(null);
                break;
            case VEHICLES:
                List<Integer> vehicleIDs = new ArrayList<Integer>();
                vehicleIDs.add(groupData.vehicle.getVehicleID());
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(emptyList);
                zoneAlert.setVehicleIDs(vehicleIDs);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
//                zoneAlert.setEmailTo(emailList);
                break;
            case VEHICLE_TYPES:
                List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
                vehicleTypes.add(groupData.vehicle.getVtype());
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(emptyList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(vehicleTypes);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
//                zoneAlert.setEmailTo(emailList);
                zoneAlert.setEscalationList(escalationList);
                zoneAlert.setMaxEscalationTries(5);
                zoneAlert.setMaxEscalationTryTime(null);
                break;
            case GROUPS:
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(groupIDList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
//                zoneAlert.setEmailTo(emailList);
                zoneAlert.setEscalationList(escalationList);
                zoneAlert.setMaxEscalationTries(5);
                zoneAlert.setMaxEscalationTryTime(null);
                break;
            case CONTACT_INFO:
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(groupIDList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
//                zoneAlert.setEmailTo(new ArrayList<String>());
                zoneAlert.setEscalationList(escalationList);
                zoneAlert.setMaxEscalationTries(5);
                zoneAlert.setMaxEscalationTryTime(null);
                break;
            case ANY_TIME:
                zoneAlert.setDriverIDs(null);
                zoneAlert.setGroupIDs(groupIDList);
                zoneAlert.setVehicleIDs(null);
                zoneAlert.setVehicleTypes(null);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setStartTOD(0);
                zoneAlert.setStopTOD(0);
//                zoneAlert.setEmailTo(emailList);
                zoneAlert.setEscalationList(escalationList);
                zoneAlert.setMaxEscalationTries(5);
                zoneAlert.setMaxEscalationTryTime(null);
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
        List<AlertEscalationItem> escalationList = new ArrayList<AlertEscalationItem>();
        escalationList.add(new AlertEscalationItem(itData.fleetUser.getPersonID(), 1));
        escalationList.add(new AlertEscalationItem(itData.districtUser.getPersonID(), 0));

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
//                redFlagAlert.setEmailTo(emailList);
                redFlagAlert.setEscalationList(escalationList);
                redFlagAlert.setMaxEscalationTries(5);
                redFlagAlert.setMaxEscalationTryTime(null);
               break;
            case VEHICLES:
                List<Integer> vehicleIDs = new ArrayList<Integer>();
                vehicleIDs.add(groupData.vehicle.getVehicleID());
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(emptyList);
                redFlagAlert.setVehicleIDs(vehicleIDs);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
//                redFlagAlert.setEmailTo(emailList);
                redFlagAlert.setEscalationList(escalationList);
                redFlagAlert.setMaxEscalationTries(5);
                redFlagAlert.setMaxEscalationTryTime(null);
                break;
            case VEHICLE_TYPES:
                List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
                vehicleTypes.add(groupData.vehicle.getVtype());
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(emptyList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(vehicleTypes);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
//                redFlagAlert.setEmailTo(emailList);
                redFlagAlert.setEscalationList(escalationList);
                redFlagAlert.setMaxEscalationTries(5);
                redFlagAlert.setMaxEscalationTryTime(null);
                break;
            case GROUPS:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(groupIDList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
//                redFlagAlert.setEmailTo(emailList);
                redFlagAlert.setEscalationList(escalationList);
                redFlagAlert.setMaxEscalationTries(5);
                redFlagAlert.setMaxEscalationTryTime(null);
                break;
            case CONTACT_INFO:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(groupIDList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
//                redFlagAlert.setEmailTo(new ArrayList<String>());
                redFlagAlert.setEscalationList(escalationList);
                redFlagAlert.setMaxEscalationTries(5);
                redFlagAlert.setMaxEscalationTryTime(null);
                break;
            case ANY_TIME:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(groupIDList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
//                redFlagAlert.setEmailTo(emailList);
                redFlagAlert.setStartTOD(0);
                redFlagAlert.setStopTOD(1439);
                redFlagAlert.setEscalationList(escalationList);
                redFlagAlert.setMaxEscalationTries(5);
                redFlagAlert.setMaxEscalationTryTime(null);
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
    	
    	return genEvent(event, imei);
    }

    private boolean genWSEvent(NoteType noteType, Event event, Device device) {
        noteGenerator.genEvent(noteType, event, device);
        return true;
    
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
        
        redFlagAlertHessianDAO = new RedFlagAlertHessianDAO();
        
        addressLookup = new GeonamesAddressLookup();
        addressLookup.setMapServerURLString(mapServerURL);
        driverDAO.setSiloService(siloService);
        groupDAO.setSiloService(siloService);
        eventDAO.setSiloService(siloService);
        personDAO.setSiloService(siloService);
        vehicleDAO.setSiloService(siloService);
        zoneHessianDAO.setSiloService(siloService);
        redFlagAlertHessianDAO.setSiloService(siloService);
    
        alertMessageDAO.setDriverDAO(driverDAO);
        alertMessageDAO.setVehicleDAO(vehicleDAO);
        alertMessageDAO.setEventDAO(eventDAO);
        alertMessageDAO.setPersonDAO(personDAO);
        alertMessageDAO.setZoneDAO(zoneHessianDAO);
        alertMessageDAO.setAddressLookup(addressLookup);

    }
    
    private boolean pollForMessages(String description) {
        
        return pollForMessagesBuilder(description) != null;
    }

    private AlertMessageBuilder pollForMessagesBuilder(String description) {
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
System.out.println(msg.getAlertMessageType() + " " + description + "address: " + msg.getAddress() + " msg: " + msg.getParamterList() + " ");
                return msg;
            }
        }
        
        return null;
    }

}
