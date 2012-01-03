package com.inthinc.pro.automation.objects;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.models.DeviceNote;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.model.configurator.ProductType;

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
//        types.add(DeviceNoteTypes.INSTALL);
        types.add(DeviceNoteTypes.IGNITION_ON);
        types.add(DeviceNoteTypes.IGNITION_OFF);
        
        types.add(DeviceNoteTypes.LOCATION);
        
        types.add(DeviceNoteTypes.NEWDRIVER_HOSRULE);
    }
    
    
    /***
     * Pack the values into a note.
     * 1-type
     * 1-version
     * 4-time
     * 2-flags
     * 4-lat
     * 4-lng
     * 1-speed
     * 1-speedLimit
     * 4-linkID
     * 4-odometer
     * 2-boundaryID
     * 4-driverID
     * ---  
     * 32 byte header, followed by attribute ID/value pairs...
     ***/
    @Override
    public byte[] Package(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        //Headers  Convert the value to an integer, then pack it as a byte in the stream
        NoteManager.longToByte(bos, type.getIndex(), 1);
        NoteManager.longToByte(bos, 2, 1);
        NoteManager.longToByte(bos, time.toInt(), 4);
        NoteManager.longToByte(bos, NoteManager.concatenateTwoInts(heading.getHeading(), sats), 2);
        NoteManager.longToByte(bos, location.encodeLatBC(), 4);
        NoteManager.longToByte(bos, location.encodeLngBC(), 4);
        NoteManager.longToByte(bos, nSpeed, 1);
        NoteManager.longToByte(bos, nSpeedLimit, 1);
        NoteManager.longToByte(bos, nLinkID, 4);
        NoteManager.longToByte(bos, nOdometer, 4);
        NoteManager.longToByte(bos, nBoundaryID, 2);
        NoteManager.longToByte(bos, nDriverID, 4);
        
        NoteManager.encodeAttributes(bos, attrs);
        
        return bos.toByteArray();
    }
    
    /**
     * @param type
     * @param method
     * @param server
     * @param mcm
     * @param imei
     
    
    * 1-type
    * 1-version
    * 4-time
    * 2-flags
    * 4-lat
    * 4-lng
    * 1-speed
    * 1-speedLimit
    * 4-linkID
    * 4-odometer
    * 2-boundaryID
    * 4-driverID
    */
    public NoteBC(DeviceNoteTypes type, DeviceState state, GeoPoint location){
    	super(type, state.getTime(), location);
        heading = state.getHeading();
        sats = state.getSats();
        nSpeed = state.getSpeed();
        nSpeedLimit = state.getSpeedLimit().intValue();
        nLinkID = state.getLinkID();
        nOdometer = state.getOdometer();
        nBoundaryID = state.getBoundaryID();
        nDriverID = state.getDriverID();
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
        DeviceState state = new DeviceState(null, ProductType.WAYSMART);
        state.getTime().setDate(time);
        state.setHeading(heading);
        state.setSats(sats);
        state.setSpeed(nSpeed);
        state.setSpeedLimit(nSpeedLimit.doubleValue());
        state.setLinkID(nLinkID);
        state.setOdometer(nOdometer);
        state.setBoundaryID(nBoundaryID);
        state.setDriverID(nDriverID);
        NoteBC temp = new NoteBC(type, state, location);
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
