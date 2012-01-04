package com.inthinc.pro.automation.objects;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.deviceEnums.DeviceEnums.WSHOSState;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.models.DeviceNote;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.utils.MasterTest;

public class WSNoteVersion3 extends DeviceNote {


	private final static int m_nVersion = 3;	// pos 2, set to 3  --  all vars up to this point must remain the same for backwards compatibility
	
	// pos 7, second flag: num of sats in lower nibble if gps locked, course in the upper nibble
	private final boolean offRoad; 				// bit  0:  	ON/OFF road
	private final boolean heavyDuty; 			// bit  1: 		Light/Heavy duty vehicle
	private final WSHOSState hosState; 			// bit  2 -  4: HOS state
	private final boolean speedingViolation;	// bit  5: 		Speeding violation
	private final boolean seatBeltViolation; 	// bit  6: 		Seatbelt Violation
	private final boolean rpmViolation; 		// bit  7: 		RPM violation
	private final Heading heading;				// bit  8 - 12: Heading
	private final int sats;						// bit 13 - 16: Number of Sats with lock
	
	private final int m_nSpeed;					// pos 17, speed in mph
	private final Integer m_nSpeedLimit;		// pos 18, speed limit in mph
	private final int m_nLinkID;				// pos 19, link ID
    private final int m_nOdometer100x;     		// pos 23, distance since last notification
	private final int m_nBoundaryID;			// pos 27, current boundary
	private final int m_nDriverIdSiloDbVersion;	// pos 29, driverId
	

	public WSNoteVersion3(DeviceNoteTypes type, DeviceState state,
			GeoPoint location) {
    	super(type, state.getTime(), location);
		offRoad = state.isOffRoad();
		heavyDuty = state.isHeavyDuty();
		hosState = state.getHOSState();
		speedingViolation = state.isSpeeding();
		seatBeltViolation = state.isSeatbeltEngaged();
		rpmViolation = state.isExceedingRPMLimit();
		heading = state.getHeading();
		sats = state.getSats();
		m_nSpeed = state.getSpeed();
		m_nSpeedLimit = state.getSpeedLimit().intValue();
		m_nLinkID = state.getLinkID();
		m_nOdometer100x = state.getOdometer();
		m_nBoundaryID = state.getStateID();
		m_nDriverIdSiloDbVersion = state.getDriverID();
	}
	
	private byte b2i(boolean bool){
		return (byte) (bool ? 1 & 0x1:0 & 0x1);
	}
	
	private int packFlags(){
		int first = ((b2i(offRoad) << 0) & 0x01) | ((b2i(heavyDuty) & 0x01) << 2) | 
				((hosState.getIndex() & 0x0C) << 2) | ((b2i(speedingViolation) << 5) & 0x10) | 
				((b2i(seatBeltViolation) & 0x20) << 6) | ((b2i(rpmViolation) & 0x40) << 7);
		int second = NoteManager.concatenateTwoInts(heading.getHeading(), sats);
		return ((first << Byte.SIZE) | second) & 0xFFFF;
	}

	@Override
	public byte[] Package() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		NoteManager.longToByte(baos, 0, 1);							// pos 0, length of struct including this byte
		NoteManager.longToByte(baos, type.getIndex(), 1);			// pos 1, defined below
		NoteManager.longToByte(baos, m_nVersion, 1);				// pos 2, set to 3  --  all vars up to this point must remain the same for backwards compatibility
		NoteManager.longToByte(baos, time.toInt(), 4);				// pos 3, time now (GMT - seconds since 1/1/1970 00:00:00)
		NoteManager.longToByte(baos, packFlags(), 2);				// pos 7, second flag: num of sats in lower nibble if gps locked, course in the upper nibble
		NoteManager.longToByte(baos, location.encodeLatBC(), 4);	// pos 9, 4 bytes for lat
		NoteManager.longToByte(baos, location.encodeLngBC(), 4);	// pos 13, 4 bytes for long
		NoteManager.longToByte(baos, m_nSpeed, 1);					// pos 17, speed in mph
		NoteManager.longToByte(baos, m_nSpeedLimit, 1);				// pos 18, speed limit in mph
		NoteManager.longToByte(baos, m_nLinkID, 4);					// pos 19, link ID
		NoteManager.longToByte(baos, m_nOdometer100x, 4);			// pos 23, distance since last notification
		NoteManager.longToByte(baos, m_nBoundaryID, 2);				// pos 27, current boundary
		NoteManager.longToByte(baos, m_nDriverIdSiloDbVersion, 4); 	// pos 29, driverId
		NoteManager.encodeAttributes(baos, getAttrs());
		
		byte[] temp = baos.toByteArray();
        temp[0] = (byte) (temp.length & 0xFF);
        
        for (int i=0;i<temp.length;i++){
            MasterTest.print("Byte " + i + " = " + temp[i], Level.DEBUG);
        }
        
        return temp;
	}

	@Override
	public DeviceNote copy() {
		DeviceState state = new DeviceState(null, null);
		state.getTime().setDate(time);
		state.setOffRoad(offRoad);
		state.setHeavyDuty(heavyDuty);
		state.setHosState(hosState);
		state.setSpeeding(speedingViolation);
		state.setSeatbeltEngaged(seatBeltViolation);
		state.setExceedingRPMLimit(rpmViolation);
		state.setHeading(heading);
		state.setSats(sats);
		state.setSpeed(m_nSpeed);
		state.setSpeedLimit(m_nSpeedLimit);
		state.setLinkID(m_nLinkID);
		state.setOdometer(m_nOdometer100x);
		state.setBoundaryID(m_nBoundaryID);
		state.setDriverID(m_nDriverIdSiloDbVersion);
		
		DeviceNote temp = new WSNoteVersion3(type, state, location);
	    temp.addAttrs(attrs);
	    return temp;
	}
}
