package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.ForgivenType;
import com.inthinc.pro.model.Trip;

public interface CrashReportDAO extends GenericDAO<CrashReport, Integer> {
    public List<CrashReport> findByGroupID(Integer groupID);

    public List<CrashReport> findByGroupID(Integer groupID, ForgivenType forgivenType);

    public List<CrashReport> findByGroupID(Integer groupID, Date startDate, Date stopDate, ForgivenType forgivenType);

    public Trip getTrip(CrashReport crashReport);

    public Integer forgiveCrash(Integer groupID);

    public Integer unforgiveCrash(Integer groupID);
}
