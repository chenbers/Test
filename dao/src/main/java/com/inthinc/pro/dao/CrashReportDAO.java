package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.CrashReport;


public interface CrashReportDAO extends GenericDAO<CrashReport, Integer>{
    
    public List<CrashReport> getCrashReportsByGroupID(Integer groupID);
    public Integer forgiveCrash(Integer groupID);
    public Integer unforgiveCrash(Integer groupID);
}
