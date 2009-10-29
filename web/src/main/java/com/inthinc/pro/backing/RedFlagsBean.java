package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneArrivalEvent;
import com.inthinc.pro.model.ZoneDepartureEvent;
import com.inthinc.pro.model.ZoneEvent;
import com.inthinc.pro.reports.ReportCriteria;

public class RedFlagsBean extends BaseNotificationsBean<RedFlagReportItem> implements TablePrefOptions<RedFlagReportItem>{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3166689931697428969L;
	private static final Logger logger = Logger.getLogger(RedFlagsBean.class);
    private final static String COLUMN_LABEL_PREFIX = "notes_redflags_";
    // TODO: what should this number be (settable by user?)
 //   private static final Integer RED_FLAG_COUNT = 500;
//    private List<RedFlagReportItem> tableData;
//    private List<RedFlagReportItem> filteredTableData;
    private EventDAO eventDAO;
    private RedFlagDAO redFlagDAO;
 //   private RedFlagReportItem clearItem;
    private EventCategory categoryFilter;
    private Event eventFilter;
    private TablePref<RedFlagReportItem> tablePref;
    // package level -- so unit test can get it
    static final List<String> AVAILABLE_COLUMNS;
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("level");
        AVAILABLE_COLUMNS.add("alerts");
        AVAILABLE_COLUMNS.add("date");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("vehicle");
        AVAILABLE_COLUMNS.add("category");
        AVAILABLE_COLUMNS.add("detail");
        AVAILABLE_COLUMNS.add("clear");
    }

    @Override
    public void initBean() {
        super.initBean();
        tablePref = new TablePref<RedFlagReportItem>(this);
    }


    @Override
    public void searchAction()
    {
        super.searchAction();
        setEventFilter(null);  
    }
	@Override
	public int getDisplaySize() {
		// TODO Auto-generated method stub
		return filteredTableData.size();
	}

    @Override
    protected void filterTableData() {
        if (tableData == null)
        {
            initTableData();
        }
         setFilteredTableData(tableData);
        if (getCategoryFilter() != null) {
            List<Integer> validEventTypes = EventMapper.getEventTypesInCategory(getCategoryFilter());
            if (validEventTypes != null) {
                filteredTableData = new ArrayList<RedFlagReportItem>();
                for (RedFlagReportItem item : tableData) {
                    if (validEventTypes.contains(item.getRedFlag().getEvent().getType())) {
                        filteredTableData.add(item);
                    }
                }
            }
        }
        if (getEventFilter() != null) {
            filteredTableData = new ArrayList<RedFlagReportItem>();
            for (RedFlagReportItem item : tableData) {
                if (item.getRedFlag().getEvent().getNoteID().equals(eventFilter.getNoteID())) {
                    filteredTableData.add(item);
                    break;
                }
            }
        }
        // Filter if search is based on group.
        if (!getEffectiveGroupId().equals(getUser().getGroupID())) {
            filteredTableData = new ArrayList<RedFlagReportItem>();
            for (RedFlagReportItem item : tableData) {
                if (item.getRedFlag().getEvent().getGroupID().equals(getEffectiveGroupId())) {
                    filteredTableData.add(item);
                }
            }
        }
        if (searchCoordinationBean.isGoodSearch()) {
            final ArrayList<RedFlagReportItem> searchTableData = new ArrayList<RedFlagReportItem>();
            searchTableData.addAll(filteredTableData);
            tablePref.filter(searchTableData, searchCoordinationBean.getSearchFor(), true);
            setFilteredTableData(searchTableData);
        }
        setMaxCount(filteredTableData.size());
        setStart(filteredTableData.size() > 0 ? 1 : 0);
        setEnd(filteredTableData.size() > getNumRowsPerPg() ? getNumRowsPerPg() : filteredTableData.size());
        setPage(1);
        tableData = filteredTableData;
    }

    @Override
    protected void filterTableDataWithoutSearch() {
        searchCoordinationBean.clearSearchFor();
        if (tableData == null) {
            initTableData();
        }
        setFilteredTableData(tableData);
        if (getCategoryFilter() != null) {
            List<Integer> validEventTypes = EventMapper.getEventTypesInCategory(getCategoryFilter());
            if (validEventTypes != null) {
                filteredTableData = new ArrayList<RedFlagReportItem>();
                for (RedFlagReportItem item : tableData) {
                    if (validEventTypes.contains(item.getRedFlag().getEvent().getType())) {
                        filteredTableData.add(item);
                    }
                }
            }
        }
        if (getEventFilter() != null) {
            filteredTableData = new ArrayList<RedFlagReportItem>();
            for (RedFlagReportItem item : tableData) {
                if (item.getRedFlag().getEvent().getNoteID().equals(eventFilter.getNoteID())) {
                    filteredTableData.add(item);
                    break;
                }
            }
        }
        // Filter if search is based on group.
        if (!getEffectiveGroupId().equals(getUser().getGroupID())) {
            filteredTableData = new ArrayList<RedFlagReportItem>();
            for (RedFlagReportItem item : tableData) {
                if (item.getRedFlag().getEvent().getGroupID().equals(getEffectiveGroupId())) {
                    filteredTableData.add(item);
                }
            }
        }
        setMaxCount(filteredTableData.size());
        setStart(filteredTableData.size() > 0 ? 1 : 0);
        setEnd(filteredTableData.size() > getNumRowsPerPg() ? getNumRowsPerPg() : filteredTableData.size());
        setPage(1);
        tableData = filteredTableData;
   }

    /**
     * Returns the value of the property of the given item described by the given column name. The default implementation calls TablePref.fieldValue.
     * 
     * @param item
     *            The item to get the value from.
     * @param column
     *            The name of the column to get the value of.
     * @return The value or <code>null</code> if unavailable.
     */
    public String fieldValue(RedFlagReportItem item, String column) {
        if ("driver".equals(column))
            column = "driverName";
        else if ("vehicle".equals(column))
            column = "vehicleName";
        else if ("alert".equals(column)) {
            if ((item.getRedFlag() != null) && (item.getRedFlag().getAlert() != null))
                return item.getRedFlag().getAlert() ? "yes" : "no";
            return null;
        }
        else if ("level".equals(column))
            column = "redFlag_level_description";
        else if ("alerts".equals(column))
            return "";
        else if ("clear".equals(column))
            return "";
        return TablePref.fieldValue(item, column);
    }

    protected void initTableData() {
    	
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();

        setFilteredTableData(null);
        List<RedFlag> redFlagList = redFlagDAO.getRedFlags(getEffectiveGroupId(), 7, RedFlagDAO.INCLUDE_FORGIVEN);
        List<RedFlagReportItem> redFlagReportItemList = new ArrayList<RedFlagReportItem>();
        addDrivers(redFlagList);
        addVehicles(redFlagList);
        Map<Integer, Zone> zoneMap = new HashMap<Integer, Zone>();
        for (RedFlag redFlag : redFlagList) {
            RedFlagReportItem item = new RedFlagReportItem(redFlag, getGroupHierarchy(), getMeasurementType());
            
//            if (redFlag.getEvent() instanceof ZoneDepartureEvent) {
//                zoneID = ((ZoneDepartureEvent) redFlag.getEvent()).getZoneID();
//            } else if (redFlag.getEvent() instanceof ZoneArrivalEvent) {
//                zoneID = ((ZoneArrivalEvent) redFlag.getEvent()).getZoneID();
//            }
            
            if (redFlag.getEvent() instanceof ZoneEvent) {
            	
            	Integer zoneID = null;
            	zoneID = ((ZoneEvent) redFlag.getEvent()).getZoneID();
            
	            if(zoneID != null && zoneMap.containsKey(zoneID)) {
	                item.setZone(zoneMap.get(zoneID));
	            } else if (zoneID != null){
	                Zone zone = getZoneDAO().findByID(zoneID);
	                item.setZone(zone);
	                zoneMap.put(zone.getZoneID(), zone);
	            }
            }
            redFlagReportItemList.add(item);
        }
        Collections.sort(redFlagReportItemList);
        setTableData(redFlagReportItemList);
    }

    public RedFlagDAO getRedFlagDAO() {
        return redFlagDAO;
    }

    public void setRedFlagDAO(RedFlagDAO redFlagDAO) {
        this.redFlagDAO = redFlagDAO;
    }


//    public NotificationReportItem getClearItem() {
//        return clearItem;
//    }
//
//    public void setClearItem(RedFlagReportItem clearItem) {
//        this.clearItem = clearItem;
//    }

    public void excludeEventAction() {
    	
    	if(clearItem.getRedFlag().getEvent().getForgiven().intValue()==0){
	        if (eventDAO.forgive(clearItem.getRedFlag().getEvent().getDriverID(), clearItem.getRedFlag().getEvent().getNoteID()) >= 1) {
//	            initTableData();
	        	clearData();
	        }
    	}
    }

    public void includeEventAction() {
    	
    	if(clearItem.getRedFlag().getEvent().getForgiven().intValue()==1){
    		if (eventDAO.unforgive(clearItem.getRedFlag().getEvent().getDriverID(), clearItem.getRedFlag().getEvent().getNoteID()) >= 1) {
//	            initTableData();
    			clearData();
	        }
    	}
    }

    public EventCategory getCategoryFilter() {
        return categoryFilter;
    }

//    private void reinit() {
//        setTableData(null);
//        setFilteredTableData(null);
//        setStart(null);
//        setEnd(null);
//        setMaxCount(null);
//        init();
//    }

    public void setCategoryFilter(EventCategory categoryFilter) {
//        reinit();
        this.eventFilter = null;
        this.categoryFilter = categoryFilter;
    }

    public List<RedFlagReportItem> getTableData() {
    	if(tableData==null) fetchData();
		return tableData;
	}

	public void setTableData(List<RedFlagReportItem> tableData) {
		this.tableData = tableData;
	}

	public List<RedFlagReportItem> getFilteredTableData() {
        return filteredTableData;
    }

    public void setFilteredTableData(List<RedFlagReportItem> filteredTableData) {
        this.filteredTableData = filteredTableData;
    }

    public Event getEventFilter() {
        return eventFilter;
    }

    public void setEventFilter(Event eventFilter) {
        // force table data to reinit
//        reinit();
        this.categoryFilter = null;
        this.eventFilter = eventFilter;
    }

    public void showAllAction() {
        setCategoryFilter(null);
        setEventFilter(null);
        filterTableDataWithoutSearch();
    }

    public TablePref<RedFlagReportItem> getTablePref() {
        return tablePref;
    }

    public void setTablePref(TablePref<RedFlagReportItem> tablePref) {
        this.tablePref = tablePref;
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
    public TableType getTableType() {
        return TableType.RED_FLAG;
    }

    @Override
    public Integer getUserID() {
        return getUser().getUserID();
    }

    // wrappers for tablePref
    // public String saveColumns()
    // {
    // return tablePref.saveColumns();
    // }
    public Map<String, TableColumn> getTableColumns() {
        return tablePref.getTableColumns();
    }

    public void setTableColumns(Map<String, TableColumn> tableColumns) {
        tablePref.setTableColumns(tableColumns);
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    protected void addDrivers(List<RedFlag> redFlagList) {
        Map<Integer, Driver> driverMap = new HashMap<Integer, Driver>();
        for (RedFlag redFlag : redFlagList) {
            if(driverMap.containsKey(redFlag.getEvent().getDriverID())) {
                redFlag.getEvent().setDriver(driverMap.get(redFlag.getEvent().getDriverID()));
            } else {
                Driver driver = getDriverDAO().findByID(redFlag.getEvent().getDriverID());
                if(driver != null) {
                    redFlag.getEvent().setDriver(driver);
                    driverMap.put(driver.getDriverID(), driver);
                }
            }
        }
    }

    protected void addVehicles(List<RedFlag> redFlagList) {
        // Get the set of possible vehicles
        Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();
        for (RedFlag redFlag : redFlagList) {
            redFlag.getEvent().setVehicle(vehicleMap.get(redFlag.getEvent().getVehicleID()));
            if(vehicleMap.containsKey(redFlag.getEvent().getVehicleID())) {
                redFlag.getEvent().setVehicle(vehicleMap.get(redFlag.getEvent().getVehicleID()));
            } else {
                Vehicle vehicle = getVehicleDAO().findByID(redFlag.getEvent().getVehicleID());
                if(vehicle != null) {
                    redFlag.getEvent().setVehicle(vehicle);
                    vehicleMap.put(vehicle.getVehicleID(), vehicle);
                }
            }
        }
    }
	protected ReportCriteria getReportCriteria() {
	    ReportCriteria reportCriteria = getReportCriteriaService().getRedFlagsReportCriteria(getUser().getGroupID(), getLocale());
	    return reportCriteria;
	}

 }
