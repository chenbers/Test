package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.reports.CriteriaType;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

public class ReportParams implements Cloneable {

    private DateRange dateRange;
    private TimeFrameSelect timeFrameSelect;
    private Integer groupID;
    private List<String> groupIDSelectList;
    private Integer driverID;
    private Locale locale;
    private ReportGroup reportGroup;
    private ReportParamType paramType;
    private Boolean isIfta;
    private Boolean isExpired;
    private boolean includeInactiveDrivers;
    private boolean includeZeroMilesDrivers;
    
    List<Driver> driverList;
    GroupHierarchy groupHierarchy;

    private static final Logger logger = Logger.getLogger(ReportParams.class);

    public ReportParams(Locale locale) {
        dateRange = new DateRange(locale);
        timeFrameSelect = new TimeFrameSelect(locale);
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

    public List<Integer> getGroupIDList() {
        if (groupIDSelectList == null)
            return null;

        List<Integer> groupIDList = new ArrayList<Integer>();
        for (String groupIDStr : groupIDSelectList) {
            try {
                groupIDList.add(Integer.valueOf(groupIDStr));
            } catch (NumberFormatException ex) {
                logger.error(ex);

            }
        }
        return groupIDList;
    }

    public Boolean getIsIfta() {
        return isIfta;
    }

    public void setIsIfta(Boolean isIfta) {
        this.isIfta = isIfta;
    }

    public Boolean getValid() {

        return getErrorMsg() == null;
    }

    public String getErrorMsg() {
        String badDates = "";
        if (reportGroup == null)
            return MessageUtil.getMessageString("reportParams_noReportSelected", getLocale());

        for (CriteriaType criteriaType : reportGroup.getCriterias()) {
            if (criteriaType == CriteriaType.TIMEFRAME || criteriaType == CriteriaType.TIMEFRAME_ALT_PLUS_CUSTOM_RANGE) {
                if (!(getDateRange() != null && getDateRange().getBadDates() == null)) {
                    badDates = getDateRange().getBadDates() + " ";
                }
                if (criteriaType == CriteriaType.TIMEFRAME_ALT_PLUS_CUSTOM_RANGE) {
                    if (getDateRange() != null && getDateRange().isMoreThanOneYear())
                        badDates += MessageUtil.getMessageString("dateRange_moreThanOneYear", getLocale());
                }
            }
        }
        if (reportGroup.getEntityType() == EntityType.ENTITY_DRIVER && getDriverID() == null)
            return MessageUtil.getMessageString("reportParams_noDriverSelected", getLocale()) + " " + badDates;

        if ((reportGroup.getEntityType() == EntityType.ENTITY_GROUP || reportGroup.getEntityType() == EntityType.ENTITY_GROUP_AND_EXPIRED) && getGroupID() == null)
            return MessageUtil.getMessageString("reportParams_noGroupSelected", getLocale()) + " " + badDates;

        if ((reportGroup.getEntityType() == EntityType.ENTITY_GROUP_LIST || reportGroup.getEntityType() == EntityType.ENTITY_GROUP_LIST_AND_IFTA)
                && (getGroupIDSelectList() == null || getGroupIDSelectList().size() == 0))
            return MessageUtil.getMessageString("reportParams_noGroupSelected", getLocale()) + " " + badDates;

        if (reportGroup.getEntityType() == EntityType.ENTITY_GROUP_LIST_OR_DRIVER) {
            if (getParamType() == null || getParamType() == ReportParamType.NONE)
                return MessageUtil.getMessageString("reportParams_noReportOnSelected", getLocale()) + " " + badDates;
            else if (getParamType() == ReportParamType.DRIVER && getDriverID() == null)
                return MessageUtil.getMessageString("reportParams_noDriverSelected", getLocale()) + " " + badDates;
            else if (getParamType() == ReportParamType.GROUPS && (getGroupIDSelectList() == null || getGroupIDSelectList().size() == 0))
                return MessageUtil.getMessageString("reportParams_noGroupSelected", getLocale()) + " " + badDates;
        }
        if (reportGroup.getEntityType() == EntityType.ENTITY_GROUP_OR_DRIVER) {
            if (getParamType() == null || getParamType() == ReportParamType.NONE)
                return MessageUtil.getMessageString("reportParams_noReportOnSelected", getLocale()) + " " + badDates;
            else if (getParamType() == ReportParamType.DRIVER && getDriverID() == null)
                return MessageUtil.getMessageString("reportParams_noDriverSelected", getLocale()) + " " + badDates;
            else if (getParamType() == ReportParamType.GROUPS && getGroupID() == null)
                return MessageUtil.getMessageString("reportParams_noGroupSelected", getLocale()) + " " + badDates;
        }
        if (badDates != null && !badDates.isEmpty())
            return badDates;
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

    protected final static String BLANK_SELECTION = " ";

    protected static void sort(List<SelectItem> selectItemList) {
        Collections.sort(selectItemList, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return o1.getLabel().toLowerCase().compareTo(o2.getLabel().toLowerCase());
            }
        });
    }

    public List<SelectItem> getGroups() {
        List<SelectItem> groups = new ArrayList<SelectItem>();
        if (getGroupHierarchy() == null)
            return groups;
        for (final Group group : getGroupHierarchy().getGroupList()) {
            String fullName = getGroupHierarchy().getFullGroupName(group.getGroupID());
            if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
                fullName = fullName.substring(0, fullName.length() - GroupHierarchy.GROUP_SEPERATOR.length());
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
        sort(drivers);
        return drivers;
    }

    public List<SelectItem> getReportParamTypes() {
        return SelectItemUtil.toList(ReportParamType.class, true, ReportParamType.NONE);
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

    public List<String> getGroupIDSelectList() {
        return groupIDSelectList;
    }

    public void setGroupIDSelectList(List<String> groupIDSelectList) {
        this.groupIDSelectList = groupIDSelectList;
    }

    public ReportParamType getParamType() {
        return paramType;
    }

    public void setParamType(ReportParamType paramType) {
        this.paramType = paramType;
        if (paramType == ReportParamType.NONE) {
            this.setGroupIDSelectList(null);
            this.setDriverID(null);
        }
        if (this.paramType == ReportParamType.DRIVER)
            this.setGroupIDSelectList(null);
        if (this.paramType == ReportParamType.GROUPS)
            this.setDriverID(null);
    }

    public ReportParams clone() {
        try {
            return (ReportParams) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public Boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public TimeFrameSelect getTimeFrameSelect() {
        return timeFrameSelect;
    }

    public void setTimeFrameSelect(TimeFrameSelect timeFrameSelect) {
        this.timeFrameSelect = timeFrameSelect;
    }

    public boolean isIncludeInactiveDrivers() {
        return includeInactiveDrivers;
    }

    public void setIncludeInactiveDrivers(boolean includeInactiveDrivers) {
        this.includeInactiveDrivers = includeInactiveDrivers;
    }

    public boolean isIncludeZeroMilesDrivers() {
        return includeZeroMilesDrivers;
    }

    public void setIncludeZeroMilesDrivers(boolean includeZeroMilesDrivers) {
        this.includeZeroMilesDrivers = includeZeroMilesDrivers;
    }
}
