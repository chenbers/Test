package it.util;

import it.com.inthinc.pro.dao.Util;
import it.config.ReportTestConst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.DOTStoppedEvent;
import com.inthinc.pro.model.event.DOTStoppedState;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.FirmwareVersionEvent;
import com.inthinc.pro.model.event.FullEvent;
import com.inthinc.pro.model.event.HOSNoHoursEvent;
import com.inthinc.pro.model.event.HOSNoHoursState;
import com.inthinc.pro.model.event.HardVertical820Event;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.IgnitionOffEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.ParkingBrakeEvent;
import com.inthinc.pro.model.event.ParkingBrakeState;
import com.inthinc.pro.model.event.QSIVersionEvent;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.model.event.VersionEvent;
import com.inthinc.pro.model.event.VersionState;
import com.inthinc.pro.model.event.WitnessVersionEvent;
import com.inthinc.pro.model.event.ZoneArrivalEvent;
import com.inthinc.pro.model.event.ZoneDepartureEvent;
import com.inthinc.pro.model.event.ZoneEvent;
import com.inthinc.pro.model.event.ZonesVersionEvent;


public class EventGenerator
{

    private final static int ATTR_TYPE_TOP_SPEED=1;
    private final static int ATTR_TYPE_AVG_SPEED=2;
    private final static int ATTR_TYPE_SPEED_LIMIT=3;
    private final static int ATTR_TYPE_AVG_RPM   =  4;
    private final static int ATTR_TYPE_RESET_REASON =5;
    private final static int ATTR_TYPE_MANRESET_REASON= 6;
    private final static int ATTR_TYPE_FWDCMD_STATUS= 7;
    private final static int ATTR_TYPE_SEVERITY=   24;
    													// 128-192 2 byte values
    private final static int ATTR_TYPE_DISTANCE =129;
    private final static int ATTR_TYPE_MAX_RPM =  130;
    private final static int ATTR_TYPE_DELTAVX =  131;
    private final static int ATTR_TYPE_DELTAVY =  132;
    private final static int ATTR_TYPE_DELTAVZ =  133;
    private final static int ATTR_TYPE_MPG = 149;
    private final static int ATTR_TYPE_GPS_QUALITY = 166;
    														// 192 up has 4 byte values
    private final static int ATTR_TYPE_ZONE_ID =  192;
    private final static int ATTR_SPEED_ID = 201;
    private final static int ATTR_TYPE_LO_IDLE  = 219;
    private final static int ATTR_TYPE_HI_IDLE  = 220;
    private final static int ATTR_TYPE_MPG_DISTANCE  = 224;
    private final static int ATTR_TYPE_DRIVETIME  = 225;

    private final static int ATTR_upToDateStatus= 8260;
    private final static int ATTR_state =8254;
    private final static int ATTR_reasonCodeDot =8233;
    private final static int ATTR_reasonCodeHos = 8234;


    /*    
    private final static int ATTR_TYPE_FIRMVER   193
    private final static int ATTR_TYPE_FWDCMD_ID 194
    private final static int ATTR_TYPE_FWDCMD    195
    private final static int ATTR_TYPE_FWDERR    196
    private final static int ATTR_TYPE_BATTERY_LEVEL 201
    private final static int ATTR_TYPE_STRING    255
    */
    public static final long FIFTEEN_SECONDS = 15000;
    public static final int DEFAULT_SPEED_LIMIT = 55;
    
    private static final LatLng locations[] =
    {
        new LatLng(33.0089,-117.1100),
        new LatLng(33.0095,-117.1103), 
        new LatLng(33.0103,-117.1105),
        new LatLng(33.0105,-117.1105),
        new LatLng(33.0103,-117.1113),
        new LatLng(33.0109,-117.1117),
        new LatLng(33.0117,-117.1120),
        new LatLng(33.0127,-117.1122),
        new LatLng(33.0139,-117.1122),
        new LatLng(33.0152,-117.1119),
        
        new LatLng(33.0164,-117.1115),	/* 10 */
        new LatLng(33.0172,-117.1124),
        new LatLng(33.0183,-117.1151), 
        new LatLng(33.0187,-117.1177),
        new LatLng(33.0192,-117.1199),
        new LatLng(33.0201,-117.1215),
        new LatLng(33.0212,-117.1235),
        new LatLng(33.0218,-117.1252),
        new LatLng(33.0219,-117.1271),
        new LatLng(33.0219,-117.1285),
        
        new LatLng(33.0219,-117.1310),
        new LatLng(33.0219,-117.1332),
        new LatLng(33.0219,-117.1356),
        new LatLng(33.0218,-117.1380),
        new LatLng(33.0218,-117.1403),
        new LatLng(33.0217,-117.1426),
        new LatLng(33.0212,-117.1446),
        new LatLng(33.0205,-117.1461),
        new LatLng(33.0195,-117.1475),
        new LatLng(33.0187,-117.1481),
        
        new LatLng(33.0175,-117.1486),
        new LatLng(33.0159,-117.1487),
        new LatLng(33.0147,-117.1482),
        new LatLng(33.0132,-117.1474),
        new LatLng(33.0112,-117.1466),
        new LatLng(33.0095,-117.1458),
        new LatLng(33.0079,-117.1451),
        new LatLng(33.0066,-117.1451),
        new LatLng(33.0054,-117.1456),
        new LatLng(33.0042,-117.1468),
        
        new LatLng(33.0025,-117.1486),
        new LatLng(33.0012,-117.1506),
        new LatLng(33.0004,-117.1522),
        new LatLng(32.9990,-117.1545),
        new LatLng(32.9975,-117.1565),
        new LatLng(32.9961,-117.1576),
        new LatLng(32.9940,-117.1583),
        new LatLng(32.9919,-117.1590),
        new LatLng(32.9903,-117.1595),
        new LatLng(32.9882,-117.1602),

        new LatLng(32.9866,-117.1607),
        new LatLng(32.9846,-117.1608),
        new LatLng(32.9833,-117.1604),
        new LatLng(32.9817,-117.1593),
        new LatLng(32.9806,-117.1579),
        new LatLng(32.9799,-117.1552),
        new LatLng(32.9798,-117.1525),
        new LatLng(32.9790,-117.1499),
        new LatLng(32.9777,-117.1482),
        new LatLng(32.9763,-117.1466),  /* 59 */
        
        new LatLng(32.9755,-117.1475),	/* 60 */
        new LatLng(32.9743,-117.1489),
        new LatLng(32.9728,-117.1508),
        new LatLng(32.9717,-117.1526),
        new LatLng(32.9710,-117.1562),  /* note event */
        new LatLng(32.9709,-117.1595),
        new LatLng(32.9707,-117.1620),
        new LatLng(32.9700,-117.1649),
        new LatLng(32.9701,-117.1674),
        new LatLng(32.9703,-117.1699),
        
        new LatLng(32.9703,-117.1721),	/* 70 */
        new LatLng(32.9702,-117.1750),
        new LatLng(32.9691,-117.1769),
        new LatLng(32.9682,-117.1783),
        new LatLng(32.9672,-117.1810),
        new LatLng(32.9663,-117.1832),
        new LatLng(32.9652,-117.1862),
        new LatLng(32.9643,-117.1891), 
        new LatLng(32.9628,-117.1910),
        new LatLng(32.9611,-117.1923),
        
        new LatLng(32.9615,-117.1940),	/* 80 */
        new LatLng(32.9617,-117.1957),	
        new LatLng(32.9613,-117.1981),
        new LatLng(32.9615,-117.2009),
        new LatLng(32.9621,-117.2037),
        new LatLng(32.9619,-117.2062),
        new LatLng(32.9608,-117.2082),
        new LatLng(32.9593,-117.2095),
        new LatLng(32.9595,-117.2101),
        new LatLng(32.9598,-117.2108),
        
        new LatLng(32.9601,-117.2103),	/* 90 */
        new LatLng(32.9603,-117.2104),
        new LatLng(32.9603,-117.2105),
        new LatLng(32.9603,-117.2106),
        new LatLng(32.9603,-117.2107),

    };

    public EventGenerator()
    {
    }
    static int eventCount;    
	public void generateTripExt(String imeiID, MCMSimulator service, Date startTime, EventGeneratorData data, Integer zoneID ) throws Exception
	{
		generateTrip(imeiID, service, startTime, data, true, zoneID, false, true);
	}
//boolean tampering = false;
    public void generateTrip(String imeiID, MCMSimulator service, Date startTime, EventGeneratorData data ) throws Exception
    {
		generateTrip(imeiID, service, startTime, data, false, 0, false, true);
    	
    }
    public void generateTrip(String imeiID, MCMSimulator service, Date startTime, EventGeneratorData data, boolean genCoaching ) throws Exception
    {
        generateTrip(imeiID, service, startTime, data, false, 0, false, genCoaching);
        
    }

    public void generateTrip(String imeiID, MCMSimulator service, Date startTime, EventGeneratorData data, boolean includeExtraEvents, Integer zoneID, boolean includeWaysmartEvents, boolean genCoaching ) throws Exception
    {
    	System.out.println("generate trip for imei" + imeiID + " Date: " + startTime);
    	
    	data.initIndexes(ReportTestConst.EVENTS_PER_DAY, includeExtraEvents);
        Date eventTime = startTime;
        Event event = null;
        List<Event> eventList = new ArrayList<Event>();
        Integer odometer = ReportTestConst.MILES_PER_EVENT;
        int locCnt = ReportTestConst.EVENTS_PER_DAY;
        eventCount = 0;
		boolean ignitionOn = false;
        boolean driverLogon = false;
		boolean badSpeeding = false;
		int adCnt = 0;
		int realEventCnt = 0;
        for (int i = 0; i < locCnt; i++)
        {
        	if (includeExtraEvents && isExtraEventIndex(i)) {
        		switch (i) {
	        		case ReportTestConst.TAMPER_EVENT_IDX:
	        			event = new Event(0l, 0, NoteType.UNPLUGGED, eventTime, 0, odometer, 
	        					locations[i].getLat(), locations[i].getLng());
	        			break;
					case ReportTestConst.TAMPER2_EVENT_IDX:
	        			event = new Event(0l, 0, NoteType.UNPLUGGED_ASLEEP, eventTime, 0, odometer, 
	        					locations[i].getLat(), locations[i].getLng());
	        			break;
					case ReportTestConst.LOW_BATTERY_EVENT_IDX:
	        			event = new Event(0l, 0, NoteType.LOW_BATTERY, eventTime, 0, odometer, 
	        					locations[i].getLat(), locations[i].getLng());
	        			break;
					case ReportTestConst.ZONE_ENTER_EVENT_IDX:
	        			event = new ZoneArrivalEvent(0l, 0, NoteType.WSZONES_ARRIVAL_EX, eventTime, 0, odometer, 
	        					locations[i].getLat(), locations[i].getLng(), zoneID);
	        			break;
					case ReportTestConst.ZONE_EXIT_EVENT_IDX:
	        			event = new ZoneArrivalEvent(0l, 0, NoteType.WSZONES_DEPARTURE_EX, eventTime, 0, odometer, 
	        					locations[i].getLat(), locations[i].getLng(), zoneID);
	        			break;
        		}
        	}
        	else if (includeWaysmartEvents && waysmartEventIndex(i) != null) {
        	    event = null;
        	    Integer ws = waysmartEventIndex(i);
        	    int idx = ReportTestConst.waySmartEventIndexes[ws].idx;
        	    switch (idx) {
        	        case ReportTestConst.NOTE_EVENT_SECONDARY_IDX:
                        // hard vert
                           event = new AggressiveDrivingEvent(0l, 0, NoteType.WAYSMART_NOTEEVENT_SECONDARY,
                                    eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                                    55, 11, -22, -33, data.severity);
                           break;
        	        case ReportTestConst.SPEEDING_EXT_IDX:
        	            event = new SpeedingEvent(0l, 0, NoteType.WAYSMART_SPEEDING_EX4,
                                           eventTime, 80, odometer,  locations[i].getLat(), locations[i].getLng(),
                                           90, 65, 60, ReportTestConst.MILES_PER_EVENT, 10);
        	            break;
        	        case ReportTestConst.HARD_VERT_820_IDX:
        	        case ReportTestConst.HARD_VERT_820_SECONDARY_IDX:
        	            event = new HardVertical820Event(0l, 0, ReportTestConst.waySmartEventIndexes[ws].type, eventTime, 99, odometer, 
                                       locations[i].getLat(), locations[i].getLng());
        	            break;
        	        case ReportTestConst.FIRMWARE_CURRENT_IDX:
                        event = new FirmwareVersionEvent(0l, 0, ReportTestConst.waySmartEventIndexes[ws].type, eventTime, 0, odometer, 
                                       locations[i].getLat(), locations[i].getLng(), VersionState.UNKNOWN);
                        break;
        	        case ReportTestConst.WITNESS_UPDATED_IDX:
                        event = new WitnessVersionEvent(0l, 0, ReportTestConst.waySmartEventIndexes[ws].type, eventTime, 0, odometer, 
                                       locations[i].getLat(), locations[i].getLng(), VersionState.CURRENT);
                        break;
        	        case ReportTestConst.QSI_UPDATED_IDX:
                        event = new QSIVersionEvent(0l, 0, ReportTestConst.waySmartEventIndexes[ws].type, eventTime, 0, odometer, 
                                       locations[i].getLat(), locations[i].getLng(), VersionState.SERVER_OLDER);
                        break;
        	        case ReportTestConst.ZONES_UPDATED_IDX:
                        event = new ZonesVersionEvent(0l, 0, ReportTestConst.waySmartEventIndexes[ws].type, eventTime, 0, odometer, 
                                       locations[i].getLat(), locations[i].getLng(), VersionState.UPDATED);
                        break;
                    case ReportTestConst.PARKING_BRAKE_IDX:
                        event = new ParkingBrakeEvent(0l, 0, ReportTestConst.waySmartEventIndexes[ws].type, eventTime, 0, odometer, 
                                       locations[i].getLat(), locations[i].getLng(), ParkingBrakeState.DRIVING);
                        break;
                    case ReportTestConst.HOS_NO_HOURS_REMAINING_IDX:
                        event = new HOSNoHoursEvent(0l, 0, ReportTestConst.waySmartEventIndexes[ws].type, eventTime, 0, odometer, 
                                       locations[i].getLat(), locations[i].getLng(), HOSNoHoursState.DRIVING);
                        break;
                    case ReportTestConst.DOT_STOPPED_IDX:
                        event = new DOTStoppedEvent(0l, 0, ReportTestConst.waySmartEventIndexes[ws].type, eventTime, 0, odometer, 
                                       locations[i].getLat(), locations[i].getLng(), DOTStoppedState.UNSAFE_VEHICLE);
                        break;
        	        default:
        	            event = new Event(0l, 0, ReportTestConst.waySmartEventIndexes[ws].type, eventTime, 0, odometer, 
                                       locations[i].getLat(), locations[i].getLng());
                       
        	    }
        	
            }
        	else if (!ignitionOn)
        	{
                event = new Event(0l, 0, NoteType.IGNITION_ON,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng());
                ignitionOn = true;
        		
        	}
            else if (!driverLogon)
            {
                event = new Event(0l, 0, NoteType.NEW_DRIVER,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng());
                driverLogon = true;
                
            }
        	else if (i == (locCnt-1))
        	{
//System.out.println("ignition off event count = " + realEventCnt);        		
                int mpg = data.getMpg()*10;
                event = new IgnitionOffEvent(0l, 0, NoteType.IGNITION_OFF,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(), mpg,
                        (realEventCnt+1)*ReportTestConst.MILES_PER_EVENT,
                        (int)((realEventCnt+1)*ReportTestConst.ELAPSED_TIME_PER_EVENT/1000l));
                
        	}
        	else if (data.isSpeedingIndex(i))
            {
                event = new SpeedingEvent(0l, 0, NoteType.SPEEDING_EX3,
                        eventTime, 80, odometer,  locations[i].getLat(), locations[i].getLng(),
                        90, 65, 60, ReportTestConst.MILES_PER_EVENT, 10);
                
            }
        	else if (data.isSeatbeltIndex(i))
            {
                event = new SeatBeltEvent(0l, 0, NoteType.SEATBELT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        55, 59, ReportTestConst.MILES_PER_EVENT);
                        
                
            }
        	else if (data.isAggressiveDrivingIndex(i))
            {
// 	from Jason        		
//        		accel: 50..225  (positive deltaVX)
//        		brake: 70..225  (negative deltaVX)
//        		turn: 60..225   (deltaVY)
//        		vert: 50..300   (deltaVZ)
        		
        		int adType = adCnt % 2;
                if (adType == 0) // hard vert
                    event = new AggressiveDrivingEvent(0l, 0, NoteType.NOTEEVENT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        55, 11, -22, -33, data.severity);
                else // hard brake
                    event = new AggressiveDrivingEvent(0l, 0, NoteType.NOTEEVENT,
                            eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                            55, -25, 22, -13, data.severity);
                
            }
        	else if (data.isIdlingIndex(i))
            {
                event = new IdleEvent(0l, 0, NoteType.IDLE,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        ReportTestConst.LO_IDLE_TIME, ReportTestConst.HI_IDLE_TIME);
            }
        	else if (data.isCrashIndex(i))
            {
//        					FullEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
                event = new FullEvent(0l, 0, NoteType.FULLEVENT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        25, 11, -22, -33);
            }
            else
            {
            	if (includeExtraEvents && !badSpeeding) {
            		// speed limit is 0 ( should be filtered out)
//            	    	SpeedingEvent(Long noteID, Integer vehicleID, Integer type, 
//            	    		Date time, Integer speed, Integer odometer, Double latitude, Double longitude, 
//            	    		Integer topSpeed,
//            	            Integer avgSpeed, Integer speedLimit, Integer distance, Integer avgRPM)
                    event = new SpeedingEvent(0l, 0, NoteType.SPEEDING_EX3,
                            eventTime, 80, odometer,  locations[i].getLat(), locations[i].getLng(),
                            0, 0, 0, ReportTestConst.MILES_PER_EVENT, 10);
            		badSpeeding = true;
            	}
            	else {
            		event = new Event(0l, 0, NoteType.LOCATION,
                                    eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng());
            	}
            }
            event.setSats(7);
            eventList.add(event);
			realEventCnt++;
            System.out.print(".");


            // coaching events
        	if ((data.isSpeedingIndex(i) || data.isSeatbeltIndex(i)) && genCoaching) 
            {
        		if (data.isSpeedingIndex(i))
        			event = new Event(0l, 0, NoteType.COACHING_SPEEDING,
        					new Date(eventTime.getTime() + 2000l), 60, odometer,  locations[i].getLat(), locations[i].getLng());
        		else event = new Event(0l, 0, NoteType.COACHING_SEATBELT,
        				new Date(eventTime.getTime() + 2000l), 60, odometer,  locations[i].getLat(), locations[i].getLng());
	            event.setSats(7);
	            event.setSpeedLimit(55);
	            eventList.add(event);
				realEventCnt++;
	            System.out.print(".");
            }
            eventTime = new Date(eventTime.getTime() + ReportTestConst.ELAPSED_TIME_PER_EVENT);
        }
//        if (includeWaysmartEvents)
//            sendWSEvents(eventList, imeiID, service);
        sendEvents(eventList, imeiID, service);
        
        
    	System.out.println(" COMPLETE");
    	System.out.println(" event count: " + eventCount);
//    	System.out.println(" realEventCnt " + realEventCnt);
    }


    private void sendEvents(List<Event> eventList, String imeiID, MCMSimulator service) throws Exception {
        
        List<byte[]> noteList = new ArrayList<byte[]>();

        int cnt = 1;
        for (Event event : eventList) {
            byte[] eventBytes = createDataBytesFromEvent(event);
            noteList.add(eventBytes);
//            System.out.println("event type: " + event.getType());
        
            if (cnt % 4 == 0 || cnt == eventList.size()){
//                System.out.print(cnt + " "  + noteList.size() + " ");
                int retryCnt = 0;
                for (; retryCnt < 10; retryCnt++) {
                    try {
                        service.note(imeiID, noteList);
                        break;
                    }
                    catch (HessianException e) {
                        System.out.println("Exception inserting notes: " + e.getErrorCode() + " retrying...");
                    }
                    catch (Throwable t) {
                        System.out.println("Exception inserting notes: " + t.getMessage() + " retrying...");
                    }
                }
                if (retryCnt == 10)
                    throw new Exception("Error inserting notes even after 10 retry attempts.");
                noteList = new ArrayList<byte[]>();
            }
            cnt++;
        }
//        System.out.println("done");
    }

/*
 * this didn't work -- trying to generate waysmart events got a 313 error,     
 */
    private void sendWSEvents(List<Event> eventList, String imeiID, MCMSimulator service) throws Exception {
        
        List<byte[]> noteList = new ArrayList<byte[]>();

        int cnt = 1;
        for (Event event : eventList) {
            byte[] eventBytes = createWSDataBytesFromEvent(event);
            noteList.add(eventBytes);
//            System.out.println("event type: " + event.getType());
        
            if (cnt % 4 == 0 || cnt == eventList.size()){
//                System.out.print(cnt + " "  + noteList.size() + " ");
                int retryCnt = 0;
                for (; retryCnt < 10; retryCnt++) {
                    try {
                        service.notews(imeiID, 2, noteList);
                        break;
                    }
                    catch (HessianException e) {
                        System.out.println("Exception inserting notes: " + e.getErrorCode() + " retrying...");
                    }
                    catch (Throwable t) {
                        System.out.println("Exception inserting notes: " + t.getMessage() + " retrying...");
                    }
                }
                if (retryCnt == 10)
                    throw new Exception("Error inserting notes even after 10 retry attempts.");
                noteList = new ArrayList<byte[]>();
            }
            cnt++;
        }
//        System.out.println("done");
    }

    
    private boolean isExtraEventIndex(int idx) {
    		for (int cnt = 0; cnt < ReportTestConst.extraEventIndexes.length; cnt++)
    			if (idx == ReportTestConst.extraEventIndexes[cnt])
    				return true;
		return false;
	}
    private Integer waysmartEventIndex(int idx) {
        for (int cnt = 0; cnt < ReportTestConst.waySmartEventIndexes.length; cnt++)
            if (idx == ReportTestConst.waySmartEventIndexes[cnt].idx)
                return cnt;
        return null;
    }
	int randomInt(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

	public void generateEvents(String imeiID, MCMSimulator service, Date startTime) throws Exception
    {
        Date eventTime = startTime;
        Event event = null;
        
        List<byte[]> noteList = new ArrayList<byte[]>();
        Integer odometer = 10;
        Integer locCnt = locations.length;
        for (int i = 0; i < locCnt; i++)
        {
            
            if (i == 4 || i == 70)
            {
                event = new SpeedingEvent(0l, 0, NoteType.SPEEDING_EX3,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        90, 65, 60, 2, 10);
                
            }
            else if (i == 55)
            {
                event = new SeatBeltEvent(0l, 0, NoteType.SEATBELT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        55, 59, 3);
                        
                
            }
            else if (i == 33 || i == 77)
            {
                if (i == 33) // hard vert
                    event = new AggressiveDrivingEvent(0l, 0, NoteType.NOTEEVENT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        55, 11, -22, -33, 50);
                else // hard brake
                    event = new AggressiveDrivingEvent(0l, 0, NoteType.NOTEEVENT,
                            eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                            55, -25, 22, -13, 50);
                
            }
            else if (i == (locCnt-1))
            {
                event = new Event(0l, 0, NoteType.LOW_TIWI_BATTERY, eventTime, 0, odometer, locations[i].getLat(), locations[i].getLng());
                
            }
            else if (i == (locCnt-2))
            {
                event = new Event(0l, 0, NoteType.LOW_BATTERY, eventTime, 0, odometer, locations[i].getLat(), locations[i].getLng());
                
            }
            else if (i == (locCnt-3))
            {
                event = new Event(0l, 0, NoteType.UNPLUGGED, eventTime, 0, odometer, locations[i].getLat(), locations[i].getLng());
                
            }
            else
            {
                event = new Event(0l, 0, NoteType.LOCATION,
                                    eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng());
            }
            eventTime = new Date(eventTime.getTime() + FIFTEEN_SECONDS);
            
            byte[] eventBytes = createDataBytesFromEvent(event);
            noteList.add(eventBytes);
            if ((i+1) % 4 == 0 ||(i+1) == locations.length)
            {
                service.note(imeiID, noteList);
//                System.out.println("saved " + noteList.size() + " events i = " + i);
                noteList = new ArrayList<byte[]>();
            }
            
        }
    }


    // method used only for the note() method to create a note and simulate what they device
    // does -- this is currently only used to generate test data
    public static byte[] createDataBytesFromEvent(Event event)
    {
    	eventCount++;    	
//System.out.println("type: " + event.getType() + " time: " + DateUtil.convertDateToSeconds(event.getTime()));    	
        byte[] eventBytes = new byte[200];
        int idx = 0;
        eventBytes[idx++] = (byte) (event.getType().getCode() & 0x000000FF);
        idx = puti4(eventBytes, idx, (int)(event.getTime().getTime()/1000l));
        if (event.getSats() == null || event.getSats().intValue() == 0)
        	event.setSats(10);
        eventBytes[idx++] = (byte) (event.getSats() & 0x000000FF);
        eventBytes[idx++] = (byte) 1; // maprev
        idx = putlat(eventBytes, idx, event.getLatitude());
        idx = putlng(eventBytes, idx, event.getLongitude());
        eventBytes[idx++] = (byte) (event.getSpeed() & 0x000000FF);
        idx = puti2(eventBytes, idx, event.getOdometer());
        if (event.getSpeedLimit() == null || event.getSpeedLimit() == 0) {
            eventBytes[idx++] = (byte) (ATTR_TYPE_SPEED_LIMIT & 0x000000FF);
            eventBytes[idx++] = (byte) (DEFAULT_SPEED_LIMIT & 0x000000FF);
        }

        
        if (event instanceof SpeedingEvent)
        {
            SpeedingEvent speedingEvent = (SpeedingEvent) event;
            eventBytes[idx++] = (byte) (ATTR_TYPE_SPEED_LIMIT & 0x000000FF);
            eventBytes[idx++] = (byte) (speedingEvent.getSpeedLimit() & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_AVG_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (speedingEvent.getAvgSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_TOP_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (speedingEvent.getTopSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_DISTANCE & 0x000000FF);
            idx = puti2(eventBytes, idx, speedingEvent.getDistance());
        }
        else if (event instanceof SeatBeltEvent)
        {
            SeatBeltEvent seatbeltEvent = (SeatBeltEvent) event;
            eventBytes[idx++] = (byte) (ATTR_TYPE_AVG_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (seatbeltEvent.getAvgSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_TOP_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (seatbeltEvent.getTopSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_DISTANCE & 0x000000FF);
            idx = puti2(eventBytes, idx, seatbeltEvent.getDistance());
        }
        else if (event instanceof AggressiveDrivingEvent)
        {
            AggressiveDrivingEvent adEvent = (AggressiveDrivingEvent) event;
            eventBytes[idx++] = (byte) (ATTR_TYPE_AVG_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (adEvent.getSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVX & 0x000000FF);
            idx = puti2(eventBytes, idx, adEvent.getDeltaX());
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVY & 0x000000FF);
            idx = puti2(eventBytes, idx, adEvent.getDeltaY());
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVZ & 0x000000FF);
            idx = puti2(eventBytes, idx, adEvent.getDeltaZ());
        }
        else if (event instanceof FullEvent)
        {
        	FullEvent fullEvent = (FullEvent) event;
            eventBytes[idx++] = (byte) (ATTR_TYPE_AVG_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (fullEvent.getSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVX & 0x000000FF);
            idx = puti2(eventBytes, idx, fullEvent.getDeltaX());
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVY & 0x000000FF);
            idx = puti2(eventBytes, idx, fullEvent.getDeltaY());
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVZ & 0x000000FF);
            idx = puti2(eventBytes, idx, fullEvent.getDeltaZ());
            eventBytes[idx++] = (byte) (ATTR_TYPE_GPS_QUALITY & 0x000000FF);
            idx = puti2(eventBytes, idx, Util.randomInt(0, 1000));
        }
        else if (event instanceof ZoneArrivalEvent)
        {
            ZoneArrivalEvent zoneArrivalEvent = (ZoneArrivalEvent)event;
            eventBytes[idx++] = (byte) (ATTR_TYPE_ZONE_ID & 0x000000FF);
            idx = puti4(eventBytes, idx, zoneArrivalEvent.getZoneID());
        }
        else if (event instanceof ZoneDepartureEvent)
        {
            ZoneEvent zoneDepartureEvent = (ZoneEvent)event;
            eventBytes[idx++] = (byte) (ATTR_TYPE_ZONE_ID & 0x000000FF);
            idx = puti4(eventBytes, idx, zoneDepartureEvent.getZoneID());
        }
        else if (event instanceof IgnitionOffEvent)
        {
        	IgnitionOffEvent ignitionOffEvent = (IgnitionOffEvent)event;
            eventBytes[idx++] = (byte) (ATTR_TYPE_MPG & 0x000000FF);
            idx = puti2(eventBytes, idx, ignitionOffEvent.getMpg());
            eventBytes[idx++] = (byte) (ATTR_TYPE_MPG_DISTANCE & 0x000000FF);
            idx = puti4(eventBytes, idx, ignitionOffEvent.getMpgDistance());
            eventBytes[idx++] = (byte) (ATTR_TYPE_DRIVETIME & 0x000000FF);
            idx = puti4(eventBytes, idx, ignitionOffEvent.getDriveTime());
            eventBytes[idx++] = (byte) (ATTR_TYPE_GPS_QUALITY & 0x000000FF);
            idx = puti2(eventBytes, idx, Util.randomInt(0, 1000));
            
        }
        else if (event instanceof IdleEvent)
        {
            IdleEvent idleEvent = (IdleEvent)event;
            eventBytes[idx++] = (byte) (ATTR_TYPE_LO_IDLE & 0x000000FF);
            idx = puti4(eventBytes, idx, idleEvent.getLowIdle());
            eventBytes[idx++] = (byte) (ATTR_TYPE_HI_IDLE & 0x000000FF);
            idx = puti4(eventBytes, idx, idleEvent.getHighIdle());
        }
        else if (event.getType().equals(NoteType.COACHING_SPEEDING))
        {
            eventBytes[idx++] = (byte) (ATTR_TYPE_SPEED_LIMIT & 0x000000FF);
            eventBytes[idx++] = (byte) (event.getSpeedLimit() & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_SPEED_ID & 0x000000FF);
            idx = puti4(eventBytes, idx, 2); //DateUtil.convertMillisecondsToSeconds(event.getTime().getTime()));
        }
        else if (event.getType().equals(NoteType.COACHING_SEATBELT))
        {
            eventBytes[idx++] = (byte) (ATTR_TYPE_SPEED_LIMIT & 0x000000FF);
            eventBytes[idx++] = (byte) (event.getSpeedLimit() & 0x000000FF);
        }
       // TODO: These are actually waysmart events
/*        
        else if (event instanceof VersionEvent) {
            VersionEvent versionEvent = (VersionEvent)event;
            idx = puti4(eventBytes, idx, ATTR_upToDateStatus);
//            eventBytes[idx++] = (byte) (ATTR_upToDateStatus & 0x000000FF);
            eventBytes[idx++] = (byte) (versionEvent.getStatus().getCode() & 0x000000FF);
            
        }
        else if (event instanceof ParkingBrakeEvent) {
            ParkingBrakeEvent parkingBrakeEvent = (ParkingBrakeEvent)event;
            idx = puti4(eventBytes, idx, ATTR_state);
            eventBytes[idx++] = (byte) (parkingBrakeEvent.getStatus().getCode() & 0x000000FF);
        }
        else if (event instanceof HOSNoHoursEvent) {
            HOSNoHoursEvent hosNoHoursEvent = (HOSNoHoursEvent)event;
            idx = puti4(eventBytes, idx, ATTR_reasonCodeHos);
            eventBytes[idx++] = (byte) (hosNoHoursEvent.getStatus().getCode() & 0x000000FF);
        }
        else if (event instanceof DOTStoppedEvent) { 
            DOTStoppedEvent dotStoppedEvent = (DOTStoppedEvent)event;
            idx = puti4(eventBytes, idx, ATTR_reasonCodeDot);
//            eventBytes[idx++] = (byte) (ATTR_upToDateStatus & 0x000000FF);
            eventBytes[idx++] = (byte) (dotStoppedEvent.getStatus().getCode() & 0x000000FF);
        }
*/        
        return Arrays.copyOf(eventBytes, idx);
    }

    
    public static byte[] createWSDataBytesFromEvent(Event event)
    {

//       que->notes[n].len = len;
//        que->notes[n].buf[len++] = 0;  /* packetLen */
//        que->notes[n].buf[len++] = nType;
//        que->notes[n].buf[len++] = 2;  /* version */
//        len += puti4(que->notes[n].buf+len, (unsigned int)nTime);
//        que->notes[n].buf[len++] = flags  & 0x00ff;
//        len += putLatitude4(que->notes[n].buf+len, latitude);
//        len += putLongitude4(que->notes[n].buf+len, longitude);
//        que->notes[n].buf[len++] = speed & 0x00ff;
//        que->notes[n].buf[len++] = speedLimit & 0x00ff;
//        len += puti4(que->notes[n].buf+len, (unsigned int)linkID);
//        len += puti4(que->notes[n].buf+len, que->odometer);
//        len += puti2(que->notes[n].buf+len, g_stateID);
//        que->notes[n].len = len;
        

        eventCount++;       
//System.out.println("type: " + event.getType() + " time: " + DateUtil.convertDateToSeconds(event.getTime()));      
        byte[] eventBytes = new byte[200];
        int idx = 0;
        eventBytes[idx++] = (byte) (0);
        eventBytes[idx++] = (byte) (event.getType().getCode() & 0x000000FF);
        eventBytes[idx++] = (byte) (2);
        idx = puti4(eventBytes, idx, (int)(event.getTime().getTime()/1000l));
//        if (event.getSats() == null || event.getSats().intValue() == 0)
//            event.setSats(10);
        eventBytes[idx++] = (byte) (0);
//        eventBytes[idx++] = (byte) 1; // maprev
        idx = putlat4(eventBytes, idx, event.getLatitude());
        idx = putlng4(eventBytes, idx, event.getLongitude());
        eventBytes[idx++] = (byte) (event.getSpeed() & 0x000000FF);
        eventBytes[idx++] = (byte) ((event.getSpeedLimit()==null ? 0 : event.getSpeedLimit()) & 0x000000FF);
        idx = puti4(eventBytes, idx, 0); // linkID
        idx = puti4(eventBytes, idx, event.getOdometer());
        idx = puti2(eventBytes, idx, 10); // stateID

        return Arrays.copyOf(eventBytes, idx);
    }

    
    private static int putlat(byte[] eventBytes, int idx, Double latitude)
    {
        latitude = 90.0 - latitude;
        latitude = latitude / 180.0;

        return putlatlng(eventBytes, idx, latitude);
    }

    private static int putlng(byte[] eventBytes, int idx, Double longitude)
    {
        if (longitude < 0.0)
            longitude = longitude + 360;
        longitude = longitude / 360.0;

        return putlatlng(eventBytes, idx, longitude);
    }

    private static int putlatlng(byte[] eventBytes, int idx, Double latlng)
    {
        int i = (int) (latlng * 0x00ffffff);
        eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }
    private static int putlat4(byte[] eventBytes, int idx, Double latitude)
    {
        latitude = 90.0 - latitude;
        latitude = latitude / 180.0;

        return putlatlng4(eventBytes, idx, latitude);
    }

    private static int putlng4(byte[] eventBytes, int idx, Double longitude)
    {
        if (longitude < 0.0)
            longitude = longitude + 360;
        longitude = longitude / 360.0;

        return putlatlng4(eventBytes, idx, longitude);
    }

    private static int putlatlng4(byte[] eventBytes, int idx, Double latlng)
    {
        int i = (int) (latlng * 0x00ffffff);
        eventBytes[idx++] = (byte) ((i >> 24) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }

    private static int puti4(byte[] eventBytes, int idx, Integer i)
    {
        eventBytes[idx++] = (byte) ((i >> 24) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }

    private static int puti2(byte[] eventBytes, int idx, Integer i)
    {
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }


}
