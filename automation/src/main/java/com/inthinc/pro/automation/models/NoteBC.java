package com.inthinc.pro.automation.models;

import java.io.ByteArrayOutputStream;

import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.deviceEnums.Ways_ATTRS;
import com.inthinc.pro.automation.deviceEnums.Ways_SAT_EVENT;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.device_emulation.NoteManager.AttrTypes;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.interfaces.DeviceTypes;
import com.inthinc.pro.automation.utils.AutomationCalendar;

public class NoteBC implements DeviceNote {
    
    private final Ways_SAT_EVENT nType;
    private final int nVersion;
    private final AutomationCalendar nTime;
    private final Heading heading;
    private final int sats;
    private final int nSpeed;
    private final int nOdometer;
    private final int nSpeedLimit;
    private final int nLinkID;
    private final int nBoundaryID;
    private final int nDriverID;
    private final DeviceAttributes attrs;
    private final GeoPoint location;
    
    public static enum Direction implements DeviceTypes{
        wifi(3),
        gprs(2),
        sat(1)
        ;
        
        private int direction;
        
        private Direction(int direction){
            this.direction = direction;
        }
        
        @Override
        public Integer getValue() {
            return direction;
        }
    };
    
    
    /***
     * Pack the values into a note.
     * 1-type
     * 1-version
     * 4-time
     * 2-flags
     * 4-lat
     * 4-lng
     * 1-speed
     * 1-speedLimit
     * 4-linkID
     * 4-odometer
     * 2-boundaryID
     * 4-driverID
     * ---  
     * 32 byte header, followed by attribute ID/value pairs...
     ***/
    @Override
    public byte[] Package(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        //Headers  Convert the value to an integer, then pack it as a byte in the stream
        NoteManager.longToByte(bos, nType.getValue(), 1);
        NoteManager.longToByte(bos, nVersion, 1);
        NoteManager.longToByte(bos, nTime.toInt(), 4);
        NoteManager.longToByte(bos, NoteManager.concatenateTwoInts(heading.getHeading(), sats), 2);
        NoteManager.longToByte(bos, location.getEncodedLat(), 4);
        NoteManager.longToByte(bos, location.getEncodedLng(), 4);
        NoteManager.longToByte(bos, nSpeed, 1);
        NoteManager.longToByte(bos, nSpeedLimit, 1);
        NoteManager.longToByte(bos, nLinkID, 4);
        NoteManager.longToByte(bos, nOdometer, 4);
        NoteManager.longToByte(bos, nBoundaryID, 2);
        NoteManager.longToByte(bos, nDriverID, 4);
        
        NoteManager.encodeAttributes(bos, attrs);
        
        return bos.toByteArray();
    }
    
    /**
     * @param type
     * @param method
     * @param server
     * @param mcm
     * @param imei
     
    
    * 1-type
    * 1-version
    * 4-time
    * 2-flags
    * 4-lat
    * 4-lng
    * 1-speed
    * 1-speedLimit
    * 4-linkID
    * 4-odometer
    * 2-boundaryID
    * 4-driverID
    */
    public NoteBC(Ways_SAT_EVENT type, DeviceState state, GeoPoint location){
        nType = type;
        nVersion = state.getProductVersion().getVersion();
        nTime = state.copyTime();
        heading = state.getHeading();
        sats = state.getSats();
        this.location = location.copy();
        nSpeed = state.getSpeed();
        nSpeedLimit = state.getSpeed_limit().intValue();
        nLinkID = state.getLinkID();
        nOdometer = state.getOdometer();
        nBoundaryID = state.getBoundaryID();
        nDriverID = state.getDeviceDriverID();
        attrs = new DeviceAttributes();
    }

    
    public void addAttr(Ways_ATTRS id, Number value, int size){
        attrs.addAttribute(id, value, size);
    }
    
    public void addAttr(Ways_ATTRS id, Number value){
        try {
            attrs.addAttribute(id, value, AttrTypes.getType(id).getSize());    
        } catch (Exception e) {
            
            throw new NullPointerException("Cannot add " + id + " with value " + value);
        }
        
    }
    
    public void addAttr(Ways_ATTRS id, Object value){
        if (value instanceof Number){
            addAttr(id, (Number) value);
        } else if (value instanceof DeviceTypes){
            addAttr(id, ((DeviceTypes) value).getValue());
        }
    }
    
    public void addAttr(Ways_ATTRS id, Object value, int size){
        if (value instanceof Number){
            addAttr(id, (Number) value, size);
        } else if (value instanceof DeviceTypes){
            addAttr(id, ((DeviceTypes) value).getValue(), size);
        }
    }
    
    public void addAttr(Ways_ATTRS id, String value){
        attrs.addAttribute(id, value, 0);
    }
    
    /**
     * private final Ways_SAT_EVENT nType;
    private final int nVersion;
    private final AutomationCalendar nTime;
    private final int heading;
    private final int sats;
    private final double fLongitude;
    private final double fLatitude;
    private final int nSpeed;
    private final int nOdometer;
    private final int nSpeedLimit;
    private final int nLinkID;
    private final int nBoundaryID;
    private final int nDriverID;
     */
    
    @Override
    public String toString(){
        String temp = String.format("NoteBC(type=%s, version=%d, time=\"%s\", heading=%d, sats=%d,\n" +
        		"lat=%.5f, lng=%.5f, speed=%d, odometer=%d, speedLimit=%d, linkID=%d, boundary=%d, driverID=%d,\n" +
        		"attrs={%s}", 
                nType.toString(), nVersion, nTime, heading, sats, location.getLat(), location.getLng(), nSpeed, nOdometer, nSpeedLimit, nLinkID, nBoundaryID, nDriverID, attrs.toString());
        return temp;
    }
    

}
