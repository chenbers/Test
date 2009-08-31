package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.Trip;


public interface CrashReportDAO extends GenericDAO<CrashReport, Integer>{
    public static final Integer EXCLUDE_FORGIVEN = 0;
    public static final Integer INCLUDE_FORGIVEN = 1;
    
    public List<CrashReport> getCrashReportsByGroupID(Integer groupID);
    public List<CrashReport> getCrashReportsByGroupIDAndForgiven(Integer groupID,Integer incForgiven);
    public List<CrashReport> getCrashes(Integer groupID, Date startDT, Date stopDT, Integer incForgiven);
    public Trip getTrip(CrashReport crashReport);
    public Integer forgiveCrash(Integer groupID);
    public Integer unforgiveCrash(Integer groupID);
}
