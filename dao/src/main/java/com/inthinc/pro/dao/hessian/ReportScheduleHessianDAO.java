package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.ReportSchedule;

public class ReportScheduleHessianDAO extends GenericHessianDAO<ReportSchedule, Integer> implements ReportScheduleDAO
{
    @Override
    public List<ReportSchedule> getReportSchedulesByUserID(Integer userID)
    {
        try
        {
            List<Map<String, Object>> reportSchedules = getSiloService().getReportPrefsByUserID(userID);
            return getMapper().convertToModelObject(reportSchedules, ReportSchedule.class);
        }catch(EmptyResultSetException e){
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<ReportSchedule> getReportSchedulesByAccountID(Integer accountID)
    {
        try
        {
            List<Map<String, Object>> reportSchedules = getSiloService().getReportPrefsByAcctID(accountID);
            return getMapper().convertToModelObject(reportSchedules, ReportSchedule.class);
        }catch(EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

	@Override
	public List<ReportSchedule> getReportSchedulesByUserIDDeep(Integer userID) {
        try
        {
            List<Map<String, Object>> reportSchedules = getSiloService().getReportPrefsByUserIDDeep(userID);
            return getMapper().convertToModelObject(reportSchedules, ReportSchedule.class);
        }catch(EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
	}

    @Override
    public List<ReportSchedule> getActiveReportSchedulesByAccountID(Integer accountID) {
        throw new NotImplementedException();
    }

}
