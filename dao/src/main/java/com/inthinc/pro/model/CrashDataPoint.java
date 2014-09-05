package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.event.FullEvent;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CrashDataPoint extends BaseEntity implements Comparable<CrashDataPoint> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer crashDataID;
    private LatLng latLng;
    private Double lat;
    private Double lng;
    private Date time;
    private Integer fullEventID;
    @Column(updateable = false)
    private FullEvent fullEvent;
    private Integer gpsSpeed;
    private Integer obdSpeed;
    private Integer rpm;
    @Column(name = "seatbeltState")
    private Boolean seatBeltState;
    @Column(name = "seatbeltAvail")
    private Boolean seatBeltAvailable;
    @Column(name = "ptOfImpact")
    private Integer pointOfImpact;

    public CrashDataPoint() {
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public Integer getCrashDataID() {
        return crashDataID;
    }

    public void setCrashDataID(Integer crashDataID) {
        this.crashDataID = crashDataID;
    }

    public void setFullEvent(FullEvent fullEvent) {
        this.fullEvent = fullEvent;
    }

    public FullEvent getFullEvent() {
        return fullEvent;
    }

    public Boolean getSeatBeltState() {
        return seatBeltState;
    }

    public void setSeatBeltState(Boolean seatBeltState) {
        this.seatBeltState = seatBeltState;
    }

    public Boolean getSeatBeltAvailable() {
        return seatBeltAvailable;
    }

    public void setSeatBeltAvailable(Boolean seatBeltAvailable) {
        this.seatBeltAvailable = seatBeltAvailable;
    }

    public Integer getPointOfImpact() {
        return pointOfImpact;
    }

    public void setPointOfImpact(Integer pointOfImpact) {
        this.pointOfImpact = pointOfImpact;
    }

    @Override
    public String toString() {
        return "CrashDataPoint [crashDataPointID=" + crashDataID + ", fullEvent=" + fullEvent + ", fullEventID=" + fullEventID + ", gpsSpeed=" + gpsSpeed + ", latLng="
                + latLng + ", obdSpeed=" + obdSpeed + ", pointOfImpact=" + pointOfImpact + ", rpm=" + rpm + ", seatBeltAvailable=" + seatBeltAvailable + ", seatBeltState="
                + seatBeltState + ", time=" + time + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((crashDataID == null) ? 0 : crashDataID.hashCode());
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
        if (crashDataID == null) {
            if (other.crashDataID != null) {
                return false;
            }
        }
        else if (!crashDataID.equals(other.crashDataID)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(CrashDataPoint crashDataPoint) {
        // natural order is time ascending (most recent last)
        if (getTime() == null || crashDataPoint == null) return 0;
        return getTime().compareTo(crashDataPoint.getTime());
    }
}
