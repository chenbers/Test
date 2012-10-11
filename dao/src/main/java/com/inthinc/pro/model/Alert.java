package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Alert extends BaseEntity
{
    @ID
    private Integer alertID;
    
    @Column(updateable=false)
    private RedFlagLevel level;
    
    
    @Column(updateable=false)
    private long noteID;


    public Integer getAlertID()
    {
        return alertID;
    }


    public void setAlertID(Integer alertID)
    {
        this.alertID = alertID;
    }


    public RedFlagLevel getLevel()
    {
        return level;
    }


    public void setLevel(RedFlagLevel level)
    {
        this.level = level;
    }


    public long getNoteID()
    {
        return noteID;
    }


    public void setNoteID(long noteID)
    {
        this.noteID = noteID;
    }

}
