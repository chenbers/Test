package com.inthinc.pro.model;

import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;


public class EventReportItem implements Comparable<EventReportItem> {

	private String date;
	private String group;
	private String driverName;
	private String vehicleName;
	private String category;
	private String detail;
	private String type;
	private Boolean excluded;
	
	public EventReportItem(Event event, MeasurementType measurementType, String dateFormat, String detailsFormat, String detailsString, String detailsUnits, Locale locale)
	{
		DateTimeFormatter fmt = DateTimeFormat.forPattern(dateFormat).withLocale(locale);
		
		
		DateTime dateTime = new DateTime(event.getTime(), DateTimeZone.forTimeZone(event.getDriverTimeZone() == null ? TimeZone.getDefault() : event.getDriverTimeZone()));
 	    setDate(fmt.print(dateTime));
		
		setGroup(event.getGroupName());
		setDriverName(event.getDriverName());
		setVehicleName(event.getVehicleName());
		setCategory(EventCategory.getCategoryForEventType(event.getEventType()).toString());
        setDetail(event.getDetails(detailsFormat, measurementType, detailsString, detailsUnits));
		setType(event.getEventType().toString());
		setExcluded(event.getForgiven() != null && event.getForgiven().intValue() == 1);
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
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public Boolean getExcluded() {
		return excluded;
	}


	public void setExcluded(Boolean excluded) {
		this.excluded = excluded;
	}




    @Override
    public int compareTo(EventReportItem o)
    {
        return getDate().compareTo(o.getDate());
    }

}
