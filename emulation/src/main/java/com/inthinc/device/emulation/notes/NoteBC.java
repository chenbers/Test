package com.inthinc.device.emulation.notes;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
    
    public final Heading heading;
    public final int sats;
    public final int nSpeed;
    public final int nOdometer;
    public final Integer nSpeedLimit;
    public final int nLinkID;
    public final int nBoundaryID;
    public final int nDriverID;
    
    public final static List<DeviceNoteTypes> types = new ArrayList<DeviceNoteTypes>();
    
    static {
        types.add(DeviceNoteTypes.INSTALL);
        types.add(DeviceNoteTypes.IGNITION_ON);
        types.add(DeviceNoteTypes.IGNITION_OFF);
        
        types.add(DeviceNoteTypes.LOCATION);
        
        types.add(DeviceNoteTypes.NEWDRIVER_HOSRULE);
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
        longToByte(bos, 2, 1);
        longToByte(bos, time.toInt(), 4);
        longToByte(bos, concatenateTwoInts(heading.getHeading(), sats), 2);
        longToByte(bos, location.encodeLatBC(), 4);
        longToByte(bos, location.encodeLngBC(), 4);
        longToByte(bos, nSpeed, 1);
        longToByte(bos, nSpeedLimit, 1);
        longToByte(bos, nLinkID, 4);
        longToByte(bos, nOdometer, 4);
        longToByte(bos, nBoundaryID, 2);
        longToByte(bos, nDriverID, 4);
        
        encodeAttributes(bos, attrs);
        
        return bos.toByteArray();
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
    public NoteBC(DeviceNoteTypes type, AutomationCalendar time, GeoPoint location, Heading heading, int sats, int speed, int speedLimit, int linkID, int odometerX100, int boundaryID, int driverID){
    	super(type, time, location);
        this.heading = heading;
        this.sats = sats;
        nSpeed = speed;
        nSpeedLimit = speedLimit;
        nLinkID = linkID;
        nOdometer = odometerX100;
        nBoundaryID = boundaryID;
        nDriverID = driverID;
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
                type.toString(), time, heading, sats, location.getLat(), location.getLng(), nSpeed, nOdometer, nSpeedLimit, nLinkID, nBoundaryID, nDriverID, attrs.toString());
        return temp;
    }

    
    @Override
    public NoteBC copy(){
        NoteBC temp = new NoteBC(type, time, location, heading, sats, nSpeed, nSpeedLimit, nLinkID, nOdometer, nBoundaryID, nDriverID);
        temp.addAttrs(attrs);
        return temp;
    }


	@Override
	public boolean equals(Object obj){
		if (obj instanceof NoteBC){
			NoteBC other = (NoteBC) obj;
			return type.equals(other.type) &&
					time.equals(other.time) && heading.equals(other.heading) &&
					sats == other.sats && nSpeed == other.nSpeed && 
					nOdometer == other.nOdometer && nSpeedLimit == other.nSpeedLimit &&
					location.equals(other.location);
			
		}else {
			return false;	
		}
		
	}
}
