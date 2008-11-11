package com.inthinc.pro.model;

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
    
    private Boolean cleared;
    
    @Column(updateable=false)
    private Event event;
    
    
    public RedFlag()
    {
        
    }
    public RedFlag(Integer redFlagID, RedFlagLevel level, Boolean alert, Boolean cleared, Event event)
    {
        this.redFlagID = redFlagID;
        this.level = level;
        this.alert = alert;
        this.cleared = cleared;
        this.event = event;
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

    public Boolean getCleared()
    {
        return cleared;
    }

    public void setCleared(Boolean cleared)
    {
        this.cleared = cleared;
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
