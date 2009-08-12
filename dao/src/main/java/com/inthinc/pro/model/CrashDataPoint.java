package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;

public class CrashDataPoint extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer crashDataPointID;
    private LatLng latLng;
    private Date date;
    private Integer fullEventID;
    @Column(updateable=false)
    private FullEvent fullEvent;
    private Integer gpsSpeed;
    private Integer obdSpeed;
    private Integer rpm;
    private Boolean seatBelt;
    
    public CrashDataPoint(){}

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getFullEventID() {
        return fullEventID;
    }

    public void setFullEventID(Integer fullEventID) {
        this.fullEventID = fullEventID;
    }

    public Integer getGpsSpeed() {
        return gpsSpeed;
    }

    public void setGpsSpeed(Integer gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public Integer getObdSpeed() {
        return obdSpeed;
    }

    public void setObdSpeed(Integer obdSpeed) {
        this.obdSpeed = obdSpeed;
    }

    public Integer getRpm() {
        return rpm;
    }

    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

    public Boolean getSeatBelt() {
        return seatBelt;
    }

    public void setSeatBelt(Boolean seatBelt) {
        this.seatBelt = seatBelt;
    }

    public void setCrashDataPointID(Integer crashDataPointID) {
        this.crashDataPointID = crashDataPointID;
    }

    public Integer getCrashDataPointID() {
        return crashDataPointID;
    }

    public void setFullEvent(FullEvent fullEvent) {
        this.fullEvent = fullEvent;
    }

    public FullEvent getFullEvent() {
        return fullEvent;
    }

    @Override
    public String toString() {
        return "CrashDataPoint [crashDataPointID=" + crashDataPointID + ", date=" + date + ", fullEventID=" + fullEventID + ", gpsSpeed=" + gpsSpeed + ", latLng=" + latLng
                + ", obdSpeed=" + obdSpeed + ", rpm=" + rpm + ", seatBelt=" + seatBelt + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((crashDataPointID == null) ? 0 : crashDataPointID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CrashDataPoint)) {
            return false;
        }
        CrashDataPoint other = (CrashDataPoint) obj;
        if (crashDataPointID == null) {
            if (other.crashDataPointID != null) {
                return false;
            }
        }
        else if (!crashDataPointID.equals(other.crashDataPointID)) {
            return false;
        }
        return true;
    }
    
    
}
