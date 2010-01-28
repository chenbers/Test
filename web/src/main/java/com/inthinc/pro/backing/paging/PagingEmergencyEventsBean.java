package com.inthinc.pro.backing.paging;

import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

public class PagingEmergencyEventsBean extends PagingEventsBean {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1191715134502069867L;

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
