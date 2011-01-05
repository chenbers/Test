package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.backing.ui.CrashHistoryReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.util.MessageUtil;

public class CrashHistoryBean extends BaseNotificationsBean<CrashHistoryReportItem> implements TablePrefOptions<CrashHistoryReportItem> {
    /**
	 * 
	 */
    private static final long serialVersionUID = -2014398186062081841L;
    private static final Logger logger = Logger.getLogger(CrashHistoryBean.class);
    private final static String COLUMN_LABEL_PREFIX = "notes_crashhistory_";
    // private List<CrashHistoryReportItem> tableData;
    // private List<CrashHistoryReportItem> filteredTableData;
    private CrashReportDAO crashReportDAO;
    private CrashHistoryReportItem clearItem;
    private TablePref<CrashHistoryReportItem> tablePref;
    private String selectedCrash;
    static final List<String> AVAILABLE_COLUMNS;
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("date");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("vehicle");
        AVAILABLE_COLUMNS.add("nbr_occupants");
        AVAILABLE_COLUMNS.add("status");
        AVAILABLE_COLUMNS.add("weather");
        AVAILABLE_COLUMNS.add("details");
        AVAILABLE_COLUMNS.add("edit");
        AVAILABLE_COLUMNS.add("clear");
    }
//    private String userRole;
//    private static DateFormat dateFormatter ;
    private final static String UNKNOWN_DRIVER = "unknown_driver";

    @Override
    public void initBean() {
        
        super.initBean();
//        dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"), LocaleBean.getCurrentLocale());
        tablePref = new TablePref<CrashHistoryReportItem>(this);
    }

    @Override
    public int getDisplaySize() {
        // TODO Auto-generated method stub
        return filteredTableData.size();
    }

    public TablePref<CrashHistoryReportItem> getTablePref() {
        return tablePref;
    }

    public void setTablePref(TablePref<CrashHistoryReportItem> tablePref) {
        this.tablePref = tablePref;
    }

    public Map<String, TableColumn> getTableColumns() {
        return tablePref.getTableColumns();
    }

    public void setTableColumns(Map<String, TableColumn> tableColumns) {
        tablePref.setTableColumns(tableColumns);
    }

    @Override
    protected void filterTableData() {
        if (tableData == null) {
logger.info("initTableData() - start");            
            initTableData();
logger.info("initTableData() - end");
        }
        setFilteredTableData(tableData);
        if (searchCoordinationBean.isGoodSearch()) {
            final ArrayList<CrashHistoryReportItem> searchTableDataResult = new ArrayList<CrashHistoryReportItem>();
            searchTableDataResult.addAll(filteredTableData);
            tablePref.filter(searchTableDataResult, searchCoordinationBean.getSearchFor(), true);
            setFilteredTableData(searchTableDataResult);
        }
        setMaxCount(filteredTableData.size());
        setStart(filteredTableData.size() > 0 ? 1 : 0);
        setEnd(filteredTableData.size() > getNumRowsPerPg() ? getNumRowsPerPg() : filteredTableData.size());
        setPage(1);
        tableData = filteredTableData;
    }

    @Override
    protected void filterTableDataWithoutSearch() {
    }
   
    public void initTableData() {
        setFilteredTableData(null);
        // this access sets a table level parameter to conditionally render
        // certain columns
//        User user = getProUser().getUser();
//        setUserRole(user.getRolesStrings());
        // the following will be how we access data when LIVE data is available
//        List<CrashReport> crashList = crashReportDAO.findByGroupID(user.getGroupID());
        List<CrashReport> crashList = new ArrayList<CrashReport>();
        if (getSearchCoordinationBean().isGoodGroupId())
        {
        	crashList = crashReportDAO.findByGroupID(getSearchCoordinationBean().getGroup().getGroupID());
        }

        List<CrashHistoryReportItem> histList = new ArrayList<CrashHistoryReportItem>();
        for (CrashReport cr : crashList) {
            CrashHistoryReportItem reportItem = new CrashHistoryReportItem();
            reportItem.setCrashReportID(cr.getCrashReportID());
            
            // Crash will now show driver time zone info
            String dateFormatStr = MessageUtil.getMessageString("dateTimeFormat", LocaleBean.getCurrentLocale());
            DateTimeFormatter fmt = DateTimeFormat.forPattern(dateFormatStr).withLocale(LocaleBean.getCurrentLocale());
            
            DateTime dateTime = new DateTime(cr.getDate().getTime(), 
                    DateTimeZone.forTimeZone(cr.getDriver().getPerson().getTimeZone() == null ? 
                                TimeZone.getDefault() : cr.getDriver().getPerson().getTimeZone()));
            reportItem.setDate(fmt.print(dateTime));
          
            reportItem.setTime(dateTime.getMillis());
            Driver driver = getCacheBean().getDriverMap().get(cr.getDriverID());
            if (driver == null) {
                driver = getDriverDAO().findByID(cr.getDriverID());
                getCacheBean().getDriverMap().put(cr.getDriverID(), driver);
            }
            if (driver != null) {
                reportItem.setDriver(driver);
                
                // The unknown driver has a null person
                if (driver.getPerson() != null) {
                    reportItem.setDriverName(driver.getPerson().getFullName());
                    Group group = getGroupHierarchy().getGroup(driver.getGroupID());
                    if (group != null)
                    {
                    	reportItem.setGroup(group.getName());
                    	reportItem.setGroupID(driver.getGroupID());
                    }
                    else {
                    	logger.error("Group not found for driverID:" + driver.getDriverID());
                        reportItem.setDriverName(MessageUtil.getMessageString(UNKNOWN_DRIVER));
                        reportItem.setGroup("");
                        reportItem.setGroupID(null);
                    }
                    	
                } else {
                    reportItem.setDriverName(MessageUtil.getMessageString(UNKNOWN_DRIVER));
                    reportItem.setGroup("");
                    reportItem.setGroupID(null);
                }
            }
            reportItem.setNbrOccupants(String.valueOf(cr.getOccupantCount().intValue()));
            reportItem.setStatus(cr.getCrashReportStatus().name());
            reportItem.setVehicle(cr.getVehicle());
            reportItem.setVehicleName((cr.getVehicle() == null) ? "" : cr.getVehicle().getName());
            reportItem.setLatitude(cr.getLatLng().getLat());
            reportItem.setLongitude(cr.getLatLng().getLng());
            reportItem.setForgiven(cr.getCrashReportStatus().getCode());
            reportItem.setWeather(cr.getWeather());
            histList.add(reportItem);
        }
        // Sort by date of crash
        Collections.sort(histList,Collections.reverseOrder());
        setTableData(histList);
        // count initialization
        setMaxCount(histList.size());
        setStart(histList.size() > 0 ? 1 : 0);
        setEnd(histList.size() > getNumRowsPerPg() ? getNumRowsPerPg() : histList.size());
        setPage(1);
    }
    
    public CrashHistoryReportItem getClearItem() {
        return clearItem;
    }

    public void setClearItem(CrashHistoryReportItem clearItem) {
        this.clearItem = clearItem;
    }

    public void updateCrashStatus() {
        // extract the new status and the crash id to change
        StringTokenizer st = new StringTokenizer(this.selectedCrash,"_");
        String stat = st.nextToken();
        Integer crashID = new Integer(st.nextToken());
        
        // set the data for update 
        CrashReport cr = new CrashReport();
        cr.setCrashReportID(crashID);
        
        if (        stat.equalsIgnoreCase(MessageUtil.getMessageString("notes_crashhistory_new")) ) {
            cr.setCrashReportStatus(CrashReportStatus.NEW);
            
        } else if ( stat.equalsIgnoreCase(MessageUtil.getMessageString("notes_crashhistory_confirmed")) ) {            
            cr.setCrashReportStatus(CrashReportStatus.CONFIRMED);
            
        } else if ( stat.equalsIgnoreCase(MessageUtil.getMessageString("notes_crashhistory_exclude")) ) {
            cr.setCrashReportStatus(CrashReportStatus.EXCLUDE);
            
        } else {
            return;
        }
        
        for ( CrashHistoryReportItem chri: tableData ) {
            if ( chri.getCrashReportID().equals(crashID) ) {
                Date d = new Date();
                d.setTime(chri.getTime());
                cr.setDate(d);
                break;
            }
        }
        
        // do the update
        if (crashReportDAO.update(cr) >= 1) {
            initTableData();
        }
    }

    // private void reinit()
    // {
    // setTableData(null);
    // setFilteredTableData(null);
    // setStart(null);
    // setEnd(null);
    // setMaxCount(null);
    // init();
    // }
    public List<CrashHistoryReportItem> getFilteredTableData() {
        return filteredTableData;
    }

    public void setFilteredTableData(List<CrashHistoryReportItem> filteredTableData) {
        this.filteredTableData = filteredTableData;
    }

    @Override
    public String fieldValue(CrashHistoryReportItem item, String column) {
        if ("driver".equals(column)) {
            column = "driverName";
        } else if ("vehicle".equals(column)) {
            column = "vehicleName";
        } else if ("clear".equals(column) || "detail".equals(column) || "edit".equals(column)) {
            return "";
        }
        return TablePref.fieldValue(item, column);
    }

    // TablePrefOptions interface
    @Override
    public List<String> getAvailableColumns() {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns() {
        HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        for (String col : AVAILABLE_COLUMNS)
            columns.put(col, true);
        return columns;
    }

    @Override
    public String getColumnLabelPrefix() {
        return COLUMN_LABEL_PREFIX;
    }

    @Override
    public Integer getUserID() {
        return getUser().getUserID();
    }

    public CrashReportDAO getCrashReportDAO() {
        return crashReportDAO;
    }

    public void setCrashReportDAO(CrashReportDAO crashReportDAO) {
        this.crashReportDAO = crashReportDAO;
    }

//    public String getUserRole() {
//        return userRole;
//    }
//
//    public void setUserRole(String userRole) {
//        this.userRole = userRole;
//    }

    public String getSelectedCrash() {
        return selectedCrash;
    }

    public void setSelectedCrash(String selectedCrash) {
        this.selectedCrash = selectedCrash;
    }

    @Override
    public TableType getTableType() {
        return TableType.CRASH_HISTORY;
    }

    public String showAllFromRecentAction() {
        // setCategoryFilter(null);
        // setEventFilter(null);
        clearData();
        return "go_crashHistory";
    }
    @Override
    protected ReportCriteria getReportCriteria()
    {
        return getReportCriteriaService().getCrashHistoryReportCriteria(getUser().getGroupID(), getLocale());
    }

}
