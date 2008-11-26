package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.StateObj;

public interface StateDAO extends GenericDAO<StateObj, Integer>
{
    List<StateObj> getStates();
    
}
