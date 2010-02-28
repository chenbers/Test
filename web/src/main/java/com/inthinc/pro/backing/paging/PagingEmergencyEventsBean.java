package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

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
    public TableType getTableType()
    {
        return TableType.EMERGENCY;
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
