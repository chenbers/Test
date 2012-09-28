package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.MeasurementType;

public class DOTStoppedEvent extends Event implements StatusEvent {
    
    private static final long serialVersionUID = 1L;
    @EventAttrID(name="REASON_CODE_DOT")
    private DOTStoppedState status;
    
    private static EventAttr[] eventAttrList = {
        EventAttr.REASON_CODE_DOT,
    };
    
    public DOTStoppedEvent()
    {
        super();
    }
    public DOTStoppedEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, DOTStoppedState status)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.status = status;
    }


    public DOTStoppedState getStatus() {
        return status;
    }

    public void setStatus(DOTStoppedState status) {
        this.status = status;
    }

    
    @Override
    public String getDetails(String formatStr,MeasurementType measurementType, String... dotStoppedString)
    {
        return MessageFormat.format(formatStr, new Object[] {dotStoppedString[0]});
    }


    @Override
    public String getStatusMessageKey() {
        return (status == null) ? DOTStoppedState.UNKNOWN.toString() : status.toString();
    }

    @Override
    public EventType getEventType()
    {
        return EventType.DOT_STOPPED;
        
    }

    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    
}
