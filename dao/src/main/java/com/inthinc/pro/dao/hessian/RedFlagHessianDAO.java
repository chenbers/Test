package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.hessian.mapper.RedFlagHessianMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.RedFlag;

public class RedFlagHessianDAO extends GenericHessianDAO<RedFlag, Integer> implements RedFlagDAO
{
    public RedFlagHessianDAO()
    {
        super();
        super.setMapper(new RedFlagHessianMapper());
    }


    @Override
    public List<RedFlag> getRedFlags(Integer groupID, Integer daysBack)
    {
        try
        {
            Date endDate = new Date();
            Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);

            List<RedFlag> redFlagList = getMapper().convertToModelObject(getSiloService().getRedFlags(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), RedFlag.class);
            return redFlagList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

}
