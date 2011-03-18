/**
 * 
 */
package com.inthinc.pro.reports.ifta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

/**
 * ReportCriteria parent class for DOT/IFTA reports.
 */
public abstract class DOTReportCriteria extends GroupListReportCriteria {

    public static final Long ZERO_DATA = 0L;

    protected DateTimeFormatter dateTimeFormatter;
    protected String units;
    protected GroupDAO groupDAO;
    protected StateMileageDAO stateMileageDAO;
    protected GroupHierarchy accountGroupHierarchy;

    /**
     * Default constructor.
     * 
     * @param reportType
     *            the report type
     * @param locale
     *            the user Locale
     */
    public DOTReportCriteria(ReportType reportType, Locale locale) {
        super(reportType, locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        setMeasurementType(MeasurementType.ENGLISH);
    }

    public GroupHierarchy getAccountGroupHierarchy() {
        return accountGroupHierarchy;
    }

    public void setAccountGroupHierarchy(GroupHierarchy accountGroupHierarchy) {
        this.accountGroupHierarchy = accountGroupHierarchy;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.reports.ReportCriteria#setMeasurementType(com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public void setMeasurementType(MeasurementType measurementType) {
        super.setMeasurementType(measurementType);
        if (measurementType != null)
            units = measurementType.name().toLowerCase();
    }

    /**
     * Setter for Group DAO.
     * 
     * @param groupDAO
     */
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    /**
     * Initiate the data set and the parameters for the report.
     * 
     * @param groupId
     *            the groupId chosen by the user
     * @param interval
     *            the date period
     * @param iftaOnly
     *            the flag to consider only IFTA
     */
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, boolean dotOnly) {
        addParameter(ReportCriteria.REPORT_START_DATE, dateTimeFormatter.print(interval.getStart()));
        addParameter(ReportCriteria.REPORT_END_DATE, dateTimeFormatter.print(interval.getEnd()));
        addParameter(ReportCriteria.REPORT_IFTA_ONLY, (dotOnly?1:0));
        addParameter("units", units);
        this.accountGroupHierarchy = accountGroupHierarchy;

        List<StateMileage> dataList = new ArrayList<StateMileage>();

        List<Group> groupList = filterOutChildrenGroups(groupIDList, accountGroupHierarchy);
        // List<Group> groupList = getReportGroupList(groupIDList, accountGroupHierarchy);

        for (Group group : groupList) {
            List<StateMileage> list = getDataByGroup(group.getGroupID(), interval, dotOnly);
            if (list != null) {
                dataList.addAll(list);
            }
        }

        initDataSet(dataList);
    }

    /*
     * Since the back end already return data for all children of a group, remove subgroups from selection if any parent group is already selected.
     */
    private List<Group> filterOutChildrenGroups(List<Integer> groupIDList, GroupHierarchy groupHierarchy) {

        List<Group> result = new ArrayList<Group>();
        Set<Integer> childrenIds = new HashSet<Integer>();

        for (Integer groupId : groupIDList) {
            List<Integer> subGroupIdList = groupHierarchy.getSubGroupIDList(groupId);
            subGroupIdList.remove(groupId);
            childrenIds.addAll(subGroupIdList);
        }

        for (Integer groupId : groupIDList) {
            Group group = groupHierarchy.getGroup(groupId);

            if (group != null && !childrenIds.contains(groupId)) {
                result.add(group);
            }
        }

        return result;
    }

    abstract void initDataSet(List<StateMileage> list);

    abstract List<StateMileage> getDataByGroup(Integer groupID, Interval interval, boolean dotOnly);

    /**
     * The StateMileageDAO setter.
     * 
     * @param stateMileageDAO
     *            the DAO to set
     */
    public void setStateMileageDAO(StateMileageDAO stateMileageDAO) {
        this.stateMileageDAO = stateMileageDAO;
    }

    public String getFullGroupName(Integer groupID) {
        if (accountGroupHierarchy == null)
            return "";
        String fullName = accountGroupHierarchy.getFullGroupName(groupID, GROUP_SEPARATOR);
        if (fullName.endsWith(GROUP_SEPARATOR)) {
            fullName = fullName.substring(0, fullName.length() - GROUP_SEPARATOR.length());
        }
        return fullName;

    }

    public String getShortGroupName(Integer groupID) {
        String res = "";
        if (accountGroupHierarchy != null) {
            res = accountGroupHierarchy.getShortGroupName(groupID, SLASH_GROUP_SEPERATOR);
        }
        return res;

    }

}
