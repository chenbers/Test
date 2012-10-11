package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.State;

public interface StateDAO extends GenericDAO<State, Integer>
{
    List<State> getStates();
    
}
