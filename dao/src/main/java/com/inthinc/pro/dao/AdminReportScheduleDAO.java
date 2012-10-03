package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.ReportSchedule;

public interface AdminReportScheduleDAO extends GenericDAO<ReportSchedule, Integer> {

    public List<ReportSchedule> getReportSchedulesForUsersDeep(Integer userID,List<Integer> groupIDs, Integer acctID);

}
