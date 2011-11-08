package com.inthinc.pro.automation.models;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.deviceEnums.TiwiAttrs;
import com.inthinc.pro.automation.deviceEnums.TiwiNoteTypes;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.interfaces.DeviceTypes;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.StackToString;

public class TiwiNote implements DeviceNote {
    

    private final static Logger logger = Logger.getLogger(TiwiNote.class);

    private final Integer sats, heading, maprev, Speed, odometer;
    private final AutomationCalendar nTime;
    private final Double lat, lng;
    private final DeviceAttributes attrs;
    private final TiwiNoteTypes nType;
    

    public TiwiNote(TiwiNoteTypes type, AutomationCalendar time, int sats, int Heading, int Maprev, Double Lat, Double Lng, int speed, int Odometer){
        nType = type;
        nTime = time;
        this.sats = sats;
        this.heading = Heading;
        this.maprev = Maprev;
        this.lat = Lat;
        this.lng = Lng;
        this.Speed = speed;
        this.odometer = Odometer;
        attrs = new DeviceAttributes();
    }
    
    
    
    public TiwiNote( TiwiNoteTypes type ){
        nType = type;
        nTime = new AutomationCalendar();
        sats = 0;
        heading = 0;
        maprev = 0;
        lat = 0.0;
        lng = 0.0;
        Speed = 0;
        odometer = 0;
        attrs = new DeviceAttributes();
    }
    
    public TiwiNote(){
        this(TiwiNoteTypes.NOTE_TYPE_LOCATION);
    }

    public void addAttr(TiwiAttrs id, Object value){
        int cast;
        if (value instanceof Integer){
            cast = (Integer) value;
        } else if (value instanceof TiwiAttrs){
            cast = ((TiwiAttrs) value).getValue();
        } else {
            throw new IllegalArgumentException("Cannot add value of type: " + value.getClass());
        }
        attrs.addAttribute(id, cast);
    }
        
    public void addAttrs(Map<TiwiAttrs, Integer> atttrs){
        TiwiAttrs attrID;
        Iterator<TiwiAttrs> itr = atttrs.keySet().iterator();
        while (itr.hasNext()){
            attrID = itr.next();
            addAttr(attrID, atttrs.get(attrID));
        }
    }
    
    @Override
    public byte[] Package(){
        logger.debug(toString());
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        //Headers  Convert the value to an integer, then pack it as a byte in the stream
        NoteManager.longToByte(bos, nType.getValue(), 1);
        NoteManager.longToByte(bos, nTime.toInt(), 4);
        NoteManager.longToByte(bos, NoteManager.concatenateTwoInts(heading, sats), 1);
        NoteManager.longToByte(bos, maprev, 1);
        NoteManager.longToByte(bos, NoteManager.encodeLat(lat), 3);
        NoteManager.longToByte(bos, NoteManager.encodeLng(lng), 3);
        NoteManager.longToByte(bos, Speed, 1);
        NoteManager.longToByte(bos, odometer, 2);
        
        NoteManager.encodeAttributes(bos, attrs);
        
        return bos.toByteArray();   
    }
    
    public Map<String, String> packageToMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("10", nType.getValue() + "");
        map.put("11", nTime.toInt() + "");
        map.put("12", heading + ", " + sats);
        map.put("20", maprev + "");
        map.put("13", "(" + lat + "," + lng + ")");
        map.put("15", Speed.toString());
        map.put("16", odometer.toString());
        Iterator<DeviceTypes> itr = attrs.iterator();
        while (itr.hasNext()){
            DeviceTypes next = itr.next();
            Object value = attrs.getValue(next);
            map.put(next.getValue().toString(), value.toString());
        }
        return map;
    }
    
    @Override
    public String toString(){
        String note = "";
        try{
            note = String.format("TiwiNote(nType=%d, nTime=\"%s\", sats=%d, heading=%d, maprev=%d, lat=%.5f, lng=%.5f, speed=%d, odometer=%d, attrs=%s)", 
                    nType.getValue(), nTime, sats, heading, maprev, lat, lng, Speed, odometer, attrs);
        }catch(Exception e){
            logger.info(StackToString.toString(e));
            e.printStackTrace();
        }
        logger.debug(note);
        return note;
    }

}
