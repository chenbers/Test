package com.inthinc.pro.model;


import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.app.States;

public class State extends BaseEntity implements ReferenceEntity
{
    @ID
    private Integer stateID;
    
    private String name;
    private String abbrev;
    
    public State()
    {
        super();
    }
    public State(Integer id, String name, String abbrev)
    {
        this.stateID = id;
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
    @Override
    public Integer retrieveID()
    {
        return stateID;
    }
        
    public static State valueOf(Integer ID)
    {
        return States.getStateById(ID);
    }

}

