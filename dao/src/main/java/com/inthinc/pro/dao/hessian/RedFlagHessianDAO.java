package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.hessian.mapper.RedFlagHessianMapper;
import com.inthinc.pro.model.RedFlag;

public class RedFlagHessianDAO extends GenericHessianDAO<RedFlag, Integer> implements RedFlagDAO
{
    public RedFlagHessianDAO()
    {
        super();
        super.setMapper(new RedFlagHessianMapper());
    }


    @Override
    public List<RedFlag> getRedFlags(Integer groupID, Integer count)
    {
        try
        {
            List<RedFlag> redFlagList = getMapper().convertToModelObject(getSiloService().getRedFlags(groupID, count), RedFlag.class);
            return redFlagList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

}
