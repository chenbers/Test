package com.inthinc.pro.dao.report;

import java.util.Collection;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.dvir.DVIRInspectionRepairReport;

public interface DVIRInspectionRepairReportDAO {
    public Collection<DVIRInspectionRepairReport> getDVIRInspectionRepairsForGroup(List<Integer> groupIDs, Interval interval);
}
