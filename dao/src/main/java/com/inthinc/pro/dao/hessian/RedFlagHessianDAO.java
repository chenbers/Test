package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.GenericHessianException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.mapper.RedFlagHessianMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class RedFlagHessianDAO extends GenericHessianDAO<RedFlag, Integer> implements RedFlagDAO
{
    private static final Logger logger = Logger.getLogger(RedFlagHessianDAO.class);
    public RedFlagHessianDAO()
    {
        super();
        super.setMapper(new RedFlagHessianMapper());
    }


	@Override
	public List<RedFlag> getRedFlagPage(Integer groupID, Date startDate, Date endDate,
			Integer includeForgiven, PageParams pageParams) {
        try
        {
            List<RedFlag> redFlagList = getMapper().convertToModelObject(getSiloService().getRedFlagsPage(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven, getMapper().convertToMap(pageParams)), RedFlag.class);
            return redFlagList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
		
		
	}
	@Override
	public Integer getRedFlagCount(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<TableFilterField> filterList) {
		
        try
        {
            if (filterList == null)
            	filterList = new ArrayList<TableFilterField>();
            return getChangedCount(getSiloService().getRedFlagsCount(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven, getMapper().convertList(filterList)));
            
        }
        catch (EmptyResultSetException e)
        {
            return 0;
        }
	}

}
