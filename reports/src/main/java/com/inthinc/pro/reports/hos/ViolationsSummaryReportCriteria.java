package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
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
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.ViolationsSummary;

public abstract class ViolationsSummaryReportCriteria extends GroupListReportCriteria  {

    private static final Logger logger = Logger.getLogger(ViolationsSummaryReportCriteria.class);
    
    protected GroupDAO groupDAO;
    protected HOSDAO hosDAO;
    
    protected DateTimeFormatter dateTimeFormatter;

    public ViolationsSummaryReportCriteria(ReportType reportType, Locale locale) 
    {
        super(reportType, locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }
    

    //public abstract void init(Integer userGroupID, Integer groupID, Interval interval);
    
    public void init(Integer userGroupID, Integer rptGroupID, Interval interval)
    {
        Group userGroup = groupDAO.findByID(userGroupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(userGroup.getAccountID(), userGroup.getGroupID());
        GroupHierarchy userGroupHierarchy = new GroupHierarchy(groupList);
        List<Integer> reportGroupList = new ArrayList<Integer>();
        reportGroupList.add(rptGroupID);
        for (Group childGroup : userGroupHierarchy.getChildren(userGroupHierarchy.getGroup(rptGroupID))) {
            reportGroupList.add(rptGroupID);
        }
        
        init(userGroupHierarchy, reportGroupList, interval);
    }
    public abstract void init(GroupHierarchy userGroupHierarchy, List<Integer> groupIDList, Interval interval);
    protected abstract void updateSummaryDriverCount(ViolationsSummary summary, Driver driver);

    
    protected ViolationsSummary findSummary(GroupHierarchy groupHierarchy, Map<Integer, ? extends ViolationsSummary> dataMap, Integer groupID) {
        ViolationsSummary summary = dataMap.get(groupID);
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


    public HOSDAO getHosDAO() {
        return hosDAO;
    }


    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }


}
