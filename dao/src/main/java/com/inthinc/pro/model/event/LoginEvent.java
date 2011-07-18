package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.MeasurementType;

public abstract class LoginEvent extends Event implements MultipleEventTypes {
    /**
     * 
     */
    private static final long serialVersionUID = -4531758494432316834L;

    private String empId;

    private static final Logger logger = Logger.getLogger(LoginEvent.class);

    public LoginEvent() {
        super();
    }

    public LoginEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, String empId) {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.setEmpId(empId);
    }

    @Override
    public String getDetails(String formatStr, MeasurementType measurementType, String... mString) {
        String empId = "?";
        if (this.empId != null)
            empId = this.empId;

        return MessageFormat.format(formatStr, empId);
    }

    @Override
    public boolean isValidEvent() {
        return (empId != null);
    }

    public EventType getEventType() {
        NoteType type = getType();
        EventType eventType;
        if (type != null) {
            eventType = type.getEventType();
        } else {
            eventType = EventType.UNKNOWN;
        }
        return eventType;
    }

    @Override
    public Set<EventType> getEventTypes() {
        return EnumSet.of(EventType.NEW_DRIVER, EventType.NEW_OCCUPANT, EventType.INVALID_DRIVER, EventType.INVALID_OCCUPANT);
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
