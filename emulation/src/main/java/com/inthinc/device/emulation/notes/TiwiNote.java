package com.inthinc.device.emulation.notes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.objects.DeviceAttributes;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class TiwiNote extends DeviceNote {
    

    public final Integer maprev;

    
    public TiwiNote(DeviceNoteTypes type, AutomationCalendar time, GeoPoint location, 
    		Heading heading, int sats, int maprev, int speed, int odometerX100){
    	super(type, time, location);
        this.sats = sats;
        this.heading = heading;
        this.maprev = maprev;
        this.speed = speed;
        this.odometer = odometerX100;
    }
    
    public TiwiNote(DeviceNoteTypes type, Calendar time, GeoPoint location, 
    		Heading heading, int sats, int maprev, int speed, int odometerX100){
    	this(type, new AutomationCalendar(time), location, heading, sats,
    			maprev, speed, odometerX100);
    }
    
    
    
    public TiwiNote( DeviceNoteTypes type, GeoPoint location){
    	this(type, new AutomationCalendar(), location, Heading.NORTH, 0, 0, 0, 0);
    }
    
    public TiwiNote(DeviceNoteTypes type, DeviceState state, GeoPoint location) {
		this(type, state.getTime(), location, state.getHeading(), 
				state.getSats(), state.getMapRev(), state.getSpeed(), 
				state.getOdometerX100());
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
        longToByte(bos, speed, 1);
        longToByte(bos, odometer, 2);
        
        encodeAttributes(bos, attrs, 1);
        
        
        return bos.toByteArray();   
    }
    

	@Override
	public DeviceNote unPackage(byte[] packagedNote) {
		return unPackageS(packagedNote);
	}
	
	public static TiwiNote unPackageS(byte[] packagedNote){
		ByteArrayInputStream bais = new ByteArrayInputStream(packagedNote);
		DeviceNoteTypes type = DeviceNoteTypes.valueOf(byteToInt(bais, 1));
		AutomationCalendar time = new AutomationCalendar(byteToLong(bais, 4) * 1000);
		int flags = byteToInt(bais, 1);
		Heading heading = Heading.valueOf((flags >> 4) & 0x00FF);
		int sats = flags & 0x00FF;
		int maprev = byteToInt(bais, 1);
		GeoPoint location = new GeoPoint(0.0,0.0);
		location.decodeLat(byteToLong(bais, 3));
		location.decodeLng(byteToLong(bais, 3));
		int speed = byteToInt(bais, 1);
		int odometer = byteToInt(bais, 2);
		DeviceAttributes attrs = decodeAttributes(bais, 1);
		TiwiNote note = new TiwiNote(type, time, location, heading, sats, maprev, speed, odometer);
		note.addAttrs(attrs);
		return note;
	}
    
    
    
    public Map<String, String> packageToMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("10", type.getIndex() + "");
        map.put("11", time.toInt() + "");
        map.put("12", heading + ", " + sats);
        map.put("20", maprev + "");
        map.put("13", location.toString());
        map.put("15", speed.toString());
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
                type, time, sats, heading, maprev, location.getLat(), location.getLng(), speed, odometer, attrs);
        return note;
    }



    @Override
    public DeviceNote copy() {
        TiwiNote temp = new TiwiNote(type, time, location, heading, sats, maprev, speed, odometer);
        temp.addAttrs(attrs);
        return temp;
    }

	
    @Override
    public boolean equals(Object obj){
    	if (obj instanceof TiwiNote){
    		TiwiNote other = (TiwiNote) obj;
    		return sats.equals(other.sats) && maprev.equals(other.maprev) &&
    				speed.equals(other.speed) && odometer.equals(other.odometer) &&
    				heading.equals(other.heading) && time.equals(other.time) &&
    				location.equals(other.location) && type.equals(other.type);
    	} else {
    		return false;
    	}
    }


    @Override
    public int hashCode(){
        return location.hashCode();
    }


}
