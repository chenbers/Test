package com.inthinc.pro.selenium.testSuites;

import java.util.Arrays;

import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.FullEvent;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.IgnitionOffEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.model.event.ZoneArrivalEvent;
import com.inthinc.pro.model.event.ZoneDepartureEvent;
import com.inthinc.pro.model.event.ZoneEvent;

public class EventGen {
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

    // method used only for the note() method to create a note and simulate what they device
    // does -- this is currently only used to generate test data
    public static byte[] createDataBytesFromEvent(Event event)
    {
//        eventCount++;       
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
            idx = puti2(eventBytes, idx, randomInt(0, 1000));
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
            idx = puti2(eventBytes, idx, randomInt(0, 1000));
            
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
            eventBytes[idx++] = (byte) (ATTR_SPEED_ID & 0x000000FF);
            idx = puti4(eventBytes, idx, 2); //DateUtil.convertMillisecondsToSeconds(event.getTime().getTime()));
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
    
    public static int randomInt(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }    
}
