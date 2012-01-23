package com.inthinc.device.emulation.notes;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Level;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;

public class SatelliteEvent extends DeviceNote {

	public final static int nVersion = 2;
	public final Heading heading;
	public final int sats;
	public final int nSpeed;
	public final int odometer;
	public final int duration;

	public SatelliteEvent(DeviceNoteTypes type, AutomationCalendar time,
			GeoPoint location, Heading heading, int sats, int speed,
			int odometerX100) {

		super(type, time, location);
		this.heading = heading;
		this.sats = sats;
		this.nSpeed = speed;
		this.odometer = odometerX100 / 100;
		this.duration = 0;
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
		longToByte(baos, nSpeed, 1);
		longToByte(baos, odometer, 3);
		longToByte(baos, duration, 2);
		encodeAttributes(baos, attrs, type.getAttributes());
		byte[] temp = baos.toByteArray();
		temp[0] = (byte) (temp.length & 0xFF);
		for (int i = 0; i < temp.length; i++) {
			MasterTest.print("Byte " + i + " = " + temp[i], Level.DEBUG);
		}
		return temp;
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
				location.getLng(), nSpeed, odometer, attrs.toString());
		return temp;
	}

	@Override
	public SatelliteEvent copy() {
		SatelliteEvent temp = new SatelliteEvent(type, time, location, heading, sats, nSpeed, odometer);
		temp.addAttrs(attrs);
		return temp;
	}

}
