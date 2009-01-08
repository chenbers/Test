package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.util.MessageUtil;

public class RedFlagReportItem
{
    
    private static final Logger logger = Logger.getLogger(RedFlagReportItem.class);
    
    private String date;
    private String group;
    private String category;
    private String detail;
    
    private RedFlag redFlag;
    
    private static DateFormat dateFormatter = new SimpleDateFormat("E, MMM d, yyyy h:mm a (z)");

    
    public RedFlagReportItem(RedFlag redFlag, GroupHierarchy groupHierarchy)
    {
        this.redFlag = redFlag;

        Event event = redFlag.getEvent();
        
        if (event == null)
        {
            logger.error("Unable to retrieve Event for Red Flag: " + redFlag.getRedFlagID());
            return;
        }
        
        dateFormatter.setTimeZone(event.getDriver().getPerson().getTimeZone());
        setDate(dateFormatter.format(event.getTime()));
        
        Group group = groupHierarchy.getGroup(event.getDriver().getGroupID());
        if (group != null)
        {
            setGroup(group.getName());
        }
        
        String catFormat = MessageUtil.getMessageString("redflags_cat" + redFlag.getEvent().getEventCategory().toString());
        setCategory(MessageFormat.format(catFormat, new Object[] {redFlag.getEvent().getEventType().toString()}));
        
        setDetail(event.getDetails(MessageUtil.getMessageString("redflags_details" + redFlag.getEvent().getEventType().getKey())));

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

    public RedFlag getRedFlag()
    {
        return redFlag;
    }

    public void setRedFlag(RedFlag redFlag)
    {
        this.redFlag = redFlag;
    }
    
    
}
