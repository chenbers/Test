package com.inthinc.pro.automation.models;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceAttrs;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.configurator.ProductType;

public class NoteWS extends DeviceNote {
    
    public final DeviceNoteTypes nType;
    public final int nVersion;
    public final AutomationCalendar nTime;
    public final Heading heading;
    public final int sats;
    public final GeoPoint location;
    public final int nSpeed;
    public final int odometer;
    public final int duration;
    public final DeviceAttributes attrs;

    
    public NoteWS(DeviceNoteTypes type, DeviceState state,
            GeoPoint currentLocation) {

        this.nType = type;
        this.nVersion = 3;
        this.nTime = state.copyTime();
        this.heading = state.getHeading();
        this.sats = state.getSats();
        this.location = currentLocation.copy();
        this.nSpeed = state.getSpeed();
        this.odometer = state.getOdometer();
        this.duration = 0;
        attrs = new DeviceAttributes();
    }

    @Override
    public byte[] Package() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0);
        NoteManager.longToByte(baos, nType.getIndex(), 1);
        NoteManager.longToByte(baos, nVersion, 1);
        NoteManager.longToByte(baos, nTime.toInt(), 4);
        NoteManager.longToByte(baos, NoteManager.concatenateTwoInts(heading.getHeading(), sats), 1);
        NoteManager.longToByte(baos, location.encodeLat(), 3);
        NoteManager.longToByte(baos, location.encodeLng(), 3);
        NoteManager.longToByte(baos, nSpeed, 1);
        NoteManager.longToByte(baos, odometer, 3);
        NoteManager.longToByte(baos, duration, 2);
        NoteManager.encodeAttributes(baos, attrs, nType.getAttributes());
        byte[] temp = baos.toByteArray();
        temp[0] = (byte) (temp.length & 0xFF);
        for (int i=0;i<temp.length;i++){
            MasterTest.print("Byte " + i + " = " + temp[i], Level.DEBUG);
        }
        return temp;
    }

    
    @Override
    public void addAttr(DeviceAttrs id, Integer value){
        try {
            attrs.addAttribute(id, value);    
        } catch (Exception e) {
            throw new NullPointerException("Cannot add " + id + " with value " + value);
        }
        
    }
    
    @Override
    public void addAttr(DeviceAttrs id, Object value){
        if (value instanceof Integer){
            addAttr(id, (Integer) value);
        } else if (value instanceof IndexEnum){
            addAttr(id, ((IndexEnum) value).getIndex());
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
        String temp = String.format("NoteWS(type=%s, version=%d, time=\"%s\", heading=%d, sats=%d,\n" +
                "lat=%.5f, lng=%.5f, speed=%d, odometer=%d,\n" +
                "attrs={%s}", 
                nType.toString(), nVersion, nTime, heading.getHeading(), sats, location.getLat(), location.getLng(), nSpeed, odometer, attrs.toString());
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
    
    @Override
    public NoteWS copy(){
        DeviceState state = new DeviceState(null, ProductType.WAYSMART);
        state.getTime().setDate(nTime);
        state.setHeading(heading);
        state.setSats(sats);
        state.setSpeed(nSpeed);
        state.setOdometer(odometer);
        NoteWS temp = new NoteWS(nType, state, location);
        temp.addAttrs(attrs);
        return temp;
    }

    @Override
    public void addAttrs(DeviceAttributes attrs){
        for (DeviceAttrs key : attrs){
            addAttr(key, attrs.getValue(key));
        }
    }

	@Override
	public GeoPoint getLocation() {
		return location.copy();
	}
    
    
}
