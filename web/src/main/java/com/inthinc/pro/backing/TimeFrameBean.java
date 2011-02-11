package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.util.TimeFrameUtil;

public class TimeFrameBean  extends BaseBean {
	
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TimeFrameBean.class);

    private TimeFrame timeFrame;
    private String timeFrameKey;
    
    
	private List<SelectItem> selectItems;
	private Map<String, TimeFrame> timeFrameMap;
	
	private boolean yearSelection;

	public boolean isYearSelection() {
        return yearSelection;
    }

    public void setYearSelection(boolean yearSelection) {
        this.yearSelection = yearSelection;
        this.selectItems = null;
    }

    public List<SelectItem> getSelectItems() {
		if (selectItems == null) {
			timeFrameMap = new HashMap<String, TimeFrame>();
			selectItems = new ArrayList<SelectItem>();
			addItem(TimeFrame.TODAY);
			addItem(TimeFrame.ONE_DAY_AGO);
			addItem(TimeFrame.TWO_DAYS_AGO);
			addItem(TimeFrame.THREE_DAYS_AGO);
			addItem(TimeFrame.FOUR_DAYS_AGO);
			addItem(TimeFrame.FIVE_DAYS_AGO);
			addItem(TimeFrame.SIX_DAYS_AGO);
			addItem(TimeFrame.WEEK);
			addItem(TimeFrame.MONTH);
			addItem(TimeFrame.LAST_THIRTY_DAYS);
			if (yearSelection)
			    addItem(TimeFrame.YEAR);
			
			if (getTimeFrameKey() == null)
				setTimeFrameKey(TimeFrame.TODAY.name());

		}
		return selectItems;
	}

	private void addItem(TimeFrame selectTimeFrame) {
		String timeFrameStr = TimeFrameUtil.getTimeFrameStr(selectTimeFrame, getLocale());
		
		SelectItem item = new SelectItem(selectTimeFrame.name(), timeFrameStr);
		timeFrameMap.put(selectTimeFrame.name(), selectTimeFrame);
		selectItems.add(item);
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
