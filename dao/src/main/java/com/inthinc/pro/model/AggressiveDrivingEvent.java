package com.inthinc.pro.model;

import java.util.Date;

public class AggressiveDrivingEvent extends Event
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer avgSpeed;
    private Integer deltaVx; // deltas store as Integer, divide by 10 for float value
    private Integer deltaVy;
    private Integer deltaVz;
    private Integer severity; // This number represents the severity of the event with
                        // in the range of 0 to 100, 100 being the most extreme.
                        // This may need to be changed.

    public AggressiveDrivingEvent()
    {
        super();
    }

    public AggressiveDrivingEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, Integer avgSpeed, Integer deltaVx, Integer deltaVy,
            Integer deltaVz, Integer severity)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.avgSpeed = avgSpeed;
        this.deltaVx = deltaVx;
        this.deltaVy = deltaVy;
        this.deltaVz = deltaVz;
        this.severity = severity;
    }

    public Integer getAvgSpeed()
    {
        return avgSpeed;
    }

    public void setAvgSpeed(Integer avgSpeed)
    {
        this.avgSpeed = avgSpeed;
    }

    public Integer getDeltaVx()
    {
        return deltaVx;
    }

    public void setDeltaVx(Integer deltaVx)
    {
        this.deltaVx = deltaVx;
    }

    public Integer getDeltaVy()
    {
        return deltaVy;
    }

    public void setDeltaVy(Integer deltaVy)
    {
        this.deltaVy = deltaVy;
    }

    public Integer getDeltaVz()
    {
        return deltaVz;
    }

    public void setDeltaVz(Integer deltaVz)
    {
        this.deltaVz = deltaVz;
    }

    public EventType getEventType()
    {
        EventType eventType = super.getEventType();

        if (eventType == EventType.UNKNOWN && deltaVx != null && deltaVy != null && deltaVz != null)
        {
            if (Math.abs(deltaVz / 10.0f) > Math.abs(deltaVx / 10.0f)
                    && Math.abs(deltaVz / 10.0f) > Math.abs(deltaVy / 10.0f))
                eventType = EventType.HARD_VERT;
            else if (Math.abs(deltaVx / 10.0f) > (Math.abs(deltaVy / 10.0f) * 1.1f) && deltaVx / 10.0f > 0f)
                eventType = EventType.HARD_ACCEL;
            else if (Math.abs(deltaVx / 10.0f) > (Math.abs(deltaVy / 10.0f) * 1.1f) && deltaVx / 10.0f < 0f)
                eventType = EventType.HARD_BRAKE;
            // TODO: Distinguish between hard left and hard right
            else if (Math.abs(deltaVy / 10.0f) * 1.1f > Math.abs(deltaVx / 10.0f)
                    && Math.abs(deltaVy / 10.0f) > Math.abs(deltaVz / 10.0f))
                eventType = EventType.HARD_LEFT_TURN;
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

}
