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
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Months;
import org.richfaces.component.UITabPanel;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Trend;

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
//    private Map<String,Map<Integer, List<ScoreableEntity>>> cachedTrendResults = Collections.synchronizedMap(new HashMap<String,Map<Integer, List<ScoreableEntity>>>());
    private Map<String,GroupTrendWrapper> cachedTrendResults = Collections.synchronizedMap(new HashMap<String,GroupTrendWrapper>());
    
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
    
    public Map<String,GroupTrendWrapper> getCachedTrendResults() {
        return cachedTrendResults;
    }

    public void setCachedTrendResults(Map<String,GroupTrendWrapper> cachedTrendResults) {
        this.cachedTrendResults = cachedTrendResults;
    }

    public void setSelectedTabId(String selectedTabId) {
    	logger.debug("Setting Selected Tab Id To: " + selectedTabId);
		this.selectedTabId = selectedTabId;
	}

	public String getSelectedTabId() {
	 	return selectedTabId;
	}
	
	public Number getOverallScoreUsingTrendScore(GroupReportDAO groupReportDAO) {
	    Number retVal = -1;
	    
	    Duration duration = Duration.DAYS;
        
        if(this.getTimeFrame().getAggregationDuration() == AggregationDuration.ONE_DAY) {
            duration = Duration.DAYS;
        } else if(this.getTimeFrame().getAggregationDuration() == AggregationDuration.ONE_MONTH) {
            duration = Duration.THREE;
        }
        
        GroupTrendWrapper groupTrendWrapper = null;
        
        //String key = teamCommonBean.getTimeFrame().name();
        if (this.getCachedTrendResults().containsKey(this.getTimeFrame().name())) {
            groupTrendWrapper = this.getCachedTrendResults().get(this.getTimeFrame().name());
        } else {
            List<GroupTrendWrapper> groupTrendWrapperList = groupReportDAO.getSubGroupsAggregateDriverTrends(this.getGroup().getParentID(), duration, getGroupHierarchy());
                            
            for(GroupTrendWrapper gtw : groupTrendWrapperList) {
                if(gtw.getGroup().getGroupID().equals(this.getGroupID())) {
                    groupTrendWrapper = gtw;
                    break;
                }
            }
            
            //fill in missing date
            this.populateDateGaps(groupTrendWrapper.getTrendList());
            // Put on cache 
            this.getCachedTrendResults().put(this.getTimeFrame().name(), groupTrendWrapper);
            groupTrendWrapper = this.getCachedTrendResults().get(this.getTimeFrame().name());
        }
        
        Trend trend = this.getMatchingTrend(groupTrendWrapper.getTrendList());
        
        if(trend != null){
            retVal = trend.getOverall() == null ? -1 : trend.getOverall();
        }   
        
        return retVal;
    }
	
	private void populateDateGaps(List<Trend> list) {
        if(list == null || list.size() < 1)
            return;
        
        Integer gap = 0;
        
        int lastIndex = 0;
        if(list != null && list.size() > 0)
            lastIndex = list.size() -1;
        
        Trend lastTrend = list.get(lastIndex);
            
        DateTime lastEntityDateTime = new DateTime(lastTrend.getEndingDate());
        lastEntityDateTime = lastEntityDateTime.withZone(DateTimeZone.UTC);
        lastEntityDateTime = lastEntityDateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        
        DateTime currentDateTime = this.getTimeFrame().getCurrent();
        currentDateTime = currentDateTime.withZone(DateTimeZone.UTC);
        currentDateTime = currentDateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        
        if (this.getTimeFrame() == TimeFrame.TODAY || 
            this.getTimeFrame() == TimeFrame.ONE_DAY_AGO ||
            this.getTimeFrame() == TimeFrame.TWO_DAYS_AGO || 
            this.getTimeFrame() == TimeFrame.THREE_DAYS_AGO ||
            this.getTimeFrame() == TimeFrame.FOUR_DAYS_AGO || 
            this.getTimeFrame() == TimeFrame.FIVE_DAYS_AGO ||
            this.getTimeFrame() == TimeFrame.SIX_DAYS_AGO) {
            gap = Days.daysBetween(lastEntityDateTime, currentDateTime).getDays();
            
            DateTime missingDateTime = new DateTime(lastTrend.getEndingDate());
            missingDateTime = missingDateTime.withZone(DateTimeZone.UTC);
            
            for(int i = 0; i < gap; i++) {
                missingDateTime = missingDateTime.plusDays(1);
                
                Trend newTrend = new Trend();
                newTrend.setEndingDate(missingDateTime.toDate());
                newTrend.setOverall(lastTrend.getOverall());
                
                list.add(newTrend);
            }
            
            return;
            
        } else if (this.getTimeFrame() == TimeFrame.MONTH) {
            currentDateTime = currentDateTime.dayOfMonth().withMinimumValue();
            gap = Months.monthsBetween(lastEntityDateTime, currentDateTime).getMonths();
            
            DateTime missingDateTime = new DateTime(lastTrend.getEndingDate());
            
            for(int i = 0; i < gap; i++) {
                missingDateTime = missingDateTime.plusMonths(1);
                
                Trend newTrend = new Trend();
                newTrend.setEndingDate(missingDateTime.toDate());
                newTrend.setOverall(lastTrend.getOverall());
                
                list.add(newTrend);
            }
            
            return;
        }
    }
	
	private DateTime getFilterDateTime() {
        DateTime filter = this.getTimeFrame().getCurrent();
        filter = filter.withZone(DateTimeZone.UTC);
        filter = filter.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        
        if (this.getTimeFrame() == TimeFrame.ONE_DAY_AGO) {
            filter = filter.minusDays(1);
        } else if (this.getTimeFrame() == TimeFrame.TWO_DAYS_AGO) {
            filter = filter.minusDays(2);
        } else if (this.getTimeFrame() == TimeFrame.THREE_DAYS_AGO) {
            filter = filter.minusDays(3);
        } else if (this.getTimeFrame() == TimeFrame.FOUR_DAYS_AGO) {
            filter = filter.minusDays(4);
        } else if (this.getTimeFrame() == TimeFrame.FIVE_DAYS_AGO) {
            filter = filter.minusDays(5);
        } else if (this.getTimeFrame() == TimeFrame.SIX_DAYS_AGO) {
            filter = filter.minusDays(6);
        } else if (this.getTimeFrame() == TimeFrame.MONTH) {
            filter = filter.dayOfMonth().withMinimumValue();
        }
        
        return filter;
    }
    
    private Trend getMatchingTrend(List<Trend> trendlist) {
        if(trendlist == null){
            return null;
        }
        
        DateTime filterDateTime = this.getFilterDateTime();
        
        for(Trend trend : trendlist){
            DateTime dateTime = new DateTime(trend.getEndingDate());
            dateTime = dateTime.withZone(DateTimeZone.UTC); 
            dateTime = dateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            
            if(dateTime.equals(filterDateTime)){
                return trend;
            }
        }
        return null;
    }
}
