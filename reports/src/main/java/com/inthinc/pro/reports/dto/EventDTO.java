package com.inthinc.pro.reports.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.RedFlagLevel;

public class EventDTO
{
    private final static String DATE_PATTERN = "";
    private static DateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);
    
    private String date;
    private String groupName;
    private String driverName;
    private String vehicleName;
    private String category;
    private String detail;
    private Event event;
    private RedFlagLevel level;
    private boolean alert;
    
    public EventDTO(Event event)
    {
        this.event = event;
        
        TimeZone tz = (event.getDriver() == null || event.getDriver().getPerson() == null) ? TimeZone.getDefault() : event.getDriver().getPerson().getTimeZone();
        dateFormatter.setTimeZone((tz==null) ? TimeZone.getDefault() : tz);
        setDate(dateFormatter.format(event.getTime()));
        
       
        setDriverName((event.getDriver() == null) ? "None Assigned" : event.getDriver().getPerson().getFullName());
        setVehicleName((event.getVehicle() == null)? "None Assigned" : event.getVehicle().getName());

        setCategory(event.getEventType().toString());
        
        //setDetail(event.getDetails(MessageUtil.getMessageString("redflags_details" + event.getEventType().getKey())));

    }
    
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public String getGroupName()
    {
        return groupName;
    }
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    public String getDriverName()
    {
        return driverName;
    }
    public void setDriverName(String driverName)
    {
        this.driverName = driverName;
    }
    public String getVehicleName()
    {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName)
    {
        this.vehicleName = vehicleName;
    }
    public String getCategory()
    {
        return category;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }
    public String getDetail()
    {
        return detail;
    }
    public void setDetail(String detail)
    {
        this.detail = detail;
    }
    public Event getEvent()
    {
        return event;
    }
    public void setEvent(Event event)
    {
        this.event = event;
    }
    public RedFlagLevel getLevel()
    {
        return level;
    }
    public void setLevel(RedFlagLevel level)
    {
        this.level = level;
    }
    public boolean isAlert()
    {
        return alert;
    }
    public void setAlert(boolean alert)
    {
        this.alert = alert;
    }
}
