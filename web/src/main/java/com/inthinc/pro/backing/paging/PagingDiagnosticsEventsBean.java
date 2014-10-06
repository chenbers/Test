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
		CATEGORIES.add(EventCategory.DVIR);
        //CATEGORIES.add(EventCategory.MAINTENANCE);
	}

	@Override
	protected List<EventCategory> getCategories() {
        Boolean testMaint = this.testMaintenance(CATEGORIES);
        if(getAccountIsMaintenance()) {
            if (testMaint == true) {
                CATEGORIES.add(EventCategory.MAINTENANCE);
            }
        }
        else {
            CATEGORIES.remove(EventCategory.MAINTENANCE);
        }
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
    	return EventCategory.DIAGNOSTICS;
    }

    public boolean testMaintenance(List<EventCategory> CATEGORIES){
        for (EventCategory eventCategory : CATEGORIES){
            if (eventCategory.valueOf(eventCategory.getCode()).toString().equals("MAINTENANCE")) {
                return false;
            }
        }
        return true;
    }

}
