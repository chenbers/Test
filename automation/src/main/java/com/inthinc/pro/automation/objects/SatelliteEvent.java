package com.inthinc.pro.automation.objects;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.models.DeviceNote;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.configurator.ProductType;

public class SatelliteEvent extends DeviceNote {
    
    public final static int nVersion = 2;
    public final Heading heading;
    public final int sats;
    public final int nSpeed;
    public final int odometer;
    public final int duration;

    
    public SatelliteEvent(DeviceNoteTypes type, DeviceState state,
            GeoPoint location) {

    	super(type, state.getTime(), location);
        this.heading = state.getHeading();
        this.sats = state.getSats();
        this.nSpeed = state.getSpeed();
        this.odometer = state.getOdometerX100() / 100;
        this.duration = 0;
    }
    
	
    @Override
    public byte[] Package() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0);
        longToByte(baos, type.getIndex(), 1);
        longToByte(baos, nVersion, 1);
        longToByte(baos, time.toInt(), 4);
        longToByte(baos, concatenateTwoInts(heading.getHeading(), sats), 1);
        longToByte(baos, location.encodeLat(), 3);
        longToByte(baos, location.encodeLng(), 3);
        longToByte(baos, nSpeed, 1);
        longToByte(baos, odometer, 3);
        longToByte(baos, duration, 2);
		encodeAttributes(baos, attrs, type.getAttributes());
        byte[] temp = baos.toByteArray();
        temp[0] = (byte) (temp.length & 0xFF);
        for (int i=0;i<temp.length;i++){
            MasterTest.print("Byte " + i + " = " + temp[i], Level.DEBUG);
        }
        return temp;
    }

    
    
    /**
     * private final DeviceNoteTypes nType;
    private final int nVersion;
    private final AutomationCalendar nTime;
    private final int heading;
    private final int sats;
    private final double fLongitude;
    private final double fLatitude;
    private final int nSpeed;
    private final int odometer;
     */
    
    @Override
    public String toString(){
        String temp = String.format("NoteWS(type=%s, version=%d, time=\"%s\", heading=%d, sats=%d,\n" +
                "lat=%.5f, lng=%.5f, speed=%d, odometer=%d,\n" +
                "attrs={%s}", 
                type, nVersion, time, heading.getHeading(), sats, location.getLat(), location.getLng(), nSpeed, odometer, attrs.toString());
        return temp;
    }
    

    
    @Override
    public SatelliteEvent copy(){
        DeviceState state = new DeviceState(null, ProductType.WAYSMART);
        state.getTime().setDate(time);
        state.setHeading(heading);
        state.setSats(sats);
        state.setSpeed(nSpeed);
        state.setOdometerX100(odometer);
        SatelliteEvent temp = new SatelliteEvent(type, state, location);
        temp.addAttrs(attrs);
        return temp;
    }

}
