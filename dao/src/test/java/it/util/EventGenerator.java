package it.util;

import it.config.ReportTestConst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.AggressiveDrivingEvent;
import com.inthinc.pro.model.DeviceLowBatteryEvent;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.FullEvent;
import com.inthinc.pro.model.IdleEvent;
import com.inthinc.pro.model.IgnitionOffEvent;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.LowBatteryEvent;
import com.inthinc.pro.model.SeatBeltEvent;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.model.TamperingEvent;
import com.inthinc.pro.model.ZoneArrivalEvent;
import com.inthinc.pro.model.ZoneDepartureEvent;
import com.inthinc.pro.model.ZoneEvent;


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
    														// 192 up has 4 byte values
    private final static int ATTR_TYPE_ZONE_ID =  192;
    private final static int ATTR_SPEED_ID = 201;
    private final static int ATTR_TYPE_LO_IDLE  = 219;
    private final static int ATTR_TYPE_HI_IDLE  = 220;
    private final static int ATTR_TYPE_MPG_DISTANCE  = 224;
    private final static int ATTR_TYPE_DRIVETIME  = 225;

/*    
    private final static int ATTR_TYPE_FIRMVER   193
    private final static int ATTR_TYPE_FWDCMD_ID 194
    private final static int ATTR_TYPE_FWDCMD    195
    private final static int ATTR_TYPE_FWDERR    196
    private final static int ATTR_TYPE_BATTERY_LEVEL 201
    private final static int ATTR_TYPE_STRING    255
    */
    public static final long FIFTEEN_SECONDS = 15000;
    
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
        new LatLng(33.0164,-117.1115),
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
        new LatLng(32.9755,-117.1475),
        new LatLng(32.9743,-117.1489),
        new LatLng(32.9728,-117.1508),
        new LatLng(32.9717,-117.1526),
        new LatLng(32.9710,-117.1562),  /* note event */
        new LatLng(32.9709,-117.1595),
        new LatLng(32.9707,-117.1620),
        new LatLng(32.9700,-117.1649),
        new LatLng(32.9701,-117.1674),
        new LatLng(32.9703,-117.1699),
        new LatLng(32.9703,-117.1721),
        new LatLng(32.9702,-117.1750),
        new LatLng(32.9691,-117.1769),
        new LatLng(32.9682,-117.1783),
        new LatLng(32.9672,-117.1810),
        new LatLng(32.9663,-117.1832),
        new LatLng(32.9652,-117.1862),
        new LatLng(32.9643,-117.1891),
        new LatLng(32.9628,-117.1910),
        new LatLng(32.9611,-117.1923),
        new LatLng(32.9615,-117.1940),
        new LatLng(32.9617,-117.1957),
        new LatLng(32.9613,-117.1981),
        new LatLng(32.9615,-117.2009),
        new LatLng(32.9621,-117.2037),
        new LatLng(32.9619,-117.2062),
        new LatLng(32.9608,-117.2082),
        new LatLng(32.9593,-117.2095),
        new LatLng(32.9595,-117.2101),
        new LatLng(32.9598,-117.2108),
        new LatLng(32.9601,-117.2103),
        new LatLng(32.9603,-117.2102),
                                            // warnings
        new LatLng(32.9603,-117.2102),      // low tiwi battery  (92)
        new LatLng(32.9603,-117.2102),      // low battery
        new LatLng(32.9603,-117.2102),      // tampering

    };

    public EventGenerator()
    {
    }
static int eventCount;    

    // used for report testing
    public void generateTrip(String imeiID, MCMSimulator service, Date startTime, EventGeneratorData data ) throws Exception
    {
    	System.out.println("generate trip for imei" + imeiID + " Date: " + startTime);
    	
    	data.initIndexes(locations.length);
        Date eventTime = startTime;
        Event event = null;
        
        List<byte[]> noteList = new ArrayList<byte[]>();
        Integer odometer = ReportTestConst.MILES_PER_EVENT;
        Integer locCnt = ReportTestConst.EVENTS_PER_DAY;
eventCount = 0;
//boolean tampering = false;
        for (int i = 0; i < locCnt; i++)
        {
        	if (i == 0)
        	{
                event = new Event(0l, 0, EventMapper.TIWIPRO_EVENT_IGNITION_ON,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng());
        		
        	}
        	else if (i == (locCnt-1))
        	{
                int mpg = data.getMpg()*10;
                event = new IgnitionOffEvent(0l, 0, EventMapper.TIWIPRO_EVENT_IGNITION_OFF,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(), mpg,
                        locCnt*ReportTestConst.MILES_PER_EVENT,
                        (int)(locCnt*ReportTestConst.ELAPSED_TIME_PER_EVENT/1000l));
                
        	}
        	else if (data.isSpeedingIndex(i))
            {
                event = new SpeedingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_SPEEDING_EX3,
                        eventTime, 80, odometer,  locations[i].getLat(), locations[i].getLng(),
                        90, 65, 60, ReportTestConst.MILES_PER_EVENT, 10);
                
            }
        	else if (data.isSeatbeltIndex(i))
            {
                event = new SeatBeltEvent(0l, 0, EventMapper.TIWIPRO_EVENT_SEATBELT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        55, 59, ReportTestConst.MILES_PER_EVENT);
                        
                
            }
        	else if (data.isAggressiveDrivingIndex(i))
            {
        		int adType = randomInt(0, 1);
                if (adType == 0) // hard vert
                    event = new AggressiveDrivingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_NOTEEVENT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        55, 11, -22, -33, data.severity);
                else // hard brake
                    event = new AggressiveDrivingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_NOTEEVENT,
                            eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                            55, -25, 22, -13, data.severity);
                
            }
        	else if (data.isIdlingIndex(i))
            {
                event = new IdleEvent(0l, 0, EventMapper.TIWIPRO_EVENT_IDLE,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        ReportTestConst.LO_IDLE_TIME, ReportTestConst.HI_IDLE_TIME);
            }
        	else if (data.isCrashIndex(i))
            {
//        					FullEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
                event = new FullEvent(0l, 0, EventMapper.TIWIPRO_EVENT_FULLEVENT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        25, 11, -22, -33);
            }
            else
            {
//                if (eventCount > 50 && !tampering)
//                {
//                    event = new TamperingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_UNPLUGGED, eventTime, 0, odometer, 
//                    		locations[i].getLat(), locations[i].getLng());
//                    tampering= true;
//                    System.out.println("generated a tampering");
//                }
//                else
//                {
                	event = new Event(0l, 0, EventMapper.TIWIPRO_EVENT_LOCATION,
                                    eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng());
//                }
            }
            event.setSats(7);
            byte[] eventBytes = createDataBytesFromEvent(event);
            noteList.add(eventBytes);

            // coaching events
        	if (data.isSpeedingIndex(i) || data.isSeatbeltIndex(i)) 
            {
        		if (data.isSpeedingIndex(i))
        			event = new Event(0l, 0, EventMapper.TIWIPRO_EVENT_COACHING_SPEEDING,
        					new Date(eventTime.getTime() + 2000l), 60, odometer,  locations[i].getLat(), locations[i].getLng());
        		else event = new Event(0l, 0, EventMapper.TIWIPRO_EVENT_COACHING_SEATBELT,
        				new Date(eventTime.getTime() + 2000l), 60, odometer,  locations[i].getLat(), locations[i].getLng());
	            event.setSats(7);
	            eventBytes = createDataBytesFromEvent(event);
	            noteList.add(eventBytes);
            }
            
            eventTime = new Date(eventTime.getTime() + ReportTestConst.ELAPSED_TIME_PER_EVENT);
            
            if ((i+1) % 4 == 0 ||(i+1) == locations.length)
            {
            	for (int retryCnt = 0; retryCnt < 10; retryCnt++)
            	{
	            	try
	            	{
	            		service.note(imeiID, noteList);
	            		break;
	            	}
	            	catch (HessianException e)
	            	{
	            		System.out.println("Exception inserting notes: " + e.getErrorCode() + " retrying...");
	            		
	            	}
	            	catch (Throwable t)
	            	{
	            		System.out.println("Exception inserting notes: " + t.getMessage() + " retrying...");
	            		
	            	}
            	}
                noteList = new ArrayList<byte[]>();
                System.out.print(".");
            }
            
        }
    	System.out.println(" COMPLETE");
    	System.out.println(" event count: " + eventCount);
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
                event = new SpeedingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_SPEEDING_EX3,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        90, 65, 60, 2, 10);
                
            }
            else if (i == 55)
            {
                event = new SeatBeltEvent(0l, 0, EventMapper.TIWIPRO_EVENT_SEATBELT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        55, 59, 3);
                        
                
            }
            else if (i == 33 || i == 77)
            {
                if (i == 33) // hard vert
                    event = new AggressiveDrivingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_NOTEEVENT,
                        eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                        55, 11, -22, -33, 50);
                else // hard brake
                    event = new AggressiveDrivingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_NOTEEVENT,
                            eventTime, 60, odometer,  locations[i].getLat(), locations[i].getLng(),
                            55, -25, 22, -13, 50);
                
            }
            else if (i == (locCnt-1))
            {
                event = new DeviceLowBatteryEvent(0l, 0, EventMapper.TIWIPRO_EVENT_LOW_TIWI_BATTERY, eventTime, 0, odometer, locations[i].getLat(), locations[i].getLng());
                
            }
            else if (i == (locCnt-2))
            {
                event = new LowBatteryEvent(0l, 0, EventMapper.TIWIPRO_EVENT_LOW_BATTERY, eventTime, 0, odometer, locations[i].getLat(), locations[i].getLng());
                
            }
            else if (i == (locCnt-3))
            {
                event = new TamperingEvent(0l, 0, EventMapper.TIWIPRO_EVENT_UNPLUGGED, eventTime, 0, odometer, locations[i].getLat(), locations[i].getLng());
                
            }
            else
            {
                event = new Event(0l, 0, EventMapper.TIWIPRO_EVENT_LOCATION,
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
        eventBytes[idx++] = (byte) (event.getType() & 0x000000FF);
        idx = puti4(eventBytes, idx, (int)(event.getTime().getTime()/1000l));
        if (event.getSats() == null || event.getSats().intValue() == 0)
        	event.setSats(10);
        eventBytes[idx++] = (byte) (event.getSats() & 0x000000FF);
        eventBytes[idx++] = (byte) 1; // maprev
        idx = putlat(eventBytes, idx, event.getLatitude());
        idx = putlng(eventBytes, idx, event.getLongitude());
        eventBytes[idx++] = (byte) (event.getSpeed() & 0x000000FF);
        idx = puti2(eventBytes, idx, event.getOdometer());

        
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
            
        }
        else if (event instanceof IdleEvent)
        {
            IdleEvent idleEvent = (IdleEvent)event;
            eventBytes[idx++] = (byte) (ATTR_TYPE_LO_IDLE & 0x000000FF);
            idx = puti4(eventBytes, idx, idleEvent.getLowIdle());
            eventBytes[idx++] = (byte) (ATTR_TYPE_HI_IDLE & 0x000000FF);
            idx = puti4(eventBytes, idx, idleEvent.getHighIdle());
        }
        else if (event.getType().equals(EventMapper.TIWIPRO_EVENT_COACHING_SPEEDING))
        {
            eventBytes[idx++] = (byte) (ATTR_SPEED_ID & 0x000000FF);
            idx = puti4(eventBytes, idx, 2); //DateUtil.convertMillisecondsToSeconds(event.getTime().getTime()));
        }

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
