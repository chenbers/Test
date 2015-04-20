package com.inthinc.pro.model.aggregation;

import com.inthinc.pro.model.event.EventType;

/**
 * Map index class that indexes by driverID and event type.
 */
public class DriverEventIndex {
    private Integer driverID;
    private EventType eventType;

    public DriverEventIndex() {
    }

    public DriverEventIndex(Integer driverID, EventType eventType) {
        this.driverID = driverID;
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverEventIndex)) return false;

        DriverEventIndex that = (DriverEventIndex) o;

        if (driverID != null ? !driverID.equals(that.driverID) : that.driverID != null) return false;
        if (eventType != that.eventType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = driverID != null ? driverID.hashCode() : 0;
        result = 31 * result + (eventType != null ? eventType.hashCode() : 0);
        return result;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
