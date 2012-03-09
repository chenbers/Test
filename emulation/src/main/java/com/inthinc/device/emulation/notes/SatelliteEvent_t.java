package com.inthinc.device.emulation.notes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import android.util.Log;

import com.inthinc.device.emulation.enums.DeviceEnums.HOSFlags;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class SatelliteEvent_t extends DeviceNote {


	private final static int m_nVersion = 3; // pos 2, set to 3 -- all vars up
												// to this point must remain the
												// same for backwards
												// compatibility

	// pos 7, second flag: num of sats in lower nibble if gps locked, course in
	// the upper nibble
	public final boolean offRoad; // bit 0: ON/OFF road
	public final boolean heavyDuty; // bit 1: Light/Heavy duty vehicle
	public final HOSFlags hosState; // bit 2 - 4: HOS state
	public final boolean speedingViolation; // bit 5: Speeding violation
	public final boolean seatBeltViolation; // bit 6: Seatbelt Violation
	public final boolean rpmViolation; // bit 7: RPM violation

	public final int m_nLinkID; // pos 19, link ID
	public final int m_nBoundaryID; // pos 27, current boundary
	public final int m_nDriverIdSiloDbVersion; // pos 29, driverId
	

	public SatelliteEvent_t(DeviceNoteTypes type, AutomationCalendar time,
			GeoPoint location, boolean offRoad, boolean heavyDuty,
			HOSFlags hosFlags, boolean speedingViolation,
			boolean seatBeltViolation, boolean rpmViolation, Heading heading,
			int sats, int speed, int speedLimit, int linkID, int odometerX100,
			int stateID, int dbDriverID) {
		super(type, time, location);
		this.offRoad = offRoad;
		this.heavyDuty = heavyDuty;
		this.hosState = hosFlags;
		this.speedingViolation = speedingViolation;
		this.seatBeltViolation = seatBeltViolation;
		this.rpmViolation = rpmViolation;
		this.heading = heading;
		this.sats = sats;
		this.speed = speed;
		this.speedLimit = speedLimit;
		m_nLinkID = linkID;
		this.odometer = odometerX100;
		m_nBoundaryID = stateID;
		m_nDriverIdSiloDbVersion = dbDriverID;
	}
	
	public SatelliteEvent_t(DeviceNoteTypes type, Calendar time,
			GeoPoint location, boolean offRoad, boolean heavyDuty,
			HOSFlags hosFlags, boolean speedingViolation,
			boolean seatBeltViolation, boolean rpmViolation, Heading heading,
			int sats, int speed, int speedLimit, int linkID, int odometerX100,
			int stateID, int dbDriverID) {
		this(type, new AutomationCalendar(time), location, offRoad, heavyDuty, hosFlags, speedingViolation,
				seatBeltViolation, rpmViolation, heading, sats, speed, speedLimit, linkID, odometerX100,
				stateID, dbDriverID);
	}
	
	public SatelliteEvent_t(DeviceNoteTypes type, DeviceState state,
			GeoPoint location) {
		this(type, state.getTime(), location, state.isOffRoad(),
				state.isHeavyDuty(), state.getHosFlags(), state.isSpeeding(),
				state.isSeatbeltEngaged(), state.isExceedingRPMLimit(), state
						.getHeading(), state.getSats(), state.getSpeed(), state
						.getSpeedLimit(), state.getLinkID(), state
						.getOdometerX100(), state.getStateID(), state.getDriverID());
	}


	private byte b2i(boolean bool) {
		return (byte) (bool ? 1 & 0x1 : 0 & 0x1);
	}

	private int packFlags() {
		int first = ((b2i(offRoad) << 0) & 0x01)
				| ((b2i(heavyDuty) & 0x01) << 2)
				| ((hosState.getIndex() & 0x0C) << 2)
				| ((b2i(speedingViolation) << 5) & 0x10)
				| ((b2i(seatBeltViolation) & 0x20) << 6)
				| ((b2i(rpmViolation) & 0x40) << 7);
		int second = concatenateTwoInts(heading.getHeading(), sats);
		return ((first << Byte.SIZE) | second) & 0xFFFF;
	}
	

	@Override
	public byte[] Package() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		longToByte(baos, 0, 1); 						// pos 0, length of struct including this byte
		longToByte(baos, type.getIndex(), 1); 			// pos 1, defined below
		longToByte(baos, m_nVersion, 1); 				// pos 2, set to 3 -- all vars up to
														// this point must remain the same
														// for backwards compatibility
		longToByte(baos, time.toInt(), 4); 				// pos 3, time now
														// (GMT - seconds since 1/1/1970 00:00:00)
		longToByte(baos, packFlags(), 2); 				// pos 7, second flag: num of sats in
														// lower nibble if gps locked,
														// course in the upper nibble
		longToByte(baos, location.encodeLatBC(), 4); 	// pos 9, 4 bytes for lat
		longToByte(baos, location.encodeLngBC(), 4); 	// pos 13, 4 bytes for long
		longToByte(baos, speed, 1); 					// pos 17, speed in mph
		longToByte(baos, speedLimit, 1); 				// pos 18, speed limit in mph
		longToByte(baos, m_nLinkID, 4); 				// pos 19, link ID
		longToByte(baos, odometer, 4); 					// pos 23, distance since last
														// notification
		longToByte(baos, m_nBoundaryID, 2); 			// pos 27, current boundary
		longToByte(baos, m_nDriverIdSiloDbVersion, 4); 	// pos 29, driverId
		encodeAttributes(baos, getAttrs(), 2);

		byte[] temp = baos.toByteArray();
		temp[0] = (byte) (temp.length & 0xFF);

		for (int i = 0; i < temp.length; i++) {
			Log.d("Byte %d = %02X", i, (temp[i] & 0xFF));
		}

		return temp;
	}
	
	@Override
	public SatelliteEvent_t unPackage(byte[] packagedNote){
		return unPackageS(packagedNote);
	}
	
	public static SatelliteEvent_t unPackageS(byte[] packagedNote){
		ByteArrayInputStream bais = new ByteArrayInputStream(packagedNote);
		bais.read(); 	// pos 0, length of struct including this byte
		DeviceNoteTypes type = DeviceNoteTypes.valueOf(byteToInt(bais, 1));  
		
		int version = byteToInt(bais, 1);  // Note Type Version
		if ( version != m_nVersion) { // version == 3
			throw new IllegalArgumentException("Not a SatelliteEvent: nVersion is " + version); 
		}
		AutomationCalendar time = new AutomationCalendar(byteToLong(bais, 4) * 1000);
		
		int flags = bais.read(); // trip flags
		boolean offRoad = (flags & 0x01 )== 0x01;
		boolean heavyDuty = (flags & 0x02 )== 0x02;
		HOSFlags hosState = HOSFlags.valueOf((flags >> 3) & 0x07);
		boolean speedingViolation = (flags & 0x20 )== 0x20;
		boolean seatBeltViolation = (flags & 0x40 )== 0x40;
		boolean rpmViolation = (flags & 0x80) == 0x80;

		
		flags = bais.read(); // Heading and sats
		int sats = flags & 0x00FF;
		Heading heading = Heading.valueOf((flags >> 4) & 0x00FF);
		
		
		GeoPoint location = new GeoPoint();
		location.decodeLatBC(byteToLong(bais, 4)); // Latitude
		location.decodeLngBC(byteToLong(bais, 4)); // Longitude
		int speed = byteToInt(bais, 1);
		int speedLimit = byteToInt(bais, 1);
		int linkID = byteToInt(bais, 4);
		int odometer = byteToInt(bais, 4);
		int boundaryID = byteToInt(bais, 2);
		int driverID = byteToInt(bais, 4);

		SatelliteEvent_t note = new SatelliteEvent_t(type, time, location, 
				offRoad, heavyDuty, hosState, speedingViolation, 
				seatBeltViolation, rpmViolation, heading, sats, 
				speed, speedLimit, linkID, odometer, boundaryID, driverID);
		note.addAttrs(decodeAttributes(bais, 2));
		return note;
	}

	@Override
	public DeviceNote copy() {

		DeviceNote temp = new SatelliteEvent_t(type, time, location, offRoad,
				heavyDuty, hosState, speedingViolation, seatBeltViolation,
				rpmViolation, heading, sats, speed, speedLimit,
				m_nLinkID, odometer, m_nBoundaryID,
				m_nDriverIdSiloDbVersion);
		temp.addAttrs(attrs);
		return temp;
	}
	public SatelliteEvent_t copy(DeviceNoteTypes type, AutomationCalendar time, boolean copyAttr){
		SatelliteEvent_t result = new SatelliteEvent_t(type, time, location, offRoad,
				heavyDuty, hosState, speedingViolation, seatBeltViolation,
				rpmViolation, heading, sats, speed, speedLimit,
				m_nLinkID, odometer, m_nBoundaryID,
				m_nDriverIdSiloDbVersion);
		if(copyAttr)
			result.addAttrs(attrs);
		
		return result;
	}
	public SatelliteEvent_t copy(DeviceNoteTypes type, boolean copyAttr) {
		return copy(type, time, copyAttr);
	}
	
}
