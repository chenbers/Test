package com.inthinc.pro.model.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValidDriverEvent extends LoginEvent {
    
    public ValidDriverEvent() {
        super();
    }

    public ValidDriverEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, String empId) {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude, empId);
    }

    @Override
    public EventType getEventType() {
        return EventType.NEW_DRIVER;
    }
}
