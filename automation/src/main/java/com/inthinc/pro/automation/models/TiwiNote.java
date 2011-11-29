package com.inthinc.pro.automation.models;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.deviceEnums.DeviceAttrs;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.interfaces.DeviceTypes;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.StackToString;

public class TiwiNote implements DeviceNote {
    

    private final static Logger logger = Logger.getLogger(TiwiNote.class);

    private final Integer sats;
    private final Integer maprev;
    private final Integer Speed;
    private final Integer odometer;
    private final Heading heading;
    private final AutomationCalendar nTime;
    private final GeoPoint location;
    private final DeviceAttributes attrs;
    private final DeviceNoteTypes nType;
    

    public TiwiNote(DeviceNoteTypes type, DeviceState state, GeoPoint location){
        nType = type;
        nTime = state.copyTime();
        this.sats = state.getSats();
        this.heading = state.getHeading();
        this.maprev = state.getMapRev();
        this.location = location.copy();
        this.Speed = state.getSpeed();
        this.odometer = state.getOdometer();
        attrs = new DeviceAttributes();
    }
    
    
    
    public TiwiNote( DeviceNoteTypes type ){
        nType = type;
        nTime = new AutomationCalendar();
        sats = 0;
        heading = Heading.NORTH;
        maprev = 0;
        location = new GeoPoint(0.0,0.0);
        Speed = 0;
        odometer = 0;
        attrs = new DeviceAttributes();
    }
    
    public TiwiNote(){
        this(DeviceNoteTypes.LOCATION);
    }

    public void addAttr(DeviceAttrs id, Object value){
        int cast;
        if (value instanceof Integer){
            cast = (Integer) value;
        } else if (value instanceof DeviceTypes){
            cast = ((DeviceTypes) value).getCode();
        } else  if (value == null){
            cast = 0;
        } else {
            throw new IllegalArgumentException("Cannot add value of type: " + value.getClass());
        }
        attrs.addAttribute(id, cast);
    }
        
    public void addAttrs(DeviceAttributes attrs){
        for (DeviceAttrs key : attrs){
            addAttr(key, attrs.getValue(key));
        }
    }
    
    @Override
    public byte[] Package(){
        logger.debug(toString());
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        //Headers  Convert the value to an integer, then pack it as a byte in the stream
        NoteManager.longToByte(bos, nType.getCode(), 1);
        NoteManager.longToByte(bos, nTime.toInt(), 4);
        NoteManager.longToByte(bos, NoteManager.concatenateTwoInts(heading.getHeading(), sats), 1);
        NoteManager.longToByte(bos, maprev, 1);
        NoteManager.longToByte(bos, location.getEncodedLat(), 3);
        NoteManager.longToByte(bos, location.getEncodedLng(), 3);
        NoteManager.longToByte(bos, Speed, 1);
        NoteManager.longToByte(bos, odometer, 2);
        
        NoteManager.encodeAttributes(bos, attrs);
        
        return bos.toByteArray();   
    }
    
    public Map<String, String> packageToMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("10", nType.getCode() + "");
        map.put("11", nTime.toInt() + "");
        map.put("12", heading + ", " + sats);
        map.put("20", maprev + "");
        map.put("13", location.toString());
        map.put("15", Speed.toString());
        map.put("16", odometer.toString());
        Iterator<DeviceAttrs> itr = attrs.iterator();
        while (itr.hasNext()){
            DeviceAttrs next = itr.next();
            Object value = attrs.getValue(next);
            map.put(next.getCode().toString(), value.toString());
        }
        return map;
    }
    
    @Override
    public String toString(){
        String note = "";
        try{
            note = String.format("TiwiNote(nType=%s, nTime=\"%s\", sats=%d, heading=%s, maprev=%d, lat=%.5f, lng=%.5f, speed=%d, odometer=%d, attrs=%s)", 
                    nType, nTime, sats, heading, maprev, location.getLat(), location.getLng(), Speed, odometer, attrs);
        }catch(Exception e){
            logger.info(StackToString.toString(e));
            e.printStackTrace();
        }
        logger.debug(note);
        return note;
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
    public DeviceNote copy() {
        DeviceState state = new DeviceState(null, null);
        state.setHeading(heading);
        state.getTime().setDate(nTime);
        state.setMapRev(maprev);
        state.setSpeed(Speed);
        state.setOdometer(odometer);
        TiwiNote temp = new TiwiNote(nType, state, location);
        return temp;
    }
}
