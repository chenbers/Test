package com.inthinc.pro.model.app;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.StateDAO;
import com.inthinc.pro.model.State;

public class States implements BaseAppEntity
{
    Logger logger = Logger.getLogger(States.class);
    private static Map<Integer, State> statesMap;
    
    private StateDAO stateDAO;

    public States()
    {
    }
    
    
    public void init()
    {
        List<State> statesList = stateDAO.getStates();
        statesMap = new HashMap<Integer, State>();

        for (State state : statesList)
        {
               statesMap.put(state.getStateID(), state);
        }
    }
    
    public StateDAO getStateDAO()
    {
        return stateDAO;
    }


    public void setStateDAO(StateDAO stateDAO)
    {
        this.stateDAO = stateDAO;
    }


    public static Map<Integer, State>  getStates()
    {
        return statesMap;
    }
    
    public static State getStateById(Integer id)
    {
        return getStates().get(id);
    }
    
    public static State getStateByAbbrev(String abbrev)
    {
        for (State state : getStates().values())
        {
            if (state.getAbbrev().equals(abbrev))
            {
                return state;
            }
        }
        return null;
    }

    public static State getStateByName(String name)
    {
        for (State state : getStates().values())
        {
            if (state.getName().equals(name))
            {
                return state;
            }
        }
        return null;
    }
}
