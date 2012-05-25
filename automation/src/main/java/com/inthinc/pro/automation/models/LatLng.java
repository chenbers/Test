package com.inthinc.pro.automation.models;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class LatLng implements Serializable, Comparable<LatLng> {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private double lat;
    private double lng;

    // This object started out for just lat/lng. We used it in the Trips object as an array of LatLngs to contain the breadcrumb of the trip.
    // Now i'm adding heading, speed, and time which turns it into something more than LatLng. For cleanness, this object should be refactored and renamed to something else. Not
    // now though.
    private Integer head;
    private Date time;
    private Integer speed;

    public LatLng() {
        super();
    }

    public LatLng(double lat, double lng) {
        super();
        this.lat = lat;
        this.lng = lng;
    }

    public LatLng(double lat, double lng, int head) {
        super();
        this.lat = lat;
        this.lng = lng;
        this.head = head;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Integer getHead() {
        return head;
    }

    public void setHead(Integer head) {
        this.head = head;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("(");
        buffer.append(getLat());
        buffer.append(",");
        buffer.append(getLng());
        buffer.append(")");
        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (LatLng.class.isInstance(obj)) {
            LatLng that = LatLng.class.cast(obj);
            if (this.getLat() == that.getLat() && this.getLng() == that.getLng())
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(5, 9);
        hcb.append(this.getLat());
        hcb.append(this.getLng());
        return hcb.toHashCode();
    }

    @Override
    public int compareTo(LatLng o) {
        // I'm not positive that this is the best way to compare latlngs, but at this point it doesn't really matter if it is precise
        double thisValue = this.lat + this.lng;
        double thatValue = o.getLat() + o.getLng();
        return Double.compare(thisValue, thatValue);
    }
}
