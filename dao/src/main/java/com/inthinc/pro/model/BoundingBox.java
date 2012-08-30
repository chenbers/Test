package com.inthinc.pro.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inthinc.pro.dao.annotations.Column;

@XmlRootElement
public class BoundingBox implements Serializable, Comparable<BoundingBox> {
    private LatLng sw, ne;
    
    public BoundingBox() {
        super();
    }

    public BoundingBox(LatLng sw, LatLng ne) {
        super();
        this.sw = sw;
        this.ne = ne;
    }

    public BoundingBox(double sw_lat, double sw_lng, double ne_lat, double ne_lng) {
        super();
        this.sw = new LatLng(sw_lat, sw_lng);
        this.ne = new LatLng(ne_lat, ne_lng);
    }

    public double[] getDoubles() {
        return new double[] {sw.getLat(), sw.getLng(), ne.getLat(), ne.getLng()};
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("sw.lat="+sw.getLat());
        buffer.append(", sw.lng="+sw.getLng());
        buffer.append(", ne.lat="+ne.getLat());
        buffer.append(", ne.lng="+ne.getLng());
        return buffer.toString();
    }
    public String latLngStr() {
        return "(("+sw.latLngStr()+"),("+ne.latLngStr()+"))";
    }

    @Override
    public boolean equals(Object obj) {
        if (BoundingBox.class.isInstance(obj)) {
            BoundingBox that = BoundingBox.class.cast(obj);
            return (this.getSw() == that.getSw() && this.getNe() == that.getNe());
        }
        return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(7, 9);
        hcb.append(this.getSw());
        hcb.append(this.getNe());
        return hcb.toHashCode();
    }

    @Override
    public int compareTo(BoundingBox o) {
//        double thisValue = this.lat + this.lng;
//        double thatValue = o.getLat() + o.getLng();
//        return Double.compare(thisValue, thatValue);
        int swCompare = this.getSw().compareTo(o.getSw());
        int neCompare = this.getNe().compareTo(o.getNe());
        if(swCompare != 0)
            return swCompare;
        else
            return neCompare;
        
    }

    public LatLng getSw() {
        return sw;
    }

    public void setSw(LatLng sw) {
        this.sw = sw;
    }

    public LatLng getNe() {
        return ne;
    }

    public void setNe(LatLng ne) {
        this.ne = ne;
    }
}
