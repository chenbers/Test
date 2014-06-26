package com.inthinc.pro.model.event;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlRootElement;
import java.text.MessageFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@XmlRootElement
public class BackingMultipleEvent extends Event implements MultipleEventTypes
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @EventAttrID(name = "TRIP_DURATION")
    private Integer duration = 0;

    @EventAttrID(name = "ATTR_BACKING_TYPE")
    private Integer backingType = 0;

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(BackingMultipleEvent.class);

    private static EventAttr[] eventAttrList = { EventAttr.TRIP_DURATION, EventAttr.ATTR_BACKING_TYPE };

    public BackingMultipleEvent()
    {
        super();
    }

    public BackingMultipleEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, Integer duration, Integer backingType) {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.duration = duration;
        this.backingType = backingType;

    }

    public EventType getEventType()
    {
        if (this.backingType == 1){
            return EventType.BACKING;
        }else if(this.backingType == 2){
            return EventType.FIRST_MOVE_FORWARD;
        }else{
            return EventType.UNKNOWN;
        }
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getBackingType() {
        return backingType;
    }

    public void setBackingType(Integer backingType) {
        this.backingType = backingType;
    }

    @Override
    public String getDetails(String formatStr,MeasurementType measurementType,String... mphString)
    {
        return MessageFormat.format(formatStr, new Object[] { getDuration() == null ? 0 : getDuration() });
    }




    @Override
    public Set<EventType> getEventTypes() {
        return EnumSet.of(EventType.BACKING, EventType.FIRST_MOVE_FORWARD);
    }
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }
    
    @Override
    public String toString() {
        return "Aggressive Driving Event [eventType: " + getEventType() + " duration: " + getDuration() + " backing type=" + getBackingType() + "]" + super.toString();
    }


}
