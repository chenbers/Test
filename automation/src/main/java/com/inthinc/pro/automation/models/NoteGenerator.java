package com.inthinc.pro.automation.models;

import com.inthinc.pro.automation.deviceEnums.DeviceAttrs;
import com.inthinc.pro.automation.deviceEnums.TiwiGenerals.ViolationFlags;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.models.AutomationDeviceEvents.AutomationEvents;
import com.inthinc.pro.automation.models.AutomationDeviceEvents.NoteEvent;
import com.inthinc.pro.automation.models.AutomationDeviceEvents.SeatBeltEvent;
import com.inthinc.pro.automation.models.AutomationDeviceEvents.SpeedingEvent;
import com.inthinc.pro.model.configurator.ProductType;

public class NoteGenerator {


    public static DeviceNote noteEvent(DeviceNote note, NoteEvent event,
            ProductType productVersion) {
        if (productVersion.equals(ProductType.WAYSMART)) {
            note.addAttr(DeviceAttrs.DELTA_VS, event.packDeltaVS());

        } else {
            note.addAttr(DeviceAttrs.DELTAV_X, event.deltaX);
            note.addAttr(DeviceAttrs.DELTAV_Y, event.deltaY);
            note.addAttr(DeviceAttrs.DELTAV_Z, event.deltaZ);
        }
        return note;
    }

    public static DeviceNote seatbeltEvent(DeviceNote note, SeatBeltEvent event,
            ProductType productVersion) {
        if (productVersion.equals(ProductType.WAYSMART)) {
            note.addAttr(DeviceAttrs.TOP_SPEED, event.topSpeed);
            note.addAttr(DeviceAttrs.DISTANCE, event.distance);
            note.addAttr(DeviceAttrs.MAX_RPM, event.maxRpm);

        } else {
            note.addAttr(DeviceAttrs.AVG_RPM, event.avgRpm);
            
            note.addAttr(DeviceAttrs.VIOLATION_FLAGS,
                    SeatBeltEvent.violationFlag);
            
            note.addAttr(DeviceAttrs.PERCENTAGE_OF_TIME_SPEED_FROM_GPS_USED,
                    event.gpsPercent);
            
            note.addAttr(DeviceAttrs.TOP_SPEED, event.topSpeed);
            note.addAttr(DeviceAttrs.AVG_SPEED, event.avgSpeed);
            note.addAttr(DeviceAttrs.DISTANCE, event.distance);
        }
        return note;
    }

    public static DeviceNote speedingEvent(DeviceNote note, SpeedingEvent event,
            ProductType productVersion) {
        if (productVersion.equals(ProductType.WAYSMART)) {
            note.addAttr(DeviceAttrs.TOP_SPEED, event.topSpeed);
            note.addAttr(DeviceAttrs.DISTANCE, event.distance);
            note.addAttr(DeviceAttrs.MAX_RPM, event.maxRpm);
            note.addAttr(DeviceAttrs.SPEED_LIMIT, event.speedLimit);
            note.addAttr(DeviceAttrs.AVG_SPEED, event.avgSpeed);
            note.addAttr(DeviceAttrs.AVG_RPM, event.avgRpm);

        } else {
            note.addAttr(DeviceAttrs.DISTANCE, event.distance);
            note.addAttr(DeviceAttrs.TOP_SPEED, event.topSpeed);
            note.addAttr(DeviceAttrs.AVG_SPEED, event.avgSpeed);
            note.addAttr(DeviceAttrs.SPEED_ID, 9999);
            note.addAttr(DeviceAttrs.VIOLATION_FLAGS, SpeedingEvent.FLAG);
        }
        return note;
    }

    public static DeviceNote locationEvent(DeviceNote note, DeviceState state) {
        if (state.getProductVersion().equals(ProductType.WAYSMART)){
            
        } else {
            if (state.getSpeeding()) {
                note.addAttr(DeviceAttrs.VIOLATION_FLAGS,
                        ViolationFlags.VIOLATION_MASK_SPEEDING);
            }

            if (state.getRpm_violation()) {
                note.addAttr(DeviceAttrs.VIOLATION_FLAGS,
                        ViolationFlags.VIOLATION_MASK_RPM);
            }

            if (state.getSeatbelt_violation()) {
                note.addAttr(DeviceAttrs.VIOLATION_FLAGS,
                        ViolationFlags.VIOLATION_MASK_SEATBELT);
            }
        }
        return note;
    }

    public static void addAttrs(DeviceNote tripEvent, AutomationEvents event, ProductType productVersion) {
        if (event instanceof SpeedingEvent){
            speedingEvent(tripEvent, (SpeedingEvent) event, productVersion);
        } else if (event instanceof SeatBeltEvent){
            seatbeltEvent(tripEvent, (SeatBeltEvent) event, productVersion);
        } else if (event instanceof NoteEvent){
            noteEvent(tripEvent, (NoteEvent) event, productVersion);
        }
    }

}
