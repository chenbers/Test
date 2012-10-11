package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.reports.ReportCriteria;

@KeepAlive
public class PagingSafetyEventsBean extends PagingEventsBean {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1651630462783406354L;
	
    private static final Logger logger = Logger.getLogger(PagingSafetyEventsBean.class);
	
	static final List<EventCategory> CATEGORIES;
	static {
		CATEGORIES = new ArrayList<EventCategory>();
//		CATEGORIES.add(EventCategory.NONE);
		CATEGORIES.add(EventCategory.VIOLATION);
	}

	@Override
	protected List<EventCategory> getCategories() {
		return CATEGORIES;
	}
	
    @Override
    public void init()
    {
        super.init();
        getTableDataProvider().getTimeFrameBean().setYearSelection(true);
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
