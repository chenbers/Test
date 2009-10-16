package com.inthinc.pro.backing;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

public class EmergencyBean extends BaseEventsBean
{
    private static final Logger logger = Logger.getLogger(DiagnosticsBean.class);

    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
        List<Event> evtForGrp = getEventDAO().getEmergencyEventsForGroup(groupID, 7,showExcludedEvents);
        
        // Add the unknown driver
        Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());
        List<Event> evtForUnk = getEventDAO().getEventsForUnknownDriver(
                acct.getUnkDriverID(), EventMapper.getEventTypesInCategory(EventCategory.EMERGENCY), 7, showExcludedEvents);
        
        // Work with the unknown driver.
        for ( Event e: evtForUnk ) {
            Person p = new Person();
            p.setFirst("Unknown");
            p.setLast("Driver");
            
            if ( e.getDriver() == null ) {
                Driver d = new Driver();
                d.setDriverID(acct.getAcctID());
                d.setPerson(p);
                e.setDriver(d);
            } else {
                e.getDriver().setPerson(p);
            }
        }
        
        evtForGrp.addAll(evtForUnk);
        
        return evtForGrp;
    }

    // TablePrefOptions interface

    @Override
    public TableType getTableType()
    {
        return TableType.EMERGENCY;
    }

    public String showAllFromRecentAction()
    {
        setCategoryFilter(null);
        setEventFilter(null);

        clearData();
        return "go_emergency";
    }
    @Override
    protected ReportCriteria getReportCriteria()
    {
        return getReportCriteriaService().getEmergencyReportCriteria(getUser().getGroupID());
    }
}
