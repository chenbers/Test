package com.inthinc.pro.backing;

import com.inthinc.pro.model.TimeFrame;

/**
 * Bean used for timeframe to be the same as long as the session
 * 
 */
public class TeamCommonBeanTimeFrame {
	private TimeFrame timeFrame = TimeFrame.ONE_DAY_AGO;

	/**
	 * @return the timeFrame
	 */
	public TimeFrame getTimeFrame() {
		return timeFrame;
	}

	/**
	 * @param timeFrame
	 *            the timeFrame to set
	 */
	public void setTimeFrame(final TimeFrame timeFrame) {
		this.timeFrame = timeFrame;
	}
}
