package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.RedFlagHessianMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.RedFlag;

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

//TESTING FOR PAGINATION
/*    
	@Override
	public List<RedFlag> getRedFlagPage(Integer groupID, Integer daysBack,
			Integer includeForgiven, PageParams pageParams) {
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
        catch (GenericHessianException e)
        {
        	if (e.getErrorCode() == 422)
        	{
        		// not implemented
        		return new ArrayList<RedFlag>();
        	}
        	throw e;
        }
	}
	@Override
	public Integer getRedFlagsCount(Integer groupID, Integer daysBack,
			Integer includeForgiven, List<FilterField> filterList) {
        try
        {
// TODO: SHOULD FILTER AND SORT JUST BE MAPs?        	
            Date endDate = new Date();
            Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
            Map<String, String> filterMap = new HashMap<String, String>();
            if (filterList != null)
            {
	            for (FilterField filterField : filterList)
	            {
	            	filterMap.put(filterField.getField(), filterField.getFilter());
	            }
            }
            return getChangedCount(getSiloService().getRedFlagsCount(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven, filterMap));
            
        }
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        catch (GenericHessianException e)
        {
        	if (e.getErrorCode() == 422)
        	{
        		// not implemented
        		return 0;
        	}
        	throw e;
        }
	}
*/
}
