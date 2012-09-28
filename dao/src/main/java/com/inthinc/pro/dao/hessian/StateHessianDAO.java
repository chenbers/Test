package com.inthinc.pro.dao.hessian;

import java.util.List;

import com.inthinc.pro.dao.StateDAO;
import com.inthinc.pro.model.State;

public class StateHessianDAO extends GenericHessianDAO<State, Integer> implements StateDAO
{
    @Override
    public List<State> getStates()
    {
        // don't catch the empty result set exception for this, because an empty result set in this case is a real error
        return getMapper().convertToModelObject(getSiloService().getStates(), State.class);
    }

}
