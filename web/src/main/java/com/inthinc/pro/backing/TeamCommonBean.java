package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.TimeFrame;

public class TeamCommonBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private List<String> dayLabels = new ArrayList<String>();

    private Integer groupID;
    private Group group;

    private TimeFrame timeFrame = TimeFrame.ONE_DAY_AGO;
    private String selectedTab;

    public void init() {
    	selectedTab="teamStats";
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

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

}
