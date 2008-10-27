package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class State
{
    @ID
    private Integer stateID;
    
    private String name;
    private String abbrev;
    public State()
    {
        super();
    }
    public State(Integer stateID, String name, String abbrev)
    {
        this.stateID = stateID;
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
        
}

