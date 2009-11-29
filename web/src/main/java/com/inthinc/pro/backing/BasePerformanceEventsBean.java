package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TrendItem;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public abstract class BasePerformanceEventsBean extends BasePerformanceBean {

	protected ScoreDAO 			scoreDAO;
	protected EventDAO 			eventDAO;
	protected EventReportItem 	clearItem;
	protected final Integer 	ROWCOUNT = 10;
	protected static final Integer NO_SCORE = -1;
	protected List<ScoreType> 	scoreTypes;
	protected int				eventType;
 	protected String 			selectedBreakdown;

	protected List<EventReportItem>              filteredEvents;
	protected List<EventReportItem>              events;
	protected Map<String, List<EventReportItem>> eventsListsMap;
  
	protected abstract void initTrends();
	protected abstract void initScores();
	protected abstract void initEvents();
	public abstract void sortEvents();
	
    public List<EventReportItem> getFilteredEvents() {
    	
        if (filteredEvents == null)
        {
            initEvents();
            tableStatsBean.reset(ROWCOUNT, getFilteredEvents().size());
        }

		return filteredEvents;
	}

	public void setFilteredEvents(List<EventReportItem> filteredEvents) {
		this.filteredEvents = filteredEvents;
	}

	public List<EventReportItem> getEvents() {
		return events;
	}

	public EventReportItem getClearItem() {
		return clearItem;
	}

	public void setClearItem(EventReportItem clearItem) {
		this.clearItem = clearItem;
	}

	public void setEvents(List<EventReportItem> events) {
		this.events = events;
	    sortEvents();
	}

	public Map<String, List<EventReportItem>> getEventsListsMap() {
	       
		if(eventsListsMap == null) {
			
			initEvents();
		}
		return eventsListsMap;
	}

	public void setEventsListsMap(Map<String, List<EventReportItem>> eventsListsMap) {
		this.eventsListsMap = eventsListsMap;
	}
	
	public ScoreDAO getScoreDAO() {
	    return scoreDAO;
	}

	public void setScoreDAO(ScoreDAO scoreDAO) {
	    this.scoreDAO = scoreDAO;
	}

	public EventDAO getEventDAO() {
	    return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
	    this.eventDAO = eventDAO;
	}
	public List<ScoreType> getScoreTypes() {
		return scoreTypes;
	}
	public void setScoreTypes(List<ScoreType> scoreTypes) {
		this.scoreTypes = scoreTypes;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public String getSelectedBreakdown() {
		return selectedBreakdown;
	}
	public void setSelectedBreakdown(String selectedBreakdown) {
		
		this.selectedBreakdown = selectedBreakdown;
	    setFilteredEvents(getEventsListsMap().get(selectedBreakdown));
	}
	public void selectBreakdownChanged() {
		
	    tableStatsBean.reset(ROWCOUNT, getEventsListsMap().get(selectedBreakdown).size());
	}
	
	public void setDuration(Duration duration) {
		
	    durationBean.setDuration(duration);
	    initScores();
	    initTrends();
	    initEvents();
	    tableStatsBean.reset(ROWCOUNT, getEventsListsMap().get(selectedBreakdown).size());
	}
	public Duration getDuration() {
		
	    return durationBean.getDuration();
	}
  

	@Override
	public Map<String, Integer> getScoreMap() {
	    if (scoreMap == null)
	        initScores();
	
	    return scoreMap;
	}

	@Override
	public Map<String, String> getStyleMap() {
	    if (styleMap == null)
	        initScores();
	
	    return styleMap;
	}

	@Override
	public Map<String, String> getTrendMap() {
	    if(trendMap == null)
	        initTrends();
	    
	    return trendMap;
	}

	public void showExcludedEventsChangeAction(){
		
		showExcludedEvents = showExcludedEvents==EventDAO.EXCLUDE_FORGIVEN?EventDAO.INCLUDE_FORGIVEN:EventDAO.EXCLUDE_FORGIVEN;
		initEvents();
        tableStatsBean.updateSize(getEventsListsMap().get(selectedBreakdown).size());
   	}
    public synchronized void excludeEventAction()
    {
        Integer result = eventDAO.forgive(getDriver().getDriverID(), clearItem.getEvent().getNoteID());
        if(result.intValue() >= 1)
            {
        		initEvents();
	            tableStatsBean.updateSize(getEventsListsMap().get(selectedBreakdown).size());
            }
    }
   
	public synchronized void includeEventAction() {
		
	    Integer result = eventDAO.unforgive(getDriver().getDriverID(), clearItem.getEvent().getNoteID());
	    if(result.intValue() >= 1)
	        {
	            initEvents();
	            tableStatsBean.updateSize(getEventsListsMap().get(selectedBreakdown).size());
	        }
	}
	public abstract ReportCriteria buildReport();
	
    public void exportReportToPdf()
    {
        getReportRenderer().exportSingleReportToPDF(buildReport(), getFacesContext());
    }

    public  void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReport(), getEmailAddress());
    }

    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(buildReport(), getFacesContext());
    }
	
}
