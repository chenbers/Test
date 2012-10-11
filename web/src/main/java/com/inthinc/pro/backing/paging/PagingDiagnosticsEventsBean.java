package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.reports.ReportCriteria;

@KeepAlive
public class PagingDiagnosticsEventsBean extends PagingEventsBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3951907220194370268L;

	static final List<EventCategory> CATEGORIES;
	static {
		CATEGORIES = new ArrayList<EventCategory>();
		CATEGORIES.add(EventCategory.NONE);
		CATEGORIES.add(EventCategory.WARNING);
	}

	@Override
	protected List<EventCategory> getCategories() {
		return CATEGORIES;
	}
	

	public PagingDiagnosticsEventsBean()
	{
	}
	
    @Override
    protected ReportCriteria getReportCriteria()
    {
        return getReportCriteriaService().getWarningsReportCriteria(getUser().getGroupID(), getLocale());
    }
    
    @Override
    public EventCategory getEventCategory()
    {
    	return EventCategory.WARNING;
    }


}
