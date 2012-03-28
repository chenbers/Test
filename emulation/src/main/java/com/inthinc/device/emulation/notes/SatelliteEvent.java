package com.inthinc.device.emulation.notes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import android.util.Log;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class SatelliteEvent extends DeviceNote {

	public final static int nVersion = 2;
	public final int duration;

	public SatelliteEvent(DeviceNoteTypes type, AutomationCalendar time,
			GeoPoint location, Heading heading, int sats, int speed,
			int odometerX100) {

		super(type, time, location);
		this.heading = heading;
		this.sats = sats;
		this.speed = speed;
		this.odometer = odometerX100 / 100;
		this.duration = 0;
	}
	
	public SatelliteEvent(DeviceNoteTypes type, Calendar time,
			GeoPoint location, Heading heading, int sats, int speed,
			int odometerX100) {
		this(type, new AutomationCalendar(time), location, heading,
				sats, speed, odometerX100);
	}
	
	public SatelliteEvent(DeviceNoteTypes type, DeviceState state,
			GeoPoint location) {
		this(type, state.getTime(), location,
				state.getHeading(), state.getSats(), state.getSats(), state
						.getOdometerX100() / 100);
	}

	@Override
	public byte[] Package() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(0);
		longToByte(baos, type.getIndex(), 1);
		longToByte(baos, nVersion, 1);
		longToByte(baos, time.toInt(), 4);
		longToByte(baos, concatenateTwoInts(heading.getHeading(), sats), 1);
		longToByte(baos, location.encodeLat(), 3);
		longToByte(baos, location.encodeLng(), 3);
		longToByte(baos, speed, 1);
		longToByte(baos, odometer, 3);
		longToByte(baos, duration, 2);
		encodeAttributes(baos, attrs, type.getAttributes());
		byte[] temp = baos.toByteArray();
		temp[0] = (byte) (temp.length & 0xFF);
		for (int i = 0; i < temp.length; i++) {
			Log.t("Byte " + i + " = " + temp[i]);
		}
		return temp;
	}
	
	public SatelliteEvent unPackage(byte[] packagedNote){
		return unPackageS(packagedNote);
	}
	
	public static SatelliteEvent unPackageS(byte[] packagedNote){
		ByteArrayInputStream bais = new ByteArrayInputStream(packagedNote);
		DeviceNoteTypes type = DeviceNoteTypes.valueOf(byteToInt(bais, 1));
		int version = byteToInt(bais, 1);  		// Note Type Version
		if ( version != nVersion) { // version == 2
			throw new IllegalArgumentException("Not a SatelliteEvent: nVersion is " + version);
		}
		AutomationCalendar time = new AutomationCalendar(byteToLong(bais, 4) * 1000);
		int flags = byteToInt(bais, 1);
		int sats = flags & 0x0F;
		Heading heading = Heading.valueOf((flags >> 2) & 0x0F);
		GeoPoint location = new GeoPoint();
		location.decodeLat(byteToLong(bais, 3));
		location.decodeLng(byteToLong(bais, 3));
		int speed = byteToInt(bais, 1);
		int odometer = byteToInt(bais, 3);
		byteToInt(bais, 2); // duration Unused, defaulted to 0
		
		SatelliteEvent note = new SatelliteEvent(type, time, location, heading, sats, speed, odometer);
		note.addAttrs(decodeAttributes(bais, type.getAttributes()));
		return note;
	}

	

	/**
	 * private final DeviceNoteTypes nType; private final int nVersion; private
	 * final AutomationCalendar nTime; private final int heading; private final
	 * int sats; private final double fLongitude; private final double
	 * fLatitude; private final int nSpeed; private final int odometer;
	 */

	@Override
	public String toString() {
		String temp = String.format(
				"NoteWS(type=%s, version=%d, time=\"%s\", heading=%d, sats=%d,\n"
						+ "lat=%.5f, lng=%.5f, speed=%d, odometer=%d,\n"
						+ "attrs={%s}", type, nVersion, time,
				heading.getHeading(), sats, location.getLat(),
				location.getLng(), speed, odometer, attrs.toString());
		return temp;
	}

	@Override
	public SatelliteEvent copy() {
		SatelliteEvent temp = new SatelliteEvent(type, time, location, heading, sats, speed, odometer);
		temp.addAttrs(attrs);
		return temp;
	}
	
	@Override
	public void setOdometer(int odometerX100){
		super.setOdometer(odometerX100 / 100);
	}
	
	@Override
	public int getOdometer(){
		return super.getOdometer() * 100;
	}

}
