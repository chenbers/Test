package com.inthinc.device.emulation.notes;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Level;

import com.inthinc.device.emulation.enums.DeviceEnums.WSHOSState;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.notes.DeviceNote.SatOnly;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;

public class SatelliteEvent_t extends DeviceNote implements SatOnly {


	private final static int m_nVersion = 3; // pos 2, set to 3 -- all vars up
												// to this point must remain the
												// same for backwards
												// compatibility

	// pos 7, second flag: num of sats in lower nibble if gps locked, course in
	// the upper nibble
	private final boolean offRoad; // bit 0: ON/OFF road
	private final boolean heavyDuty; // bit 1: Light/Heavy duty vehicle
	private final WSHOSState hosState; // bit 2 - 4: HOS state
	private final boolean speedingViolation; // bit 5: Speeding violation
	private final boolean seatBeltViolation; // bit 6: Seatbelt Violation
	private final boolean rpmViolation; // bit 7: RPM violation
	private final Heading heading; // bit 8 - 12: Heading
	private final int sats; // bit 13 - 16: Number of Sats with lock

	private final int m_nSpeed; // pos 17, speed in mph
	private final Integer m_nSpeedLimit; // pos 18, speed limit in mph
	private final int m_nLinkID; // pos 19, link ID
	private final int m_nOdometer100x; // pos 23, distance since last
										// notification
	private final int m_nBoundaryID; // pos 27, current boundary
	private final int m_nDriverIdSiloDbVersion; // pos 29, driverId

	public SatelliteEvent_t(DeviceNoteTypes type, AutomationCalendar time,
			GeoPoint location, boolean offRoad, boolean heavyDuty,
			WSHOSState hosState, boolean speedingViolation,
			boolean seatBeltViolation, boolean rpmViolation, Heading heading,
			int sats, int speed, int speedLimit, int linkID, int odometerX100,
			int stateID, int dbDriverID) {
		super(type, time, location);
		this.offRoad = offRoad;
		this.heavyDuty = heavyDuty;
		this.hosState = hosState;
		this.speedingViolation = speedingViolation;
		this.seatBeltViolation = seatBeltViolation;
		this.rpmViolation = rpmViolation;
		this.heading = heading;
		this.sats = sats;
		m_nSpeed = speed;
		m_nSpeedLimit = speedLimit;
		m_nLinkID = linkID;
		m_nOdometer100x = odometerX100;
		m_nBoundaryID = stateID;
		m_nDriverIdSiloDbVersion = dbDriverID;
	}
	
	public SatelliteEvent_t(DeviceNoteTypes type, DeviceState state,
			GeoPoint location) {
		this(type, state.getTime(), location, state.isOffRoad(),
				state.isHeavyDuty(), state.getHOSState(), state.isSpeeding(),
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
		longToByte(baos, m_nSpeed, 1); 					// pos 17, speed in mph
		longToByte(baos, m_nSpeedLimit, 1); 			// pos 18, speed limit in mph
		longToByte(baos, m_nLinkID, 4); 				// pos 19, link ID
		longToByte(baos, m_nOdometer100x, 4); 			// pos 23, distance since last
														// notification
		longToByte(baos, m_nBoundaryID, 2); 			// pos 27, current boundary
		longToByte(baos, m_nDriverIdSiloDbVersion, 4); // pos 29, driverId
		encodeAttributes(baos, getAttrs());

		byte[] temp = baos.toByteArray();
		temp[0] = (byte) (temp.length & 0xFF);

		for (int i = 0; i < temp.length; i++) {
			MasterTest.print("Byte %d = %02X", Level.INFO, i,
					(temp[i] & 0xFF));
		}

		return temp;
	}

	@Override
	public DeviceNote copy() {

		DeviceNote temp = new SatelliteEvent_t(type, time, location, offRoad,
				heavyDuty, hosState, speedingViolation, seatBeltViolation,
				rpmViolation, heading, sats, m_nSpeed, m_nSpeedLimit,
				m_nLinkID, m_nOdometer100x, m_nBoundaryID,
				m_nDriverIdSiloDbVersion);
		temp.addAttrs(attrs);
		return temp;
	}

}
