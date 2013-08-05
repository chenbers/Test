package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.richfaces.component.UITabPanel;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;

@KeepAlive
public class TeamCommonBean extends BaseBean {
	
	private static Logger logger = Logger.getLogger(TeamCommonBean.class);
	
    private static final long serialVersionUID = 1L;
    private List<String> dayLabels = new ArrayList<String>();

    private Integer groupID;
    private Group group;

    private TimeFrame timeFrame = TimeFrame.ONE_DAY_AGO;
    private UITabPanel teamTabPanel;
    private String selectedTabId;
    
    private Map<String,List<DriverVehicleScoreWrapper>> cachedResults = Collections.synchronizedMap(new HashMap<String,List<DriverVehicleScoreWrapper>>());
    private Map<String,Map<Integer, List<ScoreableEntity>>> cachedTrendResults = Collections.synchronizedMap(new HashMap<String,Map<Integer, List<ScoreableEntity>>>());
    
    public void init() {
    	//This sets the selected tab id to always be teamStatistics. Breaks the backbutton and history funtionality.
        //selectedTabId = "teamStatistics";
    }

    public Integer getGroupID() {
        if (groupID == null){
            
            groupID = getUser().getGroupID();
            group = getGroupHierarchy().getGroup(groupID);
        }
        return groupID;
    }

    public void setGroupID(Integer groupID) {

        if (groupID != null) {

            group = getGroupHierarchy().getGroup(groupID);
        }
        this.groupID = groupID;

    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<String> getDayLabels() {

        if (dayLabels.isEmpty()) {
            Interval interval = new Interval(Days.SEVEN, new DateMidnight());
            for (int i = 0; i < 7; i++) {
                dayLabels.add(interval.getEnd().minusDays(i).dayOfWeek().getAsShortText(getLocale()));
            }
        }

        return dayLabels;
    }
    
    public String getMonthLabel(){
        //just need the current month, easier ways to get it. but this will do for now.
        return new DateTime().monthOfYear().getAsText(getLocale());
    }

    public DateTime getStartTime() {
        return timeFrame.getInterval().getStart();
    }
    
    public DateTime getEndTime() {
        return timeFrame.getInterval().getEnd();
    }
    
    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }

	public UITabPanel getTeamTabPanel() {
		return teamTabPanel;
	}

	public void setTeamTabPanel(UITabPanel teamTabPanel) {
		this.teamTabPanel = teamTabPanel;
		//this.teamTabPanel.setSelectedTab(selectedTabId);
	}

	public Object getSelectedTab() {
	    
        if (teamTabPanel != null) return teamTabPanel.getSelectedTab();
        return selectedTabId;
	}
	public void setSelectedTab(Object selectedTab){
	    
	    if (teamTabPanel != null) teamTabPanel.setSelectedTab(selectedTab);
	    selectedTabId = selectedTab.toString();
	}

    public Map<String, List<DriverVehicleScoreWrapper>> getCachedResults() {
        return cachedResults;
    }

    public void setCachedResults(Map<String, List<DriverVehicleScoreWrapper>> cachedResults) {
        this.cachedResults = cachedResults;
    }
    
    public Map<String, Map<Integer, List<ScoreableEntity>>> getCachedTrendResults() {
        return cachedTrendResults;
    }

    public void setCachedTrendResults(Map<String, Map<Integer, List<ScoreableEntity>>> cachedTrendResults) {
        this.cachedTrendResults = cachedTrendResults;
    }

    public void setSelectedTabId(String selectedTabId) {
    	logger.debug("Setting Selected Tab Id To: " + selectedTabId);
		this.selectedTabId = selectedTabId;
	}

	public String getSelectedTabId() {
	 	return selectedTabId;
	}
}
