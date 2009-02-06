package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.util.MessageUtil;

/**
 * 
 * @author mstrong
 *
 */
public class ReportScheduleBean extends BaseAdminBean<ReportScheduleBean.ReportScheduleView> implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8422069679000248942L;
    
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[]        DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2 };
    private static final Map<String, Integer> REPORT_GROUPS;
    private static final Map<String, Duration> DURATIONS;
    
    private static final String REDIRECT_REPORT_SCHEDULES = "go_adminReportSchedules";
    private static final String REDIRECT_REPORT_SCHEDULE = "go_adminReportSchedule";
    private static final String REDIRECT_EDIT_REPORT_SCHEDULE = "go_adminEditReportSchedule";
    
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
        AVAILABLE_COLUMNS.add("startDate");
        AVAILABLE_COLUMNS.add("endDate");
        AVAILABLE_COLUMNS.add("timeOfDay");
        AVAILABLE_COLUMNS.add("lastEmail");
        AVAILABLE_COLUMNS.add("report");
       
        REPORT_GROUPS = new HashedMap();
        REPORT_GROUPS.put("   ", null);
        for(ReportGroup rt:EnumSet.allOf(ReportGroup.class))
        {
            REPORT_GROUPS.put(rt.getLabel(), rt.getCode());
        }
            
        DURATIONS = new HashedMap();
        for(Duration d : EnumSet.allOf(Duration.class))
        {
            DURATIONS.put(d.toString(), d);
        }
    }
    
    
    public Map<String, Integer> getReportGroups()
    { 
        return REPORT_GROUPS;
    }
    
    public EntityType getSelectedEntityType()
    {
        EntityType entityType = null;
        if(getItem() != null && this.getItem().getReportID() != null)
        {
            entityType = ReportGroup.valueOf(this.getItem().getReportID()).getEntityType();
        }
        return entityType;
    }
    
    public Map<String,Duration> getDurations()
    {
        return DURATIONS;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String,Integer> getDrivers()
    {
        Map<String,Integer> driverMap = new HashedMap();
        driverMap.put("", null);
        List<Driver> driverList = driverDAO.getAllDrivers(getGroupHierarchy().getTopGroup().getGroupID());
        for(Driver driver:driverList)
        {
            driverMap.put(driver.getPerson().getFullName(), driver.getDriverID());
        }
        
        return driverMap;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String,Integer> getGroups()
    {
        Map<String,Integer> groupMap = new HashedMap();
        List<Group> groupList = getGroupHierarchy().getGroupList();
        groupMap.put("", null);
        
        for(Group group:groupList)
        {
            switch(group.getType()){
            case FLEET:
            case DIVISION: groupMap.put(group.getName(), group.getGroupID());break;
            case TEAM:
            }
        }
        return groupMap;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String,Integer> getVehicles()
    {
        //TODO needs implementation
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
    protected void doSave(List<ReportScheduleView> saveItems, boolean create)
    {
        for (final ReportScheduleView reportSchedule : saveItems)
        {
            if (create)
            {
                reportSchedule.setReportScheduleID(reportScheduleDAO.create(getAccountID(), reportSchedule));
            }
            else
            {
                reportScheduleDAO.update(reportSchedule);
            }
                

            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "reportSchedule_added" : "reportSchedule_updated", reportSchedule.getName());
            addInfoMessage(summary);
        }
        
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
        for(ReportSchedule reportSchedule : reportScheduleList)
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
        BeanUtils.copyProperties(reportSchedule,reportScheduleView);
        reportScheduleView.setSelected(false);
        
        if(reportSchedule.getDriverID() != null)
        {
            Driver driver = driverDAO.findByID(reportScheduleView.getDriverID());
            reportScheduleView.setDriverName(driver.getPerson().getFullName());
        }
        
        if(reportSchedule.getVehicleID() != null)
        {
            Vehicle vehicle = vehicleDAO.findByID(reportScheduleView.getVehicleID());
            reportScheduleView.setVehicleName(vehicle.getFullName());
        }
        
        if(reportSchedule.getGroupID() != null)
        {
            Group group = groupDAO.findByID(reportScheduleView.getGroupID());
            reportScheduleView.setGroupName(group.getName());
        }
        
        return reportScheduleView;
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
        
        public String getEmailToString(){
            String emailString = "";
            if(getEmailTo() != null)
            {
                for(String email:this.getEmailTo())
                {
                    emailString += email + ",";
                }
            }
            return emailString; 
        }
        
        public void setEmailToString(String email)
        {
            if(email != null)
            {
                String[] emailArray = email.split(",");
                this.setEmailTo(Arrays.asList(emailArray));
            }
        }
        
        public String getReportName(){
            return ReportGroup.valueOf(getReportID()).getLabel();
        }
        
        public String getGroupName(){
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
    }

}
