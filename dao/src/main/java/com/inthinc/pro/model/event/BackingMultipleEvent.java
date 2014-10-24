package com.inthinc.pro.model.event;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlRootElement;
import java.text.MessageFormat;
import java.util.*;

@XmlRootElement
public class BackingMultipleEvent extends Event implements MultipleEventTypes
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @EventAttrID(name = "TRIP_DURATION")
    private Integer duration = 0;

    private Map<Object, Object> attrMap = new HashMap<Object, Object>();


    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(BackingMultipleEvent.class);

    private static EventAttr[] eventAttrList = { EventAttr.TRIP_DURATION, EventAttr.ATTR_BACKING_TYPE };

    public BackingMultipleEvent()
    {
        super();
    }

    public BackingMultipleEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, Integer duration, Map<Object, Object> attrMap) {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.duration = duration;
        this.attrMap = attrMap;
    }

    public EventType getEventType() {

        if (!this.getAttrMap().isEmpty()) {
            for (Map.Entry<Object,Object> entry: this.getAttrMap().entrySet()){
                String attr = (String)entry.getKey();
                attrMap.put(attr, entry.getValue());
            }
            attrMap = this.getAttrMap();
            if (attrMap.containsKey(EventAttr.ATTR_BACKING_TYPE.toString())) {
                Integer backingType = Integer.valueOf(attrMap.get(EventAttr.ATTR_BACKING_TYPE.toString()).toString());
                if (backingType == 1) {
                    return EventType.BACKING;
                } else if (backingType == 2) {
                    return EventType.FIRST_MOVE_FORWARD;
                } else {
                    return EventType.UNKNOWN;
                }
            }
        }

        if (this.getAttribs() != null) {
            String[] attribsList = this.getAttribs().split(";");
            for (String s : attribsList) {
                if (!s.trim().equals("")) {
                    attrMap.put(s.split("=")[0], s.split("=")[1]);
                }
            }
            if (attrMap.containsKey(EventAttr.ATTR_BACKING_TYPE.getCode() + "")) {
                Integer backingType = Integer.valueOf(attrMap.get(EventAttr.ATTR_BACKING_TYPE.getCode() + "").toString());
                if (backingType == 1) {
                    return EventType.BACKING;
                } else if (backingType == 2) {
                    return EventType.FIRST_MOVE_FORWARD;
                } else {
                    return EventType.UNKNOWN;
                }
            }
        }
        return EventType.UNKNOWN;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Map<Object, Object> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(Map<Object, Object> attrMap) {
        this.attrMap = attrMap;
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
        return "Aggressive Driving Event [eventType: " + getEventType() + " duration: " + getDuration() + "]" + super.toString();
    }


}
