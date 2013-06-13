package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.MeasurementType;

@XmlRootElement
public class BackingEvent extends Event {

    private static final long serialVersionUID = 1L;

    /**
     * duration - The duration in seconds the vehicle was backing (reverse gear)
     */
    @EventAttrID(name = "TRIP_DURATION")
    private Integer duration = 0;

    private static EventAttr[] eventAttrList = { EventAttr.TRIP_DURATION };

    public BackingEvent() {
        super();
    }

    public BackingEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, Integer duration) {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.duration = duration;

    }

    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    public EventType getEventType() {
        return EventType.BACKING;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String getDetails(String formatStr, MeasurementType measurementType, String... mphString) {
        return MessageFormat.format(formatStr, new Object[] { getDuration() == null ? 0 : getDuration() });
    }

}
