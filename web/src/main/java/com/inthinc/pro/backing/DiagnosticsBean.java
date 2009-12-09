package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

public class DiagnosticsBean extends BaseEventsBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DiagnosticsBean.class);

    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
    	List<Event> warnings = getEventDAO().getWarningEventsForGroup(groupID, DAYS_BACK,showExcludedEvents);
    	
    	// Add the unknown driver, making sure the name is set
        Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());      
    	List<Event> noDriverWarnings = getEventDAO().getWarningEventsForDriver(
    	        acct.getUnkDriverID(), DateUtil.getDaysBackDate(new Date(), DAYS_BACK), new Date(), showExcludedEvents);    
        noDriverWarnings = this.loadUnknownDriver(noDriverWarnings);
    	
    	warnings.addAll(noDriverWarnings);
    	Collections.sort(warnings);
    	
        return warnings;
    }

    // TablePrefOptions interface

    @Override
    public TableType getTableType()
    {
        return TableType.DIAGNOSTICS;
    }

    public String showAllFromRecentAction()
    {
        setCategoryFilter(null);
        setEventFilter(null);

        clearData();
        return "go_diagnostics";
    }
    @Override
    protected ReportCriteria getReportCriteria()
    {
        return getReportCriteriaService().getWarningsReportCriteria(getUser().getGroupID(), getLocale());
    }

}
