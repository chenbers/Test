package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.MeasurementType;

@SuppressWarnings("serial")
@XmlRootElement
public class DVIREvent extends Event {

    @EventAttrID(name = "DVIR_INSPECTION_TYPE")
    private Integer inspectionType;
    @EventAttrID(name = "DVIR_VEHICLE_SAFE_TO_OPERATE")
    private Integer vehicleSafeToOperate;

    private static EventAttr[] eventAttrList = { EventAttr.DVIR_INSPECTION_TYPE, EventAttr.DVIR_VEHICLE_SAFE_TO_OPERATE };

    public Integer getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(Integer inspectionType) {
        this.inspectionType = inspectionType;
    }

    public Integer getVehicleSafeToOperate() {
        return vehicleSafeToOperate;
    }

    public void setVehicleSafeToOperate(Integer vehicleSafeToOperate) {
        this.vehicleSafeToOperate = vehicleSafeToOperate;
    }

    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    @Override
    public boolean isValidEvent() {
        if (inspectionType != null) {
            return (vehicleSafeToOperate != null);
        } else
            return true;
    }

    public DVIREvent() {
        super();
    }

    public DVIREvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, Integer inspectionType, Integer vehicleSafeToOperate) {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.inspectionType = inspectionType;
        this.vehicleSafeToOperate = vehicleSafeToOperate;
    }

    @Override
    public EventType getEventType() {
        return EventType.DVIR;
    }

    public String getDetails(String formatStr, MeasurementType measurementType, String... mString) {
        // Vehicle {0} a {1} inspection.
        return MessageFormat.format(formatStr, vehicleSafeToOperate == 0 ? mString[1] : mString[0], inspectionType == 1 ? mString[2] : mString[3]);
    }

}
