package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.MeasurementType;


@XmlRootElement
public class TrailerDataEvent extends Event implements StatusEvent {
    private static final long serialVersionUID = 1L;
    @EventAttrID(name="HAZMAT_FLAG")
    Integer hazmatFlag;
    @EventAttrID(name="SERVICE_ID")
    String serviceId;
    @EventAttrID(name="TRAILER_ID")
    String trailerId;
    
    private static EventAttr[] eventAttrList = {
        EventAttr.DATA_LENGTH,
        EventAttr.SERVICE_ID,
        EventAttr.TRAILER_ID,
        EventAttr.HAZMAT_FLAG,
    };
    
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    
    public TrailerDataEvent()
    {
        super();
    }
    public TrailerDataEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, 
            Integer hazmatFlag, String serviceId, String trailerId)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.hazmatFlag = hazmatFlag;
        this.serviceId = serviceId;
        this.trailerId = trailerId;
        
    }
    
    @Override
    public EventType getEventType()
    {
        return EventType.TRAILER_DATA;
    }
    
    @Override
    public String getDetails(String formatStr, MeasurementType measurementType,String... noTrailerStr)
    {
        // Trailer: ABC123  Service: 123 Hazmat Flag: 1 
        return MessageFormat.format(formatStr, new Object[] {trailerId == null || trailerId.isEmpty() ? noTrailerStr[0] : trailerId, 
                serviceId == null || serviceId.isEmpty() ? "0" : serviceId, 
                hazmatFlag == null ? "0" : hazmatFlag.toString()});
    }

    
    
    
    public Integer getHazmatFlag() {
        return hazmatFlag;
    }
    public void setHazmatFlag(Integer hazmatFlag) {
        this.hazmatFlag = hazmatFlag;
    }
    public String getServiceId() {
        return serviceId;
    }
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    public String getTrailerId() {
        return trailerId;
    }
    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    @Override
    public String getStatusMessageKey() {
        return "TrailerDataEvent.NO_TRAILER";
    }
    
    
}
