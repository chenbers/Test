package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Months;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.backing.listener.TimeFrameChangeListener;

public class TeamCommonBean extends BaseBean {
	
//	public enum TeamPageTabs {
//			STATISTICS,
//			TRIPS
//	}
    private static final long serialVersionUID = 1L;
    private List<String> dayLabels = new ArrayList<String>();

    private Integer groupID;
    private Group group;

    private TimeFrame timeFrame = TimeFrame.ONE_DAY_AGO;
    private String selectedTab;
    
    private List<TimeFrameChangeListener> timeFrameChangeListeners;

    public void init() {
    	selectedTab="statistics";
    	timeFrameChangeListeners = new ArrayList<TimeFrameChangeListener>();
    }

    public Integer getGroupID() {
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
        
        notifyTimeFrameChangeListeners();
    }
	
	public void addTimeFrameChangeListener(TimeFrameChangeListener timeFrameChangeListener){
		
		timeFrameChangeListeners.add(timeFrameChangeListener);
	}
	public void removeTimeFrameChangeListener(TimeFrameChangeListener timeFrameChangeListener){
		
		timeFrameChangeListeners.remove(timeFrameChangeListener);
	}
	public void notifyTimeFrameChangeListeners(){
		
		for (TimeFrameChangeListener tfcl:timeFrameChangeListeners){
			
			tfcl.onTimeFrameChange();
		}
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

}
