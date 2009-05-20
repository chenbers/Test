package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneArrivalEvent;
import com.inthinc.pro.model.ZoneDepartureEvent;
import com.inthinc.pro.util.MessageUtil;

public class RedFlagReportItem implements Comparable<RedFlagReportItem>
{
    
    private static final Logger logger = Logger.getLogger(RedFlagReportItem.class);
    
    private String date;
    private String group;
    private String driverName;
    private String vehicleName;
    private String category;
    private String detail;
    
    private Zone zone;
    private RedFlag redFlag;
    
    private static DateFormat dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"));

    
    public RedFlagReportItem(RedFlag redFlag, GroupHierarchy groupHierarchy)
    {
        this.redFlag = redFlag;

        Event event = redFlag.getEvent();
        
        if (event == null)
        {
            logger.error("Unable to retrieve Event for Red Flag: ");
            return;
        }
        
        dateFormatter.setTimeZone(redFlag.getTimezone());
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
        
        setDriverName(event.getDriver().getPerson().getFullName());
        setVehicleName(event.getVehicle().getName());
        
        String catFormat = MessageUtil.getMessageString("redflags_cat" + redFlag.getEvent().getEventCategory().toString());
        setCategory(MessageFormat.format(catFormat, new Object[] {MessageUtil.getMessageString(redFlag.getEvent().getEventType().toString())}));
        
        setDetail(event.getDetails(MessageUtil.getMessageString("redflags_details" + redFlag.getEvent().getEventType().name())));

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

    @Override
    public int compareTo(RedFlagReportItem o)
    {
        return this.getRedFlag().getLevel().compareTo(o.getRedFlag().getLevel());
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

    public Zone getZone()
    {
        return zone;
    }

    public void setZone(Zone zone)
    {
        this.zone = zone;
    }
    
    
}

