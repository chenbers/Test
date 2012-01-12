package com.inthinc.pro.automation.models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.objects.NoteBC;
import com.inthinc.pro.automation.objects.SatelliteEvent;
import com.inthinc.pro.automation.objects.SatelliteEvent_t;
import com.inthinc.pro.automation.objects.TiwiNote;
import com.inthinc.pro.automation.objects.WaysmartDevice.Direction;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.EventAttr;

public abstract class DeviceNote {
	
	protected final AutomationCalendar time;
	protected final DeviceNoteTypes type;
	protected final GeoPoint location;
	protected final DeviceAttributes attrs;
	
	public interface SatOnly{
		
	}
	
	@Override
	public String toString(){
		return String.format("%s(NoteType: %s, Time: '%s', Location: %s, Attrs: %s)", this.getClass().getSimpleName(), type, time, location, attrs);
	}
	
	public DeviceNote (DeviceNoteTypes type, AutomationCalendar time, GeoPoint location){
		this.type = type;
		this.time = time.copy();
		this.location = location.copy();
		attrs = new DeviceAttributes();
	}
	
    public GeoPoint getLocation(){
		return location.copy();
	}
	
    public DeviceNoteTypes getType(){
    	return type;
    }
    
    public AutomationCalendar getTime(){
    	return time;
    }
    

	public void addAttr(EventAttr id, Integer value) {
		try {
			attrs.addAttribute(id, value);    
        } catch (Exception e) {
            throw new NullPointerException("Cannot add " + id + " with value " + value);
        }
	}

	public void addAttrs(DeviceAttributes attrs) {
		for (EventAttr key : attrs){
            addAttr(key, attrs.getValue(key));
        }
	}
	

	public void addAttr(EventAttr id, Object value) {
		if (value instanceof Number || value instanceof String){
        	attrs.addAttribute(id, value);
        } else if (value instanceof IndexEnum){
            addAttr(id, ((IndexEnum) value).getIndex());
        } else if (value instanceof Boolean){
        	addAttr(id, (Boolean) value ? 1:0);
        } else if (value instanceof AutomationCalendar){
        	addAttr(id, ((AutomationCalendar)value).toInt());
        } else if (value == null){
        	addAttr(id, 0);
        } else {
            throw new IllegalArgumentException("Cannot add value of type: " + value.getClass());
        }
	}
    
    
    public static DeviceNote constructNote(DeviceNoteTypes type, GeoPoint location, DeviceState state) {
        DeviceNote note = null;
        
        if (location == null){
        	location = new GeoPoint(0.0, 0.0);
        }
        
        if (state == null){
        	note = new TiwiNote(type);        	
        } else if (state.getProductVersion().equals(ProductType.WAYSMART)){
        	if (state.getWaysDirection().equals(Direction.sat)){
        		note = new SatelliteEvent_t(type, state, location);
        	}
        	else if (NoteBC.types.contains(type)){
                note = new NoteBC(type, state, location);
            } else {
                note = new SatelliteEvent(type, state, location);
            }
        } else {
            note = new TiwiNote(type, state, location);
            note.addAttr(EventAttr.SPEED_LIMIT, state.getSpeedLimit());
            state.setOdometerX100(0);
        }
        
        if (state.getViolationFlags() != 0x00){
        	note.addAttr(EventAttr.VIOLATION_FLAGS, state.getViolationFlags());	
        }
        
        return note;
    }

	public DeviceAttributes getAttrs() {
		return attrs;
	}
	

    public static Integer byteToInt(ByteArrayInputStream bais, int numOfBytes) {
        return byteToLong(bais, numOfBytes).intValue();
    }

    public static Long byteToLong(ByteArrayInputStream bais, int numOfBytes) {
        Long number = 0l;
        for (int shift = (numOfBytes-1) * Byte.SIZE; shift >= 0; shift -= Byte.SIZE) {
            number |= (bais.read() & 0xFF) << shift;
        }
        return number;
    }
    
    public static Long byteToLong(byte[] ba, int offset, int numOfBytes) {
        Long number = 0l;
        for (int shift = (numOfBytes-1) * Byte.SIZE; shift >= 0; shift -= Byte.SIZE) {
            number |= (ba[offset++] & 0xFF) << shift;
        }
        return number;
    }
    

    public static Integer byteToInt(byte[] ba, int offset, int numOfBytes) {
        return byteToLong(ba, offset, numOfBytes).intValue();
    }
    
    public static void longToByte(ByteArrayOutputStream baos, Long toAdd, int numOfBytes){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        byte[] array = null;
        
        try {
            dos.writeLong(toAdd);
            dos.flush();
            array = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=array.length-numOfBytes; i<array.length;i++){
            baos.write(array[i]);
        }
    }
    
    public static void longToByte(ByteArrayOutputStream baos, Integer toAdd, int numOfBytes){
        longToByte(baos, toAdd.longValue(), numOfBytes);
    }
    
    
    
    public static int concatenateTwoInts(int one, int two){
        MasterTest.print(one + "   " + two, Level.DEBUG);
        int value = (int)(  one << 4)  & 0xF0 | two & 0x0F;
        return value;
    }

    
    
    /**
     * @param baos
     * @param attrs 
     */
    public static void encodeAttributes(ByteArrayOutputStream baos, DeviceAttributes attrs) {
        Iterator<EventAttr> keys = attrs.iterator();
        
        while( keys.hasNext()){
            EventAttr key=null;

            key = keys.next();
            int keyCode = key.getIndex();
            if (keyCode < Math.pow(2, 1*Byte.SIZE)){
                longToByte(baos, keyCode, 1);    
            } else if (keyCode < Math.pow(2, 2*Byte.SIZE)){
                longToByte(baos, keyCode, 2);   
            } else if (keyCode < Math.pow(2, 3*Byte.SIZE)){
                longToByte(baos, keyCode, 3);   
            }
            encodeAttribute(baos, key, attrs.getValue(key));
        }
    }
    
    private static void encodeAttribute(ByteArrayOutputStream baos, EventAttr key, Object object) {
        try {
            if (object instanceof Number){
                if (object instanceof Integer){
                    longToByte(baos, ((Integer)object).longValue(), key.getSize());
                } else if (object instanceof Long){
                    longToByte(baos, (Long)object, key.getSize());    
                }
            } else if (object instanceof String){
                byte[] str = ((String)object).getBytes();
                int size = key.getSize();
                baos.write(str, 0, str.length);
                if (key.isZeroTerminated()){
                    baos.write(0x0);
                } else {
                    size -= str.length;
                    for (int i=0;i<size;i++){
                        baos.write(0x0);
                    }
                }
            }
        } catch (NullPointerException e){
            MasterTest.print("Key: " + key + " Value: " + object, Level.ERROR);
            MasterTest.print(e, Level.ERROR);
        }
    }
    
    public static void encodeAttributes(ByteArrayOutputStream baos, DeviceAttributes attrs, EventAttr[] attrList) {
        for (EventAttr attr : attrList){
            encodeAttribute(baos, attr, attrs.getValue(attr));
        }
    }

    public static Object byteToInt(List<Byte> m_pData, int numOfBytes) {
        byte[] temp = new byte[m_pData.size()];
        for (int i=0;i<m_pData.size();i++){
            temp[i] = m_pData.get(i);
        }
        return byteToInt(temp, 0, numOfBytes);
    }

	

    
    public abstract byte[] Package();
    public abstract DeviceNote copy();
	        
	        
}
