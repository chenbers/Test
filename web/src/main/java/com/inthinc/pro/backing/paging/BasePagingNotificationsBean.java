package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.EventDAO;
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

    private ReportRenderer           reportRenderer;
    private ReportCriteriaService    reportCriteriaService;

    protected Event clearItem;
    
    private EventDAO eventDAO;
    
    protected final static String BLANK_SELECTION = "&#160;";
	protected static void sort(List<SelectItem> selectItemList) {
	        Collections.sort(selectItemList, new Comparator<SelectItem>() {
	            @Override
				public int compare(SelectItem o1, SelectItem o2) {
					return o1.getLabel().toLowerCase().compareTo(o2.getLabel().toLowerCase());
				}
	        });
	}
			

 
	public List<SelectItem> getTeams() {
    	final List<SelectItem> teams = new ArrayList<SelectItem>();
		SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
		blankItem.setEscape(false);
		teams.add(blankItem);
	    for (final Group group : getGroupHierarchy().getGroupList()) {
	    		String fullName = getGroupHierarchy().getFullGroupName(group.getGroupID());
	    		if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
	    			fullName = fullName.substring(0, fullName.length() - GroupHierarchy.GROUP_SEPERATOR.length());
	    		}
	    			
	    		teams.add(new SelectItem(group.getGroupID(), fullName));
	    		
	    }
	    sort(teams);
	    
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
	
	public Event getClearItem() {
		return clearItem;
	}
	public void setClearItem(Event clearItem) {
		this.clearItem = clearItem;
	}
	
    public void excludeEventAction() {
    	
    	if(clearItem != null && clearItem.getForgiven().intValue()==0){
	        if (eventDAO.forgive(clearItem.getDriverID(), clearItem.getNoteID()) >= 1){
	        	refreshPage();
	        }
    	}
    }
    
    public void includeEventAction() {
    	
    	if(clearItem != null && clearItem.getForgiven().intValue()==1){
	    	if (eventDAO.unforgive(clearItem.getDriverID(), clearItem.getNoteID())>= 1){
	    		refreshPage();
	        }
    	}
    }
    

	public abstract void refreshPage();

	public void init()
    {
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
    
    protected abstract List<?> getReportTableData();

    protected abstract ReportCriteria getReportCriteria();
    
    public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

    
}
