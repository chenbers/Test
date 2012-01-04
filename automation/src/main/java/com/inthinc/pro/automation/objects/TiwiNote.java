package com.inthinc.pro.automation.objects;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.models.DeviceNote;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.event.EventAttr;

public class TiwiNote extends DeviceNote {
    

    public final Integer sats;
    public final Integer maprev;
    public final Integer Speed;
    public final Integer odometer;
    public final Heading heading;
    
    

    public TiwiNote(DeviceNoteTypes type, DeviceState state, GeoPoint location){
    	super(type, state.getTime(), location);
        this.sats = state.getSats();
        this.heading = state.getHeading();
        this.maprev = state.getMapRev();
        this.Speed = state.getSpeed();
        this.odometer = state.getOdometer();
    }
    
    
    
    public TiwiNote( DeviceNoteTypes type ){
    	super(type, new AutomationCalendar(), new GeoPoint(0.0, 0.0));
        sats = 0;
        heading = Heading.NORTH;
        maprev = 0;
        Speed = 0;
        odometer = 0;
    }
    

    
    @Override
    public byte[] Package(){
        MasterTest.print(toString(), Level.DEBUG);
        if (type.equals(DeviceNoteTypes.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA)){
        	MasterTest.print(this, Level.DEBUG);
        }
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        //Headers  Convert the value to an integer, then pack it as a byte in the stream
        NoteManager.longToByte(bos, type.getIndex(), 1);
        NoteManager.longToByte(bos, time.toInt(), 4);
        NoteManager.longToByte(bos, NoteManager.concatenateTwoInts(heading.getHeading(), sats), 1);
        NoteManager.longToByte(bos, maprev, 1);
        NoteManager.longToByte(bos, location.encodeLat(), 3);
        NoteManager.longToByte(bos, location.encodeLng(), 3);
        NoteManager.longToByte(bos, Speed, 1);
        NoteManager.longToByte(bos, odometer, 2);
        
        NoteManager.encodeAttributes(bos, attrs);
        
        if (type.equals(DeviceNoteTypes.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA)){
        	for (byte bits: bos.toByteArray()){
        		MasterTest.print(bits, Level.DEBUG);
        	}
        }
        
        return bos.toByteArray();   
    }
    
    public Map<String, String> packageToMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("10", type.getIndex() + "");
        map.put("11", time.toInt() + "");
        map.put("12", heading + ", " + sats);
        map.put("20", maprev + "");
        map.put("13", location.toString());
        map.put("15", Speed.toString());
        map.put("16", odometer.toString());
        Iterator<EventAttr> itr = attrs.iterator();
        while (itr.hasNext()){
            EventAttr next = itr.next();
            Object value = attrs.getValue(next);
            map.put(next.getIndex().toString(), value.toString());
        }
        return map;
    }
    
    @Override
    public String toString(){
        String note = "";
        note = String.format("TiwiNote(nType=%s, nTime=\"%s\", sats=%d, heading=%s, maprev=%d, lat=%.5f, lng=%.5f, speed=%d, odometer=%d, attrs=%s)", 
                type, time, sats, heading, maprev, location.getLat(), location.getLng(), Speed, odometer, attrs);
        MasterTest.print(note, Level.DEBUG);
        return note;
    }



    @Override
    public DeviceNote copy() {
        DeviceState state = new DeviceState(null, null);
        state.setHeading(heading);
        state.getTime().setDate(time);
        state.setMapRev(maprev);
        state.setSpeed(Speed);
        state.setOdometer(odometer);
        TiwiNote temp = new TiwiNote(type, state, location);
        return temp;
    }

	
    @Override
    public boolean equals(Object obj){
    	if (obj instanceof TiwiNote){
    		TiwiNote other = (TiwiNote) obj;
    		return sats == other.sats && maprev == other.maprev &&
    				Speed == other.Speed && odometer == other.odometer &&
    				heading.equals(other.heading) && time.equals(other.time) &&
    				location.equals(other.location) && type.equals(other.type);
    	} else {
    		return false;
    	}
    }
}
