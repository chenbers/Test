package com.inthinc.pro.table.model.provider;

import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.backing.TimeFrameBean;

public abstract class BaseNotificationPaginationDataProvider<T> extends GenericPaginationTableDataProvider<T> {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(BaseNotificationPaginationDataProvider.class);
	private TimeFrameBean timeFrameBean;
	private DateTimeZone dateTimeZone;
	protected Date endDate;
	protected Date startDate;

	
	public TimeFrameBean getTimeFrameBean() {
		return timeFrameBean;
	}

	public void setTimeFrameBean(TimeFrameBean timeFrameBean) {
		this.timeFrameBean = timeFrameBean;
	}
	public DateTimeZone getDateTimeZone() {
		if (dateTimeZone == null)
			return DateTimeZone.UTC;
		return dateTimeZone;
	}

	public void setDateTimeZone(DateTimeZone dateTimeZone) {
		this.dateTimeZone = dateTimeZone;
	}

	protected void initStartEndDates() {
	    endDate = timeFrameBean.getTimeFrame().getInterval(getDateTimeZone()).getEnd().toDate();
	    startDate = timeFrameBean.getTimeFrame().getInterval(getDateTimeZone()).getStart().toDate();
	    logger.debug("date range: " + startDate + " to " + endDate);	    
	}
	
}
