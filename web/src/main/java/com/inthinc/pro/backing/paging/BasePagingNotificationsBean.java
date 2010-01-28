package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;

public abstract class BasePagingNotificationsBean<T> extends BaseBean 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -628902123783350377L;

    protected final static Integer DAYS_BACK = 1;
    protected final static Integer MAX_DAYS_BACK = 7;
    
    
//    private Map<EventType, String>   driverActionMap;
//    private Map<EventType, String>   vehicleActionMap;

    private ReportRenderer           reportRenderer;
    private ReportCriteriaService    reportCriteriaService;

    protected T clearItem;
 
 
    public Map<String, Integer> getTeams() {
    	final TreeMap<String, Integer> teams = new TreeMap<String, Integer>();
	    for (final Group group : getGroupHierarchy().getGroupList())
//	    	if (group.getType() == GroupType.TEAM) {
	    {
	    		String fullName = getGroupHierarchy().getFullGroupName(group.getGroupID());
	    		if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
	    			fullName = fullName.substring(0, fullName.length() - GroupHierarchy.GROUP_SEPERATOR.length());
	    		}
	    			
	    		teams.put(fullName, group.getGroupID());
	    	}
	    
	    return teams;
    }

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
	
	public T getClearItem() {
		return clearItem;
	}
	public void setClearItem(T clearItem) {
		this.clearItem = clearItem;
	}
	public void init()
    {
//        driverActionMap = new HashMap<EventType, String>();
//        driverActionMap.put(EventType.HARD_ACCEL, "go_reportDriverStyle");
//        driverActionMap.put(EventType.HARD_BRAKE, "go_reportDriverStyle");
//        driverActionMap.put(EventType.HARD_TURN, "go_reportDriverStyle");
//        driverActionMap.put(EventType.HARD_VERT, "go_reportDriverStyle");
//        driverActionMap.put(EventType.SPEEDING, "go_reportDriverSpeed");
//        driverActionMap.put(EventType.SEATBELT, "go_reportDriverSeatBelt");
//        driverActionMap.put(EventType.IDLING, "go_driverTrips");
//        vehicleActionMap = new HashMap<EventType, String>();
//        vehicleActionMap.put(EventType.HARD_ACCEL, "go_reportVehicleStyle");
//        vehicleActionMap.put(EventType.HARD_BRAKE, "go_reportVehicleStyle");
//        vehicleActionMap.put(EventType.HARD_TURN, "go_reportVehicleStyle");
//        vehicleActionMap.put(EventType.HARD_VERT, "go_reportVehicleStyle");
//        vehicleActionMap.put(EventType.SPEEDING, "go_reportVehicleSpeed");
//        vehicleActionMap.put(EventType.SEATBELT, "go_reportVehicleSeatBelt");
//        vehicleActionMap.put(EventType.IDLING, "go_vehicleTrips");

    }

/*	
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
*/
/*
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
*/
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

	public void exportReportToPdf() {
	    getReportRenderer().exportSingleReportToPDF(initReportCriteria(), getFacesContext());
	}

	public void emailReport() {
	    getReportRenderer().exportReportToEmail(initReportCriteria(), getEmailAddress());
	}

	public void exportReportToExcel() {
	    getReportRenderer().exportReportToExcel(initReportCriteria(), getFacesContext());
	}

    private ReportCriteria initReportCriteria()
    {
        ReportCriteria reportCriteria = getReportCriteria();
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(getReportTableData());
        return reportCriteria;
    }
    
    protected abstract List<EventReportItem> getReportTableData();

    protected abstract ReportCriteria getReportCriteria();
    
}
