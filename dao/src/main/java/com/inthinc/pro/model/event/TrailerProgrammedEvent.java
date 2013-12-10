package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.MeasurementType;

@XmlRootElement
public class TrailerProgrammedEvent extends Event implements StatusEvent {
    private static final long serialVersionUID = 1L;
    @EventAttrID(name="TRAILERID_OLD")
    String trailerIdOld;
    @EventAttrID(name="TRAILER_ID")
    String trailerId;
    
    private static EventAttr[] eventAttrList = {
//        EventAttr.DATA_LENGTH,
        EventAttr.TRAILERID_OLD,
        EventAttr.TRAILER_ID,
    };
    
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    
    public TrailerProgrammedEvent()
    {
        super();
    }
    public TrailerProgrammedEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, 
             String trailerId, String trailerIdOld)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.trailerId = trailerId;
        this.trailerIdOld = trailerIdOld;
        
    }
    
    @Override
    public EventType getEventType()
    {
        return EventType.TRAILER_PROGRAMMED;
    }
    
    @Override
    public String getDetails(String formatStr, MeasurementType measurementType,String... noTrailerStr)
    {
        // Trailer: ABC123  Old Trailer: XYZ987 
        return MessageFormat.format(formatStr, new Object[] {trailerId == null || trailerId.isEmpty() ? noTrailerStr[0] : trailerId, 
                trailerIdOld == null || trailerIdOld.isEmpty() ? noTrailerStr[0] : trailerIdOld}); 
    }
    
    public String getTrailerId() {
        return trailerId;
    }
    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getTrailerIdOld() {
        return trailerIdOld;
    }

    public void setTrailerIdOld(String trailerIdOld) {
        this.trailerIdOld = trailerIdOld;
    }

    @Override
    public String getStatusMessageKey() {
        return "TrailerDataEvent.NO_TRAILER";
    }
}
