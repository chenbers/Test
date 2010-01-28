package com.inthinc.pro.backing.paging;

import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

public class PagingDiagnositicsEventsBean extends PagingEventsBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3951907220194370268L;

	@Override
    public TableType getTableType()
    {
        return TableType.DIAGNOSTICS;
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
