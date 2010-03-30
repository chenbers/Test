package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.reports.ReportCriteria;

public class PagingSafetyEventsBean extends PagingEventsBean {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1651630462783406354L;
	
    private static final Logger logger = Logger.getLogger(PagingSafetyEventsBean.class);
	
	static final List<EventCategory> CATEGORIES;
	static {
		CATEGORIES = new ArrayList<EventCategory>();
		CATEGORIES.add(EventCategory.NONE);
		CATEGORIES.add(EventCategory.VIOLATION);
	}

	@Override
	protected List<EventCategory> getCategories() {
		return CATEGORIES;
	}
	
	public PagingSafetyEventsBean()
	{
		logger.info("PagingSafetyEventsBean - constructor");
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
