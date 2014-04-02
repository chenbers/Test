package com.inthinc.device.emulation.notes;

import java.io.ByteArrayOutputStream;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class SatelliteEventStripped extends SatelliteEvent {

    public SatelliteEventStripped(DeviceNoteTypes type, AutomationCalendar time, GeoPoint location, Heading heading, int sats, int speed, int odometerX100) {
        super(type, time, location, heading, sats, speed, odometerX100);
    }
    @Override
    public byte[] Package() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0);
        longToByte(baos, type.getIndex(), 1);
        encodeAttributes(baos, attrs, type.getAttributes());
        byte[] temp = baos.toByteArray();
        temp[0] = (byte) (temp.length & 0xFF);
        for (int i = 0; i < temp.length; i++) {
            Log.debug("Byte " + i + " = " + temp[i]);
        }
        return temp;
    }
}
