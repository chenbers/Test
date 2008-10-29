package com.inthinc.pro.model;

import java.io.Serializable;

public class LatLng implements Serializable
{
	/**
	 * 
	 */
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

}

