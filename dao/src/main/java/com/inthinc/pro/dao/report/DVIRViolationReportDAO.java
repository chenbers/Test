package com.inthinc.pro.dao.report;

import java.util.Collection;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.dvir.DVIRViolationReport;

public interface DVIRViolationReportDAO {
	
	public Collection<DVIRViolationReport> getDVIRViolationsForGroup(List<Integer> groupIDs, Interval interval);

}
