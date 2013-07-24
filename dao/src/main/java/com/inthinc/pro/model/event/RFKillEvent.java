package com.inthinc.pro.model.event;

import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;

public class RFKillEvent extends Event {

    private static final long serialVersionUID = 1L;

    private static final Integer RF_KILL_CODE = 33;
    @EventAttrID(name="DIAGNOSTICS")
    private Integer diagnostics = RF_KILL_CODE;

    
    private static EventAttr[] eventAttrList = {
        EventAttr.DIAGNOSTICS
    };
    public RFKillEvent()
    {
        super();
    }
    public RFKillEvent(Long noteID, Integer vehicleID, NoteType noteType, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
    {
        super(noteID, vehicleID, noteType, time, speed, odometer, latitude, longitude);
        
    }

    public Integer getDiagnostics() {
        return diagnostics;
    }
    public void setDiagnostics(Integer diagnostics) {
        this.diagnostics = diagnostics;
    }
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    


}
