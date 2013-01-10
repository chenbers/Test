package com.inthinc.device.emulation.notes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.objects.AutomationCalendar;


/**
 * 1-type <br />
 * 1-version <br />
 * 4-time <br />
 * 2-flags <br />
 * 4-lat <br />
 * 4-lng <br />
 * 1-speed <br />
 * 1-speedLimit <br />
 * 4-linkID <br />
 * 4-odometer <br />
 * 2-boundaryID <br />
 * 4-driverID <br />
 * 
 */
public class NoteBC extends DeviceNote {
    
    public int getnLinkID() {
		return nLinkID;
	}


	public void setnLinkID(int nLinkID) {
		this.nLinkID = nLinkID;
	}


	public int getnBoundaryID() {
		return nBoundaryID;
	}


	public void setnBoundaryID(int nBoundaryID) {
		this.nBoundaryID = nBoundaryID;
	}


	public int getnDriverID() {
		return nDriverID;
	}


	public void setnDriverID(int nDriverID) {
		this.nDriverID = nDriverID;
	}


	public static List<DeviceNoteTypes> getTypes() {
		return types;
	}


	private int nLinkID;
    private int nBoundaryID;
    private int nDriverID;
    private final static int nVersion = 3;
    
    private final static List<DeviceNoteTypes> types = new ArrayList<DeviceNoteTypes>();
    
    static {
        types.add(DeviceNoteTypes.INSTALL);
    }
    
    
    /***
     * Pack the values into a note. <br />
     * 1-type <br />
     * 1-version <br />
     * 4-time <br />
     * 2-flags <br />
     * 4-lat <br />
     * 4-lng <br />
     * 1-speed <br />
     * 1-speedLimit <br />
     * 4-linkID <br />
     * 4-odometer <br />
     * 2-boundaryID <br />
     * 4-driverID <br />
     * ---   <br />
     * 32 byte header, followed by attribute ID/value pairs...
     ***/
    @Override
    public byte[] Package(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        //Headers  Convert the value to an integer, then pack it as a byte in the stream
        longToByte(bos, type.getIndex(), 1);
        longToByte(bos, nVersion, 1);
        longToByte(bos, time.toInt(), 4);
        longToByte(bos, heading.getHeading(), 1);
        longToByte(bos, sats, 1);
        longToByte(bos, location.encodeLatBC(), 4);
        longToByte(bos, location.encodeLngBC(), 4);
        longToByte(bos, speed, 1);
        longToByte(bos, speedLimit, 1);
        longToByte(bos, nLinkID, 4);
        longToByte(bos, odometer, 4);
        longToByte(bos, nBoundaryID, 2);
        longToByte(bos, nDriverID, 4);
        
        encodeAttributes(bos, attrs, 2);
        
        return bos.toByteArray();
    }
    
    public NoteBC unPackage(byte[] packagedNote){
    	return unPackageS(packagedNote);
    }
    
    public static NoteBC unPackageS(byte[] packagedNote){
    	ByteArrayInputStream bais = new ByteArrayInputStream(packagedNote);
    	DeviceNoteTypes type = DeviceNoteTypes.valueOf(byteToInt(bais, 1)); // Note Type
    	byteToInt(bais, 1); // Note Version
    	AutomationCalendar time = new AutomationCalendar(byteToLong(bais, 4) * 1000); // Time
    	Heading heading = Heading.valueOf(byteToInt(bais, 1));
    	int sats = byteToInt(bais, 1);
    	GeoPoint location = new GeoPoint(0.0,0.0);
    	location.decodeLatBC(byteToLong(bais, 4));
    	location.decodeLngBC(byteToLong(bais, 4));
    	int speed = byteToInt(bais, 1);
    	int speedLimit = byteToInt(bais, 1);
    	int nLinkID = byteToInt(bais, 4);
    	int odometer = byteToInt(bais, 4);
    	int nBoundaryID = byteToInt(bais, 2);
    	int nDriverID = byteToInt(bais, 4);
        NoteBC note = new NoteBC(type, time, location, heading, sats, speed, 
        		speedLimit, nLinkID, odometer, nBoundaryID, nDriverID);
        
        note.addAttrs(decodeAttributes(bais, 2));
        
        return note;
        
    }
    


	/**
	 * 1-type <br />
	 * 1-version <br />
	 * 4-time <br />
	 * 2-flags <br />
	 * 4-lat <br />
	 * 4-lng <br />
	 * 1-speed <br />
	 * 1-speedLimit <br />
	 * 4-linkID <br />
	 * 4-odometer <br />
	 * 2-boundaryID <br />
	 * 4-driverID <br />
	 * 
	 */
    public NoteBC(DeviceNoteTypes type, AutomationCalendar time, GeoPoint location, 
    		Heading heading, int sats, int speed, int speedLimit, int linkID, 
    		int odometerX100, int boundaryID, int driverID){
    	super(type, time, location);
        this.heading = heading;
        this.sats = sats;
        this.speed = speed;
        this.speedLimit = speedLimit;
        nLinkID = linkID;
        odometer = odometerX100;
        nBoundaryID = boundaryID;
        nDriverID = driverID;
    }
    
	/**
	 * 1-type <br />
	 * 1-version <br />
	 * 4-time <br />
	 * 2-flags <br />
	 * 4-lat <br />
	 * 4-lng <br />
	 * 1-speed <br />
	 * 1-speedLimit <br />
	 * 4-linkID <br />
	 * 4-odometer <br />
	 * 2-boundaryID <br />
	 * 4-driverID <br />
	 * 
	 */
    public NoteBC(DeviceNoteTypes type, Calendar time, GeoPoint location, 
    		Heading heading, int sats, int speed, int speedLimit, 
    		int linkID, int odometerX100, int boundaryID, int driverID){
    	this(type, new AutomationCalendar(time), location, heading, sats, 
    			speed, speedLimit, linkID, odometerX100, boundaryID, driverID);
    }
    
    public NoteBC(DeviceNoteTypes type, DeviceState state, GeoPoint location) {
		this(type, state.getTime(), location,
				state.getHeading(), state.getSats(), state.getSpeed(), state
						.getSpeedLimit(), state.getLinkID(), state
						.getOdometerX100(), state.getBoundaryID(), state
						.getDriverID());
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
    private final int nOdometer;
    private final int nSpeedLimit;
    private final int nLinkID;
    private final int nBoundaryID;
    private final int nDriverID;
     */
    
    @Override
    public String toString(){
        String temp = String.format("NoteBC(type=%s, time=\"%s\", heading=%s, sats=%d,\n" +
        		"lat=%.5f, lng=%.5f, speed=%d, odometer=%d, speedLimit=%d, linkID=%d, boundary=%d, driverID=%d,\n" +
        		"attrs=%s", 
                type.toString(), time, heading, sats, location.getLat(), location.getLng(), speed, odometer, speedLimit, nLinkID, nBoundaryID, nDriverID, attrs.toString());
        return temp;
    }

    
    @Override
    public NoteBC copy(){
        NoteBC temp = new NoteBC(type, time, location, heading, sats, speed, speedLimit, nLinkID, odometer, nBoundaryID, nDriverID);
        temp.addAttrs(attrs);
        return temp;
    }

    public NoteBC copy(DeviceNoteTypes type, AutomationCalendar time, boolean copyAttr){
        NoteBC result = new NoteBC(type, time, location, heading, sats, speed, speedLimit, nLinkID, odometer, nBoundaryID, nDriverID);
        if(copyAttr)
            result.addAttrs(attrs);
        
        return result;
    }
    
    public  NoteBC copy(DeviceNoteTypes type, boolean copyAttr) {
        return copy(type, time, copyAttr);
    }

	@Override
	public boolean equals(Object obj){
		if (obj instanceof NoteBC){
			NoteBC other = (NoteBC) obj;
			return type.equals(other.type) &&
					time.equals(other.time) && heading.equals(other.heading) &&
					sats.equals(other.sats) && speed.equals(other.speed) && 
					odometer.equals(other.odometer) && speedLimit.equals(other.speedLimit) &&
					location.equals(other.location);
			
		}else {
			return false;	
		}
	}
	
	@Override
	public int hashCode(){
	    return location.hashCode();
	}

}
