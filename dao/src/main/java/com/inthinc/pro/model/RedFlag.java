package com.inthinc.pro.model;

import java.util.TimeZone;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;

@XmlRootElement
public class RedFlag extends BaseEntity
{
    @Column(updateable=false)
    private RedFlagLevel level;
    
    @Column(name="sent",updateable=false)
    private Boolean alert;
    
    @Column(name="note",updateable=false)
    private Event event;
    
    @Column(name="tzName",updateable=false)
    private TimeZone timezone;
    
    public RedFlag()
    {
    }
    public RedFlag(Integer redFlagID, RedFlagLevel level, Boolean alert, Event event, TimeZone timezone)
    {
        this.level = level;
        this.alert = alert;
        this.event = event;
        this.timezone = timezone;
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
