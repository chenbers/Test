package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.MeasurementType;

public class ParkingBrakeEvent extends Event implements StatusEvent {
    
    private static final long serialVersionUID = 1L;

    @EventAttrID(name="STATE")
    private ParkingBrakeState status;
    
    public ParkingBrakeEvent()
    {
        super();
    }
    public ParkingBrakeEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, ParkingBrakeState status)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.status = status;
    }


    public ParkingBrakeState getStatus() {
        return status;
    }

    public void setStatus(ParkingBrakeState status) {
        this.status = status;
    }

    
    @Override
    public String getDetails(String formatStr,MeasurementType measurementType, String... parkingBrakeStateString)
    {
        return MessageFormat.format(formatStr, new Object[] {parkingBrakeStateString[0]});
    }


    @Override
    public String getStatusMessageKey() {
        return (status == null) ? VersionState.UNKNOWN.toString() : status.toString();
    }

    @Override
    public EventType getEventType()
    {
        return EventType.PARKING_BRAKE;
        
    }

    
}
