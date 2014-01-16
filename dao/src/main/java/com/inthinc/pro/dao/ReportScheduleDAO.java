package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.ReportSchedule;

public interface ReportScheduleDAO extends GenericDAO<ReportSchedule, Integer>
{
    public List<ReportSchedule> getReportSchedulesByUserID(Integer userID);
    
    public List<ReportSchedule> getReportSchedulesByAccountID(Integer accountID);

    public List<ReportSchedule> getReportSchedulesByUserIDDeep(Integer userID);
    
    public List<ReportSchedule> getActiveReportSchedulesByAccountID(Integer accountID);

}
