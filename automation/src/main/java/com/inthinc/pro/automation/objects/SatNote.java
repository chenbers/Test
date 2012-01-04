package com.inthinc.pro.automation.objects;

import java.io.ByteArrayOutputStream;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.models.DeviceAttributes;
import com.inthinc.pro.automation.models.DeviceNote;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.model.event.EventAttr;

public class SatNote extends DeviceNote {

	private final static int imeiLength = 15;
	private final static int headerLength = 37;
	
	private final String satIMEI;
	private final DeviceNote payload;
	private final AutomationCalendar sessionTime;
	
	
	
	public SatNote(DeviceNote payload, String imei) {
		super(payload.getType(), payload.getTime(), payload.getLocation());
		this.payload = payload;
		this.satIMEI = imei;
		this.sessionTime = new AutomationCalendar();
	}

	@Override
	public byte[] Package() {
		byte[] payload = this.payload.Package();
		int messageLength = headerLength + payload.length;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        NoteManager.longToByte(baos, 1, 1);    					// protocol revision number
        NoteManager.longToByte(baos, messageLength, 2); 		// Message Length
		NoteManager.longToByte(baos, 1, 1);    					// IEI
		NoteManager.longToByte(baos, headerLength, 2);			// Header Length
		NoteManager.longToByte(baos, 1234, 4); 					// CDR
		baos.write(satIMEI.getBytes(), 0, imeiLength);			// IMEI goes here 10-24
		NoteManager.longToByte(baos, 1, 1); 					// status
		NoteManager.longToByte(baos, 2, 2); 					// momsn
		NoteManager.longToByte(baos, 3, 2); 					// mtmsn
		NoteManager.longToByte(baos, sessionTime.toInt(), 4);	// session time
		NoteManager.longToByte(baos, 1, 1); 					// payload iei
		NoteManager.longToByte(baos, payload.length, 2);		// Payload Length
		baos.write(payload, 0, payload.length);					// Payload
		
		return baos.toByteArray();
	}
	
	// bit definitions for the second byte of m_nFlags
	/********************************************************************
	 * 
	 * first flags byte bit 0 to 7
	bit 0:  ON/OFF road
	On road = 0,
	Off road = 1,

	bit 1: Light/Heavy duty vehicle
	light duty: 0,
	heavy duty: 1,

	bit 2 - 4: HOS state
	value: state
	0: Off-Duty
	1: Off-Duty Well Site (otherwise known as waiting)
	2: Sleeper Berth
	3: Personal Use
	4: Driving
	5: On-Duty Not Driving


	bit 5: Speeding violation
	Currently NOT speeding: 0
	Currently in Speeding violation: 1

	bit 6: Seatbelt Violation
	Currently wearing seatbelt: 0
	Currently in Seatbelt violation: 1

	bit 7: RPM violation
	Currently NOT exceeding RPM limit: 0
	Currently exceeding RPM limit: 1
	*****************************************************************
	*/

	@Override
	public GeoPoint getLocation() {
		return payload.getLocation();
	}

	@Override
	public DeviceNoteTypes getType() {
		return payload.getType();
	}

	@Override
	public AutomationCalendar getTime() {
		return payload.getTime();
	}

	@Override
	public DeviceNote copy() {
		return new SatNote(payload.copy(), satIMEI);
	}

	@Override
	public void addAttr(EventAttr attr, Integer value) {
		payload.addAttr(attr, value);
	}

	@Override
	public void addAttrs(DeviceAttributes attrs) {
		payload.addAttrs(attrs);
	}

	@Override
	public void addAttr(EventAttr id, Object value) {
		payload.addAttr(id, value);
	}
	
	@Override 
	public String toString(){
		return payload.toString();
	}

}
