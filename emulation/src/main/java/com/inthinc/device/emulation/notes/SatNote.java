package com.inthinc.device.emulation.notes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.objects.DeviceAttributes;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class SatNote extends DeviceNote {

	private final static int imeiLength = 15;
	private final static int headerLength = 37;
	
	private final String satIMEI;
	private final DeviceNote payload;
	private final Calendar sessionTime;
	
	
	
	public SatNote(DeviceNote payload, String imei) {
		super(payload.getType(), payload.getTime(), payload.getLocation());
		this.payload = payload;
		this.satIMEI = imei;
		this.sessionTime = Calendar.getInstance();
	}

	@Override
	public byte[] Package() {
		byte[] payload = this.payload.Package();
		int messageLength = headerLength + payload.length;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        longToByte(baos, 1, 1);    					// protocol revision number
        longToByte(baos, messageLength, 2); 		// Message Length
		longToByte(baos, 1, 1);    					// IEI
		longToByte(baos, headerLength, 2);			// Header Length
		longToByte(baos, 1234, 4); 					// CDR
		baos.write(satIMEI.getBytes(), 0, imeiLength);			// IMEI goes here 10-24
		longToByte(baos, 1, 1); 					// status
		longToByte(baos, 2, 2); 					// momsn
		longToByte(baos, 3, 2); 					// mtmsn
		longToByte(baos, sessionTime.getTimeInMillis()/1000, 4);	// session time
		longToByte(baos, 1, 1); 					// payload iei
		longToByte(baos, payload.length, 2);		// Payload Length
		baos.write(payload, 0, payload.length);					// Payload
		
		return baos.toByteArray();
	}
	

	@Override
	public SatNote unPackage(byte[] packagedNote) {
		return unPackageS(packagedNote);
	}
	
	public static SatNote unPackageS(byte[] packagedNote){
		ByteArrayInputStream bais = new ByteArrayInputStream(packagedNote);
		byteToInt(bais, 1);  						// protocol revision number
		byteToInt(bais, 2); 			 			// Message Length
		byteToInt(bais, 1);    					 	// IEI
		byteToInt(bais, 2);							// Header Length
		byteToInt(bais, 4); 					 	// CDR
		char[] imeiArray = new char[15];
		for (int i=0;i<imeiArray.length;i++){
			imeiArray[i] = (char) bais.read();
		}
		String imei = String.copyValueOf(imeiArray);// IMEI goes here 10-24
		byteToInt(bais, 1); 						// status
		byteToInt(bais, 2); 						// momsn
		byteToInt(bais, 2); 						// mtmsn
		byteToInt(bais, 4);							// session time
		byteToInt(bais, 1); 						// payload iei
		int payLoadLength = byteToInt(bais, 2);		// Payload Length
		if (bais.available() != payLoadLength){
			throw new IllegalArgumentException("The byte array you provided is inconsistent in size");
		}
		byte[] payload = new byte[payLoadLength];
		for (int i=0; i<payLoadLength;i++){
			payload[i] = (byte) bais.read();
		}
		DeviceNote note = null;
		try {
			note = SatelliteEvent_t.unPackageS(payload);
		} catch (IllegalArgumentException e1){
			try {
				note = SatelliteEvent.unPackageS(payload);
			} catch (IllegalArgumentException e2){
				note = SatelliteStrippedConfigurator.unPackageS(payload);
			}
		}
		return new SatNote(note, imei);
	}
	

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


	@Override 
    public int getOdometer() {
		return payload.getOdometer();
	}

	@Override 
	public void setOdometer(int odometer) {
		payload.setOdometer(odometer);
	}

	@Override 
	public int getSpeed() {
		return payload.getSpeed();
	}

	@Override 
	public void setSpeed(int speed) {
		payload.setSpeed(speed);
	}

	@Override 
	public int getSpeedLimit() {
		return payload.getSpeedLimit();
	}

	@Override 
	public void setSpeedLimit(int speedLimit) {
		payload.setSpeedLimit(speedLimit);
	}

	@Override 
	public Heading getHeading() {
		return payload.getHeading();
	}

	@Override 
	public void setHeading(Heading heading) {
		payload.setHeading(heading);
	}

	@Override 
	public int getSats() {
		return payload.getSats();
	}

	@Override 
	public void setSats(int sats) {
		payload.setSats(sats);
	}


}
