package com.inthinc.pro.backing.paging;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

public class PagingSafetyEventsBean extends PagingEventsBean {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1651630462783406354L;
	
    private static final Logger logger = Logger.getLogger(PagingSafetyEventsBean.class);
	
	public PagingSafetyEventsBean()
	{
		logger.info("PagingSafetyEventsBean - constructor");
	}

	@Override
    public TableType getTableType()
    {
        return TableType.EVENTS;
    }

    @Override
    protected ReportCriteria getReportCriteria() {
        return getReportCriteriaService().getEventsReportCriteria(getUser().getGroupID(), getLocale());
    }

    @Override
    public EventCategory getEventCategory()
    {
    	return EventCategory.VIOLATION;
    }


}
