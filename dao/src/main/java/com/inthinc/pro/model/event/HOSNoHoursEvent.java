package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.MeasurementType;

public class HOSNoHoursEvent extends Event implements StatusEvent {
    
    private static final long serialVersionUID = 1L;

    @EventAttrID(name="REASON_CODE_HOS")
    private HOSNoHoursState status;
    
    
    public HOSNoHoursEvent()
    {
        super();
    }
    public HOSNoHoursEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, HOSNoHoursState status)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.status = status;
    }


    public HOSNoHoursState getStatus() {
        return status;
    }

    public void setStatus(HOSNoHoursState status) {
        this.status = status;
    }

    
    @Override
    public String getDetails(String formatStr,MeasurementType measurementType, String... hosNoHoursStateString)
    {
        return MessageFormat.format(formatStr, new Object[] {hosNoHoursStateString[0]});
    }


    @Override
    public String getStatusMessageKey() {
        return (status == null) ? HOSNoHoursState.NONE.toString() : status.toString();
    }

    @Override
    public EventType getEventType()
    {
        return EventType.HOS_NO_HOURS;
        
    }

    @Override
    public EventAttr[] getEventAttrList() {
        EventAttr[] eventAttrList = new EventAttr[1];
        eventAttrList[0] = EventAttr.REASON_CODE_HOS;
        return eventAttrList;
    }

    
}
