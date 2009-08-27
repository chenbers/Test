package com.inthinc.pro.backing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.CrashHistoryReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.MessageUtil;

public abstract class BaseCrashBean 
        extends BaseRedFlagsBean 
        implements TablePrefOptions<CrashHistoryReportItem>, 
                    PersonChangeListener {

    private static final Logger                 logger = Logger.getLogger(BaseCrashBean.class);
    
    private final static String                 COLUMN_LABEL_PREFIX = "notes_crashhistory_";

    private static final Integer                numRowsPerPg = 25;
    private Integer                             start;
    private Integer                             end;
    private Integer                             maxCount;
    private List<CrashHistoryReportItem>   	    tableData;
    private List<CrashHistoryReportItem>   	    filteredTableData;
    private TablePreferenceDAO                  tablePreferenceDAO;
    private CrashReportDAO                      crashReportDAO;
    
    private CrashHistoryReportItem              clearItem;
    
    private ReportRenderer                      reportRenderer;
    private ReportCriteriaService               reportCriteriaService;
    
    private TablePref<CrashHistoryReportItem>   tablePref;    
    
    static final List<String>                   AVAILABLE_COLUMNS;
    static
    {
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
    
    private String                              userRole;
        
    private static DateFormat dateFormatter = 
        new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"));        
    
    @Override
    public void initBean()
    {
        super.initBean();
        tablePref = new TablePref<CrashHistoryReportItem>(this);
    }    
    
    /*
     *When the search button is actually clicked, we want to make sure we use what's in
     *the searchFor field
     */
    @Override
    public void searchAction()
    {
        super.searchAction();
    }

    public TablePref<CrashHistoryReportItem> getTablePref()
    {
        return tablePref;
    }

    public void setTablePref(TablePref<CrashHistoryReportItem> tablePref)
    {
        this.tablePref = tablePref;
    }

    public Map<String, TableColumn> getTableColumns()
    {
        return tablePref.getTableColumns();
    }
    public void setTableColumns(Map<String, TableColumn> tableColumns)
    {
        tablePref.setTableColumns(tableColumns);
    }

    public void scrollerListener(DataScrollerEvent event)
    {
        logger.debug("scroll event page: " + event.getPage() + " old " + event.getOldScrolVal() + " new " + event.getNewScrolVal());

        this.start = (event.getPage() - 1) * numRowsPerPg + 1;
        this.end = (event.getPage()) * numRowsPerPg;
        // Partial page
        if (this.end > getFilteredTableData().size())
        {
            this.end = getFilteredTableData().size();
        }
    }

    public Integer getNumRowsPerPg()
    {
        return numRowsPerPg;
    }

    public List<CrashHistoryReportItem> getTableData()
    {
        init();
        return getFilteredTableData();
    }

    public void refreshAction()
    {
        setTableData(null);
        
//        init();
    }
    private void init()
    {
        if (tableData == null)
        {
            initTableData();
            filterTableData();
        }
        else if (filteredTableData == null)
        {
            filterTableData();
        }

    }
    
    @Override
    protected void filterTableData()
    {
        setFilteredTableData(tableData); 

        if (searchCoordinationBean.isGoodSearch())
        {
            final ArrayList<CrashHistoryReportItem> searchTableDataResult = 
                new ArrayList<CrashHistoryReportItem>();
            searchTableDataResult.addAll(filteredTableData);
            tablePref.filter(searchTableDataResult, searchCoordinationBean.getSearchFor(), true);
            setFilteredTableData(searchTableDataResult);
        }

        setMaxCount(filteredTableData.size());
        setStart(filteredTableData.size() > 0 ? 1 : 0);
        setEnd(filteredTableData.size() > getNumRowsPerPg() ? getNumRowsPerPg() : filteredTableData.size());
        setPage(1);
    }

    @Override
    protected void filterTableDataWithoutSearch(){}
    
    public void initTableData()
    {
        setFilteredTableData(null);
                
        // this access sets a table level parameter to conditionally render
        //  certain columns
        User u = getProUser().getUser(); 
        setUserRole(u.getRole().getName());
        
        // the following will be how we access data when LIVE data is available
        List<CrashReport> crashList = crashReportDAO.getCrashReportsByGroupID(
            getProUser().getUser().getGroupID());
        List<CrashHistoryReportItem> histList = new ArrayList<CrashHistoryReportItem>();   
        
        for ( CrashReport cr: crashList) {
            CrashHistoryReportItem chri = new CrashHistoryReportItem();
            
            chri.setCrashReportID(cr.getCrashReportID());          
            chri.setDate(dateFormatter.format(cr.getDate()));
            chri.setTime(cr.getDate().getTime());
            Driver d = this.getDriverDAO().findByID(cr.getVehicle().getDriverID());
            chri.setDriver(d);             
            chri.setDriverName(d.getPerson().getFullName());
            chri.setGroup(getGroupHierarchy().getGroup(d.getGroupID()).getName());
            chri.setNbrOccupants(String.valueOf(cr.getOccupantCount().intValue()));
            chri.setStatus(cr.getCrashReportStatus().name());                     
            chri.setVehicle(cr.getVehicle());
            chri.setVehicleName(cr.getVehicle().getFullName());
            chri.setLatitude(cr.getLatLng().getLat());
            chri.setLongitude(cr.getLatLng().getLng());

            chri.setForgiven(cr.getCrashReportStatus().getCode());
            chri.setWeather(cr.getWeather());
            
            histList.add(chri);            
        }    
        
        // Sort by date of crash
        Collections.sort(histList);
        Collections.reverse(histList);        
        setTableData(histList);

        // count initialization
        setMaxCount(histList.size());
        setStart(histList.size() > 0 ? 1 : 0);
        setEnd(histList.size() > getNumRowsPerPg() ? getNumRowsPerPg() : histList.size());
        setPage(1);        
    }

    public void setTableData(List<CrashHistoryReportItem> tableData)
    {
        this.tableData = tableData;
    }

    public Integer getStart()
    {
        if (start == null)
        {
            init();
        }
        return start;
    }

    public void setStart(Integer start)
    {
        this.start = start;
    }

    public Integer getEnd()
    {
        if (end == null)
        {
            init();
        }
        return end;
    }

    public void setEnd(Integer end)
    {
        this.end = end;
    }

    public Integer getMaxCount()
    {
        if (maxCount == null) 
        {
            init();
        }
        return maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }


    public CrashHistoryReportItem getClearItem()
    {
        return clearItem;
    }

    public void setClearItem(CrashHistoryReportItem clearItem)
    {
        this.clearItem = clearItem;
    }
    
    public void newItemAction()
    {
        CrashReport cr = new CrashReport();
        cr.setCrashReportID(clearItem.getCrashReportID());
        cr.setCrashReportStatus(CrashReportStatus.NEW);
        
        // carry the date, so it's not updated in the mapper
        Date d = new Date();
        d.setTime(clearItem.getTime());
        cr.setDate(d);        
        
        if ( crashReportDAO.update(cr) >= 1 ) {
            initTableData();
        }
    }
    
    public void confirmItemAction()
    {
        CrashReport cr = new CrashReport();
        cr.setCrashReportID(clearItem.getCrashReportID());
        cr.setCrashReportStatus(CrashReportStatus.CONFIRMED);
        
        // carry the date, so it's not updated in the mapper
        Date d = new Date();
        d.setTime(clearItem.getTime());
        cr.setDate(d);
        
        if ( crashReportDAO.update(cr) >= 1 ) {
            initTableData();
        }
    }
    
    public void forgiveItemAction()
    {
        CrashReport cr = new CrashReport();
        cr.setCrashReportID(clearItem.getCrashReportID());
        cr.setCrashReportStatus(CrashReportStatus.FORGIVEN);
        
        // carry the date, so it's not updated in the mapper
        Date d = new Date();
        d.setTime(clearItem.getTime());
        cr.setDate(d);        
        
        if ( crashReportDAO.update(cr) >= 1 ) {
            initTableData();
        }
    }    
    
    public void clearItemAction()
    {
        if ( crashReportDAO.forgiveCrash(clearItem.getCrashReportID()) >= 1 ) {
            initTableData();
        }
    }
    
    public void includeEventAction(){
    	
        if ( crashReportDAO.unforgiveCrash(clearItem.getCrashReportID()) >= 1 ) {
            initTableData();
        }
    	
    }

    private void reinit()
    {
        setFilteredTableData(null);
        setStart(null);
        setEnd(null);
        setMaxCount(null);
    }

    public List<CrashHistoryReportItem> getFilteredTableData()
    {
        return filteredTableData;
    }

    public void setFilteredTableData(List<CrashHistoryReportItem> filteredTableData)
    {
        this.filteredTableData = filteredTableData;
    }
    
    @Override
    public String fieldValue(CrashHistoryReportItem item, String column)
    {
        if (        "driver".equals(column)) {
            column = "driverName";
        }
        else if (   "vehicle".equals(column)) {
            column = "vehicleName";
        }
        else if (   "clear".equals(column) ||
                    "detail".equals(column) ||
                    "edit".equals(column) ) {
            return "";
        }
        
        return TablePref.fieldValue(item, column);
    }

    // TablePrefOptions interface
    @Override
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean>  getDefaultColumns()
    {
        HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        for (String col : AVAILABLE_COLUMNS)
            columns.put(col, true);
        return columns;

    }

    @Override
    public String getColumnLabelPrefix()
    {
        return COLUMN_LABEL_PREFIX;
    }

    @Override
    public TablePreferenceDAO getTablePreferenceDAO()
    {
        return tablePreferenceDAO;
    }

    @Override
    public TableType getTableType()
    {
        return TableType.CRASH_HISTORY;
    }

    @Override
    public Integer getUserID()
    {
        return getUser().getUserID();
    }

    public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO)
    {
        this.tablePreferenceDAO = tablePreferenceDAO;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService)
    {
        this.reportCriteriaService = reportCriteriaService;
    }

    public ReportCriteriaService getReportCriteriaService()
    {
        return reportCriteriaService;
    }

    public CrashReportDAO getCrashReportDAO() {
        return crashReportDAO;
    }

    public void setCrashReportDAO(CrashReportDAO crashReportDAO) {
        this.crashReportDAO = crashReportDAO;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public void personListChanged()
    {
        refreshAction();
    }
}
