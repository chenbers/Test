package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;

import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.util.MessageUtil;

public class TimeFrameBean  extends BaseBean {
	
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TimeFrameBean.class);

    private TimeFrame timeFrame;
    private String timeFrameKey;
    
    
	private List<SelectItem> selectItems;
	private Map<String, TimeFrame> timeFrameMap;

	public List<SelectItem> getSelectItems() {
		if (selectItems == null) {
			timeFrameMap = new HashMap<String, TimeFrame>();
			selectItems = new ArrayList<SelectItem>();
			addTimeFrameItem(TimeFrame.TODAY);
			addTimeFrameItem(TimeFrame.ONE_DAY_AGO);
			addDayItem(TimeFrame.TWO_DAYS_AGO, 2);
			addDayItem(TimeFrame.THREE_DAYS_AGO, 3);
			addDayItem(TimeFrame.FOUR_DAYS_AGO, 4);
			addDayItem(TimeFrame.FIVE_DAYS_AGO, 5);
			addDayItem(TimeFrame.SIX_DAYS_AGO, 6);
			addTimeFrameItem(TimeFrame.WEEK);
			addMonthItem(TimeFrame.MONTH);
			addTimeFrameItem(TimeFrame.YEAR);
			
			if (getTimeFrameKey() == null)
				setTimeFrameKey(TimeFrame.TODAY.name());

		}
		return selectItems;
	}

	private void addTimeFrameItem(TimeFrame selectTimeFrame)
	{
		String timeFrameStr = MessageUtil.getMessageString("timeFrame_"+selectTimeFrame.name(), getLocale());
		addItem(selectTimeFrame, timeFrameStr);
	}

	private void addDayItem(TimeFrame selectTimeFrame, int index)
	{
		String timeFrameStr = getDayLabels().get(index);
		addItem(selectTimeFrame, timeFrameStr);
	}
	private void addMonthItem(TimeFrame selectTimeFrame)
	{
		String timeFrameStr = getMonthLabel();
		addItem(selectTimeFrame, timeFrameStr);
	}

	private void addItem(TimeFrame selectTimeFrame, String timeFrameStr) {
		SelectItem item = new SelectItem(selectTimeFrame.name(), timeFrameStr);
		timeFrameMap.put(selectTimeFrame.name(), selectTimeFrame);
		selectItems.add(item);
	}

    private List<String> dayLabels = new ArrayList<String>();
    public List<String> getDayLabels() {

        if (dayLabels.isEmpty()) {
            Interval interval = new Interval(Days.SEVEN, new DateMidnight());
            for (int i = 0; i < 7; i++) {
                dayLabels.add(interval.getEnd().minusDays(i).dayOfWeek().getAsText(getLocale()));
            }
        }

        return dayLabels;
    }
    
    public String getMonthLabel(){
        return new DateTime().monthOfYear().getAsText(getLocale());
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame newTimeFrame) {
    	if (newTimeFrame != null && !newTimeFrame.name().equals(timeFrameKey))
    		timeFrameKey = newTimeFrame.name();
        this.timeFrame = newTimeFrame;
    }

    public String getTimeFrameKey() {
		return timeFrameKey;
	}

	public void setTimeFrameKey(String timeFrameKey) {
		this.timeFrameKey = timeFrameKey;
		if (timeFrameMap != null) {
			setTimeFrame(timeFrameMap.get(timeFrameKey));
		}
		else {
			setTimeFrame(null);
		}
	}


}
