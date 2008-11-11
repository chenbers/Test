package com.inthinc.pro.dao.hessian;

import java.util.List;

import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.RedFlag;

public class RedFlagHessianDAO extends GenericHessianDAO<RedFlag, Integer, CentralService> implements RedFlagDAO
{

    @Override
    public List<RedFlag> getRedFlags(Integer groupID)
    {
        List<RedFlag> redFlagList = convertToModelObject(getSiloService().getRedFlags(groupID));
        return redFlagList;
        
    }

}
