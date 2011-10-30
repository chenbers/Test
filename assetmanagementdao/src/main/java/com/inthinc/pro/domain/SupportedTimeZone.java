package com.inthinc.pro.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="timezone")
public class SupportedTimeZone 
{
    @Id
    private Integer  tzID;
    
    private String tzName;

    
    public SupportedTimeZone()
    {
    }

    public SupportedTimeZone(Integer tzID, String tzName)
    {
        this.tzID = tzID;
        this.tzName = tzName;
    }

    public Integer getTzID()
    {
        return tzID;
    }

    public void setTzID(Integer tzID)
    {
        this.tzID = tzID;
    }

    public String getTzName()
    {
        return tzName;
    }

    public void setTzName(String tzName)
    {
        this.tzName = tzName;
    }


}
