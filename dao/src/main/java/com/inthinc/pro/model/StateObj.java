package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class StateObj extends BaseEntity
{
    @ID
    private Integer stateID;
    
    private String name;
    private String abbrev;
    public StateObj()
    {
        super();
    }
    public StateObj(Integer id, String name, String abbrev)
    {
        this.name = name;
        this.abbrev = abbrev;
    }
    public String getAbbrev()
    {
        return abbrev;
    }
    public void setAbbrev(String abbrev)
    {
        this.abbrev = abbrev;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Integer getStateID()
    {
        return stateID;
    }
    public void setStateID(Integer stateID)
    {
        this.stateID = stateID;
    }
        
}

