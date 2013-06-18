package com.inthinc.pro.model.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.event.EventAttrID;

@XmlRootElement
public class ValidDriverEvent extends LoginEvent {
    
    @EventAttrID(name="MCM_RULESET")
    private Integer dotType;

    
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
    public Integer getDotType() {
        return dotType;
    }

    public void setDotType(Integer dotType) {
        this.dotType = dotType;
    }

}
