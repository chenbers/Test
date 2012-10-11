package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;

public class EventDisplay
{
    String driverName;
    String vehicleName;
    String dateStr;
    String eventType;
    Date time;
    
    Event event;
    
    private DateFormat dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"));
    
    public EventDisplay(Event event)
    {
        this.event = event;
        
        Driver driver = event.getDriver();
        if (driver != null)
        {
            Person person = driver.getPerson();
            if (person != null)
            {
                setDriverName(person.getFirst() + " " + person.getLast());
                dateFormatter.setTimeZone(person.getTimeZone());
            }
        }
        Vehicle vehicle = event.getVehicle();
        if (vehicle != null)
        {
            setVehicleName(vehicle.getName());
        }
        
        setDateStr(dateFormatter.format(event.getTime()));
        setEventType(MessageUtil.getMessageString(event.getEventType().toString()));
        setTime(event.getTime());
    }
    
    public String getDriverName()
    {
        return driverName;
    }
    public void setDriverName(String driverName)
    {
        this.driverName = driverName;
    }
    public String getDateStr()
    {
        return dateStr;
    }
    public void setDateStr(String dateStr)
    {
        this.dateStr = dateStr;
    }
    public String getEventType()
    {
        return eventType;
    }
    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public Date getTime()
    {
        return time;
    }

    public void setTime(Date time)
    {
        this.time = time;
    }

    public String getVehicleName()
    {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName)
    {
        this.vehicleName = vehicleName;
    }

    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
    {
        this.event = event;
    }
    
    
}
