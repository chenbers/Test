package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.Person;

public class EventDisplay
{
    String driverName;
    String dateStr;
    String eventType;
    Date time;
    
    private static DateFormat dateFormatter = new SimpleDateFormat("E, MMM d, yyyy h:mm a (z)");
    
    public EventDisplay(Event event)
    {
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
        
        setDateStr(dateFormatter.format(event.getTime()));
        setEventType(event.getEventType().toString());
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
    
    
}
