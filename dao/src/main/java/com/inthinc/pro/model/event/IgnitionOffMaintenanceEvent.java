package com.inthinc.pro.model.event;

import com.inthinc.pro.dao.annotations.event.EventAttrID;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
public class IgnitionOffMaintenanceEvent extends Event implements MultipleEventTypes {

    private static final long serialVersionUID = 1L;
    @EventAttrID(name="MPG")
    private Integer mpg = 0;		// units of 1/100 of a mile per gal
    @EventAttrID(name="MPG_DISTANCE")
    private Integer mpgDistance;		// units of 1/100 of a mile per gal
    @EventAttrID(name="TRIP_DURATION")
    private Integer driveTime;			// units are 1/100 of a mile
    @EventAttrID(name="PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_")
    private Integer gpsQuality;
    @EventAttrID(name="ATTR_MALFUNCTION_INDICATOR_LAMP")
    private Integer malfunctionIndicatorLamp;
    @EventAttrID(name="ATTR_CHECK_ENGINE")
    private Integer checkEngine;

    private String threshold = "";

    private Map<Object, Object> attrMap = new HashMap<Object, Object>();

    private static EventAttr[] eventAttrList = {
            EventAttr.MPG,
            EventAttr.MPG_DISTANCE,
            EventAttr.TRIP_DURATION,
            EventAttr.PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_,
            EventAttr.SPEED_COLLECTED, // WRONG ?
            EventAttr.CURRENT_IGN,
            EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP

    };
    public IgnitionOffMaintenanceEvent(){
        super();
    }

    public IgnitionOffMaintenanceEvent(Long noteID, Integer vehicleID, NoteType type,
                            Date time, Integer speed, Integer odometer, Double latitude,
                            Integer mpg, Integer mpgDistance, Integer driveTime,Double longitude, Map<Object, Object> attrMap) {
        super(noteID, vehicleID, type, time, speed, odometer, latitude,
                longitude);
        this.attrMap = attrMap;
        this.mpg = mpg;
        this.mpgDistance = mpgDistance;
        this.driveTime = driveTime;

    }

    public EventType getEventType() {
                if(this.getAttribs() != null) {
                String[] attribsList = this.getAttribs().split(";");
                for (String s : attribsList) {
                    if (!s.trim().equals("") && s.split("=").length == 2) {
                        attrMap.put(s.split("=")[0], s.split("=")[1]);
                    }
                }
                if(!attrMap.containsKey(EventAttr.ATTR_CHECK_ENGINE.getCodeAsString()) && !attrMap.containsKey(EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP.getCodeAsString())){
                    return EventType.IGNITION_OFF;
                }
                else if (attrMap.containsKey(EventAttr.ATTR_CHECK_ENGINE.getCodeAsString())) {
                    threshold = attrMap.get(EventAttr.ATTR_CHECK_ENGINE.getCodeAsString()).toString();
                    int checkEngineValue = Integer.valueOf(attrMap.get(EventAttr.ATTR_CHECK_ENGINE.getCodeAsString()).toString());
                    return decodeCheckEngineBitMask(checkEngineValue);
                } else if (attrMap.containsKey(EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP.getCodeAsString())) {
                    threshold = attrMap.get(EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP.getCodeAsString()).toString();
                    return EventType.MALFUNCTION_INDICATOR_LAMP;
                }
            } else {
                if (malfunctionIndicatorLamp != null) {
                    threshold = malfunctionIndicatorLamp + "";
                    return EventType.MALFUNCTION_INDICATOR_LAMP;
                } else if (checkEngine != null) {
                    threshold = checkEngine + "";
                    return decodeCheckEngineBitMask(checkEngine);
                }
            }
            return EventType.UNKNOWN_MAINTENANCE;
    }

    private static EventType decodeCheckEngineBitMask(int checkEngineValue) {
        int RED_STOP_BIT = 1;
        int AMBER_BIT = 2;
        int GREEN_BIT = 4;
        int minExpectedValue = 0;// 0b000 in java 1.7
        int maxExpectedValue = 7;// 0b111 in java 1.7 (3 bits)
        if(checkEngineValue<minExpectedValue || checkEngineValue >maxExpectedValue) {
            return EventType.UNKNOWN_CHECK_ENGINE_LAMP;
        }  else if ((RED_STOP_BIT&checkEngineValue)==RED_STOP_BIT) {
            return EventType.RED_STOP;
        }  else if ((AMBER_BIT&checkEngineValue)==AMBER_BIT) {
            return EventType.AMBER_WARNING;
        }  else if ((GREEN_BIT&checkEngineValue)==GREEN_BIT) {
            return EventType.PROTECT;
        } // else 
        return EventType.UNKNOWN_CHECK_ENGINE_LAMP;   
    }
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    public Map<Object, Object> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(Map<Object, Object> attrMap) {
        this.attrMap = attrMap;
    }

    public Integer getMalfunctionIndicatorLamp() {
        return malfunctionIndicatorLamp;
    }

    public void setMalfunctionIndicatorLamp(Integer malfunctionIndicatorLamp) {
        this.malfunctionIndicatorLamp = malfunctionIndicatorLamp;
    }

    public Integer getCheckEngine() {
        return checkEngine;
    }

    public void setCheckEngine(Integer checkEngine) {
        this.checkEngine = checkEngine;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    @Override
    public Set<EventType> getEventTypes() {
        return EnumSet.of(EventType.RED_STOP, EventType.AMBER_WARNING, EventType.PROTECT, EventType.MALFUNCTION_INDICATOR_LAMP, EventType.IGNITION_OFF, EventType.UNKNOWN_MAINTENANCE, EventType.UNKNOWN_CHECK_ENGINE_LAMP );
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Integer getMpg() {
        return mpg;
    }

    public void setMpg(Integer mpg) {
        this.mpg = mpg;
    }

    public Integer getMpgDistance() {
        return mpgDistance;
    }

    public void setMpgDistance(Integer mpgDistance) {
        this.mpgDistance = mpgDistance;
    }

    public Integer getDriveTime() {
        return driveTime;
    }

    public void setDriveTime(Integer driveTime) {
        this.driveTime = driveTime;
    }

    public Integer getGpsQuality() {
        return gpsQuality;
    }

    public void setGpsQuality(Integer gpsQuality) {
        this.gpsQuality = gpsQuality;
    }
}
