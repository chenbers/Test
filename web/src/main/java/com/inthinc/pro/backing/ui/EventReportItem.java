package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.model.Alert;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.util.MessageUtil;

public class EventReportItem
{
    
    private static final Logger logger = Logger.getLogger(EventReportItem.class);
    
    private String date;
    private String group;
    private String category;
    private String detail;
    private Event event;
    private RedFlagLevel level;
    private boolean alert;
    
    private static DateFormat dateFormatter = new SimpleDateFormat("E, MMM d, yyyy h:mm a (z)");

    
    public EventReportItem(Event event, Alert rfAlert, GroupHierarchy groupHierarchy)
    {
        this.event = event;
        alert = (rfAlert != null);
        if (rfAlert != null)
        {
            level = rfAlert.getLevel();
        }
        else level = RedFlagLevel.INFO;
        
        // TODO: do we need driver timezone?
        TimeZone tz = TimeZone.getDefault(); //event.getDriver().getPerson().getTimeZone();
        dateFormatter.setTimeZone((tz==null) ? TimeZone.getDefault() : tz);
        setDate(dateFormatter.format(event.getTime()));
        
        Group group = groupHierarchy.getGroup(event.getGroupID());
        if (group != null)
        {
            setGroup(group.getName());
        }
        else
        {
            setGroup("");
        }
        
        String catFormat = MessageUtil.getMessageString("redflags_cat" + event.getEventCategory().toString());
        setCategory(MessageFormat.format(catFormat, new Object[] {event.getEventType().toString()}));
        
        setDetail(event.getDetails(MessageUtil.getMessageString("redflags_details" + event.getEventType().getKey())));

    }
    
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public String getGroup()
    {
        return group;
    }
    public void setGroup(String group)
    {
        this.group = group;
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
