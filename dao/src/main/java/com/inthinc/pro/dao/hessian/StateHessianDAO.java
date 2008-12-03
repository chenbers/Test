package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.StateDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.State;

public class StateHessianDAO extends GenericHessianDAO<State, Integer> implements StateDAO
{
    @Override
    public List<State> getStates()
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getStates(), State.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }

}
