package com.inthinc.pro.automation.models;

import java.io.ByteArrayOutputStream;

import com.inthinc.pro.automation.deviceEnums.DeviceAttrs;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.interfaces.DeviceTypes;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.model.configurator.ProductType;

public class NoteWS implements DeviceNote {
    
    private final DeviceNoteTypes nType;
    private final ProductType nVersion;
    private final AutomationCalendar nTime;
    private final Heading heading;
    private final int sats;
    private final GeoPoint location;
    private final int nSpeed;
    private final int odometer;
    private final int duration;
    private final DeviceAttributes attrs;

    
    public NoteWS(DeviceNoteTypes type, DeviceState state,
            GeoPoint currentLocation, int duration) {

        this.nType = type;
        this.nVersion = state.getProductVersion();
        this.nTime = state.copyTime();
        this.heading = state.getHeading();
        this.sats = state.getSats();
        this.location = currentLocation.copy();
        this.nSpeed = state.getSpeed();
        this.odometer = state.getOdometer();
        this.duration = duration;
        attrs = new DeviceAttributes();
    }

    @Override
    public byte[] Package() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0);
        NoteManager.longToByte(baos, nType.getCode(), 1);
        NoteManager.longToByte(baos, nVersion.getVersion(), 1);
        NoteManager.longToByte(baos, nTime.toInt(), 4);
        NoteManager.longToByte(baos, NoteManager.concatenateTwoInts(heading.getHeading(), sats), 1);
        NoteManager.longToByte(baos, location.getEncodedLat(), 3);
        NoteManager.longToByte(baos, location.getEncodedLng(), 3);
        NoteManager.longToByte(baos, nSpeed, 1);
        NoteManager.longToByte(baos, odometer, 3);
        NoteManager.longToByte(baos, duration, 2);
        NoteManager.encodeAttributes(baos, attrs);
        byte[] temp = baos.toByteArray();
        temp[0] = (byte) (temp.length & 0xFF);
//        for (int i=0;i<temp.length;i++){
//            MasterTest.print("Byte " + i + " = " + temp[i]);
//        }
        return temp;
    }

    

    public void addAttr(DeviceAttrs id, Number value){
        try {
            attrs.addAttribute(id, value);    
        } catch (Exception e) {
            
            throw new NullPointerException("Cannot add " + id + " with value " + value);
        }
        
    }
    
    public void addAttr(DeviceAttrs id, Object value){
        if (value instanceof Number){
            addAttr(id, (Number) value);
        } else if (value instanceof DeviceTypes){
            addAttr(id, ((DeviceTypes) value).getCode());
        } else if (value instanceof String){
            attrs.addAttribute(id, value);
        }
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
        String temp = String.format("NoteBC(type=%s, version=%d, time=\"%s\", heading=%d, sats=%d,\n" +
                "lat=%.5f, lng=%.5f, speed=%d, odometer=%d,\n" +
                "attrs={%s}", 
                nType.toString(), nVersion.getVersion(), nTime, heading.getHeading(), sats, location.getLat(), location.getLng(), nSpeed, odometer, attrs.toString());
        return temp;
    }
    

    @Override
    public DeviceNoteTypes getType() {
        return nType;
    }

    @Override
    public Long getTime() {
        return nTime.epochSeconds();
    }
    
    
}
