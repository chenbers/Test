package com.inthinc.pro.model;


import com.inthinc.pro.dao.annotations.ID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SupportedTimeZone 
{
    @ID
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
