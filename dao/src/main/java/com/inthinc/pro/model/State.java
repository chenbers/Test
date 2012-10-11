package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.app.States;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class State extends BaseEntity implements ReferenceEntity, Comparable<State>
{
    @Column(updateable = false)
    private static final long serialVersionUID = -5519902086559670626L;

    @ID
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
    public Integer retrieveID()
    {
        return stateID;
    }

    public static State valueOf(Integer ID)
    {
        return States.getStateById(ID);
    }
    
    @Override
    public int compareTo(State state)
    {
        return state.name.compareToIgnoreCase(this.name);
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
