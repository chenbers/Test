package com.inthinc.pro.model.event;

import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;

public class NewDriverEvent extends Event {
    
    private static final long serialVersionUID = 1L;

    @EventAttrID(name="DRIVER_STR")
    private String empID;
    
    private static EventAttr[] eventAttrList = {
        EventAttr.DRIVER_STR
    };
    
    public NewDriverEvent()
    {
        super();
    }
    
    public NewDriverEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, String empID)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.empID = empID;
    }

    public String getEmpID() {
        return empID;
    }
    public void setEmpID(String empID) {
        this.empID = empID;
    }


    @Override
    public EventType getEventType()
    {
        return EventType.NEW_DRIVER;
        
    }

    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }


}
