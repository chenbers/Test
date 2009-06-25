package com.inthinc.pro.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inthinc.pro.dao.annotations.Column;
public class LatLng implements Serializable, Comparable<LatLng>
{
    /**
	 * 
	 */
    @Column(updateable = false)
    private static final long serialVersionUID = 1L;
    private double lat;
    private double lng;

    public LatLng()
    {
        super();
    }

    public LatLng(double lat, double lng)
    {
        super();
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    @Override
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("(");
        buffer.append(getLat());
        buffer.append(",");
        buffer.append(getLng());
        buffer.append(")");
        return buffer.toString();
    }

    
//  Implementing the equals and hashcode method causes hessain calls to break. Until David Story or Dave Harry fix their end to be able to handle many references to one object, we can't use this.
    @Override
    public boolean equals(Object obj)
    {
        if(LatLng.class.isInstance(obj))
        {
            LatLng that = LatLng.class.cast(obj);
            if(this.getLat() == that.getLat() && this.getLng() == that.getLng())
                return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder(5,9);
        hcb.append(this.getLat());
        hcb.append(this.getLng());
        return hcb.toHashCode();
    }

    @Override
    public int compareTo(LatLng o)
    {
        //I'm not positive that this is the best way to compare latlngs, but at this point it doesn't really matter if it is precise
        double thisValue = this.lat + this.lng;
        double thatValue = o.getLat() + o.getLng();
        return Double.compare(thisValue, thatValue);
    }
}
