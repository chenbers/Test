package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

public class EventsBean extends BaseEventsBean
{
    private static final Logger     logger                  = Logger.getLogger(EventsBean.class);
    
    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
        return getEventDAO().getViolationEventsForGroup(groupID, 7);
    }

    // TablePrefOptions interface

    @Override
    public TableType getTableType()
    {
        return TableType.EVENTS;
    }
    
    public String showAllFromRecentAction()
    {
//        setSearchText(null);
        setCategoryFilter(null);
        setEventFilter(null);
        
        refreshAction();
        return "go_events";
    }
    
    public void exportReportToPdf()
    {
        getReportRenderer().exportSingleReportToPDF(getReportCriteria(), getFacesContext());
    }    

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(getReportCriteria(),getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(getReportCriteria(), getFacesContext());
    }
    
    private ReportCriteria getReportCriteria()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getEventsReportCriteria(getUser().getGroupID());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(getTableData());
        return reportCriteria;
    }
    
    public void setLinkFromTeam(long linkFromTeam)
    {      
        // MUST re-initialize every time to get fresh search. This 
        //  will also do a search filter with nothing set, giving us
        //  everything to start with.
        setTableData(null);
        setFilteredTableData(null);              
        getTableData();
     
        // Search ALL columns for the passed search field.  Did not 
        //  drive the method further down as I thought it would be 
        //  used exclusively here for these types of "items".
        final ArrayList<EventReportItem> searchTableDataResult = new ArrayList<EventReportItem>();
        searchTableDataResult.addAll(getFilteredTableData());
        filterOnLinkFromTeam(searchTableDataResult,linkFromTeam,true);
        setFilteredTableData(searchTableDataResult);        
    }    
    
    public void filterOnLinkFromTeam(List<EventReportItem> filterItems, long filterValueInt, boolean matchAllWords)
    {
        String filterValue = String.valueOf(filterValueInt);
        if ((filterValue != null) && (filterValue.length() > 0))
        {
            final String[] filterWords = filterValue.toLowerCase().split("\\s+");
            for (Iterator<EventReportItem> i = filterItems.iterator(); i.hasNext();)
            {
                final EventReportItem o = i.next();
                boolean matched = false;
                for (final String word : filterWords)
                {
                     
                    final String value = String.valueOf(o.getNoteID());
                    if ((value != null) && value.toLowerCase().contains(word))
                        matched = true;

                    // we can break if we didn't match and we're required to match all words,
                    // or if we did match and we're only required to match one word
                    if (matched ^ matchAllWords)
                        break;
                }
                if (!matched)
                    i.remove();
            }
        }
    }        
}
