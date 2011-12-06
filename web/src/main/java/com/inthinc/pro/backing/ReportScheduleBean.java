package com.inthinc.pro.backing;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.ui.TimeFrameSelect;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportCategory;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;
import com.inthinc.pro.util.SelectItemUtil;
import com.inthinc.pro.util.TimeFrameUtil;

/**
 * 
 * @author mstrong
 * 
 */
public class ReportScheduleBean extends BaseAdminBean<ReportScheduleBean.ReportScheduleView> implements TablePrefOptions<ReportScheduleBean.ReportScheduleView>, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8422069679000248942L;
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 4 };
    private static final int[] DEFAULT_ADMIN_COLUMN_INDICES = new int[] { 5 };

    private static final String REDIRECT_REPORT_SCHEDULES = "pretty:adminReportSchedules";
    private static final String REDIRECT_REPORT_SCHEDULE = "pretty:adminReportSchedule";
    private static final String REDIRECT_EDIT_REPORT_SCHEDULE = "pretty:adminEditReportSchedule";
    private List<Driver> driverList;
    protected final static String BLANK_SELECTION = " ";
    public static final Integer ALL_DRIVERS_ID = Integer.valueOf(0);
    private TimeFrameSelect timeFrameSelect;


    /*
     * Spring managed beans
     */
    private ReportScheduleDAO reportScheduleDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private GroupDAO groupDAO;
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("occurrence");
        AVAILABLE_COLUMNS.add("lastEmail");
        AVAILABLE_COLUMNS.add("report");
        AVAILABLE_COLUMNS.add("status");
        AVAILABLE_COLUMNS.add("owner");     // only admins see this
    }
    List<User> fullUserList;
    
    
    public void initBean()
    {
        super.initBean();
        timeFrameSelect = new TimeFrameSelect(getLocale());
    }
    
    public List<User> getFullUserList() {
        if (fullUserList == null) 
                fullUserList = userDAO.getUsersInGroupHierarchy(getUser().getGroupID());
        return fullUserList;
    }

    public void setFullUserList(List<User> fullUserList) {
        this.fullUserList = fullUserList;
    }

    private static void sort(List<SelectItem> selectItemList) {
        Collections.sort(selectItemList, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
//                return MessageUtil.getMessageString(o1.toString()).toLowerCase().compareTo(MessageUtil.getMessageString(o2.toString()).toLowerCase());
                return o1.getLabel().toLowerCase().compareTo(o2.getLabel().toLowerCase());
//                return o1.getValue().toString().toLowerCase().compareTo(o2.getValue().toString().toLowerCase());
            }
        });
    }
    
    public List<SelectItemGroup> getReportGroups() {

        List<SelectItemGroup> reportGroups = new ArrayList<SelectItemGroup>();
        reportGroups.add(getBlankGroup());
        
        reportGroups.add(new SelectItemGroup("", "", false, getItemsByCategory(null)));
        for (ReportCategory cat : ReportCategory.values()) {
            SelectItem[] items = getItemsByCategory(cat);
            if (items != null && items.length > 0)
                reportGroups.add(new SelectItemGroup(cat.getLabel(), cat.getLabel(), false, items));
        }
        
        return reportGroups;
    }
    
    protected SelectItemGroup getBlankGroup(){
        SelectItem[] items = new SelectItem[1]; 
        items[0] = new SelectItem(null, "");
        return new SelectItemGroup("","",false,items);      
    }

    /**
     * Returns all the report types pertaining to a given Report Category. 
     * @param category Category of reports
     * @return Array of report types as Faces SelectItems
     */
    private SelectItem[] getItemsByCategory(ReportCategory category) {
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (ReportGroup rt : EnumSet.allOf(ReportGroup.class)) {
            if (category == null && rt.getReportCategory() == null) {
                items.add(new SelectItem(rt.getCode(), MessageUtil.getMessageString(rt.toString())));
                continue;
            }
            if (!rt.isCategory(category)) continue;
            if (rt.getRequiresHOSAccount() && !getAccountIsHOS())
                continue;
            if (rt.getRequiresWaySmartAccount() && !getAccountIsWaysmart())
                continue;
            items.add(new SelectItem(rt.getCode(), MessageUtil.getMessageString(rt.toString())));
        }
        return items.toArray(new SelectItem[0]);
    }
    
    
    public List<SelectItem> getAllGroupUsers() {
        if (allGroupUsers == null) {
            allGroupUsers = new ArrayList<SelectItem>();
            List<User> users = getFullUserList(); // userDAO.getUsersInGroupHierarchy(getUser().getGroupID());
            if (item == null || item.getReport() == null)
                return allGroupUsers;
            if (item.getReport().getEntityType().isGroupType()) {
                List<GroupType> groupTypes = Arrays.asList(item.getReport().getGroupTypes());
                for (User user : users) {
                    if (user.getPerson() != null && isUserGroupTypeValid(groupTypes, getGroupHierarchy().getGroup(user.getGroupID()).getType())) 
                        allGroupUsers.add(new SelectItem(user.getUserID(), user.getPerson().getFirst() + ' ' + user.getPerson().getLast()));
                }
            }
            else { //if (item.getReport().getEntityType() == EntityType.ENTITY_DRIVER) {
                for (User user : users) {
                    if (user.getPerson() != null) 
                        allGroupUsers.add(new SelectItem(user.getUserID(), user.getPerson().getFirst() + ' ' + user.getPerson().getLast()));
                }
            }
            MiscUtil.sortSelectItems(allGroupUsers);
            
        }
        return allGroupUsers;
    }
    
    private boolean isUserGroupTypeValid(List<GroupType> groupTypes, GroupType userGroupType)
    {
        if (groupTypes.contains(userGroupType))
            return true;
        
        for (GroupType reportGroupType : groupTypes) {
            if (reportGroupType.getCode() > userGroupType.getCode())
                return true;
        }
        
        return false;
    }

    public EntityType getSelectedEntityType() {
        EntityType entityType = null;
        if (getItem() != null && this.getItem().getReportID() != null) {
            entityType = ReportGroup.valueOf(this.getItem().getReportID()).getEntityType();
        }
        return entityType;
    }

    public List<SelectItem> getDurations() {
        return SelectItemUtil.toList(Duration.class, true);
    }
    
    public String getReportTimeFrameStr() {
    	if (item != null && item.getReportTimeFrame() != null) {
    		return TimeFrameUtil.getTimeFrameStr(item.getReportTimeFrame(), getLocale());
    	}
    	
    	return "";
    }

    public List<SelectItem> getOccurrences() {
        return SelectItemUtil.toList(Occurrence.class, true, Occurrence.DAILY_CUSTOM);
    }

    public List<SelectItem> getStatuses() {
        return SelectItemUtil.toList(Status.class, false, Status.DELETED);
    }

    public TimeFrameSelect getTimeFrameSelect() {
        return timeFrameSelect;
    }

    public void setTimeFrameSelect(TimeFrameSelect timeFrameSelect) {
        this.timeFrameSelect = timeFrameSelect;
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
    
    public List<Driver> getDriverList() {
        if (driverList == null) {
            Integer ownerID = item == null || item.getUserID() == null ? getUserID() : item.getUserID();
            User owner = null;
            if (!ownerID.equals(getUserID()))
                owner = userDAO.findByID(ownerID);
            else owner = getUser();
            driverList = driverDAO.getAllDrivers(owner.getGroupID());
        }
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }


    
    public void ownerChangedAction() {
        driverList = null;
    }
    
    public void reportGroupChangeAction() {
        allGroupUsers = null;
        getItem().setGroupIDList(null);
        getItem().setGroupID(null);
        getItem().setDriverID(null);
        getItem().setVehicleID(null);
        getItem().setIftaOnly(false);
        getItem().setGroupName(null);
        getItem().setDriverName(null);
        getItem().setVehicleName(null);
        getItem().setListDisplay(null);
        getAllGroupUsers();

    }
    
    
    public Map<String, Integer> getGroups() {
        
    	Integer ownerID = item == null || item.getUserID() == null ? getUserID() : item.getUserID();
    	User owner = null;
    	if (!ownerID.equals(getUserID()))
    		owner = userDAO.findByID(ownerID);
    	else owner = getUser();

        final TreeMap<String, Integer> groups = new TreeMap<String, Integer>();
        List<GroupType> acceptableGroupTypes = Arrays.asList(getItem().getReport().getGroupTypes());
	    for (final Group group : getGroupHierarchy().getSubGroupList(owner.getGroupID())) {
            if (acceptableGroupTypes.contains(group.getType())) {
	    		String fullName = getGroupHierarchy().getFullGroupName(group.getGroupID());
		    	if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
		    			fullName = fullName.substring(0, fullName.length() - GroupHierarchy.GROUP_SEPERATOR.length());
		    	}
		    	groups.put(fullName, group.getGroupID());
            }
	    }
	    return groups;

    
    }


    @SuppressWarnings("unchecked")
    public Map<String, Integer> getVehicles() {
        // TODO needs implementation
        return Collections.EMPTY_MAP;
    }

    @Override
    protected ReportScheduleView createAddItem() {
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
        if (getUser() != null && getUser().getPerson() != null && getUser().getPerson().getPriEmail() != null) {
            reportSchedule.getEmailTo().add(getUser().getPerson().getPriEmail());
        }
        return createReportScheduleView(reportSchedule);
    }

    @Override
    protected void doDelete(List<ReportScheduleView> deleteItems) {
        for (final ReportScheduleView reportSchedule : deleteItems) {
            reportScheduleDAO.deleteByID(reportSchedule.getReportScheduleID());
            // add a message
            final String summary = MessageUtil.formatMessageString("reportSchedule_deleted", reportSchedule.getName());
            addInfoMessage(summary);
        }
    }

    @Override
    public String save() {
        if (isBatchEdit()) {
            final boolean occurrence = Boolean.TRUE.equals(getUpdateField().get("occurrence"));
            if (item.getOccurrence() != null && item.getOccurrence().equals(Occurrence.WEEKLY)) {
                getUpdateField().put("dayOfWeek", occurrence);
            }
            else if (item.getOccurrence() != null && item.getOccurrence().equals(Occurrence.MONTHLY)) {
                getUpdateField().put("dayOfMonth", occurrence);
            }
        }
        else {
            for (SelectItem selectItem : getAllGroupUsers()) {
                if (selectItem.getValue().equals(item.getUserID())) {
                    item.setFullName(selectItem.getLabel());
                }
            }
        }
        return super.save();
    }

    @Override
    protected void doSave(List<ReportScheduleView> saveItems, boolean create) {
        for (final ReportScheduleView reportSchedule : saveItems) {
            if (reportSchedule.getOccurrence().equals(Occurrence.DAILY) || reportSchedule.getOccurrence().equals(Occurrence.WEEKLY)) {
//                Calendar now = Calendar.getInstance();
                DateTime now = new DateTime(DateTimeZone.forID(getPerson().getTimeZone().getID()));
                reportSchedule.setStartDate(now.toDate());
            }
            else if (reportSchedule.getOccurrence().equals(Occurrence.MONTHLY)) {
                logger.debug("Day of Month: " + reportSchedule.getDayOfMonth());
//                Calendar now = Calendar.getInstance(getUtcTimeZone());
                DateTimeZone userTimeZone = DateTimeZone.forID(getPerson().getTimeZone().getID());
                MutableDateTime dayOfMonth = new MutableDateTime(userTimeZone);

                if (reportSchedule.getDayOfMonth() != null) {
                    dayOfMonth.setDayOfMonth(reportSchedule.getDayOfMonth());
                }
                reportSchedule.setStartDate(new DateMidnight(dayOfMonth.getMillis(),userTimeZone).toDate());
            }

            // Get rid of all end dates
            reportSchedule.setEndDate(null);
            reportSchedule.setLastDate(new Date(0l));
            if (create) {
                reportSchedule.setReportScheduleID(reportScheduleDAO.create(getAccountID(), reportSchedule));
            }
            else {
                reportScheduleDAO.update(reportSchedule);
            }
            if (reportSchedule.getDriverID() != null) {
                Driver driver = driverDAO.findByID(reportSchedule.getDriverID());
                reportSchedule.setDriverName(driver.getPerson().getFullName());
            }
            if (reportSchedule.getVehicleID() != null) {
                Vehicle vehicle = vehicleDAO.findByID(reportSchedule.getVehicleID());
                reportSchedule.setVehicleName(vehicle.getFullName());
            }
            if (reportSchedule.getGroupID() != null) {
                Group group = groupDAO.findByID(reportSchedule.getGroupID());
                reportSchedule.setGroupName(group.getName());
            }
            reportSchedule.setListDisplay(getListDisplay(reportSchedule));
            
            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "reportSchedule_added" : "reportSchedule_updated", reportSchedule.getName());
            addInfoMessage(summary);
        }
    }
    
    private String[] getListDisplay(ReportScheduleView reportSchedule) {
            if (reportSchedule.getReport() == null || reportSchedule.getReport().getEntityType() == null)
                return null;

            
            StringBuffer buffer = new StringBuffer();
            if (reportSchedule.getReport().getEntityType() == EntityType.ENTITY_INDIVIDUAL_DRIVER && 
                reportSchedule.getDriverIDList() != null && 
                !reportSchedule.getDriverIDList().isEmpty()) {
                List<Driver> driverList = getDriverList();
                for (Integer driverID : reportSchedule.getDriverIDList()) {
                    for (Driver driver : driverList) {
                        if (driver.getDriverID().equals(driverID)) {
                            if (buffer.length() > 0)
                                buffer.append("~");
                            buffer.append(driver.getPerson().getFullName());
                            break;
                        }
                    }
                }
            }
            else if (reportSchedule.getGroupIDList() != null && !reportSchedule.getGroupIDList().isEmpty()) {
                for (Integer grpID : reportSchedule.getGroupIDList()) {
                    Group group = this.getGroupHierarchy().getGroup(grpID);
                    if (group != null) {
                        if (buffer.length() > 0)
                            buffer.append("~");
                        buffer.append(group.getName());
                    }
                }
            }
            else return null;
            
            return buffer.toString().split("~");
            
    }
    


    @Override
    public ReportScheduleView getItem() {
        ReportScheduleView reportScheduleView = super.getItem();
        if ((reportScheduleView.getDayOfWeek() == null) || (reportScheduleView.getDayOfWeek().size() != 7)) {
            final ArrayList<Boolean> dayOfWeek = new ArrayList<Boolean>();
            for (int i = 0; i < 7; i++)
                dayOfWeek.add(false);
            reportScheduleView.setDayOfWeek(dayOfWeek);
        }
        return reportScheduleView;
    }

    @Override
    protected Boolean authorizeAccess(ReportScheduleView item) {
        Integer acctID = item.getAccountID();
        if (getGroupHierarchy().getTopGroup().getAccountID().equals(acctID) && item.getId() != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public String batchEdit() {
        String resultValue = super.batchEdit();
        // If occurrences are different then null out dayOfWekk and dayOfMonth
        if (item != null && item.getOccurrence() == null) {
            item.setDayOfMonth(1);
            item.setDayOfWeek(createDayOfWeekList());
        }
        else {
            // Set all the boolean values of the day of week list to false if the two lists don't match
            for (ReportScheduleView t : getSelectedItems())
                BeanUtil.compareAndInitBoolList(getItem().getDayOfWeek(), t.getDayOfWeek());
        }
        return resultValue;
    }

    @Override
    protected boolean validateSaveItem(ReportScheduleView reportScheduleView) {
        boolean valid = true;
        if (reportScheduleView.getOccurrence() != null && reportScheduleView.getOccurrence().equals(Occurrence.WEEKLY)) {
            if (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("occurrence"))) {
                boolean dayPicked = false;
                for (boolean day : reportScheduleView.getDayOfWeek()) {
                    if (day) {
                        dayPicked = true;
                        break;
                    }
                }
                if (!dayPicked) {
                    final String summary = MessageUtil.formatMessageString("editReportSchedule_noDays");
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                    getFacesContext().addMessage("edit_form:editReportSchedule-day0", message);
                    valid = false;
                }
            }
        }
        if (reportScheduleView.getOccurrence() == null && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("occurrence")))) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
            getFacesContext().addMessage("edit_form:editReportSchedule-occurrence", message);
        }
        if (reportScheduleView.getOccurrence() != null && reportScheduleView.getOccurrence().equals(Occurrence.MONTHLY) && reportScheduleView.getDayOfMonth() == null
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("dayOfMonth")))) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
            getFacesContext().addMessage("edit_form:editReportSchedule-dayOfMonth", message);
        }
        return valid;
    }

    @Override
    public String fieldValue(ReportScheduleView item, String column) {
        // Need to make sure we return the date string just as it appears in the UI table
        String returnString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy",LocaleBean.getCurrentLocale());
        sdf.setTimeZone(getUtcTimeZone());
        
        if (        "name".equals(column)) {
            returnString = item.getName();
            
        } else if ( "startDate".equals(column)) {
            returnString = item.getStartDate() != null ? sdf.format(item.getStartDate()) : "";
            
        } else if ( "endDate".equals(column)) {
            returnString = item.getEndDate() != null ? sdf.format(item.getEndDate()) : "";
            
        } else if ( "occurrence".equals(column)) {
            returnString = item.getOccurrence() != null ? item.getOccurrence().toString() : "";
            
        } else if ( "lastEmail".equals(column)) {
            returnString = item.getLastDate() != null ? sdf.format(item.getLastDate()) : "";
            
        } else if ( "report".equals(column)) {
            returnString = item.getReport() != null ? MessageUtil.getMessageString(item.getReport().toString(), getLocale()) : "";
            
        } else if ( "status".equals(column)) {
            returnString = item.getStatus() != null ? item.getStatus().toString() : "";
            
        } else if ( "owner".equals(column)) {
            returnString = item.getFullName();
        }
        logger.debug("column: " + column);
        logger.debug("returnValue: " + returnString);
        return returnString;
    }

    public TimeZone getUtcTimeZone() {
        return TimeZone.getTimeZone("UTC");
    }
    public TimeZone getUserTimeZone() {
        return getPerson().getTimeZone();
    }

    @Override
    public List<String> getAvailableColumns() {
        if (!getProUser().isAdmin()) {
            return AVAILABLE_COLUMNS.subList(0, 5);
        }
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns() {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        if (getProUser().isAdmin()) {
            for (int i : DEFAULT_ADMIN_COLUMN_INDICES)
                columns.put(availableColumns.get(i), true);
        }
        return columns;
    }

    @Override
    protected String getDisplayRedirect() {
        return REDIRECT_REPORT_SCHEDULE;
    }

    @Override
    protected String getEditRedirect() {
        return REDIRECT_EDIT_REPORT_SCHEDULE;
    }

    @Override
    protected String getFinishedRedirect() {
        return REDIRECT_REPORT_SCHEDULES;
    }

    @Override
    protected List<ReportScheduleView> loadItems() {
        List<ReportScheduleView> returnList = new ArrayList<ReportScheduleView>();
        List<ReportSchedule> reportScheduleList = null;
        if (getProUser().isAdmin()) 
        	reportScheduleList = reportScheduleDAO.getReportSchedulesByUserIDDeep(getProUser().getUser().getUserID());
        else reportScheduleList = reportScheduleDAO.getReportSchedulesByUserID(getProUser().getUser().getUserID());
        for (ReportSchedule reportSchedule : reportScheduleList) {
            returnList.add(createReportScheduleView(reportSchedule));
        }
        return returnList;
    }

    @Override
    protected ReportScheduleView revertItem(ReportScheduleView editItem) {
        return createReportScheduleView(reportScheduleDAO.findByID(editItem.getReportScheduleID()));
    }

    @Override
    public String getColumnLabelPrefix() {
        return "reportSchedulesHeader_";
    }

    @Override
    public TableType getTableType() {
        return TableType.REPORT_SCHEDULES;
    }

    private ReportScheduleView createReportScheduleView(ReportSchedule reportSchedule) {
        final ReportScheduleView reportScheduleView = new ReportScheduleView();
        BeanUtils.copyProperties(reportSchedule, reportScheduleView);
        reportScheduleView.setSelected(false);
        
        if (reportSchedule.getDriverID() != null) {
            Driver driver = driverDAO.findByID(reportScheduleView.getDriverID());
            reportScheduleView.setDriverName(driver.getPerson().getFullName());
        }
        if (reportSchedule.getVehicleID() != null) {
            Vehicle vehicle = vehicleDAO.findByID(reportScheduleView.getVehicleID());
            reportScheduleView.setVehicleName(vehicle.getFullName());
        }
        if (reportSchedule.getGroupID() != null) {
            Group group = groupDAO.findByID(reportScheduleView.getGroupID());
            if (group != null) {
                reportScheduleView.setGroupName(group.getName());
            }
        }
        reportScheduleView.setListDisplay(getListDisplay(reportScheduleView));
        if (reportSchedule.getStartDate() != null && reportSchedule.getOccurrence().equals(Occurrence.MONTHLY)) {
            Calendar calendar = Calendar.getInstance(getUtcTimeZone());
            calendar.setTime(reportSchedule.getStartDate());
            reportScheduleView.setDayOfMonth(calendar.get(Calendar.DATE));
        }
        if (reportSchedule.getDayOfWeek() == null || reportSchedule.getDayOfWeek().size() < 6) {
            reportSchedule.setDayOfWeek(createDayOfWeekList());
        }
        return reportScheduleView;
    }

    private List<Boolean> createDayOfWeekList() {
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

    public Boolean getShowDriverInput() {
        if (getItem() == null || getItem().getReport() == null)
            return false;
        
        EntityType entityType = getItem().getReport().getEntityType();
        if (entityType == EntityType.ENTITY_DRIVER)
            return true;
        if (entityType == EntityType.ENTITY_GROUP_OR_DRIVER || entityType == EntityType.ENTITY_GROUP_LIST_OR_DRIVER) {
            ReportParamType paramType = getItem().getParamType();
            if (paramType == null || paramType == ReportParamType.NONE || paramType == ReportParamType.DRIVER)
                return true;
        }
        return false;
    }
    
    public Boolean getShowGroupListInput() {
        if (getItem() == null || getItem().getReport() == null)
            return false;
        EntityType entityType = getItem().getReport().getEntityType();
        if (entityType == EntityType.ENTITY_GROUP_LIST || entityType == EntityType.ENTITY_GROUP_LIST_AND_IFTA)
            return true;
        if (entityType == EntityType.ENTITY_GROUP_LIST_OR_DRIVER) {
            ReportParamType paramType = getItem().getParamType();
            if (paramType != null && paramType == ReportParamType.GROUPS)
                return true;
        }
        return false;
    }
    public Boolean getShowGroupInput() {
        if (getItem() == null || getItem().getReport() == null)
            return false;
        EntityType entityType = getItem().getReport().getEntityType();
        if (entityType == EntityType.ENTITY_GROUP || entityType == EntityType.ENTITY_INDIVIDUAL_DRIVER)
            return true;
        if (entityType == EntityType.ENTITY_GROUP_OR_DRIVER) {
            ReportParamType paramType = getItem().getParamType();
            if (paramType != null && paramType == ReportParamType.GROUPS)
                return true;
        }
        return false;
    }

    
    public Map<String, Integer> getTeamDrivers() {
        final TreeMap<String, Integer> teamDrivers = new TreeMap<String, Integer>();
        teamDrivers.put(MessageUtil.getMessageString("reportSchedule_allDrivers", getLocale()), ALL_DRIVERS_ID);

        Integer teamID =  item == null || item.getGroupID() == null ? null : item.getGroupID();
        if (teamID == null)
            return teamDrivers;
        
        // all eligible drivers for report owner
        List<Driver> driverList = this.getDriverList();

        for (Driver driver : driverList) {
            if (driver.getGroupID().equals(teamID)) {
                teamDrivers.put(driver.getPerson().getFullName(), driver.getDriverID());
            }
        }
        return teamDrivers;

    
    }

    
    public void setReportScheduleDAO(ReportScheduleDAO reportScheduleDAO) {
        this.reportScheduleDAO = reportScheduleDAO;
    }

    public ReportScheduleDAO getReportScheduleDAO() {
        return reportScheduleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public List<SelectItem> getReportParamTypes() {
        return SelectItemUtil.toList(ReportParamType.class, false, ReportParamType.NONE);
    }

    
    public static class ReportScheduleView extends ReportSchedule implements EditItem {
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
        private List<String> groupIDSelectList;
        private List<String> driverIDSelectList;
        @Column(updateable = false)
        private String[] listDisplay;
        
        public String[] getListDisplay() {
            return listDisplay;
        }
        public void setListDisplay(String[] listDisplay) {
            this.listDisplay = listDisplay;
        }
        @Override
        public Date getLastDate() {
            if (super.getLastDate() != null && super.getLastDate().getTime() == 0l)
                return null;
            
            return super.getLastDate();
        }
        @Override
        public void setReportID(Integer reportID) {
            super.setReportID(reportID);
        }

        public List<String> getGroupIDSelectList() {
            if (getGroupIDList() != null) {
                groupIDSelectList = new ArrayList<String>();
                for (Integer id : getGroupIDList()) {
                    groupIDSelectList.add(id.toString());
                }
            }
            else {
                groupIDSelectList = null;
            }
            
            return groupIDSelectList;
        }

        public void setGroupIDSelectList(List<String> groupIDSelectList) {
            if (groupIDSelectList == null) {
                setGroupIDList(null);
                return;
            }
            
            List<Integer> idList = new ArrayList<Integer>();
            for (String groupIDStr : groupIDSelectList) {
                try {
                    idList.add(Integer.valueOf(groupIDStr));
                }
                catch (NumberFormatException ex) {
                    logger.error(ex);
                    
                }
            }
            setGroupIDList(idList);
        }
        
        public void setDriverIDSelectList(List<String> driverIDSelectList) {
            if (driverIDSelectList == null) {
                setDriverIDList(null);
                return;
            }
            
            List<Integer> idList = new ArrayList<Integer>();
            for (String driverIDStr : driverIDSelectList) {
                try {
                    Integer id = Integer.valueOf(driverIDStr);
                    if (!id.equals(ALL_DRIVERS_ID))
                        idList.add(id);
                }
                catch (NumberFormatException ex) {
                    logger.error(ex);
                    
                }
            }
            setDriverIDList(idList);
        }
        
        public List<String> getDriverIDSelectList() {
            if (getDriverIDList() != null) {
                driverIDSelectList = new ArrayList<String>();
                for (Integer id : getDriverIDList()) {
                    driverIDSelectList.add(id.toString());
                }
            }
            else {
                driverIDSelectList = null;
            }
            
            return driverIDSelectList;
        }

        
        @Override
        public boolean isSelected() {
            return selected;
        }

        @Override
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public Integer getId() {
            return this.getReportScheduleID();
        }

        public ReportGroup getReport() {
            if (getReportID() != null) {
                report = ReportGroup.valueOf(getReportID());
            }
            return report;
        }

        public String getGroupName() {
            return this.groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setVehicleName(String vehicleName) {
            this.vehicleName = vehicleName;
        }

        public String getVehicleName() {
            return vehicleName;
        }
        

        public void setReport(ReportGroup report) {
            this.report = report;
        }

        public void setDayOfMonth(Integer dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
        }

        public Integer getDayOfMonth() {
            return dayOfMonth;
        }

    }
}
