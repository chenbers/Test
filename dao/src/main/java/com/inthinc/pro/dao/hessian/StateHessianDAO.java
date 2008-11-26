package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.StateDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.StateObj;

public class StateHessianDAO extends GenericHessianDAO<StateObj, Integer> implements StateDAO
{
    @Override
    public List<StateObj> getStates()
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getStates(), StateObj.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }

}
