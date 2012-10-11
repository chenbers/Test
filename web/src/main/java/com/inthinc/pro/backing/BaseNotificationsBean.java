package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.NotificationReportItem;
import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

public abstract class BaseNotificationsBean<T extends NotificationReportItem<T>> extends BaseBean 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -628902123783350377L;

	protected static final Integer numRowsPerPg = 25;
	
//  protected final static Integer DAYS_BACK = 7;
    protected final static Integer DAYS_BACK = 1;
    protected final static Integer MAX_DAYS_BACK = 7;
    private Integer	daysBack;
    
	private DriverDAO                driverDAO;
    private VehicleDAO               vehicleDAO;

    private ZoneDAO                  zoneDAO;
	private TablePreferenceDAO       tablePreferenceDAO;

//    private Map<Integer, Driver>     driverMap;
//    private Map<Integer, Vehicle>    vehicleMap;
    
    private Map<EventType, String>   driverActionMap;
    private Map<EventType, String>   vehicleActionMap;

    private Event                    selectedEvent;

    private ReportRenderer           reportRenderer;
    private ReportCriteriaService    reportCriteriaService;
    private RedFlagReportItem        selectedRedFlag;

    protected List<T> tableData;
    protected List<T> filteredTableData;

    protected T clearItem;    
 
	protected EventsSearchCoordinationBean searchCoordinationBean;

    private Integer start;
    private Integer end;
    private Integer maxCount;
	private Integer page;
	
	
    private CacheBean cacheBean;

    private Map<String, Integer>  teams;
    
    public TreeMap<String, Integer> getTeams(){
        return getGroupHierarchy().getTeams();
    }

//    public Map<String, Integer> getTeams() {
//    	final TreeMap<String, Integer> teams = new TreeMap<String, Integer>();
//	    for (final Group group : getGroupHierarchy().getGroupList())
////	    	if (group.getType() == GroupType.TEAM) {
//	    {
//	    		String fullName = getGroupHierarchy().getFullGroupName(group.getGroupID());
//	    		if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
//	    			fullName = fullName.substring(0, fullName.length() - GroupHierarchy.GROUP_SEPERATOR.length());
//	    		}
//	    			
//	    		teams.put(fullName, group.getGroupID());
//	    	}
//	    
//	    return teams;
//    }

    private Map<String, Integer> daysBackSel;
    public Map<String, Integer> getDaysBackSel() {
    	if (daysBackSel == null) {
	    	daysBackSel = new TreeMap<String, Integer>();
	    	
		    for (Integer i = 1; i <= MAX_DAYS_BACK; i++) {
		    	daysBackSel.put(Integer.valueOf(i).toString(), i);
		    }
    	}
	    return daysBackSel;
    }

    
    public void setTeams(Map<String, Integer>  teams) {
		this.teams = teams;
	}

	public CacheBean getCacheBean() {
		return cacheBean;
	}

	public void setCacheBean(CacheBean cacheBean) {
		this.cacheBean = cacheBean;
	}


	
	public T getClearItem() {
		return clearItem;
	}

	public void setClearItem(T clearItem) {
		this.clearItem = clearItem;
	}

    public Integer getStart() {
    	
    	if (start == null){
     		
      		fetchData();
    	}
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
    	if (end == null){
     		
      		fetchData();
    	}
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Integer getMaxCount() {
    	if (maxCount == null){
     		
      		fetchData();
    	}
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public Integer getNumRowsPerPg() {
		return numRowsPerPg;
	}




	public void initBean()
    {
//        driverMap = new HashMap<Integer, Driver>();
//        vehicleMap = new HashMap<Integer, Vehicle>();
        driverActionMap = new HashMap<EventType, String>();
        driverActionMap.put(EventType.HARD_ACCEL, "go_reportDriverStyle");
        driverActionMap.put(EventType.HARD_BRAKE, "go_reportDriverStyle");
        driverActionMap.put(EventType.HARD_TURN, "go_reportDriverStyle");
        driverActionMap.put(EventType.HARD_VERT, "go_reportDriverStyle");
        driverActionMap.put(EventType.SPEEDING, "go_reportDriverSpeed");
        driverActionMap.put(EventType.SEATBELT, "go_reportDriverSeatBelt");
        driverActionMap.put(EventType.IDLING, "go_driverTrips");
        vehicleActionMap = new HashMap<EventType, String>();
        vehicleActionMap.put(EventType.HARD_ACCEL, "go_reportVehicleStyle");
        vehicleActionMap.put(EventType.HARD_BRAKE, "go_reportVehicleStyle");
        vehicleActionMap.put(EventType.HARD_TURN, "go_reportVehicleStyle");
        vehicleActionMap.put(EventType.HARD_VERT, "go_reportVehicleStyle");
        vehicleActionMap.put(EventType.SPEEDING, "go_reportVehicleSpeed");
        vehicleActionMap.put(EventType.SEATBELT, "go_reportVehicleSeatBelt");
        vehicleActionMap.put(EventType.IDLING, "go_vehicleTrips");

    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    protected void fillInDriver(Event event)
    {
        Driver driver = event.getDriver();
        
        if (driver == null)
        {
            driver = cacheBean.getDriverMap().get(event.getDriverID());
            if (driver == null)
            {
            	driver = createUnknownDriverRecord();
                cacheBean.getDriverMap().put(driver.getDriverID(), driver);
            }
            event.setDriver(driver);
        }
    }

    private Driver createUnknownDriverRecord() {
    	
        Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());      

        Person p = new Person();
        p.setFirst(MessageUtil.getMessageString("notes_general_unknown"));
        p.setLast(MessageUtil.getMessageString("notes_general_driver"));
        
        Driver d = new Driver();
        d.setDriverID(acct.getUnkDriverID());
        d.setPerson(p);

        return d;
	}

	protected void fillInVehicle(Event event)
    {
        Vehicle vehicle = event.getVehicle();
        if (vehicle == null)
        {
            vehicle = cacheBean.getVehicleMap().get(event.getVehicleID());
            if (vehicle == null)
            {
                vehicle = vehicleDAO.findByID(event.getVehicleID());
                cacheBean.getVehicleMap().put(event.getVehicleID(), vehicle);
            }
            event.setVehicle(vehicle);
        }
    }

    public String driverAction()
    {
        String action = driverActionMap.get(selectedEvent.getEventType());
        if (action == null)
            return "go_driver";
        return action;
    }

    public String vehicleAction()
    {
        String action = vehicleActionMap.get(selectedEvent.getEventType());
        if (action == null)
            return "go_vehicle";
        return action;
    }

    public Event getSelectedEvent()
    {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent)
    {
        this.selectedEvent = selectedEvent;
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

    public EventsSearchCoordinationBean getSearchCoordinationBean()
    {
        return searchCoordinationBean;
    }

    public void setSearchCoordinationBean(EventsSearchCoordinationBean searchCoordinationBean)
    {
        this.searchCoordinationBean = searchCoordinationBean;
    }

    public void searchAction()
    {
        clearData();
        page = 1;
    }
    public void refreshAction(){
    	clearData();
    }
    protected abstract void filterTableDataWithoutSearch();

    protected abstract void filterTableData();

    protected Integer getEffectiveGroupId()
    {

        if (getSearchCoordinationBean().isGoodGroupId())
        {

            return getSearchCoordinationBean().getGroup().getGroupID();
        }
        else
        {

            return getUser().getGroupID();
        }
    }

    public void setPage(Integer page)
    {
        this.page = page;
    }

    public Integer getPage()
    {
        return page;
    }

    public RedFlagReportItem getSelectedRedFlag()
    {
        return selectedRedFlag;
    }

    public void setSelectedRedFlag(RedFlagReportItem selectedRedFlag)
    {
        this.selectedRedFlag = selectedRedFlag;
    }

    public void setZoneDAO(ZoneDAO zoneDAO)
    {
        this.zoneDAO = zoneDAO;
    }

    public ZoneDAO getZoneDAO()
    {
        return zoneDAO;
    }

	public TablePreferenceDAO getTablePreferenceDAO() {
	    return tablePreferenceDAO;
	}

	public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO) {
	    this.tablePreferenceDAO = tablePreferenceDAO;
	}

	public void fetchData() {
		
		filterTableData();
	
	}

	public List<T> getTableData() {
		
		if (tableData==null) fetchData();
		return tableData;
	}

	public void setTableData(List<T> tableData) {
		this.tableData = tableData;
	}

	public List<T> getFilteredTableData() {
		return filteredTableData;
	}

	public void setFilteredTableData(List<T> filteredTableData) {
		this.filteredTableData = filteredTableData;
	}
	public void clearData(){
		
		start = null;
		end = null;
		maxCount = null;
		tableData = null;
	    filteredTableData = null;		
		
	}

	public void scrollerListener(DataScrollerEvent event) {
	
	
	    this.start = (event.getPage() - 1) * numRowsPerPg + 1;
	    this.end = (event.getPage()) * numRowsPerPg;
	    // Partial page
	    if (this.end > getDisplaySize())
	    {
	        this.end = getDisplaySize();
	    }
	}
	public abstract int getDisplaySize();

	public void exportReportToPdf() {
	    getReportRenderer().exportSingleReportToPDF(initReportCriteria(), getFacesContext());
	}

	public void emailReport() {
	    getReportRenderer().exportReportToEmail(initReportCriteria(), getEmailAddress(), getNoReplyEmailAddress());
	}

	public void exportReportToExcel() {
	    getReportRenderer().exportReportToExcel(initReportCriteria(), getFacesContext());
	}

    private ReportCriteria initReportCriteria()
    {
        ReportCriteria reportCriteria = getReportCriteria();
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(getTableData());
        return reportCriteria;
    }

    
    public Integer getDaysBack() {
    	if (daysBack == null) {
    		daysBack = Integer.valueOf(1);
    	}
		return daysBack;
	}


	public void setDaysBack(Integer daysBack) {
		this.daysBack = daysBack;
	}

    
	protected abstract ReportCriteria getReportCriteria();
    
//    public List<Event> loadUnknownDriver(List<Event> warnings) {
//        List<Event> adjusted = new ArrayList<Event>();
//           
//        // Get the unknown driver from the account
//        Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());      
//
//        // Fix the name
//        for ( Event e: warnings ) {
//            Person p = new Person();
//            p.setFirst(MessageUtil.getMessageString("notes_general_unknown"));
//            p.setLast(MessageUtil.getMessageString("notes_general_driver"));
//            
//            if ( e.getDriver() == null ) {
//                Driver d = new Driver();
//                d.setDriverID(acct.getAcctID());
//                d.setPerson(p);
//                e.setDriver(d);
//            } else {
//                e.getDriver().setPerson(p);
//            }
//            
//            adjusted.add(e);
//        }
//        
//        return adjusted;
//    }	

}
