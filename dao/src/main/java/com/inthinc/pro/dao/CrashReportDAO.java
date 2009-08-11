package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.CrashReport;


public interface CrashReportDAO extends GenericDAO<CrashReport, Integer>{
    
    public List<CrashReport> getCrashReportsByGroupID(Integer groupID);
}
