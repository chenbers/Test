package com.inthinc.pro.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class State
{

    @Id
    private Integer           stateID;

    private String            name;
    private String            abbrev;

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
    public String toString()
    {
        return getName();
    }
}
