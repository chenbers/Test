package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.model.RedFlag;

public class RedFlagHessianDAO extends GenericHessianDAO<RedFlag, Integer> implements RedFlagDAO
{

    @Override
    public List<RedFlag> getRedFlags(Integer groupID)
    {
        try
        {
            List<RedFlag> redFlagList = getMapper().convertToModelObject(getSiloService().getRedFlags(groupID), RedFlag.class);
            return redFlagList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        catch (ProxyException e)
        {
            // TODO: remove this when method is implemented
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }


    }

}
