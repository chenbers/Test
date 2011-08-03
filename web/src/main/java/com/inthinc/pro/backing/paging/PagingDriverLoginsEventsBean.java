package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.model.EventReportItem;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.LoginEvent;
import com.inthinc.pro.model.event.StatusEvent;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.model.provider.DriverLoginsPaginationTableDataProvider;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class PagingDriverLoginsEventsBean  extends BasePagingNotificationsBean<Event> {

    private static final Logger logger = Logger.getLogger(PagingDriverLoginsEventsBean.class);
    private static final long serialVersionUID = -958772233812785016L;
    private DriverLoginsPaginationTableDataProvider tableDataProvider;
    private BasePaginationTable<Event> table;

    static final List<EventCategory> CATEGORIES;
    static {
        CATEGORIES = new ArrayList<EventCategory>();
        CATEGORIES.add(EventCategory.NONE);
        CATEGORIES.add(EventCategory.DRIVER_LOGIN);
    }
    @Override
    public void init()
    {
        super.init();
        
        tableDataProvider.setDateTimeZone(DateTimeZone.forTimeZone(getUser().getPerson().getTimeZone()));
        tableDataProvider.setSort(new TableSortField(SortOrder.DESCENDING, "time"));
        tableDataProvider.setEventCategory(getEventCategory());
        tableDataProvider.getTimeFrameBean().setYearSelection(true);
        
        table = new BasePaginationTable<Event>();
        table.initModel(tableDataProvider);
    }

    @Override
    protected List<EventCategory> getCategories() {
        return CATEGORIES;
    }

    public PagingDriverLoginsEventsBean() {}

    @Override
    protected ReportCriteria getReportCriteria() {
        return getReportCriteriaService().getEventsReportCriteria(getUser().getGroupID(), getLocale());
    }
    public DriverLoginsPaginationTableDataProvider getTableDataProvider() {
        return tableDataProvider;
    }

    public void setTableDataProvider(
            DriverLoginsPaginationTableDataProvider tableDataProvider) {
        this.tableDataProvider = tableDataProvider;
    }
    protected List<Event> getReportEvents() {
        int totalCount = getTableDataProvider().getRowCount();
        if (totalCount == 0)
            return new ArrayList<Event>();
        
        return getTableDataProvider().getItemsByRange(0, totalCount-1);
    }
    @Override
    protected List<EventReportItem> getReportTableData()
    {
       List<EventReportItem> eventReportItemList = new ArrayList<EventReportItem>();
        
        List<Event> eventList = new ArrayList<Event>();
        eventList = getReportEvents();
        
        MeasurementType measurementType = this.getMeasurementType();
        String mphString = MessageUtil.getMessageString(measurementType.toString()+"_mph");
        String miString  = MessageUtil.getMessageString(measurementType.toString()+"_miles");
        String dateFormatStr = MessageUtil.getMessageString("dateTimeFormat", LocaleBean.getCurrentLocale());

        for (Event event : eventList)
        {
            String detailsFormatStr = MessageUtil.getMessageString("redflags_details" + event.getEventType());
            if (event.getDriverName() == null || event.getDriverName().isEmpty()) {
                event.setDriverName(MessageUtil.getMessageString("unknown_driver"));
            }
            String statusString = null;
            if (event instanceof StatusEvent) {
                statusString = MessageUtil.getMessageString(((StatusEvent)event).getStatusMessageKey());
            }
            eventReportItemList.add(new EventReportItem(event, getMeasurementType(), dateFormatStr, detailsFormatStr, (statusString == null) ? mphString : statusString, miString, LocaleBean.getCurrentLocale()));
            
        }
        
        return eventReportItemList;
    }

    public void refreshAction(){
        tableDataProvider.setRefreshNeeded(true);
        table.reset();
    }
    
    @Override
    public void refreshPage() {
        table.resetPage();        
    }
    public BasePaginationTable<Event> getTable() {
        return table;
    }

    public void setTable(BasePaginationTable<Event> table) {
        this.table = table;
    }

    public EventCategory getEventCategory(){
        return EventCategory.DRIVER_LOGIN;
    }
}
