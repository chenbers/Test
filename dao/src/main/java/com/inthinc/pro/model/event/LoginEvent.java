package com.inthinc.pro.model.event;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.inthinc.pro.model.MeasurementType;

public class LoginEvent extends Event implements MultipleEventTypes {

    private static final long serialVersionUID = 3218106684884060943L;

    private String empId;

    private static final Logger logger = Logger.getLogger(LoginEvent.class);

    public LoginEvent() {
        super();
    }

    public LoginEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, String empId) {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.setEmpId(empId);
    }
    private boolean canFilterOn(String descriptorName){
        ArrayList<String> filters = new ArrayList<String>();
        filters.add("groupName");
        filters.add("vehicleName");
        filters.add("eventType");
        return filters.contains(descriptorName);
    }
    public boolean matches(LoginEvent event) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        for (final PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(event.getClass())) {
            if (canFilterOn(descriptor.getName())) {
                Object eventPropValue = descriptor.getReadMethod().invoke(event);
                Object thisPropValue = descriptor.getReadMethod().invoke(this);
                if(eventPropValue.equals(EventType.UNKNOWN))
                    continue;
                if (eventPropValue != null && !eventPropValue.equals("")) {
                    if (eventPropValue instanceof String && thisPropValue instanceof String) {
                        if (!((String) thisPropValue).toLowerCase().startsWith(((String) eventPropValue).toLowerCase()))
                            return false;
                    } else {
                        if (!eventPropValue.equals(thisPropValue))
                            return false;
                    }
                }
            }
        }
        return true;
    }
    @Override
    public String toString() {
        return "LoginEvent [vehicleName=" + super.getVehicleName() + ", groupName=" + super.getGroupName() + ", eventCategory="+getEventCategory()+"]";
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
