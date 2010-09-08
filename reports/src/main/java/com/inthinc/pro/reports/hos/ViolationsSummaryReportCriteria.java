package com.inthinc.pro.reports.hos;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.RuleViolationTypes;
import com.inthinc.hos.model.ViolationsData;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.ViolationsSummary;
import com.inthinc.pro.reports.tabular.Tabular;

public abstract class ViolationsSummaryReportCriteria extends ReportCriteria  {

    private static final Logger logger = Logger.getLogger(ViolationsSummaryReportCriteria.class);
    
    protected GroupDAO groupDAO;
    protected DriverDAO driverDAO;
    protected HOSDAO hosDAO;
    
    protected DateTimeFormatter dateTimeFormatter;

    public ViolationsSummaryReportCriteria(ReportType reportType, Locale locale) 
    {
        super(reportType, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }
    

    public abstract void init(Integer groupID, Interval interval);
    protected abstract void updateSummaryDriverCount(ViolationsSummary summary, Driver driver);

    
    protected ViolationsSummary findSummary(GroupHierarchy groupHierarchy, Group topGroup, Map<Integer, ? extends ViolationsSummary> dataMap, Integer groupID) {
        Group driverGroup = groupHierarchy.getGroup(groupID);
        if (driverGroup == null) {
            logger.error("Group is null for groupID: " + groupID);
            return null;
        }
        Group topAncestor = (groupID.equals(topGroup.getGroupID())) ? topGroup : groupHierarchy.getTopAncestor(driverGroup, groupHierarchy.getChildren(topGroup));
        if (topAncestor == null) {
            logger.error("Group topAncestor is null for group: " + driverGroup.getGroupID());
            return null;
        }
        ViolationsSummary summary = dataMap.get(topAncestor.getGroupID());
        if (summary == null) {
            logger.error("HosViolationsSummary  is null for group: " + groupID);
            return null;
        }
        return summary;
    }

    protected void updateSummary(ViolationsSummary summary, List<ViolationsData> violations) {
        
        if (violations == null)
            return;

        for (ViolationsData data : violations) {
            
            for (Entry<RuleViolationTypes, Long> entry : data.getViolationMap().entrySet()) {
                RuleViolationTypes violationType = entry.getKey();
                int minutes = entry.getValue() == null ? 0 : entry.getValue().intValue();
                
                summary.updateMinutes(violationType, minutes);
            }
        }
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public HOSDAO getHosDAO() {
        return hosDAO;
    }


    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }


}
