package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.reports.ReportCriteria;

@KeepAlive
public class PagingEmergencyEventsBean extends PagingEventsBean {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1191715134502069867L;
	
	static final List<EventCategory> CATEGORIES;
	static {
		CATEGORIES = new ArrayList<EventCategory>();
		CATEGORIES.add(EventCategory.NONE);
		CATEGORIES.add(EventCategory.EMERGENCY);
	}

	@Override
	protected List<EventCategory> getCategories() {
		return CATEGORIES;
	}
	
	public PagingEmergencyEventsBean()
	{
	}
	
    @Override
    public void init()
    {
        super.init();
        getTableDataProvider().getTimeFrameBean().setYearSelection(true);
    }


    @Override
    protected ReportCriteria getReportCriteria()
    {
        return getReportCriteriaService().getEmergencyReportCriteria(getUser().getGroupID(), getLocale());
    }

    @Override
    public EventCategory getEventCategory()
    {
    	return EventCategory.EMERGENCY;
    }
	

}
