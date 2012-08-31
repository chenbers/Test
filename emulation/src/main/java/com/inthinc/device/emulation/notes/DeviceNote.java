package com.inthinc.device.emulation.notes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.objects.DeviceAttributes;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public abstract class DeviceNote implements Comparable<DeviceNote> {
	
	
	
	protected final AutomationCalendar time;
	protected final DeviceNoteTypes type;
	protected final GeoPoint location;
	protected final DeviceAttributes attrs;

	protected Integer odometer;
	protected Integer speed;
	protected Integer speedLimit;
	protected Heading heading;
	protected Integer sats;
	
	
	public static final List<DeviceNoteTypes> endOfTripNotes = new ArrayList<DeviceNoteTypes>();
	
	static {
		endOfTripNotes.add(DeviceNoteTypes.IGNITION_OFF);
		endOfTripNotes.add(DeviceNoteTypes.UNPLUGGED);
		endOfTripNotes.add(DeviceNoteTypes.UNPLUGGED_WHILE_ASLEEP);
		endOfTripNotes.add(DeviceNoteTypes.RF_KILL);
		endOfTripNotes.add(DeviceNoteTypes.LOW_POWER_MODE);
	}
	
	
	@Override
	public String toString(){
		return String.format("%s(NoteType: %s, Time: '%s', Location: %s, Odometer: %d,\n" +
				"Speed: %d, SpeedLimit: %d, Heading: %s,\n Attrs: %s)", 
				this.getClass().getSimpleName(), type, time, location, odometer, speed, speedLimit, heading, attrs);
	}
	
	public DeviceNote (DeviceNoteTypes type, AutomationCalendar time, GeoPoint location){
		this.type = type;
		this.time = time.copy();
		this.time.setFormat(WebDateFormat.NOTE_PRECISE_TIME);
		this.location = location == null ? null : location.copy();
		attrs = new DeviceAttributes();
	}
	
    public GeoPoint getLocation(){
		return location;
	}
    
    public int getOdometer() {
		return odometer;
	}

	public void setOdometer(int odometer) {
		this.odometer = odometer;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}

	public Heading getHeading() {
		return heading;
	}

	public void setHeading(Heading heading) {
		this.heading = heading;
	}

	public int getSats() {
		return sats;
	}

	public void setSats(int sats) {
		this.sats = sats;
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
		attrs.addAttribute(id, value);
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
            

            for (int i=array.length-numOfBytes; i<array.length;i++){
                baos.write(array[i]);
            }
            
        } catch (IOException e) {
            Log.error("%s", e);
        }
    }
    
    public static void doubleToByte(ByteArrayOutputStream baos, Double toAdd){
        DataOutputStream dos = new DataOutputStream(baos);
        
        try {
            dos.writeDouble(toAdd);
            dos.flush();
        } catch (IOException e) {
            Log.error("%s", e);
        }
    }
    
    
    public static double byteToDouble(ByteArrayInputStream bais){
        DataInputStream dis = new DataInputStream(bais);
        try {
            return dis.readDouble();
        } catch (IOException e) {
            Log.error("%s", e);
        }
        return 0.0;
    }
    
    public static void longToByte(ByteArrayOutputStream baos, Integer toAdd, int numOfBytes){
        longToByte(baos, toAdd.longValue(), numOfBytes);
    }
    
    
    
    public static int concatenateTwoInts(int one, int two){
        int value = (int)(  one << 2)  & 0xF0 | two & 0x0F;
        return value;
    }

    
    
    /**
     * @param baos
     * @param attrs 
     */
    public static void encodeAttributes(ByteArrayOutputStream baos, DeviceAttributes attrs, int keySize) {
        Iterator<EventAttr> keys = attrs.iterator();
        
        while( keys.hasNext()){
            EventAttr key = keys.next();
            int keyCode = key.getIndex();
            longToByte(baos, keyCode, keySize);    
            encodeAttribute(baos, key, attrs.getValue(key));
        }
    }
    
    public static void encodeAttribute(ByteArrayOutputStream baos, EventAttr key, Object object) {
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
        	
        }
    }
    


	public static DeviceAttributes decodeAttributes(ByteArrayInputStream bais, Integer keySize) {
		DeviceAttributes attrs = new DeviceAttributes();
		for (int i = bais.available(); i>0; i = bais.available()){
		    int attrKey = byteToInt(bais, keySize);
			EventAttr key = EventAttr.valueOf(attrKey);
			if (key==null){
			    readAttribute(bais, attrKey); // Since we can't add the key, just read in the 
			                                // array, but we can't use it.
			} else {
			    decodeAttribute(bais, key, attrs);
			}
		}
		return attrs;
	}
	
	public static DeviceAttributes decodeAttributes(ByteArrayInputStream bais,
			EventAttr[] attributes) {
		DeviceAttributes attrs = new DeviceAttributes();
		for (EventAttr key: attributes){
			decodeAttribute(bais, key, attrs);
		}
		return attrs;
	}
	
	public static void readAttribute(ByteArrayInputStream bais, int key){
	    if (EventAttr.getAttrSize(key) <= Integer.SIZE/Byte.SIZE){
            byteToInt(bais, EventAttr.getAttrSize(key));
        } else {
            byteToDouble(bais);
        }
	}
	
	public static void decodeAttribute(ByteArrayInputStream bais, EventAttr key, DeviceAttributes attrs){
		if(key == null){
			throw new IllegalArgumentException("Key cannot be null");
		}
		if (key.isString()){
			StringWriter writer = new StringWriter();
			if (key.isZeroTerminated()){
				char next = (char) bais.read();
				while (next != 0x0){
					writer.append(next);
					next = (char) bais.read();
				}
			} else {
				for (int i=0; i<key.getSize(); i++){
					writer.append((char) bais.read());
				}
			}
			attrs.addAttribute(key, writer.toString());
		} else {
		    if (key.getSize() <= Integer.SIZE/Byte.SIZE){
		        attrs.addAttribute(key, byteToInt(bais, key.getSize()));
		    } else {
		        attrs.addAttribute(key, byteToDouble(bais));
		    }
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

    
    

    public static DeviceNote constructNote(DeviceNoteTypes type, GeoPoint location, DeviceState state) {
    	DeviceNote note = null;
        
        if (location == null){
        	location = new GeoPoint();
        }
        
        if (state == null){
        	note = new TiwiNote(type, location);        	
        } else if (state.getProductVersion().equals(ProductType.WAYSMART)){
//        	if (state.getWaysDirection().equals(Direction.sat)){
        		note = new SatelliteEvent_t(type, state, location);
//        	} else if (NoteBC.getTypes().contains(type)){
//                note = new NoteBC(type, state, location);
//            } else {
//            	note = new SatelliteEvent_t(type, state, location);
//            }
        } else {
            note = new TiwiNote(type, state, location);
            note.addAttr(EventAttr.SPEED_LIMIT, state.getSpeedLimit());
            state.setOdometerX100(0);
        }
        
        if (state != null) 
            if (state.getViolationFlags() != 0x00 && note.getClass().equals(TiwiNote.class)){
                note.addAttr(EventAttr.VIOLATION_FLAGS, state.getViolationFlags());	
            }
        
        return note;
    }
	
    
    public abstract byte[] Package();
    public abstract DeviceNote copy();

	public abstract DeviceNote unPackage(byte[] packagedNote);
	
	
	public static DeviceNote unPackageS(byte[] packagedNote){
		DeviceNote note = null;
		try {
			note = SatelliteEvent_t.unPackageS(packagedNote);
		} catch (IllegalArgumentException e1){
			try {
				note = SatelliteEvent.unPackageS(packagedNote);
			} catch (IllegalArgumentException e2){
				try {
					note = TiwiNote.unPackageS(packagedNote);	
				} catch (IllegalArgumentException e3){
					note = SatelliteStrippedConfigurator.unPackageS(packagedNote);
				}
				
			}
		}
		return note;
	}
	

    public int compareTo(DeviceNote o){
    	return time.compareTo(o.getTime());
    }
}
