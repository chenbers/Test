package com.inthinc.pro.dao.hessian;

import java.util.List;

import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.model.RedFlag;

public class RedFlagHessianDAO extends GenericHessianDAO<RedFlag, Integer> implements RedFlagDAO
{

    @Override
    public List<RedFlag> getRedFlags(Integer groupID)
    {
        List<RedFlag> redFlagList = getMapper().convertToModelObject(getSiloService().getRedFlags(groupID), RedFlag.class);
        return redFlagList;

    }

}
