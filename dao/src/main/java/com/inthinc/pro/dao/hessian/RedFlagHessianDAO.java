package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
    public List<RedFlag> getRedFlags(Integer groupID, Integer daysBack, Integer includeForgiven)
    {
        try
        {
            Date endDate = new Date();
            Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
//logger.info("getRedFlags for groupID: " + groupID);            

            List<RedFlag> redFlagList = getMapper().convertToModelObject(getSiloService().getRedFlags(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven), RedFlag.class);
            return redFlagList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

	@Override
	public List<RedFlag> getRedFlagPage(Integer groupID, Integer daysBack,
			Integer includeForgiven, PageParams pageParams) {
/* REAL IMPL		
        try
        {
            Date endDate = new Date();
            Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
            List<RedFlag> redFlagList = getMapper().convertToModelObject(getSiloService().getRedFlagsPage(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven, getMapper().convertToMap(pageParams)), RedFlag.class);
            return redFlagList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        catch (ProxyException e)
        {
        	if (e.getErrorCode() == 422)
        	{
        		// not implemented
        		return new ArrayList<RedFlag>();
        	}
        	throw e;
        }
*/
		
		/* TEMPORORARY */
		List<RedFlag> redFlagList = getRedFlags(groupID, daysBack, includeForgiven);
		for (RedFlag redFlag : redFlagList) {
			
			// unknown driver id for test account (temporary)
			if (!redFlag.getEvent().getDriverID().equals(Integer.valueOf(5690))) {
				redFlag.getEvent().setDriverName("Driver" + redFlag.getEvent().getDriverID());
				redFlag.getEvent().setGroupName("Group" + redFlag.getEvent().getGroupID());
				redFlag.getEvent().setDriverTimeZone(TimeZone.getTimeZone("US/Mountain"));
			}
			redFlag.getEvent().setVehicleName("Vehicle" + redFlag.getEvent().getVehicleID());
			
		}

		
		return redFlagList.subList(pageParams.getStartRow(), pageParams.getEndRow());
		
		
	}
	@Override
	public Integer getRedFlagCount(Integer groupID, Integer daysBack, Integer includeForgiven, List<TableFilterField> filterList) {
		
/* REAL IMPL		
        try
        {
            Date endDate = new Date();
            Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
            if (filterList == null)
            	filterList = new ArrayList<TableFilterField>();
            return getChangedCount(getSiloService().getRedFlagsCount(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven, getMapper().convertList(filterList)));
            
        }
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        catch (ProxyException e)
        {
        	if (e.getErrorCode() == 422)
        	{
        		// not implemented
        		return 0;
        	}
        	throw e;
        }
*/
		
		/* TEMPORORARY */
	    return getRedFlags(groupID, daysBack, includeForgiven).size();
	}
}
