package com.inthinc.pro.backing;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

/**
 * 
 * @author mstrong
 * 
 */
public class ReportScheduleBean extends BaseAdminBean<ReportScheduleBean.ReportScheduleView> implements TablePrefOptions<ReportScheduleBean.ReportScheduleView>, Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -8422069679000248942L;

    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 4 };

    private static final String REDIRECT_REPORT_SCHEDULES = "pretty:adminReportSchedules";
    private static final String REDIRECT_REPORT_SCHEDULE = "pretty:adminReportSchedule";
    private static final String REDIRECT_EDIT_REPORT_SCHEDULE = "pretty:adminEditReportSchedule";

    private List<SelectItem> reportGroups;

    /*
     * Spring managed beans
     */

    private ReportScheduleDAO reportScheduleDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private GroupDAO groupDAO;

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("occurrence");
        AVAILABLE_COLUMNS.add("lastEmail");
        AVAILABLE_COLUMNS.add("report");
        AVAILABLE_COLUMNS.add("status");

        

    }

    private static void sort(List<SelectItem> selectItemList)
    {
        Collections.sort(selectItemList, new Comparator<SelectItem>()
        {
            @Override
            public int compare(SelectItem o1, SelectItem o2)
            {
                return MessageUtil.getMessageString(o1.toString()).toLowerCase().compareTo(MessageUtil.getMessageString(o2.toString()).toLowerCase());
            }
        });
    }

    public List<SelectItem> getReportGroups()
    {

        reportGroups = new ArrayList<SelectItem>();

        for (ReportGroup rt : EnumSet.allOf(ReportGroup.class))
        {
            List<GroupType> groupTypes = Arrays.asList(rt.getGroupTypes());
            if (getGroupHierarchy().containsGroupTypes(groupTypes))
            {
                reportGroups.add(new SelectItem(rt.getCode(), MessageUtil.getMessageString(rt.toString())));
            }
        }
        sort(reportGroups);
        reportGroups.add(0, new SelectItem(null, ""));
        
        return reportGroups;
    }

    public EntityType getSelectedEntityType()
    {
        EntityType entityType = null;
        if (getItem() != null && this.getItem().getReportID() != null)
        {
            entityType = ReportGroup.valueOf(this.getItem().getReportID()).getEntityType();
        }
        return entityType;
    }

    public List<SelectItem> getDurations()
    {
        return SelectItemUtil.toList(Duration.class, true);
    }

    public List<SelectItem> getOccurrences()
    {
        return SelectItemUtil.toList(Occurrence.class, true,Occurrence.DAILY_CUSTOM);
    }

    public List<SelectItem> getStatuses()
    {
        return SelectItemUtil.toList(Status.class, false, Status.DELETED);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Integer> getDrivers()
    {
        Map<String, Integer> driverMap = new HashedMap();
        driverMap.put("", null);
        List<Driver> driverList = driverDAO.getAllDrivers(getGroupHierarchy().getTopGroup().getGroupID());
        for (Driver driver : driverList)
        {
            driverMap.put(driver.getPerson().getFullName(), driver.getDriverID());
        }

        return driverMap;
    }

    public List<SelectItem> getGroups()
    {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();

        List<Group> groupList = getGroupHierarchy().getGroupList();

        List<GroupType> acceptableGroupTypes = Arrays.asList(getItem().getReport().getGroupTypes());
        for (Group group : groupList)
        {
            if (acceptableGroupTypes.contains(group.getType()))
            {
                selectItemList.add(new SelectItem(group.getGroupID(), group.getName()));
            }

        }

        sort(selectItemList);

        selectItemList.add(0, new SelectItem(null, ""));
        return selectItemList;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Integer> getVehicles()
    {
        // TODO needs implementation
        return Collections.EMPTY_MAP;
    }

    @Override
    protected ReportScheduleView createAddItem()
    {
        final ReportSchedule reportSchedule = new ReportSchedule();
        reportSchedule.setAccountID(getAccountID());
        reportSchedule.setUserID(getUserID());

        List<Boolean> booleanList = new ArrayList<Boolean>();
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);

        reportSchedule.setDayOfWeek(booleanList);

        reportSchedule.setStatus(Status.ACTIVE);
        if (getUser() != null && getUser().getPerson() != null && getUser().getPerson().getPriEmail() != null)
        {
            reportSchedule.getEmailTo().add(getUser().getPerson().getPriEmail());
        }

        return createReportScheduleView(reportSchedule);
    }

    @Override
    protected void doDelete(List<ReportScheduleView> deleteItems)
    {
        for (final ReportScheduleView reportSchedule : deleteItems)
        {
            reportScheduleDAO.deleteByID(reportSchedule.getReportScheduleID());
            // add a message
            final String summary = MessageUtil.formatMessageString("reportSchedule_deleted", reportSchedule.getName());
            addInfoMessage(summary);
        }

    }
    
    
    @Override
    public String save()
    {
        if (isBatchEdit())
        {
            final boolean occurrence = Boolean.TRUE.equals(getUpdateField().get("occurrence"));
            if(item.getOccurrence() != null && item.getOccurrence().equals(Occurrence.WEEKLY))
            {
                getUpdateField().put("dayOfWeek", occurrence);
            }else if (item.getOccurrence() != null && item.getOccurrence().equals(Occurrence.MONTHLY))
            {
                getUpdateField().put("dayOfMonth",occurrence);
            }
        }
        return super.save();
    }

    @Override
    protected void doSave(List<ReportScheduleView> saveItems, boolean create)
    {
        for (final ReportScheduleView reportSchedule : saveItems)
        {

            if (reportSchedule.getOccurrence().equals(Occurrence.DAILY) || reportSchedule.getOccurrence().equals(Occurrence.WEEKLY))
            {
                Calendar now = Calendar.getInstance(getUtcTimeZone());
                reportSchedule.setStartDate(now.getTime());
            }
            else if (reportSchedule.getOccurrence().equals(Occurrence.MONTHLY))
            {
                logger.debug("Day of Month: " + reportSchedule.getDayOfMonth());
                Calendar now = Calendar.getInstance(getUtcTimeZone());
                if(reportSchedule.getDayOfMonth() != null){
                    now.set(Calendar.DATE, reportSchedule.getDayOfMonth());
                }
                reportSchedule.setStartDate(now.getTime());
            }

            // Get rid of all end dates
            reportSchedule.setEndDate(null);

            if (create)
            {
                reportSchedule.setReportScheduleID(reportScheduleDAO.create(getAccountID(), reportSchedule));
            }
            else
            {
                reportScheduleDAO.update(reportSchedule);
            }

            if (reportSchedule.getDriverID() != null)
            {
                Driver driver = driverDAO.findByID(reportSchedule.getDriverID());
                reportSchedule.setDriverName(driver.getPerson().getFullName());
            }

            if (reportSchedule.getVehicleID() != null)
            {
                Vehicle vehicle = vehicleDAO.findByID(reportSchedule.getVehicleID());
                reportSchedule.setVehicleName(vehicle.getFullName());
            }

            if (reportSchedule.getGroupID() != null)
            {
                Group group = groupDAO.findByID(reportSchedule.getGroupID());
                reportSchedule.setGroupName(group.getName());
            }

            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "reportSchedule_added" : "reportSchedule_updated", reportSchedule.getName());
            addInfoMessage(summary);
        }

    }
    
    @Override
    public ReportScheduleView getItem()
    {
        ReportScheduleView reportScheduleView = super.getItem();
        
        if ((reportScheduleView.getDayOfWeek() == null) || (reportScheduleView.getDayOfWeek().size() != 7))
        {
            final ArrayList<Boolean> dayOfWeek = new ArrayList<Boolean>();
            for (int i = 0; i < 7; i++)
                dayOfWeek.add(false);
            reportScheduleView.setDayOfWeek(dayOfWeek);
        }
        return reportScheduleView;
    }
    
    @Override
    public String batchEdit()
    {
        String resultValue = super.batchEdit();
        
        //If occurrences are different then null out dayOfWekk and dayOfMonth
        if(item != null && item.getOccurrence() == null)
        {
            item.setDayOfMonth(1);
            item.setDayOfWeek(createDayOfWeekList());
        }else
        {
            //Set all the boolean values of the day of week list to false if the two lists don't match
            for (ReportScheduleView t : getSelectedItems())
               BeanUtil.compareAndInitBoolList(getItem().getDayOfWeek(), t.getDayOfWeek());
        }
   
        return resultValue;
    }

    @Override
    protected boolean validateSaveItem(ReportScheduleView reportScheduleView)
    {
        boolean valid = true;

        if(reportScheduleView.getOccurrence() != null && reportScheduleView.getOccurrence().equals(Occurrence.WEEKLY))
        {
            if (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("occurrence")))
            {
                boolean dayPicked = false;
                for (boolean day : reportScheduleView.getDayOfWeek())
                {
                    if (day)
                    {
                        dayPicked = true;
                        break;
                    }
                }
                if (!dayPicked)
                {
                    final String summary = MessageUtil.formatMessageString("editReportSchedule_noDays");
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                    getFacesContext().addMessage("edit-form:day0", message);
                    valid = false;
                }
            }
        }

        if (reportScheduleView.getOccurrence() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("occurrence"))))
        {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
            getFacesContext().addMessage("edit-form:occurrence", message);
        }

        if (reportScheduleView.getOccurrence() != null && reportScheduleView.getOccurrence().equals(Occurrence.MONTHLY) && reportScheduleView.getDayOfMonth() == null
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("dayOfMonth"))))
        {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
            getFacesContext().addMessage("edit-form:dayOfMonth", message);
        }

        return valid;
    }

    @Override
    public String fieldValue(ReportScheduleView item, String column)
    {
        // Need to make sure we return the date string just as it appears in the UI table
        String returnString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        sdf.setTimeZone(getUtcTimeZone());

        if ("name".equals(column))
            returnString = item.getName();
        else if ("startDate".equals(column))
            returnString = item.getStartDate() != null ? sdf.format(item.getStartDate()) : "";
        else if ("endDate".equals(column))
            returnString = item.getEndDate() != null ? sdf.format(item.getEndDate()) : "";
        else if ("occurrence".equals(column))
            returnString = item.getOccurrence() != null ? item.getOccurrence().toString() : "";
        else if ("lastEmail".equals(column))
            returnString = item.getLastDate() != null ? sdf.format(item.getLastDate()) : "";
        else if ("report".equals(column))
            returnString = item.getReport() != null ? item.getReport().getLabel() : "";
        else if ("status".equals(column))
            returnString = item.getStatus() != null ? item.getStatus().toString() : "";

        logger.debug("column: " + column);
        logger.debug("returnValue: " + returnString);

        return returnString;

    }

    public TimeZone getUtcTimeZone()
    {
        return TimeZone.getTimeZone("UTC");
    }

    @Override
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        return columns;
    }

    @Override
    protected String getDisplayRedirect()
    {
        return REDIRECT_REPORT_SCHEDULE;
    }

    @Override
    protected String getEditRedirect()
    {
        return REDIRECT_EDIT_REPORT_SCHEDULE;
    }

    @Override
    protected String getFinishedRedirect()
    {
        return REDIRECT_REPORT_SCHEDULES;
    }

    @Override
    protected List<ReportScheduleView> loadItems()
    {
        List<ReportScheduleView> returnList = new ArrayList<ReportScheduleView>();
        List<ReportSchedule> reportScheduleList = reportScheduleDAO.getReportSchedulesByUserID(getProUser().getUser().getUserID());
        for (ReportSchedule reportSchedule : reportScheduleList)
        {
            returnList.add(createReportScheduleView(reportSchedule));
        }
        return returnList;
    }

    @Override
    protected ReportScheduleView revertItem(ReportScheduleView editItem)
    {
        return createReportScheduleView(reportScheduleDAO.findByID(editItem.getReportScheduleID()));
    }

    @Override
    public String getColumnLabelPrefix()
    {
        return "reportSchedulesHeader_";
    }

    @Override
    public TableType getTableType()
    {
        return TableType.REPORT_SCHEDULES;
    }

    private ReportScheduleView createReportScheduleView(ReportSchedule reportSchedule)
    {
        final ReportScheduleView reportScheduleView = new ReportScheduleView();
        BeanUtils.copyProperties(reportSchedule, reportScheduleView);
        reportScheduleView.setSelected(false);

        if (reportSchedule.getDriverID() != null)
        {
            Driver driver = driverDAO.findByID(reportScheduleView.getDriverID());
            reportScheduleView.setDriverName(driver.getPerson().getFullName());
        }

        if (reportSchedule.getVehicleID() != null)
        {
            Vehicle vehicle = vehicleDAO.findByID(reportScheduleView.getVehicleID());
            reportScheduleView.setVehicleName(vehicle.getFullName());
        }

        if (reportSchedule.getGroupID() != null)
        {
            Group group = groupDAO.findByID(reportScheduleView.getGroupID());
            if (group != null)
            {
                reportScheduleView.setGroupName(group.getName());
            }
        }

        if (reportSchedule.getStartDate() != null && reportSchedule.getOccurrence().equals(Occurrence.MONTHLY))
        {
            Calendar calendar = Calendar.getInstance(getUtcTimeZone());
            calendar.setTime(reportSchedule.getStartDate());

            reportScheduleView.setDayOfMonth(calendar.get(Calendar.DATE));
        }

        if (reportSchedule.getDayOfWeek() == null || reportSchedule.getDayOfWeek().size() < 6)
        {
            reportSchedule.setDayOfWeek(createDayOfWeekList());
        }

        return reportScheduleView;
    }
    
    private List<Boolean> createDayOfWeekList(){
        List<Boolean> booleanList = new ArrayList<Boolean>();
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        return booleanList;
    }

    public void setReportScheduleDAO(ReportScheduleDAO reportScheduleDAO)
    {
        this.reportScheduleDAO = reportScheduleDAO;
    }

    public ReportScheduleDAO getReportScheduleDAO()
    {
        return reportScheduleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public static class ReportScheduleView extends ReportSchedule implements EditItem
    {

        private static final long serialVersionUID = 8954277815270194338L;

        @Column(updateable = false)
        private boolean selected;

        @Column(updateable = false)
        private String groupName;

        @Column(updateable = false)
        private String driverName;

        @Column(updateable = false)
        private String vehicleName;

        @Column(updateable = false)
        private ReportGroup report;

        @Column(updateable = true)
        private Integer dayOfMonth;

        @Override
        public boolean isSelected()
        {
            return selected;
        }

        @Override
        public void setSelected(boolean selected)
        {
            this.selected = selected;

        }

        public Integer getId()
        {
            return this.getReportScheduleID();
        }

        public ReportGroup getReport()
        {
            if (getReportID() != null)
            {
                report = ReportGroup.valueOf(getReportID());
            }
            return report;
        }

        public String getGroupName()
        {
            return this.groupName;
        }

        public void setGroupName(String groupName)
        {
            this.groupName = groupName;
        }

        public void setDriverName(String driverName)
        {
            this.driverName = driverName;
        }

        public String getDriverName()
        {
            return driverName;
        }

        public void setVehicleName(String vehicleName)
        {
            this.vehicleName = vehicleName;
        }

        public String getVehicleName()
        {
            return vehicleName;
        }

        public void setReport(ReportGroup report)
        {
            this.report = report;
        }

        public void setDayOfMonth(Integer dayOfMonth)
        {
            this.dayOfMonth = dayOfMonth;
        }

        public Integer getDayOfMonth()
        {
            return dayOfMonth;
        }
    }

}
