package com.inthinc.pro.automation.models;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.deviceEnums.Ways_ATTRS;
import com.inthinc.pro.automation.deviceEnums.Ways_SAT_EVENT;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.device_emulation.NoteManager.AttrTypes;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.interfaces.DeviceTypes;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.configurator.ProductType;

public class NoteWS implements DeviceNote {
    
    private final static Logger logger = Logger.getLogger(NoteWS.class);
    
    private final Ways_SAT_EVENT nType;
    private final ProductType nVersion;
    private final AutomationCalendar nTime;
    private final int heading;
    private final int sats;
    private final double fLatitude;
    private final double fLongitude;
    private final int nSpeed;
    private final int odometer;
    private final int duration;
    private final DeviceAttributes attrs;
    
    public NoteWS(Ways_SAT_EVENT nType, ProductType nVersion, AutomationCalendar nTime, int heading,
            int sats, double fLatitude, double fLongitude, int nSpeed, int odometer, int duration){
        this.nType = nType;
        this.nVersion = nVersion;
        this.nTime = nTime;
        this.heading = heading;
        this.sats = sats;
        this.fLatitude = fLatitude;
        this.fLongitude = fLongitude;
        this.nSpeed = nSpeed;
        this.odometer = odometer;
        this.duration = duration;
        attrs = new DeviceAttributes();
    }

    @Override
    public byte[] Package() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0);
        NoteManager.longToByte(baos, nType.getValue(), 1);
        NoteManager.longToByte(baos, nVersion.getVersion(), 1);
        NoteManager.longToByte(baos, nTime.toInt(), 4);
        NoteManager.longToByte(baos, NoteManager.concatenateTwoInts(heading, sats), 1);
        NoteManager.longToByte(baos, NoteManager.encodeLat(fLatitude), 3);
        NoteManager.longToByte(baos, NoteManager.encodeLng(fLongitude), 3);
        NoteManager.longToByte(baos, nSpeed, 1);
        NoteManager.longToByte(baos, odometer, 3);
        NoteManager.longToByte(baos, duration, 2);
        NoteManager.encodeAttributes(baos, attrs);
        byte[] temp = baos.toByteArray();
        temp[0] = (byte) (temp.length & 0xFF);
        for (int i=0;i<temp.length;i++){
            MasterTest.print("Byte " + i + " = " + temp[i]);
        }
        return temp;
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
        } else if (value instanceof String){
            addAttr(id, value, ((String)value).length());
        }
    }
    
    public void addAttr(Ways_ATTRS id, Object value, int size){
        if (value instanceof Number){
            addAttr(id, (Number) value, size);
        } else if (value instanceof DeviceTypes){
            addAttr(id, ((DeviceTypes) value).getValue(), size);
        } else {
            attrs.addAttribute(id, value, size);
        }
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
    private final int odometer;
     */
    
    @Override
    public String toString(){
        String temp = String.format("NoteBC(type=%s, version=%d, time=\"%s\", heading=%d, sats=%d,\n" +
                "lat=%.5f, lng=%.5f, speed=%d, odometer=%d,\n" +
                "attrs={%s}", 
                nType.toString(), nVersion.getVersion(), nTime, heading, sats, fLatitude, fLongitude, nSpeed, odometer, attrs.toString());
        return temp;
    }
    
}
