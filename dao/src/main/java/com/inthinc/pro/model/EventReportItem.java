package com.inthinc.pro.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class EventReportItem implements Comparable<EventReportItem> {

	private String date;
	private String group;
	private String driverName;
	private String vehicleName;
	private String category;
	private String detail;
	
	public EventReportItem(Event event, MeasurementType measurementType, String dateFormat, String detailsFormat, String mphString)
	{
		DateFormat dateFormatter = new SimpleDateFormat(dateFormat);
		dateFormatter.setTimeZone(event.getDriverTimeZone() == null ? TimeZone.getDefault() : event.getDriverTimeZone());
		setDate(dateFormatter.format(event.getTime()));
		setGroup(event.getGroupName());
		setDriverName(event.getDriverName());
		setVehicleName(event.getVehicleName());
		setCategory(event.getEventCategory().toString());
		setDetail(event.getDetails(detailsFormat, measurementType, mphString));
		
	}
	
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDate() {
	    return date;
	}

	public void setDate(String date) {
	    this.date = date;
	}

	public String getGroup() {
	    return group;
	}

	public void setGroup(String group) {
	    this.group = group;
	}

	public String getCategory() {
	    return category;
	}

	public void setCategory(String category) {
	    this.category = category;
	}

	public String getDriverName() {
	    return driverName;
	}

	public void setDriverName(String driverName) {
	    this.driverName = driverName;
	}

	public String getVehicleName() {
	    return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
	    this.vehicleName = vehicleName;
	}

    @Override
    public int compareTo(EventReportItem o)
    {
        return getDate().compareTo(o.getDate());
    }

}
