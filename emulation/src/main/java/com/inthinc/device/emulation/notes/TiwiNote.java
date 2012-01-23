package com.inthinc.device.emulation.notes;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class TiwiNote extends DeviceNote {
    

    public final Integer sats;
    public final Integer maprev;
    public final Integer Speed;
    public final Integer odometer;
    public final Heading heading;
    
    

    public TiwiNote(DeviceNoteTypes type, AutomationCalendar time, GeoPoint location, Heading heading, int sats, int maprev, int speed, int odometerX100){
    	super(type, time, location);
        this.sats = sats;
        this.heading = heading;
        this.maprev = maprev;
        this.Speed = speed;
        this.odometer = odometerX100;
    }
    
    
    
    public TiwiNote( DeviceNoteTypes type ){
    	super(type, new AutomationCalendar(), new GeoPoint(0.0, 0.0));
        sats = 0;
        heading = Heading.NORTH;
        maprev = 0;
        Speed = 0;
        odometer = 0;
    }
    
    public TiwiNote(DeviceNoteTypes type, DeviceState state, GeoPoint location) {
		this(type, state.getTime(), location,
				state.getHeading(), state.getSats(), state.getMapRev(), state
						.getSpeed(), state.getOdometerX100());
	}
    

    
    @Override
    public byte[] Package(){
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        //Headers  Convert the value to an integer, then pack it as a byte in the stream
        longToByte(bos, type.getIndex(), 1);
        longToByte(bos, time.toInt(), 4);
        longToByte(bos, concatenateTwoInts(heading.getHeading(), sats), 1);
        longToByte(bos, maprev, 1);
        longToByte(bos, location.encodeLat(), 3);
        longToByte(bos, location.encodeLng(), 3);
        longToByte(bos, Speed, 1);
        longToByte(bos, odometer, 2);
        
        encodeAttributes(bos, attrs);
        
        
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
        return note;
    }



    @Override
    public DeviceNote copy() {
        TiwiNote temp = new TiwiNote(type, time, location, heading, sats, maprev, Speed, odometer);
        temp.addAttrs(attrs);
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
