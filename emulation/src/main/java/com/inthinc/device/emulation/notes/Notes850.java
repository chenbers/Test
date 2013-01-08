package com.inthinc.device.emulation.notes;

import java.util.Calendar;

import com.inthinc.device.emulation.enums.DeviceEnums.HOSFlags;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class Notes850 extends SatelliteEvent_t {

    public Notes850(DeviceNoteTypes type, AutomationCalendar time, GeoPoint location, boolean offRoad, boolean heavyDuty, HOSFlags hosFlags, boolean speedingViolation, boolean seatBeltViolation,
            boolean rpmViolation, Heading heading, int sats, int speed, int speedLimit, int linkID, int odometerX100, int stateID, int dbDriverID) {
        super(type, time, location, offRoad, heavyDuty, hosFlags, speedingViolation, seatBeltViolation, rpmViolation, heading, sats, speed, speedLimit, linkID, odometerX100, stateID, dbDriverID);
    }

    public Notes850(DeviceNoteTypes type, Calendar time, GeoPoint location, boolean offRoad, boolean heavyDuty, HOSFlags hosFlags, boolean speedingViolation, boolean seatBeltViolation,
            boolean rpmViolation, Heading heading, int sats, int speed, int speedLimit, int linkID, int odometerX100, int stateID, int dbDriverID) {
        super(type, time, location, offRoad, heavyDuty, hosFlags, speedingViolation, seatBeltViolation, rpmViolation, heading, sats, speed, speedLimit, linkID, odometerX100, stateID, dbDriverID);
    }

    public Notes850(DeviceNoteTypes type, DeviceState state, GeoPoint location) {
        super(type, state, location);
    }
    
    @Override
    public byte[] Package() {
        return Package(false);
    }

    public static SatelliteEvent_t unpackageN(byte[] packagedNote) {
        return unPackageS(packagedNote, false);
    }

}
