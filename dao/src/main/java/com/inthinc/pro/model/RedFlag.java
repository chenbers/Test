package com.inthinc.pro.model;

import java.util.TimeZone;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class RedFlag extends BaseEntity
{
    @ID
    private Integer redFlagID;
    
    @Column(updateable=false)
    private RedFlagLevel level;
    
    @Column(updateable=false)
    private Boolean alert;
    
    @Column(updateable=false)
    private Event event;
    
    @Column(updateable=false)
    private TimeZone timezone;
    
    public RedFlag()
    {
        
    }
    public RedFlag(Integer redFlagID, RedFlagLevel level, Boolean alert, Event event, TimeZone timezone)
    {
        this.redFlagID = redFlagID;
        this.level = level;
        this.alert = alert;
        this.event = event;
        this.timezone = timezone;
    }
    

    public Integer getRedFlagID()
    {
        return redFlagID;
    }

    public void setRedFlagID(Integer redFlagID)
    {
        this.redFlagID = redFlagID;
    }

    public RedFlagLevel getLevel()
    {
        return level;
    }

    public void setLevel(RedFlagLevel level)
    {
        this.level = level;
    }

    public Boolean getAlert()
    {
        return alert;
    }

    public void setAlert(Boolean alert)
    {
        this.alert = alert;
    }

    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
    {
        this.event = event;
    }
    public TimeZone getTimezone()
    {
        return timezone;
    }
    public void setTimezone(TimeZone timezone)
    {
        this.timezone = timezone;
    }



}
