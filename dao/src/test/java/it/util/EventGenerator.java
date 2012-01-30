package it.util;

import it.config.ReportTestConst;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.inthinc.pro.model.event.VersionState;
import com.inthinc.pro.model.event.WitnessVersionEvent;
import com.inthinc.pro.model.event.ZoneArrivalEvent;
import com.inthinc.pro.model.event.ZonesVersionEvent;


public class EventGenerator
{

    public static final int DEFAULT_SPEED_LIMIT = 55;
    public static final int DEFAULT_HEADING = 0;    
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

    public List<Event> generateTripEvents(Date startTime, EventGeneratorData data)//, boolean includeExtraEvents, Integer zoneID, boolean includeWaysmartEvents, boolean genCoaching ) throws Exception
    {
        List<Event> eventList = new ArrayList<Event>();
        
        data.initIndexes(ReportTestConst.EVENTS_PER_DAY, data.includeExtra);
        Date eventTime = startTime;
        Event event = null;
        Integer odometer = ReportTestConst.MILES_PER_EVENT;
        int locCnt = ReportTestConst.EVENTS_PER_DAY;
        boolean ignitionOn = false;
        boolean driverLogon = false;
        boolean badSpeeding = false;
        int adCnt = 0;
        int realEventCnt = 0;
        for (int i = 0; i < locCnt; i++)
        {
            if (data.includeExtra && isExtraEventIndex(i)) {
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
                                locations[i].getLat(), locations[i].getLng(), data.zoneID);
                        break;
                    case ReportTestConst.ZONE_EXIT_EVENT_IDX:
                        event = new ZoneArrivalEvent(0l, 0, NoteType.WSZONES_DEPARTURE_EX, eventTime, 0, odometer, 
                                locations[i].getLat(), locations[i].getLng(), data.zoneID);
                        break;
                }
            }
            else if (data.includeWaysmart && waysmartEventIndex(i) != null) {
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
//  from Jason              
//              accel: 50..225  (positive deltaVX)
//              brake: 70..225  (negative deltaVX)
//              turn: 60..225   (deltaVY)
//              vert: 50..300   (deltaVZ)
                
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
//                          FullEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
                event = new FullEvent(0l, 0, NoteType.FULLEVENT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        25, 11, -22, DEFAULT_SPEED_LIMIT);
            }
            else
            {
                if (data.includeExtra && !badSpeeding) {
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
            if (event.getSpeedLimit() == null || event.getSpeedLimit() == 0)
                event.setSpeedLimit(DEFAULT_SPEED_LIMIT);
            if (event.getHeading() == null)
                event.setHeading(DEFAULT_HEADING);
            eventList.add(event);
            realEventCnt++;
            System.out.print(".");


            // coaching events
            if ((data.isSpeedingIndex(i) || data.isSeatbeltIndex(i)) && data.includeCoaching) 
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
        System.out.println(" COMPLETE ");
        return eventList;
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

}
