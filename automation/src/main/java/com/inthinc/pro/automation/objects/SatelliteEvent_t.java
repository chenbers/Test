package com.inthinc.pro.automation.objects;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.Deflater;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceEnums.WSHOSState;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.DeviceProps;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.models.DeviceNote;
import com.inthinc.pro.automation.models.DeviceNote.SatOnly;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.utils.MasterTest;

public class SatelliteEvent_t extends DeviceNote implements SatOnly {

	public final static double PACKET_SIZE = 270.0;

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

	private Map<DeviceProps, String> settings;
	

	public SatelliteEvent_t(DeviceNoteTypes type, DeviceState state,
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
		m_nOdometer100x = state.getOdometerX100();
		m_nBoundaryID = state.getStateID();
		m_nDriverIdSiloDbVersion = state.getDriverID();
	}
	
	public void addSettings(Map<DeviceProps, String> settings){
		this.settings = settings;
	}
	
	private byte b2i(boolean bool){
		return (byte) (bool ? 1 & 0x1:0 & 0x1);
	}
	
	private int packFlags(){
		int first = ((b2i(offRoad) << 0) & 0x01) | ((b2i(heavyDuty) & 0x01) << 2) | 
				((hosState.getIndex() & 0x0C) << 2) | ((b2i(speedingViolation) << 5) & 0x10) | 
				((b2i(seatBeltViolation) & 0x20) << 6) | ((b2i(rpmViolation) & 0x40) << 7);
		int second = concatenateTwoInts(heading.getHeading(), sats);
		return ((first << Byte.SIZE) | second) & 0xFFFF;
	}

	@Override
	public byte[] Package() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		longToByte(baos, 0, 1);							// pos 0, length of struct including this byte
		longToByte(baos, type.getIndex(), 1);			// pos 1, defined below
		longToByte(baos, m_nVersion, 1);				// pos 2, set to 3  --  all vars up to this point must remain the same for backwards compatibility
		longToByte(baos, time.toInt(), 4);				// pos 3, time now (GMT - seconds since 1/1/1970 00:00:00)
		longToByte(baos, packFlags(), 2);				// pos 7, second flag: num of sats in lower nibble if gps locked, course in the upper nibble
		longToByte(baos, location.encodeLatBC(), 4);	// pos 9, 4 bytes for lat
		longToByte(baos, location.encodeLngBC(), 4);	// pos 13, 4 bytes for long
		longToByte(baos, m_nSpeed, 1);					// pos 17, speed in mph
		longToByte(baos, m_nSpeedLimit, 1);				// pos 18, speed limit in mph
		longToByte(baos, m_nLinkID, 4);					// pos 19, link ID
		longToByte(baos, m_nOdometer100x, 4);			// pos 23, distance since last notification
		longToByte(baos, m_nBoundaryID, 2);				// pos 27, current boundary
		longToByte(baos, m_nDriverIdSiloDbVersion, 4); 	// pos 29, driverId
		if (type.equals(DeviceNoteTypes.STRIPPED_CONFIGURATOR)){
			byte[] bits =  compressSettings(this.settings);
			baos.write(bits, 0, bits.length);
		} else {
			encodeAttributes(baos, getAttrs());
		}
		
		byte[] temp = baos.toByteArray();
        temp[0] = (byte) (temp.length & 0xFF);
        
        for (int i=0;i<temp.length;i++){
            MasterTest.print("Byte %d = %02X", Level.INFO, i, (temp[i] & 0xFF));
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
		state.setOdometerX100(m_nOdometer100x);
		state.setBoundaryID(m_nBoundaryID);
		state.setDriverID(m_nDriverIdSiloDbVersion);
		
		DeviceNote temp = new SatelliteEvent_t(type, state, location);
	    temp.addAttrs(attrs);
	    return temp;
	}
	

	public static byte[] compressSettings(Map<DeviceProps, String> map) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Iterator<DeviceProps> itr = map.keySet().iterator();
		while (itr.hasNext()){
			DeviceProps key = itr.next();
			int keyVal = key.getIndex();
			if (keyVal < 128){
				longToByte(baos, keyVal, 1);
			} else if (keyVal < 16384){
				longToByte(baos, keyVal, 2);
			} else if (keyVal < 2097152){
				longToByte(baos, keyVal, 3);
			}
			addString(baos, map.get(key));
		}
		return compressPacket(baos.toByteArray());
	}
	
	private static void addString(ByteArrayOutputStream baos, String value){
		baos.write(value.getBytes(), 0, value.length());
		baos.write(0x0);
	}



	private static byte[] compressPacket(byte[] bytes) {
		Deflater deflater = new Deflater();
		deflater.setLevel(Deflater.BEST_COMPRESSION);
		deflater.setInput(bytes);
		deflater.finish();

		ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);

		byte[] buffer = new byte[1024];

		while (!deflater.finished()) {
			int bytesCompressed = deflater.deflate(buffer);
			bos.write(buffer, 0, bytesCompressed);
		}

		try {
			bos.close();
		} catch (IOException ioe) {
			MasterTest.print(ioe, Level.FATAL);
		}

		// get the compressed byte array from output stream
		return bos.toByteArray();
	}
}
