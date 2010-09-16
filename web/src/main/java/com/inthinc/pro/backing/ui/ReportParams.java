package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.reports.CriteriaType;
import com.inthinc.pro.util.MessageUtil;

public class ReportParams implements Cloneable {
    
    private DateRange dateRange;
    private Integer groupID;
    private Integer driverID;
    private Locale locale;
    private ReportGroup reportGroup;

    List<Driver> driverList;
    GroupHierarchy groupHierarchy;
    
    public ReportParams(Locale locale)
    {
        dateRange = new DateRange(locale);
        this.locale = locale;
    }
    
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Integer getGroupID() {
        return groupID;
    }
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    
    public Boolean getValid() {
        
        return getErrorMsg() == null;
    }

    public String getErrorMsg() {
        
        if (reportGroup == null)
            return MessageUtil.getMessageString("reportParams_noReportSelected",getLocale());
        
        for (CriteriaType criteriaType : reportGroup.getCriterias()) {
            if (criteriaType == CriteriaType.TIMEFRAME) {
                if (!(getDateRange() != null && getDateRange().getBadDates() == null))
                    return getDateRange().getBadDates();
            }
        }
        if (reportGroup.getEntityType() == EntityType.ENTITY_DRIVER && getDriverID() == null) 
            return MessageUtil.getMessageString("reportParams_noDriverSelected",getLocale());
        
        if (reportGroup.getEntityType() == EntityType.ENTITY_GROUP && getGroupID() == null) 
            return MessageUtil.getMessageString("reportParams_noGroupSelected",getLocale());
        
        
        return null;
    }
    
    public DateRange getDateRange() {
        return dateRange;
    }
    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    
    public ReportGroup getReportGroup() {
        return reportGroup;
    }

    public void setReportGroup(ReportGroup reportGroup) {
        this.reportGroup = reportGroup;
    }

    public List<CriteriaType> getSelectedCriteria() {
        if (reportGroup == null)
            return new ArrayList<CriteriaType>();
        
        return Arrays.asList(reportGroup.getCriterias());
    }
    
    public EntityType getSelectedEntityType() {
        if (reportGroup == null)
            return null;
        return reportGroup.getEntityType();
        
    }
    
//    protected final static String BLANK_SELECTION = "&#160;";
    protected final static String BLANK_SELECTION = "";

    protected static void sort(List<SelectItem> selectItemList) {
        Collections.sort(selectItemList, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return o1.getLabel().toLowerCase().compareTo(
                        o2.getLabel().toLowerCase());
            }
        });
    }

    public List<SelectItem> getGroups() {
        List<SelectItem> groups = new ArrayList<SelectItem>();
        if (getGroupHierarchy() == null)
            return groups;
        groups.add(new SelectItem(null, BLANK_SELECTION));
        for (final Group group : getGroupHierarchy().getGroupList()) {
            String fullName = getGroupHierarchy().getFullGroupName(
                    group.getGroupID());
            if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
                fullName = fullName.substring(0, fullName.length()
                        - GroupHierarchy.GROUP_SEPERATOR.length());
            }

            groups.add(new SelectItem(group.getGroupID(), fullName));

        }
        sort(groups);

        return groups;
    }
    public List<SelectItem> getDrivers() {
        List<SelectItem> drivers = new ArrayList<SelectItem>();
        if (getDriverList() == null)
            return drivers;
        drivers.add(new SelectItem(null, BLANK_SELECTION));
        for (Driver driver : getDriverList()) {
            drivers.add(new SelectItem(driver.getDriverID(), driver.getPerson().getFullName()));
        }
        return drivers;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    public GroupHierarchy getGroupHierarchy() {
        return groupHierarchy;
    }

    public void setGroupHierarchy(GroupHierarchy groupHierarchy) {
        this.groupHierarchy = groupHierarchy;
    }

    

    public ReportParams clone() 
    {
        try {
            return (ReportParams)super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
    }

}
