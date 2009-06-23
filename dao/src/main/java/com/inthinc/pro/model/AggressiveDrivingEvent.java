package com.inthinc.pro.model;

import java.text.MessageFormat;
import java.util.Date;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;

public class AggressiveDrivingEvent extends Event
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer deltaX; // deltas store as Integer, divide by 10 for float value
    private Integer deltaY;
    private Integer deltaZ;
    private Integer severity; // This number represents the severity of the event with
                        // in the range of 0 to 100, 100 being the most extreme.
                        // This may need to be changed.
    private Integer speedLimit;
    private String eventTypeString;

    public AggressiveDrivingEvent()
    {
        super();
    }

    public AggressiveDrivingEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, Integer avgSpeed, Integer deltaX, Integer deltaY,
            Integer deltaZ, Integer severity)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.severity = severity;
    }

    public EventType getEventType()
    {
        EventType eventType = super.getEventType();

        if (eventType == EventType.UNKNOWN && deltaX != null && deltaY != null && deltaZ != null)
        {
            if (Math.abs(deltaZ / 10.0f) > Math.abs(deltaX / 10.0f)
                    && Math.abs(deltaZ / 10.0f) > Math.abs(deltaY / 10.0f))
                eventType = EventType.HARD_VERT;
            else if (Math.abs(deltaX / 10.0f) > (Math.abs(deltaY / 10.0f) * 1.1f) && deltaX / 10.0f > 0f)
                eventType = EventType.HARD_ACCEL;
            else if (Math.abs(deltaX / 10.0f) > (Math.abs(deltaY / 10.0f) * 1.1f) && deltaX / 10.0f < 0f)
                eventType = EventType.HARD_BRAKE;
            // TODO: Distinguish between hard left and hard right
            else if (Math.abs(deltaY / 10.0f) * 1.1f > Math.abs(deltaX / 10.0f)
                    && Math.abs(deltaY / 10.0f) > Math.abs(deltaZ / 10.0f))
                eventType = EventType.HARD_TURN;
        }

        return eventType;
    }

    public Integer getSeverity()
    {
        return severity;
    }

    public void setSeverity(Integer severity)
    {
        this.severity = severity;
    }
    public EventCategory getEventCategory()
    {
        return EventCategory.VIOLATION;
    }
    
    @Override
    public String getDetails(String formatStr,MeasurementType measurementType,String mphString)
    {
        Integer speed = getSpeed();
        if(measurementType.equals(MeasurementType.METRIC))
            speed = MeasurementConversionUtil.fromMPHtoKPH(speed);
        return MessageFormat.format(formatStr, new Object[] {speed, mphString});
    }

    public Integer getDeltaX()
    {
        return deltaX;
    }

    public void setDeltaX(Integer deltaX)
    {
        this.deltaX = deltaX;
    }

    public Integer getDeltaY()
    {
        return deltaY;
    }

    public void setDeltaY(Integer deltaY)
    {
        this.deltaY = deltaY;
    }

    public Integer getDeltaZ()
    {
        return deltaZ;
    }

    public void setDeltaZ(Integer deltaZ)
    {
        this.deltaZ = deltaZ;
    }

    public Integer getSpeedLimit()
    {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit)
    {
        this.speedLimit = speedLimit;
    }

    public String getEventTypeString()
    {
        return this.getEventType().toString();
    }

    public void setEventTypeString(String eventTypeString)
    {
        this.eventTypeString = eventTypeString;
    }

}
