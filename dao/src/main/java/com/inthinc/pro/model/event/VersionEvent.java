package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.MeasurementType;

public class VersionEvent extends Event implements StatusEvent {
    
    private static final long serialVersionUID = 1L;
    @EventAttrID(name="UP_TO_DATE_STATUS")
    private VersionState status;
    
    private static EventAttr[] eventAttrList = {
        EventAttr.UP_TO_DATE_STATUS
    };
    
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    
    public VersionEvent()
    {
        super();
    }
    public VersionEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, VersionState status)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.status = status;
    }


    public VersionState getStatus() {
        return status;
    }

    public void setStatus(VersionState status) {
        this.status = status;
    }

    
    @Override
    public String getDetails(String formatStr,MeasurementType measurementType, String... versionString)
    {
        return MessageFormat.format(formatStr, new Object[] {versionString[0]});
    }


    @Override
    public String getStatusMessageKey() {
        return (status == null) ? VersionState.UNKNOWN.toString() : status.toString();
    }

    
}
